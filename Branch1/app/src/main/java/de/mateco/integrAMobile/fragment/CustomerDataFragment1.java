package de.mateco.integrAMobile.fragment;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.CountryAdapter;
import de.mateco.integrAMobile.adapter.LegalFormAdapter;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.CountryModel;
import de.mateco.integrAMobile.model.CustomerModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LegalFormModel;

public class CustomerDataFragment1 extends Fragment
{
    private Language language;
    private MatecoPriceApplication matecoPriceApplication;
    private EditText textCustomerDataSalesPotential, textCustomerDataOrderNo, textCustomerDataVatNo, textCustomerDataOwner,
            textCustomerDataWebsite, textCustomerDataEmail, textCustomerDataFax, textCustomerDataPhone, textCustomerDataPlace,
            textCustomerDataZipCode, textCustomerDataRoad, textCustomerDataName3, textCustomerDataName2, textCustomerDataName1,
            textCustomerDataMatchCode;
    private EditText textCustomerDataKaNr, textCustomerDataOrderBackLog, textCustomerDataAcquisitionDate,
            textCustomerDataPaymentDeadline, textCustomerDataCreditLimit, textCustomerDataResponsiblePerson,
            textCustomerDataArea;
    private Spinner spinnerCustomerDataLegalForm, spinnerCustomerDataCountry;
    private ArrayList<CountryModel> listOfCountry;
    private ArrayList<LegalFormModel> listOfLegalForm;
    private CountryModel selectedCountry = null;
    private LegalFormModel selectedLegalForm = null;
    private CountryAdapter countryAdapter;
    private LegalFormAdapter legalFormAdapter;
    private CustomerModel customerStored;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_customer_data, container, false);
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        getActivity().invalidateOptionsMenu();
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelCustomerData());
        if(getArguments()!=null)
        {
            customerStored = getArguments().getParcelable("customerObject");
        }
        initializeComponents();
        setHasOptionsMenu(true);

        return rootView;
    }

    private void initializeComponents() {
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
        textCustomerDataResponsiblePerson = (EditText)rootView.findViewById(R.id.textCustomerDataResponsiblePerson);
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
        countryAdapter = new CountryAdapter(getActivity(), listOfCountry, R.layout.list_item_spinner_country, language);
        legalFormAdapter = new LegalFormAdapter(getActivity(), listOfLegalForm, R.layout.list_item_spinner_legal_form, language);
        spinnerCustomerDataCountry.setAdapter(countryAdapter);
        spinnerCustomerDataLegalForm.setAdapter(legalFormAdapter);
        spinnerCustomerDataLegalForm.setSelection(0);
        setLanguage();
        loadDefaultValues();

    }

    private void setLanguage()
    {
        TextView labelCustomerDataMatchCode,labelCustomerDataName1,labelCustomerDataName2,labelCustomerDataName3,labelCustomerDataRoad,
                labelCustomerDataZipcode,labelCustomerDataPlace, labelCustomerDataCountry,labelCustomerDataPhone,labelCustomerDataFax,
                labelCustomerDataEmail,labelCustomerDataWebsite,labelCustomerDataArea,labelCustomerDataResponsiblePerson,
                labelCustomerDataLegalForm, labelCustomerDataOwner, labelCustomerDataVatnr, labelCustomerDataOrderNo,
                labelCustomerDataCreditLimit, labelCustomerDataPaymentDeadline, labelCustomerDataAcquisitionDate, labelCustomerDataOrderBacklog,
                labelCustomerDataSalesPotential, labelCustomerDataKanr;

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
    }

    private void loadDefaultValues()
    {
        textCustomerDataMatchCode.setText(customerStored.getMatchCode());
        textCustomerDataName1.setText(customerStored.getName1());
        textCustomerDataName2.setText(customerStored.getName2());
        textCustomerDataName3.setText(customerStored.getName3());
        textCustomerDataRoad.setText(customerStored.getStrasse());
        textCustomerDataZipCode.setText(customerStored.getPLZ());
        textCustomerDataPlace.setText(customerStored.getOrt());
        boolean setCountry = false;
        for (int i = 0; i < listOfCountry.size(); i++) {
            if (customerStored.getLand().equals(listOfCountry.get(i).getCountryName())) {
                spinnerCustomerDataCountry.setSelection(i + 1);
                selectedCountry = listOfCountry.get(i);
                setCountry = true;
                break;
            }
        }
        if (!setCountry) {
            //spinnerCustomerDataLegalForm.setSelection(listOfLegalForm.size());
            spinnerCustomerDataLegalForm.setSelection(0);
        }
        //textCustomerDataCountry.setText(customerStored.getLand());
        textCustomerDataPhone.setText(customerStored.getTelefon());
        textCustomerDataFax.setText(customerStored.getTelefax());
        textCustomerDataEmail.setText(customerStored.getEMAil());
        textCustomerDataWebsite.setText(customerStored.getWebsite());

        textCustomerDataArea.setText(customerStored.getGebiet());
        textCustomerDataResponsiblePerson.setText(customerStored.getADM());
        boolean setLegalForm = false;
        for (int i = 0; i < listOfLegalForm.size(); i++) {
            if (customerStored.getRechtsform().equals((listOfLegalForm.get(i)).getRechtsFormDesignation())) {
                spinnerCustomerDataLegalForm.setSelection(i + 1);
                selectedLegalForm = listOfLegalForm.get(i);
                setLegalForm = true;
                break;
            }
        }
        if (!setLegalForm) {
            //spinnerCustomerDataLegalForm.setSelection(listOfLegalForm.size());
            spinnerCustomerDataLegalForm.setSelection(0);
        }
        //textCustomerDataLegalForm.setText(customerStored.getRechtsform());
        textCustomerDataOwner.setText(customerStored.getInhaber());
        textCustomerDataVatNo.setText(customerStored.getUSTIDNR());
        textCustomerDataOrderNo.setText(customerStored.getJahresbestellNr());
        //textCustomerDataCreditLimit.setText(String.format("%.2f", Float.parseFloat(customerStored.getKreditlimit())));
        textCustomerDataCreditLimit.setText(DataHelper.getGermanCurrencyFormat(customerStored.getKreditlimit()));
        textCustomerDataPaymentDeadline.setText(customerStored.getZahlungsziel());
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            textCustomerDataAcquisitionDate.setText(DataHelper.formatDate(format.parse(customerStored.getErfassungsdatum())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        textCustomerDataOrderBackLog.setText(DataHelper.getGermanCurrencyFormat(customerStored.getAuftragsbestand()));
        textCustomerDataSalesPotential.setText(DataHelper.getGermanCurrencyFormat(customerStored.getUmsatzpotenzial()));
        textCustomerDataKaNr.setText(customerStored.getKaNr());

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);
        menu.findItem(R.id.actionSearch).setVisible(false);
        menu.findItem(R.id.actionEdit).setVisible(false);
        menu.findItem(R.id.actionAdd).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);
        menu.findItem(R.id.actionWrong).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        switch (item.getItemId()) {
            case R.id.actionSettings:
                transaction.replace(R.id.content_frame, new SettingFragment(),"Setting");
                //transaction.addToBackStack(SettingFragment.Tag);
                transaction.addToBackStack("Setting");
                transaction.commit();
                return true;
            case R.id.actionRight:
                Fragment fragment = new SiteInspectionNewFragment();
                transaction.replace(R.id.content_frame, fragment);
                getActivity().getSupportFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
                transaction.addToBackStack("Customer Data");
                transaction.commit();
                return true;
            case R.id.actionBack:
                getActivity().getSupportFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }
}
