package de.mateco.integrAMobile.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

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
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import de.mateco.integrAMobile.Helper.CustomerActivityComparable;
import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.DatePickerDialogFragment;
import de.mateco.integrAMobile.Helper.DelayAutoCompleteTextView;
import de.mateco.integrAMobile.Helper.GlobalClass;
import de.mateco.integrAMobile.Helper.LogApp;
import de.mateco.integrAMobile.Helper.TimePickerDialogFragment;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.ActivityTopicAdapter;
import de.mateco.integrAMobile.adapter.ActivityTypeAdapter;
import de.mateco.integrAMobile.adapter.CustomerActivityListAdapter;
import de.mateco.integrAMobile.adapter.CustomerContactPersonListAdapter;
import de.mateco.integrAMobile.adapter.EmployeeAdapter;
import de.mateco.integrAMobile.adapter.EventAdapter;
import de.mateco.integrAMobile.adapter.OfferAdapter;
import de.mateco.integrAMobile.adapter.ProjectAdapter;
import de.mateco.integrAMobile.adapter.ProjectSearchAdapter;
import de.mateco.integrAMobile.asyncTask.AsyncTaskWithAuthorizationHeaderPost;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.LoadedCustomerFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.ContactPersonModel;
import de.mateco.integrAMobile.model.CustomerActivityGetModel;
import de.mateco.integrAMobile.model.CustomerActivityModel;
import de.mateco.integrAMobile.model.CustomerActivityUpdateModel;
import de.mateco.integrAMobile.model.CustomerAddActivityModel;
import de.mateco.integrAMobile.model.CustomerModel;
import de.mateco.integrAMobile.model.CustomerOfferModel;
import de.mateco.integrAMobile.model.CustomerProjectModel;
import de.mateco.integrAMobile.model.HintModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LoginPersonModel;
import de.mateco.integrAMobile.model.ProjectDetailGenerallyModel;
import de.mateco.integrAMobile.model.ProjectModel;
import de.mateco.integrAMobile.model.ProjectSearchPagingRequestModel;
import de.mateco.integrAMobile.model.SimpleContactPersonAnsPartner;
import de.mateco.integrAMobile.model.SimpleEmployeeMitrabeiter;
import de.mateco.integrAMobile.model.WeeklyAgendaModel;
import de.mateco.integrAMobile.model_logonsquare.CustomerActivityEmployeeListItem;
import de.mateco.integrAMobile.model_logonsquare.CustomerActivityTopicListItem;
import de.mateco.integrAMobile.model_logonsquare.CustomerActivityTypeListItem;

public class CustomerActivityFragment extends LoadedCustomerFragment implements View.OnClickListener, TextView.OnEditorActionListener
{
    private boolean isCallservice=true;
    private ProgressDialog progressDialog;

    String FOCUSED_EMPLOYEE="employee",FOCUSED_PLACE="place",FOCUSED_MATCHCODE="matccode",FOCUSED_DESCRIPTION="description",
            FOCUSED_TYPE_OF_PROJECT="type_of_project",FOCUSED_ADDRESS="address",FOCUSED_FROM="from",FOCUSED_TO="to";
    String lastFocusedvalue="";
    int thresHodValue=3;
    AutoCompleteSearchAdapter adapterAutocomplete;
    String jsonToSend="";
    ArrayList<HintModel> hintList = new ArrayList<>();

    //public String Tag = "Customer Activity Fragment";
    private Language language;
    private View rootView;
    private MatecoPriceApplication matecoPriceApplication;
    private ListView listViewCustomerActivityList, listViewCustomerActivityEmployee, listCustomerActivityContacts;
    //, listViewCustomerActivityProject, listViewCustomerActivityOffer;
    private ArrayList<CustomerActivityModel> listOfLoadedCustomerActivity;
    private CustomerActivityListAdapter customerActivityListAdapter;
    private CustomerActivityModel selectedActivity;
    private CheckBox checkBoxCustomerActivityRealized, checkBoxCustomerActivityFixedTimes;
    private ImageButton imageButtonCustomerActivityStartDate, imageButtonStartTime, imageButtonEndTime;
    private ArrayList<ContactPersonModel> listOfSelectedContactPerson, listOfRemainingContactPerson, listOfAllContactPerson;
    private ArrayList<CustomerProjectModel> listOfAllProject; //listOfSelectedProjects, listOfRemainingProject,
    private List<CustomerActivityEmployeeListItem> listOfSelectedEmployee, listOfRemainingEmployee;
    private List<CustomerActivityEmployeeListItem> listOfAllEmployee;
    private ArrayList<CustomerOfferModel> listOfAllOffer;//listOfSelectedOffer, listOfRemainingOffer,
    private List<CustomerActivityTypeListItem> listOfActivityType;
    private List<CustomerActivityTopicListItem> listOfActivityTopic;
    private Spinner spinnerCustomerActivityActivityType, spinnerCustomerActivityActivityTopic, spinnerCustomerActivityOffer; /*,spinnerCustomerActivityProjects; */ // commented on 20161110 for change spinner to edittext for project
    /************20161110***********/
    private ImageButton buttonAddProject;
    private TextView textViewProject;
    String kontaktId="";
    String objectId="";
    Dialog projectDialog;
    LinearLayout lianearMain;
    Button btnSearch,btnRight;
    DelayAutoCompleteTextView textProjectSearchMatchCode, textProjectSearchDescription, textProjectSearchTypeOfProject,
            textProjectSearchStreetAddress, textProjectSearchPlace, textProjectSearchFrom, textProjectSearchTo, textProjectSearchEmployee;
    ListView listViewProjects;
    ProjectSearchAdapter adapterProjectSearch;
    ArrayList<ProjectModel> listofProject;
    int pageNuber2 = 1;
    int customerCount2 = 0;
    int totalPageCount2 = 1;
    int pageSize2 = 10;
    View footerView2;
    Button footerButton2;
    int customerCount = 0;
    int totalPageCount = 1;
    int pageNuber = 1;
    int pageSize = 10;
    View footerView;
    Button footerButton;
    TextView textViewProjectSearch;
    private EventAdapter adapter;
    private ArrayList<WeeklyAgendaModel> listOfWeeklyAgenda,listOfWeeklyAgenda1 = new ArrayList<>();
    /*******************************/
    private ActivityTypeAdapter activityTypeAdapter;
    private ActivityTopicAdapter activityTopicAdapter;
    private TextView labelCustomerActivityStartDate, labelCustomerActivityStartTime, labelCustomerActivityEndTime;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
    private EditText textCustomerActivityNotes;
    //textCustomerActivityContactPersonPhone, textCustomerActivityContactPersonMobile,
    private ImageButton buttonRemoveCustomerActivityEmployee, buttonAddCustomerActivityEmployee, buttonRemoveCustomerActivityContacts,
            buttonAddCustomerActivityContacts;
    //, buttonRemoveCustomerActivityProject, buttonAddCustomerActivityProject, buttonAddCustomerActivityOffer, buttonRemoveCustomerActivityOffer;
    private Menu menu;
    private boolean isInEditMode = false, isInAddMode = false;
    private EmployeeAdapter selectedEmployeeAdapter, remainingEmployeeAdapter;
    private CustomerContactPersonListAdapter selectedContactPersonAdapter, remainingContactPersonAdapter;
    private ProjectAdapter projectAdapter;//, remainingProjectAdapter;
    private OfferAdapter offerAdapter;//, remainingOfferAdapter;
    private LinearLayout linearLayoutCustomerActivityList, linearLayoutCustomerActivityListNote;
    private CustomerActivityTypeListItem selectedActivityTypeModel;
    private CustomerActivityTopicListItem selectedActivityTopicModel;
    private CustomerOfferModel selectedCustomerOfferModel;
    private CustomerProjectModel selectedCustomerProjectModel;
    private DataBaseHandler db;
    private BasicAsyncTaskGetRequest asyncTask;
    private ImageView imageSortDate, imageSortDesignation, imageSortRealized;
    private boolean isAscending = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_customer_activity, container, false);
        super.initializeFragment(rootView);

        return rootView;
    }
    @Override
    public void initializeComponents(View rootView)
    {
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        db = new DataBaseHandler(getActivity());
        imageSortDate = (ImageView)rootView.findViewById(R.id.imageSortDate);
        imageSortDesignation = (ImageView)rootView.findViewById(R.id.imageSortDesignation);
        imageSortRealized = (ImageView)rootView.findViewById(R.id.imageSortRealized);
        linearLayoutCustomerActivityList = (LinearLayout)rootView.findViewById(R.id.linearLayoutCustomerActivityList);
        linearLayoutCustomerActivityListNote = (LinearLayout)rootView.findViewById(R.id.linearLayoutCustomerActivityListNote);
        listViewCustomerActivityList = (ListView)rootView.findViewById(R.id.listViewCustomerActivityList);
        listOfLoadedCustomerActivity = new ArrayList<>();
        listViewCustomerActivityEmployee = (ListView)rootView.findViewById(R.id.listViewCustomerActivityEmployee);
        listCustomerActivityContacts = (ListView)rootView.findViewById(R.id.listCustomerActivityContacts);
        //listViewCustomerActivityProject = (ListView)rootView.findViewById(R.id.listViewCustomerActivityProject);
        //listViewCustomerActivityOffer = (ListView)rootView.findViewById(R.id.listViewCustomerActivityOffer);
        if(matecoPriceApplication.isCustomerLoaded(DataHelper.isCustomerLoaded, false));
        {
            listOfLoadedCustomerActivity = matecoPriceApplication.getLoadedCustomerActivities(DataHelper.LoadedCustomerActivity, new ArrayList<CustomerActivityModel>().toString());
        }
        customerActivityListAdapter = new CustomerActivityListAdapter(getActivity(), listOfLoadedCustomerActivity, R.layout.list_item_customer_activity);
        listViewCustomerActivityList.setAdapter(customerActivityListAdapter);
//        textCustomerActivityContactPersonPhone = (EditText)rootView.findViewById(R.id.textCustomerActivityContactPersonPhone);
//        textCustomerActivityContactPersonMobile = (EditText)rootView.findViewById(R.id.textCustomerActivityContactPersonMobile);
        spinnerCustomerActivityActivityType = (Spinner)rootView.findViewById(R.id.spinnerCustomerActivityActivityType);
        spinnerCustomerActivityActivityTopic = (Spinner)rootView.findViewById(R.id.spinnerCustomerActivityActivityTopic);

      //  spinnerCustomerActivityProjects = (Spinner)rootView.findViewById(R.id.spinnerCustomerActivityProjects);
        /*****************20161110**************************/
        buttonAddProject = (ImageButton) rootView.findViewById(R.id.buttonAddProject);
        textViewProject = (TextView) rootView.findViewById(R.id.textViewProject);
        adapter = new EventAdapter(getActivity(),listOfWeeklyAgenda1);
        /***************************************************/
        spinnerCustomerActivityOffer = (Spinner)rootView.findViewById(R.id.spinnerCustomerActivityOffer);

        listOfAllEmployee = new ArrayList<>();
        listOfSelectedEmployee = new ArrayList<>();
        listOfRemainingEmployee = new ArrayList<>();

        listOfSelectedContactPerson = new ArrayList<>();
        listOfRemainingContactPerson = new ArrayList<>();
        listOfAllContactPerson = new ArrayList<>();

//        listOfSelectedOffer = new ArrayList<>();
//        listOfRemainingOffer = new ArrayList<>();
        listOfAllOffer = new ArrayList<>();

//        listOfSelectedProjects = new ArrayList<>();
//        listOfRemainingProject = new ArrayList<>();
        listOfAllProject = new ArrayList<>();

        listOfActivityTopic = new ArrayList<>();
        listOfActivityType = new ArrayList<>();

        listOfAllContactPerson = matecoPriceApplication.getLoadedCustomerContactPersons(DataHelper.LoadedCustomerContactPerson, new ArrayList<ContactPersonModel>().toString());

        activityTypeAdapter = new ActivityTypeAdapter(getActivity(), listOfActivityType, R.layout.list_item_spinner_activity_type, language);
        activityTopicAdapter = new ActivityTopicAdapter(getActivity(), listOfActivityTopic, R.layout.list_item_spinner_activity_topic, language);



        //showProgressDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                DataBaseHandler db = new DataBaseHandler(getActivity());
                //listOfActivityType = db.getActivityTypes();
                //listOfActivityTopic = db.getActivityTopics();
                listOfActivityType = matecoPriceApplication.getCustomerActivityTypeList(DataHelper.CustomerActivityTypelist,"");
                listOfActivityTopic = matecoPriceApplication.getCustomerActivityTopicList(DataHelper.CustomerActivityTopiclist,"");
                //listOfAllEmployee = db.getEmployees();
                listOfAllEmployee = matecoPriceApplication.getCustomerActivityEmployeelist(DataHelper.CustomerActivityEmployeelist,"");
                Collections.sort(listOfAllEmployee, new Comparator<CustomerActivityEmployeeListItem>() {
                    @Override
                    public int compare(CustomerActivityEmployeeListItem s1, CustomerActivityEmployeeListItem s2) {
                        return s1.getNachname().compareToIgnoreCase(s2.getNachname());
                    }
                });
                if(isAdded()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activityTypeAdapter = new ActivityTypeAdapter(getActivity(), listOfActivityType, R.layout.list_item_spinner_activity_type, language);
                            activityTopicAdapter = new ActivityTopicAdapter(getActivity(), listOfActivityTopic, R.layout.list_item_spinner_activity_topic, language);
                            spinnerCustomerActivityActivityType.setAdapter(activityTypeAdapter);
                            spinnerCustomerActivityActivityTopic.setAdapter(activityTopicAdapter);
                            selectCustomerActivityAccordingToSelectionBundleOrDefault();
                            //hideProgressDialog();
                        }
                    });
                }
            }
        }).start();



        selectedEmployeeAdapter = new EmployeeAdapter(getActivity(), listOfSelectedEmployee, R.layout.list_item_employee);
        remainingEmployeeAdapter = new EmployeeAdapter(getActivity(), listOfRemainingEmployee, R.layout.list_item_employee);



        imageButtonCustomerActivityStartDate = (ImageButton)rootView.findViewById(R.id.imageButtonCustomerActivityStartDate);
        imageButtonCustomerActivityStartDate.setOnClickListener(this);
        labelCustomerActivityStartDate = (TextView)rootView.findViewById(R.id.labelCustomerActivityStartDate);

        imageButtonCustomerActivityStartDate = (ImageButton)rootView.findViewById(R.id.imageButtonCustomerActivityStartDate);

        labelCustomerActivityStartTime = (TextView)rootView.findViewById(R.id.labelCustomerActivityStartTime);

        imageButtonEndTime = (ImageButton)rootView.findViewById(R.id.imageButtonEndTime);
        imageButtonStartTime = (ImageButton)rootView.findViewById(R.id.imageButtonStartTime);

        labelCustomerActivityEndTime = (TextView)rootView.findViewById(R.id.labelCustomerActivityEndTime);

        checkBoxCustomerActivityRealized = (CheckBox)rootView.findViewById(R.id.checkBoxCustomerActivityRealized);
        checkBoxCustomerActivityFixedTimes = (CheckBox)rootView.findViewById(R.id.checkBoxCustomerActivityFixedTimes);

//        textCustomerActivityContactPersonPhone = (EditText)rootView.findViewById(R.id.textCustomerActivityContactPersonPhone);
//        textCustomerActivityContactPersonMobile = (EditText)rootView.findViewById(R.id.textCustomerActivityContactPersonMobile);

        buttonRemoveCustomerActivityEmployee = (ImageButton)rootView.findViewById(R.id.buttonRemoveCustomerActivityEmployee);
        buttonAddCustomerActivityEmployee = (ImageButton)rootView.findViewById(R.id.buttonAddCustomerActivityEmployee);
        buttonRemoveCustomerActivityContacts = (ImageButton)rootView.findViewById(R.id.buttonRemoveCustomerActivityContacts);
        buttonAddCustomerActivityContacts = (ImageButton)rootView.findViewById(R.id.buttonAddCustomerActivityContacts);
//        buttonRemoveCustomerActivityProject = (Button)rootView.findViewById(R.id.buttonRemoveCustomerActivityProject);
//        buttonAddCustomerActivityProject = (Button)rootView.findViewById(R.id.buttonAddCustomerActivityProject);
//        buttonAddCustomerActivityOffer = (Button)rootView.findViewById(R.id.buttonAddCustomerActivityOffer);
//        buttonRemoveCustomerActivityOffer = (Button)rootView.findViewById(R.id.buttonRemoveCustomerActivityOffer);

        selectedContactPersonAdapter = new CustomerContactPersonListAdapter(getActivity(), listOfSelectedContactPerson, R.layout.list_item_customer_contact_person);
        remainingContactPersonAdapter = new CustomerContactPersonListAdapter(getActivity(), listOfRemainingContactPerson, R.layout.list_item_customer_contact_person);

        listCustomerActivityContacts.setAdapter(selectedContactPersonAdapter);

        listViewCustomerActivityEmployee.setAdapter(selectedEmployeeAdapter);
        setListViewHeightBasedOnChildren(listViewCustomerActivityEmployee);
        listOfAllProject.addAll(matecoPriceApplication.getLoadedCustomerProjects(DataHelper.LoadedCustomerProject, new ArrayList<CustomerProjectModel>().toString()));
        projectAdapter = new ProjectAdapter(getActivity(), listOfAllProject, R.layout.list_item_project, language);
        //remainingProjectAdapter = new ProjectAdapter(getActivity(), listOfRemainingProject, R.layout.list_item_project);

        listOfAllOffer.addAll(matecoPriceApplication.getLoadedCustomerOffers(DataHelper.LoadedCustomerOffer, new ArrayList<CustomerOfferModel>().toString()));
        offerAdapter = new OfferAdapter(getActivity(), listOfAllOffer, R.layout.list_item_offer, language);
        //remainingOfferAdapter = new OfferAdapter(getActivity(), listOfRemainingOffer, R.layout.list_item_offer);

        //listViewCustomerActivityProject.setAdapter(projectAdapter);
        //listViewCustomerActivityOffer.setAdapter(offerAdapter);

        //spinnerCustomerActivityProjects.setAdapter(projectAdapter);
        spinnerCustomerActivityOffer.setAdapter(offerAdapter);

//        spinnerCustomerActivityProjects.setSelection(0);
        spinnerCustomerActivityOffer.setSelection(0);
        spinnerCustomerActivityActivityType.setSelection(0);
        spinnerCustomerActivityActivityTopic.setSelection(0);

        textCustomerActivityNotes = (EditText)rootView.findViewById(R.id.textCustomerActivityNotes);

        selectedActivityTypeModel = new CustomerActivityTypeListItem();
        selectedActivityTopicModel = new CustomerActivityTopicListItem();
        selectedCustomerOfferModel = new CustomerOfferModel();
        selectedCustomerProjectModel = new CustomerProjectModel();

        setLanguage();
        getActivity().invalidateOptionsMenu();
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelActivity());
        setHasOptionsMenu(true);
        makeEditable(false);

        super.initializeComponents(rootView);
    }

    @Override
    public void bindEvents(View rootView)
    {
//        textCustomerActivityContactPersonPhone.setOnEditorActionListener(this);
        imageSortDate.setOnClickListener(this);
        imageSortDesignation.setOnClickListener(this);
        imageSortRealized.setOnClickListener(this);
        listViewCustomerActivityList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                customerActivityListAdapter.setSelectedIndex(position);
                selectedActivity = listOfLoadedCustomerActivity.get(position);
                setCustomerActivity(selectedActivity);
            }
        });

        spinnerCustomerActivityActivityType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //selectedLegalForm = listOfLegalForm.get(i);
                    selectedActivityTypeModel = null;
                } else {
                    selectedActivityTypeModel = listOfActivityType.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerCustomerActivityActivityTopic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0)
                {
                    //selectedLegalForm = listOfLegalForm.get(i);
                    selectedActivityTopicModel = null;
                }
                else
                {
                    selectedActivityTopicModel = listOfActivityTopic.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerCustomerActivityOffer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0)
                {
                    //selectedLegalForm = listOfLegalForm.get(i);
                    selectedCustomerOfferModel = null;
                }
                else
                {
                    selectedCustomerOfferModel = listOfAllOffer.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
       /* spinnerCustomerActivityProjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                if(position == 0)
                {
                    //selectedLegalForm = listOfLegalForm.get(i);
                    selectedCustomerProjectModel = null;
                }
                else
                {
                    selectedCustomerProjectModel = listOfAllProject.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        listCustomerActivityContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //view.setSelected(true);
                selectedContactPersonAdapter.setSelectedIndex(position);
            }
        });

        listViewCustomerActivityEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //view.setSelected(true);
                selectedEmployeeAdapter.setSelectedIndex(position);
            }
        });

        checkBoxCustomerActivityRealized.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                //sadsasdasdadasad
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Date date1 = null;
                try
                {
                    date1 = format.parse(labelCustomerActivityStartDate.getText().toString());
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
                Date todayDate = new Date();
                if(date1 != null)
                {
                    if(date1.compareTo(todayDate) > 0)
                    {
                        showShortToast(language.getMessageActivityIsInFuture());
                        checkBoxCustomerActivityRealized.setChecked(false);
                    }
                }
                else
                {
                    showShortToast(language.getMessagePleaseSelectDate());
                    checkBoxCustomerActivityRealized.setChecked(false);
                }
            }
        });
        /*******************************************************/
        super.bindEvents(rootView);
    }

    /**************************20161011***********************/
    public void showProjectSearchDialog()
    {
        projectDialog = showCustomDialog(R.layout.project_search_dialog, language.getLabelSearch());

        lianearMain = (LinearLayout)projectDialog.findViewById(R.id.linearMain);
        btnSearch=(Button)projectDialog.findViewById(R.id.btnSearch);
        btnRight=(Button)projectDialog.findViewById(R.id.btnDone);

        listViewProjects = (ListView)projectDialog.findViewById(R.id.listViewProjects);
        listofProject = new ArrayList<>();
        adapterProjectSearch = new ProjectSearchAdapter(getActivity(), listofProject);
        listViewProjects.setAdapter(adapterProjectSearch);

        adapterAutocomplete = new AutoCompleteSearchAdapter(getActivity());

        textProjectSearchMatchCode = (DelayAutoCompleteTextView) projectDialog.findViewById(R.id.textProjectSearchMatchCode);textProjectSearchMatchCode.setThreshold(thresHodValue);
        textProjectSearchMatchCode.setAdapter(adapterAutocomplete);
        textProjectSearchMatchCode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textProjectSearchMatchCode.setText(hintModel.getHint());
            }
        });
        textProjectSearchMatchCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_MATCHCODE;
                }
            }
        });


        textProjectSearchDescription = (DelayAutoCompleteTextView)projectDialog.findViewById(R.id.textProjectSearchDescription);
        textProjectSearchDescription.setThreshold(thresHodValue);
        textProjectSearchDescription.setAdapter(adapterAutocomplete);
        textProjectSearchDescription.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textProjectSearchDescription.setText(hintModel.getHint());
            }
        });
        textProjectSearchDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_DESCRIPTION;
                }
            }
        });

        textProjectSearchTypeOfProject = (DelayAutoCompleteTextView)projectDialog.findViewById(R.id.textProjectSearchTypeOfProject);
        textProjectSearchTypeOfProject.setThreshold(thresHodValue);
        textProjectSearchTypeOfProject.setAdapter(adapterAutocomplete);
        textProjectSearchTypeOfProject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textProjectSearchTypeOfProject.setText(hintModel.getHint());
            }
        });
        textProjectSearchTypeOfProject.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_TYPE_OF_PROJECT;
                }
            }
        });

        textProjectSearchStreetAddress = (DelayAutoCompleteTextView)projectDialog.findViewById(R.id.textProjectSearchStreetAddress);
        textProjectSearchStreetAddress.setThreshold(thresHodValue);
        textProjectSearchStreetAddress.setAdapter(adapterAutocomplete);
        textProjectSearchStreetAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textProjectSearchStreetAddress.setText(hintModel.getHint());
            }
        });
        textProjectSearchStreetAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_ADDRESS;
                }
            }
        });

        textProjectSearchFrom = (DelayAutoCompleteTextView)projectDialog.findViewById(R.id.textProjectSearchFrom);textProjectSearchFrom.setThreshold(thresHodValue);
        textProjectSearchFrom.setAdapter(adapterAutocomplete);
        textProjectSearchFrom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textProjectSearchFrom.setText(hintModel.getHint());
            }
        });
        textProjectSearchFrom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_FROM;
                }
            }
        });

        textProjectSearchTo = (DelayAutoCompleteTextView)projectDialog.findViewById(R.id.textProjectSearchTo);
        textProjectSearchTo.setThreshold(thresHodValue);
        textProjectSearchTo.setAdapter(adapterAutocomplete);
        textProjectSearchTo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textProjectSearchTo.setText(hintModel.getHint());
            }
        });
        textProjectSearchTo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_TO;
                }
            }
        });

        textProjectSearchEmployee = (DelayAutoCompleteTextView)projectDialog.findViewById(R.id.textProjectSearchEmployee);
        textProjectSearchEmployee.setThreshold(thresHodValue);
        textProjectSearchEmployee.setAdapter(adapterAutocomplete);
        textProjectSearchEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textProjectSearchEmployee.setText(hintModel.getHint());
            }
        });
        textProjectSearchEmployee.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_EMPLOYEE;
                }
            }
        });

        textProjectSearchPlace = (DelayAutoCompleteTextView)projectDialog.findViewById(R.id.textProjectSearchPlace);
        textProjectSearchPlace.setThreshold(thresHodValue);
        textProjectSearchPlace.setAdapter(adapterAutocomplete);
        textProjectSearchPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textProjectSearchPlace.setText(hintModel.getHint());
            }
        });
        textProjectSearchPlace.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_PLACE;
                }
            }
        });

        textProjectSearchMatchCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchForResultsProject();
                return true;
            }
        });
        textProjectSearchDescription.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchForResultsProject();
                return true;
            }
        });
        textProjectSearchTypeOfProject.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchForResultsProject();
                return true;
            }
        });
        textProjectSearchStreetAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchForResultsProject();
                return true;
            }
        });
        textProjectSearchFrom.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchForResultsProject();
                return true;
            }
        });
        textProjectSearchTo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchForResultsProject();
                return true;
            }
        });
        textProjectSearchEmployee.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchForResultsProject();
                return true;
            }
        });

        textProjectSearchPlace.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchForResultsProject();
                return true;
            }
        });
        footerView2 = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_list_button, null, false);
        footerButton2 = (Button)footerView2.findViewById(R.id.buttonLoadMore);
        pageSize2 = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).getInt("CustomerPageSize", 10);
        setupLanguage();

        btnSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                searchForResultsProject();
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listofProject != null){
                    if(listofProject.size() > 0){
                        if(adapterProjectSearch.selectedIndex != -1){
                            loadProjectProjectSearch(adapterProjectSearch.selectedIndex);
                        }

                    }

                }

            }
        });
        listViewProjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //view.setSelected(true);
                adapterProjectSearch.setSelectedIndex(position);

            }
        });

        footerButton2.setText(language.getMessageLoadMoreProject());
        footerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchForNewPageProjectSearch();
            }
        });

        projectDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        projectDialog.show();
    }
    private void hideKeyboard(){
        listViewProjects.requestFocus();
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(listViewProjects.getWindowToken(), 0);
    }
    private void searchForNewPageProjectSearch()
    {
        isCallservice=true;
        //loadingMore = true;
        matecoPriceApplication.hideKeyboard(getActivity());
        //listOfCustomerSearchResult.clear();
        adapter.setSelectedIndex(-1);
        adapter.notifyDataSetChanged();
        String matchCode = textProjectSearchMatchCode.getText().toString();
        String description = textProjectSearchDescription.getText().toString();
        String typeOfProject = textProjectSearchTypeOfProject.getText().toString();
        String address = textProjectSearchStreetAddress.getText().toString();
        String from = textProjectSearchFrom.getText().toString();
        String to = textProjectSearchTo.getText().toString();
        String employee = textProjectSearchEmployee.getText().toString();
        String place = textProjectSearchPlace.getText().toString();

        if(description.length() > 0 || typeOfProject.length() > 0 || matchCode.length() > 0 || address.length() > 0 ||
                from.length() > 0 || to.length() > 0 || place.length() > 0 || employee.length() > 0)
        {
            ProjectSearchPagingRequestModel projectSearchPagingRequestModel = new ProjectSearchPagingRequestModel();
            projectSearchPagingRequestModel.setMatchcode(matchCode);
            projectSearchPagingRequestModel.setBeschreibung(description);
            projectSearchPagingRequestModel.setObjektTyp(typeOfProject);
            projectSearchPagingRequestModel.setAdresse(address);
            projectSearchPagingRequestModel.setBeginn(from);
            projectSearchPagingRequestModel.setEnde(to);
            projectSearchPagingRequestModel.setMitrabeiter(employee);
            projectSearchPagingRequestModel.setOrt(place);
            pageNuber = pageNuber + 1;
            projectSearchPagingRequestModel.setPageNumber(pageNuber + "");
            projectSearchPagingRequestModel.setPageSize(pageSize + "");

            String jsonToSend = DataHelper.getGson().toJson(projectSearchPagingRequestModel);
            String url = "";
            try
            {
                /*url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.Project_Search_Paging
                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "&projectsearchparam=" + URLEncoder.encode(jsonToSend, "UTF-8");*/
                url = DataHelper.URL_PROJECT_HELPER+"projectsearchpaging"
                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/projectsearchparam=" + URLEncoder.encode(jsonToSend, "UTF-8");
                LogApp.showLog("url", url);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            LogApp.showLog("url at customer search", url);
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
                                        searchForNewPageProjectSearch();
                                    }
                                }, DataHelper.NETWORK_CALL_DURATION);
                            }
                        }
                        else
                        {
                            try
                            {
                                JSONObject jsonObject = new JSONObject(result);
                                customerCount = jsonObject.getInt("ProjectCount");
                                totalPageCount = jsonObject.getInt("PageCount");
                                pageNuber = jsonObject.getInt("PageNumber");
                                pageSize = jsonObject.getInt("PageSize");

                                listViewProjects.removeFooterView(footerView2);
                                /*if(pageNuber == totalPageCount)
                                {
                                    //listViewProjects.removeFooterView(footerView);
                                    lianearMain.removeView(footerView);&&&&&&
                                }*/

                                if(pageNuber < totalPageCount)
                                {
                                    //listViewProjects.addFooterView(footerView);
                                    listViewProjects.addFooterView(footerView2);
                                }


                                JSONArray resultsOfProjects = jsonObject.getJSONArray("Result");
                                if(resultsOfProjects.length() == 0)
                                {
                                    showLongToast(language.getMessageNoResultFound());
                                }
                                else
                                {
                                    ProjectModel.extractFromJson(resultsOfProjects.toString(), listofProject);
                                    adapterProjectSearch.notifyDataSetChanged();
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
                showShortToast(language.getMessageNetworkNotAvailable());
            }
        }
        else
        {
            showLongToast(language.getMessageInputSearchParameter());
        }
    }
    private void searchForResultsProject()
    {
        isCallservice=true;
        hideKeyboard();
        adapterProjectSearch.setSelectedIndex(-1);
        matecoPriceApplication.hideKeyboard(getActivity());
        listofProject.clear();
        adapterProjectSearch.setSelectedIndex(-1);
        adapterProjectSearch.notifyDataSetChanged();
        String matchCode = textProjectSearchMatchCode.getText().toString();
        String description = textProjectSearchDescription.getText().toString();
        String typeOfProject = textProjectSearchTypeOfProject.getText().toString();
        String address = textProjectSearchStreetAddress.getText().toString();
        String from = textProjectSearchFrom.getText().toString();
        String to = textProjectSearchTo.getText().toString();
        String employee = textProjectSearchEmployee.getText().toString();
        String place = textProjectSearchPlace.getText().toString();

        if(description.length() > 0 || typeOfProject.length() > 0 || matchCode.length() > 0 || address.length() > 0 ||
                from.length() > 0 || to.length() > 0 || place.length() > 0 || employee.length() > 0)
        {
            ProjectSearchPagingRequestModel projectSearchPagingRequestModel = new ProjectSearchPagingRequestModel();
            projectSearchPagingRequestModel.setMatchcode(matchCode);
            projectSearchPagingRequestModel.setBeschreibung(description);
            projectSearchPagingRequestModel.setObjektTyp(typeOfProject);
            projectSearchPagingRequestModel.setAdresse(address);
            projectSearchPagingRequestModel.setBeginn(from);
            projectSearchPagingRequestModel.setEnde(to);
            projectSearchPagingRequestModel.setMitrabeiter(employee);
            projectSearchPagingRequestModel.setOrt(place);
            projectSearchPagingRequestModel.setPageSize(pageSize2 + "");
            listViewProjects.removeFooterView(footerView2);
            //listViewProjects.removeFooterView(footerView2)
            pageNuber2 = 1;
            projectSearchPagingRequestModel.setPageNumber(pageNuber2+"");
            String jsonToSend = DataHelper.getGson().toJson(projectSearchPagingRequestModel);
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
                            showShortToast(language.getMessageError());
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
                                        searchForResultsProject();
                                    }
                                }, DataHelper.NETWORK_CALL_DURATION);
                            }
                        }
                        else
                        {
                            try
                            {
                                JSONObject jsonObject = new JSONObject(result);
                                customerCount2 = jsonObject.getInt("ProjectCount");
                                totalPageCount2 = jsonObject.getInt("PageCount");
                                pageNuber2 = jsonObject.getInt("PageNumber");
                                pageSize2 = jsonObject.getInt("PageSize");
                                JSONArray resultsOfProjects = jsonObject.getJSONArray("Result");
                                if(pageNuber2 < totalPageCount2)
                                {
                                    listViewProjects.addFooterView(footerView2);
                                    //lianearMain.addView(footerView2);
                                }
                                if(resultsOfProjects.length() == 0)
                                {
                                    showLongToast(language.getMessageNoResultFound());
                                }
                                else
                                {
                                    ProjectModel.extractFromJson(resultsOfProjects.toString(), listofProject);
                                    adapterProjectSearch.notifyDataSetChanged();
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
                String url = "";
                try
                {
                    /*url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.Project_Search_Paging
                            + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                            + "&projectsearchparam=" + URLEncoder.encode(jsonToSend, "UTF-8");*/
                    url = DataHelper.URL_PROJECT_HELPER+"projectsearchpaging"
                            + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                            + "/projectsearchparam=" + URLEncoder.encode(jsonToSend, "UTF-8");
                    BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                    asyncTask.execute();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                hideProgressDialog();
                showShortToast(language.getMessageNetworkNotAvailable());
            }
        }
        else
        {
            showLongToast(language.getMessageInputSearchParameter());
        }
    }

    private void loadProjectProjectSearch(int selectedIndex)
    {
        if(DataHelper.isNetworkAvailable(getActivity()))
        {
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setTitle(language.getMessageWaitWhileLoading());
            dialog.setMessage(language.getMessageWaitWhileLoading());
            dialog.show();
            try
            {
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        if(result.equals("error"))
                        {
                            showShortToast(language.getMessageError());
                            dialog.dismiss();
                        }
                        else if(result.equals(DataHelper.NetworkError)){
                            showShortToast(language.getMessageNetworkNotAvailable());
                        }
                        else
                        {
                            try
                            {
                                JSONObject jsonObject = new JSONObject(result);
                                //ProjectDetailGenerallyModel projectDetailGenerally = new Gson().fromJson(jsonObject.getJSONObject("ProjectGenerallyList").toString(), ProjectDetailGenerallyModel.class);
                                matecoPriceApplication.saveData(DataHelper.LoadedProjectDetailGenerallyInfo, jsonObject.getJSONObject("ProjectGenerallyList").toString());
                                matecoPriceApplication.saveData(DataHelper.LoadedProjectDetailActivityInfo, jsonObject.getJSONArray("ProjectActivityList").toString());
                                matecoPriceApplication.saveData(DataHelper.LoadedProjectDetailTradesInfo, jsonObject.getJSONArray("ProjectTradesList").toString());
                                dialog.dismiss();
                                projectDialog.dismiss();
                                ProjectDetailGenerallyModel projectGenerallyDetail;
                                projectGenerallyDetail = matecoPriceApplication.getProjectDetailGenerallyModel(DataHelper.LoadedProjectDetailGenerallyInfo, new ProjectDetailGenerallyModel().toString());
                                textViewProject.setText(projectGenerallyDetail.getBeschreibung());
                                objectId=projectGenerallyDetail.getProjekt();
                                if(selectedCustomerProjectModel == null)
                                    selectedCustomerProjectModel = new CustomerProjectModel();
                                selectedCustomerProjectModel.setProjectId(projectGenerallyDetail.getProjekt());
                                selectedCustomerProjectModel.setProjectName(projectGenerallyDetail.getBeschreibung());
                                //getActivity().getSupportFragmentManager().executePendingTransactions();
                            }
                            catch (JSONException ex)
                            {
                                ex.printStackTrace();
                                showShortToast(language.getMessageErrorAtParsing());
                                dialog.dismiss();
                            }
                        }
                    }
                };
                String url = DataHelper.URL_AGENDA_HELPER + "agendaprojectlistshow"//DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.AGENDA_PROJECT_LIST_SHOW
                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/fieldname=" + "Projekt"
                        + "/value=" + listofProject.get(selectedIndex).getProjekt();
                //Projekt
//                String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.PROJECT_LIST_SHOW
//                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
//                        + "&objekt=" + listofProject.get(selectedIndex).getProjekt();
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), false);
                asyncTask.execute();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
                dialog.dismiss();
            }
        }
        else
        {
            showShortToast(language.getMessageNetworkNotAvailable());
        }
    }

    private void setupLanguage()
    {
        TextView labelProjectSearchEmployee, labelProjectSearchTo, labelProjectSearchFrom, labelProjectSearchPlace, labelProjectSearchStreetAddress,
                labelProjectSearchTypeOfProject, labelProjectSearchDescription, labelProjectSearchMatchCode;

        labelProjectSearchEmployee = (TextView)projectDialog.findViewById(R.id.labelProjectSearchEmployee);
        labelProjectSearchTo = (TextView)projectDialog.findViewById(R.id.labelProjectSearchTo);
        labelProjectSearchFrom = (TextView)projectDialog.findViewById(R.id.labelProjectSearchFrom);
        labelProjectSearchPlace = (TextView)projectDialog.findViewById(R.id.labelProjectSearchPlace);
        labelProjectSearchStreetAddress = (TextView)projectDialog.findViewById(R.id.labelProjectSearchStreetAddress);
        labelProjectSearchTypeOfProject = (TextView)projectDialog.findViewById(R.id.labelProjectSearchTypeOfProject);
        labelProjectSearchDescription = (TextView)projectDialog.findViewById(R.id.labelProjectSearchDescription);
        labelProjectSearchMatchCode = (TextView)projectDialog.findViewById(R.id.labelProjectSearchMatchCode);

        labelProjectSearchMatchCode.setText(language.getLabelMatchCode());
        labelProjectSearchDescription.setText(language.getLabelDescription());
        labelProjectSearchTypeOfProject.setText(language.getLabelTypeOfProjct());
        labelProjectSearchStreetAddress.setText(language.getLabelAddress());
        labelProjectSearchPlace.setText(language.getLabelPlace());
        labelProjectSearchFrom.setText(language.getLabelFrom());
        labelProjectSearchTo.setText(language.getLabelTo());
        labelProjectSearchEmployee.setText(language.getLabelEmployee());

//        textProjectSearchMatchCode.setHint(language.getLabelMatchCode());
//        textProjectSearchDescription.setHint(language.getLabelDescription());
//        textProjectSearchTypeOfProject.setHint(language.getLabelTypeOfProjct());
//        textProjectSearchStreetAddress.setHint(language.getLabelAddress());
//        textProjectSearchFrom.setHint(language.getLabelFrom());
//        textProjectSearchTo.setHint(language.getLabelTo());
//        textProjectSearchEmployee.setHint(language.getLabelEmployee());
//        textProjectSearchPlace.setHint(language.getLabelPlace());
    }
    /*********************************************************/

    private void selectCustomerActivityAccordingToSelectionBundleOrDefault()
    {
        if(listOfLoadedCustomerActivity.size() > 0)
        {
            if(getArguments() != null && getArguments().containsKey("WeeklyAgendaSelectedModel"))
            {
                String jsonData = getArguments().getString("WeeklyAgendaSelectedModel", "");
                if(!jsonData.equals(""))
                {
                    WeeklyAgendaModel agendaModel = new Gson().fromJson(jsonData, WeeklyAgendaModel.class);
                    for(int i = 0; i < listOfLoadedCustomerActivity.size(); i++)
                    {
                        if(listOfLoadedCustomerActivity.get(i).getAktivitaet().equals(agendaModel.getAktivitaet()))
                        {
                            listViewCustomerActivityList.setSelection(i);
                            selectedActivity = listOfLoadedCustomerActivity.get(i);
                            setCustomerActivity(selectedActivity);
                            customerActivityListAdapter.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
            else if(getArguments() != null && getArguments().containsKey("VisitPlanDailyAgenda"))
            {
                String activityet = getArguments().getString("VisitPlanDailyAgenda", "");

                //String kontakt = matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();
                if(activityet != null && !activityet.equalsIgnoreCase("")){
                    for(int i = 0; i < listOfLoadedCustomerActivity.size(); i++)
                    {
                        if(listOfLoadedCustomerActivity.get(i).getAktivitaet().equals(activityet))
                        {
                            listViewCustomerActivityList.setSelection(i);
                            selectedActivity = listOfLoadedCustomerActivity.get(i);
                            setCustomerActivity(selectedActivity);
                            customerActivityListAdapter.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
            else {
                listViewCustomerActivityList.setSelection(0);
                selectedActivity = listOfLoadedCustomerActivity.get(0);
                setCustomerActivity(selectedActivity);
                customerActivityListAdapter.setSelectedIndex(0);
            }
        }
    }

    private void makeEditable(boolean editable)
    {
        listViewCustomerActivityList.setEnabled(!editable);
        spinnerCustomerActivityActivityType.setEnabled(editable);
        spinnerCustomerActivityActivityTopic.setEnabled(editable);
        checkBoxCustomerActivityRealized.setEnabled(editable);
        checkBoxCustomerActivityFixedTimes.setEnabled(editable);
        /*********20161110**********/
        buttonAddProject.setEnabled(editable);
        /***************************/

//        textCustomerActivityContactPersonMobile.setFocusable(editable);
//        textCustomerActivityContactPersonMobile.setFocusableInTouchMode(editable);
//
//        textCustomerActivityContactPersonPhone.setFocusable(editable);
//        textCustomerActivityContactPersonPhone.setFocusableInTouchMode(editable);

        textCustomerActivityNotes.setFocusable(editable);
        textCustomerActivityNotes.setFocusableInTouchMode(editable);
        if(editable)
        {
            imageButtonCustomerActivityStartDate.setOnClickListener(this);
            labelCustomerActivityStartDate.setOnClickListener(this);
            labelCustomerActivityStartTime.setOnClickListener(this);
            imageButtonEndTime.setOnClickListener(this);
            imageButtonStartTime.setOnClickListener(this);
            labelCustomerActivityEndTime.setOnClickListener(this);
            buttonRemoveCustomerActivityEmployee.setOnClickListener(this);
            buttonAddCustomerActivityEmployee.setOnClickListener(this);
            buttonRemoveCustomerActivityContacts.setOnClickListener(this);
            buttonAddCustomerActivityContacts.setOnClickListener(this);
            /*********20161011***********/
            buttonAddProject.setOnClickListener(this);
            /****************************/
//            buttonRemoveCustomerActivityProject.setOnClickListener(this);
//            buttonAddCustomerActivityProject.setOnClickListener(this);
//            buttonRemoveCustomerActivityOffer.setOnClickListener(this);
//            buttonAddCustomerActivityOffer.setOnClickListener(this);
        }
        else
        {
            imageButtonCustomerActivityStartDate.setOnClickListener(null);
            labelCustomerActivityStartDate.setOnClickListener(null);
            labelCustomerActivityStartTime.setOnClickListener(null);
            imageButtonEndTime.setOnClickListener(null);
            imageButtonStartTime.setOnClickListener(null);
            labelCustomerActivityEndTime.setOnClickListener(null);
            buttonRemoveCustomerActivityEmployee.setOnClickListener(null);
            buttonAddCustomerActivityEmployee.setOnClickListener(null);
            buttonRemoveCustomerActivityContacts.setOnClickListener(null);
            buttonAddCustomerActivityContacts.setOnClickListener(null);
            /*********20161011***********/
            buttonAddProject.setOnClickListener(null);
            /****************************/
//            buttonRemoveCustomerActivityProject.setOnClickListener(null);
//            buttonAddCustomerActivityProject.setOnClickListener(null);
//            buttonRemoveCustomerActivityOffer.setOnClickListener(null);
//            buttonAddCustomerActivityOffer.setOnClickListener(null);
        }
        //listViewCustomerActivityOffer.setEnabled(editable);
        spinnerCustomerActivityOffer.setEnabled(editable);
        listCustomerActivityContacts.setEnabled(editable);
        //listViewCustomerActivityProject.setEnabled(editable);
        //spinnerCustomerActivityProjects.setEnabled(editable);
        listViewCustomerActivityEmployee.setEnabled(editable);
        if(editable)
        {
            linearLayoutCustomerActivityList.setVisibility(View.GONE);
            linearLayoutCustomerActivityListNote.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        else
        {
            linearLayoutCustomerActivityList.setVisibility(View.VISIBLE);
            linearLayoutCustomerActivityListNote.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        isInEditMode = editable;
    }

    private void setCustomerActivity(CustomerActivityModel activity)
    {
        clearAllValues();
        if(activity != null)
        {
            boolean isActivityTypeSelected = false;
            for(int i = 0; i < listOfActivityType.size(); i++)
            {
                if(activity.getAkttypID().equals(listOfActivityType.get(i).getAkttyp()))
                {
                    spinnerCustomerActivityActivityType.setSelection(i + 1);
                    selectedActivityTypeModel = listOfActivityType.get(i);
                    isActivityTypeSelected = true;
                    //selectedLegalForm = listOfLegalForm.get(i);
                    break;
                }
            }
            if(!isActivityTypeSelected)
            {
                spinnerCustomerActivityActivityType.setSelection(0);
            }
            boolean isActivityTopicSelected = false;
            for(int i = 0; i < listOfActivityTopic.size(); i++)
            {
                if(activity.getAktthemaID().equals(listOfActivityTopic.get(i).getAktthema()))
                {
                    spinnerCustomerActivityActivityTopic.setSelection(i + 1);
                    selectedActivityTopicModel = listOfActivityTopic.get(i);
                    isActivityTopicSelected = true;
                    break;
                }
            }
            if(!isActivityTopicSelected)
            {
                spinnerCustomerActivityActivityTopic.setSelection(0);
            }
            boolean isActivityProjectSelected = false;
            if(!activity.getProjektID().equals("") || !activity.getProjektID().equals("0"))
                {
                    //spinnerCustomerActivityProjects.setSelection(i + 1);
                    //selectedCustomerProjectModel = listOfAllProject.get(i);
                    if(selectedCustomerProjectModel == null)
                        selectedCustomerProjectModel = new CustomerProjectModel();
                    selectedCustomerProjectModel.setProjectId(activity.getProjektID());
                    selectedCustomerProjectModel.setProjectName(activity.getProjekt());
                    textViewProject.setText(selectedCustomerProjectModel.getProjectName());
                    isActivityProjectSelected = true;
               //     break;
                }
           /* }*/
            if(!isActivityProjectSelected)
            {
              //  spinnerCustomerActivityProjects.setSelection(0);
            }
            boolean isActivityOfferSelected = false;
            for(int i = 0; i < listOfAllOffer.size(); i++)
            {
                if(activity.getAngebot().equals(listOfAllOffer.get(i).getOfferno()))
                {
                    spinnerCustomerActivityOffer.setSelection(i + 1);
                    selectedCustomerOfferModel = listOfAllOffer.get(i);
                    isActivityOfferSelected = true;
                    //selectedLegalForm = listOfLegalForm.get(i);
                    break;
                }
            }
            if(!isActivityOfferSelected)
            {
                spinnerCustomerActivityOffer.setSelection(0);
            }
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            try
            {
                labelCustomerActivityStartDate.setText(DataHelper.formatDate(format.parse(activity.getStartdatum())));
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }

            for(int i = 0; i < activity.getListOfContactPersonId().size(); i++)
            {
                for(int j = 0; j < listOfAllContactPerson.size(); j++)
                {
                    if(activity.getListOfContactPersonId().get(i).getAnsPartner().equals(listOfAllContactPerson.get(j).getAnspartner()))
                    {
                        listOfSelectedContactPerson.add(listOfAllContactPerson.get(j));
                        break;
                    }
                }
            }
            setListViewHeightBasedOnChildren(listCustomerActivityContacts);
            for(int i = 0; i < activity.getListOfEmployeeId().size(); i++)
            {
                for(int j = 0; j < listOfAllEmployee.size(); j++)
                {
                    if(activity.getListOfEmployeeId().get(i).getMitarbeiter().equals(listOfAllEmployee.get(j).getMitarbeiter()))
                    {
                        listOfSelectedEmployee.add(listOfAllEmployee.get(j));
                        break;
                    }
                }
            }
            setListViewHeightBasedOnChildren(listViewCustomerActivityEmployee);
            if(!activity.getStartzeit().equals("00:00"))
                labelCustomerActivityStartTime.setText(DataHelper.formatTime(activity.getStartzeit()));
            if(!activity.getEndzeit().equals("00:00"))
                labelCustomerActivityEndTime.setText(DataHelper.formatTime(activity.getEndzeit()));

            textCustomerActivityNotes.setText(activity.getNotiz());
            if(Boolean.parseBoolean(activity.getRealisiert()))
            {
                checkBoxCustomerActivityRealized.setChecked(true);
            }
            if(activity.getFest().length() > 0 && Boolean.parseBoolean(activity.getFest()))
            {
                checkBoxCustomerActivityFixedTimes.setChecked(true);
            }

            //SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            try
            {
                labelCustomerActivityStartDate.setText(DataHelper.formatDate(format.parse(activity.getStartdatum())));
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void setLanguage()
    {
        TextView labelCustomerActivityDate, labelCustomerActivityDesignation, labelCustomerActivityRealized,
                labelCustomerActivityMemo, labelCustomerActivityDetailActivity, labelCustomerActivityDetailTopic,
                labelCustomerActivityDetailStartDate, labelCustomerActivityDetailStartTime, labelCustomerActivityDetailEndTime,
                labelCustomerActivityDetailOffer, labelCustomerActivityDetailProject, labelCustomerActivityDetailRealized,
                labelCustomerActivityDetailFixedTimes, labelCustomerActivityDetailContacts, labelCustomerActivityDetailEmployee;
        //, labelCustomerActivityDetailAnsprechPhone, labelCustomerActivityDetailAnsprechMobile

        labelCustomerActivityDate = (TextView)rootView.findViewById(R.id.labelCustomerActivityDate);
        labelCustomerActivityDesignation = (TextView)rootView.findViewById(R.id.labelCustomerActivityDesignation);
        labelCustomerActivityRealized = (TextView)rootView.findViewById(R.id.labelCustomerActivityRealized);
        labelCustomerActivityMemo = (TextView)rootView.findViewById(R.id.labelCustomerActivityMemo);
        labelCustomerActivityDetailActivity = (TextView)rootView.findViewById(R.id.labelCustomerActivityDetailActivity);
        labelCustomerActivityDetailTopic = (TextView)rootView.findViewById(R.id.labelCustomerActivityDetailTopic);
        labelCustomerActivityDetailStartDate = (TextView)rootView.findViewById(R.id.labelCustomerActivityDetailStartDate);
        labelCustomerActivityDetailStartTime = (TextView)rootView.findViewById(R.id.labelCustomerActivityDetailStartTime);
        labelCustomerActivityDetailEndTime = (TextView)rootView.findViewById(R.id.labelCustomerActivityDetailEndTime);
        labelCustomerActivityDetailOffer = (TextView)rootView.findViewById(R.id.labelCustomerActivityDetailOffer);
        labelCustomerActivityDetailProject = (TextView)rootView.findViewById(R.id.labelCustomerActivityDetailProject);
        labelCustomerActivityDetailRealized = (TextView)rootView.findViewById(R.id.labelCustomerActivityDetailRealized);
        labelCustomerActivityDetailFixedTimes = (TextView)rootView.findViewById(R.id.labelCustomerActivityDetailFixedTimes);
        labelCustomerActivityDetailContacts = (TextView)rootView.findViewById(R.id.labelCustomerActivityDetailContacts);
//        labelCustomerActivityDetailAnsprechPhone = (TextView)rootView.findViewById(R.id.labelCustomerActivityDetailAnsprechPhone);
//        labelCustomerActivityDetailAnsprechMobile = (TextView)rootView.findViewById(R.id.labelCustomerActivityDetailAnsprechMobile);
        labelCustomerActivityDetailEmployee = (TextView)rootView.findViewById(R.id.labelCustomerActivityDetailEmployee);
        labelCustomerActivityDate.setOnClickListener(this);
        labelCustomerActivityDesignation.setOnClickListener(this);
        labelCustomerActivityRealized.setOnClickListener(this);
        labelCustomerActivityDate.setText(language.getLabelDate());
        labelCustomerActivityDesignation.setText(language.getLabelDesignation());
        labelCustomerActivityRealized.setText(language.getLabelRealized());
        labelCustomerActivityDetailActivity.setText(language.getLabelActivity());
        labelCustomerActivityDetailTopic.setText(language.getLabelTopic());
        labelCustomerActivityDetailStartDate.setText(language.getLabelDate());
        labelCustomerActivityDetailStartTime.setText(language.getLabelStartTime());
        labelCustomerActivityDetailEndTime.setText(language.getLabelEndTime());
        labelCustomerActivityDetailOffer.setText(language.getLabelOffer());
        labelCustomerActivityDetailProject.setText(language.getLabelProject());
        labelCustomerActivityDetailRealized.setText(language.getLabelRealized());
        labelCustomerActivityDetailFixedTimes.setText(language.getLabelFixedTimes());
        labelCustomerActivityDetailContacts.setText(language.getLabelContacts());
//        labelCustomerActivityDetailAnsprechPhone.setText(language.getLabelPhone());
//        labelCustomerActivityDetailAnsprechMobile.setText(language.getLabelMobile());
        labelCustomerActivityDetailEmployee.setText(language.getLabelEmployee());
        labelCustomerActivityMemo.setText(language.getLabelMemo());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);
        menu.findItem(R.id.actionWrong).setVisible(false);
        menu.findItem(R.id.actionRight).setVisible(false);
        menu.findItem(R.id.actionSearch).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);
        this.menu = menu;

        if(checkAllRealized(listOfLoadedCustomerActivity))
        {
            clearAllValues();
            menu.findItem(R.id.actionAdd).setVisible(false);
            menu.findItem(R.id.actionEdit).setVisible(false);
            menu.findItem(R.id.actionRight).setVisible(true);
            menu.findItem(R.id.actionWrong).setVisible(true);
            addNewActivityFormRedesign(true);
            makeEditable(true);
            showShortToast(language.getMessageActivityYouMustAddNewActivity());
        }
        //menu.clear();
       // menu.findItem(R.id.actionSettings).setVisible(true);
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
//                FragmentTransaction ft = getFragmentManager().beginTransaction();//getFragmentManager().beginTransaction();
//                ft.replace(R.id.content_frame, new SettingFragment(), "Setting");
//                ft.commit();
                return true;
            case R.id.actionBack:
                if(checkAllRealized(listOfLoadedCustomerActivity))
                {
                    clearAllValues();
                    menu.findItem(R.id.actionAdd).setVisible(false);
                    menu.findItem(R.id.actionEdit).setVisible(false);
                    menu.findItem(R.id.actionRight).setVisible(true);
                    menu.findItem(R.id.actionWrong).setVisible(true);
                    menu.findItem(R.id.actionBack).setVisible(false);
                    addNewActivityFormRedesign(true);
                    makeEditable(true);
                    showShortToast(language.getMessageActivityYouMustAddNewActivity());
                }
                else
                {
                    if(isInEditMode || isInAddMode)
                    {
                        addNewActivityFormRedesign(false);
                        makeEditable(false);
                        menu.findItem(R.id.actionEdit).setVisible(true);
                        menu.findItem(R.id.actionRight).setVisible(false);
                        menu.findItem(R.id.actionWrong).setVisible(false);
                        menu.findItem(R.id.actionAdd).setVisible(true);
                        menu.findItem(R.id.actionBack).setVisible(true);
                    }
                    else if (getFragmentManager().getBackStackEntryCount() == 0)
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
                }
                return true;
            case R.id.actionForward:
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                transaction.replace(R.id.content_frame, new CustomerOfferOrderFragment());
//                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                //transaction.addToBackStack(SettingFragment.Tag);
//                //transaction.addToBackStack("Customer Data");
//                transaction.commit();
                return true;
            case R.id.actionEdit:
                if(listOfLoadedCustomerActivity.size() > 0)
                {
                    makeEditable(true);
                    menu.findItem(R.id.actionEdit).setVisible(false);
                    menu.findItem(R.id.actionRight).setVisible(true);
                    menu.findItem(R.id.actionWrong).setVisible(true);
                    menu.findItem(R.id.actionAdd).setVisible(false);
                    menu.findItem(R.id.actionBack).setVisible(false);
                    isInEditMode = true;
                }
                else
                {
                    showShortToast(language.getMessageNoActivityFound());
                }
                return true;
            case R.id.actionAdd:
                //showShortToast("Function will be implemented soon");
                clearAllValues();
                menu.findItem(R.id.actionAdd).setVisible(false);
                menu.findItem(R.id.actionEdit).setVisible(false);
                menu.findItem(R.id.actionRight).setVisible(true);
                menu.findItem(R.id.actionWrong).setVisible(true);
                menu.findItem(R.id.actionBack).setVisible(false);
                addNewActivityFormRedesign(true);
                makeEditable(true);
                return true;
            case R.id.actionWrong:
                if(isInAddMode)
                {
                    if(checkAllRealized(listOfLoadedCustomerActivity))
                    {
                        DialogInterface.OnClickListener positiveCallback = new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.cancel();
                            }
                        };
                        String title = language.getLabelAlert();
                        String message = language.getMessageActivityYouMustAddNewActivity();
                        AlertDialog alert = showMessage(title, message, language.getLabelYes(), positiveCallback);
                        alert.show();
                    }
                    else
                    {
                        DialogInterface.OnClickListener positiveCallback = new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                addNewActivityFormRedesign(false);
                                setCustomerActivity(selectedActivity);
                                menu.findItem(R.id.actionEdit).setVisible(true);
                                menu.findItem(R.id.actionRight).setVisible(false);
                                menu.findItem(R.id.actionWrong).setVisible(false);
                                menu.findItem(R.id.actionAdd).setVisible(true);
                                menu.findItem(R.id.actionBack).setVisible(true);
                                makeEditable(false);
                                isInEditMode = false;
                                isInAddMode = false;
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
                        String message = language.getMessageCancelAddingActivity();
                        AlertDialog alert = showAlert(title, message, language.getLabelYes(), language.getLabelNo(),
                                positiveCallback, negativeCallback);
                        alert.show();
                    }
                }
                else if(isInEditMode)
                {
                    if(checkIfTextChanged(selectedActivity))
                    {
                        DialogInterface.OnClickListener positiveCallback = new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                addNewActivityFormRedesign(false);
                                setCustomerActivity(selectedActivity);
                                menu.findItem(R.id.actionEdit).setVisible(true);
                                menu.findItem(R.id.actionRight).setVisible(false);
                                menu.findItem(R.id.actionWrong).setVisible(false);
                                menu.findItem(R.id.actionAdd).setVisible(true);
                                menu.findItem(R.id.actionBack).setVisible(true);
                                makeEditable(false);
                                isInEditMode = false;
                                isInAddMode = false;
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
                        String message = language.getMessageDelete();
                        AlertDialog alert = showAlert(title, message, language.getLabelYes(), language.getLabelNo(),
                                positiveCallback, negativeCallback);
                        alert.show();
                    }
                    else
                    {
                        addNewActivityFormRedesign(false);
                        setCustomerActivity(selectedActivity);
                        menu.findItem(R.id.actionEdit).setVisible(true);
                        menu.findItem(R.id.actionRight).setVisible(false);
                        menu.findItem(R.id.actionWrong).setVisible(false);
                        menu.findItem(R.id.actionAdd).setVisible(true);
                        menu.findItem(R.id.actionBack).setVisible(true);
                        makeEditable(false);
                        isInEditMode = false;
                        isInAddMode = false;
                        return true;
                    }
                }
                return true;
            case R.id.actionRight:
                if(isInAddMode)
                {
                    addNewCustomerActivity();
                }
                else
                {
                    updateCustomerActivityInfo();
                    //showShortToast("Function will be implemented soon");
                }
                //showShortToast("Function will be implemented soon");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    private void addNewActivityFormRedesign(boolean editable)
    {
        checkBoxCustomerActivityRealized.setEnabled(true);
        if(editable)
        {
            linearLayoutCustomerActivityList.setVisibility(View.GONE);
            linearLayoutCustomerActivityListNote.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        else
        {
            linearLayoutCustomerActivityList.setVisibility(View.VISIBLE);
            linearLayoutCustomerActivityListNote.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        String loginUserNumber = matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber();
        if(editable)
        {
            for(int i = 0; i < listOfAllEmployee.size(); i++)
            {
                if(listOfAllEmployee.get(i).getMitarbeiter().equals(loginUserNumber))
                {
                    listOfSelectedEmployee.add(listOfAllEmployee.get(i));
                    break;
                }
            }
        }
        isInAddMode = editable;
    }

    private void addNewCustomerActivity()
    {
        String activityType = null;
        if(selectedActivityTypeModel != null)
        {
            activityType = selectedActivityTypeModel.getAkttyp();
        }
        String activityTopic = "";
        if(selectedActivityTopicModel != null)
        {
            activityTopic = selectedActivityTopicModel.getAktthema();
        }
        String offer = "";
        if(selectedCustomerOfferModel != null)
        {
            offer = selectedCustomerOfferModel.getOfferno();
        }
        String project = "";
        if(selectedCustomerProjectModel != null)
        {
            project = selectedCustomerProjectModel.getProjectId();
        }
        String date = DataHelper.formatDateToOriginal(labelCustomerActivityStartDate.getText().toString());
        String startTime =  DataHelper.formatTimeToOriginal(labelCustomerActivityStartDate.getText().toString(), labelCustomerActivityStartTime.getText().toString());
        String endTime = DataHelper.formatTimeToOriginal(labelCustomerActivityStartDate.getText().toString(), labelCustomerActivityEndTime.getText().toString());
        String realized = "";
        String fest = "";

        if(checkBoxCustomerActivityFixedTimes.isChecked())
        {
            fest = "1";
        }
        else
        {
            fest = "0";
        }

        if(checkBoxCustomerActivityRealized.isChecked())
        {
            realized = "1";
        }
        else
        {
            realized = "0";
        }
//        String mobile = textCustomerActivityContactPersonMobile.getText().toString();
//        String phone = textCustomerActivityContactPersonPhone.getText().toString();
        String note = textCustomerActivityNotes.getText().toString();
        String kontakt = matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();

        CustomerAddActivityModel customerAddActivityModel = new CustomerAddActivityModel();
        customerAddActivityModel.setAkttyp(activityType);
        customerAddActivityModel.setAktthema(activityTopic);
        customerAddActivityModel.setNotiz(note);
        customerAddActivityModel.setObjekt(project);
        customerAddActivityModel.setAngebot(offer);
        customerAddActivityModel.setStartdatum(date);
        customerAddActivityModel.setStartzeit(startTime);
        customerAddActivityModel.setEndzeit(endTime);
        customerAddActivityModel.setRealisiert(realized);
        String loginUserId = matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserId();
        customerAddActivityModel.setAenderungMitarbeiter(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber());
        customerAddActivityModel.setUserID(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserId());
        customerAddActivityModel.setFest(fest);
        customerAddActivityModel.setKontakt(kontakt);
        customerAddActivityModel.setGeloescht("0");

        ArrayList<String> listOfSelectedEmployeeId = new ArrayList<>();
        for(int i = 0; i < listOfSelectedEmployee.size(); i++)
        {
            listOfSelectedEmployeeId.add(listOfSelectedEmployee.get(i).getMitarbeiter());
        }
        customerAddActivityModel.setMitarbeiter(listOfSelectedEmployeeId);

        ArrayList<String> listOfSelectedContactPersonId = new ArrayList<>();
        for(int i = 0; i < listOfSelectedContactPerson.size(); i++)
        {
            listOfSelectedContactPersonId.add(listOfSelectedContactPerson.get(i).getAnspartner());
        }
        customerAddActivityModel.setAnspartner(listOfSelectedContactPersonId);

//        customerAddActivityModel.setAkttyp(activityType);
//        customerAddActivityModel.setAkttyp(activityType);
//        customerAddActivityModel.setAkttyp(activityType);
//        customerAddActivityModel.setAkttyp(activityType);

        String json = new Gson().toJson(customerAddActivityModel);

//        else if(date.length() > 0)
//        {
//            //showShortToast("Please select date");
//            showShortToast(language.getLabelDate());
//        }
        labelCustomerActivityStartDate.setError(null);
        if(selectedActivityTypeModel == null)
        {
            //showShortToast("Please selected acitivity type");
            showShortToast(language.getMessagePleaseSelectActivityType());
        }
        else if(date.equals(""))
        {
            labelCustomerActivityStartDate.setError(language.getLabelRequired());
            labelCustomerActivityStartDate.requestFocus();
            showShortToast(language.getMessagePleaseSelectDate());
        }
        else if(!DataHelper.yearLessThen2079(date) && !DataHelper.yearLessThen2079(startTime) && !DataHelper.yearLessThen2079(endTime))
        {
            showShortToast(language.getMessageDateMustBeLessThen2079());
        }
        else if(DataHelper.isNetworkAvailable(getActivity()))
        {
            AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult()
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
                            matecoPriceApplication.saveData(DataHelper.AgendaDate, "");
                            listOfLoadedCustomerActivity.clear();
                            //ArrayList<CustomerActivityGetModel> listOfCustomerActivityDetails = new ArrayList<>();
                            CustomerActivityGetModel customerActivityGetModel = new CustomerActivityGetModel();
                            //CustomerActivityGetModel.extractFromJson(result, listOfCustomerActivityDetails);
                            customerActivityGetModel = new Gson().fromJson(result, CustomerActivityGetModel.class);
                            String listOfActivity = new Gson().toJson(customerActivityGetModel.getListOfActivities());
                            matecoPriceApplication.saveData(DataHelper.LoadedCustomerActivity, listOfActivity);
                            String listOfProjects = new Gson().toJson(customerActivityGetModel.getListOfProject());
                            matecoPriceApplication.saveData(DataHelper.LoadedCustomerProject, listOfProjects);
                            String listOfOffer = new Gson().toJson(customerActivityGetModel.getListOfOffers());
                            matecoPriceApplication.saveData(DataHelper.LoadedCustomerOffer, listOfOffer);

                            String kontakt = matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();
                            if(db.isCustomerAvailable(kontakt))
                            {
                                db.updateCustomerActivityProjectOffer(kontakt, listOfLoadedCustomerActivity, listOfAllProject, listOfAllOffer);
                            }

                            menu.findItem(R.id.actionAdd).setVisible(true);
                            menu.findItem(R.id.actionEdit).setVisible(true);
                            menu.findItem(R.id.actionRight).setVisible(false);
                            menu.findItem(R.id.actionWrong).setVisible(false);
                            menu.findItem(R.id.actionBack).setVisible(true);
                            clearAllValues();
                            addNewActivityFormRedesign(false);
                            makeEditable(false);
                            listOfLoadedCustomerActivity.addAll(matecoPriceApplication.getLoadedCustomerActivities(DataHelper.LoadedCustomerActivity, new ArrayList<CustomerActivityModel>().toString()));
                            customerActivityListAdapter.notifyDataSetChanged();
                            if(listOfLoadedCustomerActivity.size() > 0)
                            {
                                setCustomerActivity(listOfLoadedCustomerActivity.get(0));
                                customerActivityListAdapter.setSelectedIndex(0);
                                listViewCustomerActivityList.setSelection(0);
                                selectedActivity = listOfLoadedCustomerActivity.get(0);
                            }
                            if(checkAllRealized(listOfLoadedCustomerActivity))
                            {
                                clearAllValues();
                                menu.findItem(R.id.actionAdd).setVisible(false);
                                menu.findItem(R.id.actionEdit).setVisible(false);
                                menu.findItem(R.id.actionRight).setVisible(true);
                                menu.findItem(R.id.actionWrong).setVisible(true);
                                menu.findItem(R.id.actionBack).setVisible(false);
                                addNewActivityFormRedesign(true);
                                makeEditable(true);
                                showShortToast(language.getMessageActivityYouMustAddNewActivity());
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
            try
            {
                String url = DataHelper.URL_CUSTOMER_HELPER+"customeractivityinsert";// + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.INSERT_CUSTOMER_ACTIVITY + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                //url += "&customeractivityinsert=" + URLEncoder.encode(json, "UTF-8");
                MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")));
                multipartEntity.addPart("customeractivityinsert", new StringBody(json, Charset.forName("UTF-8")));
                AsyncTaskWithAuthorizationHeaderPost asyncTaskPost = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, getActivity(), multipartEntity, true, language);
                asyncTaskPost.execute();

                /*BasicAsyncTaskGetRequest asyncTaskCustomerSearch = new BasicAsyncTaskGetRequest(url, onAsyncResultAddNewCustomerActivity, getActivity(), true);
                asyncTaskCustomerSearch.execute();*/
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

    private void updateCustomerActivityInfo()
    {
        String activityType = null;
        if(selectedActivityTypeModel != null)
        {
            activityType = selectedActivityTypeModel.getAkttyp();
        }
        String activityTopic = "";
        if(selectedActivityTopicModel != null)
        {
            activityTopic = selectedActivityTopicModel.getAktthema();
        }
        String offer = "";
        if(selectedCustomerOfferModel != null)
        {
            offer = selectedCustomerOfferModel.getOfferno();
        }
        String project = "";
        if(selectedCustomerProjectModel != null)
        {
            project = selectedCustomerProjectModel.getProjectId();
        }
        String date = DataHelper.formatDateToOriginal(labelCustomerActivityStartDate.getText().toString());
        String startTime =  DataHelper.formatTimeToOriginal(labelCustomerActivityStartDate.getText().toString(), labelCustomerActivityStartTime.getText().toString());
        String endTime = DataHelper.formatTimeToOriginal(labelCustomerActivityStartDate.getText().toString(), labelCustomerActivityEndTime.getText().toString());
        String realized = "";
        String fest = "";

        if(checkBoxCustomerActivityFixedTimes.isChecked())
        {
            fest = "1";
        }
        else
        {
            fest = "0";
        }

        if(checkBoxCustomerActivityRealized.isChecked())
        {
            realized = "1";
        }
        else
        {
            realized = "0";
        }

//        String mobile = textCustomerActivityContactPersonMobile.getText().toString();
//        String phone = textCustomerActivityContactPersonPhone.getText().toString();
        String note = textCustomerActivityNotes.getText().toString();
        String kontakt = matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();

        CustomerActivityUpdateModel customerActivityUpdateModel = new CustomerActivityUpdateModel();
        customerActivityUpdateModel.setAkttyp(activityType);
        customerActivityUpdateModel.setAktthema(activityTopic);
        customerActivityUpdateModel.setNotiz(note);
        customerActivityUpdateModel.setObjekt(project + "");
        customerActivityUpdateModel.setAngebot(offer);
        customerActivityUpdateModel.setStartdatum(date);
        customerActivityUpdateModel.setStartzeit(startTime);
        customerActivityUpdateModel.setEndzeit(endTime);
        customerActivityUpdateModel.setRealisiert(realized);
        customerActivityUpdateModel.setFest(fest);
        customerActivityUpdateModel.setKontakt(kontakt);
        customerActivityUpdateModel.setGeloescht("0");
        customerActivityUpdateModel.setAenderungMitarbeiter(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber());
        String loginUserId = matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserId();
//        customerActivityUpdateModel.setAenderungMitarbeiter(loginUserId);
        if(selectedActivity != null)
            customerActivityUpdateModel.setAktivitaet(selectedActivity.getAktivitaet());

        ArrayList<String> listOfSelectedEmployeeId = new ArrayList<>();
        for(int i = 0; i < listOfSelectedEmployee.size(); i++)
        {
            listOfSelectedEmployeeId.add(listOfSelectedEmployee.get(i).getMitarbeiter());
        }
        customerActivityUpdateModel.setMitarbeiter(listOfSelectedEmployeeId);

        ArrayList<String> listOfSelectedContactPersonId = new ArrayList<>();
        for(int i = 0; i < listOfSelectedContactPerson.size(); i++)
        {
            listOfSelectedContactPersonId.add(listOfSelectedContactPerson.get(i).getAnspartner());
        }
        customerActivityUpdateModel.setAnspartner(listOfSelectedContactPersonId);

//        customerAddActivityModel.setAkttyp(activityType);
//        customerAddActivityModel.setAkttyp(activityType);
//        customerAddActivityModel.setAkttyp(activityType);
//        customerAddActivityModel.setAkttyp(activityType);

        String json = new Gson().toJson(customerActivityUpdateModel);

//        else if(date.length() > 0)
//        {
//            //showShortToast("Please select date");
//            showShortToast(language.getLabelDate());
//        }
        if(selectedActivityTypeModel == null)
        {
            //showShortToast("Please selected acitivity type");
            showShortToast(language.getMessagePleaseSelectActivityType());
        }
        else if(selectedActivity == null)
        {
            showShortToast(language.getMessagePleaseSelectActivity());
        }
        else if(date.equals(""))
        {
            labelCustomerActivityStartDate.setError(language.getLabelRequired());
            labelCustomerActivityStartDate.requestFocus();
            showShortToast(language.getMessagePleaseSelectDate());
        }
        else if(!DataHelper.yearLessThen2079(date) && !DataHelper.yearLessThen2079(startTime) && !DataHelper.yearLessThen2079(endTime))
        {
            showShortToast(language.getMessageDateMustBeLessThen2079());
        }
        else if(DataHelper.isNetworkAvailable(getActivity()))
        {
            AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult()
            {
                @Override
                public void OnAsynResult(String result)
                {
                    if(result.equals("error"))
                    {
                        showShortToast(language.getMessageError());
                    }else if(result.equals(DataHelper.NetworkError)){
                        showShortToast(language.getMessageNetworkNotAvailable());
                    }

                    else
                    {
                        try
                        {
                            listOfLoadedCustomerActivity.clear();
                            //ArrayList<CustomerActivityGetModel> listOfCustomerActivityDetails = new ArrayList<>();
                            CustomerActivityGetModel customerActivityGetModel = new CustomerActivityGetModel();
                            //CustomerActivityGetModel.extractFromJson(result, listOfCustomerActivityDetails);
                            customerActivityGetModel = new Gson().fromJson(result, CustomerActivityGetModel.class);
                            String listOfActivity = new Gson().toJson(customerActivityGetModel.getListOfActivities());
                            matecoPriceApplication.saveData(DataHelper.LoadedCustomerActivity, listOfActivity);
                            String listOfProjects = new Gson().toJson(customerActivityGetModel.getListOfProject());
                            matecoPriceApplication.saveData(DataHelper.LoadedCustomerProject, listOfProjects);
                            String listOfOffer = new Gson().toJson(customerActivityGetModel.getListOfOffers());
                            matecoPriceApplication.saveData(DataHelper.LoadedCustomerOffer, listOfOffer);
                            String kontakt = matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();
                            if(db.isCustomerAvailable(kontakt))
                            {
                                db.updateCustomerActivityProjectOffer(kontakt, listOfLoadedCustomerActivity, listOfAllProject, listOfAllOffer);
                            }
                            String needToaddActivity = customerActivityGetModel.getNeedToAddNewActivity();
                            menu.findItem(R.id.actionAdd).setVisible(true);
                            menu.findItem(R.id.actionEdit).setVisible(true);
                            menu.findItem(R.id.actionRight).setVisible(false);
                            menu.findItem(R.id.actionWrong).setVisible(false);
                            menu.findItem(R.id.actionBack).setVisible(true);
                            clearAllValues();
                            listOfLoadedCustomerActivity.addAll( matecoPriceApplication.getLoadedCustomerActivities(DataHelper.LoadedCustomerActivity, new ArrayList<CustomerActivityModel>().toString()));
                            if(listOfLoadedCustomerActivity.size() > 0)
                            {
                                setCustomerActivity(listOfLoadedCustomerActivity.get(0));
                                customerActivityListAdapter.setSelectedIndex(0);
                                listViewCustomerActivityList.setSelection(0);
                                selectedActivity = listOfLoadedCustomerActivity.get(0);
                            }
                            customerActivityListAdapter.notifyDataSetChanged();
                            makeEditable(false);
                            if(needToaddActivity.equalsIgnoreCase("true")){
                                if(checkAllRealized(listOfLoadedCustomerActivity))
                                {
                                    clearAllValues();
                                    menu.findItem(R.id.actionAdd).setVisible(false);
                                    menu.findItem(R.id.actionEdit).setVisible(false);
                                    menu.findItem(R.id.actionRight).setVisible(true);
                                    menu.findItem(R.id.actionWrong).setVisible(true);
                                    menu.findItem(R.id.actionBack).setVisible(false);
                                    addNewActivityFormRedesign(true);
                                    makeEditable(true);
                                    showShortToast(language.getMessageActivityYouMustAddNewActivity());
                                }
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
            try
            {
                String url = DataHelper.URL_CUSTOMER_HELPER+"customeractivityupdate"; //+ DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.UPDATE_CUSTOMER_ACTIVITY
                        /*+ "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "&customeractivityupdate=" + URLEncoder.encode(json, "UTF-8");*/
                MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")));
                multipartEntity.addPart("customeractivityupdate", new StringBody(json, Charset.forName("UTF-8")));
                AsyncTaskWithAuthorizationHeaderPost asyncTaskPost = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, getActivity(), multipartEntity, true, language);
                asyncTaskPost.execute();

               /* asyncTaskCustomerSearch = new BasicAsyncTaskGetRequest(url, onAsyncResultCustomerActivityUpdate, getActivity(), true);
                asyncTaskCustomerSearch.execute();*/
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

    private boolean checkAllRealized(ArrayList<CustomerActivityModel> customerActivities)
    {
        for(int i = 0; i < customerActivities.size(); i++)
        {
            if(!Boolean.parseBoolean(customerActivities.get(i).getRealisiert()))
            {
                return false;
            }
        }
        return true;
    }

    private void clearAllValues()
    {
        listOfSelectedEmployee.clear();
        listOfRemainingEmployee.addAll(listOfAllEmployee);
        selectedActivityTypeModel = null;
        selectedActivityTopicModel = null;
        selectedCustomerProjectModel = null;
        selectedCustomerOfferModel = null;
        selectedEmployeeAdapter.notifyDataSetChanged();
        spinnerCustomerActivityOffer.setSelection(0);
      //  spinnerCustomerActivityProjects.setSelection(0);
        spinnerCustomerActivityActivityTopic.setSelection(0);
        spinnerCustomerActivityActivityType.setSelection(0);

        if(activityTypeAdapter != null) {
            activityTopicAdapter.notifyDataSetChanged();
        }
        if(activityTopicAdapter != null) {
            activityTypeAdapter.notifyDataSetChanged();
        }
        projectAdapter.notifyDataSetChanged();
        offerAdapter.notifyDataSetChanged();

        listOfSelectedContactPerson.clear();
        listOfRemainingContactPerson.addAll(listOfAllContactPerson);
        selectedContactPersonAdapter.notifyDataSetChanged();
        //listOfSelectedProjects.clear();
        projectAdapter.notifyDataSetChanged();
        //listOfSelectedOffer.clear();
        offerAdapter.notifyDataSetChanged();
        checkBoxCustomerActivityRealized.setChecked(false);
        checkBoxCustomerActivityFixedTimes.setChecked(false);
        textViewProject.setText("");

        super.clearAll();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(asyncTask != null && asyncTask.getStatus() == AsyncTask.Status.RUNNING)
        {
            asyncTask.cancel(true);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.labelCustomerActivityStartDate:
                openDatePicker();
                break;
            case R.id.imageButtonCustomerActivityStartDate:
                openDatePicker();
                break;
            case R.id.labelCustomerActivityStartTime:
                openTimePicker(listenerStartTime, "start");
                break;
            case R.id.labelCustomerActivityEndTime:
                openTimePicker(listenerEndTime, "end");
                break;
            case R.id.imageButtonEndTime:
                openTimePicker(listenerEndTime, "end");
                break;
            case R.id.imageButtonStartTime:
                openTimePicker(listenerStartTime, "start");
                break;
            case R.id.buttonRemoveCustomerActivityEmployee:
                if(selectedEmployeeAdapter.selectedIndex != -1)
                {
                    listOfRemainingEmployee.add(listOfSelectedEmployee.get(selectedEmployeeAdapter.selectedIndex));
                    listOfSelectedEmployee.remove(selectedEmployeeAdapter.selectedIndex);
                    selectedEmployeeAdapter.setSelectedIndex(-1);
                    remainingEmployeeAdapter.notifyDataSetChanged();
                    selectedEmployeeAdapter.notifyDataSetChanged();
                }
                else
                {
                    showShortToast(language.getMessageSelectItemToRemove());
                }
                break;
            case R.id.buttonAddCustomerActivityEmployee:
                showAddEmployeeDialog();
                break;
            case R.id.buttonRemoveCustomerActivityContacts:
                if(selectedContactPersonAdapter.selectedIndex != -1)
                {
                    listOfRemainingContactPerson.add(listOfSelectedContactPerson.get(selectedContactPersonAdapter.selectedIndex));
                    listOfSelectedContactPerson.remove(selectedContactPersonAdapter.selectedIndex);
                    selectedContactPersonAdapter.setSelectedIndex(-1);
                    remainingContactPersonAdapter.notifyDataSetChanged();
                }
                else
                {
                    showShortToast(language.getMessageSelectItemToRemove());
                }
                break;
            case R.id.buttonAddCustomerActivityContacts:
                showAddContactPersonDialog();
                break;
            case R.id.buttonAddProject:
                showProjectSearchDialog();
                break;
//            case R.id.buttonRemoveCustomerActivityProject:
//                if(projectAdapter.selectedIndex != -1)
//                {
//                    listOfRemainingProject.add(listOfSelectedProjects.get(projectAdapter.selectedIndex));
//                    listOfSelectedProjects.remove(projectAdapter.selectedIndex);
//                    projectAdapter.selectedIndex = -1;
//                    remainingProjectAdapter.notifyDataSetChanged();
//                    projectAdapter.notifyDataSetChanged();
//                }
//                else
//                {
//                    showShortToast("Please select a item");
//                }
//                break;
//            case R.id.buttonAddCustomerActivityProject:
//                showAddProjectDialog();
//                break;
//            case R.id.buttonAddCustomerActivityOffer:
//                showAddOfferDialog();
//                break;
//            case R.id.buttonRemoveCustomerActivityOffer:
//
//                break;
            case R.id.imageSortDate:
            case R.id.labelCustomerActivityDate:
                if(imageSortDate.getVisibility() != View.GONE && !isAscending)
                {
                    //((TextView)rootView.findViewById(R.id.labelAgendaWeeklyDueName)).setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.ic_sort_asc_24dp);
                    imageSortDate.setImageResource(R.drawable.ic_sort_asc_24dp);
                    Collections.sort(listOfLoadedCustomerActivity, new CustomerActivityComparable(1, 0));
                    isAscending = true;
                }
                else
                {
                    imageSortDate.setImageResource(R.drawable.ic_sort_dsc_24dp);
                    Collections.sort(listOfLoadedCustomerActivity, new CustomerActivityComparable(1, 1));
                    imageSortDate.setVisibility(View.VISIBLE);
                    isAscending = false;
                }
                imageSortDesignation.setVisibility(View.GONE);
                imageSortRealized.setVisibility(View.GONE);
                if(listOfLoadedCustomerActivity.size() > 0)
                {
                    customerActivityListAdapter.setSelectedIndex(0);
                    selectedActivity = listOfLoadedCustomerActivity.get(0);
                    setCustomerActivity(selectedActivity);
                }
                break;
            case R.id.imageSortDesignation:
            case R.id.labelCustomerActivityDesignation:
                if(imageSortDesignation.getVisibility() != View.GONE && !isAscending)
                {
                    //((TextView)rootView.findViewById(R.id.labelAgendaWeeklyDueName)).setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.ic_sort_asc_24dp);
                    imageSortDesignation.setImageResource(R.drawable.ic_sort_asc_24dp);
                    Collections.sort(listOfLoadedCustomerActivity, new CustomerActivityComparable(2, 0));
                    isAscending = true;
                }
                else
                {
                    imageSortDesignation.setImageResource(R.drawable.ic_sort_dsc_24dp);
                    Collections.sort(listOfLoadedCustomerActivity, new CustomerActivityComparable(2, 1));
                    imageSortDesignation.setVisibility(View.VISIBLE);
                    isAscending = false;
                }
                imageSortDate.setVisibility(View.GONE);
                imageSortRealized.setVisibility(View.GONE);
                if(listOfLoadedCustomerActivity.size() > 0)
                {
                    customerActivityListAdapter.setSelectedIndex(0);
                    selectedActivity = listOfLoadedCustomerActivity.get(0);
                    setCustomerActivity(selectedActivity);
                }
                break;
            case R.id.imageSortRealized:
            case R.id.labelCustomerActivityRealized:
                if(imageSortRealized.getVisibility() != View.GONE && !isAscending)
                {
                    //((TextView)rootView.findViewById(R.id.labelAgendaWeeklyDueName)).setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.ic_sort_asc_24dp);
                    imageSortRealized.setImageResource(R.drawable.ic_sort_asc_24dp);
                    Collections.sort(listOfLoadedCustomerActivity, new CustomerActivityComparable(3, 0));
                    isAscending = true;
                }
                else
                {
                    imageSortRealized.setImageResource(R.drawable.ic_sort_dsc_24dp);
                    Collections.sort(listOfLoadedCustomerActivity, new CustomerActivityComparable(3, 1));
                    imageSortRealized.setVisibility(View.VISIBLE);
                    isAscending = false;
                }
                imageSortDesignation.setVisibility(View.GONE);
                imageSortDate.setVisibility(View.GONE);
                if(listOfLoadedCustomerActivity.size() > 0)
                {
                    customerActivityListAdapter.setSelectedIndex(0);
                    selectedActivity = listOfLoadedCustomerActivity.get(0);
                    setCustomerActivity(selectedActivity);
                }
                break;
        }
    }

    private void openDatePicker()
    {
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
        newFragment.setCallBack(onFromDate);
        //newFragment.setMinDate(today);
        newFragment.show(getActivity().getSupportFragmentManager(), "startDate");
    }

    private void openTimePicker(TimePickerDialog.OnTimeSetListener callback, String pickerOf)
    {
        if(pickerOf.equals("start"))
        {
            TimePickerDialogFragment timePicker = new TimePickerDialogFragment();
            Bundle args = new Bundle();
            Date today = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(today);
            args.putInt("hour", c.get(Calendar.HOUR_OF_DAY));
            args.putInt("min", c.get(Calendar.MINUTE));
            timePicker.setArguments(args);
            timePicker.setCallBack(callback);
            timePicker.show(getActivity().getSupportFragmentManager(), "Time");
        }
        else
        {
            if(labelCustomerActivityStartTime.getText().toString().equals(""))
            {
                showShortToast(language.getMessageSelectStartTime());
            }
            else
            {
                SimpleDateFormat outputFormatter = new SimpleDateFormat("HH:mm");
                try {
                    Date date = outputFormatter.parse(labelCustomerActivityStartTime.getText().toString());
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    Bundle args = new Bundle();
                    args.putInt("hour", c.get(Calendar.HOUR_OF_DAY));
                    args.putInt("min", c.get(Calendar.MINUTE));
                    TimePickerDialogFragment timePicker = new TimePickerDialogFragment();
                    timePicker.setCallBack(callback);
                    timePicker.setArguments(args);
                    timePicker.show(getActivity().getSupportFragmentManager(), "Time");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    DatePickerDialog.OnDateSetListener onFromDate = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            try
            {
                monthOfYear += 1;
                String date = dayOfMonth + " " + monthOfYear + " " + year;
                String finaldate = DataHelper.formatDate(formatter.parse(date));
                labelCustomerActivityStartDate.setText(finaldate);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
    };

    TimePickerDialog.OnTimeSetListener listenerStartTime = new TimePickerDialog.OnTimeSetListener()
    {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            String time = String.format("%02d:%02d", hourOfDay, minute);
            labelCustomerActivityStartTime.setText(time);
        }
    };

    TimePickerDialog.OnTimeSetListener listenerEndTime = new TimePickerDialog.OnTimeSetListener()
    {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            String endTime = String.format("%02d:%02d", hourOfDay, minute);
            Date inTime = null;
            try
            {
                inTime = sdf.parse(labelCustomerActivityStartTime.getText().toString());
                Date outTime = sdf.parse(endTime);
                if(inTime.compareTo(outTime) >= 0)
                {
                    showShortToast(language.getMessageEndTimeGreaterThenStartTime());
                }
                else
                {
                    labelCustomerActivityEndTime.setText(endTime);
                }
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
    };

    private void showAddEmployeeDialog()
    {
        final Dialog dialog = showCustomDialog(R.layout.dialog_add_employee, language.getMessageSelectEmployee());
        listOfRemainingEmployee.clear();
        listOfRemainingEmployee.addAll(removeSelectedEmployee(listOfAllEmployee, listOfSelectedEmployee));
        ListView listViewAlertSelectFeature = (ListView)dialog.findViewById(R.id.listViewAlertSelectEmployee);
        listViewAlertSelectFeature.setAdapter(remainingEmployeeAdapter);
        remainingEmployeeAdapter.notifyDataSetChanged();
        Button buttonDialogAddEmployee, buttonDialogAddEmployeeCancel;

        buttonDialogAddEmployee = (Button)dialog.findViewById(R.id.buttonDialogAddEmployee);
        buttonDialogAddEmployeeCancel = (Button)dialog.findViewById(R.id.buttonDialogAddEmployeeCancel);
        buttonDialogAddEmployee.setText(language.getLabelAdd());
        buttonDialogAddEmployeeCancel.setText(language.getLabelCancel());
        listViewAlertSelectFeature.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //view.setSelected(true);
                remainingEmployeeAdapter.setSelectedIndex(position);
            }
        });
        buttonDialogAddEmployee.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(remainingEmployeeAdapter.selectedIndex != -1)
                {
                    listOfSelectedEmployee.add(listOfRemainingEmployee.get(remainingEmployeeAdapter.selectedIndex));
                    listOfRemainingEmployee.remove(remainingEmployeeAdapter.selectedIndex);
                    remainingEmployeeAdapter.notifyDataSetChanged();
                    selectedEmployeeAdapter.notifyDataSetChanged();
                    setListViewHeightBasedOnChildren(listViewCustomerActivityEmployee);
                    remainingEmployeeAdapter.setSelectedIndex(-1);
                    dialog.dismiss();
                }
                else
                {
                    showShortToast(language.getMessageSelectItemToRemove());
                }
            }
        });

        buttonDialogAddEmployeeCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showShortToast(language.getLabelCancel());
                dialog.dismiss();
            }
        });
        if(listOfAllEmployee.size() > 0)
        {
            dialog.show();
        }
        else
        {
            //showLongToast("There are no Employee");
            showLongToast(language.getMessageNoEmployees());
        }
    }

    private void showAddProjectDialog()
    {
//        final Dialog dialog = showCustomDialog(R.layout.dialog_add_project, "Select Project");
//        listOfAllProject.clear();
//        listOfAllProject.addAll(removeSelectedProject(listOfAllProject, listOfAllProject));
//        final ListView listViewAlertSelectProject = (ListView)dialog.findViewById(R.id.listViewAlertSelectProject);
//        listViewAlertSelectProject.setAdapter(remainingProjectAdapter);
//        remainingProjectAdapter.notifyDataSetChanged();
//        Button buttonDialogAddProject, buttonDialogAddProjectCancel;
//
//        buttonDialogAddProject = (Button)dialog.findViewById(R.id.buttonDialogAddProject);
//        buttonDialogAddProjectCancel = (Button)dialog.findViewById(R.id.buttonDialogAddProjectCancel);
//
//        listViewAlertSelectProject.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                view.setSelected(true);
//                remainingProjectAdapter.setSelectedIndex(position);
//            }
//        });
//        buttonDialogAddProject.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                if(remainingContactPersonAdapter.selectedIndex != -1)
//                {
//                    listOfAllProject.add(listOfAllProject.get(remainingProjectAdapter.selectedIndex));
//                    listOfAllProject.remove(remainingProjectAdapter.selectedIndex);
//                    remainingProjectAdapter.notifyDataSetChanged();
//                    projectAdapter.notifyDataSetChanged();
//                    setListViewHeightBasedOnChildren(listViewAlertSelectProject);
//                    remainingProjectAdapter.selectedIndex = -1;
//                    dialog.dismiss();
//                }
//                else
//                {
//                    showShortToast("Please select a item");
//                }
//            }
//        });
//
//        buttonDialogAddProjectCancel.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                remainingProjectAdapter.selectedIndex = -1;
//                showShortToast("Cancel");
//                dialog.dismiss();
//            }
//        });
//        if(listOfAllProject.size() > 0)
//        {
//            dialog.show();
//        }
//        else
//        {
//            showLongToast("There are no projects");
//        }
    }

    private void showAddContactPersonDialog()
    {
        final Dialog dialog = showCustomDialog(R.layout.dialog_add_contact_person, language.getMessageSelectContactPerson());
        listOfRemainingContactPerson.clear();
        listOfRemainingContactPerson.addAll(removeSelectedContactPerson(listOfAllContactPerson, listOfSelectedContactPerson));
        ListView listViewAlertSelectContactPerson = (ListView)dialog.findViewById(R.id.listViewAlertSelectContactPerson);
        listViewAlertSelectContactPerson.setAdapter(remainingContactPersonAdapter);
        remainingEmployeeAdapter.notifyDataSetChanged();
        Button buttonDialogAddContactPerson, buttonDialogAddContactPersonCancel;

        buttonDialogAddContactPerson = (Button)dialog.findViewById(R.id.buttonDialogAddContactPerson);
        buttonDialogAddContactPersonCancel = (Button)dialog.findViewById(R.id.buttonDialogAddContactPersonCancel);
        buttonDialogAddContactPerson.setText(language.getLabelAdd());
        buttonDialogAddContactPersonCancel.setText(language.getLabelCancel());
        listViewAlertSelectContactPerson.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //view.setSelected(true);
                remainingContactPersonAdapter.setSelectedIndex(position);
            }
        });
        buttonDialogAddContactPerson.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(remainingContactPersonAdapter.selectedIndex != -1)
                {
                    listOfSelectedContactPerson.add(listOfRemainingContactPerson.get(remainingContactPersonAdapter.selectedIndex));
                    listOfRemainingContactPerson.remove(remainingContactPersonAdapter.selectedIndex);
                    remainingContactPersonAdapter.notifyDataSetChanged();
                    selectedContactPersonAdapter.notifyDataSetChanged();
                    setListViewHeightBasedOnChildren(listCustomerActivityContacts);
                    remainingContactPersonAdapter.setSelectedIndex(-1);
                    dialog.dismiss();
                }
                else
                {
                    showShortToast(language.getMessageSelectItemToRemove());
                }
            }
        });

        buttonDialogAddContactPersonCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                remainingContactPersonAdapter.setSelectedIndex(-1);
                showShortToast(language.getLabelCancel());
                dialog.dismiss();
            }
        });
        if(listOfRemainingContactPerson.size() > 0)
        {
            dialog.show();
        }
        else
        {
            showLongToast(language.getMessageNoContactPersons());
        }
    }

    private void showAddOfferDialog()
    {
//        final Dialog dialog = showCustomDialog(R.layout.dialog_add_offer, "Select Offer");
//        listOfRemainingOffer.clear();
//        listOfRemainingOffer.addAll(removeSelectedOffer(listOfAllOffer, listOfSelectedOffer));
//        ListView listViewAlertSelectOffer = (ListView)dialog.findViewById(R.id.listViewAlertSelectOffer);
//        listViewAlertSelectOffer.setAdapter(remainingOfferAdapter);
//        remainingOfferAdapter.notifyDataSetChanged();
//        Button buttonDialogAddOffer, buttonDialogAddOfferCancel;
//
//        buttonDialogAddOffer = (Button)dialog.findViewById(R.id.buttonDialogAddOffer);
//        buttonDialogAddOfferCancel = (Button)dialog.findViewById(R.id.buttonDialogAddOfferCancel);
//
//        listViewAlertSelectOffer.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                view.setSelected(true);
//                remainingOfferAdapter.setSelectedIndex(position);
//            }
//        });
//        buttonDialogAddOffer.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                if(remainingOfferAdapter.selectedIndex != -1)
//                {
//                    listOfSelectedOffer.add(listOfRemainingOffer.get(remainingOfferAdapter.selectedIndex));
//                    listOfRemainingOffer.remove(remainingOfferAdapter.selectedIndex);
//                    remainingOfferAdapter.notifyDataSetChanged();
//                    offerAdapter.notifyDataSetChanged();
//                    //setListViewHeightBasedOnChildren(listViewCustomerActivityOffer);
//                    remainingOfferAdapter.selectedIndex = -1;
//                    dialog.dismiss();
//                }
//                else
//                {
//                    showShortToast("Please select a item");
//                }
//            }
//        });
//
//        buttonDialogAddOfferCancel.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                remainingOfferAdapter.selectedIndex = -1;
//                showShortToast("Cancel");
//                dialog.dismiss();
//            }
//        });
//        if(listOfRemainingOffer.size() > 0)
//        {
//            dialog.show();
//        }
//        else
//        {
//            showLongToast("There are no offers");
//        }
    }

    private List<CustomerActivityEmployeeListItem> removeSelectedEmployee(List<CustomerActivityEmployeeListItem> listOfEmployee, List<CustomerActivityEmployeeListItem> selectedEmployee)
    {
        List<CustomerActivityEmployeeListItem> tempList = new ArrayList<>();
        //listOfRemainingEmployee.clear();
        tempList.addAll(listOfEmployee);
        for(int i = 0; i < selectedEmployee.size(); i++)
        {
            for(int j = 0; j < tempList.size(); j++)
            {
                if(tempList.get(j).getMitarbeiter().equals(selectedEmployee.get(i).getMitarbeiter()))
                {
                    tempList.remove(j);
                }
            }
        }
        return tempList;
    }

    private ArrayList<ContactPersonModel> removeSelectedContactPerson(ArrayList<ContactPersonModel> listOfContactPerson, ArrayList<ContactPersonModel> selectedContactPerson)
    {
        ArrayList<ContactPersonModel> tempList = new ArrayList<>();
        //listOfRemainingContactPerson.clear();
        tempList.addAll(listOfContactPerson);
        for(int i = 0; i < selectedContactPerson.size(); i++)
        {
            for(int j = 0; j < tempList.size(); j++)
            {
                if(tempList.get(j).getAnspartner().equals(selectedContactPerson.get(i).getAnspartner()))
                {
                    tempList.remove(j);
                }
            }
        }
        return tempList;
    }

    private ArrayList<CustomerProjectModel> removeSelectedProject(ArrayList<CustomerProjectModel> listOfProjects, ArrayList<CustomerProjectModel> selectedProjects)
    {
        for(int i = 0; i < selectedProjects.size(); i++)
        {
            for(int j = 0; j < listOfProjects.size(); j++)
            {
                if(listOfProjects.get(j).getProjectId().equals(selectedProjects.get(i).getProjectId()))
                {
                    listOfProjects.remove(j);
                }
            }
        }
        return listOfProjects;
    }

    private ArrayList<CustomerOfferModel> removeSelectedOffer(ArrayList<CustomerOfferModel> listOfOffer, ArrayList<CustomerOfferModel> selectedOffer)
    {
        for(int i = 0; i < selectedOffer.size(); i++)
        {
            for(int j = 0; j < listOfOffer.size(); j++)
            {
                if(listOfOffer.get(j).getOfferno().equals(selectedOffer.get(i).getOfferno()))
                {
                    listOfOffer.remove(j);
                }
            }
        }
        return listOfOffer;
    }

    private boolean checkIfTextChanged(CustomerActivityModel activity)
    {
        if(activity != null)
        {
            String defaultActivityType = activity.getAkttypID();
            String defaultActivityTopic = activity.getAktthemaID();
            String defaultProject = activity.getProjektID();
            String defaultOffer = activity.getAngebot();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            String defaultDate = null;
            try
            {
                defaultDate = DataHelper.formatDate(format.parse(activity.getStartdatum()));
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            String defaultStartTime = DataHelper.formatTime(activity.getStartzeit());
            String defaultEndTime = DataHelper.formatTime(activity.getEndzeit());
            String defaultNotes = activity.getNotiz();
            String defaultRealized = activity.getRealisiert();
            String defaultFest = activity.getFest();
            ArrayList<SimpleContactPersonAnsPartner> defaultContactPerson = activity.getListOfContactPersonId();
            ArrayList<SimpleEmployeeMitrabeiter> defaultEmployee = activity.getListOfEmployeeId();

            String activityType = null;
            if(selectedActivityTypeModel != null)
            {
                activityType = selectedActivityTypeModel.getAkttyp();
            }
            String activityTopic = "";
            if(selectedActivityTopicModel != null)
            {
                activityTopic = selectedActivityTopicModel.getAktthema();
            }
            String offer = "";
            if(selectedCustomerOfferModel != null)
            {
                offer = selectedCustomerOfferModel.getOfferno();
            }
            String project = "";
            if(selectedCustomerProjectModel != null)
            {
                project = selectedCustomerProjectModel.getProjectId();
            }

            String date = labelCustomerActivityStartDate.getText().toString();
            String startTime = labelCustomerActivityStartTime.getText().toString();
            String endTime = labelCustomerActivityEndTime.getText().toString();
            String realized = checkBoxCustomerActivityRealized.isChecked()+"";
            String fest = checkBoxCustomerActivityFixedTimes.isChecked()+"";

//            String mobile = textCustomerActivityContactPersonMobile.getText().toString();
//            String phone = textCustomerActivityContactPersonPhone.getText().toString();
            String note = textCustomerActivityNotes.getText().toString();
            //offer.equalsIgnoreCase(defaultOffer) && project.equalsIgnoreCase(defaultProject) &&
            if(activityType != null && activityType.equalsIgnoreCase(defaultActivityType) && activityTopic.equalsIgnoreCase(defaultActivityTopic) &&
                    date.equalsIgnoreCase(defaultDate)
                    && startTime.equalsIgnoreCase(defaultStartTime) && endTime.equalsIgnoreCase(defaultEndTime) && note.equalsIgnoreCase(defaultNotes) &&
                    realized.equalsIgnoreCase(defaultRealized))
            {
                if(defaultContactPerson.size() == listOfSelectedContactPerson.size() && defaultEmployee.size() == listOfSelectedEmployee.size())
                {
                    for(int i = 0; i < defaultEmployee.size(); i++)
                    {
                        boolean isEqual = false;
                        for(int j = 0; j < listOfSelectedEmployee.size(); j++)
                        {
                            if(defaultEmployee.get(i).getMitarbeiter().equals(listOfSelectedEmployee.get(j).getMitarbeiter()))
                            {
                                isEqual = true;
                                break;
                            }
                        }
                        if(!isEqual)
                        {
                            return true;
                        }
                    }

                    for(int i = 0; i < defaultContactPerson.size(); i++)
                    {
                        boolean isEqual = false;
                        for(int j = 0; j < listOfSelectedContactPerson.size(); j++)
                        {
                            if(defaultContactPerson.get(i).getAnsPartner().equals(listOfSelectedContactPerson.get(j).getAnspartner()))
                            {
                                isEqual = true;
                                break;
                            }
                        }
                        if(!isEqual)
                        {
                            return true;
                        }
                    }
                    return false;
                }
                else
                {
                    return true;
                }
            }
            return true;
        }
        return true;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        switch (v.getId())
        {
//            case R.id.textCustomerActivityContactPersonPhone:
//                textCustomerActivityContactPersonMobile.requestFocus();
//                break;
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
        private void findHintsFromApi(final Context context, String search) {
            // GoogleBooksProtocol is a wrapper for the Google Books API
            //GoogleBooksProtocol protocol = new GoogleBooksProtocol(context, MAX_RESULTS);
            arraylistHint = new ArrayList<HintModel>();

            try {
                String matchCode = textProjectSearchMatchCode.getText().toString();
                String description = textProjectSearchDescription.getText().toString();
                String typeOfProject = textProjectSearchTypeOfProject.getText().toString();
                String address = textProjectSearchStreetAddress.getText().toString();
                String from = textProjectSearchFrom.getText().toString();
                String to = textProjectSearchTo.getText().toString();
                String employee = textProjectSearchEmployee.getText().toString();
                String place = textProjectSearchPlace.getText().toString();

                if(description.length() > 0 || typeOfProject.length() > 0 || matchCode.length() > 0 || address.length() > 0 ||
                        from.length() > 0 || to.length() > 0 || place.length() > 0 || employee.length() > 0)
                {
                    ProjectSearchPagingRequestModel projectSearchPagingRequestModel = new ProjectSearchPagingRequestModel();

                    projectSearchPagingRequestModel.setMatchcode("");
                    projectSearchPagingRequestModel.setBeschreibung("");
                    projectSearchPagingRequestModel.setObjektTyp("");
                    projectSearchPagingRequestModel.setAdresse("");
                    projectSearchPagingRequestModel.setBeginn("");
                    projectSearchPagingRequestModel.setEnde("");
                    projectSearchPagingRequestModel.setMitrabeiter("");
                    projectSearchPagingRequestModel.setOrt("");

                    if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_MATCHCODE)){
                        projectSearchPagingRequestModel.setMatchcode(matchCode);
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_DESCRIPTION)){
                        projectSearchPagingRequestModel.setBeschreibung(description);
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_TYPE_OF_PROJECT)){
                        projectSearchPagingRequestModel.setObjektTyp(typeOfProject);
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_ADDRESS)){
                        projectSearchPagingRequestModel.setAdresse(address);
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_FROM)){
                        projectSearchPagingRequestModel.setBeginn(from);
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_EMPLOYEE)){
                        projectSearchPagingRequestModel.setMitrabeiter(employee);
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_TO)){
                        projectSearchPagingRequestModel.setEnde(to);
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_PLACE)){
                        projectSearchPagingRequestModel.setOrt(place);
                    }
                    projectSearchPagingRequestModel.setPageSize(GlobalClass.pageSizeForHint + "");
                    pageNuber = 1;
                    projectSearchPagingRequestModel.setPageNumber(GlobalClass.pageNuber+"");
                    jsonToSend = DataHelper.getGson().toJson(projectSearchPagingRequestModel);
                }
                String base64Data = DataHelper.getToken();
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
                        DataHelper.URL_PROJECT_HELPER+"projectsearchhint"
                                + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                                + "/projectsearchparam=" + URLEncoder.encode(jsonToSend, "UTF-8"),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.toString());
                                    JSONArray resultsOfCustomers = jsonObject.getJSONArray("Result");
                                    TypeToken<List<HintModel>> token = new TypeToken<List<HintModel>>() {};
                                    arraylistHint = new Gson().fromJson(resultsOfCustomers.toString(),token.getType());
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
