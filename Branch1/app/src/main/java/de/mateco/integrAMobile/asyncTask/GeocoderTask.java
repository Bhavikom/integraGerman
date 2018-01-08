package de.mateco.integrAMobile.asyncTask;

import android.content.Context;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import de.mateco.integrAMobile.Helper.DataHelper;

public class GeocoderTask extends AsyncTask<Location, Void, HashMap<String, String>> {

    private Context context;
    private Geocoder geocoder;
    private LookupResult resultCallback;
    HashMap<String, String> results = new HashMap<String, String>();

    public GeocoderTask(Context context, LookupResult callback)
    {
        //super();
        this.context = context;
        this.resultCallback = callback;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected HashMap<String, String> doInBackground(Location... locations)
    {
        try
        {
            HttpClient httpClient = new DefaultHttpClient();
//            String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" +
//                  locations[0].getLatitude() + "," + locations[0].getLongitude() +
//                    "&sensor=true" +"&key=AIzaSyC0VxGNVV9G8VoAdtnqo3LPhaBUOdAeVkY";
            String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" +
                    locations[0].getLatitude() + "," + locations[0].getLongitude()+ "&sensor=true"
                    + "&client=gme-matecogmbh";
            url = DataHelper.generateSignedUrl(url);
            Log.e("url", url);
            if(!url.equals("error"))
            {
                HttpPost httpPost = new HttpPost(url);
                HttpResponse httpResponse = null;
                httpResponse = httpClient.execute(httpPost);
                if (httpResponse != null)
                {
                    HttpEntity httpentity = httpResponse.getEntity();
                    InputStream input = httpentity.getContent();
                    BufferedReader br = new BufferedReader(new InputStreamReader(input, "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    String ch;
                    while ((ch = br.readLine()) != null)
                    {
                        sb.append(ch);
                    }
                    input.close();
                    char[] utf8;
                    StringBuilder properString = new StringBuilder("");
                    utf8 = sb.toString().toCharArray();
                    for (int i = 0; i < utf8.length; i++)
                    {
                        if ((int) utf8[i] < 65000)
                        {
                            properString.append(utf8[i]);
                        }
                    }
                    Log.e("result",properString.toString());
                    JSONObject jsonObject = new JSONObject(properString.toString());

                    if (jsonObject.getString("status").equals("OK"))
                    {
                        JSONArray resultsArray = jsonObject.getJSONArray("results");
                        JSONArray addressComponents = resultsArray.getJSONObject(0).
                                getJSONArray("address_components");
                        for (int i = 0; i < addressComponents.length(); i++)
                        {
                            if (addressComponents.getJSONObject(i).
                                    getJSONArray("types").toString().contains("street_number"))
                            {
                                Log.e("street",addressComponents.getJSONObject(i).getString("short_name"));
                                results.put("StreetNumber", addressComponents.
                                        getJSONObject(i).getString("short_name"));
                            }
                            if (addressComponents.getJSONObject(i).
                                    getJSONArray("types").toString().contains("route"))
                            {
                                Log.e("route",addressComponents.getJSONObject(i).getString("short_name"));
                                results.put("Route", addressComponents.getJSONObject(i).getString("short_name"));
                            }
                            if (addressComponents.getJSONObject(i).
                                    getJSONArray("types").toString().contains("postal_code"))
                            {
                                Log.e("code",addressComponents.getJSONObject(i).getString("short_name"));
                                results.put("PostalCode", addressComponents.
                                        getJSONObject(i).getString("short_name"));
                            }
                            if (addressComponents.getJSONObject(i).
                                    getJSONArray("types").toString().contains("locality"))
                            {
                                Log.e("locality",addressComponents.getJSONObject(i).getString("long_name"));
                                results.put("Locality", addressComponents.getJSONObject(i).getString("long_name"));
                            }
                            if (addressComponents.getJSONObject(i).
                                    getJSONArray("types").toString().contains("administrative_area_level_1")) {
                                Log.e("area", addressComponents.getJSONObject(i).getString("long_name"));
                                results.put("Area", addressComponents.getJSONObject(i).getString("long_name"));
                            }
                        }
                    }
                    return results;
                }
                return null;
            }
            return null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(HashMap<String, String> result)
    {
        super.onPostExecute(result);
        if (result != null && resultCallback != null)
        {
            resultCallback.onLookupComplete(result);
        }
        else
        {
            resultCallback.onLookupComplete(null);
        }
    }

    /*public interface LookupResult
    {
        void onLookupComplete(Address result);
    }*/

    public interface LookupResult
    {
        void onLookupComplete(HashMap<String, String> results);
    }
}
