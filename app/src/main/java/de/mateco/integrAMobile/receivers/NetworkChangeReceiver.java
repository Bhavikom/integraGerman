package de.mateco.integrAMobile.receivers;

/**
 * Created by mmehta on 31.05.2016.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.LogApp;
import de.mateco.integrAMobile.Helper.NetworkUtil;
import de.mateco.integrAMobile.asyncTask.AsynchTaskWithAuthorizationHeaderPost2;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.PriceInfoModelClass;


public class NetworkChangeReceiver extends BroadcastReceiver
{
    ArrayList<PriceInfoModelClass> arrylistJson = new ArrayList<>();
    private Language language;
    public Context con;
    DataBaseHandler databaseHandler;
    private MatecoPriceApplication matecoPriceApplication;
    @Override
    public void onReceive(final Context context, final Intent intent) {

        databaseHandler = new DataBaseHandler(context);
        con = context;
        matecoPriceApplication = (MatecoPriceApplication) con.getApplicationContext();
        language = matecoPriceApplication.getLanguage();
        String status = NetworkUtil.getConnectivityStatusString(context);

        if(status.equalsIgnoreCase("WIFI CONNECTION") || status.equalsIgnoreCase("MOBILE DATA"))
        {
            try {
                if(databaseHandler.getCountPRice() > 0){
                    arrylistJson= databaseHandler.getPriceInfo();

                    if(arrylistJson.size() > 0){
                        callWebservice();
                    }
                }

            }
            catch (Exception e){
            }
        }
        else {
        }

    }
    private void callWebservice()
    {
        if(DataHelper.isNetworkAvailable(con))
        {
            AsynchTaskWithAuthorizationHeaderPost2.OnAsyncResult onAsyncResult = new AsynchTaskWithAuthorizationHeaderPost2.OnAsyncResult()
            {
                @Override
                public void OnAsynResult(String result)
                {
                    LogApp.showLog(" &&&&&"," result varaible after successfully service call :  "+result);
                    try
                    {
                        if(result.equalsIgnoreCase("error")){
                            if(arrylistJson.size() > 0){
                                arrylistJson.remove(0);
                            }
                            if(arrylistJson.size() > 0){
                                if(DataHelper.isNetworkAvailable(con)) {
                                    callWebservice();
                                }
                                else {
                                    arrylistJson.clear();
                                }

                            }
                        }
                        else if(result.equalsIgnoreCase(""))
                        {
                            if(arrylistJson.size() > 0){
                                arrylistJson.remove(0);
                            }
                            if(arrylistJson.size() > 0){
                                if(DataHelper.isNetworkAvailable(con)) {
                                    callWebservice();
                                }
                                else {
                                    arrylistJson.clear();
                                }

                            }
                        }
                        else {
                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray jsonArray = jsonObject.getJSONArray("Warenkorb");
                            if(jsonArray.length()> 0)
                            {
                                databaseHandler.deletePriceInfo(arrylistJson.get(0).getId());
                                arrylistJson.remove(0);
                                if(arrylistJson.size() > 0){
                                    callWebservice();
                                }
                            }
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            };
            try {
                String url = DataHelper.URL_PRICE_HELPER+"priceinsert";
                MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")));
                multipartEntity.addPart("priceinsert", new StringBody(arrylistJson.get(0).getStrJson(), Charset.forName("UTF-8")));
                AsynchTaskWithAuthorizationHeaderPost2 asyncTaskPost = new AsynchTaskWithAuthorizationHeaderPost2(url, onAsyncResult, con , multipartEntity, true, language);
                asyncTaskPost.execute();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {

        }
    }
}
