package de.mateco.integrAMobile.fragment;

import android.app.DatePickerDialog;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.DatePickerDialogFragment;
import de.mateco.integrAMobile.Helper.LogApp;
import de.mateco.integrAMobile.Helper.NonSwipeableViewPager;
import de.mateco.integrAMobile.Helper.SamplePagerItem;
import de.mateco.integrAMobile.Helper.SlidingTabLayout;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.EmployeeAdapter;
import de.mateco.integrAMobile.adapter.VisitPlanDailyAdapter;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.DailyAgendaModel;
import de.mateco.integrAMobile.model.EmployeeModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LoginPersonModel;

public class VisitPlanDailyAgendaFragment extends BaseFragment implements ViewPager.OnPageChangeListener
{
   /* private LinearLayout compoundButtonAgendaDailyPrevious, compoundButtonAgendaDailyDatePicker, compoundButtonAgendaDailyNext;
    private TextView labelAgendaDailyDateSelected;*/
    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    private DataBaseHandler db;
    private View rootView;
    private MatecoPriceApplication matecoPriceApplication;
    private Language language;
    private NonSwipeableViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    private List<SamplePagerItem> mTabs = new ArrayList<SamplePagerItem>();
    private VisitPlanDailyAdapter adapter;
    private String loginPersonId;
    private String dateString="";
    private BasicAsyncTaskGetRequest asyncTask, asyncTask1;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        mTabs.add(new SamplePagerItem(
                language.getLabelToday(), // Title
                getResources().getColor(R.color.dark_grey), // Indicator color
                Color.BLACK // Divider color
        ));

        mTabs.add(new SamplePagerItem(
                language.getLabelOther(), // Title
                getResources().getColor(R.color.dark_grey), // Indicator color
                Color.BLACK // Divider color
        ));

        mTabs.add(new SamplePagerItem(
                language.getLabelWeeklyAgenda(), // Title
                getResources().getColor(R.color.dark_grey), // Indicator color
                Color.BLACK // Divider color
        ));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (NonSwipeableViewPager) rootView.findViewById(R.id.viewpager);
        adapter = new VisitPlanDailyAdapter(getChildFragmentManager(), mTabs);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(1);
        // END_INCLUDE (setup_viewpager)

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setOnPageChangeListener(this);
        if(getArguments()!=null)
            mViewPager.setCurrentItem(2);
        if(mViewPager.getCurrentItem()==0 || mViewPager.getCurrentItem() == 1)
            ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelDailyAgenda());
        else
            ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelWeeklyAgenda());
        // BEGIN_INCLUDE (tab_colorizer)
        // Set a TabColorizer to customize the indicator and divider colors. Here we just retrieve
        // the tab at the position, and return it's set color
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return mTabs.get(position).getIndicatorColor();
            }

            @Override
            public int getDividerColor(int position) {
                return mTabs.get(position).getDividerColor();
            }
        });
        if(getArguments()!=null)
            mViewPager.setCurrentItem(2);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_daily_agenda, container, false);
        //rootView = inflater.inflate(R.layout.temp_under_construction, container, false);
        super.initializeFragment(rootView);

        return rootView;
    }

//    @Override
//    public void onResume() {
//        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
//        language = matecoPriceApplication.getLanguage();
//        mSlidingTabLayout.setText(language.getLabelToday(), 0);
//        mSlidingTabLayout.setText(language.getLabelOther(), 1);
//        mSlidingTabLayout.setText(language.getLabelWeeklyAgenda(), 2);
//
//        super.onResume();
//    }

    @Override
    public void initializeComponents(View rootView) {
        super.initializeComponents(rootView);

        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);


        db = new DataBaseHandler(getActivity());

      /*  compoundButtonAgendaDailyPrevious = (LinearLayout)rootView.findViewById(R.id.compoundButtonAgendaDailyPrevious);
        compoundButtonAgendaDailyDatePicker = (LinearLayout)rootView.findViewById(R.id.compoundButtonAgendaDailyDatePicker);
        compoundButtonAgendaDailyNext = (LinearLayout)rootView.findViewById(R.id.compoundButtonAgendaDailyNext);
        labelAgendaDailyDateSelected = (TextView)rootView.findViewById(R.id.labelAgendaDailyDateSelected);*/

        SimpleDateFormat readFormat = new SimpleDateFormat("dd.MM.yyyy, E");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            dateString = format.format(readFormat.parse(DataHelper.formatDisplayDate(new Date())));
            LogApp.showLog("date1",dateString + " date2 " + matecoPriceApplication.getAgendaSelectedDate(DataHelper.AgendaDate, ""));
            //if(!matecoPriceApplication.getAgendaSelectedDate(DataHelper.AgendaDate, "").equals(dateString))
                adapterLoad();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /*if(!dateString.equals("")) {

            setDate(dateString);
        }
        else {
            Log.e("enter","else");
            labelAgendaDailyDateSelected.setText(DataHelper.formatDisplayDate(new Date()));
        }*/
        /*compoundButtonAgendaDailyPrevious.setOnClickListener(this);
        compoundButtonAgendaDailyDatePicker.setOnClickListener(this);
        compoundButtonAgendaDailyNext.setOnClickListener(this);*/
    }

    private void setDate(String dateString)
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter.parse(dateString);
           // labelAgendaDailyDateSelected.setText(DataHelper.formatDisplayDate(date));
            //adapterLoad();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
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
                                        adapter = new VisitPlanDailyAdapter(getChildFragmentManager(), mTabs);
                                        mViewPager.setAdapter(adapter);
                                        if(getArguments()!=null)
                                            mViewPager.setCurrentItem(2);
                                    }

                                }
                            };

                            String url = null;
                            try {
                                url = DataHelper.URL_AGENDA_HELPER + "agendadates"//DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.GET_TODAY_AGENDA_OTHER
                                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                                        + "/mitarbeiter=" + loginPersonId
                                        + "/datum=" + dateString;
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            asyncTask1 = new BasicAsyncTaskGetRequest(url, onAsyncResult1, getActivity(), false);
                            asyncTask1.execute();
                        }
                    }
                };

                /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.GET_TODAY_AGENDA
                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "&mitarbeiter=" + loginPersonId
                        + "&datum=" + dateString;*/
                String url = DataHelper.URL_AGENDA_HELPER + "agendatoday"//DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.GET_TODAY_AGENDA
                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/mitarbeiter=" + loginPersonId
                        + "/datum=" + dateString;
                asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), false);
                asyncTask.execute(); // load adapter
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


    @Override
    public void bindEvents(View rootView) {
        super.bindEvents(rootView);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_menu, menu);
        menu.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionSettings:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new SettingFragment());
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //mViewPager.setCurrentItem(position);
    }

    @Override
    public void onPageSelected(int position) {
//        if(position==0 || position==1)
//            ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelDailyAgenda());
//        else
//            ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelWeeklyAgenda());
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



    /*@Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.compoundButtonAgendaDailyNext:
               // addDate(1);
                break;
            case R.id.compoundButtonAgendaDailyDatePicker:
              //  openDatePicker();
                break;
            case R.id.compoundButtonAgendaDailyPrevious:
               // addDate(-1);
                break;
        }
    }
*/
    /*private void addDate(int numbersToAdd) {
        Calendar cal = Calendar.getInstance();
        try
        {
            cal.setTime(formatter.parse(labelAgendaDailyDateSelected.getText().toString()));
            cal.add(Calendar.DAY_OF_MONTH, numbersToAdd);
            labelAgendaDailyDateSelected.setText(DataHelper.formatDisplayDate(cal.getTime()));
            adapterLoad();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }*/

    /*private void openDatePicker()
    {
        try
        {
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
            *//**
             * Set Call back to capture selected date
             *//*
            newFragment.setCallBack(onFromDate);
            Log.e("tag", "startDate");
            newFragment.setMinDate(today);
            newFragment.show(getActivity().getSupportFragmentManager(), "dateSelected");
        }
        catch (ParseException e)
        {
            e.printStackTrace();
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
                String date = dayOfMonth + "." + monthOfYear + "." + year;
                String finaldate = DataHelper.formatDisplayDate(formatter.parse(date));
                Log.e("startDate", finaldate);
                labelAgendaDailyDateSelected.setText(finaldate);
                adapterLoad();
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
    };*/
}
