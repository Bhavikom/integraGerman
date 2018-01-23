package de.mateco.integrAMobile.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;

import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.PreferencesData;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.EmployeeModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LoginPersonModel;

public class ExtraSendVisitingCardFragment extends BaseFragment implements View.OnClickListener
{
    PreferencesData preferences;
    private Language language;
    private View rootView;
    private MatecoPriceApplication matecoPriceApplication;
    private Button buttonShareInformation;

    ArrayList<EmployeeModel> listOfEmployee = new ArrayList<>();
    private LinearLayout LayoutVisitingCard;
    private TextView labelName,labelFirm,labelPosition,labelInternet,labelEmail,labelWebsite,
            labelPhone,labelBusiness,labelMobile,labelAddress,labelBusinessAddress;
    private TextView textName,textFirm,textPosition,textEmail,textWebsite,
            textBusiness,textMobile,textBusinessAddress;
    private File vcfFile;
    int pos;
    private String gender,company,title,street,city;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.layout_visiting_card, container, false);
        //rootView = inflater.inflate(R.layout.temp_under_construction, container, false);
        super.initializeFragment(rootView);

        return rootView;
    }

    @Override
    public void initializeComponents(View rootView)
    {
        preferences = new PreferencesData(getActivity());
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();

        textName = (EditText)rootView.findViewById(R.id.textName);
        textFirm = (EditText)rootView.findViewById(R.id.textFirm);
        textPosition = (EditText)rootView.findViewById(R.id.textPosition);
        textEmail = (EditText)rootView.findViewById(R.id.textEmail);
        textWebsite = (EditText)rootView.findViewById(R.id.textWebsite);
        textBusiness = (EditText)rootView.findViewById(R.id.textBusiness);
        textMobile = (EditText)rootView.findViewById(R.id.textMobile);
        textBusinessAddress = (EditText)rootView.findViewById(R.id.textBusinessAddress);

        textName.setEnabled(false);
        textFirm.setEnabled(false);
        textPosition.setEnabled(false);
        textEmail.setEnabled(false);
        textWebsite.setEnabled(false);
        textBusiness.setEnabled(false);
        textMobile.setEnabled(false);
        textBusinessAddress.setEnabled(false);

        labelName = (TextView)rootView.findViewById(R.id.labelName);
        labelFirm = (TextView)rootView.findViewById(R.id.labelFirm);
        labelPosition = (TextView)rootView.findViewById(R.id.labelPosition);
        labelInternet = (TextView)rootView.findViewById(R.id.labelInternet);
        labelEmail = (TextView)rootView.findViewById(R.id.labelEmail);
        labelWebsite = (TextView)rootView.findViewById(R.id.labelWebsite);
        labelPhone = (TextView)rootView.findViewById(R.id.labelPhone);
        labelBusiness = (TextView)rootView.findViewById(R.id.labelBusiness);
        labelMobile = (TextView)rootView.findViewById(R.id.labelMobile);
        labelAddress = (TextView)rootView.findViewById(R.id.labelAddress);
        labelBusinessAddress = (TextView)rootView.findViewById(R.id.labelBusinessAddress);

        labelName.setText(language.getLabelName());
        labelFirm.setText(language.getLabelFirm());
        labelEmail.setText(language.getLabelEmail());
        labelWebsite.setText(language.getLabelWebsite());
        labelPhone.setText(language.getLabelTelefonnummern());
        labelBusiness.setText(language.getLabelBusinessPhone());
        labelMobile.setText(language.getLabelMobiltelefon());
        labelAddress.setText(language.getLabelAddress());
        labelBusinessAddress.setText(language.getLabelBusinessPhone());

        if(!preferences.getFunction().equalsIgnoreCase("")){

        textPosition.setText(preferences.getFunction());
        }
        if(!preferences.getAddress().equalsIgnoreCase("")){
        textBusinessAddress.setText(preferences.getAddress());
        }
         if(!preferences.getFirm().equalsIgnoreCase("")){
        textFirm.setText(preferences.getFirm());
        }

        String loginPersonId = matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new ArrayList<LoginPersonModel>().toString()).get(0).getUserNumber();
        String userInformation = "";

        DataBaseHandler db = new DataBaseHandler(getActivity());
        listOfEmployee = db.getEmployees();
        for(int i = 0; i < listOfEmployee.size(); i++)
        {
            if(listOfEmployee.get(i).getMitarbeiter().equals(loginPersonId))
            {
                pos = i;
                String url="";
                if(DataHelper.isNetworkAvailable(context)){
                    BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult() {
                        @TargetApi(Build.VERSION_CODES.KITKAT)
                        @Override
                        public void OnAsynResult(String result) {
                            try {
                                JSONObject json = new JSONObject(result);
                                gender = json.getString("Anrede");
                                company = json.getString("Firma");
                                title = json.getString("Funktion");
                                street = json.getString("Strasse");
                                city = json.getString("City");

                                if(company == null || company.equalsIgnoreCase("null")){
                                    textFirm.setText("");
                                    preferences.saveFirm("");
                                }else {
                                    textFirm.setText(company+"");
                                    preferences.saveFirm(company+"");
                                }
                                if(title == null || title.equalsIgnoreCase("null")) {
                                    textPosition.setText("");
                                    preferences.saveFunction("");
                                }else {
                                    textPosition.setText(title + "");
                                    preferences.saveFunction(title);

                                }
                                if(street == null || city == null || street.equalsIgnoreCase("null") || city.equalsIgnoreCase("null")) {
                                    textBusinessAddress.setText("");
                                    preferences.saveAddress("");
                                }else {
                                    textBusinessAddress.setText(street + " " + city);
                                    preferences.saveAddress(street + " " + city);
                                }


                                textWebsite.setText("www.mateco.de");
                                vcfFile = new File(Environment.getExternalStorageDirectory(),  gender + "_" +
                                        listOfEmployee.get(pos).getVorname() + "_" + listOfEmployee.get(pos).getNachname()
                                        + ".vcf");

                                //FileWriter fw = null;
                                try {
                                    if (Build.VERSION.SDK_INT >= 23) {
                                        if (ContextCompat.checkSelfPermission(getActivity(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                                                == PackageManager.PERMISSION_GRANTED) {
                                            FileOutputStream fos = new FileOutputStream(vcfFile);
                                            Writer out = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                                            //out = new FileWriter(vcfFile);
                                            out.write("BEGIN:VCARD\r\n");
                                            out.write("VERSION:3.0\r\n");
                                            out.write("FN:" + listOfEmployee.get(pos).getVorname() + " " + listOfEmployee.get(pos).getNachname() + "\r\n");
                                            out.write("ORG:" + company + "\r\n");
                                            out.write("TITLE;CHARSET=UTF-8:" + title + "\r\n");
                                            //out.write("ADR;TYPE=WORK:" + street + ";" + city + "\r\n");
                                            out.write("ADR;WORK:;;" + street + ";" + city + "\r\n");
                                            out.write("TEL;TYPE=CELL,VOICE:" + listOfEmployee.get(pos).getMobil() + "\r\n");
                                            out.write("TEL;TYPE=WORK,VOICE:" + listOfEmployee.get(pos).getTelefon() + "\r\n");
                                            out.write("TEL;TYPE=WORK FAX:" + listOfEmployee.get(pos).getTelefax() + "\r\n");
                                            out.write("EMAIL;PREF,INTERNET:" + listOfEmployee.get(pos).getEMail() + "\r\n");
                                            //out.write("URL;TYPE=WORK:"+ "https://www.mateco.de" + "\r\n");
                                            out.write("URL;WORK:www.mateco.de\r\n");
                                            out.write("END:VCARD\r\n");
                                            out.close();

                                        } else {
                                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                    Manifest.permission.READ_EXTERNAL_STORAGE}, 2002);
                                        }
                                    }
                                    else {
                                        //permission is automatically granted on sdk<23 upon installation
                                        FileOutputStream fos = new FileOutputStream(vcfFile);
                                        Writer out = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                                        //out = new FileWriter(vcfFile);
                                        out.write("BEGIN:VCARD\r\n");
                                        out.write("VERSION:3.0\r\n");
                                        out.write("FN:" + listOfEmployee.get(pos).getVorname() + " " + listOfEmployee.get(pos).getNachname() + "\r\n");
                                        out.write("ORG:" + company + "\r\n");
                                        out.write("TITLE;CHARSET=UTF-8:" + title + "\r\n");
                                        //out.write("ADR;TYPE=WORK:" + street + ";" + city + "\r\n");
                                        out.write("ADR;WORK:;;" + street + ";" + city + "\r\n");
                                        out.write("TEL;TYPE=CELL,VOICE:" + listOfEmployee.get(pos).getMobil() + "\r\n");
                                        out.write("TEL;TYPE=WORK,VOICE:" + listOfEmployee.get(pos).getTelefon() + "\r\n");
                                        out.write("TEL;TYPE=WORK FAX:" + listOfEmployee.get(pos).getTelefax() + "\r\n");
                                        out.write("EMAIL;PREF,INTERNET:" + listOfEmployee.get(pos).getEMail() + "\r\n");
                                        //out.write("URL;TYPE=WORK:"+ "https://www.mateco.de" + "\r\n");
                                        out.write("URL;WORK:www.mateco.de\r\n");
                                        out.write("END:VCARD\r\n");
                                        out.close();
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    try
                    {
                        /*url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.GET_VISITING_CARD_DATA
                                + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                                + "&userName=" + listOfEmployee.get(i).getVorname().substring(0,1) + listOfEmployee.get(i).getNachname();*/
                        url = DataHelper.URL_USER_HELPER +"getvisitingcard/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                                + "/userName=" + listOfEmployee.get(i).getVorname().substring(0,1) + listOfEmployee.get(i).getNachname();
                    }
                    catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                    asyncTask.execute();


                }

                break;
            }
        }

        if(listOfEmployee != null && listOfEmployee.size() > 0){
            textName.setText(listOfEmployee.get(pos).getVorname()  + " " + listOfEmployee.get(pos).getNachname());
            textEmail.setText(listOfEmployee.get(pos).getEMail());
            textBusiness.setText(listOfEmployee.get(pos).getTelefon());
            textMobile.setText(listOfEmployee.get(pos).getMobil());
        }
        textWebsite.setText("www.mateco.de");



        buttonShareInformation = (Button)rootView.findViewById(R.id.buttonShareInformation);
        buttonShareInformation.setOnClickListener(this);

        setLanguage();
        getActivity().invalidateOptionsMenu();
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelSendVisitingCard());
        setHasOptionsMenu(true);
        super.initializeComponents(rootView);
    }

    @Override
    public void bindEvents(View rootView)
    {
        super.bindEvents(rootView);
    }

    private void setLanguage()
    {

        buttonShareInformation.setText(language.getLabelShare());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);
        menu.findItem(R.id.actionSearch).setVisible(false);
        menu.findItem(R.id.actionBack).setVisible(false);
        menu.findItem(R.id.actionWrong).setVisible(false);
        menu.findItem(R.id.actionRight).setVisible(false);
        menu.findItem(R.id.actionAdd).setVisible(false);
        menu.findItem(R.id.actionEdit).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);
        //return true;
        //super.onCreateOptionsMenu(menu, inflater);
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
            case R.id.buttonShareInformation:
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (vcfFile != null) {
                            Uri photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", vcfFile);
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            //sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(vcfFile));
                            sendIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
                            sendIntent.setType("text/x-vcard");
                            sendIntent.setType("application/x-vcard");
                            sendIntent.setType("vnd.android.cursor.dir/email");
                            sendIntent.putExtra(Intent.EXTRA_SUBJECT, language.getLabelVisitingCardSubject() + " " + gender + " " + listOfEmployee.get(pos).getVorname() + " " + listOfEmployee.get(pos).getNachname());
                            startActivity(sendIntent);
                        }
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{
                                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                3003);
                    }
                }
                else {
                    Uri photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", vcfFile);
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
                    sendIntent.setType("text/x-vcard");
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, language.getLabelVisitingCardSubject() + " " + gender + " " + listOfEmployee.get(pos).getVorname() + " " + listOfEmployee.get(pos).getNachname());
                    startActivity(sendIntent);
                }

                break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            if(requestCode == 3003){
                if (vcfFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", vcfFile);
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
                    sendIntent.setType("text/x-vcard");
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, language.getLabelVisitingCardSubject() + " " + gender + " " + listOfEmployee.get(pos).getVorname() + " " + listOfEmployee.get(pos).getNachname());
                    startActivity(sendIntent);
                }
            }else {
                //resume tasks needing this permission
                try {
                    FileOutputStream fos = new FileOutputStream(vcfFile);
                    Writer out = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                    //out = new FileWriter(vcfFile);
                    out.write("BEGIN:VCARD\r\n");
                    out.write("VERSION:3.0\r\n");
                    out.write("FN:" + listOfEmployee.get(pos).getVorname() + " " + listOfEmployee.get(pos).getNachname() + "\r\n");
                    out.write("ORG:" + company + "\r\n");
                    out.write("TITLE;CHARSET=UTF-8:" + title + "\r\n");
                    //out.write("ADR;TYPE=WORK:" + street + ";" + city + "\r\n");
                    out.write("ADR;WORK:;;" + street + ";" + city + "\r\n");
                    out.write("TEL;TYPE=CELL,VOICE:" + listOfEmployee.get(pos).getMobil() + "\r\n");
                    out.write("TEL;TYPE=WORK,VOICE:" + listOfEmployee.get(pos).getTelefon() + "\r\n");
                    out.write("TEL;TYPE=WORK FAX:" + listOfEmployee.get(pos).getTelefax() + "\r\n");
                    out.write("EMAIL;PREF,INTERNET:" + listOfEmployee.get(pos).getEMail() + "\r\n");
                    //out.write("URL;TYPE=WORK:"+ "https://www.mateco.de" + "\r\n");
                    out.write("URL;WORK:www.mateco.de\r\n");
                    out.write("END:VCARD\r\n");
                    out.close();
                }
                catch (Exception e){

                }
            }


        }
    }
}
