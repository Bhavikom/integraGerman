package de.mateco.integrAMobile.receivers;

/**
 * Created by mmehta on 31.05.2016.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.GlobalClass;
import de.mateco.integrAMobile.Helper.LogApp;
import de.mateco.integrAMobile.Helper.NetworkUtil;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.Pricing3LostSaleDataAdapter;
import de.mateco.integrAMobile.asyncTask.AsyncTaskWithAuthorizationHeaderPost;
import de.mateco.integrAMobile.asyncTask.AsynchTaskWithAuthorizationHeaderPost2;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.PriceInfoModelClass;
import de.mateco.integrAMobile.model.Pricing2InsertPriceUseInformationListData;
import de.mateco.integrAMobile.model.PricingCustomerOrderBasicInfo;


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
            //GlobalClass.showToast((Activity) context,"Network is availabe now calling services");
            //Toast.makeText(context,"Netwok available now ",Toast.LENGTH_SHORT).show();
            // network available here
            //GlobalClass.arraylistTransportDetail.clear();
            try {
                if(databaseHandler.getCountPRice() > 0){
                    arrylistJson= databaseHandler.getPriceInfo();
                    //arrylistJson.add(arrylistJson.get(0));

                    if(arrylistJson.size() > 0){
                       // databaseHandler.deletePriceInfo();

                        //Toast.makeText(con," in receiver class to call calling service : ",Toast.LENGTH_LONG).show();
                        callWebservice();
                    }
                /*for (int i=0;i<arrylistJson.size();i++)
                {
                       callWebservice(arrylistJson.get(i));
                }*/

                }

            }
            catch (Exception e){
                /*Toast.makeText(con," catch : : "+e.toString(),Toast.LENGTH_LONG).show();
                File file = new File(Environment.getExternalStorageDirectory() + "/catch.txt");
                try {
                    OutputStreamWriter osw;
                    FileOutputStream f = new FileOutputStream(file);
                    f.write(e.toString().getBytes());
                    osw = new OutputStreamWriter(f);
                    osw.append("\n\n");

                    osw.append("\n\n");
                    osw.flush();
                    osw.close();
                    f.flush();
                    f.close();
                } catch (IOException ex) {
                    e.printStackTrace();
                }*/
            }

            //if(databaseHandler.getCursorCount() > 0){
              //  GlobalClass.arraylistTransportDetail=databaseHandler.getTransportDetail();
               // if(GlobalClass.arraylistTransportDetail.size() > 0){
                 //   for (int i=0;i<GlobalClass.arraylistTransportDetail.size();i++){
                    //    callWebservice(i);
                    //}
                //}

           // }

        }
        else {
            // no more network available
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
                    /*Random random = new Random();
                    int n = 100;
                    File file = new File(Environment.getExternalStorageDirectory() + "/Result : " + random.nextInt(n) + ".txt");
                                try {
                                    OutputStreamWriter osw;
                                    FileOutputStream f = new FileOutputStream(file);
                                    f.write(result.getBytes());
                                    osw = new OutputStreamWriter(f);
                                    osw.append("\n\n");
                                    osw.append(result);
                                    osw.append("\n\n");
                                    osw.flush();
                                    osw.close();
                                    f.flush();
                                    f.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }*/


                    //Toast.makeText(con,"REsult : "+result,Toast.LENGTH_LONG).show();
                    try
                    {
                        if(result.equalsIgnoreCase("error")){
                            /* new code*/
                            //databaseHandler.addPriceInfo(arrylistJson.get(0));

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
                            /* new code*/

                            /*if(arrylistJson.size() > 0){
                                for (int i =0;i<arrylistJson.size();i++){
                                    databaseHandler.addPriceInfo(arrylistJson.get(i));
                                }
                            }*/
                        }
                        else if(result.equalsIgnoreCase(""))
                        {
                            /*if(arrylistJson.size() > 0){
                                for (int i =0;i<arrylistJson.size();i++){
                                    databaseHandler.addPriceInfo(arrylistJson.get(i));
                                }
                            }*/
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
//                      success goes here
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
                                //databaseHandler.deletePriceInfo();
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
                /*if(arrylistJson.size() > 0){
                    for (int i=0;i<arrylistJson.size();i++){
                        databaseHandler.deletePriceInfo(arrylistJson.get(i).getId());
                    }

                }*/

                //Toast.makeText(con," calling service in receiver : "+arrylistJson.size(),Toast.LENGTH_LONG).show();
                String url = DataHelper.URL_PRICE_HELPER+"priceinsert";
                //String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.PriceInsert;
                MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")));
                multipartEntity.addPart("priceinsert", new StringBody(arrylistJson.get(0).getStrJson(), Charset.forName("UTF-8")));
                AsynchTaskWithAuthorizationHeaderPost2 asyncTaskPost = new AsynchTaskWithAuthorizationHeaderPost2(url, onAsyncResult, con , multipartEntity, true, language);
                asyncTaskPost.execute();
            }
            catch (Exception e) {
                //Toast.makeText(con," exception calling service : ",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
        else
        {

        }
    }
}
