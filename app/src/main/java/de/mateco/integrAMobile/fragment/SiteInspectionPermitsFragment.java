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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import de.mateco.integrAMobile.Helper.GlobalMethods;
import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.InputFilterMinMax;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.SiteInspectionOperationalDataPermitsModel;

public class SiteInspectionPermitsFragment extends BaseFragment implements View.OnClickListener
{
    private View rootView;
    private MatecoPriceApplication application;
    private Language language;
    private CheckBox labelLayingBusStop,labelTransferTaxiStand,labelLayingHandicappedParking;
    private EditText textName,textAnsbach,textFaxNo,textIsFord,textTelephone,textNote,textRichTung1,textRichTung2,textFahrstreifenAnzahl1,textFahrstreifenBauzeit1,
            textFahrstreifenAnzahl2,textFahrstreifenBauzeit2,textFahrbahnbreite1,textFahrbahnbreite2,textGehwegbreite1,textGehwegbreite2,
            textRadwegbreite1,textRadwegbreite2,textFBBauzeit1,textFBBauzeit2,textGWBauzeit1,textGWBauzeit2,textRWBauzeit1,textRWBauzeit2, textUsuallyPlane;
    private ToggleButton buttonAuthOwner,buttonAuthCommunity,buttonAuthPt,buttonAuthTaxiAssociation,buttonAbsperma,buttonSketch,buttonTrafficSign,buttonPreparations;
    private SiteInspectionOperationalDataPermitsModel siteInspectionOperationalDataPermitsModel = new SiteInspectionOperationalDataPermitsModel();
    private DataBaseHandler db;
    private SharedPreferences preferences;
    private ImageView imageViewCall;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_site_inspection_oprational_data_permits, container, false);
        getActivity().invalidateOptionsMenu();
        application = (MatecoPriceApplication)getActivity().getApplication();
        language = application.getLanguage();
        super.initializeFragment(rootView);
        db = new DataBaseHandler(getActivity());
        preferences = getActivity().getSharedPreferences("SiteInspection", Context.MODE_PRIVATE);
        setHasOptionsMenu(true);
        setLanguage();
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelPermits());
        return rootView;
    }

    @Override
    public void initializeComponents(View rootView)
    {
        super.initializeComponents(rootView);
        imageViewCall = (ImageView)rootView.findViewById(R.id.imageViewCall);
    }

    @Override
    public void bindEvents(View rootView) {
        super.bindEvents(rootView);
        imageViewCall.setOnClickListener(this);
    }

    private void setLanguage()
    {
        TextView labelAuthOwner,labelName,labelAnsbach,labelPhone,labelFaxNo,labelIsFord,labelAuthCommunity,labelAuthPt,labelAuthTaxiAssociation,
                labelAbsperrma,labelSketch,labelTrafficSignPlanRequired,labelPreparationOfStoppingSigns,labelNote;
        TextView labelLaneRoadway,labelDirection,labelNumber,labelLane,labelGoAway,labelRoadway,labelNumber1,labelLane1,labelWidth,labelWidth1,
                labelGoAway1,labelRoadway1,labelDirection1,labelAvailable,labelDuringConstruction,labelUsuallyPlan;

        labelAuthOwner = (TextView)rootView.findViewById(R.id.labelAuthOwner);
        labelName = (TextView)rootView.findViewById(R.id.labelName);
        labelAnsbach = (TextView)rootView.findViewById(R.id.labelAnsbach);
        labelPhone = (TextView)rootView.findViewById(R.id.labelPhone);
        labelFaxNo = (TextView)rootView.findViewById(R.id.labelFaxNo);
        labelIsFord = (TextView)rootView.findViewById(R.id.labelIsFord);
        labelAuthCommunity = (TextView)rootView.findViewById(R.id.labelAuthCommunity);
        labelAuthPt = (TextView)rootView.findViewById(R.id.labelAuthPt);
        labelAuthTaxiAssociation = (TextView)rootView.findViewById(R.id.labelAuthTaxiAssociation);
        labelAbsperrma = (TextView)rootView.findViewById(R.id.labelAbsperrmaß);
        labelSketch = (TextView)rootView.findViewById(R.id.labelSketch);
        labelTrafficSignPlanRequired = (TextView)rootView.findViewById(R.id.labelTrafficSignPlanRequired);
        labelPreparationOfStoppingSigns = (TextView)rootView.findViewById(R.id.labelPreparationOfStoppingSigns);
        labelNote = (TextView)rootView.findViewById(R.id.labelNote);

        labelLaneRoadway = (TextView)rootView.findViewById(R.id.labelLaneRoadway);
        labelDirection = (TextView)rootView.findViewById(R.id.labelDirection);
        labelNumber = (TextView)rootView.findViewById(R.id.labelNumber);
        labelLane = (TextView)rootView.findViewById(R.id.labelLane);
        labelGoAway = (TextView)rootView.findViewById(R.id.labelGoAway);
        labelRoadway = (TextView)rootView.findViewById(R.id.labelRoadway);
        labelNumber1 = (TextView)rootView.findViewById(R.id.labelNumber1);
        labelLane1 = (TextView)rootView.findViewById(R.id.labelLane1);
        labelWidth = (TextView)rootView.findViewById(R.id.labelWidth);
        labelWidth1 = (TextView)rootView.findViewById(R.id.labelWidth1);
        labelGoAway1 = (TextView)rootView.findViewById(R.id.labelGoAway1);
        labelRoadway1 = (TextView)rootView.findViewById(R.id.labelRoadway1);
        labelDirection1 = (TextView)rootView.findViewById(R.id.labelDirection1);
        labelAvailable = (TextView)rootView.findViewById(R.id.labelAvailable);
        labelDuringConstruction = (TextView)rootView.findViewById(R.id.labelDuringConstruction);
        labelUsuallyPlan = (TextView)rootView.findViewById(R.id.labelUsuallyPlan);


        labelLayingBusStop = (CheckBox)rootView.findViewById(R.id.labelLayingBusStop);
        labelTransferTaxiStand = (CheckBox)rootView.findViewById(R.id.labelTransferTaxiStand);
        labelLayingHandicappedParking = (CheckBox)rootView.findViewById(R.id.labelLayingHandicappedParking);

        buttonAuthOwner = (ToggleButton)rootView.findViewById(R.id.button1);
        buttonAuthCommunity = (ToggleButton)rootView.findViewById(R.id.button2);
        buttonAuthPt = (ToggleButton)rootView.findViewById(R.id.button3);
        buttonAuthTaxiAssociation = (ToggleButton)rootView.findViewById(R.id.button4);
        buttonAbsperma = (ToggleButton)rootView.findViewById(R.id.button5);
        buttonSketch = (ToggleButton)rootView.findViewById(R.id.button6);
        buttonTrafficSign = (ToggleButton)rootView.findViewById(R.id.button7);
        buttonPreparations = (ToggleButton)rootView.findViewById(R.id.button8);

        textName = (EditText)rootView.findViewById(R.id.textName);
        textAnsbach = (EditText)rootView.findViewById(R.id.textAnsbach);
        textFaxNo = (EditText)rootView.findViewById(R.id.textFaxNo);
        textIsFord = (EditText)rootView.findViewById(R.id.textIsFord);
        textTelephone = (EditText)rootView.findViewById(R.id.textTelephone);
        textNote = (EditText)rootView.findViewById(R.id.textNote);
        textRichTung1 = (EditText)rootView.findViewById(R.id.textRichTung1);
        textRichTung2 = (EditText)rootView.findViewById(R.id.textRichTung2);
        textFahrstreifenAnzahl1 = (EditText)rootView.findViewById(R.id.textFahrstreifenAnzahl1);
        textFahrstreifenBauzeit1 = (EditText)rootView.findViewById(R.id.textFahrstreifenBauzeit1);
        textFahrstreifenAnzahl2 = (EditText)rootView.findViewById(R.id.textFahrstreifenAnzahl2);
        textFahrstreifenBauzeit2 = (EditText)rootView.findViewById(R.id.textFahrstreifenBauzeit2);
        textFahrbahnbreite1 = (EditText)rootView.findViewById(R.id.textFahrbahnbreite1);
        textFahrbahnbreite2 = (EditText)rootView.findViewById(R.id.textFahrbahnbreite2);
        textGehwegbreite1 = (EditText)rootView.findViewById(R.id.textGehwegbreite1);
        textGehwegbreite2 = (EditText)rootView.findViewById(R.id.textGehwegbreite2);
        textRadwegbreite1 = (EditText)rootView.findViewById(R.id.textRadwegbreite1);
        textRadwegbreite2 = (EditText)rootView.findViewById(R.id.textRadwegbreite2);
        textFBBauzeit1 = (EditText)rootView.findViewById(R.id.textFBBauzeit1);
        textFBBauzeit2 = (EditText)rootView.findViewById(R.id.textFBBauzeit2);
        textGWBauzeit1 = (EditText)rootView.findViewById(R.id.textGWBauzeit1);
        textGWBauzeit2 = (EditText)rootView.findViewById(R.id.textGWBauzeit2);
        textRWBauzeit1 = (EditText)rootView.findViewById(R.id.textRWBauzeit1);
        textRWBauzeit2 = (EditText)rootView.findViewById(R.id.textRWBauzeit2);
        textUsuallyPlane = (EditText)rootView.findViewById(R.id.textUsuallyPlane);

     /*   textFahrbahnbreite2.setFilters(new InputFilter[]{new InputFilterMinMax("0", "9")});
        textGehwegbreite1.setFilters(new InputFilter[]{new InputFilterMinMax("0", "9")});
        textGehwegbreite2.setFilters(new InputFilter[]{new InputFilterMinMax("0", "9")});
        textRadwegbreite1.setFilters(new InputFilter[]{new InputFilterMinMax("0", "9")});
        textRadwegbreite2.setFilters(new InputFilter[]{new InputFilterMinMax("0", "9")});
        textFBBauzeit1.setFilters(new InputFilter[]{new InputFilterMinMax("0", "9")});
        textFBBauzeit2.setFilters(new InputFilter[]{new InputFilterMinMax("0", "9")});
        textGWBauzeit1.setFilters(new InputFilter[]{new InputFilterMinMax("0", "9")});
        textGWBauzeit2.setFilters(new InputFilter[]{new InputFilterMinMax("0", "9")});
        textRWBauzeit1.setFilters(new InputFilter[]{new InputFilterMinMax("0", "9")});
        textRWBauzeit2.setFilters(new InputFilter[]{new InputFilterMinMax("0", "9")});*/

        labelAuthOwner.setText(language.getLabelAuthOwner());
        labelName.setText(language.getLabelName());
        labelAnsbach.setText(language.getLabelAnsbach());
        //todo remove lable from Telefon and set Telefon lable as a edittext hint
        labelPhone.setText("");
        //labelPhone.setText(language.getLabelPhone());
//todo remove lable from Fax and set Fax lable as a edittext hint
        labelFaxNo.setText("");
        //labelFaxNo.setText(language.getLabelFaxNo());
        labelIsFord.setText(language.getLabelIsFord());
        labelAuthCommunity.setText(language.getLabelAuthCommunity());
        labelAuthPt.setText(language.getLabelAuthPt());
        labelAuthTaxiAssociation.setText(language.getLabelAuthTaxiAssociation());
        labelAbsperrma.setText(language.getLabelAbsperrma());
        labelSketch.setText(language.getLabelSketch());
        labelTrafficSignPlanRequired.setText(language.getLabelTrafficSignPlanRequired());
        labelPreparationOfStoppingSigns.setText(language.getLabelPreparationOfStoppingSigns());
        labelNote.setText(language.getLabelNote());

        labelLaneRoadway.setText(language.getLabelRoadway1());
        labelDirection.setText(language.getLabelDirection());
        labelNumber.setText(language.getLabelNumber());
        labelLane.setText(language.getLabelLane());
        labelGoAway.setText(language.getLabelGoAway());
        labelRoadway.setText(language.getLabelRoadway());
        labelNumber1.setText(language.getLabelNumber());
        labelLane1.setText(language.getLabelLane());
        labelWidth.setText(language.getLabelWidth());
        labelWidth1.setText(language.getLabelWidth());
        labelGoAway1.setText(language.getLabelGoAway());
        labelRoadway1.setText(language.getLabelRoadway());
        labelDirection1.setText(language.getLabelDirection());
        labelAvailable.setText(language.getLabelAvailable());
        labelDuringConstruction.setText(language.getLabelDuringConstruction());
        labelUsuallyPlan.setText(language.getLabelUsuallyPlan());

        labelLayingBusStop.setText(language.getLabelLayingBusStop());
        labelTransferTaxiStand.setText(language.getLabelTransferTaxiStand());
        labelLayingHandicappedParking.setText(language.getLabelLayingHandicappedParking());
        textFahrstreifenAnzahl1.setFilters(new InputFilter[]{new InputFilterMinMax("0", "32767")});
        textFahrstreifenBauzeit1.setFilters(new InputFilter[]{new InputFilterMinMax("0", "32767")});
        textFahrstreifenAnzahl2.setFilters(new InputFilter[]{new InputFilterMinMax("0", "32767")});
        textFahrstreifenBauzeit2.setFilters(new InputFilter[]{new InputFilterMinMax("0", "32767")});
        setData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_site_inspection_permits, menu);
        menu.findItem(R.id.actionAdd).setVisible(false);
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
                    if(saveData())
                    {
                        db.updateOperationalDataPermits(siteInspectionOperationalDataPermitsModel,preferences.getInt(DataHelper.SiteInspectionId,0));
                        getFragmentManager().popBackStack();
                    }
                }
                return true;
            case R.id.actionForward:
                if(saveData())
                {
                    db.updateOperationalDataPermits(siteInspectionOperationalDataPermitsModel, preferences.getInt(DataHelper.SiteInspectionId, 0));
                    FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction1.replace(R.id.content_frame, new SiteInspectionAdditionalMobileWindPowerFragment());
                    transaction1.addToBackStack("Site Inspection Additional Mobile Wind Power");
                    transaction1.commit();
                }
                return true;
            case R.id.actionRight:
                if(saveData())
                {
                    db.updateOperationalDataPermits(siteInspectionOperationalDataPermitsModel,preferences.getInt(DataHelper.SiteInspectionId,0));
                    FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction2.replace(R.id.content_frame, new SiteInspectionSelectMailFragment());
                    transaction2.addToBackStack("Site Inspection Mail");
                    transaction2.commit();
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
                    db.updateOperationalDataPermits(siteInspectionOperationalDataPermitsModel,preferences.getInt(DataHelper.SiteInspectionId,0));
                    db.updateSiteInspectionDate(preferences.getInt(DataHelper.SiteInspectionId,0));
                    db.updateSiteInspectionUpload(preferences.getInt(DataHelper.SiteInspectionId,0),"",2);
                    preferences.edit().clear().commit();
                    showShortToast(language.getMessageBvoStored());
                    getActivity().getSupportFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    public boolean saveData()
    {
        if(buttonAuthOwner.isChecked())
            siteInspectionOperationalDataPermitsModel.setEigentuemer(1);
        else
            siteInspectionOperationalDataPermitsModel.setEigentuemer(0);

        if(buttonAuthCommunity.isChecked())
            siteInspectionOperationalDataPermitsModel.setKommune(1);
        else
            siteInspectionOperationalDataPermitsModel.setKommune(0);

        if(buttonAbsperma.isChecked())
            siteInspectionOperationalDataPermitsModel.setAbsperrmass(1);
        else
            siteInspectionOperationalDataPermitsModel.setAbsperrmass(0);

        if(buttonAuthPt.isChecked())
            siteInspectionOperationalDataPermitsModel.setOEPNV(1);
        else
            siteInspectionOperationalDataPermitsModel.setOEPNV(0);

        if(buttonSketch.isChecked())
            siteInspectionOperationalDataPermitsModel.setSkizze(1);
        else
            siteInspectionOperationalDataPermitsModel.setSkizze(0);

        if(buttonAuthTaxiAssociation.isChecked())
            siteInspectionOperationalDataPermitsModel.setTaxiverband(1);
        else
            siteInspectionOperationalDataPermitsModel.setTaxiverband(0);

        if(buttonTrafficSign.isChecked())
            siteInspectionOperationalDataPermitsModel.setVerkehrzeichenplan(1);
        else
            siteInspectionOperationalDataPermitsModel.setVerkehrzeichenplan(0);

        if(buttonPreparations.isChecked())
            siteInspectionOperationalDataPermitsModel.setHalteverbotsschilder(1);
        else
            siteInspectionOperationalDataPermitsModel.setHalteverbotsschilder(0);

        if(labelLayingBusStop.isChecked())
            siteInspectionOperationalDataPermitsModel.setVerlegungBushaltestelle(1);
        else
            siteInspectionOperationalDataPermitsModel.setVerlegungBushaltestelle(0);

        if(labelTransferTaxiStand.isChecked())
            siteInspectionOperationalDataPermitsModel.setVerlegungTaxi(1);
        else
            siteInspectionOperationalDataPermitsModel.setVerlegungTaxi(0);

        if(labelLayingHandicappedParking.isChecked())
            siteInspectionOperationalDataPermitsModel.setVerlegungBehi(1);
        else
            siteInspectionOperationalDataPermitsModel.setVerlegungBehi(0);

        siteInspectionOperationalDataPermitsModel.setEingetuemerName(textName.getText().toString());
        siteInspectionOperationalDataPermitsModel.setEigentuemerAnsPartner(textAnsbach.getText().toString());
        siteInspectionOperationalDataPermitsModel.setAngefordert(textIsFord.getText().toString());
        siteInspectionOperationalDataPermitsModel.setHinweis(textNote.getText().toString());
        siteInspectionOperationalDataPermitsModel.setRichtung1(textRichTung1.getText().toString());
        siteInspectionOperationalDataPermitsModel.setRichtung2(textRichTung2.getText().toString());
        siteInspectionOperationalDataPermitsModel.setFahrstreifenAnzahl1(textFahrstreifenAnzahl1.getText().toString());
        siteInspectionOperationalDataPermitsModel.setFahrstreifenBauzeit1(textFahrstreifenBauzeit1.getText().toString());
        siteInspectionOperationalDataPermitsModel.setFahrstreifenAnzahl2(textFahrstreifenAnzahl2.getText().toString());
        siteInspectionOperationalDataPermitsModel.setFahrstreifenBauzeit2(textFahrstreifenBauzeit2.getText().toString());

        String outputValue = GlobalMethods.getBlankValueForZero(DataHelper.getEnglishCurrencyFromGermanBvo(textFahrbahnbreite1.getText().toString()));
        siteInspectionOperationalDataPermitsModel.setFahrbahnbreite1(outputValue);
        outputValue = GlobalMethods.getBlankValueForZero(DataHelper.getEnglishCurrencyFromGermanBvo(textFahrbahnbreite2.getText().toString()));
        siteInspectionOperationalDataPermitsModel.setFahrbahnbreite2(outputValue);
        outputValue = GlobalMethods.getBlankValueForZero(DataHelper.getEnglishCurrencyFromGermanBvo(textGehwegbreite1.getText().toString()));
        siteInspectionOperationalDataPermitsModel.setGehwegbreite1(outputValue);
        outputValue = GlobalMethods.getBlankValueForZero(DataHelper.getEnglishCurrencyFromGermanBvo(textGehwegbreite2.getText().toString()));
        siteInspectionOperationalDataPermitsModel.setGehwegbreite2(outputValue);
        outputValue = GlobalMethods.getBlankValueForZero(DataHelper.getEnglishCurrencyFromGermanBvo(textRadwegbreite1.getText().toString()));
        siteInspectionOperationalDataPermitsModel.setRadwegbreite1(outputValue);
        outputValue = GlobalMethods.getBlankValueForZero(DataHelper.getEnglishCurrencyFromGermanBvo(textRadwegbreite2.getText().toString()));
        siteInspectionOperationalDataPermitsModel.setRadwegbreite2(outputValue);
        outputValue = GlobalMethods.getBlankValueForZero(DataHelper.getEnglishCurrencyFromGermanBvo(textFBBauzeit1.getText().toString()));
        siteInspectionOperationalDataPermitsModel.setFBBauzeit1(outputValue);
        outputValue = GlobalMethods.getBlankValueForZero(DataHelper.getEnglishCurrencyFromGermanBvo(textFBBauzeit2.getText().toString()));
        siteInspectionOperationalDataPermitsModel.setFBBauzeit2(outputValue);
        outputValue = GlobalMethods.getBlankValueForZero(DataHelper.getEnglishCurrencyFromGermanBvo(textGWBauzeit1.getText().toString()));
        siteInspectionOperationalDataPermitsModel.setGWBauzeit1(outputValue);
        outputValue = GlobalMethods.getBlankValueForZero(DataHelper.getEnglishCurrencyFromGermanBvo(textGWBauzeit2.getText().toString()));
        siteInspectionOperationalDataPermitsModel.setGWBauzeit2(outputValue);
        outputValue = GlobalMethods.getBlankValueForZero(DataHelper.getEnglishCurrencyFromGermanBvo(textRWBauzeit1.getText().toString()));
        siteInspectionOperationalDataPermitsModel.setRWBauzeit1(outputValue);
        outputValue = GlobalMethods.getBlankValueForZero(DataHelper.getEnglishCurrencyFromGermanBvo(textRWBauzeit2.getText().toString()));
        siteInspectionOperationalDataPermitsModel.setRWBauzeit2(outputValue);
        siteInspectionOperationalDataPermitsModel.setRegelplaene(textUsuallyPlane.getText().toString());

        if(!DataHelper.phoneNumberValidationGerman(textTelephone.getText().toString()) && !textTelephone.getText().toString().equals(""))
        {
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
            showMessage(language.getLabelAlert(),language.getMessagePhoneValidation(),language.getLabelOk(),listener).show();
            // textCustomerNewPhone.setError(language.getMessageNotValidNumber());
            textTelephone.setError(language.getMessageNotValidNumber());
            return false;
        } else
            siteInspectionOperationalDataPermitsModel.setEigentuemerTelefon(textTelephone.getText().toString());

        if(!DataHelper.phoneNumberValidationGerman(textFaxNo.getText().toString()) && !textFaxNo.getText().toString().equals(""))
        {
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
            showMessage(language.getLabelAlert(),language.getMessagePhoneValidation(),language.getLabelOk(),listener).show();
            // textCustomerNewPhone.setError(language.getMessageNotValidNumber());
            textFaxNo.setError(language.getMessageNotValidNumber());
            return false;
        } else
            siteInspectionOperationalDataPermitsModel.setEigentuemerTelefax(textFaxNo.getText().toString());
        return true;
    }

    public void setData()
    {
        SiteInspectionOperationalDataPermitsModel model = db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId,0)).getSiteInspectionOperationalDataPermitsModel();
        if(model!=null) {
            if (model.getEigentuemer() == 0)
                buttonAuthOwner.setChecked(false);
            else
                buttonAuthOwner.setChecked(true);

            if (model.getKommune() == 0)
                buttonAuthCommunity.setChecked(false);
            else
                buttonAuthCommunity.setChecked(true);

            if (model.getAbsperrmass() == 0)
                buttonAbsperma.setChecked(false);
            else
                buttonAbsperma.setChecked(true);

            if (model.getOEPNV() == 0)
                buttonAuthPt.setChecked(false);
            else
                buttonAuthPt.setChecked(true);

            if (model.getSkizze() == 0)
                buttonSketch.setChecked(false);
            else
                buttonSketch.setChecked(true);

            if (model.getTaxiverband() == 0)
                buttonAuthTaxiAssociation.setChecked(false);
            else
                buttonAuthTaxiAssociation.setChecked(true);

            if (model.getVerkehrzeichenplan() == 0)
                buttonTrafficSign.setChecked(false);
            else
                buttonTrafficSign.setChecked(true);

            if (model.getHalteverbotsschilder() == 0)
                buttonPreparations.setChecked(false);
            else
                buttonPreparations.setChecked(true);

            if (model.getVerlegungBushaltestelle() == 0)
                labelLayingBusStop.setChecked(false);
            else
                labelLayingBusStop.setChecked(true);

            if (model.getVerlegungTaxi() == 0)
                labelTransferTaxiStand.setChecked(false);
            else
                labelTransferTaxiStand.setChecked(true);

            if (model.getVerlegungBehi() == 0)
                labelLayingHandicappedParking.setChecked(false);
            else
                labelLayingHandicappedParking.setChecked(true);

            textName.setText(model.getEingetuemerName());
            textAnsbach.setText(model.getEigentuemerAnsPartner());
            textTelephone.setText(model.getEigentuemerTelefon());
            textFaxNo.setText(model.getEigentuemerTelefax());
            textIsFord.setText(model.getAngefordert());
            textNote.setText(model.getHinweis());
            textRichTung1.setText(model.getRichtung1());
            textRichTung2.setText(model.getRichtung2());
            textFahrstreifenAnzahl1.setText(model.getFahrstreifenAnzahl1());
            textFahrstreifenBauzeit1.setText(model.getFahrstreifenBauzeit1());
            textFahrstreifenAnzahl2.setText(model.getFahrstreifenAnzahl2());
            textFahrstreifenBauzeit2.setText(model.getFahrstreifenBauzeit2());

            textFahrbahnbreite1.setText(DataHelper.getGermanFromEnglishBvo(model.getFahrbahnbreite1()));
            GlobalMethods.setBlankValueForZero(textFahrbahnbreite1);
            textFahrbahnbreite2.setText(DataHelper.getGermanFromEnglishBvo(model.getFahrbahnbreite2()));
            GlobalMethods.setBlankValueForZero(textFahrbahnbreite2);
            textGehwegbreite1.setText(DataHelper.getGermanFromEnglishBvo(model.getGehwegbreite1()));
            GlobalMethods.setBlankValueForZero(textGehwegbreite1);
            textGehwegbreite2.setText(DataHelper.getGermanFromEnglishBvo(model.getGehwegbreite2()));
            GlobalMethods.setBlankValueForZero(textGehwegbreite2);
            textRadwegbreite1.setText(DataHelper.getGermanFromEnglishBvo(model.getRadwegbreite1()));
            GlobalMethods.setBlankValueForZero(textRadwegbreite1);
            textRadwegbreite2.setText(DataHelper.getGermanFromEnglishBvo(model.getRadwegbreite2()));
            GlobalMethods.setBlankValueForZero(textRadwegbreite2);
            textFBBauzeit1.setText(DataHelper.getGermanFromEnglishBvo(model.getFBBauzeit1()));
            GlobalMethods.setBlankValueForZero(textFBBauzeit1);
            textFBBauzeit2.setText(DataHelper.getGermanFromEnglishBvo(model.getFBBauzeit2()));
            GlobalMethods.setBlankValueForZero(textFBBauzeit2);
            textGWBauzeit1.setText(DataHelper.getGermanFromEnglishBvo(model.getGWBauzeit1()));
            GlobalMethods.setBlankValueForZero(textGWBauzeit1);
            textGWBauzeit2.setText(DataHelper.getGermanFromEnglishBvo(model.getGWBauzeit2()));
            GlobalMethods.setBlankValueForZero(textGWBauzeit2);
            textRWBauzeit1.setText(DataHelper.getGermanFromEnglishBvo(model.getRWBauzeit1()));
            GlobalMethods.setBlankValueForZero(textRWBauzeit1);
            textRWBauzeit2.setText(DataHelper.getGermanFromEnglishBvo(model.getRWBauzeit2()));
            GlobalMethods.setBlankValueForZero(textRWBauzeit2);
            textUsuallyPlane.setText(model.getRegelplaene());
        }
    }

    public String checkValue(EditText edt,String value) {
        if (value.contains(".")) {
            int maxLength = 5;
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(maxLength);
            edt.setFilters(fArray);
        }
        return value;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.imageViewCall:
                if ( Build.VERSION.SDK_INT >= 23)
                {
                    if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE) ==
                            PackageManager.PERMISSION_GRANTED)
                    {
                        GlobalMethods.callToNumber(context, textTelephone);
                    }
                    else{
                        ActivityCompat.requestPermissions(getActivity(), new String[] {
                                        Manifest.permission.CALL_PHONE},
                                35);
                    }
                }
                else {
                    GlobalMethods.callToNumber(context, textTelephone);
                }

                break;
        }
    }
}


