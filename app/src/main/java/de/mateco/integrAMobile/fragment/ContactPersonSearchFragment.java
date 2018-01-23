package de.mateco.integrAMobile.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import de.mateco.integrAMobile.Helper.Constants;
import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.DelayAutoCompleteTextView;
import de.mateco.integrAMobile.Helper.GlobalClass;
import de.mateco.integrAMobile.Helper.LogApp;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.ContactPersonSearchAdapter;
import de.mateco.integrAMobile.adapter.CustomerSearchResultAdapter;
import de.mateco.integrAMobile.adapter.SalutationAdapter;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.ContactPersonModel;
import de.mateco.integrAMobile.model.ContactPersonSearchModel;
import de.mateco.integrAMobile.model.CustomerActivityModel;
import de.mateco.integrAMobile.model.CustomerCompletedOrderModel;
import de.mateco.integrAMobile.model.CustomerDatabaseModel;
import de.mateco.integrAMobile.model.CustomerFullDetailModel;
import de.mateco.integrAMobile.model.CustomerLostSaleDataModel;
import de.mateco.integrAMobile.model.CustomerModel;
import de.mateco.integrAMobile.model.CustomerOfferModel;
import de.mateco.integrAMobile.model.CustomerOpenOfferModel;
import de.mateco.integrAMobile.model.CustomerOpenOrderModel;
import de.mateco.integrAMobile.model.CustomerProjectModel;
import de.mateco.integrAMobile.model.CustomerSearchPagingRequestModel;
import de.mateco.integrAMobile.model.HintModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LoginPersonModel;
import de.mateco.integrAMobile.model.SalutationModel;

/**
 * Created by S Soft on 13-Sep-17.
 */

public class ContactPersonSearchFragment extends BaseFragment implements TextView.OnEditorActionListener{

    String FOCUSED_CUSTOMER_NO="customerno",FOCUSED_KANR="kanrno",FOCUSED_MATCHCODE="matccode",FOCUSED_NAME1="name1",
            FOCUSED_STREET="street",FOCUSED_PLZ="plz",FOCUSED_ORT="ort",FOCUSED_TEL="telephone",FOCUSED_USTIDNR="ustidnr",
            FOCUSED_ANREDE="anrede",FOCUSED_VORNAME="vorname",FOCUSED_NACHNAME="nachname",FOCUSED_ANSPTELEPHONE="ans_telephone";
    String lastFocusedvalue="";
    AutoCompleteSearchAdapter adapterAutocomplete;
    String jsonToSend="";
    ArrayList<HintModel> hintList = new ArrayList<>();

    String loginUserNumber="";

    private SalutationAdapter salutationAdapter;
    private SalutationModel selectedSalutation;
    private ArrayList<SalutationModel> listOfSalutation;

    private MatecoPriceApplication matecoPriceApplication;
    private View rootView;
    private Language language;
    private Menu menu;
    private DataBaseHandler db;
    private int pageNuber = 1;
    private int customerCount = 0;
    private int totalPageCount = 1;
    private int pageSize = 10;
    private View footerView;
    //private boolean loadingMore = false;
    private Button footerButton;
    private DelayAutoCompleteTextView textCustomerSearchCustomerNo, textCustomerSearchKanr,textCustomerSearchMatchCode,
            textCustomerSearchName1,textCustomerSearchRoad, textCustomerSearchZipCode,textCustomerSearchPlace, textCustomerSearchTel,
        textCustomerSearchUstIDNr,textCustomerSearchVorname,textCustomerSearchNachname,textCustomerSearchAnsTelephone;
    Spinner spinnerAnrede;
    private ListView listViewContctPersonSearchResult;
    private ArrayList<ContactPersonSearchModel> arrayListContactPersonSearchResult;
    private ContactPersonSearchAdapter adapterContactPersonSearch;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if(rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_contact_person_search, container, false);
            super.initializeFragment(rootView);
            ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Suche Ansprechpartner");
        }
        else
        {
            if(rootView.getParent() != null)
                ((ViewGroup)rootView.getParent()).removeView(rootView);
        }
        return rootView;
    }
    @Override
    public void initializeComponents(View rootView)
    {
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        db = new DataBaseHandler(getActivity());
        getActivity().invalidateOptionsMenu();
//        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelSearch());
        setHasOptionsMenu(true);
        footerView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_list_button, null, false);
        footerButton = (Button)footerView.findViewById(R.id.buttonLoadMore);
        pageSize = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).getInt("CustomerPageSize", 10);

        loginUserNumber = matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber();

        selectedSalutation = new SalutationModel();
        listOfSalutation = new ArrayList<>();
        listOfSalutation = db.getSalutation();

        spinnerAnrede = (Spinner)rootView.findViewById(R.id.spinnerCustomerContactPersonSalutation);
        if(listOfSalutation.size() > 0) {
            salutationAdapter = new SalutationAdapter(getActivity(), listOfSalutation, R.layout.list_item_spinner_salutation, language);
            spinnerAnrede.setAdapter(salutationAdapter);
            spinnerAnrede.setSelection(0);
        }


        listViewContctPersonSearchResult = (ListView)rootView.findViewById(R.id.listviewContactPersonList);
        listViewContctPersonSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                adapterContactPersonSearch.setSelectedIndex(position);
                menu.findItem(R.id.actionForward).setVisible(true);
            }
        });

        adapterAutocomplete = new AutoCompleteSearchAdapter(getActivity());
        textCustomerSearchCustomerNo = (DelayAutoCompleteTextView)rootView.findViewById(R.id.textCustomerSearchCustomerNo);
        textCustomerSearchCustomerNo.setThreshold(3);
        //textCustomerSearchCustomerNo.setLoadingIndicator((android.widget.ProgressBar)rootView.findViewById(R.id.pb_loading_indicator));
        textCustomerSearchCustomerNo.setAdapter(adapterAutocomplete);
        textCustomerSearchCustomerNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textCustomerSearchCustomerNo.setText(hintModel.getHint());
            }
        });
        textCustomerSearchCustomerNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_CUSTOMER_NO;
                }
            }
        });

        textCustomerSearchKanr = (DelayAutoCompleteTextView)rootView.findViewById(R.id.textCustomerSearchKanr);
        textCustomerSearchKanr.setThreshold(GlobalClass.thresHodValue);
        //textCustomerSearchKanr.setLoadingIndicator((android.widget.ProgressBar)rootView.findViewById(R.id.pb_loading_indicator));
        textCustomerSearchKanr.setAdapter(adapterAutocomplete);
        textCustomerSearchKanr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textCustomerSearchKanr.setText(hintModel.getHint());
            }
        });
        textCustomerSearchKanr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_KANR;
                }
            }
        });

        textCustomerSearchMatchCode = (DelayAutoCompleteTextView) rootView.findViewById(R.id.textCustomerSearchMatchCode);
        textCustomerSearchMatchCode.setThreshold(GlobalClass.thresHodValue);
        //textCustomerSearchMatchCode.setLoadingIndicator((android.widget.ProgressBar)rootView.findViewById(R.id.pb_loading_indicator));
        textCustomerSearchMatchCode.setAdapter(adapterAutocomplete);
        textCustomerSearchMatchCode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textCustomerSearchMatchCode.setText(hintModel.getHint());
            }
        });
        textCustomerSearchMatchCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_MATCHCODE;
                }
            }
        });

        textCustomerSearchName1 = (DelayAutoCompleteTextView)rootView.findViewById(R.id.textCustomerSearchName1);
        textCustomerSearchName1.setThreshold(GlobalClass.thresHodValue);
        // textCustomerSearchName1.setLoadingIndicator((android.widget.ProgressBar)rootView.findViewById(R.id.pb_loading_indicator));
        textCustomerSearchName1.setAdapter(adapterAutocomplete);
        textCustomerSearchName1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textCustomerSearchName1.setText(hintModel.getHint());
            }
        });
        textCustomerSearchName1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_NAME1;
                }
            }
        });

        textCustomerSearchRoad = (DelayAutoCompleteTextView)rootView.findViewById(R.id.textCustomerSearchRoad);
        textCustomerSearchRoad.setThreshold(GlobalClass.thresHodValue);
        //textCustomerSearchRoad.setLoadingIndicator((android.widget.ProgressBar)rootView.findViewById(R.id.pb_loading_indicator));
        textCustomerSearchRoad.setAdapter(adapterAutocomplete);
        textCustomerSearchRoad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textCustomerSearchRoad.setText(hintModel.getHint());
            }
        });
        textCustomerSearchRoad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_STREET;
                }
            }
        });

        textCustomerSearchZipCode = (DelayAutoCompleteTextView)rootView.findViewById(R.id.textCustomerSearchZipCode);
        textCustomerSearchZipCode.setThreshold(GlobalClass.thresHodValue);
        //textCustomerSearchZipCode.setLoadingIndicator((android.widget.ProgressBar)rootView.findViewById(R.id.pb_loading_indicator));
        textCustomerSearchZipCode.setAdapter(adapterAutocomplete);
        textCustomerSearchZipCode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textCustomerSearchZipCode.setText(hintModel.getHint());
            }
        });
        textCustomerSearchZipCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_PLZ;
                }
            }
        });

        textCustomerSearchPlace = (DelayAutoCompleteTextView)rootView.findViewById(R.id.textCustomerSearchPlace);
        textCustomerSearchPlace.setThreshold(GlobalClass.thresHodValue);
        //textCustomerSearchPlace.setLoadingIndicator((android.widget.ProgressBar)rootView.findViewById(R.id.pb_loading_indicator));
        textCustomerSearchPlace.setAdapter(adapterAutocomplete);
        textCustomerSearchPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textCustomerSearchPlace.setText(hintModel.getHint());
            }
        });
        textCustomerSearchPlace.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_ORT;
                }
            }
        });

        textCustomerSearchTel = (DelayAutoCompleteTextView)rootView.findViewById(R.id.textCustomerSearchTel);
        textCustomerSearchTel.setThreshold(GlobalClass.thresHodValue);
        //textCustomerSearchTel.setLoadingIndicator((android.widget.ProgressBar)rootView.findViewById(R.id.pb_loading_indicator));
        textCustomerSearchTel.setAdapter(adapterAutocomplete);
        textCustomerSearchTel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textCustomerSearchTel.setText(hintModel.getHint());
            }
        });
        textCustomerSearchTel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_TEL;
                }
            }
        });

        textCustomerSearchUstIDNr = (DelayAutoCompleteTextView)rootView.findViewById(R.id.textCustomerSearchUstIDNr);
        textCustomerSearchUstIDNr.setThreshold(GlobalClass.thresHodValue);
        //textCustomerSearchTel.setLoadingIndicator((android.widget.ProgressBar)rootView.findViewById(R.id.pb_loading_indicator));
        textCustomerSearchUstIDNr.setAdapter(adapterAutocomplete);
        textCustomerSearchUstIDNr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textCustomerSearchUstIDNr.setText(hintModel.getHint());
            }
        });
        textCustomerSearchUstIDNr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_USTIDNR;
                }
            }
        });

        textCustomerSearchVorname = (DelayAutoCompleteTextView)rootView.findViewById(R.id.textCustomerSearchVorName);
        textCustomerSearchVorname.setThreshold(GlobalClass.thresHodValue);
        //textCustomerSearchTel.setLoadingIndicator((android.widget.ProgressBar)rootView.findViewById(R.id.pb_loading_indicator));
        textCustomerSearchVorname.setAdapter(adapterAutocomplete);
        textCustomerSearchVorname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textCustomerSearchVorname.setText(hintModel.getHint());
            }
        });
        textCustomerSearchVorname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_VORNAME;
                }
            }
        });

        textCustomerSearchNachname = (DelayAutoCompleteTextView)rootView.findViewById(R.id.textCustomerSearchNachName);
        textCustomerSearchNachname.setThreshold(GlobalClass.thresHodValue);
        //textCustomerSearchTel.setLoadingIndicator((android.widget.ProgressBar)rootView.findViewById(R.id.pb_loading_indicator));
        textCustomerSearchNachname.setAdapter(adapterAutocomplete);
        textCustomerSearchNachname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textCustomerSearchNachname.setText(hintModel.getHint());
            }
        });
        textCustomerSearchNachname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_NACHNAME;
                }
            }
        });

        textCustomerSearchAnsTelephone = (DelayAutoCompleteTextView)rootView.findViewById(R.id.textCustomerSearchAnspTelefon);
        textCustomerSearchAnsTelephone.setThreshold(GlobalClass.thresHodValue);
        //textCustomerSearchTel.setLoadingIndicator((android.widget.ProgressBar)rootView.findViewById(R.id.pb_loading_indicator));
        textCustomerSearchAnsTelephone.setAdapter(adapterAutocomplete);
        textCustomerSearchAnsTelephone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textCustomerSearchAnsTelephone.setText(hintModel.getHint());
            }
        });
        textCustomerSearchAnsTelephone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_ANSPTELEPHONE;
                }
            }
        });

        arrayListContactPersonSearchResult = new ArrayList<>();
        adapterContactPersonSearch = new ContactPersonSearchAdapter(getActivity(), arrayListContactPersonSearchResult);
        listViewContctPersonSearchResult.setAdapter(adapterContactPersonSearch);
        footerView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_list_button, null, false);
        footerButton = (Button)footerView.findViewById(R.id.buttonLoadMore);
        pageSize = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).getInt("CustomerPageSize", 10);
        setUpLanguage();
        super.initializeComponents(rootView);
    }

    @Override
    public void bindEvents(View rootView)
    {
        textCustomerSearchCustomerNo.setOnEditorActionListener(this);
        textCustomerSearchKanr.setOnEditorActionListener(this);
        textCustomerSearchMatchCode.setOnEditorActionListener(this);
        textCustomerSearchName1.setOnEditorActionListener(this);
        textCustomerSearchRoad.setOnEditorActionListener(this);
        textCustomerSearchZipCode.setOnEditorActionListener(this);
        textCustomerSearchPlace.setOnEditorActionListener(this);
        textCustomerSearchTel.setOnEditorActionListener(this);

        textCustomerSearchUstIDNr.setOnEditorActionListener(this);
        textCustomerSearchNachname.setOnEditorActionListener(this);
        textCustomerSearchVorname.setOnEditorActionListener(this);
        textCustomerSearchAnsTelephone.setOnEditorActionListener(this);


        spinnerAnrede.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0)
                {
                    selectedSalutation = null;
                }
                else
                {
                    selectedSalutation = listOfSalutation.get(position - 1);
                   // lastFocusedvalue = FOCUSED_ANREDE;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        footerButton.setText(language.getMessageLoadMoreData());
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchForNewPage();
            }
        });

        super.bindEvents(rootView);
    }
    private void setUpLanguage()
    {
        TextView labelCustomerSearchCustomerNo, labelCustomerSearchKanr, labelCustomerSearchMatchCode,labelCustomerSearchName1,
                labelCustomerSearchRoad, labelCustomerSearchZipCode, labelCustomerSearchPlace, labelCustomerSearchTel,
                labelCustomerSearchUstNo,labelCustomerSearchAnrede,labelCustomerSearchVorname,labelCustomerSearchNachname,labelCustomerSearchAnsTelephone;

        labelCustomerSearchCustomerNo = (TextView)rootView.findViewById(R.id.labelCustomerSearchCustomerNo);
        labelCustomerSearchKanr = (TextView)rootView.findViewById(R.id.labelCustomerSearchKanr);
        labelCustomerSearchMatchCode = (TextView)rootView.findViewById(R.id.labelCustomerSearchMatchCode);
        labelCustomerSearchName1 = (TextView)rootView.findViewById(R.id.labelCustomerSearchName1);
        labelCustomerSearchRoad = (TextView)rootView.findViewById(R.id.labelCustomerSearchRoad);
        labelCustomerSearchZipCode = (TextView)rootView.findViewById(R.id.labelCustomerSearchZipCode);
        labelCustomerSearchPlace = (TextView)rootView.findViewById(R.id.labelCustomerSearchPlace);
        labelCustomerSearchTel = (TextView)rootView.findViewById(R.id.labelCustomerSearchTel);

        labelCustomerSearchUstNo = (TextView)rootView.findViewById(R.id.labelCustomerSearchUstIDNr);
        labelCustomerSearchAnrede = (TextView)rootView.findViewById(R.id.labelCustomerSearchAnrede);
        labelCustomerSearchVorname = (TextView)rootView.findViewById(R.id.labelCustomerSearchVorname);
        labelCustomerSearchNachname = (TextView)rootView.findViewById(R.id.labelCustomerSearchNachname);
        labelCustomerSearchAnsTelephone = (TextView)rootView.findViewById(R.id.labelCustomerSearchAnspTelephone);


        labelCustomerSearchCustomerNo.setText(language.getLabelCustomerNo());
        labelCustomerSearchKanr.setText(language.getLabelKanr());
        labelCustomerSearchMatchCode.setText(language.getLabelMatchCode());
        labelCustomerSearchName1.setText(language.getLabelName() + " 1");
        labelCustomerSearchRoad.setText(language.getLabelRoad());
        labelCustomerSearchZipCode.setText(language.getLabelZipCode());
        labelCustomerSearchPlace.setText(language.getLabelPlace());
        labelCustomerSearchTel.setText(language.getLabelTelephone());

        labelCustomerSearchUstNo.setText(language.getLabelVatNo());
        labelCustomerSearchAnrede.setText(language.getLabelSalutation());
        labelCustomerSearchVorname.setText(language.getLabelFirstName());
        labelCustomerSearchNachname.setText(language.getLabelLastName());
        labelCustomerSearchAnsTelephone.setText(language.getLabelTelephoneAnsp());
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);
        this.menu = menu;
        menu.findItem(R.id.actionWrong).setVisible(false);
        menu.findItem(R.id.actionRight).setVisible(false);
        menu.findItem(R.id.actionEdit).setVisible(false);
        menu.findItem(R.id.actionAdd).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);
        if(getArguments() == null)
        {
            menu.findItem(R.id.actionBack).setVisible(false);
        }
        else
        {
            menu.findItem(R.id.actionBack).setVisible(true);
        }

        //return true;
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.actionSettings:
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new SettingFragment(),"Setting");
                //transaction.addToBackStack(SettingFragment.Tag);
                transaction.addToBackStack("Setting");
                transaction.commit();
                return true;
            case R.id.actionBack:
                if (getFragmentManager().getBackStackEntryCount() == 0)
                {
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
            case R.id.actionForward:
                if(!(adapterContactPersonSearch.selectedIndex == -1))
                {
                    getCustomerDetails(adapterContactPersonSearch.selectedIndex);
                }
                else
                {
                    //showShortToast(language.getMessageSelectCustomerFirst());
                }
                return true;
            case R.id.actionAdd:
                //loadCustomer();
                //menu.findItem(R.id.actionForward).setVisible(true);
                return true;
            case R.id.actionSearch:
                searchForResults();
                return true;
            case R.id.actionRight:
                if(getArguments().containsKey("ProjectCustomer"))
                {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                else if(getArguments().containsKey("TradeObject"))
                {
                    Fragment fragment = new ProjectDetailFragment();
                    Bundle args = new Bundle();
                    args.putParcelable("TradeObject",getArguments().getParcelable("TradeObject"));
                    fragment.setArguments(args);
                    transaction.replace(R.id.content_frame, fragment);
                    getActivity().getSupportFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
                    transaction.addToBackStack("Customer Data");
                    transaction.commit();
                }
                else
                {
                    Fragment fragment = new SiteInspectionNewFragment();
                    transaction.replace(R.id.content_frame, fragment);
                    getActivity().getSupportFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
                    transaction.addToBackStack("Customer Data");
                    transaction.commit();
                }

            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }
    private void searchForNewPage()
    {
        matecoPriceApplication.hideKeyboard(getActivity());
        adapterContactPersonSearch.setSelectedIndex(-1);
        adapterContactPersonSearch.notifyDataSetChanged();
        String customerNo = textCustomerSearchCustomerNo.getText().toString().trim();
        String kaNr = textCustomerSearchKanr.getText().toString().trim();
        String matchCode = textCustomerSearchMatchCode.getText().toString().trim();
        String name1 = textCustomerSearchName1.getText().toString().trim();
        String road = textCustomerSearchRoad.getText().toString().trim();
        String zipCode = textCustomerSearchZipCode.getText().toString().trim();
        String place = textCustomerSearchPlace.getText().toString().trim();
        String telePhone = textCustomerSearchTel.getText().toString().trim();
        String ustId = textCustomerSearchUstIDNr.getText().toString().trim();
        String vorname = textCustomerSearchVorname.getText().toString().trim();
        String nachname = textCustomerSearchNachname.getText().toString().trim();
        String anstelephone = textCustomerSearchAnsTelephone.getText().toString().trim();
        String salutation = "";
        if(selectedSalutation != null)
        {
            salutation = selectedSalutation.getSalutationDesignation();
        }


        if(customerNo.length() > 0 || kaNr.length() > 0 || matchCode.length() > 0 || name1.length() > 0 ||
                road.length() > 0 || zipCode.length() > 0 || place.length() > 0 || telePhone.length() > 0 ||
                ustId.length() > 0 || vorname.length() > 0 || nachname.length() > 0 || anstelephone.length() > 0
                || salutation.length() > 0)
        {
            ContactPersonSearchModel contactperson = new ContactPersonSearchModel();
            contactperson.setKundenNr(customerNo.trim());
            contactperson.setKaNr(kaNr.trim());
            contactperson.setMatchCode(matchCode.trim());
            contactperson.setName1(name1.trim());
            contactperson.setStrasse(road.trim());
            contactperson.setPLZ(zipCode.trim());
            contactperson.setOrt(place.trim());
            contactperson.setTelefon(telePhone.trim());
            contactperson.setMitarbeiter(loginUserNumber);
            contactperson.setUstIDNr(ustId);
            contactperson.setNachName(nachname);
            contactperson.setVorName(vorname);
            contactperson.setAnspTelefon(anstelephone);
            contactperson.setAnrede(salutation);

            pageNuber = pageNuber + 1;
            contactperson.setPageSize(pageSize + "");
            contactperson.setPageNumber(pageNuber+"");
            String jsonToSend = DataHelper.getGson().toJson(contactperson);
            String base64Data = DataHelper.getToken();
            String url = "";
            try
            {
                url = DataHelper.URL_CUSTOMER_HELPER+"anspartnersearchpaging/token=" + URLEncoder.encode(base64Data.trim(), "UTF-8")
                        + "/anspartnersearch=" + URLEncoder.encode(jsonToSend, "UTF-8");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            if(DataHelper.isNetworkAvailable(getActivity()))
            {
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        if(result.equals("error"))
                        {
                            showLongToast(language.getMessageError());
                        }
                        else if(result.equals(DataHelper.NetworkError)){
                            showShortToast(language.getMessageNetworkNotAvailable());
                        }
                        else
                        {
                            try
                            {
                                JSONObject jsonObject = new JSONObject(result);
                                customerCount = jsonObject.getInt("AnsPartnerCount");
                                totalPageCount = jsonObject.getInt("PageCount");
                                pageNuber = jsonObject.getInt("PageNumber");
                                pageSize = jsonObject.getInt("PageSize");
                                JSONArray resultsOfCustomers = jsonObject.getJSONArray("Result");

                                if(pageNuber == totalPageCount)
                                {
                                    listViewContctPersonSearchResult.removeFooterView(footerView);
                                }
                                if(result.length() == 0)
                                {
                                    showLongToast(language.getMessageNoResultFound());
                                }
                                else
                                {
                                    ContactPersonSearchModel.extractFromJson(
                                            resultsOfCustomers.toString(), arrayListContactPersonSearchResult);
                                    adapterContactPersonSearch.notifyDataSetChanged();
                                }
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                    }
                };
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                asyncTask.execute();
            }
            else
            {
                showLongToast(language.getMessageNetworkNotAvailable());
            }
        }
        else
        {
            showLongToast(language.getMessageInputSearchParameter());
        }
    }
    private void searchForResults()
    {
        pageSize = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).getInt("CustomerPageSize", 10);
        pageNuber = 1;
        matecoPriceApplication.hideKeyboard(getActivity());
        arrayListContactPersonSearchResult.clear();
        //listOfCustomerSearchResultTemp.clear();
        adapterContactPersonSearch.setSelectedIndex(-1);
        adapterContactPersonSearch.notifyDataSetChanged();
        String customerNo = textCustomerSearchCustomerNo.getText().toString().trim();
        String kaNr = textCustomerSearchKanr.getText().toString().trim();
        String matchCode = textCustomerSearchMatchCode.getText().toString().trim();
        String name1 = textCustomerSearchName1.getText().toString().trim();
        String road = textCustomerSearchRoad.getText().toString().trim();
        String zipCode = textCustomerSearchZipCode.getText().toString().trim();
        String place = textCustomerSearchPlace.getText().toString().trim();
        String telePhone = textCustomerSearchTel.getText().toString().trim();
        String ustId = textCustomerSearchUstIDNr.getText().toString().trim();
        String vorname = textCustomerSearchVorname.getText().toString().trim();
        String nachname = textCustomerSearchNachname.getText().toString().trim();
        String anstelephone = textCustomerSearchAnsTelephone.getText().toString().trim();
        String salutation = "";
        if(selectedSalutation != null)
        {
            salutation = selectedSalutation.getSalutationDesignation();
        }


        if(customerNo.length() > 0 || kaNr.length() > 0 || matchCode.length() > 0 || name1.length() > 0 ||
                road.length() > 0 || zipCode.length() > 0 || place.length() > 0 || telePhone.length() > 0 ||
                ustId.length() > 0 || vorname.length() > 0 || nachname.length() > 0 || anstelephone.length() > 0
                || salutation.length() > 0)
        {
            ContactPersonSearchModel contactperson = new ContactPersonSearchModel();
            contactperson.setKundenNr(customerNo.trim());
            contactperson.setKaNr(kaNr.trim());
            contactperson.setMatchCode(matchCode.trim());
            contactperson.setName1(name1.trim());
            contactperson.setStrasse(road.trim());
            contactperson.setPLZ(zipCode.trim());
            contactperson.setOrt(place.trim());
            contactperson.setTelefon(telePhone.trim());
            contactperson.setPageSize(pageSize + "");
            contactperson.setPageNumber(pageNuber+"");
            contactperson.setMitarbeiter(loginUserNumber);
            contactperson.setUstIDNr(ustId);
            contactperson.setNachName(nachname);
            contactperson.setVorName(vorname);
            contactperson.setAnspTelefon(anstelephone);
            contactperson.setAnrede(salutation);


            listViewContctPersonSearchResult.removeFooterView(footerView);
            pageNuber = 1;
            //Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String jsonToSend = DataHelper.getGson().toJson(contactperson);
            String base64Data = DataHelper.getToken();
            String url = "";
            try
            {

                url = DataHelper.URL_CUSTOMER_HELPER+"anspartnersearchpaging/token=" + URLEncoder.encode(base64Data.trim(), "UTF-8")
                        + "/anspartnersearch=" + URLEncoder.encode(jsonToSend, "UTF-8");

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            if(DataHelper.isNetworkAvailable(getActivity()))
            {
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        if(result.equals("error"))
                        {
                            showLongToast(language.getMessageError());
                        }
                        else if(result.equals(DataHelper.NetworkError)){
                            showShortToast(language.getMessageNetworkNotAvailable());
                        }
                        else
                        {
                            try
                            {
                                JSONObject jsonObject = new JSONObject(result);
                                customerCount = jsonObject.getInt("AnsPartnerCount");
                                totalPageCount = jsonObject.getInt("PageCount");
                                pageNuber = jsonObject.getInt("PageNumber");
                                pageSize = jsonObject.getInt("PageSize");
                                JSONArray resultsOfCustomers = jsonObject.getJSONArray("Result");
                                if(pageNuber < totalPageCount)
                                {
                                    listViewContctPersonSearchResult.addFooterView(footerView);
                                }
                                if(resultsOfCustomers.length() == 0)
                                {
                                    showLongToast(language.getMessageNoResultFound());
                                }
                                else
                                {
                                    ContactPersonSearchModel.extractFromJson(
                                            resultsOfCustomers.toString(), arrayListContactPersonSearchResult);
                                    adapterContactPersonSearch.notifyDataSetChanged();
                                }
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                    }
                };
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                asyncTask.execute();
            }
            else
            {
                showLongToast(language.getMessageNetworkNotAvailable());
            }
        }
        else
        {
            showLongToast(language.getMessageInputSearchParameter());
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        if (actionId == EditorInfo.IME_ACTION_SEARCH)
        {
            searchForResults();
            return true;
        }
        return false;
    }
    public class AutoCompleteSearchAdapter extends BaseAdapter implements Filterable {

        private static final int MAX_RESULTS = 10;
        private Context mContext;
        private List<HintModel> arraylistHint;

        public AutoCompleteSearchAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return arraylistHint.size();
        }

        @Override
        public HintModel getItem(int index) {
            return arraylistHint.get(index);
        }

        @Override
        public long getItemId(int index) {
            return index;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.auto_complete_item, parent, false);
            }
            ((TextView) convertView.findViewById(R.id.txtAutoitem)).setText(getItem(position).getHint());
            //((TextView) convertView.findViewById(R.id.tv_id_product_ac_barcode)).setText(getItem(position).getBarcode());
            return convertView;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        findHintsFromApi(mContext, constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = arraylistHint;
                        filterResults.count = arraylistHint.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        arraylistHint = (List<HintModel>) results.values;
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }};
            return filter;
        }

        /**
         * Returns a search result for the given book title.
         */
        public void findHintsFromApi(final Context context, String search) {
            arraylistHint = new ArrayList<HintModel>();
            try {
                String customerNo = textCustomerSearchCustomerNo.getText().toString().trim();
                String kaNr = textCustomerSearchKanr.getText().toString().trim();
                String matchCode = textCustomerSearchMatchCode.getText().toString().trim();
                String name1 = textCustomerSearchName1.getText().toString().trim();
                String road = textCustomerSearchRoad.getText().toString().trim();
                String zipCode = textCustomerSearchZipCode.getText().toString().trim();
                String place = textCustomerSearchPlace.getText().toString().trim();
                String telePhone = textCustomerSearchTel.getText().toString().trim();


                String ustId = textCustomerSearchUstIDNr.getText().toString().trim();
                String Vorname = textCustomerSearchVorname.getText().toString().trim();
                String Nachname = textCustomerSearchNachname.getText().toString().trim();
                String ansTelephone = textCustomerSearchAnsTelephone.getText().toString().trim();
                /*String salutation = "";
                if(selectedSalutation != null)
                {
                    salutation = selectedSalutation.getSalutationId();
                }*/

                if(customerNo.length() > 0 || kaNr.length() > 0 || matchCode.length() > 0 || name1.length() > 0 ||
                        road.length() > 0 || zipCode.length() > 0 || place.length() > 0 || telePhone.length() > 0 ||
                        ustId.length() > 0 || Vorname.length() > 0 || Nachname.length() > 0 || ansTelephone.length() > 0) {
                    ContactPersonSearchModel customerSearch = new ContactPersonSearchModel();

                    customerSearch.setKundenNr("");
                    customerSearch.setKaNr("");
                    customerSearch.setMatchCode("");
                    customerSearch.setName1("");
                    customerSearch.setStrasse("");
                    customerSearch.setPLZ("");
                    customerSearch.setOrt("");
                    customerSearch.setTelefon("");

                    customerSearch.setUstIDNr("");
                    customerSearch.setNachName("");
                    customerSearch.setVorName("");
                    customerSearch.setAnspTelefon("");
                    customerSearch.setAnrede("");

                    if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_CUSTOMER_NO)){
                        customerSearch.setKundenNr(customerNo.trim());
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_KANR)){
                        customerSearch.setKaNr(kaNr.trim());
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_MATCHCODE)){
                        customerSearch.setMatchCode(matchCode.trim());
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_NAME1)){
                        customerSearch.setName1(name1.trim());
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_STREET)){
                        customerSearch.setStrasse(road.trim());
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_PLZ)){
                        customerSearch.setPLZ(zipCode.trim());
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_ORT)){
                        customerSearch.setOrt(place.trim());
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_TEL)){
                        customerSearch.setTelefon(telePhone.trim());
                    }

                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_USTIDNR)){
                        customerSearch.setUstIDNr(ustId.trim());
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_VORNAME)){
                        customerSearch.setVorName(Vorname.trim());
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_NACHNAME)){
                        customerSearch.setNachName(Nachname.trim());
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_ANSPTELEPHONE)){
                        customerSearch.setAnspTelefon(ansTelephone.trim());
                    }

                    customerSearch.setPageSize(GlobalClass.pageSizeForHint + "");
                    customerSearch.setPageNumber(GlobalClass.pageNuber + "");
                    customerSearch.setMitarbeiter(loginUserNumber);
                    jsonToSend = DataHelper.getGson().toJson(customerSearch);
                }
                String base64Data = DataHelper.getToken();
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, DataHelper.URL_CUSTOMER_HELPER+"anspartnersearchpaginghint/token=" + URLEncoder.encode(base64Data.trim(), "UTF-8")
                        + "/anspartnersearch=" + URLEncoder.encode(jsonToSend, "UTF-8"),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.toString());
                                    JSONArray resultsOfCustomers = jsonObject.getJSONArray("Result");
                                    TypeToken<List<HintModel>> token = new TypeToken<List<HintModel>>() {};
                                    arraylistHint = new Gson().fromJson(resultsOfCustomers.toString(),token.getType());
                                    LogApp.showLog(" test "," hint list size : "+ arraylistHint.size());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                notifyDataSetChanged();
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogApp.showLog(""," errro r : "+error);
                        // Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                req.setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


                Volley.newRequestQueue(this.mContext).add(req);
            }
            catch (Exception e){
                LogApp.showLog(""," errro r : "+e.toString());
                //Toast.makeText(context,"", Toast.LENGTH_SHORT).show();
            }

        }
    }
    private void getCustomerDetails(final int selectedIndex)
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
                                matecoPriceApplication.clearPricingData();
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
                                    ((HomeActivity)getActivity()).updateAdapter();
                                Fragment fragment = new PricingFragment();
                                Bundle bundle = new Bundle();
                                bundle.putBoolean(Constants.IsFromSearch,true);
                                bundle.putString("AnsPartenrId",arrayListContactPersonSearchResult.get(selectedIndex).getAnsPartnerId());
                                fragment.setArguments(bundle);
                                transaction.replace(R.id.content_frame, fragment);
                                //getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                //transaction.addToBackStack(SettingFragment.Tag);
                                transaction.addToBackStack("ContactPerson search");
                                transaction.commit();
                                    /*Fragment fragment = new CustomerContactPersonFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putBoolean(Constants.IsFromSearch,true);
                                    bundle.putString("AnsPartenrId",arrayListContactPersonSearchResult.get(selectedIndex).getAnsPartnerId());
                                    fragment.setArguments(bundle);
                                    transaction.replace(R.id.content_frame, fragment);
                                    //getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                    //transaction.addToBackStack(SettingFragment.Tag);
                                    transaction.addToBackStack("ContactPerson search");
                                    transaction.commit();*/
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                                showShortToast(language.getMessageErrorAtParsing());
                            }
                        }
                    }
                };

                String url = DataHelper.URL_AGENDA_HELPER + "agendacustomershow" //+ DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.AGENDA_CUSTOMER_SHOW
                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/fieldname=" + "Kontakt"
                        + "/value=" + arrayListContactPersonSearchResult.get(selectedIndex).getKontakt();
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
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
            if(db.isCustomerAvailable(arrayListContactPersonSearchResult.get(selectedIndex).getKontakt()))
            {
                Gson gson = new Gson();
                CustomerDatabaseModel customerDatabaseModel = db.getCustomer(arrayListContactPersonSearchResult.get(selectedIndex).getKontakt());
                matecoPriceApplication.saveLoadedCustomer(DataHelper.LoadedCustomer, gson.toJson(customerDatabaseModel.getCustomerModel()));

                ArrayList<ContactPersonModel> listOfContactPersonList = new ArrayList<>();
                listOfContactPersonList.addAll(customerDatabaseModel.getListOfContactPerson());
                matecoPriceApplication.saveData(DataHelper.LoadedCustomerContactPerson, gson.toJson(listOfContactPersonList));

                ArrayList<CustomerActivityModel> listOfActivity = new ArrayList<>();
                listOfActivity.addAll(customerDatabaseModel.getListOfActivity());
                matecoPriceApplication.saveData(DataHelper.LoadedCustomerActivity, gson.toJson(listOfActivity));

                ArrayList<CustomerProjectModel> listOfProject = new ArrayList<>();
                listOfProject.addAll(customerDatabaseModel.getListOfProject());
                matecoPriceApplication.saveData(DataHelper.LoadedCustomerProject, gson.toJson(listOfProject));

                ArrayList<CustomerOfferModel> listOfOffer = new ArrayList<>();
                listOfOffer.addAll(customerDatabaseModel.getListOfOffer());
                matecoPriceApplication.saveData(DataHelper.LoadedCustomerOffer, gson.toJson(listOfOffer));

                ArrayList<CustomerOpenOrderModel> listOfOpenOrder = new ArrayList<>();
                listOfOpenOrder.addAll(customerDatabaseModel.getListOfOpenOrder());
                matecoPriceApplication.saveData(DataHelper.LoadedCustomerOpenOrders, gson.toJson(listOfOpenOrder));

                ArrayList<CustomerCompletedOrderModel> listOfCompletedOrder = new ArrayList<>();
                listOfCompletedOrder.addAll(customerDatabaseModel.getListOfCompletedOrder());
                matecoPriceApplication.saveData(DataHelper.LoadedCustomerCompletedOrders, gson.toJson(listOfCompletedOrder));

                ArrayList<CustomerOpenOfferModel> listOfOpenSpecials = new ArrayList<>();
                listOfOpenSpecials.addAll(customerDatabaseModel.getListOfOpenOffer());
                matecoPriceApplication.saveData(DataHelper.LoadedCustomerOffer, gson.toJson(listOfOpenSpecials));

                ArrayList<CustomerLostSaleDataModel> listOfLostSale = new ArrayList<>();
                listOfLostSale.addAll(customerDatabaseModel.getListOfLostSale());
                matecoPriceApplication.saveData(DataHelper.LoadedCustomerOffer, gson.toJson(listOfLostSale));

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                Fragment fragment = new CustomerContactPersonFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constants.IsFromSearch,true);
                fragment.setArguments(bundle);
                transaction.addToBackStack("Customer Search");
                transaction.commit();
            }
            else
            {
                showShortToast(language.getMessageCustomerNotFound());
            }
        }
    }
}
