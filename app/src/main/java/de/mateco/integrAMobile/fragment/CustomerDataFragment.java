package de.mateco.integrAMobile.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.GlobalMethods;
import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.LogApp;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.CountryAdapter;
import de.mateco.integrAMobile.adapter.CustomerBranchAdapter;
import de.mateco.integrAMobile.adapter.LegalFormAdapter;
import de.mateco.integrAMobile.asyncTask.AsyncTaskWithAuthorizationHeaderPost;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.LoadedCustomerFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.ContactPersonModel;
import de.mateco.integrAMobile.model.CountryModel;
import de.mateco.integrAMobile.model.CustomerActivityModel;
import de.mateco.integrAMobile.model.CustomerBranchModel;
import de.mateco.integrAMobile.model.CustomerCompletedOrderModel;
import de.mateco.integrAMobile.model.CustomerDatabaseModel;
import de.mateco.integrAMobile.model.CustomerLostSaleDataModel;
import de.mateco.integrAMobile.model.CustomerModel;
import de.mateco.integrAMobile.model.CustomerOfferModel;
import de.mateco.integrAMobile.model.CustomerOpenOfferModel;
import de.mateco.integrAMobile.model.CustomerOpenOrderModel;
import de.mateco.integrAMobile.model.CustomerProjectModel;
import de.mateco.integrAMobile.model.CustomerUpdateModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LegalFormModel;
import de.mateco.integrAMobile.model.LoginPersonModel;

public class CustomerDataFragment extends LoadedCustomerFragment implements TextView.OnEditorActionListener, View.OnClickListener
{
    private View rootView;
    private Menu menu;
    private boolean isInEditMode = false;
    private Language language;
    private MatecoPriceApplication matecoPriceApplication;
    private EditText textCustomerDataSalesPotential, textCustomerDataOrderNo, textCustomerDataVatNo, textCustomerDataOwner,
            textCustomerDataWebsite, textCustomerDataEmail, textCustomerDataFax, textCustomerDataPhone, textCustomerDataPlace,
            textCustomerDataZipCode, textCustomerDataRoad, textCustomerDataName3, textCustomerDataName2, textCustomerDataName1,
            textCustomerDataMatchCode;
    private EditText textCustomerDataKaNr, textCustomerDataOrderBackLog, textCustomerDataAcquisitionDate,
            textCustomerDataPaymentDeadline, textCustomerDataCreditLimit, textCustomerDataResponsiblePerson,textCustomerDataResponsiblePersonOld,
            textCustomerDataArea;
    private CustomerModel customerStored;
    private Spinner spinnerCustomerDataLegalForm, spinnerCustomerDataCountry, spinnerCustomerDataBranch;
    private ArrayList<CountryModel> listOfCountry;
    private ArrayList<LegalFormModel> listOfLegalForm;
    private CountryModel selectedCountry = null;
    private LegalFormModel selectedLegalForm = null;
    private CountryAdapter countryAdapter;
    private LegalFormAdapter legalFormAdapter;
    //private CustomerModel customerObject;
    private String country, legalForm;
    private String acquisitionDate;
    private ArrayList<CustomerBranchModel> listOfCustomerBranch;
    private CustomerBranchModel selectedBranch;
    private CustomerBranchAdapter adapterCustomerBranch;
    private ImageView imageViewCall, imageViewEmail, imageViewWebsite;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_customer_data, container, false);
        super.initializeFragment(rootView);

//        TextView txtNameFragment = (TextView)rootView.findViewById(R.id.txtNameFragment);
//        String text = getArguments().getString("Name");
//        txtNameFragment.setText(text);

        //super.setUpUi(getActivity().getWindow().getDecorView().getRootView());

        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelCustomerData());
        return rootView;
    }

    @Override
    public void initializeComponents(View rootView)
    {
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        getActivity().invalidateOptionsMenu();
//        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelCustomerData());
        setHasOptionsMenu(true);
        textCustomerDataKaNr = (EditText)rootView.findViewById(R.id.textCustomerDataKaNr);
        textCustomerDataSalesPotential = (EditText)rootView.findViewById(R.id.textCustomerDataSalesPotential);
        textCustomerDataOrderBackLog = (EditText)rootView.findViewById(R.id.textCustomerDataOrderBackLog);
        textCustomerDataAcquisitionDate = (EditText)rootView.findViewById(R.id.textCustomerDataAcquisitionDate);
        textCustomerDataPaymentDeadline = (EditText)rootView.findViewById(R.id.textCustomerDataPaymentDeadline);
        textCustomerDataCreditLimit = (EditText)rootView.findViewById(R.id.textCustomerDataCreditLimit);
        textCustomerDataOrderNo = (EditText)rootView.findViewById(R.id.textCustomerDataOrderNo);
        textCustomerDataVatNo = (EditText)rootView.findViewById(R.id.textCustomerDataVatNo);
        textCustomerDataOwner = (EditText)rootView.findViewById(R.id.textCustomerDataOwner);
        spinnerCustomerDataLegalForm = (Spinner)rootView.findViewById(R.id.spinnerCustomerDataLegalForm);
        spinnerCustomerDataBranch = (Spinner)rootView.findViewById(R.id.spinnerCustomerDataBranch);
        textCustomerDataResponsiblePerson = (EditText)rootView.findViewById(R.id.textCustomerDataResponsiblePerson);
        textCustomerDataResponsiblePersonOld = (EditText)rootView.findViewById(R.id.textCustomerDataResponsiblePersonOld);
        textCustomerDataArea = (EditText)rootView.findViewById(R.id.textCustomerDataArea);
        textCustomerDataWebsite = (EditText)rootView.findViewById(R.id.textCustomerDataWebsite);
        textCustomerDataEmail = (EditText)rootView.findViewById(R.id.textCustomerDataEmail);
        textCustomerDataFax = (EditText)rootView.findViewById(R.id.textCustomerDataFax);
        textCustomerDataPhone = (EditText)rootView.findViewById(R.id.textCustomerDataPhone);
        spinnerCustomerDataCountry = (Spinner)rootView.findViewById(R.id.spinnerCustomerDataCountry);
        textCustomerDataPlace = (EditText)rootView.findViewById(R.id.textCustomerDataPlace);
        textCustomerDataZipCode = (EditText)rootView.findViewById(R.id.textCustomerDataZipCode);
        textCustomerDataRoad = (EditText)rootView.findViewById(R.id.textCustomerDataRoad);
        textCustomerDataName3 = (EditText)rootView.findViewById(R.id.textCustomerDataName3);
        textCustomerDataName2 = (EditText)rootView.findViewById(R.id.textCustomerDataName2);
        textCustomerDataName1 = (EditText)rootView.findViewById(R.id.textCustomerDataName1);
        textCustomerDataMatchCode = (EditText)rootView.findViewById(R.id.textCustomerDataMatchCode);
        listOfCountry = new ArrayList<CountryModel>();
        DataBaseHandler db = new DataBaseHandler(getActivity());
        listOfCountry = db.getCountries();
        listOfLegalForm = new ArrayList<LegalFormModel>();
        listOfLegalForm = db.getLegalForms();
        listOfCustomerBranch = new ArrayList<>();
        listOfCustomerBranch = db.getCustomerBranches();
        countryAdapter = new CountryAdapter(getActivity(), listOfCountry, R.layout.list_item_spinner_country, language);
        legalFormAdapter = new LegalFormAdapter(getActivity(), listOfLegalForm, R.layout.list_item_spinner_legal_form, language);
        adapterCustomerBranch = new CustomerBranchAdapter(getActivity(), listOfCustomerBranch, R.layout.list_item_spinner_customer_branch, language);
        spinnerCustomerDataCountry.setAdapter(countryAdapter);
        spinnerCustomerDataLegalForm.setAdapter(legalFormAdapter);
        spinnerCustomerDataBranch.setAdapter(adapterCustomerBranch);
        spinnerCustomerDataLegalForm.setSelection(0);

        imageViewCall = (ImageView)rootView.findViewById(R.id.imageViewCall);
        imageViewEmail = (ImageView)rootView.findViewById(R.id.imageViewEmail);
        imageViewWebsite = (ImageView)rootView.findViewById(R.id.imageViewWebsite);

//        if(listOfCountry.size() > 0)
//            selectedCountry = listOfCountry.get(0);
//        if(listOfLegalForm.size() > 0)
//            selectedLegalForm = listOfLegalForm.get(0);
        makeEditable(false);

        setLanguage();
        loadDefaultValues();

        super.initializeComponents(rootView);
    }

    @Override
    public void bindEvents(View rootView)
    {
        textCustomerDataWebsite.setOnEditorActionListener(this);
        imageViewCall.setOnClickListener(this);
        imageViewEmail.setOnClickListener(this);
        imageViewWebsite.setOnClickListener(this);
        spinnerCustomerDataCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (position == 0)
                {
                    selectedCountry = null;
                }
                else
                {
                    selectedCountry = listOfCountry.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        spinnerCustomerDataLegalForm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0)
                {
                    selectedLegalForm = null;
                }
                else
                {
                    selectedLegalForm = listOfLegalForm.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        spinnerCustomerDataBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0)
                {
                    selectedBranch = null;
                }
                else
                {
                    selectedBranch = listOfCustomerBranch.get(position - 1);
                    LogApp.showLog(" combo box ite selected "," selected Branchd : "+selectedBranch.getName());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        super.bindEvents(rootView);
    }

    private void setLanguage()
    {
        TextView labelCustomerDataMatchCode,labelCustomerDataName1,labelCustomerDataName2,labelCustomerDataName3,labelCustomerDataRoad,
                labelCustomerDataZipcode,labelCustomerDataPlace, labelCustomerDataCountry,labelCustomerDataPhone,labelCustomerDataFax,
                labelCustomerDataEmail,labelCustomerDataWebsite,labelCustomerDataArea,labelCustomerDataResponsiblePerson,labelCustomerDataResponsiblePersonOld,
                labelCustomerDataLegalForm, labelCustomerDataOwner, labelCustomerDataVatnr, labelCustomerDataOrderNo,
                labelCustomerDataCreditLimit, labelCustomerDataPaymentDeadline, labelCustomerDataAcquisitionDate, labelCustomerDataOrderBacklog,
                labelCustomerDataSalesPotential, labelCustomerDataKanr, labelCustomerDataBranch;

        labelCustomerDataMatchCode = (TextView)rootView.findViewById(R.id.labelCustomerDataMatchCode);
        labelCustomerDataName1 = (TextView)rootView.findViewById(R.id.labelCustomerDataName1);
        labelCustomerDataName2 = (TextView)rootView.findViewById(R.id.labelCustomerDataName2);
        labelCustomerDataName3 = (TextView)rootView.findViewById(R.id.labelCustomerDataName3);
        labelCustomerDataRoad = (TextView)rootView.findViewById(R.id.labelCustomerDataRoad);
        labelCustomerDataZipcode = (TextView)rootView.findViewById(R.id.labelCustomerDataZipcode);
        labelCustomerDataCountry = (TextView)rootView.findViewById(R.id.labelCustomerDataCountry);
        labelCustomerDataPhone = (TextView)rootView.findViewById(R.id.labelCustomerDataPhone);
        labelCustomerDataFax = (TextView)rootView.findViewById(R.id.labelCustomerDataFax);
        labelCustomerDataEmail = (TextView)rootView.findViewById(R.id.labelCustomerDataEmail);
        labelCustomerDataWebsite = (TextView)rootView.findViewById(R.id.labelCustomerDataWebsite);
        labelCustomerDataArea = (TextView)rootView.findViewById(R.id.labelCustomerDataArea);
        labelCustomerDataResponsiblePerson = (TextView)rootView.findViewById(R.id.labelCustomerDataResponsiblePerson);
        labelCustomerDataResponsiblePersonOld = (TextView)rootView.findViewById(R.id.labelCustomerDataResponsiblePersonOld);
        labelCustomerDataPlace = (TextView)rootView.findViewById(R.id.labelCustomerDataPlace);
        labelCustomerDataLegalForm = (TextView)rootView.findViewById(R.id.labelCustomerDataLegalForm);
        labelCustomerDataOwner = (TextView)rootView.findViewById(R.id.labelCustomerDataOwner);
        labelCustomerDataVatnr = (TextView)rootView.findViewById(R.id.labelCustomerDataVatnr);
        labelCustomerDataOrderNo = (TextView)rootView.findViewById(R.id.labelCustomerDataOrderNo);
        labelCustomerDataCreditLimit = (TextView)rootView.findViewById(R.id.labelCustomerDataCreditLimit);
        labelCustomerDataPaymentDeadline = (TextView)rootView.findViewById(R.id.labelCustomerDataPaymentDeadline);
        labelCustomerDataAcquisitionDate = (TextView)rootView.findViewById(R.id.labelCustomerDataAcquisitionDate);
        labelCustomerDataOrderBacklog = (TextView)rootView.findViewById(R.id.labelCustomerDataOrderBacklog);
        labelCustomerDataSalesPotential = (TextView)rootView.findViewById(R.id.labelCustomerDataSalesPotential);
        labelCustomerDataKanr = (TextView)rootView.findViewById(R.id.labelCustomerDataKanr);
        labelCustomerDataBranch = (TextView)rootView.findViewById(R.id.labelCustomerDataBranch);

        labelCustomerDataMatchCode.setText(language.getLabelMatchCode());
        labelCustomerDataName1.setText(language.getLabelName() + " 1");
        labelCustomerDataName2.setText(language.getLabelName() + " 2");
        labelCustomerDataName3.setText(language.getLabelName() + " 3");
        labelCustomerDataRoad.setText(language.getLabelRoad());
        labelCustomerDataZipcode.setText(language.getLabelZipCode());
        labelCustomerDataFax.setText(language.getLabelFax());
        labelCustomerDataEmail.setText(language.getLabelEmail());
        labelCustomerDataArea.setText(language.getLabelArea());
        labelCustomerDataResponsiblePerson.setText(language.getLabelStatesADM());
        labelCustomerDataResponsiblePersonOld.setText(language.getLabelStatesADMOld());
        labelCustomerDataCreditLimit.setText(language.getLabelCreditLimit());
        labelCustomerDataKanr.setText(language.getLabelKanr());
        labelCustomerDataSalesPotential.setText(language.getLabelSalesPotential());
        labelCustomerDataOrderBacklog.setText(language.getLabelOrderBacklog());
        labelCustomerDataAcquisitionDate.setText(language.getLabelAcquisitionDate());
        labelCustomerDataPaymentDeadline.setText(language.getLabelPaymentDeadline());
        labelCustomerDataOrderNo.setText(language.getLabelOrderNo());
        labelCustomerDataVatnr.setText(language.getLabelVatNo());
        labelCustomerDataOwner.setText(language.getLabelOwner());
        labelCustomerDataLegalForm.setText(language.getLabelLegalForm());
        labelCustomerDataPlace.setText(language.getLabelPlace());
        labelCustomerDataCountry.setText(language.getLabelCountry());
        labelCustomerDataPhone.setText(language.getLabelPhone());
        labelCustomerDataWebsite.setText(language.getLabelWebsite());
        labelCustomerDataBranch.setText(language.getLabelBranch1());
    }

    private void loadDefaultValues()
    {
        if(matecoPriceApplication.isCustomerLoaded(DataHelper.isCustomerLoaded, false))
        {
            customerStored = matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString());
            if(customerStored != null){
                textCustomerDataMatchCode.setText(customerStored.getMatchCode());
                textCustomerDataName1.setText(customerStored.getName1());
                textCustomerDataName2.setText(customerStored.getName2());
                textCustomerDataName3.setText(customerStored.getName3());
                textCustomerDataRoad.setText(customerStored.getStrasse());
                textCustomerDataZipCode.setText(customerStored.getPLZ());
                textCustomerDataPlace.setText(customerStored.getOrt());
            }

            boolean setCountry = false;

            if(customerStored != null) {
                for (CountryModel model : listOfCountry) {
                    if(model != null && customerStored.getLand() != null && model.getCountryName() != null) {
                        if (model.getCountryName().equals(customerStored.getLand())) {
                            selectedCountry = listOfCountry.get(listOfCountry.indexOf(model));
                            spinnerCustomerDataCountry.setSelection(listOfCountry.indexOf(model) + 1);
                            setCountry = true;
                            break;
                        }
                    }

                }
            }
            /*for(int i = 0; i < listOfCountry.size(); i++)
            {
                if(customerStored != null) {
                    if (customerStored.getLand().equals(listOfCountry.get(i).getCountryName())) {
                        spinnerCustomerDataCountry.setSelection(i + 1);
                        selectedCountry = listOfCountry.get(i);
                        setCountry = true;
                        break;
                    }
                }
            }*/
            if(!setCountry)
            {
                //spinnerCustomerDataLegalForm.setSelection(listOfLegalForm.size());
                spinnerCustomerDataCountry.setSelection(0);
            }
            boolean setBranch = false;
            for(int i = 0; i < listOfCustomerBranch.size(); i++)
            {
                if(customerStored != null) {
                    if (String.valueOf(customerStored.getBranche()).equalsIgnoreCase(String.valueOf(listOfCustomerBranch.get(i).getName()))) {
                        spinnerCustomerDataBranch.setSelection(i + 1);
                        selectedBranch = listOfCustomerBranch.get(i);
                        setBranch = true;
                        break;
                    }
                }
            }
            if(!setBranch)
            {
                //spinnerCustomerDataLegalForm.setSelection(listOfLegalForm.size());
                spinnerCustomerDataBranch.setSelection(0);
            }

            //textCustomerDataCountry.setText(customerStored.getLand());
            if(customerStored != null) {
                textCustomerDataPhone.setText(customerStored.getTelefon());
                textCustomerDataFax.setText(customerStored.getTelefax());
                textCustomerDataEmail.setText(customerStored.getEMAil());
                textCustomerDataWebsite.setText(customerStored.getWebsite());

                textCustomerDataArea.setText(customerStored.getGebiet());
                textCustomerDataResponsiblePerson.setText(customerStored.getADM());
                textCustomerDataResponsiblePersonOld.setText(customerStored.getOldADM());
            }
            boolean setLegalForm = false;
            for(int i = 0; i < listOfLegalForm.size(); i++)
            {
                if(customerStored != null) {
                    if (customerStored.getRechtsform().equals((listOfLegalForm.get(i)).getRechtsFormDesignation())) {
                        spinnerCustomerDataLegalForm.setSelection(i + 1);
                        selectedLegalForm = listOfLegalForm.get(i);
                        setLegalForm = true;
                        break;
                    }
                }
            }
            if(!setLegalForm)
            {
                //spinnerCustomerDataLegalForm.setSelection(listOfLegalForm.size());
                spinnerCustomerDataLegalForm.setSelection(0);
            }
            //textCustomerDataLegalForm.setText(customerStored.getRechtsform());
            if(customerStored != null) {
                textCustomerDataOwner.setText(customerStored.getInhaber());
                textCustomerDataVatNo.setText(customerStored.getUSTIDNR());
                textCustomerDataOrderNo.setText(customerStored.getJahresbestellNr());
                //textCustomerDataCreditLimit.setText(String.format("%.2f", Float.parseFloat(customerStored.getKreditlimit())));
                textCustomerDataCreditLimit.setText(DataHelper.getGermanFromEnglish(customerStored.getKreditlimit()));
                textCustomerDataPaymentDeadline.setText(customerStored.getZahlungsziel());
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    textCustomerDataAcquisitionDate.setText(DataHelper.formatDate(format.parse(customerStored.getErfassungsdatum())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                textCustomerDataOrderBackLog.setText(DataHelper.getGermanFromEnglish(customerStored.getAuftragsbestand()));
                //textCustomerDataSalesPotential.setText(DataHelper.getGermanFromEnglish(customerStored.getUmsatzpotenzial()));
                textCustomerDataSalesPotential.setText(customerStored.getUmsatzpotenzial());
                textCustomerDataKaNr.setText(customerStored.getKaNr());
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_all_without_edit, menu);
        menu.findItem(R.id.actionRight).setVisible(false);
        menu.findItem(R.id.actionWrong).setVisible(false);
        menu.findItem(R.id.actionAdd).setVisible(false);

        this.menu = menu;
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
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new SettingFragment(),"Setting");
                //transaction.addToBackStack(SettingFragment.Tag);
                transaction.addToBackStack("Setting");
                transaction.commit();
                return true;
            case R.id.actionBack:
                if(isInEditMode)
                {
                    makeEditable(false);
                    menu.findItem(R.id.actionForward).setVisible(true);
                    menu.findItem(R.id.actionEdit).setVisible(true);
                    menu.findItem(R.id.actionRight).setVisible(false);
                    menu.findItem(R.id.actionWrong).setVisible(false);
                    menu.findItem(R.id.actionSave).setVisible(false);
                }
                else if (getFragmentManager().getBackStackEntryCount() == 0)
                {
                    //getActivity().finish();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.replace(R.id.content_frame, new HomeFragment());
                    getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    transaction.commit();
                }
                else
                {
                    getFragmentManager().popBackStack();
                }
                return true;
            case R.id.actionEdit:
//                FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                transaction1.replace(R.id.content_frame, new CustomerDataFragmentEdit(), "Customer Data Edit");
//                //transaction.addToBackStack(SettingFragment.Tag);
//                transaction1.addToBackStack("Customer Data");
//                transaction1.commit();
                makeEditable(true);
                menu.findItem(R.id.actionEdit).setVisible(false);
                menu.findItem(R.id.actionForward).setVisible(false);
                menu.findItem(R.id.actionRight).setVisible(true);
                menu.findItem(R.id.actionWrong).setVisible(true);
                menu.findItem(R.id.actionBack).setVisible(false);
                menu.findItem(R.id.actionSave).setVisible(false);

                return true;
            case R.id.actionRight:
                updateCustomer();
                return true;
            case R.id.actionWrong:
                if(checkIfTextChanged())
                {
                    DialogInterface.OnClickListener positiveCallback = new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            menu.findItem(R.id.actionForward).setVisible(true);
                            menu.findItem(R.id.actionEdit).setVisible(true);
                            menu.findItem(R.id.actionRight).setVisible(false);
                            menu.findItem(R.id.actionBack).setVisible(true);
                            menu.findItem(R.id.actionWrong).setVisible(false);
                            menu.findItem(R.id.actionSave).setVisible(true);
                            loadDefaultValues();
                            makeEditable(false);
                            dialog.cancel();
                        }
                    };

                    DialogInterface.OnClickListener negativeCallback = new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.cancel();
                        }
                    };
                    String title = language.getLabelConfirmation();
                    //String message = "Are you sure want to delete changes?";
                    String message = language.getMessageDelete();
                    AlertDialog alert = showAlert(title, message, language.getLabelYes(), language.getLabelNo(),
                            positiveCallback, negativeCallback);
                    alert.show();
                }
                else
                {
                    menu.findItem(R.id.actionForward).setVisible(true);
                    menu.findItem(R.id.actionEdit).setVisible(true);
                    menu.findItem(R.id.actionRight).setVisible(false);
                    menu.findItem(R.id.actionWrong).setVisible(false);
                    menu.findItem(R.id.actionBack).setVisible(true);
                    menu.findItem(R.id.actionSave).setVisible(true);
                    loadDefaultValues();
                    makeEditable(false);
                }
                return true;
            case R.id.actionSave:
                DataBaseHandler db = new DataBaseHandler(getActivity());
                CustomerDatabaseModel customerDatabaseModel = new CustomerDatabaseModel();
                customerDatabaseModel.setCustomerNo(customerStored.getKundenNr());
                customerDatabaseModel.setKaNr(customerStored.getKaNr());
                customerDatabaseModel.setCustomerContact(customerStored.getKontakt());
                customerDatabaseModel.setMatchCode(customerStored.getMatchCode());
                customerDatabaseModel.setName1(customerStored.getName1());
                customerDatabaseModel.setRoad(customerStored.getStrasse());
                customerDatabaseModel.setZipCode(customerStored.getPLZ());
                customerDatabaseModel.setPlace(customerStored.getOrt());
                customerDatabaseModel.setTelephone(customerStored.getTelefon());
                customerDatabaseModel.setCustomerModel(customerStored);
                customerDatabaseModel.setListOfContactPerson(matecoPriceApplication.getLoadedCustomerContactPersons(DataHelper.LoadedCustomerContactPerson, new ArrayList<ContactPersonModel>().toString()));
                customerDatabaseModel.setListOfActivity(matecoPriceApplication.getLoadedCustomerActivities(DataHelper.LoadedCustomerActivity, new ArrayList<CustomerActivityModel>().toString()));
                customerDatabaseModel.setListOfProject(matecoPriceApplication.getLoadedCustomerProjects(DataHelper.LoadedCustomerProject, new ArrayList<CustomerProjectModel>().toString()));
                customerDatabaseModel.setListOfOffer(matecoPriceApplication.getLoadedCustomerOffers(DataHelper.LoadedCustomerOffer, new ArrayList<CustomerOfferModel>().toString()));
                customerDatabaseModel.setListOfOpenOrder(matecoPriceApplication.getLoadedCustomerOpenOrders(DataHelper.LoadedCustomerOpenOrders, new ArrayList<CustomerOpenOrderModel>().toString()));
                customerDatabaseModel.setListOfCompletedOrder(matecoPriceApplication.getLoadedCustomerCompletedOrders(DataHelper.LoadedCustomerCompletedOrders, new ArrayList<CustomerCompletedOrderModel>().toString()));
                customerDatabaseModel.setListOfOpenOffer(matecoPriceApplication.getLoadedCustomerOpenSpecials(DataHelper.LoadedCustomerOpenSpecials, new ArrayList<CustomerOpenOfferModel>().toString()));
                customerDatabaseModel.setListOfLostSale(matecoPriceApplication.getLoadedCustomerLostSale(DataHelper.LoadedCustomerLostSale, new ArrayList<CustomerLostSaleDataModel>().toString()));
                if(db.isCustomerAvailable(customerStored.getKontakt()))
                {
                    db.updateCustomer(customerDatabaseModel);
                    showShortToast(language.getMessageCustomerAlreadyExistsUpdatingContent());
                }
                else
                {
                    if(db.getCustomerTableRowsSize() < 50)
                    {
                        db.addCustomer(customerDatabaseModel);
                        showShortToast(language.getMessageCustomerStored());
                    }
                    else
                    {
                        DialogInterface.OnClickListener positiveCallback = new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.cancel();
                            }
                        };
                        AlertDialog alert = showMessage(language.getLabelMessage(), language.getMessageMaximumSizeToStoreCustomer(), language.getLabelOk(), positiveCallback);
                        alert.show();
                    }
                }

                //showShortToast(language.getMessageFunctionImplemented());
                return true;
            case R.id.actionForward:
                if(matecoPriceApplication.isCustomerLoaded(DataHelper.isCustomerLoaded, false))
                {
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    //getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    //getActivity().getSupportFragmentManager().popBackStack();
                    transaction.replace(R.id.content_frame, new CustomerContactPersonFragment()).addToBackStack("Customer Data");
                    //transaction.addToBackStack(SettingFragment.Tag);
                    //transaction.addToBackStack("Customer Data");
                    transaction.commit();
                }
                else
                {
                    showShortToast(language.getMessageLoadCustomerFirst());
                }
                return true;
            case R.id.actionAdd:
                //loadCustomer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    private void makeEditable(boolean editable)
    {
        if(editable)
        {
        }
        else
        {
        }
        spinnerCustomerDataLegalForm.setEnabled(editable);
        spinnerCustomerDataCountry.setEnabled(editable);
        spinnerCustomerDataBranch.setEnabled(editable);
//        textCustomerDataKaNr.setFocusable(editable);
//        textCustomerDataKaNr.setFocusableInTouchMode(editable);

        textCustomerDataSalesPotential.setFocusable(editable);
        textCustomerDataSalesPotential.setFocusableInTouchMode(editable);

//        textCustomerDataOrderBackLog.setFocusable(editable);
//        textCustomerDataOrderBackLog.setFocusableInTouchMode(editable);

//        textCustomerDataAcquisitionDate.setFocusable(editable);
//        textCustomerDataAcquisitionDate.setFocusableInTouchMode(editable);
//
//        textCustomerDataPaymentDeadline.setFocusable(editable);
//        textCustomerDataPaymentDeadline.setFocusableInTouchMode(editable);
//
//        textCustomerDataCreditLimit.setFocusable(editable);
//        textCustomerDataCreditLimit.setFocusableInTouchMode(editable);

        textCustomerDataOrderNo.setFocusable(editable);
        textCustomerDataOrderNo.setFocusableInTouchMode(editable);

        textCustomerDataVatNo.setFocusable(editable);
        textCustomerDataVatNo.setFocusableInTouchMode(editable);

        textCustomerDataOwner.setFocusable(editable);
        textCustomerDataOwner.setFocusableInTouchMode(editable);

//        textCustomerDataLegalForm.setFocusable(editable);
//        textCustomerDataLegalForm.setFocusableInTouchMode(editable);

//        textCustomerDataResponsiblePerson.setFocusable(editable);
//        textCustomerDataResponsiblePerson.setFocusableInTouchMode(editable);
//
//        textCustomerDataArea.setFocusable(editable);
//        textCustomerDataArea.setFocusableInTouchMode(editable);

        textCustomerDataWebsite.setFocusable(editable);
        textCustomerDataWebsite.setFocusableInTouchMode(editable);

        textCustomerDataEmail.setFocusable(editable);
        textCustomerDataEmail.setFocusableInTouchMode(editable);
        textCustomerDataFax.setFocusable(editable);
        textCustomerDataFax.setFocusableInTouchMode(editable);
        textCustomerDataPhone.setFocusable(editable);
        textCustomerDataPhone.setFocusableInTouchMode(editable);
//        textCustomerDataCountry.setFocusable(editable);
//        textCustomerDataCountry.setFocusableInTouchMode(editable);
        textCustomerDataPlace.setFocusable(editable);
        textCustomerDataPlace.setFocusableInTouchMode(editable);
        textCustomerDataZipCode.setFocusable(editable);
        textCustomerDataZipCode.setFocusableInTouchMode(editable);
        textCustomerDataRoad.setFocusable(editable);
        textCustomerDataRoad.setFocusableInTouchMode(editable);
        textCustomerDataName3.setFocusable(editable);
        textCustomerDataName3.setFocusableInTouchMode(editable);
        textCustomerDataName2.setFocusable(editable);
        textCustomerDataName2.setFocusableInTouchMode(editable);
        textCustomerDataName1.setFocusable(editable);
        textCustomerDataName1.setFocusableInTouchMode(editable);
        textCustomerDataMatchCode.setFocusable(editable);
        textCustomerDataMatchCode.setFocusableInTouchMode(editable);
        isInEditMode = editable;
    }
//    public void updateView(String data)
//    {
//        Toast.makeText(getActivity(), "Data updated and displayed", Toast.LENGTH_SHORT).show();
//    }

    private void updateCustomer()
    {
        final String matchCode = textCustomerDataMatchCode.getText().toString().trim();
        final String name1 = textCustomerDataName1.getText().toString().trim();
        final String name2 = textCustomerDataName2.getText().toString().trim();
        final String name3 = textCustomerDataName3.getText().toString().trim();
        final String road = textCustomerDataRoad.getText().toString().trim();
        final String zipCode = textCustomerDataZipCode.getText().toString().trim();
        final String place = textCustomerDataPlace.getText().toString().trim();
        country = "";
        if(selectedCountry != null)
        {
            country = selectedCountry.getCountryId()+"";
        }
        final String phone = textCustomerDataPhone.getText().toString().trim();
        final String fax = textCustomerDataFax.getText().toString().trim();
        final String email = textCustomerDataEmail.getText().toString().trim();
        final String website = textCustomerDataWebsite.getText().toString().trim();
        final String area = textCustomerDataArea.getText().toString().trim();
        String responsiblePerson = textCustomerDataResponsiblePerson.getText().toString().trim();
        legalForm = "";
        if(selectedLegalForm != null)
        {
            legalForm = selectedLegalForm.getRechtsFormId()+"";
        }
        final String owner = textCustomerDataOwner.getText().toString().trim();
        final String vatNo = textCustomerDataVatNo.getText().toString().trim();
        final String orderNo = textCustomerDataOrderNo.getText().toString().trim();
        final String creditLimit = DataHelper.getEnglishCurrencyFromGerman(textCustomerDataCreditLimit.getText().toString().trim());
        final String paymentDeadline = textCustomerDataPaymentDeadline.getText().toString().trim();
        SimpleDateFormat input_format = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat output_format = new SimpleDateFormat("dd-MM-yyyy");

        try {
            acquisitionDate = output_format.format(input_format.parse(textCustomerDataAcquisitionDate.getText().toString().trim()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final String orderBacklog = DataHelper.getEnglishCurrencyFromGerman(textCustomerDataOrderBackLog.getText().toString().trim());
        //final String salesPotential = DataHelper.getEnglishCurrencyFromGerman(textCustomerDataSalesPotential.getText().toString().trim());
        final String salesPotential = textCustomerDataSalesPotential.getText().toString().trim();
        final String kaNr = textCustomerDataKaNr.getText().toString().trim();
        String branch = "";
        if(selectedBranch != null)
        {
            branch = selectedBranch.getId();
        }
        textCustomerDataName1.setError(null);
        textCustomerDataPhone.setError(null);
        textCustomerDataEmail.setError(null);
        textCustomerDataWebsite.setError(null);

        CustomerUpdateModel customerUpdateModel = new CustomerUpdateModel();
        customerUpdateModel.setMATCHCODE(matchCode);
        customerUpdateModel.setNAME1(name1);
        customerUpdateModel.setNAME2(name2);
        customerUpdateModel.setNAME3(name3);
        customerUpdateModel.setStrasse(road);
        customerUpdateModel.setPLZ(zipCode);
        customerUpdateModel.setOrt(place);
        customerUpdateModel.setLand(country);
        customerUpdateModel.setTelefon(phone);
        customerUpdateModel.setTelefax(fax);
        customerUpdateModel.setEMAil(email);
        customerUpdateModel.setWww(website);
        customerUpdateModel.setRechtsform(legalForm);
        customerUpdateModel.setInhaber(owner);
        customerUpdateModel.setUSTIDNR(vatNo);
        customerUpdateModel.setJbestNr(orderNo);
        customerUpdateModel.setUmsatzpotenzial(salesPotential);
        customerUpdateModel.setKontakt(customerStored.getKontakt());
        customerUpdateModel.setBranche(branch);

        customerUpdateModel.setAenderungMitarbeiter(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber());

        if(name1.length() == 0)
        {
            textCustomerDataName1.setError(language.getLabelRequired());
            textCustomerDataName1.requestFocus();
        }
        else if(matchCode.length() == 0)
        {
            textCustomerDataMatchCode.setError(language.getLabelRequired());
            textCustomerDataMatchCode.requestFocus();
        }
        else if(place.length() == 0)
        {
            textCustomerDataPlace.setError(language.getLabelRequired());
            textCustomerDataPlace.requestFocus();
        }
        else if(!DataHelper.isValidBlankMail(email))
        {
            textCustomerDataEmail.setError(language.getMessageNotValidEmail());
            textCustomerDataEmail.requestFocus();
        }
        else if(selectedBranch == null){
            showShortToast(language.getMessagePleaseSelectBranch());
            //spinnerCustomerNewBranch.setError(language.getLabelRequired());
            //textCustomerNewVatNo.requestFocus();
        }
        else if(selectedLegalForm == null){
            showShortToast(language.getMessagePleaseSelectLegalForm());
            //spinnerCustomerNewBranch.setError(language.getLabelRequired());
            //textCustomerNewVatNo.requestFocus();
        }
        else if(!phone.equalsIgnoreCase("") && !DataHelper.phoneNumberValidationGerman(phone))
        {
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
            showMessage(language.getLabelAlert(),language.getMessagePhoneValidation(),language.getLabelOk(),listener).show();
            // textCustomerNewPhone.setError(language.getMessageNotValidNumber());
            textCustomerDataPhone.requestFocus();
        }
//        else if(!DataHelper.isValidMobile(phone))
//        {
//            textCustomerDataPhone.setError(language.getMessageNotValidNumber());
//            textCustomerDataPhone.requestFocus();
//        }
        else if(!DataHelper.isWebsiteValid(website))
        {
            textCustomerDataWebsite.setError(language.getMessageNotValidWebsite());
            textCustomerDataWebsite.requestFocus();
        }
        else
        {
            if(DataHelper.isNetworkAvailable(getActivity()) && customerStored != null)
            {
                AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        if(result.trim().equals("false") && result.trim().equals("error"))
                        {

                            showShortToast(language.getMessageError());
                        }
                        else if(result.equals(DataHelper.NetworkError)){
                            showShortToast(language.getMessageNetworkNotAvailable());
                        }
                        else
                        {
                            try
                            {
                                menu.findItem(R.id.actionForward).setVisible(true);
                                menu.findItem(R.id.actionEdit).setVisible(true);
                                menu.findItem(R.id.actionRight).setVisible(false);
                                menu.findItem(R.id.actionWrong).setVisible(false);
                                menu.findItem(R.id.actionBack).setVisible(true);
                                menu.findItem(R.id.actionSave).setVisible(true);
                                CustomerModel customer = new CustomerModel();
                                customer.setADM(customerStored.getADM());
                                customer.setAuftragsbestand(orderBacklog);
                                customer.setBoni_Index(customerStored.getBoni_Index());
                                customer.setBranche(customerStored.getBranche());
                                customer.setEMAil(email);
                                customer.setErfassungsdatum(acquisitionDate);
                                customer.setKaNr(kaNr);
                                customer.setGebiet(area);
                                customer.setKontakt(customerStored.getKontakt());
                                customer.setKundenNr(customerStored.getKundenNr());
                                customer.setMatchCode(matchCode);
                                //selectedBranch = new CustomerBranchModel();
                                if(selectedBranch != null){
                                    customer.setBranche(selectedBranch.getName());
                                }
                                else {
                                    customer.setBranche(null);
                                }


                                customer.setName1(name1);
                                customer.setOrt(place);
                                customer.setPLZ(zipCode);
                                customer.setStrasse(road);
                                customer.setTelefon(phone);
                                customer.setGesamt_OP(customerStored.getGesamt_OP());
                                customer.setInhaber(owner);
                                customer.setUmsatz_LFD(customerStored.getUmsatz_LFD());
                                customer.setUmsatz_Vorjahr(customerStored.getUmsatz_Vorjahr());
                                customer.setUmsatz_12_Monate(customerStored.getUmsatz_12_Monate());
                                customer.setUmsatzpotenzial(salesPotential);
                                customer.setBoni_Datum(customerStored.getBoni_Datum());
                                customer.setKA_Umsatz_12_Monate(customerStored.getKA_Umsatz_12_Monate());
                                customer.setKA_Umsatz_LFD(customerStored.getKA_Umsatz_LFD());
                                customer.setKA_Umsatz_Vorjahr(customerStored.getKA_Umsatz_Vorjahr());
                                customer.setKreditlimit(creditLimit);
                                if(selectedCountry != null)
                                    customer.setLand(selectedCountry.getCountryName());
                                else
                                    customer.setLand("");
                                customer.setName2(name2);
                                customer.setName3(name3);
                                if(selectedLegalForm != null)
                                    customer.setRechtsform(selectedLegalForm.getRechtsFormDesignation());
                                else
                                    customer.setRechtsform("");
                                customer.setTelefax(fax);
                                customer.setUSTIDNR(vatNo);
                                customer.setWebsite(website);
                                customer.setZahlungsziel(paymentDeadline);
                                customer.setJahresbestellNr(orderNo);
                                String json = new Gson().toJson(customer);
                                matecoPriceApplication.saveLoadedCustomer(DataHelper.LoadedCustomer, json);
                                menu.findItem(R.id.actionForward).setVisible(true);
                                menu.findItem(R.id.actionEdit).setVisible(true);
                                menu.findItem(R.id.actionRight).setVisible(false);
                                menu.findItem(R.id.actionWrong).setVisible(false);
                                menu.findItem(R.id.actionBack).setVisible(true);
                                menu.findItem(R.id.actionSave).setVisible(true);
                                DataBaseHandler db = new DataBaseHandler(getActivity());
                                if(db.isCustomerAvailable(customerStored.getKontakt()))
                                {
                                    db.updateCustomerDetails(customer);
                                }
                                loadDefaultValues();
                                makeEditable(false);
                            }
                            catch (Exception ex)
                            {
                               ex.printStackTrace();
                               showShortToast(language.getMessageErrorAtParsing());
                            }
                        }
                    }
                };
                String url = "";
                try
                {
                    //url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.CUSTOMER_UPDATE;
                    url = DataHelper.URL_CUSTOMER_HELPER+"customerupdate";

                    //url += "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                    //CustomerModel customer = new CustomerModel();

                    String json = new Gson().toJson(customerUpdateModel);
                    /*BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                    asyncTask.execute();*/

                    /* this is for post method */
                        //String url = DataHelper.URL_PRICE_HELPER+"priceinsert";
                        MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                        multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")));
                        multipartEntity.addPart("customerupdate", new StringBody(json, Charset.forName("UTF-8")));
                        AsyncTaskWithAuthorizationHeaderPost asyncTaskPost = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, getActivity(), multipartEntity, true, language);
                        asyncTaskPost.execute();

                    //url += "/customerupdate=" + URLEncoder.encode(json, "UTF-8");
//                    url += "&Kontakt=" + "";
//                    url += "&Name1=" + name1;
//                    url += "&Name2=" + name2;
//                    url += "&Name3=" + name3;
//                    url += "&Strasse=" + road;
//                    url += "&PLZ=" + zipCode;
//                    url += "&Ort=" + place;
//                    url += "&Land=" + country;
//                    url += "&Telefon=" + phone;
//                    url += "&Branche=" + "";
//                    url += "&UstidNr=" + vatNo;
//                    url += "&Mitarbeiter=" + "";
//                    url += "&Gebiet=" + area;
//                    url += "&ErfDatum=" + acquisitionDate;
//                    url += "&ErfMitarbeiter=" + "";
//                    url += "&Aenderungsstatus=" + "";
//                    url += "&Geloescht=" + "";
//                    url += "&LetzteAenderung=" + "";
//                    url += "&AenderungMitarbeiter=" + "";
//                    url = url.replace(" ", "%20");
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
                /*Log.e("url", url);
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                asyncTask.execute();*/
            }
            else if(customerStored == null)
            {
                showShortToast(language.getMessageLoadCustomerFirst());
            }
            else
            {
                showShortToast(language.getMessageNetworkNotAvailable());
            }
        }
    }

    private boolean checkIfTextChanged()
    {
        //customerStored = matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString());
        String defaultMatchCode = customerStored.getMatchCode();
        String defaultName1 = customerStored.getName1();
        String defaultName2 = customerStored.getName2();
        String defaultName3 = customerStored.getName3();
        String defaultRoad = customerStored.getStrasse();
        String defaultZipCode = customerStored.getPLZ();
        String defaultPlace = customerStored.getOrt();
        String defaultCountry = customerStored.getLand();
        String defaultTelephone = customerStored.getTelefon();
        String defaultTelefax = customerStored.getTelefax();
        String defaultEmail = customerStored.getEMAil();
        String defaultWebsite = customerStored.getWebsite();
        String defaultArea = customerStored.getGebiet();
        String defaultResponsibelPerson = customerStored.getADM();
        String defaultRechtsForm = customerStored.getRechtsform();
        String defaultOwner = customerStored.getInhaber();
        String defaultVatno = customerStored.getUSTIDNR();
        String defaultOrderNo = customerStored.getJahresbestellNr();
        String defaultSalesPotential = DataHelper.getEnglishCurrencyFormat(customerStored.getUmsatzpotenzial());
        String defaultCreditLimit = DataHelper.getEnglishCurrencyFormat(customerStored.getKreditlimit());
        String defaultAcquisitionDate = customerStored.getErfassungsdatum();
        String defaultKanr = customerStored.getKaNr();
        String defaultPaymentDeadline = customerStored.getZahlungsziel();
        String defaultOrderBacklog = DataHelper.getEnglishCurrencyFormat(customerStored.getAuftragsbestand());


        String matchCode = textCustomerDataMatchCode.getText().toString().trim();
        String name1 = textCustomerDataName1.getText().toString().trim();
        String name2 = textCustomerDataName2.getText().toString().trim();
        String name3 = textCustomerDataName3.getText().toString().trim();
        String road = textCustomerDataRoad.getText().toString().trim();
        String zipCode = textCustomerDataZipCode.getText().toString().trim();
        String place = textCustomerDataPlace.getText().toString().trim();
        country = "";
        if(selectedCountry != null)
        {
            country = selectedCountry.getCountryName()+"";
        }
        String phone = textCustomerDataPhone.getText().toString().trim();
        String fax = textCustomerDataFax.getText().toString().trim();
        String email = textCustomerDataEmail.getText().toString().trim();
        String website = textCustomerDataWebsite.getText().toString().trim();
        String area = textCustomerDataArea.getText().toString().trim();
        String responsiblePerson = textCustomerDataResponsiblePerson.getText().toString().trim();
        legalForm = "";
        if(selectedLegalForm != null)
        {
            legalForm = selectedLegalForm.getRechtsFormDesignation()+"";
        }

        String owner = textCustomerDataOwner.getText().toString().trim();
        String vatNo = textCustomerDataVatNo.getText().toString().trim();
        String orderNo = textCustomerDataOrderNo.getText().toString().trim();
        String creditLimit = DataHelper.getEnglishCurrencyFromGerman(textCustomerDataCreditLimit.getText().toString().trim());
        String paymentDeadline = textCustomerDataPaymentDeadline.getText().toString().trim();

        SimpleDateFormat input_format = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat output_format = new SimpleDateFormat("dd-MM-yyyy");
        try
        {
            final String acquisitionDate = output_format.format(input_format.parse(textCustomerDataAcquisitionDate.getText().toString().trim()));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        final String orderBacklog = DataHelper.getEnglishCurrencyFromGerman(textCustomerDataOrderBackLog.getText().toString().trim());
        final String salesPotential = DataHelper.getEnglishCurrencyFromGerman(textCustomerDataSalesPotential.getText().toString().trim());

        String kaNr = textCustomerDataKaNr.getText().toString().trim();
        if((matchCode.equals(defaultMatchCode)) && (name1.equals(defaultName1)) && (name2.equals(defaultName2)) &&
                (name3.equals(defaultName3)) && (road.equals(defaultRoad)) && (zipCode.equals(defaultZipCode)) &&
                (place.equals(defaultPlace)) && (country.equals(defaultCountry)) && (phone.equals(defaultTelephone)) &&
                (fax.equals(defaultTelefax)) && (email.equals(defaultEmail)) && (website.equals(defaultWebsite)) &&
                (area.equals(defaultArea)) && (responsiblePerson.equals(defaultResponsibelPerson)) && (owner.equals(defaultOwner)) &&
                (legalForm.equals(defaultRechtsForm)) && (vatNo.equals(defaultVatno)) && (orderNo.equals(defaultOrderNo)) &&
                (creditLimit.equals(defaultCreditLimit)) && (defaultKanr.equals(kaNr)) &&
                (salesPotential.equals(defaultSalesPotential)) && (paymentDeadline.equals(defaultPaymentDeadline)) &&
                (orderBacklog.equals(defaultOrderBacklog)))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        switch (v.getId())
        {
            case R.id.textCustomerDataWebsite:
                textCustomerDataOwner.requestFocus();
                break;
        }
        return false;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.imageViewCall:
                if ( Build.VERSION.SDK_INT >= 23)
                {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) ==
                            PackageManager.PERMISSION_GRANTED)
                    {
                        GlobalMethods.callToNumber(context, textCustomerDataPhone);
                    }
                    else{
                        ActivityCompat.requestPermissions(getActivity(), new String[] {
                                        android.Manifest.permission.CALL_PHONE},
                                3005);
                    }
                }
                else {
                    GlobalMethods.callToNumber(context, textCustomerDataPhone);
                }


                break;
            case R.id.imageViewEmail:
                GlobalMethods.sendEmail(context, textCustomerDataEmail);
                break;
            case R.id.imageViewWebsite:
                GlobalMethods.openLink(context, textCustomerDataWebsite);
                break;
        }
    }
}
