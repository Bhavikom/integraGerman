package de.mateco.integrAMobile.asyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.zip.GZIPInputStream;

import de.mateco.integrAMobile.Helper.CustomSSLFactory;
import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.GlobalClass;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.model.Language;

/**
 * simple asynctask for calling service of get request
 */
public class BasicAsyncTaskGetRequest2 extends AsyncTask<NameValuePair, Void, String>
{
	private String url;
	private OnAsyncResult listener;
	private Context context;
	private ProgressDialog prd;
	private boolean isProgressEnabled;
    private Language language;
    private MatecoPriceApplication matecoPriceApplication;
    long startTime;
    long elapsedTime;
    HttpEntity httpEntity;
    byte[] bytesArray = null;
	public BasicAsyncTaskGetRequest2(String urls, OnAsyncResult listener, Context context, boolean isProgressEnabled)
	{
		this.url = urls;
		this.listener = listener;
		this.context = context;
		this.isProgressEnabled = isProgressEnabled;
        matecoPriceApplication = (MatecoPriceApplication)((Activity)context).getApplication();
        language = matecoPriceApplication.getLanguage();
	}
	
	@Override
	protected void onPreExecute() 
	{
		super.onPreExecute();
		if(isProgressEnabled)
		{
			prd = new ProgressDialog(context);
			prd.setCancelable(false);
			prd.setTitle(language.getMessageWaitWhileLoading());
			prd.setMessage(language.getMessageWaitWhileLoading());
			prd.show();
		}		
	}
	
	@Override
	protected String doInBackground(NameValuePair... arguments)
	{
        try {

            startTime = System.currentTimeMillis();

            URL url2 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)url2.openConnection();
            conn.setRequestProperty("Accept-Encoding", "gzip");
            InputStream inputStream = new GZIPInputStream(conn.getInputStream());

            HttpClient httpclient = CustomSSLFactory.getNewHttpClient();
            //HttpClient httpclient = new DefaultHttpClient();
            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 50000);
            httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 50000);
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Accept-Encoding", "gzip");
            HttpResponse response = httpclient.execute(httpGet);

            httpEntity = response.getEntity();
            //return EntityUtils.toByteArray(response.getEntity());
            bytesArray = EntityUtils.toByteArray(response.getEntity());
            return EntityUtils.toString(response.getEntity());
           /*
            ArrayList<BasicNameValuePair> reqArguments = new ArrayList<BasicNameValuePair>();
            for (int i = 0; i < arguments.length; i++)
            {
                BasicNameValuePair argument = new BasicNameValuePair(
                        arguments[i].getName(), arguments[i].getValue());
                reqArguments.add(argument);
            }
			HttpResponse response = httpclient.execute(httpGet);
			if (response == null)
			{
				return "error";
			}
			else
			{
				HttpEntity httpentity = response.getEntity();
				InputStream input = httpentity.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(input, "UTF-8"));
				StringBuilder sb = new StringBuilder();
				String ch = null;
				while ((ch = br.readLine()) != null)
				{
					sb.append(ch);
				}
				input.close();
				char[] utf8 = null;
				StringBuilder properString = new StringBuilder("");
				utf8 = sb.toString().toCharArray();
				for (int i = 0; i < utf8.length; i++)
				{
					if ((int) utf8[i] < 65000)
					{
						properString.append(utf8[i]);
					}
				}
				return properString.toString();
            }*/
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
            return "error";
        }
	}

	public interface OnAsyncResult 
	{
		void OnAsynResult(String result,byte[] bytes);
	}

	protected void onPostExecute(String result)
	{
        elapsedTime = System.currentTimeMillis() - startTime;
        int seconds = (int) (elapsedTime / 1000) % 60 ;
        try {
            if(isProgressEnabled)
            {
                prd.dismiss();
            }
            if (!result.equals("error"))
            {
                listener.OnAsynResult(result,bytesArray);
            }
            else
            {
                listener.OnAsynResult("error",null);
            }
        }
        catch (Exception e){

        }
	}
}