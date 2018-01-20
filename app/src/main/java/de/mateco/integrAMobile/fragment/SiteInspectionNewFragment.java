package de.mateco.integrAMobile.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import de.mateco.integrAMobile.Helper.GlobalMethods;
import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.DatePickerDialogFragment;
import de.mateco.integrAMobile.Helper.InputFilterMinMax;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.Manifest;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.SiteInspectionAccessAdapter;
import de.mateco.integrAMobile.adapter.SiteInspectionBuildingProjectAdapter;
import de.mateco.integrAMobile.asyncTask.AsyncTaskWithAuthorizationHeaderPost;
import de.mateco.integrAMobile.asyncTask.GeocoderTask;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.ContactPersonModel;
import de.mateco.integrAMobile.model.CustomerModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LoginPersonModel;
import de.mateco.integrAMobile.model.Pricing1BranchData;
import de.mateco.integrAMobile.model.ResponseFormat;
import de.mateco.integrAMobile.model.SiteInspectionAccessModel;
import de.mateco.integrAMobile.model.SiteInspectionAdditionalMobileWindPowerModel;
import de.mateco.integrAMobile.model.SiteInspectionBuildingProjectModel;
import de.mateco.integrAMobile.model.SiteInspectionModel;
import de.mateco.integrAMobile.model.SiteInspectionNewModel;
import de.mateco.integrAMobile.model.SiteInspectionOperationalDataPermitsModel;
import de.mateco.integrAMobile.model.SiteInspectionOperationalEnvironmentModel;

public class SiteInspectionNewFragment extends BaseFragment implements View.OnClickListener,LocationListener
{
    ArrayList<ContactPersonModel> arrayListContactPersonFromDb;
    private String strKontactFromDb="";
    private View rootView;
    private MatecoPriceApplication application;
    private Language language;
    private LocationManager locManager;
    private boolean gpsEnabled = false,networkEnabled = false;
    private Double latitude,longitude;
    private EditText textRoad,textZipCode,textPlace,textApOnSite,textTelephone,textEmail,textOperationalLite,textWorkToBePerformed,
            textKnickPointHigh,textInterferingEdgesAt,textOther;
    private EditText textMatchCode,textName,textCostomerRoad, textCustomerPlace, textCustomerZip, textPurchaser, textpurchaserTelephone, textPurchaserEmail,textInsertDate;
    private CustomerModel customer = new CustomerModel();
    private Location location;
    private GeocoderTask geocoderTask;
    private ContactPersonModel ContactPerson;
    private ContactPersonModel AnsprechPartner;
    private Spinner SpnBuildingProject,SpnAccess;
    private ImageButton imgDate;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    private ArrayList<SiteInspectionBuildingProjectModel> listOfBuildingProject = new ArrayList<>();
    private ArrayList<SiteInspectionAccessModel> listOfAccess = new ArrayList<>();
    private DataBaseHandler db;
    private SiteInspectionModel siteInspectionModel = new SiteInspectionModel();
    private SiteInspectionNewModel siteInspectionNewModel = new SiteInspectionNewModel();
    private SiteInspectionOperationalEnvironmentModel siteInspectionOperationalEnvironmentModel = new SiteInspectionOperationalEnvironmentModel();
    private SiteInspectionOperationalDataPermitsModel siteInspectionOperationalDataPermitsModel = new SiteInspectionOperationalDataPermitsModel();
    private SiteInspectionAdditionalMobileWindPowerModel siteInspectionAdditionalMobileWindPowerModel = new SiteInspectionAdditionalMobileWindPowerModel();
    private ArrayList<String> listOfEmailAddress = new ArrayList<>();
    private ToggleButton buttonYes;
    private SharedPreferences preferences;
    final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    final long MIN_TIME_BW_UPDATES = 1000 * 10;
    private Button buttonCustomer,buttonSiteInspectionWithoutCustomer,buttonSiteInspectionMap,buttonUpdate,buttonPurchaser,buttonAnsprechpartner;
    private TextView labelValueLocationLongitude,labelValueLocationLatitude, labelValueLocationLatitudeFormat, labelValueLocationLongitudeFormat;
    private ArrayList<LoginPersonModel> loginPerson = new ArrayList<>();
    private ArrayList<Pricing1BranchData> listOfBranch = new ArrayList<>();
    private SiteInspectionNewModel model;
    private ImageView imageViewDialPurchaser, imageViewDialContactPerson;
    private String blockCharacterSet = "\"";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if(rootView == null)
        {
            Log.e("rootview", "null");
            rootView = inflater.inflate(R.layout.fragment_site_inspection, container, false);
            super.initializeFragment(rootView);
            if(getArguments() != null && getArguments().containsKey("Resume"))
            {

            }
            else if(getArguments() != null && getArguments().containsKey("ContactPerson"))
            {

            }
            else if(getArguments() != null && getArguments().containsKey("AnsprechPartner"))
            {

            }
            else
            {
                updateLocation();
            }
        }
        else
        {
            Log.e("rootview", "else");
            if(rootView.getParent() != null)
            {
                Log.e("rootView.getParent()", "not null");
                ((ViewGroup)rootView.getParent()).removeView(rootView);
            }
            super.initializeFragment(rootView);
        }
        return rootView;
    }

    @Override
    public void initializeComponents(View rootView)
    {
        super.initializeComponents(rootView);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        imageViewDialPurchaser = (ImageView)rootView.findViewById(R.id.imageViewDialPurchaser);
        imageViewDialContactPerson = (ImageView)rootView.findViewById(R.id.imageViewDialContactPerson);
        db = new DataBaseHandler(getActivity());
        preferences = getActivity().getSharedPreferences("SiteInspection",Context.MODE_PRIVATE);
        application = (MatecoPriceApplication)getActivity().getApplication();
        language = application.getLanguage();
        setLanguage();
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelSiteInspectionNew());
        locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        if(application.isCustomerLoaded(DataHelper.isCustomerLoaded,false))
        {
            Log.e("enter",preferences.getBoolean(DataHelper.SiteInspectionWithoutCustomer,false)+"");
            if(!preferences.getBoolean(DataHelper.SiteInspectionWithoutCustomer,false))
            {
                customer = application.getLoadedCustomer(DataHelper.LoadedCustomer,"");
                if(customer != null) {
                    textMatchCode.setText(customer.getMatchCode());
                    textCostomerRoad.setText(customer.getStrasse());
                    textCustomerZip.setText(customer.getPLZ());
                    textCustomerPlace.setText(customer.getOrt());
                    textName.setText(customer.getName1());
                }
                textPurchaser.setText("");
                textpurchaserTelephone.setText("");
                textPurchaserEmail.setText("");

                textApOnSite.setText("");
                textTelephone.setText("");
                textEmail.setText("");


            }
        }
        if(getArguments() != null)
        {
            if(getArguments().containsKey("ContactPerson"))
            {
                ContactPerson = getArguments().getParcelable("ContactPerson");
                textPurchaser.setText(ContactPerson.getNachname());
                textpurchaserTelephone.setText(ContactPerson.getTelefon());
                textPurchaserEmail.setText(ContactPerson.getEmail());
                AnsprechPartner = getArguments().getParcelable("AnsprechPartnerBack");
                if(AnsprechPartner!=null)
                {
                    textApOnSite.setText(AnsprechPartner.getNachname());
                    textTelephone.setText(AnsprechPartner.getTelefon());
                    textEmail.setText(AnsprechPartner.getEmail());
                }
            }
            else if(getArguments().containsKey("AnsprechPartner"))
            {
                AnsprechPartner = getArguments().getParcelable("AnsprechPartner");
                textApOnSite.setText(AnsprechPartner.getNachname());
                textTelephone.setText(AnsprechPartner.getTelefon());
                textEmail.setText(AnsprechPartner.getEmail());
                ContactPerson = getArguments().getParcelable("ContactPersonBack");
                if(ContactPerson!=null)
                {
                    textPurchaser.setText(ContactPerson.getNachname());
                    textpurchaserTelephone.setText(ContactPerson.getTelefon());
                    textPurchaserEmail.setText(ContactPerson.getEmail());
                }
            }
            else if(getArguments() != null && getArguments().containsKey("Resume"))
            {
                setData();
            }
        }
    }

    @Override
    public void bindEvents(View rootView)
    {
        super.bindEvents(rootView);
        imageViewDialContactPerson.setOnClickListener(this);
        imageViewDialPurchaser.setOnClickListener(this);
        SpnBuildingProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                siteInspectionNewModel.setBauvorhabenText(listOfBuildingProject.get(position).getBezeichnung().length() > 40 ? listOfBuildingProject.get(position).getBezeichnung().substring(0, 40) : listOfBuildingProject.get(position).getBezeichnung());
                siteInspectionNewModel.setBauvorhaben(listOfBuildingProject.get(position).getBauvorhaben());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SpnAccess.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                siteInspectionNewModel.setZugang(listOfAccess.get(position).getZugang());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setLanguage()
    {
        TextView labelLocationLatitude, labelLocationLongitude;

        labelLocationLatitude = (TextView)rootView.findViewById(R.id.labelLocationLatitude);
        labelLocationLongitude = (TextView)rootView.findViewById(R.id.labelLocationLongitude);

        labelLocationLatitude.setText(language.getLabelLongitude());
        labelLocationLongitude.setText(language.getLabelLatitude());

        TextView labelMatchCode = (TextView)rootView.findViewById(R.id.labelMatchCode);
        TextView labelName = (TextView)rootView.findViewById(R.id.labelName);
        TextView labelRoad = (TextView)rootView.findViewById(R.id.labelRoad);
        TextView labelPlace = (TextView)rootView.findViewById(R.id.labelPlace);
        TextView labelZipCode = (TextView)rootView.findViewById(R.id.labelZipCode);
        TextView labelPurchaser = (TextView)rootView.findViewById(R.id.labelPurchaser);
        TextView labelTelephone = (TextView)rootView.findViewById(R.id.labelTelephone);
        TextView labelEmail = (TextView)rootView.findViewById(R.id.labelEmail);
        TextView labelApOnSite = (TextView)rootView.findViewById(R.id.labelApOnSite);
        TextView labeltelePhone = (TextView)rootView.findViewById(R.id.labeltelephone);
        TextView labelemail = (TextView)rootView.findViewById(R.id.labelemail);
        TextView labelroad = (TextView)rootView.findViewById(R.id.labelroad);
        TextView labelzipCode = (TextView)rootView.findViewById(R.id.labelzipCode);
        TextView labelplace = (TextView)rootView.findViewById(R.id.labelplace);
        TextView labelInsertData = (TextView)rootView.findViewById(R.id.labelInsertData);
        TextView labelOperationalLite = (TextView)rootView.findViewById(R.id.labelOperationalLite);
        TextView labelBuildingProject = (TextView)rootView.findViewById(R.id.labelBuildingProject);
        TextView labelAccess = (TextView)rootView.findViewById(R.id.labelAccess);
        TextView labelRegistioationRequired = (TextView)rootView.findViewById(R.id.labelRegistrationRequired);
        TextView labelWorkToBePerformed = (TextView)rootView.findViewById(R.id.labelWorkToBePerformed);
        TextView labelKinkPointHigh = (TextView)rootView.findViewById(R.id.labelKinkPointHigh);
        TextView labelOther = (TextView)rootView.findViewById(R.id.labelOther);
        TextView labelInterferingEdgesAt = (TextView)rootView.findViewById(R.id.labelInterferingEdgesAt);
        labelValueLocationLongitude = (TextView)rootView.findViewById(R.id.labelValueLocationLongitude);
        labelValueLocationLatitude = (TextView)rootView.findViewById(R.id.labelValueLocationLatitude);
        labelValueLocationLatitudeFormat = (TextView)rootView.findViewById(R.id.labelValueLocationLatitudeFormat);
        labelValueLocationLongitudeFormat = (TextView)rootView.findViewById(R.id.labelValueLocationLongitudeFormat);

        labelMatchCode.setText(language.getLabelMatchCode());
        labelName.setText(language.getLabelName());
        labelRoad.setText(language.getLabelRoad());
        labelPlace.setText(language.getLabelPlace());
        labelZipCode.setText(language.getLabelZipCode());
        labelPurchaser.setText(language.getLabelPurchaser());
        labelTelephone.setText("");
        //labelTelephone.setText(language.getLabelTelephone());
        //to do remove lable from email and set E-mail lable as a edittext hint
        labelEmail.setText("");
        //labelEmail.setText(language.getLabelEmail());
        labelApOnSite.setText(language.getLabelApOnSite());
        labeltelePhone.setText("");
       // labeltelePhone.setText(language.getLabelTelephone());
        //to do remove lable from email and set E-mail lable as a edittext hint
        labelemail.setText("");
        //labelemail.setText(language.getLabelEmail());
        labelzipCode.setText(language.getLabelZipCode());
        labelplace.setText(language.getLabelPlace());
        labelInsertData.setText(language.getLabelInsertData());
        labelOperationalLite.setText(language.getLabelOperationalLife());
        labelBuildingProject.setText(language.getLabelBuildingProject());
        labelAccess.setText(language.getLabelAccess());
        labelRegistioationRequired.setText(language.getLabelRegistrationRequired());
        labelWorkToBePerformed.setText(language.getLabelWorkToBePerformed());
        labelKinkPointHigh.setText(language.getLabelKnickPointHigh());
        labelOther.setText(language.getLabelOther());
        labelInterferingEdgesAt.setText(language.getLabelInterferingEdgesAt());
        labelroad.setText(language.getLabelRoad());

        buttonCustomer = (Button)rootView.findViewById(R.id.buttonCustomer);
        buttonSiteInspectionWithoutCustomer = (Button)rootView.findViewById(R.id.buttonSiteInspectionWithoutCustomer);
        buttonSiteInspectionMap = (Button)rootView.findViewById(R.id.buttonSiteInspectionMap);
        buttonUpdate = (Button)rootView.findViewById(R.id.buttonUpdate);
        buttonPurchaser = (Button)rootView.findViewById(R.id.buttonPurchaser);
        buttonAnsprechpartner = (Button)rootView.findViewById(R.id.buttonAnsprechpartner);


        InputFilter filter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                if (source != null && blockCharacterSet.contains(("" + source))) {
                    return "";
                }
                return null;
            }
        };

        textRoad = (EditText)rootView.findViewById(R.id.textRoad);
        textRoad.setFilters(new InputFilter[] { filter });
        textZipCode = (EditText)rootView.findViewById(R.id.textZipCode);
        textPlace = (EditText)rootView.findViewById(R.id.textPlace);
        textMatchCode = (EditText)rootView.findViewById(R.id.textMatchCode);
        textName = (EditText)rootView.findViewById(R.id.textName);
        textCostomerRoad = (EditText)rootView.findViewById(R.id.textCostomerRoad);
        textCustomerPlace = (EditText)rootView.findViewById(R.id.textCustomerPlace);
        textCustomerZip = (EditText)rootView.findViewById(R.id.textCustomerZip);
        textPurchaser = (EditText)rootView.findViewById(R.id.textPurchaser);
        textpurchaserTelephone = (EditText)rootView.findViewById(R.id.textPurchaserTelephone);
        textPurchaserEmail = (EditText)rootView.findViewById(R.id.textPurchaserEmail);
        textApOnSite = (EditText)rootView.findViewById(R.id.textApOnSite);
        textTelephone = (EditText)rootView.findViewById(R.id.textTelephone);
        textEmail = (EditText)rootView.findViewById(R.id.textEmail);
        textInsertDate = (EditText)rootView.findViewById(R.id.textInsertDate);
        textOperationalLite = (EditText)rootView.findViewById(R.id.textOperationalLite);
        textWorkToBePerformed = (EditText)rootView.findViewById(R.id.textWorkToBePerformed);
        textKnickPointHigh = (EditText)rootView.findViewById(R.id.textKnickPointHigh);
        textInterferingEdgesAt = (EditText)rootView.findViewById(R.id.textInterferingEdgesAt);
        textOther = (EditText)rootView.findViewById(R.id.textOther);

        SpnBuildingProject = (Spinner)rootView.findViewById(R.id.SpnBuildingProject);
        SpnAccess = (Spinner)rootView.findViewById(R.id.SpnAccess);

        buttonYes = (ToggleButton) rootView.findViewById(R.id.labelYes);

        imgDate = (ImageButton)rootView.findViewById(R.id.imgBtnDate);

        buttonPurchaser.setText(language.getLabelPurchaser());
        buttonCustomer.setText(language.getLabelCustomer());
        buttonSiteInspectionWithoutCustomer.setText(language.getLabelSiteInspectionWithoutCustomer());
        buttonSiteInspectionMap.setText(language.getLabelMap());
        buttonUpdate.setText(language.getLabelUpdate());
        buttonAnsprechpartner.setText(language.getLabelAnspPartner());
        buttonSiteInspectionWithoutCustomer.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
        buttonSiteInspectionMap.setOnClickListener(this);
        buttonCustomer.setOnClickListener(this);
        buttonPurchaser.setOnClickListener(this);
        buttonAnsprechpartner.setOnClickListener(this);
        imgDate.setOnClickListener(this);

        textMatchCode.setFocusable(false);
        textName.setFocusable(false);
        textCustomerPlace.setFocusable(false);
        textCostomerRoad.setFocusable(false);
        textPurchaser.setFocusable(false);
        textpurchaserTelephone.setFocusable(false);
        textPurchaserEmail.setFocusable(false);
        textCustomerZip.setFocusable(false);
        textInsertDate.setFocusable(false);
        textOperationalLite.setFilters(new InputFilter[]{new InputFilterMinMax("0", "999")});
        textKnickPointHigh.setFilters(new InputFilter[]{new InputFilterMinMax("0", "32767")});
        textInterferingEdgesAt.setFilters(new InputFilter[]{new InputFilterMinMax("0", "32767")});
        loadSpinnerData();
        setData();

    }

    private void loadSpinnerData()
    {
        listOfBuildingProject.clear();
        listOfAccess.clear();

        listOfBuildingProject.addAll(db.getBuildingProject());
        SiteInspectionBuildingProjectAdapter adapter = new SiteInspectionBuildingProjectAdapter(getActivity(), listOfBuildingProject, language);
        SpnBuildingProject.setAdapter(adapter);

        listOfAccess.addAll(db.getAccess());
        SiteInspectionAccessAdapter adapter1 = new SiteInspectionAccessAdapter(getActivity(), listOfAccess, language);
        SpnAccess.setAdapter(adapter1);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_site_inspection_permits, menu);
        menu.findItem(R.id.actionAdd).setVisible(false);
        menu.findItem(R.id.actionBack).setVisible(false);
        menu.findItem(R.id.actionRight).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        final FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch (item.getItemId())
        {
            case R.id.actionSettings:
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new SettingFragment(),"Setting");
                transaction.addToBackStack("Setting");
                transaction.commit();
                return true;
            case R.id.actionSave:
                DialogInterface.OnClickListener positiveCallback1 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(addUpdateSiteInspection())
                        {
                            db.updateSiteInspectionDate(preferences.getInt(DataHelper.SiteInspectionId, 0));
                            db.updateSiteInspectionUpload(preferences.getInt(DataHelper.SiteInspectionId, 0), "", 2);
                            preferences.edit().clear().commit();
                            showShortToast(language.getMessageBvoStored());
                            getActivity().getSupportFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
                        }
                    }
                };
                DialogInterface.OnClickListener negativeCallback1 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                AlertDialog alert1 = showAlert(language.getLabelNote(),language.getMessageBvoSave(),language.getLabelYes(), language.getLabelNo(),
                        positiveCallback1, negativeCallback1);
                alert1.show();
                return true;
            case R.id.actionForward:
                application.hideKeyboard(getActivity());

                /* commented on 17th Aug 2017 because it was getting wrong lat long from address */
                /*String address = textRoad.getText().toString() + " " +textPlace.getText().toString() + " " + textZipCode.getText().toString();
                ArrayList<BasicNameValuePair> latLong = DataHelper.getLatLongFromAddress(getActivity(), address);
                if (latLong.size() > 0)
                {
                    latitude = Double.parseDouble(latLong.get(0).getValue());
                    longitude = Double.parseDouble(latLong.get(1).getValue());
                    LatLng latLng = new LatLng(latitude, longitude);
                    Log.e("update button ", latitude + "longi" + longitude);
                    labelValueLocationLatitude.setText(latitude + "");
                    labelValueLocationLongitude.setText(longitude + "");
                    BasicNameValuePair nameValuePair = DataHelper.getFormattedLocationInDegree(getActivity(), latitude, longitude);
                    labelValueLocationLatitudeFormat.setText(nameValuePair.getName());
                    labelValueLocationLongitudeFormat.setText(nameValuePair.getValue());
                }*/
                if(addUpdateSiteInspection()) {

                    /**************20161108**************/
                    siteInspectionModel = db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId, 0));
                    final String json = new Gson().toJson(siteInspectionModel);
                    Log.e(" call service : ", " BVOInsertList in json string : "+json);
                    String url = null;
                    if(DataHelper.isNetworkAvailable(getActivity()))
                    {
                        AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult()
                        {
                            @Override
                            public void OnAsynResult(String result)
                            {
                                if(result.equals(DataHelper.NetworkError)){
                                showShortToast(language.getMessageNetworkNotAvailable());
                                }else {
                                    try {
                                        Log.e(" respnose &*&*&*&*& ", "response from service for mail fragmetn : " + result);
                                        ResponseFormat responseFormat = new Gson().fromJson(result, ResponseFormat.class);
                                        if (responseFormat.getResponseCode() == 10) {
                                            FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                                            SiteInspectionMapFragment fragment = new SiteInspectionMapFragment();
                                            transaction2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                            transaction2.replace(R.id.content_frame, fragment);
                                            transaction2.addToBackStack("Map");
                                            transaction2.commit();
                                        } else if (responseFormat.getResponseCode() == 20 && responseFormat.getErrorMessage().equalsIgnoreCase("MatchcodeNotMatch")) {
                                            showLongToast(language.getMessageMatchcodeNotMatch());
                                        }
                                    } catch (Exception e) {
                                        FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                                        SiteInspectionMapFragment fragment = new SiteInspectionMapFragment();
                                        transaction2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                        transaction2.replace(R.id.content_frame, fragment);
                                        transaction2.addToBackStack("Map");
                                        transaction2.commit();
                                    }
                                }

                            }
                        };
                        //url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.SiteInspectionCheckForProperMatchcode;
                        url = DataHelper.URL_BVO_HELPER + "checkforpropermatchcode";
                        MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                        try
                        {
                            Charset chars = Charset.forName("UTF-8");
                            multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8"), chars));
                            multipartEntity.addPart("BVOInsertList", new StringBody(json, chars));
                        }
                        catch (UnsupportedEncodingException e)
                        {
                            e.printStackTrace();
                        }
                        AsyncTaskWithAuthorizationHeaderPost asyncTask = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, getActivity(),multipartEntity ,true, language);
                        asyncTask.execute();
                    }
                    else
                    {
                        SiteInspectionMapFragment fragment = new SiteInspectionMapFragment();
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        transaction.replace(R.id.content_frame, fragment);
                        transaction.addToBackStack("Map");
                        transaction.commit();
                    }
                    /************************************/
                }
                return true;
            case R.id.actionWrong:
                DialogInterface.OnClickListener positiveCallback2 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deletePhotosById(preferences.getInt(DataHelper.SiteInspectionId,0));
                        db.deleteDeviceById(preferences.getInt(DataHelper.SiteInspectionId,0));
                        db.deleteSiteInspection(preferences.getInt(DataHelper.SiteInspectionId,0));
                        preferences.edit().clear().commit();
                        getActivity().getSupportFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
                    }
                };
                DialogInterface.OnClickListener negativeCallback2 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                AlertDialog alert2 = showAlert(language.getLabelNote(),language.getMessageLeaveBvo(),language.getLabelYes(), language.getLabelNo(),
                        positiveCallback2, negativeCallback2);
                alert2.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        Bundle args = new Bundle();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch (v.getId())
        {
            case R.id.buttonSiteInspectionWithoutCustomer:
                if(addUpdateSiteInspection()) {
                    DialogInterface.OnClickListener positiveCallback = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!preferences.getBoolean(DataHelper.SiteInspectionWithoutCustomer, false)) {
                                preferences.edit().putBoolean(DataHelper.SiteInspectionWithoutCustomer, true).commit();
                                buttonAnsprechpartner.setEnabled(false);
                                buttonPurchaser.setEnabled(false);
                                textMatchCode.setText("");
                                textName.setText("");
                                textCustomerZip.setText("");
                                textCustomerPlace.setText("");
                                textCostomerRoad.setText("");
                                textPurchaser.setText("");
                                textPurchaserEmail.setText("");
                                textpurchaserTelephone.setText("");
                                textApOnSite.setText("");
                                textEmail.setText("");
                                textTelephone.setText("");
                            }
                        }
                    };
                    DialogInterface.OnClickListener negativeCallback = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    };
                    AlertDialog alert = showAlert(language.getLabelAlert(), language.getMessageWithoutCustomer(), language.getLabelYes(), language.getLabelNo(),
                            positiveCallback, negativeCallback);
                    alert.show();
                }
                break;
            case R.id.buttonSiteInspectionMap:
                application.hideKeyboard(getActivity());
                String address = textRoad.getText().toString() + " " +textPlace.getText().toString() + " " + textZipCode.getText().toString();
                ArrayList<BasicNameValuePair> latLong = DataHelper.getLatLongFromAddress(getActivity(), address);
                if (latLong.size() > 0)
                {
                    latitude = Double.parseDouble(latLong.get(0).getValue());
                    longitude = Double.parseDouble(latLong.get(1).getValue());
                    LatLng latLng = new LatLng(latitude, longitude);
                    Log.e("update button ", latitude + "longi" + longitude);
                    labelValueLocationLatitude.setText(latitude+"");
                    labelValueLocationLongitude.setText(longitude + "");
                    BasicNameValuePair nameValuePair = DataHelper.getFormattedLocationInDegree(getActivity(), latitude, longitude);
                    labelValueLocationLatitudeFormat.setText(nameValuePair.getName());
                    labelValueLocationLongitudeFormat.setText(nameValuePair.getValue());
                }
                if(addUpdateSiteInspection()) {
                    SiteInspectionMapFragment fragment1 = new SiteInspectionMapFragment();
                    if (latitude != null && longitude != null) {
                        args.putDouble("Latitude", latitude);
                        args.putDouble("Longitude", longitude);
                        fragment1.setArguments(args);
                    }
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.replace(R.id.content_frame, fragment1);
                    transaction.addToBackStack("Site Inspection Map");
                    transaction.commit();
                }
                break;
            case R.id.buttonUpdate:
                updateLocation();
            break;
            case R.id.buttonCustomer:
                if(addUpdateSiteInspection()) {
                    if (preferences.getBoolean(DataHelper.SiteInspectionWithoutCustomer, false)) {
                        preferences.edit().putBoolean(DataHelper.SiteInspectionWithoutCustomer, false).commit();
                        buttonPurchaser.setEnabled(true);
                        buttonAnsprechpartner.setEnabled(true);
                    }
                    CustomerSearchFragment fragment2 = new CustomerSearchFragment();
                    Bundle args1 = new Bundle();
                    args1.putString("SiteInspectionCustomer", "SiteInspectionCustomer");
                    fragment2.setArguments(args1);
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.replace(R.id.content_frame, fragment2);
                    transaction.addToBackStack("Site Inspection New");
                    transaction.commit();
                }
                break;
            case R.id.buttonPurchaser:
                if(addUpdateSiteInspection()) {
                    if (application.isCustomerLoaded(DataHelper.isCustomerLoaded, false)) {
                        CustomerContactPersonFragment1 fragment3 = new CustomerContactPersonFragment1();
                        Bundle args2 = new Bundle();
                        args2.putParcelable("AnsprechPartnerBack", AnsprechPartner);
                        args2.putString("SiteInspectionContactPerson", "SiteInspectionContactPerson");
                        args2.putParcelableArrayList("contact_person",siteInspectionNewModel.getCustomerContactPersonList());
                        fragment3.setArguments(args2);
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        transaction.replace(R.id.content_frame, fragment3);
                        transaction.addToBackStack("Site Inspection New");
                        transaction.commit();
                    }
                }
                break;
            case R.id.buttonAnsprechpartner:
                if(addUpdateSiteInspection()) {
                    if(application.isCustomerLoaded(DataHelper.isCustomerLoaded,false))
                    {
                        CustomerContactPersonFragment1 fragment4 = new CustomerContactPersonFragment1();
                        Bundle args3 = new Bundle();
                        args3.putParcelable("ContactPersonBack",ContactPerson);
                        args3.putString("SiteInspectionAnsprechPartner", "SiteInspectionAnsprechPartner");
                        fragment4.setArguments(args3);
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        transaction.replace(R.id.content_frame, fragment4);
                        transaction.addToBackStack("Site Inspection Customer Contact Person");
                        transaction.commit();
                    }
                }
                break;
            case R.id.imgBtnDate:
                Date today = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(today);
                DatePickerDialogFragment newFragment = new DatePickerDialogFragment();
                args = new Bundle();
                args.putInt("year", c.get(Calendar.YEAR));
                args.putInt("month", c.get(Calendar.MONTH));
                args.putInt("day", c.get(Calendar.DAY_OF_MONTH));
                newFragment.setArguments(args);
                newFragment.setCallBack(onDate);
                newFragment.show(getActivity().getSupportFragmentManager(), "Date");
                break;
            case R.id.imageViewDialContactPerson:
                EditText textPurchaserTelephone = (EditText)rootView.findViewById(R.id.textPurchaserTelephone);
                if ( Build.VERSION.SDK_INT >= 23)
                {
                    if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE) ==
                            PackageManager.PERMISSION_GRANTED)
                    {
                        GlobalMethods.callToNumber(context, textPurchaserTelephone);
                    }
                    else{
                        ActivityCompat.requestPermissions(getActivity(), new String[] {
                                        android.Manifest.permission.CALL_PHONE},
                                3005);
                    }
                }
                else {
                    GlobalMethods.callToNumber(context, textPurchaserTelephone);
                }

                break;
            case R.id.imageViewDialPurchaser:
                if ( Build.VERSION.SDK_INT >= 23)
                {
                    if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE) ==
                            PackageManager.PERMISSION_GRANTED)
                    {
                        GlobalMethods.callToNumber(context, textTelephone);
                    }
                    else{
                        ActivityCompat.requestPermissions(getActivity(), new String[] {
                                        android.Manifest.permission.CALL_PHONE},
                                3005);
                    }
                }
                else {
                    GlobalMethods.callToNumber(context, textTelephone);
                }


                break;
        }
    }

    public boolean addUpdateSiteInspection()
    {
        if(saveData())
        {
            if(!preferences.getBoolean(DataHelper.isSiteInspectionLoaded,false))
            {
                siteInspectionModel.setSiteInspectionOperationalEnvironmentModel(siteInspectionOperationalEnvironmentModel);
                siteInspectionModel.setSiteInspectionOperationalDataPermitsModel(siteInspectionOperationalDataPermitsModel);
                siteInspectionModel.setSiteInspectionAdditionalMobileWindPowerModel(siteInspectionAdditionalMobileWindPowerModel);
                siteInspectionModel.setListOfEmailAddress(listOfEmailAddress);
                db.addSiteInspection(siteInspectionModel);
                siteInspectionModel.setId(db.getSiteInspectionId());
                preferences.edit().putInt(DataHelper.SiteInspectionId,db.getSiteInspectionId()).commit();
                preferences.edit().putBoolean(DataHelper.isSiteInspectionLoaded, true).commit();
            }
            else
            {
                db.updateSiteInspection(siteInspectionModel, preferences.getInt(DataHelper.SiteInspectionId, 0));
            }
        }
        else
        {
            return false;
        }
        return true;
    }
    public DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            monthOfYear += 1;
            String date = dayOfMonth + "." + monthOfYear + "." + year;
            String finaldate = null;
            try
            {
                finaldate = DataHelper.formatDate(formatter.parse(date));
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            textInsertDate.setText(finaldate);
        }
    };

    @Override
    public void onLocationChanged(Location location)
    {
        Log.e("on location changd", "location changed");
        if(location!=null)
        {
            //Geocoder geocoder = new Geocoder(getActivity(), Locale.ENGLISH);
            try
            {
                Log.e("enter","location changes");
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                onLocationChanged2(location);
                /*labelValueLocationLongitude.setText(longitude + "");
                labelValueLocationLatitude.setText(latitude + "");
                BasicNameValuePair nameValuePair = DataHelper.getFormattedLocationInDegree(getActivity(), latitude, longitude);
                labelValueLocationLatitudeFormat.setText(nameValuePair.getName());
                labelValueLocationLongitudeFormat.setText(nameValuePair.getValue());
                Log.e("lat", latitude + "longi" + longitude);
                Geocoder geoCoder = new Geocoder(getActivity());
                List<Address> addressList = geoCoder.getFromLocation(latitude,longitude, 1);
                if (addressList != null && addressList.size() > 0)
                {
                    Address address = addressList.get(0);
                    Log.e("add",address.getAddressLine(1));
                    textRoad.setText(address.getAddressLine(0));
                    textPlace.setText(address.getLocality());
                    textZipCode.setText(address.getPostalCode());
                }*/

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public void onLocationChanged2(Location location)
    {
        Log.e("on location changd", "location changed");
        if(location!=null)
        {
            //Geocoder geocoder = new Geocoder(getActivity(), Locale.ENGLISH);
            try
            {
                Log.e("enter","location changes");
                latitude = Double.parseDouble(String.valueOf(location.getLatitude()));
                longitude = Double.parseDouble(String.valueOf(location.getLongitude()));
                //latitude = location.getLatitude();
                //longitude = location.getLongitude();
                labelValueLocationLongitude.setText(longitude + "");
                labelValueLocationLatitude.setText(latitude + "");
                BasicNameValuePair nameValuePair = DataHelper.getFormattedLocationInDegree(getActivity(), latitude, longitude);
                labelValueLocationLatitudeFormat.setText(nameValuePair.getName());
                labelValueLocationLongitudeFormat.setText(nameValuePair.getValue());
                Log.e("lat", latitude + "longi" + longitude);
                Geocoder geoCoder = new Geocoder(getActivity());
                List<Address> addressList = geoCoder.getFromLocation(latitude,longitude, 1);


                if (addressList != null && addressList.size() > 0)
                {
                    Address address = addressList.get(0);
                    String splite[] = address.getAddressLine(0).split(",");
                    if(splite.length > 0) {
                        textRoad.setText(splite[0].toString());
                    }
                    textPlace.setText(address.getLocality());
                    textZipCode.setText(address.getPostalCode());
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void updateLocation()
    {
        try
        {
            gpsEnabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkEnabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        if ( Build.VERSION.SDK_INT >= 23)
        {
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED)
            {
                if(!gpsEnabled) {
                    if (networkEnabled)
                    {
                        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        onLocationChanged2(location);
                    }
                    else
                    {
                        try
                        {
                            if(getActivity()!=null)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle(language.getLabelAlert());
                                builder.setMessage(language.getMessageProviders());
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        if(getActivity()!=null)
                                        {
                                            getActivity().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                        }
                                        dialog.dismiss();
                                    }
                                }).create();
                                builder.create().show();
                            }
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                }
                else
                {
                    locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if(location != null)
                    {
                        onLocationChanged2(location);
                    }
                    else
                    {
                        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(location!=null)
                            onLocationChanged2(location);
                    }



                }
            }
            else{
                ActivityCompat.requestPermissions(getActivity(), new String[] {
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION },
                        3001);
            }
        }
        else {
            if(!gpsEnabled) {
                if (networkEnabled)
                {
                    locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    onLocationChanged2(location);
                }
                else
                {
                    try
                    {
                        if(getActivity()!=null)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle(language.getLabelAlert());
                            builder.setMessage(language.getMessageProviders());
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    if(getActivity()!=null)
                                    {
                                        getActivity().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                    }
                                    dialog.dismiss();
                                }
                            }).create();
                            builder.create().show();
                        }
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
            else
            {
                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location != null)
                {
                    onLocationChanged2(location);
                }
                else
                {
                    locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if(location!=null)
                        onLocationChanged2(location);
                }



            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 3001 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //setupContactsPicker();
            //Toast.makeText(getActivity(),"permission granted : ",Toast.LENGTH_SHORT).show();
            updateLocation();
        } else {

            // We were not granted permission this time, so don't try to show the contact picker
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public boolean saveData() {
        siteInspectionNewModel.setKennung("");

        if(getArguments() != null && getArguments().containsKey("Resume"))
        {
            /* for saved bvo (offline) added on 12th aug*/
            if(arrayListContactPersonFromDb != null && arrayListContactPersonFromDb.size() > 0) {
                siteInspectionNewModel.setCustomerContactPersonList(arrayListContactPersonFromDb);
            }
            if (textMatchCode.getText().toString().length() > 0)
                siteInspectionNewModel.setKontakt(strKontactFromDb);
            else
                siteInspectionNewModel.setKontakt("");
        }else {
            /* for new bvo added on 12th Aug*/
            if (application.isCustomerLoaded(DataHelper.isCustomerLoaded, false)){
                ArrayList<ContactPersonModel> arrayList = application.getLoadedCustomerContactPersons
                        (DataHelper.LoadedCustomerContactPerson, new ArrayList<ContactPersonModel>().toString());
                if(arrayList != null && arrayList.size() > 0) {
                    siteInspectionNewModel.setCustomerContactPersonList(arrayList);
                }
            }
            if (textMatchCode.getText().toString().length() > 0)
                siteInspectionNewModel.setKontakt(customer.getKontakt());
            else
                siteInspectionNewModel.setKontakt("");
        }

        siteInspectionNewModel.setKundeName(textName.getText().toString());
        siteInspectionNewModel.setMatchCode(textMatchCode.getText().toString());
        siteInspectionNewModel.setKontaktStrasse(textCostomerRoad.getText().toString());
        siteInspectionNewModel.setKontaktPLZ(textCustomerZip.getText().toString());
        siteInspectionNewModel.setKontaktOrt(textCustomerPlace.getText().toString());
        siteInspectionNewModel.setBesteller(textPurchaser.getText().toString());
        siteInspectionNewModel.setBestellerTelefon(textpurchaserTelephone.getText().toString());
        siteInspectionNewModel.setBestellerEMail(textPurchaserEmail.getText().toString());
        if (AnsprechPartner != null)
            siteInspectionNewModel.setAVO(AnsprechPartner.getAnspartner());
        else
            siteInspectionNewModel.setAVO("");
        siteInspectionNewModel.setAVOName(textApOnSite.getText().toString());

        if (!DataHelper.phoneNumberValidationGerman(textTelephone.getText().toString()) && !textTelephone.getText().toString().equals("")) {
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
            showMessage(language.getLabelAlert(), language.getMessagePhoneValidation(), language.getLabelOk(), listener).show();
            // textCustomerNewPhone.setError(language.getMessageNotValidNumber());
            textTelephone.setError(language.getMessageNotValidNumber());
            return false;
        } else
            siteInspectionNewModel.setAVOTelefon(textTelephone.getText().toString());

        if (!textEmail.getText().toString().equals("") && !android.util.Patterns.EMAIL_ADDRESS.matcher(textEmail.getText().toString()).matches()) {
            textEmail.setError(language.getMessageNotValidEmail());
            return false;
        }
        else {
            siteInspectionNewModel.setAVOEMail(textEmail.getText().toString());
        }
        siteInspectionNewModel.setEinsatzstrasse(textRoad.getText().toString());
        siteInspectionNewModel.setEinsatzPLZ(textZipCode.getText().toString());
        siteInspectionNewModel.setEinsatzort(textPlace.getText().toString());
        Log.e("date",DataHelper.formatDateToOriginal(textInsertDate.getText().toString()));
        String strdatetosend = DataHelper.formatDateToOriginal(textInsertDate.getText().toString());
        if(strdatetosend.equalsIgnoreCase("")){
            strdatetosend="";
        }
        Log.e(" fffff nulll "," null varialbe checking : "+strdatetosend);

        siteInspectionNewModel.setEinsatzdatum(strdatetosend);
        siteInspectionNewModel.setEinsatzdauer(textOperationalLite.getText().toString());
        if(buttonYes.isChecked())
            siteInspectionNewModel.setAnmeldung(1);
        else
            siteInspectionNewModel.setAnmeldung(0);
        siteInspectionNewModel.setArbeit(textWorkToBePerformed.getText().toString());
        siteInspectionNewModel.setKnickpunkt(textKnickPointHigh.getText().toString());
        siteInspectionNewModel.setStoerkante(textInterferingEdgesAt.getText().toString());
        siteInspectionNewModel.setNotiz(textOther.getText().toString());
        siteInspectionNewModel.setGeoLaenge(labelValueLocationLongitude.getText().toString());
        siteInspectionNewModel.setGeoBreite(labelValueLocationLatitude.getText().toString());
        //siteInspectionNewModel.setGeoLaenge("7.07822822034359");
        //siteInspectionNewModel.setGeoBreite("51.51698434637142");
        siteInspectionNewModel.setGeoBreiteFormate(labelValueLocationLatitudeFormat.getText().toString());
        siteInspectionNewModel.setGeoLaengeFormate(labelValueLocationLongitudeFormat.getText().toString());

        loginPerson = application.getLoginUser(DataHelper.LoginPerson,new ArrayList<LoginPersonModel>().toString());
        siteInspectionNewModel.setErsteller(loginPerson.get(0).getUserNumber() + "");
        siteInspectionNewModel.setMandant(loginPerson.get(0).getBranchCode() + "");
        siteInspectionModel.setSiteInspectionNewModel(siteInspectionNewModel);
        return true;
    }

    private void setData()
    {
        model = db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId,0)).getSiteInspectionNewModel();
        if(model != null)
        {
            /* added on 12th Aug to get contact person and kontakt from db*/
            arrayListContactPersonFromDb = model.getCustomerContactPersonList();
            strKontactFromDb = model.getKontakt();


            textMatchCode.setText(model.getMatchCode());
            textName.setText(model.getKundeName());
            textCostomerRoad.setText(model.getKontaktStrasse());
            textCustomerZip.setText(model.getKontaktPLZ());
            textCustomerPlace.setText(model.getKontaktOrt());

            textPurchaser.setText(model.getBesteller());
            textpurchaserTelephone.setText(model.getBestellerTelefon());
            textPurchaserEmail.setText(model.getBestellerEMail());
            textApOnSite.setText(model.getAVOName());
            textTelephone.setText(model.getAVOTelefon());
            textEmail.setText(model.getAVOEMail());
            String splite[] = model.getEinsatzstrasse().split(",");
            if(splite.length > 0) {
                textRoad.setText(splite[0]);
            }
            textZipCode.setText(model.getEinsatzPLZ()+"");
            textPlace.setText(model.getEinsatzort()+"");

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            try
            {
                if(!model.getEinsatzdatum().equals(""))
                    textInsertDate.setText(DataHelper.formatDate(format.parse(model.getEinsatzdatum())));
            }
            catch (ParseException e)
            {
                e.printStackTrace();
                textInsertDate.setText("");
            }
            textOperationalLite.setText(model.getEinsatzdauer());
            textWorkToBePerformed.setText(model.getArbeit());
            textKnickPointHigh.setText(model.getKnickpunkt());
            textInterferingEdgesAt.setText(model.getStoerkante());
            textOther.setText(model.getNotiz());
            labelValueLocationLongitude.setText(model.getGeoLaenge());
            labelValueLocationLatitude.setText(model.getGeoBreite());
            if(!TextUtils.isEmpty(model.getGeoBreite()) && model.getGeoBreite().trim().length() > 0)
            {
                latitude = Double.parseDouble(model.getGeoBreite());
            }
            if(!TextUtils.isEmpty(model.getGeoLaenge()) && model.getGeoLaenge().trim().length() > 0)
            {
                longitude = Double.parseDouble(model.getGeoLaenge());
            }
            if(latitude != null && longitude != null)
            {
                BasicNameValuePair nameValuePair = DataHelper.getFormattedLocationInDegree(getActivity(), latitude, longitude);
                labelValueLocationLatitudeFormat.setText(nameValuePair.getName());
                labelValueLocationLongitudeFormat.setText(nameValuePair.getValue());
                location = new Location("");
                location.setLatitude(latitude);
                location.setLongitude(longitude);
            }
            if(model.getAnmeldung()==0)
                buttonYes.setChecked(false);
            else
                buttonYes.setChecked(true);

            for(int i=0;i<listOfBuildingProject.size();i++)
            {
                if(model.getBauvorhabenText() != null && model.getBauvorhabenText().equals(listOfBuildingProject.get(i).getBezeichnung()))
                    SpnBuildingProject.setSelection(i);
            }

            for(int i=0;i<listOfAccess.size();i++)
            {
                if(!TextUtils.isEmpty(model.getZugang())  && model.getZugang().equals(listOfAccess.get(i).getZugang()))
                    SpnAccess.setSelection(i);
            }
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        model = db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId,0)).getSiteInspectionNewModel();
        Log.e("onStart callled", "onStart");
        if(model != null)
        {
            Log.e("get model not null", "model not null");
            if(!TextUtils.isEmpty(model.getEinsatzstrasse()))
            {
                Log.e("get knotakt not null", "getEinsatzstrasse not null " + model.getEinsatzstrasse());
                String splite[] = model.getEinsatzstrasse().split(",");
                if(splite.length > 0) {
                    textRoad.setText(splite[0]);
                }
            }
            else
            {
                textRoad.setText("");
            }
            if(!TextUtils.isEmpty(model.getEinsatzPLZ()))
            {
                Log.e("get knotakt not null", "getEinsatzPLZ not null " + model.getEinsatzPLZ());
                textZipCode.setText(model.getEinsatzPLZ());
            }
            else
            {
                textZipCode.setText("");
            }
            if(!TextUtils.isEmpty(model.getEinsatzort()))
            {
                Log.e("get knotakt not null", "getEinsatzort not null " + model.getEinsatzort());
                textPlace.setText(model.getEinsatzort());
            }
            else
            {
                textPlace.setText("");
            }
        }
    }

}
