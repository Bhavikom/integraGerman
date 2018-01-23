package de.mateco.integrAMobile.Helper;

import android.util.Log;

/**
 * Created by S Soft on 23-Jan-18.
 */

public class LogApp {
    static boolean  flag = true;

    public static void showLog(String tag,String message){
        if(flag) {
            Log.e(tag, message);
        }
    }
}
