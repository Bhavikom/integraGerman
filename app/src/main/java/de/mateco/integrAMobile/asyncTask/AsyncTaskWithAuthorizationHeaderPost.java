package de.mateco.integrAMobile.asyncTask;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.CustomSSLFactory;
import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.LogApp;
import de.mateco.integrAMobile.model.Language;

/**
 * async task used for setting multipart entity
 */
public class AsyncTaskWithAuthorizationHeaderPost extends AsyncTask<BasicNameValuePair, Void, String>
{
	private String url;
	private OnAsyncResult listener;
	private Context context;
	private ProgressDialog prd;
    private boolean isProcessDialogEnabled;
    private MultipartEntity multipartEntity;
	private Language language;
	long startTime;
	long elapsedTime;

	public AsyncTaskWithAuthorizationHeaderPost(String urls, OnAsyncResult listener, Context context,
			MultipartEntity multipartEntity,boolean isProcessDialogEnabled, Language language)
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
            prd = new ProgressDialog(context);
			prd.setCancelable(false);
            prd.setTitle(language.getMessageWaitWhileLoading());
            prd.setMessage(language.getMessageWaitWhileLoading());
            prd.show();
        }
	}
	
	@Override
	protected String doInBackground(BasicNameValuePair... arguments)
	{
		startTime = System.currentTimeMillis();

        HttpClient httpclient = CustomSSLFactory.getNewHttpClient();

		HttpPost httppost = new HttpPost(url);
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 50000);
       	httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 50000);
		ArrayList<BasicNameValuePair> reqArguments = new ArrayList<BasicNameValuePair>();
		try
		{
			for (int i = 0; i < arguments.length; i++)
			{
				BasicNameValuePair argument = new BasicNameValuePair(arguments[i].getName(),
						arguments[i].getValue());
				reqArguments.add(argument);
			}

            httppost.setEntity(multipartEntity);
			HttpResponse response = httpclient.execute(httppost);
			return EntityUtils.toString(response.getEntity());
		}
		catch (UnknownHostException ex)
		{
			ex.printStackTrace();
			return DataHelper.NetworkError;
		}
		catch (SocketException ex)
		{
			ex.printStackTrace();
			return DataHelper.NetworkError;
		}
		catch (ConnectTimeoutException ex)
		{
			ex.printStackTrace();
			return DataHelper.NetworkError;
		}
		catch (ConnectionClosedException ex)
		{
			ex.printStackTrace();
			return DataHelper.NetworkError;
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
			return "error";
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return "error";
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return DataHelper.NetworkError;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(isProcessDialogEnabled)
			{
				prd.dismiss();
			}
			return "error";
		}
	}


	public interface OnAsyncResult 
	{
		void OnAsynResult(String result);
	}

	protected void onPostExecute(String result) 
	{
		elapsedTime = System.currentTimeMillis() - startTime;
		int seconds = (int) (elapsedTime / 1000) % 60 ;
		LogApp.showLog(""," total time that service has elapsed : "+seconds);
        if(isProcessDialogEnabled)
        {
			if(prd != null){
				prd.dismiss();
			}

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