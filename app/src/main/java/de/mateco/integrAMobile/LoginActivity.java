package de.mateco.integrAMobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.GlobalClass;
import de.mateco.integrAMobile.Helper.LogApp;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.BaseActivity;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.ActivityTopicModel;
import de.mateco.integrAMobile.model.ActivityTypeModel;
import de.mateco.integrAMobile.model.BuheneartModel;
import de.mateco.integrAMobile.model.CountryModel;
import de.mateco.integrAMobile.model.CustomerBranchModel;
import de.mateco.integrAMobile.model.DecisionMakerModel;
import de.mateco.integrAMobile.model.DocumentLanguageModel;
import de.mateco.integrAMobile.model.EmployeeModel;
import de.mateco.integrAMobile.model.FeatureModel;
import de.mateco.integrAMobile.model.FunctionModel;
import de.mateco.integrAMobile.model.HintModel;
import de.mateco.integrAMobile.model.LadefahrzeugComboBoxItemModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LegalFormModel;
import de.mateco.integrAMobile.model.LoginPersonModel;
import de.mateco.integrAMobile.model.MainServiceCallModel;
import de.mateco.integrAMobile.model.PriceStaffelModel;
import de.mateco.integrAMobile.model.Pricing1BranchData;
import de.mateco.integrAMobile.model.Pricing1DeviceData;
import de.mateco.integrAMobile.model.Pricing1PriceRentalData;
import de.mateco.integrAMobile.model.PricingOfflineEquipmentData;
import de.mateco.integrAMobile.model.PricingOfflineStandardPriceData;
import de.mateco.integrAMobile.model.ProjectAreaModel;
import de.mateco.integrAMobile.model.ProjectArtModel;
import de.mateco.integrAMobile.model.ProjectPhaseModel;
import de.mateco.integrAMobile.model.ProjectStagesModel;
import de.mateco.integrAMobile.model.ProjectTradeModel;
import de.mateco.integrAMobile.model.ProjectTypeModel;
import de.mateco.integrAMobile.model.SalutationModel;
import de.mateco.integrAMobile.model.SiteInspectionAccessModel;
import de.mateco.integrAMobile.model.SiteInspectionBuildingProjectModel;
import de.mateco.integrAMobile.model.SiteInspectionDeviceTypeModel;

public class LoginActivity extends BaseActivity implements View.OnClickListener
{
    long startTime;
    long elapsedTime;

    // change in login activity for git on 8th jan.

    // chage in working copy and alos commit on master branch
    BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult;
    private ProgressDialog progressDialog;
    ProgressDialog prd;
    private DataBaseHandler db;
    private MatecoPriceApplication matecoPriceApplication;
    private Language language;
    private EditText textUserName, textPassword;
    private TextView labelLoginTitle;
    ImageView imgVisibility;
    private boolean isCallservice=true;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        GlobalClass.retainOrientation(LoginActivity.this);
        super.initializeActivity();
        matecoPriceApplication = (MatecoPriceApplication)getApplication();
        language = matecoPriceApplication.getLanguage();

        Window window= this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if(DataHelper.APP_NAME.equalsIgnoreCase("integrAMobile/MatecoSalesAppService.svc/json/")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(this.getResources().getColor(R.color.primary_dark));
            }
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                if(DataHelper.APP_NAME.equalsIgnoreCase("integrAMobileTest/MatecoSalesAppService.svc/json/")){
                    window.setStatusBarColor(this.getResources().getColor(R.color.primary_dark2));
                }
                else {
                    window.setStatusBarColor(this.getResources().getColor(R.color.primary_dark3));
                }
            }
        }

        textUserName = (EditText) findViewById(R.id.edtUserName);
        textUserName.setHint(language.getLabelEmail());
        textPassword = (EditText) findViewById(R.id.edtPassword);
        textPassword.setHint(language.getLabelPassword());
        textPassword.setInputType(129);

        labelLoginTitle = (TextView)findViewById(R.id.labelLoginTitle);
        labelLoginTitle.setText(language.getLabelLogin());

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        db = new DataBaseHandler(this);
        btnLogin.setText(language.getLabelLogin());
        btnLogin.setOnClickListener(this);
        Button buttonForgotPassword = (Button)findViewById(R.id.buttonForgotPassword);
        buttonForgotPassword.setText(language.getLabelForgotPassword());
        buttonForgotPassword.setOnClickListener(this);
        imgVisibility=(ImageView)findViewById(R.id.imgvisibility);
        imgVisibility.setOnClickListener(this);
    }

    @Override
    public void initializeComponents()
    {
        super.initializeComponents();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnLogin:
                isCallservice=true;
                String userName = textUserName.getText().toString().trim();
                String password = textPassword.getText().toString();
                textUserName.setError(null);
                textPassword.setError(null);
                if(userName.length() == 0)
                {
                    textUserName.setError(language.getLabelRequired());
                }
                else if(password.length() == 0)
                {
                    textPassword.setError(language.getLabelRequired());
                }
                else if(!DataHelper.isValidBlankMail(textUserName.getText().toString()))
                {
                    textUserName.setError(language.getMessageNotValidEmail());
                    textUserName.requestFocus();
                }
                else if(DataHelper.isNetworkAvailable(LoginActivity.this))
                {
                    prd = new ProgressDialog(LoginActivity.this);
                    prd.setTitle(language.getMessageWaitWhileLoading());
                    prd.setMessage(language.getMessageWaitWhileLoading());
                    prd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    prd.setIndeterminate(false);
                    prd.show();
                    prd.setProgress(0);
                    prd.setMax(100);
                    prd.setCancelable(false);
                    BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResultLogin = new BasicAsyncTaskGetRequest.OnAsyncResult()
                    {
                        @Override
                        public void OnAsynResult(final String result)
                        {
                            if(result.equals("error"))
                            {
                                showShortToast(language.getMessageError());
                                prd.dismiss();
                            }
                            else if(result.equals("[]"))
                            {
                                showShortToast("Fehler");
                                prd.dismiss();
                            }else if(result.equals(DataHelper.NetworkError)){
                                showShortToast(language.getMessageNetworkNotAvailable());
                                prd.dismiss();
                            }

                            else
                            {
                                try
                                {
                                    prd.setProgress(0);
                                    prd.setMax(100);
                                    prd.setProgress(5);
                                    final ArrayList<LoginPersonModel> listOfUser = new ArrayList<>();
                                    LoginPersonModel.extractFromJson(result, listOfUser);
                                    //prd.setMessage(language.getMessagePleaseWaitWhileDataBeingLoadedForFirstTime());
                                    /*onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                                    {
                                        @Override
                                        public void OnAsynResult(final String result)

                                        {
                                            Log.e("result 11111","main service resule variabe : "+result);
                                            //hideProgressDialog();
                                            if(result.equals("error"))
                                            {
                                                prd.dismiss();
                                                showShortToast(language.getMessageError());
                                            }
                                            else if(result.equals(DataHelper.NetworkError)){
                                                Log.e(" ###### "," network problem : "+result);
                                                prd.dismiss();
                                                //showLongToast("Network problem while service calling before");
                                                if(isCallservice) {
                                                   // showLongToast("service call start now");
                                                    isCallservice=false;
                                                    showProgressDialog();
                                                    Handler handler = new Handler();
                                                    handler.postDelayed(new Runnable() {
                                                        public void run() {
                                                            // Actions to do after 10 seconds
                                                            String url = null;
                                                            try {
                                                                url = DataHelper.URL_USER_HELPER +"salesservice/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                                                            } catch (UnsupportedEncodingException e) {e.printStackTrace();}
                                                            if(DataHelper.isNetworkAvailable(LoginActivity.this)) {
                                                                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, LoginActivity.this, false);
                                                                asyncTask.execute();
                                                            }else {
                                                                prd.dismiss();
                                                                showShortToast(language.getMessageNetworkNotAvailable());
                                                            }
                                                        }
                                                    }, DataHelper.NETWORK_CALL_DURATION);
                                                }
                                            }
                                            else
                                            {
                                                try
                                                {

                                                    new Thread(new Runnable()
                                                    {
                                                        @Override
                                                        public void run()
                                                        {
                                                            try
                                                            {
                                                                //if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                                                                   // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                                                                //} else setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                                                                prd.setProgress(0);
                                                                prd.setMax(100);
                                                                prd.setProgress(20);
                                                                //InputStream in = IOUtils.toInputStream(source, "UTF-8");
                                                                byte[] bytes = decompress2(result.getBytes());
                                                                String str = new String(bytes, "UTF-8");
                                                                MainServiceCallModel mainServiceCallModel = new Gson().fromJson(result, MainServiceCallModel.class);
                                                                db.deleteTableAtLogin();
                                                                ArrayList<PricingOfflineStandardPriceData> listOfOfflineStandardPrice = mainServiceCallModel.getListOfStandardPrice();
                                                                db.addPricingOfflineStandardPrice(listOfOfflineStandardPrice);

                                                                ArrayList<LadefahrzeugComboBoxItemModel> arraylistLadefahrzeug = mainServiceCallModel.getArraylsitLadefahrzeug();
                                                                db.addLadefahzeg(arraylistLadefahrzeug);

                                                                prd.setProgress(0);
                                                                prd.setMax(100);
                                                                //prd.setMessage();
                                                                prd.setProgress(25);
                                                                Log.e("list of standard price", listOfOfflineStandardPrice.size() + "");
                                                                ArrayList<Pricing1PriceRentalData> rentalData = mainServiceCallModel.getListOfPriceRental();
                                                                db.addPriceRental(rentalData);
                                                                prd.setProgress(0);
                                                                prd.setMax(100);
                                                                prd.setProgress(35);
                                                                ArrayList<PricingOfflineEquipmentData> listOfOfflineEquipment = mainServiceCallModel.getListOfEquipmentHeight();
                                                                db.addPricingOfflineEquipmentData(listOfOfflineEquipment);
                                                                ArrayList<Pricing1DeviceData> deviceGroups = mainServiceCallModel.getListOfDeviceGroup();
                                                                db.addDevice(deviceGroups);
                                                                prd.setProgress(0);
                                                                prd.setMax(100);
                                                                prd.setProgress(40);
                                                                ArrayList<Pricing1BranchData> branches = mainServiceCallModel.getListOfBranch();
                                                                db.addBranch(branches);
                                                                ArrayList<LegalFormModel> legalForms = mainServiceCallModel.getListOfLegalForm();
                                                                db.addLegalForms(legalForms);
                                                                prd.setProgress(0);
                                                                prd.setMax(100);
                                                                prd.setProgress(45);
                                                                ArrayList<CountryModel> countries = mainServiceCallModel.getListOfCountry();
                                                                db.addCountries(countries);
                                                                ArrayList<SalutationModel> salutations = mainServiceCallModel.getListOfSalutations();
                                                                db.addSalutation(salutations);
                                                                prd.setProgress(0);
                                                                prd.setMax(100);
                                                                prd.setProgress(50);
                                                                ArrayList<FunctionModel> functions = mainServiceCallModel.getListOfFunctions();
                                                                db.addFunction(functions);
                                                                ArrayList<FeatureModel> listOfFeatures = mainServiceCallModel.getListOfFeatures();
                                                                db.addFeatures(listOfFeatures);
                                                                prd.setProgress(55);
                                                                ArrayList<DocumentLanguageModel> languages = mainServiceCallModel.getListOfDocumentLanguage();
                                                                db.addDocumentLanguage(languages);
                                                                ArrayList<DecisionMakerModel> listOfDecisionMaker = mainServiceCallModel.getListOfDecisionMaker();
                                                                db.addDecisionMakers(listOfDecisionMaker);
                                                                prd.setProgress(60);
                                                                ArrayList<ActivityTypeModel> listOfActivityType = mainServiceCallModel.getListOfActivityType();
                                                                db.addActivityTypes(listOfActivityType);
                                                                prd.setProgress(65);
                                                                ArrayList<ActivityTopicModel> listOfTopic = mainServiceCallModel.getListOfActivityTopic();
                                                                db.addActivityTopics(listOfTopic);
                                                                ArrayList<EmployeeModel> listOfEmployee = mainServiceCallModel.getListOfEmployee();
                                                                db.addEmployees(listOfEmployee);
                                                                prd.setProgress(70);
                                                                ArrayList<SiteInspectionDeviceTypeModel> listOfDeviceTypes = mainServiceCallModel.getListOfDeviceType();
                                                                db.addSiteInspectionDeviceType(listOfDeviceTypes);
                                                                prd.setProgress(75);
                                                                ArrayList<SiteInspectionBuildingProjectModel> listOfBUildingProject = mainServiceCallModel.getListOfBuildingProject();
                                                                db.addBuildingProject(listOfBUildingProject);
                                                                ArrayList<SiteInspectionAccessModel> listOfAccess = mainServiceCallModel.getListOfAccess();
                                                                db.addAccess(listOfAccess);
                                                                ArrayList<PriceStaffelModel> listOfPriceStaffel = mainServiceCallModel.getListOfPriceStaffel();
                                                                db.addPriceStaffel(listOfPriceStaffel);
                                                                prd.setProgress(80);
                                                                ArrayList<CustomerBranchModel> listOfCustomerBranch = mainServiceCallModel.getListOfCustomerBranch();
                                                                if(listOfCustomerBranch != null)
                                                                    db.addCustomerBranch(listOfCustomerBranch);
                                                                ArrayList<ProjectArtModel> listOfProjectArt = mainServiceCallModel.getListOfProjectArt();
                                                                if(listOfProjectArt != null)
                                                                    db.addProjectArt(listOfProjectArt);
                                                                ArrayList<ProjectTypeModel> listOfProjectType = mainServiceCallModel.getListOfProjectType();
                                                                if(listOfProjectType != null)
                                                                    db.addProjectType(listOfProjectType);
                                                                prd.setProgress(85);
                                                                ArrayList<ProjectPhaseModel> listOfProjectPhase = mainServiceCallModel.getListOfProjectPhase();
                                                                if(listOfProjectPhase != null)
                                                                    db.addProjectPhase(listOfProjectPhase);
                                                                ArrayList<ProjectStagesModel> listOfProjectStage = mainServiceCallModel.getListOfProjectStage();
                                                                if(listOfProjectStage != null)
                                                                    db.addProjectStage(listOfProjectStage);
                                                                ArrayList<ProjectTradeModel> listOfProjectTrade = mainServiceCallModel.getListOfProjectTrade();
                                                                prd.setProgress(90);
                                                                if(listOfProjectTrade != null)
                                                                    db.addProjectTrade(listOfProjectTrade);
                                                                prd.setProgress(95);
                                                                ArrayList<ProjectAreaModel> listOfArea = mainServiceCallModel.getListOfArea();
                                                                if(listOfArea != null)
                                                                    db.addProjectArea(listOfArea);
                                                                ArrayList<BuheneartModel> listOfBuheneart = mainServiceCallModel.getListOfBuheneart();
                                                                if(listOfBuheneart != null)
                                                                    db.addBuheneart(listOfBuheneart);
                                                                prd.setProgress(100);
                                                                prd.dismiss();
                                                                String json = new Gson().toJson(listOfUser);
                                                                matecoPriceApplication.saveLoginUser(DataHelper.LoginPerson, json);
                                                                Intent intentHome = new Intent(LoginActivity.this, HomeActivity.class);
                                                                startActivity(intentHome);
                                                                finish();
                                                            }
                                                            catch (Exception ex)
                                                            {
                                                                //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                                                                ex.printStackTrace();
                                                                prd.dismiss();
                                                                LoginActivity.this.runOnUiThread(new Runnable()
                                                                {
                                                                    @Override
                                                                    public void run()
                                                                    {
                                                                        showShortToast(language.getMessageErrorAtParsing());
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    }).start();

                                                }
                                                catch (Exception ex)
                                                {
                                                    ex.printStackTrace();
                                                    prd.dismiss();
                                                    showShortToast(language.getMessageErrorAtParsing());
                                                }
                                            }
                                        }
                                    };

                                    try
                                    {
                                        //String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.MAIN_SERVICE + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                                        String url = DataHelper.URL_USER_HELPER +"salesservice/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                                        //String url = "http://mobile.mateco.de/UserServiceTest/api/userhelper/logindetails/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                                        Log.e("url", url);
                                        BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, LoginActivity.this, false);
                                        asyncTask.execute();
                                    }
                                    catch (IOException e)
                                    {
                                        e.printStackTrace();
                                    }*/

                                    String url = DataHelper.URL_USER_HELPER +"salesservice/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                                    // for volley
                                    startTime = System.currentTimeMillis();
                                    callMainServiceUsingVolley(url,listOfUser);
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                    showShortToast(language.getMessageErrorAtParsing());
                                    prd.dismiss();
                                }
                            }
                        }
                    };

                    try
                    {
                        //String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.CHECK_LOGIN + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                        String url = DataHelper.URL_USER_HELPER +"logindetails/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                        url = url + "/name=" + userName;
                        url = url + "/password=" + password;
                        BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResultLogin, LoginActivity.this, false);
                        asyncTask.execute();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    showShortToast(language.getMessageNetworkNotAvailable());
                }
                break;
            case R.id.buttonForgotPassword:
                Intent intentHome = new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(intentHome);
                break;
            case R.id.imgvisibility:
                if (textPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD)
                {
                    textPassword.setInputType(129);
                    imgVisibility.setImageResource(R.drawable.ic_visibility_off);
                }
                else
                {
                    textPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    imgVisibility.setImageResource(R.drawable.ic_visibility);
                }
                break;
        }
    }
    private void callMainServiceUsingVolley(final String url, final ArrayList<LoginPersonModel> listOfUser){
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        LogApp.showLog(" ###### "," response of volley : "+response);
                        try
                        {
                            new Thread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    try
                                    {

                                        prd.setProgress(0);
                                        prd.setMax(100);
                                        prd.setProgress(20);
                                        MainServiceCallModel mainServiceCallModel = new Gson().fromJson(response.toString(), MainServiceCallModel.class);
                                        db.deleteTableAtLogin();

                                        ArrayList<PricingOfflineStandardPriceData> listOfOfflineStandardPrice = mainServiceCallModel.getListOfStandardPrice();
                                        db.addPricingOfflineStandardPrice(listOfOfflineStandardPrice); // 1


                                        ArrayList<LadefahrzeugComboBoxItemModel> arraylistLadefahrzeug = mainServiceCallModel.getArraylsitLadefahrzeug();
                                        db.addLadefahzeg(arraylistLadefahrzeug); // 2

                                        prd.setProgress(0);
                                        prd.setMax(100);
                                        //prd.setMessage();
                                        prd.setProgress(25);
                                        ArrayList<Pricing1PriceRentalData> rentalData = mainServiceCallModel.getListOfPriceRental();
                                        db.addPriceRental(rentalData); // 3
                                        prd.setProgress(0);
                                        prd.setMax(100);
                                        prd.setProgress(35);
                                        ArrayList<PricingOfflineEquipmentData> listOfOfflineEquipment = mainServiceCallModel.getListOfEquipmentHeight();
                                        db.addPricingOfflineEquipmentData(listOfOfflineEquipment); // 4
                                        ArrayList<Pricing1DeviceData> deviceGroups = mainServiceCallModel.getListOfDeviceGroup();
                                        db.addDevice(deviceGroups); // 5
                                        prd.setProgress(0);
                                        prd.setMax(100);
                                        prd.setProgress(40);
                                        ArrayList<Pricing1BranchData> branches = mainServiceCallModel.getListOfBranch();
                                        db.addBranch(branches); // 6
                                        ArrayList<LegalFormModel> legalForms = mainServiceCallModel.getListOfLegalForm();
                                        db.addLegalForms(legalForms); // 7
                                        prd.setProgress(0);
                                        prd.setMax(100);
                                        prd.setProgress(45);
                                        ArrayList<CountryModel> countries = mainServiceCallModel.getListOfCountry();
                                        db.addCountries(countries); // 8
                                        ArrayList<SalutationModel> salutations = mainServiceCallModel.getListOfSalutations();
                                        db.addSalutation(salutations); // 9
                                        prd.setProgress(0);
                                        prd.setMax(100);
                                        prd.setProgress(50);
                                        ArrayList<FunctionModel> functions = mainServiceCallModel.getListOfFunctions();
                                        db.addFunction(functions); // 10
                                        ArrayList<FeatureModel> listOfFeatures = mainServiceCallModel.getListOfFeatures();
                                        db.addFeatures(listOfFeatures); // 11
                                        prd.setProgress(55);
                                        ArrayList<DocumentLanguageModel> languages = mainServiceCallModel.getListOfDocumentLanguage();
                                        db.addDocumentLanguage(languages); // 12
                                        ArrayList<DecisionMakerModel> listOfDecisionMaker = mainServiceCallModel.getListOfDecisionMaker();
                                        db.addDecisionMakers(listOfDecisionMaker); // 13
                                        prd.setProgress(60);
                                        ArrayList<ActivityTypeModel> listOfActivityType = mainServiceCallModel.getListOfActivityType();
                                        db.addActivityTypes(listOfActivityType); // 14
                                        prd.setProgress(65);
                                        ArrayList<ActivityTopicModel> listOfTopic = mainServiceCallModel.getListOfActivityTopic();
                                        db.addActivityTopics(listOfTopic); // 15
                                        ArrayList<EmployeeModel> listOfEmployee = mainServiceCallModel.getListOfEmployee();
                                        db.addEmployees(listOfEmployee); // 16
                                        prd.setProgress(70);
                                        ArrayList<SiteInspectionDeviceTypeModel> listOfDeviceTypes = mainServiceCallModel.getListOfDeviceType();
                                        db.addSiteInspectionDeviceType(listOfDeviceTypes);// 17
                                        prd.setProgress(75);
                                        ArrayList<SiteInspectionBuildingProjectModel> listOfBUildingProject = mainServiceCallModel.getListOfBuildingProject();
                                        db.addBuildingProject(listOfBUildingProject); // 18
                                        ArrayList<SiteInspectionAccessModel> listOfAccess = mainServiceCallModel.getListOfAccess();
                                        db.addAccess(listOfAccess); // 19

                                        ArrayList<PriceStaffelModel> listOfPriceStaffel = mainServiceCallModel.getListOfPriceStaffel();
                                        db.addPriceStaffel(listOfPriceStaffel); // 20
                                        prd.setProgress(80);
                                        ArrayList<CustomerBranchModel> listOfCustomerBranch = mainServiceCallModel.getListOfCustomerBranch();
                                        if(listOfCustomerBranch != null)
                                            db.addCustomerBranch(listOfCustomerBranch); // 21
                                        ArrayList<ProjectArtModel> listOfProjectArt = mainServiceCallModel.getListOfProjectArt();
                                        if(listOfProjectArt != null)
                                            db.addProjectArt(listOfProjectArt); // 22
                                        ArrayList<ProjectTypeModel> listOfProjectType = mainServiceCallModel.getListOfProjectType();
                                        if(listOfProjectType != null)
                                            db.addProjectType(listOfProjectType); // 23
                                        prd.setProgress(85);
                                        ArrayList<ProjectPhaseModel> listOfProjectPhase = mainServiceCallModel.getListOfProjectPhase();
                                        if(listOfProjectPhase != null)
                                            db.addProjectPhase(listOfProjectPhase); // 24
                                        ArrayList<ProjectStagesModel> listOfProjectStage = mainServiceCallModel.getListOfProjectStage();
                                        if(listOfProjectStage != null)
                                            db.addProjectStage(listOfProjectStage); // 25
                                        ArrayList<ProjectTradeModel> listOfProjectTrade = mainServiceCallModel.getListOfProjectTrade();
                                        prd.setProgress(90);
                                        if(listOfProjectTrade != null)
                                            db.addProjectTrade(listOfProjectTrade); // 26
                                        prd.setProgress(95);
                                        ArrayList<ProjectAreaModel> listOfArea = mainServiceCallModel.getListOfArea();
                                        if(listOfArea != null)
                                            db.addProjectArea(listOfArea); // 27
                                        ArrayList<BuheneartModel> listOfBuheneart = mainServiceCallModel.getListOfBuheneart();
                                        if(listOfBuheneart != null)
                                            db.addBuheneart(listOfBuheneart); // 28
                                        prd.setProgress(100);
                                        prd.dismiss();
                                        String json = new Gson().toJson(listOfUser);
                                        matecoPriceApplication.saveLoginUser(DataHelper.LoginPerson, json);

                                        elapsedTime = System.currentTimeMillis() - startTime;
                                        Log.e(" gson "," arraylis size : "+listOfOfflineStandardPrice.size() + "time : "+elapsedTime);
                                        Intent intentHome = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intentHome);
                                        finish();
                                    }
                                    catch (Exception ex)
                                    {
                                        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                                        ex.printStackTrace();
                                        prd.dismiss();
                                        LoginActivity.this.runOnUiThread(new Runnable()
                                        {
                                            @Override
                                            public void run()
                                            {
                                                showShortToast(language.getMessageErrorAtParsing());
                                            }
                                        });
                                    }
                                }
                            }).start();

                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                            prd.dismiss();
                            showShortToast(language.getMessageErrorAtParsing());
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                prd.dismiss();
                //showLongToast("Network problem while service calling before");
                if(isCallservice) {
                    // showLongToast("service call start now");
                    isCallservice=false;
                    showProgressDialog();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            // Actions to do after 10 seconds
                            String urlNew = null;
                            try {
                                urlNew = DataHelper.URL_USER_HELPER +"salesservice/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            callMainServiceUsingVolley(urlNew,listOfUser);
                        }
                    }, DataHelper.NETWORK_CALL_DURATION);
                }
            }
        });

        Volley.newRequestQueue(LoginActivity.this).add(req);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(prd != null){
            prd.dismiss();
        }

    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
    }
    public void showProgressDialog(){
        prd = new ProgressDialog(LoginActivity.this);
        prd.setTitle(language.getMessageWaitWhileLoading());
        prd.setMessage(language.getMessageWaitWhileLoading());
        prd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        prd.setIndeterminate(false);
        prd.show();
        prd.setProgress(5);
        prd.setMax(100);
        prd.setCancelable(false);
    }
    public void hideProgressDialog(){
        if(prd != null && prd.isShowing()){
            prd.dismiss();
        }
    }
    public static byte[] decompress2(byte[] data) throws IOException, DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        //LOG.debug("Original: " + data.length);
        //LOG.debug("Compressed: " + output.length);
        return output;
    }
    public  String decompress(byte[] compressed) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
        GZIPInputStream gis = new GZIPInputStream(bis);
        BufferedReader br = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        gis.close();
        bis.close();
        return sb.toString();
    }
}
