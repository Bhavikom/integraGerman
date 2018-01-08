    package de.mateco.integrAMobile.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.ParseException;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.mateco.integrAMobile.Helper.AddPriceParsableClass;
import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.GlobalMethods;
import de.mateco.integrAMobile.Helper.NestedListView;
import de.mateco.integrAMobile.Helper.PreferencesClass;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.Pricing3LostSaleDataAdapter;
import de.mateco.integrAMobile.asyncTask.AsyncTaskWithAuthorizationHeaderPost;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.LoadedCustomerFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.ContactPersonModel;
import de.mateco.integrAMobile.model.CustomerModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LoginPersonModel;
import de.mateco.integrAMobile.model.Pricing1EquipmentInsertData;
import de.mateco.integrAMobile.model.Pricing2InsertPriceUseInformationListData;
import de.mateco.integrAMobile.model.Pricing3GetDetailRequestModel;
import de.mateco.integrAMobile.model.Pricing3InsertData;
import de.mateco.integrAMobile.model.Pricing3LostSaleData;
import de.mateco.integrAMobile.model.Pricing3LostSaleInsertData;
import de.mateco.integrAMobile.model.PricingCustomerOrderBasicInfo;
import de.mateco.integrAMobile.model.PricingScreen3ObjectList;

    public class PricingFragment3 extends LoadedCustomerFragment
    {
        LinearLayout linearListview,linearTop,linearMain;
        LinearLayout.LayoutParams paramListview;
        LinearLayout.LayoutParams paramTop;

        private String startDate, endDate,fromDate="",toDate="";
        PreferencesClass preferences;
        AddPriceParsableClass parsableClass;
        private MatecoPriceApplication matecoPriceApplication;
        private Language language;
        private View rootView;
        private Menu menu;
        private TextView txtCost, txtCost1, txtHaftb, txtHandllingFee, txtTotal, txtTransport1, txtEquipmentRent, txtToll, txtGesAmenities, txtSatntag, txtServicePackages, txtBranch, txtHGRP, txtMD, txtRentalPrice, txtSB, txtHF, txtTransport, txtApproach, txtDeparture, txtAdditionalCargoPrice, txtFlateRate, txtNote, txtLiabilityLimitation,txtBest,txtHb,txtSP;
        private CheckBox chkCalendarDaily, chkInrinsicActivity, chkHandllingFeeP2, chkServicePackagesP2,chkGenehmi;
        private Button btnCache, btnLostSale, btnContract, btnOffer, btnVerbalOffer;
        private EditText etApproachP2, etAdditionalCargoPriceP2, etDepartureP2, etFlatRateP2, etCostP2, etGesAmenitiesP2, etHaftbP2, etSatntagP2, etTollP2, etTotalP2, etNoteP2, etTranspotP2,
                etEuipmentRentP2;
        private RadioGroup radioGrpSB;
        private RadioButton rbSB1000, rbSB2000, rbSB3000,rbSB500;
        private NestedListView lvPricing3LostSaleListView;
        //ArrayList<Pricing3LostSaleData> rowLostSaleItems;
        private ArrayList<Pricing3LostSaleData> lablesLostSale;
        final ArrayList<Integer> selectedEquipments = new ArrayList<>();
        private Pricing3LostSaleDataAdapter lostSaleAdapter;
        private String kanr;
        private int kaNrOfLoadedCustomer;
        private int branchId;
        private String deviceType;
        private String branchName;
        private String contactPerson = "", contactPersonNo = "";
        private String equipmentIds;
        private String equipmentJson;
        private int rental;
        private int rentalDaysWithoutSatSun,rentalDaysWithSatSun;
        private String datesComma="";
        private String GeratetypeId = "";
        private double price = 0, gesAminities = 0, haftb;
        private String plz;
        private String customer_contactNo = "";
        private String customer_KundenNr = "";
        private DataBaseHandler db;
        private double SB, hf, sp;
        private String hfStastus = "", spStatus = "";
        private int WarenkorbartBtnPressIds = 0;
        private int AbsagegrundIds = 0;
        private int totalCountofLostSaleItem = 0;
        private ArrayList<Pricing3InsertData> pricingInser = null;

        private TextWatcher twApproach, twAdditionalCargoPrice, twDeparture, twFlatRate, twCost, twGesAmenities, twTransport, twEquipment,
                twHaftb, twsatntagp2, twToll, twOtherDLGP3;
        private String LostsaleUId = "";
        private int chkCounter = 0;
        String PriceUseInformationList = "";
        /* Lost Sale Dialog Param*/
        CheckBox chkLogisticsDLGP3, chkAvailabilityDLGP3, chkPriceDLGP3, chkGeratermieteDLGP3, chkLimitationOfLiabilityDLGP3, chkTransportDLGP3, chkTermsofPaymentDLGP3, chkPaysNoAdvancePaymentDLGP3, chkCashDiscountDLGP3, chkTheCustomerHasChosenAnAlternativeMtecoUnitDLGP3, chkTheCustomerDoesNotHaveNeedLessDLGP3, chkTheCustomerHasNotReceivedTheOrderDLGP3, chkOtherDLGP3;
        TextView txtPriceDLGP3, txtCompetitorDLGP3, txtCompetitor1DLGP3,txtGenehmigungsgebiet;
        EditText etGeratermietePriceDLGP3, etGeratermieteCompititerDLGP3, etLimitationOfLiabilityPriceDLGP3, etLimitationOfLiabilityCompititerDLGP3, etTransportPriceDLGP3, etTransportCompititerDLGP3, etTheCustomerHasNotReceivedTheOrderCompititerDLGP3, etOtherDLGP3;
        final ArrayList<Integer> AbsagegrundId = new ArrayList<>();
        final ArrayList<String> WarenkorbIds = new ArrayList<>();
        final ArrayList<Double> AbsPreis = new ArrayList<>();
        final ArrayList<String> Wettbewerber = new ArrayList<>();
        final ArrayList<String> Notiz = new ArrayList<>();

        String besteller_Telefon = "";
        String besteller_Email = "";
        String besteller_Anrede = "";
        String besteller_Mobil = "";

        private ContactPersonModel ContactPerson = new ContactPersonModel();
        String EinsatzinformationId = "";
        String jsonOfEqi = "";
        /**********20161114*******/
        ArrayList<String> jsonOfEqiForInsert = new ArrayList<>();
        /**********20161114*******/
        private PricingScreen3ObjectList pricingScreen;
        private BasicAsyncTaskGetRequest asyncTask;
        boolean isGenehmigungsgebiet=false;
        /*@Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            if(savedInstanceState!=null)
            {
                String app=savedInstanceState.getString("approach");
                Log.e(" sdfsdfsd"," approach string "+app);
            }
        }*/

        @Override
        public void onPause() {
            super.onPause();
            Log.e(" pause "," on pause called ");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            rootView = inflater.inflate(R.layout.fragment_pricing_3, container, false);
            super.initializeFragment(rootView);


            return rootView;
        }

        @Override
        public void initializeComponents(View rootView)
        {
            super.initializeComponents(rootView);
            if (getArguments() != null)
            {
                endDate = getArguments().getString("endDate");
                startDate = getArguments().getString("startDate");

                kanr = getArguments().getString("kanr");
                kaNrOfLoadedCustomer = getArguments().getInt("kaNrOfLoadedCustomer");
                branchId = getArguments().getInt("branchId");
                Log.e(" in last fragment : "," branchId Id value : "+ branchId);
                branchName = getArguments().getString("branchName");
                contactPersonNo = getArguments().getString("contactPersonNo");
                contactPerson = getArguments().getString("contactPersonName", contactPerson);
                deviceType = getArguments().getString("deviceType");
                equipmentIds = getArguments().getString("equipmentIds");
                equipmentJson = getArguments().getString("EquipmentJson");

                rental = getArguments().getInt("rental");
                rentalDaysWithoutSatSun = getArguments().getInt("rentalDays");
                datesComma = getArguments().getString("dates_comma");
                price = getArguments().getDouble("price");
                gesAminities = getArguments().getDouble("gesAminities");
                PriceUseInformationList = getArguments().getString("PriceUseInformationList");
                GeratetypeId = getArguments().getString("GeratetypeId");
                Log.e(" initiiallly "," geratetypeId value in fragment 3 : "+GeratetypeId);
                plz = getArguments().getString("plz");
                besteller_Telefon = getArguments().getString("Besteller_Telefon");
                besteller_Email = getArguments().getString("Besteller_Email");
                besteller_Anrede = getArguments().getString("Besteller_Anrede");
                besteller_Mobil = getArguments().getString("Besteller_Mobil");
                fromDate=getArguments().getString("fromDate");
                toDate=getArguments().getString("toDate");

            }
            preferences = new PreferencesClass(getActivity());
            parsableClass=preferences.getPriceData(getActivity());
            Log.e(" 3 ###"," get value from parsable class : "+parsableClass.flagEinweisung+" : "+parsableClass.strKann+" : ");
            Gson gson = new Gson();
            Pricing2InsertPriceUseInformationListData myJson = gson.fromJson(PriceUseInformationList, Pricing2InsertPriceUseInformationListData.class);

            Log.e(" $$$$$$$$$$$$$ "," get string from j sont string : "+myJson.getAVO()+" : "+myJson.getEinsatzort());

            db = new DataBaseHandler(getActivity());
            lablesLostSale = new ArrayList<Pricing3LostSaleData>();

            matecoPriceApplication = (MatecoPriceApplication) getActivity().getApplication();
            language = matecoPriceApplication.getLanguage();

            linearListview = (LinearLayout)rootView.findViewById(R.id.linearListview);
            linearTop = (LinearLayout)rootView.findViewById(R.id.linearTop);
            linearMain = (LinearLayout) rootView.findViewById(R.id.linearMain);

            etApproachP2 = (EditText) rootView.findViewById(R.id.etApproachP2);
            etAdditionalCargoPriceP2 = (EditText) rootView.findViewById(R.id.etAdditionalCargoPriceP2);
            etDepartureP2 = (EditText) rootView.findViewById(R.id.etDepartureP2);
            etFlatRateP2 = (EditText) rootView.findViewById(R.id.etFlatRateP2);
            etCostP2 = (EditText) rootView.findViewById(R.id.etCostP2);
            etTranspotP2 = (EditText) rootView.findViewById(R.id.etTranspotP2);
            etGesAmenitiesP2 = (EditText) rootView.findViewById(R.id.etGesAmenitiesP2);
            etHaftbP2 = (EditText) rootView.findViewById(R.id.etHaftbP2);
            etEuipmentRentP2 = (EditText) rootView.findViewById(R.id.etEuipmentRentP2);
            etSatntagP2 = (EditText) rootView.findViewById(R.id.etSatntagP2);
            etSatntagP2.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2)});
            etTollP2 = (EditText) rootView.findViewById(R.id.etTollP2);
            etTotalP2 = (EditText) rootView.findViewById(R.id.etTotalP2);
            etNoteP2 = (EditText) rootView.findViewById(R.id.etNoteP2);

            txtGenehmigungsgebiet = (TextView)rootView.findViewById(R.id.txtGenehmigungsgebiet);
            txtLiabilityLimitation = (TextView) rootView.findViewById(R.id.txtLiabilityLimitation);
            txtCost = (TextView) rootView.findViewById(R.id.txtCost);
            txtCost1 = (TextView) rootView.findViewById(R.id.txtPrice);
            txtHaftb = (TextView) rootView.findViewById(R.id.txtHaftb);
            txtHandllingFee = (TextView) rootView.findViewById(R.id.txtHandllingFee);
            txtTotal = (TextView) rootView.findViewById(R.id.txtTotal);
            txtTransport1 = (TextView) rootView.findViewById(R.id.txtTransportCost);
            txtEquipmentRent = (TextView) rootView.findViewById(R.id.txtEquipmentRent);
            txtToll = (TextView) rootView.findViewById(R.id.txtToll);
            txtGesAmenities = (TextView) rootView.findViewById(R.id.txtGesAmenities);
            txtSatntag = (TextView) rootView.findViewById(R.id.txtSatntag);
            txtServicePackages = (TextView) rootView.findViewById(R.id.txtServicePackages);
            txtBranch = (TextView) rootView.findViewById(R.id.txtBranch);
            txtHGRP = (TextView) rootView.findViewById(R.id.txtHGRP);

            txtBest = (TextView) rootView.findViewById(R.id.txtBest);
            txtHb = (TextView) rootView.findViewById(R.id.txtHB);
            txtSP = (TextView) rootView.findViewById(R.id.txtSP);

            txtMD = (TextView) rootView.findViewById(R.id.txtMD);
            txtRentalPrice = (TextView) rootView.findViewById(R.id.txtRentalPrice);
            txtSB = (TextView) rootView.findViewById(R.id.txtSB);
            txtHF = (TextView) rootView.findViewById(R.id.txtHF);
            txtNote = (TextView) rootView.findViewById(R.id.txtNote);


            txtTransport = (TextView) rootView.findViewById(R.id.txtTransport);
            txtApproach = (TextView) rootView.findViewById(R.id.txtApproach);
            txtDeparture = (TextView) rootView.findViewById(R.id.txtDeparture);
            txtAdditionalCargoPrice = (TextView) rootView.findViewById(R.id.txtAdditionalCargoPrice);
            txtFlateRate = (TextView) rootView.findViewById(R.id.txtFlateRate);

            chkCalendarDaily = (CheckBox) rootView.findViewById(R.id.chkCalendarDailyP2);
            chkCalendarDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    sumOfAll();
                }
            });
            chkInrinsicActivity = (CheckBox) rootView.findViewById(R.id.chkInrinsicActivityP2);
            chkHandllingFeeP2 = (CheckBox) rootView.findViewById(R.id.chkHandllingFeeP2);
            chkServicePackagesP2 = (CheckBox) rootView.findViewById(R.id.chkServicePackagesP2);
            chkGenehmi=(CheckBox)rootView.findViewById(R.id.chkGenehmigungsgebiet);
            chkGenehmi.setChecked(false);

            radioGrpSB = (RadioGroup) rootView.findViewById(R.id.radioGrpSB);
            rbSB1000 = (RadioButton) radioGrpSB.findViewById(R.id.rdBtnSB1000P3);
            rbSB2000 = (RadioButton) radioGrpSB.findViewById(R.id.rdBtnSB2000P3);
            rbSB3000 = (RadioButton) radioGrpSB.findViewById(R.id.rdBtnSB3000P3);
            rbSB500 = (RadioButton) radioGrpSB.findViewById(R.id.rdBtnSB500P3);

            btnCache = (Button) rootView.findViewById(R.id.btnCache);

            btnContract = (Button) rootView.findViewById(R.id.txtShoppingCartContract);
            btnOffer = (Button) rootView.findViewById(R.id.txtShoppingCartOffer);

            btnVerbalOffer = (Button) rootView.findViewById(R.id.txtShoppingCartVerbalOffer);
            btnLostSale = (Button) rootView.findViewById(R.id.txtShoppingCartLostSale);

            lvPricing3LostSaleListView = (NestedListView) rootView.findViewById(R.id.lvPricing3LostSaleListView);
            lvPricing3LostSaleListView.setOnTouchListener(new ListView.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            // Disallow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                            break;

                        case MotionEvent.ACTION_UP:
                            // Allow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }

                    // Handle ListView touch events.
                    v.onTouchEvent(event);
                    return true;
                }
            });
            /*if(getScreenOrientation() ==1) {
                //setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                //linearMain.setWeightSum(1f);
                /// for landscape
                *//* this is to set HEIGHT as orientation change *//*
                *//*paramTop = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 0,0.66f);*//*
                paramListview = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,0,1f);
            } else {
                //setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                // for portrait
                *//* this is to set HEIGHT as orientation change *//*
                //linearMain.setWeightSum(1f);
                *//*paramTop = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 0,0.40f);*//*
                paramListview = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,400);
            }*/
            //linearListview.setLayoutParams(paramListview);
            //linearTop.setLayoutParams(paramTop);s
            getActivity().invalidateOptionsMenu();
            setHasOptionsMenu(true);

            etCostP2.setText(DataHelper.getGermanCurrencyFormat(price + ""));
            //etGesAmenitiesP2.setHint(DataHelper.getGermanCurrencyFormat(gesAminities + ""));
            etGesAmenitiesP2.setText(DataHelper.getGermanCurrencyFormat(gesAminities + ""));
            etEuipmentRentP2.setHint(DataHelper.getGermanCurrencyFormat("0.00"));
            etTranspotP2.setHint(DataHelper.getGermanCurrencyFormat("0.00"));
            etHaftbP2.setHint(DataHelper.getGermanCurrencyFormat("0.00"));
            etSatntagP2.setHint(DataHelper.getGermanCurrencyFormat("100.00"));
            etTollP2.setHint(DataHelper.getGermanCurrencyFormat("0.00"));
            etTotalP2.setHint(DataHelper.getGermanCurrencyFormat("0.00"));

            //etSatntagP2.setFilters(new InputFilter[]{new InputFilterDecimal("0.00", "100.00")});

            PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo,
                    new Gson().toJson(new PricingCustomerOrderBasicInfo()));

            if(preferences.getComefrom().equalsIgnoreCase("selected")){
                etApproachP2.setText("0,00");
            }
            else {
                if(model != null && model.getAbfahrt() != null && !model.getAbfahrt().equals(""))
                {
                    if(Double.parseDouble(model.getAbfahrt()) == 0.00)
                    {
                        etApproachP2.setText("");
                    }
                    else
                    {
                        etApproachP2.setText(DataHelper.getGermanCurrencyFormat(model.getAbfahrt()));
                    }

                }
            }

            if(preferences.getComefrom().equalsIgnoreCase("selected")){
                etDepartureP2.setText("0,00");
            }
            else {
                if(model != null && model.getAbfahrt() != null && !model.getAbfahrt().equals(""))
                {
                    if(Double.parseDouble(model.getAbfahrt()) == 0.00)
                    {
                        etDepartureP2.setText("");
                    }
                    else
                    {

                        etDepartureP2.setText(DataHelper.getGermanCurrencyFormat(model.getAnfahrt()));
                    }
                }
            }

            if(model != null && model.getBeiladungsPreis() != null && !model.getBeiladungsPreis().equals(""))
            {
                if(Double.parseDouble(model.getBeiladungsPreis()) == 0.00)
                {
                    etAdditionalCargoPriceP2.setText("");
                }
                else
                {
                    etAdditionalCargoPriceP2.setText(DataHelper.getGermanCurrencyFormat(model.getBeiladungsPreis()));
                }

            }
            if(model != null && model.getPauschale() != null && !model.getPauschale().equals(""))
            {
                if(Double.parseDouble(model.getPauschale()) == 0.00)
                {
                    etFlatRateP2.setText("");
                }
                else
                {
                    etFlatRateP2.setText(DataHelper.getGermanCurrencyFormat(model.getPauschale()));
                }

            }
            if(model != null && model.getNotes() != null && !model.getNotes().equals(""))
            {
                etNoteP2.setText(model.getNotes());
            }
            ((HomeActivity) getActivity()).getSupportActionBar().setTitle(language.getLabelPrice());
            setUpLanguage();
        }

        private double MiteduerCalculation(int rentalDays)
        {
            double md = rentalDays;
            double temp = 0;
            if (md >= 5)
            {
                temp = md - 5;
                temp = ((Math.floor(temp / 5)) + 1) * 2;
                md = md + temp;
            }
            return md;
        }

        private void sumOfAll()
        {
            if (chkHandllingFeeP2.isChecked() == true)
            {
                hf = 1.98;
            }
            else
            {
                hf = 0;
            }
            if (chkServicePackagesP2.isChecked() == true)
            {
                sp = 15;
            }
            else
            {
                sp = 0;
            }

            String approach = etApproachP2.getText().toString().trim();

            String departure = etDepartureP2.getText().toString().trim();
            double approachFinal,departureFinal;

                approachFinal = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(approach));
                departureFinal = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(departure));
                double tranportTotal = approachFinal + departureFinal;
                String flat = etFlatRateP2.getText().toString().trim();
            //410
            //0
                double flatFinal = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(flat));

                if (flatFinal > 0)
                {
                    etApproachP2.removeTextChangedListener(twApproach);
                    etDepartureP2.removeTextChangedListener(twDeparture);
                    //etTranspotP2.removeTextChangedListener(twApproach);
                    etApproachP2.setText("");
                    etDepartureP2.setText("");
                    etApproachP2.setHint("0");
                    etDepartureP2.setHint("0");
                    etApproachP2.addTextChangedListener(twApproach);
                    etDepartureP2.addTextChangedListener(twDeparture);
                    etTranspotP2.setText(DataHelper.getGermanFromEnglish(flatFinal + ""));
                }
                else if (tranportTotal > 0)
                {
                    Log.e("tranportTotal", tranportTotal + "");
                    etTranspotP2.setText(DataHelper.getGermanFromEnglish(tranportTotal + ""));
                    //410
                }
                else if(tranportTotal <= 0)
                {
                    Log.e("tranportTotal", tranportTotal + "");
                    etTranspotP2.setText(DataHelper.getGermanFromEnglish(tranportTotal + ""));
                }


            String transpot = etTranspotP2.getText().toString();
            String haftb = etHaftbP2.getText().toString();
            //11
            String equipmentRent = etEuipmentRentP2.getText().toString();
            //125
            String cost = etCostP2.getText().toString();
            //125
            String gesAmenities = etGesAmenitiesP2.getText().toString();
            //10
            String satntag = etSatntagP2.getText().toString();
            //100
            String toll = etTollP2.getText().toString();
            //31.86
            //etTotalP2
            double costFinal = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(cost));
            //125
            double haftbFinal = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(haftb));
            //11
            double transportFinal = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(transpot));
            //410
            double equipmentRentFinal = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(equipmentRent));
            //125
            double amenitiesFinal = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(gesAmenities));
            //10
            //double satntagFinal = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(satntag));
            double tollFinal = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(toll));
            //31,86
            double equipmentRentTotal = costFinal + amenitiesFinal;
            //135
            if (equipmentRentTotal > 0)
            {
                etEuipmentRentP2.setText(DataHelper.getGermanFromEnglish(equipmentRentTotal + ""));
            }

            /*if (rentalDaysWithoutSatSun >= 5)
            {
                double eqp = equipmentRentTotal;
                equipmentRentTotal = (eqp * rentalDaysWithoutSatSun);
            }*/

            /*if (chkCalendarDaily.isChecked())
            {
                double calenderDays = MiteduerCalculation(rentalDaysWithoutSatSun);
                double haf = haftbFinal;
                haftbFinal = (haf * calenderDays);
            }
            else
            {
                double haf = haftbFinal;
                haftbFinal = (haf * rentalDaysWithoutSatSun);
            }*/
            /*chkCalendarDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    String haftb = etHaftbP2.getText().toString();
                    double haftbFinal = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(haftb));
                    if(isChecked){
                        double calenderDays = MiteduerCalculation(rentalDaysWithoutSatSun);
                        double haf = haftbFinal;
                        haftbFinal = (haf * calenderDays);
                    }
                    else {
                        double haf = haftbFinal;
                        haftbFinal = (haf * rentalDaysWithoutSatSun);
                    }
                }
            });*/
            //double finalTotal = equipmentRentTotal + haftbFinal + transportFinal + satntagFinal + tollFinal + hf + sp;
            PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo,
                    new Gson().toJson(new PricingCustomerOrderBasicInfo()));
            String startdate = model.getStartDate();
            String enddate = model.getEndDate();

            if(startdate!=null && !startdate.equalsIgnoreCase("")){
                if (enddate != null && !enddate.equalsIgnoreCase("")) {
                    rentalDaysWithSatSun = (int) Daybetween(startdate,enddate,"dd.MM.yyyy") + 1;
                }
                else {
                    rentalDaysWithSatSun=rentalDaysWithoutSatSun;
                }
            }
            else {
                rentalDaysWithSatSun=rentalDaysWithoutSatSun;
            }



            Log.e(" ###### "," days betweenn two date date : "+rentalDaysWithSatSun);
            double newValue1;
            double newValue2;
            double totalofNewvalue;
            if(chkCalendarDaily.isChecked()){
                newValue1=rentalDaysWithoutSatSun*equipmentRentTotal;
                newValue2=rentalDaysWithSatSun*haftbFinal;
            }
            else {
                newValue1=rentalDaysWithoutSatSun*equipmentRentTotal;
                newValue2=rentalDaysWithoutSatSun*haftbFinal;
            }
            totalofNewvalue=newValue1+newValue2;
            Log.e(" $$$$ "," total  of new vlaue : "+totalofNewvalue);

            //double finalTotal = equipmentRentTotal + haftbFinal + transportFinal + tollFinal + hf + sp;
            double finalTotal = totalofNewvalue + transportFinal + tollFinal + hf + sp;
            if (finalTotal > 0)
            {
                etTotalP2.setText(DataHelper.getGermanFromEnglish(finalTotal + ""));
            }
        }


        public long Daybetween(String date1,String date2,String pattern)
        {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
            Date Date1 = null,Date2 = null;
            try{
                Date1 = sdf.parse(date1);
                Date2 = sdf.parse(date2);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            return (Date2.getTime() - Date1.getTime())/(24*60*60*1000);
        }
        static long days(Date start, Date end){
            //Ignore argument check

            Calendar c1 = Calendar.getInstance();
            c1.setTime(start);
            int w1 = c1.get(Calendar.DAY_OF_WEEK);
            c1.add(Calendar.DAY_OF_WEEK, -w1);

            Calendar c2 = Calendar.getInstance();
            c2.setTime(end);
            int w2 = c2.get(Calendar.DAY_OF_WEEK);
            c2.add(Calendar.DAY_OF_WEEK, -w2);

            //end Saturday to start Saturday
            long days = (c2.getTimeInMillis()-c1.getTimeInMillis())/(1000*60*60*24);
            long daysWithoutWeekendDays = days-(days*2/7);

            // Adjust days to add on (w2) and days to subtract (w1) so that Saturday
            // and Sunday are not included
            if (w1 == Calendar.SUNDAY && w2 != Calendar.SATURDAY) {
                w1 = Calendar.MONDAY;
            } else if (w1 == Calendar.SATURDAY && w2 != Calendar.SUNDAY) {
                w1 = Calendar.FRIDAY;
            }

            if (w2 == Calendar.SUNDAY) {
                w2 = Calendar.MONDAY;
            } else if (w2 == Calendar.SATURDAY) {
                w2 = Calendar.FRIDAY;
            }

            return daysWithoutWeekendDays-w1+w2;
        }
        public Date StringtoDate(String strdate)
        {
            Date date=null;
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            try {
                 date = format.parse(strdate);
                System.out.println(date);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            return  date;
        }

        @Override
        public void bindEvents(View rootView)
        {
            super.bindEvents(rootView);
            twApproach = new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String flat = etFlatRateP2.getText().toString().trim();
                        double flatFinal = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(flat));
                        if (flatFinal > 0)
                        {
                            showShortToast(language.getMessagePleaseRemoveFlatRate());
                            etApproachP2.removeTextChangedListener(twApproach);
                            etApproachP2.setText("");
                            etApproachP2.addTextChangedListener(twApproach);
                        }
                        else
                        {
                            sumOfAll();
                        }
                }
            };

            twAdditionalCargoPrice = new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {

                }

                @Override
                public void afterTextChanged(Editable s)
                {
                    sumOfAll();
                }
            };

            twDeparture = new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {

                }

                @Override
                public void afterTextChanged(Editable s)
                {
                    String flat = etFlatRateP2.getText().toString().trim();
                    double flatFinal=0;



                        flatFinal = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(flat));


                    if (flatFinal > 0)
                    {
                        showShortToast(language.getMessagePleaseRemoveFlatRate());
                        etDepartureP2.removeTextChangedListener(twDeparture);
                        etDepartureP2.setText("");
                        etDepartureP2.addTextChangedListener(twDeparture);
                    }
                    else
                    {
                        sumOfAll();
                    }
                }
            };

            twFlatRate = new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {

                }

                @Override
                public void afterTextChanged(Editable s)
                {
                    sumOfAll();
                }
            };

            twCost = new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {

                }

                @Override
                public void afterTextChanged(Editable s)
                {
                    sumOfAll();
                }
            };


            twGesAmenities = new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {

                }

                @Override
                public void afterTextChanged(Editable s)
                {
                    sumOfAll();
                }
            };


            twTransport = new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {

                }

                @Override
                public void afterTextChanged(Editable s)
                {
                    sumOfAll();
                }
            };

            twEquipment = new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {

                }

                @Override
                public void afterTextChanged(Editable s)
                {
                    sumOfAll();
                }
            };

            twHaftb = new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {

                }

                @Override
                public void afterTextChanged(Editable s)
                {
                    sumOfAll();
                }
            };

            twsatntagp2 = new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {

                }

                @Override
                public void afterTextChanged(Editable s)
                {
                    String enteredValue = s.toString();
                    if(checkNullValues(enteredValue))
                    {
                        float inputvalue = Float.parseFloat(DataHelper.getEnglishCurrencyFromGerman(enteredValue.trim()));
                        if(inputvalue > 100)
                        {
                            showShortToast(language.getMessageValueMustBeLessThen100());
                            etSatntagP2.setText("");
                        }
                    }
                    sumOfAll();
                }
            };

            twToll = new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {

                }

                @Override
                public void afterTextChanged(Editable s)
                {
                    sumOfAll();
                }
            };

            etApproachP2.addTextChangedListener(twApproach);
            etDepartureP2.addTextChangedListener(twDeparture);
            etAdditionalCargoPriceP2.addTextChangedListener(twAdditionalCargoPrice);
            etFlatRateP2.addTextChangedListener(twFlatRate);
            etCostP2.addTextChangedListener(twCost);
            etGesAmenitiesP2.addTextChangedListener(twGesAmenities);
            etHaftbP2.addTextChangedListener(twHaftb);
            etSatntagP2.addTextChangedListener(twsatntagp2);
            etTollP2.addTextChangedListener(twToll);

            rbSB1000.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Is the button now checked?
                    boolean checkedRadioSB1000 = ((RadioButton) v).isChecked();
                    if (checkedRadioSB1000) {
                        if (pricingScreen != null) {
                            SB = 1000;
                            if(pricingScreen.getListOfPriceHaftb() != null)
                                etHaftbP2.setText(DataHelper.getGermanFromEnglish(Double.parseDouble(pricingScreen.getListOfPriceHaftb().get(0).getValue()) + ""));
                        }

                        //loadPriceHAFTBDataStandardPrice(customer_contactNo, 1000, deviceType);
                    } else {

                    }
                }
            });
            rbSB500.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Is the button now checked?
                    boolean checkedRadioSB500 = ((RadioButton) v).isChecked();
                    if (checkedRadioSB500) {
                        if (pricingScreen != null) {
                            SB = 500;
                            if(pricingScreen.getListOfPriceHaftb() != null)
                                etHaftbP2.setText(DataHelper.getGermanFromEnglish(Double.parseDouble(pricingScreen.getListOfPriceHaftb().get(3).getValue()) + ""));
                        }

                        //loadPriceHAFTBDataStandardPrice(customer_contactNo, 1000, deviceType);
                    } else {

                    }
                }
            });

            rbSB2000.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    boolean checkedRadioSB2000 = ((RadioButton) v).isChecked();
                    if (checkedRadioSB2000)
                    {
                        if(pricingScreen != null){
                            SB = 2000;
                            if(pricingScreen.getListOfPriceHaftb() != null)
                                etHaftbP2.setText(DataHelper.getGermanFromEnglish(Double.parseDouble(pricingScreen.getListOfPriceHaftb().get(1).getValue()) + ""));
                        }

                        //loadPriceHAFTBDataStandardPrice(customer_contactNo, 2000, deviceType);
                    }
                    else
                    {

                    }
                }
            });
            rbSB3000.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checkedRadioSB3000 = ((RadioButton) v).isChecked();
                    if (checkedRadioSB3000) {

                        if(pricingScreen != null){
                            SB = 3000;
                            //SB = 1000;
                            if(pricingScreen.getListOfPriceHaftb() != null)
                                etHaftbP2.setText(DataHelper.getGermanFromEnglish(Double.parseDouble(pricingScreen.getListOfPriceHaftb().get(2).getValue()) + ""));
                            //loadPriceHAFTBDataStandardPrice(customer_contactNo, 3000, deviceType);
                        }

                    } else {

                    }
                }
            });

            chkHandllingFeeP2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        hf = 1.98;
                        hfStastus = "ja";
                    } else {
                        hf = 0;
                        hfStastus = "nein";
                    }
                }
            });
            chkServicePackagesP2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        sp = 15;
                        spStatus = "ja";
                    } else {
                        sp = 0;
                        spStatus = "nein";
                    }
                }
            });
            chkGenehmi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        isGenehmigungsgebiet=true;
                    } else {
                        isGenehmigungsgebiet=false;
                    }
                }
            });


            btnCache.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e(" $$$$$$$$ "," click on chache button : ");
                    if(DataHelper.isNetworkAvailable(context))
                    {
                        if (chkHandllingFeeP2.isEnabled() == true) {
                            if (chkHandllingFeeP2.isChecked() == true) {
                                hf = 1.98;
                                hfStastus = "ja";
                            } else {
                                hf = 0;
                                hfStastus = "nein";
                            }
                        } else {
                            if (chkHandllingFeeP2.isChecked() == true) {
                                hf = 1.98;
                                hfStastus = "ja";
                            } else {
                                hf = 0;
                                hfStastus = "nein";
                            }
                        }

                        if (chkServicePackagesP2.isEnabled() == true) {
                            if (chkServicePackagesP2.isChecked() == true) {
                                sp = 15;
                                spStatus = "ja";
                            } else {
                                sp = 0;
                                spStatus = "nein";
                            }
                        } else {
                            if (chkServicePackagesP2.isChecked() == true) {
                                sp = 15;
                                spStatus = "ja";
                            } else {
                                sp = 0;
                                spStatus = "nein";
                            }
                        }

                        price = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(etCostP2.getText().toString().trim()));
                        haftb = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(etHaftbP2.getText().toString().trim()));

                        db.addLostSale(new Pricing3LostSaleData(branchName, deviceType, "", "", "", rentalDaysWithoutSatSun, price, SB, hfStastus, spStatus, haftb, contactPerson, customer_KundenNr));

                        // into local database
                        InsertPriceUseInformationList();

                        loadLostSaleListViewData();

                                                                                        //btnCache.setEnabled(true);

                         showShortToast(language.getMessageCachePriceAddedToCacheCart());
                    }
                    else
                    {
                        showShortToast(language.getMessageNetworkNotAvailable());
                    }
                }
            });


            btnContract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedEquipments.clear();
                    WarenkorbartBtnPressIds = 0;
                    WarenkorbartBtnPressIds = 4;

                    for (int i = 0; i < lablesLostSale.size(); i++) {
                        if (lablesLostSale.get(i).isSelected() == true) {
                            selectedEquipments.add(lablesLostSale.get(i).getId());
                        }
                    }

                    if (selectedEquipments.isEmpty()) {
                        showShortToast(language.getMessagePleaseSelectCartItem());
                    } else {
                        if (selectedEquipments.size() > 1) {
    //                        btnContract.setEnabled(false);
                            showShortToast(language.getMessageAuftragSelectOne());
                        } else {
                            // getting data from db and calling service
                            InsertData(WarenkorbartBtnPressIds, selectedEquipments, 4);
                        }
                    }
                }
            });

            btnVerbalOffer.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    btnContract.setEnabled(true);
                    WarenkorbartBtnPressIds = 0;
                    WarenkorbartBtnPressIds = 2;
                    selectedEquipments.clear();
                    for (int i = 0; i < lablesLostSale.size(); i++)
                    {
                        if (lablesLostSale.get(i).isSelected() == true)
                        {
                            selectedEquipments.add(lablesLostSale.get(i).getId());
                        }
                    }

                    if (selectedEquipments.isEmpty())
                    {
                        showShortToast(language.getMessagePleaseSelectCartItem());
                    }
                    else
                    {
                        InsertData(WarenkorbartBtnPressIds, selectedEquipments, 2);
                    }
                }
            });

            btnLostSale.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    btnContract.setEnabled(true);
                    WarenkorbartBtnPressIds = 0;
                    WarenkorbartBtnPressIds = 3;
                    selectedEquipments.clear();
                    for (int i = 0; i < lablesLostSale.size(); i++)
                    {
                        if (lablesLostSale.get(i).isSelected() == true)
                        {
                            selectedEquipments.add(lablesLostSale.get(i).getId());
                        }
                    }

                    if (selectedEquipments.isEmpty())
                    {
                        showShortToast(language.getMessagePleaseSelectCartItem());
                    }
                    else
                    {
                        LostSaleDialog(WarenkorbartBtnPressIds, selectedEquipments, 3);
                    }
                }
            });

            btnOffer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnContract.setEnabled(true);
                    WarenkorbartBtnPressIds = 0;
                    WarenkorbartBtnPressIds = 1;
                    selectedEquipments.clear();
                    for (int i = 0; i < lablesLostSale.size(); i++) {
                        if (lablesLostSale.get(i).isSelected() == true) {
                            selectedEquipments.add(lablesLostSale.get(i).getId());
                        }
                    }
                    if (selectedEquipments.isEmpty()) {
                        showShortToast(language.getMessagePleaseSelectCartItem());
                    } else {
                        // InsertData(WarenkorbartBtnPressIds,selectedEquipments.get(i),i);
                        InsertData(WarenkorbartBtnPressIds, selectedEquipments, 1);
                    }
                }
            });

            lvPricing3LostSaleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                    //view.setSelected(true);
                    //lostSaleAdapter.setSelectedIndex(position);
                }
            });


            if (matecoPriceApplication.isCustomerLoaded(DataHelper.isCustomerLoaded, false))
            {
                customer_contactNo = matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();
                customer_KundenNr = matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKundenNr();
                btnCache.setEnabled(true);
                btnLostSale.setEnabled(true);
                btnContract.setEnabled(true);
                btnOffer.setEnabled(true);
                btnVerbalOffer.setEnabled(true);
                PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo, new Gson().toJson(new PricingCustomerOrderBasicInfo()));
                if(GlobalMethods.isZero(model.getAbfahrt()) && GlobalMethods.isZero(model.getAnfahrt()))
                {
                    //getTransportPrice(customer_contactNo, deviceType, String.valueOf(branchId), plz);

                }
                loadLostSaleListViewData();
                getInitialStatus(kanr, kaNrOfLoadedCustomer+"", deviceType);
                 /* Add new Functionality */
                /* Add new Functionality */

    //            chkHAFTBDataIsTrue(kanr);
    //            chkPriceHandlingCheckBoxDataIsTrue(kanr);
    //            chkPriceServiceCheckBoxDataIsTrue(kanr);

                //deviceType
            }
            else
            {
                showLongToast(language.getMessageSelectCustomerFirst());
                btnCache.setEnabled(false);
                btnLostSale.setEnabled(false);
                btnContract.setEnabled(false);
                btnOffer.setEnabled(false);
                btnVerbalOffer.setEnabled(false);

                radioGrpSB.setEnabled(false);
                rbSB1000.setEnabled(false);
                rbSB2000.setEnabled(false);
                rbSB3000.setEnabled(false);
                rbSB500.setEnabled(false);

    //            chkCalendarDaily.setEnabled(false);
                chkHandllingFeeP2.setEnabled(false);
                chkServicePackagesP2.setEnabled(false);
            }
            sumOfAll();

        }
        @Override
        public void onDestroy() {
            super.onDestroy();
            if(asyncTask != null && asyncTask.getStatus() == AsyncTask.Status.RUNNING)
                asyncTask.cancel(true);
        }

        public boolean checkNullValues(String valueToCheck)
        {
            //Log.i("Log","CheckForNullValues : "+valueToCheck);
            if(!(valueToCheck == null))
            {
                String valueCheck = valueToCheck.trim();
                if(valueCheck.equals("") || valueCheck.equals("0")  )
                {
                    //  Log.i("Log","Returning false 0 or Blank");
                    return false;
                }
                return true;
            }
            //Log.i("Log","Returning false null");
            return false;
        }

        private void setUpLanguage()
        {
            txtBest.setText(language.getLabelBest());
            txtHb.setText(language.getLableHB());
            txtSP.setText(language.getLabelSP());

            txtCost.setText(language.getLabelCost());
            txtLiabilityLimitation.setText(language.getLabelLiabilityLimitation());
            txtCost1.setText(language.getLabelPrice());
            txtHaftb.setText(language.getLabelHaftb());
            txtHandllingFee.setText(language.getLabelHandlingFee());
            txtTotal.setText(language.getLabelTotal());
            txtTransport1.setText(language.getLabelTransport());
            txtEquipmentRent.setText(language.getLabelEquipmentRent());
            txtToll.setText(language.getLabelToll());

            txtGesAmenities.setText(language.getLabelGesAmenities());
            txtSatntag.setText(language.getLabelSatntag());
            txtServicePackages.setText(language.getLabelServicePackages());
            txtBranch.setText(language.getLabelBranch());
            txtHGRP.setText(language.getLabelHGRP());
    //        txtDeviceType.setText(language.getLabelDeviceType());
            // txtManufacturer.setText(language.getLabelManufacturer());
            //  txtType.setText(language.getLabelType());
            txtMD.setText(language.getLabelMD());
            txtRentalPrice.setText(language.getLabelRentalPrice());
            txtSB.setText(language.getLabelSB());
            txtHF.setText(language.getLabelHF());
            txtNote.setText(language.getLabelNotes());
            txtTransport.setText(language.getLabelTransport());
            txtApproach.setText(language.getLabelApproach());
            txtDeparture.setText(language.getLabelDeparture());
            txtAdditionalCargoPrice.setText(language.getLabelAdditionalCargoPrice());
            txtFlateRate.setText(language.getLabelFlateRate());
            chkCalendarDaily.setText(language.getLabelIntegraCalenderFrom());
            chkInrinsicActivity.setText(language.getLabelInrinsicActivity());
            btnCache.setText(language.getLabelCache());
            btnLostSale.setText(language.getLabelLostSale());
            btnContract.setText(language.getLabelWithContract());
            btnOffer.setText(language.getLabelOffer());
            btnVerbalOffer.setText(language.getLabelVerbleOffered());
            txtGenehmigungsgebiet.setText(language.getLabelGenehmi());
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
        {
            inflater.inflate(R.menu.menu_main_menu, menu);
            this.menu = menu;
            menu.findItem(R.id.actionAdd).setVisible(false);
            menu.findItem(R.id.actionEdit).setVisible(false);
            menu.findItem(R.id.actionSearch).setVisible(false);
            menu.findItem(R.id.actionRight).setVisible(false);
            menu.findItem(R.id.actionWrong).setVisible(false);
            //menu.findItem(R.id.actionForward).setVisible(false);
            menu.findItem(R.id.actionForward).setIcon(R.drawable.icn_blank);
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
                    savePricingCustomerOrderBasicInfo();
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

        public void LostSaleDialog(final int fromWhichBtn, final ArrayList<Integer> ids, final int starting)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            View promptsView = li.inflate(R.layout.fragment_pricing_3_lost_contract_dialog, null);
            final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setCancelable(false);
            alertDialog.setTitle(language.getMessagePleaseEnterReasonForRejection());
            LinearLayout linearLostSaleDLG = (LinearLayout) promptsView.findViewById(R.id.linearLostSaleDLG);

            alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            //DataHelper.setUpUi(getActivity(), alertDialog.getWindow().getDecorView().findViewById(android.R.id.content));
            //DataHelper.setUpUi(getActivity(), linearLostSaleDLG);
            linearLostSaleDLG.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View view, MotionEvent ev)
                {
                    alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                    hideKeyboard(view);
                    return true;
                }
            });

            etGeratermietePriceDLGP3 = (EditText) promptsView.findViewById(R.id.etGeratermietePriceDLGP3);
            etGeratermieteCompititerDLGP3 = (EditText) promptsView.findViewById(R.id.etGeratermieteCompititerDLGP3);
            etLimitationOfLiabilityPriceDLGP3 = (EditText) promptsView.findViewById(R.id.etLimitationOfLiabilityPriceDLGP3);
            etLimitationOfLiabilityCompititerDLGP3 = (EditText) promptsView.findViewById(R.id.etLimitationOfLiabilityCompititerDLGP3);
            etTransportPriceDLGP3 = (EditText) promptsView.findViewById(R.id.etTransportPriceDLGP3);
            etTransportCompititerDLGP3 = (EditText) promptsView.findViewById(R.id.etTransportCompititerDLGP3);
            etTheCustomerHasNotReceivedTheOrderCompititerDLGP3 = (EditText) promptsView.findViewById(R.id.etTheCustomerHasNotReceivedTheOrderCompititerDLGP3);
            etOtherDLGP3 = (EditText) promptsView.findViewById(R.id.etOtherDLGP3);
            chkLogisticsDLGP3 = (CheckBox) promptsView.findViewById(R.id.chkLogisticsDLGP3);
            chkAvailabilityDLGP3 = (CheckBox) promptsView.findViewById(R.id.chkAvailabilityDLGP3);
            chkPriceDLGP3 = (CheckBox) promptsView.findViewById(R.id.chkPriceDLGP3);
            chkGeratermieteDLGP3 = (CheckBox) promptsView.findViewById(R.id.chkGeratermieteDLGP3);
            chkLimitationOfLiabilityDLGP3 = (CheckBox) promptsView.findViewById(R.id.chkLimitationOfLiabilityDLGP3);
            chkTransportDLGP3 = (CheckBox) promptsView.findViewById(R.id.chkTransportDLGP3);
            chkTermsofPaymentDLGP3 = (CheckBox) promptsView.findViewById(R.id.chkTermsofPaymentDLGP3);
            chkPaysNoAdvancePaymentDLGP3 = (CheckBox) promptsView.findViewById(R.id.chkPaysNoAdvancePaymentDLGP3);
            chkCashDiscountDLGP3 = (CheckBox) promptsView.findViewById(R.id.chkCashDiscountDLGP3);
            chkTheCustomerHasChosenAnAlternativeMtecoUnitDLGP3 = (CheckBox) promptsView.findViewById(R.id.chkTheCustomerHasChosenAnAlternativeMtecoUnitDLGP3);
            chkTheCustomerDoesNotHaveNeedLessDLGP3 = (CheckBox) promptsView.findViewById(R.id.chkTheCustomerDoesNotHaveNeedLessDLGP3);
            chkTheCustomerHasNotReceivedTheOrderDLGP3 = (CheckBox) promptsView.findViewById(R.id.chkTheCustomerHasNotReceivedTheOrderDLGP3);
            chkOtherDLGP3 = (CheckBox) promptsView.findViewById(R.id.chkOtherDLGP3);
            txtPriceDLGP3 = (TextView) promptsView.findViewById(R.id.txtPriceDLGP3);
            txtCompetitorDLGP3 = (TextView) promptsView.findViewById(R.id.txtCompetitorDLGP3);
            txtCompetitor1DLGP3 = (TextView) promptsView.findViewById(R.id.txtCompetitor1DLGP3);
            chkLogisticsDLGP3.setText(language.getLabelLogistics());
            chkAvailabilityDLGP3.setText(language.getLabelAvailability());
            chkPriceDLGP3.setText(language.getLabelPrice());
            chkGeratermieteDLGP3.setText(language.getLabelGeratermiete());
            chkTransportDLGP3.setText(language.getLabelTransport());
            chkLimitationOfLiabilityDLGP3.setText(language.getLabelLimitationOfLiability());
            chkTermsofPaymentDLGP3.setText(language.getLabelTermsofPayment());
            chkPaysNoAdvancePaymentDLGP3.setText(language.getLabelPaysNoAdvancePayment());
            chkCashDiscountDLGP3.setText(language.getLabelCashDiscount());
            chkTheCustomerHasChosenAnAlternativeMtecoUnitDLGP3.setText(language.getLabelTheCustomerHasChosenAnAlternativeMtecoUnit());
            chkTheCustomerDoesNotHaveNeedLessDLGP3.setText(language.getLabelTheCustomerDoesNotHaveNeedLess());
            chkTheCustomerHasNotReceivedTheOrderDLGP3.setText(language.getLabelTheCustomerHasNotReceivedTheOrder());
            chkOtherDLGP3.setText(language.getLabelOther());
            txtPriceDLGP3.setText(language.getLabelPrice());
            txtCompetitorDLGP3.setText(language.getLabelCompetitor());
            txtCompetitor1DLGP3.setText(language.getLabelCompetitor());

            Button btnOk = (Button) promptsView.findViewById(R.id.btnOkDLGP3);
            Button btnAbortDLGP3 = (Button) promptsView.findViewById(R.id.btnAbortDLGP3);


            chkLogisticsDLGP3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if (isChecked)
                    {
                        chkCounter = 1;
                    }
                    else
                    {
                        chkCounter = 0;
                    }
                }
            });
            chkAvailabilityDLGP3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if (isChecked)
                    {
                        chkCounter = 1;
                    }
                    else
                    {
                        chkCounter = 0;
                    }
                }
            });
            chkPriceDLGP3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {

                    if (isChecked)
                    {
                        chkCounter = 1;
                    }
                    else
                    {
                        chkCounter = 0;
                    }
                }
            });
            chkGeratermieteDLGP3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if (isChecked)
                    {
                        chkCounter = 1;
                    }
                    else
                    {
                        chkCounter = 0;
                    }
                }
            });
            chkTransportDLGP3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if (isChecked)
                    {
                        chkCounter = 1;
                    }
                    else
                    {
                        chkCounter = 0;
                    }
                }
            });
            chkLimitationOfLiabilityDLGP3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if (isChecked)
                    {
                        chkCounter = 1;
                    }
                    else
                    {
                        chkCounter = 0;
                    }
                }
            });
            chkTermsofPaymentDLGP3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if (isChecked)
                    {
                        chkCounter = 1;
                    }
                    else
                    {
                        chkCounter = 0;
                    }
                }
            });
            chkPaysNoAdvancePaymentDLGP3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if (isChecked)
                    {
                        chkCounter = 1;
                    }
                    else
                    {
                        chkCounter = 0;
                    }
                }
            });
            chkCashDiscountDLGP3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if (isChecked)
                    {
                        chkCounter = 1;
                    }
                    else
                    {
                        chkCounter = 0;
                    }
                }
            });
            chkTheCustomerHasChosenAnAlternativeMtecoUnitDLGP3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if (isChecked)
                    {
                        chkCounter = 1;
                    }
                    else
                    {
                        chkCounter = 0;
                    }
                }
            });
            chkTheCustomerDoesNotHaveNeedLessDLGP3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if (isChecked)
                    {
                        chkCounter = 1;
                    }
                    else
                    {
                        chkCounter = 0;
                    }
                }
            });
            chkTheCustomerHasNotReceivedTheOrderDLGP3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if (isChecked)
                    {
                        chkCounter = 1;
                    }
                    else
                    {
                        chkCounter = 0;
                    }
                }
            });


            twOtherDLGP3 = new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {

                }

                @Override
                public void afterTextChanged(Editable s)
                {
                    if (s.toString().length() > 0)
                    {
                        chkOtherDLGP3.setChecked(true);
                    }
                    else
                    {
                        chkOtherDLGP3.setChecked(false);
                    }
                }
            };

            etOtherDLGP3.addTextChangedListener(twOtherDLGP3);

            chkOtherDLGP3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if (isChecked)
                    {
                        chkCounter = 1;
                    }
                    else
                    {
                        chkCounter = 0;
                    }
                }
            });


            btnOk.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (chkCounter > 0)
                    {
                        try
                        {
                            //  InsertData(3,selectedEquipments, 1);
                            selectedEquipments.clear();
                            for (int i = 0; i < lablesLostSale.size(); i++)
                            {
                                if (lablesLostSale.get(i).isSelected() == true)
                                {
                                    selectedEquipments.add(lablesLostSale.get(i).getId());
                                }
                            }
                            InsertData(WarenkorbartBtnPressIds, selectedEquipments, 3);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        alertDialog.dismiss();
                    }
                    else
                    {
                        showShortToast(language.getMessageSelectOneReasonForRejection());
                    }
                }
            });
            btnAbortDLGP3.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    alertDialog.dismiss();
                    //  overCounter();
                }
            });
            alertDialog.setView(promptsView);
            alertDialog.show();
        }

        /**
         * Function to load the spinner data from SQLite database
         */
        private void loadLostSaleListViewData()
        {
            lablesLostSale.clear();
            // ListView elements
            lablesLostSale = db.getPricing3LostsaleData(customer_KundenNr);
            if(lablesLostSale.size() > 0){
                lostSaleAdapter = new Pricing3LostSaleDataAdapter(getActivity(), R.layout.fragment_pricing_3_lost_sale_row, lablesLostSale);
                // Drop down layout style - list view with radio button
                // attaching data adapter to spinner
                lvPricing3LostSaleListView.setAdapter(lostSaleAdapter);
                preferences.setisPrice("yes");
            }
        }
        private void getInitialStatus(String kontakt, String kanr, String deviceType)
        {
            if(DataHelper.isNetworkAvailable(getActivity()))
            {
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        Log.e(" errror  ","result on getting radio button functionality : "+result);
                        if(result.equals("error"))
                        {
                            showShortToast(language.getMessageError());
                        }
                        else
                        {
                            try
                            {
                                Log.e("******%%%%%","result on getting radio button functionality : "+result);
                                pricingScreen = new Gson().fromJson(result, PricingScreen3ObjectList.class);
                                if(pricingScreen.getPriceHaftungsbegrenzungBoxStatus().equals("True"))
                                {
                                    radioGrpSB.setEnabled(true);
                                    rbSB1000.setEnabled(true);
                                    rbSB2000.setEnabled(true);
                                    rbSB3000.setEnabled(true);
                                    rbSB500.setEnabled(true);

                                }
                                else
                                {
                                    radioGrpSB.setEnabled(false);
                                    rbSB1000.setEnabled(false);
                                    rbSB2000.setEnabled(false);
                                    rbSB3000.setEnabled(false);
                                    rbSB500.setEnabled(false);
                                }

                                if(Integer.parseInt(pricingScreen.getPriceHandlingCheckBox()) > 0)
                                {
                                    chkHandllingFeeP2.setChecked(false);
                                }
                                else
                                {
                                    chkHandllingFeeP2.setChecked(true);
                                }

                                if(pricingScreen.getPriceKalendertaeglichCheckBox().equals("True"))
                                {
                                    chkCalendarDaily.setChecked(true);
                                }
                                else
                                {
                                    chkCalendarDaily.setChecked(false);
                                }

                                if(Integer.parseInt(pricingScreen.getPriceServiceCheckBox()) > 0)
                                {
                                    chkServicePackagesP2.setChecked(false);
                                }
                                else
                                {
                                    chkServicePackagesP2.setChecked(true);
                                }
                                Boolean selected=false;
                                for(int i = 0; i < pricingScreen.getListOfPriceHaftb().size(); i++)
                                {

                                    if(pricingScreen.getListOfPriceHaftb().get(i).getSelected().equals("1"))
                                    {
                                        selected=true;
                                        Log.e(" in ifff "," i value in for loop fragemtn 3 : "+i);
                                        if(i == 0)
                                        {
                                            rbSB1000.setChecked(true);
                                            SB = 1000;
                                        }
                                        else if( i == 1)
                                        {
                                            rbSB2000.setChecked(true);
                                            SB = 2000;
                                        }
                                        else if (i== 2){
                                            rbSB3000.setChecked(true);
                                            SB = 3000;
                                        }
                                        else
                                        {
                                            rbSB500.setChecked(true);
                                            SB = 500;
                                        }
                                        etHaftbP2.setText(DataHelper.getGermanFromEnglish(Double.parseDouble(pricingScreen.getListOfPriceHaftb().get(i).getValue())+""));
                                        sumOfAll();
                                        break;
                                    }
                                }
                                if(selected){
                                    rbSB1000.setEnabled(true);
                                    rbSB500.setEnabled(true);
                                    rbSB3000.setEnabled(true);
                                    rbSB2000.setEnabled(true);
                                }
                                else {
                                    rbSB1000.setEnabled(false);
                                    rbSB500.setEnabled(false);
                                    rbSB3000.setEnabled(false);
                                    rbSB2000.setEnabled(false);
                                }

                                if(preferences.getComefrom().equalsIgnoreCase("selected")){
                                    etApproachP2.setText("0,00");
                                    etDepartureP2.setText("0,00");

                                }
                                else {
                                    etApproachP2.setText("" + pricingScreen.getTransportPrice());
                                    etDepartureP2.setText("" + pricingScreen.getTransportPrice());
                                }

                                GlobalMethods.setBlankValueForZero(etApproachP2);

                                GlobalMethods.setBlankValueForZero(etDepartureP2);
                                etTollP2.setText(pricingScreen.getMaut() + "");
                                GlobalMethods.setBlankValueForZero(etDepartureP2);
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                    }
                };
                try
                {
                    Pricing3GetDetailRequestModel object = new Pricing3GetDetailRequestModel();
                    object.setKontakt(kontakt);
                    object.setKanr(kanr);
                    object.setDeviceType(deviceType);
                    object.setZipCode(plz);
                    object.setMandant(branchId+"");
                    object.setGeratetypeId(GeratetypeId);
                    String json = new Gson().toJson(object);
                    //+ "&kontakt=" + kontakt+ "&hoehengruppe=" + hoehengruppe+ "&mandant=" + mandant+ "&plz=" + plz;
                    Log.e(" $%$%$%$% ", " json of screen 3"+json);
                    /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.Pricing_Screen_3_Obj
                            + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                            + "&pricing3ScreenJson=" + URLEncoder.encode(json, "UTF-8");*/
                    String url = DataHelper.URL_PRICE_HELPER
                            + "pricescreenthreeobj/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                            + "/pricing3ScreenJson=" + URLEncoder.encode(json, "UTF-8");
    //                        + "&Kontakt=" + kontakt
    //                        + "&KaNr=" + kanr
    //                        + "&Hoehengruppe=" + deviceType;
                    Log.e("url", url);
                    asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                    asyncTask.execute();
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
            else
            {
                showShortToast(language.getMessageNetworkNotAvailable());
            }
        }

        public void InsertData(final int fromWhichBtn, final ArrayList<Integer> ids, final int starting)
        {
            final ArrayList<Pricing3InsertData> listOfPricingSend = new ArrayList<>();
            jsonOfEqi = "";
            pricingInser = new ArrayList<>();

            for (int i = 0; i < ids.size(); i++)
            {
                jsonOfEqi = "";
                pricingInser.add(db.getPricing3InsertData(customer_KundenNr, ids.get(i)));
                Pricing3InsertData pricingInsert = new Pricing3InsertData();
                String loginUserNumberrange = pricingInser.get(i).getUserID();
                String Kontakt = pricingInser.get(i).getKontakt();
                String KundenNr = pricingInser.get(i).getKundenNr();
                int Mandant = pricingInser.get(i).getMandant();
                int Warenkorbart = fromWhichBtn;
                String Hoehengruppe = pricingInser.get(i).getHoehengruppe();
                int Einheit_Mietdauer = pricingInser.get(i).getEinheit_Mietdauer();
                int Mietdauer = pricingInser.get(i).getMietdauer();
                //String Mietpreis = (String.valueOf(pricingInser.get(i).getMietpreis()));
                String Mietpreis = String.valueOf(Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(String.valueOf(pricingInser.get(i).getMietpreis()))));
                //double Mietpreis = (pricingInser.get(i).getMietpreis());
                double Standtag = pricingInser.get(i).getStandtag();
                String Selbstbehalt = pricingInser.get(i).getSelbstbehalt();
                int HandlingFee = pricingInser.get(i).getHandlingFee();

                int ServicePauschale = pricingInser.get(i).getServicePauschale();
                double Versicherung = pricingInser.get(i).getVersicherung();
                int WochenendeMitversichert = pricingInser.get(i).getWochenendeMitversichert();
                double TransportAnfahrt = pricingInser.get(i).getTransportAnfahrt();
                double TransportPauschal = pricingInser.get(i).getTransportPauschal();
                double TransportAbfahrt = pricingInser.get(i).getTransportAbfahrt();
                double Beiladungspauschale = pricingInser.get(i).getBeiladungspauschale();
                String Einsatzinformation = pricingInser.get(i).getEinsatzinformation();

                String Besteller = pricingInser.get(i).getBesteller();
                String Besteller_Telefon="";
                if(pricingInser.get(i).getBesteller_Telefon().equalsIgnoreCase("")){
                    Besteller_Telefon="";
                }
                else {
                    Besteller_Telefon = pricingInser.get(i).getBesteller_Telefon();
                }

                String Besteller_Email = pricingInser.get(i).getBesteller_Email();
                String Notiz = pricingInser.get(i).getNotiz();

                PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo,
                        new Gson().toJson(new PricingCustomerOrderBasicInfo()));


                //Notiz = Notiz + " Mietbeginn: " + model.getStartDate() + " Mietende: " + model.getEndDate();
                Notiz = Notiz + " Mietbeginn: " + startDate + " Mietende: " + endDate;

                int Ersteller = pricingInser.get(i).getErsteller();
                int KaNr = pricingInser.get(i).getKaNr();
                String AnsPartner = pricingInser.get(i).getAnsPartner();
                String Besteller_Anrede="";
                String Besteller_Mobil="";

                if(!pricingInser.get(i).getBesteller_Anrede().equalsIgnoreCase("")){
                    Besteller_Anrede = pricingInser.get(i).getBesteller_Anrede();
                }
                if(!pricingInser.get(i).getBesteller_Mobil().equalsIgnoreCase("")){
                    Besteller_Mobil = pricingInser.get(i).getBesteller_Mobil();
                }
                String Mautkilometer = pricingInser.get(i).getMautkilometer();
                int Winterreifenpauschale = pricingInser.get(i).getWinterreifenpauschale();
                int Bearbeitet = pricingInser.get(i).getBearbeitet();
                int Kalendertage = pricingInser.get(i).getKalendertage();
                String Referenz = pricingInser.get(i).getReferenz();
                String Geraetetyp = pricingInser.get(i).getGeraetetyp();
                jsonOfEqi = pricingInser.get(i).getJsonOfEqu();
                /*******20161114******/
                jsonOfEqiForInsert.add(jsonOfEqi);
                /*********************/

                pricingInsert.setStrDateList(datesComma);
                pricingInsert.setUserID(loginUserNumberrange);
                pricingInsert.setAnsPartner(AnsPartner);
                pricingInsert.setSelbstbehalt(Selbstbehalt);
                pricingInsert.setReferenz(Referenz);
                pricingInsert.setGeraetetyp(Geraetetyp);
                if(Besteller != null){
                    if(Besteller.equalsIgnoreCase("null") || Besteller == null){
                        pricingInsert.setBesteller("");
                    }
                    else {
                        pricingInsert.setBesteller(Besteller);
                    }
                }
                else {
                    pricingInsert.setBesteller("");
                }
                pricingInsert.setBesteller_Telefon(Besteller_Telefon);
                pricingInsert.setBesteller_Anrede(Besteller_Anrede);
                pricingInsert.setBesteller_Email(Besteller_Email);
                pricingInsert.setBesteller_Mobil(Besteller_Mobil);
                pricingInsert.setNotiz(Notiz);
                pricingInsert.setEinsatzinformation(Einsatzinformation);
                pricingInsert.setMautkilometer(Mautkilometer);
                pricingInsert.setKundenNr(KundenNr);
                pricingInsert.setHoehengruppe(Hoehengruppe);
                pricingInsert.setKontakt(Kontakt);
                pricingInsert.setKalendertage(Kalendertage);
                pricingInsert.setVersicherung(Versicherung);
                pricingInsert.setTransportPauschal(TransportPauschal);
                pricingInsert.setTransportAnfahrt(TransportAnfahrt);
                pricingInsert.setTransportAbfahrt(TransportAbfahrt);
                pricingInsert.setStandtag(Standtag);
                pricingInsert.setMietpreis(Mietpreis);
                pricingInsert.setBeiladungspauschale(Beiladungspauschale);
                pricingInsert.setEinheit_Mietdauer(Einheit_Mietdauer);
                pricingInsert.setBearbeitet(Bearbeitet);
                pricingInsert.setServicePauschale(ServicePauschale);
                pricingInsert.setMietdauer(Mietdauer);
                pricingInsert.setErsteller(Ersteller);
                pricingInsert.setMandant(Mandant);
                pricingInsert.setHandlingFee(HandlingFee);

                pricingInsert.setKann(pricingInser.get(i).isKann());
                pricingInsert.setLieferung(pricingInser.get(i).isLieferung());
                pricingInsert.setVoranmeldung(pricingInser.get(i).isVoranmeldung());
                pricingInsert.setBenachrichtgung(pricingInser.get(i).isBenachrichtgung());
                pricingInsert.setRampena(pricingInser.get(i).isRampena());
                pricingInsert.setSonstige(pricingInser.get(i).isSonstige());
                pricingInsert.setEinweisung(pricingInser.get(i).isEinweisung());
                pricingInsert.setSelbstfahrer(pricingInser.get(i).isSelbstfahrer());

                pricingInsert.setStrKann(pricingInser.get(i).getStrKann());
                pricingInsert.setStrVoranmeldung(pricingInser.get(i).getStrVoranmeldung());
                pricingInsert.setStrBenachrich(pricingInser.get(i).getStrBenachrich());
                pricingInsert.setStrSonstige(pricingInser.get(i).getStrSonstige());
                pricingInsert.setintLadeiahrzeug(pricingInser.get(i).getintLadeiahrzeug());
                if((pricingInser.get(i).getStrstartDate().equalsIgnoreCase("")) && (pricingInser.get(i).getStrstartTime().equalsIgnoreCase(""))){
                    pricingInsert.setStrstartDate(pricingInser.get(i).getStrstartDate());
                }
                else if(pricingInser.get(i).getStrstartTime().equalsIgnoreCase("")){
                    pricingInsert.setStrstartDate(pricingInser.get(i).getStrstartDate());
                }
                else if((!pricingInser.get(i).getStrstartDate().equalsIgnoreCase("")) && (!pricingInser.get(i).getStrstartTime().equalsIgnoreCase(""))){
                    pricingInsert.setStrstartDate(pricingInser.get(i).getStrstartDate() + " " + pricingInser.get(i).getStrstartTime());
                }
                else
                {
                    pricingInsert.setStrstartDate(pricingInser.get(i).getStrstartDate());
                }

                pricingInsert.setStrstartTime(pricingInser.get(i).getStrstartTime());

                if((pricingInser.get(i).getStrendDate().equalsIgnoreCase("")) && (pricingInser.get(i).getStrendTime().equalsIgnoreCase(""))){
                    pricingInsert.setStrendDate(pricingInser.get(i).getStrendDate());
                }
                else if(pricingInser.get(i).getStrendTime().equalsIgnoreCase("")){
                    pricingInsert.setStrendDate(pricingInser.get(i).getStrendDate());
                }
                else if((!pricingInser.get(i).getStrendDate().equalsIgnoreCase("")) && (!pricingInser.get(i).getStrendTime().equalsIgnoreCase(""))){
                    pricingInsert.setStrendDate(pricingInser.get(i).getStrendDate() + " " + pricingInser.get(i).getStrendTime());
                }
                else{
                    pricingInsert.setStrendDate(pricingInser.get(i).getStrendDate());
                }
                //pricingInsert.setStrendDate(pricingInser.get(i).getStrendDate() + " " + (pricingInser.get(i).getStrendTime()));

                pricingInsert.setStrendTime(pricingInser.get(i).getStrendTime());
                Log.e("#######", " str kann value before service calling : " + pricingInser.get(i).getStrKann());
                if (fromWhichBtn == 1)
                {
                    pricingInsert.setAngebotSuffix(i);
                }
                else if (fromWhichBtn == 2)
                {
                    pricingInsert.setAngebotSuffix(i);
                }
                else
                {
                    pricingInsert.setAngebotSuffix(0);
                }
                pricingInsert.setKaNr(KaNr);
                pricingInsert.setWarenkorbart(Warenkorbart);
                pricingInsert.setWinterreifenpauschale(Winterreifenpauschale);
                pricingInsert.setWochenendeMitversichert(WochenendeMitversichert);
                pricingInsert.setGenehmigungsgebiet(isGenehmigungsgebiet);

                Gson gson = new GsonBuilder().serializeNulls().create();
                Pricing2InsertPriceUseInformationListData myJson = gson.fromJson(PriceUseInformationList, Pricing2InsertPriceUseInformationListData.class);

                pricingInsert.setStrAVO(myJson.getAVO());
                pricingInsert.setStrAVOMobile(myJson.getAVOTelefon());
                listOfPricingSend.add(pricingInsert);
                String json = new Gson().toJson(listOfPricingSend);
                Log.e(" fragment 333"," json string while calling service" + json);
            }

            if(DataHelper.isNetworkAvailable(context))
            {
                AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        //Toast.makeText(getActivity(),"result variable in price :  "+result, Toast.LENGTH_LONG).show();
                        Log.e(" &&&&&"," result varaible after successfully service call :  "+result);
                        try
                        {
                            if(result.equalsIgnoreCase("error")){
                                String json = new Gson().toJson(listOfPricingSend);
                                db.addPriceInfo(json);
                                /*for (int i=0;i<20;i++){
                                    db.addPriceInfo(json);
                                }*/

                                /*String json = new Gson().toJson(pricingInsertDuplicate);
                                for (int i=0;i<20;i++){
                                    final ArrayList<Pricing3InsertData> listOfPricingSend = new ArrayList<>();
                                    Gson gson = new Gson();
                                    Pricing3InsertData pricingdata =  gson.fromJson(json,Pricing3InsertData.class);
                                    pricingdata.setNotiz(" : "+i+" : ");

                                    String json2 = gson.toJson(pricingdata);

                                    db.addPriceInfo(json2);
                                }*/



                                JSONObject jsonObject = new JSONObject(result);
                                 JSONArray jsonArray = jsonObject.getJSONArray("Warenkorb");
                                if(jsonArray.length() > 0)
                                {
                                    if (fromWhichBtn == 2)
                                        showShortToast(language.getMessageMunclichesAngeboteBtn());
                                    else if (fromWhichBtn == 0)
                                        showShortToast(language.getMessageMunclichesAngeboteBtn());
                                    else if (fromWhichBtn == 1)
                                        showShortToast(language.getMessageAngeboteBtn());
                                    else if (fromWhichBtn == 3)
                                        ;//showShortToast(language.getMessageMunclichesAngeboteBtn());
                                    else if (fromWhichBtn == 4)
                                        showShortToast(language.getMessageAuftragBtn());
                                }



                            }
                            else if(result.equalsIgnoreCase(""))
                            {
                                String json = new Gson().toJson(listOfPricingSend);
                                db.addPriceInfo(json);
                                showShortToast(language.getMessageFailer());
                            }
                            // successfully added to service
                            else
                            {
                                JSONObject jsonObject = new JSONObject(result);
                                JSONArray jsonArray = jsonObject.getJSONArray("Warenkorb");
                                if(jsonArray.length() > 0)
                                {
                                    if (fromWhichBtn == 2)
                                        showShortToast(language.getMessageMunclichesAngeboteBtn());
                                    else if (fromWhichBtn == 0)
                                        showShortToast(language.getMessageMunclichesAngeboteBtn());
                                    else if (fromWhichBtn == 1)
                                        showShortToast(language.getMessageAngeboteBtn());
                                    else if (fromWhichBtn == 3)
                                        ;//showShortToast(language.getMessageMunclichesAngeboteBtn());
                                    else if (fromWhichBtn == 4)
                                        showShortToast(language.getMessageAuftragBtn());
                                }
                                for (int i = 0; i < jsonArray.length(); i++)
                                {
                                   // InsertPricing1EquipmentData(jsonOfEqi, jsonArray.get(i).toString());
                                    /********20161114*********/
                                    InsertPricing1EquipmentData(jsonOfEqiForInsert.get(i), jsonArray.get(i).toString());
                                    /*************************/
                                }
                                if (fromWhichBtn == 3)
                                {
                                    for (int i = 0; i < jsonArray.length(); i++)
                                    {
                                        lostSaleRejectionReason(jsonArray.get(i).toString());
                                    }
                                }
                                /*for (int i = 0; i < ids.size(); i++)
                                {
                                    db.deleteLostSaleCart(ids.get(i));
                                }
                                pricingInser.clear();
                                pricingInser = null;
                                jsonOfEqi="";
                                lablesLostSale = db.getPricing3LostsaleData(customer_KundenNr);
                                lostSaleAdapter = new Pricing3LostSaleDataAdapter(getActivity(), R.layout.fragment_pricing_3_lost_sale_row, lablesLostSale);
                                lostSaleAdapter.setSelectedIndex(-1);
                                lvPricing3LostSaleListView.setAdapter(lostSaleAdapter);
                                preferences.setisPrice("");
                                if(lablesLostSale.size() == 0)
                                {
                                    etApproachP2.setText("");
                                    etDepartureP2.setText("");
                                    etAdditionalCargoPriceP2.setText("");
                                    etFlatRateP2.setText("");
                                    etNoteP2.setText("");
                                    matecoPriceApplication.saveData(DataHelper.PricingCustomerBasicOrderInfo, new Gson().toJson(new PricingCustomerOrderBasicInfo()));

    //                    PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo,
    //                            new Gson().toJson(new PricingCustomerOrderBasicInfo()));
                                }
                                lostSaleAdapter.notifyDataSetChanged();
                                selectedEquipments.clear();
                        /*if (fromWhichBtn == 2)
                            showShortToast(language.getMessageMunclichesAngeboteBtn());
                        else if (fromWhichBtn == 0)
                            showShortToast(language.getMessageMunclichesAngeboteBtn());
                        else if (fromWhichBtn == 1)
                            showShortToast(language.getMessageAngeboteBtn());
                        else if (fromWhichBtn == 3)
                            ;//showShortToast(language.getMessageMunclichesAngeboteBtn());
                        else if (fromWhichBtn == 4)
                            showShortToast(language.getMessageAuftragBtn());*/

                               /* Gson gson = new Gson();
                                Pricing2InsertPriceUseInformationListData myJson = gson.fromJson(PriceUseInformationList, Pricing2InsertPriceUseInformationListData.class);

                                PricingCustomerOrderBasicInfo model=new PricingCustomerOrderBasicInfo();
                                model.setEnferrung(String.valueOf(myJson.getEntfernung()));
                                model.setZipCode(myJson.getEinsatzPLZ());
                                model.setPlace(myJson.getEinsatzort());
                                model.setStreet(myJson.getEinsatzStrasse());
                                model.setZusatz(myJson.getZusatz());
                                model.setContactPersonName(myJson.getAVO());
                                model.setContactPersonMobile(myJson.getAVOTelefon());
                                String jsonString = new Gson().toJson(model);
                                matecoPriceApplication.saveData(DataHelper.PricingCustomerBasicOrderInfo,jsonString);
                                */
                            }

                        }
                        catch (JSONException e)
                        {
                            /// SHOW MESSAGE HERE
                            e.printStackTrace();
                        }


                        for (int i = 0; i < ids.size(); i++)
                        {
                            db.deleteLostSaleCart(ids.get(i));
                        }
                        pricingInser.clear();
                        pricingInser = null;
                        jsonOfEqi="";
                        /*********20161114*******/
                        jsonOfEqiForInsert.clear();;
                        /************************/
                        lablesLostSale = db.getPricing3LostsaleData(customer_KundenNr);
                        lostSaleAdapter = new Pricing3LostSaleDataAdapter(getActivity(), R.layout.fragment_pricing_3_lost_sale_row, lablesLostSale);
                        lostSaleAdapter.setSelectedIndex(-1);
                        lvPricing3LostSaleListView.setAdapter(lostSaleAdapter);
                        preferences.setisPrice("");
                        if(lablesLostSale.size() == 0)
                        {
                            etApproachP2.setText("");
                            etDepartureP2.setText("");
                            etAdditionalCargoPriceP2.setText("");
                            etFlatRateP2.setText("");
                            etNoteP2.setText("");
                            matecoPriceApplication.saveData(DataHelper.PricingCustomerBasicOrderInfo, new Gson().toJson(new PricingCustomerOrderBasicInfo()));

    //                    PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo,
    //                            new Gson().toJson(new PricingCustomerOrderBasicInfo()));
                        }
                        lostSaleAdapter.notifyDataSetChanged();
                        selectedEquipments.clear();
                        /*if (fromWhichBtn == 2)
                            showShortToast(language.getMessageMunclichesAngeboteBtn());
                        else if (fromWhichBtn == 0)
                            showShortToast(language.getMessageMunclichesAngeboteBtn());
                        else if (fromWhichBtn == 1)
                            showShortToast(language.getMessageAngeboteBtn());
                        else if (fromWhichBtn == 3)
                            ;//showShortToast(language.getMessageMunclichesAngeboteBtn());
                        else if (fromWhichBtn == 4)
                            showShortToast(language.getMessageAuftragBtn());*/

                        Gson gson = new Gson();
                        Pricing2InsertPriceUseInformationListData myJson = gson.fromJson(PriceUseInformationList, Pricing2InsertPriceUseInformationListData.class);

                        PricingCustomerOrderBasicInfo model=new PricingCustomerOrderBasicInfo();
                        model.setEnferrung(String.valueOf(myJson.getEntfernung()));
                        model.setZipCode(myJson.getEinsatzPLZ());
                        model.setPlace(myJson.getEinsatzort());
                        model.setStreet(myJson.getEinsatzStrasse());
                        model.setZusatz(myJson.getZusatz());
                        model.setContactPersonName(myJson.getAVO());
                        model.setContactPersonMobile(myJson.getAVOTelefon());
                        String jsonString = new Gson().toJson(model);
                        matecoPriceApplication.saveData(DataHelper.PricingCustomerBasicOrderInfo,jsonString);


                        //Log.e(""," priceing  string : "+pricing);
                    }
                };

                try
                {
                    String json = new Gson().toJson(listOfPricingSend);
                    Log.e(" %^%^%%^%^%^%^%^ "," json string final before calling service : "+json);
                    /*String url = DataHelper.ACCESS_PROTOCOL +
                            DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.PriceInsert;*/
                    String url = DataHelper.URL_PRICE_HELPER+"priceinsert";
                    MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                    multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")));
                    multipartEntity.addPart("priceinsert", new StringBody(json, Charset.forName("UTF-8")));
                    AsyncTaskWithAuthorizationHeaderPost asyncTaskPost = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, getActivity(), multipartEntity, true, language);
                    asyncTaskPost.execute();
                }
                catch (Exception e)

                {
                    Log.e(" $$$$$$$ "," exception while calling ser vie : "+e.toString());
                    e.printStackTrace();
                }
            }
            else
            {
                showShortToast(language.getMessageNetworkNotAvailable());
            }
        }

        public void InsertPricing1EquipmentData(String json, String Warenkorb)
        {
            ArrayList<Pricing1EquipmentInsertData> listOfPriceingEquInsert = new ArrayList<Pricing1EquipmentInsertData>();
            if (json.equals("null"))
            {
                Log.e("Equipment Json is null", json);
            }
            else
            {
                try
                {
                    JSONArray jsonArrayEqp = new JSONArray(json);
                    listOfPriceingEquInsert.clear();
                    for (int i = 0; i < jsonArrayEqp.length(); i++)
                    {
                        Pricing1EquipmentInsertData priceing1Equ = new Pricing1EquipmentInsertData();
                        priceing1Equ.setWarenkorb(Warenkorb);
                        priceing1Equ.setAusstattung(Integer.parseInt(jsonArrayEqp.getJSONObject(i).getString("Ausstattung")));
                        priceing1Equ.setPreis(Double.parseDouble(jsonArrayEqp.getJSONObject(i).getString("Preis")));
                        listOfPriceingEquInsert.add(priceing1Equ);
                    }
                    String jsonFinal = new Gson().toJson(listOfPriceingEquInsert);
                    AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult()
                    {
                        @Override
                        public void OnAsynResult(String result)
                        {
                            Log.e("result of eqp.", result);
                        }
                    };
                    try
                    {
                        Log.e("json of final eqp....", jsonFinal);
                        //String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.PricePriceEquipmentInsert;
                        String url = DataHelper.URL_PRICE_HELPER+"priceequipmentinsert";
                        MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                        multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")));
                        multipartEntity.addPart("EquipmentsList", new StringBody(jsonFinal, Charset.forName("UTF-8")));
                        AsyncTaskWithAuthorizationHeaderPost asyncTaskPost = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, getActivity(), multipartEntity, true, language);
                        asyncTaskPost.execute();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }


        private void insertDataIntoDB(int fromWhichBtn)
        {
            if (matecoPriceApplication.isCustomerLoaded(DataHelper.isCustomerLoaded, false))
            {

                Pricing3InsertData pricingInser = new Pricing3InsertData();
                String loginUserNumberrange = matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserId();
                String Kontakt = matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();
                String KundenNr = matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKundenNr();
                int Mandant = branchId;
                int Warenkorbart = fromWhichBtn;
                String Hoehengruppe = deviceType;
                int Einheit_Mietdauer = rental;
                int Mietdauer = rentalDaysWithoutSatSun;
                // double Mietpreis = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(etGesAmenitiesP2.getText().toString().trim()));// gesAminities;
                //double Mietpreis = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(etEuipmentRentP2.getText().toString().trim()));// gesAminities;
                String Mietpreis = etEuipmentRentP2.getText().toString().trim();// gesAminities;

                double Standtag = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(etSatntagP2.getText().toString().trim()));
                if(Standtag == 0.00)
                {
                    Standtag = 100.00;
                }
                String Selbstbehalt = String.valueOf((int) SB);
                int HandlingFee;
                if (chkHandllingFeeP2.isChecked())
                {
                    HandlingFee = 1;
                }
                else
                {
                    HandlingFee = 0;
                }
                int ServicePauschale;
                if (chkServicePackagesP2.isChecked())
                {
                    ServicePauschale = 1;
                }
                else
                {
                    ServicePauschale = 0;
                }
                double Versicherung = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(etHaftbP2.getText().toString().trim()));

                int WochenendeMitversichert;
                if (chkCalendarDaily.isChecked())
                {
                    WochenendeMitversichert = 1;
                }
                else
                {
                    WochenendeMitversichert = 0;
                }
                double TransportAnfahrt = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(etApproachP2.getText().toString().trim()));
                double TransportPauschal = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(etFlatRateP2.getText().toString().trim()));
                double TransportAbfahrt = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(etDepartureP2.getText().toString().trim()));
                double Beiladungspauschale = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(etAdditionalCargoPriceP2.getText().toString().trim()));
                String Einsatzinformation = EinsatzinformationId;
                String Besteller = contactPerson;
                String Besteller_Telefon = besteller_Telefon;
                String Besteller_Email = besteller_Email;
                String Notiz = etNoteP2.getText().toString().trim();
                int Ersteller = 0;
                try
                {
                    String est = matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber();
                    if (est == null || est.equals("null") || est.equals(""))
                    {
                        Ersteller = 0;
                    }
                    else
                    {
                        Ersteller = Integer.parseInt(est);
                    }
                }
                catch (Exception e)
                {
                    Ersteller = 0;
                    e.printStackTrace();
                }
                int KaNr = kaNrOfLoadedCustomer;
                String AnsPartner = contactPersonNo;
                String Besteller_Anrede = besteller_Anrede;
                String Besteller_Mobil = besteller_Mobil;
                //String Mautkilometer = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(etTollP2.getText().toString().trim()))+"";
                String Mautkilometer = etTollP2.getText().toString().trim()+"";
                int Winterreifenpauschale = 0;
                int Bearbeitet = 0;
                int Kalendertage;
                if (chkCalendarDaily.isChecked())
                {
                    Kalendertage = 1;
                }
                else
                {
                    Kalendertage = 0;
                }
                String Referenz = "";
                String Geratetype = GeratetypeId;
                pricingInser.setKontakt(Kontakt);
                pricingInser.setKundenNr(KundenNr);
                pricingInser.setMandant(Mandant);
                pricingInser.setWarenkorbart(Warenkorbart);
                pricingInser.setHoehengruppe(Hoehengruppe);
                pricingInser.setEinheit_Mietdauer(Einheit_Mietdauer);
                pricingInser.setMietdauer(Mietdauer);
                pricingInser.setMietpreis(Mietpreis);
                pricingInser.setStandtag(Standtag);
                pricingInser.setSelbstbehalt(Selbstbehalt);
                pricingInser.setHandlingFee(HandlingFee);
                pricingInser.setServicePauschale(ServicePauschale);
                pricingInser.setVersicherung(Versicherung);
                pricingInser.setWochenendeMitversichert(WochenendeMitversichert);
                pricingInser.setTransportAnfahrt(TransportAnfahrt);
                pricingInser.setTransportPauschal(TransportPauschal);
                pricingInser.setTransportAbfahrt(TransportAbfahrt);
                pricingInser.setBeiladungspauschale(Beiladungspauschale);
                pricingInser.setEinsatzinformation(Einsatzinformation);
                pricingInser.setBesteller(Besteller);
                pricingInser.setBesteller_Telefon(Besteller_Telefon);
                pricingInser.setBesteller_Email(Besteller_Email);
                pricingInser.setNotiz(Notiz);
                pricingInser.setErsteller(Ersteller);
                pricingInser.setKaNr(KaNr);
                pricingInser.setAnsPartner(AnsPartner);
                pricingInser.setBesteller_Anrede(Besteller_Anrede);
                pricingInser.setBesteller_Mobil(Besteller_Mobil);
                pricingInser.setMautkilometer(Mautkilometer);
                pricingInser.setWinterreifenpauschale(Winterreifenpauschale);
                pricingInser.setBearbeitet(Bearbeitet);
                pricingInser.setKalendertage(Kalendertage);
                pricingInser.setReferenz(Referenz);
                pricingInser.setGeraetetyp(Geratetype);
                pricingInser.setUserID(loginUserNumberrange);

                pricingInser.setBranch(branchName);
                pricingInser.sethGRP(deviceType);
                pricingInser.setDeviceType("");
                pricingInser.setManufacturer("");
                pricingInser.setType("");
                pricingInser.setMd(rentalDaysWithoutSatSun);
                pricingInser.setRentalPrice(price);
                pricingInser.setSb(SB);
                pricingInser.setHfStatus(hfStastus);
                pricingInser.setSpStatus(spStatus);
                pricingInser.setHb(haftb);
                pricingInser.setBest(contactPerson);
                pricingInser.setKundenNr(customer_KundenNr);

                pricingInser.setKann(Boolean.parseBoolean(parsableClass.flagKann));
                pricingInser.setLieferung(Boolean.parseBoolean(parsableClass.flagLieferung));
                pricingInser.setVoranmeldung(Boolean.parseBoolean(parsableClass.flagVoranmeldung));
                pricingInser.setBenachrichtgung(Boolean.parseBoolean(parsableClass.flagBenachrichtgung));
                pricingInser.setRampena(Boolean.parseBoolean(parsableClass.flagRampena));
                pricingInser.setSonstige(Boolean.parseBoolean(parsableClass.flagSonstige));
                pricingInser.setEinweisung(Boolean.parseBoolean(parsableClass.flagEinweisung));
                pricingInser.setSelbstfahrer(Boolean.parseBoolean(parsableClass.flagSelbstfahrer));

                pricingInser.setStrKann(parsableClass.strKann);
                pricingInser.setStrVoranmeldung(parsableClass.strVoranmeldung);
                pricingInser.setStrBenachrich(parsableClass.strBenachrich);
                pricingInser.setStrSonstige(parsableClass.strSonstige);
                pricingInser.setintLadeiahrzeug(parsableClass.intSpinneritem);
                pricingInser.setStrstartDate(preferences.getstartDate());
                pricingInser.setStrstartTime(preferences.getstartTime());
                pricingInser.setStrendDate(preferences.getendDate());
                pricingInser.setStrendTime(preferences.getendTime());

                Log.e(""," start date end ate and time infor in 3 fragment : "+parsableClass.strStartDate+" : "+parsableClass.strStartTime+" : "+
                        parsableClass.strEndDate+" : "+parsableClass.strEndtime);

                //String json = new Gson().toJson(pricingInser);
                //Log.e("insert json of equi.", equipmentJson);
                db.addPricing3InsertJson(customer_KundenNr, equipmentJson, pricingInser);

            }
            else
            {

            }
        }


        private void lostSaleRejectionReason(String LostsaleUId)
        {
            Log.e("LostsaleUId", LostsaleUId);
            double GeratermietePrice;
            String GeratermieteCompi;
            double TransportPrice;
            String TransportCompi;
            double LimitationOfLiabilityPrice;
            String LimitationOfLiabilityCompi;
            String TheCustomerHasNotReceivedTheOrderCompi;
            String Others;
            if (chkLogisticsDLGP3.isChecked())
            {
                AbsagegrundIds = 1;
                WarenkorbIds.add(LostsaleUId);
                AbsagegrundId.add(1);
                AbsPreis.add(0.0);
                Wettbewerber.add("");
                Notiz.add("");
                chkCounter++;
            }
            if (chkLogisticsDLGP3.isChecked())
            {
                AbsagegrundIds = 2;
                WarenkorbIds.add(LostsaleUId);
                AbsagegrundId.add(2);
                AbsPreis.add(0.0);
                Wettbewerber.add("");
                Notiz.add("");
                chkCounter++;
            }
            if (chkAvailabilityDLGP3.isChecked())
            {
                AbsagegrundIds = 3;
                WarenkorbIds.add(LostsaleUId);
                AbsagegrundId.add(3);
                AbsPreis.add(0.0);
                Wettbewerber.add("");
                Notiz.add("");
                chkCounter++;
            }
            if (chkPriceDLGP3.isChecked())
            {
                AbsagegrundIds = 4;
                WarenkorbIds.add(LostsaleUId);
                AbsagegrundId.add(4);
                AbsPreis.add(0.0);
                Wettbewerber.add("");
                Notiz.add("");
                chkCounter++;
            }

            if (chkGeratermieteDLGP3.isChecked())
            {
                //chkGeratermieteDLGP3.setChecked(true);
                AbsagegrundIds = 5;
                WarenkorbIds.add(LostsaleUId);
                GeratermieteCompi = etGeratermieteCompititerDLGP3.getText().toString().trim();
                AbsagegrundId.add(5);
                if (etGeratermietePriceDLGP3.getText().toString().trim().equals(""))
                {
                    GeratermietePrice = 0.0;
                }
                else
                {
                    GeratermietePrice = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(etGeratermietePriceDLGP3.getText().toString().trim()));
                }
                AbsPreis.add(GeratermietePrice);
                Wettbewerber.add(GeratermieteCompi);
                Notiz.add("");
                chkCounter++;
            }

            if (chkTransportDLGP3.isChecked())
            {
                AbsagegrundIds = 6;
                WarenkorbIds.add(LostsaleUId);
                //chkTransportDLGP3.setChecked(true);
                TransportCompi = etTransportCompititerDLGP3.getText().toString().trim();
                if (etTransportPriceDLGP3.getText().toString().trim().equals(""))
                {
                    TransportPrice = 0.0;
                }
                else
                {
                    TransportPrice = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(etTransportPriceDLGP3.getText().toString().trim()));
                }
                AbsagegrundId.add(6);
                AbsPreis.add(TransportPrice);
                Wettbewerber.add(TransportCompi);
                Notiz.add("");
                chkCounter++;
            }

            if (chkLimitationOfLiabilityDLGP3.isChecked())
            {
                AbsagegrundIds = 7;
                WarenkorbIds.add(LostsaleUId);
                chkLimitationOfLiabilityDLGP3.setChecked(true);
                LimitationOfLiabilityCompi = etLimitationOfLiabilityCompititerDLGP3.getText().toString().trim();
                if (etLimitationOfLiabilityPriceDLGP3.getText().toString().trim().equals(""))
                {
                    LimitationOfLiabilityPrice = 0.0;
                }
                else
                {
                    LimitationOfLiabilityPrice = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(etLimitationOfLiabilityPriceDLGP3.getText().toString().trim()));
                }
                AbsagegrundId.add(7);
                AbsPreis.add(LimitationOfLiabilityPrice);
                Wettbewerber.add(LimitationOfLiabilityCompi);
                Notiz.add("");
                chkCounter++;
            }
            if (chkTermsofPaymentDLGP3.isChecked())
            {
                AbsagegrundIds = 8;
                WarenkorbIds.add(LostsaleUId);
                AbsagegrundId.add(8);
                AbsPreis.add(0.0);
                Wettbewerber.add("");
                Notiz.add("");
                chkCounter++;
            }
            if (chkPaysNoAdvancePaymentDLGP3.isChecked())
            {
                AbsagegrundIds = 9;
                WarenkorbIds.add(LostsaleUId);
                AbsagegrundId.add(9);
                AbsPreis.add(0.0);
                Wettbewerber.add("");
                Notiz.add("");
                chkCounter++;
            }

            if (chkCashDiscountDLGP3.isChecked())
            {

                AbsagegrundIds = 10;
                WarenkorbIds.add(LostsaleUId);
                AbsagegrundId.add(10);
                AbsPreis.add(0.0);
                Wettbewerber.add("");
                Notiz.add("");
                chkCounter++;
            }
            if (chkTheCustomerHasChosenAnAlternativeMtecoUnitDLGP3.isChecked())
            {
                AbsagegrundIds = 11;
                WarenkorbIds.add(LostsaleUId);
                AbsagegrundId.add(11);
                AbsPreis.add(0.0);
                Wettbewerber.add("");
                Notiz.add("");
                chkCounter++;
            }
            if (chkTheCustomerDoesNotHaveNeedLessDLGP3.isChecked())
            {
                AbsagegrundIds = 12;
                WarenkorbIds.add(LostsaleUId);
                AbsagegrundId.add(12);
                AbsPreis.add(0.0);
                Wettbewerber.add("");
                Notiz.add("");
                chkCounter++;
            }
            if (chkTheCustomerHasNotReceivedTheOrderDLGP3.isChecked())
            {
                AbsagegrundIds = 13;
                WarenkorbIds.add(LostsaleUId);
                TheCustomerHasNotReceivedTheOrderCompi = etTheCustomerHasNotReceivedTheOrderCompititerDLGP3.getText().toString().trim();
                AbsagegrundId.add(13);
                AbsPreis.add(0.0);
                Wettbewerber.add(TheCustomerHasNotReceivedTheOrderCompi);
                Notiz.add("");
                chkCounter++;
            }

            if (chkOtherDLGP3.isChecked())
            {
                AbsagegrundIds = 14;
                WarenkorbIds.add(LostsaleUId);
                Others = etOtherDLGP3.getText().toString().trim();
                AbsagegrundId.add(14);
                AbsPreis.add(0.0);
                Wettbewerber.add(Others);
                Notiz.add("");
                chkCounter++;
            }

            if (DataHelper.isNetworkAvailable(context) && chkCounter > 0)
            {
                String url = null;
                ArrayList<Pricing3LostSaleInsertData> listOfLoastsaleinsert = new ArrayList<Pricing3LostSaleInsertData>();
                for (int i = 0; i < selectedEquipments.size(); i++)
                {
                    for (int j = 0; j < AbsagegrundId.size(); j++)
                    {
                        Pricing3LostSaleInsertData pricingLostSaleInst = new Pricing3LostSaleInsertData();
                        //pricingLostSaleInst.setWarenkorb(WarenkorbIds.get(j));
                        pricingLostSaleInst.setWarenkorb(LostsaleUId);
                        pricingLostSaleInst.setAbsagegrund(AbsagegrundId.get(j));
                        pricingLostSaleInst.setPreis(AbsPreis.get(j));
                        pricingLostSaleInst.setWettbewerber(Wettbewerber.get(j));
                        pricingLostSaleInst.setNotiz(Notiz.get(j));
                        listOfLoastsaleinsert.add(pricingLostSaleInst);
                    }

                    db.deleteLostSaleCart(selectedEquipments.get(i));
                    lablesLostSale = db.getPricing3LostsaleData(customer_KundenNr);
                }
                String json = new Gson().toJson(listOfLoastsaleinsert);
                Log.e(" LostSale.json...", json);
                AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        Log.e("lostsale rejection result", result);
                    }
                };

                try
                {
                    //url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.PriceReasonRejectionInsert;
                    url = DataHelper.URL_PRICE_HELPER+"pricereasonrejectioninsert";
                    Log.e("url", url);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                try
                {
                    multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")));
                    multipartEntity.addPart("RejectionReasonsList", new StringBody(json, Charset.forName("UTF-8")));
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
                AsyncTaskWithAuthorizationHeaderPost asyncTask = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, getActivity(), multipartEntity, true, language);
                asyncTask.execute();

                lostSaleAdapter = new Pricing3LostSaleDataAdapter(getActivity(), R.layout.fragment_pricing_3_lost_sale_row, lablesLostSale);

                lvPricing3LostSaleListView.setAdapter(lostSaleAdapter);
                preferences.setisPrice("yes");
                lostSaleAdapter.notifyDataSetChanged();
                selectedEquipments.clear();
                showShortToast(language.getMessageLostSaleBtn());
                LostsaleUId = "";

            }
            else if(chkCounter <= 0)
            {
                showShortToast(language.getMessageSelectOneReasonForRejection());
            }
            else
            {
                showShortToast(language.getMessageNetworkNotAvailable());
            }
        }

        public void InsertPriceUseInformationList()
        {
            if(DataHelper.isNetworkAvailable(context))
            {
                Log.e("PriceUseInfo ..", PriceUseInformationList);
                AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        result = result.replace("\"", "");
                        if (result.matches("[0-9]+") && result.length() > 0) {
                            EinsatzinformationId = result.replace("\"", "");
                            Log.e("EinsatzinformationId result", EinsatzinformationId);
                            insertDataIntoDB(0);
                        }else {

                        }

                    }
                };
                try
                {
                    //String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.PriceUseInformationInsert + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");

                    String url = DataHelper.URL_PRICE_HELPER + "priceuseinformationinsert";

                    /*String url = DataHelper.URL_PRICE_HELPER + "priceuseinformationinsert/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                    url += "/priceuseinformation=" + URLEncoder.encode(PriceUseInformationList, "UTF-8"); //&&&&*/
                    Log.e("url", url);

                    MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                    multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")));
                    multipartEntity.addPart("priceuseinformation", new StringBody(PriceUseInformationList, Charset.forName("UTF-8")));
                    AsyncTaskWithAuthorizationHeaderPost asyncTaskPost = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, getActivity(), multipartEntity, true, language);
                    asyncTaskPost.execute();

                    /*BasicAsyncTaskGetRequest asyncTaskLoadContactPersons = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                    asyncTaskLoadContactPersons.execute();*/
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
        }

        /**
         * Hides virtual keyboard
         */
        protected void hideKeyboard(View view)
        {
            InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        private void savePricingCustomerOrderBasicInfo()
        {
            PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo, new Gson().toJson(new PricingCustomerOrderBasicInfo()));
            double value = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(etApproachP2.getText().toString()));
            model.setAbfahrt(value + "");
            value = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(etDepartureP2.getText().toString()));
            model.setAnfahrt(value+"");
            value = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(etAdditionalCargoPriceP2.getText().toString()));
            model.setBeiladungsPreis(value + "");
            value = Double.parseDouble(DataHelper.getEnglishCurrencyFromGerman(etFlatRateP2.getText().toString()));
            model.setPauschale(value + "");
            String notes = etNoteP2.getText().toString();
            model.setNotes(notes);
            matecoPriceApplication.saveData(DataHelper.PricingCustomerBasicOrderInfo, new Gson().toJson(model));
            if(model != null && model.getAbfahrt() != null && !model.getAbfahrt().equals(""))
            {
                etApproachP2.setText(DataHelper.getGermanCurrencyFormat(model.getAbfahrt())+"");
                GlobalMethods.setBlankValueForZero(etApproachP2);
            }

            if(model != null && model.getAnfahrt() != null && !model.getAnfahrt().equals(""))
            {
                etDepartureP2.setText(DataHelper.getGermanCurrencyFormat(model.getAnfahrt()));
                GlobalMethods.setBlankValueForZero(etDepartureP2);
            }
            if(model != null && model.getBeiladungsPreis() != null && !model.getBeiladungsPreis().equals(""))
            {
                etAdditionalCargoPriceP2.setText(DataHelper.getGermanCurrencyFormat(model.getBeiladungsPreis()));
                GlobalMethods.setBlankValueForZero(etAdditionalCargoPriceP2);
            }
            if(model != null && model.getPauschale() != null && !model.getPauschale().equals(""))
            {
                etFlatRateP2.setText(DataHelper.getGermanCurrencyFormat(model.getPauschale()));
                GlobalMethods.setBlankValueForZero(etFlatRateP2);
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
        private void populateViewForOrientation(LayoutInflater inflater, ViewGroup viewGroup) {
            viewGroup.removeAllViewsInLayout();
            View subview = inflater.inflate(R.layout.fragment_pricing_3, viewGroup);

            // Find your buttons in subview, set up onclicks, set up callbacks to your parent fragment or activity here.

            // You can create ViewHolder or separate method for that.
            // example of accessing views: TextView textViewExample = (TextView) view.findViewById(R.id.text_view_example);
            // textViewExample.setText("example");
        }
        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            //LayoutInflater inflater = LayoutInflater.from(getActivity());
           // populateViewForOrientation(inflater, (ViewGroup) getView());
            //setRetainInstance(true);
            // Checks the orientation of the screen for landscape and portrait and set portrait mode always
            /*if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                //setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                //linearMain.setWeightSum(1f);
                /// for landscape
                *//* this is to set HEIGHT as orientation change *//*
                *//*paramTop = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 0,0.66f);*//*
                paramListview = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,400);

            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){

                //setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                // for portrait
                *//* this is to set HEIGHT as orientation change *//*
                //linearMain.setWeightSum(1f);
                *//*paramTop = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 0,0.40f);*//*
                paramListview = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,0,1f);

            }
            linearListview.setLayoutParams(paramListview);*/
            //linearTop.setLayoutParams(paramTop);
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
           // outState.putString("approach", etApproachP2.getText().toString());
        }
        public int getScreenOrientation() {
            Display getOrient = getActivity().getWindowManager().getDefaultDisplay();
            int orientation = Configuration.ORIENTATION_UNDEFINED;
            if(getOrient.getWidth()==getOrient.getHeight()){
                orientation = Configuration.ORIENTATION_SQUARE;
            } else{
                if(getOrient.getWidth() < getOrient.getHeight()){
                    orientation = Configuration.ORIENTATION_PORTRAIT;
                }else {
                    orientation = Configuration.ORIENTATION_LANDSCAPE;
                }
            }
            return orientation;
        }
        public static class DecimalDigitsInputFilter implements InputFilter {

            private final int decimalDigits;

            /**
             * Constructor.
             *
             * @param decimalDigits maximum decimal digits
             */
            public DecimalDigitsInputFilter(int decimalDigits) {
                this.decimalDigits = decimalDigits;
            }

            @Override
            public CharSequence filter(CharSequence source,
                                       int start,
                                       int end,
                                       Spanned dest,
                                       int dstart,
                                       int dend) {


                int dotPos = -1;
                int len = dest.length();
                for (int i = 0; i < len; i++) {
                    char c = dest.charAt(i);
                    if (c == ',') {
                        dotPos = i;
                        break;
                    }
                }
                if (dotPos >= 0) {

                    // protects against many dots
                    if (source.equals(",")) {
                        return "";
                    }
                    // if the text is entered before the dot
                    if (dend <= dotPos) {
                        return null;
                    }
                    if (len - dotPos > decimalDigits) {
                        return "";
                    }
                }

                return null;
            }
        }

        }