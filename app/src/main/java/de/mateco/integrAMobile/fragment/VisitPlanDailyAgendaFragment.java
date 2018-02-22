package de.mateco.integrAMobile.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.LogApp;
import de.mateco.integrAMobile.Helper.NonSwipeableViewPager;
import de.mateco.integrAMobile.Helper.SamplePagerItem;
import de.mateco.integrAMobile.Helper.SlidingTabLayout;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.VisitPlanDailyAdapter;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LoginPersonModel;

public class VisitPlanDailyAgendaFragment extends BaseFragment implements ViewPager.OnPageChangeListener
{

    private String strDateToSendWeekly ="";
    SimpleDateFormat readFormat = new SimpleDateFormat("dd.MM.yyyy, E");
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
   /* private LinearLayout compoundButtonAgendaDailyPrevious, compoundButtonAgendaDailyDatePicker, compoundButtonAgendaDailyNext;
    private TextView labelAgendaDailyDateSelected;*/
   private ProgressDialog progressDialog;
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
    private String strDateToSendAgenda ="";
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
        /*mViewPager = (NonSwipeableViewPager) rootView.findViewById(R.id.viewpager);
        adapter = new VisitPlanDailyAdapter(getChildFragmentManager(), mTabs);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(2);
        mSlidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setOnPageChangeListener(this);
        if(getArguments()!=null) {
            mViewPager.setCurrentItem(2);
        }
        else{
            callAgendaService();
        }
        if(mViewPager.getCurrentItem()==0 || mViewPager.getCurrentItem() == 1) {
            ((HomeActivity) getActivity()).getSupportActionBar().setTitle(language.getLabelDailyAgenda());
        }
        else {
            ((HomeActivity) getActivity()).getSupportActionBar().setTitle(language.getLabelWeeklyAgenda());
        }

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
        });*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        if(rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_daily_agenda, container, false);
            super.initializeFragment(rootView);
        }
        else
        {
            if(mViewPager != null) {
                matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
                language = matecoPriceApplication.getLanguage();
                mSlidingTabLayout.setText(language.getLabelToday(),0);
                mSlidingTabLayout.setText(language.getLabelOther(),1);
                mSlidingTabLayout.setText(language.getLabelWeeklyAgenda(),2);
                if (mViewPager.getCurrentItem() == 0 || mViewPager.getCurrentItem() == 1) {
                    ((HomeActivity) getActivity()).getSupportActionBar().setTitle(language.getLabelDailyAgenda());
                } else {
                    ((HomeActivity) getActivity()).getSupportActionBar().setTitle(language.getLabelWeeklyAgenda());
                }
            }

        }
        return rootView;

        //rootView = inflater.inflate(R.layout.temp_under_construction, container, false);

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

        try {
            if(matecoPriceApplication.getData(DataHelper.WeeklyAgendaDate,"").equals(""))
            {
                strDateToSendAgenda = format.format(readFormat.parse(DataHelper.formatDisplayDate(new Date())));
                setDateWeely(strDateToSendAgenda);
            }
            else{
                strDateToSendWeekly = matecoPriceApplication.getData(DataHelper.WeeklyAgendaDate,"");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

      /*  compoundButtonAgendaDailyPrevious = (LinearLayout)rootView.findViewById(R.id.compoundButtonAgendaDailyPrevious);
        compoundButtonAgendaDailyDatePicker = (LinearLayout)rootView.findViewById(R.id.compoundButtonAgendaDailyDatePicker);
        compoundButtonAgendaDailyNext = (LinearLayout)rootView.findViewById(R.id.compoundButtonAgendaDailyNext);
        labelAgendaDailyDateSelected = (TextView)rootView.findViewById(R.id.labelAgendaDailyDateSelected);*/

        SimpleDateFormat readFormat = new SimpleDateFormat("dd.MM.yyyy, E");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            strDateToSendAgenda = format.format(readFormat.parse(DataHelper.formatDisplayDate(new Date())));
            LogApp.showLog("date1", strDateToSendAgenda + " date2 " + matecoPriceApplication.getAgendaSelectedDate(DataHelper.AgendaDate, ""));
            //if(!matecoPriceApplication.getAgendaSelectedDate(DataHelper.AgendaDate, "").equals(strDateToSendAgenda))
            /*if(getArguments() == null) {
                callAgendaService();
            }*/
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mViewPager = (NonSwipeableViewPager) rootView.findViewById(R.id.viewpager);
        adapter = new VisitPlanDailyAdapter(getChildFragmentManager(), mTabs);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(2);
        mSlidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setOnPageChangeListener(this);
        if(getArguments()!=null) {
            mViewPager.setCurrentItem(2);
        }
        else{
            callAgendaService();
        }
        if(mViewPager.getCurrentItem()==0 || mViewPager.getCurrentItem() == 1) {
            ((HomeActivity) getActivity()).getSupportActionBar().setTitle(language.getLabelDailyAgenda());
        }
        else {
            ((HomeActivity) getActivity()).getSupportActionBar().setTitle(language.getLabelWeeklyAgenda());
        }

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

        /*if(!strDateToSendAgenda.equals("")) {

            setDate(strDateToSendAgenda);
        }
        else {
            Log.e("enter","else");
            labelAgendaDailyDateSelected.setText(DataHelper.formatDisplayDate(new Date()));
        }*/
        /*compoundButtonAgendaDailyPrevious.setOnClickListener(this);
        compoundButtonAgendaDailyDatePicker.setOnClickListener(this);
        compoundButtonAgendaDailyNext.setOnClickListener(this);*/
    }

    private void setDateWeely(String dateString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter.parse(dateString);
            strDateToSendWeekly = DataHelper.formatDisplayDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void callAgendaService()
    {
        if(DataHelper.isNetworkAvailable(getActivity()))
        {
            try {
                final String finaldate = format.format(readFormat.parse(strDateToSendWeekly));
                showProgressDialog();
                final long starTime = System.currentTimeMillis();
                loginPersonId = matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber();
                String urlAgendadate = DataHelper.URL_AGENDA_HELPER + "combinedagendalist"
                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/mitarbeiter=" + loginPersonId
                        + "/datum=" + strDateToSendAgenda
                        + "/datumweekly=" + finaldate;
                JsonObjectRequest object = new JsonObjectRequest(Request.Method.GET, urlAgendadate, "", new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        long endTime = System.currentTimeMillis() - starTime;
                        LogApp.showLog("success"," response from volley :  time taken : " + endTime);
                        matecoPriceApplication.saveData(DataHelper.StoreAgenda, response.toString());
                        adapter = new VisitPlanDailyAdapter(getChildFragmentManager(), mTabs);
                        mViewPager.setAdapter(adapter);
                        if(getArguments()!=null) {
                            mViewPager.setCurrentItem(2);
                        }
                        //loadAdapterWeeklyAgenda();
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
        if(position==0 || position==1) {
            ((HomeActivity) getActivity()).getSupportActionBar().setTitle(language.getLabelDailyAgenda());
        }
        else {
            ((HomeActivity) getActivity()).getSupportActionBar().setTitle(language.getLabelWeeklyAgenda());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
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
