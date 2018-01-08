package de.mateco.integrAMobile.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.assist.MemoryCacheKeyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.mateco.integrAMobile.Helper.Constants;
import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.GlobalMethods;
import de.mateco.integrAMobile.Helper.InputFilterMinMax;
import de.mateco.integrAMobile.Helper.PreferencesClass;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.CountryAdapter;
import de.mateco.integrAMobile.adapter.Pricing2KaNrDataAdapter;
import de.mateco.integrAMobile.adapter.Pricing2KaNrListViewDataAdapter;
import de.mateco.integrAMobile.adapter.Pricing2KaNrListViewLessThenDataAdapter;
import de.mateco.integrAMobile.adapter.Pricing2KaNrListViewMoreThenDataAdapter;
import de.mateco.integrAMobile.adapter.Pricing2OfflineKaNrStandardPriceListViewDataAdapter;
import de.mateco.integrAMobile.adapter.SiteInspectionDeviceTypeAdapter;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.LoadedCustomerFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.ContactPersonModel;
import de.mateco.integrAMobile.model.CountryModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LoginPersonModel;
import de.mateco.integrAMobile.model.PriceGeraeteTypeParam;
import de.mateco.integrAMobile.model.PriceStaffelModel;
import de.mateco.integrAMobile.model.Pricing1BranchData;
import de.mateco.integrAMobile.model.Pricing2InsertPriceUseInformationListData;
import de.mateco.integrAMobile.model.Pricing2KaNrData;
import de.mateco.integrAMobile.model.Pricing2KaNrListViewData;
import de.mateco.integrAMobile.model.Pricing2KaNrListViewLessThen1800Data;
import de.mateco.integrAMobile.model.Pricing2KaNrListViewMoreThen1800Data;
import de.mateco.integrAMobile.model.PricingCustomerOrderBasicInfo;
import de.mateco.integrAMobile.model.PricingOfflineStandardPriceData;
import de.mateco.integrAMobile.model.SiteInspectionDeviceTypeModel;

public class PricingFragment2 extends LoadedCustomerFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private MatecoPriceApplication matecoPriceApplication;
    private Language language;
    private View rootView;
    private int rentalDays;
    private String datesComma="",fromDate="",toDate="";
    private ArrayList<Pricing2KaNrData> rowKaNrItems;
    private ArrayList<Pricing2KaNrData> lablesKaNr;
    private ArrayList<SiteInspectionDeviceTypeModel> listDeviceType;
    private SiteInspectionDeviceTypeAdapter deviceTypeAdapter = null;

    private Spinner spKaNr;
    private Spinner spGeraetetype;
    private DataBaseHandler db;
    private String kanr;  //kanr is kontakton
    private int kaNrOfLoadedCustomer;
    private int branchId;
    private String branchName;
    private String contactPersonNo = "", contactPerson = "";
    private String deviceType;
    private String equipmentIds;
    private int rental;
    private String equipmentJson = "";
    private ListView lvKaNrListView;
    private Spinner spLandUse;
    //private Spinner spinnerEinsatzland;
    private ArrayList<CountryModel> rowLandUseItems;
    //private ArrayList<CountryModel> lablesLandUse;
    private ArrayList<Pricing2KaNrListViewData> rowKaNrListViewItems;
    private Pricing2KaNrListViewDataAdapter kaNrListViewItemsAdapter = null;
    private ArrayList<Pricing2KaNrListViewLessThen1800Data> rowKaNrListViewLessThenItems;
    private Pricing2KaNrListViewLessThenDataAdapter kaNrListViewLessThenDataItemsAdapter = null;
    private ArrayList<Pricing2KaNrListViewMoreThen1800Data> rowKaNrListViewMoreThenItems;
    private Pricing2KaNrListViewMoreThenDataAdapter kaNrListViewMoreThenDataItemsAdapter = null;
    private ArrayList<CountryModel> arraylistCountry = null;
    private ArrayList<PricingOfflineStandardPriceData> rowOfflineStandardPriceListViewItems;
    private Pricing2OfflineKaNrStandardPriceListViewDataAdapter offlineStandardPriceListViewItemsAdapter = null;
    private Menu menu;
    private LinearLayout linearHeaderTags;
    private double price = 0.0;
    private double gesAminitiesPrice = 0.0;
    private double gesAm = 0.0;
    private String tags;
    private ArrayList<Double> listOfRValue = new ArrayList<>();
    private ArrayList<Double> listOfMValue = new ArrayList<>();
    private ArrayList<String> listOfTagName;
    private ArrayList<String> TagName;
    private ContactPersonModel ContactPerson;
    private ArrayList<PriceStaffelModel> listOfRealTag;
    private ImageButton imgBtnContractSpotHelp, imgBtnProjectHelp, imgBtnProjectRemove, imageButtonCurrentLocation, imageButtonMapLocation;
    private EditText textUseZipCode, textUserPlace, textUserStreet, textZusatz, textContactPersonName, textEntferunung, textContactPersonTelephone;
    private TextView labelProject;
    CheckBox checkBoxSelbstfahrer;
    private int countryNameId = 0;
    private String countryName="";
    String Besteller_Telefon = "";
    String Besteller_Email = "";
    String Besteller_Anrede = "";
    String Besteller_Mobil = "";
    String GeratetypeId = "0";

    private String startDate, endDate;

    int KaNrNo = 0;
    private boolean isFirstTime = true;
    private ImageView imageViewCall;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    PreferencesClass preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_pricing_2, container, false);
            super.initializeFragment(rootView);
        } else {
            Log.e("at error", "not null rootview");
            ((HomeActivity) getActivity()).getSupportActionBar().setTitle(language.getLabelPrice());
            if (rootView.getParent() != null)
                ((ViewGroup) rootView.getParent()).removeView(rootView);
            gesAm = 0.0;
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadPricingCustomerOrderInfoFromPreference();
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("enter", "enter");
    }

    @Override
    public void initializeComponents(View rootView) {
        preferences =new PreferencesClass(getActivity());
        matecoPriceApplication = (MatecoPriceApplication) getActivity().getApplication();
        rowKaNrItems = new ArrayList<>();
        language = matecoPriceApplication.getLanguage();
        db = new DataBaseHandler(getActivity());
        lvKaNrListView = (ListView) rootView.findViewById(R.id.lvPricing2KaNr);
        spLandUse = (Spinner) rootView.findViewById(R.id.spLandUse);
       // spinnerEinsatzland = (Spinner)rootView.findViewById(R.id.spinnerEinsatazland);
        spKaNr = (Spinner) rootView.findViewById(R.id.spKaNr);
        spGeraetetype = (Spinner) rootView.findViewById(R.id.spGeraetetype);
        imageViewCall = (ImageView) rootView.findViewById(R.id.imageViewCall);
        ContactPerson = new ContactPersonModel();
        //lablesLandUse = new ArrayList<>();
        rowKaNrListViewItems = new ArrayList<>();
        rowKaNrListViewLessThenItems = new ArrayList<>();
        rowKaNrListViewMoreThenItems = new ArrayList<>();
        rowOfflineStandardPriceListViewItems = new ArrayList<>();
        textContactPersonName = (EditText) rootView.findViewById(R.id.etContractSpot);
        textContactPersonTelephone = (EditText) rootView.findViewById(R.id.etAnspTelefon);
        labelProject = (TextView) rootView.findViewById(R.id.etProjectP2);
        textUseZipCode = (EditText) rootView.findViewById(R.id.etUseZipCodeP2);
        textUserPlace = (EditText) rootView.findViewById(R.id.etEinsatzPLZP2);
        textUserStreet = (EditText) rootView.findViewById(R.id.etRoadUseP2);
        textZusatz = (EditText) rootView.findViewById(R.id.etAddition);
        checkBoxSelbstfahrer=(CheckBox)rootView.findViewById(R.id.checkBoxSelbstfaher);
        if(!TextUtils.isEmpty(preferences.getPriceDevice())){
            if(preferences.getPriceDevice().equalsIgnoreCase("1") || preferences.getPriceDevice().equalsIgnoreCase("2"))
            {
                checkBoxSelbstfahrer.setEnabled(true);
            }
            else {
                checkBoxSelbstfahrer.setEnabled(false);
            }
        }
        else {
            checkBoxSelbstfahrer.setEnabled(false);
        }
        final PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo,
                new Gson().toJson(new PricingCustomerOrderBasicInfo()));
        labelProject.setText(model.getProject());
        ArrayList<Pricing1BranchData> branches = new ArrayList<>();
        branches=db.getBranchList(preferences.getBranchId());


        final ArrayList<Pricing1BranchData> finalBranches = branches;
        checkBoxSelbstfahrer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    textUseZipCode.setText(finalBranches.get(0).getPlz());
                    textUserPlace.setText(finalBranches.get(0).getOrt());
                    textUserStreet.setText(finalBranches.get(0).getStrasse());
                    spLandUse.setPrompt(finalBranches.get(0).getDesignation());

                    textUseZipCode.setEnabled(false);
                    textUserPlace.setEnabled(false);
                    textUserStreet.setEnabled(false);
                    spLandUse.setEnabled(false);
                }
                else {
                    textUseZipCode.setText(model.getZipCode());
                    textUserPlace.setText(model.getPlace());
                    textUserStreet.setText(model.getStreet());

                    textUseZipCode.setEnabled(true);
                    textUserPlace.setEnabled(true);
                    textUserStreet.setEnabled(true);
                    spLandUse.setEnabled(true);
                }
            }
        });


        textEntferunung = (EditText) rootView.findViewById(R.id.etEntfernungP2);
        textEntferunung.setFilters(new InputFilter[]{new InputFilterMinMax("0", "999")});
        /*offlineStandardPriceListViewItemsAdapter = new Pricing2OfflineKaNrStandardPriceListViewDataAdapter(getActivity(), rowOfflineStandardPriceListViewItems);
        lvKaNrListView.setAdapter(offlineStandardPriceListViewItemsAdapter);*/
        loadArguments();
        listDeviceType = new ArrayList<>();
        deviceTypeAdapter = new SiteInspectionDeviceTypeAdapter(getActivity(), listDeviceType, language);
        spGeraetetype.setAdapter(deviceTypeAdapter);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle(language.getLabelPrice());
        setUpLanguage();
        super.initializeComponents(rootView);

        buildGoogleApiClient();
        mGoogleApiClient.connect();

        /* arraylistCountry=db.getCountries();
        CountryAdapter2 countryAdapter2 = new CountryAdapter2(getActivity(), arraylistCountry, R.layout.list_item_spinner_country, language);
        spinnerEinsatzland.setAdapter(countryAdapter2);

        spinnerEinsatzland.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countryNameId = Integer.parseInt(arraylistCountry.get(position - 1).getCountryId());
                //  LevelGroupDesignation = lablesDevice.get(position).getDesignation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    private void loadArguments() {
        if (getArguments() != null) {
            Log.e("here at load", "arguments not null");
            if (getArguments().containsKey("kanr"))
                kanr = getArguments().getString("kanr");
            if (getArguments().containsKey("branchId"))
                branchId = getArguments().getInt("branchId");

            if (getArguments().containsKey("branchName"))
                branchName = getArguments().getString("branchName");
            if (getArguments().containsKey("contactPersonNo"))
                contactPersonNo = getArguments().getString("contactPersonNo");
            if (getArguments().containsKey("contactPersonName"))
                contactPerson = getArguments().getString("contactPersonName");
            if (getArguments().containsKey("deviceType"))
                deviceType = getArguments().getString("deviceType");
            if (getArguments().containsKey("equipmentIds"))
                equipmentIds = getArguments().getString("equipmentIds");
            if (getArguments().containsKey("EquipmentJson"))
                equipmentJson = getArguments().getString("EquipmentJson");
            if (getArguments().containsKey("rental"))
                rental = getArguments().getInt("rental");
            if (getArguments().containsKey("rentalDays"))
                rentalDays = getArguments().getInt("rentalDays");
            if(getArguments().containsKey("dates_comma"))
            {
                datesComma = getArguments().getString("dates_comma");
            }
            if(getArguments().containsKey("fromDate")){
                fromDate = getArguments().getString("fromDate");
            }
            if(getArguments().containsKey("toDate")){
                toDate = getArguments().getString("toDate");
            }
            if(getArguments().containsKey("startDate"))
            {
                startDate = getArguments().getString("startDate");
                if(getArguments().containsKey("endDate"))
                {
                    endDate = getArguments().getString("endDate");
                }
                PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo,
                        new Gson().toJson(new PricingCustomerOrderBasicInfo()));
                model.setStartDate(startDate);
                model.setEndDate(endDate);
                String datesComma=getArguments().getString("dates_comma");
                model.setDatesCommas(datesComma);
                matecoPriceApplication.saveData(DataHelper.PricingCustomerBasicOrderInfo, new Gson().toJson(model));
            }
            if (getArguments().containsKey("ContactPerson"))
            {
                ContactPerson = getArguments().getParcelable("ContactPerson");
                textContactPersonName.setText(ContactPerson.getNachname());
                //textContactPersonName.setText(ContactPerson.getAnrede());
                textContactPersonTelephone.setText(ContactPerson.getMobil());

                Besteller_Telefon = ContactPerson.getTelefon();
                Besteller_Email = ContactPerson.getEmail();
                Besteller_Anrede = ContactPerson.getAnredeID();
                Besteller_Mobil = ContactPerson.getMobil();

                if (textUserStreet == null)
                    textUserStreet = (EditText) rootView.findViewById(R.id.etRoadUseP2);
                if (getArguments().containsKey("EinsatzStrasse")) {
                    //textUserStreet.setText("test");
                    textUserStreet.setText(getArguments().getString("EinsatzStrasse") + "");
                }
                if (getArguments().containsKey("EinsatzPLZ")) {


                    textUseZipCode = (EditText) rootView.findViewById(R.id.etUseZipCodeP2);
                    if(!TextUtils.isEmpty(preferences.getComefrom())) {
                        if(preferences.getComefrom().equalsIgnoreCase("selected")) {
                            textUseZipCode.setText("Selected");
                        }
                        else {
                            textUseZipCode.setText(getArguments().getString("EinsatzPLZ") + "");
                        }
                    }
                    else {

                        textUseZipCode.setText(getArguments().getString("EinsatzPLZ") + "");
                    }

                }
                if (getArguments().containsKey("Einsatzort")) {
                    textUserPlace = (EditText) rootView.findViewById(R.id.etEinsatzPLZP2);
                    textUserPlace.setText(getArguments().getString("Einsatzort") + "");
                }
                if (getArguments().containsKey("Zusatz")) {
                    textZusatz = (EditText) rootView.findViewById(R.id.etAddition);
                    textZusatz.setText(getArguments().getString("Zusatz") + "");
                }

                if (getArguments().containsKey("Entfernung")) {
                    textEntferunung = (EditText) rootView.findViewById(R.id.etEntfernungP2);
                    textEntferunung.setText(getArguments().getString("Entfernung") + "");
                }

            }
            if (getArguments().containsKey("PricingProject")) {
                //isFirstTime = false;
                if(getArguments().containsKey("gerateNo"))
                    GeratetypeId = getArguments().getString("gerateNo");
            }
        } else {
            Log.e("arguments null", "arguments null");
        }
        loadPricingCustomerOrderInfoFromPreference();
    }

    @Override
    public void bindEvents(View rootView) {
        imageViewCall.setOnClickListener(this);
        if (matecoPriceApplication.isCustomerLoaded(DataHelper.isCustomerLoaded, false)) {
            spLandUse.setEnabled(true);
        } else {
            spLandUse.setEnabled(false);
        }
        spGeraetetype.setOnItemSelectedListener(this);
        spKaNr.setOnItemSelectedListener(this);


        rowLandUseItems = db.getCountries();
        CountryAdapter countryAdapter = new CountryAdapter(getActivity(), rowLandUseItems, R.layout.list_item_spinner_country, language);
        spLandUse.setAdapter(countryAdapter);


        if (rowLandUseItems.size() > 0) {
            for (int i = 0; i < rowLandUseItems.size(); i++) {
                if (rowLandUseItems.get(i).getCountryId().equals("1")) {
                    spLandUse.setSelection(i + 1);
                }
            }
        }

        spLandUse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(rowLandUseItems.size() > 0){
                    countryNameId = Integer.parseInt(rowLandUseItems.get(position - 1).getCountryId());
                    countryName = rowLandUseItems.get(position - 1).getCountryName();
                }

                //  LevelGroupDesignation = lablesDevice.get(position).getDesignation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lvKaNrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                //view.setSelected(true);
            }
        });


        imgBtnContractSpotHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storePricingCustomerOrderInfo();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                if (matecoPriceApplication.isCustomerLoaded(DataHelper.isCustomerLoaded, false)) {
                    CustomerContactPersonFragment1 fragment4 = new CustomerContactPersonFragment1();
                    Bundle args3 = new Bundle();
                    args3.putString("Pricing2AnsprechPartner", "Pricing2AnsprechPartner");
                    args3.putString("kanr", kanr);
                    args3.putInt("branchId", branchId);
                    args3.putString("branchName", branchName);
                    args3.putString("contactPersonNo", contactPersonNo);
                    args3.putString("contactPersonName", contactPerson);
                    args3.putString("deviceType", deviceType);
                    args3.putString("equipmentIds", equipmentIds);
                    args3.putString("EquipmentJson", equipmentJson);
                    args3.putInt("rental", rental);
                    args3.putInt("rentalDays", rentalDays);
                    args3.putString("Besteller_Telefon", Besteller_Telefon);
                    args3.putString("Besteller_Email", Besteller_Email);
                    args3.putString("Besteller_Anrede", Besteller_Anrede);
                    args3.putString("Besteller_Mobil", Besteller_Mobil);

                    String EinsatzStrasse = textUserStreet.getText().toString().trim();
                    String EinsatzPLZ = textUseZipCode.getText().toString().trim();
                    String Einsatzort = textUserPlace.getText().toString().trim();
                    String Zusatz = textZusatz.getText().toString().trim();
                    String Entfernung = textEntferunung.getText().toString().trim();

                    args3.putString("EinsatzStrasse", EinsatzStrasse);
                    args3.putString("EinsatzPLZ", EinsatzPLZ);
                    args3.putString("Einsatzort", Einsatzort);
                    args3.putString("Zusatz", Zusatz);
                    args3.putString("Entfernung", Entfernung);

                    fragment4.setArguments(args3);
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.replace(R.id.content_frame, fragment4);
                    transaction.addToBackStack("Pricing Ansprech Partner");
                    transaction.commit();

                }
            }
        });


        /*new functionality add  */
        textUseZipCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // code to execute when EditText loses focus
                    String plz = textUseZipCode.getText().toString().trim();
                    getEntfernung(String.valueOf(branchId),plz);
                }
            }
        });
        /*new functionality add  */

        LayoutInflater inflater1 = getActivity().getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater1.inflate(R.layout.fragment_pricing2_kanrlistview_data_header_row, lvKaNrListView, false);
        lvKaNrListView.addHeaderView(header, null, false);
        linearHeaderTags = (LinearLayout) rootView.findViewById(R.id.linearHeaderTags);
        super.bindEvents(rootView);
    }

    private void setUpLanguage() {
        Log.e("set up language", "set up language");
        TextView txtContactNo, txtTransport, txtGeraetetype;
        Button btnApplicationInformation, btnOk, btnAbort;
        txtContactNo = (TextView) rootView.findViewById(R.id.txtContactNo);
        txtGeraetetype = (TextView) rootView.findViewById(R.id.txtGeraetetype);
        btnApplicationInformation = (Button) rootView.findViewById(R.id.btnApplicationInformation);
        TextView txtProject, txtLandUse, txtUseZipCode, txtOfUse, txtReadUse, txtAddition, txtContractSpot, textAnspMobile, txtTollKilometers;

        imgBtnProjectHelp = (ImageButton) rootView.findViewById(R.id.imgBtnProjectHelp);
        imgBtnProjectRemove = (ImageButton) rootView.findViewById(R.id.imgBtnProjectRemove);
        imageButtonCurrentLocation = (ImageButton) rootView.findViewById(R.id.imageButtonCurrentLocation);
        imageButtonMapLocation = (ImageButton) rootView.findViewById(R.id.imageButtonMapLocation);
        imgBtnProjectRemove.setOnClickListener(this);
        imageButtonCurrentLocation.setOnClickListener(this);
        imageButtonMapLocation.setOnClickListener(this);
        imgBtnProjectHelp.setOnClickListener(this);
        spLandUse = (Spinner) rootView.findViewById(R.id.spLandUse);
        txtProject = (TextView) rootView.findViewById(R.id.txtProject);
        txtLandUse = (TextView) rootView.findViewById(R.id.txtLandUse);
        txtUseZipCode = (TextView) rootView.findViewById(R.id.txtUseZipCode);
        txtOfUse = (TextView) rootView.findViewById(R.id.txtOfUse);
        txtReadUse = (TextView) rootView.findViewById(R.id.txtReadUse);
        txtAddition = (TextView) rootView.findViewById(R.id.txtAddition);
        txtContractSpot = (TextView) rootView.findViewById(R.id.txtContractSpot);
        textAnspMobile = (TextView) rootView.findViewById(R.id.textAnspMobile);
        txtTollKilometers = (TextView) rootView.findViewById(R.id.txtTollKilometers);

        imgBtnContractSpotHelp = (ImageButton) rootView.findViewById(R.id.imgBtnContractSpotHelp);


        txtGeraetetype.setText(language.getLabelDeviceType());
        txtContactNo.setText(language.getLabelContactNo());

        // btnApplicationInformation.setText(language.getLabelApplicationInformation());
        txtProject.setText(language.getLabelProject());
        txtLandUse.setText(language.getLabelLandUse());
        txtUseZipCode.setText(language.getLabelUseZipCode());
        txtOfUse.setText(language.getLabelOfUse());
        txtReadUse.setText(language.getLabelReadUse());
        txtAddition.setText(language.getLabelAddition());
        txtContractSpot.setText(language.getLabelContractSpot());
        textAnspMobile.setText(language.getLabelAnspMobile());
        txtTollKilometers.setText(language.getLabelTollKilometers());

        checkBoxSelbstfahrer.setText(language.getLabelSelbstbehalt());
        //txtTollKilometers.setText(language.getLabelTollKilometers());

        if (kanr.equals("0")) {
            loadSpinnerData();
        } else {
            loadKontaktDropDownData(kanr);
        }
    }

    /**
     * Function to load the spinner data from SQLite database
     */
    private void loadKontaktDropDownData(String KontaktId) {
        if (DataHelper.isNetworkAvailable(getActivity())) {
            BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult() {
                @Override
                public void OnAsynResult(String result) {
                    Log.e("KanR", result);
                    try {

                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            db.addKaNrData(new Pricing2KaNrData(Integer.parseInt(jsonObject.getString("KaNr")), jsonObject.getString("Name1")));
                        }
                        loadSpinnerData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            try {
                //String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.pricing2PriceKaNrCombo + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8") + "&Kontakt=" + KontaktId;
                String url = DataHelper.URL_PRICE_HELPER + "pricekanrcombo/token=" +
                        URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8") + "/Kontakt=" + KontaktId;
                Log.e("Url KontaktId", url);
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                asyncTask.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            loadSpinnerData();
        }
    }

    /**
     * Function to load the spinner data from SQLite database
     */
    private void loadSpinnerData() {
        rowKaNrItems.clear();
        // database handler

        // Spinner Drop down elements
        lablesKaNr = db.getPricing2KaNrData(kanr);
        if (lablesKaNr.size() == 0) {
            lablesKaNr.add(new Pricing2KaNrData(0, "Standard Price"));
            spGeraetetype.setEnabled(false);
        }
       /* else if(lablesKaNr.size() > 1)
        {
            Log.e("lablesKaNr",""+lablesKaNr.size());
            spKaNr.setSelection(1);

        }*/
        Log.e("on spinner data", "on spinner data");
        Pricing2KaNrDataAdapter kaNrAdapter = new Pricing2KaNrDataAdapter(getActivity(), lablesKaNr);
        spKaNr.setAdapter(kaNrAdapter);
        if (lablesKaNr.size() > 1) {
            Log.e("lablesKaNr", "" + lablesKaNr.size());
            spKaNr.setSelection(1);
        }

        kaNrAdapter.notifyDataSetChanged();

        //kaNrAdapter.notifyDataSetChanged();
        // loadSpinnerGratetypeData();
    }

    public String getEntfernung(String mandant, String plz)
    {
        String result="";
        if (DataHelper.isNetworkAvailable(getActivity()))
        {
            BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult() {
                @Override
                public void OnAsynResult(String result) {

                    try {
                        String entfernungId = result.replace("\"","").trim();
                        Log.e("result of getEntfernung", result);
                        //textEntferunung.setText(""+entfernungId.trim());
                        PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo,
                                new Gson().toJson(new PricingCustomerOrderBasicInfo()));
                        model.setEnferrung(entfernungId);
                        textEntferunung.setText(model.getEnferrung());
                        matecoPriceApplication.saveData(DataHelper.PricingCustomerBasicOrderInfo, new Gson().toJson(model));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            try {
                if(TextUtils.isEmpty(plz) || plz == null){
                    plz="0";
                }
                /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME +
                        DataHelper.pricing2PricegGetEntfernung + "?token=" +
                        URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8") + "&mandant=" +
                        mandant+ "&plz=" + plz;*/
                String url = DataHelper.URL_PRICE_HELPER + "getEntfernung/token=" +
                        URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8") + "/mandant=" +
                        mandant+ "/plz=" + plz;
                Log.e("Url getEntfernung", url);
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                asyncTask.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

        }
        return result;

    }
   /* private void loadSpinnerGratetypeData() {

        listDeviceType.clear();
        Log.e("Blind...deviceType","test"+deviceType+"test");

        listDeviceType.addAll(db.getDeviceType(deviceType.trim()));

        deviceTypeAdapter = new SiteInspectionDeviceTypeAdapter(getActivity(),listDeviceType,language);
        spGeraetetype.setAdapter(deviceTypeAdapter);
        deviceTypeAdapter.notifyDataSetChanged();

    }*/

    /**
     * Function to load the spinner data from SQLite database
     */
    private void loadKontaktListViewDataStandardPrice(int Branch, final String DeviceType, String GeratetypeId, int Einheit, String Assesories) {
        price = 0.0;
        gesAminitiesPrice = 0.0;
        gesAm = 0.0;
        if (DataHelper.isNetworkAvailable(getActivity())) {
            kaNrListViewItemsAdapter = new Pricing2KaNrListViewDataAdapter(getActivity(), rowKaNrListViewItems);
            lvKaNrListView.setAdapter(kaNrListViewItemsAdapter);
            BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult() {
                @Override
                public void OnAsynResult(String result) {
                    price = 0.0;
                    gesAminitiesPrice = 0.0;
                    gesAm = 0.0;
                    Log.e("KanR", result);
                    rowKaNrListViewItems.clear();
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        Log.e("array size standard pri", jsonArray.length() + "");
                        for (int i = 0; i < jsonArray.length(); i = i + 2) {
                            Log.e(" 1111111 "," in for loooop check i value : "+i);
                            Log.e("i", i + "");
                            Pricing2KaNrListViewData kaNrListView = new Pricing2KaNrListViewData();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            kaNrListView.setHoehengruppe(jsonObject.getString("Hoehengruppe"));
                            kaNrListView.setListenpreis(Double.parseDouble(jsonObject.getString("Listenpreis")));
                            kaNrListView.setSatzart(jsonObject.getString("Satzart"));
                            kaNrListView.setSort(jsonObject.getString("Sort"));
                            JSONArray itemsDetails = jsonObject.getJSONArray("tageslist");
                            listOfTagName = new ArrayList<>();
                            listOfRealTag = new ArrayList<>();
                            listOfRValue = new ArrayList<>();
                            listOfMValue = new ArrayList<>();
                            Log.e(" 2222222 "," in for loooop check i value : "+itemsDetails.length());
                            for (int j = 0; j < itemsDetails.length(); j++) {
                                JSONObject object = itemsDetails.getJSONObject(j);
                                listOfTagName.add(object.getString("Key"));
                                listOfRealTag.add(db.getStaffelObjectFromStaffel(Integer.parseInt(listOfTagName.get(j))));
                                listOfRValue.add(Double.parseDouble(object.getString("Value")));
                                listOfMValue.add(Double.parseDouble(jsonArray.getJSONObject(i + 1).getJSONArray("tageslist").getJSONObject(j).getString("Value")));

                                Log.e(" check for ^^^^ "," in for loop of itemDetails : "+" key object : "+object.getString("Key")+" list of real tag size : "+listOfRealTag.size());
                            }
                            for (int k =0;k<listOfTagName.size();k++){
                                Log.e(" ######## "," list of tagname value : "+listOfTagName.get(k));
                            }
                            for (int k =0;k<listOfRealTag.size();k++){
                                Log.e(" ######## "," list of listOfRealTag value bezeichnung: "+listOfRealTag.get(k).getBezeichnung()+" abeinheit: "+
                                        listOfRealTag.get(k).getAbEinheit()+" aktiv: "+
                                        listOfRealTag.get(k).getAktiv()+ " angebo: "+
                                        listOfRealTag.get(k).getAngebotstext()+" einheit: "+
                                        listOfRealTag.get(k).getEinheit()+" sprache: "+
                                        listOfRealTag.get(k).getSprache()+" staffel: "+
                                        listOfRealTag.get(k).getStaffel()+" standardstaffel: "+
                                        listOfRealTag.get(k).getStandardStaffel()+" : "
                                        );
                            }
                            kaNrListView.setListOfMPrice(listOfMValue);
                            kaNrListView.setListOfRPrice(listOfRValue);
                            //kaNrListView.setKey(listOfTagName);
                            kaNrListView.setKey(listOfRealTag);
                            rowKaNrListViewItems.add(kaNrListView);
                            Log.e(" size arraylist "," in for loooop check i value : "+rowKaNrListViewItems.size());
                            kaNrListViewItemsAdapter.notifyDataSetChanged();
                        }
                        //setListViewHeightBasedOnChildren(lvKaNrListView);
                        /// removing all aview from header
                        linearHeaderTags.removeAllViews();

                        TextView txtPricing2KaNrHoehengruppeDataHeaderRow = (TextView) rootView.findViewById(R.id.txtPricing2KaNrHoehengruppeDataHeaderRow);
                        txtPricing2KaNrHoehengruppeDataHeaderRow.setVisibility(View.GONE);
                        TextView txtPricing2KaNrHeightGroupDataHeaderRow = (TextView) rootView.findViewById(R.id.txtPricing2KaNrHeightGroupDataHeaderRow);
                        TextView txtPricing2KaNrListPriceDataHeaderRow = (TextView) rootView.findViewById(R.id.txtPricing2KaNrListPriceDataHeaderRow);
                        TextView txtPricing2KaNrSortDataHeaderRow = (TextView) rootView.findViewById(R.id.txtPricing2KaNrSortDataHeaderRow);
                        txtPricing2KaNrSortDataHeaderRow.setVisibility(View.GONE);

                        txtPricing2KaNrHeightGroupDataHeaderRow.setText(language.getLabelLevelGroup());
                        txtPricing2KaNrListPriceDataHeaderRow.setText("Listenpreis");
                        //txtPricing2KaNrSortDataHeaderRow.setText("Satzart");
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
                        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        //layoutParams.setMargins(0,0,5,0);
                        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        Log.e("jsonarray 0 standard", jsonArray.length() + "");
                        if (jsonArray.length() > 0) {
                            //for (int i = 0; i < jsonArray.getJSONObject(0).getJSONArray("tages").length(); i++)
                            for (int i = 0; i < listOfRealTag.size(); i++)
                            {
                                Log.e(""," list of real tag value : "+listOfRealTag.get(i).getBezeichnung());
                                LinearLayout llWholeTag = new LinearLayout(getActivity());
                                llWholeTag.setLayoutParams(layoutParams);
                                llWholeTag.setOrientation(LinearLayout.VERTICAL);
                                LinearLayout llTag = new LinearLayout(getActivity());
                                llTag.setLayoutParams(layoutParams1);
                                llTag.setGravity(Gravity.CENTER);
                               // llTag.setBackgroundColor(Color.parseColor("#2EFE64"));
                                llTag.setOrientation(LinearLayout.HORIZONTAL);
                                TextView textViewTag = new TextView(getActivity());
                                textViewTag.setLayoutParams(layoutParams);
                                //textViewTag.setEms(7);
                                textViewTag.setGravity(Gravity.CENTER);
                                TagName = new ArrayList<>();
                                //textViewTag.setText(jsonArray.getJSONObject(0).getJSONArray("tages").getJSONObject(i).getString("Key"));
                                textViewTag.setText(listOfRealTag.get(i).getBezeichnung());
                                llTag.addView(textViewTag);
                                llWholeTag.addView(llTag);
                                LinearLayout llWholeTagSub = new LinearLayout(getActivity());
                                llWholeTagSub.setLayoutParams(layoutParams1);
                                llWholeTagSub.setGravity(Gravity.CENTER);
                                llWholeTagSub.setOrientation(LinearLayout.HORIZONTAL);
                                TextView textView = new TextView(getActivity());
                                textView.setLayoutParams(layoutParams);
                                textView.setGravity(Gravity.CENTER);
                                //textView.setEms(3);
                                textView.setText("R");
                                TextView textView1 = new TextView(getActivity());
                                textView1.setLayoutParams(layoutParams);
                                textView1.setGravity(Gravity.CENTER);
                                //textView1.setEms(3);
                                textView1.setText("M");
                                llWholeTagSub.addView(textView);
                                llWholeTagSub.addView(textView1);
                                llWholeTag.addView(llWholeTagSub);
                                linearHeaderTags.addView(llWholeTag);
                            }


                            TagName.clear();
                            for (int i = 0; i < listOfTagName.size(); i++) {
                                TagName.add("" + listOfTagName.get(i).replace("Tage", ""));
                            }
                            int column = -1;


                            for(int i = 0; i < listOfRealTag.size(); i++)
                            { Log.e(" in for loop "," list of real tag value for selection : "+listOfRealTag.get(i).getAbEinheit() + " : "+
                                    listOfRealTag.get(i).getBisEinheit());
                                if(GlobalMethods.checkForNotNull(listOfRealTag.get(i).getAbEinheit())
                                        && GlobalMethods.checkForNotNull(listOfRealTag.get(i).getBisEinheit()))
                                {
                                    if(Integer.parseInt(listOfRealTag.get(i).getAbEinheit()) == rentalDays || (Integer.parseInt(listOfRealTag.get(i).getBisEinheit()) == rentalDays))
                                    {
                                        column = i;
                                        break;
                                    }
                                    else if (rental == 6){
                                        column = i;
                                        break;
                                    }
                                    else if(rentalDays > Integer.parseInt(listOfRealTag.get(i).getAbEinheit()) && rentalDays < Integer.parseInt(listOfRealTag.get(i).getBisEinheit()))
                                    {
                                        column = i;
                                        break;
                                    }
                                }
                                /// 11 = pauschal
                                else if(rental==11)
                                {
                                    Log.e(" in else if "," in rental conctioin : "+rental);
                                    column = i;
                                    break;
                                }
                                else if(rental==14){
                                    column = i;
                                    break;
                                }

                            }
                            if (column != -1) {
                                kaNrListViewItemsAdapter.setSelection(column);
                                kaNrListViewItemsAdapter.notifyDataSetChanged();
                            }
                            for (int i = 0; i < rowKaNrListViewItems.size(); i++) {
                                if(rental ==  6){
                                    kaNrListViewItemsAdapter.setSelectionOfRow(i);
                                }
                                if (deviceType.equals(rowKaNrListViewItems.get(i).getHoehengruppe().toString().trim())) {
                                    kaNrListViewItemsAdapter.setSelectionOfRow(i);
                                    kaNrListViewItemsAdapter.notifyDataSetChanged();
                                    if (column != -1) {
                                        price = rowKaNrListViewItems.get(i).getListOfRPrice().get(column);
                                    }
                                    //break;
                                }
                                if ((rowKaNrListViewItems.get(i).getSort().toLowerCase()).contains(deviceType.toLowerCase().toString())) {
                                    if (column != -1) {
                                        double gesAmt = rowKaNrListViewItems.get(i).getListOfRPrice().get(column);
                                        gesAminitiesPrice = (gesAminitiesPrice + gesAmt);
                                    }
                                } else {
                                    Log.e("column", column + " " + rowKaNrListViewItems.get(i).getSatzart());
                                }
                            }
                            //Log.e("column", column + " " + TagName.get(column).toString() + " price: " + price);
                        }
                        kaNrListViewItemsAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            try {
                /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME
                        + DataHelper.pricing2PriceCockpitPreise
                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "&Branch=" + Branch
                        + "&DeviceType=" + DeviceType.trim()
                        + "&GeratetypID=" + GeratetypeId
                        + "&Einheit=" + Einheit
                        + "&Assesories=" + Assesories;*/
                String url = DataHelper.URL_PRICE_HELPER
                        + "pricecockpitpreise/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/Branch=" + Branch
                        + "/DeviceType=" + DeviceType.trim()
                        + "/Einheit=" + Einheit
                        + "/Assesories=" + Assesories
                        + "/GeratetypID=" + GeratetypeId;
                Log.e("Url KontaktId", url);
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                asyncTask.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            showLongToast(language.getMessageNetworkNotAvailable());
            Log.e(" all variable "," standard offline called : "+" branch : "+Branch + " device type " + DeviceType + " Einheit : " + Einheit + " assesories : " + Assesories);
            rowOfflineStandardPriceListViewItems.addAll(db.getPricing2StandardPriceData(Branch, DeviceType, Einheit, Assesories));
            Log.e(" arraylist size : ", " standard offline called : "+rowOfflineStandardPriceListViewItems.size() + "");
            offlineStandardPriceListViewItemsAdapter = new Pricing2OfflineKaNrStandardPriceListViewDataAdapter(getActivity(), rowOfflineStandardPriceListViewItems);
            lvKaNrListView.setAdapter(offlineStandardPriceListViewItemsAdapter);

            // our <code></code>
            TextView txtPricing2KaNrHoehengruppeDataHeaderRow = (TextView) rootView.findViewById(R.id.txtPricing2KaNrHoehengruppeDataHeaderRow);
            txtPricing2KaNrHoehengruppeDataHeaderRow.setVisibility(View.GONE);
            TextView txtPricing2KaNrHeightGroupDataHeaderRow = (TextView) rootView.findViewById(R.id.txtPricing2KaNrHeightGroupDataHeaderRow);
            TextView txtPricing2KaNrListPriceDataHeaderRow = (TextView) rootView.findViewById(R.id.txtPricing2KaNrListPriceDataHeaderRow);
            TextView txtPricing2KaNrSortDataHeaderRow = (TextView) rootView.findViewById(R.id.txtPricing2KaNrSortDataHeaderRow);
            txtPricing2KaNrSortDataHeaderRow.setVisibility(View.GONE);

            //txtPricing2KaNrHeightGroupDataHeaderRow.setText(language.getLabelLevelGroup());
            //txtPricing2KaNrListPriceDataHeaderRow.setText("Listenpreis");
            //txtPricing2KaNrSortDataHeaderRow.setText("Satzart");
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1f);
            //layoutParams.setMargins(0,0,5,0);
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            listOfRealTag = new ArrayList<>();
            listOfRValue = new ArrayList<>();
            listOfMValue = new ArrayList<>();
            //Log.e(" 2222222 "," in for loooop check i value : "+itemsDetails.length());
            for (int j = 0; j < rowOfflineStandardPriceListViewItems.size(); j++) {


                Log.e(" in for loop : "," get id from offline standard price :" +rowOfflineStandardPriceListViewItems.get(j).getHoehenhauptgruppeID());
                listOfRealTag.add(db.getStaffelObjectFromStaffel(rowOfflineStandardPriceListViewItems.get(j).getHoehenhauptgruppeID()));
                //listOfRValue.add(Double.parseDouble(object.getString("Value")));
                //listOfMValue.add(Double.parseDouble(jsonArray.getJSONObject(i + 1).getJSONArray("tages").getJSONObject(j).getString("Value")));
            }
            /*for (int i = 0; i < listOfRealTag.size(); i++)
            {
                Log.e(""," list of real tag value : "+listOfRealTag.get(i).getBezeichnung());
                LinearLayout llWholeTag = new LinearLayout(getActivity());
                llWholeTag.setLayoutParams(layoutParams);
                llWholeTag.setOrientation(LinearLayout.VERTICAL);
                LinearLayout llTag = new LinearLayout(getActivity());
                llTag.setLayoutParams(layoutParams1);
                llTag.setGravity(Gravity.CENTER);
                // llTag.setBackgroundColor(Color.parseColor("#2EFE64"));
                llTag.setOrientation(LinearLayout.HORIZONTAL);
                TextView textViewTag = new TextView(getActivity());
                textViewTag.setLayoutParams(layoutParams);
                //textViewTag.setEms(7);
                textViewTag.setGravity(Gravity.CENTER);
                TagName = new ArrayList<>();
                //textViewTag.setText(jsonArray.getJSONObject(0).getJSONArray("tages").getJSONObject(i).getString("Key"));
                textViewTag.setText(listOfRealTag.get(i).getBezeichnung());
                llTag.addView(textViewTag);
                llWholeTag.addView(llTag);
                LinearLayout llWholeTagSub = new LinearLayout(getActivity());
                llWholeTagSub.setLayoutParams(layoutParams1);
                llWholeTagSub.setGravity(Gravity.CENTER);
                llWholeTagSub.setOrientation(LinearLayout.HORIZONTAL);
                TextView textView = new TextView(getActivity());
                textView.setLayoutParams(layoutParams);
                textView.setGravity(Gravity.CENTER);
                //textView.setEms(3);
                textView.setText("R");
                TextView textView1 = new TextView(getActivity());
                textView1.setLayoutParams(layoutParams);
                textView1.setGravity(Gravity.CENTER);
                //textView1.setEms(3);
                textView1.setText("M");
                llWholeTagSub.addView(textView);
                llWholeTagSub.addView(textView1);
                llWholeTag.addView(llWholeTagSub);
                linearHeaderTags.addView(llWholeTag);
            }*/

            // our code
            offlineStandardPriceListViewItemsAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Function to load the spinner data from SQLite database
     */
    private void loadKontaktListViewDataLessThen1800(int Branch, final String DeviceType, int Einheit) {
        price = 0.0;
        gesAminitiesPrice = 0.0;
        gesAm = 0.0;
        if (DataHelper.isNetworkAvailable(getActivity())) {
            kaNrListViewLessThenDataItemsAdapter = new Pricing2KaNrListViewLessThenDataAdapter(getActivity(), rowKaNrListViewLessThenItems);
            lvKaNrListView.setAdapter(kaNrListViewLessThenDataItemsAdapter);
            BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult() {
                @Override
                public void OnAsynResult(String result) {
                    price = 0.0;
                    gesAminitiesPrice = 0.0;
                    gesAm = 0.0;
                    rowKaNrListViewLessThenItems.clear();
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        Log.e("json array length", jsonArray.length() + "");
                        //for (int i = 0; i < jsonArray.length(); i = i + 2)
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Pricing2KaNrListViewLessThen1800Data kaNrListViewLessThen = new Pricing2KaNrListViewLessThen1800Data();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            kaNrListViewLessThen.setArbeitshoehe((jsonObject.getString("Arbeitshoehe")));
                            kaNrListViewLessThen.setGerätetyp(jsonObject.getString("Geratetyp"));
                            kaNrListViewLessThen.setHoehengruppe((jsonObject.getString("Hoehengruppe")));
                            kaNrListViewLessThen.setListenpreis(Double.parseDouble(jsonObject.getString("Listenpreis")));
                            JSONArray itemsDetails = jsonObject.getJSONArray("tages");
                            listOfTagName = new ArrayList<>();
                            listOfRValue = new ArrayList<>();
                            listOfRealTag = new ArrayList<>();
                            for (int j = 0; j < itemsDetails.length(); j++) {
                                JSONObject object = itemsDetails.getJSONObject(j);
                                listOfTagName.add(object.getString("Key"));
                                listOfRealTag.add(db.getStaffelObjectFromStaffel(Integer.parseInt(listOfTagName.get(j))));
                                listOfRValue.add(Double.parseDouble(object.getString("Value")));
                            }
                            kaNrListViewLessThen.setListPrice(listOfRValue);
                            kaNrListViewLessThen.setKey(listOfRealTag);
                            rowKaNrListViewLessThenItems.add(kaNrListViewLessThen);
                            kaNrListViewLessThenDataItemsAdapter.notifyDataSetChanged();
                        }
                        //setListViewHeightBasedOnChildren(lvKaNrListView);
                        linearHeaderTags.removeAllViews();

                        TextView txtPricing2KaNrHoehengruppeDataHeaderRow = (TextView) rootView.findViewById(R.id.txtPricing2KaNrHoehengruppeDataHeaderRow);
                        txtPricing2KaNrHoehengruppeDataHeaderRow.setVisibility(View.VISIBLE);
                        txtPricing2KaNrHoehengruppeDataHeaderRow.setText(language.getLabelLevelGroup());
                        TextView txtPricing2KaNrHeightGroupDataHeaderRow = (TextView) rootView.findViewById(R.id.txtPricing2KaNrHeightGroupDataHeaderRow);
                        txtPricing2KaNrHeightGroupDataHeaderRow.setText("Arbeitshoehe");

                        TextView txtPricing2KaNrListPriceDataHeaderRow = (TextView) rootView.findViewById(R.id.txtPricing2KaNrListPriceDataHeaderRow);
                        txtPricing2KaNrListPriceDataHeaderRow.setText("Gerätetyp");

                        TextView txtPricing2KaNrSortDataHeaderRow = (TextView) rootView.findViewById(R.id.txtPricing2KaNrSortDataHeaderRow);
                        txtPricing2KaNrSortDataHeaderRow.setVisibility(View.VISIBLE);
                        txtPricing2KaNrSortDataHeaderRow.setText("Listenpreis");


                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < listOfRealTag.size(); i++) {
                                LinearLayout llWholeTag = new LinearLayout(getActivity());
                                llWholeTag.setLayoutParams(layoutParams);
                                llWholeTag.setOrientation(LinearLayout.VERTICAL);
                                LinearLayout llTag = new LinearLayout(getActivity());
                                llTag.setLayoutParams(layoutParams1);
                                llTag.setGravity(Gravity.CENTER);
                                llTag.setOrientation(LinearLayout.HORIZONTAL);
                                TextView textViewTag = new TextView(getActivity());
                                textViewTag.setLayoutParams(layoutParams);
                                textViewTag.setGravity(Gravity.CENTER);
                                TagName = new ArrayList<>();
                                TagName.add(jsonArray.getJSONObject(0).getJSONArray("tages").getJSONObject(i).getString("Key"));
                                //textViewTag.setText(jsonArray.getJSONObject(0).getJSONArray("tages").getJSONObject(i).getString("Key"));
                                textViewTag.setText(listOfRealTag.get(i).getBezeichnung());
                                llTag.addView(textViewTag);
                                llWholeTag.addView(llTag);
                                linearHeaderTags.addView(llWholeTag);
                            }
                            TagName.clear();
                            for (int i = 0; i < listOfTagName.size(); i++) {
                                TagName.add(listOfTagName.get(i).replace("Tage", "").replace("Tag", ""));
                            }
                            int column = -1;
                            Log.e("Tage name.size.. <1800", TagName.size() + "");
                            for(int i = 0; i < listOfRealTag.size(); i++)
                            {
                                if((Integer.parseInt(listOfRealTag.get(i).getAbEinheit()) == rentalDays) || (Integer.parseInt(listOfRealTag.get(i).getBisEinheit()) == rentalDays))
                                {
                                    column = i;
                                    break;
                                }
                                else if(rentalDays > Integer.parseInt(listOfRealTag.get(i).getAbEinheit()) && rentalDays < Integer.parseInt(listOfRealTag.get(i).getBisEinheit()))
                                {
                                    column = i;
                                    break;
                                }
                                else if(listOfRealTag.size()<=1){
                                    column = i;
                                    break;
                                }
                            }
                           /* for (int i = 0; i < TagName.size(); i++)
                            {
                                Log.e("Tage name...", TagName.get(i).toString());
                                if (TagName.get(i).toString().contains("-")) {
                                    String[] test = TagName.get(i).toString().trim().split("-", 2);
                                    int a = Integer.parseInt(test[0].trim());
                                    int b = Integer.parseInt(test[1].trim());
                                    Log.e("inTage 4..", a + " " + b + rentalDays);
                                    for (int j = a; j <= b; j++) {
//                                    if (j == rental) {
                                        if (j == rentalDays) {
                                            column = i;
                                            break;
                                        }
                                    }
                                } else {
                                    String test = TagName.get(i).toString().trim().replace("ab", "");
                                    int tes = Integer.parseInt(test.trim());
                                    if (tes <= rentalDays) {
                                        column = i;
                                    }
                                }
                                Log.e("Column...", "" + column);
                            }*/
//                            for (int i = 0; i < TagName.size(); i++)
//                            {
//                                if (TagName.get(i).toString().contains("Stunden"))
//                                {
//
//                                    String bis = TagName.get(i).toString().replace("Stunden", "");
//
//                                    if (bis.contains("bis")) {
//
//                                        String bis1 = bis.replace("bis", "").trim();
//                                        int day = Integer.parseInt(bis1.trim());
//                                        for (int j = 1; j <= day; j++) {
//                                            if (j == rentalDays) {
//                                                column = i;
//                                            }
//                                        }
//
//                                    } else if (bis.contains("über"))
//                                    {
//
//                                        String bis1 = bis.replace("über", "").trim();
//                                        int day = Integer.parseInt(bis1.trim());
//                                        if (day < rentalDays) {
//                                            column = i;
//                                        }
//                                    }
//                                }
//                                else if (TagName.get(i).toString().contains("bis"))
//                                {
//
//                                    String bis = TagName.get(i).toString();
//
//                                    if (bis.contains("bis"))
//                                    {
//
//
//                                        String tag13 = TagName.get(i).toString().replace("bis", "");
//                                        String tagfinal13="";
//                                        if(tag13.contains("Std"))
//                                        {
//                                            tagfinal13 = tag13.replace("Std", "");
//                                            Log.e("tag contain bis std standard 0", "" + tagfinal13);
//                                        }
//                                        else
//                                        {
//                                            tagfinal13 = tag13;
//                                            Log.e("tag contain bis standard 0", "" + tagfinal13);
//                                        }
//
//
//                                        String bis1 = tagfinal13;
//                                        //String bis1 = bis.replace("bis", "").trim();
//                                        int day = Integer.parseInt(bis1.trim());
//                                        for (int j = 1; j <= day; j++) {
//                                            if (j == rentalDays) {
//                                                column = i;
//                                            }
//                                        }
//
//                                    } else if (bis.contains("über"))
//                                    {
//                                        String tag13 = TagName.get(i).toString().replace("über", "");
//                                        String tagfinal13="";
//                                        if(tag13.contains("Std"))
//                                        {
//                                            tagfinal13 = tag13.replace("Std", "");
//                                            Log.e("tag contain  1800> über", "" + tagfinal13);
//                                        }
//                                        else
//                                        {
//                                            tagfinal13 = tag13;
//                                            Log.e("tag contain  1800> über", "" + tagfinal13);
//                                        }
//
//
//                                        Log.e("tagfinal13 1800> über", "" + tagfinal13);
//                                        String bis1 = tagfinal13;
//                                        //String bis1 = bis.replace("über", "").trim();
//                                        int day = Integer.parseInt(bis1.trim());
//                                        if (day < rentalDays) {
//                                            column = i;
//                                        }
//                                    }
//                                }
//                                else {
//                                    Log.e("tag name", TagName.get(i).toString() + " " + rentalDays);
//                                    if (TagName.get(i).toString().contains("-")) {
//                                        String[] test = TagName.get(i).toString().trim().split("-", 2);
//                                        int a = Integer.parseInt(test[0].trim());
//                                        int b = Integer.parseInt(test[1].trim());
//                                        for (int j = a; j <= b; j++) {
//                                            if (j == rentalDays) {
//                                                column = i;
//                                            }
//                                        }
//                                    } else {
//                                        /*String test = TagName.get(i).toString().trim().replace("ab", "");
//                                        int tes = Integer.parseInt(test.trim());*/
//                                        // String test = TagName.get(i).toString().trim().replace("ab", "");
//                                        String test = "";
//                                        String test1 = "";
//                                        if (TagName.get(i).toString().trim().contains("ab"))
//                                        {
//                                            test = TagName.get(i).toString().trim().replace("ab", "");
//                                            test1 = test;
//                                            Log.e("Standard price 0 test ab ", "" + test1);
//                                        } else {
//                                            test = TagName.get(i).toString().trim().replace("über", "");
//                                            test1 = test.replace("Std", "");
//                                            Log.e("Standard price 0 test1 ab über", "" + test1);
//                                        }
//                                        int tes = Integer.parseInt(test1.trim());
//                                        Log.e("tes name", tes + " " + rentalDays);
//                                        if (tes <= rentalDays) {
//                                            column = i;
//                                        }
//                                    }
//                                }
//                            }
                            if (column != -1) {
                                kaNrListViewLessThenDataItemsAdapter.setSelection(column);
                                kaNrListViewLessThenDataItemsAdapter.notifyDataSetChanged();
                            }
                            for (int i = 0; i < rowKaNrListViewLessThenItems.size(); i++) {
                                // if (deviceType.equals(rowKaNrListViewLessThenItems.get(i).getArbeitshoehe()))
                                if (deviceType.equals(rowKaNrListViewLessThenItems.get(i).getHoehengruppe())) {
                                    kaNrListViewLessThenDataItemsAdapter.setSelectionOfRow(i);
                                    kaNrListViewLessThenDataItemsAdapter.notifyDataSetChanged();
                                    if (column != -1) {
                                        price = rowKaNrListViewLessThenItems.get(i).getListPrice().get(column);
                                    }
                                    // break;
                                }
                                if ((rowKaNrListViewLessThenItems.get(i).getHoehengruppe().toLowerCase()).contains(deviceType.toLowerCase().toString())) {
                                    if (column != -1) {
                                        double gesAmt = rowKaNrListViewLessThenItems.get(i).getListPrice().get(column);
                                        gesAminitiesPrice = (gesAminitiesPrice + gesAmt);
                                    }
                                } else {
                                    Log.e("column", column + " " + rowKaNrListViewLessThenItems.get(i).getGerätetyp());
                                }
                            }
//                            Log.e("column", column + " " + TagName.get(column).toString() + " price: " + price);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            try {
                /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.pricing2PriceCockpitPreiseLessThen
                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "&Kanr=" + Branch
                        + "&DeviceType=" + DeviceType.trim()
                        + "&Einheit=" + Einheit;*/
                String url = DataHelper.URL_PRICE_HELPER
                        + "pricecockpitpreiseless/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/Kanr=" + Branch
                        + "/DeviceType=" + DeviceType.trim()
                        + "/Einheit=" + Einheit;
                Log.e("Url KontaktId", url);
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                asyncTask.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showLongToast(language.getMessageNetworkNotAvailable());
        }
    }

    /**
     * Function to load the spinner data from SQLite database
     */
    private void loadKontaktListViewDataMoreThen1800(int Branch, final String DeviceType, String GerateType, int Einheit, String Assesories) {
        // database handler
        price = 0.0;
        gesAminitiesPrice = 0.0;
        gesAm = 0.0;
        if (DataHelper.isNetworkAvailable(getActivity())) {
            kaNrListViewMoreThenDataItemsAdapter = new Pricing2KaNrListViewMoreThenDataAdapter(getActivity(), rowKaNrListViewMoreThenItems);
            lvKaNrListView.setAdapter(kaNrListViewMoreThenDataItemsAdapter);

            BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult() {
                @Override
                public void OnAsynResult(String result) {
                    price = 0.0;
                    gesAminitiesPrice = 0.0;
                    gesAm = 0.0;
                    rowKaNrListViewMoreThenItems.clear();
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Pricing2KaNrListViewMoreThen1800Data kaNrListViewMoreThen = new Pricing2KaNrListViewMoreThen1800Data();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            kaNrListViewMoreThen.setHoehengruppe(jsonObject.getString("Hoehengruppe"));
                            //kaNrListViewMoreThen.setHoehengruppe(jsonObject.getString(language.getLabelLevelGroup()));
                            kaNrListViewMoreThen.setListenpreis(Double.parseDouble(jsonObject.getString("Listenpreis")));
                            kaNrListViewMoreThen.setSort(jsonObject.getString("Sort"));
                            JSONArray itemsDetails = jsonObject.getJSONArray("tages");
                            listOfTagName = new ArrayList<>();
                            listOfRValue = new ArrayList<>();
                            listOfRealTag = new ArrayList<>();
                            for (int j = 0; j < itemsDetails.length(); j++) {
                                JSONObject object = itemsDetails.getJSONObject(j);

                                listOfTagName.add(object.getString("Key"));
                                listOfRealTag.add(db.getStaffelObjectFromStaffel(Integer.parseInt(listOfTagName.get(j))));
                                listOfRValue.add(Double.parseDouble(object.getString("Value")));
                            }
                            kaNrListViewMoreThen.setListPrice(listOfRValue);
                            //kaNrListViewMoreThen.setKey(listOfTagName);
                            kaNrListViewMoreThen.setKey(listOfRealTag);
                            rowKaNrListViewMoreThenItems.add(kaNrListViewMoreThen);
                            kaNrListViewMoreThenDataItemsAdapter.notifyDataSetChanged();
                        }
                        //setListViewHeightBasedOnChildren(lvKaNrListView);
                        linearHeaderTags.removeAllViews();
                        TextView txtPricing2KaNrHeightGroupDataHeaderRow = (TextView) rootView.findViewById(R.id.txtPricing2KaNrHeightGroupDataHeaderRow);
                        TextView txtPricing2KaNrListPriceDataHeaderRow = (TextView) rootView.findViewById(R.id.txtPricing2KaNrListPriceDataHeaderRow);
                        TextView txtPricing2KaNrSortDataHeaderRow = (TextView) rootView.findViewById(R.id.txtPricing2KaNrSortDataHeaderRow);
                        txtPricing2KaNrHeightGroupDataHeaderRow.setText(language.getLabelLevelGroup());
                        txtPricing2KaNrListPriceDataHeaderRow.setText("Listenpreis");
                        //txtPricing2KaNrSortDataHeaderRow.setText("Sort");
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < listOfRealTag.size(); i++) {
                                LinearLayout llWholeTag = new LinearLayout(getActivity());
                                llWholeTag.setLayoutParams(layoutParams);
                                llWholeTag.setOrientation(LinearLayout.VERTICAL);
                                LinearLayout llTag = new LinearLayout(getActivity());
                                llTag.setLayoutParams(layoutParams1);
                                llTag.setGravity(Gravity.CENTER);
                                llTag.setOrientation(LinearLayout.HORIZONTAL);
                                TextView textViewTag = new TextView(getActivity());
                                textViewTag.setLayoutParams(layoutParams);
                                textViewTag.setGravity(Gravity.CENTER);
                                TagName = new ArrayList<>();
                                TagName.add(jsonArray.getJSONObject(0).getJSONArray("tages").getJSONObject(i).getString("Key"));
                                //textViewTag.setText(jsonArray.getJSONObject(0).getJSONArray("tages").getJSONObject(i).getString("Key"));
                                textViewTag.setText(listOfRealTag.get(i).getBezeichnung());
                                llTag.addView(textViewTag);
                                llWholeTag.addView(llTag);
                                linearHeaderTags.addView(llWholeTag);
                            }
                            TagName.clear();
                            for (int i = 0; i < listOfTagName.size(); i++) {
                            /*if(listOfTagName.get(i).equals("Tage"))
                            {
                                TagName.add(listOfTagName.get(i).replace("Tage", ""));
                                Log.e("tagname " + i, TagName.get(i));
                            }
                            else
                            {
                                TagName.add(listOfTagName.get(i).replace("bis", ""));
                                Log.e("tagname " + i, TagName.get(i));
                            }*/
                                TagName.add(listOfTagName.get(i).replace("Tage", ""));
                            }

                            int column = -1;
                            for(int i = 0; i < listOfRealTag.size(); i++)
                            {
                                if((Integer.parseInt(listOfRealTag.get(i).getAbEinheit()) == rentalDays) || (Integer.parseInt(listOfRealTag.get(i).getBisEinheit()) == rentalDays))
                                {
                                    column = i;
                                    break;
                                }
                                else if(rentalDays > Integer.parseInt(listOfRealTag.get(i).getAbEinheit()) && rentalDays < Integer.parseInt(listOfRealTag.get(i).getBisEinheit()))
                                {
                                    column = i;
                                    break;
                                }
                            }
                           /* for (int i = 0; i < TagName.size(); i++) {
                                if (TagName.get(i).toString().contains("-")) {
                                    String[] test = TagName.get(i).toString().trim().split("-", 2);
                                    int a = Integer.parseInt(test[0].trim());
                                    int b = Integer.parseInt(test[1].trim());
                                    for (int j = a; j <= b; j++) {
                                        //if (j == rental)
                                        Log.e("a............ " + j, TagName.get(i));
                                        if (j == rentalDays) {
                                            Log.e("j............ " + j, TagName.get(i));
                                            column = i;
                                            break;
                                        }
                                    }
                                } else {
                                    String test = TagName.get(i).toString().trim().replace("ab", "");
                                    int tes = Integer.parseInt(test.trim());
                                    if (tes <= rentalDays) {
                                        column = i;
                                    }
                                }
                                Log.e("Column More..", "" + column);
                            }*/

//                            for (int i = 0; i < TagName.size(); i++)
//                            {
//                                if (TagName.get(i).toString().contains("Stunden"))
//                                {
//
//                                    String bis = TagName.get(i).toString().replace("Stunden", "");
//
//                                    if (bis.contains("bis"))
//                                    {
//
//                                        String bis1 = bis.replace("bis", "").trim();
//                                        int day = Integer.parseInt(bis1.trim());
//                                        for (int j = 1; j <= day; j++) {
//                                            if (j == rentalDays) {
//                                                column = i;
//                                            }
//                                        }
//
//                                    } else if (bis.contains("über"))
//                                    {
//
//                                        String bis1 = bis.replace("über", "").trim();
//                                        int day = Integer.parseInt(bis1.trim());
//                                        if (day < rentalDays) {
//                                            column = i;
//                                        }
//                                    }
//                                }
//                                else if (TagName.get(i).toString().contains("bis"))
//                                {
//
//                                    String tag = TagName.get(i).toString().replace("Std", "");
//                                    String bis = tag;
//                                    Log.e("bis more 1800", "" + bis);
//                                    if (bis.contains("bis")) {
//                                        String tag12 = TagName.get(i).toString().replace("bis", "");
//                                        String tagfinal12 = tag12.replace("Std", "");
//                                        Log.e("tagfinal12 bis", "" + tagfinal12);
//                                        String bis1 = tagfinal12;
//                                        Log.e("bis1", "" + bis1.trim());
//                                        //String bis1 = bis.replace("bis", "").trim();
//                                        int day = Integer.parseInt(bis1.trim());
//                                        for (int j = 1; j <= day; j++) {
//                                            if (j == rentalDays) {
//                                                Log.e("int bis1", j + " rentalDays " + rentalDays);
//                                                column = i;
//                                            }
//                                        }
//                                    } else if (bis.contains("über")) {
//                                        String tag1 = TagName.get(i).toString().replace("über", "");
//                                        String tagfinal1 = tag1.replace("Std", "");
//                                        Log.e("tagfinal1 über", "" + tagfinal1);
//                                        String bis1 = tagfinal1;
//                                        //String bis1 = bis.replace("über", "").trim();
//                                        Log.e("über", "" + bis1.trim());
//                                        int day = Integer.parseInt(bis1.trim());
//                                        if (day < rentalDays) {
//                                            column = i;
//                                        }
//                                    }
//                                } else
//                                {
//                                    if (TagName.get(i).toString().contains("-")) {
//                                        String[] test = TagName.get(i).toString().trim().split("-", 2);
//                                        int a = Integer.parseInt(test[0].trim());
//                                        int b = Integer.parseInt(test[1].trim());
//                                        for (int j = a; j <= b; j++) {
//                                            if (j == rentalDays) {
//                                                column = i;
//                                            }
//                                        }
//                                    } else {
//                                        try {
//                                            // String test = TagName.get(i).toString().trim().replace("ab", "");
//                                            String test = "";
//                                            String test1 = "";
//                                            if (TagName.get(i).toString().trim().contains("ab")) {
//                                                test = TagName.get(i).toString().trim().replace("ab", "");
//                                                test1 = test;
//                                                Log.e("Standard price 0 test ab ", "" + test1);
//                                            } else {
//                                                test = TagName.get(i).toString().trim().replace("über", "");
//                                                test1 = test.replace("Std", "");
//                                                Log.e("Standard price 0 test1 ab über", "" + test1);
//                                            }
//
//
//                                            int tes = Integer.parseInt(test1.trim());
//                                            if (tes <= rentalDays) {
//                                                column = i;
//                                            }
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//
//                                    }
//                                }
//                            }
                            if (column != -1) {
                                kaNrListViewMoreThenDataItemsAdapter.setSelection(column);
                                kaNrListViewMoreThenDataItemsAdapter.notifyDataSetChanged();
                            }

                            for (int i = 0; i < rowKaNrListViewMoreThenItems.size(); i++) {
                                if (deviceType.equals(rowKaNrListViewMoreThenItems.get(i).getHoehengruppe())) {
                                    kaNrListViewMoreThenDataItemsAdapter.setSelectionOfRow(i);
                                    kaNrListViewMoreThenDataItemsAdapter.notifyDataSetChanged();
                                    if (column != -1) {
                                        price = rowKaNrListViewMoreThenItems.get(i).getListPrice().get(column);
                                    }
                                    //break;
                                }
                                if ((rowKaNrListViewMoreThenItems.get(i).getSort().toLowerCase()).contains(deviceType.toLowerCase().toString())) {
                                    if (column != -1) {
                                        double gesAmt = rowKaNrListViewMoreThenItems.get(i).getListPrice().get(column);
                                        gesAminitiesPrice = (gesAminitiesPrice + gesAmt);
                                    } else {

                                    }
                                } else {
                                    Log.e("column", column + " " + rowKaNrListViewMoreThenItems.get(i).getSort() + "... " + "" + rowKaNrListViewMoreThenItems.get(i).getListPrice().get(column));
                                }

                            }
                            // Log.e("column", column + " " + TagName.get(column).toString() + " price: " + price);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            try {
                /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME
                        + DataHelper.pricing2PriceCockpitPreiseMoreThen
                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "&Kanr=" + Branch
                        + "&DeviceType=" + DeviceType.trim()
                        + "&Einheit=" + Einheit
                        + "&GeratetypID=" + GerateType
                        + "&Assesories=" + Assesories;*/
                String url = DataHelper.URL_PRICE_HELPER
                        + "pricecockpitpreisemore/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/Kanr=" + Branch
                        + "/DeviceType=" + DeviceType.trim()
                        + "/Einheit=" + Einheit
                        + "/Assesories=" + Assesories
                        + "/GeratetypID=" + GerateType;
                Log.e("Url KontaktId", url);
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                asyncTask.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showLongToast(language.getMessageNetworkNotAvailable());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_menu, menu);
        this.menu = menu;
        menu.findItem(R.id.actionAdd).setVisible(false);
        menu.findItem(R.id.actionSearch).setVisible(false);
        menu.findItem(R.id.actionEdit).setVisible(false);
        menu.findItem(R.id.actionRight).setVisible(false);
        menu.findItem(R.id.actionWrong).setVisible(false);
        //return true;
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.actionSettings:

                if(!preferences.getisPrice().equalsIgnoreCase(""))
                {
                    if(db.getLostsaleCount() > 0 ){
                        showAlertDialg();
                    }

                }
                else{
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.replace(R.id.content_frame, new SettingFragment(),"Setting");
                    //transaction.addToBackStack(SettingFragment.Tag);
                    transaction.addToBackStack("Setting");
                    transaction.commit();
                }


                return true;
            case R.id.actionBack:
                storePricingCustomerOrderInfo();
                if (getFragmentManager().getBackStackEntryCount() == 0) {
                    getActivity().finish();
                    //getFragmentManager().popBackStack();
                } else {
                    getFragmentManager().popBackStack();
                }

                return true;
            case R.id.actionForward:
                if(checkBoxSelbstfahrer.isChecked())
                {
                    preferences.saveComeFrom("selected");
                }
                else {
                    preferences.saveComeFrom("");
                }

                storePricingCustomerOrderInfo();
                Bundle args = new Bundle();
                args.putString("kanr", kanr);
                args.putInt("kaNrOfLoadedCustomer", kaNrOfLoadedCustomer);
                args.putInt("branchId", branchId);
                args.putString("deviceType", deviceType);
                args.putString("branchName", branchName);
                args.putString("contactPersonNo", contactPersonNo);
                args.putString("contactPersonName", contactPerson);
                args.putString("equipmentIds", equipmentIds);
                args.putString("EquipmentJson", equipmentJson);
                args.putInt("rental", rental);
                args.putInt("rentalDays", rentalDays);
                args.putString("dates_comma", datesComma);
                args.putString("startDate",startDate);
                args.putString("endDate",endDate);
                args.putDouble("price", price);
                args.putString("fromDate",fromDate);
                args.putString("toDate",toDate);
                args.putString("PriceUseInformationList", PriceUseInformationList());
                args.putString("GeratetypeId", GeratetypeId);
                if(textUseZipCode.getText().toString().trim().equals(""))
                {
                    args.putString("plz", "");
                }
                else
                {
                    args.putString("plz", textUseZipCode.getText().toString().trim());
                }
                args.putString("Besteller_Telefon", Besteller_Telefon);
                args.putString("Besteller_Email", Besteller_Email);
                args.putString("Besteller_Anrede", Besteller_Anrede);
                args.putString("Besteller_Mobil", Besteller_Mobil);

                Log.e(" ****** "," all varialbe before sending to in fragment 222 kanr : "+kanr + " kaNrofLOadedcustomer : "+kaNrOfLoadedCustomer +" branchId : "+branchId+" deviceType : "+
                        deviceType+" branchName : "+branchName+" contactPersonNo : "+
                        contactPersonNo+" contactPerson : "+contactPerson+" equipmentIds : "+equipmentIds+" equipmentJson : "+equipmentJson+" rental : "+rental+" rentalDays : "+rentalDays+" price : "+price);

                if (gesAminitiesPrice == 0.0) {

                    gesAminitiesPrice = 0.0;
                    gesAm = 0.0;
                } else {
                    //gesAminitiesPrice = (gesAminitiesPrice - price);
                    gesAm = (gesAminitiesPrice - price);
                }
                args.putDouble("gesAminities", gesAm);
                if (KaNrNo == 0) {
                    if ((price > 0)) {
                       //PricingFragment3 ft = new PricingFragment3();
                       // transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        //ft.setArguments(args);
                       //transaction.replace(R.id.content_frame, ft);
                        //transaction.addToBackStack("Gethering Activity");
                        //transaction.commit();
                        if(DataHelper.APP_NAME.equalsIgnoreCase("integrAMobile/MatecoSalesAppService.svc/json/") ||
                                DataHelper.APP_NAME.equalsIgnoreCase("integrAMobileSchulung/MatecoSalesAppService.svc/json/")||
                                DataHelper.APP_NAME.equalsIgnoreCase("integrAMobileTest/MatecoSalesAppService.svc/json/"))
                        {
                            FragmentPricingDetail fragmentPricingDetail = new FragmentPricingDetail();
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            fragmentPricingDetail.setArguments(args);
                            transaction.replace(R.id.content_frame, fragmentPricingDetail);
                            transaction.addToBackStack("Gethering Activity");
                            transaction.commit();
                        }
                        else{
                            PricingFragment3 fragmentPricingDetail = new PricingFragment3();
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            fragmentPricingDetail.setArguments(args);
                            transaction.replace(R.id.content_frame, fragmentPricingDetail);
                            transaction.addToBackStack("Gethering Activity");
                            transaction.commit();
                        }

                    } else {
                        showLongToast(language.getMessageTheCustomerAgreementDoesNotContainPrices());
                    }
                } else if (KaNrNo <= 1800) {
                    if (!GeratetypeId.equals("")) {
                        if ((price > 0)) {
                            //PricingFragment3 ft = new PricingFragment3();
                            //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            //ft.setArguments(args);
                            //transaction.replace(R.id.content_frame, ft);
                            ////transaction.addToBackStack("Gethering Activity");
                            //transaction.commit();
                            if(DataHelper.APP_NAME.equalsIgnoreCase("integrAMobile/MatecoSalesAppService.svc/json/") ||
                                    DataHelper.APP_NAME.equalsIgnoreCase("integrAMobileSchulung/MatecoSalesAppService.svc/json/")||
                                    DataHelper.APP_NAME.equalsIgnoreCase("integrAMobileTest/MatecoSalesAppService.svc/json/"))
                            {
                                FragmentPricingDetail fragmentPricingDetail = new FragmentPricingDetail();
                                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                fragmentPricingDetail.setArguments(args);
                                transaction.replace(R.id.content_frame, fragmentPricingDetail);
                                transaction.addToBackStack("Gethering Activity");
                                transaction.commit();
                            }
                            else {
                                PricingFragment3 fragmentPricingDetail = new PricingFragment3();
                                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                fragmentPricingDetail.setArguments(args);
                                transaction.replace(R.id.content_frame, fragmentPricingDetail);
                                transaction.addToBackStack("Gethering Activity");
                                transaction.commit();
                            }

                        } else {
                            showLongToast(language.getMessageTheCustomerAgreementDoesNotContainPrices());
                        }
                    } else {
                        if(listDeviceType.size() > 0)
                        {
                            showShortToast(language.getMessagePleaseSelectGerateType());
                        }
                        else
                        {
                            //showShortToast(language.getMessageGerateTypNotAvailable());
                        }
                    }
                } else {
                    if ((price > 0)) {
                        //PricingFragment3 ft = new PricingFragment3();
                        //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        //ft.setArguments(args);
                        //transaction.replace(R.id.content_frame, ft);
                        //transaction.addToBackStack("Gethering Activity");
                        //transaction.commit();
                        if(DataHelper.APP_NAME.equalsIgnoreCase("integrAMobile/MatecoSalesAppService.svc/json/") ||
                                DataHelper.APP_NAME.equalsIgnoreCase("integrAMobileSchulung/MatecoSalesAppService.svc/json/")||
                                DataHelper.APP_NAME.equalsIgnoreCase("integrAMobileTest/MatecoSalesAppService.svc/json/")){
                            FragmentPricingDetail fragmentPricingDetail = new FragmentPricingDetail();
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            fragmentPricingDetail.setArguments(args);
                            transaction.replace(R.id.content_frame, fragmentPricingDetail);
                            transaction.addToBackStack("Gethering Activity");
                            transaction.commit();
                        }
                        else {
                            PricingFragment3 fragmentPricingDetail = new PricingFragment3();
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            fragmentPricingDetail.setArguments(args);
                            transaction.replace(R.id.content_frame, fragmentPricingDetail);
                            transaction.addToBackStack("Gethering Activity");
                            transaction.commit();
                        }


                    } else {
                        showLongToast(language.getMessageTheCustomerAgreementDoesNotContainPrices());
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    public String PriceUseInformationList() {
        String infojson = "";
        Pricing2InsertPriceUseInformationListData userInfo = new Pricing2InsertPriceUseInformationListData();
        int EinsatzLand = countryNameId;
        //String EinsatzLand = countryName;
        String EinsatzStrasse = textUserStreet.getText().toString().trim();
        String EinsatzPLZ = textUseZipCode.getText().toString().trim();
        String Einsatzort = textUserPlace.getText().toString().trim();
        String Projekt = "";
        PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo,
                new Gson().toJson(new PricingCustomerOrderBasicInfo()));
        if (model.getProjectId() != null && !model.getProjectId().trim().equals("")) {
            Projekt = model.getProjectId();
        } else {
            Projekt = "";
        }

        String Zusatz = textZusatz.getText().toString().trim();
        String AVO = textContactPersonName.getText().toString().trim();
        String AVOTelefon = textContactPersonTelephone.getText().toString().trim();
        int Mautkilometer = 0;
        if ((textEntferunung.getText().toString().trim()).equals("")) {
            Mautkilometer = 0;
        } else {
            Mautkilometer = Integer.parseInt(textEntferunung.getText().toString().trim());
        }
        int Entfernung;
        String UserId = matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserId();
        userInfo.setEinsatzLand(EinsatzLand);
        userInfo.setEinsatzStrasse(EinsatzStrasse);
        userInfo.setEinsatzPLZ(EinsatzPLZ);
        userInfo.setEinsatzort(Einsatzort);
        userInfo.setProjekt(Projekt);
        userInfo.setZusatz(Zusatz);
        userInfo.setAVO(AVO);
        userInfo.setAVOTelefon(AVOTelefon);
        userInfo.setMautkilometer(0);
        userInfo.setEntfernung(Mautkilometer);
        userInfo.setUserID(UserId);

        infojson = new Gson().toJson(userInfo);
        Log.e("infojson..", infojson);

        return infojson;
    }

    private void getDeviceTypes(String Hoehengruppe, String KaNr, List<String> Ausstattung) {
        PriceGeraeteTypeParam model = new PriceGeraeteTypeParam();
        model.setHoehengruppe(Hoehengruppe);
        model.setKaNr(KaNr);
        if (Ausstattung.size() > 0) {
            String value = Ausstattung.get(0).toString();
            if (value.equals("null")) {
                value = "0";
            }
            model.setAusstattung1(value);
            if (Ausstattung.size() > 1) {
                value = Ausstattung.get(1).toString();
                if (value.equals("null")) {
                    value = "0";
                }
                model.setAusstattung2(value);
                if (Ausstattung.size() > 2) {
                    value = Ausstattung.get(2).toString();
                    if (value.equals("null")) {
                        value = "0";
                    }
                    model.setAusstattung3(value);
                    if (Ausstattung.size() > 3) {
                        value = Ausstattung.get(3).toString();
                        if (value.equals("null")) {
                            value = "0";
                        }
                        model.setAusstattung4(value);
                        if (Ausstattung.size() > 4) {
                            value = Ausstattung.get(4).toString();
                            if (value.equals("null")) {
                                value = "0";
                            }
                            model.setAusstattung5(value);
                        } else {
                            model.setAusstattung5("0");
                        }
                    } else {
                        model.setAusstattung4("0");
                        model.setAusstattung5("0");
                    }
                } else {
                    model.setAusstattung3("0");
                    model.setAusstattung4("0");
                    model.setAusstattung5("0");
                }
            } else {
                model.setAusstattung2("0");
                model.setAusstattung3("0");
                model.setAusstattung4("0");
                model.setAusstattung5("0");
            }
        } else {
            model.setAusstattung1("0");
            model.setAusstattung2("0");
            model.setAusstattung3("0");
            model.setAusstattung4("0");
            model.setAusstattung5("0");
        }

        BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult() {
            @Override
            public void OnAsynResult(String result) {
                Log.e("result", result);
                try {
                    listDeviceType.clear();
                    SiteInspectionDeviceTypeModel.extractFromJson(result, listDeviceType);

//                    spGeraetetype.setOnItemSelectedListener(null);
//                    spGeraetetype.setSelection(0);
//                    spGeraetetype.setOnItemSelectedListener(this);
//                    deviceTypeAdapter.notifyDataSetChanged();

                    deviceTypeAdapter.notifyDataSetChanged();
                    //spGeraetetype.setSelection(0);
                    if(listDeviceType.size() > 0) {
                        deviceTypeAdapter = new SiteInspectionDeviceTypeAdapter(getActivity(), listDeviceType, language);
                        spGeraetetype.setAdapter(deviceTypeAdapter);
                    }
//                    spGeraetetype.setOnItemSelectedListener(PricingFragment2.this);
                } catch (Exception ex) {
                    showShortToast(language.getMessageErrorAtParsing());
                    ex.printStackTrace();
                }
            }
        };
        String json = new Gson().toJson(model);
        Log.e("json at price", json);
        String url = "";
        try {
            /*url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.PRICE_DEVICE_TYPE_COMBO_SERVICE
                    + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                    + "&pricedeviceparam=" + URLEncoder.encode(json, "UTF-8");*/
            url = DataHelper.URL_PRICE_HELPER
                    + "pricegeraetetypecombo/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                    + "/pricedeviceparam=" + URLEncoder.encode(json, "UTF-8");
            Log.e("url at gerat", url);
            BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(),                true);
            asyncTask.execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void loadPricingCustomerOrderInfoFromPreference() {
        PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo,
                new Gson().toJson(new PricingCustomerOrderBasicInfo()));
        labelProject.setText(model.getProject());

         ArrayList<Pricing1BranchData> branches = new ArrayList<>();
        branches=db.getBranchList(preferences.getBranchId());
        /*if(!TextUtils.isEmpty(preferences.getComefrom()))
        {
            if(preferences.getComefrom().equalsIgnoreCase("selected")){

                textUseZipCode.setText(branches.get(0).getPlz());
                textUserPlace.setText(branches.get(0).getOrt());
                textUserStreet.setText(branches.get(0).getStrasse());
                spLandUse.setPrompt(branches.get(0).getDesignation());

                textUseZipCode.setEnabled(false);
                textUserPlace.setEnabled(false);
                textUserStreet.setEnabled(false);
                spLandUse.setEnabled(false);
            }
            else
            {
                textUseZipCode.setText(model.getZipCode());
                textUserPlace.setText(model.getPlace());
                textUserStreet.setText(model.getStreet());

                textUseZipCode.setEnabled(true);
                textUserPlace.setEnabled(true);
                textUserStreet.setEnabled(true);
                spLandUse.setEnabled(true);
            }

        }
        else {
            textUseZipCode.setText(model.getZipCode());
            textUserPlace.setText(model.getPlace());
            textUserStreet.setText(model.getStreet());

            textUseZipCode.setEnabled(true);
            textUserPlace.setEnabled(true);
            textUserStreet.setEnabled(true);
            spLandUse.setEnabled(true);
        }*/




        textUseZipCode.setText(model.getZipCode());
        textUserPlace.setText(model.getPlace());
        textUserStreet.setText(model.getStreet());

        textZusatz.setText(model.getZusatz());
        textContactPersonName.setText(model.getContactPersonName());
        textContactPersonTelephone.setText(model.getContactPersonMobile());
        textEntferunung.setText(model.getEnferrung());
        GeratetypeId = ""+model.getGeraeteTypeId();
        Besteller_Anrede = model.getBestellerAnrede();
        Besteller_Email = model.getBestellerEmail();
        Besteller_Mobil = model.getBestellerMobile();
        Besteller_Telefon = model.getBestellerTelephone();
        startDate = model.getStartDate();
        endDate = model.getEndDate();
        String plz = textUseZipCode.getText().toString().trim();
        if(plz != null && !plz.trim().equals(""))
            getEntfernung(String.valueOf(branchId), plz);

        try{
            if(model.getLatitude() != 0 && model.getLongitude() != 0)
            {
                mLastLocation.set(new Location(""));
                mLastLocation.setLatitude(model.getLatitude());
                mLastLocation.setLongitude(model.getLongitude());

            }
        }
        catch (Exception e){

        }

    }

    private void storePricingCustomerOrderInfo() {
        PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo,
                new Gson().toJson(new PricingCustomerOrderBasicInfo()));
        model.setProject(labelProject.getText().toString());
        model.setCountry(countryNameId + "");
        model.setZipCode(textUseZipCode.getText().toString());
        model.setPlace(textUserPlace.getText().toString());
        model.setStreet(textUserStreet.getText().toString());
        model.setZusatz(textZusatz.getText().toString());
        model.setContactPersonName(textContactPersonName.getText().toString());
        model.setContactPersonMobile(textContactPersonTelephone.getText().toString());
        model.setEnferrung(textEntferunung.getText().toString());
        model.setBestellerAnrede(Besteller_Anrede);
        model.setBestellerEmail(Besteller_Email);
        model.setBestellerMobile(Besteller_Mobil);
        model.setBestellerTelephone(Besteller_Telefon);
        model.setStartDate(startDate);

        model.setEndDate(endDate);
        if(mLastLocation != null)
        {
            model.setLatitude(mLastLocation.getLatitude());
            model.setLongitude(mLastLocation.getLongitude());
        }
        if(GeratetypeId != null && GeratetypeId.length() > 0)
            model.setGeraeteTypeId(Integer.parseInt(GeratetypeId));
        String jsonString = new Gson().toJson(model);
        matecoPriceApplication.saveData(DataHelper.PricingCustomerBasicOrderInfo,jsonString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnProjectHelp:
                storePricingCustomerOrderInfo();
                addProject();
                break;
            case R.id.imageViewCall:
                if ( Build.VERSION.SDK_INT >= 23)
                {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) ==
                            PackageManager.PERMISSION_GRANTED)
                    {
                        GlobalMethods.callToNumber(context, textContactPersonTelephone);
                    }
                    else{
                        ActivityCompat.requestPermissions(getActivity(), new String[] {
                                        Manifest.permission.CALL_PHONE},
                                3005);
                    }
                }
                else {
                    GlobalMethods.callToNumber(context, textContactPersonTelephone);
                }

                break;
            case R.id.imgBtnProjectRemove:
                removeSelectedProject();
                break;
            case R.id.imageButtonCurrentLocation:
                getCurrentLocation();
                break;
            case R.id.imageButtonMapLocation:
                getCurrentLocationFromMap();
                break;
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void getCurrentLocation()
    {
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected())
        {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                //mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
                //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
                List<Address> addressList = GlobalMethods.reverseGeoCode(getActivity(), mLastLocation);
                if (addressList != null && addressList.size() > 0)
                {
                    Address address = addressList.get(0);
                    textUseZipCode.setText(address.getPostalCode());
                    textUserPlace.setText(address.getLocality());

                    String splite[] = address.getAddressLine(0).split(",");
                    if(splite.length > 0){
                        textUserStreet.setText(splite[0].toString());
                    }else {
                        textUserStreet.setText(address.getAddressLine(0));
                    }

                    if(address.getPostalCode() != null && !address.getPostalCode().trim().equals(""))
                        getEntfernung(String.valueOf(branchId), address.getPostalCode());
                }
            }
        }
    }

    public void getCurrentLocationFromMap()
    {
        PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo,
                new Gson().toJson(new PricingCustomerOrderBasicInfo()));
        if(mLastLocation == null && model.getLatitude() == 0 && model.getLongitude() == 0)
        {
            getCurrentLocation();
        }
        storePricingCustomerOrderInfo();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        //ProjectSearchFragment1 fragment = new ProjectSearchFragment1();
        SiteInspectionMapFragment fragment = new SiteInspectionMapFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.IsFromPricing, true);
        fragment.setArguments(bundle);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack("Pricing 2");
        transaction.commit();
    }

    private void removeSelectedProject()
    {
        labelProject.setText("");
        textEntferunung.setText("");
        textUserPlace.setText("");
        textUserStreet.setText("");
        textUseZipCode.setText("");
        textZusatz.setText("");
        PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo,
                new Gson().toJson(new PricingCustomerOrderBasicInfo()));
        model.setPlace("");
        model.setStreet("");
        model.setZipCode("");
        model.setZusatz("");
        model.setEnferrung("");
        model.setProject("");
        matecoPriceApplication.saveData(DataHelper.PricingCustomerBasicOrderInfo, new Gson().toJson(model));
    }

    private void addProject() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (matecoPriceApplication.isCustomerLoaded(DataHelper.isCustomerLoaded, false)) {
            ProjectSearchFragment1 fragment = new ProjectSearchFragment1();
            Bundle args3 = new Bundle();
            args3.putString("Pricing2AnsprechPartner", "Pricing2AnsprechPartner");
            args3.putString("kanr", kanr);
            args3.putInt("branchId", branchId);
            args3.putString("branchName", branchName);
            args3.putString("contactPersonNo", contactPersonNo);
            args3.putString("contactPersonName", contactPerson);
            args3.putString("deviceType", deviceType);
            args3.putString("equipmentIds", equipmentIds);
            args3.putString("EquipmentJson", equipmentJson);
            args3.putInt("rental", rental);
            args3.putInt("rentalDays", rentalDays);
            args3.putString("Besteller_Telefon", Besteller_Telefon);
            args3.putString("Besteller_Email", Besteller_Email);
            args3.putString("Besteller_Anrede", Besteller_Anrede);
            args3.putString("Besteller_Mobil", Besteller_Mobil);

            String EinsatzStrasse = textUserStreet.getText().toString().trim();
            String EinsatzPLZ = textUseZipCode.getText().toString().trim();
            String Einsatzort = textUserPlace.getText().toString().trim();
            String Zusatz = textZusatz.getText().toString().trim();
            String Entfernung = textEntferunung.getText().toString().trim();

            args3.putString("EinsatzStrasse", EinsatzStrasse);
            args3.putString("EinsatzPLZ", EinsatzPLZ);
            args3.putString("Einsatzort", Einsatzort);
            args3.putString("Zusatz", Zusatz);
            args3.putString("Entfernung", Entfernung);
            args3.putString("PricingScreen2", "PricingScreen2");
            args3.putString("gerateNo", GeratetypeId);
            fragment.setArguments(args3);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.replace(R.id.content_frame, fragment);
            transaction.addToBackStack("Pricing 2");
            transaction.commit();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
    {
        Spinner spinner = (Spinner) adapterView;
        if (spinner.getId() == R.id.spGeraetetype)
        {
            if (rowKaNrListViewLessThenItems != null) {
                Log.e("rowKaNrListViewLessThenItems", "not null");
                rowKaNrListViewLessThenItems.clear();
            }
            if (rowKaNrListViewMoreThenItems != null) {
                Log.e("rowKaNrListViewMoreThenItems", "not null");
                rowKaNrListViewMoreThenItems.clear();
            }
            if (rowKaNrListViewItems != null) {
                Log.e("rowKaNrListViewItems", "not null");
                rowKaNrListViewItems.clear();
            }
            if (offlineStandardPriceListViewItemsAdapter != null) {
                Log.e("offlineStandardPriceListViewItemsAdapter", "not null");
                offlineStandardPriceListViewItemsAdapter.notifyDataSetChanged();
            }
            if (kaNrListViewLessThenDataItemsAdapter != null) {
                Log.e("kaNrListViewLessThenDataItemsAdapter", "not null");
                kaNrListViewLessThenDataItemsAdapter.notifyDataSetChanged();
            }
            if (kaNrListViewMoreThenDataItemsAdapter != null) {
                Log.e("kaNrListViewMoreThenDataItemsAdapter", "not null");
                kaNrListViewMoreThenDataItemsAdapter.notifyDataSetChanged();
            }
            if (rowKaNrListViewItems != null) {
                rowKaNrListViewItems.clear();
            }
            /*if (kaNrListViewItemsAdapter != null) {
                Log.e("kaNrListViewMoreThenDataItemsAdapter", "not null");
                kaNrListViewItemsAdapter.notifyDataSetChanged();
            }*/
            if (position == 0) {
                GeratetypeId = "";
                if (KaNrNo <= 1800 && KaNrNo != 0)
                {
//                    if(listDeviceType.size() > 0)
//                    {
//                        showShortToast(language.getMessagePleaseSelectGerateType());
//                    }
//                    else
//                    {
//                        showShortToast(language.getMessageGerateTypNotAvailable());
//                    }

                }
            } else {
                GeratetypeId = listDeviceType.get(position - 1).getGeraeettypID();
                Log.e("GeratetypeId Id", "" + GeratetypeId);
                if (KaNrNo <= 1800 && KaNrNo != 0) {
                    Log.e("KaNrNo Less", "" + KaNrNo);
                    Log.e("deviceType Less", "" + deviceType);
                    Log.e("GeratetypeId Id", "" + GeratetypeId);
                    if(GeratetypeId.equalsIgnoreCase("")){
                        GeratetypeId="0";
                    }
                    loadKontaktListViewDataLessThen1800(KaNrNo, GeratetypeId, rental);
                } else {
                    kaNrListViewLessThenDataItemsAdapter = new Pricing2KaNrListViewLessThenDataAdapter(getActivity(), rowKaNrListViewLessThenItems);
                    lvKaNrListView.setAdapter(kaNrListViewLessThenDataItemsAdapter);
                }
            }
            if (KaNrNo == 0) {
                Log.e("kan", "0");
                if (position == 0)
                    GeratetypeId = "0";
                Log.e("deviceType standard", "" + deviceType);
                // when click on standard price call this method
                loadKontaktListViewDataStandardPrice(branchId, deviceType, GeratetypeId, rental, equipmentIds);
            } else if (KaNrNo > 1800) {
                Log.e("KaNrNo", "1800");
                if (position == 0)
                    GeratetypeId = "0";
                Log.e("deviceType more", "" + deviceType);
                Log.e("param more", "" + KaNrNo + "-" + deviceType + "-" + GeratetypeId + "-" + rental + "-" + equipmentIds);
                loadKontaktListViewDataMoreThen1800(KaNrNo, deviceType, GeratetypeId, rental, equipmentIds);
            }
            /// mymy
            else if (KaNrNo <= 1800 && KaNrNo != 0)
            {
                Log.e("KaNrNo Less", "" + KaNrNo);
                Log.e("deviceType Less", "" + deviceType);
                Log.e("GeratetypeId Id", "" + GeratetypeId);
                if(GeratetypeId.equalsIgnoreCase("")){
                    GeratetypeId="0";
                }
                loadKontaktListViewDataLessThen1800(KaNrNo, GeratetypeId, rental);
            }
            // mymy
        }
        else if(spinner.getId() == R.id.spKaNr)
        {
            KaNrNo = lablesKaNr.get(position).getKaNr();
            kaNrOfLoadedCustomer = KaNrNo;
            Log.e("KaNrNo Id", "" + KaNrNo);


            spGeraetetype.setSelection(0);
            if(listDeviceType.size() > 0)
            {
                //
                // for these devices group , there is no agreement - please selections Standard prices or random grupp
                showShortToast(language.getMessagePleaseSelectGerateType());
            }
            else
            {
                //showShortToast(language.getMessageGerateTypNotAvailable());
            }
            deviceTypeAdapter.notifyDataSetChanged();
            if (rowKaNrListViewLessThenItems != null) {
                Log.e("rowKaNrListViewLessThenItems", "not null");
                rowKaNrListViewLessThenItems.clear();
            }
            if (rowKaNrListViewMoreThenItems != null) {
                Log.e("rowKaNrListViewMoreThenItems", "not null");
                rowKaNrListViewMoreThenItems.clear();
            }
            if (rowKaNrListViewItems != null) {
                Log.e("rowKaNrListViewItems", "not null");
                rowKaNrListViewItems.clear();
            }
            if (offlineStandardPriceListViewItemsAdapter != null) {
                Log.e("offlineStandardPriceListViewItemsAdapter", "not null");
                offlineStandardPriceListViewItemsAdapter.notifyDataSetChanged();
            }
            Log.e(" before adapter "," rowKaNrListViewLessThenItems  size befor notify : "+
                rowKaNrListViewLessThenItems.size()+ " offline : "+rowOfflineStandardPriceListViewItems.size()+" more  then : "+
                rowKaNrListViewMoreThenItems.size() + " main list : "+rowKaNrListViewItems.size());
            // this is firing
            if (kaNrListViewLessThenDataItemsAdapter != null) {
                Log.e("kaNrListViewLessThenDataItemsAdapter", "not null");
                kaNrListViewLessThenDataItemsAdapter.notifyDataSetChanged();
            }
            if (kaNrListViewMoreThenDataItemsAdapter != null) {
                Log.e("kaNrListViewMoreThenDataItemsAdapter", "not null");
                kaNrListViewMoreThenDataItemsAdapter.notifyDataSetChanged();
            }
            if (rowKaNrListViewItems != null) {
                rowKaNrListViewItems.clear();
            }
            /*if (kaNrListViewItemsAdapter != null) {
                Log.e("kaNrListViewMoreThenDataItemsAdapter", "not null");
                //kaNrListViewItemsAdapter = new Pricing2KaNrListViewDataAdapter(getActivity(), rowKaNrListViewItems);
                kaNrListViewItemsAdapter.notifyDataSetChanged();
            }*/
            if (DataHelper.isNetworkAvailable(getActivity())) {
                List<String> items = new ArrayList<>();
                if (equipmentIds != null) {
                    items = Arrays.asList(equipmentIds.split("\\s*,\\s*"));
                }
                getDeviceTypes(deviceType, KaNrNo + "", items);
            } else {
                listDeviceType.clear();
                listDeviceType.addAll(db.getDeviceType(deviceType));
            }

            //                if (KaNrNo == 0)
            //                {
            //
            //                    //spGeraetetype.setEnabled(false);
            //                    loadKontaktListViewDataStandardPrice(branchId, deviceType, rental, equipmentIds);
            //                }
            //                else if (KaNrNo <= 1800)
            //                {
            //                    Log.e("KaNrNo Less", "" + KaNrNo);
            //                    //spGeraetetype.setEnabled(true);
            //                    //loadKontaktListViewDataLessThen1800(KaNrNo, deviceType, rental);
            //
            //                    if(!GeratetypeId.equals(""))
            //                    {
            //                        loadKontaktListViewDataLessThen1800(KaNrNo, GeratetypeId, rental);
            //                    }
            //                }
            //                else
            //                {
            //                    Log.e("KaNrNo More", "" + KaNrNo);
            //                    spGeraetetype.setSelection(0);
            //                    deviceTypeAdapter.notifyDataSetChanged();
            //                    //spGeraetetype.setEnabled(false);
            //                    loadKontaktListViewDataMoreThen1800(KaNrNo, deviceType, rental, equipmentIds);
            //                }
        }
    }
    public void showAlertDialg()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle(language.getLabelNote());

        // set dialog message
        alertDialogBuilder
                .setMessage("Die kundenmaske kann nicht geschlossen werden! Bitte bearbeiten sie alle zeilen des Zwischenspeichers!")
                .setCancelable(false)

                .setNegativeButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            //mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}