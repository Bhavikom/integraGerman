package de.mateco.integrAMobile.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.LogApp;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.DailyAgendaTelephoneAdapter;
import de.mateco.integrAMobile.adapter.DailyAgendaTermineAdapter;
import de.mateco.integrAMobile.adapter.SimpleStringAdapter;
import de.mateco.integrAMobile.adapter.VisitPlanDailyAdapter;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.CustomerFullDetailModel;
import de.mateco.integrAMobile.model.DailyAgendaModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LoginPersonModel;
import de.mateco.integrAMobile.model_logonsquare.CustomerActivityEmployeeListItem;
import de.mateco.integrAMobile.model_logonsquare.ResponseMainAgenda;
import de.mateco.integrAMobile.model_logonsquare.TelefonateItem;
import de.mateco.integrAMobile.model_logonsquare.TermineItem;

public class VisitPlanDailyAgendaTodayFragment extends BaseFragment implements View.OnClickListener
{
    private ProgressDialog progressDialog;
    private int typeModel;
    private View rootView;
    private MatecoPriceApplication matecoPriceApplication;
    private Language language;
    private DataBaseHandler db;
    private ListView listViewTermine, listViewTelephone;
    private EditText textAgendaCustomerDetails, textAgendaProjectDetails, textAgendaOfferDetails,
                textAgendaNotice, textAgendaActivityStartTime, textAgendaActivityEndTime;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    private List<TermineItem> arrayListAgendaTermine;
    private List<TelefonateItem> arrayListAgendaTelephone;
    private DailyAgendaTermineAdapter adapterAgendaTermine;
    private DailyAgendaTelephoneAdapter adapterAgendaTelephone;
    private Button buttonCustomerDetails, buttonProjectDetails;
    private ArrayList<CustomerActivityEmployeeListItem> listOfEmployee;
    private CustomerActivityEmployeeListItem selectedEmployee;
    private ListView listViewAgendaEmployee, listViewAgendaContacts;
    private ArrayList<String> listOfAgendaRelatedContactPerson, listOfAgendaRelatedEmployee;
    private SimpleStringAdapter adapterAgendaRelatedContactPerson, adapterAgendaRelatedEmployee;
    //private DailyAgendaModel model = null;
    private TermineItem termineItem;
    private TelefonateItem telefonateItem;
    private boolean isFirstTimeSelectedSpinnerEmployee = false;
    private String dateString = "",loginPersonId = "";
    private BasicAsyncTaskGetRequest asyncTask, asyncTask1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_agenda_daily, container, false);
        //rootView = inflater.inflate(R.layout.temp_under_construction, container, false);
        super.initializeFragment(rootView);
        return rootView;
    }

    @Override
    public void initializeComponents(View rootView)
    {
        super.initializeComponents(rootView);

        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        db = new DataBaseHandler(getActivity());

        listViewTermine = (ListView)rootView.findViewById(R.id.listViewMeeting);
        listViewTelephone = (ListView)rootView.findViewById(R.id.listViewPhoneCalls);

        textAgendaCustomerDetails = (EditText)rootView.findViewById(R.id.textAgendaCustomerDetails);
        textAgendaProjectDetails = (EditText)rootView.findViewById(R.id.textAgendaProjectDetails);
        textAgendaOfferDetails = (EditText)rootView.findViewById(R.id.textAgendaOfferDetails);
        textAgendaNotice = (EditText)rootView.findViewById(R.id.textAgendaNotice);
        textAgendaActivityStartTime = (EditText)rootView.findViewById(R.id.textAgendaActivityStartTime);
        textAgendaActivityEndTime = (EditText)rootView.findViewById(R.id.textAgendaActivityEndTime);

        arrayListAgendaTermine = new ArrayList<>();
        arrayListAgendaTelephone = new ArrayList<>();

        //adapterAgendaTermine = new DailyAgendaTermineAdapter(getActivity(), arrayListAgendaTermine, R.layout.list_item_daily_today_agenda);
        //adapterAgendaTelephone = new DailyAgendaTermineAdapter(getActivity(), arrayListAgendaTelephone, R.layout.list_item_daily_today_agenda);

        //listViewTermine.setAdapter(adapterAgendaTermine);
        //listViewTelephone.setAdapter(adapterAgendaTelephone);

        buttonProjectDetails = (Button)rootView.findViewById(R.id.buttonProjectDetails);
        buttonCustomerDetails = (Button)rootView.findViewById(R.id.buttonCustomerDetails);

        buttonCustomerDetails.setOnClickListener(this);
        buttonProjectDetails.setOnClickListener(this);


        setAgenda(matecoPriceApplication.getData(DataHelper.StoreAgenda, ""));

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
        listViewTermine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterAgendaTermine.setSelectedIndex(position);
                showDetailAgenda(position, 0);
            }
        });
        listViewTelephone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterAgendaTelephone.setSelectedIndex(position);
                showDetailAgenda(position, 1);
            }
        });

        textAgendaActivityStartTime.setOnClickListener(this);
        textAgendaActivityEndTime.setOnClickListener(this);
    }

    private void showDetailAgenda(int position, int type)
    {
        clearFields();
        if(type == 0)
        {
            typeModel = 0;
            termineItem = arrayListAgendaTermine.get(position);
            adapterAgendaTelephone.setSelectedIndex(-1);

            String customerDetail = termineItem.getMatchcode() + "\n" + termineItem.getName1() + "\n" + termineItem.getStrasse() + "\n" + termineItem.getPLZ() + " " + termineItem.getOrt();
            textAgendaCustomerDetails.setText(customerDetail);
            String projectDetail = termineItem.getMatchcodeProjekt() + "\n" + termineItem.getBeschreibung() + "\n" + termineItem.getAdresse() + "\n" + termineItem.getPLZProjekt() + " " + termineItem.getPLZOrt();
            textAgendaProjectDetails.setText(projectDetail);
            //textAgendaActivityDetails.setText(model.getAktivitaet());
            textAgendaNotice.setText(termineItem.getNotiz());
            textAgendaOfferDetails.setText(termineItem.getAngebot());

            if(termineItem.getStartzeit() != null) {
                if (termineItem.getStartzeit().equals("") || termineItem.getStartzeit().equals("00:00") || termineItem.getStartzeit().equals("00:00:00")) {
                    textAgendaActivityStartTime.setText("");
                } else {
                    textAgendaActivityStartTime.setText(DataHelper.formatTime(termineItem.getStartzeit()));
                }
            }

            if(!TextUtils.isEmpty(termineItem.getEndzeit())) {
                if (termineItem.getEndzeit().equals("") || termineItem.getEndzeit().equals("00:00") || termineItem.getStartzeit().equals("00:00:00")) {
                    textAgendaActivityEndTime.setText("");
                } else {
                    textAgendaActivityEndTime.setText(DataHelper.formatTime(termineItem.getEndzeit()));
                }
            }
            textAgendaOfferDetails.setText(termineItem.getAngebot());

            listOfAgendaRelatedContactPerson.clear();
            listOfAgendaRelatedEmployee.clear();

            if(termineItem.getAnsprechpartner() != null) {
                listOfAgendaRelatedContactPerson.addAll(termineItem.getAnsprechpartner());
            }
            if(termineItem.getMitarbeiterName() != null) {
                listOfAgendaRelatedEmployee.addAll(termineItem.getMitarbeiterName());
            }
            adapterAgendaRelatedEmployee.notifyDataSetChanged();
            adapterAgendaRelatedContactPerson.notifyDataSetChanged();

        }
        else
        {
            typeModel = 1;
            adapterAgendaTermine.setSelectedIndex(-1);
            telefonateItem = arrayListAgendaTelephone.get(position);

            String customerDetail = telefonateItem.getMatchcode() + "\n" + telefonateItem.getName1() + "\n" + telefonateItem.getStrasse() + "\n" + telefonateItem.getPLZ() + " " + telefonateItem.getOrt();
            textAgendaCustomerDetails.setText(customerDetail);
            String projectDetail = telefonateItem.getMatchcodeProjekt() + "\n" + telefonateItem.getBeschreibung() + "\n" + telefonateItem.getAdresse() + "\n" + telefonateItem.getPLZProjekt() + " " + telefonateItem.getPLZOrt();
            textAgendaProjectDetails.setText(projectDetail);
            //textAgendaActivityDetails.setText(model.getAktivitaet());
            textAgendaNotice.setText(telefonateItem.getNotiz());
            textAgendaOfferDetails.setText(telefonateItem.getAngebot());

            if(telefonateItem.getStartzeit() != null) {
                if (telefonateItem.getStartzeit().equals("") || telefonateItem.getStartzeit().equals("00:00") || telefonateItem.getStartzeit().equals("00:00:00")) {
                    textAgendaActivityStartTime.setText("");
                } else {
                    textAgendaActivityStartTime.setText(DataHelper.formatTime(telefonateItem.getStartzeit()));
                }
            }

            if(!TextUtils.isEmpty(telefonateItem.getEndzeit())) {
                if (telefonateItem.getEndzeit().equals("") || telefonateItem.getEndzeit().equals("00:00") || telefonateItem.getStartzeit().equals("00:00:00")) {
                    textAgendaActivityEndTime.setText("");
                } else {
                    textAgendaActivityEndTime.setText(DataHelper.formatTime(telefonateItem.getEndzeit()));
                }
            }
            textAgendaOfferDetails.setText(telefonateItem.getAngebot());

            listOfAgendaRelatedContactPerson.clear();
            listOfAgendaRelatedEmployee.clear();

            if(telefonateItem.getAnsprechpartner() != null) {
                listOfAgendaRelatedContactPerson.addAll(telefonateItem.getAnsprechpartner());
            }
            if(telefonateItem.getMitarbeiterName() != null) {
                listOfAgendaRelatedEmployee.addAll(telefonateItem.getMitarbeiterName());
            }
            adapterAgendaRelatedEmployee.notifyDataSetChanged();
            adapterAgendaRelatedContactPerson.notifyDataSetChanged();
        }

    }

    private void setupLanguage()
    {
        TextView labelFragmentAgendaDailyPhoneCalls, labelFragmentAgendaDailyMeetings, labelAgendaCustomerDetails, labelAgendaProjectDetails, labelAgendaOfferDetails,
                labelAgendaNotice, labelAgendaActivityStartTime, labelAgendaActivityEndTime, labelAgendaDailyPhoneCallDesignation, labelAgendaDailyPhoneCallRealized,
                labelAgendaDailyPhoneCallDate, labelAgendaDailyMeetingDesignation, labelAgendaDailyMeetingRealized,
                labelAgendaDailyMeetingDate, labelAgendaTodayEmployee, labelAgendaTodayContacts;

        labelFragmentAgendaDailyMeetings = (TextView)rootView.findViewById(R.id.labelFragmentAgendaDailyMeetings);
        labelFragmentAgendaDailyPhoneCalls = (TextView)rootView.findViewById(R.id.labelFragmentAgendaDailyPhoneCalls);
        labelAgendaCustomerDetails = (TextView)rootView.findViewById(R.id.labelAgendaCustomerDetails);
        labelAgendaProjectDetails = (TextView)rootView.findViewById(R.id.labelAgendaProjectDetails);
        labelAgendaNotice = (TextView)rootView.findViewById(R.id.labelAgendaNotice);
        labelAgendaOfferDetails = (TextView)rootView.findViewById(R.id.labelAgendaOfferDetails);
        //labelAgendaActivityDetails = (TextView)rootView.findViewById(R.id.labelAgendaActivityDetails);
        labelAgendaActivityStartTime = (TextView)rootView.findViewById(R.id.labelAgendaActivityStartTime);
        labelAgendaActivityEndTime = (TextView)rootView.findViewById(R.id.labelAgendaActivityEndTime);
        labelAgendaDailyPhoneCallDesignation = (TextView)rootView.findViewById(R.id.labelAgendaDailyPhoneCallDesignation);
        labelAgendaDailyPhoneCallRealized = (TextView)rootView.findViewById(R.id.labelAgendaDailyPhoneCallRealized);
        labelAgendaDailyPhoneCallDate = (TextView)rootView.findViewById(R.id.labelAgendaDailyPhoneCallDate);
        labelAgendaDailyMeetingDesignation = (TextView)rootView.findViewById(R.id.labelAgendaDailyMeetingDesignation);
        labelAgendaDailyMeetingRealized = (TextView)rootView.findViewById(R.id.labelAgendaDailyMeetingRealized);
        labelAgendaDailyMeetingDate = (TextView)rootView.findViewById(R.id.labelAgendaDailyMeetingDate);
        labelAgendaTodayEmployee = (TextView)rootView.findViewById(R.id.labelAgendaTodayEmployee);
        labelAgendaTodayContacts = (TextView)rootView.findViewById(R.id.labelAgendaTodayContacts);

        labelFragmentAgendaDailyMeetings.setText(language.getLabelMessage());
        labelFragmentAgendaDailyPhoneCalls.setText(language.getLabelPhoneCalls());
        labelAgendaCustomerDetails.setText(language.getLabelCustomerDetailsSpace());
        labelAgendaProjectDetails.setText(language.getLabelProjectDetailsSpace());
        labelAgendaNotice.setText(language.getLabelNotice());
        labelAgendaOfferDetails.setText(language.getLabelOfferDetailsSpace());
        //labelAgendaActivityDetails.setText(language.getLabelActivityDetails());
        labelAgendaActivityStartTime.setText(language.getLabelStartTime());
        labelAgendaActivityEndTime.setText(language.getLabelEndTime());
        labelAgendaDailyPhoneCallDesignation.setText(language.getLabelActivity());
        labelAgendaDailyPhoneCallRealized.setText(language.getLabelRealized());
        labelAgendaDailyPhoneCallDate.setText(language.getLabelCustomer());
        labelAgendaDailyMeetingDesignation.setText(language.getLabelActivity());
        labelAgendaDailyMeetingRealized.setText(language.getLabelRealized());
        labelAgendaDailyMeetingDate.setText(language.getLabelCustomer());
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
        //super.clearAll();
        textAgendaActivityEndTime.setText("");
        textAgendaActivityStartTime.setText("");
        textAgendaCustomerDetails.setText("");
        textAgendaNotice.setText("");
        textAgendaOfferDetails.setText("");
        textAgendaProjectDetails.setText("");
        listOfAgendaRelatedContactPerson.clear();
        listOfAgendaRelatedEmployee.clear();
//        arrayListAgendaTermine.clear();
//        arrayListAgendaTelephone.clear();
//        adapterAgendaTermine.notifyDataSetChanged();
//        adapterAgendaTelephone.notifyDataSetChanged();
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
        switch (item.getItemId())
        {
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
        if(DataHelper.isNetworkAvailable(getActivity()))
        {
            try {
                showProgressDialog();
                final long starTime = System.currentTimeMillis();
                loginPersonId = matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber();
                matecoPriceApplication.saveData(DataHelper.AgendaDate, dateString);

                String urlAgendadate = DataHelper.URL_AGENDA_HELPER + "combinedagendalist"
                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/mitarbeiter=" + loginPersonId
                        + "/datum=" + dateString;
                JsonObjectRequest object = new JsonObjectRequest(Request.Method.GET, urlAgendadate, "", new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        long endTime = System.currentTimeMillis() - starTime;
                        LogApp.showLog("success"," response from volley :  time taken : " + endTime);

                        matecoPriceApplication.saveData(DataHelper.StoreAgenda, response.toString());
                        setAgenda(matecoPriceApplication.getData(DataHelper.StoreAgenda, ""));
                        hideProgressDialog();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgressDialog();
                        LogApp.showLog(" error "," response from volley : "+error.toString());
                        showShortToast(language.getMessageError());
                    }
                });
                MatecoPriceApplication.getInstance().addToRequestQueue(object);
            }catch (Exception e){
                hideProgressDialog();
                LogApp.showLog(" exception "," exception while calling agenda date");
            }

        }else {
            showShortToast(language.getMessageNetworkNotAvailable());
        }

        /*try
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
                        if(result.equals("error"))
                        {
                            showShortToast(language.getMessageError());
                        }
                        else if(result.equals(DataHelper.NetworkError)){
                            showShortToast(language.getMessageNetworkNotAvailable());
                        }
                        else {
                            matecoPriceApplication.saveData(DataHelper.StoreAgenda, result);

                            BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult1 = new BasicAsyncTaskGetRequest.OnAsyncResult()
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
                                        matecoPriceApplication.saveData(DataHelper.StoreOtherAgenda, result);
                                        setAgenda(matecoPriceApplication.getData(DataHelper.StoreAgenda, ""));
                                    }

                                }
                            };

                            String url = null;
                            try {
                                *//*url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.GET_TODAY_AGENDA_OTHER
                                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                                        + "&mitarbeiter=" + loginPersonId
                                        + "&datum=" + dateString;*//*
                                url = DataHelper.URL_AGENDA_HELPER + "agendadates"//DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.GET_TODAY_AGENDA_OTHER
                                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                                        + "/mitarbeiter=" + loginPersonId
                                        + "/datum=" + dateString;
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            asyncTask1 = new BasicAsyncTaskGetRequest(url, onAsyncResult1, getActivity(), true);
                            asyncTask1.execute();
                        }
                    }
                };

                *//*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.GET_TODAY_AGENDA
                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "&mitarbeiter=" + loginPersonId
                        + "&datum=" + dateString;*//*
                String url = DataHelper.URL_AGENDA_HELPER + "agendatoday"//DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.GET_TODAY_AGENDA
                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/mitarbeiter=" + loginPersonId
                        + "/datum=" + dateString;
                asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), false);
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
        }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(asyncTask != null && asyncTask.getStatus() == AsyncTask.Status.RUNNING)
            asyncTask.cancel(true);
        if(asyncTask1 != null && asyncTask1.getStatus() == AsyncTask.Status.RUNNING)
            asyncTask1.cancel(true);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonProjectDetails:
                getProjectDetails();
                break;
            case R.id.buttonCustomerDetails:
                getCustomerDetails();
                break;
            case R.id.textAgendaActivityEndTime:

                break;
            case R.id.textAgendaActivityStartTime:

                break;
        }
    }

    public void setAgenda(String result)
    {
            arrayListAgendaTermine = new ArrayList<>();
            arrayListAgendaTelephone = new ArrayList<>();
            if(!result.equals(""))
            {
                ResponseMainAgenda responseMainAgenda = new ResponseMainAgenda();
                try {
                    long startTime = System.currentTimeMillis();
                    responseMainAgenda = LoganSquare.parse(result.toString(), ResponseMainAgenda.class);
                    arrayListAgendaTermine = responseMainAgenda.getTermine();
                    arrayListAgendaTelephone = responseMainAgenda.getTelefonate();
                    long endTime = System.currentTimeMillis()-startTime;
                    LogApp.showLog(""," time taken by parsing of agenda : "+endTime);
                }catch (Exception e){

                }
                /*JSONObject jsonObject = new JSONObject(result);
                if(jsonObject.has("Termine"))
                    DailyAgendaModel.extractFromJson(jsonObject.getJSONArray("Termine").toString(), arrayListAgendaTermine);
                if(jsonObject.has("Telefonate"))
                    DailyAgendaModel.extractFromJson(jsonObject.getJSONArray("Telefonate").toString(), arrayListAgendaTelephone);*/
            }
            adapterAgendaTermine = new DailyAgendaTermineAdapter(getActivity(), arrayListAgendaTermine, R.layout.list_item_daily_today_agenda);
            adapterAgendaTelephone = new DailyAgendaTelephoneAdapter(getActivity(), arrayListAgendaTelephone, R.layout.list_item_daily_today_agenda);

            listViewTermine.setAdapter(adapterAgendaTermine);
            listViewTelephone.setAdapter(adapterAgendaTelephone);

            //adapterAgendaTermine.notifyDataSetChanged();
            //adapterAgendaTelephone.notifyDataSetChanged();
    }


    private void getCustomerDetails()
    {
        if(DataHelper.isNetworkAvailable(getActivity()))
        {
            String strKontakt="";
            //if(model != null && !model.getMatchcode_Projekt().equals(""))
            if(typeModel == 0){
                // termine
                if(termineItem != null) {
                    strKontakt = termineItem.getKontakt();
                }
            }else {
                // telephone
                if(telefonateItem != null) {
                    strKontakt = telefonateItem.getKontakt();
                }
            }

            if(!TextUtils.isEmpty(strKontakt))
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
                            else if(result.equalsIgnoreCase("null") || TextUtils.isEmpty(result)){
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
                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    //Fragment fragment = new CustomerDataFragment();
                                    Fragment fragment = new CustomerActivityFragment();
                                    transaction.replace(R.id.content_frame, fragment);
                                    transaction.addToBackStack("Daily Agenda");
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
                    //String kontakt="0";
                    String url = DataHelper.URL_AGENDA_HELPER + "agendacustomershow" //+ DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.AGENDA_CUSTOMER_SHOW
                            + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                            + "/fieldname=" + "Kontakt"
                            + "/value=" + strKontakt;
//                            + "&fieldname=" + "MatchCode"
//                            + "&value=" + model.getMatchcode();
                    asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), false);
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
            String strProject="";
            //if(model != null && !model.getMatchcode_Projekt().equals(""))
            if(typeModel == 0){
                // termine
                if(termineItem != null) {
                    strProject = termineItem.getProjekt();
                }
            }else {
                // telephone
                if(telefonateItem != null) {
                    strProject = telefonateItem.getProjekt();
                }
            }
            if(!TextUtils.isEmpty(strProject))
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
                            + "/value=" + strProject;
//                            + "&fieldname=" + "MatchCode"
//                            + "&value=" + model.getMatchcode_Projekt();
                    //Projekt
                    //Projekt
                    asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), false);
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
