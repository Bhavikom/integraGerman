package de.mateco.integrAMobile.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
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

import com.google.gson.Gson;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.EmployeeSorter;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.EmployeeAdapter;
import de.mateco.integrAMobile.adapter.ProjectArtAdapter;
import de.mateco.integrAMobile.asyncTask.AsyncTaskWithAuthorizationHeaderPost;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.EmployeeModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LoginPersonModel;
import de.mateco.integrAMobile.model.ProjectArtModel;
import de.mateco.integrAMobile.model.ProjectInsertModel;

public class ProjectNewFragment extends BaseFragment implements View.OnClickListener
{
    private View rootView;
    private EditText textProjectNewDescription, textProjectNewRoad, textProjectNewZipCode, textProjectNewPlace, textProjectNewMatchCode;
    private Spinner spinnerProjectNewProjectArt, spinnerProjectNewEmployee;
    private Button buttonProjectNewSubmit, buttonProjectNewCancel;
    private MatecoPriceApplication matecoPriceApplication;
    private Language language;
    private DataBaseHandler db;
    private ArrayList<EmployeeModel> listOfEmployee;
    private EmployeeAdapter adapterEmployee;
    private ArrayList<ProjectArtModel> listOfProjectArt;
    private ProjectArtAdapter adapterProjectArt;
    private ProjectArtModel selectedProjectArt;
    private EmployeeModel selectedEmployee;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_project_new, container, false);
        super.initializeFragment(rootView);
        return rootView;
    }

    @Override
    public void initializeComponents(View rootView)
    {
        super.initializeComponents(rootView);
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        db = new DataBaseHandler(getActivity());
        textProjectNewDescription = (EditText)rootView.findViewById(R.id.textProjectNewDescription);
        textProjectNewRoad = (EditText)rootView.findViewById(R.id.textProjectNewRoad);
        textProjectNewZipCode = (EditText)rootView.findViewById(R.id.textProjectNewZipCode);
        textProjectNewPlace = (EditText)rootView.findViewById(R.id.textProjectNewPlace);
        textProjectNewMatchCode = (EditText)rootView.findViewById(R.id.textProjectNewMatchCode);
        spinnerProjectNewProjectArt = (Spinner)rootView.findViewById(R.id.spinnerProjectNewProjectArt);
        spinnerProjectNewEmployee = (Spinner)rootView.findViewById(R.id.spinnerProjectNewEmployee);
        buttonProjectNewSubmit = (Button)rootView.findViewById(R.id.buttonProjectNewSubmit);
        buttonProjectNewCancel = (Button)rootView.findViewById(R.id.buttonProjectNewCancel);

        listOfProjectArt = new ArrayList<>();
        listOfProjectArt.addAll(db.getProjectArt());
        adapterProjectArt = new ProjectArtAdapter(getActivity(), listOfProjectArt, R.layout.list_item_spinner_country, language);
        spinnerProjectNewProjectArt.setAdapter(adapterProjectArt);

        listOfEmployee = new ArrayList<>();
        listOfEmployee = db.getEmployees();
        Collections.sort(listOfEmployee, new EmployeeSorter());
        /*Collections.sort(listOfEmployee, new Comparator<EmployeeModel>() {
            @Override
            public int compare(EmployeeModel s1, EmployeeModel s2) {
                return (s1.getNachname()).equalsIgnoreCase(s2.getNachname());
            }
        });*/
        adapterEmployee = new EmployeeAdapter(getActivity(), listOfEmployee, R.layout.list_item_employee);
        spinnerProjectNewEmployee.setAdapter(adapterEmployee);
        for(int i=0;i<listOfEmployee.size();i++)
        {
            if(listOfEmployee.get(i).getMitarbeiter().equals(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new ArrayList<LoginPersonModel>().toString()).get(0).getUserNumber()))
            {
                spinnerProjectNewEmployee.setSelection(i);
            }
        }
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle(language.getLabelNewProject());
        setHasOptionsMenu(true);
        setupLanguage();
    }

    @Override
    public void bindEvents(View rootView)
    {
        super.bindEvents(rootView);

        spinnerProjectNewProjectArt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (position == 0)
                {
                    selectedProjectArt = null;
                }
                else
                {
                    selectedProjectArt = listOfProjectArt.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerProjectNewEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEmployee = listOfEmployee.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        buttonProjectNewSubmit.setOnClickListener(this);
        buttonProjectNewCancel.setOnClickListener(this);
    }

    private void setupLanguage()
    {
        TextView labelProjectNewDescription, labelProjectNewProjectArt, labelProjectNewRoad, labelProjectNewZipCode, labelProjectNewPlace, labelProjectNewMatchCode,
                labelProjectNewEmployee;

        labelProjectNewDescription = (TextView)rootView.findViewById(R.id.labelProjectNewDescription);
        labelProjectNewProjectArt = (TextView)rootView.findViewById(R.id.labelProjectNewProjectArt);
        labelProjectNewRoad = (TextView)rootView.findViewById(R.id.labelProjectNewRoad);
        labelProjectNewZipCode = (TextView)rootView.findViewById(R.id.labelProjectNewZipCode);
        labelProjectNewPlace = (TextView)rootView.findViewById(R.id.labelProjectNewPlace);
        labelProjectNewMatchCode = (TextView)rootView.findViewById(R.id.labelProjectNewMatchCode);
        labelProjectNewEmployee = (TextView)rootView.findViewById(R.id.labelProjectNewEmployee);

        labelProjectNewDescription.setText(language.getLabelDescription());
        labelProjectNewProjectArt.setText(language.getLabelProjectArt());
        labelProjectNewRoad.setText(language.getLabelRoad());
        labelProjectNewZipCode.setText(language.getLabelZipCode());
        labelProjectNewPlace.setText(language.getLabelPlace());
        labelProjectNewMatchCode.setText(language.getLabelMatchCode());
        labelProjectNewEmployee.setText(language.getLabelEmployee());

        buttonProjectNewSubmit.setText(language.getLabelOk());
        buttonProjectNewCancel.setText(language.getLabelCancel());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);
        menu.findItem(R.id.actionSearch).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);
        menu.findItem(R.id.actionAdd).setVisible(false);
        menu.findItem(R.id.actionEdit).setVisible(false);
        menu.findItem(R.id.actionRight).setVisible(false);
        menu.findItem(R.id.actionWrong).setVisible(false);
        menu.findItem(R.id.actionRefresh).setVisible(false);
        //return true;
        //super.onCreateOptionsMenu(menu, inflater);
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
            case R.id.buttonProjectNewSubmit:
                createNewProject();
                break;
            case R.id.buttonProjectNewCancel:
                clearForm();
                break;
        }
    }

    private void clearForm()
    {
        textProjectNewDescription.setText("");
        textProjectNewRoad.setText("");
        textProjectNewZipCode.setText("");
        textProjectNewPlace.setText("");
        textProjectNewMatchCode.setText("");
        spinnerProjectNewProjectArt.setSelection(0);
        spinnerProjectNewEmployee.setSelection(0);
    }

    private void createNewProject()
    {
        String description = textProjectNewDescription.getText().toString();
        String typeOfProject = "";
        if(selectedProjectArt != null)
            typeOfProject = selectedProjectArt.getProjectArtId();
        String road = textProjectNewRoad.getText().toString();
        String zipCode = textProjectNewZipCode.getText().toString();
        String place = textProjectNewPlace.getText().toString();
        String matchCode = textProjectNewMatchCode.getText().toString();
        String employee="";
        if(selectedEmployee != null){
            employee = selectedEmployee.getMitarbeiter();
        }

        if(description.equals(""))
        {
            textProjectNewDescription.setError(language.getLabelPlease() + " " +
                    language.getLabelDescription() + " " + language.getLabelEnter());
            textProjectNewDescription.requestFocus();
            //showShortToast(language.getMessageEnter() + " " + language.getLabelDescription());
            //showShortToast(language.getLabelPlease() + " " + language.getLabelDescription() + " " + language.getLabelEnter());
        }
        else if(selectedProjectArt == null)
        {
            //showShortToast(language.getLabelProjectArt());
            showShortToast(language.getLabelPlease() + " " + language.getLabelProjectArt() + " " + language.getLabelEnter());
        }
        else if(place.equals(""))
        {
            textProjectNewPlace.setError(language.getLabelPlease() + " " +
                    language.getLabelPlace() + " " + language.getLabelEnter());
            textProjectNewPlace.requestFocus();
            //showShortToast(language.getMessageEnter() + " " + language.getLabelPlace());
            //showShortToast(language.getLabelPlease() + " " + language.getLabelPlace() + " " + language.getLabelEnter());
        }
        else if(matchCode.equals(""))
        {
            textProjectNewMatchCode.setError(language.getLabelPlease() + " " +
                    language.getLabelMatchCode() + " " + language.getLabelEnter());
            textProjectNewMatchCode.requestFocus();
            //showShortToast(language.getMessageEnter() + " " + language.getLabelMatchCode());
            //showShortToast(language.getLabelPlease() + " " + language.getLabelMatchCode() + " " + language.getLabelEnter());
        }
        else
        {
            ProjectInsertModel model = new ProjectInsertModel();
            model.setPLZ(zipCode);
            model.setBeschreibung(description);
            model.setMatchCode(matchCode);
            model.setMitarbeiter(employee);
            model.setOrt(place);
            model.setProjektart(typeOfProject);
            Log.e("type of project", typeOfProject);
            model.setStrasse(road);
            model.setUserID(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber());

            if(DataHelper.isNetworkAvailable(getActivity()))
            {

                AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult() {
                    @Override
                    public void OnAsynResult(String result) {
                        Log.e("result",result);
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
                                JSONObject jsonObject = new JSONObject(result);
                                //ProjectDetailGenerallyModel projectDetailGenerally = new Gson().fromJson(jsonObject.getJSONObject("ProjectGenerallyList").toString(), ProjectDetailGenerallyModel.class);
                                matecoPriceApplication.saveData(DataHelper.LoadedProjectDetailGenerallyInfo, jsonObject.getJSONObject("ProjectGenerallyList").toString());
                                matecoPriceApplication.saveData(DataHelper.LoadedProjectDetailActivityInfo, jsonObject.getJSONArray("ProjectActivityList").toString());
                                matecoPriceApplication.saveData(DataHelper.LoadedProjectDetailTradesInfo, jsonObject.getJSONArray("ProjectTradesList").toString());
                                clearForm();
                                FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                transaction1.replace(R.id.content_frame, new ProjectDetailFragment());
                                //transaction.addToBackStack(SettingFragment.Tag);
                                transaction1.addToBackStack("Project detail");
                                transaction1.commit();
                            }
                            catch (JSONException ex)
                            {
                                ex.printStackTrace();
                                showShortToast(language.getMessageErrorAtParsing());
                            }
                        }
                    }
                };
                try
                {
                    String jsonString = new Gson().toJson(model);
                    Log.e("jsonString", jsonString);
                    /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.Project_Insert
                            + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                            + "&projectinsert=" + URLEncoder.encode(jsonString, "UTF-8");*/
                    String url = DataHelper.URL_PROJECT_HELPER+"projectinsert"; //+ DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.Project_Insert
                            //+ "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                            //+ "&projectinsert=" + URLEncoder.encode(jsonString, "UTF-8");
                    Log.e("url", url);

                    MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                    multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")));
                    multipartEntity.addPart("projectinsert", new StringBody(jsonString, Charset.forName("UTF-8")));
                    AsyncTaskWithAuthorizationHeaderPost asyncTaskPost = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, getActivity(), multipartEntity, true, language);
                    asyncTaskPost.execute();


                    /*BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                    asyncTask.execute();*/
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
    }
}