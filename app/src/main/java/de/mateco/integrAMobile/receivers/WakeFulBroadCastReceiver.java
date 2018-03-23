package de.mateco.integrAMobile.receivers;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.WakefulBroadcastReceiver;

public class WakeFulBroadCastReceiver extends WakefulBroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.isConnected())
        {
            Intent service = new Intent(context, UploadService.class);
            service.putExtra("bvoId", intent.getExtras().getInt("bvoId"));
            startWakefulService(context, service);
        }
    }
}
