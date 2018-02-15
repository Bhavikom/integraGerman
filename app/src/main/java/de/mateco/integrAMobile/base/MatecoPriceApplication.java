package de.mateco.integrAMobile.base;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.Application;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;

import android.os.Looper;
import android.os.Process;
import android.preference.PreferenceManager;

import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.bluelinelabs.logansquare.LoganSquare;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.facebook.stetho.Stetho;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import de.mateco.integrAMobile.BuildConfig;
import de.mateco.integrAMobile.Helper.Constants;
import de.mateco.integrAMobile.model_logonsquare.CustomerActivityEmployeeListItem;
import de.mateco.integrAMobile.model_logonsquare.CustomerActivityTopicListItem;
import de.mateco.integrAMobile.model_logonsquare.CustomerActivityTypeListItem;
import io.fabric.sdk.android.Fabric;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.ItemXMLHandler;

import de.mateco.integrAMobile.model.ContactPersonModel;
import de.mateco.integrAMobile.model.CustomerActivityModel;
import de.mateco.integrAMobile.model.CustomerCompletedOrderModel;
import de.mateco.integrAMobile.model.CustomerLostSaleDataModel;
import de.mateco.integrAMobile.model.CustomerModel;
import de.mateco.integrAMobile.model.CustomerOfferModel;
import de.mateco.integrAMobile.model.CustomerOpenOfferModel;
import de.mateco.integrAMobile.model.CustomerOpenOrderModel;
import de.mateco.integrAMobile.model.CustomerProjectModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LoginPersonModel;
import de.mateco.integrAMobile.model.PricingCustomerOrderBasicInfo;
import de.mateco.integrAMobile.model.ProjectDetailActivityModel;
import de.mateco.integrAMobile.model.ProjectDetailGenerallyModel;
import de.mateco.integrAMobile.model.ProjectDetailTradeModel;

public class MatecoPriceApplication extends Application
{
    private static MatecoPriceApplication instance;
    private static final String LOG_TAG = "CrashCatch";
    private Language language;
    private SharedPreferences prefs, settingsPref;
    private Thread.UncaughtExceptionHandler defaultUEH;
    Context con;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        con = this;
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());

        CrashlyticsCore core = new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build();
        Fabric.with(this, new Crashlytics.Builder().core(core).build()); // comment it when signed apk

        //Fabric.with(this, new Crashlytics()); // do uncomment when make signed apk

        MultiDex.install(this);
        //defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        //Thread.setDefaultUncaughtExceptionHandler(handler);
        language = languageLoad();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        settingsPref = getSharedPreferences(Constants.SettingPref, Context.MODE_PRIVATE);
        //prefs.edit().clear().commit();
        //getSharedPreferences("SiteInspection",MODE_PRIVATE).edit().clear().commit();
        //deleteDatabase("MatecoSales.db");

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCacheSize(1500000) // 1.5 Mb

                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .enableLogging() // Not necessary in common
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
        //startCatcher();
    }
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    private Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
               public void uncaughtException(Thread thread, Throwable ex) {
                   final Writer result = new StringWriter();
                   final PrintWriter printWriter = new PrintWriter(result);
                   ex.printStackTrace(printWriter);
                   String error = result.toString();
                   printWriter.close();
                   Intent emailIntent = new Intent(Intent.ACTION_SEND);
                   emailIntent.setType("text/html");
                   emailIntent.putExtra(Intent.EXTRA_SUBJECT, language.getMessageError());
                   emailIntent.putExtra(Intent.EXTRA_TEXT,error);
                   emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{"integrAMobile@mateco.de"});
                   Intent emailChooser = Intent.createChooser(emailIntent,language.getMessageErrorOccured());
                   emailChooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   try {
                       startActivity(emailChooser);
                   } catch (ActivityNotFoundException e) {
                       // If there is nothing that can send a text/html MIME type
                       e.printStackTrace();
                   }
                   //defaultUEH.uncaughtException(thread, ex);
                  }
         };


    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void clearPreferences()
    {
        prefs.edit().clear();
    }

    public void saveLoginUser(String variable, String data)
    {
        prefs.edit().putString(variable, data).commit();
        prefs.edit().putBoolean(DataHelper.isPersonLogin, true).commit();
    }

    public void logoutUser(boolean data)
    {
        prefs.edit().putBoolean(DataHelper.isPersonLogin, data).commit();
    }

    public void saveData(String variable, String data)
    {
        prefs.edit().putString(variable, data).commit();
    }

    public void saveLoadedCustomer(String variable, String data)
    {
        prefs.edit().putString(variable, data).commit();
        prefs.edit().putBoolean(DataHelper.isCustomerLoaded, true).commit();
        //isCustomerLoaded = true;
    }

    public CustomerActivityEmployeeListItem getAgendaSelectedEmployee(String variable, String data)
    {
        String json = prefs.getString(variable, data);
        CustomerActivityEmployeeListItem employee = new Gson().fromJson(json, CustomerActivityEmployeeListItem.class);
        return employee;
    }

    public String getAgendaSelectedDate(String variable, String data)
    {
        return prefs.getString(variable, data);
    }

    public void clearAgendaSeletion()
    {
        prefs.edit().remove(DataHelper.AgendaEmployeeSelected).commit();
    }

//    public void saveLoadedCustomerContactPerson(String variable, String data)
//    {
//        prefs.edit().putString(variable, data).commit();
//    }
//
//    public void saveLoadedCustomerActivity(String variable, String data)
//    {
//        prefs.edit().putString(variable, data).commit();
//    }
//
//    public void saveLoadedCustomerProjects(String variable, String data)
//    {
//        prefs.edit().putString(variable, data).commit();
//    }
//
//    public void saveLoadedCustomerOffers(String variable, String data)
//    {
//        prefs.edit().putString(variable, data).commit();
//    }
//
//    public void saveLoadedCustomerOpenOrders(String variable, String data)
//    {
//        prefs.edit().putString(variable, data).commit();
//    }
//
//    public void saveLoadedCustomerCompletedOrders(String variable, String data)
//    {
//        prefs.edit().putString(variable, data).commit();
//    }
//
//    public void saveLoadedCustomerOpenSpecials(String variable, String data)
//    {
//        prefs.edit().putString(variable, data).commit();
//    }
//
//    public void saveLoadedCustomerLostSale(String variable, String data)
//    {
//        prefs.edit().putString(variable, data).commit();
//    }

    public boolean isUserLogin(String variable, boolean defaultValue)
    {
        return prefs.getBoolean(variable, defaultValue);
    }

    public PricingCustomerOrderBasicInfo getPricingCustomerOrderGeneralInfo(String variable, String defaultValue)
    {
        String data = prefs.getString(variable, defaultValue);
        PricingCustomerOrderBasicInfo model = new Gson().fromJson(data, PricingCustomerOrderBasicInfo.class);
        return model;
    }

    public ProjectDetailGenerallyModel getProjectDetailGenerallyModel(String variable, String defaultValue)
    {
        String data = prefs.getString(variable, defaultValue);
        ProjectDetailGenerallyModel model = new Gson().fromJson(data, ProjectDetailGenerallyModel.class);
        return model;
    }

    public ArrayList<LoginPersonModel> getLoginUser(String variable, String defaultValue)
    {
        String data = prefs.getString(variable, defaultValue);
        ArrayList<LoginPersonModel> listOfUser = new ArrayList<>();
        LoginPersonModel.extractFromJson(data, listOfUser);
        return listOfUser;
    }

    public CustomerModel getLoadedCustomer(String variable, String defaultValue)
    {
        String data = prefs.getString(variable, defaultValue);
        CustomerModel customer = new CustomerModel();
        customer = new Gson().fromJson(data, CustomerModel.class);
        return customer;
    }

    public ArrayList<ContactPersonModel> getLoadedCustomerContactPersons(String variable, String defaultValue)
    {
        String data = prefs.getString(variable, defaultValue);
        ArrayList<ContactPersonModel> listOfContactPerson = new ArrayList<>();
        if(!TextUtils.isEmpty(data) && !data.equalsIgnoreCase("null")) {
            ContactPersonModel.extractFromJson(data, listOfContactPerson);
        }else {
            return null;
        }
        return listOfContactPerson;
    }

    public ArrayList<CustomerActivityModel> getLoadedCustomerActivities(String variable, String defaultValue)
    {
        String data = prefs.getString(variable, defaultValue);
        ArrayList<CustomerActivityModel> listOfCustomerActivity = new ArrayList<>();
        if(!TextUtils.isEmpty(data) && !data.equalsIgnoreCase("null")) {
            CustomerActivityModel.extractFromJson(data, listOfCustomerActivity);
        }else {
            return null;
        }
        return listOfCustomerActivity;
    }

    public ArrayList<CustomerProjectModel> getLoadedCustomerProjects(String variable, String defaultValue)
    {
        String data = prefs.getString(variable, defaultValue);
        ArrayList<CustomerProjectModel> listOfProjects = new ArrayList<>();
        if(!TextUtils.isEmpty(data) && !data.equalsIgnoreCase("null")) {
            CustomerProjectModel.extractFromJson(data, listOfProjects);
        }else {
            return null;
        }
        return listOfProjects;
    }
    public List<CustomerActivityEmployeeListItem> getCustomerActivityEmployeelist(String variable, String defaultValue)
    {
        String data = prefs.getString(variable, defaultValue);
        List<CustomerActivityEmployeeListItem> listOfEmployee = null;

        try {
            /*CustomerActivityEmployeeListItem employeeListItem = LoganSquare.parse(data,CustomerActivityEmployeeListItem.class);
            listOfEmployee.addAll(employeeListItem.get);*/
            listOfEmployee = LoganSquare.parseList(data,CustomerActivityEmployeeListItem.class);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfEmployee;
    }
    public List<CustomerActivityTypeListItem> getCustomerActivityTypeList(String variable, String defaultValue)
    {
        String data = prefs.getString(variable, defaultValue);
        List<CustomerActivityTypeListItem> listOfTypelist = null;

        try {
            /*CustomerActivityEmployeeListItem employeeListItem = LoganSquare.parse(data,CustomerActivityEmployeeListItem.class);
            listOfEmployee.addAll(employeeListItem.get);*/
            listOfTypelist = LoganSquare.parseList(data,CustomerActivityTypeListItem.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfTypelist;
    }
    public List<CustomerActivityTopicListItem> getCustomerActivityTopicList(String variable, String defaultValue)
    {
        String data = prefs.getString(variable, defaultValue);
        List<CustomerActivityTopicListItem> listOfTopic = null;

        try {
            /*CustomerActivityEmployeeListItem employeeListItem = LoganSquare.parse(data,CustomerActivityEmployeeListItem.class);
            listOfEmployee.addAll(employeeListItem.get);*/
            listOfTopic = LoganSquare.parseList(data,CustomerActivityTopicListItem.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfTopic;
    }

    public ArrayList<CustomerOfferModel> getLoadedCustomerOffers(String variable, String defaultValue)
    {
        String data = prefs.getString(variable, defaultValue);
        ArrayList<CustomerOfferModel> listOfContactPerson = new ArrayList<>();
        if(!TextUtils.isEmpty(data) && !data.equalsIgnoreCase("null")) {
            CustomerOfferModel.extractFromJson(data, listOfContactPerson);
        }else {
            return null;
        }
        return listOfContactPerson;
    }

    public ArrayList<CustomerOpenOrderModel> getLoadedCustomerOpenOrders(String variable, String defaultValue)
    {
        String data = prefs.getString(variable, defaultValue);
        ArrayList<CustomerOpenOrderModel> listOfCustomerOpenOffer = new ArrayList<>();
        CustomerOpenOrderModel.extractFromJson(data, listOfCustomerOpenOffer);
        return listOfCustomerOpenOffer;
    }

    public ArrayList<CustomerCompletedOrderModel> getLoadedCustomerCompletedOrders(String variable, String defaultValue)
    {
        String data = prefs.getString(variable, defaultValue);
        ArrayList<CustomerCompletedOrderModel> listOfCustomerCompletedOrder = new ArrayList<>();
        CustomerCompletedOrderModel.extractFromJson(data, listOfCustomerCompletedOrder);
        return listOfCustomerCompletedOrder;
    }

    public ArrayList<CustomerOpenOfferModel> getLoadedCustomerOpenSpecials(String variable, String defaultValue)
    {
        String data = prefs.getString(variable, defaultValue);
        ArrayList<CustomerOpenOfferModel> listOfOpenSpecial = new ArrayList<>();
        CustomerOpenOfferModel.extractFromJson(data, listOfOpenSpecial);
        return listOfOpenSpecial;
    }

    public ArrayList<CustomerLostSaleDataModel> getLoadedCustomerLostSale(String variable, String defaultValue)
    {
        String data = prefs.getString(variable, defaultValue);
        ArrayList<CustomerLostSaleDataModel> listOfCustomerLostSale = new ArrayList<>();
        CustomerLostSaleDataModel.extractFromJson(data, listOfCustomerLostSale);
        return listOfCustomerLostSale;
    }

    public ArrayList<ProjectDetailActivityModel> getLoadedProjectActivities(String variable, String defaultValue)
    {
        String data = prefs.getString(variable, defaultValue);
        ArrayList<ProjectDetailActivityModel> listOfProjectDetailActivityModel = new ArrayList<>();
        ProjectDetailActivityModel.extractFromJson(data, listOfProjectDetailActivityModel);
        return listOfProjectDetailActivityModel;
    }

    public ArrayList<ProjectDetailTradeModel> getLoadedProjectTrades(String variable, String defaultValue)
    {
        String data = prefs.getString(variable, defaultValue);
        ArrayList<ProjectDetailTradeModel> listOfProjectDetailTrade = new ArrayList<>();
        ProjectDetailTradeModel.extractFromJson(data, listOfProjectDetailTrade);
        return listOfProjectDetailTrade;
    }

    public boolean isCustomerLoaded(String variable, boolean defaultValue)
    {
        return prefs.getBoolean(variable, defaultValue);
    }

    public String getDate()
    {
        return prefs.getString(DataHelper.AgendaDate, "");
    }

    public String getData(String variable,String defaultValue)
    {
        return prefs.getString(variable, defaultValue);
    }

//    public void clearData()
//    {
//        prefs.edit().remove(DataHelper.LoadedCustomerActivity);
//        prefs.edit().remove(DataHelper.isCustomerLoaded);
//        prefs.edit().remove(DataHelper.LoadedCustomerContactPerson);
//        prefs.edit().remove(DataHelper.LoadedCustomer);
//        prefs.edit().remove(DataHelper.LoadedCustomerProject);
//        prefs.edit().remove(DataHelper.LoadedCustomerOffer);
//        prefs.edit().remove(DataHelper.LoadedCustomerLostSale);
//        prefs.edit().remove(DataHelper.LoadedCustomerOpenSpecials);
//        prefs.edit().remove(DataHelper.LoadedCustomerOpenOrders);
//        prefs.edit().remove(DataHelper.LoadedCustomerCompletedOrders);
//    }

    public void clearPricingData()
    {
        String jsonString = new Gson().toJson(new PricingCustomerOrderBasicInfo());
        prefs.edit().putString(DataHelper.PricingCustomerBasicOrderInfo, jsonString).commit();
    }

    public void saveSettingData(String keyName, String keyValue)
    {
        settingsPref.edit().putString(keyName, keyValue).commit();
    }

    public String getSettingData(String keyName, String defaultValue)
    {
        return settingsPref.getString(keyName, defaultValue);
    }

    public void saveSettingData(String keyName, int keyValue)
    {
        settingsPref.edit().putInt(keyName, keyValue).commit();
    }

    public int getSettingData(String keyName, int defaultValue)
    {
        return settingsPref.getInt(keyName, defaultValue);
    }

    public void saveHintPagesize(String keyName, int keyValue)
    {
        settingsPref.edit().putInt(keyName, keyValue).commit();
    }

    public int getHintPagesize(String keyName, int defaultValue)
    {
        return settingsPref.getInt(keyName, defaultValue);
    }


    public void hideKeyboard(Activity activity)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public Language languageLoad()
    {
        ArrayList<Language> languageList;
        Language language = new Language();
        SharedPreferences preferences = getSharedPreferences("Language", Context.MODE_PRIVATE);
        try
        {
            // language parsing
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            ItemXMLHandler myXmlHandler = new ItemXMLHandler();
            xr.setContentHandler(myXmlHandler);
            xr.parse(new InputSource(getAssets().open("LanguageXml.txt")));
            String selectedLanaguage="";
            if(DataHelper.APP_NAME.equalsIgnoreCase("integrAMobilePl/MatecoSalesAppService.svc/json/") ||
                    DataHelper.APP_NAME.equalsIgnoreCase("integrAMobilePlTest/MatecoSalesAppService.svc/json/"))
            {
                //SharedPreferences.Editor editor = preferences.edit();
               // editor.putString("language","pl");
                //editor.commit();
                selectedLanaguage="pl";
                //selectedLanaguage = preferences.getString("language", "de");
            }
            else {
                selectedLanaguage = preferences.getString("language", "de");
            }
            //selectedLanaguage = preferences.getString("language", "de");
            languageList = myXmlHandler.getItemsList();
            for(int i = 0; i < languageList.size(); i++)
            {
                if(selectedLanaguage.equals(languageList.get(i).getLangCode()))
                {
                    language = languageList.get(i);
                    setLanguage(language);
                    return language;
                }
            }
        }
        catch (SAXException ex)
        {
            ex.printStackTrace();
        }
        catch (ParserConfigurationException ex)
        {
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return language;
    }
    public static MatecoPriceApplication getInstance(){
        return instance;
    }
    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if(appDir.exists()){
            String[] children = appDir.list();
            for(String s : children){
                if(!s.equals("lib")){
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "File /data/data/APP_PACKAGE/" + s +" DELETED ");
                }
            }
        }
    }
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
    /**
     * This handler catches exceptions in the background threads and propagates them to the UI thread
     */
    static class UncaughtHandler implements Thread.UncaughtExceptionHandler
    {

        private final Handler mHandler;

        UncaughtHandler(Handler handler) {
            mHandler = handler;
        }

        public void uncaughtException(Thread thread, final Throwable e) {
            final int tid = Process.myTid();
            final String threadName = thread.getName();
            mHandler.post(new Runnable() {
                public void run() {
                    throw new BackgroundException(e, tid, threadName);
                }
            });
        }
    }

    /**
     * Wrapper class for exceptions caught in the background
     */
    static class BackgroundException extends RuntimeException
    {

        final int tid;
        final String threadName;

        /**
         * @param e original exception
         * @param tid id of the thread where exception occurred
         * @param threadName name of the thread where exception occurred
         */
        BackgroundException(Throwable e, int tid, String threadName) {
            super(e);
            this.tid = tid;
            this.threadName = threadName;
        }
    }
    public void sendErrorMail() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(con);
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                builder.setTitle("Sorry...!");
                builder.create();
                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                System.exit(0);
                            }
                        });
                builder.setPositiveButton("Report",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                System.exit(0);
                            }
                        });
                builder.setMessage("Oops,Your application has crashed");
                builder.show();
                Looper.loop();
            }
        }.start();
    }
    public void showAlertDialog(Context context) {
        /** define onClickListener for dialog */
        DialogInterface.OnClickListener listener
                = new   DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do some stuff eg: context.onCreate(super)
            }
        };

        /** create builder for dialog */
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setMessage("Messag...")
                .setTitle("Title")
                .setPositiveButton("OK", listener);
        /** create dialog & set builder on it */
        Dialog dialog = builder.create();
        /** this required special permission but u can use aplication context */
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        /** show dialog */
        dialog.show();
    }

}
