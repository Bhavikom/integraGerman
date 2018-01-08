package de.mateco.integrAMobile.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.CustomNumberPicker;
import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.SiteInspectionDeviceGroupAdapter;
import de.mateco.integrAMobile.adapter.SiteInspectionDeviceTypeAdapter;
import de.mateco.integrAMobile.adapter.SiteInspectionHeightScaleAdapter;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.Pricing1DeviceData;
import de.mateco.integrAMobile.model.Pricing1LevelGroupData;
import de.mateco.integrAMobile.model.SiteInspectionDeviceDataModel;
import de.mateco.integrAMobile.model.SiteInspectionDeviceTypeModel;


public class NewDeviceData extends BaseFragment implements NumberPicker.OnScrollListener, View.OnClickListener
{
    private MatecoPriceApplication application;
    private Language language;
    private CheckBox labelDiesel,labelElectrostatics,labelFourWheel,labelChainRubber,labelWhiteReef,labelPowerLiftSystem,labelPowerGenerators,labelOthers;
    private View rootView;
    private DataBaseHandler db;
    private Spinner spnDeviceGroup,spnHeightScale,spnDeviceType,spnDeviceTypeNumber;
    private Integer[] arrDeviceTypeNum = new Integer[99];
    private SiteInspectionDeviceDataModel deviceData = new SiteInspectionDeviceDataModel();
    private SiteInspectionDeviceDataModel deviceData1 = new SiteInspectionDeviceDataModel();
    private Button buttonAlternativeDevice;
    private SharedPreferences preferences;
    private boolean flag;
    private EditText textOthers;
    private ArrayList<Pricing1DeviceData> lablesDevice = new ArrayList<Pricing1DeviceData>();
    ArrayList<Pricing1LevelGroupData> rowLevelGroupItems = new ArrayList<Pricing1LevelGroupData>();
    private ArrayList<SiteInspectionDeviceTypeModel> listDeviceType = new ArrayList<>();
    private SiteInspectionDeviceTypeAdapter deviceTypeAdapter;
    private SiteInspectionHeightScaleAdapter heightScaleAdapter;
    private ArrayList<SiteInspectionDeviceDataModel> listOfDeviceData = new ArrayList<>();
    SiteInspectionDeviceDataModel model = new SiteInspectionDeviceDataModel();
    private CustomNumberPicker nPWorkingHeight1, nPWorkingHeight2, nPWorkingHeight3;
    private CustomNumberPicker nPLateralReach1, nPLateralReach2, nPLateralReach3, nPLateralReach4;
    private CustomNumberPicker nPMaxLength1, nPMaxLength2, nPMaxLength3, nPMaxLength4;
    private CustomNumberPicker nPMaxBredth1, nPMaxBredth2, nPMaxBredth3, nPMaxBredth4;
    private CustomNumberPicker nPMaxWeight1, nPMaxWeight2, nPMaxWeight3, nPMaxWeight4;
    private CustomNumberPicker nPMaxHeight;
    private CustomNumberPicker nPBasketLoad1, nPBasketLoad2, nPBasketLoad3, nPBasketLoad4;
    private CustomNumberPicker nPBoomLength1, nPBoomLength2, nPBoomLength3, nPBoomLength4;
    private TextView labelWorkinhHeight1, labelLateralReach1, labelMaxLength1, labelMaxBreadth1,
            labelMaxWeight1, labelMaxHeight1, labelBasketLoad1, labelBoomLength1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_device_data_new, container, false);
        super.initializeFragment(rootView);
        return rootView;
    }

    @Override
    public void initializeComponents(View rootView)
    {
        super.initializeComponents(rootView);

        application = (MatecoPriceApplication)getActivity().getApplication();
        language = application.getLanguage();
        db = new DataBaseHandler(getActivity());
        preferences = getActivity().getSharedPreferences("SiteInspection", Context.MODE_PRIVATE);
        setLanguage();

        if(db.getParentDevice(preferences.getInt(DataHelper.SiteInspectionId,0))==0)
        {
            deviceData.setParentId(0);
            deviceData.setBvoId(preferences.getInt(DataHelper.SiteInspectionId, 0));
            deviceData.setPosition(1);
            db.addDevice(deviceData);
            preferences.edit().putInt("ID1", db.getDeviceDataId()).commit();
        }
        if(getArguments()!=null) {
            buttonAlternativeDevice.setEnabled(false);
            ((HomeActivity) getActivity()).getSupportActionBar().setTitle(language.getLabelAlternateDeviceUnit() + "1");
        }
        else
            ((HomeActivity) getActivity()).getSupportActionBar().setTitle(language.getLabelDeviceData() + " 1");

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

        nPWorkingHeight1.setOnScrollListener(this);
        nPWorkingHeight2.setOnScrollListener(this);
        nPWorkingHeight3.setOnScrollListener(this);

        nPLateralReach1.setOnScrollListener(this);
        nPLateralReach2.setOnScrollListener(this);
        nPLateralReach3.setOnScrollListener(this);
        nPLateralReach4.setOnScrollListener(this);

        nPMaxLength1.setOnScrollListener(this);
        nPMaxLength2.setOnScrollListener(this);
        nPMaxLength3.setOnScrollListener(this);
        nPMaxLength4.setOnScrollListener(this);

        nPMaxBredth1.setOnScrollListener(this);
        nPMaxBredth2.setOnScrollListener(this);
        nPMaxBredth3.setOnScrollListener(this);
        nPMaxBredth4.setOnScrollListener(this);

        nPMaxWeight1.setOnScrollListener(this);
        nPMaxWeight2.setOnScrollListener(this);
        nPMaxWeight3.setOnScrollListener(this);
        nPMaxWeight4.setOnScrollListener(this);

        nPMaxHeight.setOnScrollListener(this);

        nPBasketLoad1.setOnScrollListener(this);
        nPBasketLoad2.setOnScrollListener(this);
        nPBasketLoad3.setOnScrollListener(this);
        nPBasketLoad4.setOnScrollListener(this);

        nPBoomLength1.setOnScrollListener(this);
        nPBoomLength2.setOnScrollListener(this);
        nPBoomLength3.setOnScrollListener(this);
        nPBoomLength4.setOnScrollListener(this);

        lablesDevice = db.getPricing1DeviceData();
        SiteInspectionDeviceGroupAdapter deviceAdapter = new SiteInspectionDeviceGroupAdapter(getActivity(), lablesDevice,language);
        spnDeviceGroup.setAdapter(deviceAdapter);

        for(int i=0;i<99;i++)
        {
            arrDeviceTypeNum[i]=i+1;
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(),android.R.layout.simple_spinner_item, arrDeviceTypeNum);
        spnDeviceTypeNumber.setAdapter(adapter);
        setData();


        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
    }

    private void setData()
    {
        if(getArguments()==null)
        {
            listOfDeviceData = db.getDeviceByID(preferences.getInt(DataHelper.SiteInspectionId,0));
            for(int i=0;i<listOfDeviceData.size();i++)
            {
                if((listOfDeviceData.get(i).getPosition()==1 && listOfDeviceData.get(i).getHauptgeraet()=="0")) {
                    Log.e("value", listOfDeviceData.get(i).getHauptgeraet() + "");
                    model = listOfDeviceData.get(i);
                }
            }
        }
        else
        {
            listOfDeviceData = db.getDeviceByID(preferences.getInt(DataHelper.SiteInspectionId,0));
            for(int i=0;i<listOfDeviceData.size();i++)
            {
                if(listOfDeviceData.get(i).getHauptgeraet().equals("1")) {
                    model = listOfDeviceData.get(i);
                }
            }
        }

        if(model!=null)
        {
            if(getArguments()==null)
                preferences.edit().putInt("ID1",model.getId()).commit();
            else
                preferences.edit().putInt("AlternativeDeviceID1",model.getId()).commit();

            if(model.getDiesel()==1)
                labelDiesel.setChecked(true);
            if(model.getElektro()==1)
                labelElectrostatics.setChecked(true);
            if(model.getAllrad()==1)
                labelFourWheel.setChecked(true);
            if(model.getKette_Gummi()==1)
                labelChainRubber.setChecked(true);
            if(model.getReifen_markierungsarm()==1)
                labelWhiteReef.setChecked(true);
            if(model.getPowerlift()==1)
                labelPowerLiftSystem.setChecked(true);
            if(model.getStromerzeuger()==1)
                labelPowerGenerators.setChecked(true);
            if(model.getSonstiges()==1)
                labelOthers.setChecked(true);
            textOthers.setText(model.getSonstigesText());

            if(model.getArbeitshoehe()!=null)
            {
                Log.e("getArbeitshoehe", model.getArbeitshoehe());

                int workingHeight = Integer.parseInt(model.getArbeitshoehe());
                if (workingHeight > 0)
                {
                    int lastDigit = workingHeight % 10;
                    nPWorkingHeight3.setValue(lastDigit);
                    if(workingHeight > 0)
                    {
                        workingHeight = workingHeight / 10;
                        lastDigit = workingHeight % 10;
                        nPWorkingHeight2.setValue(lastDigit);
                        if(workingHeight > 0)
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
                //npWorkingHeight.setValue(Integer.parseInt(model.getArbeitshoehe()));
            }
            if(model.getSeitlicheReichweite()!=null)
            {
                Log.e("getSeitlicheReichweite", model.getSeitlicheReichweite());
                //npLateralReach.setValue(Integer.parseInt(model.getSeitlicheReichweite()));

                Double number = Double.parseDouble(model.getSeitlicheReichweite());
                int workingHeight = (int)(number * 100);
                if (workingHeight > 0)
                {
                    int lastDigit = workingHeight % 10;
                    nPLateralReach4.setValue(lastDigit);
                    if(workingHeight > 0)
                    {
                        workingHeight = workingHeight / 10;
                        lastDigit = workingHeight % 10;
                        nPLateralReach3.setValue(lastDigit);
                        if(workingHeight > 0)
                        {
                            workingHeight = workingHeight / 10;
                            lastDigit = workingHeight % 10;
                            nPLateralReach2.setValue(lastDigit);
                            if(workingHeight > 0)
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

            }
            if(model.getLaenge()!=null)
            {
                Log.e("getArbeitshoehe", model.getLaenge());
                //npMaxLength.setValue(Integer.parseInt(model.getLaenge()));
                Double number = Double.parseDouble(model.getLaenge());
                int workingHeight = (int)(number * 100);
                if (workingHeight > 0)
                {
                    int lastDigit = workingHeight % 10;
                    nPMaxLength4.setValue(lastDigit);
                    if(workingHeight > 0)
                    {
                        workingHeight = workingHeight / 10;
                        lastDigit = workingHeight % 10;
                        nPMaxLength3.setValue(lastDigit);
                        if(workingHeight > 0)
                        {
                            workingHeight = workingHeight / 10;
                            lastDigit = workingHeight % 10;
                            nPMaxLength2.setValue(lastDigit);
                            if(workingHeight > 0)
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
            }
            if(model.getBreite()!=null)
            {
                Log.e("getArbeitshoehe", model.getBreite());
                //npMaxBreadth.setValue(Integer.parseInt(model.getBreite()));

                Double number = Double.parseDouble(model.getBreite());
                int workingHeight = (int)(number * 100);
                if (workingHeight > 0)
                {
                    int lastDigit = workingHeight % 10;
                    nPMaxBredth4.setValue(lastDigit);
                    if(workingHeight > 0)
                    {
                        workingHeight = workingHeight / 10;
                        lastDigit = workingHeight % 10;
                        nPMaxBredth3.setValue(lastDigit);
                        if(workingHeight > 0)
                        {
                            workingHeight = workingHeight / 10;
                            lastDigit = workingHeight % 10;
                            nPMaxBredth2.setValue(lastDigit);
                            if(workingHeight > 0)
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
            }
            if(model.getHoehe()!=null)
            {
                Log.e("getArbeitshoehe", model.getHoehe());
                //npMaxHeight.setValue(Integer.parseInt(model.getHoehe()));
                int number = Integer.parseInt(model.getHoehe());
                nPMaxHeight.setValue(number);
            }
            if(model.getGewicht()!=0)
            {
                Log.e("getArbeitshoehe", model.getGewicht()+"");
                //npMaxWeight.setValue(model.getGewicht());

                int workingHeight = model.getGewicht();
                if (workingHeight > 0)
                {
                    int lastDigit = workingHeight % 10;
                    nPMaxWeight4.setValue(lastDigit);
                    if(workingHeight > 0)
                    {
                        workingHeight = workingHeight / 10;
                        lastDigit = workingHeight % 10;
                        nPMaxWeight3.setValue(lastDigit);
                        if(workingHeight > 0)
                        {
                            workingHeight = workingHeight / 10;
                            lastDigit = workingHeight % 10;
                            nPMaxWeight2.setValue(lastDigit);
                            if(workingHeight > 0)
                            {
                                workingHeight = workingHeight / 10;
                                lastDigit = workingHeight % 10;
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
            }
            if(model.getKorbbelastung()!=null)
            {
                Log.e("getArbeitshoehe", model.getKorbbelastung()+"");
                //npBasketLoad.setValue(Integer.parseInt(model.getGewicht()));

                int workingHeight = Integer.parseInt(model.getKorbbelastung());
                if (workingHeight > 0)
                {
                    int lastDigit = workingHeight % 10;
                    nPBasketLoad4.setValue(lastDigit);
                    if(workingHeight > 0)
                    {
                        workingHeight = workingHeight / 10;
                        lastDigit = workingHeight % 10;
                        nPBasketLoad3.setValue(lastDigit);
                        if(workingHeight > 0)
                        {
                            workingHeight = workingHeight / 10;
                            lastDigit = workingHeight % 10;
                            nPBasketLoad2.setValue(lastDigit);
                            if(workingHeight > 0)
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
            }
            if(model.getKorbarmlaenge()!=null)
            {
                Log.e("getArbeitshoehe", model.getKorbarmlaenge());
                //npBoomLength.setValue(Integer.parseInt(model.getKorbarmlaenge()));

                Double number = Double.parseDouble(model.getKorbarmlaenge());
                int workingHeight = (int)(number * 100);
                if (workingHeight > 0)
                {
                    int lastDigit = workingHeight % 10;
                    nPBoomLength4.setValue(lastDigit);
                    if(workingHeight > 0)
                    {
                        workingHeight = workingHeight / 10;
                        lastDigit = workingHeight % 10;
                        nPBoomLength3.setValue(lastDigit);
                        if(workingHeight > 0)
                        {
                            workingHeight = workingHeight / 10;
                            lastDigit = workingHeight % 10;
                            nPBoomLength2.setValue(lastDigit);
                            if(workingHeight > 0)
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
            }

            if(model.getGeraetegruppe()!=null)
            {
                for(int i=0;i<lablesDevice.size();i++)
                {
                    if(model.getGeraetegruppe().equals(String.valueOf(lablesDevice.get(i).getHeight_main_group())))
                        spnDeviceGroup.setSelection(i);
                }
            }
            for(int i=0;i<arrDeviceTypeNum.length;i++)
            {
                if(model.getAnzahl()==arrDeviceTypeNum[i])
                    spnDeviceTypeNumber.setSelection(i);
            }
        }
    }

    @Override
    public void bindEvents(View rootView)
    {
        super.bindEvents(rootView);
        spnDeviceGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rowLevelGroupItems.clear();
                rowLevelGroupItems.addAll(db.getPricing1LevelGroupData(lablesDevice.get(position).getHeight_main_group()));
                heightScaleAdapter = new SiteInspectionHeightScaleAdapter(getActivity(), rowLevelGroupItems, language);
                spnHeightScale.setAdapter(heightScaleAdapter);
                heightScaleAdapter.notifyDataSetChanged();

                if(model.getHoehengruppe()!=null)
                {
                    for(int i=0;i<rowLevelGroupItems.size();i++)
                    {
                        if(model.getHoehengruppe().equals(rowLevelGroupItems.get(i).getHeightGroup())) {
                            spnHeightScale.setSelection(i);
                        }
                    }
                }
                if(getArguments()==null)
                    deviceData.setGeraetegruppe(lablesDevice.get(position).getHeight_main_group() + "");
                else
                    deviceData1.setGeraetegruppe(lablesDevice.get(position).getHeight_main_group() + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnHeightScale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listDeviceType.clear();
                listDeviceType.addAll(db.getDeviceType(rowLevelGroupItems.get(position).getHeightGroup()));
                deviceTypeAdapter = new SiteInspectionDeviceTypeAdapter(getActivity(),listDeviceType,language);
                spnDeviceType.setAdapter(deviceTypeAdapter);
                deviceTypeAdapter.notifyDataSetChanged();

                if(model.getGeraetetyp()!=null)
                {
                    for(int i=0;i<listDeviceType.size();i++)
                    {
                        if(model.getGeraetetyp().equals(listDeviceType.get(i).getGeraeettypID()))
                            spnDeviceType.setSelection(i);
                    }
                }
                if(getArguments()==null)
                    deviceData.setHoehengruppe(rowLevelGroupItems.get(position).getHeightGroup());
                else
                    deviceData1.setHoehengruppe(rowLevelGroupItems.get(position).getHeightGroup());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnDeviceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(getArguments()==null)
                    deviceData.setGeraetetyp(listDeviceType.get(position).getGeraeettypID());
                else
                    deviceData1.setGeraetetyp(listDeviceType.get(position).getGeraeettypID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnDeviceTypeNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(getArguments()==null)
                    deviceData.setAnzahl(arrDeviceTypeNum[position]);
                else
                    deviceData1.setAnzahl(arrDeviceTypeNum[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    private void setLanguage()
    {
        TextView labelDeviceGroup,labelHeightScale,labelDeviceType,labelWorkinhHeight,labelDeviceTypeNumber,
                labelLateralReach,labelMaxLength,labelMaxBreadth,labelMaxHeight,labelMaxWeight,labelBasketLoad,labelBoomLength;

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

//        npWorkingHeight = (NumberPicker)rootView.findViewById(R.id.numberPicker1);
//        npLateralReach = (NumberPicker)rootView.findViewById(R.id.numberPicker2);
//        npMaxLength = (NumberPicker)rootView.findViewById(R.id.numberPicker3);
//        npMaxBreadth = (NumberPicker)rootView.findViewById(R.id.numberPicker4);
//        npMaxHeight = (NumberPicker)rootView.findViewById(R.id.numberPicker5);
//        npMaxWeight = (NumberPicker)rootView.findViewById(R.id.numberPicker6);
//        npBasketLoad = (NumberPicker)rootView.findViewById(R.id.numberPicker7);
//        npBoomLength = (NumberPicker)rootView.findViewById(R.id.numberPicker8);

        labelWorkinhHeight1 = (TextView)rootView.findViewById(R.id.labelWorkinhHeight1);
        labelLateralReach1 = (TextView)rootView.findViewById(R.id.labelLateralReach1);
        labelMaxLength1 = (TextView)rootView.findViewById(R.id.labelMaxLength1);
        labelMaxBreadth1 = (TextView)rootView.findViewById(R.id.labelMaxBreadth1);
        labelMaxHeight1 = (TextView)rootView.findViewById(R.id.labelMaxHeight1);
        labelMaxWeight1 = (TextView)rootView.findViewById(R.id.labelMaxWeight1);
        labelBasketLoad1 = (TextView)rootView.findViewById(R.id.labelBasketLoad1);
        labelBoomLength1 = (TextView)rootView.findViewById(R.id.labelBoomLength1);

        spnDeviceGroup = (Spinner)rootView.findViewById(R.id.spnDeviceGroup);
        spnHeightScale = (Spinner)rootView.findViewById(R.id.spnHeightScale);
        spnDeviceTypeNumber = (Spinner)rootView.findViewById(R.id.spnDeviceTypeNumber);
        spnDeviceType = (Spinner)rootView.findViewById(R.id.spnDeviceType);

        buttonAlternativeDevice = (Button)rootView.findViewById(R.id.labelAlternativeDevice);

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
        buttonAlternativeDevice.setText(language.getLabelAlternateDevice());
        labelDiesel.setText(language.getLabelDiesel());
        labelElectrostatics.setText(language.getLabelElectrostatics());
        labelFourWheel.setText(language.getLabelFourWheel());
        labelChainRubber.setText(language.getLabelChainRubber());
        labelWhiteReef.setText(language.getLabelWhiteReef());
        labelPowerLiftSystem.setText(language.getLabelPowerLiftSystem());
        labelPowerGenerators.setText(language.getLabelPowerGenerator());
        labelOthers.setText(language.getLabelOther());

        buttonAlternativeDevice.setOnClickListener(this);
        textOthers.setEnabled(false);
    }

    @Override
    public void onScrollStateChange(NumberPicker view, int scrollState)
    {
        String numberString = "";
        int number = 0;
        double numberDouble = 0.0;
        switch (view.getId())
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
                numberString = (nPMaxLength1.getValue() + "") + (nPMaxLength2.getValue() + "") + "." + (nPMaxLength2.getValue() + "") + (nPMaxLength4.getValue() + "");
                numberDouble =  Double.parseDouble(numberString);
                labelMaxLength1.setText(DataHelper.getGermanFromEnglish(numberDouble+"")+ " m");
                break;
            case R.id.nPMaxLength2:
                numberString = (nPMaxLength1.getValue() + "") + (nPMaxLength2.getValue() + "") + "." + (nPMaxLength2.getValue() + "") + (nPMaxLength4.getValue() + "");
                numberDouble =  Double.parseDouble(numberString);
                labelMaxLength1.setText(DataHelper.getGermanFromEnglish(numberDouble+"")+ " m");
                break;
            case R.id.nPMaxLength3:
                numberString = (nPMaxLength1.getValue() + "") + (nPMaxLength2.getValue() + "") + "." + (nPMaxLength2.getValue() + "") + (nPMaxLength4.getValue() + "");
                numberDouble =  Double.parseDouble(numberString);
                labelMaxLength1.setText(DataHelper.getGermanFromEnglish(numberDouble+"")+ " m");
                break;
            case R.id.nPMaxLength4:
                numberString = (nPMaxLength1.getValue() + "") + (nPMaxLength2.getValue() + "") + "." + (nPMaxLength2.getValue() + "") + (nPMaxLength4.getValue() + "");
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
                numberString = (nPBoomLength1.getValue() + "") + (nPBoomLength1.getValue() + "") + "." + (nPBoomLength1.getValue() + "") + (nPBoomLength1.getValue() + "");
                numberDouble =  Double.parseDouble(numberString);
                labelBoomLength1.setText(DataHelper.getGermanFromEnglish(numberDouble +"")+ " m");
                break;
            case R.id.nPBoomLength2:
                numberString = (nPBoomLength1.getValue() + "") + (nPBoomLength1.getValue() + "") + "." + (nPBoomLength1.getValue() + "") + (nPBoomLength1.getValue() + "");
                numberDouble =  Double.parseDouble(numberString);
                labelBoomLength1.setText(DataHelper.getGermanFromEnglish(numberDouble +"")+ " m");
                break;
            case R.id.nPBoomLength3:
                numberString = (nPBoomLength1.getValue() + "") + (nPBoomLength1.getValue() + "") + "." + (nPBoomLength1.getValue() + "") + (nPBoomLength1.getValue() + "");
                numberDouble =  Double.parseDouble(numberString);
                labelBoomLength1.setText(DataHelper.getGermanFromEnglish(numberDouble +"")+ " m");
                break;
            case R.id.nPBoomLength4:
                numberString = (nPBoomLength1.getValue() + "") + (nPBoomLength1.getValue() + "") + "." + (nPBoomLength1.getValue() + "") + (nPBoomLength1.getValue() + "");
                numberDouble =  Double.parseDouble(numberString);
                labelBoomLength1.setText(DataHelper.getGermanFromEnglish(numberDouble +"")+ " m");
                break;
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.labelAlternativeDevice:
                if(getArguments()==null) {
                    saveDeviceData();
                    db.updateDevice(deviceData,preferences.getInt("ID1",0));
                }
                else
                {
                    saveDeviceData1();
                    db.updateDevice(deviceData1,preferences.getInt("AlternativeDeviceID1",0));
                }
                flag = preferences.getBoolean("AlternativeDevice1",false);
                if(!flag)
                {
                    deviceData1.setParentId(preferences.getInt("ID1",0));
                    deviceData1.setBvoId(preferences.getInt(DataHelper.SiteInspectionId, 0));
                    deviceData1.setHauptgeraet("1:1");
                    db.addDevice(deviceData1);
                    preferences.edit().putInt("AlternativeDeviceID1",db.getDeviceDataId()).commit();
                }
                Bundle args = new Bundle();
                args.putString("AlternativeDevice","AlternativeDevice");
                preferences.edit().putBoolean("AlternativeDevice1",true).commit();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                SiteInspectionDeviceData fragment = new SiteInspectionDeviceData();
                fragment.setArguments(args);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack("Site Inspection alternative device data");
                transaction.commit();
                break;
        }
    }

    public void saveDeviceData()
    {
        deviceData.setParentId(0);
        if(labelDiesel.isChecked())
            deviceData.setDiesel(1);
        else
            deviceData.setDiesel(0);

        if(labelElectrostatics.isChecked())
            deviceData.setElektro(1);
        else
            deviceData.setElektro(0);

        if(labelFourWheel.isChecked())
            deviceData.setAllrad(1);
        else
            deviceData.setAllrad(0);

        if(labelChainRubber.isChecked())
            deviceData.setKette_Gummi(1);
        else
            deviceData.setKette_Gummi(0);

        if(labelWhiteReef.isChecked())
            deviceData.setReifen_markierungsarm(1);
        else
            deviceData.setReifen_markierungsarm(0);

        if(labelPowerLiftSystem.isChecked())
            deviceData.setPowerlift(1);
        else
            deviceData.setPowerlift(0);

        if(labelPowerGenerators.isChecked())
            deviceData.setStromerzeuger(1);
        else
            deviceData.setStromerzeuger(0);

        if(labelOthers.isChecked())
            deviceData.setSonstiges(1);
        else
            deviceData.setSonstiges(0);

        String numberString = (nPWorkingHeight1.getValue() + "") + (nPWorkingHeight2.getValue() + "") + (nPWorkingHeight3.getValue()+ "");
        int number = Integer.parseInt(numberString);
        deviceData.setArbeitshoehe(number+"");

        numberString = (nPLateralReach1.getValue() + "") + (nPLateralReach2.getValue() + "") + "." + (nPLateralReach3.getValue() + "") + (nPLateralReach4.getValue() + "") ;
        double numberDouble =  Double.parseDouble(numberString);
        deviceData.setSeitlicheReichweite(numberDouble + "");

        numberString = (nPMaxLength1.getValue() + "") + (nPMaxLength2.getValue() + "") + "." + (nPMaxLength2.getValue() + "") + (nPMaxLength4.getValue() + "");
        numberDouble =  Double.parseDouble(numberString);

        deviceData.setLaenge(numberDouble+"");

        numberString = (nPMaxBredth1.getValue() + "") + (nPMaxBredth2.getValue() + "") + "." + (nPMaxBredth3.getValue() + "") + (nPMaxBredth4.getValue() + "");
        numberDouble =  Double.parseDouble(numberString);
        deviceData.setBreite(numberDouble+"");

        numberString = (nPMaxHeight.getValue() + "");
        number = Integer.parseInt(numberString);
        deviceData.setHoehe(number+"");

        numberString = (nPMaxWeight1.getValue() + "") + (nPMaxWeight2.getValue() + "") + (nPMaxWeight3.getValue() + "") + (nPMaxWeight4.getValue() + "");
        number =  Integer.parseInt(numberString);
        deviceData.setGewicht(number);

        numberString = (nPBasketLoad1.getValue() + "") + (nPBasketLoad2.getValue() + "") + (nPBasketLoad3.getValue() + "") + (nPBasketLoad4.getValue() + "");
        number = Integer.parseInt(numberString);
        deviceData.setKorbbelastung(number+"");

        numberString = (nPBoomLength1.getValue() + "") + (nPBoomLength1.getValue() + "") + "." + (nPBoomLength1.getValue() + "") + (nPBoomLength1.getValue() + "");
        numberDouble =  Double.parseDouble(numberString);
        deviceData.setKorbarmlaenge(numberDouble+"");

//        deviceData.setArbeitshoehe(npWorkingHeight.getValue()+"");
//        deviceData.setSeitlicheReichweite(npLateralReach.getValue()+"");
//        deviceData.setLaenge(npMaxLength.getValue()+"");
//        deviceData.setBreite(npMaxBreadth.getValue()+"");
//        deviceData.setHoehe(npMaxHeight.getValue()+"");
//        deviceData.setGewicht(npMaxWeight.getValue());
//        deviceData.setKorbbelastung(npBasketLoad.getValue()+"");
//        deviceData.setKorbarmlaenge(npBoomLength.getValue()+"");

        deviceData.setSonstigesText(textOthers.getText().toString());
        if(preferences.getBoolean("AlternativeDevice1",false))
            deviceData.setAlternativ(1);
        else
            deviceData.setAlternativ(0);
        deviceData.setPosition(1);
        deviceData.setHauptgeraet("0");
    }

    public void saveDeviceData1()
    {
        deviceData1.setParentId(preferences.getInt("ID1",0));
        if(labelDiesel.isChecked())
            deviceData1.setDiesel(1);
        else
            deviceData1.setDiesel(0);

        if(labelElectrostatics.isChecked())
            deviceData1.setElektro(1);
        else
            deviceData1.setElektro(0);

        if(labelFourWheel.isChecked())
            deviceData1.setAllrad(1);
        else
            deviceData1.setAllrad(0);

        if(labelChainRubber.isChecked())
            deviceData1.setKette_Gummi(1);
        else
            deviceData1.setKette_Gummi(0);

        if(labelWhiteReef.isChecked())
            deviceData1.setReifen_markierungsarm(1);
        else
            deviceData1.setReifen_markierungsarm(0);

        if(labelPowerLiftSystem.isChecked())
            deviceData1.setPowerlift(1);
        else
            deviceData1.setPowerlift(0);

        if(labelPowerGenerators.isChecked())
            deviceData1.setStromerzeuger(1);
        else
            deviceData1.setStromerzeuger(0);

        if(labelOthers.isChecked())
            deviceData1.setSonstiges(1);
        else
            deviceData1.setSonstiges(0);

        String numberString = (nPWorkingHeight1.getValue() + "") + (nPWorkingHeight2.getValue() + "") + (nPWorkingHeight3.getValue()+ "");
        int number = Integer.parseInt(numberString);
        deviceData1.setArbeitshoehe(number+"");

        numberString = (nPLateralReach1.getValue() + "") + (nPLateralReach2.getValue() + "") + "." + (nPLateralReach3.getValue() + "") + (nPLateralReach4.getValue() + "") ;
        double numberDouble =  Double.parseDouble(numberString);
        deviceData1.setSeitlicheReichweite(numberDouble + "");

        numberString = (nPMaxLength1.getValue() + "") + (nPMaxLength2.getValue() + "") + "." + (nPMaxLength2.getValue() + "") + (nPMaxLength4.getValue() + "");
        numberDouble =  Double.parseDouble(numberString);

        deviceData1.setLaenge(numberDouble+"");

        numberString = (nPMaxBredth1.getValue() + "") + (nPMaxBredth2.getValue() + "") + "." + (nPMaxBredth3.getValue() + "") + (nPMaxBredth4.getValue() + "");
        numberDouble =  Double.parseDouble(numberString);
        deviceData1.setBreite(numberDouble+"");

        numberString = (nPMaxHeight.getValue() + "");
        number = Integer.parseInt(numberString);
        deviceData1.setHoehe(number+"");

        numberString = (nPMaxWeight1.getValue() + "") + (nPMaxWeight2.getValue() + "") + (nPMaxWeight3.getValue() + "") + (nPMaxWeight4.getValue() + "");
        number =  Integer.parseInt(numberString);
        deviceData1.setGewicht(number);

        numberString = (nPBasketLoad1.getValue() + "") + (nPBasketLoad2.getValue() + "") + (nPBasketLoad3.getValue() + "") + (nPBasketLoad4.getValue() + "");
        number = Integer.parseInt(numberString);
        deviceData1.setKorbbelastung(number+"");

        numberString = (nPBoomLength1.getValue() + "") + (nPBoomLength1.getValue() + "") + "." + (nPBoomLength1.getValue() + "") + (nPBoomLength1.getValue() + "");
        numberDouble =  Double.parseDouble(numberString);
        deviceData1.setKorbarmlaenge(numberDouble+"");

        deviceData1.setSonstigesText(textOthers.getText().toString());
        deviceData1.setAlternativ(0);
        deviceData1.setPosition(1);
        deviceData1.setHauptgeraet("1");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_site_inspection_permits, menu);
        menu.findItem(R.id.actionRight).setVisible(false);
        menu.findItem(R.id.actionWrong).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        switch (item.getItemId()) {
            case R.id.actionSettings:
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new SettingFragment());
                transaction.addToBackStack("Setting");
                transaction.commit();
                return true;
            case R.id.actionBack:
//                if (getFragmentManager().getBackStackEntryCount() == 0)
//                {
//                    getActivity().finish();
//                }
//                else
//                {
//                    if(getArguments()==null) {
//                        saveDeviceData();
//                        db.updateDevice(deviceData,preferences.getInt("ID1",0));
//                    }
//                    else
//                    {
//                        saveDeviceData1();
//                        db.updateDevice(deviceData1,preferences.getInt("AlternativeDeviceID1",0));
//                    }
//                    getFragmentManager().popBackStack();
//                }
                return true;
            case R.id.actionForward:
//                if(getArguments()==null) {
//                    saveDeviceData();
//                    db.updateDevice(deviceData,preferences.getInt("ID1",0));
//                }
//                else
//                {
//                    saveDeviceData1();
//                    db.updateDevice(deviceData1,preferences.getInt("AlternativeDeviceID1",0));
//                }
//                if(db.getTotalDevice()==1 || db.getParentDevice(preferences.getInt(DataHelper.SiteInspectionId,0))==1)
//                {
//
//                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                    transaction.replace(R.id.content_frame, new SiteInspectionOperationalEnvironmentFragment());
//                    transaction.addToBackStack("Site Inspection environmental fragment");
//                    transaction.commit();
//                }
//                else if(db.getParentDevice(preferences.getInt(DataHelper.SiteInspectionId,0))>=2 && getArguments()==null)
//                {
//
//                    SiteInspectionDeviceData2 fragment = new SiteInspectionDeviceData2();
//                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                    transaction.replace(R.id.content_frame, fragment);
//                    transaction.addToBackStack("Site Inspection device data");
//                    transaction.commit();
//                }
//                else if(db.getParentDevice(preferences.getInt(DataHelper.SiteInspectionId,0))>=2 && getArguments()!=null)
//                {
//
//                    SiteInspectionDeviceData2 fragment = new SiteInspectionDeviceData2();
//                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                    transaction.replace(R.id.content_frame, fragment);
//                    transaction.addToBackStack("Site Inspection device data");
//                    transaction.commit();
//                }
//                else if(db.getParentDevice(preferences.getInt(DataHelper.SiteInspectionId,0))==1 && getArguments()!=null)
//                {
//
//                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                    transaction.replace(R.id.content_frame, new SiteInspectionOperationalEnvironmentFragment());
//                    transaction.addToBackStack("Site Inspection environmental fragment");
//                    transaction.commit();
//                }
//                else
//                {
//
//                    SiteInspectionDeviceData2 fragment = new SiteInspectionDeviceData2();
//                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                    transaction.replace(R.id.content_frame, fragment);
//                    transaction.addToBackStack("Site Inspection device data");
//                    transaction.commit();
//                }

                return true;
            case R.id.actionAdd:
//                if(getArguments()==null)
//                {
//                    saveDeviceData();
//                    db.updateDevice(deviceData,preferences.getInt("ID1",0));
//                }
//                else
//                {
//                    saveDeviceData1();
//                    db.updateDevice(deviceData1,preferences.getInt("AlternativeDeviceID1",0));
//                }
//                if(db.getParentDevice(preferences.getInt(DataHelper.SiteInspectionId,0))<5)
//                {
//                    SiteInspectionDeviceData2 fragment = new SiteInspectionDeviceData2();
//                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                    transaction.replace(R.id.content_frame, fragment);
//                    transaction.addToBackStack("Site Inspection device data");
//                    transaction.commit();
//                }
                return true;
            case R.id.actionSave:
//                db.updateSiteInspectionDate(preferences.getInt(DataHelper.SiteInspectionId,0));
//                db.updateSiteInspectionUpload(preferences.getInt(DataHelper.SiteInspectionId,0),"",2);
//                preferences.edit().clear().commit();
//                showShortToast(language.getMessageBvoStored());
//                for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++)
//                {
//                    getFragmentManager().popBackStack();
//                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
