package de.mateco.integrAMobile.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.GlobalMethods;
import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.CountryAdapter;
import de.mateco.integrAMobile.adapter.CustomerBranchAdapter;
import de.mateco.integrAMobile.adapter.LegalFormAdapter;
import de.mateco.integrAMobile.asyncTask.AsyncTaskWithAuthorizationHeaderPost;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.CustomerFullDetailModel;
import de.mateco.integrAMobile.model.CustomerModel;
import de.mateco.integrAMobile.model.CustomerNewModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LoginPersonModel;
import de.mateco.integrAMobile.model_logonsquare.BrancheListItem;
import de.mateco.integrAMobile.model_logonsquare.CustomerLandListItem;
import de.mateco.integrAMobile.model_logonsquare.CustomerRechtsFormComboListItem;

public class CustomerNewFragment extends BaseFragment implements TextView.OnEditorActionListener, View.OnClickListener
{
    private Language language;
    private View rootView;
    private MatecoPriceApplication matecoPriceApplication;
    private EditText textCustomerNewMatchCode, textCustomerNewName1, textCustomerNewName2, textCustomerNewName3,
                    textCustomerNewRoad, textCustomerNewZipCode, textCustomerNewPlace, textCustomerNewPhone,
                    textCustomerNewFax, textCustomerNewEmail, textCustomerNewWebsite, textCustomerNewOwner,
                    textCustomerNewVatNo, textCustomerNewOrderNo, textCustomerNewSalesPotential;
    //textCustomerNewOrderBackLog,textCustomerNewKaNr
    private Spinner spinnerCustomerNewCountry, spinnerCustomerNewLegalForm, spinnerCustomerNewBranch;
    private ArrayList<CustomerLandListItem> listOfCountry;
    private CustomerLandListItem  selectedCountry = null;
    private CustomerRechtsFormComboListItem selectedLegalForm = null;
    private ArrayList<CustomerRechtsFormComboListItem > listOfLegalForm;
    private CountryAdapter countryAdapter;
    private LegalFormAdapter legalFormAdapter;
    private ArrayList<BrancheListItem> listOfCustomerBranches;
    private CustomerBranchAdapter adapterCustomerBranch;
    private BrancheListItem selectedBranch;
    private ImageView imageViewCall;
    private RadioButton radioButtonIndustrie,radioButtonStrasse;
    RadioGroup rbGroup;
    String strRbValue="2";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_customer_new, container, false);
        super.initializeFragment(rootView);
        return rootView;
    }

    @Override
    public void initializeComponents(View rootView)
    {
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        language = matecoPriceApplication.getLanguage();
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelCustomer() + " "+ language.getLabelNew());
        //int y = 5/0;
        textCustomerNewMatchCode = (EditText)rootView.findViewById(R.id.textCustomerNewMatachCode);
        textCustomerNewName1 = (EditText)rootView.findViewById(R.id.textCustomerNewName1);
        textCustomerNewName2 = (EditText)rootView.findViewById(R.id.textCustomerNewName2);
        textCustomerNewName3 = (EditText)rootView.findViewById(R.id.textCustomerNewName3);
        textCustomerNewRoad = (EditText)rootView.findViewById(R.id.textCustomerNewRoad);
        textCustomerNewZipCode = (EditText)rootView.findViewById(R.id.textCustomerNewZipCode);
        textCustomerNewPlace = (EditText)rootView.findViewById(R.id.textCustomerNewPlace);
        spinnerCustomerNewCountry = (Spinner)rootView.findViewById(R.id.spinnerCustomerNewCountry);
        spinnerCustomerNewBranch = (Spinner)rootView.findViewById(R.id.spinnerCustomerNewBranch);
        textCustomerNewPhone = (EditText)rootView.findViewById(R.id.textCustomerNewPhone);
        textCustomerNewFax = (EditText)rootView.findViewById(R.id.textCustomerNewFax);
        textCustomerNewEmail = (EditText)rootView.findViewById(R.id.textCustomerNewEmail);
        textCustomerNewWebsite = (EditText)rootView.findViewById(R.id.textCustomerNewWebsite);
        textCustomerNewOwner = (EditText)rootView.findViewById(R.id.textCustomerNewOwner);
        spinnerCustomerNewLegalForm = (Spinner)rootView.findViewById(R.id.spinnerCustomerNewLegalForm);
        textCustomerNewVatNo = (EditText)rootView.findViewById(R.id.textCustomerNewVatNo);
        textCustomerNewOrderNo = (EditText)rootView.findViewById(R.id.textCustomerNewOrderNo);
        //textCustomerNewOrderBackLog = (EditText)rootView.findViewById(R.id.textCustomerNewOrderBackLog);
        textCustomerNewSalesPotential = (EditText)rootView.findViewById(R.id.textCustomerNewSalesPotential);
        imageViewCall = (ImageView)rootView.findViewById(R.id.imageViewCall);
        rbGroup = (RadioGroup)rootView.findViewById(R.id.radioGrp);
        radioButtonIndustrie=(RadioButton)rbGroup.findViewById(R.id.rdBtnIndustrie);
        radioButtonStrasse=(RadioButton)rbGroup.findViewById(R.id.rdBtnStrasse);
        radioButtonIndustrie.setChecked(true);


        //textCustomerNewKaNr = (EditText)rootView.findViewById(R.id.textCustomerNewKaNr);
        listOfCountry = new ArrayList<CustomerLandListItem >();
        DataBaseHandler db = null; //= new DataBaseHandler(getActivity());
        listOfCountry = db.getCountries();
        listOfLegalForm = new ArrayList<CustomerRechtsFormComboListItem >();
        listOfLegalForm = db.getLegalForms();
        listOfCustomerBranches = new ArrayList<>();
        listOfCustomerBranches = db.getCustomerBranches();
        adapterCustomerBranch = new CustomerBranchAdapter(getActivity(), listOfCustomerBranches, R.layout.list_item_spinner_customer_branch, language);
        spinnerCustomerNewBranch.setAdapter(adapterCustomerBranch);
        countryAdapter = new CountryAdapter(getActivity(), listOfCountry, R.layout.list_item_spinner_country, language);
        legalFormAdapter = new LegalFormAdapter(getActivity(), listOfLegalForm, R.layout.list_item_spinner_legal_form, language);
        spinnerCustomerNewCountry.setAdapter(countryAdapter);
        spinnerCustomerNewLegalForm.setAdapter(legalFormAdapter);
        spinnerCustomerNewLegalForm.setSelection(0);
        if(listOfCountry.size() > 0)
        {
            for(int i = 0; i < listOfCountry.size(); i++)
            {
                if(listOfCountry.get(i).getLand().equals("1"))
                {
                    selectedCountry = listOfCountry.get(i);
                    spinnerCustomerNewCountry.setSelection(i + 1);
                }
            }
        }

        setLanguage();
        super.initializeComponents(rootView);
    }

    @Override
    public void bindEvents(View rootView)
    {
        radioButtonIndustrie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        strRbValue="2";
                    }
            }
        });
        radioButtonStrasse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    strRbValue="1";
                }
            }
        });
        textCustomerNewWebsite.setOnEditorActionListener(this);
        imageViewCall.setOnClickListener(this);
        spinnerCustomerNewCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedCountry = null;
                } else {
                    selectedCountry = listOfCountry.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerCustomerNewLegalForm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
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
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCustomerNewBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
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
                    selectedBranch = listOfCustomerBranches.get(position - 1);
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
        TextView labelCustomerNewSalesPotential, labelCustomerNewOrderNo, labelCustomerNewVatnr,
                labelCustomerNewOwner, labelCustomerNewLegalForm, labelCustomerNewWebsite, labelCustomerNewEmail, labelCustomerNewFax,
                labelCustomerNewPhone, labelCustomerNewCountry, labelCustomerNewPlace, labelCustomerNewZipcode,labelCustomerNewRoad,
                labelCustomerNewName3, labelCustomerNewName2, labelCustomerNewName1,labelCustomerNewMatchCode, labelCustomerNewBranch;
        //labelCustomerNewKanr, labelCustomerNewOrderBacklog
        //labelCustomerNewKanr = (TextView)rootView.findViewById(R.id.labelCustomerNewKanr);
        labelCustomerNewSalesPotential = (TextView)rootView.findViewById(R.id.labelCustomerNewSalesPotential);
        //labelCustomerNewOrderBacklog = (TextView)rootView.findViewById(R.id.labelCustomerNewOrderBacklog);
        labelCustomerNewOrderNo = (TextView)rootView.findViewById(R.id.labelCustomerNewOrderNo);
        labelCustomerNewOwner = (TextView)rootView.findViewById(R.id.labelCustomerNewOwner);
        labelCustomerNewLegalForm = (TextView)rootView.findViewById(R.id.labelCustomerNewLegalForm);
        labelCustomerNewWebsite = (TextView)rootView.findViewById(R.id.labelCustomerNewWebsite);
        labelCustomerNewEmail = (TextView)rootView.findViewById(R.id.labelCustomerNewEmail);
        labelCustomerNewFax = (TextView)rootView.findViewById(R.id.labelCustomerNewFax);
        labelCustomerNewPhone = (TextView)rootView.findViewById(R.id.labelCustomerNewPhone);
        labelCustomerNewCountry = (TextView)rootView.findViewById(R.id.labelCustomerNewCountry);
        labelCustomerNewPlace = (TextView)rootView.findViewById(R.id.labelCustomerNewPlace);
        labelCustomerNewZipcode = (TextView)rootView.findViewById(R.id.labelCustomerNewZipcode);
        labelCustomerNewRoad = (TextView)rootView.findViewById(R.id.labelCustomerNewRoad);
        labelCustomerNewName3 = (TextView)rootView.findViewById(R.id.labelCustomerNewName3);
        labelCustomerNewName2 = (TextView)rootView.findViewById(R.id.labelCustomerNewName2);
        labelCustomerNewName1 = (TextView)rootView.findViewById(R.id.labelCustomerNewName1);
        labelCustomerNewMatchCode = (TextView)rootView.findViewById(R.id.labelCustomerNewMatchCode);
        labelCustomerNewVatnr = (TextView)rootView.findViewById(R.id.labelCustomerNewVatnr);
        labelCustomerNewBranch = (TextView)rootView.findViewById(R.id.labelCustomerNewBranch);

        //labelCustomerNewKanr.setText(language.getLabelKanr());
        labelCustomerNewSalesPotential.setText(language.getLabelSalesPotential());
        labelCustomerNewOrderNo.setText(language.getLabelOrderNo());
        //labelCustomerNewOrderBacklog.setText(language.getLabelOrderBacklog());
        labelCustomerNewLegalForm.setText(language.getLabelLegalForm());
        labelCustomerNewOwner.setText(language.getLabelOwner());
        labelCustomerNewWebsite.setText("");
        //labelCustomerNewWebsite.setText(language.getLabelWebsite());
        labelCustomerNewEmail.setText("");
        //labelCustomerNewEmail.setText(language.getLabelEmail());
        labelCustomerNewFax.setText("");
        //labelCustomerNewFax.setText(language.getLabelFax());
        //labelCustomerNewPhone.setText("");
        labelCustomerNewPhone.setText(language.getLabelPhone());
        labelCustomerNewCountry.setText(language.getLabelCountry());
        labelCustomerNewPlace.setText(language.getLabelPlace());
        labelCustomerNewZipcode.setText(language.getLabelZipCode());
        labelCustomerNewRoad.setText(language.getLabelRoad());
        labelCustomerNewName3.setText(language.getLabelName() + " 3");
        labelCustomerNewName2.setText(language.getLabelName() + " 2");
        labelCustomerNewName1.setText(language.getLabelName() + " 1");
        labelCustomerNewMatchCode.setText(language.getLabelMatchCode());
        labelCustomerNewVatnr.setText(language.getLabelVatNo());
        labelCustomerNewBranch.setText(language.getLabelBranch1());

        radioButtonIndustrie.setText(language.getLabelIndustrie());
        radioButtonStrasse.setText(language.getLabelStrasse());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);
        menu.findItem(R.id.actionAdd).setVisible(false);
        menu.findItem(R.id.actionSearch).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);
        menu.findItem(R.id.actionEdit).setVisible(false);
        //return true;
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.actionSettings:
                transaction.replace(R.id.content_frame, new SettingFragment(), "Setting");
                transaction.replace(R.id.content_frame, new SettingFragment());
                //transaction.addToBackStack(SettingFragment.Tag);
                transaction.addToBackStack("Setting");
                transaction.commit();
                return true;
            case R.id.actionBack:
                if (getFragmentManager().getBackStackEntryCount() == 0)
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
            case R.id.actionWrong:
                    super.clearAll();
                return true;
            case R.id.actionRight:
                insertCustomer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    private void insertCustomer()
    {
        String matchCode = textCustomerNewMatchCode.getText().toString().trim();
        String name1 = textCustomerNewName1.getText().toString().trim();
        String name2 = textCustomerNewName2.getText().toString().trim();
        String name3 = textCustomerNewName3.getText().toString().trim();
        String road = textCustomerNewRoad.getText().toString().trim();
        String zipCode = textCustomerNewZipCode.getText().toString().trim();
        String place = textCustomerNewPlace.getText().toString().trim();
        String country="";
        if(selectedCountry != null){
            country = selectedCountry.getLand();
        }
        else {
            country="";
        }

        String branch = "";
        if(selectedBranch != null)
        {
            branch = selectedBranch.getBrancheID();
        }
        String phone = textCustomerNewPhone.getText().toString().trim();
        String fax = textCustomerNewFax.getText().toString().trim();
        String email = textCustomerNewEmail.getText().toString().trim();
        String website = textCustomerNewWebsite.getText().toString().trim();
        //String legalForm = textCustomerNewLegalForm.getText().toString().trim();
        String owner = textCustomerNewOwner.getText().toString().trim();
        String vatNo = textCustomerNewVatNo.getText().toString().trim();
        String orderNo = textCustomerNewOrderNo.getText().toString().trim();
        //String orderBacklog = textCustomerNewOrderBackLog.getText().toString().trim();
        String salesPotential = textCustomerNewSalesPotential.getText().toString().trim();
        //String kaNr = textCustomerNewKaNr.getText().toString().trim();
        textCustomerNewName1.setError(null);
        textCustomerNewPhone.setError(null);
        textCustomerNewEmail.setError(null);
        textCustomerNewWebsite.setError(null);
        if(!(name1.length() > 0))
        {
            textCustomerNewName1.setError(language.getLabelRequired());
            textCustomerNewName1.requestFocus();
        }
        /* else if(!(phone.length() > 0))
        {
            textCustomerNewPhone.setError(language.getLabelRequired());
            textCustomerNewPhone.requestFocus();
        }*/
        else if(!(matchCode.length() > 0))
        {
            textCustomerNewMatchCode.setError(language.getLabelRequired());
            textCustomerNewMatchCode.requestFocus();
        }
        else if(!(place.length() > 0))
        {
            textCustomerNewPlace.setError(language.getLabelRequired());
            textCustomerNewPlace.requestFocus();
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
            textCustomerNewPhone.requestFocus();
        }
//        else if(!DataHelper.isValidMail(email))
//        {
//            textCustomerNewEmail.setError("Not a valid email");
//            textCustomerNewEmail.requestFocus();
//        }
//        else if(!DataHelper.isValidMobile(phone))
//        {
//            textCustomerNewPhone.setError("Not a valid number");
//            textCustomerNewPhone.requestFocus();
//        }
        else if(!DataHelper.isWebsiteValid(website))
        {
            textCustomerNewWebsite.setError(language.getMessageNotValidWebsite());
            textCustomerNewWebsite.requestFocus();
        }
        else
        {
            if(DataHelper.isNetworkAvailable(getActivity()))
            {
                AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result) {
                        if(result.equals("error"))
                        {
                            showShortToast(language.getMessageErrorCustomerInsert());
                        }
                        else if(result.equals(DataHelper.NetworkError)){
                            showShortToast(language.getMessageNetworkNotAvailable());
                        }
                        else
                        {
                            try
                            {
                                CustomerModel customerModel  = new Gson().fromJson(result, CustomerModel.class);
                                getCustomerDetails(customerModel);
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                                showShortToast(language.getMessageErrorAtParsing());
                            }
                        }
                    }
                };
                /*BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)

                };*/
                String url = "";
                String json = "";

                    /*url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.CUSTOMER_INSERT;*/
                    url = DataHelper.URL_CUSTOMER_HELPER+"customerinsert";

                    //url += "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                    CustomerNewModel customerNew = new CustomerNewModel();
                    //matchCode = "XXXXXXXX";
                    customerNew.setMatchcode(matchCode);
                    customerNew.setName1(name1);
                    customerNew.setName2(name2);
                    customerNew.setName3(name3);
                    customerNew.setStrasse(road);
                    customerNew.setPLZ(zipCode);
                    customerNew.setOrt(place);
                    customerNew.setLand(country);
                    customerNew.setTelefon(phone);
                    customerNew.setTelefax(fax);
                    customerNew.setEMail(email);
                    customerNew.setWWW(website);
                    customerNew.setBranche(branch);
                    customerNew.setUstidNr(vatNo);
                    customerNew.setMitarbeiter(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber());
                    customerNew.setUserID(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserId());
                    customerNew.setStreetOrIndustry(strRbValue);
                    //customerNew.setProjectAreaId("");
                    String legalForm = "";
                    if(selectedLegalForm != null)
                    {
                        legalForm = selectedLegalForm.getRECHTSFORM()+"";
                    }
                    customerNew.setRechtsform(legalForm);
                    customerNew.setInhaber(owner);
                    customerNew.setJBestNr(orderNo);
                    customerNew.setUmsatzpotenzial(salesPotential);
                    customerNew.setErfMitarbeiter(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber());
                    customerNew.setAenderungMitarbeiter(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber());
                    json = new Gson().toJson(customerNew);
                    //url += "/customerinsert=" + URLEncoder.encode(json, "UTF-8");

                try {
                    /*BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                    asyncTask.execute();*/

                /* this is for post method */
                    //String url = DataHelper.URL_PRICE_HELPER+"priceinsert";
                    MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                    multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")));
                    multipartEntity.addPart("customerinsert", new StringBody(json, Charset.forName("UTF-8")));
                    AsyncTaskWithAuthorizationHeaderPost asyncTaskPost = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, getActivity(), multipartEntity, true, language);
                    asyncTaskPost.execute();
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
    }

    private void getCustomerDetails(CustomerModel customerModel)
    {
        if(DataHelper.isNetworkAvailable(getActivity()))
        {
            try
            {
                DataBaseHandler db = new DataBaseHandler(getActivity());
                db.deleteKANR();
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        if(result.equals("error"))
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
                                CustomerFullDetailModel customerFullDetail = new Gson().fromJson(result, CustomerFullDetailModel.class);
                                String json = new Gson().toJson(customerFullDetail.getCustomerSearchList());
                                matecoPriceApplication.saveLoadedCustomer(DataHelper.LoadedCustomer, json);
                                String listOfContactPerson = new Gson().toJson(customerFullDetail.getCustomerContactPersonList());
                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerContactPerson, listOfContactPerson);

                                String listOfActivity = new Gson().toJson(customerFullDetail.getCustomerActivityListt().getListOfActivities());
                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerActivity, listOfActivity);

                                String listOfProject = new Gson().toJson(customerFullDetail.getCustomerActivityListt().getListOfProject());
                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerProject, listOfProject);

                                String listOfOffer = new Gson().toJson(customerFullDetail.getCustomerActivityListt().getListOfOffers());
                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerOffer, listOfOffer);

                                String listOfLostSale = new Gson().toJson(customerFullDetail.getCustomerLostSaleList());
                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerLostSale, listOfLostSale);

                                String listOfOpenSpecials = new Gson().toJson(customerFullDetail.getCustomerOpenSpecialsList());
                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerOpenSpecials, listOfOpenSpecials);

                                String listOfOpenOrder = new Gson().toJson(customerFullDetail.getCustomerOpenOrderList());
                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerOpenOrders, listOfOpenOrder);

                                String listOfCompletedOrder = new Gson().toJson(customerFullDetail.getCustomerCompletedOrdersList());
                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerCompletedOrders, listOfCompletedOrder);
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                if(getArguments()==null)
                                {
                                    CustomerNewFragment.super.clearAll();
                                    spinnerCustomerNewBranch.setSelection(0);
                                    spinnerCustomerNewLegalForm.setSelection(0);

                                    ((HomeActivity)getActivity()).updateAdapter();
                                    Fragment fragment = new CustomerDataFragment();
                                    //Bundle bundle = new Bundle();
                                    //bundle.putParcelable("");
                                    //bundle.putParcelable("customerObject", listOfCustomerSearchResult.get(adapter.selectedIndex));
                                    //fragment.setArguments(bundle);
                                    transaction.replace(R.id.content_frame, fragment);
                                    //getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                    //transaction.addToBackStack(SettingFragment.Tag);
                                    transaction.addToBackStack("Customer Search");
                                    transaction.commit();
                                }
                                else
                                {
                                   /* Fragment fragment = new CustomerDataFragment1();
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("customerObject",listOfCustomerSearchResult.get(adapter.selectedIndex));
                                    fragment.setArguments(bundle);
                                    transaction.replace(R.id.content_frame, fragment);
                                    //getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                    transaction.addToBackStack("Customer Search");
                                    transaction.commit();*/
                                }
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                                showShortToast(language.getMessageErrorAtParsing());
                            }
                        }
                    }
                };

                String url = DataHelper.URL_AGENDA_HELPER + "agendacustomershow"//+ DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.AGENDA_CUSTOMER_SHOW
                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/fieldname=" + "Kontakt"
                        + "/value=" + customerModel.getKontakt();
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), false);
                asyncTask.execute();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        else {
            showShortToast(language.getMessageNetworkNotAvailable());
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        switch (v.getId())
        {
            case R.id.textCustomerNewWebsite:
                textCustomerNewOwner.requestFocus();
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
                        GlobalMethods.callToNumber(context, textCustomerNewPhone);
                    }
                    else{
                        ActivityCompat.requestPermissions(getActivity(), new String[] {
                                        Manifest.permission.CALL_PHONE},
                                35);
                    }
                }
                else {
                    GlobalMethods.callToNumber(context, textCustomerNewPhone);
                }

                break;
        }
    }
}
