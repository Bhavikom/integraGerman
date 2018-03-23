package de.mateco.integrAMobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.GlobalClass;
import de.mateco.integrAMobile.Helper.LogApp;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.BaseActivity;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model_logonsquare.BVOBauvorhabenComboListItem;
import de.mateco.integrAMobile.model_logonsquare.BVODeviceTypeListItem;
import de.mateco.integrAMobile.model_logonsquare.BVOZugangComboListItem;
import de.mateco.integrAMobile.model_logonsquare.BrancheListItem;
import de.mateco.integrAMobile.model_logonsquare.CustomerActivityEmployeeListItem;
import de.mateco.integrAMobile.model_logonsquare.CustomerActivityTopicListItem;
import de.mateco.integrAMobile.model_logonsquare.CustomerActivityTypeListItem;
import de.mateco.integrAMobile.model_logonsquare.CustomerContactPersonDecisionMakersListItem;
import de.mateco.integrAMobile.model_logonsquare.CustomerContactPersonDocumentlanguageListItem;
import de.mateco.integrAMobile.model_logonsquare.CustomerContactPersonFeatureListItem;
import de.mateco.integrAMobile.model_logonsquare.CustomerContactPersonFunctionComboListItem;
import de.mateco.integrAMobile.model_logonsquare.CustomerContactPersonSalutationComboListItem;
import de.mateco.integrAMobile.model_logonsquare.CustomerLandListItem;
import de.mateco.integrAMobile.model_logonsquare.CustomerRechtsFormComboListItem;
import de.mateco.integrAMobile.model_logonsquare.ListOfBuheneartComboBoxItemItem;
import de.mateco.integrAMobile.model_logonsquare.ListOfLadefahrzeugComboBoxItemItem;
import de.mateco.integrAMobile.model_logonsquare.PriceBranchListItem;
import de.mateco.integrAMobile.model_logonsquare.PriceDeviceGroupListItem;
import de.mateco.integrAMobile.model_logonsquare.PriceEquipmentHeightListItem;
import de.mateco.integrAMobile.model_logonsquare.PriceRentalListItem;
import de.mateco.integrAMobile.model_logonsquare.PriceStaffelListItem;
import de.mateco.integrAMobile.model_logonsquare.PriceStandardListItem;
import de.mateco.integrAMobile.model_logonsquare.ProjektBUhnenAubenInnenComboListItem;
import de.mateco.integrAMobile.model_logonsquare.ProjektGebietComboListItem;
import de.mateco.integrAMobile.model_logonsquare.ProjektGewerkComboListItem;
import de.mateco.integrAMobile.model_logonsquare.ProjektartComboListItem;
import de.mateco.integrAMobile.model_logonsquare.ProjektphaseComboListItem;
import de.mateco.integrAMobile.model_logonsquare.ProjekttypComboListItem;
import de.mateco.integrAMobile.model_logonsquare.ResponseMain;

public class LoginActivity extends BaseActivity implements View.OnClickListener
{
    long startTimeSeviceCalling;
    long startTimePermenent;
    long elapsedTimeServiceCalling;
    long elapsedTimePermenent;

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

                    // using volley to call service
                    String urlLoginDetail = null;
                    try {
                        urlLoginDetail = DataHelper.URL_USER_HELPER +"logindetails/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                        urlLoginDetail = urlLoginDetail + "/name=" + userName;
                        urlLoginDetail = urlLoginDetail + "/password=" + password;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    // for volley
                    callMainServiceUsingVolley(urlLoginDetail,userName,password);
                }
                else
                {
                    showShortToast(language.getMessageNetworkNotAvailable());
                }
                break;
            case R.id.buttonForgotPassword:
                Intent intentHome = new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(intentHome);
                //finish();
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
    private void callMainServiceUsingVolley(final String url, final String userName, final String password){
        startTimeSeviceCalling = System.currentTimeMillis();
        startTimePermenent = System.currentTimeMillis();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        LogApp.showLog(" ###### "," response of volley : "+response);
                        elapsedTimeServiceCalling = System.currentTimeMillis() - startTimeSeviceCalling;
                        LogApp.showLog(" serviceCAlling time  "," time taken by service calling and parsing : "+ elapsedTimeServiceCalling);
                        try
                        {
                            new Thread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    /*try
                                    {*/

                                        //parseUsingGson(response.toString());
                                        parseUsingLogonSQuare(response.toString());
                                    }
                                    /*catch (Exception ex)
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
                                    }*/
                                //}
                            }).start();

                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                            prd.dismiss();
                            showShortToast(language.getMessageError());
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                prd.dismiss();
                    //showLongToast("Network problem while service calling before");
                    if (isCallservice) {
                        // showLongToast("service call start now");
                        isCallservice = false;
                        showProgressDialog();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                // Actions to do after 10 seconds
                                String urlLoginDetail = null;
                                try {
                                    urlLoginDetail = DataHelper.URL_USER_HELPER + "logindetails/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                                    urlLoginDetail = urlLoginDetail + "/name=" + userName;
                                    urlLoginDetail = urlLoginDetail + "/password=" + password;
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                callMainServiceUsingVolley(urlLoginDetail, userName, password);
                            }
                        }, DataHelper.NETWORK_CALL_DURATION);
                    } else {
                        showShortToast(language.getMessageError());
                    }
            }
        });

        req.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(LoginActivity.this).add(req);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(prd != null){
            prd.dismiss();
        }

    }
    private void parseUsingLogonSQuare(String response){
        long parsingTime = System.currentTimeMillis();
        ResponseMain responseMain = new ResponseMain();
        try {
            responseMain = LoganSquare.parse(response.toString(),ResponseMain.class);

            if(responseMain.getUserRecordList().size() > 0) {

                prd.setProgress(0);
                prd.setMax(100);
                prd.setProgress(5);

                List<de.mateco.integrAMobile.model_logonsquare.UserRecordListItem> listOfUser = new ArrayList<>();
                listOfUser = responseMain.getUserRecordList(); // 1 = size 1
                prd.setProgress(0);
                prd.setMax(100);
                prd.setProgress(20);
                db.deleteTableAtLogin();


                List<PriceBranchListItem> branches = responseMain.getPriceBranchList(); // 2 = size 51
                db.addPriceBranchList(branches); // 2
                List<PriceDeviceGroupListItem> deviceGroups = responseMain.getPriceDeviceGroupList(); // 3 = size 17
                db.addPriceDeviceGroupListItem(deviceGroups); // 3
                List<PriceRentalListItem> rentalData = responseMain.getPriceRentalList(); // 4 = size 7
                db.addPriceRentalListItem(rentalData); // 4
                prd.setProgress(0);
                prd.setMax(100);
                prd.setProgress(25);


                List<PriceStandardListItem> listOfOfflineStandardPrice = responseMain.getPriceStandardList(); // 5 = size 15975

                long startTime = System.currentTimeMillis();
                db.addPriceStandardListItem(listOfOfflineStandardPrice); // 5
                long endTime = System.currentTimeMillis()-startTime;
                Log.e(" ####### "," time take to insert : "+endTime);
                prd.setProgress(0);
                prd.setMax(100);
                prd.setProgress(35);


                List<PriceEquipmentHeightListItem> listOfOfflineEquipment = responseMain.getPriceEquipmentHeightList(); // 6 = size 1445
                db.addPriceEquipmentHeightListItem(listOfOfflineEquipment); // 6
                List<PriceStaffelListItem> listOfPriceStaffel = responseMain.getPriceStaffelList(); // 7 = size 129
                db.addPriceStaffelListItem(listOfPriceStaffel); // 7
                List<CustomerLandListItem> countries = responseMain.getCustomerLandList(); // 8 = size = 65
                db.addCustomerLandListItem(countries); // 8
                prd.setProgress(0);
                prd.setMax(100);
                prd.setProgress(40);


                List<CustomerRechtsFormComboListItem> legalForms = responseMain.getCustomerRechtsFormComboList(); // 9 = size 40
                db.addCustomerRechtsFormComboListItem(legalForms); // 9
                List<CustomerContactPersonSalutationComboListItem> salutations =
                        responseMain.getCustomerContactPersonSalutationComboList(); // 10 = size 2
                db.addCustomerContactPersonSalutationComboListItem(salutations); // 10
                prd.setProgress(0);
                prd.setMax(100);
                prd.setProgress(45);


                List<CustomerContactPersonFunctionComboListItem> functions =
                        responseMain.getCustomerContactPersonFunctionComboList(); //11 = size 88 - prb 72
                db.addCustomerContactPersonFunctionComboListItem(functions); // 11
                List<CustomerContactPersonDecisionMakersListItem> listOfDecisionMaker =
                        responseMain.getCustomerContactPersonDecisionMakersList();//12 = size 5
                db.addCustomerContactPersonDecisionMakersListItem(listOfDecisionMaker); // 12
                prd.setProgress(0);
                prd.setMax(100);
                prd.setProgress(50);


                List<CustomerContactPersonDocumentlanguageListItem> languages =
                        responseMain.getCustomerContactPersonDocumentlanguageList(); // 13 = size 1
                db.addCustomerContactPersonDocumentlanguageListItem(languages); // 13
                List<CustomerContactPersonFeatureListItem> listOfFeatures = responseMain.getCustomerContactPersonFeatureList(); // 14 = size 85
                db.addCustomerContactPersonFeatureListItem(listOfFeatures); // 14
                prd.setProgress(55);


                List<CustomerActivityTypeListItem> listOfActivityType = responseMain.getCustomerActivityTypeList(); // 15 = size 14
                db.addCustomerActivityTypeListItem(listOfActivityType); // 15
                String jsonActivityType = LoganSquare.serialize(listOfActivityType, CustomerActivityTypeListItem.class);
                matecoPriceApplication.saveData(DataHelper.CustomerActivityTypelist, jsonActivityType);

                List<CustomerActivityTopicListItem> listOfTopic = responseMain.getCustomerActivityTopicList(); // 16 = size 28
                db.addCustomerActivityTopicListItem(listOfTopic); // 16
                String jsonActivityTopic = LoganSquare.serialize(listOfTopic, CustomerActivityTopicListItem.class);
                matecoPriceApplication.saveData(DataHelper.CustomerActivityTopiclist, jsonActivityTopic);

                prd.setProgress(60);


                List<CustomerActivityEmployeeListItem> listOfEmployee = responseMain.getCustomerActivityEmployeeList(); // 17 = size 913
                db.addCustomerActivityEmployeeListItem(listOfEmployee); // 17
                String jsonEmployeeList = LoganSquare.serialize(listOfEmployee, CustomerActivityEmployeeListItem.class);
                matecoPriceApplication.saveData(DataHelper.CustomerActivityEmployeelist, jsonEmployeeList);

                List<BVODeviceTypeListItem> listOfDeviceTypes = responseMain.getBVODeviceTypeList(); // 18 = size 859
                db.addBVODeviceTypeListItem(listOfDeviceTypes);// 18
                prd.setProgress(65);


                List<BVOZugangComboListItem> listOfAccess = responseMain.getBVOZugangComboList(); // 19 = size 2
                db.addBVOZugangComboListItem(listOfAccess); // 19
                List<BVOBauvorhabenComboListItem> listOfBUildingProject = responseMain.getBVOBauvorhabenComboList(); // 20 = size 7
                db.addBVOBauvorhabenComboListItem(listOfBUildingProject); // 20
                prd.setProgress(70);


                List<ProjektBUhnenAubenInnenComboListItem> listOfProjectStage = responseMain.getProjektBUhnenAubenInnenComboList(); // 21 = size 3
                db.addProjektBUhnenAubenInnenComboListItem(listOfProjectStage); // 21
                List<ProjektGebietComboListItem> listOfArea = responseMain.getProjektGebietComboList(); // 22 = size 151 prb here = 127
                db.addProjektGebietComboListItem(listOfArea); // 22
                prd.setProgress(75);

                List<ProjektartComboListItem> listOfProjectArt = responseMain.getProjektartComboList(); // 23 = size 37
                db.addProjektartComboListItem(listOfProjectArt); // 23
                prd.setProgress(80);


                List<ProjekttypComboListItem> listOfProjectType = responseMain.getProjekttypComboList(); // 24 = size 4
                db.addProjekttypComboListItem(listOfProjectType); // 24
                prd.setProgress(85);


                List<ProjektphaseComboListItem> listOfProjectPhase = responseMain.getProjektphaseComboList(); // 25 = size 10
                db.addProjektphaseComboListItem(listOfProjectPhase); // 25
                prd.setProgress(90);

                List<ProjektGewerkComboListItem> listOfProjectTrade = responseMain.getProjektGewerkComboList(); // 26 = size 36
                db.addProjektGewerkComboListItem(listOfProjectTrade); // 26
                List<BrancheListItem> listOfCustomerBranch = responseMain.getBrancheList(); // 27 = size 45
                db.addBrancheListItem(listOfCustomerBranch); // 27
                prd.setProgress(95);

                List<ListOfBuheneartComboBoxItemItem> listOfBuheneart = responseMain.getListOfBuheneartComboBoxItem(); // 28 = size 3
                db.addListOfBuheneartComboBoxItemItem(listOfBuheneart); // 28
                List<ListOfLadefahrzeugComboBoxItemItem> arraylistLadefahrzeug = responseMain.getListOfLadefahrzeugComboBoxItem(); // 29 = size 6
                db.addListOfLadefahrzeugComboBoxItemItem(arraylistLadefahrzeug); // 29

                long parsingElaplseTime = System.currentTimeMillis() - parsingTime;
                LogApp.showLog(" logon parsing ", " time taken by service calling and parsing : " + parsingElaplseTime);
                long databaseTime = System.currentTimeMillis();
                long databaseElaplseTime = System.currentTimeMillis() - databaseTime;
                LogApp.showLog("database insertion", " time taken by service calling and parsing : " + databaseElaplseTime);

                prd.setProgress(100);
                prd.dismiss();

                String json = new Gson().toJson(listOfUser);
                matecoPriceApplication.saveLoginUser(DataHelper.LoginPerson, json);
                elapsedTimeServiceCalling = System.currentTimeMillis() - startTimeSeviceCalling;
                elapsedTimePermenent = System.currentTimeMillis() - startTimePermenent;
                LogApp.showLog("parsing_logon_servicecall_entireTime", " time taken by service calling and parsing : " + elapsedTimePermenent);

                Intent intentHome = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intentHome);
                finish();
            }else {
                LoginActivity.this.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        prd.dismiss();
                        showShortToast("Fehler");
                    }
                });


            }

        } catch (IOException e) {
            e.printStackTrace();
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
}
