package de.mateco.integrAMobile.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.RectF;

import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
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
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;


import com.alamkanak.weekview.DateTimeInterpreter;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.vision.text.Text;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.DatePickerDialogFragment;
import de.mateco.integrAMobile.Helper.DelayAutoCompleteTextView;
import de.mateco.integrAMobile.Helper.GlobalClass;
import de.mateco.integrAMobile.Helper.TimePickerDialogFragment;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;

import de.mateco.integrAMobile.adapter.ActivityTopicAdapter;
import de.mateco.integrAMobile.adapter.ActivityTypeAdapter;
import de.mateco.integrAMobile.adapter.CustomerContactPersonListAdapter;
import de.mateco.integrAMobile.adapter.CustomerContactPersonSpinnerAdapter;
import de.mateco.integrAMobile.adapter.CustomerSearchResultAdapter;
import de.mateco.integrAMobile.adapter.EmployeeAdapter;
import de.mateco.integrAMobile.adapter.EventAdapter;
import de.mateco.integrAMobile.adapter.OfferAdapter;
import de.mateco.integrAMobile.adapter.ProjectSearchAdapter;
import de.mateco.integrAMobile.adapter.ProjectTradeSpinnerAdapter;
import de.mateco.integrAMobile.asyncTask.AsyncTaskWithAuthorizationHeaderPost;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.ActivityTopicModel;
import de.mateco.integrAMobile.model.ActivityTypeModel;
import de.mateco.integrAMobile.model.ContactPersonModel;
import de.mateco.integrAMobile.model.CustomerActivityGetModel;
import de.mateco.integrAMobile.model.CustomerActivityModel;
import de.mateco.integrAMobile.model.CustomerActivityUpdateModel;
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
import de.mateco.integrAMobile.model.ProjectDetailGenerallyModel;
import de.mateco.integrAMobile.model.ProjectModel;
import de.mateco.integrAMobile.model.ProjectSearchPagingRequestModel;
import de.mateco.integrAMobile.model.ProjectTradeInsert;
import de.mateco.integrAMobile.model.WeeklyAgendaModel;

public class VisitPlanDailyAgendaWeeklyFragment extends BaseFragment implements WeekView.EventClickListener,MonthLoader.MonthChangeListener,DateTimeInterpreter,WeekView.ScrollListener,CheckBox.OnCheckedChangeListener,View.OnClickListener, TextView.OnEditorActionListener {

    private boolean isCallservice=true;
    private ProgressDialog progressDialog;
    String FOCUSED_EMPLOYEE="employee",FOCUSED_PLACE="place",FOCUSED_MATCHCODE_PROJECT="matccode",FOCUSED_DESCRIPTION="description",
            FOCUSED_TYPE_OF_PROJECT="type_of_project",FOCUSED_ADDRESS="address",FOCUSED_FROM="from",FOCUSED_TO="to";
    String lastFocusedvalueProject="";
    int thresHodValue=3;
    AutoCompleteSearchAdapterProject adapterAutocompleteProject;
    String jsonToSendProject="";
    ArrayList<HintModel> hintListProject = new ArrayList<>();

    String FOCUSED_CUSTOMER_NO="customerno",FOCUSED_KANR="kanrno",FOCUSED_MATCHCODE="matccode",FOCUSED_NAME1="name1",
            FOCUSED_STREET="street",FOCUSED_PLZ="plz",FOCUSED_ORT="ort",FOCUSED_TEL="telephone";
    String lastFocusedvalue="";
    String jsonToSend="";
    AutoCompleteSearchAdapter adapterAutocomplete;

    /////// for projectSearchDialog
    private boolean isEdited=false;
    String kontaktId="";
    String objectId="";
    Dialog projectDialog;
    LinearLayout lianearMain;
    Button btnSearch,btnRight;
    DelayAutoCompleteTextView textProjectSearchMatchCode, textProjectSearchDescription, textProjectSearchTypeOfProject, textProjectSearchStreetAddress, textProjectSearchPlace, textProjectSearchFrom, textProjectSearchTo, textProjectSearchEmployee;
    ListView listViewProjects;
    ProjectSearchAdapter adapterProjectSearch;
    ArrayList<ProjectModel> listofProject;
    int pageNuber2 = 1;
    int customerCount2 = 0;
    int totalPageCount2 = 1;
    int pageSize2 = 10;
    View footerView2;
    Button footerButton2;
    ////////// end for projectSearchDialog

    ///////////////////////////////////////// for  Customersearch dialog
    int customerCount = 0;
    int totalPageCount = 1;
    int pageNuber = 1;
    int pageSize = 10;
    View footerView;
    Button footerButton;
    CustomerSearchResultAdapter adapterSearch;
    ListView listCustomerSearchResult;
    DelayAutoCompleteTextView textCustomerSearchCustomerNo, textCustomerSearchKanr,textCustomerSearchMatchCode,textCustomerSearchName1,
            textCustomerSearchRoad, textCustomerSearchZipCode,textCustomerSearchPlace, textCustomerSearchTel;
    TextView labelCustomerSearchCustomerNo, labelCustomerSearchKanr, labelCustomerSearchMatchCode,labelCustomerSearchName1,
            labelCustomerSearchRoad, labelCustomerSearchZipCode, labelCustomerSearchPlace, labelCustomerSearchTel;
    ArrayList<CustomerModel> listOfCustomerSearchResult;
    TextView textViewProjectActivityKunde;
    private ArrayList<ContactPersonModel> listOfSelectedContactPerson, listOfRemainingContactPerson,
            listOfAllContactPerson,listOfRemainingContactPersontemp;
    private CustomerContactPersonListAdapter selectedContactPersonAdapter, remainingContactPersonAdapter;
    private ActivityTypeAdapter adapterActivityType;
    private ActivityTopicAdapter adapterActivityTopic;
    private ArrayList<ActivityTypeModel> listOfActivityType;
    private ArrayList<ActivityTopicModel> listOfActivityTopic;
    private ArrayList<CustomerOfferModel> listOfAllOffer;
    private OfferAdapter offerAdapter;

    ////////// end for customer search dailgo
    TextView textViewProjectSearch;

    private ActivityTypeModel selectedActivityTypeModel;
    private ArrayList<CustomerActivityModel> listOfLoadedCustomerActivity;
    private ArrayList<CustomerProjectModel> listOfAllProject;
    /////////



    ///// for activity dialog
    ListView listViewProjectActivityList, listViewProjectActivityEmployee, listProjectActivityContacts;
    Spinner spinnerProjectActivityActivityType, spinnerProjectActivityActivityTopic, spinnerProjectActivityOffer;
    Button buttonDialogAddTrade, buttonDialogAddTradeCancel,btnDone,btnEdit;
    TextView labelProjectActivityStartDate,labelProjectActivityEndTime,labelProjectActivityStartTime;
    ImageButton imageButtonProjectActivityStartDate, imageButtonStartTime, imageButtonEndTime,buttonAddProject;
    ImageButton buttonRemoveProjectActivityEmployee, buttonAddProjectActivityEmployee, buttonRemoveProjectActivityContacts,
            buttonAddProjectActivityContacts,buttonAddProjectActivityKunde;
    CheckBox checkBoxProjectActivityRealized, checkBoxProjectActivityFixedTimes;

    private EmployeeAdapter selectedEmployeeAdapter, remainingEmployeeAdapter;
    private ArrayList<EmployeeModel> listOfSelectedEmployee, listOfRemainingEmployee, listOfAllEmployee;
    private CustomerActivityModel selectedActivity;
    private ActivityTopicModel selectedActivityTopicModel;
    private CustomerOfferModel selectedCustomerOfferModel;
    private CustomerProjectModel selectedCustomerProjectModel;
    private BasicAsyncTaskGetRequest asyncTask;
    ///////  end for activity dialog


    Dialog dialog,activityDialog;
    private View rootView;
    private MatecoPriceApplication matecoPriceApplication;
    private Language language;
    private DataBaseHandler db;
    private WeekView mWeekView;
    private TextView labelAgendaDailyDateSelected;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    private ArrayList<WeeklyAgendaModel> listOfWeeklyAgenda,listOfWeeklyAgenda1 = new ArrayList<>();
    private String firstDate = "";
    private boolean flag = false,isViewShown;
    private EventAdapter adapter;
    private int pos=0;
    private String dateString = "";
    SimpleDateFormat readFormat = new SimpleDateFormat("dd.MM.yyyy, E");
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    private CheckBox checkBoxRealizedActivities;
    private Calendar cal1 = Calendar.getInstance();
    private Calendar cal2 = Calendar.getInstance();
    private Button buttonCurrentweek;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_agenda_weekly, container, false);
        super.initializeFragment(rootView);
        return rootView;
    }

    @Override
    public void initializeComponents(View rootView) {
        super.initializeComponents(rootView);

        matecoPriceApplication = (MatecoPriceApplication) getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        db = new DataBaseHandler(getActivity());

        labelAgendaDailyDateSelected = (TextView) rootView.findViewById(R.id.labelAgendaDailyDateSelected);
        checkBoxRealizedActivities = (CheckBox)rootView.findViewById(R.id.checkBoxRealizedActivities);
        buttonCurrentweek = (Button)rootView.findViewById(R.id.buttonCurrentweek);
        checkBoxRealizedActivities.setText(language.getLabelWithRealized());
        buttonCurrentweek.setText(language.getLabelCurrentWeek());
        checkBoxRealizedActivities.setChecked(true);
        checkBoxRealizedActivities.setOnCheckedChangeListener(this);
        buttonCurrentweek.setOnClickListener(this);

        adapter = new EventAdapter(getActivity(),listOfWeeklyAgenda1);

        try {
            if(matecoPriceApplication.getData(DataHelper.WeeklyAgendaDate,"").equals(""))
            {
                dateString = format.format(readFormat.parse(DataHelper.formatDisplayDate(new Date())));
                setDate(dateString);
            }
            else
                labelAgendaDailyDateSelected.setText(matecoPriceApplication.getData(DataHelper.WeeklyAgendaDate,""));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mWeekView = (WeekView) rootView.findViewById(R.id.weekView);
        mWeekView.setOnEventClickListener(this);
        mWeekView.setMonthChangeListener(this);
        mWeekView.setDateTimeInterpreter(this);
        listOfWeeklyAgenda = new ArrayList<>();

        if(isViewShown) {
            adapterLoad();
        }
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
}


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isViewShown = true;
        } else {
            isViewShown = false;
        }
        if (getView() != null) {
            if(isViewShown){
                adapterLoad();
            }
            //isViewShown = true;
            // fetchdata() contains logic to show data when page is selected mostly asynctask to fill the data

        } else {
            //isViewShown = false;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void bindEvents(View rootView) {
        super.bindEvents(rootView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCurrentweek:
                try
                {
                    dateString = format.format(readFormat.parse(DataHelper.formatDisplayDate(new Date())));
                    setDate(dateString);
                    adapterLoad();
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

                break;

        }
    }

    private void addDate(int numbersToAdd) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(formatter.parse(labelAgendaDailyDateSelected.getText().toString()));
            cal.add(Calendar.DAY_OF_MONTH, numbersToAdd);
            labelAgendaDailyDateSelected.setText(DataHelper.formatDisplayDate(cal.getTime()));
            adapterLoad();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setDate(String dateString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter.parse(dateString);
            labelAgendaDailyDateSelected.setText(DataHelper.formatDisplayDate(date));
            firstDate = labelAgendaDailyDateSelected.getText().toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void adapterLoad() {

        try {
            firstDate = labelAgendaDailyDateSelected.getText().toString();
            String date = labelAgendaDailyDateSelected.getText().toString();
            final String finaldate = format.format(readFormat.parse(date));
            matecoPriceApplication.saveData(DataHelper.WeeklyAgendaDate, firstDate);
            final Calendar calendar1 = Calendar.getInstance();
            final Date date1 = format.parse(finaldate);
            calendar1.setTime(date1);
            String loginPersonId = matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber();

            if (DataHelper.isNetworkAvailable(getActivity())) {
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult() {
                    @Override
                    public void OnAsynResult(String result) {
                        Log.e("result", result);
                        if (result.equals("error")) {
                            showShortToast(language.getMessageError());
                        }
                        else if(result.equals(DataHelper.NetworkError)){
                            showShortToast(language.getMessageNetworkNotAvailable());
                        }
                        else
                        {
                            try
                            {
                                listOfWeeklyAgenda.clear();
                                WeeklyAgendaModel.extractFromJson(result, listOfWeeklyAgenda);
                                mWeekView.notifyDatasetChanged();
                                calendar1.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);

                                mWeekView.goToDate(calendar1);
                                mWeekView.goToHour(7);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                };

                String url = DataHelper.URL_AGENDA_HELPER + "agendaweek" //DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.GET_WEEKLY_AGENDA
                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/mitarbeiter=" + loginPersonId
                        + "/datum=" + finaldate;

                Log.e("url", url);
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                asyncTask.execute();
            } else {
                showShortToast(language.getMessageNetworkNotAvailable());
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void openDatePicker() {
        try {
            Date today = new Date();
            Calendar c = Calendar.getInstance();
            //c.setTime(today);
            c.setTime(formatter.parse(labelAgendaDailyDateSelected.getText().toString()));
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
            Log.e("tag", "startDate");
            newFragment.show(getActivity().getSupportFragmentManager(), "dateSelected");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    DatePickerDialog.OnDateSetListener onFromDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            try {
                monthOfYear += 1;
                String date = dayOfMonth + "." + monthOfYear + "." + year;
                String finaldate = DataHelper.formatDisplayDate(formatter.parse(date));
                Log.e("startDate", finaldate);
                labelAgendaDailyDateSelected.setText(finaldate);
                adapterLoad();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_menu, menu);
        menu.findItem(R.id.actionSearch).setVisible(false);
        menu.findItem(R.id.actionBack).setVisible(false);
        menu.findItem(R.id.actionWrong).setVisible(false);
        menu.findItem(R.id.actionRight).setVisible(false);
        menu.findItem(R.id.actionAdd).setVisible(false);
        menu.findItem(R.id.actionEdit).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);
        //return true;
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionSettings:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new SettingFragment(),"Setting");
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
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF rectF) {
        listOfWeeklyAgenda1.clear();
        String datum = "";
        Calendar start = event.getStartTime();
        String startTime = manageTime(start.get(Calendar.HOUR_OF_DAY) + "", start.get(Calendar.MINUTE) + "");
        Calendar end = event.getEndTime();
        String endTime = manageTime(end.get(Calendar.HOUR_OF_DAY) + "", end.get(Calendar.MINUTE) + "");
        String listDate = "";
        String startHour = start.get(Calendar.HOUR_OF_DAY)+"";
        if(startHour.length()==1)
            startHour = "0"+startHour;
        try {
             datum = format.format(readFormat.parse(DataHelper.formatDisplayDate(event.getStartTime().getTime())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for(int i=0;i<listOfWeeklyAgenda.size();i++)
        {
            if((event.getName().equals(listOfWeeklyAgenda.get(i).getName1()) || event.getName().equals(listOfWeeklyAgenda.get(i).getAktivitaetstytp()))
                    && (listOfWeeklyAgenda.get(i).getDatum().equals(datum)))
            {
                listDate = listOfWeeklyAgenda.get(i).getDatum();
            }
        }
        for(int i=0;i<listOfWeeklyAgenda.size();i++)
        {
            if(!listOfWeeklyAgenda.get(i).getStartzeit().equals("")) {
                if ((listOfWeeklyAgenda.get(i).getDatum().equals(listDate) &&
                        listOfWeeklyAgenda.get(i).getStartzeit().equals(startTime) && listOfWeeklyAgenda.get(i).getEndzeit().equals(endTime))
                        || (listOfWeeklyAgenda.get(i).getDatum().equals(listDate) && listOfWeeklyAgenda.get(i).getStartzeit().substring(0, 2).equals(startHour)))
                    listOfWeeklyAgenda1.add(listOfWeeklyAgenda.get(i));
            }
        }
        showEventDialog();
    }

    public String manageTime(String hour, String min) {
        Log.e("hour", hour);
        if(hour.length()==1)
            hour = "0" + hour;
        if(min.length()==1)
            min = "0" + min;
        if(hour.equals("00"))
            hour = "12";
        return hour + ":" + min + ":" + "00";
    }

    private void showEventDialog()
    {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.dialog_click_event, null);

        alertDialog.setView(convertView);
        alertDialog.setTitle(language.getMessageSelectEvent());
        final AlertDialog ad = alertDialog.show();

        //final Dialog dialog = showCustomDialog(R.layout.dialog_click_event, language.getMessageSelectEvent());
        ListView listViewEvent = (ListView)convertView.findViewById(R.id.listViewEvents);
        listViewEvent.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Button buttonOk, buttonCancel;
        adapter.setSelectedIndex(-1);
        pos = -1;
        buttonOk = (Button)convertView.findViewById(R.id.buttonEventOk);
        buttonCancel = (Button)convertView.findViewById(R.id.buttonEventCancel);
        buttonOk.setText(language.getLabelOk());
        buttonCancel.setText(language.getLabelCancel());
        listViewEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedIndex(position);
                pos = position;
            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pos >= 0)
                {

                    if (!listOfWeeklyAgenda1.get(pos).getKontakt().equals(""))

                        // this will go in customer activity
                        getCustomerDetails(pos);
                    else
                        // this will go in project activity
                        loadProject(pos);
                    ad.dismiss();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
            }
        });
        if(listOfWeeklyAgenda1.size() > 0)
        {
            Log.e("show dialog",listOfWeeklyAgenda1.size()+"");
            ad.show();
        }
    }

    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        Log.e("on ", "month changed " + newMonth);
        events.clear();
        SimpleDateFormat formatterWithTime = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        SimpleDateFormat formatterWithDateOnly = new SimpleDateFormat("dd-MM-yyyy");
        newMonth--;
        if (checkBoxRealizedActivities.isChecked()) {
            for (int i = 0; i < listOfWeeklyAgenda.size(); i++) {
                try {
                    Calendar calendar = Calendar.getInstance();
                    Calendar startTime = Calendar.getInstance();
                    String startDateTime = listOfWeeklyAgenda.get(i).getDatum() + " " + listOfWeeklyAgenda.get(i).getStartzeit();

                    Date d = formatterWithTime.parse(startDateTime);
                    calendar.setTime(d);

                    if (calendar.get(Calendar.MONTH) == newMonth) {

                        if (listOfWeeklyAgenda.get(i).getStartzeit().equals("")) {
                            startTime.setTime(formatterWithDateOnly.parse(listOfWeeklyAgenda.get(i).getDatum()));
                        } else {
                            Calendar cal = Calendar.getInstance();
                            Date date = formatterWithTime.parse(startDateTime);
                            cal.setTime(date);
                            int year = cal.get(Calendar.YEAR);
                            int day = cal.get(Calendar.DAY_OF_MONTH);
                            int hour = cal.get(Calendar.HOUR_OF_DAY);
                            if (hour == 0)
                                hour = 12;
                            startTime.set(year, newMonth, day, hour, date.getMinutes());
                        }

                        Calendar endTime = (Calendar) startTime.clone();
                        String endDateTime = listOfWeeklyAgenda.get(i).getDatum() + " " + listOfWeeklyAgenda.get(i).getEndzeit();

                        if (listOfWeeklyAgenda.get(i).getEndzeit().equals("")) {
                            endTime.setTime(formatterWithDateOnly.parse(listOfWeeklyAgenda.get(i).getDatum()));
                        } else {
                            Calendar cal = Calendar.getInstance();
                            Date date = formatterWithTime.parse(endDateTime);
                            cal.setTime(date);
                            int year = cal.get(Calendar.YEAR);
                            int day = cal.get(Calendar.DAY_OF_MONTH);
                            int hour = cal.get(Calendar.HOUR_OF_DAY);
                            if (hour == 0)
                                hour = 12;
                            endTime.set(year, newMonth, day, hour, date.getMinutes());
                        }

                        WeekViewEvent event = new WeekViewEvent(i, getEventTitle(i), startTime, endTime);

                        if (listOfWeeklyAgenda.get(i).getRealisiert().equals("False") && listOfWeeklyAgenda.get(i).getFest().equals("False")) {
                            event.setColor(getResources().getColor(R.color.lightRed));
                            for (int j = 0; j < i; j++) {
                                if (listOfWeeklyAgenda.get(j).getDatum().equals(listOfWeeklyAgenda.get(i).getDatum()) &&
                                        listOfWeeklyAgenda.get(j).getStartzeit().equals(listOfWeeklyAgenda.get(i).getStartzeit()) &&
                                        listOfWeeklyAgenda.get(j).getEndzeit().equals(listOfWeeklyAgenda.get(i).getEndzeit())) {
                                    event.setColor(getResources().getColor(R.color.yellow));
                                }
                            }
                        } else if (listOfWeeklyAgenda.get(i).getRealisiert().equals("False") && listOfWeeklyAgenda.get(i).getFest().equals("True")) {
                            event.setColor(getResources().getColor(R.color.red));
                            for (int j = 0; j < i; j++) {
                                if (listOfWeeklyAgenda.get(j).getDatum().equals(listOfWeeklyAgenda.get(i).getDatum()) &&
                                        listOfWeeklyAgenda.get(j).getStartzeit().equals(listOfWeeklyAgenda.get(i).getStartzeit()) &&
                                        listOfWeeklyAgenda.get(j).getEndzeit().equals(listOfWeeklyAgenda.get(i).getEndzeit())) {
                                    event.setColor(getResources().getColor(R.color.yellow));
                                }
                            }
                        } else if (listOfWeeklyAgenda.get(i).getRealisiert().equals("True")) {
                            event.setColor(getResources().getColor(R.color.cyan));
                        } else {
                            event.setColor(getResources().getColor(R.color.ripple_material_dark));
                        }
                        events.add(event);
                        Log.e("size",events.size()+"");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }
        else
        {
            for (int i = 0; i < listOfWeeklyAgenda.size(); i++) {
                if(listOfWeeklyAgenda.get(i).getRealisiert().equals("False")){
                    try {
                        Calendar calendar = Calendar.getInstance();
                        Calendar startTime = Calendar.getInstance();
                        String startDateTime = listOfWeeklyAgenda.get(i).getDatum() + " " + listOfWeeklyAgenda.get(i).getStartzeit();

                        Date d = formatterWithTime.parse(startDateTime);
                        calendar.setTime(d);

                        if (calendar.get(Calendar.MONTH) == newMonth) {

                            if (listOfWeeklyAgenda.get(i).getStartzeit().equals("")) {
                                startTime.setTime(formatterWithDateOnly.parse(listOfWeeklyAgenda.get(i).getDatum()));
                            } else {
                                Calendar cal = Calendar.getInstance();
                                Date date = formatterWithTime.parse(startDateTime);
                                cal.setTime(date);
                                int year = cal.get(Calendar.YEAR);
                                int day = cal.get(Calendar.DAY_OF_MONTH);
                                int hour = cal.get(Calendar.HOUR_OF_DAY);
                                if (hour == 0)
                                    hour = 12;
                                startTime.set(year, newMonth, day, hour, date.getMinutes());
                            }

                            Calendar endTime = (Calendar) startTime.clone();
                            String endDateTime = listOfWeeklyAgenda.get(i).getDatum() + " " + listOfWeeklyAgenda.get(i).getEndzeit();

                            if (listOfWeeklyAgenda.get(i).getEndzeit().equals("")) {
                                endTime.setTime(formatterWithDateOnly.parse(listOfWeeklyAgenda.get(i).getDatum()));
                            } else {
                                Calendar cal = Calendar.getInstance();
                                Date date = formatterWithTime.parse(endDateTime);
                                cal.setTime(date);
                                int year = cal.get(Calendar.YEAR);
                                int day = cal.get(Calendar.DAY_OF_MONTH);
                                int hour = cal.get(Calendar.HOUR_OF_DAY);
                                if (hour == 0)
                                    hour = 12;
                                endTime.set(year, newMonth, day, hour, date.getMinutes());
                            }

                            WeekViewEvent event = new WeekViewEvent(i, getEventTitle(i), startTime, endTime);

                            if (listOfWeeklyAgenda.get(i).getRealisiert().equals("False") && listOfWeeklyAgenda.get(i).getFest().equals("False")) {
                                event.setColor(getResources().getColor(R.color.lightRed));
                                for (int j = 0; j < i; j++) {
                                    if (listOfWeeklyAgenda.get(j).getDatum().equals(listOfWeeklyAgenda.get(i).getDatum()) &&
                                            listOfWeeklyAgenda.get(j).getStartzeit().equals(listOfWeeklyAgenda.get(i).getStartzeit()) &&
                                            listOfWeeklyAgenda.get(j).getEndzeit().equals(listOfWeeklyAgenda.get(i).getEndzeit())) {
                                        event.setColor(getResources().getColor(R.color.yellow));
                                    }
                                }
                            } else if (listOfWeeklyAgenda.get(i).getRealisiert().equals("False") && listOfWeeklyAgenda.get(i).getFest().equals("True")) {
                                event.setColor(getResources().getColor(R.color.red));
                                for (int j = 0; j < i; j++) {
                                    if (listOfWeeklyAgenda.get(j).getDatum().equals(listOfWeeklyAgenda.get(i).getDatum()) &&
                                            listOfWeeklyAgenda.get(j).getStartzeit().equals(listOfWeeklyAgenda.get(i).getStartzeit()) &&
                                            listOfWeeklyAgenda.get(j).getEndzeit().equals(listOfWeeklyAgenda.get(i).getEndzeit())) {
                                        event.setColor(getResources().getColor(R.color.yellow));
                                    }
                                }
                            } else if (listOfWeeklyAgenda.get(i).getRealisiert().equals("True")) {
                                event.setColor(getResources().getColor(R.color.cyan));
                            } else {
                                event.setColor(getResources().getColor(R.color.lightRed));
                            }
                            events.add(event);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        flag = true;
        mWeekView.setScrollListener(this);
        return events;
    }

    private String getEventTitle(int i) {
        String name = "";
        if(listOfWeeklyAgenda.get(i).getName1().equals("") || listOfWeeklyAgenda.get(i).getName1()==null)
            name = listOfWeeklyAgenda.get(i).getAktivitaetstytp();
        else
            name = listOfWeeklyAgenda.get(i).getName1();
        return name;
    }

    @Override
    public String interpretDate(Calendar date) {

        SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
        String weekday = weekdayNameFormat.format(date.getTime());
        SimpleDateFormat format = new SimpleDateFormat(" dd.MM", Locale.getDefault());
        return "KW" + date.get(Calendar.WEEK_OF_YEAR)+ ":" + weekday.toUpperCase() + format.format(date.getTime());
    }

    @Override
    public String interpretTime(int i) {
        return i + ":00";
    }

    @Override
    public void onFirstVisibleDayChanged(Calendar calendar, Calendar calendar1) {

        /*if(calendar.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY)
        {
            mWeekView.setXScrollingSpeed(0);
            try {
                Date date = readFormat.parse(firstDate);
                cal2.setTime(date);
                if(cal1.getTimeInMillis()<calendar.getTimeInMillis())
                {
                    if(cal2.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY)
                        cal2.add(Calendar.DATE,7);
                    else
                        cal2.add(Calendar.DATE,6);
                }
                else
                {
                    if(cal2.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY)
                        cal2.add(Calendar.DATE,-7);
                    else
                        cal2.add(Calendar.DATE,-6);
                }

                String dateString = format.format(readFormat.parse(DataHelper.formatDisplayDate(cal2.getTime())));
                date = format.parse(dateString);
                labelAgendaDailyDateSelected.setText(DataHelper.formatDisplayDate(date));
                if(flag)
                {
                    flag = false;
                    adapterLoad();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else
        {
            cal1 = (Calendar)calendar.clone();
        }*/
        Calendar cal = Calendar.getInstance();
        try {
            String dateString = format.format(readFormat.parse(DataHelper.formatDisplayDate(calendar.getTime())));
            if (!dateString.equals("")) {
                Log.e("enter",dateString);
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date date = formatter.parse(dateString);
                Date date1 = readFormat.parse(firstDate);
                cal.setTime(date1);
                long diff = calendar.getTimeInMillis() - cal.getTimeInMillis();
                long days = diff / (24 * 60 * 60 * 1000);
                labelAgendaDailyDateSelected.setText(DataHelper.formatDisplayDate(date));
                Log.e("days", days + " days");
                if(days >= 6 || days <= -6)
                {
                    if(flag)
                    {
                        flag = false;
                        adapterLoad();
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void getCustomerDetails(int selectedIndex)
    {
        if(DataHelper.isNetworkAvailable(getActivity()))
        {
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setTitle(language.getMessageWaitWhileLoading());
            dialog.setMessage(language.getMessageWaitWhileLoading());
            dialog.show();
            try
            {
                DataBaseHandler db = new DataBaseHandler(getActivity());
                db.deleteKANR();
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        if(result.equals("error") || result.equalsIgnoreCase(""))
                        {
                            showShortToast(language.getMessageError());
                            dialog.dismiss();
                        }
                        else if(result.equals(DataHelper.NetworkError)){
                            showShortToast(language.getMessageNetworkNotAvailable());
                            dialog.dismiss();
                        }
                        else
                        {
                            try
                            {
                                CustomerFullDetailModel customerFullDetail = new Gson().fromJson(result, CustomerFullDetailModel.class);
                                String json = new Gson().toJson(customerFullDetail.getCustomerSearchList());
                                matecoPriceApplication.saveLoadedCustomer(DataHelper.LoadedCustomer, json);
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
                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerCompletedOrders, listOfCompletedOrder);
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                Bundle bundle = new Bundle();
                                bundle.putString("WeeklyAgendaSelectedModel", new Gson().toJson(listOfWeeklyAgenda1.get(pos)));
                                dialog.dismiss();
                                Fragment fragment = new CustomerActivityFragment();
                                fragment.setArguments(bundle);
                                transaction.replace(R.id.content_frame, fragment);
                                transaction.addToBackStack("Weekly Agenda");
                                transaction.commit();
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                                showShortToast(language.getMessageErrorAtParsing());
                                dialog.dismiss();
                            }
                        }
                    }
                };

                String url = DataHelper.URL_AGENDA_HELPER + "agendacustomershow" //+ DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.AGENDA_CUSTOMER_SHOW
                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/fieldname=" + "Kontakt"
                        + "/value=" + listOfWeeklyAgenda1.get(selectedIndex).getKontakt();
                Log.e("url", url);

                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
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
            if(db.isCustomerAvailable(listOfWeeklyAgenda1.get(selectedIndex).getKontakt()))
            {
                Gson gson = new Gson();
                CustomerDatabaseModel customerDatabaseModel = db.getCustomer(listOfWeeklyAgenda1.get(selectedIndex).getKontakt());
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
                Fragment fragment = new CustomerActivityFragment();
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack("Weekly Agenda");
                transaction.commit();
            }
            else
            {
                showShortToast(language.getMessageCustomerNotFound());
            }
        }
    }
    private void getCustomerDetails2(int selectedIndex, final WeeklyAgendaModel weekModel)
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
                        Log.e("result",result);
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
                                Log.e("customer detail", customerFullDetail.getCustomerSearchList().toString());
                                String listOfContactPerson = new Gson().toJson(customerFullDetail.getCustomerContactPersonList());
                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerContactPerson, listOfContactPerson);

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
                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerCompletedOrders, listOfCompletedOrder);
                                textViewProjectActivityKunde.setText(matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, "").getName1() + "");
                                kontaktId=matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, "").getKontakt();
                                //weekModel.setKontakt(kontaktId);
                                selectedContactPersonAdapter.notifyDataSetChanged();

                                isEdited = true;
                                listOfRemainingContactPersontemp = new ArrayList<>();
                                listOfRemainingContactPersontemp.addAll(customerFullDetail.getCustomerContactPersonList());



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
                Log.e("url", url);

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
    private void loadProject(final int selectedIndex)
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
                        Log.e("result",result);
                        if(result.equals("error")) {
                            dialog.dismiss();
                            //showShortToast(language.getMessageError());
                            showShortToast("Lieber Anwender – Momentan können Aktivitäten ohne Bezug zu Kunde oder Projekt leider nicht angezeigt werden.");
                        }
                        else if(result.equals(DataHelper.NetworkError)){
                            dialog.dismiss();
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
                                FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                transaction1.replace(R.id.content_frame, new ProjectDetailFragment());
                                //transaction.addToBackStack(SettingFragment.Tag);
                                transaction1.addToBackStack("Project Search");
                                transaction1.commit();
                            }
                            catch (JSONException ex)
                            {
                                ex.printStackTrace();
                                dialog.dismiss();
                                //showShortToast(language.getMessageErrorAtParsing());



                                WeeklyAgendaModel model = listOfWeeklyAgenda1.get(selectedIndex);

                                showActivityDialog(selectedIndex,model);
                            }
                        }
                    }
                };
                String projectId="";
                if(listOfWeeklyAgenda1.get(selectedIndex).getProjekt().equalsIgnoreCase("0")){
                    projectId=listOfWeeklyAgenda1.get(selectedIndex).getProjekt();
                }
                else {
                    projectId=listOfWeeklyAgenda1.get(selectedIndex).getProjekt();
                }
                String url = DataHelper.URL_AGENDA_HELPER + "agendaprojectlistshow" //+ DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.AGENDA_PROJECT_LIST_SHOW
                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/fieldname=" + "Projekt"
                        + "/value=" + projectId ;
                Log.e("url", url);
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mWeekView.notifyDatasetChanged();
    }
    private void showActivityDialog(int pos, final WeeklyAgendaModel weekModel)
    {
        activityDialog = showCustomDialog2(R.layout.project_activity_detail2,"");
        buttonDialogAddTradeCancel = (Button) activityDialog.findViewById(R.id.buttonDialogAddTradeCancel);
        btnDone = (Button) activityDialog.findViewById(R.id.buttonDone);
        btnEdit = (Button) activityDialog.findViewById(R.id.buttonEdit);
        buttonAddProjectActivityKunde=(ImageButton) activityDialog.findViewById(R.id.buttonAddProjectActivityKunde);


        spinnerProjectActivityActivityType = (Spinner)activityDialog.findViewById(R.id.spinnerProjectActivityActivityType);
        spinnerProjectActivityActivityTopic = (Spinner)activityDialog.findViewById(R.id.spinnerProjectActivityActivityTopic);
        spinnerProjectActivityOffer = (Spinner)activityDialog.findViewById(R.id.spinnerProjectActivityOffer);

        labelProjectActivityStartDate = (TextView)activityDialog.findViewById(R.id.labelProjectActivityStartDate);
        labelProjectActivityStartTime = (TextView)activityDialog.findViewById(R.id.labelProjectActivityStartTime);
        labelProjectActivityEndTime = (TextView)activityDialog.findViewById(R.id.labelProjectActivityEndTime);
        buttonAddProject = (ImageButton) activityDialog.findViewById(R.id.buttonAddProject);

        imageButtonProjectActivityStartDate = (ImageButton)activityDialog.findViewById(R.id.imageButtonProjectActivityStartDate);
        imageButtonEndTime = (ImageButton)activityDialog.findViewById(R.id.imageButtonEndTime);
        imageButtonStartTime = (ImageButton)activityDialog.findViewById(R.id.imageButtonStartTime);

        textViewProjectActivityKunde = (TextView)activityDialog.findViewById(R.id.textViewProjectActivityKunde);
        textViewProjectSearch=(TextView)activityDialog.findViewById(R.id.textViewProject);

        checkBoxProjectActivityRealized = (CheckBox)activityDialog.findViewById(R.id.checkBoxProjectActivityRealized);
        checkBoxProjectActivityFixedTimes = (CheckBox)activityDialog.findViewById(R.id.checkBoxProjectActivityFixedTimes);

        listViewProjectActivityEmployee = (ListView)activityDialog.findViewById(R.id.listViewProjectActivityEmployee);
        listProjectActivityContacts = (ListView)activityDialog.findViewById(R.id.listProjectActivityContacts);


        buttonRemoveProjectActivityEmployee = (ImageButton)activityDialog.findViewById(R.id.buttonRemoveProjectActivityEmployee);
        buttonAddProjectActivityEmployee = (ImageButton)activityDialog.findViewById(R.id.buttonAddProjectActivityEmployee);
        buttonRemoveProjectActivityContacts = (ImageButton)activityDialog.findViewById(R.id.buttonRemoveProjectActivityContacts);
        buttonAddProjectActivityContacts = (ImageButton)activityDialog.findViewById(R.id.buttonAddProjectActivityContacts);
        setLanguage();
        try {
            labelProjectActivityStartDate.setText(weekModel.getDatum().replaceAll("-","."));
        } catch (Exception e) {
            e.printStackTrace();
        }

        labelProjectActivityStartTime.setText(changeTimeFormate(weekModel.getStartzeit()));
        labelProjectActivityEndTime.setText(changeTimeFormate(weekModel.getEndzeit()));
        if(weekModel.getRealisiert().equalsIgnoreCase("False")){
            checkBoxProjectActivityRealized.setChecked(false);
        }
        else {
            checkBoxProjectActivityRealized.setChecked(true);
        }
        if(weekModel.getFest().equalsIgnoreCase("False")){
            checkBoxProjectActivityFixedTimes.setChecked(false);
        }
        else {
            checkBoxProjectActivityFixedTimes.setChecked(true);
        }
        listOfActivityTopic = new ArrayList<>();
        listOfActivityType = new ArrayList<>();
        listOfAllOffer = new ArrayList<>();

        listOfActivityType = db.getActivityTypes();
        listOfActivityTopic = db.getActivityTopics();

        adapterActivityType = new ActivityTypeAdapter(getActivity(), listOfActivityType, R.layout.list_item_spinner_activity_type, language);
        adapterActivityTopic = new ActivityTopicAdapter(getActivity(), listOfActivityTopic, R.layout.list_item_spinner_activity_topic, language);
        offerAdapter = new OfferAdapter(getActivity(), listOfAllOffer, R.layout.list_item_offer, language);

        spinnerProjectActivityActivityType.setAdapter(adapterActivityType);
        spinnerProjectActivityActivityTopic.setAdapter(adapterActivityTopic);
        spinnerProjectActivityOffer.setAdapter(offerAdapter);

        ////////// setting actvitiy and topic
        boolean isActivityTypeSelected = false;
        for(int i = 0; i < listOfActivityType.size(); i++)
        {
            if(weekModel.getAktivitaetstytp().equals(listOfActivityType.get(i).getActivityTypeName()))
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
            if(String.valueOf(weekModel.getAktThema()).equals(listOfActivityTopic.get(i).getActivityTopicId()))
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
        //////////// end setting activity and topic

        //////////// for offer
        if(listOfAllOffer.size() > 0){
            boolean isActivityOfferSelected = false;
            for(int i = 0; i < listOfAllOffer.size(); i++)
            {
                if(weekModel.getAngebot().equals(listOfAllOffer.get(i).getOfferno()))
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
        }

        ///////////// end for offer
        //listOfAllContactPerson = matecoPriceApplication.getLoadedCustomerContactPersons(DataHelper.LoadedCustomerContactPerson, new ArrayList<ContactPersonModel>().toString());




        listOfAllContactPerson = new ArrayList<>();
        listOfSelectedContactPerson = new ArrayList<>();
        listOfRemainingContactPerson = new ArrayList<>();
        listOfAllContactPerson.addAll(matecoPriceApplication.getLoadedCustomerContactPersons(DataHelper.LoadedCustomerContactPerson, new ArrayList<ContactPersonModel>().toString()));

        selectedContactPersonAdapter = new CustomerContactPersonListAdapter(getActivity(), listOfSelectedContactPerson, R.layout.list_item_customer_contact_person);
        remainingContactPersonAdapter = new CustomerContactPersonListAdapter(getActivity(), listOfRemainingContactPerson, R.layout.list_item_customer_contact_person);

        listProjectActivityContacts.setAdapter(selectedContactPersonAdapter);
        setListViewHeightBasedOnChildren(listProjectActivityContacts);

        listOfAllEmployee = new ArrayList<>();
        listOfSelectedEmployee = new ArrayList<>();
        listOfRemainingEmployee = new ArrayList<>();

        listOfAllEmployee = db.getEmployees();
        if(weekModel.getMitarbeiterName().size() > 0){
            for(int i = 0; i < weekModel.getMitarbeiterName().size(); i++)
            {
                for(int j = 0; j < listOfAllEmployee.size(); j++)
                {
                    //Log.e("employee", listOfAllEmployee.get(j).getEmployeeId() + " " +activity.getListOfEmployeeId().get(i).getMitarbeiter());
                    if(weekModel.getMitarbeiterName().get(i).equals(listOfAllEmployee.get(j).getMitarbeiter()))
                    {
                        listOfSelectedEmployee.add(listOfAllEmployee.get(j));
                        break;
                    }
                }
            }


        }
        //listOfSelectedEmployee.addAll(weekModel.getMitarbeiterName());
        selectedEmployeeAdapter = new EmployeeAdapter(getActivity(), listOfSelectedEmployee, R.layout.list_item_employee);
        remainingEmployeeAdapter = new EmployeeAdapter(getActivity(), listOfRemainingEmployee, R.layout.list_item_employee);
        listViewProjectActivityEmployee.setAdapter(selectedEmployeeAdapter);
        setListViewHeightBasedOnChildren(listViewProjectActivityEmployee);


        if(weekModel.getAnsprechpartner().size() > 0){
            for(int i = 0; i < weekModel.getAnsprechpartner().size(); i++)
            {
                for(int j = 0; j < listOfAllContactPerson.size(); j++)
                {
                    if(weekModel.getAnsprechpartner().get(i).equals(listOfAllContactPerson.get(j).getAnredeID()))
                    {
                        listOfSelectedContactPerson.add(listOfAllContactPerson.get(j));
                        break;
                    }
                }
            }
        }



        makeEditable(false);
        imageButtonProjectActivityStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker2();
            }
        });
        imageButtonStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker(listenerStartTime, "start");
            }
        });
        imageButtonEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker(listenerEndTime, "end");

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
        listViewProjectActivityEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedEmployeeAdapter.setSelectedIndex(position);
            }
        });

        buttonAddProjectActivityContacts.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!kontaktId.equalsIgnoreCase(""))
                    showAddContactPersonDialog();
            }
        });
        buttonAddProjectActivityEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddEmployeeDialog();
            }
        });
        buttonRemoveProjectActivityEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                makeEditable(false);
                //updateCustomerActivityInfo();
                updateProjectActivityInfo(weekModel);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                makeEditable(true);
            }
        });
        buttonAddProjectActivityKunde.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showCustomerDialog(weekModel);
            }
        });
        buttonAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showProjectSearchDialog();
            }
        });
        buttonDialogAddTradeCancel.setText(language.getLabelCancel());

        buttonDialogAddTradeCancel.setOnClickListener(new View.OnClickListener()
                                                      {
                                                          @Override
                                                          public void onClick(View v)
                                                          {
                                                              activityDialog.dismiss();
                                                          }
                                                      }

        );

        listOfLoadedCustomerActivity = new ArrayList<>();
        listOfAllProject = new ArrayList<>();
        if(matecoPriceApplication.isCustomerLoaded(DataHelper.isCustomerLoaded, false));
        {
            listOfLoadedCustomerActivity = matecoPriceApplication.getLoadedCustomerActivities(DataHelper.LoadedCustomerActivity, new ArrayList<CustomerActivityModel>().toString());
        }
        listOfAllProject.addAll(matecoPriceApplication.getLoadedCustomerProjects(DataHelper.LoadedCustomerProject, new ArrayList<CustomerProjectModel>().toString()));

        selectCustomerActivityAccordingToSelectionBundleOrDefault(weekModel);

        activityDialog.show();
    }
    private void selectCustomerActivityAccordingToSelectionBundleOrDefault(WeeklyAgendaModel weekmodel)
    {
        selectedActivity = new CustomerActivityModel();
        selectedCustomerOfferModel = new CustomerOfferModel();
        selectedCustomerProjectModel = new CustomerProjectModel();
        if(listOfLoadedCustomerActivity.size() > 0)
        {

                    for(int i = 0; i < listOfLoadedCustomerActivity.size(); i++)
                    {
                        if(listOfLoadedCustomerActivity.get(i).getAktivitaet().equals(weekmodel.getAktivitaet()))
                        {
                            //listViewCustomerActivityList.setSelection(i);
                            selectedActivity = listOfLoadedCustomerActivity.get(i);
                            setCustomerActivity(selectedActivity);
                            //customerActivityListAdapter.setSelectedIndex(i);
                            break;
                        }
                    }



        }
    }
    private void setCustomerActivity(CustomerActivityModel activity)
    {
        selectedActivityTypeModel = new ActivityTypeModel();
        selectedActivityTopicModel = new ActivityTopicModel();
        //clearAllValues();
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
            boolean isActivityProjectSelected = false;
            for(int i = 0; i < activity.getListOfEmployeeId().size(); i++)
            {
                for(int j = 0; j < listOfAllEmployee.size(); j++)
                {
                    //Log.e("employee", listOfAllEmployee.get(j).getEmployeeId() + " " +activity.getListOfEmployeeId().get(i).getMitarbeiter());
                    if(activity.getListOfEmployeeId().get(i).getMitarbeiter().equals(listOfAllEmployee.get(j).getMitarbeiter()))
                    {
                        listOfSelectedEmployee.add(listOfAllEmployee.get(j));
                        break;
                    }
                }
            }
            selectedEmployeeAdapter.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(listViewProjectActivityEmployee);
            /*for(int i = 0; i < listOfAllProject.size(); i++)
            {
                Log.e("project", activity.getProjektID() + " " + listOfAllProject.get(i).getProjectId());
                Log.e("project name", activity.getProjekt() + listOfAllProject.get(i).getProjectName());
                if(activity.getProjektID().equals(listOfAllProject.get(i).getProjectId()))
                {
                    spinnerCustomerActivityProjects.setSelection(i + 1);
                    selectedCustomerProjectModel = listOfAllProject.get(i);
                    isActivityProjectSelected = true;
                    break;
                }
            }
            if(!isActivityProjectSelected)
            {
                spinnerCustomerActivityProjects.setSelection(0);
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
                    //Log.e("employee", listOfAllEmployee.get(j).getEmployeeId() + " " +activity.getListOfEmployeeId().get(i).getMitarbeiter());
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
            }*/
        }
    }
    /*private void updateCustomerActivityInfo()
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
        String project = "";
        if(selectedCustomerProjectModel != null)
        {
            project = selectedCustomerProjectModel.getProjectId();
        }
        String date = DataHelper.formatDateToOriginal(labelProjectActivityStartDate.getText().toString());
        //String startTime =  DataHelper.formatTimeToOriginal(labelProjectActivityStartDate.getText().toString(), labelProjectActivityStartTime.getText().toString());
        //String endTime = DataHelper.formatTimeToOriginal(labelProjectActivityStartDate.getText().toString(), labelProjectActivityEndTime.getText().toString());
        String startTime =  labelProjectActivityStartDate.getText().toString()+" "+labelProjectActivityStartTime.getText().toString();
        String endTime = labelProjectActivityStartDate.getText().toString()+" "+labelProjectActivityEndTime.getText().toString();
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


        //String note = textCustomerActivityNotes.getText().toString();
        String note="";
        String kontakt = matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();

        CustomerActivityUpdateModel customerActivityUpdateModel = new CustomerActivityUpdateModel();
        customerActivityUpdateModel.setAkttyp(activityType);
        customerActivityUpdateModel.setAktthema(activityTopic);
        customerActivityUpdateModel.setNotiz(note);
        Log.e("project", project + "");
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
            BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResultCustomerActivityUpdate = new BasicAsyncTaskGetRequest.OnAsyncResult()
            {
                @Override
                public void OnAsynResult(String result)
                {
                    Log.e("result", result);
                    if(result.equals("error"))
                    {
                        showShortToast(language.getMessageError());
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
                            Log.e("customerActivityGetMod", customerActivityGetModel.getListOfActivities().toString());
                            String listOfActivity = new Gson().toJson(customerActivityGetModel.getListOfActivities());
                            Log.e("listOfActivity", listOfActivity);
                            matecoPriceApplication.saveData(DataHelper.LoadedCustomerActivity, listOfActivity);
                            String listOfProjects = new Gson().toJson(customerActivityGetModel.getListOfProject());
                            Log.e("listOfProjects", listOfProjects);
                            matecoPriceApplication.saveData(DataHelper.LoadedCustomerProject, listOfProjects);
                            String listOfOffer = new Gson().toJson(customerActivityGetModel.getListOfOffers());
                            Log.e("listOfOffer", listOfOffer);
                            matecoPriceApplication.saveData(DataHelper.LoadedCustomerOffer, listOfOffer);
                            String kontakt = matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();
                            if(db.isCustomerAvailable(kontakt))
                            {
                                db.updateCustomerActivityProjectOffer(kontakt, listOfLoadedCustomerActivity, listOfAllProject, listOfAllOffer);
                            }

                            //clearAllValues();
                            listOfLoadedCustomerActivity.addAll( matecoPriceApplication.getLoadedCustomerActivities(DataHelper.LoadedCustomerActivity, new ArrayList<CustomerActivityModel>().toString()));
                            if(listOfLoadedCustomerActivity.size() > 0)
                            {
                                setCustomerActivity(listOfLoadedCustomerActivity.get(0));
                                //customerActivityListAdapter.setSelectedIndex(0);
                                //listViewCustomerActivityList.setSelection(0);
                                selectedActivity = listOfLoadedCustomerActivity.get(0);
                            }
                            //customerActivityListAdapter.notifyDataSetChanged();
                            makeEditable(false);
                            activityDialog.dismiss();
                            *//*if(checkAllRealized(listOfLoadedCustomerActivity))
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
                            }*//*
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
                Log.e("json", json);
                String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.UPDATE_CUSTOMER_ACTIVITY
                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "&customeractivityupdate=" + URLEncoder.encode(json, "UTF-8");
                Log.e("url", url);
                asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResultCustomerActivityUpdate, getActivity(), true);
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
    }*/
    private void updateProjectActivityInfo(WeeklyAgendaModel weekModel)
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
        //String note = textProjectActivityNotes.getText().toString();
        String note = "";
        //String kontakt = matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();

        ProjectActivityUpdateModel projectActivityUpdateModel = new ProjectActivityUpdateModel();
        projectActivityUpdateModel.setAkttyp(activityType);
        projectActivityUpdateModel.setAktthema(activityTopic);
        projectActivityUpdateModel.setNotiz(note);
        //ProjectDetailGenerallyModel projectGenerallyDetail = matecoPriceApplication.getProjectDetailGenerallyModel(DataHelper.LoadedProjectDetailGenerallyInfo, new ProjectDetailGenerallyModel().toString());

        //Log.e("project", project + "");
        //projectActivityUpdateModel.setObjekt(projectGenerallyDetail.getProjekt() + "");

        projectActivityUpdateModel.setWithoutKontactAndProject("true");
        projectActivityUpdateModel.setObjekt(objectId);
        projectActivityUpdateModel.setAngebot(offer);
        projectActivityUpdateModel.setStartdatum(date);
        projectActivityUpdateModel.setStartzeit(startTime);
        projectActivityUpdateModel.setEndzeit(endTime);
        projectActivityUpdateModel.setRealisiert(realized);
        projectActivityUpdateModel.setFest(fest);
        projectActivityUpdateModel.setFirstName(FirstName);
        projectActivityUpdateModel.setAktivitaet(weekModel.getAktivitaet());
        projectActivityUpdateModel.setNotiz(weekModel.getNotiz());



        //projectActivityUpdateModel.setKontakt(listOfLoadedProjectActivity.get(adapterProjectActivityList.selectedIndex).getKontakt());
        /*if(matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, "") != null){
            projectActivityUpdateModel.setKontakt(matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, "").getKontakt());
        }
        else {
            projectActivityUpdateModel.setKontakt("");
        }*/
        projectActivityUpdateModel.setKontakt(kontaktId);


        projectActivityUpdateModel.setGeloescht("0");
        projectActivityUpdateModel.setAenderungMitarbeiter(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber());
        String loginUserId = matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserId();
//        customerActivityUpdateModel.setAenderungMitarbeiter(loginUserId);
        //if(selectedActivity != null)
            //projectActivityUpdateModel.setAktivitaet(selectedActivity.getAktivitaet());

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
                    Log.e("result", result);
                    if(result.equals("error"))
                    {
                        showShortToast(language.getMessageError());
                        activityDialog.dismiss();
                    }
                    else if(result.equals(DataHelper.NetworkError)){
                        showShortToast(language.getMessageNetworkNotAvailable());
                    }
                    else {
                        try
                        {
                            String project="";
                            try
                            {
                                // jsonString is a string variable that holds the JSON
                                JSONArray jsonArray=new JSONArray(result);

                                for (int i = 0; i < jsonArray.length(); i++)
                                {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    project=jsonObject.getString("Projekt");

                                }
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            if(project.equalsIgnoreCase("-1")){
                                activityDialog.dismiss();
                                adapterLoad();

                            }
                            else{
                                activityDialog.dismiss();
                                adapterLoad();
                            }
                            //matecoPriceApplication.saveData(DataHelper.LoadedProjectDetailActivityInfo, result);
                            //listOfLoadedProjectActivity.clear();
                            //listOfLoadedProjectActivity.addAll(matecoPriceApplication.getLoadedProjectActivities(DataHelper.LoadedProjectDetailActivityInfo, new ArrayList<>().toString()));
                            //adapterProjectActivityList.notifyDataSetChanged();
                            //menu.findItem(R.id.actionAdd).setVisible(true);
                            //menu.findItem(R.id.actionEdit).setVisible(true);
                            ///menu.findItem(R.id.actionRight).setVisible(false);
                            ///menu.findItem(R.id.actionWrong).setVisible(false);
                            // menu.findItem(R.id.actionBack).setVisible(true);
                            //clearAllValues();
                            flag = true;
                            //addNewActivityFormRedesign(false);
                            makeEditable(false);
                            /*if(listOfLoadedProjectActivity.size() > 0)
                            {
                                adapterProjectActivityList.setSelectedIndex(0);
                                selectedActivity = listOfLoadedProjectActivity.get(0);
                                setProjectActivity(listOfLoadedProjectActivity.get(0));
                            }
                            else
                            {
                                adapterProjectActivityList.setSelectedIndex(-1);
                            }*/
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                            showShortToast(language.getMessageErrorAtParsing());
                            activityDialog.dismiss();
                        }
                    }
                }
            };
            try
            {
                Log.e("json", json);
                //String url = DataHelper.URL_PROJECT_HELPER+"ProjectActivityUpdate";
                /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.PROJECT_ACTIVITY_UPDATE
                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "&projectactivityupdate=" + URLEncoder.encode(json, "UTF-8");
                Log.e("url", url);
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResultCustomerActivityUpdate, getActivity(), true);
                asyncTask.execute();*/
                String url = DataHelper.URL_PROJECT_HELPER+"projectactivityupdate";
                Log.e("url", url);
                MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")));
                multipartEntity.addPart("projectactivityupdate", new StringBody(json, Charset.forName("UTF-8")));
                AsyncTaskWithAuthorizationHeaderPost asyncTaskPost = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, getActivity(), multipartEntity, true, language);
                asyncTaskPost.execute();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
                activityDialog.dismiss();
            }
        }
        else
        {
            showShortToast(language.getMessageNetworkNotAvailable());
        }
    }
    private void showAddContactPersonDialog()
    {
        final Dialog dialog = showCustomDialog(R.layout.dialog_add_employee, language.getMessageSelectContactPerson());
        if(isEdited){
            if(listOfRemainingContactPersontemp != null && listOfRemainingContactPersontemp.size() > 0){
                listOfRemainingContactPerson.clear();
                listOfRemainingContactPerson.addAll(listOfRemainingContactPersontemp);
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
    private void setLanguage()
    {
        TextView labelProjectActivityDetailActivity, labelProjectActivityDetailTopic, labelProjectActivityDetailStartDate,
                labelProjectActivityDetailStartTime, labelProjectActivityDetailEndTime, labelProjectActivityDetailKunde,
                labelProjectActivityDetailRealized, labelProjectActivityDetailContacts, labelProjectActivityDetailEmployee,
                labelProjectActivityDetailOffer,labelProjectActivityDetailFixedTimes;

        labelProjectActivityDetailOffer = (TextView)rootView.findViewById(R.id.labelProjectActivityDetailOffer);
        labelProjectActivityDetailActivity = (TextView)activityDialog.findViewById(R.id.labelProjectActivityDetailActivity);
        labelProjectActivityDetailTopic = (TextView)activityDialog.findViewById(R.id.labelProjectActivityDetailTopic);
        labelProjectActivityDetailStartDate = (TextView)activityDialog.findViewById(R.id.labelProjectActivityDetailStartDate);
        labelProjectActivityDetailStartTime = (TextView)activityDialog.findViewById(R.id.labelProjectActivityDetailStartTime);
        labelProjectActivityDetailEndTime = (TextView)activityDialog.findViewById(R.id.labelProjectActivityDetailEndTime);
        labelProjectActivityDetailKunde = (TextView)activityDialog.findViewById(R.id.labelProjectActivityDetailKunde);
        labelProjectActivityDetailRealized = (TextView)activityDialog.findViewById(R.id.labelProjectActivityDetailRealized);
        labelProjectActivityDetailContacts = (TextView)activityDialog.findViewById(R.id.labelProjectActivityDetailContacts);
        labelProjectActivityDetailEmployee = (TextView)activityDialog.findViewById(R.id.labelProjectActivityDetailEmployee);
        labelProjectActivityDetailFixedTimes = (TextView)activityDialog.findViewById(R.id.labelProjectActivityDetailFixedTimes);
        ///////////////////////////////////////////////
        // ///////////
        labelProjectActivityDetailActivity.setText(language.getLabelActivity());
        labelProjectActivityDetailTopic.setText(language.getLabelTopic());
        labelProjectActivityDetailStartDate.setText(language.getLabelDate());
        labelProjectActivityDetailStartTime.setText(language.getLabelStartTime());
        labelProjectActivityDetailEndTime.setText(language.getLabelEndTime());
        //labelProjectActivityDetailOffer.setText(language.getLabelOffer());
        labelProjectActivityDetailRealized.setText(language.getLabelRealized());
        labelProjectActivityDetailFixedTimes.setText(language.getLabelFixedTimes());
        labelProjectActivityDetailContacts.setText(language.getLabelContacts());
//        labelProjectActivityDetailAnsprechPhone.setText(language.getLabelPhone());
//        labelProjectActivityDetailAnsprechMobile.setText(language.getLabelMobile());
        labelProjectActivityDetailEmployee.setText(language.getLabelEmployee());

        labelProjectActivityDetailKunde.setText(language.getLabelCustomer());
    }
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

        adapterAutocompleteProject = new AutoCompleteSearchAdapterProject(getActivity());

        textProjectSearchMatchCode = (DelayAutoCompleteTextView) projectDialog.findViewById(R.id.textProjectSearchMatchCode);
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
                    lastFocusedvalueProject=FOCUSED_MATCHCODE_PROJECT;
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
                    lastFocusedvalueProject=FOCUSED_DESCRIPTION;
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
                    lastFocusedvalueProject=FOCUSED_TYPE_OF_PROJECT;
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
                    lastFocusedvalueProject=FOCUSED_ADDRESS;
                }
            }
        });

        textProjectSearchFrom = (DelayAutoCompleteTextView)projectDialog.findViewById(R.id.textProjectSearchFrom);
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
                    lastFocusedvalueProject=FOCUSED_FROM;
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
                    lastFocusedvalueProject=FOCUSED_TO;
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
                    lastFocusedvalueProject=FOCUSED_EMPLOYEE;
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
                    lastFocusedvalueProject=FOCUSED_PLACE;
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

    public void showCustomerDialog(final WeeklyAgendaModel weekModel)
    {
        final Dialog dialog = showCustomDialog(R.layout.fragment_customer_search2, language.getLabelSearch());
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

        Button btnSearch,btnDone;


        labelCustomerSearchCustomerNo = (TextView)dialog.findViewById(R.id.labelCustomerSearchCustomerNo);
        labelCustomerSearchKanr = (TextView)dialog.findViewById(R.id.labelCustomerSearchKanr);
        labelCustomerSearchMatchCode = (TextView)dialog.findViewById(R.id.labelCustomerSearchMatchCode);
        labelCustomerSearchName1 = (TextView)dialog.findViewById(R.id.labelCustomerSearchName1);
        labelCustomerSearchRoad = (TextView)dialog.findViewById(R.id.labelCustomerSearchRoad);
        labelCustomerSearchZipCode = (TextView)dialog.findViewById(R.id.labelCustomerSearchZipCode);
        labelCustomerSearchPlace = (TextView)dialog.findViewById(R.id.labelCustomerSearchPlace);
        labelCustomerSearchTel = (TextView)dialog.findViewById(R.id.labelCustomerSearchTel);
        btnSearch = (Button)dialog.findViewById(R.id.btnSearch);
        btnDone = (Button)dialog.findViewById(R.id.btnright);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchForResults();
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

        labelCustomerSearchCustomerNo.setText(language.getLabelCustomerNo());
        labelCustomerSearchKanr.setText(language.getLabelKanr());
        labelCustomerSearchMatchCode.setText(language.getLabelMatchCode());
        labelCustomerSearchName1.setText(language.getLabelName() + " 1");
        labelCustomerSearchRoad.setText(language.getLabelRoad());
        labelCustomerSearchZipCode.setText(language.getLabelZipCode());
        labelCustomerSearchPlace.setText(language.getLabelPlace());
        labelCustomerSearchTel.setText(language.getLabelTelephone());
        listOfCustomerSearchResult = new ArrayList<>();
        adapterSearch = new CustomerSearchResultAdapter(getActivity(), listOfCustomerSearchResult);
        listCustomerSearchResult.setAdapter(adapterSearch);
        footerView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_list_button, null, false);
        footerButton = (Button)footerView.findViewById(R.id.buttonLoadMore);
        pageSize = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).getInt("CustomerPageSize", 10);

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.show();

        listCustomerSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                //dialog.dismiss();
                adapterSearch.setSelectedIndex(position);
                /*if (!(adapterSearch.selectedIndex == -1)) {
                    getCustomerDetails2(adapterSearch.selectedIndex, weekModel);

                } else {
                    showShortToast(language.getMessageSelectCustomerFirst());
                }*/
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (!(adapterSearch.selectedIndex == -1)) {
                    getCustomerDetails2(adapterSearch.selectedIndex, weekModel);

                } else {
                    showShortToast(language.getMessageSelectCustomerFirst());
                }
            }
        });

        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchForNewPage();
            }
        });

    }
    private void searchForNewPage()
    {
        isCallservice=true;
        Log.e("search for new page", "called");
        //loadingMore = true;
        matecoPriceApplication.hideKeyboard(getActivity());
        //listOfCustomerSearchResult.clear();
        adapterSearch.setSelectedIndex(-1);
        adapterSearch.notifyDataSetChanged();
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
            Log.e("customer to json", jsonToSend);
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
            Log.e("url at customer search", url);
            if(DataHelper.isNetworkAvailable(getActivity()))
            {
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        Log.e("result ", result);
                        hideProgressDialog();
                        if(result.equals("error"))
                        {
                            showLongToast(language.getMessageError());
                        }
                        else if(result.equals(DataHelper.NetworkError)){
                            Log.e(" ###### "," network problem : "+result);
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
                                Log.e("size add page" + pageNuber, resultsOfCustomers.length()+"");
                                if(result.length() == 0)
                                {
                                    showLongToast(language.getMessageNoResultFound());
                                }
                                else
                                {
                                    CustomerModel.extractFromJson(resultsOfCustomers.toString(), listOfCustomerSearchResult);
                                    adapterSearch.notifyDataSetChanged();
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
                Log.e("clear", listOfCustomerSearchResult.size()+"");
                adapterSearch.notifyDataSetChanged();
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
    private void searchForResults()
    {
        isCallservice=true;
        matecoPriceApplication.hideKeyboard(getActivity());
        listOfCustomerSearchResult.clear();
        adapterSearch.setSelectedIndex(-1);
        adapterSearch.notifyDataSetChanged();
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
            Log.e("customer to json", jsonToSend);
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
            Log.e("url at customer search", url);
            if(DataHelper.isNetworkAvailable(getActivity()))
            {
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        Log.e("result ", result);
                        hideProgressDialog();
                        if(result.equals("error"))
                        {
                            showLongToast(language.getMessageError());
                        }
                        else if(result.equals(DataHelper.NetworkError)){
                            Log.e(" ###### "," network problem : "+result);
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
                                    adapterSearch.notifyDataSetChanged();
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
                Log.e("clear", listOfCustomerSearchResult.size()+"");
                adapterSearch.notifyDataSetChanged();
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
    private void searchForResultsProject()
    {
        isCallservice=true;
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
            pageNuber2 = 1;
            projectSearchPagingRequestModel.setPageSize(pageSize2 + "");
            //listViewProjects.removeFooterView(footerView);
            lianearMain.removeView(footerView2);
            projectSearchPagingRequestModel.setPageNumber(pageNuber2+"");
            String jsonToSend = DataHelper.getGson().toJson(projectSearchPagingRequestModel);
            Log.e("customer to json", jsonToSend);
            if(DataHelper.isNetworkAvailable(getActivity()))
            {
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        Log.e("result project search", result);
                        hideProgressDialog();
                        if(result.equals("error"))
                        {
                            showShortToast(language.getMessageError());
                        }
                        else if(result.equals(DataHelper.NetworkError)){
                            Log.e(" ###### "," network problem : "+result);
                           // showLongToast("Network problem while service calling before");
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
                                    //listViewProjects.addFooterView(footerView);
                                    lianearMain.addView(footerView2);
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
                    Log.e("url", url);
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
                        Log.e("result",result);
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
                                textViewProjectSearch.setText(projectGenerallyDetail.getBeschreibung());
                                objectId=projectGenerallyDetail.getProjekt();
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
                String url = DataHelper.URL_AGENDA_HELPER + "agendaprojectlistshow" //+ DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.AGENDA_PROJECT_LIST_SHOW
                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/fieldname=" + "Projekt"
                        + "/value=" + listofProject.get(selectedIndex).getProjekt();
                //Projekt
//                String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.PROJECT_LIST_SHOW
//                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
//                        + "&objekt=" + listofProject.get(selectedIndex).getProjekt();
                Log.e("url", url);
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

    private void searchForNewPageProjectSearch()
    {
        isCallservice=true;
        Log.e("search for new page", "called");
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
            Log.e("customer to json", jsonToSend);
            String url = "";
            try
            {
                url = DataHelper.URL_PROJECT_HELPER+"projectsearchpaging"
                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/projectsearchparam=" + URLEncoder.encode(jsonToSend, "UTF-8");

                /*url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.Project_Search_Paging
                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "&projectsearchparam=" + URLEncoder.encode(jsonToSend, "UTF-8");*/
                Log.e("url", url);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            Log.e("url at customer search", url);
            if(DataHelper.isNetworkAvailable(getActivity()))
            {
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        Log.e("result ", result);
                        hideProgressDialog();
                        if(result.equals("error"))
                        {
                            showLongToast(language.getMessageError());
                        }
                        else if(result.equals(DataHelper.NetworkError)){
                            Log.e(" ###### "," network problem : "+result);
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

                                if(pageNuber == totalPageCount)
                                {
                                    //listViewProjects.removeFooterView(footerView);
                                    lianearMain.removeView(footerView);
                                }

                                JSONArray resultsOfProjects = jsonObject.getJSONArray("Result");
                                Log.e("size add page" + pageNuber, resultsOfProjects.length()+"");
                                if(resultsOfProjects.length() == 0)
                                {
                                    showLongToast(language.getMessageNoResultFound());
                                }
                                else
                                {
                                    ProjectModel.extractFromJson(resultsOfProjects.toString(), listofProject);
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
                showShortToast(language.getMessageNetworkNotAvailable());
            }
        }
        else
        {
            showLongToast(language.getMessageInputSearchParameter());
        }
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
    private void makeEditable(boolean editable)
    {
        spinnerProjectActivityActivityTopic.setEnabled(editable);
        spinnerProjectActivityActivityType.setEnabled(editable);
        spinnerProjectActivityOffer.setEnabled(editable);
        checkBoxProjectActivityFixedTimes.setEnabled(editable);
        checkBoxProjectActivityRealized.setEnabled(editable);
        imageButtonEndTime.setEnabled(editable);
        imageButtonProjectActivityStartDate.setEnabled(editable);
        imageButtonStartTime.setEnabled(editable);
        buttonAddProject.setEnabled(editable);
        buttonAddProjectActivityContacts.setEnabled(editable);
        buttonAddProjectActivityEmployee.setEnabled(editable);
        buttonAddProjectActivityKunde.setEnabled(editable);
        buttonRemoveProjectActivityContacts.setEnabled(editable);
        buttonRemoveProjectActivityEmployee.setEnabled(editable);

        listViewProjectActivityEmployee.setEnabled(editable);
        listProjectActivityContacts.setEnabled(editable);
    }
    private void openDatePicker2()
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
        newFragment.setCallBack(onFromDate2);
        Log.e("tag", "startDate");
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
            if(labelProjectActivityStartTime.getText().toString().equals(""))
            {
                showShortToast(language.getMessageSelectStartTime());
            }
            else
            {
                SimpleDateFormat outputFormatter = new SimpleDateFormat("HH:mm");
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
    DatePickerDialog.OnDateSetListener onFromDate2 = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            try
            {
                monthOfYear += 1;
                String date = dayOfMonth + "." + monthOfYear + "." + year;
                String finaldate = DataHelper.formatDate(formatter.parse(date));
                Log.e("startDate", finaldate);
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
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            String endTime = String.format("%02d:%02d", hourOfDay, minute);
            Log.e("end time",endTime);
            Date inTime = null;
            try
            {
                inTime = sdf.parse(labelProjectActivityStartTime.getText().toString());
                Date outTime = sdf.parse(endTime);
                Log.e("diff",inTime.compareTo(outTime)+"");
                if(inTime.compareTo(outTime) >= 0)
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
    public String changeTimeFormate(String time){
        String changeString="";
        Date date=null;
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            date=dateFormat.parse(time);

            changeString = dateFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  changeString;
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
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
                        //Log.v(MainActivity.TAG, "Search string: " + constraint.toString());
                        findHintsFromApi(mContext, constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = arraylistHint;
                        filterResults.count = arraylistHint.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    // Log.v(MainActivity.TAG, "publishResults");
                    if (results != null && results.count > 0) {
                        //Log.v(MainActivity.TAG, "publishResult OK.");
                        arraylistHint = (List<HintModel>) results.values;
                        notifyDataSetChanged();
                    } else {
                        //Log.v(MainActivity.TAG, "publishResult Invalid.");
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
                    Log.e("customer to json", jsonToSend);
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
                                    Log.e(" test "," hint list size : "+ arraylistHint.size());
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
    public class AutoCompleteSearchAdapterProject extends BaseAdapter implements Filterable {

        private static final int MAX_RESULTS = 10;
        private Context mContext;
        private List<HintModel> arraylistHint;

        public AutoCompleteSearchAdapterProject(Context context) {
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
                        //Log.v(MainActivity.TAG, "Search string: " + constraint.toString());
                        findHintsFromApi(mContext, constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = arraylistHint;
                        filterResults.count = arraylistHint.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    // Log.v(MainActivity.TAG, "publishResults");
                    if (results != null && results.count > 0) {
                        //Log.v(MainActivity.TAG, "publishResult OK.");
                        arraylistHint = (List<HintModel>) results.values;
                        notifyDataSetChanged();
                    } else {
                        //Log.v(MainActivity.TAG, "publishResult Invalid.");
                        notifyDataSetInvalidated();
                    }
                }};
            return filter;
        }

        /**
         * Returns a search result for the given book title.
         */
        private void findHintsFromApi(final Context context, String search) {
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

                    if(lastFocusedvalueProject.equalsIgnoreCase(FOCUSED_MATCHCODE_PROJECT)){
                        projectSearchPagingRequestModel.setMatchcode(matchCode);
                    }
                    else if(lastFocusedvalueProject.equalsIgnoreCase(FOCUSED_DESCRIPTION)){
                        projectSearchPagingRequestModel.setBeschreibung(description);
                    }
                    else if(lastFocusedvalueProject.equalsIgnoreCase(FOCUSED_TYPE_OF_PROJECT)){
                        projectSearchPagingRequestModel.setObjektTyp(typeOfProject);
                    }
                    else if(lastFocusedvalueProject.equalsIgnoreCase(FOCUSED_ADDRESS)){
                        projectSearchPagingRequestModel.setAdresse(address);
                    }
                    else if(lastFocusedvalueProject.equalsIgnoreCase(FOCUSED_FROM)){
                        projectSearchPagingRequestModel.setBeginn(from);
                    }
                    else if(lastFocusedvalueProject.equalsIgnoreCase(FOCUSED_EMPLOYEE)){
                        projectSearchPagingRequestModel.setMitrabeiter(employee);
                    }
                    else if(lastFocusedvalueProject.equalsIgnoreCase(FOCUSED_TO)){
                        projectSearchPagingRequestModel.setEnde(to);
                    }
                    else if(lastFocusedvalueProject.equalsIgnoreCase(FOCUSED_PLACE)){
                        projectSearchPagingRequestModel.setOrt(place);
                    }
                    projectSearchPagingRequestModel.setPageSize(GlobalClass.pageSizeForHint + "");
                    pageNuber = 1;
                    projectSearchPagingRequestModel.setPageNumber(GlobalClass.pageNuber+"");
                    jsonToSendProject = DataHelper.getGson().toJson(projectSearchPagingRequestModel);
                    Log.e("customer to json", jsonToSendProject);
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
                                    Log.e(" test "," hint list size : "+ arraylistHint.size());
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


