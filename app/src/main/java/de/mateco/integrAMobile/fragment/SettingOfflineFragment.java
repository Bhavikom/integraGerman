package de.mateco.integrAMobile.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.LoginActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.BaseFragment;
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

public class SettingOfflineFragment extends BaseFragment implements View.OnClickListener
{
    private View rootView;
    private MatecoPriceApplication application;
    private Language language;
    private DataBaseHandler db;
    private TextView labelValueLastSynchTimeCustomer, labelValueLastSynchTimePricing, labelValueLastSynchTimeProject,
                labelValueLastSynchTimeSiteInspection;
    private Button buttonSyncNowCustomer, buttonSyncNowPricing, buttonSyncNowProject, buttonSyncNowSiteInspection;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_setting_offline_data, container, false);
        super.initializeFragment(rootView);

        return rootView;
    }

    @Override
    public void initializeComponents(View rootView)
    {
        application = (MatecoPriceApplication)getActivity().getApplication();
        language = application.getLanguage();
        db = new DataBaseHandler(getActivity());
        labelValueLastSynchTimeCustomer = (TextView)rootView.findViewById(R.id.labelValueLastSynchTimeCustomer);
        labelValueLastSynchTimePricing = (TextView)rootView.findViewById(R.id.labelValueLastSynchTimePricing);
        labelValueLastSynchTimeProject = (TextView)rootView.findViewById(R.id.labelValueLastSynchTimeProject);
        labelValueLastSynchTimeSiteInspection = (TextView)rootView.findViewById(R.id.labelValueLastSynchTimeSiteInspection);

        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        //((HomeActivity) getActivity()).getSupportActionBar().setTitle(language.getLabelofflineSettings());

        setLanguage();
        updateLastSynchTimeLabel();
        super.initializeComponents(rootView);
    }

    @Override
    public void bindEvents(View rootView)
    {
        super.bindEvents(rootView);
    }

    private void updateLastSynchTimeLabel()
    {
        labelValueLastSynchTimeCustomer.setText(db.getLoginSynchTime(getActivity()));
        labelValueLastSynchTimePricing.setText(db.getLoginSynchTime(getActivity()));
        labelValueLastSynchTimeProject.setText(db.getLoginSynchTime(getActivity()));
        labelValueLastSynchTimeSiteInspection.setText(db.getLoginSynchTime(getActivity()));
    }

    private void setLanguage()
    {
        TextView labelCustomer,labelLastSyncTime,labelPricing,labelLastSyncTime1,labelProject,labelLastSyncTime2,
                labelSiteInspection,labelLastSyncTime3;

        labelCustomer = (TextView)rootView.findViewById(R.id.labelCustomer);
        labelLastSyncTime = (TextView)rootView.findViewById(R.id.labelLastSyncTime);
        labelPricing = (TextView) rootView.findViewById(R.id.labelPricing);
        labelLastSyncTime1 = (TextView)rootView.findViewById(R.id.labelLastSyncTime1);
        labelProject = (TextView)rootView. findViewById(R.id.labelProject);
        labelLastSyncTime2 = (TextView)rootView.findViewById(R.id.labelLastSyncTime2);
        labelSiteInspection = (TextView)rootView.findViewById(R.id.labelSiteInspection);
        labelLastSyncTime3 = (TextView)rootView.findViewById(R.id.labelLastSyncTime3);

        labelCustomer.setText(language.getLabelCustomer());
        labelLastSyncTime.setText(language.getLabelLastSyncTime());
        labelPricing.setText(language.getLabelPricing());
        labelLastSyncTime1.setText(language.getLabelLastSyncTime());
        labelProject.setText(language.getLabelProject());
        labelLastSyncTime2.setText(language.getLabelLastSyncTime());
        labelSiteInspection.setText(language.getLabelSiteInspection());
        labelLastSyncTime3.setText(language.getLabelLastSyncTime());

        buttonSyncNowCustomer = (Button)rootView.findViewById(R.id.buttonSyncNow1);
        buttonSyncNowPricing = (Button)rootView.findViewById(R.id.buttonSyncNow2);
        buttonSyncNowProject = (Button)rootView.findViewById(R.id.buttonSyncNow3);
        buttonSyncNowSiteInspection = (Button)rootView.findViewById(R.id.buttonSyncNow4);

        buttonSyncNowCustomer.setText(language.getLabelSyncNow());
        buttonSyncNowPricing.setText(language.getLabelSyncNow());
        buttonSyncNowProject.setText(language.getLabelSyncNow());
        buttonSyncNowSiteInspection.setText(language.getLabelSyncNow());

        buttonSyncNowCustomer.setOnClickListener(this);
        buttonSyncNowPricing.setOnClickListener(this);
        buttonSyncNowProject.setOnClickListener(this);
        buttonSyncNowSiteInspection.setOnClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);
        menu.findItem(R.id.actionSearch).setVisible(false);
        menu.findItem(R.id.actionWrong).setVisible(false);
        menu.findItem(R.id.actionRight).setVisible(false);
        menu.findItem(R.id.actionAdd).setVisible(false);
        menu.findItem(R.id.actionEdit).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);
        //menu.findItem(R.id.actionForwardSpace).setVisible(false);
        menu.findItem(R.id.actionSettings).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.actionSettings:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new SettingFragment());
                //transaction.addToBackStack(SettingFragment.Tag);
                transaction.addToBackStack("Setting");
                transaction.commit();
                return true;
            case R.id.actionBack:
                if (getFragmentManager().getBackStackEntryCount() == 0)
                {
                    getActivity().finish();
                }
                else
                {
                    getFragmentManager().popBackStack();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonSyncNow1:
                updateSynchData();
                break;
            case R.id.buttonSyncNow2:
                updateSynchData();
                break;
            case R.id.buttonSyncNow3:
                updateSynchData();
                break;
            case R.id.buttonSyncNow4:
                updateSynchData();
                break;
        }
    }

    private void updateSynchData()
    {
        if(DataHelper.isNetworkAvailable(getActivity()))
        {
            final ProgressDialog prd;
            prd = new ProgressDialog(getActivity());
            prd.setTitle(language.getMessageWaitWhileLoading());
            prd.setMessage(language.getMessageWaitWhileLoading());
            prd.show();

            String url = null;
            try {
                url = DataHelper.URL_USER_HELPER +"salesservice/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try
                    {
                        MainServiceCallModel mainServiceCallModel = new Gson().fromJson(response.toString(), MainServiceCallModel.class);
                        db.deleteTableAtLogin();
                        ArrayList<PricingOfflineStandardPriceData> listOfOfflineStandardPrice = mainServiceCallModel.getListOfStandardPrice();
                        db.addPricingOfflineStandardPrice(listOfOfflineStandardPrice); // 1
                        ArrayList<LadefahrzeugComboBoxItemModel> arraylistLadefahrzeug = mainServiceCallModel.getArraylsitLadefahrzeug();
                        db.addLadefahzeg(arraylistLadefahrzeug); // 2
                        ArrayList<Pricing1PriceRentalData> rentalData = mainServiceCallModel.getListOfPriceRental();
                        db.addPriceRental(rentalData); // 3
                        ArrayList<PricingOfflineEquipmentData> listOfOfflineEquipment = mainServiceCallModel.getListOfEquipmentHeight();
                        db.addPricingOfflineEquipmentData(listOfOfflineEquipment); // 4
                        ArrayList<Pricing1DeviceData> deviceGroups = mainServiceCallModel.getListOfDeviceGroup();
                        db.addDevice(deviceGroups); // 5
                        ArrayList<Pricing1BranchData> branches = mainServiceCallModel.getListOfBranch();
                        db.addBranch(branches); // 6
                        ArrayList<LegalFormModel> legalForms = mainServiceCallModel.getListOfLegalForm();
                        db.addLegalForms(legalForms); // 7
                        ArrayList<CountryModel> countries = mainServiceCallModel.getListOfCountry();
                        db.addCountries(countries); // 8
                        ArrayList<SalutationModel> salutations = mainServiceCallModel.getListOfSalutations();
                        db.addSalutation(salutations); // 9
                        ArrayList<FunctionModel> functions = mainServiceCallModel.getListOfFunctions();
                        db.addFunction(functions); // 10
                        ArrayList<FeatureModel> listOfFeatures = mainServiceCallModel.getListOfFeatures();
                        db.addFeatures(listOfFeatures); // 11
                        ArrayList<DocumentLanguageModel> languages = mainServiceCallModel.getListOfDocumentLanguage();
                        db.addDocumentLanguage(languages); // 12
                        ArrayList<DecisionMakerModel> listOfDecisionMaker = mainServiceCallModel.getListOfDecisionMaker();
                        db.addDecisionMakers(listOfDecisionMaker); // 13
                        ArrayList<ActivityTypeModel> listOfActivityType = mainServiceCallModel.getListOfActivityType();
                        db.addActivityTypes(listOfActivityType); // 14
                        ArrayList<ActivityTopicModel> listOfTopic = mainServiceCallModel.getListOfActivityTopic();
                        db.addActivityTopics(listOfTopic); // 15
                        ArrayList<EmployeeModel> listOfEmployee = mainServiceCallModel.getListOfEmployee();
                        db.addEmployees(listOfEmployee); // 16
                        ArrayList<SiteInspectionDeviceTypeModel> listOfDeviceTypes = mainServiceCallModel.getListOfDeviceType();
                        db.addSiteInspectionDeviceType(listOfDeviceTypes); // 17
                        ArrayList<SiteInspectionBuildingProjectModel> listOfBUildingProject = mainServiceCallModel.getListOfBuildingProject();
                        db.addBuildingProject(listOfBUildingProject); // 18
                        ArrayList<SiteInspectionAccessModel> listOfAccess = mainServiceCallModel.getListOfAccess();
                        db.addAccess(listOfAccess); // 19
                        ArrayList<PriceStaffelModel> listOfPriceStaffel = mainServiceCallModel.getListOfPriceStaffel();
                        db.addPriceStaffel(listOfPriceStaffel); // 20
                        ArrayList<CustomerBranchModel> listOfCustomerBranch = mainServiceCallModel.getListOfCustomerBranch();
                        if(listOfCustomerBranch != null)
                            db.addCustomerBranch(listOfCustomerBranch); // 21
                        ArrayList<ProjectArtModel> listOfProjectArt = mainServiceCallModel.getListOfProjectArt();
                        if(listOfProjectArt != null)
                            db.addProjectArt(listOfProjectArt); // 22
                        ArrayList<ProjectTypeModel> listOfProjectType = mainServiceCallModel.getListOfProjectType();
                        if(listOfProjectType != null)
                            db.addProjectType(listOfProjectType); // 23
                        ArrayList<ProjectPhaseModel> listOfProjectPhase = mainServiceCallModel.getListOfProjectPhase();
                        if(listOfProjectPhase != null)
                            db.addProjectPhase(listOfProjectPhase); // 24
                        ArrayList<ProjectStagesModel> listOfProjectStage = mainServiceCallModel.getListOfProjectStage();
                        if(listOfProjectStage != null)
                            db.addProjectStage(listOfProjectStage); // 25
                        ArrayList<ProjectTradeModel> listOfProjectTrade = mainServiceCallModel.getListOfProjectTrade();
                        if(listOfProjectTrade != null)
                            db.addProjectTrade(listOfProjectTrade); // 26
                        ArrayList<ProjectAreaModel> listOfArea = mainServiceCallModel.getListOfArea();
                        if(listOfArea != null)
                            db.addProjectArea(listOfArea); // 27
                        ArrayList<BuheneartModel> listOfBuheneart = mainServiceCallModel.getListOfBuheneart();
                        if(listOfBuheneart != null)
                            db.addBuheneart(listOfBuheneart); // 28

                        updateLastSynchTimeLabel();
                        prd.dismiss();
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                        showShortToast(language.getMessageErrorAtParsing());
                        prd.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    prd.dismiss();
                    showShortToast(language.getMessageError());
                }
            });
            Volley.newRequestQueue(getActivity()).add(objectRequest);
            /*BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
            {
                @Override
                public void OnAsynResult(String result)
                {
                    Log.e("main service update ", result);
                    if(result.equals("error"))
                    {
                        prd.dismiss();
                        showShortToast(language.getMessageError());
                    }
                    else if(result.equals(DataHelper.NetworkError)){
                        showShortToast(language.getMessageNetworkNotAvailable());
                    }
                    else
                    {
                        try
                        {
                            MainServiceCallModel mainServiceCallModel = new Gson().fromJson(result, MainServiceCallModel.class);
                            db.deleteTableAtLogin();


                            ArrayList<PricingOfflineStandardPriceData> listOfOfflineStandardPrice = mainServiceCallModel.getListOfStandardPrice();
                            db.addPricingOfflineStandardPrice(listOfOfflineStandardPrice);
                            ArrayList<Pricing1PriceRentalData> rentalData = mainServiceCallModel.getListOfPriceRental();
                            db.addPriceRental(rentalData);
                            ArrayList<PricingOfflineEquipmentData> listOfOfflineEquipment = mainServiceCallModel.getListOfEquipmentHeight();
                            db.addPricingOfflineEquipmentData(listOfOfflineEquipment);
                            ArrayList<Pricing1DeviceData> deviceGroups = mainServiceCallModel.getListOfDeviceGroup();
                            db.addDevice(deviceGroups);
                            ArrayList<Pricing1BranchData> branches = mainServiceCallModel.getListOfBranch();
                            db.addBranch(branches);
                            ArrayList<LegalFormModel> legalForms = mainServiceCallModel.getListOfLegalForm();
                            db.addLegalForms(legalForms);
                            ArrayList<CountryModel> countries = mainServiceCallModel.getListOfCountry();
                            db.addCountries(countries);
                            ArrayList<SalutationModel> salutations = mainServiceCallModel.getListOfSalutations();
                            db.addSalutation(salutations);
                            ArrayList<FunctionModel> functions = mainServiceCallModel.getListOfFunctions();
                            db.addFunction(functions);
                            ArrayList<FeatureModel> listOfFeatures = mainServiceCallModel.getListOfFeatures();
                            db.addFeatures(listOfFeatures);
                            ArrayList<DocumentLanguageModel> languages = mainServiceCallModel.getListOfDocumentLanguage();
                            db.addDocumentLanguage(languages);
                            ArrayList<DecisionMakerModel> listOfDecisionMaker = mainServiceCallModel.getListOfDecisionMaker();
                            db.addDecisionMakers(listOfDecisionMaker);
                            ArrayList<ActivityTypeModel> listOfActivityType = mainServiceCallModel.getListOfActivityType();
                            db.addActivityTypes(listOfActivityType);
                            ArrayList<ActivityTopicModel> listOfTopic = mainServiceCallModel.getListOfActivityTopic();
                            db.addActivityTopics(listOfTopic);
                            ArrayList<EmployeeModel> listOfEmployee = mainServiceCallModel.getListOfEmployee();
                            db.addEmployees(listOfEmployee);
                            ArrayList<SiteInspectionDeviceTypeModel> listOfDeviceTypes = mainServiceCallModel.getListOfDeviceType();
                            db.addSiteInspectionDeviceType(listOfDeviceTypes);
                            ArrayList<SiteInspectionBuildingProjectModel> listOfBUildingProject = mainServiceCallModel.getListOfBuildingProject();
                            db.addBuildingProject(listOfBUildingProject);
                            ArrayList<SiteInspectionAccessModel> listOfAccess = mainServiceCallModel.getListOfAccess();
                            db.addAccess(listOfAccess);
                            updateLastSynchTimeLabel();
                            prd.dismiss();
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                            showShortToast(language.getMessageErrorAtParsing());
                            prd.dismiss();
                        }
                    }
                }
            };

            try
            {
                //String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.MAIN_SERVICE + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                String url = DataHelper.URL_USER_HELPER +"salesservice/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), false);
                asyncTask.execute();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                prd.dismiss();
            }*/
        }
        else
        {
            showShortToast(language.getMessageNetworkNotAvailable());
        }
    }
}