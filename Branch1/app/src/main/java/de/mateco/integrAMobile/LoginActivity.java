package de.mateco.integrAMobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.DataHelper;
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
    ProgressDialog prd;
    private DataBaseHandler db;
    private MatecoPriceApplication matecoPriceApplication;
    private Language language;
    private EditText textUserName, textPassword;
    private TextView labelLoginTitle;
    ImageView imgVisibility;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                        public void OnAsynResult(String result)
                        {
                            Log.e("result", result);
                            if(result.equals("error"))
                            {
                                showShortToast(language.getMessageError());
                                prd.dismiss();
                            }
                            else if(result.equals("[]"))
                            {
                                showShortToast("Fehler");
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
                                    BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                                    {
                                        @Override
                                        public void OnAsynResult(final String result)

                                        {
                                            Log.e("result 11111","main service resule variabe : "+result);
                                            if(result.equals("error"))
                                            {
                                                prd.dismiss();
                                                showShortToast(language.getMessageError());
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
                                    }
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
                        Log.e("url", url);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(prd != null){
            prd.dismiss();
        }

    }
}
