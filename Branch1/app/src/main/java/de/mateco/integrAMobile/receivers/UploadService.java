package de.mateco.integrAMobile.receivers;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.asyncTask.AsyncTaskWithAuthorizationHeaderPost;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.GridImage;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.SiteInspectionModel;

public class UploadService extends IntentService
{
    private SharedPreferences preferences;
    private DataBaseHandler db;
    private ArrayList<GridImage> imageList = new ArrayList<GridImage>();
    private MatecoPriceApplication application;
    private Language language;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder  builder;
    private SiteInspectionModel model;
    private int uploadedImageNumber = 1;
    int bvoId;
    public UploadService() {
        super(UploadService.class.getName());
    }

    @Override
    protected void onHandleIntent(final Intent intent)
    {
        Log.e("enter","service");
        preferences = getSharedPreferences("SiteInspection", Context.MODE_PRIVATE);
        db = new DataBaseHandler(this);
        application = (MatecoPriceApplication)this.getApplication();
        language = application.getLanguage();
        bvoId = intent.getExtras().getInt("bvoId");
        //imageList = db.getPhotos(preferences.getInt(DataHelper.SiteInspectionId, 0));
        imageList = db.getPhotosByFlag(bvoId);
        //model = db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId, 0));
        model = db.getSiteInspection(bvoId);

        builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Image Upload");
        builder.setContentText("Image is uploaded successfully");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setGroup(model.getUploadId());
        builder.setGroupSummary(true);
        builder.setNumber(uploadedImageNumber);
        Intent resultIntent = new Intent();
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        uploadImage();

    }

    private void uploadImage()
    {
        if(imageList.size() > 0)
        {
            AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult()
            {
                @Override
                public void OnAsynResult(String result)
                {
                    Log.e("result at image upload", result);
                    if(result.equals("true"))
                    {
                        if(uploadedImageNumber > 1)
                        {
                            builder.setContentText(uploadedImageNumber + " Images uploaded successfully");
                        }
                        else {
                            builder.setContentText("Image uploaded successfully");
                        }

                        builder.setNumber(uploadedImageNumber++);
                        moveFile();
                        notificationManager.notify(model.getId(), builder.build());
                        if(imageList.size() > 0){
                            db.updatePhotoByFlag(imageList.get(0).getId());
                        }
                        imageList = db.getPhotosByFlag(bvoId);
                        uploadImage();
                    }
                    else
                    {
                        Random random = new Random();
                        int n = 100;
                        File file = new File(Environment.getExternalStorageDirectory() + "/BvoImageUploadError" + random.nextInt(n) + ".txt");
                        String bvoModelJson = new Gson().toJson(model);
                        bvoModelJson = bvoModelJson + "\n" + " Image upload errorr " + result;
                        try {
                            FileOutputStream f = new FileOutputStream(file);
                            f.write(bvoModelJson.getBytes());
                            f.flush();
                            f.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            //String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + "BVOInsertImage";
            String url = DataHelper.URL_BVO_HELPER + "bvoinsertimage";
            MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            try
            {
                multipartEntity.addPart("BVOId",new StringBody(model.getUploadId()));
                multipartEntity.addPart("ImageId",new StringBody(imageList.get(0).getId()+""));
                if(imageList.get(0).getName() == null)
                    multipartEntity.addPart("Notiz",new StringBody("", Charset.forName("UTF-8")));
                else
                    multipartEntity.addPart("Notiz",new StringBody(imageList.get(0).getName()+"", Charset.forName("UTF-8")));
                Bitmap bitmap = BitmapFactory.decodeFile(imageList.get(0).getPath());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos);
                byte[] data = bos.toByteArray();
                multipartEntity.addPart("ImageInsertStream", new ByteArrayBody(data, "image"));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            Log.e("url", url);
            AsyncTaskWithAuthorizationHeaderPost asyncTask = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult,this,multipartEntity ,false, language);
            asyncTask.execute();
        }
        else
        {
            db.updateSiteInspectionFlag(bvoId,4);
            preferences.edit().clear().commit();
            if(db.checkIfPhotoUploadRemaining() || db.checkIfSiteInspectionUploadRemaining())
            {
                Intent photoUploadIntent = new Intent();
                photoUploadIntent.setAction(DataHelper.bvoAgainStartBvoUpload);
                getApplicationContext().sendBroadcast(photoUploadIntent);
            }
        }
    }

    private void moveFile() {
        String root = Environment.getExternalStorageDirectory().toString();
        File dir = null;
        if(model.getSiteInspectionNewModel().getEinsatzstrasse() != null){
            if(model.getSiteInspectionNewModel().getEinsatzstrasse().contains("/"))
            {
                dir = new File(root + "/Bvo/" + model.getSiteInspectionNewModel().getEinsatzPLZ() + "_" + model.getSiteInspectionNewModel().getEinsatzort() + "_" +
                        model.getSiteInspectionNewModel().getEinsatzstrasse().replace("/","-") + "_" + model.getUploadId() + "/");
            }
            else
            {
                dir = new File(root + "/Bvo/" + model.getSiteInspectionNewModel().getEinsatzPLZ() + "_" + model.getSiteInspectionNewModel().getEinsatzort() + "_" +
                        model.getSiteInspectionNewModel().getEinsatzstrasse() + "_" + model.getUploadId() + "/");
            }
        }

        if(!dir.exists())
            dir.mkdirs();
        Random random = new Random();
        int n = 10000;
        String image = "Bvo " + random.nextInt(n) + ".jpg";
        File dest = new File(dir, image);
        File source = null;
        if(imageList != null && imageList.size() > 0){
            source = new File(imageList.get(0).getPath());
        }

        try {
            InputStream in = new FileInputStream(source);
            OutputStream out = new FileOutputStream(dest);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.flush();
            out.close();

            // delete the original file
            new File(imageList.get(0).getPath()).delete();
            db.updatePhotoByPath(dest.getAbsolutePath(),imageList.get(0).getId());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
