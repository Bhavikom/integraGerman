package de.mateco.integrAMobile.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import de.mateco.integrAMobile.Helper.GlobalMethods;
import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.InputFilterMinMax;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.SiteInspectionAdditionalMobileWindPowerModel;

public class SiteInspectionAdditionalMobileWindPowerFragment extends BaseFragment implements View.OnClickListener
{
    private View rootView;
    private ToggleButton buttonCurrentGrounding, buttonTieDown, buttonCableLaying, buttonBasePlan, buttonAdequeteSpacing, buttonBoltingWKA, buttonMicrowave, buttonElectricityPylon, buttonWindPower;
    private MatecoPriceApplication application;
    private EditText textID,textNetzbetreiber,textAntennen_1,textAntennen_2,textAntennen_3,textGewichtStahlbau,textArbeit,textWKANr,textWKATyp,
            textRichtfunkAnsp,textRichtfunkTelefon,textStrommastAnsp,textStrommastTelefon,textWindkraftAnsp,textWindkraftTelefon;
    private SiteInspectionAdditionalMobileWindPowerModel siteInspectionAdditionalMobileWindPowerModel = new SiteInspectionAdditionalMobileWindPowerModel();
    private Language language;
    private DataBaseHandler db;
    private SharedPreferences preferences;
    private ImageView imageViewCallPhone1, imageViewCallPhone2, imageViewCallPhone3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_site_inspection_additional_mobile_wind_power, container, false);
        super.initializeFragment(rootView);
        getActivity().invalidateOptionsMenu();

        setHasOptionsMenu(true);
        application = (MatecoPriceApplication) getActivity().getApplication();
        language = application.getLanguage();
        db = new DataBaseHandler(getActivity());
        preferences = getActivity().getSharedPreferences("SiteInspection", Context.MODE_PRIVATE);
        setLanguage();
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle(language.getLabelAdditionalMobileWindPower());
        return rootView;
    }

    @Override
    public void initializeComponents(View rootView) {
        super.initializeComponents(rootView);
        imageViewCallPhone1 = (ImageView)rootView.findViewById(R.id.imageViewCallPhone1);
        imageViewCallPhone2 = (ImageView)rootView.findViewById(R.id.imageViewCallPhone2);
        imageViewCallPhone3 = (ImageView)rootView.findViewById(R.id.imageViewCallPhone3);
    }

    @Override
    public void bindEvents(View rootView) {
        super.bindEvents(rootView);
        imageViewCallPhone1.setOnClickListener(this);
        imageViewCallPhone2.setOnClickListener(this);
        imageViewCallPhone3.setOnClickListener(this);
    }

    private void setLanguage() {

        TextView labelSiteNo,labelNetworkOperator,labelMoreAntennasAvailable,labelCurrentGroundingNecessary,
                labelSteelConstruction,labelCableLaying,labelAdequateSpacing,labelTieDown,labelBasePlan,labelWhatToDo,labelNumberWKA,labelWKAType,labelBoltingWKA,labelShutDown,
                labelMicrowave,labelAnsbach,labelElectricityPylon,labelAnsbach1,labelPhoneNo,labelWindPower,labelAnsbach2,labelPhoneNo1,labelPhoneNo2;

        labelSiteNo = (TextView) rootView. findViewById(R.id.labelSiteNo);
        labelNetworkOperator = (TextView) rootView. findViewById(R.id.labelNetworkOperator);
        labelMoreAntennasAvailable = (TextView) rootView. findViewById(R.id.labelMoreAntennasAvailable);
        labelCurrentGroundingNecessary = (TextView) rootView. findViewById(R.id.labelCurrentGroundingNecessary);
        labelSteelConstruction = (TextView) rootView. findViewById(R.id.labelSteelConstruction);
        labelCableLaying = (TextView) rootView. findViewById(R.id.labelCableLaying);
        labelAdequateSpacing = (TextView) rootView. findViewById(R.id.labelAdequateSpacing);
        labelTieDown = (TextView) rootView. findViewById(R.id.labelTieDown);
        labelBasePlan = (TextView) rootView. findViewById(R.id.labelBasePlan);
        labelWhatToDo = (TextView) rootView. findViewById(R.id.labelWhatToDo);
        labelNumberWKA = (TextView) rootView. findViewById(R.id.labelNumberWKA);
        labelWKAType = (TextView) rootView. findViewById(R.id.labelWKAType);
        labelBoltingWKA = (TextView) rootView.findViewById(R.id.labelBoltingWKA);
        labelShutDown = (TextView) rootView. findViewById(R.id.labelShutDown);
        labelMicrowave = (TextView) rootView. findViewById(R.id.labelMicrowave);
        labelAnsbach = (TextView) rootView. findViewById(R.id.labelAnsbach);
        labelElectricityPylon = (TextView) rootView. findViewById(R.id.labelElectricityPylon);
        labelAnsbach1 = (TextView) rootView. findViewById(R.id.labelAnsbach1);
        labelPhoneNo = (TextView) rootView. findViewById(R.id.labelPhoneNo);
        labelWindPower = (TextView) rootView. findViewById(R.id.labelWindPower);
        labelAnsbach2 = (TextView) rootView. findViewById(R.id.labelAnsbach2);
        labelPhoneNo1 = (TextView) rootView. findViewById(R.id.labelPhoneNo1);
        labelPhoneNo2 = (TextView) rootView. findViewById(R.id.labelPhoneNo2);

        labelSiteNo.setText(language.getLabelSiteNo());
        labelNetworkOperator.setText(language.getLabelNetworkOperator());
        labelMoreAntennasAvailable.setText(language.getLabelMoreAntennasAvailable());
        labelCurrentGroundingNecessary.setText(language.getLabelCurrentGroundingNecessary());
        labelSteelConstruction.setText(language.getLabelSteelConstruction());
        labelCableLaying.setText(language.getLabelCableLaying());
        labelAdequateSpacing.setText(language.getLabelAdequateSpacing());
        labelTieDown.setText(language.getLabelTieDown());
        labelBasePlan.setText(language.getLabelBasePlan());
        labelWhatToDo.setText(language.getLabelWhatToDo());
        labelNumberWKA.setText(language.getLabelNumberWKA());
        labelWKAType.setText(language.getLabelWKAType());
        labelBoltingWKA.setText(language.getLabelBoltingWKA());
        labelShutDown.setText(language.getLabelShutDown());
        labelMicrowave.setText(language.getLabelMicrowave());
        labelAnsbach.setText(language.getLabelAnsbach());
        labelElectricityPylon.setText(language.getLabelElectricityPylon());
        labelAnsbach1.setText(language.getLabelAnsbach());
        labelPhoneNo.setText(language.getLabelPhone());
        labelWindPower.setText(language.getLabelWindPower());
        labelAnsbach2.setText(language.getLabelAnsbach());
        labelPhoneNo1.setText(language.getLabelPhone());
        labelPhoneNo2.setText(language.getLabelPhone());

        buttonCurrentGrounding = (ToggleButton) rootView.findViewById(R.id.button1);
        buttonTieDown = (ToggleButton) rootView.findViewById(R.id.button2);
        buttonCableLaying = (ToggleButton) rootView.findViewById(R.id.button3);
        buttonBasePlan = (ToggleButton) rootView.findViewById(R.id.button4);
        buttonAdequeteSpacing = (ToggleButton) rootView.findViewById(R.id.button5);
        buttonBoltingWKA = (ToggleButton) rootView.findViewById(R.id.button6);
        buttonMicrowave = (ToggleButton) rootView.findViewById(R.id.button7);
        buttonElectricityPylon = (ToggleButton) rootView.findViewById(R.id.button8);
        buttonWindPower = (ToggleButton) rootView.findViewById(R.id.button9);

        textID = (EditText)rootView.findViewById(R.id.textID);
        textNetzbetreiber = (EditText)rootView.findViewById(R.id.textNetzbetreiber);
        textAntennen_1 = (EditText)rootView.findViewById(R.id.textAntennen_1);
        textAntennen_2 = (EditText)rootView.findViewById(R.id.textAntennen_2);
        textAntennen_3 = (EditText)rootView.findViewById(R.id.textAntennen_3);
        textGewichtStahlbau = (EditText)rootView.findViewById(R.id.textGewichtStahlbau);
        textArbeit = (EditText)rootView.findViewById(R.id.textArbeit);
        textWKANr = (EditText)rootView.findViewById(R.id.textWKANr);
        textWKATyp = (EditText)rootView.findViewById(R.id.textWKATyp);
        textRichtfunkAnsp = (EditText)rootView.findViewById(R.id.textRichtfunkAnsp);
        textRichtfunkTelefon = (EditText)rootView.findViewById(R.id.textRichtfunkTelefon);
        textStrommastAnsp = (EditText)rootView.findViewById(R.id.textStrommastAnsp);
        textStrommastTelefon = (EditText)rootView.findViewById(R.id.textStrommastTelefon);
        textWindkraftAnsp = (EditText)rootView.findViewById(R.id.textWindkraftAnsp);
        textWindkraftTelefon = (EditText)rootView.findViewById(R.id.textWindkraftTelefon);
        textAntennen_1.setFilters(new InputFilter[]{new InputFilterMinMax("0", "32767")});
        textAntennen_2.setFilters(new InputFilter[]{new InputFilterMinMax("0", "32767")});
        textAntennen_3.setFilters(new InputFilter[]{new InputFilterMinMax("0", "32767")});
        setData();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_site_inspection_permits, menu);
        menu.findItem(R.id.actionAdd).setVisible(false);
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
                    if(saveData()) {
                        db.updateAdditionalMobileWindPower(siteInspectionAdditionalMobileWindPowerModel, preferences.getInt(DataHelper.SiteInspectionId, 0));
                        getFragmentManager().popBackStack();
                    }
                }
                return true;
            case R.id.actionRight:
                if(saveData())
                {
                    db.updateAdditionalMobileWindPower(siteInspectionAdditionalMobileWindPowerModel,preferences.getInt(DataHelper.SiteInspectionId,0));
                    FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction1.replace(R.id.content_frame, new SiteInspectionSelectMailFragment());
                    transaction1.addToBackStack("Site Inspection Mobile Wind");
                    transaction1.commit();
                }
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
            case R.id.actionSave:
                if(saveData())
                {
                    db.updateAdditionalMobileWindPower(siteInspectionAdditionalMobileWindPowerModel, preferences.getInt(DataHelper.SiteInspectionId,0));
                    db.updateSiteInspectionDate(preferences.getInt(DataHelper.SiteInspectionId,0));
                    db.updateSiteInspectionUpload(preferences.getInt(DataHelper.SiteInspectionId,0),"",2);
                    preferences.edit().clear().commit();
                    showShortToast(language.getMessageBvoStored());
                    getActivity().getSupportFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
                }
                return true;
            case R.id.actionForward:
                if(saveData())
                {
                    db.updateAdditionalMobileWindPower(siteInspectionAdditionalMobileWindPowerModel, preferences.getInt(DataHelper.SiteInspectionId, 0));
                    FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction2.replace(R.id.content_frame, new SiteInspectionSelectMailFragment());
                    transaction2.addToBackStack("Site Inspection Mobile Wind");
                    transaction2.commit();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    public boolean saveData()
    {
        if(buttonCurrentGrounding.isChecked())
            siteInspectionAdditionalMobileWindPowerModel.setStomerdung(1);
        else
            siteInspectionAdditionalMobileWindPowerModel.setStomerdung(0);

        if(buttonTieDown.isChecked())
            siteInspectionAdditionalMobileWindPowerModel.setSpannbaender(1);
        else
            siteInspectionAdditionalMobileWindPowerModel.setSpannbaender(0);

        if(buttonCableLaying.isChecked())
            siteInspectionAdditionalMobileWindPowerModel.setKabelverlegung(1);
        else
            siteInspectionAdditionalMobileWindPowerModel.setKabelverlegung(0);

        if(buttonBasePlan.isChecked())
            siteInspectionAdditionalMobileWindPowerModel.setGrundwerksplan(1);
        else
            siteInspectionAdditionalMobileWindPowerModel.setGrundwerksplan(0);

        if(buttonAdequeteSpacing.isChecked())
            siteInspectionAdditionalMobileWindPowerModel.setMastabstand(1);
        else
            siteInspectionAdditionalMobileWindPowerModel.setMastabstand(0);

        if(buttonBoltingWKA.isChecked())
            siteInspectionAdditionalMobileWindPowerModel.setWKAVerbolzung(1);
        else
            siteInspectionAdditionalMobileWindPowerModel.setWKAVerbolzung(0);

        if(buttonMicrowave.isChecked())
            siteInspectionAdditionalMobileWindPowerModel.setAbschaltungRichtfunk(1);
        else
            siteInspectionAdditionalMobileWindPowerModel.setAbschaltungRichtfunk(0);

        if(buttonElectricityPylon.isChecked())
            siteInspectionAdditionalMobileWindPowerModel.setAbschaltungStrommast(1);
        else
            siteInspectionAdditionalMobileWindPowerModel.setAbschaltungStrommast(0);

        if(buttonWindPower.isChecked())
            siteInspectionAdditionalMobileWindPowerModel.setAbschaltungWindkraft(1);
        else
            siteInspectionAdditionalMobileWindPowerModel.setAbschaltungWindkraft(0);

        siteInspectionAdditionalMobileWindPowerModel.setID(textID.getText().toString());
        siteInspectionAdditionalMobileWindPowerModel.setNetzbetreiber(textNetzbetreiber.getText().toString());
        siteInspectionAdditionalMobileWindPowerModel.setAntennen_1(textAntennen_1.getText().toString());
        siteInspectionAdditionalMobileWindPowerModel.setAntennen_2(textAntennen_2.getText().toString());
        siteInspectionAdditionalMobileWindPowerModel.setAntennen_3(textAntennen_3.getText().toString());
        String outputValue = GlobalMethods.getBlankValueForZero(DataHelper.getEnglishCurrencyFromGermanBvo(textGewichtStahlbau.getText().toString()));
        siteInspectionAdditionalMobileWindPowerModel.setGewichtStahlbau(outputValue);
        siteInspectionAdditionalMobileWindPowerModel.setArbeit(textArbeit.getText().toString());
        siteInspectionAdditionalMobileWindPowerModel.setWKANr(textWKANr.getText().toString());
        siteInspectionAdditionalMobileWindPowerModel.setWKATyp(textWKATyp.getText().toString());
        siteInspectionAdditionalMobileWindPowerModel.setRichtfunkAnsp(textRichtfunkAnsp.getText().toString());
        siteInspectionAdditionalMobileWindPowerModel.setStrommastAnsp(textStrommastAnsp.getText().toString());
        siteInspectionAdditionalMobileWindPowerModel.setWindkraftAnsp(textWindkraftAnsp.getText().toString());

        if(!DataHelper.phoneNumberValidationGerman(textRichtfunkTelefon.getText().toString()) && !textRichtfunkTelefon.getText().toString().equals(""))
        {
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
            showMessage(language.getLabelAlert(),language.getMessagePhoneValidation(),language.getLabelOk(),listener).show();
            // textCustomerNewPhone.setError(language.getMessageNotValidNumber());
            textRichtfunkTelefon.setError(language.getMessageNotValidNumber());
            return false;
        }
        else
            siteInspectionAdditionalMobileWindPowerModel.setRichtfunkTelefon(textRichtfunkTelefon.getText().toString());

        if(!DataHelper.phoneNumberValidationGerman(textStrommastTelefon.getText().toString()) && !textStrommastTelefon.getText().toString().equals(""))
        {
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
            showMessage(language.getLabelAlert(),language.getMessagePhoneValidation(),language.getLabelOk(),listener).show();
            // textCustomerNewPhone.setError(language.getMessageNotValidNumber());
            textStrommastTelefon.setError(language.getMessageNotValidNumber());
            return false;
        } else
            siteInspectionAdditionalMobileWindPowerModel.setStrommastTelefon(textStrommastTelefon.getText().toString());

        if(!DataHelper.phoneNumberValidationGerman(textWindkraftTelefon.getText().toString()) && !textWindkraftTelefon.getText().toString().equals(""))
        {
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
            showMessage(language.getLabelAlert(),language.getMessagePhoneValidation(),language.getLabelOk(),listener).show();
            // textCustomerNewPhone.setError(language.getMessageNotValidNumber());
            textWindkraftTelefon.setError(language.getMessageNotValidNumber());
            return false;
        } else
            siteInspectionAdditionalMobileWindPowerModel.setWindkraftTelefon(textWindkraftTelefon.getText().toString());

       return true;
    }

    public void setData()
    {
        SiteInspectionAdditionalMobileWindPowerModel model = db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId,0)).getSiteInspectionAdditionalMobileWindPowerModel();
        Log.e("string",new Gson().toJson(model));
        if(model != null)
        {
            if(model.getStomerdung()==0)
                buttonCurrentGrounding.setChecked(false);
            else
                buttonCurrentGrounding.setChecked(true);

            if(model.getSpannbaender()==0)
                buttonTieDown.setChecked(false);
            else
                buttonTieDown.setChecked(true);

            if(model.getKabelverlegung()==0)
                buttonCableLaying.setChecked(false);
            else
                buttonCableLaying.setChecked(true);

            if(model.getGrundwerksplan()==0)
                buttonBasePlan.setChecked(false);
            else
                buttonBasePlan.setChecked(true);

            if(model.getMastabstand()==0)
                buttonAdequeteSpacing.setChecked(false);
            else
                buttonAdequeteSpacing.setChecked(true);

            if(model.getWKAVerbolzung()==0)
                buttonBoltingWKA.setChecked(false);
            else
                buttonBoltingWKA.setChecked(true);

            if(model.getAbschaltungRichtfunk()==0)
                buttonMicrowave.setChecked(false);
            else
                buttonMicrowave.setChecked(true);

            if(model.getAbschaltungStrommast()==0)
                buttonElectricityPylon.setChecked(false);
            else
                buttonElectricityPylon.setChecked(true);

            if(model.getAbschaltungWindkraft()==0)
                buttonWindPower.setChecked(false);
            else
                buttonWindPower.setChecked(true);

            textID.setText(model.getID());
            textNetzbetreiber.setText(model.getNetzbetreiber());
            textAntennen_1.setText(model.getAntennen_1());
            textAntennen_2.setText(model.getAntennen_2());
            textAntennen_3.setText(model.getAntennen_3());
            if(model.getGewichtStahlbau()!=null)
                textGewichtStahlbau.setText(DataHelper.getGermanFromEnglishBvo(model.getGewichtStahlbau()));
            GlobalMethods.setBlankValueForZero(textGewichtStahlbau);
            textArbeit.setText(model.getArbeit());
            textWKANr.setText(model.getWKANr());
            textWKATyp.setText(model.getWKATyp());
            textRichtfunkAnsp.setText(model.getRichtfunkAnsp());
            textRichtfunkTelefon.setText(model.getRichtfunkTelefon());
            textStrommastAnsp.setText(model.getStrommastAnsp());
            textStrommastTelefon.setText(model.getStrommastTelefon());
            textWindkraftAnsp.setText(model.getWindkraftAnsp());
            textWindkraftTelefon.setText(model.getWindkraftTelefon());
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.imageViewCallPhone1:
                if ( Build.VERSION.SDK_INT >= 23)
                {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) ==
                            PackageManager.PERMISSION_GRANTED)
                    {
                        GlobalMethods.callToNumber(context, textRichtfunkTelefon);
                    }
                    else{
                        ActivityCompat.requestPermissions(getActivity(), new String[] {
                                        Manifest.permission.CALL_PHONE},
                                3005);
                    }
                }
                else {
                    GlobalMethods.callToNumber(context, textRichtfunkTelefon);
                }

                break;
            case R.id.imageViewCallPhone2:
                if ( Build.VERSION.SDK_INT >= 23)
                {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) ==
                            PackageManager.PERMISSION_GRANTED)
                    {
                        GlobalMethods.callToNumber(context, textStrommastTelefon);
                    }
                    else{
                        ActivityCompat.requestPermissions(getActivity(), new String[] {
                                        Manifest.permission.CALL_PHONE},
                                3005);
                    }
                }
                else {
                    GlobalMethods.callToNumber(context, textStrommastTelefon);
                }


                break;
            case R.id.imageViewCallPhone3:
                if ( Build.VERSION.SDK_INT >= 23)
                {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) ==
                            PackageManager.PERMISSION_GRANTED)
                    {
                        GlobalMethods.callToNumber(context, textWindkraftTelefon);
                    }
                    else{
                        ActivityCompat.requestPermissions(getActivity(), new String[] {
                                        Manifest.permission.CALL_PHONE},
                                3005);
                    }
                }
                else {
                    GlobalMethods.callToNumber(context, textWindkraftTelefon);
                }


                break;
        }
    }
}