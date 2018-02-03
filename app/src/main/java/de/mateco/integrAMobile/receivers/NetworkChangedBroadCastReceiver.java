package de.mateco.integrAMobile.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.google.gson.Gson;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.asyncTask.AsyncTaskWithAuthorizationHeaderPost;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.ResponseFormat;
import de.mateco.integrAMobile.model.SiteInspectionModel;
import de.mateco.integrAMobile.model.SiteInspectionOperationalEnvironmentModel;

public class NetworkChangedBroadCastReceiver extends BroadcastReceiver {
    //private ArrayList<SiteInspectionModel> listOfSiteInspection = new ArrayList<>();
    private DataBaseHandler db;
    //private SharedPreferences preferences;
    private Language language;
    private MatecoPriceApplication application;

    @Override
    public void onReceive(final Context context, Intent intent)
    {
        db = new DataBaseHandler(context);
        //preferences = context.getSharedPreferences("SiteInspection",Context.MODE_PRIVATE);
        application = ((MatecoPriceApplication)context.getApplicationContext());
        language = application.getLanguage();
        //listOfSiteInspection = db.getSiteInspectionListToUpload();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


        if ((mWifi != null && mWifi.isConnected()) || (mobile!=null && mobile.isConnected()))
        {
            if(db.checkIfSiteInspectionUploadRemaining())
            {
                final SiteInspectionModel model = db.getSiteInspectionListToUploadSingle();
                SiteInspectionModel siteInspectionModel = db.getSiteInspection(model.getId());
                SiteInspectionOperationalEnvironmentModel siteInspectionOperationalEnvironmentModel = siteInspectionModel.getSiteInspectionOperationalEnvironmentModel();
                String json = new Gson().toJson(siteInspectionModel);
                String url = null;
                //String deviceList = new Gson().toJson(db.getDeviceByID(preferences.getInt(DataHelper.SiteInspectionId, 0)));
                String deviceList = new Gson().toJson(db.getDeviceByID(model.getId()));
                String practicabilityList = new Gson().toJson(siteInspectionOperationalEnvironmentModel.getListOfPracticability());
                AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        if(result.equalsIgnoreCase("error")){
                        }
                        else {
                            //if(result != null && !result.equals("error") && result.length()<=38 && !result.trim().equals("false"))
                            ResponseFormat responseFormat = new Gson().fromJson(result, ResponseFormat.class);
                            if(responseFormat.getResponseCode() == 10)
                            {
                                //db.updateSiteInspectionUpload(preferences.getInt(DataHelper.SiteInspectionId, 0), result.substring(1, result.length() - 1), 1);
                                //db.updateSiteInspectionUpload(model.getId(), result.substring(1, result.length() - 1), 3);
                                db.updateSiteInspectionUpload(model.getId(), responseFormat.getBvoID(), 3);
                                Intent photoUploadIntent = new Intent();
                                photoUploadIntent.putExtra("bvoId", model.getId());
                                photoUploadIntent.setAction(DataHelper.bvoInsertPhotoStart);
                                context.sendBroadcast(photoUploadIntent);
                            }
                            else {
                                db.updateSiteInspectionUpload(model.getId(), "", 2);
                            }
                        }


                    }
                };
                //url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.SiteInspectionInsert;
                url = DataHelper.URL_BVO_HELPER + "bvoinsert";
                MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                try
                {
                    Charset chars = Charset.forName("UTF-8");
                    multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8"), chars));
                    multipartEntity.addPart("BVOInsertList", new StringBody(json, chars));
                    multipartEntity.addPart("GeraetListDetail",new StringBody(deviceList, chars));
                    multipartEntity.addPart("numberOfImages",new StringBody(db.getPhotos(model.getId()).size()+""));
                    multipartEntity.addPart("BefahrbarkeitList",new StringBody(practicabilityList, chars));
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
                AsyncTaskWithAuthorizationHeaderPost asyncTask = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, context, multipartEntity, false, language);
                asyncTask.execute();
            }
            else if(db.checkIfPhotoUploadRemaining())
            {
                SiteInspectionModel model = db.getSiteInspectionPhotoUploadModelSingle();
                Intent photoUploadIntent = new Intent();
                photoUploadIntent.putExtra("bvoId", model.getId());
                photoUploadIntent.setAction(DataHelper.bvoInsertPhotoStart);
                context.sendBroadcast(photoUploadIntent);
            }
        }
    }
}