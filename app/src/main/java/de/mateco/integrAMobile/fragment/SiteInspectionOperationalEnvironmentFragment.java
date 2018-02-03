package de.mateco.integrAMobile.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.GlobalMethods;
import de.mateco.integrAMobile.Helper.InputFilterMinMax;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.SiteInspectionOperationalEnvironmentModel;
import de.mateco.integrAMobile.model.SiteInspectionPracticabilityModel;

public class SiteInspectionOperationalEnvironmentFragment extends BaseFragment
{
    private MatecoPriceApplication application;
    private Language language;
    private CheckBox labelSummer,labelWinter,labelWetness,labelperformWorkLabels,LabelDrivingPlates,labelBongossiPlates,
            labelPublicRoadCountry,labelPrivateProperty,labelInterior,labelOutdoor,labelOther;
    private EditText textStk,textLoadStand,textWhich,textFor,textOther,textAccessLying,textDirections,textLoadAccess,textLoadText,
            textMaxTrafficLoad,textUndergroundStand,textRoadGrade,textNotices;
    private ToggleButton buttonAccessObstruction,buttonDesclaimer,buttonAccessForest,buttonVerdSewers,buttonAdditionalSupportingMaterial;
    private View rootView;
    private DataBaseHandler db;
    private SharedPreferences preferences;
    private SiteInspectionOperationalEnvironmentModel siteInspectionOperationalEnvironmentModel = new SiteInspectionOperationalEnvironmentModel();
    private ArrayList<SiteInspectionPracticabilityModel> listOfPracticability = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_site_inspection_operational_enviroment, container, false);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        super.initializeFragment(rootView);
        application = (MatecoPriceApplication)getActivity().getApplication();
        language = application.getLanguage();
        db = new DataBaseHandler(getActivity());
        preferences = getActivity().getSharedPreferences("SiteInspection", Context.MODE_PRIVATE);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelOperatingEnvironment());
        setLnaguage();
        return rootView;
    }

    private void setLnaguage() {
        TextView labelAccessObstruction,labelDesclaimer,labelAccessForest,labelWhich,labelFor,labelAccessLying,labelStk,labelDirections,
                labelVerdSewers,labelLoadAccess,labelLoadStand,labelMaxTrafficLoad,labelAdditionalSupportingMaterial,labelUndergroundStand,
                labelRoadGrade,labelNotices,labelPracticability;

        labelSummer = (CheckBox)rootView.findViewById(R.id.labelSummer);
        labelWinter = (CheckBox)rootView.findViewById(R.id.labelWinter);
        labelWetness = (CheckBox)rootView.findViewById(R.id.labelWetness);
        labelperformWorkLabels = (CheckBox)rootView.findViewById(R.id.labelperformWorkLabels);
        LabelDrivingPlates = (CheckBox)rootView.findViewById(R.id.LabelDrivingPlates);
        labelBongossiPlates = (CheckBox)rootView.findViewById(R.id.labelBongossiPlates);
        labelPublicRoadCountry = (CheckBox)rootView.findViewById(R.id.labelPublicRoadCountry);
        labelPrivateProperty = (CheckBox)rootView.findViewById(R.id.labelPrivateProperty);
        labelInterior = (CheckBox)rootView.findViewById(R.id.labelInterior);
        labelOutdoor = (CheckBox)rootView.findViewById(R.id.labelOutdoor);
        labelOther = (CheckBox)rootView.findViewById(R.id.labelOther);

        labelAccessObstruction = (TextView)rootView.findViewById(R.id.labelAccessObstruction);
        labelDesclaimer = (TextView)rootView.findViewById(R.id.labelDisclaimer);
        labelAccessForest = (TextView)rootView.findViewById(R.id.labelAccessForest);
        labelWhich = (TextView)rootView.findViewById(R.id.labelWhich);
        labelFor = (TextView)rootView.findViewById(R.id.labelFor);
        labelAccessLying = (TextView)rootView.findViewById(R.id.labelAccessLying);
        labelStk = (TextView)rootView.findViewById(R.id.labelStk);
        labelDirections = (TextView)rootView.findViewById(R.id.labelDirections);
        labelVerdSewers = (TextView)rootView.findViewById(R.id.labelVerdSewers);
        labelLoadAccess = (TextView)rootView.findViewById(R.id.labelLoadAccess);
        labelLoadStand = (TextView)rootView.findViewById(R.id.labelLoadStand);
        labelMaxTrafficLoad = (TextView)rootView.findViewById(R.id.labelMaxTrafficLoad);
        labelAdditionalSupportingMaterial = (TextView)rootView.findViewById(R.id.labelAdditionalSupportingMaterial);
        labelUndergroundStand = (TextView)rootView.findViewById(R.id.labelUndergroundStand);
        labelRoadGrade = (TextView)rootView.findViewById(R.id.labelRoadGrade);
        labelPracticability = (TextView)rootView.findViewById(R.id.labelPracticability);
        labelNotices = (TextView)rootView.findViewById(R.id.labelNotices);

        textLoadStand = (EditText)rootView.findViewById(R.id.textLoadStand);
        textStk = (EditText)rootView.findViewById(R.id.textStk);
        textWhich = (EditText)rootView.findViewById(R.id.textWhich);
        textFor = (EditText)rootView.findViewById(R.id.textFor);
        textOther = (EditText)rootView.findViewById(R.id.textOther);
        textAccessLying = (EditText)rootView.findViewById(R.id.textAccessLying);
        textDirections = (EditText)rootView.findViewById(R.id.textDirections);
        textLoadText = (EditText)rootView.findViewById(R.id.textLoadText);
        textLoadAccess = (EditText)rootView.findViewById(R.id.textLoadAccess);
        textMaxTrafficLoad = (EditText)rootView.findViewById(R.id.textMaxTrafficLoad);
        textUndergroundStand = (EditText)rootView.findViewById(R.id.textUndergroundStand);
        textRoadGrade = (EditText)rootView.findViewById(R.id.textRoadGrade);
        textNotices = (EditText)rootView.findViewById(R.id.textNotices);

        buttonAccessObstruction = (ToggleButton)rootView.findViewById(R.id.button1);
        buttonDesclaimer = (ToggleButton)rootView.findViewById(R.id.button2);
        buttonAccessForest = (ToggleButton)rootView.findViewById(R.id.button3);
        buttonVerdSewers = (ToggleButton)rootView.findViewById(R.id.button4);
        buttonAdditionalSupportingMaterial = (ToggleButton)rootView.findViewById(R.id.button5);

        labelAccessObstruction.setText(language.getLabelAccessObstruction());
        labelDesclaimer.setText(language.getLabelDesclaimer());
        labelAccessForest.setText(language.getLabelAccessForest());
        labelWhich.setText(language.getLabelWhich());
        labelFor.setText(language.getLabelFor());
        labelAccessLying.setText(language.getLabelAccessLying());
        labelStk.setText(language.getLabelStk());
        labelDirections.setText(language.getLabelDirections());
        labelVerdSewers.setText(language.getLabelVerdSewers());
        labelLoadAccess.setText(language.getLabelLoadAccess());
        labelLoadStand.setText(language.getLabelLoadStand());
        labelMaxTrafficLoad.setText(language.getLabelMaxTrafficLoad());
        labelAdditionalSupportingMaterial.setText(language.getLabelAdditionalSupportingMaterial());
        labelUndergroundStand.setText(language.getLabelUndergroundStand());
        labelRoadGrade.setText(language.getLabelRoadGrade());
        labelPracticability.setText(language.getLabelPracticability());
        labelNotices.setText(language.getLabelNote());

        labelSummer.setText(language.getLabelSummer());
        labelWinter.setText(language.getLabelWinter());
        labelWetness.setText(language.getLabelWetness());
        labelOther.setText(language.getLabelOther());
        labelperformWorkLabels.setText(language.getLabelperformWorkLabels());
        LabelDrivingPlates.setText(language.getLabelDrivingPlates());
        labelBongossiPlates.setText(language.getLabelBongossiPlates());
        labelPublicRoadCountry.setText(language.getLabelPublicRoadCountry());
        labelPrivateProperty.setText(language.getLabelPrivateProperty());
        labelInterior.setText(language.getLabelInterior());
        labelOutdoor.setText(language.getLabelOutdoor());

        textLoadStand.setText(language.getLabelLoadStand());
        textStk.setHint(language.getLabelStk());
        textAccessLying.setFilters(new InputFilter[]{new InputFilterMinMax("0", "32767")});
        textStk.setFilters(new InputFilter[]{new InputFilterMinMax("0", "32767")});
        setData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_site_inspection_permits, menu);
        menu.findItem(R.id.actionRight).setVisible(false);
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
                    saveData();
                    db.updateOperationalEnvironment(siteInspectionOperationalEnvironmentModel,preferences.getInt(DataHelper.SiteInspectionId,0));
                    getFragmentManager().popBackStack();
                }
                return true;
            case R.id.actionForward:
                saveData();
                db.updateOperationalEnvironment(siteInspectionOperationalEnvironmentModel, preferences.getInt(DataHelper.SiteInspectionId, 0));
                FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction1.replace(R.id.content_frame, new SiteInspectionPermitsFragment());
                //transaction.addToBackStack(SettingFragment.Tag);
                transaction1.addToBackStack("Site Inspection Operational Environment");
                transaction1.commit();


                return true;
            case R.id.actionSave:
                saveData();
                db.updateOperationalEnvironment(siteInspectionOperationalEnvironmentModel, preferences.getInt(DataHelper.SiteInspectionId, 0));
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
        //return super.onOptionsItemSelected(item);
    }

    public boolean checkNumber()
    {
        if(!textAccessLying.getText().toString().equals(""))
        {
            if(Double.parseDouble(textAccessLying.getText().toString())>32767) {
                textAccessLying.setError(language.getMessageLargeNumber());
                return false;
            }
        }
        if(!textStk.getText().toString().equals(""))
        {
            if(Double.parseDouble(textStk.getText().toString())>32767) {
                textStk.setError(language.getMessageLargeNumber());
                return false;
            }
        }
        /*if(!textLoadAccess.getText().toString().equals("") && !textLoadAccess.getText().toString().equals("0,00") && !textLoadAccess.getText().toString().startsWith("0,00"))
        {
            if(Double.parseDouble(textLoadAccess.getText().toString())>3.40282346638528860e+38) {
                textLoadAccess.setError(language.getMessageLargeNumber());
                return false;
            }
        }
        if(!textLoadText.getText().toString().equals("") && !textLoadText.getText().toString().equals("0,00") && !textLoadText.getText().toString().startsWith("0,00"))
        {
            if(Double.parseDouble(textLoadText.getText().toString())>3.40282346638528860e+38) {
                textLoadText.setError(language.getMessageLargeNumber());
                return false;
            }
        }
        if(!textMaxTrafficLoad.getText().toString().equals("") && !textMaxTrafficLoad.getText().toString().equals("0,00") && !textMaxTrafficLoad.getText().toString().startsWith("0,00"))
        {
            if(Double.parseDouble(textMaxTrafficLoad.getText().toString())>3.40282346638528860e+38) {
                textMaxTrafficLoad.setError(language.getMessageLargeNumber());
                return false;
            }
        }
        if(!textRoadGrade.getText().toString().equals("") && !textRoadGrade.getText().toString().equals("0,00") && !textRoadGrade.getText().toString().contains(","))
        {
            if(Double.parseDouble(textRoadGrade.getText().toString())>3.40282346638528860e+38) {
                textRoadGrade.setError(language.getMessageLargeNumber());
                return false;
            }
        }*/
        textAccessLying.setError(null);
        textStk.setError(null);
        return true;
    }
    public void saveData()
    {
        if(buttonAccessObstruction.isChecked())
            siteInspectionOperationalEnvironmentModel.setHindernis(1);
        else
            siteInspectionOperationalEnvironmentModel.setHindernis(0);

        if(buttonDesclaimer.isChecked())
            siteInspectionOperationalEnvironmentModel.setHaftungsausschluss(1);
        else
            siteInspectionOperationalEnvironmentModel.setHaftungsausschluss(0);

        if(buttonAccessForest.isChecked())
            siteInspectionOperationalEnvironmentModel.setWaldweg(1);
        else
            siteInspectionOperationalEnvironmentModel.setWaldweg(0);

        if(buttonVerdSewers.isChecked())
            siteInspectionOperationalEnvironmentModel.setVerdachtAbwasserkanal(1);
        else
            siteInspectionOperationalEnvironmentModel.setVerdachtAbwasserkanal(0);

        if(buttonAdditionalSupportingMaterial.isChecked())
            siteInspectionOperationalEnvironmentModel.setStuetzmaterial(1);
        else
            siteInspectionOperationalEnvironmentModel.setStuetzmaterial(0);

        if(labelperformWorkLabels.isChecked())
        {
            siteInspectionOperationalEnvironmentModel.setSchaltafeln(textStk.getText().toString());
        }
        else
            siteInspectionOperationalEnvironmentModel.setSchaltafeln("0");

        if(LabelDrivingPlates.isChecked())
            siteInspectionOperationalEnvironmentModel.setFahrbleche("1");
        else
            siteInspectionOperationalEnvironmentModel.setFahrbleche("0");

        if(labelBongossiPlates.isChecked())
            siteInspectionOperationalEnvironmentModel.setBongossiPlatten("1");
        else
            siteInspectionOperationalEnvironmentModel.setBongossiPlatten("0");

        if(labelPublicRoadCountry.isChecked())
            siteInspectionOperationalEnvironmentModel.setPublicRoadCountry("1");
        else
            siteInspectionOperationalEnvironmentModel.setPublicRoadCountry("0");

        if(labelPrivateProperty.isChecked())
            siteInspectionOperationalEnvironmentModel.setPrivateProperty("1");
        else
            siteInspectionOperationalEnvironmentModel.setPrivateProperty("0");

        if(labelInterior.isChecked())
            siteInspectionOperationalEnvironmentModel.setInterior("1");
        else
            siteInspectionOperationalEnvironmentModel.setInterior("0");

        if(labelOutdoor.isChecked())
            siteInspectionOperationalEnvironmentModel.setOutdoor("1");
        else
            siteInspectionOperationalEnvironmentModel.setOutdoor("0");

        siteInspectionOperationalEnvironmentModel.setWegbeschreibung(textDirections.getText().toString());
        siteInspectionOperationalEnvironmentModel.setHindernisText(textWhich.getText().toString());
        siteInspectionOperationalEnvironmentModel.setHaftungsausschlussText(textFor.getText().toString());
        siteInspectionOperationalEnvironmentModel.setZufahrtAuslegen(textAccessLying.getText().toString());

        String outputValue = GlobalMethods.getBlankValueForZero(DataHelper.getEnglishCurrencyFromGermanBvo(textLoadAccess.getText().toString()));
        siteInspectionOperationalEnvironmentModel.setTragslastZufahrt(outputValue);
        outputValue = GlobalMethods.getBlankValueForZero(DataHelper.getEnglishCurrencyFromGermanBvo(textLoadText.getText().toString()));
        siteInspectionOperationalEnvironmentModel.setTraglastStandplatz(outputValue);
        siteInspectionOperationalEnvironmentModel.setVerkehrslastmax(textMaxTrafficLoad.getText().toString());
        siteInspectionOperationalEnvironmentModel.setStuetzmaterialText(textLoadStand.getText().toString());
        siteInspectionOperationalEnvironmentModel.setUntergrund(textUndergroundStand.getText().toString());
        outputValue = GlobalMethods.getBlankValueForZero(DataHelper.getEnglishCurrencyFromGermanBvo(textRoadGrade.getText().toString()));
        siteInspectionOperationalEnvironmentModel.setStassengefaelle(outputValue);
        siteInspectionOperationalEnvironmentModel.setHinweise(textNotices.getText().toString());

        if(listOfPracticability != null){
            listOfPracticability.clear();
        }
        if (labelSummer.isChecked())
        {
            SiteInspectionPracticabilityModel siteInspectionPracticabilityModel = new SiteInspectionPracticabilityModel();
            siteInspectionPracticabilityModel.setBefahrbarkeit("1");
            siteInspectionPracticabilityModel.setBefahrbarkeitText("");
            listOfPracticability.add(siteInspectionPracticabilityModel);
        }
        if (labelWinter.isChecked())
        {
            SiteInspectionPracticabilityModel siteInspectionPracticabilityModel = new SiteInspectionPracticabilityModel();
            siteInspectionPracticabilityModel.setBefahrbarkeit("2");
            siteInspectionPracticabilityModel.setBefahrbarkeitText("");
            listOfPracticability.add(siteInspectionPracticabilityModel);
        }
        if (labelWetness.isChecked())
        {
            SiteInspectionPracticabilityModel siteInspectionPracticabilityModel = new SiteInspectionPracticabilityModel();
            siteInspectionPracticabilityModel.setBefahrbarkeit("3");
            siteInspectionPracticabilityModel.setBefahrbarkeitText("");
            listOfPracticability.add(siteInspectionPracticabilityModel);
        }
        if (labelOther.isChecked())
        {
            SiteInspectionPracticabilityModel siteInspectionPracticabilityModel = new SiteInspectionPracticabilityModel();
            siteInspectionPracticabilityModel.setBefahrbarkeit("4");
            siteInspectionPracticabilityModel.setBefahrbarkeitText(textOther.getText().toString());
            listOfPracticability.add(siteInspectionPracticabilityModel);
        }
        siteInspectionOperationalEnvironmentModel.setListOfPracticability(listOfPracticability);
    }

    private void setData() {
        SiteInspectionOperationalEnvironmentModel model = db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId,0)).getSiteInspectionOperationalEnvironmentModel();
        if(model!=null) {
            if (model.getHindernis() == 0)
                buttonAccessObstruction.setChecked(false);
            else
                buttonAccessObstruction.setChecked(true);

            if (model.getHaftungsausschluss() == 0)
                buttonDesclaimer.setChecked(false);
            else
                buttonDesclaimer.setChecked(true);

            if (model.getWaldweg() == 0)
                buttonAccessForest.setChecked(false);
            else
                buttonAccessForest.setChecked(true);

            if (model.getVerdachtAbwasserkanal() == 0)
                buttonVerdSewers.setChecked(false);
            else
                buttonVerdSewers.setChecked(true);

            if (model.getStuetzmaterial() == 0)
                buttonAdditionalSupportingMaterial.setChecked(false);
            else
                buttonAdditionalSupportingMaterial.setChecked(true);

            if (!TextUtils.isEmpty(model.getSchaltafeln())) {
                if (model.getSchaltafeln().equals("0"))
                    labelperformWorkLabels.setChecked(false);
                else
                {
                    labelperformWorkLabels.setChecked(true);
                    textStk.setText(model.getSchaltafeln());
                }
            }

            if (!TextUtils.isEmpty(model.getFahrbleche())) {
                if (model.getFahrbleche().equals("0"))
                    LabelDrivingPlates.setChecked(false);
                else
                    LabelDrivingPlates.setChecked(true);
            }

            if (!TextUtils.isEmpty(model.getBongossiPlatten())) {
                if (model.getBongossiPlatten().equals("0"))
                    labelBongossiPlates.setChecked(false);
                else
                    labelBongossiPlates.setChecked(true);
            }

            if (!TextUtils.isEmpty(model.getPublicRoadCountry())) {
                if (model.getPublicRoadCountry().equals("0"))
                    labelPublicRoadCountry.setChecked(false);
                else
                    labelPublicRoadCountry.setChecked(true);
            }

            if (!TextUtils.isEmpty(model.getPrivateProperty())) {
                if (model.getPrivateProperty().equals("0"))
                    labelPrivateProperty.setChecked(false);
                else
                    labelPrivateProperty.setChecked(true);
            }

            if (!TextUtils.isEmpty(model.getInterior())) {
                if (model.getInterior().equals("0"))
                    labelInterior.setChecked(false);
                else
                    labelInterior.setChecked(true);
            }

            if (!TextUtils.isEmpty(model.getOutdoor())) {
                if (model.getOutdoor().equals("0"))
                    labelOutdoor.setChecked(false);
                else
                    labelOutdoor.setChecked(true);
            }
            textDirections.setText(model.getWegbeschreibung());
            textWhich.setText(model.getHindernisText());
            textFor.setText(model.getHaftungsausschlussText());
            textAccessLying.setText(model.getZufahrtAuslegen());
            if(!TextUtils.isEmpty(model.getTragslastZufahrt())) {
                textLoadAccess.setText(DataHelper.getGermanFromEnglishBvo(model.getTragslastZufahrt()));
            }
            GlobalMethods.setBlankValueForZero(textLoadAccess);
            if(!TextUtils.isEmpty(model.getTraglastStandplatz())) {
                textLoadText.setText(DataHelper.getGermanFromEnglishBvo(model.getTraglastStandplatz()));
            }
            GlobalMethods.setBlankValueForZero(textLoadText);
            textMaxTrafficLoad.setText(model.getVerkehrslastmax());
            textLoadStand.setText(model.getStuetzmaterialText());
            textUndergroundStand.setText(model.getUntergrund());

            if(!TextUtils.isEmpty(model.getStassengefaelle())) {
                textRoadGrade.setText(DataHelper.getGermanFromEnglishBvo(model.getStassengefaelle()));
            }
            GlobalMethods.setBlankValueForZero(textRoadGrade);
            textNotices.setText(model.getHinweise());

            ArrayList<SiteInspectionPracticabilityModel> listOfPracticability = new ArrayList<>();
            listOfPracticability = model.getListOfPracticability();
            if (listOfPracticability != null) {
                for (int i = 0; i < listOfPracticability.size(); i++) {
                    if (listOfPracticability.get(i).getBefahrbarkeit().equals("1"))
                        labelSummer.setChecked(true);
                    else if (listOfPracticability.get(i).getBefahrbarkeit().equals("2"))
                        labelWinter.setChecked(true);
                    else if (listOfPracticability.get(i).getBefahrbarkeit().equals("3"))
                        labelWetness.setChecked(true);
                    else if (listOfPracticability.get(i).getBefahrbarkeit().equals("4")) {
                        labelOther.setChecked(true);
                        textOther.setText(listOfPracticability.get(i).getBefahrbarkeitText());
                    }
                }
            }
        }
    }
}
