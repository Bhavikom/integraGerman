package de.mateco.integrAMobile.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.LogApp;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.MailListAdapter;
import de.mateco.integrAMobile.adapter.Pricing1BranchDataAdapter;
import de.mateco.integrAMobile.asyncTask.AsyncTaskWithAuthorizationHeaderPost;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LoginPersonModel;
import de.mateco.integrAMobile.model.MailAddressModel;
import de.mateco.integrAMobile.model.ResponseFormat;
import de.mateco.integrAMobile.model.SiteInspectionModel;
import de.mateco.integrAMobile.model.SiteInspectionOperationalEnvironmentModel;
import de.mateco.integrAMobile.model_logonsquare.PriceBranchListItem;

public class SiteInspectionSelectMailFragment extends BaseFragment
{
    private View rootView;
    private Spinner spinnerSiteInspectionMailBranch;
    private ArrayList<PriceBranchListItem> listOfBranch;
    private MatecoPriceApplication matecoPriceApplication;
    private Language language;
    private DataBaseHandler db;
    private SharedPreferences preferences;
    private ListView listViewSiteInspectionbranchMailAddress;
    private MailListAdapter adapter;
    private ArrayList<MailAddressModel> listOfAllMail;
    private ArrayList<String> listOfSelectedMail;
    private CheckBox checkBoxSentMailToEmployeeAdress;
    private Pricing1BranchDataAdapter branchAdapter;
    private EditText textEmailBodyContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_site_inspection_select_mail, container, false);
        super.initializeFragment(rootView);

        return rootView;
    }

    @Override
    public void initializeComponents(View rootView)
    {
        super.initializeComponents(rootView);

        preferences = getActivity().getSharedPreferences("SiteInspection", Context.MODE_PRIVATE);
        matecoPriceApplication = (MatecoPriceApplication) getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        db = new DataBaseHandler(getActivity());
        spinnerSiteInspectionMailBranch = (Spinner)rootView.findViewById(R.id.spinnerSiteInspectionMailBranch);
        textEmailBodyContent = (EditText)rootView.findViewById(R.id.textEmailBodyContent);
        textEmailBodyContent.setText(db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId, 0)).getEmailBodyContent());
        listOfBranch = new ArrayList<>();
        listOfBranch = db.getPricing1BranchData();
        branchAdapter = new Pricing1BranchDataAdapter(getActivity(), listOfBranch, language);
        spinnerSiteInspectionMailBranch.setAdapter(branchAdapter);
        listViewSiteInspectionbranchMailAddress = (ListView)rootView.findViewById(R.id.listViewSiteInspectionbranchMailAddress);
        listOfAllMail = new ArrayList<>();
        listOfSelectedMail = new ArrayList<>();
        adapter = new MailListAdapter(getActivity(), listOfAllMail);
        listViewSiteInspectionbranchMailAddress.setAdapter(adapter);
        checkBoxSentMailToEmployeeAdress = (CheckBox)rootView.findViewById(R.id.checkBoxSentMailToEmployeeAdress);
        String customerBranch;
        customerBranch = matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new ArrayList<LoginPersonModel>().toString()).get(0).getLocation();
        boolean isbranchSeleted = false;
        for(int i = 0; i < listOfBranch.size(); i++)
        {
            if(customerBranch.equals(listOfBranch.get(i).getBezeichnung()))
            {
                spinnerSiteInspectionMailBranch.setSelection(i + 1);
                //getEmailAddress(listOfBranch.get(i + 1));
                getEmailAddress(listOfBranch.get(i));
                isbranchSeleted = true;
                break;
            }
            else if(customerBranch.contains(listOfBranch.get(i).getBezeichnung()))
            {
                spinnerSiteInspectionMailBranch.setSelection(i + 1);
                //getEmailAddress(listOfBranch.get(i + 1));
                getEmailAddress(listOfBranch.get(i));
                isbranchSeleted = true;
                break;
            }
        }
        if(!isbranchSeleted)
        {
            spinnerSiteInspectionMailBranch.setSelection(0);
        }

        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelEmailAddress());

        setupLanguage();
    }

    @Override
    public void bindEvents(View rootView)
    {
        spinnerSiteInspectionMailBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0)
                {
                    listOfAllMail.clear();
                    listOfSelectedMail.clear();
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    getEmailAddress(listOfBranch.get(position - 1));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        listViewSiteInspectionbranchMailAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.mCheckStates.get(position))
                    adapter.mCheckStates.put(position, false);
                else
                    adapter.mCheckStates.put(position, true);
                adapter.notifyDataSetChanged();
            }
        });

        super.bindEvents(rootView);
    }

    private void setupLanguage()
    {
        TextView labelSiteInspectionBranch, labelSiteInspectionEmailAddress, labelSiteInspectionMailSelectionEmailAddress, labelSiteInspectionMailSelectionEmailDesignation, labelEmailBodyContent;

        TextView labelInspectionMailSelectionSelectMailId;
        labelSiteInspectionBranch = (TextView)rootView.findViewById(R.id.labelSiteInspectionBranch);
        labelSiteInspectionBranch.setText(language.getLabelBranch());
        labelSiteInspectionEmailAddress = (TextView)rootView.findViewById(R.id.labelSiteInspectionEmailAddress);
        labelSiteInspectionEmailAddress.setText(language.getLabelEmailAddress());
        labelSiteInspectionMailSelectionEmailAddress = (TextView)rootView.findViewById(R.id.labelSiteInspectionMailSelectionEmailAddress);
        labelSiteInspectionMailSelectionEmailAddress.setText(language.getLabelEmailAddress());
        labelSiteInspectionMailSelectionEmailDesignation = (TextView)rootView.findViewById(R.id.labelSiteInspectionMailSelectionEmailDesignation);
        labelSiteInspectionMailSelectionEmailDesignation.setText(language.getLabelDesignation());
        labelInspectionMailSelectionSelectMailId = (TextView)rootView.findViewById(R.id.labelInspectionMailSelectionSelectMailId);
        labelEmailBodyContent = (TextView)rootView.findViewById(R.id.labelEmailBodyContent);
        labelEmailBodyContent.setText(language.getLabelEmailBody());
        labelInspectionMailSelectionSelectMailId.setText(language.getLabelSelectText());
        checkBoxSentMailToEmployeeAdress.setText(language.getLabelSendEmailToMe());
    }

    private void getEmailAddress(PriceBranchListItem  branchObject)
    {
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
                    else
                    {
                        try
                        {
                            listOfAllMail.clear();
                            listOfSelectedMail.clear();
                            MailAddressModel.extractFromJson(result, listOfAllMail);
                            adapter.notifyDataSetChanged();
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                            showShortToast(language.getMessageErrorAtParsing());
                        }
                    }
                }
            };
            try
            {
                String url = DataHelper.URL_BVO_HELPER+"bvoinfoemails";// + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.SITEINSPECTION_GET_EMAIL_FROM_BRANCH;
                url += "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                url += "/branch=" + branchObject.getMandant();
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                asyncTask.execute();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        else
        {
            showShortToast(language.getMessageNetworkNotAvailable());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_site_inspection_permits, menu);
        //menu.findItem(R.id.actionForward).setVisible(false);
        menu.findItem(R.id.actionAdd).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);
        menu.findItem(R.id.actionSave).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.actionSettings:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new SettingFragment(),"Setting");
                //transaction.addToBackStack(SettingFragment.Tag);
                transaction.addToBackStack("Setting");
                transaction.commit();
                return true;
            case R.id.actionBack:
                db.updateSiteInspectionEmailBody(preferences.getInt(DataHelper.SiteInspectionId, 0), textEmailBodyContent.getText().toString().trim());
                if (getFragmentManager().getBackStackEntryCount() == 0)
                {
                    getActivity().finish();
                }
                else
                {
                    getFragmentManager().popBackStack();
                }
                return true;
            case R.id.actionRight:
                DialogInterface.OnClickListener positiveCallback = new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(getActivity()!=null)
                        {
                            if(DataHelper.isNetworkAvailable(getActivity()))
                            {
                                //showShortToast(DataHelper.getFileSize(getActivity())+"");
                                if(!DataHelper.isFileSizeGreaterThan10MB(getActivity()))
                                {
                                    uploadData();
                                }
                                else
                                {
                                    listOfSelectedMail.clear();
                                    if (!listOfAllMail.isEmpty() || checkBoxSentMailToEmployeeAdress.isChecked())
                                    {
                                        for (int i = 0; i < listOfAllMail.size(); i++)
                                        {
                                            if (adapter.mCheckStates.get(i) == true)
                                            {
                                                listOfSelectedMail.add(listOfAllMail.get(i).getEmail());
                                            }
                                        }
                                        if (checkBoxSentMailToEmployeeAdress.isChecked())
                                        {
                                            listOfSelectedMail.add(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new ArrayList<LoginPersonModel>().toString()).get(0).getUserEmail());
                                        }
                                        if (listOfSelectedMail.size() > 0 || checkBoxSentMailToEmployeeAdress.isChecked())
                                        {
                                            if(DataHelper.isWifiConnected(getActivity()))
                                            {
                                                uploadData();
                                            }
                                            else
                                            {
                                                db.updateSelectEmail(listOfSelectedMail, preferences.getInt(DataHelper.SiteInspectionId, 0));
                                                db.updateSiteInspectionUpload(preferences.getInt(DataHelper.SiteInspectionId, 0), "", 1);
                                                db.updateSiteInspectionEmailBody(preferences.getInt(DataHelper.SiteInspectionId, 0), textEmailBodyContent.getText().toString().trim());
                                                preferences.edit().clear().commit();
                                                showLongToast(language.getMessageBvoSentByWifi());
                                                getActivity().getSupportFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
                                            }
                                        }
                                        else
                                        {
                                            showShortToast(language.getMessagePleaseSelectOneEmailId());
                                        }
                                    }
                                    else if(spinnerSiteInspectionMailBranch.getSelectedItemPosition() == 0)
                                    {
                                        showShortToast(language.getMessagePleaseSelectBranch());
                                    }
                                    else
                                    {
                                        showShortToast(language.getMessagePleaseSelectOneEmailId());
                                    }
                                }
                            }
                            else
                            {
                                listOfSelectedMail.clear();
                                if (!listOfAllMail.isEmpty() || checkBoxSentMailToEmployeeAdress.isChecked())
                                {
                                    for (int i = 0; i < listOfAllMail.size(); i++) {
                                        if (adapter.mCheckStates.get(i) == true) {
                                            listOfSelectedMail.add(listOfAllMail.get(i).getEmail());
                                        }
                                    }
                                    if (checkBoxSentMailToEmployeeAdress.isChecked()) {
                                        listOfSelectedMail.add(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new ArrayList<LoginPersonModel>().toString()).get(0).getUserEmail());
                                    }
                                    if (listOfSelectedMail.size() > 0 || checkBoxSentMailToEmployeeAdress.isChecked()) {
                                        db.updateSelectEmail(listOfSelectedMail, preferences.getInt(DataHelper.SiteInspectionId, 0));
                                        db.updateSiteInspectionUpload(preferences.getInt(DataHelper.SiteInspectionId, 0), "", 1);
                                        db.updateSiteInspectionEmailBody(preferences.getInt(DataHelper.SiteInspectionId, 0), textEmailBodyContent.getText().toString().trim());
                                        preferences.edit().clear().commit();
                                        showLongToast(language.getMessageNetworkNotAvailable());
                                        getActivity().getSupportFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
                                    } else {
                                        showShortToast(language.getMessagePleaseSelectOneEmailId());
                                    }
                                } else {
                                    showShortToast(language.getMessagePleaseSelectBranch());
                                }
                            }
                        }
                    }
                };
                DialogInterface.OnClickListener negativeCallback = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                AlertDialog alert = showAlert(language.getLabelNote(),language.getMessageBvoConfirm(),language.getLabelYes(), language.getLabelNo(),
                        positiveCallback, negativeCallback);
                alert.show();
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

                return true;
        }
    }

    private void uploadData()
    {
        listOfSelectedMail.clear();
        if(!listOfAllMail.isEmpty() || checkBoxSentMailToEmployeeAdress.isChecked())
        {
            for(int i = 0; i < listOfAllMail.size(); i++)
            {
                if(adapter.mCheckStates.get(i) == true)
                {
                    listOfSelectedMail.add(listOfAllMail.get(i).getEmail());
                }
            }
            if(checkBoxSentMailToEmployeeAdress.isChecked())
            {
                listOfSelectedMail.add(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new ArrayList<LoginPersonModel>().toString()).get(0).getUserEmail());
            }
            if(listOfSelectedMail.size() > 0 || checkBoxSentMailToEmployeeAdress.isChecked())
            {
                SiteInspectionModel siteInspectionModel = db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId, 0));
                SiteInspectionOperationalEnvironmentModel siteInspectionOperationalEnvironmentModel = siteInspectionModel.getSiteInspectionOperationalEnvironmentModel();
                siteInspectionModel.setListOfEmailAddress(listOfSelectedMail);
                siteInspectionModel.setEmailBodyContent(textEmailBodyContent.getText().toString());
                //Log.e(" datum "," get siteInseprction model : "+siteInspectionModel.getSiteInspectionNewModel().getEinsatzdatum());
                final String json = new Gson().toJson(siteInspectionModel);
                String url = null;
                final String deviceList = new Gson().toJson(db.getDeviceByID(preferences.getInt(DataHelper.SiteInspectionId,0)));
                String practicabilityList="";
                if(siteInspectionOperationalEnvironmentModel != null){
                    practicabilityList = new Gson().toJson(siteInspectionOperationalEnvironmentModel.getListOfPracticability());
                }
                if(DataHelper.isNetworkAvailable(getActivity()))
                {
                    AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult()
                    {
                        @Override
                        public void OnAsynResult(String result)
                        {
                            if(result.equals(DataHelper.NetworkError)){
                            showShortToast(language.getMessageNetworkNotAvailable());
                            }else {
                                try {
                                    ResponseFormat responseFormat = new Gson().fromJson(result, ResponseFormat.class);
                                    if (responseFormat.getResponseCode() == 10) {
                                        db.updateSelectEmail(listOfSelectedMail, preferences.getInt(DataHelper.SiteInspectionId, 0));
                                        db.updateSiteInspectionEmailBody(preferences.getInt(DataHelper.SiteInspectionId, 0), textEmailBodyContent.getText().toString().trim());
                                        //db.updateSiteInspectionUpload(preferences.getInt(DataHelper.SiteInspectionId, 0), result.substring(1, result.length() - 1), 3);
                                        db.updateSiteInspectionUpload(preferences.getInt(DataHelper.SiteInspectionId, 0), responseFormat.getBvoID(), 3);
                                        Intent intent = new Intent();
                                        intent.putExtra("bvoId", preferences.getInt(DataHelper.SiteInspectionId, 0));
                                        intent.setAction(DataHelper.bvoInsertPhotoStart);
                                        getActivity().sendBroadcast(intent);
                                        showShortToast(language.getMessageBvoSaved());
                                        DialogInterface.OnClickListener positiveButtonClick = new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {
                                                    getFragmentManager().popBackStack();
                                                }
                                            }
                                        };
                                        String message = language.getMessageBvOEmailSendSuccessful();

                                        for (int i = 0; i < listOfSelectedMail.size(); i++) {
                                            message += "\n" + listOfSelectedMail.get(i).toString();
                                        }

                                        Dialog dialog = showMessage(language.getLabelMessage(), message, language.getLabelOk(), positiveButtonClick);
                                        dialog.show();
                                    } else if (responseFormat.getResponseCode() == 20 && responseFormat.getErrorMessage().equalsIgnoreCase("MatchcodeNotMatch")) {
                                        showLongToast(language.getMessageMatchcodeNotMatch());
                                        for (int i = 1; i < getFragmentManager().getBackStackEntryCount(); i++) {
                                            getFragmentManager().popBackStack();
                                        }
                                    } else {
                                        db.updateSelectEmail(listOfSelectedMail, preferences.getInt(DataHelper.SiteInspectionId, 0));
                                        db.updateSiteInspectionEmailBody(preferences.getInt(DataHelper.SiteInspectionId, 0), textEmailBodyContent.getText().toString().trim());
                                        db.updateSiteInspectionUpload(preferences.getInt(DataHelper.SiteInspectionId, 0), "", 2);
                                        showLongToast(language.getMessageBvoUploadFailed());
                                        Random random = new Random();
                                        int n = 100;
                                /*File file = new File(Environment.getExternalStorageDirectory() + "/BvoError" + random.nextInt(n) + ".txt");
                                try {
                                    OutputStreamWriter osw;
                                    FileOutputStream f = new FileOutputStream(file);
                                    f.write(result.getBytes());
                                    osw = new OutputStreamWriter(f);
                                    osw.append("\n\n");
                                    osw.append(json);
                                    osw.append("\n\n");
                                    osw.append(deviceList);
                                    osw.flush();
                                    osw.close();
                                    f.flush();
                                    f.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }*/
                                    }
                                } catch (Exception e) {
                                    LogApp.showLog("", " exception : " + e.toString());
                                }
                            }


                        }
                    };
                    //url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.SiteInspectionInsert;
                    url = DataHelper.URL_BVO_HELPER + "bvoinsert";//DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.SiteInspectionInsert;
                    MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                    try
                    {
                        Charset chars = Charset.forName("UTF-8");
                        multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8"), chars));
                        multipartEntity.addPart("BVOInsertList", new StringBody(json, chars));
                        multipartEntity.addPart("GeraetListDetail",new StringBody(deviceList, chars));
                        multipartEntity.addPart("numberOfImages",new StringBody(db.getPhotos(preferences.getInt(DataHelper.SiteInspectionId,0)).size()+""));
                        multipartEntity.addPart("BefahrbarkeitList",new StringBody(practicabilityList, chars));
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        e.printStackTrace();
                    }
                    AsyncTaskWithAuthorizationHeaderPost asyncTask = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, getActivity(),multipartEntity ,true, language);
                    asyncTask.execute();
                }
                else
                {
                    showShortToast(language.getMessageNetworkNotAvailable());
                }
            }
            else
            {
                showShortToast(language.getMessagePleaseSelectOneEmailId());
            }
        }
        else if(spinnerSiteInspectionMailBranch.getSelectedItemPosition() == 0)
        {
            showShortToast(language.getMessagePleaseSelectBranch());
        }
        else
        {
            showShortToast(language.getMessagePleaseSelectOneEmailId());
        }
    }
}