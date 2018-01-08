package de.mateco.integrAMobile.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.mateco.integrAMobile.CalendarPickerView;
import de.mateco.integrAMobile.Helper.Constants;
import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.GlobalClass;
import de.mateco.integrAMobile.Helper.PreferencesClass;
import de.mateco.integrAMobile.Helper.PreferencesData;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.Pricing1BranchDataAdapter;
import de.mateco.integrAMobile.adapter.Pricing1ContactPersonDataAdapter;
import de.mateco.integrAMobile.adapter.Pricing1DeviceDataAdapter;
import de.mateco.integrAMobile.adapter.Pricing1EquipementDataAdapter;
import de.mateco.integrAMobile.adapter.Pricing1LevelGroupDataAdapter;
import de.mateco.integrAMobile.adapter.Pricing1PriceRentalDataAdapter;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.LoadedCustomerFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.ContactPersonModel;
import de.mateco.integrAMobile.model.CustomerModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LoginPersonModel;
import de.mateco.integrAMobile.model.Pricing1BranchData;
import de.mateco.integrAMobile.model.Pricing1DeviceData;
import de.mateco.integrAMobile.model.Pricing1EquipmentData;
import de.mateco.integrAMobile.model.Pricing1EquipmentInsertData;
import de.mateco.integrAMobile.model.Pricing1LevelGroupData;
import de.mateco.integrAMobile.model.Pricing1PriceRentalData;
import de.mateco.integrAMobile.timesquare.CalendarCellDecorator;

public class PricingFragment extends LoadedCustomerFragment implements CalendarPickerView.interfaceDateSelected
{

    private Handler customHandler = new Handler();
    Boolean isCheckBoxChecked=false, isCalanderEnabled = false;
    EditText editStart,editEnd;
    CheckBox checkboxIsWeekend,checkBoxDisable;
    Button btnReset = null;
    ImageButton imgFromdate,imgTodate;

    public static Date selectedStartdate=null,selectedEnddate=null;
    public static ArrayList<Date> selectedDatesStartEnd;

    private CalendarPickerView dialogCalendarView;
    private Calendar nextYear,lastYear;
    private AlertDialog alertDialogSelectDate;

    List<Date> arrayListDates = new ArrayList<>();
    List<String> arraylistDatesString;
    List<Date> arrayStartEndDate;

    PreferencesClass prefereces;
    PreferencesData prefereces2;
    private View rootView;
    private MatecoPriceApplication matecoPriceApplication;
    private Language language;
    private DataBaseHandler db;
    private List<Pricing1BranchData> rowBranchItems;
    private ArrayList<Pricing1BranchData> lablesBranch;
    private List<ContactPersonModel> rowContactPersonItems;
    private ArrayList<ContactPersonModel> lablesContactPerson;
    private List<Pricing1DeviceData> rowDeviceItems;
    private ArrayList<Pricing1DeviceData> lablesDevice;
    private List<Pricing1PriceRentalData> rowPriceRentalItems;
    private ArrayList<Pricing1PriceRentalData> lablesPriceRental;
    private Spinner spPricing1Brand;
    private Spinner spPricing1ContactPerson;
    private Spinner spPricing1Device;
    private Spinner spPricing1PriceRental;
    private ListView lvPricing1LevelGroup;
    private ListView lvPricing1Equipment;
    private int deviceId;
    private int unitId;
    private int branchId;
    private String contactPersonNo = "";
    private String contactPerson = "";
    private String branchName,branchOrt,branchPlz,branchStrasse;
    private List<Pricing1LevelGroupData> rowLevelGroupItems;
    private Pricing1LevelGroupDataAdapter levelGroupAdapter = null;
    private ArrayList<Pricing1EquipmentData> rowEquipmentItems;
    private Pricing1EquipementDataAdapter equipmentAdapter = null;
    private ArrayList<Integer> selectedEquipments = new ArrayList<>();
    private String LevelGroupDesignation="";
    private ImageButton imgBtnFromDate;
    private ImageButton imgBtnToDate;
    private TextView txtFromDate;
    private TextView txtToDate;
    private EditText etRetalPriceDays;
    private LinearLayout llStartEndDateContainer;

    //private SimpleDateFormat formatterDate = new SimpleDateFormat("dd.MM.yyyy");
    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    private TextView txtEinheit;
    private String kanr;
    private boolean isFromSearch;
    private String AnsPartenrId;
    private BasicAsyncTaskGetRequest asyncTaskLevelGroup, asyncTaskEquipment;
    private String blockCharacterSet = "~#^|$%&*!,.;'";
    Pricing1PriceRentalDataAdapter priceRentalAdapter=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if(rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_pricing_1, container, false);
            super.initializeFragment(rootView);
        }
        else
        {
            if(rootView.getParent() != null)
                ((ViewGroup)rootView.getParent()).removeView(rootView);
        }
        return rootView;
//        TextView txtNameFragment = (TextView)rootView.findViewById(R.id.txtNameFragment);
//        String text = getArguments().getString("Name");
//        txtNameFragment.setText(text);

        /*rootView = inflater.inflate(R.layout.fragment_pricing_1, container, false);
        super.initializeFragment(rootView);
        return rootView;*/
    }
    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };
    @Override
    public void initializeComponents(View rootView)
    {

        if(getArguments() != null) {
            isFromSearch = getArguments().getBoolean(Constants.IsFromSearch, false);
            AnsPartenrId = getArguments().getString("AnsPartenrId","");
        }

        matecoPriceApplication = (MatecoPriceApplication) getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        db = new DataBaseHandler(getActivity());
        //int y = 5/0;
        prefereces = new PreferencesClass(getActivity());
        prefereces2 = new PreferencesData(getActivity());
        rowBranchItems = new ArrayList<>();
        rowContactPersonItems = new ArrayList<>();
        rowDeviceItems = new ArrayList<>();
        rowPriceRentalItems = new ArrayList<>();
        rowLevelGroupItems = new ArrayList<>();
        rowEquipmentItems = new ArrayList<>();
        txtEinheit = (TextView)rootView.findViewById(R.id.txtEinheit);
        spPricing1Brand = (Spinner) rootView.findViewById(R.id.spPricing1Brand);
        spPricing1ContactPerson = (Spinner) rootView.findViewById(R.id.spPricing1CustomerName);
        spPricing1Device = (Spinner) rootView.findViewById(R.id.spPricing1Device);
        spPricing1PriceRental = (Spinner) rootView.findViewById(R.id.spPriceRental);
        lvPricing1LevelGroup = (ListView) rootView.findViewById(R.id.lvPricing1LevelGroup);
        lvPricing1Equipment = (ListView) rootView.findViewById(R.id.lvPricing1Equipment);
        etRetalPriceDays = (EditText) rootView.findViewById(R.id.etRetalPriceDays);
        etRetalPriceDays.setFilters(new InputFilter[] { filter });
        imgBtnFromDate = (ImageButton) rootView.findViewById(R.id.imgBtnFromDate);
        imgBtnToDate = (ImageButton) rootView.findViewById(R.id.imgBtnToDate);
        levelGroupAdapter = new Pricing1LevelGroupDataAdapter(getActivity(), rowLevelGroupItems);
        lvPricing1LevelGroup.setAdapter(levelGroupAdapter);
        equipmentAdapter = new Pricing1EquipementDataAdapter(getActivity(), R.layout.fragment_pricing1_equipment_row, rowEquipmentItems);
        lvPricing1Equipment.setAdapter(equipmentAdapter);
//        equipmentAdapter = new Pricing1EquipementDataAdapter(getActivity(), R.layout.fragment_pricing1_equipment_row, rowEquipmentItems);
//        lvPricing1Equipment.setAdapter(equipmentAdapter);
        try
        {
            if (matecoPriceApplication.isCustomerLoaded(DataHelper.isCustomerLoaded, false))
            {
                kanr = matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();
                Log.e("kanr ...", kanr);
            }
            else
            {
                kanr = "0";
                //Toast.makeText(getActivity(), "Please select Customer first", Toast.LENGTH_SHORT).show();
                showShortToast(language.getMessageSelectCustomerFirst());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            kanr = "0";
        }
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle(language.getLabelPrice());
        setUpLanguage();
        super.initializeComponents(rootView);
    }

    @Override
    public void bindEvents(View rootView)
    {
        spPricing1Brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                matecoPriceApplication.clearPricingData();
                if(position == 0)
                {
                    //selectedCountry = null;
                    branchId = -1;
                    branchName = null;
                }
                else
                {
                    //selectedCountry = listOfCountry.get(position - 1);
                    branchId = lablesBranch.get(position - 1).getClientId();
                    branchName = lablesBranch.get(position - 1).getDesignation();
                    branchOrt = lablesBranch.get(position - 1) .getOrt();
                    branchPlz = lablesBranch.get(position - 1).getPlz();
                    branchStrasse = lablesBranch.get(position -1).getStrasse();
                    prefereces.saveBranchId(branchId);

//                LevelGroupDesignation = lablesDevice.get(position).getDesignation();
                    Log.e(" in first fragment : "," branchId Id value : "+ branchId);
                    //loadLevelGroupListViewData(deviceId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        spPricing1ContactPerson.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (lablesContactPerson.size() > 0)
                {
                    if(position == 0)
                    {
                        contactPersonNo = "";
                        contactPerson = "";
                    }
                    else
                    {
                        contactPersonNo = lablesContactPerson.get(position - 1).getAnspartner();
                        contactPerson = lablesContactPerson.get(position - 1).getAnrede()+" "+lablesContactPerson.get(position - 1).getNachname();
                        Log.e("contactPersonNo", "" + contactPersonNo);
                    }
                }
                else
                {
                    contactPersonNo = "";
                    contactPerson = "";
                    Log.e("contactPersonNo", "" + contactPersonNo);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        spPricing1Device.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LevelGroupDesignation="";
                if(position == 0)
                {
                    deviceId = -1;
                }
                else
                {
                    deviceId = lablesDevice.get(position - 1).getHeight_main_group();
                    prefereces.savePriceDevice(String.valueOf(lablesDevice.get(position - 1).getHeight_main_group()));

                    //  LevelGroupDesignation = lablesDevice.get(position).getDesignation();
                }
                Log.e("Device Id", "" + deviceId);
                levelGroupAdapter.setSelectedIndex(-1);
                loadLevelGroupListViewData(deviceId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        spPricing1PriceRental.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unitId = lablesPriceRental.get(position).getUnit();
                Log.e("unitId Id", "" + unitId);
                //loadLevelGroupListViewData(deviceId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lvPricing1LevelGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                //view.setSelected(true);
                levelGroupAdapter.setSelectedIndex(position);
                equipmentAdapter.setSelectedIndex(-1);
//                LevelGroupDesignation = rowLevelGroupItems.get(position).getHeightGroup();
                LevelGroupDesignation = rowLevelGroupItems.get(position).getHeightGroup();
                Log.e("LevelGroup Designation", LevelGroupDesignation);


                loadEquipmentListViewData(LevelGroupDesignation);
            }
        });

        lvPricing1Equipment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                if (equipmentAdapter.mCheckStates.get(position))
                    equipmentAdapter.mCheckStates.put(position, false);
                else
                    equipmentAdapter.mCheckStates.put(position, true);

                equipmentAdapter.notifyDataSetChanged();


                // equipmentAdapter.setSelectedIndex(position);
            }
        });

        final DatePickerDialog.OnDateSetListener onFromDate = new DatePickerDialog.OnDateSetListener()
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
                    txtFromDate.setText(finaldate);

                    int totalDays = daysCalculate();
                    Log.e("totalDays", "" + totalDays);
                    if((txtFromDate.getText().toString().length()>0) && (txtToDate.getText().toString().length()>0))
                    {
                        etRetalPriceDays.setText(String.valueOf(totalDays));
                        for(int i = 0; i < lablesPriceRental.size(); i++)
                        {
                            if(lablesPriceRental.get(i).getUnit() == 2)
                            {
                                spPricing1PriceRental.setSelection(i);
                                break;
                            }
                        }
                    }
                    else
                    {
                        etRetalPriceDays.setText("1");
                        for(int i = 0; i < lablesPriceRental.size(); i++)
                        {
                            if(lablesPriceRental.get(i).getUnit() == 2)
                            {
                                spPricing1PriceRental.setSelection(i);
                                break;
                            }
                        }
                    }

                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
        };

        final DatePickerDialog.OnDateSetListener onToDate = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                try
                {
                    monthOfYear += 1;
                    String date = dayOfMonth + "." + monthOfYear + "." + year;
                    String finaldate = DataHelper.formatDate(formatter.parse(date));
                    Log.e("endDate", finaldate);
                    txtToDate.setText(finaldate);
                    int totalDays = daysCalculate();
                    Log.e("totalDays", "" + totalDays);
                    etRetalPriceDays.setText(String.valueOf(totalDays));
                    for(int i = 0; i < lablesPriceRental.size(); i++)
                    {
                        if(lablesPriceRental.get(i).getUnit() == 2)
                        {
                            spPricing1PriceRental.setSelection(i);
                            break;
                        }
                    }
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
        };

        imgBtnFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCalendar();
                /* Date today = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(today);
                DatePickerDialogFragment newFragment = new DatePickerDialogFragment();
                Bundle args = new Bundle();
                args.putInt("year", c.get(Calendar.YEAR));
                args.putInt("month", c.get(Calendar.MONTH));
                args.putInt("day", c.get(Calendar.DAY_OF_MONTH));
                newFragment.setArguments(args);*/
                /**
                 * Set Call back to capture selected date
                 */
                /* newFragment.setCallBack(onFromDate);
                Log.e("tag", "startDate");
                newFragment.show(getActivity().getSupportFragmentManager(), "startDate"); */
            }
        });

        imgBtnToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCalendar();
                /* Date today = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(today);
                // datePicker.setMinDate(date1)
                DatePickerDialogFragment newFragment = new DatePickerDialogFragment();
                try {
                    Date date1 = formatter.parse(txtFromDate.getText().toString());
                    Calendar cal1 = Calendar.getInstance();
                    cal1.setTime(date1);
                    Bundle args = new Bundle();
                    args.putInt("year", c.get(Calendar.YEAR));
                    args.putInt("month", c.get(Calendar.MONTH));
                    args.putInt("day", c.get(Calendar.DAY_OF_MONTH));
                    newFragment.setArguments(args);
                    /**
                     * Set Call back to capture selected date
                     */
                    /* newFragment.setCallBack(onToDate);
                    newFragment.setMinDate(date1);
                    Log.e("tag", "endDate");
                    newFragment.show(getActivity().getSupportFragmentManager(), "endDate");
                    // newFragment.setMinimumDate(cal1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
            }
        });

        txtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showCalendar();
                /*Date today = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(today);
                DatePickerDialogFragment newFragment = new DatePickerDialogFragment();
                Bundle args = new Bundle();
                args.putInt("year", c.get(Calendar.YEAR));
                args.putInt("month", c.get(Calendar.MONTH));
                args.putInt("day", c.get(Calendar.DAY_OF_MONTH));
                newFragment.setArguments(args);*/
                /**
                 * Set Call back to capture selected date
                 */
                /*newFragment.setCallBack(onFromDate);
                Log.e("tag", "startDate");
                newFragment.show(getActivity().getSupportFragmentManager(), "startDate");*/
            }
        });

        txtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCalendar();
                /*Date today = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(today);
                // datePicker.setMinDate(date1)
                DatePickerDialogFragment newFragment = new DatePickerDialogFragment();
                try {
                    Date date1 = formatter.parse(txtFromDate.getText().toString());
                    Calendar cal1 = Calendar.getInstance();
                    cal1.setTime(date1);
                    Bundle args = new Bundle();
                    args.putInt("year", c.get(Calendar.YEAR));
                    args.putInt("month", c.get(Calendar.MONTH));
                    args.putInt("day", c.get(Calendar.DAY_OF_MONTH));
                    newFragment.setArguments(args);
                    newFragment.setMinDate(date1);
                    /**
                     * Set Call back to capture selected date
                     */
                    /*newFragment.setCallBack(onToDate);
                    Log.e("tag", "endDate");
                    newFragment.show(getActivity().getSupportFragmentManager(), "endDate");
                    // newFragment.setMinimumDate(cal1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
            }
        });


        /*lvPricing1LevelGroup.setOnTouchListener(new ListView.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int action = event.getAction();
                switch (action)
                {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });*/

        lvPricing1Equipment.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });




        /*TextWatcher twRetalPriceDays = new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>0) {


                }
                else
                {
                    etRetalPriceDays.setText(" ");

                }
            }
        };

        etRetalPriceDays.addTextChangedListener(twRetalPriceDays);
        */

        super.bindEvents(rootView);
    }
    public void showCalendar(){
        String title = "";
        showCalendarInDialog(title, R.layout.dialog);
        ArrayList<Date> dates = new ArrayList<Date>();
        if(arrayListDates != null){
            dates.addAll(arrayListDates);
        }
        if(arrayListDates != null)
        {
            if(arrayListDates.size() > 0){

                GlobalClass.isFirsttime=false;
                dialogCalendarView.setDecorators(Collections.<CalendarCellDecorator>emptyList());
                dialogCalendarView.init(lastYear.getTime(), nextYear.getTime()) //
                        .inMode(CalendarPickerView.SelectionMode.MULTIPLE) //
                        .withSelectedDates(dates);

                editStart.setText(DateToString(arrayListDates.get(0)));
                editEnd.setText(DateToString(arrayListDates.get(arrayListDates.size()-1)));

            }
            else {
                GlobalClass.isFirsttime=true;
                dialogCalendarView.init(lastYear.getTime(), nextYear.getTime()) //
                        .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
                        .withSelectedDate(new Date());
                GlobalClass.isFirsttime=false;
            }
        }
        else {
            GlobalClass.isFirsttime=true;
            dialogCalendarView.init(lastYear.getTime(), nextYear.getTime()) //
                    .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
                    .withSelectedDate(new Date());
            GlobalClass.isFirsttime=false;
        }

    }
    private void showCalendarInDialog(String title, int layoutResId)
    {
        //AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog, null);
        dialogCalendarView = (CalendarPickerView) dialogView.findViewById(R.id.calendar_view);
        customHandler.postDelayed(updateTimerThread,20);

        //AttributeSet attrs = null;
        //new CalendarPickerView(getActivity(), attrs).setListener(getActivity());

        imgFromdate=(ImageButton)dialogView.findViewById(R.id.imgBtnFromDate);
        imgTodate=(ImageButton)dialogView.findViewById(R.id.imgBtnToDate);

        editStart = (EditText)dialogView.findViewById(R.id.editStart);
        editEnd = (EditText)dialogView.findViewById(R.id.editEnd);
        llStartEndDateContainer = (LinearLayout) dialogView.findViewById(R.id.linearEdittext);

        editStart.setKeyListener(null);
        editEnd.setKeyListener(null);
        checkboxIsWeekend = (CheckBox) dialogView.findViewById(R.id.checkboxWeekend);
        checkboxIsWeekend.setEnabled(false);
        checkBoxDisable = (CheckBox) dialogView.findViewById(R.id.checkboxDisable);
        btnReset = (Button) dialogView.findViewById(R.id.btnreset);

        btnReset.setText(language.getLabelReset());
        checkBoxDisable.setText(language.getLabelDisable());
        checkboxIsWeekend.setText(language.getLableWeekend());


        if(isCheckBoxChecked){
            checkboxIsWeekend.setChecked(true);
        }
        else {
            checkboxIsWeekend.setChecked(false);
        }
        if(isCalanderEnabled)
        {
            checkBoxDisable.setChecked(true);
            llStartEndDateContainer.setBackgroundColor(getResources().getColor(R.color.bg_start_end_date_container));
        }
        else
        {
            checkBoxDisable.setChecked(false);
            llStartEndDateContainer.setBackgroundColor(getResources().getColor(R.color.disabled_color));
        }

        checkBoxDisable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isCalanderEnabled = true;
                    llStartEndDateContainer.setBackgroundColor(getResources().getColor(R.color.bg_start_end_date_container));
                    imgFromdate.setEnabled(true);
                    imgTodate.setEnabled(true);
                    editStart.setEnabled(true);
                    editEnd.setEnabled(true);
                    btnReset.setEnabled(true);
                    checkboxIsWeekend.setEnabled(true);

                }
                else {
                    isCalanderEnabled = false;
                    llStartEndDateContainer.setBackgroundColor(getResources().getColor(R.color.disabled_color));
                    imgFromdate.setEnabled(false);
                    imgTodate.setEnabled(false);
                    editStart.setEnabled(false);
                    editEnd.setEnabled(false);
                    btnReset.setEnabled(false);
                    checkboxIsWeekend.setEnabled(false);
                }
            }
        });
        if(checkBoxDisable.isChecked()){
            isCalanderEnabled = true;
            llStartEndDateContainer.setBackgroundColor(getResources().getColor(R.color.bg_start_end_date_container));
            imgFromdate.setEnabled(true);
            imgTodate.setEnabled(true);
            editStart.setEnabled(true);
            editEnd.setEnabled(true);
            btnReset.setEnabled(true);
            checkboxIsWeekend.setEnabled(true);

        }
        else {
            isCalanderEnabled = false;
            llStartEndDateContainer.setBackgroundColor(getResources().getColor(R.color.disabled_color));
            imgFromdate.setEnabled(false);
            imgTodate.setEnabled(false);
            editStart.setEnabled(false);
            editEnd.setEnabled(false);
            btnReset.setEnabled(false);
            checkboxIsWeekend.setEnabled(false);
        }

        checkboxIsWeekend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isCalanderEnabled && checkboxIsWeekend.isEnabled()) {
                    if (isChecked) {

                        isCheckBoxChecked = true;
                        if (selectedStartdate != null && selectedEnddate.before(selectedStartdate)) {
                            Date temp = selectedStartdate;
                            selectedStartdate = selectedEnddate;
                            selectedEnddate = temp;
                        }
                        if (selectedEnddate == null && selectedStartdate == null) {
                            arrayListDates = dialogCalendarView.getSelectedDates();
                            if (arrayListDates != null && arrayListDates.size() > 1) {
                                Collections.sort(arrayListDates);

                                ArrayList<Date> selectedDates = null;

                                Date date1 = null;
                                Date date2 = null;
                                if (arrayListDates != null && arrayListDates.size() >= 2) {
                                    selectedDates = new ArrayList<Date>(arrayListDates);
                                    date1 = arrayListDates.get(0);
                                    date2 = arrayListDates.get(arrayListDates.size() - 1);
                                }

                                Calendar cal1 = Calendar.getInstance();
                                cal1.setTime(date1);


                                Calendar cal2 = Calendar.getInstance();
                                cal2.setTime(date2);

                                while (!cal1.after(cal2)) {
                                    if (cal1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                                        if (!selectedDates.contains(cal1.getTime()))
                                            selectedDates.add(cal1.getTime());
                                    }
                                    cal1.add(Calendar.DATE, 1);
                                }

                                if (selectedDates != null) {
                                    dialogCalendarView.init(lastYear.getTime(), nextYear.getTime()) //
                                            .inMode(CalendarPickerView.SelectionMode.MULTIPLE) //
                                            .withSelectedDates(selectedDates);
                                }
                            }
                        }
                        new onDateSelectedAsynch().execute(selectedStartdate, selectedEnddate);
                        //onDateSelected(selectedStartdate,selectedEnddate);
                    } else {

                        isCheckBoxChecked = false;
                        if (selectedStartdate != null && selectedEnddate.before(selectedStartdate)) {
                            Date temp = selectedStartdate;
                            selectedStartdate = selectedEnddate;
                            selectedEnddate = temp;
                        }
                        if (selectedEnddate == null && selectedStartdate == null) {
                            arrayListDates = dialogCalendarView.getSelectedDates();
                            if (arrayListDates != null && arrayListDates.size() > 1) {
                                Collections.sort(arrayListDates);

                                ArrayList<Date> selectedDates = null;

                                if (arrayListDates != null && arrayListDates.size() >= 2) {
                                    selectedDates = new ArrayList<Date>(arrayListDates);
                                }

                                Calendar cal1 = Calendar.getInstance();

                                for (int i = 0; i < selectedDates.size(); i++) {
                                    cal1.setTime(selectedDates.get(i));
                                    if (cal1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                                        selectedDates.remove(i);
                                        i--;
                                    }
                                }

                                if (selectedDates != null) {
                                    dialogCalendarView.init(lastYear.getTime(), nextYear.getTime()) //
                                            .inMode(CalendarPickerView.SelectionMode.MULTIPLE) //
                                            .withSelectedDates(selectedDates);
                                }
                            }
                        }
                        new onDateSelectedAsynch().execute(selectedStartdate, selectedEnddate);
                        //onDateSelected(selectedStartdate,selectedEnddate);
                    }

                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(btnReset.isEnabled()) {
                    GlobalClass.isFirsttime = false;
                    selectedStartdate = null;
                    selectedEnddate = null;

                    if (arrayListDates != null) {
                        arrayListDates.clear();
                    }
                    if (selectedDatesStartEnd != null) {
                        selectedDatesStartEnd.clear();
                    }

                    editEnd.setText("");
                    editStart.setText("");
                    txtToDate.setText("");
                    txtFromDate.setText("");

                    GlobalClass.isFirsttime = true;
                    dialogCalendarView.init(lastYear.getTime(), nextYear.getTime()) //
                            .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
                            .withSelectedDate(new Date());
                    GlobalClass.isFirsttime = false;
                }
            }
        });

        imgFromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(1);
            }
        });
        imgTodate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(2);
            }
        });
        editStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showDatePickerDialog(1);
            }
        });
        editEnd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showDatePickerDialog(2);
            }
        });

        alertDialogSelectDate = new AlertDialog.Builder(getActivity()) //
                .setTitle(title)
                .setCancelable(false)
                .setView(dialogView)
                .setNeutralButton("Ok", new DialogInterface.OnClickListener()
                {
                    @Override public void onClick(DialogInterface dialogInterface, int i)
                    {
                        int totalDays = 0;
                        arrayListDates=dialogCalendarView.getSelectedDates();
                        arrayStartEndDate = new ArrayList<Date>();
                        txtFromDate.setText("");
                        txtToDate.setText("");
                        if(arrayListDates.size() > 0){
                            Collections.sort(arrayListDates);
                            arraylistDatesString = new ArrayList<String>();
                            for (int j =0;j<arrayListDates.size();j++){
                                arraylistDatesString.add(DateToString(arrayListDates.get(j)));
                            }
                            arrayStartEndDate = new ArrayList<Date>();
                            arrayStartEndDate.add(arrayListDates.get(0));
                            arrayStartEndDate.add(arrayListDates.get(arrayListDates.size()-1));
                            Log.e(" start end date :  "," arraylist dates : "+arrayStartEndDate.get(0)+ " : "+arrayStartEndDate.get(1));

                            txtFromDate.setText(DateToString(arrayListDates.get(0)));
                            txtToDate.setText(DateToString(arrayListDates.get(arrayListDates.size()-1)));

                            totalDays = arrayListDates.size();
                        }

                        if((txtFromDate.getText().toString().length()>0) && (txtToDate.getText().toString().length()>0))
                        {
                            etRetalPriceDays.setText(String.valueOf(totalDays));
                            for(int j = 0; j < lablesPriceRental.size(); j++)
                            {
                                if(lablesPriceRental.get(j).getUnit() == 2)
                                {
                                    spPricing1PriceRental.setSelection(j);
                                    break;
                                }
                            }
                        }
                        else
                        {
                            etRetalPriceDays.setText("1");
                            for(int k = 0; k < lablesPriceRental.size(); k++)
                            {
                                if(lablesPriceRental.get(k).getUnit() == 2)
                                {
                                    spPricing1PriceRental.setSelection(k);
                                    break;
                                }
                            }
                        }

                        dialogInterface.dismiss();
                        customHandler.removeCallbacks(updateTimerThread);

                    }
                })
                .setPositiveButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        customHandler.removeCallbacks(updateTimerThread);

                    }
                })
                .create();
        alertDialogSelectDate.setOnShowListener(new DialogInterface.OnShowListener()
        {
            @Override public void onShow(DialogInterface dialogInterface) {
                //Log.d(TAG, "onShow: fix the dimens!");
                //dialogView.fixDialogDimens();
            }
        });
        alertDialogSelectDate.setView(dialogView);

        alertDialogSelectDate.show();
    }

    public void showDatePickerDialog(final int pos)
    {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String selectedDate = dayOfMonth+"."+(monthOfYear+1)+"."+year;

                        if(pos == 1){
                            try
                            {
                                selectedStartdate=formatter.parse(selectedDate.toString());
                                // onDateSelected(selectedStartdate,selectedEnddate);

                                if(selectedEnddate != null && selectedEnddate.before(selectedStartdate))
                                {
                                    Date temp = selectedStartdate;
                                    selectedStartdate = selectedEnddate;
                                    selectedEnddate = temp;
                                }
                                new onDateSelectedAsynch().execute(selectedStartdate,selectedEnddate);
                            }
                            catch (Exception e){

                            }
                        }
                        else {
                            try
                            {
                                selectedEnddate=formatter.parse(selectedDate.toString());
                                //onDateSelected(selectedStartdate,selectedEnddate);
                                if(selectedStartdate != null && selectedEnddate.before(selectedStartdate))
                                {
                                    Date temp = selectedStartdate;
                                    selectedStartdate = selectedEnddate;
                                    selectedEnddate = temp;
                                }
                                new onDateSelectedAsynch().execute(selectedStartdate,selectedEnddate);
                            }
                            catch (Exception e){

                            }
                        }


                    }
                }, mYear, mMonth, mDay);
        final Calendar calNext = Calendar.getInstance();
        final Calendar calPrevious = Calendar.getInstance();
        calNext.add(Calendar.YEAR,5);
        calPrevious.add(Calendar.YEAR,-5);
        Date date1 = calNext.getTime();
        Date date2 = calPrevious.getTime();

        datePickerDialog.getDatePicker().setMaxDate(date1.getTime());
        datePickerDialog.getDatePicker().setMinDate(date2.getTime());
        datePickerDialog.show();
    }




    public class onDateSelectedAsynch extends AsyncTask<Date, Void, String>
    {
        List<Date> allDatesBetweenDates;
        Date start,end;
        ProgressDialog progressDialog;
        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(Date... arg0) {

            start = arg0[0];
            end = arg0[1];
            if(start != null && end!= null)
            {
                selectedDatesStartEnd = new ArrayList<Date>();
                selectedDatesStartEnd.add(start);
                selectedDatesStartEnd.add(end);
                Collections.sort(selectedDatesStartEnd);



                getActivity().runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if(checkboxIsWeekend.isChecked()){

                            allDatesBetweenDates = getDatesWithWeekend(DateToString(selectedDatesStartEnd.get(0)),DateToString(selectedDatesStartEnd.get(1)));
                            if(allDatesBetweenDates.size() > 0){
                                selectedDatesStartEnd = new ArrayList<Date>();
                                selectedDatesStartEnd.add(allDatesBetweenDates.get(0));
                                selectedDatesStartEnd.add(allDatesBetweenDates.get(allDatesBetweenDates.size()-1));
                            }

                        }
                        else {
                            allDatesBetweenDates = getDatesWithoutWeekend(start,end);
                            if(allDatesBetweenDates.size() > 0){
                                selectedDatesStartEnd = new ArrayList<Date>();
                                selectedDatesStartEnd.add(allDatesBetweenDates.get(0));
                                selectedDatesStartEnd.add(allDatesBetweenDates.get(allDatesBetweenDates.size()-1));
                            }

                        }
                    }
                });


                GlobalClass.isFirsttime=false;

                getActivity().runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        dialogCalendarView.init(lastYear.getTime(), nextYear.getTime()) //
                                .inMode(CalendarPickerView.SelectionMode.MULTIPLE) //
                                .withSelectedDates(allDatesBetweenDates);

                        editStart.setText(DateToString(selectedDatesStartEnd.get(0)));
                        editEnd.setText(DateToString(selectedDatesStartEnd.get(1)));
                    }
                });



            }
            else if(start != null)
            {
                GlobalClass.isFirsttime=false;
                selectedDatesStartEnd = new ArrayList<Date>();
                selectedDatesStartEnd.add(start);

                getActivity().runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        dialogCalendarView.init(lastYear.getTime(), nextYear.getTime()) //
                                .inMode(CalendarPickerView.SelectionMode.MULTIPLE) //
                                .withSelectedDates(selectedDatesStartEnd);
                        editStart.setText(DateToString(start));
                    }
                });

            }
            else if(end != null)
            {
                GlobalClass.isFirsttime=false;
                selectedDatesStartEnd = new ArrayList<Date>();
                selectedDatesStartEnd.add(end);

                getActivity().runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        dialogCalendarView.init(lastYear.getTime(), nextYear.getTime()) //
                                .inMode(CalendarPickerView.SelectionMode.MULTIPLE) //
                                .withSelectedDates(selectedDatesStartEnd);
                        editEnd.setText(DateToString(end));
                    }
                });

            }
            return null;

        }

        @Override
        protected void onPostExecute(String result) {

            progressDialog.dismiss();

        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),"","Please wait...",false,false);
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

    }

    private static List<Date> getDatesWithWeekend(String dateString1, String dateString2)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("dd.MM.yyyy");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1 .parse(dateString1);
            date2 = df1 .parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2))
        {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }
    private static List<Date> getDates2(String dateString1, String dateString2)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("dd.MM.yyyy");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1 .parse(dateString1);
            date2 = df1 .parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2))
        {
            int day = cal1.get(Calendar.DAY_OF_WEEK);
            if ((day >= Calendar.MONDAY) && (day <= Calendar.FRIDAY))
            {
                dates.add(cal1.getTime());
                cal1.add(Calendar.DATE, 1);
                //Log.e(" yes "," days betweend date withou sat sunday : "+dates);
            }
            else
            {
                cal1.add(Calendar.DATE, 1);
                //Log.e(" No "," days betweend date withou sat sunday : "+da);
            }

            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }
    public ArrayList<Date> getDatesWithoutWeekend(Date date1, Date date2)
    {
        try
        {
            ArrayList<Date> dates = new ArrayList<Date>();
            //Date date1 = formatter.parse(txtFromDate.getText().toString().trim());
            //Date date2 = formatter.parse(txtToDate.getText().toString().trim());
            long diff = date2.getTime() - date1.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            Calendar cal1 = Calendar.getInstance();
            cal1.setFirstDayOfWeek(Calendar.SUNDAY);
            Calendar cal2 = Calendar.getInstance();
            cal2.setFirstDayOfWeek(Calendar.SUNDAY);
            cal1.setTime(date1);
            //cal1.add(Calendar.DAY_OF_MONTH, -1);
            cal2.setTime(date2);
            cal2.add(Calendar.DATE, 1);
            int numberOfDays = 0;
            while (cal1.before(cal2)){
                int month = cal1.get(Calendar.MONTH);
                int day = cal1.get(Calendar.DAY_OF_WEEK);
                if ((day >= Calendar.MONDAY) && (day <= Calendar.FRIDAY))
                {
                    dates.add(cal1.getTime());
                    numberOfDays++;
                    cal1.add(Calendar.DATE, 1);
                    Log.e(" yes "," days betweend date withou sat sunday : "+Calendar.DATE);
                }
                else
                {
                    cal1.add(Calendar.DATE, 1);
                    Log.e(" No "," days betweend date withou sat sunday : "+Calendar.DATE);
                }
            }
            return dates;
        }
        catch (Exception p)
        {
            p.printStackTrace();
            return null;
        }
    }
    private String DateToString(Date date)
    {
        String dateStr="";
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            dateStr = sdf.format(date);
        }
        catch (Exception e){

        }

        return  dateStr;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(asyncTaskLevelGroup != null && asyncTaskLevelGroup.getStatus() == AsyncTask.Status.RUNNING)
            asyncTaskLevelGroup.cancel(true);
        if(asyncTaskEquipment != null && asyncTaskEquipment.getStatus() == AsyncTask.Status.RUNNING)
            asyncTaskEquipment.cancel(true);
    }

    private void setUpLanguage()
    {
        nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 5);
        lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR,-5);

        final TextView txtBranch, txtCustomerName, txtLevelGroup, txtEquipment, txtRental, txtIntegraCalenderForm, txtFrom, txtTo, txtDevice;
        Button btnOk, btnAbort;
        txtFromDate = (TextView) rootView.findViewById(R.id.txtFromDate);
        txtToDate = (TextView) rootView.findViewById(R.id.txtToDate);
        txtCustomerName = (TextView) rootView.findViewById(R.id.txtCustomerName);
        txtBranch = (TextView) rootView.findViewById(R.id.txtBranch);
        txtDevice = (TextView) rootView.findViewById(R.id.txtDevice);
        txtLevelGroup = (TextView) rootView.findViewById(R.id.txtLevelGroup);
        txtEquipment = (TextView) rootView.findViewById(R.id.txtEquipment);
        txtRental = (TextView) rootView.findViewById(R.id.txtRental);
        txtFrom = (TextView) rootView.findViewById(R.id.txtFrom);
        txtTo = (TextView) rootView.findViewById(R.id.txtTo);
        txtBranch.setText(language.getLabelBranch());
        txtCustomerName.setText(language.getLabelContactPerson());
        txtDevice.setText(language.getLabelDevice());
        txtLevelGroup.setText(language.getLabelLevelGroup());
        txtEquipment.setText(language.getLabelEquipment());
        txtRental.setText(language.getLabelRental());
        txtEinheit.setText(language.getLabelUnit());
        txtFrom.setText(language.getLabelFrom());
        txtTo.setText(language.getLabelTo());
        // btnOk.setText(language.getLabelOk());
        // btnAbort.setText(language.getLabelAbort());
        loadSpinnerData();



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);
        menu.findItem(R.id.actionAdd).setVisible(false);
        menu.findItem(R.id.actionEdit).setVisible(false);
        menu.findItem(R.id.actionSearch).setVisible(false);
        menu.findItem(R.id.actionRight).setVisible(false);
        menu.findItem(R.id.actionWrong).setVisible(false);
        menu.findItem(R.id.actionBack).setVisible(false);
        //return true;
        //super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Function to load the spinner data from SQLite database
     */
    private void loadSpinnerData()
    {
        rowBranchItems.clear();
        rowContactPersonItems.clear();
        rowDeviceItems.clear();
        rowPriceRentalItems.clear();
        // database handler
        // Spinner Drop down elements
        lablesBranch = db.getPricing1BranchData();

        if (matecoPriceApplication.isCustomerLoaded(DataHelper.isCustomerLoaded, false))
        {
            //lablesContactPerson = matecoPriceApplication.getLoadedCustomerContactPersons(DataHelper.LoadedCustomerContactPerson, new ArrayList<ContactPersonModel>().toString());
            lablesContactPerson = new ArrayList<>();
            ArrayList<ContactPersonModel> customerContactPersonModel = new ArrayList<ContactPersonModel>();
            customerContactPersonModel = matecoPriceApplication.getLoadedCustomerContactPersons
                    (DataHelper.LoadedCustomerContactPerson, new ContactPersonModel().toString());
            //ArrayList<ContactPersonModel> lablesContactPersonTemp = new ArrayList<>();

            for(int i = 0; i < customerContactPersonModel.size(); i++)
            {
                if(customerContactPersonModel.get(i).getAusgeschieden().equals("Nein"))
                {
                    lablesContactPerson.add(customerContactPersonModel.get(i));
                }
            }
        }
        else
        {
            lablesContactPerson = new ArrayList<ContactPersonModel>();
            showShortToast(language.getMessageLoadCustomerFirst());
        }
        lablesDevice = db.getPricing1DeviceData();
        lablesPriceRental = db.getPricing1PriceRentalData();
        Pricing1BranchDataAdapter branchAdapter = new Pricing1BranchDataAdapter(getActivity(), lablesBranch,language);
        Pricing1ContactPersonDataAdapter contactPersonAdapter = new Pricing1ContactPersonDataAdapter(getActivity(), lablesContactPerson, true, language);
        spPricing1ContactPerson.setAdapter(contactPersonAdapter);
        if(isFromSearch){
            if(lablesContactPerson.size() > 0)
            {
                for (int i=0;i<lablesContactPerson.size();i++){
                    if(lablesContactPerson.get(i).getAnspartner() !=null &&
                            lablesContactPerson.get(i).getAnspartner().equalsIgnoreCase(AnsPartenrId)){
                        spPricing1ContactPerson.setSelection(i+1);
                        break;
                    }
                }

            }
        }


        Pricing1DeviceDataAdapter deviceAdapter = new Pricing1DeviceDataAdapter(getActivity(), lablesDevice, true, language);
        priceRentalAdapter = new Pricing1PriceRentalDataAdapter(getActivity(), lablesPriceRental);
        // Drop down layout style - list view with radio button
        // attaching data adapter to spinner
        spPricing1Brand.setAdapter(branchAdapter);
        String customerBranch;
        customerBranch = matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new ArrayList<LoginPersonModel>().toString()).get(0).getLocation();
        boolean isbranchSeleted = false;
        for(int i = 0; i < lablesBranch.size(); i++)
        {
            if(customerBranch.equals(lablesBranch.get(i).getDesignation()))
            {
                spPricing1Brand.setSelection(i + 1);
                isbranchSeleted = true;
                break;
            }
            else if(customerBranch.contains(lablesBranch.get(i).getDesignation()))
            {
                spPricing1Brand.setSelection(i + 1);
                isbranchSeleted = true;
                break;
            }
        }
        if(!isbranchSeleted)
        {
            //spinnerCustomerDataLegalForm.setSelection(listOfLegalForm.size());
            spPricing1Brand.setSelection(0);
        }

        spPricing1Device.setAdapter(deviceAdapter);
        spPricing1PriceRental.setAdapter(priceRentalAdapter);
        for(int i = 0; i < lablesPriceRental.size(); i++)
        {
            if(lablesPriceRental.get(i).getUnit() == 2)
            {
               spPricing1PriceRental.setSelection(i);
            }
        }
    }

    /**
     * Function to load the spinner data from SQLite database
     */
    private void loadLevelGroupListViewData(int DeviceId)
    {
        rowLevelGroupItems.clear();
        rowEquipmentItems.clear();
        equipmentAdapter.mCheckStates.clear();

        equipmentAdapter.notifyDataSetChanged();
        if(DataHelper.isNetworkAvailable(getActivity()))
        {
            BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
            {
                @Override
                public void OnAsynResult(String result)
                {
                    Log.e("LevelGroup", result);
                    if(result.equals(DataHelper.NetworkError)){
                        showShortToast(language.getMessageNetworkNotAvailable());
                    }else {
                        //equipmentAdapter.notifyDataSetChanged();
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Log.e("Bezeichnung", jsonObject.getString("Bezeichnung"));
                                Log.e("Hoehengruppe", "" + jsonObject.getString("Hoehengruppe"));
                                Pricing1LevelGroupData levelGroup = new Pricing1LevelGroupData();
                                levelGroup.setDesignation(jsonObject.getString("Bezeichnung"));
                                levelGroup.setHeightGroup(jsonObject.getString("Hoehengruppe"));
                                rowLevelGroupItems.add(levelGroup);
                                levelGroupAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            try
            {
                /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.pricing1LevelGroup
                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "&hoehenhauptgruppe=" + DeviceId;*/
                String url = DataHelper.URL_PRICE_HELPER +"pricelevelgroup/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/hoehenhauptgruppe=" + DeviceId;

                Log.e("Url LevelGroup", url);
                asyncTaskLevelGroup = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                if(DeviceId == -1)
                {
                    showShortToast(language.getMessagePleaseSelectDeviceGroupItem());
                    levelGroupAdapter.notifyDataSetChanged();
                }
                else
                {
                    asyncTaskLevelGroup.execute();
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            showLongToast(language.getMessageNetworkNotAvailable());
            if(DeviceId == -1)
            {
                showShortToast(language.getMessagePleaseSelectDeviceGroupItem());
            }
            else
            {
                rowLevelGroupItems.addAll(db.getPricing1LevelGroupData(deviceId));
            }
            levelGroupAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Function to load the spinner data from SQLite database
     */
    private void loadEquipmentListViewData(String designation)
    {
        rowEquipmentItems.clear();
        equipmentAdapter.notifyDataSetChanged();
        equipmentAdapter.mCheckStates.clear();

        if(DataHelper.isNetworkAvailable(getActivity()))
        {
            BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
            {
                @Override
                public void OnAsynResult(String result)
                {
                    Log.e("EquipmentItems", result);
                    if(result.equals(DataHelper.NetworkError)){
                        showShortToast(language.getMessageNetworkNotAvailable());
                    }else {
                        //  rowEquipmentItems.clear();
                        try {
                            JSONObject jsonObjectMain = new JSONObject(result);
                            JSONArray jsonArray = jsonObjectMain.getJSONArray("PriceEquipmentHeightList");
                            JSONArray jsonArrayPriceRentalList = jsonObjectMain.getJSONArray("PriceRentalList");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Log.e("Ausstattung", jsonObject.getString("Ausstattung"));
                                Log.e("Bezeichnung", "" + jsonObject.getString("bezeichnung"));
                                Pricing1EquipmentData equipment = new Pricing1EquipmentData();
                                equipment.setEquipment(jsonObject.getInt("Ausstattung"));
                                equipment.setDesignation(jsonObject.getString("Bezeichnung"));
                                //equipment.setHeightGroup(jsonObject.getString("Hoehengruppe"));
                                equipment.setHeightMainGroup(jsonObject.getInt("Hoehenhauptgruppe"));
                                equipment.setDesignationId(jsonObject.getString("bezeichnung"));
                                rowEquipmentItems.add(equipment);
                                equipmentAdapter.notifyDataSetChanged();
                            }

                            if (jsonArrayPriceRentalList.length() > 0) {
                                lablesPriceRental.clear();
                                for (int i = 0; i < jsonArrayPriceRentalList.length(); i++) {
                                    JSONObject jsonObject = jsonArrayPriceRentalList.getJSONObject(i);

                                    Pricing1PriceRentalData priceRental = new Pricing1PriceRentalData();

                                    priceRental.setDesignation(jsonObject.getString("bezeichnung"));
                                    priceRental.setUnit(Integer.parseInt(jsonObject.getString("einheit")));
                                    lablesPriceRental.add(priceRental);
                                }
                                if (lablesPriceRental.size() > 0) {
                                    priceRentalAdapter = new Pricing1PriceRentalDataAdapter(getActivity(), lablesPriceRental);
                                    spPricing1PriceRental.setAdapter(priceRentalAdapter);

                                    for (int i = 0; i < lablesPriceRental.size(); i++) {
                                        if (lablesPriceRental.get(i).getUnit() == 2) {
                                            spPricing1PriceRental.setSelection(i);
                                        }
                                    }
                                }

                            }
//                    equipmentAdapter = new Pricing1EquipementDataAdapter(getActivity(), rowEquipmentItems);
//                    lvPricing1Equipment.setAdapter(equipmentAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            try
            {
                //String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.pricing1Equipment + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8") + "&hoehengruppe=" + designation;
                String url = DataHelper.URL_PRICE_HELPER+"priceequipmentheight/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/hoehengruppe=" + designation+ "/mandant="+branchId;
                Log.e("Url Equi", url);
                asyncTaskEquipment = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                asyncTaskEquipment.execute();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            lablesPriceRental.clear();
            lablesPriceRental = db.getPricing1PriceRentalData();
            if(lablesPriceRental.size() > 0) {
                priceRentalAdapter = new Pricing1PriceRentalDataAdapter(getActivity(), lablesPriceRental);
                spPricing1PriceRental.setAdapter(priceRentalAdapter);

                for(int i = 0; i < lablesPriceRental.size(); i++)
                {
                    if(lablesPriceRental.get(i).getUnit() == 2)
                    {
                        spPricing1PriceRental.setSelection(i);
                    }
                }
            }
            showLongToast(language.getMessageNetworkNotAvailable());
            rowEquipmentItems.addAll(db.getPricing1EquipmentData(designation));
            equipmentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch (item.getItemId())
        {
            case R.id.actionSettings:
                if(!prefereces.getisPrice().equalsIgnoreCase(""))
                {
                    if(db.getLostsaleCount() > 0 ){
                        showAlertDialg();
                    }

                }
                else{
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.replace(R.id.content_frame, new SettingFragment(),"Setting");
                    //transaction.addToBackStack(SettingFragment.Tag);
                    transaction.addToBackStack("Setting");
                    transaction.commit();
                }


                return true;
            case R.id.actionBack:
                //prefereces2.saveIsfirstTime(true);
                if (getFragmentManager().getBackStackEntryCount() == 0)
                {
                    getActivity().finish();
                }
                else
                {
                    getFragmentManager().popBackStack();
                }
                return true;
            case R.id.actionForward:
                //prefereces2.saveIsfirstTime(true);
                if(deviceId == -1)
                {
                    showShortToast(language.getMessagePleaseSelectDeviceGroupItem());
                }
                else
                {
                    selectedEquipments.clear();
                    String json="";
                    for (int i = 0; i < rowEquipmentItems.size(); i++)
                    {
                        if (equipmentAdapter.mCheckStates.get(i) == true)
                        {
                            Log.e("Checked position...", "" + equipmentAdapter.getItemId(i));
                            selectedEquipments.add(rowEquipmentItems.get(i).getEquipment());
                            Log.e("selectedEquipments ...", "" + selectedEquipments);
                        }
                    }
                    Bundle args = new Bundle();

                    if (kanr.equals(""))
                    {
                        Log.e("kanr ...", kanr);
                        //Toast.makeText(getActivity(), "Please select Customer first", Toast.LENGTH_SHORT).show();
                        showShortToast(language.getMessageNoContactPersons());
                    }
                    else
                    {
                        String Euq = "";
                        if (selectedEquipments.isEmpty())
                        {
                            Euq = "null";
                            Log.e("selectedEquipments", "here...0 " + Euq);
                            json = "null";
                            Log.e("selectedEquipments json", "here...0 " + json);
                        }
                        else
                        {
                            if(selectedEquipments.isEmpty())
                            {
                                json = "null";
                                Log.e("Equipment Json",json+"..null");
                            }
                            else
                            {
                                ArrayList<Pricing1EquipmentInsertData> listOfPriceingEquInsert = new ArrayList<>();

                           /* for (int i = 0; i < selectedEquipments.size(); i++)
                            {
                                Pricing1EquipmentInsertData priceing1Equ = new Pricing1EquipmentInsertData();

                                priceing1Equ.setWarenkorb("");
                                Log.e("setAusstattung",json+"..null");
                                Log.e("setPreis",json+"..null");
                                priceing1Equ.setAusstattung(rowEquipmentItems.get(i).getEquipment());
                                priceing1Equ.setPreis(rowEquipmentItems.get(i).getHeightMainGroup());

                                listOfPriceingEquInsert.add(priceing1Equ);

                            }*/


                                for (int i = 0; i < rowEquipmentItems.size(); i++)
                                {
                                    if (equipmentAdapter.mCheckStates.get(i) == true)
                                    {
                                        Log.e("Checked ", "" + equipmentAdapter.getItemId(i));

                                        Pricing1EquipmentInsertData priceing1Equ = new Pricing1EquipmentInsertData();

                                        priceing1Equ.setWarenkorb("");
                                        Log.e("setAusstattung",rowEquipmentItems.get(i).getEquipment()+"");
                                        Log.e("setPreis",rowEquipmentItems.get(i).getHeightMainGroup()+"");
                                        priceing1Equ.setAusstattung(rowEquipmentItems.get(i).getEquipment());
                                        priceing1Equ.setPreis(rowEquipmentItems.get(i).getHeightMainGroup());

                                        listOfPriceingEquInsert.add(priceing1Equ);

                                    }
                                }

                                json = new Gson().toJson(listOfPriceingEquInsert);
                                Log.e("Equipment Json..",json);
                            }
                            // database handler
                            String s = "[";
                            String q = "]";
                            String w = "";
                            String Ass1 = selectedEquipments.toString().replace(s, w).replace(q, w);
                            Log.e("Ass1 Ass1", Ass1.trim());
                            String Ass = Ass1.replace(", ", ",");
                            Log.e("Ass Ass", Ass.trim());
                            Euq = Ass;
                            Log.e("selectedEquipments", "here " + Euq);
                        }

                        if(LevelGroupDesignation.equals(""))
                        {
                            showShortToast(language.getMessagePleaseSelectDeviceGroupItem());
                        }
                        else if(etRetalPriceDays.getText().toString().trim().equals("0") )
                        {
                            showLongToast(language.getMessageMietdauermussGreterThenZero());
                        }
                        else if(branchId == -1 && branchName == null)
                        {
                            showLongToast(language.getMessagePleaseSelectBranch());
                        }
                        else if(selectedEquipments.size() > 5)
                        {
                            showLongToast(language.getMessageEquipmentSizeMustBeLessThen5());
                        }
                        else
                        {
                            args.putString("kanr", kanr);
                            args.putInt("branchId", branchId);
                            args.putString("branchName", branchName);
                            args.putString("deviceType", LevelGroupDesignation);
                            args.putString("equipmentIds", Euq);
                            args.putString("contactPersonNo", contactPersonNo);
                            args.putString("contactPersonName", contactPerson);
                            args.putInt("rental", unitId);
                            Log.e("EquipmentJson on forward",json);
                            args.putString("EquipmentJson", json);





                            if(etRetalPriceDays.getText().toString().trim().equals(""))
                            {
                                args.putInt("rentalDays", 1);
                            }
                            else
                            {
                                args.putInt("rentalDays", Integer.parseInt(etRetalPriceDays.getText().toString().trim()));
                            }

                            String startDate = "";
                            String endDate = "";
                            if((txtFromDate.getText().toString().length()>0) && (txtToDate.getText().toString().length()>0))
                            {
                                int totalDays = daysCalculate();
                                if(String.valueOf(totalDays).equalsIgnoreCase(etRetalPriceDays.getText().toString()))
                                {
                                    startDate = txtFromDate.getText().toString().trim();
                                    endDate = txtToDate.getText().toString().trim();
                                }
                                else {
                                    startDate = "";
                                    endDate = "";
                                }
                            }
                            String DatesComma="";
                            if(arraylistDatesString != null){
                                if(arraylistDatesString.size() > 0){
                                    DatesComma= TextUtils.join(",",arraylistDatesString);
                                }
                            }


                            args.putString("startDate", startDate);
                            args.putString("endDate", endDate);
                            args.putString("dates_comma", DatesComma);
                            args.putString("fromDate",txtFromDate.getText().toString());
                            args.putString("toDate",txtToDate.getText().toString());

                            Log.e("here", "here " + contactPersonNo);
                            PricingFragment2 ft = new PricingFragment2();
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            ft.setArguments(args);
                            transaction.replace(R.id.content_frame, ft);
                            transaction.addToBackStack("Pricing 1");
                            transaction.commit();
                        }
                    }
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    public int daysCalculate()
    {
        try
        {
            Date date1 = formatter.parse(txtFromDate.getText().toString().trim());
            Date date2 = formatter.parse(txtToDate.getText().toString().trim());
            long diff = date2.getTime() - date1.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            Calendar cal1 = Calendar.getInstance();
            cal1.setFirstDayOfWeek(Calendar.SUNDAY);
            Calendar cal2 = Calendar.getInstance();
            cal2.setFirstDayOfWeek(Calendar.SUNDAY);
            cal1.setTime(date1);
            //cal1.add(Calendar.DAY_OF_MONTH, -1);
            cal2.setTime(date2);
            cal2.add(Calendar.DATE, 1);
            int numberOfDays = 0;
            while (cal1.before(cal2)){
                int month = cal1.get(Calendar.MONTH);
                int day = cal1.get(Calendar.DAY_OF_WEEK);
                if ((day >= Calendar.MONDAY) && (day <= Calendar.FRIDAY))
                {
                    numberOfDays++;
                    cal1.add(Calendar.DATE, 1);
                }
                else
                {
                    cal1.add(Calendar.DATE, 1);
                }
            }
            return numberOfDays;
        }
        catch (ParseException p)
        {
            p.printStackTrace();
            return 0;
        }
    }

    public void showAlertDialg()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

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


    @Override
    public void onDateSelected(Date start) {
        Toast.makeText(getActivity(),"cell clicked : "+start,Toast.LENGTH_SHORT).show();
        int totalDays = 0;
        arrayListDates=dialogCalendarView.getSelectedDates();
        arrayStartEndDate = new ArrayList<Date>();
        editStart.setText("");
        editEnd.setText("");
        if(arrayListDates.size() > 0){
            Collections.sort(arrayListDates);
            arraylistDatesString = new ArrayList<String>();
            for (int j =0;j<arrayListDates.size();j++){
                arraylistDatesString.add(DateToString(arrayListDates.get(j)));
            }
            arrayStartEndDate = new ArrayList<Date>();
            arrayStartEndDate.add(arrayListDates.get(0));
            arrayStartEndDate.add(arrayListDates.get(arrayListDates.size()-1));
            Log.e(" start end date :  "," arraylist dates : "+arrayStartEndDate.get(0)+ " : "+arrayStartEndDate.get(1));

            editStart.setText(DateToString(arrayListDates.get(0)));
            editEnd.setText(DateToString(arrayListDates.get(arrayListDates.size()-1)));

            totalDays = arrayListDates.size();
        }
        else if(arrayListDates.size() < 2 && arrayListDates.size() > 0){
            editStart.setText(DateToString(arrayListDates.get(0)));
        }
    }
    private Runnable updateTimerThread = new Runnable() {

        public void run() {

           // Toast.makeText(getActivity(),"cell clicked : ",Toast.LENGTH_SHORT).show();
            int totalDays = 0;
            arrayListDates=dialogCalendarView.getSelectedDates();
            arrayStartEndDate = new ArrayList<Date>();
            editStart.setText("");
            editEnd.setText("");
            if(arrayListDates.size() > 0){
                Collections.sort(arrayListDates);
                arraylistDatesString = new ArrayList<String>();
                for (int j =0;j<arrayListDates.size();j++){
                    arraylistDatesString.add(DateToString(arrayListDates.get(j)));
                }
                arrayStartEndDate = new ArrayList<Date>();
                arrayStartEndDate.add(arrayListDates.get(0));
                arrayStartEndDate.add(arrayListDates.get(arrayListDates.size()-1));
                Log.e(" start end date :  "," arraylist dates : "+arrayStartEndDate.get(0)+ " : "+arrayStartEndDate.get(1));

                editStart.setText(DateToString(arrayListDates.get(0)));
                editEnd.setText(DateToString(arrayListDates.get(arrayListDates.size()-1)));

                totalDays = arrayListDates.size();
            }
            else if(arrayListDates.size() < 2 && arrayListDates.size() > 0){
                editStart.setText(DateToString(arrayListDates.get(0)));
            }
            customHandler.postDelayed(this, 20);
        }

    };

    @Override
    public void onDetach() {
        super.onDetach();
        if(updateTimerThread != null){
            customHandler.removeCallbacks(updateTimerThread);
        }


    }
}