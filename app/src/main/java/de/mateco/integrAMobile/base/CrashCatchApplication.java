package de.mateco.integrAMobile.base;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.Log;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;

import de.mateco.integrAMobile.CrashDisplayActivity;
import de.mateco.integrAMobile.Helper.LogApp;

public class CrashCatchApplication extends Application
{
    private static CrashCatchApplication instance;
    private static final String LOG_TAG = "CrashCatch";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //startCatcher();
    }
    public static CrashCatchApplication getInstance(){
        return instance;
    }
    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if(appDir.exists()){
            String[] children = appDir.list();
            for(String s : children){
                if(!s.equals("lib")){
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "File /data/data/APP_PACKAGE/" + s +" DELETED ");
                }
            }
        }
    }
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    private void startCatcher() {
        UncaughtExceptionHandler systemUncaughtHandler = Thread.getDefaultUncaughtExceptionHandler();
        // the following handler is used to catch exceptions thrown in background threads
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtHandler(new Handler()));

        while (true) {
            try {
                Looper.loop();
                Thread.setDefaultUncaughtExceptionHandler(systemUncaughtHandler);
                throw new RuntimeException("Main thread loop unexpectedly exited");
            } catch (BackgroundException e) {
                showCrashDisplayActivity(e.getCause());
            } catch (Throwable e) {
                showCrashDisplayActivity(e);
            }
        }
    }

    void showCrashDisplayActivity(Throwable e) {
        Intent i = new Intent(this, CrashDisplayActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("e", e);
        startActivity(i);

    }


    /**
     * This handler catches exceptions in the background threads and propagates them to the UI thread
     */
    static class UncaughtHandler implements UncaughtExceptionHandler
    {

        private final Handler mHandler;

        UncaughtHandler(Handler handler) {
            mHandler = handler;
        }

        public void uncaughtException(Thread thread, final Throwable e) {
            final int tid = Process.myTid();
            final String threadName = thread.getName();
            mHandler.post(new Runnable() {
                public void run() {
                    throw new BackgroundException(e, tid, threadName);
                }
            });
        }
    }

    /**
     * Wrapper class for exceptions caught in the background
     */
    static class BackgroundException extends RuntimeException
    {

        final int tid;
        final String threadName;

        /**
         * @param e original exception
         * @param tid id of the thread where exception occurred
         * @param threadName name of the thread where exception occurred
         */
        BackgroundException(Throwable e, int tid, String threadName) {
            super(e);
            this.tid = tid;
            this.threadName = threadName;
        }
    }

}
