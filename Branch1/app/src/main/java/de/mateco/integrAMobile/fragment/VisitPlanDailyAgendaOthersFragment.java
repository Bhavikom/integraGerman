package de.mateco.integrAMobile.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.DatePickerDialogFragment;
import de.mateco.integrAMobile.Helper.FuturePastAgendaComparable;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.DailyAgendaOtherAdapter;
import de.mateco.integrAMobile.adapter.EmployeeAdapter;
import de.mateco.integrAMobile.adapter.SimpleStringAdapter;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.CustomerFullDetailModel;
import de.mateco.integrAMobile.model.DailyAgendaModel;
import de.mateco.integrAMobile.model.EmployeeModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LoginPersonModel;

public class VisitPlanDailyAgendaOthersFragment extends BaseFragment implements View.OnClickListener
{
    String activityId="";
    private View rootView;
    private MatecoPriceApplication matecoPriceApplication;
    private Language language;
    private DataBaseHandler db;
    private ListView listViewWeeklyDue, listViewWeeklyFuture;
    private EditText textAgendaCustomerDetails, textAgendaProjectDetails, textAgendaOfferDetails,
            textAgendaNotice, textAgendaActivityStartTime, textAgendaActivityEndTime;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    private ArrayList<DailyAgendaModel> listWeeklyDue, listWeeklyFuture;
    private DailyAgendaOtherAdapter adapterAgendaWeeklyDue, adapterAgendaWeeklyFuture;
    private Button buttonCustomerDetails, buttonProjectDetails;
    private ArrayList<EmployeeModel> listOfEmployee;

    private EmployeeModel selectedEmployee;
    private ListView listViewAgendaEmployee, listViewAgendaContacts;
    private ArrayList<String> listOfAgendaRelatedContactPerson, listOfAgendaRelatedEmployee;
    private SimpleStringAdapter adapterAgendaRelatedContactPerson, adapterAgendaRelatedEmployee;
    private DailyAgendaModel model = null;
    private boolean isFirstTimeSelectedSpinnerEmployee = false;
    private String loginPersonId = "",dateString = "";
    private boolean isAscending = false, isAscendingFuture = false;
    private ImageView imageSortName, imageSortDate, imageSortType;
    private ImageView imageSortTypeFuture, imageSortDateFuture, imageSortNameFuture;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_agenda_daily_others, container, false);
        //rootView = inflater.inflate(R.layout.temp_under_construction, container, false);
        super.initializeFragment(rootView);
//        getActivity().invalidateOptionsMenu();
//        setHasOptionsMenu(true);
        //((HomeActivity)getActivity()).getSupportActionBar().setTitle("Visit Plan Weekly");
        return rootView;
    }

    @Override
    public void initializeComponents(View rootView)
    {
        super.initializeComponents(rootView);
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        db = new DataBaseHandler(getActivity());

        listViewWeeklyDue = (ListView)rootView.findViewById(R.id.listViewWeeklyDue);
        listViewWeeklyFuture = (ListView)rootView.findViewById(R.id.listViewWeeklyFuture);

        imageSortName = (ImageView)rootView.findViewById(R.id.imageSortName);
        imageSortDate = (ImageView)rootView.findViewById(R.id.imageSortDate);
        imageSortType = (ImageView)rootView.findViewById(R.id.imageSortType);

        imageSortTypeFuture = (ImageView)rootView.findViewById(R.id.imageSortTypeFuture);
        imageSortDateFuture = (ImageView)rootView.findViewById(R.id.imageSortDateFuture);
        imageSortNameFuture = (ImageView)rootView.findViewById(R.id.imageSortNameFuture);

        textAgendaCustomerDetails = (EditText)rootView.findViewById(R.id.textAgendaCustomerDetails);
        textAgendaProjectDetails = (EditText)rootView.findViewById(R.id.textAgendaProjectDetails);
        textAgendaOfferDetails = (EditText)rootView.findViewById(R.id.textAgendaOfferDetails);
        textAgendaNotice = (EditText)rootView.findViewById(R.id.textAgendaNotice);
        textAgendaActivityStartTime = (EditText)rootView.findViewById(R.id.textAgendaActivityStartTime);
        textAgendaActivityEndTime = (EditText)rootView.findViewById(R.id.textAgendaActivityEndTime);

        listWeeklyDue = new ArrayList<>();
        listWeeklyFuture = new ArrayList<>();

        adapterAgendaWeeklyDue = new DailyAgendaOtherAdapter(getActivity(), listWeeklyDue, R.layout.list_item_daily_other_agenda);
        adapterAgendaWeeklyFuture = new DailyAgendaOtherAdapter(getActivity(), listWeeklyFuture, R.layout.list_item_daily_other_agenda);

        listViewWeeklyDue.setAdapter(adapterAgendaWeeklyDue);
        listViewWeeklyFuture.setAdapter(adapterAgendaWeeklyFuture);

        buttonProjectDetails = (Button)rootView.findViewById(R.id.buttonProjectDetails);
        buttonCustomerDetails = (Button)rootView.findViewById(R.id.buttonCustomerDetails);

        buttonCustomerDetails.setOnClickListener(this);
        buttonProjectDetails.setOnClickListener(this);

        setAgenda(matecoPriceApplication.getData(DataHelper.StoreOtherAgenda, ""));

        listViewAgendaEmployee = (ListView)rootView.findViewById(R.id.listViewAgendaEmployee);

        listViewAgendaContacts = (ListView)rootView.findViewById(R.id.listViewAgendaContacts);

        listOfAgendaRelatedContactPerson = new ArrayList<>();
        listOfAgendaRelatedEmployee = new ArrayList<>();
        adapterAgendaRelatedContactPerson = new SimpleStringAdapter(getActivity(), listOfAgendaRelatedContactPerson);
        adapterAgendaRelatedEmployee = new SimpleStringAdapter(getActivity(), listOfAgendaRelatedEmployee);

        listViewAgendaEmployee.setAdapter(adapterAgendaRelatedEmployee);
        listViewAgendaContacts.setAdapter(adapterAgendaRelatedContactPerson);

        makeEditable(false);

        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        //((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelDailyAgenda());
        setupLanguage();
    }

    @Override
    public void bindEvents(View rootView)
    {
        super.bindEvents(rootView);
        imageSortDate.setOnClickListener(this);
        imageSortType.setOnClickListener(this);
        imageSortName.setOnClickListener(this);
        imageSortNameFuture.setOnClickListener(this);
        imageSortDateFuture.setOnClickListener(this);
        imageSortTypeFuture.setOnClickListener(this);
        listViewWeeklyDue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterAgendaWeeklyDue.setSelectedIndex(position);
                showDetailAgenda(position, 0);
            }
        });
        listViewWeeklyFuture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterAgendaWeeklyFuture.setSelectedIndex(position);
                showDetailAgenda(position, 1);
            }
        });

    }

    private void showDetailAgenda(int position, int type)
    {
        clearFields();
        if (type == 0)
        {
            model = listWeeklyDue.get(position);
            adapterAgendaWeeklyFuture.setSelectedIndex(-1);
        }
        else
        {
            adapterAgendaWeeklyDue.setSelectedIndex(-1);
            model = listWeeklyFuture.get(position);
        }
        activityId = model.getAktivitaet();
        String customerDetail = model.getMatchcode() + "\n" + model.getName1() + "\n" + model.getStrasse() + "\n" + model.getPLZ() + " " + model.getOrt();
        textAgendaCustomerDetails.setText(customerDetail);
        String projectDetail = model.getMatchcode_Projekt() + "\n" + model.getBeschreibung() + "\n" + model.getAdresse() + "\n" + model.getPLZ_Projekt() + " " + model.getPLZ_Ort();
        textAgendaProjectDetails.setText(projectDetail);
        //textAgendaActivityDetails.setText(model.getAktivitaet());
        textAgendaNotice.setText(model.getNotiz());
        textAgendaOfferDetails.setText(model.getAngebot());
        if(model.getStartzeit() != null){
            if(model.getStartzeit().equals("") || model.getStartzeit().equals("00:00") || model.getStartzeit().equals("00:00:00"))
            {
                textAgendaActivityStartTime.setText("");
            }
            else
            {
                textAgendaActivityStartTime.setText(DataHelper.formatTime(model.getStartzeit()));
            }
        }

        if(model.getEndzeit() != null){
            if(model.getEndzeit().equals("") || model.getEndzeit().equals("00:00") || model.getEndzeit().equals("00:00:00"))
            {
                textAgendaActivityEndTime.setText("");
            }
            else
            {
                textAgendaActivityEndTime.setText(DataHelper.formatTime(model.getEndzeit()));
            }
        }


        listOfAgendaRelatedContactPerson.clear();
        listOfAgendaRelatedEmployee.clear();

        if(model.getAnsprechpartner() != null){
            listOfAgendaRelatedContactPerson.addAll(model.getAnsprechpartner());
        }else {
            listOfAgendaRelatedContactPerson.clear();
        }

        if(model.getMitarbeiterName() != null){
            listOfAgendaRelatedEmployee.addAll(model.getMitarbeiterName());
        }else {
            listOfAgendaRelatedEmployee.clear();
        }
        adapterAgendaRelatedEmployee.notifyDataSetChanged();
        adapterAgendaRelatedContactPerson.notifyDataSetChanged();
    }

    private void setupLanguage()
    {
        TextView labelAgendaCustomerDetails, labelAgendaProjectDetails, labelAgendaOfferDetails,
                labelAgendaNotice, labelAgendaActivityStartTime, labelAgendaActivityEndTime, labelAgendaWeeklyDueName, labelAgendaWeeklyDueDate,
                labelAgendaWeeklyDueType, labelAgendaWeeklyFutureName, labelAgendaWeeklyFutureDate, labelAgendaWeeklyFutureDesignation,
                labelAgendaWeeklyFuture, labelAgendaWeeklyOverDue, labelAgendaTodayEmployee, labelAgendaTodayContacts;
//labelFragmentAgendaDailyPhoneCalls, labelFragmentAgendaDailyMeetings,
//        labelFragmentAgendaDailyMeetings = (TextView)rootView.findViewById(R.id.labelFragmentAgendaDailyMeetings);
//        labelFragmentAgendaDailyPhoneCalls = (TextView)rootView.findViewById(R.id.labelFragmentAgendaDailyPhoneCalls);
        labelAgendaCustomerDetails = (TextView)rootView.findViewById(R.id.labelAgendaCustomerDetails);
        labelAgendaProjectDetails = (TextView)rootView.findViewById(R.id.labelAgendaProjectDetails);
        labelAgendaNotice = (TextView)rootView.findViewById(R.id.labelAgendaNotice);
        labelAgendaOfferDetails = (TextView)rootView.findViewById(R.id.labelAgendaOfferDetails);
        //labelAgendaActivityDetails = (TextView)rootView.findViewById(R.id.labelAgendaActivityDetails);
        labelAgendaActivityStartTime = (TextView)rootView.findViewById(R.id.labelAgendaActivityStartTime);
        labelAgendaActivityEndTime = (TextView)rootView.findViewById(R.id.labelAgendaActivityEndTime);
        labelAgendaWeeklyDueName = (TextView)rootView.findViewById(R.id.labelAgendaWeeklyDueName);
        labelAgendaWeeklyDueDate = (TextView)rootView.findViewById(R.id.labelAgendaWeeklyDueDate);
        labelAgendaWeeklyDueType = (TextView)rootView.findViewById(R.id.labelAgendaWeeklyDueType);
        labelAgendaWeeklyDueName.setOnClickListener(this);
        labelAgendaWeeklyDueDate.setOnClickListener(this);
        labelAgendaWeeklyDueType.setOnClickListener(this);
        labelAgendaWeeklyFutureName = (TextView)rootView.findViewById(R.id.labelAgendaWeeklyFutureName);
        labelAgendaWeeklyFutureDate = (TextView)rootView.findViewById(R.id.labelAgendaWeeklyFutureDate);
        labelAgendaWeeklyFutureDesignation = (TextView)rootView.findViewById(R.id.labelAgendaWeeklyFutureDesignation);
        labelAgendaWeeklyFutureName.setOnClickListener(this);
        labelAgendaWeeklyFutureDate.setOnClickListener(this);
        labelAgendaWeeklyFutureDesignation.setOnClickListener(this);
        labelAgendaWeeklyFuture = (TextView)rootView.findViewById(R.id.labelAgendaWeeklyFuture);
        labelAgendaWeeklyOverDue = (TextView)rootView.findViewById(R.id.labelAgendaWeeklyOverDue);
        labelAgendaTodayEmployee = (TextView)rootView.findViewById(R.id.labelAgendaTodayEmployee);
        labelAgendaTodayContacts = (TextView)rootView.findViewById(R.id.labelAgendaTodayContacts);

//        labelFragmentAgendaDailyMeetings.setText(language.getLabelMessage());
//        labelFragmentAgendaDailyPhoneCalls.setText(language.getLabelPhoneCalls());
        labelAgendaCustomerDetails.setText(language.getLabelCustomerDetailsSpace());
        labelAgendaProjectDetails.setText(language.getLabelProjectDetailsSpace());
        labelAgendaNotice.setText(language.getLabelNotice());
        labelAgendaOfferDetails.setText(language.getLabelOfferDetailsSpace());
        //labelAgendaActivityDetails.setText(language.getLabelActivityDetails());
        labelAgendaActivityStartTime.setText(language.getLabelStartTime());
        labelAgendaActivityEndTime.setText(language.getLabelEndTime());
        labelAgendaWeeklyDueName.setText(language.getLabelCustomer());
        labelAgendaWeeklyDueDate.setText(language.getLabelDate());
        labelAgendaWeeklyDueType.setText(language.getLabelActivity());
        labelAgendaWeeklyFutureName.setText(language.getLabelCustomer());
        labelAgendaWeeklyFutureDate.setText(language.getLabelDate());
        labelAgendaWeeklyFutureDesignation.setText(language.getLabelActivity());
        labelAgendaWeeklyFuture.setText(language.getLabelFuture());
        labelAgendaWeeklyOverDue.setText(language.getLabelOverDue());
        labelAgendaTodayEmployee.setText(language.getLabelEmployee());
        labelAgendaTodayContacts.setText(language.getLabelContactPerson());

        buttonProjectDetails.setText(language.getLabelProjectDetailsSpace());
        buttonCustomerDetails.setText(language.getLabelCustomerDetailsSpace());
    }

    private void makeEditable(boolean isEditable)
    {
        textAgendaActivityEndTime.setFocusable(isEditable);
        textAgendaActivityEndTime.setFocusableInTouchMode(isEditable);

        textAgendaOfferDetails.setFocusable(isEditable);
        textAgendaOfferDetails.setFocusableInTouchMode(isEditable);

        textAgendaActivityStartTime.setFocusable(isEditable);
        textAgendaActivityStartTime.setFocusableInTouchMode(isEditable);

        textAgendaNotice.setFocusable(isEditable);
        textAgendaNotice.setFocusableInTouchMode(isEditable);

        textAgendaCustomerDetails.setFocusable(isEditable);
        textAgendaCustomerDetails.setFocusableInTouchMode(isEditable);

        textAgendaProjectDetails.setFocusable(isEditable);
        textAgendaProjectDetails.setFocusableInTouchMode(isEditable);
    }

    private void clearFields()
    {
        textAgendaActivityEndTime.setText("");
        textAgendaActivityStartTime.setText("");
        textAgendaCustomerDetails.setText("");
        textAgendaNotice.setText("");
        textAgendaOfferDetails.setText("");
        textAgendaProjectDetails.setText("");
        listOfAgendaRelatedContactPerson.clear();
        listOfAgendaRelatedEmployee.clear();
//        listWeeklyDue.clear();
//        listWeeklyFuture.clear();
//        adapterAgendaWeeklyDue.notifyDataSetChanged();
//        adapterAgendaWeeklyFuture.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.buttonProjectDetails:
                getProjectDetails();
                break;
            case R.id.buttonCustomerDetails:
                getCustomerDetails();
                break;
            case R.id.imageSortName:
            case R.id.labelAgendaWeeklyDueName:
                if(imageSortName.getVisibility() != View.GONE && !isAscending)
                {
                    //((TextView)rootView.findViewById(R.id.labelAgendaWeeklyDueName)).setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.ic_sort_asc_24dp);
                    imageSortName.setImageResource(R.drawable.ic_sort_asc_24dp);
                    Collections.sort(listWeeklyDue, new FuturePastAgendaComparable(1, 0));
                    isAscending = true;
                }
                else
                {
                    imageSortName.setImageResource(R.drawable.ic_sort_dsc_24dp);
                    Collections.sort(listWeeklyDue, new FuturePastAgendaComparable(1, 1));
                    imageSortName.setVisibility(View.VISIBLE);
                    isAscending = false;
                }
                imageSortType.setVisibility(View.GONE);
                imageSortDate.setVisibility(View.GONE);
                if(listWeeklyDue.size() > 0)
                {
                    adapterAgendaWeeklyDue.setSelectedIndex(0);
                    showDetailAgenda(0, 0);
                }
                break;
            case R.id.imageSortDate:
            case R.id.labelAgendaWeeklyDueDate:
                if(imageSortDate.getVisibility() != View.GONE && !isAscending)
                {
                    imageSortDate.setImageResource(R.drawable.ic_sort_asc_24dp);
                    Collections.sort(listWeeklyDue, new FuturePastAgendaComparable(2, 0));
                    isAscending = true;
                }
                else
                {
                    imageSortDate.setImageResource(R.drawable.ic_sort_dsc_24dp);
                    Collections.sort(listWeeklyDue, new FuturePastAgendaComparable(2, 1));
                    imageSortDate.setVisibility(View.VISIBLE);
                    isAscending = false;
                }
                imageSortType.setVisibility(View.GONE);
                imageSortName.setVisibility(View.GONE);
                if(listWeeklyDue.size() > 0)
                {
                    adapterAgendaWeeklyDue.setSelectedIndex(0);
                    showDetailAgenda(0, 0);
                }
                break;
            case R.id.imageSortType:
            case R.id.labelAgendaWeeklyDueType:
                if(imageSortType.getVisibility() != View.GONE && !isAscending)
                {
                    imageSortType.setImageResource(R.drawable.ic_sort_asc_24dp);
                    Collections.sort(listWeeklyDue, new FuturePastAgendaComparable(3, 0));
                    isAscending = true;
                }
                else
                {
                    imageSortType.setImageResource(R.drawable.ic_sort_dsc_24dp);
                    Collections.sort(listWeeklyDue, new FuturePastAgendaComparable(3, 1));
                    imageSortType.setVisibility(View.VISIBLE);
                    isAscending = false;
                }
                imageSortDate.setVisibility(View.GONE);
                imageSortName.setVisibility(View.GONE);
                if(listWeeklyDue.size() > 0)
                {
                    adapterAgendaWeeklyDue.setSelectedIndex(0);
                    showDetailAgenda(0, 0);
                }
                break;
            case R.id.imageSortNameFuture:
            case R.id.labelAgendaWeeklyFutureName:
                if(imageSortNameFuture.getVisibility() != View.GONE && !isAscendingFuture)
                {
                    //((TextView)rootView.findViewById(R.id.labelAgendaWeeklyDueName)).setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.ic_sort_asc_24dp);
                    imageSortNameFuture.setImageResource(R.drawable.ic_sort_asc_24dp);
                    Collections.sort(listWeeklyFuture, new FuturePastAgendaComparable(1, 0));
                    isAscendingFuture = true;
                }
                else
                {
                    imageSortNameFuture.setImageResource(R.drawable.ic_sort_dsc_24dp);
                    Collections.sort(listWeeklyFuture, new FuturePastAgendaComparable(1, 1));
                    imageSortNameFuture.setVisibility(View.VISIBLE);
                    isAscendingFuture = false;
                }
                imageSortTypeFuture.setVisibility(View.GONE);
                imageSortDateFuture.setVisibility(View.GONE);
                if(listWeeklyFuture.size() > 0)
                {
                    adapterAgendaWeeklyFuture.setSelectedIndex(0);
                    showDetailAgenda(0, 1);
                }
                break;
            case R.id.imageSortDateFuture:
            case R.id.labelAgendaWeeklyFutureDate:
                if(imageSortDateFuture.getVisibility() != View.GONE && !isAscendingFuture)
                {
                    imageSortDateFuture.setImageResource(R.drawable.ic_sort_asc_24dp);
                    Collections.sort(listWeeklyFuture, new FuturePastAgendaComparable(2, 0));
                    isAscendingFuture = true;
                }
                else
                {
                    imageSortDateFuture.setImageResource(R.drawable.ic_sort_dsc_24dp);
                    Collections.sort(listWeeklyFuture, new FuturePastAgendaComparable(2, 1));
                    imageSortDateFuture.setVisibility(View.VISIBLE);
                    isAscendingFuture = false;
                }
                imageSortTypeFuture.setVisibility(View.GONE);
                imageSortNameFuture.setVisibility(View.GONE);
                if(listWeeklyFuture.size() > 0)
                {
                    adapterAgendaWeeklyFuture.setSelectedIndex(0);
                    showDetailAgenda(0, 1);
                }
                break;
            case R.id.imageSortTypeFuture:
            case R.id.labelAgendaWeeklyFutureDesignation:
                if(imageSortTypeFuture.getVisibility() != View.GONE && !isAscendingFuture)
                {
                    imageSortTypeFuture.setImageResource(R.drawable.ic_sort_asc_24dp);
                    Collections.sort(listWeeklyFuture, new FuturePastAgendaComparable(3, 0));
                    isAscendingFuture = true;
                }
                else
                {
                    imageSortTypeFuture.setImageResource(R.drawable.ic_sort_dsc_24dp);
                    Collections.sort(listWeeklyFuture, new FuturePastAgendaComparable(3, 1));
                    imageSortTypeFuture.setVisibility(View.VISIBLE);
                    isAscendingFuture = false;
                }
                imageSortDateFuture.setVisibility(View.GONE);
                imageSortNameFuture.setVisibility(View.GONE);
                if(listWeeklyFuture.size() > 0)
                {
                    adapterAgendaWeeklyFuture.setSelectedIndex(0);
                    showDetailAgenda(0, 1);
                }
                break;
        }
    }


   /* private void adapterLoad()
    {

        try
        {
            String date = labelAgendaDailyDateSelected.getText().toString();
            SimpleDateFormat readFormat = new SimpleDateFormat("dd.MM.yyyy, E");
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            String finaldate = format.format(readFormat.parse(date));

            String loginPersonId = matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber();
            if(selectedEmployee != null)
            {
                loginPersonId = selectedEmployee.getMitarbeiter();
            }
            listWeeklyDue.clear();
            listWeeklyFuture.clear();
            if(DataHelper.isNetworkAvailable(getActivity()))
            {
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
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
                            if(DataHelper.formatDisplayDate(new Date()).equals(l    abelAgendaDailyDateSelected.getText().toString()))
                                matecoPriceApplication.SaveOtherAgenda(DataHelper.StoreOtherAgenda, result);
                            setAgenda(result);

                        }

                    }
                };

                String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.GET_TODAY_AGENDA_OTHER
                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "&mitarbeiter=" + loginPersonId
                        //+ "&datum=" + "13-11-2003";
                        + "&datum=" + finaldate;
                Log.e("url", url);
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                asyncTask.execute();
            }
            else
            {
                showShortToast(language.getMessageNetworkNotAvailable());
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }*/

    public void setAgenda(String result)
    {
        try
        {
            listWeeklyDue.clear();
            listWeeklyFuture.clear();
            if(!result.equals(""))
            {
                JSONObject jsonObject = new JSONObject(result);
                if(jsonObject.has("Past"))
                    DailyAgendaModel.extractFromJson(jsonObject.getJSONArray("Past").toString(), listWeeklyDue);
                if(jsonObject.has("Future"))
                    DailyAgendaModel.extractFromJson(jsonObject.getJSONArray("Future").toString(), listWeeklyFuture);
            }

            /*Collections.sort(listWeeklyDue, new Comparator<DailyAgendaModel>() {
                @Override
                public int compare(DailyAgendaModel lhs, DailyAgendaModel rhs) {
                    Date firstDate = new Date();
                    try {

                        firstDate = formatter.parse(lhs.getStartzeit());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date secondDate = new Date();
                    try {
                        secondDate = formatter.parse(rhs.getStartzeit());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return firstDate.compareTo(secondDate);
                }
            });*/

            /*Collections.sort(listWeeklyFuture, new Comparator<DailyAgendaModel>() {
                @Override
                public int compare(DailyAgendaModel lhs, DailyAgendaModel rhs) {
                    Date firstDate = new Date();
                    try {
                        firstDate = formatter.parse(lhs.getStartzeit());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date secondDate = new Date();
                    try {
                        secondDate = formatter.parse(rhs.getStartzeit());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return firstDate.compareTo(secondDate);
                }
            });*/
            adapterAgendaWeeklyDue.notifyDataSetChanged();
            adapterAgendaWeeklyFuture.notifyDataSetChanged();
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }
    }

    private void getCustomerDetails()
    {
        if(DataHelper.isNetworkAvailable(getActivity()))
        {
            if(model != null && model.getKontakt() != null && !model.getKontakt().equals(""))
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
                                    dialog.dismiss();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("VisitPlanDailyAgenda",activityId);
                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    //Fragment fragment = new CustomerDataFragment();
                                    Fragment fragment = new CustomerActivityFragment();
                                    fragment.setArguments(bundle);
                                    transaction.replace(R.id.content_frame, fragment);
                                    transaction.addToBackStack("Daily Agenda");
                                    transaction.commit();
                                }
                                catch (Exception ex)
                                {
                                    ex.printStackTrace();
                                    dialog.dismiss();
                                    showShortToast(language.getMessageErrorAtParsing());
                                }
                            }
                        }
                    };
                    String url = DataHelper.URL_AGENDA_HELPER + "agendacustomershow" //+ DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.AGENDA_CUSTOMER_SHOW
                            + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                            + "/fieldname=" + "Kontakt"
                            + "/value=" + model.getKontakt();
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
                showShortToast(language.getMessageMatchCodeNotFound());
            }
        }
        else
        {
            showShortToast(language.getMessageNetworkNotAvailable());
        }
    }

    private void getProjectDetails()
    {
        if(DataHelper.isNetworkAvailable(getActivity()))
        {
            if(model != null && model.getProjekt() != null && !model.getProjekt().equals(""))
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
                                    transaction1.addToBackStack("Project detail");
                                    transaction1.commit();
                                }
                                catch (JSONException ex)
                                {
                                    ex.printStackTrace();
                                    dialog.dismiss();
                                }
                            }
                        }
                    };

                    String url = DataHelper.URL_AGENDA_HELPER + "agendaprojectlistshow" //+ DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.AGENDA_PROJECT_LIST_SHOW
                            + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                            + "/fieldname=" + "Projekt"
                            + "/value=" + model.getProjekt();
                    //Projekt
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
                showShortToast(language.getMessageMatchCodeNotFound());
            }
        }
        else
        {
            showShortToast(language.getMessageNetworkNotAvailable());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);
        menu.findItem(R.id.actionSearch).setVisible(false);
        menu.findItem(R.id.actionBack).setVisible(false);
        menu.findItem(R.id.actionWrong).setVisible(false);
        menu.findItem(R.id.actionRight).setVisible(false);
        menu.findItem(R.id.actionAdd).setVisible(false);
        menu.findItem(R.id.actionEdit).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);
        menu.findItem(R.id.actionRefresh).setVisible(true);
        //return true;
        //super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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
                if (getFragmentManager().getBackStackEntryCount() == 0)
                {
                    getActivity().finish();
                }
                else
                {
                    getFragmentManager().popBackStack();
                }
                return true;
            case R.id.actionRefresh:
                SimpleDateFormat readFormat = new SimpleDateFormat("dd.MM.yyyy, E");
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    dateString = format.format(readFormat.parse(DataHelper.formatDisplayDate(new Date())));
                    adapterLoad();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    private void adapterLoad()
    {
        try
        {
            matecoPriceApplication.saveData(DataHelper.AgendaDate, dateString);
            loginPersonId = matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber();

            if(DataHelper.isNetworkAvailable(getActivity()))
            {
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        Log.e("todayresult", result);
                        if(result.equals("error"))
                        {
                            showShortToast(language.getMessageError());
                        }
                        else {
                            matecoPriceApplication.saveData(DataHelper.StoreAgenda, result);
                            BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult1 = new BasicAsyncTaskGetRequest.OnAsyncResult()
                            {
                                @Override
                                public void OnAsynResult(String result)
                                {
                                    Log.e("otherresult", result);
                                    if(result.equals("error"))
                                    {
                                        showShortToast(language.getMessageError());
                                    }
                                    else
                                    {
                                        matecoPriceApplication.saveData(DataHelper.StoreOtherAgenda, result);
                                        setAgenda(matecoPriceApplication.getData(DataHelper.StoreOtherAgenda, ""));
                                    }

                                }
                            };

                            String url = null;
                            try {
                                url = DataHelper.URL_AGENDA_HELPER + "agendadates"//DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.GET_TODAY_AGENDA_OTHER
                                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                                        + "/mitarbeiter=" + loginPersonId
                                        + "/datum=" + dateString;
                                Log.e("otherurl",url);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            Log.e("otherurl", url);
                            BasicAsyncTaskGetRequest asyncTask1 = new BasicAsyncTaskGetRequest(url, onAsyncResult1, getActivity(), true);
                            asyncTask1.execute();
                        }
                    }
                };

                String url = DataHelper.URL_AGENDA_HELPER + "agendatoday"//DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.GET_TODAY_AGENDA
                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/mitarbeiter=" + loginPersonId
                        + "/datum=" + dateString;
                Log.e("todayurl", url);
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                asyncTask.execute();
            }
            else
            {
                showShortToast(language.getMessageNetworkNotAvailable());
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
