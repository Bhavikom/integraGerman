package de.mateco.integrAMobile.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.mateco.integrAMobile.Helper.ContactPersonComparable;
import de.mateco.integrAMobile.Helper.DelayAutoCompleteTextView;
import de.mateco.integrAMobile.Helper.GlobalClass;
import de.mateco.integrAMobile.Helper.GlobalMethods;
import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.DatePickerDialogFragment;
import de.mateco.integrAMobile.Helper.LogApp;
import de.mateco.integrAMobile.Helper.ProjectTradeComparable;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.CustomerContactPersonSpinnerAdapter;
import de.mateco.integrAMobile.adapter.CustomerSearchResultAdapter;
import de.mateco.integrAMobile.adapter.ProjectDetailTradeAdapter;
import de.mateco.integrAMobile.adapter.ProjectTradeSpinnerAdapter;
import de.mateco.integrAMobile.asyncTask.AsyncTaskWithAuthorizationHeaderPost;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.ContactPersonModel;
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
import de.mateco.integrAMobile.model.ProjectDetailActivityModel;
import de.mateco.integrAMobile.model.ProjectDetailGenerallyModel;
import de.mateco.integrAMobile.model.ProjectDetailTradeModel;
import de.mateco.integrAMobile.model.ProjectTradeDetailUpdateModel;
import de.mateco.integrAMobile.model.ProjectTradeInsert;
import de.mateco.integrAMobile.model_logonsquare.ProjektGewerkComboListItem;

public class ProjectDetailTradesFragment extends BaseFragment implements View.OnClickListener,TextView.OnEditorActionListener {
    private boolean isCallservice=true;
    private ProgressDialog progressDialog;

    String FOCUSED_CUSTOMER_NO="customerno",FOCUSED_KANR="kanrno",FOCUSED_MATCHCODE="matccode",FOCUSED_NAME1="name1",
            FOCUSED_STREET="street",FOCUSED_PLZ="plz",FOCUSED_ORT="ort",FOCUSED_TEL="telephone";
    String lastFocusedvalue="";
    String jsonToSend="";
    AutoCompleteSearchAdapter adapterAutocomplete;

    String selectedKontact,selectedKUndenNr;
    private View rootView;
    private Menu menu;
    private MatecoPriceApplication matecoPriceApplication;
    private Language language;
    private DataBaseHandler db;
    private ListView listViewProjectTrades;
    private ProjectDetailTradeAdapter adapterProjectDetailTrade;
    private ArrayList<ProjectDetailTradeModel> listOfProjectTrade;
    private ArrayList<ProjectDetailActivityModel> listOfLoadedProjectActivity;
    private EditText textProjectTradeName1, textProjectTradeName2, textProjectTradeKundenNr, textProjectTradeRoad,
            textProjectTradeZipCode, textProjectTradePlace, textProjectTradePhone, textProjectTradeFax, textProjectDetailTradeFaxBuildingSite,
            textProjectDetailTelephoneConstruction;
    private Spinner spinnerProjectDetailTradeTrade, spinnerProjectDetailTradeContactPerson;
    private ArrayList<ProjektGewerkComboListItem> listOfTrades;
    private ArrayList<ContactPersonModel> listOfAllContactPerson;
    private ProjectTradeSpinnerAdapter adapterSpinnerProjectTrade;
    private boolean isInEditMode = false, isInAddMode = false;
    private ProjectDetailTradeModel selectedDetailTrade;
    private CustomerContactPersonSpinnerAdapter adapterContactPerson;
    private ContactPersonModel selectedContactPerson, contactPerson1;

    private ProjektGewerkComboListItem selectedTrade, selectedTrade1;
    private ImageButton imageButtonTradeStartDate, imageButtonTradeEndDate;
    private TextView labelTradeStartDate, textProjectDetailTradeMountingEnd;
    private String kontakt = "";
    private ProjectTradeDetailUpdateModel projectTradeDetailUpdateModel;
    DelayAutoCompleteTextView textCustomerSearchCustomerNo, textCustomerSearchKanr,textCustomerSearchMatchCode,textCustomerSearchName1,
            textCustomerSearchRoad, textCustomerSearchZipCode,textCustomerSearchPlace, textCustomerSearchTel;
    TextView labelCustomerSearchCustomerNo, labelCustomerSearchKanr, labelCustomerSearchMatchCode,labelCustomerSearchName1,
            labelCustomerSearchRoad, labelCustomerSearchZipCode, labelCustomerSearchPlace, labelCustomerSearchTel;
    ListView listCustomerSearchResult;
    ArrayList<CustomerModel> listOfCustomerSearchResult;
    CustomerSearchResultAdapter adapter;
    ProjectTradeInsert projectTradeInsert = new ProjectTradeInsert();
    int pageNuber = 1;
    int customerCount = 0;
    int totalPageCount = 1;
    int pageSize = 10;
    View footerView;
    Button footerButton,buttonDone,buttonSearch;
    private ImageButton imageButtonCallPhone, imageButtonCallTelephone;
    private ImageView imageSortMatchCode, imageSortGewerk;
    private boolean isAscending = false;
    private ArrayList<ContactPersonModel> listOfContactPerson;
    String name1="",kontact="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_project_search_trades, container, false);

        //View rootView = inflater.inflate(R.layout.temp_under_construction, container, false);
//

//        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Project Detail Trades");

        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initializeFragment(view);
            }
        }, 500);
    }

    @Override
    public void initializeComponents(View rootView) {
        super.initializeComponents(rootView);
        matecoPriceApplication = (MatecoPriceApplication) getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        db = new DataBaseHandler(getActivity());
        listOfProjectTrade = new ArrayList<>();

        imageSortMatchCode = (ImageView) rootView.findViewById(R.id.imageSortMatchCode);
        imageSortGewerk = (ImageView) rootView.findViewById(R.id.imageSortGewerk);
        listViewProjectTrades = (ListView) rootView.findViewById(R.id.listViewProjectTrades);
        listOfProjectTrade.addAll(matecoPriceApplication.getLoadedProjectTrades(DataHelper.LoadedProjectDetailTradesInfo, new ArrayList<>().toString()));
        adapterProjectDetailTrade = new ProjectDetailTradeAdapter(getActivity(), listOfProjectTrade);
        listViewProjectTrades.setAdapter(adapterProjectDetailTrade);

        listOfLoadedProjectActivity = matecoPriceApplication.getLoadedProjectActivities(DataHelper.LoadedProjectDetailActivityInfo, new ArrayList<>().toString());
        if (listOfLoadedProjectActivity.size() > 0)
            kontakt = listOfLoadedProjectActivity.get(0).getKontakt();

        textProjectTradeName1 = (EditText) rootView.findViewById(R.id.textProjectTradeName1);
        textProjectTradeName2 = (EditText) rootView.findViewById(R.id.textProjectTradeName2);
        textProjectTradeKundenNr = (EditText) rootView.findViewById(R.id.textProjectTradeKundenNr);
        textProjectTradeRoad = (EditText) rootView.findViewById(R.id.textProjectTradeRoad);
        textProjectTradeZipCode = (EditText) rootView.findViewById(R.id.textProjectTradeZipCode);
        textProjectTradePlace = (EditText) rootView.findViewById(R.id.textProjectTradePlace);
        textProjectTradePhone = (EditText) rootView.findViewById(R.id.textProjectTradePhone);
        textProjectTradeFax = (EditText) rootView.findViewById(R.id.textProjectTradeFax);
        textProjectDetailTradeFaxBuildingSite = (EditText) rootView.findViewById(R.id.textProjectDetailTradeFaxBuildingSite);
        textProjectDetailTradeMountingEnd = (TextView) rootView.findViewById(R.id.textProjectDetailTradeMountingEnd);
        textProjectDetailTelephoneConstruction = (EditText) rootView.findViewById(R.id.textProjectDetailTelephoneConstruction);
        spinnerProjectDetailTradeTrade = (Spinner) rootView.findViewById(R.id.spinnerProjectDetailTradeTrade);
        spinnerProjectDetailTradeContactPerson = (Spinner) rootView.findViewById(R.id.spinnerProjectDetailTradeContactPerson);
        imageButtonTradeStartDate = (ImageButton) rootView.findViewById(R.id.imageButtonTradeStartDate);
        labelTradeStartDate = (TextView) rootView.findViewById(R.id.labelTradeStartDate);
        imageButtonTradeEndDate = (ImageButton) rootView.findViewById(R.id.imageButtonTradeEndDate);
        imageButtonCallPhone = (ImageButton) rootView.findViewById(R.id.imageButtonCallPhone);
        imageButtonCallTelephone = (ImageButton) rootView.findViewById(R.id.imageButtonCallTelephone);

        textProjectTradeName1.setFocusable(false);
        textProjectTradeName1.setFocusableInTouchMode(false);

        textProjectTradeName2.setFocusable(false);
        textProjectTradeName2.setFocusableInTouchMode(false);

        textProjectTradeKundenNr.setFocusable(false);
        textProjectTradeKundenNr.setFocusableInTouchMode(false);

        textProjectTradeRoad.setFocusable(false);
        textProjectTradeRoad.setFocusableInTouchMode(false);

        textProjectTradeZipCode.setFocusable(false);
        textProjectTradeZipCode.setFocusableInTouchMode(false);

        textProjectTradePlace.setFocusable(false);
        textProjectTradePlace.setFocusableInTouchMode(false);

        textProjectTradePhone.setFocusable(false);
        textProjectTradePhone.setFocusableInTouchMode(false);

        textProjectTradeFax.setFocusable(false);
        textProjectTradeFax.setFocusableInTouchMode(false);

        imageButtonTradeStartDate.setOnClickListener(this);
        imageButtonTradeEndDate.setOnClickListener(this);

        listOfTrades = new ArrayList<>();
        listOfTrades.addAll(db.getProjectTrade());

        listOfAllContactPerson = new ArrayList<>();
        adapterContactPerson = new CustomerContactPersonSpinnerAdapter(getActivity(), listOfAllContactPerson, R.layout.list_item_spinner_customer_contact_person, language);
        spinnerProjectDetailTradeContactPerson.setAdapter(adapterContactPerson);

        adapterSpinnerProjectTrade = new ProjectTradeSpinnerAdapter(getActivity(), listOfTrades);
        spinnerProjectDetailTradeTrade.setAdapter(adapterSpinnerProjectTrade);
        if (listOfProjectTrade.size() > 0) {
            adapterProjectDetailTrade.setSelectedIndex(0);
            loadTrade(listOfProjectTrade.get(0));
            selectedDetailTrade = listOfProjectTrade.get(0);
        }
        makeEditable(false);
        setupLanguage();
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle(language.getLabelProject());
    }

    @Override
    public void bindEvents(View rootView) {
        super.bindEvents(rootView);
        imageButtonCallPhone.setOnClickListener(this);
        imageSortMatchCode.setOnClickListener(this);
        imageSortGewerk.setOnClickListener(this);
        imageButtonCallTelephone.setOnClickListener(this);
        listViewProjectTrades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterProjectDetailTrade.setSelectedIndex(position);
                selectedDetailTrade = listOfProjectTrade.get(position);
                loadTrade(listOfProjectTrade.get(position));
            }
        });
        spinnerProjectDetailTradeContactPerson.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedContactPerson = null;
                } else {
                    selectedContactPerson = listOfAllContactPerson.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerProjectDetailTradeTrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedTrade = null;
                } else {
                    selectedTrade = listOfTrades.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadTrade(final ProjectDetailTradeModel object) {
        if (DataHelper.isNetworkAvailable(getActivity())) {

            listOfAllContactPerson.clear();
            clearAllValues();
            textProjectTradeName1.setText(object.getName1());
            textProjectTradeName2.setText(object.getName2());
            textProjectTradeKundenNr.setText(object.getKundenNr());
            textProjectTradeRoad.setText(object.getStrasse());
            textProjectTradeZipCode.setText(object.getPLZ());
            textProjectTradePlace.setText(object.getOrt());
            textProjectTradePhone.setText(object.getTelefon());
            textProjectTradeFax.setText(object.getTelefax());
            textProjectDetailTradeFaxBuildingSite.setText(object.getFax_Baustelle());
            if(!TextUtils.isEmpty(object.getMontageende()) && !object.getMontageende().equals(""))
                textProjectDetailTradeMountingEnd.setText(object.getMontageende().substring(0,10));
            if(!TextUtils.isEmpty(object.getMontagebeginn()) && !object.getMontagebeginn().equalsIgnoreCase(""))
                labelTradeStartDate.setText(object.getMontagebeginn().substring(0, 10));
            textProjectDetailTelephoneConstruction.setText(object.getTel_Baustelle());

            for (int i = 0; i < listOfTrades.size(); i++) {
                if (listOfTrades.get(i).getGewerk().equals(object.getGewerkID())) {
                    spinnerProjectDetailTradeTrade.setSelection(i);
                }
            }
            if (!object.getKundenNr().equals("")) {
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult() {
                    @Override
                    public void OnAsynResult(String result) {
                        if (result.equals("error")) {
                            showShortToast(language.getMessageError());
                        }
                        else if(result.equals(DataHelper.NetworkError)){
                            showShortToast(language.getMessageNetworkNotAvailable());
                        }
                        else {
                            try {
                                listOfAllContactPerson.clear();
                                CustomerFullDetailModel customerFullDetail = new Gson().fromJson(result, CustomerFullDetailModel.class);
                                listOfAllContactPerson.addAll(customerFullDetail.getCustomerContactPersonList());
                                boolean contactPersonSelected = false;
                                for (int j = 0; j < listOfAllContactPerson.size(); j++) {
                                    if (object.getAnspartnerID().equals(listOfAllContactPerson.get(j).getAnspartner())) {
                                        spinnerProjectDetailTradeContactPerson.setSelection(j + 1);
                                        contactPersonSelected = true;
                                        break;
                                    }
                                }
                                if (!contactPersonSelected) {
                                    spinnerProjectDetailTradeContactPerson.setSelection(0);
                                }
                                adapterContactPerson.notifyDataSetChanged();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                //showShortToast(language.getMessageErrorAtParsing());
                            }
                        }
                    }
                };

                try {
                    String url = DataHelper.URL_AGENDA_HELPER + "agendacustomershow" //+ DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.AGENDA_CUSTOMER_SHOW
                            + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                            + "/fieldname=" + "KundenNr"
                            + "/value=" + object.getKundenNr();
                    BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                    asyncTask.execute();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            showShortToast(language.getMessageNetworkNotAvailable());
        }
    }

    private void makeEditable(boolean isEditable) {
        textProjectDetailTradeFaxBuildingSite.setFocusable(isEditable);
        textProjectDetailTradeFaxBuildingSite.setFocusableInTouchMode(isEditable);

        textProjectDetailTelephoneConstruction.setFocusable(isEditable);
        textProjectDetailTelephoneConstruction.setFocusableInTouchMode(isEditable);

        spinnerProjectDetailTradeTrade.setEnabled(isEditable);
        spinnerProjectDetailTradeContactPerson.setEnabled(isEditable);

        imageButtonTradeStartDate.setEnabled(isEditable);
        imageButtonTradeEndDate.setEnabled(isEditable);
    }

    private void setupLanguage() {
        TextView labelProjectTradeGewerk, labelProjectTradeMatchCode;
        TextView labelProjectTradeName1, labelProjectTradeName2, labelProjectTradeKundenNr, labelProjectTradeRoad, labelProjectTradeZipCode,
                labelProjectTradePlace, labelProjectTradePhone, labelProjectTradeFax, labelProjectTradeTrade, labelProjectTradeContactPerson,
                labelProjectDetailTradeFaxBuildingSite, labelProjectDetailTradeMountingEnd, labelProjectDetailTelephoneConstruction, textMont;

        labelProjectTradeGewerk = (TextView) rootView.findViewById(R.id.labelProjectTradeGewerk);
        labelProjectTradeMatchCode = (TextView) rootView.findViewById(R.id.labelProjectTradeMatchCode);
        labelProjectTradeMatchCode.setOnClickListener(this);
        labelProjectTradeGewerk.setOnClickListener(this);
        labelProjectTradeName1 = (TextView) rootView.findViewById(R.id.labelProjectTradeName1);
        labelProjectTradeName2 = (TextView) rootView.findViewById(R.id.labelProjectTradeName2);
        labelProjectTradeKundenNr = (TextView) rootView.findViewById(R.id.labelProjectTradeKundenNr);
        labelProjectTradeRoad = (TextView) rootView.findViewById(R.id.labelProjectTradeRoad);
        labelProjectTradeZipCode = (TextView) rootView.findViewById(R.id.labelProjectTradeZipCode);
        labelProjectTradePlace = (TextView) rootView.findViewById(R.id.labelProjectTradePlace);
        labelProjectTradePhone = (TextView) rootView.findViewById(R.id.labelProjectTradePhone);
        labelProjectTradeFax = (TextView) rootView.findViewById(R.id.labelProjectTradeFax);
        labelProjectTradeTrade = (TextView) rootView.findViewById(R.id.labelProjectTradeTrade);
        labelProjectTradeContactPerson = (TextView) rootView.findViewById(R.id.labelProjectTradeContactPerson);
        labelProjectDetailTradeFaxBuildingSite = (TextView) rootView.findViewById(R.id.labelProjectDetailTradeFaxBuildingSite);
        labelProjectDetailTradeMountingEnd = (TextView) rootView.findViewById(R.id.labelProjectDetailTradeMountingEnd);
        labelProjectDetailTelephoneConstruction = (TextView) rootView.findViewById(R.id.labelProjectDetailTelephoneConstruction);
        textMont = (TextView) rootView.findViewById(R.id.textMont);

        labelProjectTradeGewerk.setText(language.getLabelTrades());
        labelProjectTradeMatchCode.setText(language.getLabelMatchCode());
        labelProjectTradeName1.setText(language.getLabelName() + " 1");
        labelProjectTradeName2.setText(language.getLabelName() + " 2");
        labelProjectTradeKundenNr.setText(language.getLabelCustomerNo());
        labelProjectTradeRoad.setText(language.getLabelRoad());
        labelProjectTradeZipCode.setText(language.getLabelZipCode());
        labelProjectTradePlace.setText(language.getLabelPlace());
        labelProjectTradePhone.setText(language.getLabelPhone());
        labelProjectTradeFax.setText("Fax");
        labelProjectTradeTrade.setText(language.getLabelTrades());
        labelProjectTradeContactPerson.setText(language.getLabelContactPerson());
        labelProjectDetailTradeFaxBuildingSite.setText(language.getLabelFaxBuildingSite());
        labelProjectDetailTradeMountingEnd.setText(language.getLabelMountingEnd());
        labelProjectDetailTelephoneConstruction.setText(language.getLabelTrl());
        textMont.setText(language.getLabelMontBegin());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_menu, menu);
        this.menu = menu;
        menu.findItem(R.id.actionSearch).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);
        menu.findItem(R.id.actionWrong).setVisible(false);
        menu.findItem(R.id.actionRight).setVisible(false);
        if(listOfProjectTrade.size()==0)
            menu.findItem(R.id.actionEdit).setVisible(false);
        //return true;
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionSettings:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new SettingFragment(), "Setting");
                //transaction.addToBackStack(SettingFragment.Tag);
                transaction.addToBackStack("Setting");
                transaction.commit();
                return true;
            case R.id.actionBack:
                if (getFragmentManager().getBackStackEntryCount() == 0) {
                    getActivity().finish();
                } else {
                    getFragmentManager().popBackStack();
                }
                return true;
            case R.id.actionEdit:
                if (listOfProjectTrade.size() > 0) {
                    makeEditable(true);
                    menu.findItem(R.id.actionEdit).setVisible(false);
                    menu.findItem(R.id.actionRight).setVisible(true);
                    menu.findItem(R.id.actionWrong).setVisible(true);
                    menu.findItem(R.id.actionBack).setVisible(false);
                    menu.findItem(R.id.actionAdd).setVisible(false);
                    isInEditMode = true;
                } else {
                    showShortToast(language.getMessageNoTradeFound());
                }
                return true;
            case R.id.actionAdd:
                showAddTradeDialog("without arguments");
                return true;
            case R.id.actionWrong:
                if (isInAddMode) {
                    DialogInterface.OnClickListener positiveCallback = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            addNewTradeFormRedesign(false);
                            loadTrade(selectedDetailTrade);
                            menu.findItem(R.id.actionEdit).setVisible(true);
                            menu.findItem(R.id.actionRight).setVisible(false);
                            menu.findItem(R.id.actionWrong).setVisible(false);
                            menu.findItem(R.id.actionBack).setVisible(true);
                            makeEditable(false);
                            isInEditMode = false;
                            isInAddMode = false;
                            dialog.cancel();
                        }
                    };

                    DialogInterface.OnClickListener negativeCallback = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    };
                    String title = language.getLabelConfirmation();
                    String message = language.getMessageCancelAddingTrade();
                    AlertDialog alert = showAlert(title, message, language.getLabelYes(), language.getLabelNo(),
                            positiveCallback, negativeCallback);
                    alert.show();
                } else if (isInEditMode) {
                    addNewTradeFormRedesign(false);
                    loadTrade(selectedDetailTrade);
                    menu.findItem(R.id.actionEdit).setVisible(true);
                    menu.findItem(R.id.actionRight).setVisible(false);
                    menu.findItem(R.id.actionWrong).setVisible(false);
                    menu.findItem(R.id.actionBack).setVisible(true);
                    menu.findItem(R.id.actionAdd).setVisible(true);
                    makeEditable(false);
                    isInEditMode = false;
                    isInAddMode = false;
                    return true;
                }
                return true;
            case R.id.actionRight:
                if (isInAddMode) {
                    addNewTradeFormRedesign(true);
                } else {
                    updateProjectTradeInfo();
                }
                //showShortToast("Function will be implemented soon");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    private void clearAllValues() {
        textProjectTradeName1.setText("");
        textProjectTradeName2.setText("");
        textProjectTradeKundenNr.setText("");
        textProjectTradeRoad.setText("");
        textProjectTradeZipCode.setText("");
        textProjectTradePlace.setText("");
        textProjectTradePhone.setText("");
        textProjectTradeFax.setText("");
        spinnerProjectDetailTradeTrade.setSelection(0);
        spinnerProjectDetailTradeContactPerson.setSelection(0);
        textProjectDetailTelephoneConstruction.setText("");
        textProjectDetailTradeMountingEnd.setText("");
        labelTradeStartDate.setText("");
        textProjectDetailTradeFaxBuildingSite.setText("");
    }

    private void addNewTradeFormRedesign(boolean isAddMode) {
        isInAddMode = isAddMode;
        String url = "";
        String contactPerson = "";
        if (selectedContactPerson != null) {
            contactPerson = selectedContactPerson.getAnspartner();
        }
        String trade = "";
        if (selectedTrade != null) {
            trade = selectedTrade.getGewerk();
        }
        if(DataHelper.isNetworkAvailable(getActivity())) {
            AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult() {
                @Override
                public void OnAsynResult(String result) {
                    if (result.equals(DataHelper.NetworkError)) {
                        showShortToast(language.getMessageNetworkNotAvailable());
                    }
                }
            };
            try {
            /*url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.ProjectTradeInsert
                    + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                    + "&projectId=" + selectedDetailTrade.getProjekt()
                    + "&Kontakt=" + kontakt
                    + "&Anspartner=" + contactPerson
                    + "&Gewerk=" + trade;*/
                url = DataHelper.URL_PROJECT_HELPER + "projecttradeinsert";// + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.ProjectTradeInsert
                //+ "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                //+ "&projectId=" + selectedDetailTrade.getProjekt()
                //+ "&Kontakt=" + kontakt
                //+ "&Anspartner=" + contactPerson
                //+ "&Gewerk=" + trade;
                MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")));
                multipartEntity.addPart("projectId", new StringBody(selectedDetailTrade.getProjekt()));
                multipartEntity.addPart("Kontakt", new StringBody(kontakt));
                multipartEntity.addPart("Anspartner", new StringBody(contactPerson));
                multipartEntity.addPart("Gewerk", new StringBody(trade));
                AsyncTaskWithAuthorizationHeaderPost asyncTaskPost = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, getActivity(), multipartEntity, true, language);
                asyncTaskPost.execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else {
            showLongToast(language.getMessageNetworkNotAvailable());
        }
        /*BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
        asyncTask.execute();*/
    }

    private void updateProjectTradeInfo() {
        String url = "";
        String name1 = textProjectTradeName1.getText().toString();
        String name2 = textProjectTradeName2.getText().toString();
        String kundenNr = textProjectTradeKundenNr.getText().toString();
        String road = textProjectTradeRoad.getText().toString();
        String zipCode = textProjectTradeZipCode.getText().toString();
        String place = textProjectTradePlace.getText().toString();
        String phone = textProjectTradePhone.getText().toString();
        String fax = textProjectTradeFax.getText().toString();
        String faxBuildingSite = textProjectDetailTradeFaxBuildingSite.getText().toString();
        String mountingEnd = textProjectDetailTradeMountingEnd.getText().toString();
        String telephoneConsturciton = textProjectDetailTelephoneConstruction.getText().toString();

        projectTradeDetailUpdateModel = new ProjectTradeDetailUpdateModel();
        if (selectedContactPerson != null) {
            projectTradeDetailUpdateModel.setAnspartner(selectedContactPerson.getAnspartner());
        } else
            projectTradeDetailUpdateModel.setAnspartner("");

        /* commented on 7th July due to trade was not updating becoze gewarke id was sending null to service*/
        /*if (selectedTrade != null)
            projectTradeDetailUpdateModel.setGewerk(selectedTrade.getProjectTradeId());
        else
            projectTradeDetailUpdateModel.setGewerk("");*/

        /* added on 7th July deu to above issue*/
        if(selectedDetailTrade != null){
            projectTradeDetailUpdateModel.setGewerk(selectedDetailTrade.getGewerkID());
        }
        else {
            projectTradeDetailUpdateModel.setGewerk("");
        }
        projectTradeDetailUpdateModel.setProjectId(selectedDetailTrade.getProjekt());
        //projectTradeDetailUpdateModel.setKontakt(kontakt);
        //projectTradeDetailUpdateModel.setKontakt(matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, "").getKontakt());
        projectTradeDetailUpdateModel.setKontakt(selectedDetailTrade.getKontakt());

        projectTradeDetailUpdateModel.setBeginn(labelTradeStartDate.getText().toString() + "");
        projectTradeDetailUpdateModel.setEnde(textProjectDetailTradeMountingEnd.getText().toString() + "");

        if(!DataHelper.phoneNumberValidationWithoutBlankGerman(textProjectDetailTelephoneConstruction.getText().toString())){
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
            showMessage(language.getLabelAlert(),language.getMessagePhoneValidation(),language.getLabelOk(),listener).show();
            // textCustomerNewPhone.setError(language.getMessageNotValidNumber());
            textProjectDetailTelephoneConstruction.requestFocus();
            //textProjectDetailTelephoneConstruction.setError(language.getMessageNotValidNumber());
        }
        else if(!DataHelper.phoneNumberValidationWithoutBlankGerman(textProjectDetailTradeFaxBuildingSite.getText().toString()))
            textProjectDetailTradeFaxBuildingSite.setError(language.getMessageNotValidNumber());
        else
        {
            projectTradeDetailUpdateModel.setTelefax(textProjectDetailTradeFaxBuildingSite.getText().toString() + "");
            projectTradeDetailUpdateModel.setTelefon(textProjectDetailTelephoneConstruction.getText().toString() + "");

            if(DataHelper.isNetworkAvailable(getActivity())) {
                AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult() {
                    @Override
                    public void OnAsynResult(String result) {
                        if (result.equals(DataHelper.NetworkError)) {
                            showShortToast(language.getMessageNetworkNotAvailable());
                        }
                        else if (!result.equals("error")) {
                            try {
                                JSONArray jsonArray = new JSONArray(result);
                                matecoPriceApplication.saveData(DataHelper.LoadedProjectDetailTradesInfo, jsonArray.toString());
                                listOfProjectTrade.clear();
                                listOfProjectTrade.addAll(matecoPriceApplication.getLoadedProjectTrades(DataHelper.LoadedProjectDetailTradesInfo, new ArrayList<>().toString()));
                                adapterProjectDetailTrade.notifyDataSetChanged();
                                for (int i = 0; i < listOfProjectTrade.size(); i++) {
                                    if (listOfProjectTrade.get(i).equals(selectedDetailTrade.getProjekt())) {
                                        adapterProjectDetailTrade.setSelectedIndex(i);
                                        loadTrade(listOfProjectTrade.get(i));
                                    }

                                }
                                makeEditable(false);
                                menu.findItem(R.id.actionEdit).setVisible(true);
                                menu.findItem(R.id.actionRight).setVisible(false);
                                menu.findItem(R.id.actionWrong).setVisible(false);
                                menu.findItem(R.id.actionBack).setVisible(true);
                                menu.findItem(R.id.actionAdd).setVisible(true);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                };
                String json = new Gson().toJson(projectTradeDetailUpdateModel);
                try {
                /*url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.ProjectTradeUpdate
                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "&projectTradeUpdate=" + URLEncoder.encode(new Gson().toJson(projectTradeDetailUpdateModel), "UTF-8");*/
                    url = DataHelper.URL_PROJECT_HELPER + "projecttradeupdate";// + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.ProjectTradeUpdate
                    //+ "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                    //+ "&projectTradeUpdate=" + URLEncoder.encode(new Gson().toJson(projectTradeDetailUpdateModel), "UTF-8");
                    MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                    multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")));
                    multipartEntity.addPart("projectTradeUpdate", new StringBody(new Gson().toJson(projectTradeDetailUpdateModel), Charset.forName("UTF-8")));
                    AsyncTaskWithAuthorizationHeaderPost asyncTaskPost = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, getActivity(), multipartEntity, true, language);
                    asyncTaskPost.execute();

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }else {
                showLongToast(language.getMessageNetworkNotAvailable());
            }
            /*BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
            asyncTask.execute();*/
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.labelProjectTradeMatchCode:
                if(imageSortMatchCode.getVisibility() != View.GONE && !isAscending)
                {
                    //((TextView)rootView.findViewById(R.id.labelAgendaWeeklyDueName)).setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.ic_sort_asc_24dp);
                    imageSortMatchCode.setImageResource(R.drawable.ic_sort_asc_24dp);
                    Collections.sort(listOfProjectTrade, new ProjectTradeComparable(1, 0));
                    isAscending = true;
                }
                else
                {
                    imageSortMatchCode.setImageResource(R.drawable.ic_sort_dsc_24dp);
                    Collections.sort(listOfProjectTrade, new ProjectTradeComparable(1, 1));
                    imageSortMatchCode.setVisibility(View.VISIBLE);
                    isAscending = false;
                }
                imageSortGewerk.setVisibility(View.GONE);
                adapterProjectDetailTrade.notifyDataSetChanged();

                break;
            case R.id.labelProjectTradeGewerk:
                if(imageSortGewerk.getVisibility() != View.GONE && !isAscending)
                {
                    //((TextView)rootView.findViewById(R.id.labelAgendaWeeklyDueName)).setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.ic_sort_asc_24dp);
                    imageSortGewerk.setImageResource(R.drawable.ic_sort_asc_24dp);
                    Collections.sort(listOfProjectTrade, new ProjectTradeComparable(2, 0));
                    isAscending = true;
                }
                else
                {
                    imageSortGewerk.setImageResource(R.drawable.ic_sort_dsc_24dp);
                    Collections.sort(listOfProjectTrade, new ProjectTradeComparable(2, 1));
                    imageSortGewerk.setVisibility(View.VISIBLE);
                    isAscending = false;
                }
                imageSortMatchCode.setVisibility(View.GONE);
                adapterProjectDetailTrade.notifyDataSetChanged();
                break;
            case R.id.imageButtonTradeStartDate:
                openDatePicker("start");
                break;
            case R.id.imageButtonTradeEndDate:
                openDatePicker("end");
                break;
            case R.id.imageButtonCallPhone:
                if ( Build.VERSION.SDK_INT >= 23)
                {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) ==
                            PackageManager.PERMISSION_GRANTED)
                    {
                        GlobalMethods.callToNumber(context, textProjectTradePhone);
                    }
                    else{
                        ActivityCompat.requestPermissions(getActivity(), new String[] {
                                        Manifest.permission.CALL_PHONE},
                                35);
                    }
                }
                else {
                    GlobalMethods.callToNumber(context, textProjectTradePhone);
                }

                break;
            case R.id.imageButtonCallTelephone:
                if ( Build.VERSION.SDK_INT >= 23)
                {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) ==
                            PackageManager.PERMISSION_GRANTED)
                    {
                        GlobalMethods.callToNumber(context, textProjectDetailTelephoneConstruction);
                    }
                    else{
                        ActivityCompat.requestPermissions(getActivity(), new String[] {
                                        Manifest.permission.CALL_PHONE},
                                35);
                    }
                }
                else {
                    GlobalMethods.callToNumber(context, textProjectDetailTelephoneConstruction);
                }

                break;
            case R.id.imageSortMatchCode:
            case R.id.labelCustomerContactPersonGridHeaderSalutation:
                if(imageSortMatchCode.getVisibility() != View.GONE && !isAscending)
                {
                    //((TextView)rootView.findViewById(R.id.labelAgendaWeeklyDueName)).setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.ic_sort_asc_24dp);
                    imageSortMatchCode.setImageResource(R.drawable.ic_sort_asc_24dp);
                    Collections.sort(listOfContactPerson, new ContactPersonComparable(1, 0));
                    isAscending = true;
                }
                else
                {
                    imageSortMatchCode.setImageResource(R.drawable.ic_sort_dsc_24dp);
                    Collections.sort(listOfContactPerson, new ContactPersonComparable(1, 1));
                    imageSortMatchCode.setVisibility(View.VISIBLE);
                    isAscending = false;
                }
                imageSortGewerk.setVisibility(View.GONE);
                if(listOfContactPerson.size() > 0)
                {
                    selectedContactPerson = listOfContactPerson.get(0);
                    //setCustomerContactPerson(selectedContactPerson);
                    //customerContactPersonListDetailAdapter.setSelectedIndex(0);
                }
                break;
        }
    }

    DatePickerDialog.OnDateSetListener onFromDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
            try {
                monthOfYear += 1;
                String date = dayOfMonth + " " + monthOfYear + " " + year;
                String finaldate = DataHelper.formatDate(formatter.parse(date));
                labelTradeStartDate.setText(finaldate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };

    DatePickerDialog.OnDateSetListener onEndDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
            try {
                monthOfYear += 1;
                String date = dayOfMonth + " " + monthOfYear + " " + year;
                String finaldate = DataHelper.formatDate(formatter.parse(date));
                textProjectDetailTradeMountingEnd.setText(finaldate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };

    private void openDatePicker(String flag) {
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        DatePickerDialogFragment newFragment = new DatePickerDialogFragment();
        Bundle args = new Bundle();
        args.putInt("year", c.get(Calendar.YEAR));
        args.putInt("month", c.get(Calendar.MONTH));
        args.putInt("day", c.get(Calendar.DAY_OF_MONTH));
        newFragment.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        if (flag.equals("start"))
            newFragment.setCallBack(onFromDate);
        else
            newFragment.setCallBack(onEndDate);
        //newFragment.setMinDate(today);
        newFragment.show(getActivity().getSupportFragmentManager(), "startDate");
    }

    private boolean isTradeAlreadyInsert(ProjektGewerkComboListItem object,String kontact)
    {
        for(int i = 0; i < listOfProjectTrade.size(); i++)
        {
            if(listOfProjectTrade.get(i).getGewerkID().trim().equalsIgnoreCase(object.getGewerk().trim())
                    && listOfProjectTrade.get(i).getName1().trim().equalsIgnoreCase(kontact))
            {
                return true;
            }
        }
        return false;
    }

    private void showAddTradeDialog(String args) {
        final Dialog dialog = showCustomDialog(R.layout.dialog_insert_trade, language.getLabelAddTrade());
        dialog.setCancelable(false);
        final TextView labelProjectActivityTradeKunde, textViewProjectTradeKunde, labelProjectDetailTradeProject, labelProjectTradeGewark, labelProjectTradeAnsp;;
        Spinner spinnerProjectTradeTrade, spinnerProjectTradeAnsp;
        EditText textProjectDetailTradeProject;
        ImageButton buttonAddProjectTradeKunde;
        Button buttonDialogAddTrade, buttonDialogAddTradeCancel;

        labelProjectActivityTradeKunde = (TextView) dialog.findViewById(R.id.labelProjectActivityTradeKunde);
        textViewProjectTradeKunde = (TextView) dialog.findViewById(R.id.textViewProjectTradeKunde);
        labelProjectDetailTradeProject = (TextView) dialog.findViewById(R.id.labelProjectDetailTradeProject);
        labelProjectTradeGewark = (TextView) dialog.findViewById(R.id.labelProjectTradeGewark);
        spinnerProjectTradeTrade = (Spinner) dialog.findViewById(R.id.spinnerProjectTradeTrade);
        labelProjectTradeAnsp = (TextView) dialog.findViewById(R.id.labelProjectTradeAnsp);
        spinnerProjectTradeAnsp = (Spinner) dialog.findViewById(R.id.spinnerProjectTradeAnsp);
        buttonAddProjectTradeKunde = (ImageButton) dialog.findViewById(R.id.buttonAddProjectTradeKunde);
        textProjectDetailTradeProject = (EditText) dialog.findViewById(R.id.textProjectDetailTradeProject);
        buttonDialogAddTradeCancel = (Button) dialog.findViewById(R.id.buttonDialogAddTradeCancel);
        buttonDialogAddTrade = (Button) dialog.findViewById(R.id.buttonDialogAddTrade);
        textProjectDetailTradeProject.setEnabled(false);
        buttonDialogAddTrade.setText(language.getLabelOk());
        buttonDialogAddTradeCancel.setText(language.getLabelCancel());

        labelProjectActivityTradeKunde.setText(language.getLabelCustomer());
        labelProjectDetailTradeProject.setText(language.getLabelProject());
        labelProjectTradeGewark.setText(language.getLabelTrades());
        labelProjectTradeAnsp.setText(language.getLabelAnspPartner());

        textProjectDetailTradeProject.setText(matecoPriceApplication.getProjectDetailGenerallyModel(DataHelper.LoadedProjectDetailGenerallyInfo, new ProjectDetailGenerallyModel().toString()).getBeschreibung());
        textProjectDetailTradeProject.setFocusable(false);

        ProjectTradeSpinnerAdapter adapterSpinnerProjectAddTrade = new ProjectTradeSpinnerAdapter(getActivity(), listOfTrades);
        spinnerProjectTradeTrade.setAdapter(adapterSpinnerProjectAddTrade);
        CustomerContactPersonSpinnerAdapter adapterContactPerson = new CustomerContactPersonSpinnerAdapter(getActivity(), listOfAllContactPerson, R.layout.list_item_spinner_customer_contact_person, language);
        spinnerProjectTradeAnsp.setAdapter(adapterContactPerson);

        if (args.equals("Trade")) {
            textViewProjectTradeKunde.setText(name1);
            //textViewProjectTradeKunde.setText(matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, "").getName1() + "");
            if (!projectTradeInsert.getAnspartner().equals("")) {
                for (int i = 0; i < listOfAllContactPerson.size(); i++) {
                    if (projectTradeInsert.getAnspartner().equals(listOfAllContactPerson.get(i).getAnspartner()))
                        spinnerProjectTradeAnsp.setSelection(i);
                }
            }
            for (int i = 0; i < listOfTrades.size(); i++) {
                if (projectTradeInsert.getGewerk().equals(listOfTrades.get(i).getGewerk()))
                    spinnerProjectTradeTrade.setSelection(i);
            }
        }

        buttonAddProjectTradeKunde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                if (contactPerson1 != null)
                    projectTradeInsert.setAnspartner(contactPerson1.getAnspartner() + "");
                else
                    projectTradeInsert.setAnspartner("");
                if (selectedTrade1 != null)
                    projectTradeInsert.setGewerk(selectedTrade1.getGewerk() + "");
                else
                    if(listOfTrades != null && listOfTrades.size() > 0) {
                        projectTradeInsert.setGewerk(listOfTrades.get(0).getGewerk() + "");
                    }

                showCustomerDialog();
            }
        });

        buttonDialogAddTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "";
                /* commented on 8th July*/
                //String kontactTOsend = matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, "").getName1();
                String kontactTOsend = name1;
                if (textViewProjectTradeKunde.getText().toString().equals(""))
                    textViewProjectTradeKunde.setError(language.getMessageEnter() + " " + language.getLabelCustomer());
                else if(selectedTrade1 != null && isTradeAlreadyInsert(selectedTrade1,kontactTOsend))
                {
                    showShortToast(language.getMessageGewerkeAlreadyExists());
                }
                else
                {
                    ProjectTradeInsert projectTradeInsert = new ProjectTradeInsert();
                    if (contactPerson1 != null)
                        projectTradeInsert.setAnspartner(contactPerson1.getAnspartner() + "");
                    else
                        projectTradeInsert.setAnspartner("");
                    if (selectedTrade1 != null)
                    {
                        projectTradeInsert.setGewerk(selectedTrade1.getGewerk() + "");
                    }
                    else
                    {
                        //TODO check
                        //projectTradeInsert.setGewerk(listOfTrades.get(0).getProjectTradeId() + "");
                        projectTradeInsert.setGewerk("");
                    }
                    if(selectedDetailTrade!=null)
                        projectTradeInsert.setProjectId(selectedDetailTrade.getProjekt());
                    else
                        projectTradeInsert.setProjectId(matecoPriceApplication.getProjectDetailGenerallyModel(DataHelper.LoadedProjectDetailGenerallyInfo, new ProjectDetailGenerallyModel().toString()).getProjekt());

                    /* commented and addeed below line  on 8th July*/
                    ///projectTradeInsert.setKontakt(matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, "").getKontakt());
                    projectTradeInsert.setKontakt(kontact);
                    if(DataHelper.isNetworkAvailable(getActivity())) {
                        AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult() {
                            @Override
                            public void OnAsynResult(String result) {
                                if (result.equals(DataHelper.NetworkError)) {
                                    showShortToast(language.getMessageNetworkNotAvailable());
                                } else if (result != null && !result.equals("")) {
                                    matecoPriceApplication.saveData(DataHelper.LoadedProjectDetailTradesInfo, result);
                                    listOfProjectTrade.clear();
                                    listOfProjectTrade.addAll(matecoPriceApplication.getLoadedProjectTrades(DataHelper.LoadedProjectDetailTradesInfo, new ArrayList<>().toString()));
                                    if (listOfProjectTrade.size() > 0) {
                                        adapterProjectDetailTrade.notifyDataSetChanged();
                                        adapterProjectDetailTrade.setSelectedIndex(0);
                                        loadTrade(listOfProjectTrade.get(0));
                                        selectedDetailTrade = listOfProjectTrade.get(0);
                                    }
                                    menu.findItem(R.id.actionEdit).setVisible(true);
                                    dialog.dismiss();
                                }
                            }
                        };
                        try {
                        /*url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.ProjectTradeInsert
                                + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                                + "&projectTradeInsert=" + URLEncoder.encode(new Gson().toJson(projectTradeInsert), "UTF-8");*/
                            url = DataHelper.URL_PROJECT_HELPER + "projecttradeinsert"; //+ DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.ProjectTradeInsert
                            //+ "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                            //+ "&projectTradeInsert=" + URLEncoder.encode(new Gson().toJson(projectTradeInsert), "UTF-8");
                            MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                            multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")));
                            multipartEntity.addPart("projectTradeInsert", new StringBody(new Gson().toJson(projectTradeInsert), Charset.forName("UTF-8")));
                            AsyncTaskWithAuthorizationHeaderPost asyncTaskPost = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, getActivity(), multipartEntity, true, language);
                            asyncTaskPost.execute();


                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }else {
                        showLongToast(language.getMessageNetworkNotAvailable());
                    }
                    /*BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                    asyncTask.execute();*/
                }
            }
        });

        buttonDialogAddTradeCancel.setOnClickListener(new View.OnClickListener()

                                                      {
                                                          @Override
                                                          public void onClick(View v) {
                                                              dialog.dismiss();
                                                          }
                                                      }

        );

        spinnerProjectTradeAnsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                              @Override
                                                              public void onItemSelected(AdapterView<?> parent, View view, int position,
                                                                                         long id) {
                                                                  if (position == 0) {
                                                                      contactPerson1 = null;
                                                                  } else {
                                                                      contactPerson1 = listOfAllContactPerson.get(position - 1);
                                                                  }
                                                              }

                                                              @Override
                                                              public void onNothingSelected(AdapterView<?> parent) {

                                                              }
                                                          }

        );

        spinnerProjectTradeTrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                               @Override
                                                               public void onItemSelected(AdapterView<?> parent, View view, int position,
                                                                                          long id) {
//                                                                   if (position == 0) {
//                                                                       selectedTrade1 = null;
//                                                                   } else {
//
//                                                                   }
                                                                   selectedTrade1 = listOfTrades.get(position);
                                                               }

                                                               @Override
                                                               public void onNothingSelected(AdapterView<?> parent) {

                                                               }
                                                           }

        );

        dialog.show();
    }

    public void showCustomerDialog()
    {
        final Dialog dialog = showCustomDialog(R.layout.fragment_customer_search1, language.getLabelSearch());
        listCustomerSearchResult = (ListView)dialog.findViewById(R.id.listCustomerSearchResult);

        adapterAutocomplete = new AutoCompleteSearchAdapter(getActivity());

        textCustomerSearchCustomerNo = (DelayAutoCompleteTextView) dialog.findViewById(R.id.textCustomerSearchCustomerNo);
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

        textCustomerSearchKanr = (DelayAutoCompleteTextView)dialog.findViewById(R.id.textCustomerSearchKanr);
        textCustomerSearchKanr.setThreshold(3);
        //textCustomerSearchCustomerNo.setLoadingIndicator((android.widget.ProgressBar)rootView.findViewById(R.id.pb_loading_indicator));
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

        textCustomerSearchMatchCode = (DelayAutoCompleteTextView)dialog.findViewById(R.id.textCustomerSearchMatchCode);
        textCustomerSearchMatchCode.setThreshold(3);
        //textCustomerSearchCustomerNo.setLoadingIndicator((android.widget.ProgressBar)rootView.findViewById(R.id.pb_loading_indicator));
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

        textCustomerSearchName1 = (DelayAutoCompleteTextView)dialog.findViewById(R.id.textCustomerSearchName1);
        textCustomerSearchName1.setThreshold(3);
        //textCustomerSearchCustomerNo.setLoadingIndicator((android.widget.ProgressBar)rootView.findViewById(R.id.pb_loading_indicator));
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

        textCustomerSearchRoad = (DelayAutoCompleteTextView)dialog.findViewById(R.id.textCustomerSearchRoad);
        textCustomerSearchRoad.setThreshold(3);
        //textCustomerSearchCustomerNo.setLoadingIndicator((android.widget.ProgressBar)rootView.findViewById(R.id.pb_loading_indicator));
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

        textCustomerSearchZipCode = (DelayAutoCompleteTextView)dialog.findViewById(R.id.textCustomerSearchZipCode);
        textCustomerSearchZipCode.setThreshold(3);
        //textCustomerSearchCustomerNo.setLoadingIndicator((android.widget.ProgressBar)rootView.findViewById(R.id.pb_loading_indicator));
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

        textCustomerSearchPlace = (DelayAutoCompleteTextView)dialog.findViewById(R.id.textCustomerSearchPlace);
        textCustomerSearchPlace.setThreshold(3);
        //textCustomerSearchCustomerNo.setLoadingIndicator((android.widget.ProgressBar)rootView.findViewById(R.id.pb_loading_indicator));
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

        textCustomerSearchTel = (DelayAutoCompleteTextView)dialog.findViewById(R.id.textCustomerSearchTel);
        textCustomerSearchTel.setThreshold(3);
        //textCustomerSearchCustomerNo.setLoadingIndicator((android.widget.ProgressBar)rootView.findViewById(R.id.pb_loading_indicator));
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

        textCustomerSearchCustomerNo.setOnEditorActionListener(this);
        textCustomerSearchKanr.setOnEditorActionListener(this);
        textCustomerSearchMatchCode.setOnEditorActionListener(this);
        textCustomerSearchName1.setOnEditorActionListener(this);
        textCustomerSearchRoad.setOnEditorActionListener(this);
        textCustomerSearchZipCode.setOnEditorActionListener(this);
        textCustomerSearchPlace.setOnEditorActionListener(this);
        textCustomerSearchTel.setOnEditorActionListener(this);

        labelCustomerSearchCustomerNo = (TextView)dialog.findViewById(R.id.labelCustomerSearchCustomerNo);
        labelCustomerSearchKanr = (TextView)dialog.findViewById(R.id.labelCustomerSearchKanr);
        labelCustomerSearchMatchCode = (TextView)dialog.findViewById(R.id.labelCustomerSearchMatchCode);
        labelCustomerSearchName1 = (TextView)dialog.findViewById(R.id.labelCustomerSearchName1);
        labelCustomerSearchRoad = (TextView)dialog.findViewById(R.id.labelCustomerSearchRoad);
        labelCustomerSearchZipCode = (TextView)dialog.findViewById(R.id.labelCustomerSearchZipCode);
        labelCustomerSearchPlace = (TextView)dialog.findViewById(R.id.labelCustomerSearchPlace);
        labelCustomerSearchTel = (TextView)dialog.findViewById(R.id.labelCustomerSearchTel);

        labelCustomerSearchCustomerNo.setText(language.getLabelCustomerNo());
        labelCustomerSearchKanr.setText(language.getLabelKanr());
        labelCustomerSearchMatchCode.setText(language.getLabelMatchCode());
        labelCustomerSearchName1.setText(language.getLabelName() + " 1");
        labelCustomerSearchRoad.setText(language.getLabelRoad());
        labelCustomerSearchZipCode.setText(language.getLabelZipCode());
        labelCustomerSearchPlace.setText(language.getLabelPlace());
        labelCustomerSearchTel.setText(language.getLabelTelephone());
        listOfCustomerSearchResult = new ArrayList<>();
        adapter = new CustomerSearchResultAdapter(getActivity(), listOfCustomerSearchResult);
        listCustomerSearchResult.setAdapter(adapter);
        footerView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_list_button, null, false);
        footerButton = (Button)footerView.findViewById(R.id.buttonLoadMore);
        footerButton.setText(language.getMessageLoadMoreData());
        buttonDone = (Button)dialog.findViewById(R.id.buttonDone);
        buttonSearch = (Button)dialog.findViewById(R.id.buttonSearch);
        pageSize = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).getInt("CustomerPageSize", 10);

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.show();

        listCustomerSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                adapter.setSelectedIndex(position);
                selectedKontact = listOfCustomerSearchResult.get(position).getKontakt();
                selectedKUndenNr = listOfCustomerSearchResult.get(position).getKundenNr();
            }
        });

        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchForNewPage();
            }
        });

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(adapter.selectedIndex == -1)) {
                    getCustomerDetails(adapter.selectedIndex);
                    dialog.dismiss();
                } else {
                    showShortToast(language.getMessageSelectCustomerFirst());
                }
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchForResults();
            }
        });
    }



    private void searchForResults()
    {
        isCallservice=true;
        matecoPriceApplication.hideKeyboard(getActivity());
        listOfCustomerSearchResult.clear();
        adapter.setSelectedIndex(-1);
        adapter.notifyDataSetChanged();
        String customerNo = textCustomerSearchCustomerNo.getText().toString();
        String kaNr = textCustomerSearchKanr.getText().toString();
        String matchCode = textCustomerSearchMatchCode.getText().toString();
        String name1 = textCustomerSearchName1.getText().toString();
        String road = textCustomerSearchRoad.getText().toString();
        String zipCode = textCustomerSearchZipCode.getText().toString();
        String place = textCustomerSearchPlace.getText().toString();
        String telePhone = textCustomerSearchTel.getText().toString();

        if(customerNo.length() > 0 || kaNr.length() > 0 || matchCode.length() > 0 || name1.length() > 0 ||
                road.length() > 0 || zipCode.length() > 0 || place.length() > 0 || telePhone.length() > 0)
        {
            CustomerSearchPagingRequestModel customerSearch = new CustomerSearchPagingRequestModel();
            customerSearch.setKundenNr(customerNo);
            customerSearch.setKaNr(kaNr);
            customerSearch.setMatchCode(matchCode);
            customerSearch.setName1(name1);
            customerSearch.setStrasse(road);
            customerSearch.setPLZ(zipCode);
            customerSearch.setOrt(place);
            customerSearch.setTelefon(telePhone);
            pageNuber = 1;
            customerSearch.setPageSize(pageSize + "");
            customerSearch.setPageNumber(pageNuber+"");
            listCustomerSearchResult.removeFooterView(footerView);
            String jsonToSend = DataHelper.getGson().toJson(customerSearch);
            String base64Data = DataHelper.getToken();
            String url = "";
            try
            {
                /*url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.CUSTOMER_SEARCH_PAGING
                        + "?token=" + URLEncoder.encode(base64Data.trim(), "UTF-8")
                        + "&customersearch=" + URLEncoder.encode(jsonToSend, "UTF-8");*/
                url = DataHelper.URL_CUSTOMER_HELPER+"customersearchpaging/token=" + URLEncoder.encode(base64Data.trim(), "UTF-8")
                        + "/customersearch=" + URLEncoder.encode(jsonToSend, "UTF-8");
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
                        hideProgressDialog();
                        if(result.equals("error"))
                        {
                            showLongToast(language.getMessageError());
                        }
                        else if(result.equals(DataHelper.NetworkError)){
                            //showLongToast("Network problem while service calling before");
                            if(isCallservice) {
                                //showLongToast("service call start now");
                                isCallservice=false;
                                showProgressDialog();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        // Actions to do after 10 seconds
                                        searchForResults();
                                    }
                                }, DataHelper.NETWORK_CALL_DURATION);
                            }
                        }
                        else
                        {
                            try
                            {
                                JSONObject jsonObject = new JSONObject(result);
                                customerCount = jsonObject.getInt("CustomerCount");
                                totalPageCount = jsonObject.getInt("PageCount");
                                pageNuber = jsonObject.getInt("PageNumber");
                                pageSize = jsonObject.getInt("PageSize");
                                JSONArray resultsOfCustomers = jsonObject.getJSONArray("Result");
                                if(pageNuber < totalPageCount)
                                {
                                    listCustomerSearchResult.addFooterView(footerView);
                                }
                                if(resultsOfCustomers.length() == 0)
                                {
                                    showLongToast(language.getMessageNoResultFound());
                                }
                                else
                                {
                                    CustomerModel.extractFromJson(resultsOfCustomers.toString(), listOfCustomerSearchResult);
                                    adapter.notifyDataSetChanged();
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
                hideProgressDialog();
                listOfCustomerSearchResult.clear();
                showLongToast(language.getMessageNetworkNotAvailable());
                listOfCustomerSearchResult.addAll(db.getCustomer(customerSearch));
                adapter.notifyDataSetChanged();
//                if(listOfCustomerSearchResult.size() > 20)
//                {
//                    showShortToast(language.getMessageSearchQueryBroad());
//                }
            }
        }
        else
        {
            showLongToast(language.getMessageInputSearchParameter());
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH)
        {
            searchForResults();
            listCustomerSearchResult.requestFocus();
            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(listCustomerSearchResult.getWindowToken(), 0);
            return true;
        }
        return false;
    }

    private void getCustomerDetails(int selectedIndex)
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

                                JSONObject obj = new JSONObject(json);
                                name1 = obj.getString("Name1");
                                kontact = obj.getString("Kontakt");

                                /* commented below portion on 8th july to avoid searched customer setting as loaded customer setting */
                                /*matecoPriceApplication.saveLoadedCustomer(DataHelper.LoadedCustomer, json);
                                Log.e("customer detail", customerFullDetail.getCustomerSearchList().toString());
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
                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerCompletedOrders, listOfCompletedOrder);*/

                                String listofContact = new Gson().toJson(customerFullDetail.getCustomerContactPersonList());
                                listOfAllContactPerson.clear();
                                CustomerFullDetailModel customerFullDetail2 = new Gson().fromJson(result, CustomerFullDetailModel.class);
                                listOfAllContactPerson.addAll(customerFullDetail2.getCustomerContactPersonList());


                                showAddTradeDialog("Trade");
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
                        + "/value=" + selectedKontact;
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
            if(db.isCustomerAvailable(listOfCustomerSearchResult.get(selectedIndex).getKontakt()))
            {
                Gson gson = new Gson();
                CustomerDatabaseModel customerDatabaseModel = db.getCustomer(listOfCustomerSearchResult.get(selectedIndex).getKontakt());
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

            }
            else
            {
                showShortToast(language.getMessageCustomerNotFound());
            }
        }
    }

    private void searchForNewPage()
    {
        isCallservice=true;
        //loadingMore = true;
        matecoPriceApplication.hideKeyboard(getActivity());
        //listOfCustomerSearchResult.clear();
        adapter.setSelectedIndex(-1);
        adapter.notifyDataSetChanged();
        String customerNo = textCustomerSearchCustomerNo.getText().toString();
        String kaNr = textCustomerSearchKanr.getText().toString();
        String matchCode = textCustomerSearchMatchCode.getText().toString();
        String name1 = textCustomerSearchName1.getText().toString();
        String road = textCustomerSearchRoad.getText().toString();
        String zipCode = textCustomerSearchZipCode.getText().toString();
        String place = textCustomerSearchPlace.getText().toString();
        String telePhone = textCustomerSearchTel.getText().toString();

        if(customerNo.length() > 0 || kaNr.length() > 0 || matchCode.length() > 0 || name1.length() > 0 ||
                road.length() > 0 || zipCode.length() > 0 || place.length() > 0 || telePhone.length() > 0)
        {
            CustomerSearchPagingRequestModel customerSearch = new CustomerSearchPagingRequestModel();
            customerSearch.setKundenNr(customerNo);
            customerSearch.setKaNr(kaNr);
            customerSearch.setMatchCode(matchCode);
            customerSearch.setName1(name1);
            customerSearch.setStrasse(road);
            customerSearch.setPLZ(zipCode);
            customerSearch.setOrt(place);
            customerSearch.setTelefon(telePhone);
            pageNuber = pageNuber + 1;
            customerSearch.setPageNumber(pageNuber + "");
            customerSearch.setPageSize(pageSize + "");

            String jsonToSend = DataHelper.getGson().toJson(customerSearch);
            String base64Data = DataHelper.getToken();
            String url = "";
            try
            {
                /*url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.CUSTOMER_SEARCH_PAGING
                        + "?token=" + URLEncoder.encode(base64Data.trim(), "UTF-8")
                        + "&customersearch=" + URLEncoder.encode(jsonToSend, "UTF-8");*/
                url = DataHelper.URL_CUSTOMER_HELPER+"customersearchpaging/token=" + URLEncoder.encode(base64Data.trim(), "UTF-8")
                        + "/customersearch=" + URLEncoder.encode(jsonToSend, "UTF-8");
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
                        hideProgressDialog();
                        if(result.equals("error"))
                        {
                            showLongToast(language.getMessageError());
                        }
                        else if(result.equals(DataHelper.NetworkError)){
                            //showLongToast("Network problem while service calling before");
                            if(isCallservice) {
                                //showLongToast("service call start now");
                                isCallservice=false;
                                showProgressDialog();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        // Actions to do after 10 seconds
                                        searchForNewPage();
                                    }
                                }, DataHelper.NETWORK_CALL_DURATION);
                            }
                        }
                        else
                        {
                            try
                            {
                                JSONObject jsonObject = new JSONObject(result);
                                customerCount = jsonObject.getInt("CustomerCount");
                                totalPageCount = jsonObject.getInt("PageCount");
                                pageNuber = jsonObject.getInt("PageNumber");
                                pageSize = jsonObject.getInt("PageSize");

                                if(pageNuber == totalPageCount)
                                {
                                    listCustomerSearchResult.removeFooterView(footerView);
                                }

                                JSONArray resultsOfCustomers = jsonObject.getJSONArray("Result");
                                if(result.length() == 0)
                                {
                                    showLongToast(language.getMessageNoResultFound());
                                }
                                else
                                {
                                    CustomerModel.extractFromJson(resultsOfCustomers.toString(), listOfCustomerSearchResult);
                                    adapter.notifyDataSetChanged();
                                }
                                //loadingMore = false;
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
                hideProgressDialog();
                listOfCustomerSearchResult.clear();
                showLongToast(language.getMessageNetworkNotAvailable());
                listOfCustomerSearchResult.addAll(db.getCustomer(customerSearch));
                adapter.notifyDataSetChanged();
//                if(listOfCustomerSearchResult.size() > 20)
//                {
//                    showShortToast(language.getMessageSearchQueryBroad());
//                }
            }
        }
        else
        {
            showLongToast(language.getMessageInputSearchParameter());
        }
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
                if(customerNo.length() > 0 || kaNr.length() > 0 || matchCode.length() > 0 || name1.length() > 0 ||
                        road.length() > 0 || zipCode.length() > 0 || place.length() > 0 || telePhone.length() > 0) {
                    CustomerSearchPagingRequestModel customerSearch = new CustomerSearchPagingRequestModel();

                    customerSearch.setKundenNr("");
                    customerSearch.setKaNr("");
                    customerSearch.setMatchCode("");
                    customerSearch.setName1("");
                    customerSearch.setStrasse("");
                    customerSearch.setPLZ("");
                    customerSearch.setOrt("");
                    customerSearch.setTelefon("");

                    if(lastFocusedvalue.equalsIgnoreCase("") || TextUtils.isEmpty(lastFocusedvalue)){
                        if(!TextUtils.isEmpty(matchCode)){
                            customerSearch.setMatchCode(matchCode);
                        }
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_CUSTOMER_NO)){
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
                    customerSearch.setPageSize(GlobalClass.pageSizeForHint + "");
                    customerSearch.setPageNumber(GlobalClass.pageNuber + "");
                    //customerSearch.setMitarbeiter(loginUserNumber);
                    jsonToSend = DataHelper.getGson().toJson(customerSearch);
                }
                String base64Data = DataHelper.getToken();
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, DataHelper.URL_CUSTOMER_HELPER+"customersearchhint/token=" + URLEncoder.encode(base64Data.trim(), "UTF-8")
                        + "/customersearch=" + URLEncoder.encode(jsonToSend, "UTF-8"),
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
                        // Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                Volley.newRequestQueue(this.mContext).add(req);
            }
            catch (Exception e){
                //Toast.makeText(context,"", Toast.LENGTH_SHORT).show();
            }

        }
    }
    public void showProgressDialog(){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setTitle(language.getMessageWaitWhileLoading());
        progressDialog.setMessage(language.getMessageWaitWhileLoading());
        progressDialog.show();
    }
    public void hideProgressDialog(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
