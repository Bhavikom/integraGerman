package de.mateco.integrAMobile.Helper;

/**
 * Created by mmehta on 31.05.2016.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.TransportDetailModel;


public class GlobalClass
{
    public static boolean isFirsttime=false;
    public static final String ROOT_URL = "http://sm.ssoft.in/StudService.svc";
    public static Context context;
    public static int SCREEN_HEIGHT;
    public static int SCREEN_WIDTH;
    public static ProgressDialog progressDialog;
    public static ArrayList<TransportDetailModel> arraylistTransportDetail = new ArrayList<>();
    public static Language language;
    public GlobalClass()
    {

    }
    public GlobalClass(Context context,Language launguage)
    {
        GlobalClass.context = context;
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        SCREEN_HEIGHT = displaymetrics.heightPixels;
        SCREEN_WIDTH = displaymetrics.widthPixels;
        this.language=launguage;

    }
    public static boolean isEmailValid(String email)
    {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static void showProgressDialog(Activity activity,Language langueage)
    {
        progressDialog= new ProgressDialog(activity);
        progressDialog.setTitle(langueage.getMessageWaitWhileLoading());
        progressDialog.setMessage(langueage.getMessageWaitWhileLoading());
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    public static void dismissProgressDialog()
    {
        Log.v("11111","dismiss progress dialog :"+progressDialog);
        try {
            if(progressDialog != null){
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            Log.v(""," in catch in global method for progress: : "+e.toString());
        }


    }
    public static void showToast(Activity activity, String text)
    {
        Toast.makeText(activity,text, Toast.LENGTH_SHORT).show();
    }
    public static void showToastInternet(Activity activity, String text)
    {
        Toast.makeText(activity,"Please check internet connection.", Toast.LENGTH_SHORT).show();
    }
    public static void showToastInternet(Activity activity)
    {
        Toast.makeText(activity,"Please check internet connection", Toast.LENGTH_SHORT).show();
    }
    public static void showToastEmail(Activity activity)
    {
        Toast.makeText(activity,"Please enter valid email address", Toast.LENGTH_SHORT).show();
    }
    public static boolean intToBool(int iVal)
    {
        boolean b=true;
        if(iVal==0)b=false;
        return b;
    }

    public static int boolToInt(boolean b) {
        return b ? 1 : 0;
    }





}
