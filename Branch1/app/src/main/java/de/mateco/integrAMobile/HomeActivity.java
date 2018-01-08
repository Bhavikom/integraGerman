package de.mateco.integrAMobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import de.mateco.integrAMobile.Helper.AddPriceParsableClass;
import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.NavigationChild;
import de.mateco.integrAMobile.Helper.PreferencesClass;
import de.mateco.integrAMobile.Helper.PreferencesData;
import de.mateco.integrAMobile.adapter.ExpandableListDrawerAdapter;
import de.mateco.integrAMobile.base.BaseActivity;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.fragment.CustomerActivityFragment;
import de.mateco.integrAMobile.fragment.CustomerContactPersonFragment;
import de.mateco.integrAMobile.fragment.CustomerDashBoardFragment;
import de.mateco.integrAMobile.fragment.CustomerDataFragment;
import de.mateco.integrAMobile.fragment.CustomerNewFragment;
import de.mateco.integrAMobile.fragment.CustomerOfferOrderFragment;
import de.mateco.integrAMobile.fragment.CustomerSearchFragment;
import de.mateco.integrAMobile.fragment.ExtraSendVisitingCardFragment;
import de.mateco.integrAMobile.fragment.HomeFragment;
import de.mateco.integrAMobile.fragment.PricingFragment;
import de.mateco.integrAMobile.fragment.ProjectNewFragment;
import de.mateco.integrAMobile.fragment.ProjectSearchFragment;
import de.mateco.integrAMobile.fragment.SettingAppFragment;
import de.mateco.integrAMobile.fragment.SettingFragment;
import de.mateco.integrAMobile.fragment.SiteInspectionNewFragment;
import de.mateco.integrAMobile.fragment.SiteInspectionResumeDraftFragment;
import de.mateco.integrAMobile.fragment.SiteInspectionResumeOutboxFragment;
import de.mateco.integrAMobile.fragment.SiteInspectionResumeSentFragment;
import de.mateco.integrAMobile.fragment.VisitPlanDailyAgendaFragment;
import de.mateco.integrAMobile.fragment.VisitPlanDailyAgendaWeeklyFragment;
import de.mateco.integrAMobile.model.Language;


public class HomeActivity extends BaseActivity implements ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener, SettingAppFragment.OnLanguageSelected
{

    public boolean isFirsttime=true;
    PreferencesClass preferences2;
    PreferencesData prefObj;
    private DrawerLayout drawer;
    private ExpandableListView drawerList;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ArrayList<NavigationChild> groupItem = new ArrayList<NavigationChild>();
    private ArrayList<ArrayList<String>> childItem = new ArrayList<ArrayList<String>>();
    private Toolbar toolbar;
    private MatecoPriceApplication matecoPriceApplication;
    private ExpandableListDrawerAdapter adapter;
    private Language language;
    private SharedPreferences preferences;
    private SharedPreferences preferences3;

    private DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        super.initializeActivity();
        if (savedInstanceState == null) {
            selectItem(0, 0);
        }
        if(!matecoPriceApplication.isUserLogin(DataHelper.isPersonLogin, false))
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }


    }

    @Override
    public void initializeComponents()
    {
        Window window= this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if(DataHelper.APP_NAME.equalsIgnoreCase("integrAMobile/MatecoSalesAppService.svc/json/")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(this.getResources().getColor(R.color.primary_dark));
            }
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                if(DataHelper.APP_NAME.equalsIgnoreCase("integrAMobileTest/MatecoSalesAppService.svc/json/")){
                    window.setStatusBarColor(this.getResources().getColor(R.color.primary_dark2));
                }
                else {
                    window.setStatusBarColor(this.getResources().getColor(R.color.primary_dark3));
                }
            }
        }
        preferences2 = new PreferencesClass(this);
        prefObj = new PreferencesData(this);
        matecoPriceApplication = (MatecoPriceApplication)getApplication();
        language = matecoPriceApplication.getLanguage();
        preferences = getSharedPreferences("SiteInspection",MODE_PRIVATE);
        preferences3 = getSharedPreferences("PRICED",MODE_PRIVATE);
        db = new DataBaseHandler(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerList = (ExpandableListView) findViewById(R.id.left_drawer);
        adapter = new ExpandableListDrawerAdapter(this, groupItem, childItem, matecoPriceApplication);

        setGroupData();
        setChildGroupData();
        setLanguage();
        super.initializeComponents();
    }

    @Override
    public void bindEvents()
    {
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawer.setDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        adapter.setInflater((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE), this);
        drawerList.setAdapter(adapter);
        drawerList.setOnGroupClickListener(this);
        drawerList.setOnChildClickListener(this);
        actionBarDrawerToggle.syncState();
        super.bindEvents();
    }
    private void setLanguage()
    {

    }
    public void setGroupData()
    {
        groupItem.add(new NavigationChild(0, language.getLabelHome(), R.drawable.icn_home));
        groupItem.add(new NavigationChild(1, language.getLabelCustomer(), R.drawable.icn_customer));
        groupItem.add(new NavigationChild(2, language.getLabelCockpit(), R.drawable.icn_pricing));
        groupItem.add(new NavigationChild(3, language.getLabelProject(), R.drawable.icn_projects));
        groupItem.add(new NavigationChild(4, language.getLabelSiteInspection(), R.drawable.icn_site_inspection));
        groupItem.add(new NavigationChild(5, language.getLabelVisitPlan(), R.drawable.icn_visit_plan));
        groupItem.add(new NavigationChild(6, language.getLabelExtra(), R.drawable.icn_extra));
    }

    public void setChildGroupData()
    {
        ArrayList<String> child = new ArrayList<String>();
        childItem.add(child);

        child = new ArrayList<String>();
        child.add(language.getLabelSearch());
        child.add(language.getLabelNew());
        child.add(language.getLabelCustomerData());
        child.add(language.getLabelContactPerson());
        child.add(language.getLabelActivity());
        //child.add(language.getLabelDashBoard());
        child.add(language.getLabelOffer()+"/"+language.getLabelOrder());
        childItem.add(child);

        child = new ArrayList<String>();
        child.add(language.getLabelPricing());
        childItem.add(child);


        child = new ArrayList<String>();
        child.add(language.getLabelSearch());
        child.add(language.getLabelNew());
        childItem.add(child);

        child = new ArrayList<String>();
        child.add(language.getLabelNew());
        child.add(language.getLabelBvoDraft());
        child.add(language.getLabelBvoSent());
        child.add(language.getLabelBvoProcessed());
        childItem.add(child);

        child = new ArrayList<String>();
        child.add(language.getLabelDailyAgenda());
        child.add(language.getLabelWeeklyAgenda());
        childItem.add(child);

        child = new ArrayList<String>();
        child.add(language.getLabelSendVisitingCard());
        childItem.add(child);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
    {
        int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
        parent.setItemChecked(index, true);
        selectItem(groupPosition, childPosition);
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id)
    {
        selectItem(groupPosition, -1);
        return false;
    }

    private void selectItem(int groupPosition, int childPosition)
    {
        //selectedPosition = position;

        Fragment fragment = null;
        Bundle args = new Bundle();
        String tag = "";
        if(groupPosition == 0)
        {
            if(isFirsttime){
                isFirsttime=false;
                resetPrice();

                preferences2.clearPreferences();
                preferences2.clearCombobox();
                clearDate();

                fragment = new HomeFragment();
                setTitle("Home");
                args.putString("Name", "Home");
                tag = "Home";
                PreferencesClass pref = new PreferencesClass(this);
                AddPriceParsableClass parsableClass = pref.getPriceData(this);
                Log.e(" stasrty date : "," get start date : "+parsableClass.strStartDate+" : "+parsableClass.strEndDate);
            }
            else {
                if(!preferences2.getisPrice().equalsIgnoreCase(""))
                {
                    if(db.getLostsaleCount() > 0 ){
                        showAlertDialg(1);
                    }

                }
                else {
                    resetPrice();

                    preferences2.clearPreferences();
                    preferences2.clearCombobox();
                    clearDate();

                    fragment = new HomeFragment();
                    setTitle("Home");
                    args.putString("Name", "Home");
                    tag = "Home";
                    PreferencesClass pref = new PreferencesClass(this);
                    AddPriceParsableClass parsableClass = pref.getPriceData(this);
                    Log.e(" stasrty date : "," get start date : "+parsableClass.strStartDate+" : "+parsableClass.strEndDate);

                }
            }


        }
        else if(groupPosition == 1 && childPosition == 0)
        {
            if(!preferences2.getisPrice().equalsIgnoreCase("")) {
                if (db.getLostsaleCount() > 0) {
                    showAlertDialg(2);
                }
            }
            else {
                preferences2.clearPreferences();
                preferences2.clearCombobox();
                clearDate();
                fragment = new CustomerSearchFragment();
                setTitle("Customer Search");
                args.putString("Name", "Customer Search");
                tag = "Customer Search";

            }


        }
        else if(groupPosition == 1 && childPosition == 1)
        {
            if(!preferences2.getisPrice().equalsIgnoreCase(""))
            {
                if(db.getLostsaleCount() > 0 )
                {
                    showAlertDialg(3);
                }
            }
            else {
                preferences2.clearPreferences();
                preferences2.clearCombobox();
                clearDate();
                fragment = new CustomerNewFragment();
                setTitle("New Customer");
                args.putString("Name", "New Customer");
                tag = "New Customer";

            }


        }
        else if(groupPosition == 1 && childPosition == 2)
        {

            if(!preferences2.getisPrice().equalsIgnoreCase(""))
            {
                if(db.getLostsaleCount() > 0 ){
                    showAlertDialg(4);
                }
            }

            else {
                if(matecoPriceApplication.isCustomerLoaded(DataHelper.isCustomerLoaded, false))
                {
                    preferences2.clearPreferences();
                    preferences2.clearCombobox();
                    clearDate();
                    fragment = new CustomerDataFragment();
                    setTitle("Customer Data");
                    args.putString("Name", "Customer Data");
                    tag = "Customer Data";
                }
                else
                {
                    //Toast.makeText(this, "Please select customer first", Toast.LENGTH_SHORT).show();
                    showShortToast(language.getMessageLoadCustomerFirst());
                }

            }


        }
        else if(groupPosition == 1 && childPosition == 3)
        {

            if(!preferences2.getisPrice().equalsIgnoreCase(""))
            {
                if(db.getLostsaleCount() > 0 ){
                    showAlertDialg(5);
                }
            }

            else {
                if(matecoPriceApplication.isCustomerLoaded(DataHelper.isCustomerLoaded, false))
                {
                    preferences2.clearPreferences();
                    preferences2.clearCombobox();
                    clearDate();
                    fragment = new CustomerContactPersonFragment();
                    setTitle("Customer Contact Person");
                    args.putString("Name", "Customer Contact Person");
                    tag = "Customer Contact Person";
                }
                else
                {
                    //Toast.makeText(this, "Please select customer first", Toast.LENGTH_SHORT).show();
                    showShortToast(language.getMessageLoadCustomerFirst());
                }

            }



        }
        else if(groupPosition == 1 && childPosition == 4)
        {
            if(!preferences2.getisPrice().equalsIgnoreCase(""))
            {
                if(db.getLostsaleCount() > 0 ){
                    showAlertDialg(6);
                }
            }

            else {
                if(matecoPriceApplication.isCustomerLoaded(DataHelper.isCustomerLoaded, false))
                {
                    preferences2.clearPreferences();
                    preferences2.clearCombobox();
                    clearDate();
                    fragment = new CustomerActivityFragment();
                    setTitle("Customer Activity");
                    args.putString("Name", "Customer Activity");
                    tag = "Customer Activity";
                }
                else
                {
                    //Toast.makeText(this, "Please select customer first", Toast.LENGTH_SHORT).show();
                    showShortToast(language.getMessageLoadCustomerFirst());
                }
            }


        }
        /*else if(groupPosition == 1 && childPosition == 5)
        {
            if(!preferences2.getisPrice().equalsIgnoreCase(""))
            {
                if(db.getLostsaleCount() > 0 ){
                    showAlertDialg(7);
                }
            }

            else {
                if(matecoPriceApplication.isCustomerLoaded(DataHelper.isCustomerLoaded, false))
                {
                    preferences2.clearPreferences();
                    preferences2.clearCombobox();
                    clearDate();
                    fragment = new CustomerDashBoardFragment();
                    setTitle("Customer DashBoard");
                    args.putString("Name", "Customer Dashboard");
                    tag = "Customer Dashboard";
                }
                else
                {
                    //Toast.makeText(this, "Please select customer first", Toast.LENGTH_SHORT).show();
                    showShortToast(language.getMessageLoadCustomerFirst());
                }
            }

        }*/
        else if(groupPosition == 1 && childPosition == 5)
        {
            if(!preferences2.getisPrice().equalsIgnoreCase(""))
            {
                if(db.getLostsaleCount() > 0 ){
                    showAlertDialg(8);
                }
            }
            else {
                if(matecoPriceApplication.isCustomerLoaded(DataHelper.isCustomerLoaded, false))
                {
                    preferences2.clearPreferences();
                    preferences2.clearCombobox();
                    clearDate();
                    fragment = new CustomerOfferOrderFragment();
                    setTitle("Customer Offer/Order");
                    args.putString("Name", "Customer Offer");
                    tag = "Customer Offer";
                }
                else
                {
                    //Toast.makeText(this, "Please select customer first", Toast.LENGTH_SHORT).show();
                    showShortToast(language.getMessageLoadCustomerFirst());
                }
            }

        }
        else if(groupPosition == 2 && childPosition == 0)
        {


                //preferences2.clearPreferences();
                fragment = new PricingFragment();
                setTitle("Pricing");
                args.putString("Name", "Pricing");
                tag = "Pricing";



        }
        else if(groupPosition == 3 && childPosition == 0)
        {
            if(!preferences2.getisPrice().equalsIgnoreCase(""))
            {
                if(db.getLostsaleCount() > 0 ){
                    showAlertDialg(10);
                }
            }

            else {
                preferences2.clearPreferences();
                preferences2.clearCombobox();
                clearDate();
                fragment = new ProjectSearchFragment();
                setTitle("Project Search");
                args.putString("Name", "Project Search");
                tag = "Project";
            }

        }
        else if(groupPosition == 3 && childPosition == 1)
        {
            if(!preferences2.getisPrice().equalsIgnoreCase(""))
            {
                if(db.getLostsaleCount() > 0 ){
                    showAlertDialg(11);
                }
            }

            else {
                preferences2.clearPreferences();
                preferences2.clearCombobox();
                clearDate();
                fragment = new ProjectNewFragment();
                setTitle("Project New");
                args.putString("Name", "Project New");
                tag = "Project New";
            }

        }
        else if(groupPosition == 4 && childPosition == 0)
        {
            if(!preferences2.getisPrice().equalsIgnoreCase(""))
            {
                if(db.getLostsaleCount() > 0 ){
                    showAlertDialg(12);
                }
            }

            else {
                preferences2.clearPreferences();
                preferences2.clearCombobox();
                clearDate();
                fragment = new SiteInspectionNewFragment();
                setTitle("Site Inspection New");
                args.putString("Name", "Site Inspection new");
                tag = "Site Inspection New";
            }


        }
        else if(groupPosition == 4 && childPosition == 1)
        {
            if(!preferences2.getisPrice().equalsIgnoreCase(""))
            {
                if(db.getLostsaleCount() > 0 ){
                    showAlertDialg(13);
                }
            }

            else {
                preferences2.clearPreferences();
                preferences2.clearCombobox();
                clearDate();
                fragment = new SiteInspectionResumeDraftFragment();
                setTitle("Site Inspection Resume Draft");
                args.putString("Name", "Resume Draft");
                tag = "Site Inspection Resume Draft";
            }


        }
        else if(groupPosition == 4 && childPosition == 2)
        {
            if(!preferences2.getisPrice().equalsIgnoreCase(""))
            {
                if(db.getLostsaleCount() > 0 ){
                    showAlertDialg(14);
                }
            }

            else {
                preferences2.clearPreferences();
                preferences2.clearCombobox();
                clearDate();
                fragment = new SiteInspectionResumeSentFragment();
                setTitle("Site Inspection Resume Sent");
                args.putString("Name", "Resume Sent");
                tag = "Site Inspection Resume Sent";
            }


        }
        else if(groupPosition == 4 && childPosition == 3)
        {
            if(!preferences2.getisPrice().equalsIgnoreCase(""))
            {
                if(db.getLostsaleCount() > 0 ){
                    showAlertDialg(15);
                }
            }

            else {
                preferences2.clearPreferences();
                preferences2.clearCombobox();
                clearDate();
                fragment = new SiteInspectionResumeOutboxFragment();
                setTitle("Site Inspection Resume Outboc");
                args.putString("Name", "Resume Outbox");
                tag = "Site Inspection Resume Outbox";
            }

        }
        else if(groupPosition == 5 && childPosition == 0)
        {
            if(!preferences2.getisPrice().equalsIgnoreCase(""))
            {
                if(db.getLostsaleCount() > 0 ){
                    showAlertDialg(16);
                }
            }

            else {
                preferences2.clearPreferences();
                preferences2.clearCombobox();
                clearDate();
                fragment = new VisitPlanDailyAgendaFragment();
                setTitle("Daily Agenda");
                args.putString("Name", "Site inspection Daily Agenda");
                tag = "Site Inspection Daily Agenda";
            }

        }
        else if(groupPosition == 5 && childPosition == 1)
        {
            if(!preferences2.getisPrice().equalsIgnoreCase(""))
            {
                if(db.getLostsaleCount() > 0 ){
                    showAlertDialg(17);
                }
            }

            else {
                preferences2.clearPreferences();
                preferences2.clearCombobox();
                clearDate();
                fragment = new VisitPlanDailyAgendaFragment();
                setTitle("Weekly Agenda");
                args.putString("Name", "Site Inspection Weekly Agenda");
                fragment.setArguments(args);
                tag = "Site Inspection Weekly Agenda";
            }


        }
        else if(groupPosition == 6 && childPosition == 0)
        {
            if(!preferences2.getisPrice().equalsIgnoreCase(""))
            {
                if(db.getLostsaleCount() > 0 ){
                    showAlertDialg(18);
                }
            }

            else {
                preferences2.clearPreferences();
                preferences2.clearCombobox();
                clearDate();
                fragment = new ExtraSendVisitingCardFragment();
                setTitle("Send Card");
                args.putString("Name", "Send card");
                tag = "Send Card";
            }


        }
        // update the main content by replacing fragments
        if(fragment != null)
        {
            if(preferences.getInt(DataHelper.SiteInspectionId, 0)!=0)
            {
                if(db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId, 0)).getFlag()!=3)
                {
                    db.updateSiteInspectionDate(preferences.getInt(DataHelper.SiteInspectionId, 0));
                    db.updateSiteInspectionUpload(preferences.getInt(DataHelper.SiteInspectionId, 0), "", 2);
                }
            }
            preferences.edit().clear().commit();
            prefObj.clearEinztatInfo();
            preferences2.clearPreferences();
            preferences2.clearCombobox();
            clearDate();
            //fragment.setArguments(args);
            drawer.closeDrawer(drawerList);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            /*for(int i = 0; i < fragmentManager.getBackStackEntryCount(); i++)
            {
                fragmentManager.popBackStack();
            }*/
            if(groupPosition != 0)
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, tag).addToBackStack(tag).commit();
            else
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, tag).commit();
        }
        // update selected item and title, then close the drawer
        //mDrawerList.setItemChecked(selectedPosition, true);
    }
    public void showAlertDialg(final int pos)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle(language.getLabelNote());

        // set dialog message
        alertDialogBuilder
                .setMessage("Die kundenmaske kann nicht geschlossen werden! Bitte bearbeiten sie alle zeilen des Zwischenspeichers!")
                .setCancelable(false)
                .setNegativeButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
    private void clearDate(){
        preferences2.clearstartDate();
        preferences2.clearendDate();
        preferences2.clearstartTime();
        preferences2.clearendTime();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 300 || requestCode == 400)
        {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setTitle(CharSequence title)
    {
        //mTitle = title;
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        menu.clear();
        return true;
    }

    @Override
    public void onBackPressed()
    {
        if(drawer.isDrawerOpen(Gravity.LEFT))
        {
            drawer.closeDrawer(Gravity.LEFT);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    public void updateAdapter()
    {
        if(adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onLanguageUpdated()
    {
        Log.e("here at","language updated");
        language = matecoPriceApplication.getLanguage();
        groupItem.get(0).setName(language.getLabelHome());
        groupItem.get(1).setName(language.getLabelCustomer());
        groupItem.get(2).setName(language.getLabelCockpit());
        groupItem.get(3).setName(language.getLabelProject());
        groupItem.get(4).setName(language.getLabelSiteInspection());
        groupItem.get(5).setName(language.getLabelVisitPlan());
        groupItem.get(6).setName(language.getLabelExtra());

        childItem.get(1).set(0, language.getLabelSearch());
        childItem.get(1).set(1, language.getLabelNew());
        childItem.get(1).set(2, language.getLabelCustomerData());
        childItem.get(1).set(3, language.getLabelContactPerson());
        childItem.get(1).set(4, language.getLabelActivity());
        //childItem.get(1).set(5, language.getLabelDashBoard());
        childItem.get(1).set(5, language.getLabelOffer()+"/"+language.getLabelOrder());

        childItem.get(2).set(0, language.getLabelPricing());

        childItem.get(3).set(0, language.getLabelSearch());
        childItem.get(3).set(1, language.getLabelNew());

        childItem.get(4).set(0, language.getLabelNew());
        childItem.get(4).set(1, language.getLabelResume());
        childItem.get(4).set(2, language.getLabelBvoSent());
        childItem.get(4).set(3, language.getLabelBvoProcessed());

        childItem.get(5).set(0, language.getLabelDailyAgenda());
        childItem.get(5).set(1, language.getLabelWeeklyAgenda());

        childItem.get(6).set(0, language.getLabelSendVisitingCard());
        adapter.notifyDataSetChanged();
        SettingFragment fragment = (SettingFragment)getSupportFragmentManager().findFragmentByTag("Setting");
        if(fragment!=null && fragment.isVisible())
            fragment.updateTabs();
    }
    public void resetPrice(){
        AddPriceParsableClass parssable = new AddPriceParsableClass(String.valueOf(false),String.valueOf(false),
                String.valueOf(false),String.valueOf(false),String.valueOf(false)
                ,String.valueOf(false),
                String.valueOf(false),String.valueOf(false),String.valueOf(false),"",
                "", "", "", 0);

        PreferencesClass.storePriceData(this, parssable);
    }
    @Override
    protected void onDestroy() {

        super.onDestroy();
        preferences2.clearisPrice();
        //Put your http calls code here . It always called when your activity close
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        preferences2.clearisPrice();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && permissions!= null && grantResults!= null)
        {
            try
            {
                for (Fragment fragment : fragments) {
                    fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            }
            catch (Exception e){
                Log.e(""," exception whie permission grant : ");
            }

        }
    }
    //    @Override
//    public void editingComplete(String data)
//    {
//        //CustomerDataFragment customerDataFragment = (CustomerDataFragment) getSupportFragmentManager().findFragmentByTag("Customer Data");
//
////        if (customerDataFragment != null)
////        {
////            // If article frag is available, we're in two-pane layout...
////            // Call a method in the ArticleFragment to update its content
////            Log.e("in update", "in update");
////            customerDataFragment.updateView(data);
////        }
////        else
////        {
//            Log.e("in if else update", "in if else update");
//        getFragmentManager().popBackStack();
//
////            CustomerDataFragment newFragment = new CustomerDataFragment();
////            Bundle args = new Bundle();
////            //args.putInt(ArticleFragment.ARG_POSITION, position);
////            newFragment.setArguments(args);
////            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
////            // Replace whatever is in the fragment_container view with this fragment,
////            // and add the transaction to the back stack so the user can navigate back
////            transaction.replace(R.id.content_frame, newFragment);
////            transaction.addToBackStack("Customer Data");
////            // Commit the transaction
////            transaction.commit();
//        //}
//    }
}
