package de.mateco.integrAMobile.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bluelinelabs.logansquare.LoganSquare;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.LogApp;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LoginPersonModel;
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
import de.mateco.integrAMobile.model_logonsquare.UserRecordListItem;

public class SettingOfflineFragment extends BaseFragment implements View.OnClickListener
{
    ProgressDialog prd;
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
            prd = new ProgressDialog(getActivity());
            prd.setCancelable(false);
            prd.setTitle(language.getMessageWaitWhileLoading());
            prd.setMessage(language.getMessageWaitWhileLoading());
            prd.show();

            String urlLoginDetail = null;
            String userName = application.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserEmail();
            String password = "Mateco01";
            try {
                urlLoginDetail = DataHelper.URL_USER_HELPER +"logindetails/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                urlLoginDetail = urlLoginDetail + "/name=" + userName;
                urlLoginDetail = urlLoginDetail + "/password=" + password;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            // for volley
            callMainServiceUsingVolley(urlLoginDetail);

        }
        else
        {
            showShortToast(language.getMessageNetworkNotAvailable());
        }
    }
    private void callMainServiceUsingVolley(final String url){
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
                                        parseUsingLogonSQuare(response.toString());
                                    }
                                    catch (Exception ex)
                                    {
                                        ex.printStackTrace();
                                        showShortToast(language.getMessageErrorAtParsing());
                                        prd.dismiss();
                                    }
                                }
                            }).start();

                        }
                        catch (Exception ex)
                        {
                            prd.dismiss();
                            showShortToast(language.getMessageError());
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                prd.dismiss();
                showShortToast(language.getMessageError());
            }
        });

        req.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getActivity()).add(req);
    }
    private void parseUsingLogonSQuare(String response){
        long parsingTime = System.currentTimeMillis();
        ResponseMain responseMain = new ResponseMain();
        try {
            responseMain = LoganSquare.parse(response.toString(),ResponseMain.class);
            List<UserRecordListItem> listOfUser = new ArrayList<>();
            listOfUser = responseMain.getUserRecordList(); // 1 = size 1
            db.deleteTableAtLogin();


            List<PriceBranchListItem> branches = responseMain.getPriceBranchList(); // 2 = size 51
            db.addPriceBranchList(branches); // 2
            List<PriceDeviceGroupListItem> deviceGroups = responseMain.getPriceDeviceGroupList(); // 3 = size 17
            db.addPriceDeviceGroupListItem(deviceGroups); // 3
            List<PriceRentalListItem> rentalData = responseMain.getPriceRentalList(); // 4 = size 7
            db.addPriceRentalListItem(rentalData); // 4

            List<PriceStandardListItem> listOfOfflineStandardPrice = responseMain.getPriceStandardList(); // 5 = size 15975
            db.addPriceStandardListItem(listOfOfflineStandardPrice); // 5


            List<PriceEquipmentHeightListItem> listOfOfflineEquipment = responseMain.getPriceEquipmentHeightList(); // 6 = size 1445
            db.addPriceEquipmentHeightListItem(listOfOfflineEquipment); // 6
            List<PriceStaffelListItem> listOfPriceStaffel = responseMain.getPriceStaffelList(); // 7 = size 130
            db.addPriceStaffelListItem(listOfPriceStaffel); // 7
            List<CustomerLandListItem> countries = responseMain.getCustomerLandList(); // 8 = size = 66
            db.addCustomerLandListItem(countries); // 8

            List<CustomerRechtsFormComboListItem> legalForms = responseMain.getCustomerRechtsFormComboList(); // 9 = size 40
            db.addCustomerRechtsFormComboListItem(legalForms); // 9
            List<CustomerContactPersonSalutationComboListItem> salutations =
                    responseMain.getCustomerContactPersonSalutationComboList(); // 10 = size 2
            db.addCustomerContactPersonSalutationComboListItem(salutations); // 10


            List<CustomerContactPersonFunctionComboListItem> functions =
                    responseMain.getCustomerContactPersonFunctionComboList(); //11 = size 88
            db.addCustomerContactPersonFunctionComboListItem(functions); // 11
            List<CustomerContactPersonDecisionMakersListItem> listOfDecisionMaker =
                    responseMain.getCustomerContactPersonDecisionMakersList();//12 = size 5
            db.addCustomerContactPersonDecisionMakersListItem(listOfDecisionMaker); // 12


            List<CustomerContactPersonDocumentlanguageListItem> languages =
                    responseMain.getCustomerContactPersonDocumentlanguageList(); // 13 = size 1
            db.addCustomerContactPersonDocumentlanguageListItem(languages); // 13
            List<CustomerContactPersonFeatureListItem> listOfFeatures = responseMain.getCustomerContactPersonFeatureList(); // 14 = size 85
            db.addCustomerContactPersonFeatureListItem(listOfFeatures); // 14

            List<CustomerActivityTypeListItem> listOfActivityType = responseMain.getCustomerActivityTypeList(); // 15 = size 14
            db.addCustomerActivityTypeListItem(listOfActivityType); // 15
            List<CustomerActivityTopicListItem> listOfTopic = responseMain.getCustomerActivityTopicList(); // 16 = size 28
            db.addCustomerActivityTopicListItem(listOfTopic); // 16


            List<CustomerActivityEmployeeListItem> listOfEmployee = responseMain.getCustomerActivityEmployeeList(); // 17 = size 913
            db.addCustomerActivityEmployeeListItem(listOfEmployee); // 17
            List<BVODeviceTypeListItem> listOfDeviceTypes = responseMain.getBVODeviceTypeList(); // 18 = size 859
            db.addBVODeviceTypeListItem(listOfDeviceTypes);// 18


            List<BVOZugangComboListItem> listOfAccess = responseMain.getBVOZugangComboList(); // 19 = size 2
            db.addBVOZugangComboListItem(listOfAccess); // 19
            List<BVOBauvorhabenComboListItem> listOfBUildingProject = responseMain.getBVOBauvorhabenComboList(); // 20 = size 7
            db.addBVOBauvorhabenComboListItem(listOfBUildingProject); // 20


            List<ProjektBUhnenAubenInnenComboListItem> listOfProjectStage = responseMain.getProjektBUhnenAubenInnenComboList(); // 21 = size 3
            db.addProjektBUhnenAubenInnenComboListItem(listOfProjectStage); // 21
            List<ProjektGebietComboListItem> listOfArea = responseMain.getProjektGebietComboList(); // 22 = size 151
            db.addProjektGebietComboListItem(listOfArea); // 22


            List<ProjektartComboListItem> listOfProjectArt = responseMain.getProjektartComboList(); // 23 = size 37
            db.addProjektartComboListItem(listOfProjectArt); // 23

            List<ProjekttypComboListItem> listOfProjectType = responseMain.getProjekttypComboList(); // 24 = size 4
            db.addProjekttypComboListItem(listOfProjectType); // 24

            List<ProjektphaseComboListItem> listOfProjectPhase = responseMain.getProjektphaseComboList(); // 25 = size 10
            db.addProjektphaseComboListItem(listOfProjectPhase); // 25

            List<ProjektGewerkComboListItem> listOfProjectTrade = responseMain.getProjektGewerkComboList(); // 26 = size 36
            db.addProjektGewerkComboListItem(listOfProjectTrade); // 26
            List<BrancheListItem> listOfCustomerBranch = responseMain.getBrancheList(); // 27 = size 45
            db.addBrancheListItem(listOfCustomerBranch); // 27

            List<ListOfBuheneartComboBoxItemItem> listOfBuheneart = responseMain.getListOfBuheneartComboBoxItem(); // 28 = size 3
            db.addListOfBuheneartComboBoxItemItem(listOfBuheneart); // 28
            List<ListOfLadefahrzeugComboBoxItemItem> arraylistLadefahrzeug = responseMain.getListOfLadefahrzeugComboBoxItem(); // 29 = size 6
            db.addListOfLadefahrzeugComboBoxItemItem(arraylistLadefahrzeug); // 29

            getActivity().runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    updateLastSynchTimeLabel();
                    if(prd != null) {
                        prd.dismiss();
                    }
                }
            });



        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }
}