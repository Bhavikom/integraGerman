package de.mateco.integrAMobile.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.vision.text.Text;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.mateco.integrAMobile.Helper.AddPriceParsableClass;
import de.mateco.integrAMobile.Helper.CustomSSLFactory;
import de.mateco.integrAMobile.Helper.EinzatzInformationDataSet;
import de.mateco.integrAMobile.Helper.GlobalClass;
import de.mateco.integrAMobile.Helper.PreferencesClass;
import de.mateco.integrAMobile.Helper.PreferencesData;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.SpinnerAdapterClass;
import de.mateco.integrAMobile.base.LoadedCustomerFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.LadefahrzeugComboBoxItemModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.Pricing1BranchData;
import de.mateco.integrAMobile.model.Pricing2InsertPriceUseInformationListData;
import de.mateco.integrAMobile.model.SpinnerModel;


public class FragmentPricingDetail extends LoadedCustomerFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private String startDate, endDate;
    public static Activity activity;
   // private Language language;maharshi.mehta@mateco.de
    private MatecoPriceApplication matecoPriceApplication;
    private static Language language;
    private View rootView;
    AddPriceParsableClass parsableClass;
    PreferencesClass preferences;
    PreferencesData prerObj;

    private Menu menu;
    public static final String ROOT_URL = "http://sm.ssoft.in/StudService.svc/AddPricing";
    boolean flagAnlieferung, flagKann, flagLieferung, flagVoranmeldung, flagBenachrichtgung, flagRampena, flagSonstige,
            flagEinweisung,flagSelbstfahrer;
    String strKann ="", strVoranmeldung ="", strBenachrich ="", strSonstige ="";
    String strStartDate ="", strEndDate ="", strStartTime ="", strEndtime ="";
    int intLadefahrzeug=0;

    private int Hour;
    private int Minute;
    static final int TIME_DIALOG_ID = 0;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private static String clickedTextbox="";
    private static String clickedTextboxTime="";


    Spinner spinnerLadefahrzeug;
    CheckBox checkBoxAnlieferung, checkBoxKann, checkBoxLieferung, checkBoxVoranmeldung, checkBoxBenachrichtgung, checkBoxRampena,
            checkBoxsonstige,checkBoxEinweisung,checkBoxSelbstfahrer;
    EditText edittextKannDetail, edittextVoranmeldungDetail, edittextBenachrichDetial, edittextSonstigeDetail;
    static EditText textviewHourStart;
    static EditText textviewHourEnd;
    static EditText textviewDate1;
    static EditText textviewDate2;
    ImageButton imgbtnStartdate,imgbtnenddate;
    ImageButton imgbtnStarttime,imgbtnendtime;
    Button btnSubmit;
    ImageView imgQuestionMark1,imgQuestionMark2;
    DataBaseHandler databaseHanlder;
    public  ArrayList<SpinnerModel> arraylistSpinner = new ArrayList<SpinnerModel>();
    private ArrayList<LadefahrzeugComboBoxItemModel> arraylistLadefahrzeug =  null;
    static SpinnerAdapterClass adapter;

    private String kanr;  //kanr is kontakton
    private int kaNrOfLoadedCustomer;
    private int branchId;
    private String branchName;
    private String contactPersonNo = "", contactPerson = "";
    private String deviceType;
    private String equipmentIds,fromDate="",toDate="";
    private int rental;
    private String equipmentJson = "";

    private int countryNameId = 0;
    String Besteller_Telefon = "";
    String Besteller_Email = "";
    String Besteller_Anrede = "";
    String Besteller_Mobil = "";
    String GeratetypeId = "0";
    private int rentalDays;
    private String datesComma="";
    private double price = 0.0;
    String PriceUseInformationList="",plz="",besteller_Telefon="",besteller_Email="",besteller_Anrede="",besteller_Mobil="";
    private double gesAm = 0.0;
    DataBaseHandler db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //if (rootView == null) {
         //   rootView = inflater.inflate(R.layout.fragment_pricing_detail, container, false);
        //    super.initializeFragment(rootView);
        //} else {
          //  Log.e("at error", "not null rootview");
           // ((HomeActivity) getActivity()).getSupportActionBar().setTitle(language.getLabelPrice());
           // if (rootView.getParent() != null)
            //    ((ViewGroup) rootView.getParent()).removeView(rootView);
           // gesAm = 0.0;
       // }
        rootView = inflater.inflate(R.layout.fragment_pricing_detail, container, false);
        super.initializeFragment(rootView);

        return rootView;

    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e("enter", "enter");
    }

    @Override
    public void initializeComponents(View rootView)
    {
        super.initializeComponents(rootView);

        db = new DataBaseHandler(getActivity());
        preferences = new PreferencesClass(getActivity());
        prerObj = new PreferencesData(getActivity());
        activity = getActivity();
        if (getArguments() != null)
        {
            price = getArguments().getDouble("price");
            endDate = getArguments().getString("endDate");
            startDate = getArguments().getString("startDate");

            kanr = getArguments().getString("kanr");
            kaNrOfLoadedCustomer = getArguments().getInt("kaNrOfLoadedCustomer");
            branchId = getArguments().getInt("branchId");
            branchName = getArguments().getString("branchName");
            contactPersonNo = getArguments().getString("contactPersonNo");
            contactPerson = getArguments().getString("contactPersonName", contactPerson);
            deviceType = getArguments().getString("deviceType");
            equipmentIds = getArguments().getString("equipmentIds");
            equipmentJson = getArguments().getString("EquipmentJson");

            rental = getArguments().getInt("rental");
            rentalDays = getArguments().getInt("rentalDays");
            datesComma = getArguments().getString("dates_comma");

            gesAm = getArguments().getDouble("gesAminities");
            PriceUseInformationList = getArguments().getString("PriceUseInformationList");
            GeratetypeId = getArguments().getString("GeratetypeId");
            plz = getArguments().getString("plz");
            besteller_Telefon = getArguments().getString("Besteller_Telefon");
            besteller_Email = getArguments().getString("Besteller_Email");
            besteller_Anrede = getArguments().getString("Besteller_Anrede");
            besteller_Mobil = getArguments().getString("Besteller_Mobil");
            fromDate=getArguments().getString("fromDate");
            toDate=getArguments().getString("toDate");

        }
        matecoPriceApplication = (MatecoPriceApplication) getActivity().getApplication();
        parsableClass=preferences.getPriceData(getActivity());
        language = matecoPriceApplication.getLanguage();
        initControl();
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle(language.getLabelPrice());


        loadDataFromParsable();


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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch (item.getItemId())
        {
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
                    transaction.addToBackStack("Setting");
                    transaction.commit();
                }
                return true;
            case R.id.actionBack:
                //savePricingCustomerOrderBasicInfo();
                storedataInParsableClass();
                /*if(checkBoxSelbstfahrer.isChecked())
                {
                    preferences.saveComeFrom("selected");
                }
                else {
                    preferences.saveComeFrom("");
                }*/
                if (getFragmentManager().getBackStackEntryCount() == 0)
                {

                    getActivity().finish();

                }
                else
                {
                    getFragmentManager().popBackStack();
                }
                return true;
            case R.id.actionForward:
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
                args.putString("dates_comma",datesComma);
                args.putDouble("price", price);
                args.putString("startDate",startDate);
                args.putString("endDate",endDate);
                args.putString("fromDate",fromDate);
                args.putString("toDate",toDate);

                Gson gson = new Gson();
                Pricing2InsertPriceUseInformationListData myJson = gson.fromJson(PriceUseInformationList, Pricing2InsertPriceUseInformationListData.class);

                Log.e("before before  ", " get Einsatzort value on forward button : " + myJson.getEinsatzort());

               // Log.e(" $$$$$$$$$$$$$ "," get string from j sont string : "+myJson.getAVO()+" : "+myJson.getEinsatzort());&&&&&
                /*if(checkBoxSelbstfahrer.isChecked()){
                    ArrayList<Pricing1BranchData> branches = new ArrayList<>();
                    branches=db.getBranchList(preferences.getBranchId());
                    PriceUseInformationList=PriceUseInformationList.replace(myJson.getEinsatzort(), String.valueOf(preferences.getBranchId()));
                    PriceUseInformationList=PriceUseInformationList.replace(String.valueOf(myJson.getEinsatzPLZ()),String.valueOf(branches.get(0).getPlz()));

                    PriceUseInformationList=PriceUseInformationList.replace(String.valueOf(myJson.getEinsatzStrasse()), String.valueOf(branches.get(0).getStrasse()));
                }*/
                /*if(preferences.getComefrom().equalsIgnoreCase("selected")){
                    ArrayList<Pricing1BranchData> branches = new ArrayList<>();
                    branches=db.getBranchList(preferences.getBranchId());
                    PriceUseInformationList=PriceUseInformationList.replace(myJson.getEinsatzort(), String.valueOf(preferences.getBranchId()));
                    PriceUseInformationList=PriceUseInformationList.replace(String.valueOf(myJson.getEinsatzPLZ()),String.valueOf(branches.get(0).getPlz()));

                    PriceUseInformationList=PriceUseInformationList.replace(String.valueOf(myJson.getEinsatzStrasse()), String.valueOf(branches.get(0).getStrasse()));
                }*/

               // Pricing2InsertPriceUseInformationListData pricingInformation = new Pricing2InsertPriceUseInformationListData();



                Gson gson2 = new Gson();
                Pricing2InsertPriceUseInformationListData myJson2 = gson2.fromJson(PriceUseInformationList, Pricing2InsertPriceUseInformationListData.class);
                Log.e(" after after ", " get Einsatzort value on forward button : "+myJson2.getEinsatzort());


                args.putString("PriceUseInformationList", PriceUseInformationList);
                args.putString("GeratetypeId", GeratetypeId);

                args.putString("plz",plz.toString().trim());

                args.putString("Besteller_Telefon", Besteller_Telefon);
                args.putString("Besteller_Email", Besteller_Email);
                args.putString("Besteller_Anrede", Besteller_Anrede);
                args.putString("Besteller_Mobil", Besteller_Mobil);
                args.putDouble("gesAminities", gesAm);

                if(!TextUtils.isEmpty(textviewDate2.getText().toString()) && !TextUtils.isEmpty(textviewDate1.getText().toString())){
                    String selecteddate=textviewDate1.getText().toString().replace(".","-");
                    String seconddate = textviewDate2.getText().toString().replace(".","-");
                    Log.e(" in method 3333"," in populate date set mothod : "+selecteddate+" : "+seconddate);
                    if(compareDate1WithDate2(selecteddate,seconddate))
                    {
                        storedataInParsableClass();
                        PricingFragment3 pricingFragment3 = new PricingFragment3();
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        transaction.replace(R.id.content_frame, pricingFragment3);
                        pricingFragment3.setArguments(args);
                        transaction.addToBackStack("Gethering Activity");
                        transaction.commit();
                    }
                    else {
                        // give message here
                        showShortToast(language.getMessageEndTimeGreaterThenStartTime());
                        //GlobalClass.showToast(getActivity(),"please selecte proprer start date");
                    }
                }
                else
                {
                    storedataInParsableClass();
                    PricingFragment3 pricingFragment3 = new PricingFragment3();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.replace(R.id.content_frame, pricingFragment3);
                    pricingFragment3.setArguments(args);
                    transaction.addToBackStack("Gethering Activity");
                    transaction.commit();
                }

                //getActivity().finish();
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }
    public void initControl()
    {
        databaseHanlder = new DataBaseHandler(getActivity());

        spinnerLadefahrzeug =(Spinner)rootView.findViewById(R.id.spinnerLadefahrzeug);


        checkBoxAnlieferung =(CheckBox)rootView.findViewById(R.id.checkBoxAnlieferung);
        checkBoxKann =(CheckBox)rootView.findViewById(R.id.checkBoxKann);
        checkBoxLieferung =(CheckBox)rootView.findViewById(R.id.checkBoxLieferung);
        checkBoxVoranmeldung =(CheckBox)rootView.findViewById(R.id.checkBoxVoranmeldung);
        checkBoxBenachrichtgung =(CheckBox)rootView.findViewById(R.id.checkBoxBenachrichtgung);
        checkBoxRampena =(CheckBox)rootView.findViewById(R.id.checkBoxRampena);
        checkBoxsonstige =(CheckBox)rootView.findViewById(R.id.checkBoxsonstige);
        checkBoxEinweisung=(CheckBox)rootView.findViewById(R.id.checkBoxEinweisung);
        checkBoxSelbstfahrer=(CheckBox)rootView.findViewById(R.id.checkBoxSelbstfaher);

        edittextKannDetail =(EditText)rootView.findViewById(R.id.edittextKannDetail);
        edittextVoranmeldungDetail =(EditText)rootView.findViewById(R.id.edittextVoranmeldungDetail);
        edittextBenachrichDetial =(EditText)rootView.findViewById(R.id.edittextBenachrichDetial);
        edittextSonstigeDetail =(EditText)rootView.findViewById(R.id.edittextSonstigeDetail);

        edittextKannDetail.setEnabled(false);
        edittextVoranmeldungDetail.setEnabled(false);
        edittextBenachrichDetial.setEnabled(false);
        edittextSonstigeDetail.setEnabled(false);

        textviewHourStart =(EditText) rootView.findViewById(R.id.textviewHourStart);
        textviewHourEnd =(EditText)rootView.findViewById(R.id.textviewHourEnd);
        //textviewMinuteEnd =(TextView)rootView.findViewById(R.id.textviewMinuteEnd);

        textviewDate1=(EditText)rootView.findViewById(R.id.textviewDateStart);
        textviewDate2=(EditText)rootView.findViewById(R.id.textviewDateEnd);



        imgQuestionMark1=(ImageView)rootView.findViewById(R.id.imageviewQuetionMark1);
        imgQuestionMark2=(ImageView)rootView.findViewById(R.id.imageviewQuetionMark2);
        imgQuestionMark2.setOnClickListener(this);
        imgQuestionMark1.setOnClickListener(this);

        btnSubmit=(Button)rootView.findViewById(R.id.btnSubmit);
        imgbtnStartdate=(ImageButton) rootView.findViewById(R.id.imgBtnFromDate);
        imgbtnenddate=(ImageButton)rootView.findViewById(R.id.imgBtnToDate);

        imgbtnStarttime=(ImageButton) rootView.findViewById(R.id.imgBtnstartTime);
        imgbtnendtime=(ImageButton)rootView.findViewById(R.id.imgBtnendTime);
        imgbtnStarttime.setOnClickListener(this);
        imgbtnendtime.setOnClickListener(this);

        imgbtnStartdate.setOnClickListener(this);
        imgbtnenddate.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        textviewDate1.setOnClickListener(this);
        textviewDate2.setOnClickListener(this);
        textviewHourStart.setOnClickListener(this);
        textviewHourEnd.setOnClickListener(this);
       // textviewMinuteEnd.setOnClickListener(this);

        if(checkBoxAnlieferung.isChecked()){
            textviewDate1.setEnabled(true);
            textviewDate2.setEnabled(true);
            textviewHourStart.setEnabled(true);
            textviewHourEnd.setEnabled(true);

            imgbtnendtime.setEnabled(true);
            imgbtnStarttime.setEnabled(true);
            imgbtnenddate.setEnabled(true);
            imgbtnStartdate.setEnabled(true);
        }
        else {
            textviewDate1.setEnabled(false);
            textviewDate2.setEnabled(false);
            textviewHourStart.setEnabled(false);
            textviewHourEnd.setEnabled(false);

            imgbtnendtime.setEnabled(false);
            imgbtnStarttime.setEnabled(false);
            imgbtnenddate.setEnabled(false);
            imgbtnStartdate.setEnabled(false);
        }


        /*if(!TextUtils.isEmpty(preferences.getPriceDevice())){
            if(preferences.getPriceDevice().equalsIgnoreCase("Anhänger") || preferences.getPriceDevice().equalsIgnoreCase("LKW"))
            {
                checkBoxSelbstfahrer.setEnabled(true);
            }
            else {
                checkBoxSelbstfahrer.setEnabled(false);
            }
        }
        else {
            checkBoxSelbstfahrer.setEnabled(false);
        }*/

        checkBoxChangeEffect();

        spinnerLadefahrzeug.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                preferences.saveComboboxPos(position);
                intLadefahrzeug = arraylistLadefahrzeug.get(position).getId();
                Log.e(" spinnner value ^^^^^"," selected value of combo box : "+intLadefahrzeug);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        addToArraylist();
        arraylistLadefahrzeug=databaseHanlder.getLadefahzeg();
        // Resources passed to adapter to get image
        Resources res = getResources();

        // Create custom adapter object
        Log.e(" in outside "," arraylist ladefahrzeu size before spineer adapter : "+arraylistLadefahrzeug.size());
        adapter = new SpinnerAdapterClass(getActivity(), R.layout.spinner_rows, arraylistLadefahrzeug,res);

        // Set adapter to spinner
        spinnerLadefahrzeug.setAdapter(adapter);
        spinnerLadefahrzeug.setSelection(preferences.getComboboxPos());

        //setCurrentDateAndtime();
    }
    public void storedataInParsableClass(){
        flagAnlieferung = checkBoxAnlieferung.isChecked();
        flagKann = checkBoxKann.isChecked();
        flagLieferung = checkBoxLieferung.isChecked();
        flagVoranmeldung = checkBoxVoranmeldung.isChecked();
        flagBenachrichtgung = checkBoxBenachrichtgung.isChecked();
        flagRampena = checkBoxRampena.isChecked();
        flagSonstige = checkBoxsonstige.isChecked();
        flagEinweisung = checkBoxEinweisung.isChecked();
        if(preferences.getComefrom().equalsIgnoreCase("selected")){
            flagSelbstfahrer=true;
        }
        else {
            flagSelbstfahrer=false;
        }


        if(checkBoxKann.isChecked()){
            strKann = edittextKannDetail.getText().toString();
        }
        else {
            strKann = "";
        }

        if(checkBoxVoranmeldung.isChecked()){
            strVoranmeldung = edittextVoranmeldungDetail.getText().toString();
        }
        else {
            strVoranmeldung = "";
        }
        if(checkBoxBenachrichtgung.isChecked())
        {
            strBenachrich = edittextBenachrichDetial.getText().toString();
        } else
        {
            strBenachrich = "";
        }
        if(checkBoxsonstige.isChecked()) {
            strSonstige = edittextSonstigeDetail.getText().toString();
        }
        else {
            strSonstige = "";
        }




        // intLadefahrzeug = spinnerLadefahrzeug.getSelectedItemPosition();
        if(checkBoxLieferung.isChecked()){
            strStartDate =textviewDate1.getText().toString();
            strEndDate =textviewDate2.getText().toString();
            strStartTime = textviewHourStart.getText().toString();
            strEndtime =textviewHourEnd.getText().toString();

        }
        else {
            strStartDate ="";
            strEndDate ="";
            strStartTime = "";
            strEndtime ="";
        }


        preferences.startDate(strStartDate);
        preferences.endDate(strEndDate);
        preferences.startTime(strStartTime);
        preferences.endTime(strEndtime);
        AddPriceParsableClass parssable = new AddPriceParsableClass(String.valueOf(flagAnlieferung),String.valueOf(flagKann),
                String.valueOf(flagLieferung),String.valueOf(flagVoranmeldung),String.valueOf(flagBenachrichtgung)
                ,String.valueOf(flagRampena),
                String.valueOf(flagSonstige),String.valueOf(flagEinweisung),String.valueOf(flagSelbstfahrer),strKann,
                strVoranmeldung, strBenachrich, strSonstige, intLadefahrzeug);

        PreferencesClass.storePriceData(getActivity(), parssable);
    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onClick(View v) {
        if(v==btnSubmit){
            strKann = edittextKannDetail.getText().toString();
            strVoranmeldung = edittextVoranmeldungDetail.getText().toString();
            strBenachrich = edittextBenachrichDetial.getText().toString();
            strSonstige = edittextSonstigeDetail.getText().toString();


            flagAnlieferung = checkBoxAnlieferung.isChecked();
            flagKann = checkBoxKann.isChecked();
            flagLieferung = checkBoxLieferung.isChecked();
            flagVoranmeldung = checkBoxVoranmeldung.isChecked();
            flagBenachrichtgung = checkBoxBenachrichtgung.isChecked();
            flagRampena = checkBoxRampena.isChecked();
            flagSonstige = checkBoxsonstige.isChecked();
            flagEinweisung = checkBoxEinweisung.isChecked();
            flagSelbstfahrer=checkBoxSelbstfahrer.isChecked();

            strStartDate =textviewDate1.getText().toString();
            strEndDate =textviewDate2.getText().toString();
            strStartTime = textviewHourStart.getText().toString();
            strEndtime =textviewHourEnd.getText().toString();

            /*if(TextUtils.isEmpty(strKann))
            {
                edittextKannDetail.setError(getResources().getString(R.string.error_message));
            }
            else if(TextUtils.isEmpty(strVoranmeldung))
            {
                edittextVoranmeldungDetail.setError(getResources().getString(R.string.error_message));
            }
            else if(TextUtils.isEmpty(strBenachrich)){
                edittextBenachrichDetial.setError(getResources().getString(R.string.error_message));
            }
            else if(TextUtils.isEmpty(strSonstige)){
                edittextSonstigeDetail.setError(getResources().getString(R.string.error_message));
            }
            else if(strLadefahrzeug.equalsIgnoreCase("") || strLadefahrzeug.equalsIgnoreCase("Select")){
                Toast.makeText(MainActivity.this,"PLease select item",Toast.LENGTH_SHORT).show();
            }
            else {*/
            if(GlobalClass.isNetworkAvailable(getActivity()))
            {
                GlobalClass.showToast(getActivity(), "Network available");
                // call service here to add the data in service
                callWebservice();
            }
            else
            {
                GlobalClass.showToast(getActivity(),"NOOO Network available");
                //databaseHanlder.addTransportDetail(GlobalClass.boolToInt(flagAnlieferung),GlobalClass.boolToInt(flagKann),
                       // GlobalClass.boolToInt(flagLieferung),GlobalClass.boolToInt(flagVoranmeldung),GlobalClass.boolToInt(flagBenachrichtgung),
                       // GlobalClass.boolToInt(flagRampena),GlobalClass.boolToInt(flagSonstige),GlobalClass.boolToInt(flagEinweisung),
                       // GlobalClass.boolToInt(flagSelbstfahrer), strKann, strVoranmeldung, strBenachrich, strSonstige,strLadefahrzeug,strStartDate,strStartTime,strEndDate,strEndtime);
            }


            // }


        }
        if(v== imgbtnStartdate){
            clickedTextbox="first";
            DialogFragment newFragment = new SelectDateFragment();
            newFragment.show(getActivity().getFragmentManager(), "DatePicker");
        }
        if(v== imgbtnenddate){
            clickedTextbox="second";
            DialogFragment newFragment = new SelectDateFragment();
            newFragment.show(getActivity().getFragmentManager(), "DatePicker");
        }
        if(v==textviewDate1){
            clickedTextbox="first";
            DialogFragment newFragment = new SelectDateFragment();
            newFragment.show(getActivity().getFragmentManager(), "DatePicker");



        }
        if(v==imgbtnStarttime){
            clickedTextboxTime="first";
            DialogFragment newFragment = new SelectTimeFragment();
            newFragment.show(getActivity().getFragmentManager(), "TimePicker");
        }
        if(v==imgbtnendtime){
            clickedTextboxTime="second";
            DialogFragment newFragment = new SelectTimeFragment();
            newFragment.show(getActivity().getFragmentManager(), "TimePicker");
        }
        if(v==textviewDate2){
            clickedTextbox="second";
            DialogFragment newFragment = new SelectDateFragment();
            newFragment.show(getActivity().getFragmentManager(), "DatePicker");

        }
        if(v== textviewHourStart){
            clickedTextboxTime="first";
            DialogFragment newFragment = new SelectTimeFragment();
            newFragment.show(getActivity().getFragmentManager(), "TimePicker");

        }
        if(v==textviewHourEnd){
            clickedTextboxTime="second";
            DialogFragment newFragment = new SelectTimeFragment();
            newFragment.show(getActivity().getFragmentManager(), "TimePicker");

        }
        /*if(v== textviewMinuteStart){
            clickedTextboxTime="first";
            DialogFragment newFragment = new SelectTimeFragment();
            newFragment.show(getActivity().getFragmentManager(), "TimePicker");

        }*/
        /*if(v== textviewMinuteEnd){
            clickedTextboxTime="second";
            DialogFragment newFragment = new SelectTimeFragment();
            newFragment.show(getActivity().getFragmentManager(), "TimePicker");

        }*/
        if(v==imgQuestionMark1)
        {

        }
        if(v==imgQuestionMark2)
        {

        }
    }
    public void addToArraylist()
    {
        for (int i = 0; i < 11; i++)
        {
            final SpinnerModel model = new SpinnerModel();
            model.setCompanyName("Item "+i);
            arraylistSpinner.add(model);
        }

    }
    @SuppressWarnings("deprecation")
    public void setDate() {
        //showDialog(999);

    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {

        if(!TextUtils.isEmpty(clickedTextbox)){
            if(clickedTextbox.equalsIgnoreCase("first")){
                textviewDate1.setText(new StringBuilder().append(day).append(".")
                        .append(month).append(".").append(year));
            }
            else if(clickedTextbox.equalsIgnoreCase("second")) {
                textviewDate2.setText(new StringBuilder().append(day).append(".")
                        .append(month).append(".").append(year));
            }

        }
        else {
            textviewDate1.setText(new StringBuilder().append(day).append(".")
                    .append(month).append(".").append(year));
            textviewDate2.setText(new StringBuilder().append(day).append(".")
                    .append(month).append(".").append(year));
        }

    }
    /** Callback received when the user "picks" a time in the dialog */
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Hour = hourOfDay;
                    Minute = minute;
                    updateDisplay();

                }
            };
    /** Updates the time in the TextView */
    private void updateDisplay() {

        if(!TextUtils.isEmpty(clickedTextboxTime)){
            if(clickedTextboxTime.equalsIgnoreCase("first")){
                textviewHourStart.setText(new StringBuilder().append(pad(Hour)));
                //textviewMinuteStart.setText(new StringBuilder().append(pad(Minute)));
            }
            else {
                textviewHourEnd.setText(new StringBuilder().append(pad(Hour)));
                //textviewMinuteEnd.setText(new StringBuilder().append(pad(Minute)));
            }

        }
        else {
            textviewHourStart.setText(new StringBuilder().append(pad(Hour)));
            //textviewMinuteStart.setText(new StringBuilder().append(pad(Minute)));
            textviewHourEnd.setText(new StringBuilder().append(pad(Hour)));
            //textviewMinuteEnd.setText(new StringBuilder().append(pad(Minute)));
        }

    }


    public void checkBoxChangeEffect(){
        checkBoxKann.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked){
                    edittextKannDetail.setEnabled(true);
                }
                else {
                    edittextKannDetail.setText("");
                    edittextKannDetail.setEnabled(false);

                }
            }
        });
        checkBoxLieferung.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    setDefaultTimeandDate();
                    textviewDate1.setEnabled(true);
                    textviewDate2.setEnabled(true);
                    textviewHourEnd.setEnabled(true);
                    textviewHourStart.setEnabled(true);
                    imgbtnendtime.setEnabled(true);
                    imgbtnStartdate.setEnabled(true);
                    imgbtnenddate.setEnabled(true);
                    imgbtnStarttime.setEnabled(true);
                }
                else {

                    textviewDate1.setText("");
                    textviewDate2.setText("");
                    textviewHourEnd.setText("");
                    textviewHourStart.setText("");


                    textviewDate1.setEnabled(false);
                    textviewDate2.setEnabled(false);
                    textviewHourEnd.setEnabled(false);
                    textviewHourStart.setEnabled(false);
                    imgbtnendtime.setEnabled(false);
                    imgbtnStartdate.setEnabled(false);
                    imgbtnenddate.setEnabled(false);
                    imgbtnStarttime.setEnabled(false);
                }
            }
        });
        checkBoxVoranmeldung.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked){
                    edittextVoranmeldungDetail.setEnabled(true);
                }
                else {
                    edittextVoranmeldungDetail.setText("");
                    edittextVoranmeldungDetail.setEnabled(false);
                }
            }
        });
        checkBoxsonstige.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked){
                    edittextSonstigeDetail.setEnabled(true);
                }
                else {
                    edittextSonstigeDetail.setText("");
                    edittextSonstigeDetail.setEnabled(false);
                }
            }
        });
        checkBoxBenachrichtgung.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked){
                    edittextBenachrichDetial.setEnabled(true);
                }
                else {
                    edittextBenachrichDetial.setText("");
                    edittextBenachrichDetial.setEnabled(false);
                }
            }
        });
    }
    public void setCurrentDateAndtime(){
        final  Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        textviewDate1.setText(pad(date) + "-" + pad(month) + "-" + pad(year));
        textviewHourStart.setText(pad(hour));
        //textviewMinuteStart.setText(pad(minute));

        textviewDate2.setText(pad(date) + "-" + pad(month) + "-" + pad(year));
        textviewHourEnd.setText(pad(hour));
        //textviewMinuteEnd.setText(pad(minute));
    }
    private  class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
    {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }


        public void populateSetDate(int day, int month, int year) {
            Log.e(" in method"," in populate date set mothod : "+day+" : "+month+" : "+year+" : "+clickedTextbox);
            if(!TextUtils.isEmpty(clickedTextbox)){
                if(clickedTextbox.equalsIgnoreCase("first")){
                    Log.e(" in method 22222"," in populate date set mothod : "+day+" : "+month+" : "+year+" : "+clickedTextbox);
                    if(!TextUtils.isEmpty(textviewDate2.getText().toString())){
                        String selecteddate=day+"-"+month+"-"+year;
                        String seconddate = textviewDate2.getText().toString().replace(".","-");
                        Log.e(" in method 3333"," in populate date set mothod : "+selecteddate+" : "+seconddate);
                        //if(compareDate1WithDate2(selecteddate,seconddate))
                        //{
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                            String date=day+"."+month+"."+year;

                            Date start=null;
                            try {
                                start = dateFormat.parse(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String showeddate=dateFormat.format(start);
                            textviewDate1.setText(showeddate);
                        //}
                        //else {
                            // give message here
                           // showShortToast(language.getMessageEndTimeGreaterThenStartTime());
                            //GlobalClass.showToast(getActivity(),"please selecte proprer start date");
                        //}
                    }
                    else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                        String date=day+"."+month+"."+year;

                        Date start2=null;
                        try {
                            start2 = dateFormat.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String showeddate=dateFormat.format(start2);
                        textviewDate1.setText(showeddate);
                    }

                }
                else if(clickedTextbox.equalsIgnoreCase("second")) {
                    if(!TextUtils.isEmpty(textviewDate1.getText().toString())){
                        String selecteddate=day+"-"+month+"-"+year;
                        String seconddate = textviewDate1.getText().toString().replace(".","-");
                        Log.e(" in method 44444"," in populate date set mothod : "+selecteddate+" : "+seconddate);
                        //if(compareDate2WithDate1(seconddate,selecteddate))
                        //{
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                            String date=day+"."+month+"."+year;

                            Date end=null;
                            try {
                                end = dateFormat.parse(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String showeddate=dateFormat.format(end);
                            textviewDate2.setText(showeddate);
                        //}
                        //else {
                            //showShortToast(language.getMessageEndTimeGreaterThenStartTime());
                            //GlobalClass.showToast(getActivity(),"please selecte proprer end date");
                            // give message here
                        //}
                    }
                    else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                        String date=day+"."+month+"."+year;

                        Date end=null;
                        try {
                            end = dateFormat.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String showeddate=dateFormat.format(end);
                        textviewDate2.setText(showeddate);
                    }
                }

            }
        }


        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            Log.e(" on date set"," populatesetdate clicked : "+dayOfMonth+" : "+monthOfYear+" : "+year);
            populateSetDate(dayOfMonth, monthOfYear+1, year);
        }
    }
    public  void showShortToast(String message)
    {
        if(activity != null)
        {
            LayoutInflater inflater = activity.getLayoutInflater();
            if(inflater != null)
            {
                View layout = inflater.inflate(R.layout.toast_custom, (ViewGroup) rootView.findViewById(R.id.toast_layout_root));
                TextView text = (TextView) layout.findViewById(R.id.text);
                text.setText(message+"");
                Toast toast = new Toast(activity);
                toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
            }
        }
    }
    public  static Boolean compareDate1WithDate2(String date1,String date2)
    {
        Boolean flag=false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date dateStart = sdf.parse(date1);
            Date dateEnd = sdf.parse(date2);

            if(dateStart.equals(dateEnd))
            {
                flag=true;
            }

            if(dateStart.before(dateEnd)){
                flag=true;
            }

            if(dateStart.after(dateEnd)){
                flag=false;
            }
        }
        catch (Exception e){

        }

        return  flag;

    }
    public static  Boolean compareTime(String startTIme,String endTime){
        Boolean flag=false;
        String pattern = "dd.MM.yyyy HH:mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date start=null;
        Date end=null;
        try {
            start = dateFormat.parse(startTIme);
            end = dateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(start.after(end)){
            flag=false;
        }
        else{
            flag=true;
        }

        return flag;

    }
    public  static Boolean compareDate2WithDate1(String date1,String date2)
    {
        Boolean flag=false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date dateStart = sdf.parse(date1);
            Date dateEnd = sdf.parse(date2);

            if(dateEnd.equals(dateStart))
            {
                flag=true;
            }

            if(dateEnd.before(dateStart)){
                flag=false;
            }

            if(dateEnd.after(dateStart)){
                flag=true;
            }
        }
        catch (Exception e){

        }

        return  flag;

    }
    public static class SelectTimeFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);


            return  new TimePickerDialog(getActivity(),this,hour,minute,true);
            //return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }
        public void populateSetTime(int Hour,int Minute){
            if(!TextUtils.isEmpty(clickedTextboxTime)){
                if(clickedTextboxTime.equalsIgnoreCase("first")){

                    textviewHourStart.setText(new StringBuilder().append(pad(Hour))+":"+new StringBuilder().append(pad(Minute)));


                    if(textviewHourEnd.getText().toString().equalsIgnoreCase("")){
                        /*SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                        Date dateT=null;
                        dateT.setHours(Hour);
                        dateT.setMinutes(Minute);
                        String currentDateandTime = sdf.format(dateT);*/

                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.HOUR,Hour);
                        cal.set(Calendar.HOUR,Minute);


                        Date date = cal.getTime();
                        try {
                            //date = formatter.parse(currentDateandTime);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        calendar.add(Calendar.HOUR_OF_DAY, 1);
                        Log.e(" $$$$$$$$$ "," get calendar time : "+calendar.getTime());

                        Date newdate = calendar.getTime();
                        Calendar calTemp = Calendar.getInstance();
                        calTemp.setTime(newdate);

                        int hours;
                        if(Hour==23){
                            hours=00;
                        }
                        else {
                            hours=Hour+1;
                        }
                        textviewHourEnd.setText(pad(hours)+":"+pad(Minute));
                    }


                    //textviewMinuteStart.setText(new StringBuilder().append(pad(Minute)));
                }
                else {
                    textviewHourEnd.setText(new StringBuilder().append(pad(Hour))+":"+new StringBuilder().append(pad(Minute)));
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR,Hour);
                    calendar.set(Calendar.MINUTE,Minute);
                    calendar.add(Calendar.HOUR, -1);
                    Log.e(" $$$$$$$$$ "," get calendar time : "+calendar.getTime());

                    if(textviewHourStart.getText().toString().equalsIgnoreCase("")){
                        int hours;
                        if(Hour==00 || Hour==0)
                        {
                            hours=23;
                        }
                        else {
                            hours=Hour-1;
                        }
                        textviewHourStart.setText(pad(hours)+":"+pad(Minute));
                    }


                    //textviewMinuteEnd.setText(new StringBuilder().append(pad(Minute)));
                }

            }
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            populateSetTime(hourOfDay,minute);
        }
    }
    public void setDefaultTimeandDate()
    {
        if(textviewDate1.getText().toString().equalsIgnoreCase(""))
        {
            if(fromDate.equalsIgnoreCase("") && toDate.equalsIgnoreCase("")){
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                String dateString = formatter.format(new Date());
                textviewDate1.setText(dateString);
            }
            else {
                textviewDate1.setText(fromDate);
            }

        }
        if(textviewDate2.getText().toString().equalsIgnoreCase(""))
        {
            if(fromDate.equalsIgnoreCase("") && toDate.equalsIgnoreCase("")){
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                String dateString = formatter.format(new Date());
                textviewDate2.setText(dateString);
            }
            else {
                textviewDate2.setText(toDate);
            }


        }
        if(textviewHourStart.getText().toString().equalsIgnoreCase(""))
        {
            Calendar calendar = Calendar.getInstance();
            // Set the time and date information and display it.
            calendar.set(Calendar.HOUR, 07);
            calendar.set(Calendar.MINUTE, 00);
            textviewHourStart.setText(pad(calendar.get(Calendar.HOUR))+":"+pad(calendar.get(Calendar.MINUTE)));
        }
        if(textviewHourEnd.getText().toString().equalsIgnoreCase(""))
        {
            Calendar calendar = Calendar.getInstance();
            // Set the time and date information and display it.
            calendar.set(Calendar.HOUR, 17);
            calendar.set(Calendar.MINUTE, 00);
            textviewHourEnd.setText("17"+":"+pad(calendar.get(Calendar.MINUTE)));
        }

    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
    public void loadDataFromParsable(){
        Log.e("","all varialbe before set data from parsable  kann : "+parsableClass.strKann +" voranme: "+strVoranmeldung);
        if(!TextUtils.isEmpty(parsableClass.flagKann)){
            checkBoxKann.setChecked(Boolean.parseBoolean(parsableClass.flagKann));
        }
        if(!TextUtils.isEmpty(parsableClass.flagLieferung)){
            checkBoxLieferung.setChecked(Boolean.parseBoolean(parsableClass.flagLieferung));
        }
        if(!TextUtils.isEmpty(parsableClass.flagVoranmeldung)){
            checkBoxVoranmeldung.setChecked(Boolean.parseBoolean(parsableClass.flagVoranmeldung));
        }
        if(!TextUtils.isEmpty(parsableClass.flagBenachrichtgung)){
            checkBoxBenachrichtgung.setChecked(Boolean.parseBoolean(parsableClass.flagBenachrichtgung));
        }
        if(!TextUtils.isEmpty(parsableClass.flagRampena)){
            checkBoxRampena.setChecked(Boolean.parseBoolean(parsableClass.flagRampena));
        }
        if(!TextUtils.isEmpty(parsableClass.flagSonstige)){
            checkBoxsonstige.setChecked(Boolean.parseBoolean(parsableClass.flagSonstige));
        }
        if(!TextUtils.isEmpty(parsableClass.flagEinweisung)){
            checkBoxEinweisung.setChecked(Boolean.parseBoolean(parsableClass.flagEinweisung));
        }
        if(!TextUtils.isEmpty(parsableClass.flagSelbstfahrer)){
            checkBoxSelbstfahrer.setChecked(Boolean.parseBoolean(parsableClass.flagSelbstfahrer));
        }
        if(!TextUtils.isEmpty(parsableClass.strKann)){
            edittextKannDetail.setText(parsableClass.strKann);
        }
        else {
            edittextKannDetail.setText("");
        }
        if(!TextUtils.isEmpty(parsableClass.strVoranmeldung)){
            edittextVoranmeldungDetail.setText(parsableClass.strVoranmeldung);
        }
        else {
            edittextVoranmeldungDetail.setText("");
        }
        if(!TextUtils.isEmpty(parsableClass.strBenachrich)){
            edittextBenachrichDetial.setText(parsableClass.strBenachrich);
        }
        else {
            edittextBenachrichDetial.setText("");
        }
        if(!TextUtils.isEmpty(parsableClass.strSonstige)){
            edittextSonstigeDetail.setText(parsableClass.strSonstige);
        }
        else {
            edittextSonstigeDetail.setText("");
        }




        try {
            if(!TextUtils.isEmpty(preferences.getstartDate())){
                if(fromDate.equalsIgnoreCase("")){
                    if(preferences.getstartDate().equalsIgnoreCase("null")){
                        textviewDate1.setText("");
                    }
                    else {
                        textviewDate1.setText(preferences.getstartDate());
                    }
                }
                else {
                    textviewDate1.setText(fromDate);
                }


            }
            if(!TextUtils.isEmpty(preferences.getendDate())){
                if(toDate.equalsIgnoreCase("")){
                    if(preferences.getendDate().equalsIgnoreCase("null")){
                        textviewDate2.setText("");
                    }
                    else {
                        textviewDate2.setText(preferences.getendDate());
                    }
                }
                else {
                    textviewDate2.setText(toDate);
                }


            }

            if(!TextUtils.isEmpty(preferences.getstartTime())){
                if(preferences.getstartTime().equalsIgnoreCase("null")){
                    textviewHourStart.setText("");
                }
                else {
                    textviewHourStart.setText(preferences.getstartTime());
                }
                //textviewHourStart.setText(parsableClass.strStartTime.substring(0, 2));
                //textviewMinuteStart.setText(Math.max(parsableClass.strStartTime.length() - 2, 0));
                //textviewMinuteStart.setText(parsableClass.strStartTime.substring(parsableClass.strStartTime.length() - 2));
            }
            if(!TextUtils.isEmpty(preferences.getendTime())){
                if(preferences.getendTime().equalsIgnoreCase("null")){
                    textviewHourEnd.setText("");
                }
                else {
                    textviewHourEnd.setText(preferences.getendTime());
                }
                //textviewHourEnd.setText(parsableClass.strEndtime.substring(0,2));
                //textviewMinuteEnd.setText(parsableClass.strEndtime.substring(parsableClass.strEndtime.length() - 2));
            }

        }
        catch (Exception e){
            Log.e(" exeexe"," exception while date operation "+e.toString());
        }

    }


    public void callWebservice()
    {
        try{
            GlobalClass.showProgressDialog(getActivity(),language);
            new addPricing_api().execute();

        }
        catch (Exception e){

        }


    }
    public class addPricing_api extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... arg0)
        {
            HttpClient httpclient = CustomSSLFactory.getNewHttpClient();
            HttpPost httppost = new HttpPost(ROOT_URL);

            MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            try
            {


                multipartEntity.addPart("isAnlieferung", new StringBody(URLEncoder.encode(String.valueOf(flagAnlieferung), "UTF-8")));
                multipartEntity.addPart("isKann", new StringBody(URLEncoder.encode(String.valueOf(flagAnlieferung), "UTF-8")));
                multipartEntity.addPart("isLieferung", new StringBody(URLEncoder.encode(String.valueOf(flagAnlieferung), "UTF-8")));
                multipartEntity.addPart("isVoranmeldung", new StringBody(URLEncoder.encode(String.valueOf(flagAnlieferung), "UTF-8")));
                multipartEntity.addPart("isBenachrichtgung", new StringBody(URLEncoder.encode(String.valueOf(flagAnlieferung), "UTF-8")));
                multipartEntity.addPart("isRampena", new StringBody(URLEncoder.encode(String.valueOf(flagAnlieferung), "UTF-8")));
                multipartEntity.addPart("isSonstige", new StringBody(URLEncoder.encode(String.valueOf(flagAnlieferung), "UTF-8")));
                multipartEntity.addPart("isEinweisung", new StringBody(URLEncoder.encode(String.valueOf(flagAnlieferung), "UTF-8")));
                multipartEntity.addPart("isSelbstfahrer", new StringBody(URLEncoder.encode(String.valueOf(flagAnlieferung), "UTF-8")));
                multipartEntity.addPart("Kann", new StringBody(URLEncoder.encode(strKann, "UTF-8")));
                multipartEntity.addPart("Voranmeldung", new StringBody(URLEncoder.encode(strVoranmeldung, "UTF-8")));
                multipartEntity.addPart("Benachrich", new StringBody(URLEncoder.encode(strBenachrich, "UTF-8")));
                multipartEntity.addPart("Sonstige", new StringBody(URLEncoder.encode(strSonstige, "UTF-8")));
                multipartEntity.addPart("Ladefahrzeug", new StringBody(URLEncoder.encode(String.valueOf(intLadefahrzeug), "UTF-8")));
                multipartEntity.addPart("startDate", new StringBody(URLEncoder.encode(strStartDate, "UTF-8")));
                multipartEntity.addPart("startTime", new StringBody(URLEncoder.encode(strStartTime, "UTF-8")));
                multipartEntity.addPart("endDate", new StringBody(URLEncoder.encode(strEndDate, "UTF-8")));
                multipartEntity.addPart("endTime", new StringBody(URLEncoder.encode(strEndtime, "UTF-8")));
            }
            catch (UnsupportedEncodingException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.e("@@@@@"," in catch while multipart : "+e.toString());
            }


            try
            {
                httppost.setEntity(multipartEntity);
                HttpResponse response = httpclient.execute(httppost);
                String responseText=EntityUtils.toString(response.getEntity());
                Log.e("##########"," response while service calling :"+responseText);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                Log.e("$$$$", " in catch while multipart : " + e.toString());
            }
            return null;

        }
        @Override
        protected void onPostExecute(String result)
        {
            GlobalClass.dismissProgressDialog();

        }
        @Override
        protected void onPreExecute()
        {


        }

        @Override
        protected void onProgressUpdate(Void... values)
        {

        }

    }

    public HttpEntity addToMultipart(HttpEntity reqEntity)
    {
       // MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);



        try
        {
            multipartEntity.addPart("isAnlieferung", new StringBody(String.valueOf(flagAnlieferung)));
            multipartEntity.addPart("isKann", new StringBody(String.valueOf(flagAnlieferung)));
            multipartEntity.addPart("isLieferung", new StringBody(String.valueOf(flagAnlieferung)));
            multipartEntity.addPart("isVoranmeldung", new StringBody(String.valueOf(flagAnlieferung)));
            multipartEntity.addPart("isBenachrichtgung", new StringBody(String.valueOf(flagAnlieferung)));
            multipartEntity.addPart("isRampena", new StringBody(String.valueOf(flagAnlieferung)));
            multipartEntity.addPart("isSonstige", new StringBody(String.valueOf(flagAnlieferung)));
            multipartEntity.addPart("isEinweisung", new StringBody(String.valueOf(flagAnlieferung)));
            multipartEntity.addPart("isSelbstfahrer", new StringBody(String.valueOf(flagAnlieferung)));
            multipartEntity.addPart("Kann", new StringBody(strKann));
            multipartEntity.addPart("Voranmeldung", new StringBody(strVoranmeldung));
            multipartEntity.addPart("Benachrich", new StringBody(strBenachrich));
            multipartEntity.addPart("Sonstige", new StringBody(strSonstige));
            multipartEntity.addPart("Ladefahrzeug", new StringBody(String.valueOf(intLadefahrzeug)));
            multipartEntity.addPart("startDate", new StringBody(strStartDate));
            multipartEntity.addPart("startTime", new StringBody(strStartTime));
            multipartEntity.addPart("endDate", new StringBody(strEndDate));
            multipartEntity.addPart("endTime", new StringBody(strEndtime));
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.v(""," in catch while multipart : "+e.toString());
        }

        reqEntity = multipartEntity;

        return reqEntity;

    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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

}
