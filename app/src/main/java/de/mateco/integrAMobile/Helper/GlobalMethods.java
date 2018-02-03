package de.mateco.integrAMobile.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;

public class GlobalMethods
{
    public static void callToNumber(Context context, TextView textView)
    {
        String phoneNumber = textView.getText().toString();
        if(!phoneNumber.equals(""))
        {
            String uri = "tel:" + phoneNumber.replaceAll("[^0-9|\\+]", "");
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
            context.startActivity(intent);
        }
    }

    public static void sendEmail(Context context, TextView textView)
    {
        String emailAddress = textView.getText().toString();
        sendEmail(context, emailAddress, "", "");
    }

    public static void sendEmail(Context context, String emailAddress, String emailSubject, String emailMessage)
    {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{emailAddress});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, emailSubject);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailMessage);
        context.startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
    }

    public static void sendSupportEmail(Context context)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{Constants.emailAddress});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, Constants.emailSubjectForGeneral);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("plain/text");
        context.startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
    }

    public static void sendEmail(Context context, ArrayList<Uri> listOfFileUri)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{Constants.emailAddress});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, Constants.emailSubjectForProblem);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("application/octet-stream");
        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, listOfFileUri);
        context.startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
    }

    public static void openLink(Context context, TextView textView)
    {
        try
        {
            String url = textView.getText().toString();
            if(!url.equals(""))
            {
                if(!url.startsWith("http://") || !url.startsWith("https://"))
                {
                    url = "http://" + url;
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static boolean isNullOrWhiteSpace(String value)
    {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isZero(String value)
    {
        if(value == null || value.trim().isEmpty())
        {
            return true;
        }
        else
        {
            if(Double.parseDouble(value) == 0)
            {
                return  true;
            }
        }
        return false;
    }

    public static void setBlankValueForZero(EditText editText)
    {
        if(editText != null && editText.getText().toString().trim().equals(("0,00")))
        {
            editText.setText("");
        }
    }

    public static String getBlankValueForZero(String value)
    {
        if(value != null && value.toString().trim().equals(("0,00")))
        {
            return "";
        }
        else
        {
            return value;
        }
    }

    public static File getAppExternalStoragePath()
    {
        String root = Environment.getExternalStorageDirectory().toString();
        File newDir = new File(root + "/MatecoSales");
        newDir.mkdirs();
        return newDir;
    }

    public static void sendBackupToUs(Context context)
    {
        File backupDbFilePath = exportDatabase(context);
        File backupPrefrenceFilePath = saveSharedPreferencesToFile(context);
        ArrayList<Uri> items = new ArrayList<Uri>();
        if(backupDbFilePath != null){
            Uri uri = Uri.fromFile(backupDbFilePath);
            items.add(uri);
            uri = Uri.fromFile(backupPrefrenceFilePath);
            items.add(uri);
            sendEmail(context, items);
        }

    }

    public static File exportDatabase(Context context)
    {
        try
        {
            String databaseName = DataBaseHandler.DATABASE_NAME;
            File sd = getAppExternalStoragePath();
            File data = Environment.getDataDirectory();
            if (sd.canWrite())
            {
                String currentDBPath = "//data//" + context.getPackageName() + "//databases//"+databaseName+"";
                String backupDBPath = "backupname_" + System.currentTimeMillis() + ".db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists())
                {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
                return backupDB;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private static File saveSharedPreferencesToFile(Context context)
    {
        boolean res = false;
        ObjectOutputStream output = null;
        File sd = getAppExternalStoragePath();
        String backupPrefPath = "backupname_" + System.currentTimeMillis() + ".txt";
        //FileChannel dst = new FileOutputStream(backupPrefPath).getChannel();
        File dst = new File(sd, backupPrefPath);
        try {
            output = new ObjectOutputStream(new FileOutputStream(dst));

            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);;
            output.writeObject(pref.getAll());

            res = true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (output != null) {
                    output.flush();
                    output.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if(res == true)
            return dst;
        return null;
    }

    public static String getAppVersion(Context context)
    {
        PackageInfo pInfo = null;
        try
        {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    public static List<Address> reverseGeoCode(Activity activity, Location location)
    {
        Geocoder geoCoder = new Geocoder(activity);
        List<Address> addressList = null;
        try {
            return geoCoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkForNotNull(String value)
    {
        if(value == null)
        {
            return false;
        }
        else if(value.equalsIgnoreCase("null"))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
