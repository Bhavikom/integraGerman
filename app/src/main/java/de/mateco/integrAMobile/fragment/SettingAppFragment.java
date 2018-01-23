package de.mateco.integrAMobile.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import de.mateco.integrAMobile.Helper.Constants;
import de.mateco.integrAMobile.Helper.GlobalMethods;
import de.mateco.integrAMobile.Helper.InputFilterMinMax;
import de.mateco.integrAMobile.Helper.ItemXMLHandler;
import de.mateco.integrAMobile.Helper.PreferencesClass;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.LoginActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.SpinnerLanguageAdapter;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.Language;

public class SettingAppFragment extends BaseFragment implements View.OnClickListener
{
    private ArrayList<Language> languageList;
    private ArrayList<String> languages;
    private ArrayList<String> languagesFlag;
    private Language language;
    private View rootView;
    private MatecoPriceApplication matecoPriceApplication;
    private OnLanguageSelected mCallback;
    private Button buttonCustomerOfflineData, buttonSaveCustomerPageSize, buttonSaveBVOUploadLimit;
    private Spinner spinnerLanguageSelector;
    private SharedPreferences preferences;
    private Button buttonLogout;
    PreferencesClass prefernces2;
    private EditText textCustomerPageSize, textBVOUploadLimit;

    public interface OnLanguageSelected
    {
        void onLanguageUpdated();
    }



    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            mCallback = (OnLanguageSelected) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_send_visiting_card, container, false);
        super.initializeFragment(rootView);

        return rootView;
    }

    @Override
    public void initializeComponents(View rootView)
    {
        prefernces2 = new PreferencesClass(getActivity());
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        preferences = getActivity().getSharedPreferences("Language", Context.MODE_PRIVATE);
        String selectedLanaguage = preferences.getString("language", "de");
        languageList = new ArrayList<Language>();
        languages = new ArrayList<String>();
        languagesFlag = new ArrayList<String>();
        try
        {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            ItemXMLHandler myXmlHandler = new ItemXMLHandler();
            xr.setContentHandler(myXmlHandler);
            xr.parse(new InputSource(getActivity().getAssets().open("LanguageXml.txt")));
            languageList = myXmlHandler.getItemsList();
            for(int i = 0; i < languageList.size(); i++)
            {
                language = languageList.get(i);
                languagesFlag.add(language.getLangFlag());
                languages.add(language.getLangName());
            }
        }
        catch (SAXException ex)
        {
            ex.printStackTrace();
        }
        catch (ParserConfigurationException ex)
        {
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        spinnerLanguageSelector = (Spinner)rootView.findViewById(R.id.spinnerLanguageSelector);
        SpinnerLanguageAdapter spinnerLanguageAdapter = new SpinnerLanguageAdapter(getActivity(), languages, languagesFlag);
        spinnerLanguageSelector.setAdapter(spinnerLanguageAdapter);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        for(int i = 0; i < languageList.size(); i++)
        {
            if(selectedLanaguage.equals(languageList.get(i).getLangCode()))
            {
                language = languageList.get(i);
                spinnerLanguageSelector.setSelection(i);
                break;
            }
        }
        textCustomerPageSize = (EditText)rootView.findViewById(R.id.textCustomerPageSize);
        textBVOUploadLimit = (EditText)rootView.findViewById(R.id.textBVOUploadLimit);
        buttonCustomerOfflineData = (Button)rootView.findViewById(R.id.buttonCustomerOfflineData);
        buttonSaveCustomerPageSize = (Button)rootView.findViewById(R.id.buttonSaveCustomerPageSize);
        buttonLogout = (Button)rootView.findViewById(R.id.buttonLogout);
        buttonSaveBVOUploadLimit = (Button)rootView.findViewById(R.id.buttonSaveBVOUploadLimit);

        buttonLogout.setText(language.getLabelLogout());
        buttonLogout.setOnClickListener(this);
        buttonSaveBVOUploadLimit.setOnClickListener(this);

        buttonSaveCustomerPageSize.setOnClickListener(this);
        textCustomerPageSize.setFilters(new InputFilter[]{new InputFilterMinMax("10", "100")});
        textCustomerPageSize.setText(matecoPriceApplication.getSettingData(Constants.CustomerPageSize, 10) + "");
        textBVOUploadLimit.setText(matecoPriceApplication.getSettingData(Constants.BVOUploadSize, 15) + "");
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelAppSetting());
        super.initializeComponents(rootView);
    }

    @Override
    public void bindEvents(View rootView)
    {
        spinnerLanguageSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                SharedPreferences.Editor editor = preferences.edit();
                language = languageList.get(i);
                matecoPriceApplication.setLanguage(language);
                editor.putString("language", language.getLangCode());
                editor.commit();
                setLanguage();
                mCallback.onLanguageUpdated();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        buttonCustomerOfflineData.setOnClickListener(this);
        super.bindEvents(rootView);
    }

    private void setLanguage()
    {
        TextView labelSettingAppLanguage, labelSettingAppCustomerOfflineData,
                labelSettingAppLogout, labelSettingCustomerPageSize, labelBVOUploadSizeHeading, labelBVOUploadSize;
        TextView labelCurrentLanguage,labelCustomerOffline,labelPageSize,labelLogout;

        //,labelSettingAppRefrshInterval;
        labelPageSize = (TextView)rootView.findViewById(R.id.labelPageSize);
        labelCurrentLanguage = (TextView)rootView.findViewById(R.id.labelCurrentLanguage);
        labelCustomerOffline = (TextView)rootView.findViewById(R.id.labelCustomerOffline);
        labelLogout = (TextView)rootView.findViewById(R.id.labelLogout);
        labelBVOUploadSizeHeading = (TextView)rootView.findViewById(R.id.labelBVOUploadSizeHeading);
        labelBVOUploadSize = (TextView)rootView.findViewById(R.id.labelBVOUploadSize);


        labelSettingAppLanguage = (TextView)rootView.findViewById(R.id.labelSettingAppLanguage);
        labelSettingAppCustomerOfflineData = (TextView)rootView.findViewById(R.id.labelSettingAppCustomerOfflineData);
        labelSettingAppLogout = (TextView)rootView.findViewById(R.id.labelSettingAppLogout);
        labelSettingCustomerPageSize = (TextView)rootView.findViewById(R.id.labelSettingCustomerPageSize);
        labelSettingAppLogout.setText(language.getLabelLogout());
//        labelSettingAppRefrshInterval = (TextView)rootView.findViewById(R.id.labelSettingAppRefrshInterval);
        labelSettingAppLanguage.setText(language.getLabelLanguage());
//        labelSettingAppRefrshInterval.setText(language.getLabelRefreshInterval());

        labelSettingAppCustomerOfflineData.setText(language.getLabelCustomerOfflineData());
        labelSettingCustomerPageSize.setText(language.getLabelCustomerPageSize());

        buttonCustomerOfflineData.setText(language.getLabelCustomerOfflineData());

        buttonSaveCustomerPageSize.setText(language.getLabelSet());
        buttonSaveBVOUploadLimit.setText(language.getLabelSet());

        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelAppSetting());
        labelCurrentLanguage.setText(language.getLabelCurrentLanguage() + ":" + language.getLangName());
        labelPageSize.setText(language.getLabelSetPages());
        labelCustomerOffline.setText(language.getLabelOfflineCustomer());
        labelLogout.setText(language.getLabelLogoutFromApp());
        labelBVOUploadSize.setText(language.getLabelBVOUploadSize());
        labelBVOUploadSizeHeading.setText(language.getLabelBVOUploadSizeHeading());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);
        menu.findItem(R.id.actionSearch).setVisible(false);
        menu.findItem(R.id.actionWrong).setVisible(false);
        menu.findItem(R.id.actionRight).setVisible(false);
        menu.findItem(R.id.actionAdd).setVisible(false);
        menu.findItem(R.id.actionEdit).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);
        //menu.findItem(R.id.actionForwardSpace).setVisible(false);
        menu.findItem(R.id.actionSettings).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.actionSettings:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new SettingFragment());
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
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonCustomerOfflineData:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new CustomerSearchOfflineFragment());
                transaction.addToBackStack("Customer Search Offline");
                transaction.commit();
                break;
            case R.id.buttonLogout:
                String title = language.getLabelConfirmation();
                String message = language.getMessageAreYouSureWantToLogout();
                DialogInterface.OnClickListener positiveCallback = new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        prefernces2.clearPreferences();
                        prefernces2.clearCombobox();
                        clearDate();

                        DataBaseHandler db = new DataBaseHandler(getActivity());
                        db.deleteAllTable();
                        matecoPriceApplication.clearPreferences();
                        getActivity().getSharedPreferences("SiteInspection",Context.MODE_PRIVATE).edit().clear().commit();
                        matecoPriceApplication.logoutUser(false);
                        getActivity().deleteDatabase("MatecoSales.db");
                        dialog.dismiss();
                        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(loginIntent);
                        getActivity().finish();
                    }
                };
                DialogInterface.OnClickListener negativeCallback = new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                };
                Dialog dialog = showAlert(title, message, language.getLabelYes(), language.getLabelNo(), positiveCallback, negativeCallback);
                dialog.show();
                break;
            case R.id.buttonSaveCustomerPageSize:
                String text = textCustomerPageSize.getText().toString();
                //textCustomerPageSize.setError(null);
                try
                {
                    int num = Integer.parseInt(text);
                    matecoPriceApplication.saveSettingData(Constants.CustomerPageSize, num);
                    showShortToast(language.getMessageSuccessfullySaved());
                }
                catch (NumberFormatException e)
                {
                    //textCustomerPageSize.setError(language.getMessageError());
                }
                break;
            case R.id.buttonSaveBVOUploadLimit:
                String text1 = textBVOUploadLimit.getText().toString();
                //textCustomerPageSize.setError(null);
                try
                {
                    int num = Integer.parseInt(text1);
                    matecoPriceApplication.saveSettingData(Constants.BVOUploadSize, num);
                    showShortToast(language.getMessageSuccessfullySaved());
                }
                catch (NumberFormatException e)
                {
                    //textCustomerPageSize.setError(language.getMessageError());
                }
                break;
        }
    }
    private void clearDate(){
        prefernces2.clearstartDate();
        prefernces2.clearendDate();
        prefernces2.clearstartTime();
        prefernces2.clearendTime();
    }
}
