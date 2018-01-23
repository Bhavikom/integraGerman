package de.mateco.integrAMobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import java.io.File;

import de.mateco.integrAMobile.base.MatecoPriceApplication;
import io.fabric.sdk.android.Fabric;

public class CrashDisplayActivity extends Activity
{
    Button btnYes,btnNo;
    private Thread.UncaughtExceptionHandler defaultUEH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.crash_dialog);
        btnYes=(Button)findViewById(R.id.btnYes);
        btnNo=(Button)findViewById(R.id.btnNo);

        btnYes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //finish();
                MatecoPriceApplication.getInstance().clearApplicationData();
                Fabric.with(CrashDisplayActivity.this, new Crashlytics());
                MultiDex.install(CrashDisplayActivity.this);
                defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
                finishApplication();


            }
        });
        btnNo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fabric.with(CrashDisplayActivity.this, new Crashlytics());
                MultiDex.install(CrashDisplayActivity.this);
                defaultUEH = Thread.getDefaultUncaughtExceptionHandler();

                moveTaskToBack(true);
                finish();
                startActivity(new Intent(CrashDisplayActivity.this, HomeActivity.class));
                android.os.Process.killProcess(android.os.Process.myPid());
                //CrashCatchApplication.getInstance().clearApplicationData();

            }
        });

        Throwable exception = (Throwable) getIntent().getSerializableExtra("e");
        Throwable cause = findCause(exception);


// since API 14 one may also use android.app.ApplicationErrorReport.CrashInfo class 
//  to easily parse exception details into human readable form, like this:
//    
//    CrashInfo crashInfo = new CrashInfo(mException);
//    String exClassName = crashInfo.exceptionClassName;
//    String exStackTrace = crashInfo.stackTrace;

    }


    private Throwable findCause(Throwable exception) {
        Throwable prev = null;
        Throwable cause = exception;
        while (cause.getCause() != null && cause != prev) {
            prev = cause;
            cause = cause.getCause();
        }
        return cause;
    }

    @Override
    public void onBackPressed() {
        return;
    }

    public void onCloseClick(View v) {
        finishApplication();
    }

    // Kill the application in the way it won't be auto restarted by Android.
    //
    // Alternatively we may use some bootstrap activity, start it here with CLEAR_TOP flag
    // and then call finish() in its onCreate() method
    private void finishApplication() {
        moveTaskToBack(true);
        finish();
        startActivity(new Intent(this, LoginActivity.class));
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
