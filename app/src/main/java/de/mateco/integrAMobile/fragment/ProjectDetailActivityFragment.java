package de.mateco.integrAMobile.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
import java.util.Locale;

import de.mateco.integrAMobile.Helper.CustomerActivityComparable;
import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.DatePickerDialogFragment;
import de.mateco.integrAMobile.Helper.DelayAutoCompleteTextView;
import de.mateco.integrAMobile.Helper.GlobalClass;
import de.mateco.integrAMobile.Helper.LogApp;
import de.mateco.integrAMobile.Helper.ProjectActivityComparable;
import de.mateco.integrAMobile.Helper.TimePickerDialogFragment;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.ActivityTopicAdapter;
import de.mateco.integrAMobile.adapter.ActivityTypeAdapter;
import de.mateco.integrAMobile.adapter.CustomerContactPersonListAdapter;
import de.mateco.integrAMobile.adapter.CustomerSearchResultAdapter;
import de.mateco.integrAMobile.adapter.EmployeeAdapter;
import de.mateco.integrAMobile.adapter.OfferAdapter;
import de.mateco.integrAMobile.adapter.ProjectActivityListAdapter;
import de.mateco.integrAMobile.asyncTask.AsyncTaskWithAuthorizationHeaderPost;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.ActivityTopicModel;
import de.mateco.integrAMobile.model.ActivityTypeModel;
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
import de.mateco.integrAMobile.model.EmployeeModel;
import de.mateco.integrAMobile.model.HintModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LoginPersonModel;
import de.mateco.integrAMobile.model.ProjectActivityUpdateModel;
import de.mateco.integrAMobile.model.ProjectAddActivityModel;
import de.mateco.integrAMobile.model.ProjectDetailActivityModel;
import de.mateco.integrAMobile.model.ProjectDetailGenerallyModel;

public class ProjectDetailActivityFragment extends BaseFragment implements TextView.OnEditorActionListener,View.OnClickListener,CheckBox.OnCheckedChangeListener
{
    private boolean isCallservice=true;
    private ProgressDialog progressDialog;

    String FOCUSED_CUSTOMER_NO="customerno",FOCUSED_KANR="kanrno",FOCUSED_MATCHCODE="matccode",FOCUSED_NAME1="name1",
            FOCUSED_STREET="street",FOCUSED_PLZ="plz",FOCUSED_ORT="ort",FOCUSED_TEL="telephone";
    String lastFocusedvalue="";
    String jsonToSend="";
    AutoCompleteSearchAdapter adapterAutocomplete;
    String kontakt = "",kontaktWhileUpdate="";
    private static String clickedTextboxTime="";
    private boolean isAscending = false;
    private View rootView;
    private DataBaseHandler db;
    private Language language;
    private MatecoPriceApplication matecoPriceApplication;
    private Menu menu;
    private LinearLayout linearLayoutProjectActivityList, linearLayoutProjectActivityListNote;
    private ListView listViewProjectActivityList, listViewProjectActivityEmployee, listProjectActivityContacts;
    private ArrayList<ProjectDetailActivityModel> listOfLoadedProjectActivity;
    private ProjectActivityListAdapter adapterProjectActivityList;
    private Spinner spinnerProjectActivityActivityType, spinnerProjectActivityActivityTopic, spinnerProjectActivityOffer;
    private boolean isEdited=false;
    private ArrayList<ContactPersonModel> listOfSelectedContactPerson, listOfRemainingContactPerson, listOfAllContactPerson,listOfRemainingContactPersontemp;
    private ArrayList<EmployeeModel> listOfSelectedEmployee, listOfRemainingEmployee, listOfAllEmployee;
    private ArrayList<CustomerOfferModel> listOfAllOffer;
    private ArrayList<ActivityTypeModel> listOfActivityType;
    private ArrayList<ActivityTopicModel> listOfActivityTopic;
    private ImageButton imageButtonProjectActivityStartDate, imageButtonStartTime, imageButtonEndTime;
    private CheckBox checkBoxProjectActivityRealized, checkBoxProjectActivityFixedTimes;
    private EmployeeAdapter selectedEmployeeAdapter, remainingEmployeeAdapter;
    private CustomerContactPersonListAdapter selectedContactPersonAdapter, remainingContactPersonAdapter;
    private OfferAdapter offerAdapter;
    private ActivityTypeModel selectedActivityTypeModel;
    private ActivityTopicModel selectedActivityTopicModel;
    private CustomerOfferModel selectedCustomerOfferModel;
    private boolean isInEditMode = false, isInAddMode = false;
    private ActivityTypeAdapter adapterActivityType;
    private ActivityTopicAdapter adapterActivityTopic;
    private TextView labelProjectActivityStartDate;
    private  TextView labelProjectActivityStartTime;
    private  TextView labelProjectActivityEndTime;
    private ImageButton buttonRemoveProjectActivityEmployee, buttonAddProjectActivityEmployee, buttonRemoveProjectActivityContacts,
            buttonAddProjectActivityContacts,buttonAddProjectActivityKunde;
    private EditText textProjectActivityNotes;
    private ProjectDetailActivityModel selectedActivity;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
    private TextView textViewProjectActivityKunde;
    private boolean flag = true,isViewShown;
    DelayAutoCompleteTextView textCustomerSearchCustomerNo, textCustomerSearchKanr,textCustomerSearchMatchCode,textCustomerSearchName1,
            textCustomerSearchRoad, textCustomerSearchZipCode,textCustomerSearchPlace, textCustomerSearchTel;
    TextView labelCustomerSearchCustomerNo, labelCustomerSearchKanr, labelCustomerSearchMatchCode,labelCustomerSearchName1,
            labelCustomerSearchRoad, labelCustomerSearchZipCode, labelCustomerSearchPlace, labelCustomerSearchTel;
    ListView listCustomerSearchResult;
    ArrayList<CustomerModel> listOfCustomerSearchResult;
    CustomerSearchResultAdapter adapter;
    int pageNuber = 1;
    int customerCount = 0;
    int totalPageCount = 1;
    int pageSize = 10;
    View footerView;
    Button footerButton;
    private ImageView imageSortDate, imageSortDesignation, imageSortRealized;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_project_search_activity, container, false);

//        View rootView = inflater.inflate(R.layout.temp_under_construction, container, false);
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
    public void initializeComponents(View rootView)
    {
        super.initializeComponents(rootView);
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        db = new DataBaseHandler(getActivity());

        imageSortDate = (ImageView)rootView.findViewById(R.id.imageSortDate);
        imageSortDesignation = (ImageView)rootView.findViewById(R.id.imageSortDesination);
        imageSortRealized = (ImageView)rootView.findViewById(R.id.imageSortRealized);

        imageSortDate.setOnClickListener(this);
        imageSortDesignation.setOnClickListener(this);
        imageSortRealized.setOnClickListener(this);

        linearLayoutProjectActivityList = (LinearLayout)rootView.findViewById(R.id.linearLayoutProjectActivityList);
        linearLayoutProjectActivityListNote = (LinearLayout)rootView.findViewById(R.id.linearLayoutProjectActivityListNote);
        listViewProjectActivityList = (ListView)rootView.findViewById(R.id.listViewProjectActivityList);
        listOfLoadedProjectActivity = new ArrayList<>();

        listViewProjectActivityEmployee = (ListView)rootView.findViewById(R.id.listViewProjectActivityEmployee);
        listProjectActivityContacts = (ListView)rootView.findViewById(R.id.listProjectActivityContacts);

        listOfLoadedProjectActivity = matecoPriceApplication.getLoadedProjectActivities(DataHelper.LoadedProjectDetailActivityInfo, new ArrayList<>().toString());

        adapterProjectActivityList = new ProjectActivityListAdapter(getActivity(), listOfLoadedProjectActivity, R.layout.list_item_project_activity);
        listViewProjectActivityList.setAdapter(adapterProjectActivityList);

        spinnerProjectActivityActivityType = (Spinner)rootView.findViewById(R.id.spinnerProjectActivityActivityType);
        spinnerProjectActivityActivityTopic = (Spinner)rootView.findViewById(R.id.spinnerProjectActivityActivityTopic);

        spinnerProjectActivityOffer = (Spinner)rootView.findViewById(R.id.spinnerProjectActivityOffer);

        listOfAllEmployee = new ArrayList<>();
        listOfSelectedEmployee = new ArrayList<>();
        listOfRemainingEmployee = new ArrayList<>();

        listOfSelectedContactPerson = new ArrayList<>();
        listOfRemainingContactPerson = new ArrayList<>();
        listOfAllContactPerson = new ArrayList<>();

        listOfAllOffer = new ArrayList<>();
        listOfActivityTopic = new ArrayList<>();
        listOfActivityType = new ArrayList<>();
        //listOfAllContactPerson = matecoPriceApplication.getLoadedCustomerContactPersons(DataHelper.LoadedCustomerContactPerson, new ArrayList<ContactPersonModel>().toString());
        listOfActivityType = db.getActivityTypes();
        listOfActivityTopic = db.getActivityTopics();
        listOfAllEmployee = db.getEmployees();

        adapterActivityType = new ActivityTypeAdapter(getActivity(), listOfActivityType, R.layout.list_item_spinner_activity_type, language);
        adapterActivityTopic = new ActivityTopicAdapter(getActivity(), listOfActivityTopic, R.layout.list_item_spinner_activity_topic, language);

        selectedEmployeeAdapter = new EmployeeAdapter(getActivity(), listOfSelectedEmployee, R.layout.list_item_employee);
        remainingEmployeeAdapter = new EmployeeAdapter(getActivity(), listOfRemainingEmployee, R.layout.list_item_employee);

        spinnerProjectActivityActivityType.setAdapter(adapterActivityType);
        spinnerProjectActivityActivityTopic.setAdapter(adapterActivityTopic);

        imageButtonProjectActivityStartDate = (ImageButton)rootView.findViewById(R.id.imageButtonProjectActivityStartDate);
        imageButtonProjectActivityStartDate.setOnClickListener(this);
        labelProjectActivityStartDate = (TextView)rootView.findViewById(R.id.labelProjectActivityStartDate);

        imageButtonProjectActivityStartDate = (ImageButton)rootView.findViewById(R.id.imageButtonProjectActivityStartDate);

        labelProjectActivityStartTime = (TextView)rootView.findViewById(R.id.labelProjectActivityStartTime);

        imageButtonEndTime = (ImageButton)rootView.findViewById(R.id.imageButtonEndTime);
        imageButtonStartTime = (ImageButton)rootView.findViewById(R.id.imageButtonStartTime);

        labelProjectActivityEndTime = (TextView)rootView.findViewById(R.id.labelProjectActivityEndTime);

        checkBoxProjectActivityRealized = (CheckBox)rootView.findViewById(R.id.checkBoxProjectActivityRealized);
        checkBoxProjectActivityFixedTimes = (CheckBox)rootView.findViewById(R.id.checkBoxProjectActivityFixedTimes);

        buttonRemoveProjectActivityEmployee = (ImageButton)rootView.findViewById(R.id.buttonRemoveProjectActivityEmployee);
        buttonAddProjectActivityEmployee = (ImageButton)rootView.findViewById(R.id.buttonAddProjectActivityEmployee);
        buttonRemoveProjectActivityContacts = (ImageButton)rootView.findViewById(R.id.buttonRemoveProjectActivityContacts);
        buttonAddProjectActivityContacts = (ImageButton)rootView.findViewById(R.id.buttonAddProjectActivityContacts);

        buttonAddProjectActivityKunde = (ImageButton)rootView.findViewById(R.id.buttonAddProjectActivityKunde);

        selectedContactPersonAdapter = new CustomerContactPersonListAdapter(getActivity(), listOfSelectedContactPerson, R.layout.list_item_customer_contact_person);
        remainingContactPersonAdapter = new CustomerContactPersonListAdapter(getActivity(), listOfRemainingContactPerson, R.layout.list_item_customer_contact_person);

        listProjectActivityContacts.setAdapter(selectedContactPersonAdapter);

        listViewProjectActivityEmployee.setAdapter(selectedEmployeeAdapter);
        setListViewHeightBasedOnChildren(listViewProjectActivityEmployee);

        //listOfAllOffer.addAll(matecoPriceApplication.getLoadedCustomerOffers(DataHelper.LoadedCustomerOffer, new ArrayList<CustomerOfferModel>().toString()));
        offerAdapter = new OfferAdapter(getActivity(), listOfAllOffer, R.layout.list_item_offer, language);

        spinnerProjectActivityOffer.setAdapter(offerAdapter);

        textProjectActivityNotes = (EditText)rootView.findViewById(R.id.textProjectActivityNotes);

        selectedActivityTypeModel = new ActivityTypeModel();
        selectedActivityTopicModel = new ActivityTopicModel();
        selectedCustomerOfferModel = new CustomerOfferModel();
        setLanguage();

        if(listOfLoadedProjectActivity.size() > 0)
        {
            /*if(isViewShown)
            {*/
                adapterProjectActivityList.setSelectedIndex(0);
                selectedActivity = listOfLoadedProjectActivity.get(0);
                setProjectActivity(selectedActivity);
            //}
        }
        makeEditable(false);
        getActivity().invalidateOptionsMenu();
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelProject());
        setHasOptionsMenu(true);

        labelProjectActivityStartDate.setOnClickListener(this);
    }

    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isViewShown = true;
        } else {
            isViewShown = false;
        }
    }*/
    @Override
    public void bindEvents(View rootView)
    {
        super.bindEvents(rootView);

        listViewProjectActivityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterProjectActivityList.setSelectedIndex(position);
                selectedActivity = listOfLoadedProjectActivity.get(position);
                setProjectActivity(selectedActivity);
                kontaktWhileUpdate = selectedActivity.getKontakt();
            }
        });
        listViewProjectActivityEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedEmployeeAdapter.setSelectedIndex(position);
            }
        });

        spinnerProjectActivityActivityType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        spinnerProjectActivityActivityTopic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
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
        spinnerProjectActivityOffer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //selectedLegalForm = listOfLegalForm.get(i);
                    selectedCustomerOfferModel = null;
                } else {
                    selectedCustomerOfferModel = listOfAllOffer.get(position - 1);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listProjectActivityContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //view.setSelected(true);
                selectedContactPersonAdapter.setSelectedIndex(position);
            }
        });

        listViewProjectActivityEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //view.setSelected(true);
                selectedEmployeeAdapter.setSelectedIndex(position);
            }
        });
    }

    private void setLanguage()
    {
        TextView labelProjectActivityDate, labelProjectActivityDesignation, labelProjectActivityRealized,
                labelProjectActivityMemo, labelProjectActivityDetailActivity, labelProjectActivityDetailTopic,
                labelProjectActivityDetailStartDate, labelProjectActivityDetailStartTime, labelProjectActivityDetailEndTime,
                labelProjectActivityDetailOffer, labelProjectActivityDetailRealized,
                labelProjectActivityDetailFixedTimes, labelProjectActivityDetailContacts, labelProjectActivityDetailEmployee,labelProjectActivityDetailKunde;
        //, labelProjectActivityDetailAnsprechPhone, labelProjectActivityDetailAnsprechMobile

        labelProjectActivityDate = (TextView)rootView.findViewById(R.id.labelProjectActivityDate);
        labelProjectActivityDate.setOnClickListener(this);
        labelProjectActivityDesignation = (TextView)rootView.findViewById(R.id.labelProjectActivityDesignation);
        labelProjectActivityDesignation.setOnClickListener(this);
        labelProjectActivityRealized = (TextView)rootView.findViewById(R.id.labelProjectActivityRealized);
        labelProjectActivityRealized.setOnClickListener(this);
        labelProjectActivityMemo = (TextView)rootView.findViewById(R.id.labelProjectActivityMemo);
        labelProjectActivityDetailActivity = (TextView)rootView.findViewById(R.id.labelProjectActivityDetailActivity);
        labelProjectActivityDetailTopic = (TextView)rootView.findViewById(R.id.labelProjectActivityDetailTopic);
        labelProjectActivityDetailStartDate = (TextView)rootView.findViewById(R.id.labelProjectActivityDetailStartDate);
        labelProjectActivityDetailStartTime = (TextView)rootView.findViewById(R.id.labelProjectActivityDetailStartTime);
        labelProjectActivityDetailEndTime = (TextView)rootView.findViewById(R.id.labelProjectActivityDetailEndTime);
        labelProjectActivityDetailOffer = (TextView)rootView.findViewById(R.id.labelProjectActivityDetailOffer);
        labelProjectActivityDetailRealized = (TextView)rootView.findViewById(R.id.labelProjectActivityDetailRealized);
        labelProjectActivityDetailFixedTimes = (TextView)rootView.findViewById(R.id.labelProjectActivityDetailFixedTimes);
        labelProjectActivityDetailContacts = (TextView)rootView.findViewById(R.id.labelProjectActivityDetailContacts);
//        labelProjectActivityDetailAnsprechPhone = (TextView)rootView.findViewById(R.id.labelProjectActivityDetailAnsprechPhone);
//        labelProjectActivityDetailAnsprechMobile = (TextView)rootView.findViewById(R.id.labelProjectActivityDetailAnsprechMobile);
        labelProjectActivityDetailEmployee = (TextView)rootView.findViewById(R.id.labelProjectActivityDetailEmployee);
        labelProjectActivityDetailKunde = (TextView)rootView.findViewById(R.id.labelProjectActivityDetailKunde);
        textViewProjectActivityKunde = (TextView)rootView.findViewById(R.id.textViewProjectActivityKunde);

        labelProjectActivityDate.setText(language.getLabelDate());
        labelProjectActivityDesignation.setText(language.getLabelDesignation());
        labelProjectActivityRealized.setText(language.getLabelRealized());
        labelProjectActivityDetailActivity.setText(language.getLabelActivity());
        labelProjectActivityDetailTopic.setText(language.getLabelTopic());
        labelProjectActivityDetailStartDate.setText(language.getLabelDate());
        labelProjectActivityDetailStartTime.setText(language.getLabelStartTime());
        labelProjectActivityDetailEndTime.setText(language.getLabelEndTime());
        labelProjectActivityDetailOffer.setText(language.getLabelOffer());
        labelProjectActivityDetailRealized.setText(language.getLabelRealized());
        labelProjectActivityDetailFixedTimes.setText(language.getLabelFixedTimes());
        labelProjectActivityDetailContacts.setText(language.getLabelContacts());
//        labelProjectActivityDetailAnsprechPhone.setText(language.getLabelPhone());
//        labelProjectActivityDetailAnsprechMobile.setText(language.getLabelMobile());
        labelProjectActivityDetailEmployee.setText(language.getLabelEmployee());
        labelProjectActivityMemo.setText(language.getLabelMemo());
        labelProjectActivityDetailKunde.setText(language.getLabelCustomer());
    }

    private void makeEditable(boolean isEditable)
    {
        listViewProjectActivityList.setEnabled(!isEditable);
        spinnerProjectActivityActivityType.setEnabled(isEditable);
        spinnerProjectActivityActivityTopic.setEnabled(isEditable);
        checkBoxProjectActivityRealized.setEnabled(isEditable);
        checkBoxProjectActivityFixedTimes.setEnabled(isEditable);

//        textProjectActivityContactPersonMobile.setFocusable(editable);
//        textProjectActivityContactPersonMobile.setFocusableInTouchMode(editable);
//
//        textProjectActivityContactPersonPhone.setFocusable(editable);
//        textProjectActivityContactPersonPhone.setFocusableInTouchMode(editable);

        textProjectActivityNotes.setFocusable(isEditable);
        textProjectActivityNotes.setFocusableInTouchMode(isEditable);
        if(isEditable)
        {
            imageButtonProjectActivityStartDate.setOnClickListener(this);
            buttonAddProjectActivityKunde.setOnClickListener(this);
            checkBoxProjectActivityRealized.setOnCheckedChangeListener(this);
            labelProjectActivityStartDate.setOnClickListener(this);
            labelProjectActivityStartTime.setOnClickListener(this);
            imageButtonEndTime.setOnClickListener(this);
            imageButtonStartTime.setOnClickListener(this);
            labelProjectActivityEndTime.setOnClickListener(this);
            buttonRemoveProjectActivityEmployee.setOnClickListener(this);
            buttonAddProjectActivityEmployee.setOnClickListener(this);
            buttonRemoveProjectActivityContacts.setOnClickListener(this);
            buttonAddProjectActivityContacts.setOnClickListener(this);
//            buttonRemoveProjectActivityProject.setOnClickListener(this);
//            buttonAddProjectActivityProject.setOnClickListener(this);
//            buttonRemoveProjectActivityOffer.setOnClickListener(this);
//            buttonAddProjectActivityOffer.setOnClickListener(this);
        }
        else
        {
            imageButtonProjectActivityStartDate.setOnClickListener(null);
            labelProjectActivityStartDate.setOnClickListener(null);
            buttonAddProjectActivityKunde.setOnClickListener(null);
            checkBoxProjectActivityRealized.setOnCheckedChangeListener(null);
            labelProjectActivityStartTime.setOnClickListener(null);
            imageButtonEndTime.setOnClickListener(null);
            imageButtonStartTime.setOnClickListener(null);
            labelProjectActivityEndTime.setOnClickListener(null);
            buttonRemoveProjectActivityEmployee.setOnClickListener(null);
            buttonAddProjectActivityEmployee.setOnClickListener(null);
            buttonRemoveProjectActivityContacts.setOnClickListener(null);
            buttonAddProjectActivityContacts.setOnClickListener(null);
//            buttonRemoveProjectActivityProject.setOnClickListener(null);
//            buttonAddProjectActivityProject.setOnClickListener(null);
//            buttonRemoveProjectActivityOffer.setOnClickListener(null);
//            buttonAddProjectActivityOffer.setOnClickListener(null);
        }
        //listViewProjectActivityOffer.setEnabled(editable);
        spinnerProjectActivityOffer.setEnabled(isEditable);
        listProjectActivityContacts.setEnabled(isEditable);
        //listViewProjectActivityProject.setEnabled(editable);
        listViewProjectActivityEmployee.setEnabled(isEditable);
        if(isEditable)
        {
            linearLayoutProjectActivityList.setVisibility(View.GONE);
            linearLayoutProjectActivityListNote.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        else
        {
            linearLayoutProjectActivityList.setVisibility(View.VISIBLE);
            linearLayoutProjectActivityListNote.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        isInEditMode = isEditable;
    }

    private void setProjectActivity(final ProjectDetailActivityModel activity)
    {
        clearAllValues();
        listOfAllContactPerson.clear();
        listOfSelectedContactPerson.clear();
        listOfRemainingContactPerson.clear();
        listOfAllOffer.clear();

        if(DataHelper.isNetworkAvailable(getActivity()))
        {
            if(activity != null)
            {

                boolean isActivityTypeSelected = false;
                for(int i = 0; i < listOfActivityType.size(); i++)
                {
                    if(activity.getAkttypID().equals(listOfActivityType.get(i).getActivityTypeId()))
                    {
                        spinnerProjectActivityActivityType.setSelection(i + 1);
                        selectedActivityTypeModel = listOfActivityType.get(i);
                        isActivityTypeSelected = true;
                        //selectedLegalForm = listOfLegalForm.get(i);
                        break;
                    }
                }
                if(!isActivityTypeSelected)
                {
                    spinnerProjectActivityActivityType.setSelection(0);
                }
                boolean isActivityTopicSelected = false;
                for(int i = 0; i < listOfActivityTopic.size(); i++)
                {
                    if(activity.getAktthemaID().equals(listOfActivityTopic.get(i).getActivityTopicId()))
                    {
                        spinnerProjectActivityActivityTopic.setSelection(i + 1);
                        selectedActivityTopicModel = listOfActivityTopic.get(i);
                        isActivityTopicSelected = true;
                        break;
                    }
                }
                if(!isActivityTopicSelected)
                {
                    spinnerProjectActivityActivityTopic.setSelection(0);
                }
                boolean isActivityOfferSelected = false;
                for(int i = 0; i < listOfAllOffer.size(); i++)
                {
                    if(activity.getAngebot().equals(listOfAllOffer.get(i).getOfferno()))
                    {
                        spinnerProjectActivityOffer.setSelection(i + 1);
                        selectedCustomerOfferModel = listOfAllOffer.get(i);
                        isActivityOfferSelected = true;
                        //selectedLegalForm = listOfLegalForm.get(i);
                        break;
                    }
                }
                if(!isActivityOfferSelected)
                {
                    spinnerProjectActivityOffer.setSelection(0);
                }
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try
                {
                    labelProjectActivityStartDate.setText(DataHelper.formatDate(format.parse(activity.getStartdatum())));
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
                for(int i = 0; i < activity.getListOfEmployee().size(); i++)
                {
                    for(int j = 0; j < listOfAllEmployee.size(); j++)
                    {
                        if(activity.getListOfEmployee().get(i).getMitarbeiter().equals(listOfAllEmployee.get(j).getMitarbeiter()))
                        {
                            listOfSelectedEmployee.add(listOfAllEmployee.get(j));
                            break;
                        }
                    }
                }
                setListViewHeightBasedOnChildren(listViewProjectActivityEmployee);
                if(!activity.getStartzeit().equals("00:00"))
                    labelProjectActivityStartTime.setText(DataHelper.formatTime(activity.getStartzeit()));
                if(!activity.getEndzeit().equals("00:00"))
                    labelProjectActivityEndTime.setText(DataHelper.formatTime(activity.getEndzeit()));
                textProjectActivityNotes.setText(activity.getNotiz());
                //Toast.makeText(getActivity(),"first naemr : "+activity.getFirstName(),Toast.LENGTH_LONG).show();

                if(!TextUtils.isEmpty(activity.getCustomerName())){
                    textViewProjectActivityKunde.setText(activity.getCustomerName());
                }
                /*CustomerDatabaseModel customerModelTemp = db.getCustomer(activity.getKontakt());
                //textViewProjectActivityKunde.setText(activity.getKontakt());

                if(customerModelTemp != null && !TextUtils.isEmpty(customerModelTemp.getName1()))
                {
                    textViewProjectActivityKunde.setText(customerModelTemp.getName1());
                }
                else {
                    textViewProjectActivityKunde.setText(activity.getKontakt());
                }*/

                if(Boolean.parseBoolean(activity.getRealisiert()))
                {
                    checkBoxProjectActivityRealized.setChecked(true);
                }
                if(activity.getFest().length() > 0 && Boolean.parseBoolean(activity.getFest()))
                {
                    checkBoxProjectActivityFixedTimes.setChecked(true);
                }

                //SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try
                {
                    labelProjectActivityStartDate.setText(DataHelper.formatDate(format.parse(activity.getStartdatum())));
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }

                if(!activity.getKontakt().equals(""))
                {
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
                                    listOfAllContactPerson.addAll(customerFullDetail.getCustomerContactPersonList());
                                    for(int i = 0; i < activity.getListOfContact().size(); i++)
                                    {
                                        for(int j = 0; j < listOfAllContactPerson.size(); j++)
                                        {
                                            if(activity.getListOfContact().get(i).getAnsPartner().equals(listOfAllContactPerson.get(j).getAnspartner()))
                                            {

                                                listOfSelectedContactPerson.add(listOfAllContactPerson.get(j));
                                                //Toast.makeText(getActivity()," first name : "+listOfAllContactPerson.get(j).getNachname()+" "+
                                                        //listOfAllContactPerson.get(j).getVorname(),

                                                //textViewProjectActivityKunde.setText(listOfAllContactPerson.get(j).getKontakt);
                                                //textViewProjectActivityKunde.setText(listOfAllContactPerson.get(j).getNachname()+" "+
                                                        //listOfAllContactPerson.get(j).getVorname());
                                                break;
                                            }
                                        }
                                    }
                                    setListViewHeightBasedOnChildren(listProjectActivityContacts);
                                    listOfAllOffer.addAll(customerFullDetail.getCustomerActivityListt().getListOfOffers());
                                    for(int j = 0; j < listOfAllOffer.size(); j++)
                                    {
                                        if(activity.getAngebot().equals(listOfAllOffer.get(j).getOfferno()))
                                        {
                                            spinnerProjectActivityOffer.setSelection(j+1);
                                            break;
                                        }
                                    }
                                }
                                catch (Exception ex)
                                {
                                 //   showShortToast(language.getMessageErrorAtParsing());
                                }
                            }
                        }
                    };
                    try
                    {
                        String url = DataHelper.URL_AGENDA_HELPER + "agendacustomershow" //+ DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.AGENDA_CUSTOMER_SHOW
                                + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                                + "/fieldname=" + "Kontakt"
                                + "/value=" + activity.getKontakt();
                        /*String url = DataHelper.ACCESS_PROTOCOL+DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.AGENDA_CUSTOMER_SHOW
                                + "&token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                                + "&fieldname=" + "Kontakt"
                                + "&value=" + activity.getKontakt();*/
                        BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                        asyncTask.execute();
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        }
        else
        {
            showShortToast(language.getMessageNetworkNotAvailable());
        }
    }

    private void clearAllValues()
    {
        textViewProjectActivityKunde.setText("");
        listOfSelectedEmployee.clear();
        listOfRemainingEmployee.addAll(listOfAllEmployee);
        selectedActivityTypeModel = null;
        selectedActivityTopicModel = null;
        selectedCustomerOfferModel = null;
        selectedEmployeeAdapter.notifyDataSetChanged();
        spinnerProjectActivityOffer.setSelection(0);
        spinnerProjectActivityActivityTopic.setSelection(0);
        spinnerProjectActivityActivityType.setSelection(0);

        adapterActivityTopic.notifyDataSetChanged();
        adapterActivityType.notifyDataSetChanged();
        offerAdapter.notifyDataSetChanged();

        listOfSelectedContactPerson.clear();
        listOfRemainingContactPerson.addAll(listOfAllContactPerson);
        selectedContactPersonAdapter.notifyDataSetChanged();
        offerAdapter.notifyDataSetChanged();
        checkBoxProjectActivityRealized.setChecked(false);
        checkBoxProjectActivityFixedTimes.setChecked(false);
        labelProjectActivityStartTime.setText("");
        labelProjectActivityStartDate.setText("");
        labelProjectActivityEndTime.setText("");
        textProjectActivityNotes.setText("");
        //super.clearAll();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.labelProjectActivityStartDate:
                openDatePicker();
                break;
            case R.id.imageButtonProjectActivityStartDate:
                openDatePicker();
                break;
            case R.id.labelProjectActivityStartTime:
                //openTimePicker(listenerStartTime, "start");&&&&&
                clickedTextboxTime="first";
                DialogFragment newFragment = new SelectTimeFragment();
                newFragment.show(getActivity().getFragmentManager(), "TimePicker");
                break;
            case R.id.labelProjectActivityEndTime:
                //openTimePicker(listenerEndTime, "end");
                if(labelProjectActivityStartTime.getText().toString().equals(""))
                {
                    showShortToast(language.getMessageSelectStartTime());
                }
                else{
                    clickedTextboxTime="second";
                    DialogFragment newFragment2 = new SelectTimeFragment();
                    newFragment2.show(getActivity().getFragmentManager(), "TimePicker");
                }

                break;
            case R.id.imageButtonEndTime:

                if(labelProjectActivityStartTime.getText().toString().equals(""))
                {
                    showShortToast(language.getMessageSelectStartTime());
                }
                else {
                    clickedTextboxTime="second";
                    DialogFragment newFragment3 = new SelectTimeFragment();
                    newFragment3.show(getActivity().getFragmentManager(), "TimePicker");
                }
                //openTimePicker(listenerEndTime, "end");
                break;
            case R.id.imageButtonStartTime:
                //openTimePicker(listenerStartTime, "start");
                clickedTextboxTime="first";
                DialogFragment newFragment4 = new SelectTimeFragment();
                newFragment4.show(getActivity().getFragmentManager(), "TimePicker");
                break;
            case R.id.buttonRemoveProjectActivityEmployee:
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
            case R.id.buttonAddProjectActivityEmployee:
                showAddEmployeeDialog();
                break;
            case R.id.buttonRemoveProjectActivityContacts:
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
            case R.id.buttonAddProjectActivityContacts:
                showAddContactPersonDialog();
                break;
            case R.id.buttonAddProjectActivityKunde:
                showCustomerDialog();
                break;
            case  R.id.labelProjectActivityDate:
                if(imageSortDate.getVisibility() != View.GONE && !isAscending)
                {
                    //((TextView)rootView.findViewById(R.id.labelAgendaWeeklyDueName)).setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.ic_sort_asc_24dp);
                    imageSortDate.setImageResource(R.drawable.ic_sort_asc_24dp);
                    Collections.sort(listOfLoadedProjectActivity, new ProjectActivityComparable(1, 0));
                    isAscending = true;
                }
                else
                {
                    imageSortDate.setImageResource(R.drawable.ic_sort_dsc_24dp);
                    Collections.sort(listOfLoadedProjectActivity, new ProjectActivityComparable(1, 1));
                    imageSortDate.setVisibility(View.VISIBLE);
                    isAscending = false;
                }
                imageSortDesignation.setVisibility(View.GONE);
                imageSortRealized.setVisibility(View.GONE);

                adapterProjectActivityList.notifyDataSetChanged();
                /*Collections.sort(listOfLoadedProjectActivity, new Comparator<ProjectDetailActivityModel>() {
                    @Override
                    public int compare(ProjectDetailActivityModel p1, ProjectDetailActivityModel p2)
                    {
                        return p1.getStartdatum().compareToIgnoreCase(p2.getStartdatum());
                    }
                });
                adapterProjectActivityList.notifyDataSetChanged();*/
                break;
            case  R.id.labelProjectActivityDesignation:
                if(imageSortDesignation.getVisibility() != View.GONE && !isAscending)
                {
                    //((TextView)rootView.findViewById(R.id.labelAgendaWeeklyDueName)).setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.ic_sort_asc_24dp);
                    imageSortDesignation.setImageResource(R.drawable.ic_sort_asc_24dp);
                    Collections.sort(listOfLoadedProjectActivity, new ProjectActivityComparable(2, 0));
                    isAscending = true;
                }
                else
                {
                    imageSortDesignation.setImageResource(R.drawable.ic_sort_dsc_24dp);
                    Collections.sort(listOfLoadedProjectActivity, new ProjectActivityComparable(2, 1));
                    imageSortDesignation.setVisibility(View.VISIBLE);
                    isAscending = false;
                }
                imageSortDate.setVisibility(View.GONE);
                imageSortRealized.setVisibility(View.GONE);
                adapterProjectActivityList.notifyDataSetChanged();
                /*Collections.sort(listOfLoadedProjectActivity, new Comparator<ProjectDetailActivityModel>() {
                    @Override
                    public int compare(ProjectDetailActivityModel p1, ProjectDetailActivityModel p2)
                    {
                        return p1.getAktivitatstyp().compareToIgnoreCase(p2.getAktivitatstyp());
                    }
                });
                adapterProjectActivityList.notifyDataSetChanged();*/
                break;
            case  R.id.labelProjectActivityRealized:
                if(imageSortRealized.getVisibility() != View.GONE && !isAscending)
                {
                    //((TextView)rootView.findViewById(R.id.labelAgendaWeeklyDueName)).setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.ic_sort_asc_24dp);
                    imageSortRealized.setImageResource(R.drawable.ic_sort_asc_24dp);
                    Collections.sort(listOfLoadedProjectActivity, new ProjectActivityComparable(3, 0));
                    isAscending = true;
                }
                else
                {
                    imageSortRealized.setImageResource(R.drawable.ic_sort_dsc_24dp);
                    Collections.sort(listOfLoadedProjectActivity, new ProjectActivityComparable(3, 1));
                    imageSortRealized.setVisibility(View.VISIBLE);
                    isAscending = false;
                }
                imageSortDesignation.setVisibility(View.GONE);
                imageSortDate.setVisibility(View.GONE);
                adapterProjectActivityList.notifyDataSetChanged();
                /*Collections.sort(listOfLoadedProjectActivity, new Comparator<ProjectDetailActivityModel>() {
                    @Override
                    public int compare(ProjectDetailActivityModel p1, ProjectDetailActivityModel p2)
                    {
                        return p1.getRealisiert().compareToIgnoreCase(p2.getRealisiert());
                    }
                });
                adapterProjectActivityList.notifyDataSetChanged();*/
                break;


        }
    }

    private void showAddContactPersonDialog() {
        final Dialog dialog = showCustomDialog(R.layout.dialog_add_employee, language.getMessageSelectContactPerson());

        if(isEdited){
            if(listOfRemainingContactPersontemp != null && listOfRemainingContactPersontemp.size() > 0){
                listOfAllContactPerson.clear();
                listOfRemainingContactPerson.clear();
                listOfRemainingContactPerson.addAll(listOfRemainingContactPersontemp);
                listOfAllContactPerson.addAll(listOfRemainingContactPersontemp);
                listOfRemainingContactPersontemp.clear();
            }
            else {
                listOfRemainingContactPerson.clear();
                listOfRemainingContactPerson.addAll(removeSelectedContactPerson(listOfAllContactPerson, listOfSelectedContactPerson));
            }
        }
        else {
            listOfRemainingContactPerson.clear();
            listOfRemainingContactPerson.addAll(removeSelectedContactPerson(listOfAllContactPerson, listOfSelectedContactPerson));
        }



        ListView listViewAlertSelectFeature = (ListView)dialog.findViewById(R.id.listViewAlertSelectEmployee);
        listViewAlertSelectFeature.setAdapter(remainingContactPersonAdapter);
        remainingContactPersonAdapter.notifyDataSetChanged();
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
                remainingContactPersonAdapter.setSelectedIndex(position);
            }
        });
        buttonDialogAddEmployee.setOnClickListener(new View.OnClickListener()
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
                    setListViewHeightBasedOnChildren(listProjectActivityContacts);
                    remainingContactPersonAdapter.setSelectedIndex(-1);
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
        if(listOfAllContactPerson.size() > 0)
        {
            dialog.show();
        }
        else
        {
            showLongToast(language.getMessageNoContactPersons());
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
        newFragment.setMinDate(today);
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
            if(labelProjectActivityStartTime.getText().toString().equals(""))
            {
                showShortToast(language.getMessageSelectStartTime());
            }
            else
            {
                SimpleDateFormat outputFormatter = new SimpleDateFormat("kk:mm");
                try {
                    Date date = outputFormatter.parse(labelProjectActivityStartTime.getText().toString());
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
                labelProjectActivityStartDate.setText(finaldate);
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
            labelProjectActivityStartTime.setText(time);
        }

    };

    TimePickerDialog.OnTimeSetListener listenerEndTime = new TimePickerDialog.OnTimeSetListener()
    {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
            String endTime = String.format("%02d:%02d", hourOfDay, minute);
            Date inTime = null;
            try
            {
                inTime = sdf.parse(labelProjectActivityStartTime.getText().toString());
                Date outTime = sdf.parse(endTime);

                /*if(checktimings(inTime.toString(),outTime.toString())){
                    labelProjectActivityEndTime.setText(endTime);
                }
                else{
                    showShortToast(language.getMessageEndTimeGreaterThenStartTime());
                }*/
                if(inTime.compareTo(outTime) > 0)
                {
                    showShortToast(language.getMessageEndTimeGreaterThenStartTime());
                }
                else
                {
                    labelProjectActivityEndTime.setText(endTime);
                }
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
    };
    public  class SelectTimeFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
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
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.UK);
            if(!TextUtils.isEmpty(clickedTextboxTime)){
                String time = null;
                Date inTime= null;
                Date outTime = null;
                if(clickedTextboxTime.equalsIgnoreCase("first")){


                    try
                    {
                        time = pad(Hour)+":"+pad(Minute);
                        inTime = sdf.parse(time);
                        //outTime = sdf.parse(labelProjectActivityEndTime.getText().toString());
                    }
                    catch (ParseException e)
                    {
                        e.printStackTrace();
                    }
                    /*if(inTime.compareTo(outTime) > 0)
                    {
                        showShortToast(language.getMessageEndTimeGreaterThenStartTime());
                    }
                    else
                    {
                        labelProjectActivityStartTime.setText(time);
                    }*/
                    labelProjectActivityStartTime.setText(time);


                }
                else {
                    String endTime=null;
                    try
                    {
                        endTime = pad(Hour)+":"+pad(Minute);
                        inTime = sdf.parse(labelProjectActivityStartTime.getText().toString());
                        outTime = sdf.parse(endTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(inTime.compareTo(outTime) > 0)
                    {
                        showShortToast(language.getMessageEndTimeGreaterThenStartTime());
                    }
                    else
                    {
                        labelProjectActivityEndTime.setText(endTime);
                    }

                    //textviewHourEnd.setText(new StringBuilder().append(pad(Hour))+":"+new StringBuilder().append(pad(Minute)));
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
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
    private boolean checktimings(String time, String endtime) {
        Boolean flag=false;
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date starttime = sdf.parse(time);
            Date end = sdf.parse(endtime);

            if(starttime.before(end)) {
                flag=true;
            } else {

                flag=false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return flag;
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
    private void showAddEmployeeDialog()
    {
        final Dialog dialog = showCustomDialog(R.layout.dialog_add_employee, language.getMessageSelectEmployee());
        listOfRemainingEmployee.clear();
        listOfRemainingEmployee.addAll(removeSelectedEmployee(listOfAllEmployee, listOfSelectedEmployee));
        Collections.sort(listOfRemainingEmployee, new Comparator<EmployeeModel>() {
            @Override
            public int compare(EmployeeModel s1, EmployeeModel s2) {
                return s1.getNachname().compareToIgnoreCase(s2.getNachname());
            }
        });
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
                    setListViewHeightBasedOnChildren(listViewProjectActivityEmployee);
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

    private ArrayList<EmployeeModel> removeSelectedEmployee(ArrayList<EmployeeModel> listOfEmployee, ArrayList<EmployeeModel> selectedEmployee)
    {
        ArrayList<EmployeeModel> tempList = new ArrayList<>();
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
        //listOfRemainingEmployee.clear();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);
        this.menu = menu;
        menu.findItem(R.id.actionSearch).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);
        menu.findItem(R.id.actionWrong).setVisible(false);
        menu.findItem(R.id.actionRight).setVisible(false);
        /*menu.findItem(R.id.actionEdit).setVisible(false);
        menu.findItem(R.id.actionAdd).setVisible(false);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.actionSettings:
                isEdited=false;
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new SettingFragment(),"Setting");
                //transaction.addToBackStack(SettingFragment.Tag);
                transaction.addToBackStack("Setting");
                transaction.commit();
                return true;
            case R.id.actionBack:
                isEdited=false;
                getActivity().onBackPressed();
                return true;
            case R.id.actionEdit:
                if(listOfLoadedProjectActivity.size() > 0)
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
                listOfAllContactPerson.clear();
                listOfSelectedContactPerson.clear();
                selectedContactPersonAdapter.notifyDataSetChanged();
                for(int i=0;i<listOfAllEmployee.size();i++)
                {
                    if(listOfAllEmployee.get(i).getMitarbeiter().equals(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new ArrayList<LoginPersonModel>().toString()).get(0).getUserNumber()))
                    {
                        listOfSelectedEmployee.add(listOfAllEmployee.get(i));
                        selectedEmployeeAdapter.notifyDataSetChanged();
                    }

                }
                menu.findItem(R.id.actionAdd).setVisible(false);
                menu.findItem(R.id.actionEdit).setVisible(false);
                menu.findItem(R.id.actionRight).setVisible(true);
                menu.findItem(R.id.actionWrong).setVisible(true);
                menu.findItem(R.id.actionBack).setVisible(false);
                addNewActivityFormRedesign(true);
                makeEditable(true);
                return true;
            case R.id.actionWrong:

                isEdited=false;
                if(isInAddMode)
                {
                    DialogInterface.OnClickListener positiveCallback = new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            addNewActivityFormRedesign(false);
                            setProjectActivity(selectedActivity);
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
                else if(isInEditMode)
                {
                    addNewActivityFormRedesign(false);
                    setProjectActivity(selectedActivity);
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
                return true;
            case R.id.actionRight:

                isEdited=false;
                if(isInAddMode)
                {
                    addNewProjectActivity();
                }
                else
                {
                    if(adapterProjectActivityList.getSelectedIndex() != -1)
                        updateProjectActivityInfo();
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
        checkBoxProjectActivityRealized.setEnabled(true);
        if(editable)
        {
            linearLayoutProjectActivityList.setVisibility(View.GONE);
            linearLayoutProjectActivityListNote.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        else
        {
            linearLayoutProjectActivityList.setVisibility(View.VISIBLE);
            linearLayoutProjectActivityListNote.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        isInAddMode = editable;
    }

    private void addNewProjectActivity()
    {
        String activityType = null;
        if(selectedActivityTypeModel != null)
        {
            activityType = selectedActivityTypeModel.getActivityTypeId();
        }
        String activityTopic = "";
        if(selectedActivityTopicModel != null)
        {
            activityTopic = selectedActivityTopicModel.getActivityTopicId();
        }
        String offer = "";
        if(selectedCustomerOfferModel != null)
        {
            offer = selectedCustomerOfferModel.getOfferno();
        }
        String date = DataHelper.formatDateToOriginal(labelProjectActivityStartDate.getText().toString());
        String startTime =  DataHelper.formatTimeToOriginal(labelProjectActivityStartDate.getText().toString(), labelProjectActivityStartTime.getText().toString());
        String endTime = DataHelper.formatTimeToOriginal(labelProjectActivityStartDate.getText().toString(), labelProjectActivityEndTime.getText().toString());
        String realized = "";
        String fest = "";

        if(checkBoxProjectActivityFixedTimes.isChecked())
        {
            fest = "1";
        }
        else
        {
            fest = "0";
        }

        if(checkBoxProjectActivityRealized.isChecked())
        {
            realized = "1";
        }
        else
        {
            realized = "0";
        }
//        String mobile = textCustomerActivityContactPersonMobile.getText().toString();
//        String phone = textCustomerActivityContactPersonPhone.getText().toString();
        String note = textProjectActivityNotes.getText().toString();
        if(TextUtils.isEmpty(textViewProjectActivityKunde.getText().toString()))
        {
            kontakt="0";
        }
        /* commented on 12th July to send selected kontact instead of loaded customer kontakt */
        /*else
        {
            kontakt=matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, "").getKontakt();
        }*/

        //kontakt="100000668";
        ProjectDetailGenerallyModel projectGenerallyDetail = matecoPriceApplication.getProjectDetailGenerallyModel(DataHelper.LoadedProjectDetailGenerallyInfo, new ProjectDetailGenerallyModel().toString());

        ProjectAddActivityModel projectAddactivityModel = new ProjectAddActivityModel();
        projectAddactivityModel.setAkttyp(activityType);
        projectAddactivityModel.setAktthema(activityTopic);
        projectAddactivityModel.setNotiz(note);
        projectAddactivityModel.setObjekt(projectGenerallyDetail.getProjekt());
        projectAddactivityModel.setAngebot(offer);
        projectAddactivityModel.setStartdatum(date);
        projectAddactivityModel.setStartzeit(startTime);
        projectAddactivityModel.setEndzeit(endTime);
        projectAddactivityModel.setRealisiert(realized);
        projectAddactivityModel.setAenderungMitarbeiter(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber());
        projectAddactivityModel.setUserID(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserId());
        projectAddactivityModel.setFest(fest);
        projectAddactivityModel.setKontakt(kontakt);
        projectAddactivityModel.setGeloescht("0");
        //projectAddactivityModel.setFirstName(textViewProjectActivityKunde.getText().toString());
        projectAddactivityModel.setFirstName(" this is test ");

        ArrayList<String> listOfSelectedEmployeeId = new ArrayList<>();
        for(int i = 0; i < listOfSelectedEmployee.size(); i++)
        {
            listOfSelectedEmployeeId.add(listOfSelectedEmployee.get(i).getMitarbeiter());
        }
        projectAddactivityModel.setMitarbeiter(listOfSelectedEmployeeId);

        ArrayList<String> listOfSelectedContactPersonId = new ArrayList<>();
        for(int i = 0; i < listOfSelectedContactPerson.size(); i++)
        {
            listOfSelectedContactPersonId.add(listOfSelectedContactPerson.get(i).getAnspartner());
        }
        projectAddactivityModel.setAnspartner(listOfSelectedContactPersonId);

        String json = new Gson().toJson(projectAddactivityModel);

        labelProjectActivityStartDate.setError(null);
        if(selectedActivityTypeModel == null)
        {
            //showShortToast("Please selected acitivity type");
            showShortToast(language.getMessagePleaseSelectActivityType());
        }
        else if(date.equals(""))
        {
            labelProjectActivityStartDate.setError(language.getLabelRequired());
            labelProjectActivityStartDate.requestFocus();
            showShortToast(language.getMessagePleaseSelectDate());
        }
        else if(!DataHelper.yearLessThen2079(date) && !DataHelper.yearLessThen2079(startTime) && !DataHelper.yearLessThen2079(endTime))
        {
            showShortToast(language.getMessageDateMustBeLessThen2079());
        }
        else if(DataHelper.isNetworkAvailable(getActivity()))
        {
            AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult() {
                @Override
                public void OnAsynResult(String result) {
                    if(result.equals("error")) {
                        showShortToast(language.getMessageError());
                    }
                    else if(result.equals(DataHelper.NetworkError)){
                        showShortToast(language.getMessageNetworkNotAvailable());
                    }
                    else
                    {
                        try {
                            matecoPriceApplication.saveData(DataHelper.AgendaDate, "");
                            matecoPriceApplication.saveData(DataHelper.LoadedProjectDetailActivityInfo, result);
                            listOfLoadedProjectActivity.clear();
                            listOfLoadedProjectActivity.addAll(matecoPriceApplication.getLoadedProjectActivities(DataHelper.LoadedProjectDetailActivityInfo, new ArrayList<>().toString()));
                            menu.findItem(R.id.actionAdd).setVisible(true);
                            menu.findItem(R.id.actionEdit).setVisible(true);
                            menu.findItem(R.id.actionRight).setVisible(false);
                            menu.findItem(R.id.actionWrong).setVisible(false);
                            menu.findItem(R.id.actionBack).setVisible(true);
                            flag = true;
                            adapterProjectActivityList.notifyDataSetChanged();
                            addNewActivityFormRedesign(false);
                            makeEditable(false);
                            if(listOfLoadedProjectActivity.size() > 0)
                            {
                                adapterProjectActivityList.setSelectedIndex(0);
                                selectedActivity = listOfLoadedProjectActivity.get(0);
                                setProjectActivity(listOfLoadedProjectActivity.get(0));
                            }
                            else
                            {
                                adapterProjectActivityList.setSelectedIndex(-1);
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
                /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.PROJECT_ACTIVITY_INSERT
                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "&projectactivityinsert=" + URLEncoder.encode(json, "UTF-8");*/
                String url = DataHelper.URL_PROJECT_HELPER+"projectactivityinsert";// + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.PROJECT_ACTIVITY_INSERT
                        //+ "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        //+ "&projectactivityinsert=" + URLEncoder.encode(json, "UTF-8");

                MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")));
                multipartEntity.addPart("projectactivityinsert", new StringBody(json, Charset.forName("UTF-8")));
                AsyncTaskWithAuthorizationHeaderPost asyncTaskPost = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, getActivity(), multipartEntity, true, language);
                asyncTaskPost.execute();
                /*BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResultAddNewCustomerActivity, getActivity(), true);
                asyncTask.execute();*/
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

    private void updateProjectActivityInfo()
    {
        String activityType = null;
        if(selectedActivityTypeModel != null)
        {
            activityType = selectedActivityTypeModel.getActivityTypeId();
        }
        String activityTopic = "";
        if(selectedActivityTopicModel != null)
        {
            activityTopic = selectedActivityTopicModel.getActivityTopicId();
        }
        String offer = "";
        if(selectedCustomerOfferModel != null)
        {
            offer = selectedCustomerOfferModel.getOfferno();
        }
        String date = DataHelper.formatDateToOriginal(labelProjectActivityStartDate.getText().toString());
        String startTime =  DataHelper.formatTimeToOriginal(labelProjectActivityStartDate.getText().toString(), labelProjectActivityStartTime.getText().toString());
        String FirstName = textViewProjectActivityKunde.getText().toString();
        String endTime = DataHelper.formatTimeToOriginal(labelProjectActivityStartDate.getText().toString(), labelProjectActivityEndTime.getText().toString());
        String realized = "";
        String fest = "";

        if(checkBoxProjectActivityFixedTimes.isChecked())
        {
            fest = "1";
        }
        else
        {
            fest = "0";
        }

        if(checkBoxProjectActivityRealized.isChecked())
        {
            realized = "1";
        }
        else
        {
            realized = "0";
        }

//        String mobile = textCustomerActivityContactPersonMobile.getText().toString();
//        String phone = textCustomerActivityContactPersonPhone.getText().toString();
        String note = textProjectActivityNotes.getText().toString();
        //String kontakt = matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();

        ProjectActivityUpdateModel projectActivityUpdateModel = new ProjectActivityUpdateModel();
        projectActivityUpdateModel.setAkttyp(activityType);
        projectActivityUpdateModel.setAktthema(activityTopic);
        projectActivityUpdateModel.setNotiz(note);
        ProjectDetailGenerallyModel projectGenerallyDetail = matecoPriceApplication.getProjectDetailGenerallyModel(DataHelper.LoadedProjectDetailGenerallyInfo, new ProjectDetailGenerallyModel().toString());
        if(TextUtils.isEmpty(textViewProjectActivityKunde.getText().toString())){
            projectActivityUpdateModel.setWithoutKontactAndProject("true");
        }else {
            projectActivityUpdateModel.setWithoutKontactAndProject("false");
        }

        projectActivityUpdateModel.setObjekt(projectGenerallyDetail.getProjekt() + "");
        projectActivityUpdateModel.setAngebot(offer);
        projectActivityUpdateModel.setStartdatum(date);
        projectActivityUpdateModel.setStartzeit(startTime);
        projectActivityUpdateModel.setEndzeit(endTime);
        projectActivityUpdateModel.setRealisiert(realized);
        projectActivityUpdateModel.setFest(fest);
        projectActivityUpdateModel.setFirstName(FirstName);


        if(TextUtils.isEmpty(textViewProjectActivityKunde.getText().toString()))
        {
            projectActivityUpdateModel.setKontakt("0");
        }
        else
        {
            /* commented below line and add new line to send real kontakt instead of loaded customer */
            projectActivityUpdateModel.setKontakt(kontaktWhileUpdate);
            //projectActivityUpdateModel.setKontakt(kontakt);
            //projectActivityUpdateModel.setKontakt(matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, "").getKontakt());
        }

        // new
        //projectActivityUpdateModel.setKontakt(listOfLoadedProjectActivity.get(adapterProjectActivityList.selectedIndex).getKontakt());


        // old
        /*if(matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, "") != null)
        {
            projectActivityUpdateModel.setKontakt(matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, "").getKontakt());
        }
        else
        {
            projectActivityUpdateModel.setKontakt("");
        }*/

        projectActivityUpdateModel.setGeloescht("0");
        projectActivityUpdateModel.setAenderungMitarbeiter(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber());
        String loginUserId = matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserId();
//        customerActivityUpdateModel.setAenderungMitarbeiter(loginUserId);
        if(selectedActivity != null)
            projectActivityUpdateModel.setAktivitaet(selectedActivity.getAktivitaet());

        ArrayList<String> listOfSelectedEmployeeId = new ArrayList<>();
        for(int i = 0; i < listOfSelectedEmployee.size(); i++)
        {
            listOfSelectedEmployeeId.add(listOfSelectedEmployee.get(i).getMitarbeiter());
        }
        projectActivityUpdateModel.setMitarbeiter(listOfSelectedEmployeeId);

        ArrayList<String> listOfSelectedContactPersonId = new ArrayList<>();
        for(int i = 0; i < listOfSelectedContactPerson.size(); i++)
        {
            listOfSelectedContactPersonId.add(listOfSelectedContactPerson.get(i).getAnspartner());
        }
        projectActivityUpdateModel.setAnspartner(listOfSelectedContactPersonId);

        String json = new Gson().toJson(projectActivityUpdateModel);

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
            labelProjectActivityStartDate.setError(language.getLabelRequired());
            labelProjectActivityStartDate.requestFocus();
            showShortToast(language.getMessagePleaseSelectDate());
        }
        else if(!DataHelper.yearLessThen2079(date) && !DataHelper.yearLessThen2079(startTime) && !DataHelper.yearLessThen2079(endTime))
        {
            showShortToast(language.getMessageDateMustBeLessThen2079());
        }
        else if(DataHelper.isNetworkAvailable(getActivity()))
        {
            AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult() {
                @Override
                public void OnAsynResult(String result) {
                    if(result.equals("error"))
                    {
                        showShortToast(language.getMessageError());
                    }
                    else if(result.equals(DataHelper.NetworkError)){
                        showShortToast(language.getMessageNetworkNotAvailable());
                    }
                    else {
                        try
                        {
                            matecoPriceApplication.saveData(DataHelper.LoadedProjectDetailActivityInfo, result);
                            listOfLoadedProjectActivity.clear();
                            listOfLoadedProjectActivity.addAll(matecoPriceApplication.getLoadedProjectActivities(DataHelper.LoadedProjectDetailActivityInfo, new ArrayList<>().toString()));
                            adapterProjectActivityList.notifyDataSetChanged();
                            menu.findItem(R.id.actionAdd).setVisible(true);
                            menu.findItem(R.id.actionEdit).setVisible(true);
                            menu.findItem(R.id.actionRight).setVisible(false);
                            menu.findItem(R.id.actionWrong).setVisible(false);
                            menu.findItem(R.id.actionBack).setVisible(true);
                            //clearAllValues();
                            flag = true;
                            addNewActivityFormRedesign(false);
                            makeEditable(false);
                            if(listOfLoadedProjectActivity.size() > 0)
                            {
                                adapterProjectActivityList.setSelectedIndex(0);
                                selectedActivity = listOfLoadedProjectActivity.get(0);
                                setProjectActivity(listOfLoadedProjectActivity.get(0));
                            }
                            else
                            {
                                adapterProjectActivityList.setSelectedIndex(-1);
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
                /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.PROJECT_ACTIVITY_UPDATE
                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "&projectactivityupdate=" + URLEncoder.encode(json, "UTF-8");*/
                String url = DataHelper.URL_PROJECT_HELPER+"projectactivityupdate";// + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.PROJECT_ACTIVITY_UPDATE
                        //+ "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        //+ "&projectactivityupdate=" + URLEncoder.encode(json, "UTF-8");
                MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")));
                multipartEntity.addPart("projectactivityupdate", new StringBody(json, Charset.forName("UTF-8")));
                AsyncTaskWithAuthorizationHeaderPost asyncTaskPost = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, getActivity(), multipartEntity, true, language);
                asyncTaskPost.execute();

                /*BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResultCustomerActivityUpdate, getActivity(), true);
                asyncTask.execute();*/
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

        Button btnSearch = (Button) dialog.findViewById(R.id.buttonSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchForResults();
            }
        });
        Button btnDone = (Button) dialog.findViewById(R.id.buttonDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
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
        pageSize = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).getInt("CustomerPageSize", 10);

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.show();


        listCustomerSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                adapter.setSelectedIndex(position);
                /*if (!(adapter.selectedIndex == -1)) {
                    getCustomerDetails(adapter.selectedIndex);
                    dialog.dismiss();
                } else {
                    showShortToast(language.getMessageSelectCustomerFirst());
                }*/
            }
        });

        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchForNewPage();
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
                                /* commented below line on 8th july 2017 to avoid searched customer to set as
                                  loaded customer */
                                //matecoPriceApplication.saveLoadedCustomer(DataHelper.LoadedCustomer, json);
                                //Log.e("customer detail", customerFullDetail.getCustomerSearchList().toString());


                                /*String listOfContactPerson = new Gson().toJson(customerFullDetail.getCustomerContactPersonList());
                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerContactPerson, listOfContactPerson);


                                *//* clear below arrayilst before add value on 30th june 2017 to avoid duplicate value *//*
                                listOfAllContactPerson.clear();

                                listOfAllContactPerson.addAll(matecoPriceApplication.getLoadedCustomerContactPersons(DataHelper.LoadedCustomerContactPerson, new ArrayList<ContactPersonModel>().toString()));

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

                                JSONObject  obj = new JSONObject(json);
                                String name1 = obj.getString("Name1");

                                textViewProjectActivityKunde.setText(name1);
                                kontakt=obj.getString("Kontakt");
                                kontaktWhileUpdate=kontakt;
                                //textViewProjectActivityKunde.setText(matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, "").getName1() + "");

                                isEdited = true;
                                listOfRemainingContactPersontemp = new ArrayList<>();
                                listOfRemainingContactPersontemp.addAll(customerFullDetail.getCustomerContactPersonList());
                                /// my code 16-09
                                //listOfSelectedContactPerson.clear();
                                //listOfSelectedContactPerson.addAll(customerFullDetail.getCustomerContactPersonList());
                                //selectedContactPersonAdapter.notifyDataSetChanged();
                                // my code



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
                        + "/value=" + listOfCustomerSearchResult.get(selectedIndex).getKontakt();
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

            //Gson gson = new GsonBuilder().disableHtmlEscaping().create();
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
                           // showLongToast("Network problem while service calling before");
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date date1 = null;
        try {
            date1 = format.parse(labelProjectActivityStartDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date todayDate = new Date();
        if (date1 != null) {
            if (date1.compareTo(todayDate) > 0) {
                showShortToast(language.getMessageActivityIsInFuture());
                checkBoxProjectActivityRealized.setChecked(false);
            }
        } else {
            showShortToast(language.getMessagePleaseSelectDate());
            checkBoxProjectActivityRealized.setChecked(false);
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
