package de.mateco.integrAMobile.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.CustomNumberPicker;
import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.GlobalMethods;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.SiteInspectionDeviceGroupAdapter;
import de.mateco.integrAMobile.adapter.SiteInspectionDeviceTypeAdapter;
import de.mateco.integrAMobile.adapter.SiteInspectionDeviceTypeListAdapter;
import de.mateco.integrAMobile.adapter.SiteInspectionHeightScaleAdapter;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.Pricing1DeviceData;
import de.mateco.integrAMobile.model.Pricing1LevelGroupData;
import de.mateco.integrAMobile.model.SiteInspectionDeviceDataModel;
import de.mateco.integrAMobile.model.SiteInspectionDeviceTypeModel;

public class SiteInspectionDeviceData2 extends BaseFragment implements NumberPicker.OnValueChangeListener, View.OnClickListener, AdapterView.OnItemSelectedListener
{
    private MatecoPriceApplication application;
    private Language language;
    private CheckBox labelDiesel,labelElectrostatics,labelFourWheel,labelChainRubber,labelWhiteReef,labelPowerLiftSystem,labelPowerGenerators,labelOthers;
    private View rootView;
    private DataBaseHandler db;
    private Integer[] arrDeviceTypeNum = new Integer[99];
    private ArrayList<Pricing1LevelGroupData> rowLevelGroupItems;

    private TextView labelWorkinhHeight1,labelLateralReach1,labelMaxLength1,labelMaxBreadth1,labelMaxHeight1,labelMaxWeight1,labelBasketLoad1,labelBoomLength1;
    private SiteInspectionDeviceDataModel deviceData;

    private SharedPreferences preferences;
    private ArrayList<Pricing1DeviceData> lablesDevice;
    private EditText textOthers;
    private Spinner spnDeviceGroup,spnHeightScale,spnHeightScale1,spnDeviceType,spnDeviceTypeNumber;
    private SiteInspectionHeightScaleAdapter heightScaleAdapter;
    private ArrayList<SiteInspectionDeviceTypeModel> listDeviceType;
    private SiteInspectionDeviceTypeAdapter deviceTypeAdapter;

    private CustomNumberPicker nPWorkingHeight1, nPWorkingHeight2, nPWorkingHeight3;
    private CustomNumberPicker nPLateralReach1, nPLateralReach2, nPLateralReach3, nPLateralReach4;
    private CustomNumberPicker nPMaxLength1, nPMaxLength2, nPMaxLength3, nPMaxLength4;
    private CustomNumberPicker nPMaxBredth1, nPMaxBredth2, nPMaxBredth3, nPMaxBredth4;
    private CustomNumberPicker nPMaxWeight1, nPMaxWeight2, nPMaxWeight3, nPMaxWeight4;
    private CustomNumberPicker nPMaxHeight;
    private CustomNumberPicker nPBasketLoad1, nPBasketLoad2, nPBasketLoad3, nPBasketLoad4;
    private CustomNumberPicker nPBoomLength1, nPBoomLength2, nPBoomLength3, nPBoomLength4;
    private ArrayList<SiteInspectionDeviceTypeModel> allDevices, selectedDevices, remainingDevices;
    private ListView listViewAlternativeDevices;
    private SiteInspectionDeviceTypeListAdapter selectedDeviceTypeListViewAdapter, remainingDeviceTypeListViewAdapter;
    private String gerateType = "", heightMainGroup = "", heightGroup = "", anzahl = "",heightGroup1 = "";
    private SiteInspectionDeviceGroupAdapter deviceAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_site_inspection_device_data2, container, false);
        super.initializeFragment(rootView);
        return rootView;
    }

    @Override
    public void initializeComponents(View rootView)
    {
        super.initializeComponents(rootView);

        application = (MatecoPriceApplication)getActivity().getApplication();
        language = application.getLanguage();
        listViewAlternativeDevices = (ListView)rootView.findViewById(R.id.listViewAlternativeDevices);
        db = new DataBaseHandler(getActivity());
        preferences = getActivity().getSharedPreferences("SiteInspection", Context.MODE_PRIVATE);
        setLanguage();

        deviceData = new SiteInspectionDeviceDataModel();
        lablesDevice = new ArrayList<>();
        rowLevelGroupItems = new ArrayList<>();
        //listOfDeviceData  = new ArrayList<>();

        if(db.getParentDevice(preferences.getInt(DataHelper.SiteInspectionId,0))==1)
        {
            deviceData.setParentId(0);
            deviceData.setBvoId(preferences.getInt(DataHelper.SiteInspectionId, 0));
            deviceData.setPosition(2);
            deviceData.setHauptgeraet("2");
            db.addDevice(deviceData);
            preferences.edit().putInt("ID2", db.getDeviceDataId()).commit();
        }

        ((HomeActivity) getActivity()).getSupportActionBar().setTitle(language.getLabelDeviceData() + " 2");

        labelWorkinhHeight1 = (TextView)rootView.findViewById(R.id.labelWorkinhHeight1);
        labelLateralReach1 = (TextView)rootView.findViewById(R.id.labelLateralReach1);
        labelMaxLength1 = (TextView)rootView.findViewById(R.id.labelMaxLength1);
        labelMaxBreadth1 = (TextView)rootView.findViewById(R.id.labelMaxBreadth1);
        labelMaxWeight1 = (TextView)rootView.findViewById(R.id.labelMaxWeight1);
        labelMaxHeight1 = (TextView)rootView.findViewById(R.id.labelMaxHeight1);
        labelBasketLoad1 = (TextView)rootView.findViewById(R.id.labelBasketLoad1);
        labelBoomLength1 = (TextView)rootView.findViewById(R.id.labelBoomLength1);

        nPWorkingHeight1 = (CustomNumberPicker)rootView.findViewById(R.id.nPWorkingHeight1);
        nPWorkingHeight2 = (CustomNumberPicker)rootView.findViewById(R.id.nPWorkingHeight2);
        nPWorkingHeight3 = (CustomNumberPicker)rootView.findViewById(R.id.nPWorkingHeight3);

        nPLateralReach1 = (CustomNumberPicker)rootView.findViewById(R.id.nPLateralReach1);
        nPLateralReach2 = (CustomNumberPicker)rootView.findViewById(R.id.nPLateralReach2);
        nPLateralReach3 = (CustomNumberPicker)rootView.findViewById(R.id.nPLateralReach3);
        nPLateralReach4 = (CustomNumberPicker)rootView.findViewById(R.id.nPLateralReach4);

        nPMaxLength1 = (CustomNumberPicker)rootView.findViewById(R.id.nPMaxLength1);
        nPMaxLength2 = (CustomNumberPicker)rootView.findViewById(R.id.nPMaxLength2);
        nPMaxLength3 = (CustomNumberPicker)rootView.findViewById(R.id.nPMaxLength3);
        nPMaxLength4 = (CustomNumberPicker)rootView.findViewById(R.id.nPMaxLength4);

        nPMaxBredth1 = (CustomNumberPicker)rootView.findViewById(R.id.nPMaxBredth1);
        nPMaxBredth2 = (CustomNumberPicker)rootView.findViewById(R.id.nPMaxBredth2);
        nPMaxBredth3 = (CustomNumberPicker)rootView.findViewById(R.id.nPMaxBredth3);
        nPMaxBredth4 = (CustomNumberPicker)rootView.findViewById(R.id.nPMaxBredth4);

        nPMaxWeight1 = (CustomNumberPicker)rootView.findViewById(R.id.nPMaxWeight1);
        nPMaxWeight2 = (CustomNumberPicker)rootView.findViewById(R.id.nPMaxWeight2);
        nPMaxWeight3 = (CustomNumberPicker)rootView.findViewById(R.id.nPMaxWeight3);
        nPMaxWeight4 = (CustomNumberPicker)rootView.findViewById(R.id.nPMaxWeight4);

        nPMaxHeight = (CustomNumberPicker)rootView.findViewById(R.id.nPMaxHeight);

        nPBasketLoad1 = (CustomNumberPicker)rootView.findViewById(R.id.nPBasketLoad1);
        nPBasketLoad2 = (CustomNumberPicker)rootView.findViewById(R.id.nPBasketLoad2);
        nPBasketLoad3 = (CustomNumberPicker)rootView.findViewById(R.id.nPBasketLoad3);
        nPBasketLoad4 = (CustomNumberPicker)rootView.findViewById(R.id.nPBasketLoad4);

        nPBoomLength1 = (CustomNumberPicker)rootView.findViewById(R.id.nPBoomLength1);
        nPBoomLength2 = (CustomNumberPicker)rootView.findViewById(R.id.nPBoomLength2);
        nPBoomLength3 = (CustomNumberPicker)rootView.findViewById(R.id.nPBoomLength3);
        nPBoomLength4 = (CustomNumberPicker)rootView.findViewById(R.id.nPBoomLength4);

        nPWorkingHeight1.setOnValueChangedListener(this);
        nPWorkingHeight2.setOnValueChangedListener(this);
        nPWorkingHeight3.setOnValueChangedListener(this);

        nPLateralReach1.setOnValueChangedListener(this);
        nPLateralReach2.setOnValueChangedListener(this);
        nPLateralReach3.setOnValueChangedListener(this);
        nPLateralReach4.setOnValueChangedListener(this);

        nPMaxLength1.setOnValueChangedListener(this);
        nPMaxLength2.setOnValueChangedListener(this);
        nPMaxLength3.setOnValueChangedListener(this);
        nPMaxLength4.setOnValueChangedListener(this);

        nPMaxBredth1.setOnValueChangedListener(this);
        nPMaxBredth2.setOnValueChangedListener(this);
        nPMaxBredth3.setOnValueChangedListener(this);
        nPMaxBredth4.setOnValueChangedListener(this);

        nPMaxWeight1.setOnValueChangedListener(this);
        nPMaxWeight2.setOnValueChangedListener(this);
        nPMaxWeight3.setOnValueChangedListener(this);
        nPMaxWeight4.setOnValueChangedListener(this);

        nPMaxHeight.setOnValueChangedListener(this);

        nPBasketLoad1.setOnValueChangedListener(this);
        nPBasketLoad2.setOnValueChangedListener(this);
        nPBasketLoad3.setOnValueChangedListener(this);
        nPBasketLoad4.setOnValueChangedListener(this);

        nPBoomLength1.setOnValueChangedListener(this);
        nPBoomLength2.setOnValueChangedListener(this);
        nPBoomLength3.setOnValueChangedListener(this);
        nPBoomLength4.setOnValueChangedListener(this);

        lablesDevice = db.getPricing1DeviceData();
        listDeviceType = new ArrayList<>();

        allDevices = new ArrayList<>();
        selectedDevices = new ArrayList<>();
        remainingDevices = new ArrayList<>();

        for (int i = 0; i < 99; i++)
        {
            arrDeviceTypeNum[i] = i + 1;
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, arrDeviceTypeNum);
        spnDeviceTypeNumber.setAdapter(adapter);

        heightScaleAdapter = new SiteInspectionHeightScaleAdapter(getActivity(), rowLevelGroupItems, language);
        spnHeightScale.setAdapter(heightScaleAdapter);
        spnHeightScale1.setAdapter(heightScaleAdapter);

        deviceAdapter = new SiteInspectionDeviceGroupAdapter(getActivity(), lablesDevice, language);
        spnDeviceGroup.setAdapter(deviceAdapter);
        deviceTypeAdapter = new SiteInspectionDeviceTypeAdapter(getActivity(), listDeviceType, language);
        spnDeviceType.setAdapter(deviceTypeAdapter);
        selectedDeviceTypeListViewAdapter = new SiteInspectionDeviceTypeListAdapter(getActivity(), selectedDevices, language);
        listViewAlternativeDevices.setAdapter(selectedDeviceTypeListViewAdapter);
        ImageButton buttonAddAlterNativeDevice = (ImageButton)rootView.findViewById(R.id.buttonAddAlterNativeDevice);
        ImageButton buttonRemoveAlterNativeDevice = (ImageButton)rootView.findViewById(R.id.buttonRemoveAlterNativeDevice);
        buttonAddAlterNativeDevice.setOnClickListener(this);
        buttonRemoveAlterNativeDevice.setOnClickListener(this);
        remainingDeviceTypeListViewAdapter = new SiteInspectionDeviceTypeListAdapter(getActivity(), remainingDevices, language);
        setData();
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
    }

    @Override
    public void bindEvents(View rootView)
    {
        super.bindEvents(rootView);
        listViewAlternativeDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedDeviceTypeListViewAdapter.setSelectedIndex(position);
            }
        });
        spnDeviceGroup.setOnItemSelectedListener(this);
        spnHeightScale.setOnItemSelectedListener(this);
        spnHeightScale1.setOnItemSelectedListener(this);
        spnDeviceType.setOnItemSelectedListener(this);
        spnDeviceTypeNumber.setOnItemSelectedListener(this);
        labelOthers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    textOthers.setEnabled(true);
                else
                    textOthers.setEnabled(false);
            }
        });
    }

    private ArrayList<SiteInspectionDeviceTypeModel> removeDeviceType(ArrayList<SiteInspectionDeviceTypeModel> listOfDeviceType, String id)
    {
        ArrayList<SiteInspectionDeviceTypeModel> tempList = new ArrayList<>();
        tempList.addAll(listOfDeviceType);
        for(int i = 0 ; i < tempList.size(); i++)
        {
            if(tempList.get(i).getGeraeettypID().equals(id))
            {
                tempList.remove(i);
                break;
            }
        }
        return tempList;
    }

    private void setLanguage() {
        TextView labelDeviceGroup,labelHeightScale,labelDeviceType,labelDeviceTypeNumber,labelWorkinhHeight,
                labelLateralReach,labelMaxLength,labelMaxBreadth,labelMaxHeight,labelMaxWeight,labelBasketLoad,labelBoomLength,
                labelAlternativeDevice;


        labelAlternativeDevice = (TextView) rootView.findViewById(R.id.labelAlternativeDevice);
        labelDeviceGroup = (TextView) rootView.findViewById(R.id.labelDeviceGroup);
        labelHeightScale = (TextView) rootView.findViewById(R.id.labelHeightScale);
        labelDeviceType = (TextView)rootView.findViewById(R.id.labelDeviceType);
        labelDeviceTypeNumber = (TextView)rootView.findViewById(R.id.labelDeviceTypeNumber);
        labelWorkinhHeight = (TextView)rootView.findViewById(R.id.labelWorkinhHeight);
        labelLateralReach = (TextView)rootView.findViewById(R.id.labelLateralReach);
        labelMaxLength = (TextView)rootView.findViewById(R.id.labelMaxLength);
        labelMaxBreadth = (TextView)rootView.findViewById(R.id.labelMaxBreadth);
        labelMaxHeight = (TextView)rootView.findViewById(R.id.labelMaxHeight);
        labelMaxWeight = (TextView)rootView.findViewById(R.id.labelMaxWeight);
        labelBasketLoad = (TextView)rootView.findViewById(R.id.labelBasketLoad);
        labelBoomLength = (TextView)rootView.findViewById(R.id.labelBoomLength);

        labelDiesel = (CheckBox)rootView.findViewById(R.id.labelDiesel);
        labelElectrostatics = (CheckBox)rootView.findViewById(R.id.labelElectrostatics);
        labelFourWheel = (CheckBox)rootView.findViewById(R.id.labelFourWheel);
        labelChainRubber = (CheckBox)rootView.findViewById(R.id.labelChainRubber);
        labelWhiteReef = (CheckBox)rootView.findViewById(R.id.labelWhiteReef);
        labelPowerLiftSystem = (CheckBox)rootView.findViewById(R.id.labelPowerLiftSystem);
        labelPowerGenerators = (CheckBox)rootView.findViewById(R.id.labelPowerGenerators);
        labelOthers = (CheckBox)rootView.findViewById(R.id.labelOthers);

        spnDeviceGroup = (Spinner)rootView.findViewById(R.id.spnDeviceGroup);
        spnHeightScale = (Spinner)rootView.findViewById(R.id.spnHeightScale);
        spnHeightScale1 = (Spinner)rootView.findViewById(R.id.spnHeightScale1);
        spnDeviceTypeNumber = (Spinner)rootView.findViewById(R.id.spnDeviceTypeNumber);
        spnDeviceType = (Spinner)rootView.findViewById(R.id.spnDeviceType);

        textOthers = (EditText)rootView.findViewById(R.id.textOthers);


        labelDeviceGroup.setText(language.getLabelDeviceGroup());
        labelHeightScale.setText(language.getLabelHeightScale());
        labelDeviceType.setText(language.getLabelDeviceType());
        labelDeviceTypeNumber.setText(language.getLabelDeviceTypeNumber());
        labelWorkinhHeight.setText(language.getLabelWorkinhHeight());
        labelLateralReach.setText(language.getLabelLateralReach());
        labelMaxLength.setText(language.getLabelMaxLength());
        labelMaxBreadth.setText(language.getLabelMaxBreadth());
        labelMaxHeight.setText(language.getLabelMaxHeight());
        labelMaxWeight.setText(language.getLabelMaxWeight());
        labelBasketLoad.setText(language.getLabelBasketLoad());
        labelBoomLength.setText(language.getLabelBoomLength());

        labelDiesel.setText(language.getLabelDiesel());
        labelElectrostatics.setText(language.getLabelElectrostatics());
        labelFourWheel.setText(language.getLabelFourWheel());
        labelChainRubber.setText(language.getLabelChainRubber());
        labelWhiteReef.setText(language.getLabelWhiteReef());
        labelPowerLiftSystem.setText(language.getLabelPowerLiftSystem());
        labelPowerGenerators.setText(language.getLabelPowerGenerator());
        labelOthers.setText(language.getLabelOther());

        labelAlternativeDevice.setText(language.getLabelAlternateDevice());
        textOthers.setEnabled(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_site_inspection_permits, menu);
        menu.findItem(R.id.actionRight).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        //Bundle args = new Bundle();
        switch (item.getItemId())
        {
            case R.id.actionSettings:
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new SettingFragment(),"Setting");
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
                    saveDeviceData();
                    getFragmentManager().popBackStack();
                }
                return true;
            case R.id.actionForward:
                saveDeviceData();
                if (db.getParentDevice(preferences.getInt(DataHelper.SiteInspectionId, 0)) == 2) {
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.replace(R.id.content_frame, new SiteInspectionOperationalEnvironmentFragment());
                    transaction.addToBackStack("Site Inspection environmental fragment");
                    transaction.commit();
                } else if (db.getParentDevice(preferences.getInt(DataHelper.SiteInspectionId, 0)) >= 3) {
                    SiteInspectionDeviceData3 fragment = new SiteInspectionDeviceData3();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.replace(R.id.content_frame, fragment);
                    transaction.addToBackStack("Site Inspection device data");
                    transaction.commit();
                }
                return true;
            case R.id.actionAdd:
                saveDeviceData();
                if(db.getParentDevice(preferences.getInt(DataHelper.SiteInspectionId, 0))<5)
                {
                    SiteInspectionDeviceData3 fragment = new SiteInspectionDeviceData3();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.replace(R.id.content_frame, fragment);
                    transaction.addToBackStack("Site Inspection device data");
                    transaction.commit();
                }
                else
                {
                }
                return true;
            case R.id.actionSave:
                saveDeviceData();
                db.updateSiteInspectionDate(preferences.getInt(DataHelper.SiteInspectionId, 0));
                db.updateSiteInspectionUpload(preferences.getInt(DataHelper.SiteInspectionId, 0), "", 2);
                preferences.edit().clear().commit();
                showShortToast(language.getMessageBvoStored());
                getActivity().getSupportFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
                return true;
            case R.id.actionWrong:
                DialogInterface.OnClickListener positiveCallback1 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deletePhotosById(preferences.getInt(DataHelper.SiteInspectionId,0));
                        db.deleteDeviceById(preferences.getInt(DataHelper.SiteInspectionId,0));
                        db.deleteSiteInspection(preferences.getInt(DataHelper.SiteInspectionId,0));
                        preferences.edit().clear().commit();
                        getActivity().getSupportFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
                    }
                };
                DialogInterface.OnClickListener negativeCallback1 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                AlertDialog alert1 = showAlert(language.getLabelNote(),language.getMessageLeaveBvo(),language.getLabelYes(), language.getLabelNo(),
                        positiveCallback1, negativeCallback1);
                alert1.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveDeviceData()
    {
        deviceData.setParentId(0);
        if (labelDiesel.isChecked())
            deviceData.setDiesel(1);
        else
            deviceData.setDiesel(0);

        if (labelElectrostatics.isChecked())
            deviceData.setElektro(1);
        else
            deviceData.setElektro(0);

        if (labelFourWheel.isChecked())
            deviceData.setAllrad(1);
        else
            deviceData.setAllrad(0);

        if (labelChainRubber.isChecked())
            deviceData.setKette_Gummi(1);
        else
            deviceData.setKette_Gummi(0);

        if (labelWhiteReef.isChecked())
            deviceData.setReifen_markierungsarm(1);
        else
            deviceData.setReifen_markierungsarm(0);

        if (labelPowerLiftSystem.isChecked())
            deviceData.setPowerlift(1);
        else
            deviceData.setPowerlift(0);

        if (labelPowerGenerators.isChecked())
            deviceData.setStromerzeuger(1);
        else
            deviceData.setStromerzeuger(0);

        if (labelOthers.isChecked())
            deviceData.setSonstiges(1);
        else
            deviceData.setSonstiges(0);

        String numberString = (nPWorkingHeight1.getValue() + "") + (nPWorkingHeight2.getValue() + "") + (nPWorkingHeight3.getValue() + "");
        int number = Integer.parseInt(numberString);
        deviceData.setArbeitshoehe(number + "");

        numberString = (nPLateralReach1.getValue() + "") + (nPLateralReach2.getValue() + "") + "." + (nPLateralReach3.getValue() + "") + (nPLateralReach4.getValue() + "");
        double numberDouble = Double.parseDouble(numberString);
        deviceData.setSeitlicheReichweite(numberDouble + "");
        numberString = (nPMaxLength1.getValue() + "") + (nPMaxLength2.getValue() + "") + "." + (nPMaxLength3.getValue() + "") + (nPMaxLength4.getValue() + "");
        numberDouble = Double.parseDouble(numberString);

        deviceData.setLaenge(numberDouble + "");

        numberString = (nPMaxBredth1.getValue() + "") + (nPMaxBredth2.getValue() + "") + "." + (nPMaxBredth3.getValue() + "") + (nPMaxBredth4.getValue() + "");
        numberDouble = Double.parseDouble(numberString);
        deviceData.setBreite(numberDouble + "");

        numberString = (nPMaxHeight.getValue() + "");
        number = Integer.parseInt(numberString);
        deviceData.setHoehe(number + "");

        numberString = (nPMaxWeight1.getValue() + "") + (nPMaxWeight2.getValue() + "") + (nPMaxWeight3.getValue() + "") + (nPMaxWeight4.getValue() + "");
        number = Integer.parseInt(numberString);
        deviceData.setGewicht(number);

        numberString = (nPBasketLoad1.getValue() + "") + (nPBasketLoad2.getValue() + "") + (nPBasketLoad3.getValue() + "") + (nPBasketLoad4.getValue() + "");
        number = Integer.parseInt(numberString);
        deviceData.setKorbbelastung(number + "");

        numberString = (nPBoomLength1.getValue() + "") + (nPBoomLength2.getValue() + "") + "." + (nPBoomLength3.getValue() + "") + (nPBoomLength4.getValue() + "");
        numberDouble = Double.parseDouble(numberString);
        deviceData.setKorbarmlaenge(numberDouble + "");

        deviceData.setSonstigesText(textOthers.getText().toString());

        deviceData.setPosition(2);
        //deviceData.setPosition(5);
        deviceData.setHauptgeraet("2");

        for(int i = 0; i < listDeviceType.size(); i++)
        {
            if(gerateType.equals(listDeviceType.get(i).getGeraeettypID()))
            {
                deviceData.setGeraetetyp(listDeviceType.get(i).getGeraeettypID());
                break;
            }
        }
        deviceData.setAlternativ(0);
//        if(selectedDevices.size() > 0)
//        {
//            deviceData.setAlternativ(1);
//        }
//        else
//        {
//
//        }
        deviceData.setGeraetetyp(gerateType);
        deviceData.setGeraetegruppe(heightMainGroup);
        deviceData.setHoehengruppe(heightGroup);
        deviceData.setAnzahl(Integer.parseInt(anzahl));
        db.updateDevice(deviceData, preferences.getInt("ID2", 0));
        db.deleteAlterNateDeviceFromParentId(preferences.getInt("ID2", 0));
        for(int i = 0; i < selectedDevices.size(); i++)
        {
            SiteInspectionDeviceDataModel newData = new SiteInspectionDeviceDataModel();
            newData = deviceData;
            //newData.setAlternativ(i + 1);
            newData.setAlternativ(1);
            newData.setBvoId(preferences.getInt(DataHelper.SiteInspectionId, 0));
            newData.setParentId(preferences.getInt("ID2", 0));
            newData.setPosition(2);
            //newData.setPosition(5 + i + 1);
            newData.setHauptgeraet("2");
            newData.setGeraetetyp(selectedDevices.get(i).getGeraeettypID());
            newData.setHoehengruppe(heightGroup1);
            db.addDevice(newData);
        }
    }

    private void setData()
    {
        ArrayList<SiteInspectionDeviceDataModel> listOfDeviceData = db.getDeviceByID(preferences.getInt(DataHelper.SiteInspectionId, 0));
        selectedDevices.clear();
        for (int i = 0; i < listOfDeviceData.size(); i++)
        {
            if (listOfDeviceData.get(i).getHauptgeraet().equals("2") && listOfDeviceData.get(i).getParentId() == 0)
            {
                deviceData = listOfDeviceData.get(i);
            }
        }

        if (deviceData != null)
        {
            preferences.edit().putInt("ID2", deviceData.getId()).commit();
            if (deviceData.getDiesel() == 1)
                labelDiesel.setChecked(true);
            if (deviceData.getElektro() == 1)
                labelElectrostatics.setChecked(true);
            if (deviceData.getAllrad() == 1)
                labelFourWheel.setChecked(true);
            if (deviceData.getKette_Gummi() == 1)
                labelChainRubber.setChecked(true);
            if (deviceData.getReifen_markierungsarm() == 1)
                labelWhiteReef.setChecked(true);
            if (deviceData.getPowerlift() == 1)
                labelPowerLiftSystem.setChecked(true);
            if (deviceData.getStromerzeuger() == 1)
                labelPowerGenerators.setChecked(true);
            if (deviceData.getSonstiges() == 1)
                labelOthers.setChecked(true);
            textOthers.setText(deviceData.getSonstigesText());

            if (!TextUtils.isEmpty(deviceData.getArbeitshoehe()))
            {
                int workingHeight = Integer.parseInt(deviceData.getArbeitshoehe());
                if (workingHeight > 0)
                {
                    int lastDigit = workingHeight % 10;
                    nPWorkingHeight3.setValue(lastDigit);
                    if (workingHeight > 0)
                    {
                        workingHeight = workingHeight / 10;
                        lastDigit = workingHeight % 10;
                        nPWorkingHeight2.setValue(lastDigit);
                        if (workingHeight > 0)
                        {
                            workingHeight = workingHeight / 10;
                            lastDigit = workingHeight % 10;
                            nPWorkingHeight1.setValue(lastDigit);
                        }
                        else
                        {
                            nPWorkingHeight1.setValue(0);
                        }
                    }
                    else
                    {
                        nPWorkingHeight2.setValue(0);
                    }
                }
                else
                {
                    nPWorkingHeight3.setValue(0);
                }
                String numberString = (nPWorkingHeight1.getValue() + "") + (nPWorkingHeight2.getValue() + "") + (nPWorkingHeight3.getValue() + "");
                int number = Integer.parseInt(numberString);
                labelWorkinhHeight1.setText(number + " m");
            }
            if (!TextUtils.isEmpty(deviceData.getSeitlicheReichweite()))
            {
                Double number = Double.parseDouble(deviceData.getSeitlicheReichweite());
                int workingHeight = (int) (number * 100);
                if (workingHeight > 0)
                {
                    int lastDigit = workingHeight % 10;
                    nPLateralReach4.setValue(lastDigit);
                    if (workingHeight > 0)
                    {
                        workingHeight = workingHeight / 10;
                        lastDigit = workingHeight % 10;
                        nPLateralReach3.setValue(lastDigit);
                        if (workingHeight > 0)
                        {
                            workingHeight = workingHeight / 10;
                            lastDigit = workingHeight % 10;
                            nPLateralReach2.setValue(lastDigit);
                            if (workingHeight > 0)
                            {
                                workingHeight = workingHeight / 10;
                                lastDigit = workingHeight % 10;
                                nPLateralReach1.setValue(lastDigit);
                            }
                            else
                            {
                                nPLateralReach1.setValue(0);
                            }
                        }
                        else
                        {
                            nPLateralReach2.setValue(0);
                        }
                    }
                    else
                    {
                        nPLateralReach3.setValue(0);
                    }
                }
                else
                {
                    nPLateralReach4.setValue(0);
                }
                String numberString = (nPLateralReach1.getValue() + "") + (nPLateralReach2.getValue() + "") + "." + (nPLateralReach3.getValue() + "") + (nPLateralReach4.getValue() + "");
                Double numberDouble = Double.parseDouble(numberString);
                labelLateralReach1.setText(DataHelper.getGermanFromEnglish(numberDouble + "") + " m");
            }
            if (!TextUtils.isEmpty(deviceData.getLaenge()))
            {
                Double number = Double.parseDouble(deviceData.getLaenge());
                int workingHeight = (int) (number * 100);
                if (workingHeight > 0)
                {
                    int lastDigit = workingHeight % 10;
                    nPMaxLength4.setValue(lastDigit);
                    if (workingHeight > 0)
                    {
                        workingHeight = workingHeight / 10;
                        lastDigit = workingHeight % 10;
                        nPMaxLength3.setValue(lastDigit);
                        if (workingHeight > 0)
                        {
                            workingHeight = workingHeight / 10;
                            lastDigit = workingHeight % 10;
                            nPMaxLength2.setValue(lastDigit);
                            if (workingHeight > 0)
                            {
                                workingHeight = workingHeight / 10;
                                lastDigit = workingHeight % 10;
                                nPMaxLength1.setValue(lastDigit);
                            }
                            else
                            {
                                nPMaxLength1.setValue(0);
                            }
                        }
                        else
                        {
                            nPMaxLength2.setValue(0);
                        }
                    }
                    else
                    {
                        nPMaxLength3.setValue(0);
                    }
                }
                else
                {
                    nPMaxLength4.setValue(0);
                }
                String numberString = (nPMaxLength1.getValue() + "") + (nPMaxLength2.getValue() + "") + "." + (nPMaxLength3.getValue() + "") + (nPMaxLength4.getValue() + "");
                Double numberDouble = Double.parseDouble(numberString);
                labelMaxLength1.setText(DataHelper.getGermanFromEnglish(numberDouble + "") + " m");
            }
            if (!TextUtils.isEmpty(deviceData.getBreite()))
            {
                Double number = Double.parseDouble(deviceData.getBreite());
                int workingHeight = (int) (number * 100);
                if (workingHeight > 0)
                {
                    int lastDigit = workingHeight % 10;
                    nPMaxBredth4.setValue(lastDigit);
                    if (workingHeight > 0)
                    {
                        workingHeight = workingHeight / 10;
                        lastDigit = workingHeight % 10;
                        nPMaxBredth3.setValue(lastDigit);
                        if (workingHeight > 0)
                        {
                            workingHeight = workingHeight / 10;
                            lastDigit = workingHeight % 10;
                            nPMaxBredth2.setValue(lastDigit);
                            if (workingHeight > 0)
                            {
                                workingHeight = workingHeight / 10;
                                lastDigit = workingHeight % 10;
                                nPMaxBredth1.setValue(lastDigit);
                            }
                            else
                            {
                                nPMaxBredth1.setValue(0);
                            }
                        }
                        else
                        {
                            nPMaxBredth2.setValue(0);
                        }
                    }
                    else
                    {
                        nPMaxBredth3.setValue(0);
                    }
                }
                else
                {
                    nPMaxBredth4.setValue(0);
                }
                String numberString = (nPMaxBredth1.getValue() + "") + (nPMaxBredth2.getValue() + "") + "." + (nPMaxBredth3.getValue() + "") + (nPMaxBredth4.getValue() + "");
                Double numberDouble = Double.parseDouble(numberString);
                labelMaxBreadth1.setText(DataHelper.getGermanFromEnglish(numberDouble + "") + " m");
            }
            if (!TextUtils.isEmpty(deviceData.getHoehe()))
            {
                int number = Integer.parseInt(deviceData.getHoehe());
                nPMaxHeight.setValue(number);
                labelMaxHeight1.setText(number + " m");
            }
            if (deviceData.getGewicht() != 0)
            {
                int workingHeight = deviceData.getGewicht();
                if (workingHeight > 0)
                {
                    int lastDigit = workingHeight % 10;
                    nPMaxWeight4.setValue(lastDigit);
                    if (workingHeight > 0)
                    {
                        workingHeight = workingHeight / 10;
                        lastDigit = workingHeight % 10;
                        nPMaxWeight3.setValue(lastDigit);
                        if (workingHeight > 0)
                        {
                            workingHeight = workingHeight / 10;
                            lastDigit = workingHeight % 10;
                            nPMaxWeight2.setValue(lastDigit);
                            if (workingHeight > 0)
                            {
                                workingHeight = workingHeight / 10;
                                lastDigit = workingHeight % 50;
                                nPMaxWeight1.setValue(lastDigit);
                            }
                            else
                            {
                                nPMaxWeight1.setValue(0);
                            }
                        }
                        else
                        {
                            nPMaxWeight2.setValue(0);
                        }
                    }
                    else
                    {
                        nPMaxWeight3.setValue(0);
                    }
                }
                else
                {
                    nPMaxWeight4.setValue(0);
                }
                String numberString = (nPMaxWeight1.getValue() + "") + (nPMaxWeight2.getValue() + "") + (nPMaxWeight3.getValue() + "") + (nPMaxWeight4.getValue() + "");
                int number = Integer.parseInt(numberString);
                labelMaxWeight1.setText(number + " kg");
            }
            if (!TextUtils.isEmpty(deviceData.getKorbbelastung()))
            {
                int workingHeight = Integer.parseInt(deviceData.getKorbbelastung());
                if (workingHeight > 0)
                {
                    int lastDigit = workingHeight % 10;
                    nPBasketLoad4.setValue(lastDigit);
                    if (workingHeight > 0)
                    {
                        workingHeight = workingHeight / 10;
                        lastDigit = workingHeight % 10;
                        nPBasketLoad3.setValue(lastDigit);
                        if (workingHeight > 0)
                        {
                            workingHeight = workingHeight / 10;
                            lastDigit = workingHeight % 10;
                            nPBasketLoad2.setValue(lastDigit);
                            if (workingHeight > 0)
                            {
                                workingHeight = workingHeight / 10;
                                lastDigit = workingHeight % 10;
                                nPBasketLoad1.setValue(lastDigit);
                            }
                            else
                            {
                                nPBasketLoad1.setValue(0);
                            }
                        }
                        else
                        {
                            nPBasketLoad2.setValue(0);
                        }
                    }
                    else
                    {
                        nPBasketLoad3.setValue(0);
                    }
                }
                else
                {
                    nPBasketLoad4.setValue(0);
                }
                String numberString = (nPBasketLoad1.getValue() + "") + (nPBasketLoad2.getValue() + "") + (nPBasketLoad3.getValue() + "") + (nPBasketLoad4.getValue() + "");
                int number = Integer.parseInt(numberString);
                labelBasketLoad1.setText(number + " kg");
            }
            if (!TextUtils.isEmpty(deviceData.getKorbarmlaenge()))
            {
                Double number = Double.parseDouble(deviceData.getKorbarmlaenge());
                int workingHeight = (int) (number * 100);
                if (workingHeight > 0)
                {
                    int lastDigit = workingHeight % 10;
                    nPBoomLength4.setValue(lastDigit);
                    if (workingHeight > 0)
                    {
                        workingHeight = workingHeight / 10;
                        lastDigit = workingHeight % 10;
                        nPBoomLength3.setValue(lastDigit);
                        if (workingHeight > 0)
                        {
                            workingHeight = workingHeight / 10;
                            lastDigit = workingHeight % 10;
                            nPBoomLength2.setValue(lastDigit);
                            if (workingHeight > 0)
                            {
                                workingHeight = workingHeight / 10;
                                lastDigit = workingHeight % 10;
                                nPBoomLength1.setValue(lastDigit);
                            }
                            else
                            {
                                nPBoomLength1.setValue(0);
                            }
                        }
                        else
                        {
                            nPBoomLength2.setValue(0);
                        }
                    }
                    else
                    {
                        nPBoomLength3.setValue(0);
                    }
                }
                else
                {
                    nPBoomLength4.setValue(0);
                }
                String numberString = (nPBoomLength1.getValue() + "") + (nPBoomLength2.getValue() + "") + "." + (nPBoomLength3.getValue() + "") + (nPBoomLength4.getValue() + "");
                Double numberDouble = Double.parseDouble(numberString);
                labelBoomLength1.setText(DataHelper.getGermanFromEnglish(numberDouble + "") + " m");
            }
            spnDeviceGroup.setOnItemSelectedListener(null);
            spnHeightScale.setOnItemSelectedListener(null);
            spnDeviceType.setOnItemSelectedListener(null);
            heightMainGroup = deviceData.getGeraetegruppe();
            heightGroup = deviceData.getHoehengruppe();

            for (int i = 0; i < arrDeviceTypeNum.length; i++)
            {
                if (deviceData.getAnzahl() == arrDeviceTypeNum[i])
                {
                    spnDeviceTypeNumber.setSelection(i);
                    anzahl = arrDeviceTypeNum[i]+"";
                }
            }

            rowLevelGroupItems.clear();
            if((heightMainGroup == null || heightMainGroup.equals("")) && lablesDevice.size() > 0)
            {
                heightMainGroup = lablesDevice.get(0).getHeight_main_group()+"";
            }
            if(heightGroup == null)
                heightGroup = "";
            gerateType = deviceData.getGeraetetyp();
            if(gerateType == null)
                gerateType = "";
            if (!TextUtils.isEmpty(deviceData.getGeraetegruppe()))
            {
                for (int i = 0; i < lablesDevice.size(); i++)
                {
                    if (heightMainGroup.equals(String.valueOf(lablesDevice.get(i).getHeight_main_group())))
                    {
                        spnDeviceGroup.setSelection(i, false);
                    }
                }
            }
            if(GlobalMethods.checkForNotNull(heightMainGroup))
            {
                rowLevelGroupItems.addAll(db.getPricing1LevelGroupData(Integer.parseInt(heightMainGroup)));
            }
            else
            {
                rowLevelGroupItems = new ArrayList<>();
            }
            heightScaleAdapter.notifyDataSetChanged();
            for (int i = 0; i < rowLevelGroupItems.size(); i++)
            {
                if (heightGroup.equals(rowLevelGroupItems.get(i).getHeightGroup()))
                {
                    spnHeightScale.setSelection(i, false);
                }
            }
            listDeviceType.clear();
            listDeviceType.addAll(db.getDeviceType(deviceData.getHoehengruppe()));
            deviceTypeAdapter.notifyDataSetChanged();
            for (int i = 0; i < listDeviceType.size(); i++)
            {
                if (gerateType.equals(listDeviceType.get(i).getGeraeettypID()))
                {
                    spnDeviceType.setSelection(i + 1, false);
                }
            }
            spnDeviceType.setOnItemSelectedListener(this);
            spnHeightScale.setOnItemSelectedListener(this);
            spnDeviceGroup.setOnItemSelectedListener(this);
            for (int i = 1; i < listOfDeviceData.size(); i++)
            {
                if(listOfDeviceData.get(i).getPosition()==2 && listOfDeviceData.get(i).getParentId()==deviceData.getId())
                {
                    heightGroup1 = listOfDeviceData.get(i).getHoehengruppe();
                }
            }
            ArrayList<SiteInspectionDeviceTypeModel> tempList = new ArrayList<>();
            tempList.addAll(db.getDeviceType(heightGroup1));
            allDevices.addAll(tempList);
            for (int i = 1; i < listOfDeviceData.size(); i++)
            {
                for(int j = 0; j < tempList.size(); j++)
                {
                    if(listOfDeviceData.get(i).getParentId()== deviceData.getId()
                            && listOfDeviceData.get(i).getGeraetetyp().equals(tempList.get(j).getGeraeettypID()))
                    {
                        selectedDevices.add(tempList.get(j));
                        break;
                    }
                }
            }
            for (int i = 0; i < rowLevelGroupItems.size(); i++)
            {
                if (heightGroup1.equals(rowLevelGroupItems.get(i).getHeightGroup()))
                {
                    spnHeightScale1.setSelection(i, false);
                }
            }
            selectedDeviceTypeListViewAdapter.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(listViewAlternativeDevices);
        }
    }


    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        String numberString = "";
        int number = 0;
        double numberDouble = 0.0;
        switch (picker.getId())
        {
            case R.id.nPWorkingHeight1:
                numberString = (nPWorkingHeight1.getValue() + "") + (nPWorkingHeight2.getValue() + "") + (nPWorkingHeight3.getValue()+ "");
                number = Integer.parseInt(numberString);
                labelWorkinhHeight1.setText( number+ " m");
                break;
            case R.id.nPWorkingHeight2:
                numberString = (nPWorkingHeight1.getValue() + "") + (nPWorkingHeight2.getValue() + "") + (nPWorkingHeight3.getValue()+ "");
                number = Integer.parseInt(numberString);
                labelWorkinhHeight1.setText( number+ " m");
                break;
            case R.id.nPWorkingHeight3:
                numberString = (nPWorkingHeight1.getValue() + "") + (nPWorkingHeight2.getValue() + "") + (nPWorkingHeight3.getValue()+ "");
                number = Integer.parseInt(numberString);
                labelWorkinhHeight1.setText( number+ " m");
                break;

            case R.id.nPLateralReach1:
                numberString = (nPLateralReach1.getValue() + "") + (nPLateralReach2.getValue() + "") + "." + (nPLateralReach3.getValue() + "") + (nPLateralReach4.getValue() + "") ;
                numberDouble =  Double.parseDouble(numberString);
                labelLateralReach1.setText(DataHelper.getGermanFromEnglish(numberDouble+"")+ " m");
                break;
            case R.id.nPLateralReach2:
                numberString = (nPLateralReach1.getValue() + "") + (nPLateralReach2.getValue() + "") + "." + (nPLateralReach3.getValue() + "") + (nPLateralReach4.getValue() + "") ;
                numberDouble =  Double.parseDouble(numberString);
                labelLateralReach1.setText(DataHelper.getGermanFromEnglish(numberDouble+"")+ " m");
                break;
            case R.id.nPLateralReach3:
                numberString = (nPLateralReach1.getValue() + "") + (nPLateralReach2.getValue() + "") + "." + (nPLateralReach3.getValue() + "") + (nPLateralReach4.getValue() + "") ;
                numberDouble =  Double.parseDouble(numberString);
                labelLateralReach1.setText(DataHelper.getGermanFromEnglish(numberDouble+"")+ " m");
                break;
            case R.id.nPLateralReach4:
                numberString = (nPLateralReach1.getValue() + "") + (nPLateralReach2.getValue() + "") + "." + (nPLateralReach3.getValue() + "") + (nPLateralReach4.getValue() + "") ;
                numberDouble =  Double.parseDouble(numberString);
                labelLateralReach1.setText(DataHelper.getGermanFromEnglish(numberDouble+"")+ " m");
                break;

            case R.id.nPMaxLength1:
                numberString = (nPMaxLength1.getValue() + "") + (nPMaxLength2.getValue() + "") + "." + (nPMaxLength3.getValue() + "") + (nPMaxLength4.getValue() + "");
                numberDouble =  Double.parseDouble(numberString);
                labelMaxLength1.setText(DataHelper.getGermanFromEnglish(numberDouble+"")+ " m");
                break;
            case R.id.nPMaxLength2:
                numberString = (nPMaxLength1.getValue() + "") + (nPMaxLength2.getValue() + "") + "." + (nPMaxLength3.getValue() + "") + (nPMaxLength4.getValue() + "");
                numberDouble =  Double.parseDouble(numberString);
                labelMaxLength1.setText(DataHelper.getGermanFromEnglish(numberDouble+"")+ " m");
                break;
            case R.id.nPMaxLength3:
                numberString = (nPMaxLength1.getValue() + "") + (nPMaxLength2.getValue() + "") + "." + (nPMaxLength3.getValue() + "") + (nPMaxLength4.getValue() + "");
                numberDouble =  Double.parseDouble(numberString);
                labelMaxLength1.setText(DataHelper.getGermanFromEnglish(numberDouble+"")+ " m");
                break;
            case R.id.nPMaxLength4:
                numberString = (nPMaxLength1.getValue() + "") + (nPMaxLength2.getValue() + "") + "." + (nPMaxLength3.getValue() + "") + (nPMaxLength4.getValue() + "");
                numberDouble =  Double.parseDouble(numberString);
                labelMaxLength1.setText(DataHelper.getGermanFromEnglish(numberDouble+"")+ " m");
                break;

            case R.id.nPMaxBredth1:
                numberString = (nPMaxBredth1.getValue() + "") + (nPMaxBredth2.getValue() + "") + "." + (nPMaxBredth3.getValue() + "") + (nPMaxBredth4.getValue() + "");
                numberDouble =  Double.parseDouble(numberString);
                labelMaxBreadth1.setText(DataHelper.getGermanFromEnglish(numberDouble+"") + " m");
                break;
            case R.id.nPMaxBredth2:
                numberString = (nPMaxBredth1.getValue() + "") + (nPMaxBredth2.getValue() + "") + "." + (nPMaxBredth3.getValue() + "") + (nPMaxBredth4.getValue() + "");
                numberDouble =  Double.parseDouble(numberString);
                labelMaxBreadth1.setText(DataHelper.getGermanFromEnglish(numberDouble+"") + " m");
                break;
            case R.id.nPMaxBredth3:
                numberString = (nPMaxBredth1.getValue() + "") + (nPMaxBredth2.getValue() + "") + "." + (nPMaxBredth3.getValue() + "") + (nPMaxBredth4.getValue() + "");
                numberDouble =  Double.parseDouble(numberString);
                labelMaxBreadth1.setText(DataHelper.getGermanFromEnglish(numberDouble+"") + " m");
                break;
            case R.id.nPMaxBredth4:
                numberString = (nPMaxBredth1.getValue() + "") + (nPMaxBredth2.getValue() + "") + "." + (nPMaxBredth3.getValue() + "") + (nPMaxBredth4.getValue() + "");
                numberDouble =  Double.parseDouble(numberString);
                labelMaxBreadth1.setText(DataHelper.getGermanFromEnglish(numberDouble +"")+ " m");
                break;

            case R.id.nPMaxWeight1:
                numberString = (nPMaxWeight1.getValue() + "") + (nPMaxWeight2.getValue() + "") + (nPMaxWeight3.getValue() + "") + (nPMaxWeight4.getValue() + "");
                number =  Integer.parseInt(numberString);
                labelMaxWeight1.setText(number + " kg");
                break;
            case R.id.nPMaxWeight2:
                numberString = (nPMaxWeight1.getValue() + "") + (nPMaxWeight2.getValue() + "") + (nPMaxWeight3.getValue() + "") + (nPMaxWeight4.getValue() + "");
                number =  Integer.parseInt(numberString);
                labelMaxWeight1.setText(number + " kg");
                break;
            case R.id.nPMaxWeight3:
                numberString = (nPMaxWeight1.getValue() + "") + (nPMaxWeight2.getValue() + "") + (nPMaxWeight3.getValue() + "") + (nPMaxWeight4.getValue() + "");
                number =  Integer.parseInt(numberString);
                labelMaxWeight1.setText(number + " kg");
                break;
            case R.id.nPMaxWeight4:
                numberString = (nPMaxWeight1.getValue() + "") + (nPMaxWeight2.getValue() + "") + (nPMaxWeight3.getValue() + "") + (nPMaxWeight4.getValue() + "");
                number =  Integer.parseInt(numberString);
                labelMaxWeight1.setText(number + " kg");
                break;

            case R.id.nPMaxHeight:
                numberString = (nPMaxHeight.getValue() + "");
                number = Integer.parseInt(numberString);
                labelMaxHeight1.setText(number + " m");
                break;

            case R.id.nPBasketLoad1:
                numberString = (nPBasketLoad1.getValue() + "") + (nPBasketLoad2.getValue() + "") + (nPBasketLoad3.getValue() + "") + (nPBasketLoad4.getValue() + "");
                number = Integer.parseInt(numberString);
                labelBasketLoad1.setText(number + " kg");
                break;
            case R.id.nPBasketLoad2:
                numberString = (nPBasketLoad1.getValue() + "") + (nPBasketLoad2.getValue() + "") + (nPBasketLoad3.getValue() + "") + (nPBasketLoad4.getValue() + "");
                number = Integer.parseInt(numberString);
                labelBasketLoad1.setText(number + " kg");
                break;
            case R.id.nPBasketLoad3:
                numberString = (nPBasketLoad1.getValue() + "") + (nPBasketLoad2.getValue() + "") + (nPBasketLoad3.getValue() + "") + (nPBasketLoad4.getValue() + "");
                number = Integer.parseInt(numberString);
                labelBasketLoad1.setText(number + " kg");
                break;
            case R.id.nPBasketLoad4:
                numberString = (nPBasketLoad1.getValue() + "") + (nPBasketLoad2.getValue() + "") + (nPBasketLoad3.getValue() + "") + (nPBasketLoad4.getValue() + "");
                number = Integer.parseInt(numberString);
                labelBasketLoad1.setText(number + " kg");
                break;

            case R.id.nPBoomLength1:
                numberString = (nPBoomLength1.getValue() + "") + (nPBoomLength2.getValue() + "") + "." + (nPBoomLength3.getValue() + "") + (nPBoomLength4.getValue() + "");
                numberDouble =  Double.parseDouble(numberString);
                labelBoomLength1.setText(DataHelper.getGermanFromEnglish(numberDouble +"")+ " m");
                break;
            case R.id.nPBoomLength2:
                numberString = (nPBoomLength1.getValue() + "") + (nPBoomLength2.getValue() + "") + "." + (nPBoomLength3.getValue() + "") + (nPBoomLength4.getValue() + "");
                numberDouble =  Double.parseDouble(numberString);
                labelBoomLength1.setText(DataHelper.getGermanFromEnglish(numberDouble +"")+ " m");
                break;
            case R.id.nPBoomLength3:
                numberString = (nPBoomLength1.getValue() + "") + (nPBoomLength2.getValue() + "") + "." + (nPBoomLength3.getValue() + "") + (nPBoomLength4.getValue() + "");
                numberDouble =  Double.parseDouble(numberString);
                labelBoomLength1.setText(DataHelper.getGermanFromEnglish(numberDouble +"")+ " m");
                break;
            case R.id.nPBoomLength4:
                numberString = (nPBoomLength1.getValue() + "") + (nPBoomLength2.getValue() + "") + "." + (nPBoomLength3.getValue() + "") + (nPBoomLength4.getValue() + "");
                numberDouble =  Double.parseDouble(numberString);
                labelBoomLength1.setText(DataHelper.getGermanFromEnglish(numberDouble +"")+ " m");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.buttonAddAlterNativeDevice:
                showAddAlternateDeviceDialog();
                break;
            case R.id.buttonRemoveAlterNativeDevice:
                if(selectedDeviceTypeListViewAdapter.selectedIndex != -1)
                {
                    remainingDevices.add(selectedDevices.get(selectedDeviceTypeListViewAdapter.selectedIndex));
                    selectedDevices.remove(selectedDeviceTypeListViewAdapter.selectedIndex);
                    selectedDeviceTypeListViewAdapter.setSelectedIndex(-1);
                    remainingDeviceTypeListViewAdapter.notifyDataSetChanged();
                }
                else
                {
                    showShortToast(language.getMessageSelectItemToRemove());
                }
                break;
        }
    }

    private void showAddAlternateDeviceDialog()
    {
        final Dialog dialog = showCustomDialog(R.layout.dialog_add_alternative_device, language.getMessageSelectDeviceType());
        remainingDevices.clear();
        //remainingDevices.addAll(removeAlternativeDevice(allDevices, selectedDevices));

        remainingDevices.addAll(removeAlternativeDevice(db.getDeviceType(heightGroup1), selectedDevices));
        final ListView listViewRemainingAlterNativeDevice = (ListView)dialog.findViewById(R.id.listViewRemainingAlterNativeDevice);
        listViewRemainingAlterNativeDevice.setAdapter(remainingDeviceTypeListViewAdapter);
        remainingDeviceTypeListViewAdapter.notifyDataSetChanged();
        Button buttonDialogAddAlterNativeDevices, buttonDialogAddAlternativeDevicesCancel;

        buttonDialogAddAlterNativeDevices = (Button)dialog.findViewById(R.id.buttonDialogAddAlterNativeDevices);
        buttonDialogAddAlternativeDevicesCancel = (Button)dialog.findViewById(R.id.buttonDialogAddAlternativeDevicesCancel);
        buttonDialogAddAlterNativeDevices.setText(language.getLabelAdd());
        buttonDialogAddAlternativeDevicesCancel.setText(language.getLabelCancel());
        listViewRemainingAlterNativeDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //view.setSelected(true);
                remainingDeviceTypeListViewAdapter.setSelectedIndex(position);
            }
        });
        buttonDialogAddAlterNativeDevices.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(remainingDeviceTypeListViewAdapter.selectedIndex != -1)
                {
                    selectedDevices.add(remainingDevices.get(remainingDeviceTypeListViewAdapter.selectedIndex));
                    remainingDevices.remove(remainingDeviceTypeListViewAdapter.selectedIndex);
                    remainingDeviceTypeListViewAdapter.notifyDataSetChanged();
                    selectedDeviceTypeListViewAdapter.notifyDataSetChanged();
                    setListViewHeightBasedOnChildren(listViewAlternativeDevices);
                    remainingDeviceTypeListViewAdapter.setSelectedIndex(-1);
                    dialog.dismiss();
                }
                else
                {
                    showShortToast(language.getMessageSelectItemToRemove());
                }
            }
        });

        buttonDialogAddAlternativeDevicesCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showShortToast(language.getLabelCancel());
                dialog.dismiss();
            }
        });
        /*if(gerateType.equals(""))
        {
            showShortToast(language.getMessagePleaseSelectMainDevice());
        }*/
        if(selectedDevices.size() < 3)
        {
            dialog.show();
        }
        else if(selectedDevices.size() >= 3)
        {
            showLongToast(language.getMessageCantEnterMoreThen3devices());
        }
        else
        {
            //showLongToast("There are no Employee");
            showLongToast(language.getMessagePleaseSelectGerateType());
        }
    }

    private ArrayList<SiteInspectionDeviceTypeModel> removeAlternativeDevice(ArrayList<SiteInspectionDeviceTypeModel> allDevices, ArrayList<SiteInspectionDeviceTypeModel> selectedDevices)
    {
        ArrayList<SiteInspectionDeviceTypeModel> tempList = new ArrayList<>();
        //listOfRemainingEmployee.clear();
        tempList.addAll(allDevices);
        for(int i = 0; i < selectedDevices.size(); i++)
        {
            for(int j = 0; j < tempList.size(); j++)
            {
                if(tempList.get(j).getGeraeettypID().equals(selectedDevices.get(i).getGeraeettypID()))
                {
                    tempList.remove(j);
                }
            }
        }
        return tempList;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId())
        {
            case R.id.spnDeviceGroup:
                rowLevelGroupItems.clear();
                rowLevelGroupItems.addAll(db.getPricing1LevelGroupData(lablesDevice.get(position).getHeight_main_group()));
                heightScaleAdapter.notifyDataSetChanged();
                heightMainGroup = lablesDevice.get(position).getHeight_main_group() + "";
                if(rowLevelGroupItems.size() > 0)
                {
                    spnHeightScale.setSelection(0);
                }
                listDeviceType.clear();
                if(rowLevelGroupItems.size() > 0){
                    listDeviceType.addAll(db.getDeviceType(rowLevelGroupItems.get(0).getHeightGroup()));
                }
                deviceTypeAdapter.notifyDataSetChanged();
                heightGroup = rowLevelGroupItems.get(0).getHeightGroup();
                if(listDeviceType.size() > 0)
                    spnDeviceType.setSelection(0);
                heightGroup1 = rowLevelGroupItems.get(0).getHeightGroup();
                selectedDevices.clear();
                selectedDeviceTypeListViewAdapter.notifyDataSetChanged();
                if(rowLevelGroupItems.size() > 0){
                    spnHeightScale1.setSelection(0);
                }

                break;
            case R.id.spnHeightScale:
                listDeviceType.clear();
                listDeviceType.addAll(db.getDeviceType(rowLevelGroupItems.get(position).getHeightGroup()));
                deviceTypeAdapter.notifyDataSetChanged();
                heightGroup = rowLevelGroupItems.get(position).getHeightGroup();
                if(listDeviceType.size() > 0)
                    spnDeviceType.setSelection(0);
                break;
            case R.id.spnHeightScale1:
                heightGroup1 = rowLevelGroupItems.get(position).getHeightGroup();
                selectedDevices.clear();
                selectedDeviceTypeListViewAdapter.notifyDataSetChanged();
                break;
            case R.id.spnDeviceType:
                if (position == 0)
                {
                    gerateType = "";
                }
                else
                {
                    gerateType = listDeviceType.get(position - 1).getGeraeettypID();
                }
                break;
            case R.id.spnDeviceTypeNumber:
                anzahl = arrDeviceTypeNum[position]+"";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

