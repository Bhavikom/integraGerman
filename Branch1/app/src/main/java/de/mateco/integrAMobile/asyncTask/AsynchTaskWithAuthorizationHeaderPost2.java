package de.mateco.integrAMobile.asyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.CustomSSLFactory;
import de.mateco.integrAMobile.model.Language;

/**
 * async task used for setting multipart entity
 */
public class AsynchTaskWithAuthorizationHeaderPost2 extends AsyncTask<BasicNameValuePair, Void, String>
{
    private String url;
    private OnAsyncResult listener;
    private Context context;
    //private ProgressDialog prd;
    private boolean isProcessDialogEnabled;
    private MultipartEntity multipartEntity;
    private Language language;
    long startTime;
    long elapsedTime;

    public AsynchTaskWithAuthorizationHeaderPost2(String urls, OnAsyncResult listener,
                                                  Context context, MultipartEntity multipartEntity,boolean isProcessDialogEnabled, Language language)
    {
        this.url = urls;
        this.listener = listener;
        this.context = context;
        this.isProcessDialogEnabled = isProcessDialogEnabled;
        this.multipartEntity = multipartEntity;
        this.language = language;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        if(isProcessDialogEnabled)
        {
           // prd = new ProgressDialog(context);
           // prd.setTitle(language.getMessageWaitWhileLoading());
           // prd.setMessage(language.getMessageWaitWhileLoading());
           // prd.show();
        }
    }

    @Override
    protected String doInBackground(BasicNameValuePair... arguments)
    {
        startTime = System.currentTimeMillis();

        HttpClient httpclient = CustomSSLFactory.getNewHttpClient();
        HttpPost httppost = new HttpPost(url);
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 40000);
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 40000);
        Log.e("submit button : ","url at execute on submit  : " +url);
//        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
//        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
        ArrayList<BasicNameValuePair> reqArguments = new ArrayList<BasicNameValuePair>();

        for (int i = 0; i < arguments.length; i++)
        {
            BasicNameValuePair argument = new BasicNameValuePair(arguments[i].getName(), arguments[i].getValue());
            reqArguments.add(argument);
        }
        try
        {
            httppost.setEntity(multipartEntity);
            HttpResponse response = httpclient.execute(httppost);
            //Log.e(" SSSSS"," return from service : "+EntityUtils.toString(response.getEntity()));
            return EntityUtils.toString(response.getEntity());
        }
        catch (Exception e)
        {
            Log.e(" AAAAAAAAAAAAAAAAAA"," exception while callinge service : "+e.toString());
            e.printStackTrace();
        }
        return "error";
    }

    public interface OnAsyncResult
    {
        void OnAsynResult(String result);
    }

    protected void onPostExecute(String result)
    {
        elapsedTime = System.currentTimeMillis() - startTime;
        int seconds = (int) (elapsedTime / 1000) % 60 ;
        Log.e(""," total time that service has elapsed : "+seconds);
        Log.e("*&*&*&*&*&*&*&*&","on post execute result varialbe : "+result);
        if(isProcessDialogEnabled)
        {
           // prd.dismiss();
        }
        if(!result.equals("error"))
        {
            if (listener != null)
            {
                listener.OnAsynResult(result);
            }
        }
        else
        {
            listener.OnAsynResult("error");
        }
    }
}
