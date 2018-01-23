package de.mateco.integrAMobile.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.DelayAutoCompleteTextView;
import de.mateco.integrAMobile.Helper.GlobalClass;
import de.mateco.integrAMobile.Helper.LogApp;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.ProjectSearchAdapter;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.LoadedCustomerFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.model.CustomerSearchPagingRequestModel;
import de.mateco.integrAMobile.model.HintModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.ProjectModel;
import de.mateco.integrAMobile.model.ProjectSearchPagingRequestModel;

public class ProjectSearchFragment extends LoadedCustomerFragment implements TextView.OnEditorActionListener
{
    private boolean isCallservice=true;
    private ProgressDialog progressDialog;
    String FOCUSED_EMPLOYEE="employee",FOCUSED_PLACE="place",FOCUSED_MATCHCODE="matccode",FOCUSED_DESCRIPTION="description",
            FOCUSED_TYPE_OF_PROJECT="type_of_project",FOCUSED_ADDRESS="address",FOCUSED_FROM="from",FOCUSED_TO="to";
    String lastFocusedvalue="";
    int thresHodValue=3;
    AutoCompleteSearchAdapter adapterAutocomplete;
    String jsonToSend="";
    ArrayList<HintModel> hintList = new ArrayList<>();

    LinearLayout lianearMain;
    private Language language;
    private View rootView;
    private MatecoPriceApplication matecoPriceApplication;
    private DelayAutoCompleteTextView textProjectSearchMatchCode, textProjectSearchDescription, textProjectSearchTypeOfProject, textProjectSearchStreetAddress, textProjectSearchPlace,
            textProjectSearchFrom, textProjectSearchTo, textProjectSearchEmployee;
    private ListView listViewProjects;
    private ProjectSearchAdapter adapter;
    private ArrayList<ProjectModel> listofProject;
    private int pageNuber = 1;
    private int customerCount = 0;
    private int totalPageCount = 1;
    private int pageSize = 10;
    private View footerView;
    private Button footerButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        /*if(rootView == null)
        {
            Log.e("rootview", "null");
            rootView = inflater.inflate(R.layout.fragment_project_search, container, false);
            super.initializeFragment(rootView);
        }
        else
        {
            if(rootView.getParent() != null)
                ((ViewGroup)rootView.getParent()).removeView(rootView);

        }*/
        rootView = inflater.inflate(R.layout.fragment_project_search, container, false);
        super.initializeFragment(rootView);
        return rootView;

        //rootView = inflater.inflate(R.layout.temp_under_construction, container, false);
//        super.initializeFragment(rootView);
    }

    @Override
    public void initializeComponents(View rootView)
    {
        super.initializeComponents(rootView);
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();

        lianearMain = (LinearLayout)rootView.findViewById(R.id.linearMain);
        listViewProjects = (ListView)rootView.findViewById(R.id.listViewProjects);
        listofProject = new ArrayList<>();
        adapter = new ProjectSearchAdapter(getActivity(), listofProject);
        listViewProjects.setAdapter(adapter);

        adapterAutocomplete = new AutoCompleteSearchAdapter(getActivity());

        textProjectSearchMatchCode = (DelayAutoCompleteTextView) rootView.findViewById(R.id.textProjectSearchMatchCode);
        textProjectSearchMatchCode.setThreshold(thresHodValue);
        textProjectSearchMatchCode.setAdapter(adapterAutocomplete);
        textProjectSearchMatchCode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textProjectSearchMatchCode.setText(hintModel.getHint());
            }
        });
        textProjectSearchMatchCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_MATCHCODE;
                }
            }
        });

        textProjectSearchDescription = (DelayAutoCompleteTextView)rootView.findViewById(R.id.textProjectSearchDescription);
        textProjectSearchDescription.setThreshold(thresHodValue);
        textProjectSearchDescription.setAdapter(adapterAutocomplete);
        textProjectSearchDescription.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textProjectSearchDescription.setText(hintModel.getHint());
            }
        });
        textProjectSearchDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_DESCRIPTION;
                }
            }
        });

        textProjectSearchTypeOfProject = (DelayAutoCompleteTextView)rootView.findViewById(R.id.textProjectSearchTypeOfProject);
        textProjectSearchTypeOfProject.setThreshold(thresHodValue);
        textProjectSearchTypeOfProject.setAdapter(adapterAutocomplete);
        textProjectSearchTypeOfProject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textProjectSearchTypeOfProject.setText(hintModel.getHint());
            }
        });
        textProjectSearchTypeOfProject.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_TYPE_OF_PROJECT;
                }
            }
        });

        textProjectSearchStreetAddress = (DelayAutoCompleteTextView)rootView.findViewById(R.id.textProjectSearchStreetAddress);
        textProjectSearchStreetAddress.setThreshold(thresHodValue);
        textProjectSearchStreetAddress.setAdapter(adapterAutocomplete);
        textProjectSearchStreetAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textProjectSearchStreetAddress.setText(hintModel.getHint());
            }
        });
        textProjectSearchStreetAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_ADDRESS;
                }
            }
        });

        textProjectSearchFrom = (DelayAutoCompleteTextView)rootView.findViewById(R.id.textProjectSearchFrom);
        textProjectSearchFrom.setThreshold(thresHodValue);
        textProjectSearchFrom.setAdapter(adapterAutocomplete);
        textProjectSearchFrom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textProjectSearchFrom.setText(hintModel.getHint());
            }
        });
        textProjectSearchFrom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_FROM;
                }
            }
        });

        textProjectSearchTo = (DelayAutoCompleteTextView)rootView.findViewById(R.id.textProjectSearchTo);
        textProjectSearchTo.setThreshold(thresHodValue);
        textProjectSearchTo.setAdapter(adapterAutocomplete);
        textProjectSearchTo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textProjectSearchTo.setText(hintModel.getHint());
            }
        });
        textProjectSearchTo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_TO;
                }
            }
        });

        textProjectSearchEmployee = (DelayAutoCompleteTextView)rootView.findViewById(R.id.textProjectSearchEmployee);
        textProjectSearchEmployee.setThreshold(thresHodValue);
        textProjectSearchEmployee.setAdapter(adapterAutocomplete);
        textProjectSearchEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textProjectSearchEmployee.setText(hintModel.getHint());
            }
        });
        textProjectSearchEmployee.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_EMPLOYEE;
                }
            }
        });

        textProjectSearchPlace = (DelayAutoCompleteTextView)rootView.findViewById(R.id.textProjectSearchPlace);
        textProjectSearchPlace.setThreshold(thresHodValue);
        textProjectSearchPlace.setAdapter(adapterAutocomplete);
        textProjectSearchPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HintModel hintModel = (HintModel) adapterView.getItemAtPosition(position);
                textProjectSearchPlace.setText(hintModel.getHint());
            }
        });
        textProjectSearchPlace.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    lastFocusedvalue=FOCUSED_PLACE;
                }
            }
        });

        footerView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_list_button, null, false);
        footerButton = (Button)footerView.findViewById(R.id.buttonLoadMore);
        pageSize = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).getInt("CustomerPageSize", 10);
        setupLanguage();
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelProject());
    }

    @Override
    public void bindEvents(View rootView)
    {
        super.bindEvents(rootView);
        textProjectSearchMatchCode.setOnEditorActionListener(this);
        textProjectSearchDescription.setOnEditorActionListener(this);
        textProjectSearchTypeOfProject.setOnEditorActionListener(this);
        textProjectSearchStreetAddress.setOnEditorActionListener(this);
        textProjectSearchFrom.setOnEditorActionListener(this);
        textProjectSearchTo.setOnEditorActionListener(this);
        textProjectSearchEmployee.setOnEditorActionListener(this);
        textProjectSearchPlace.setOnEditorActionListener(this);
        listViewProjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //view.setSelected(true);
                adapter.setSelectedIndex(position);
            }
        });
        footerButton.setText(language.getMessageLoadMoreProject());
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchForNewPage();
            }
        });
    }

    private void setupLanguage()
    {
        TextView labelProjectSearchEmployee, labelProjectSearchTo, labelProjectSearchFrom, labelProjectSearchPlace, labelProjectSearchStreetAddress,
                labelProjectSearchTypeOfProject, labelProjectSearchDescription, labelProjectSearchMatchCode;

        labelProjectSearchEmployee = (TextView)rootView.findViewById(R.id.labelProjectSearchEmployee);
        labelProjectSearchTo = (TextView)rootView.findViewById(R.id.labelProjectSearchTo);
        labelProjectSearchFrom = (TextView)rootView.findViewById(R.id.labelProjectSearchFrom);
        labelProjectSearchPlace = (TextView)rootView.findViewById(R.id.labelProjectSearchPlace);
        labelProjectSearchStreetAddress = (TextView)rootView.findViewById(R.id.labelProjectSearchStreetAddress);
        labelProjectSearchTypeOfProject = (TextView)rootView.findViewById(R.id.labelProjectSearchTypeOfProject);
        labelProjectSearchDescription = (TextView)rootView.findViewById(R.id.labelProjectSearchDescription);
        labelProjectSearchMatchCode = (TextView)rootView.findViewById(R.id.labelProjectSearchMatchCode);

        labelProjectSearchMatchCode.setText(language.getLabelMatchCode());
        labelProjectSearchDescription.setText(language.getLabelDescription());
        labelProjectSearchTypeOfProject.setText(language.getLabelTypeOfProjct());
        labelProjectSearchStreetAddress.setText(language.getLabelAddress());
        labelProjectSearchPlace.setText(language.getLabelPlace());
        labelProjectSearchFrom.setText(language.getLabelFrom());
        labelProjectSearchTo.setText(language.getLabelTo());
        labelProjectSearchEmployee.setText(language.getLabelEmployee());

//        textProjectSearchMatchCode.setHint(language.getLabelMatchCode());
//        textProjectSearchDescription.setHint(language.getLabelDescription());
//        textProjectSearchTypeOfProject.setHint(language.getLabelTypeOfProjct());
//        textProjectSearchStreetAddress.setHint(language.getLabelAddress());
//        textProjectSearchFrom.setHint(language.getLabelFrom());
//        textProjectSearchTo.setHint(language.getLabelTo());
//        textProjectSearchEmployee.setHint(language.getLabelEmployee());
//        textProjectSearchPlace.setHint(language.getLabelPlace());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);
        //menu.clear();
        menu.findItem(R.id.actionBack).setVisible(false);
        menu.findItem(R.id.actionWrong).setVisible(false);
        menu.findItem(R.id.actionAdd).setVisible(false);
        menu.findItem(R.id.actionEdit).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);

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
            case R.id.actionForward:
                if(!(adapter.selectedIndex == -1))
                {
                    loadProject(adapter.selectedIndex);
                }
                else
                {
                    showShortToast(language.getMessageSelectProjectFirst());
                }
                return true;
            case R.id.actionSearch:
                //Toast.makeText(getActivity(), "Search", Toast.LENGTH_SHORT).show();
                searchForResults();
                return true;
            case R.id.actionRight:
                if(!(adapter.selectedIndex == -1))
                {
                    loadProject(adapter.selectedIndex);
                }
                else
                {
                    showShortToast(language.getMessageSelectProjectFirst());
                }
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        if (actionId == EditorInfo.IME_ACTION_SEARCH)
        {
            searchForResults();
            return true;
        }
        return false;
    }

    private void searchForResults()
    {
        isCallservice=true;
        adapter.setSelectedIndex(-1);
        matecoPriceApplication.hideKeyboard(getActivity());
        listofProject.clear();
        adapter.setSelectedIndex(-1);
        adapter.notifyDataSetChanged();
        listViewProjects.removeFooterView(footerView);
        String matchCode = textProjectSearchMatchCode.getText().toString();
        String description = textProjectSearchDescription.getText().toString();
        String typeOfProject = textProjectSearchTypeOfProject.getText().toString();
        String address = textProjectSearchStreetAddress.getText().toString();
        String from = textProjectSearchFrom.getText().toString();
        String to = textProjectSearchTo.getText().toString();
        String employee = textProjectSearchEmployee.getText().toString();
        String place = textProjectSearchPlace.getText().toString();

        if(description.length() > 0 || typeOfProject.length() > 0 || matchCode.length() > 0 || address.length() > 0 ||
                from.length() > 0 || to.length() > 0 || place.length() > 0 || employee.length() > 0)
        {
            ProjectSearchPagingRequestModel projectSearchPagingRequestModel = new ProjectSearchPagingRequestModel();
            projectSearchPagingRequestModel.setMatchcode(matchCode);
            projectSearchPagingRequestModel.setBeschreibung(description);
            projectSearchPagingRequestModel.setObjektTyp(typeOfProject);
            projectSearchPagingRequestModel.setAdresse(address);
            projectSearchPagingRequestModel.setBeginn(from);
            projectSearchPagingRequestModel.setEnde(to);
            projectSearchPagingRequestModel.setMitrabeiter(employee);
            projectSearchPagingRequestModel.setOrt(place);
            projectSearchPagingRequestModel.setPageSize(pageSize + "");
            //listViewProjects.removeFooterView(footerView);
           // lianearMain.removeView(footerView);
            pageNuber = 1;
            projectSearchPagingRequestModel.setPageNumber(pageNuber+"");
            //Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String jsonToSend = DataHelper.getGson().toJson(projectSearchPagingRequestModel);
            if(DataHelper.isNetworkAvailable(getActivity()))
            {
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        hideProgressDialog();
                        if(result.equals("error"))
                        {
                            showShortToast(language.getMessageError());
                        }
                        else if(result.equals(DataHelper.NetworkError)){
                            //showLongToast("Network problem while service calling before");
                            if(isCallservice) {
                                //showLongToast("service call start now");
                                isCallservice=false;
                                showProgressDialog();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        // Actions to do after 10 seconds
                                        searchForResults();
                                    }
                                }, DataHelper.NETWORK_CALL_DURATION);
                            }
                        }
                        else
                        {
                            try
                            {
                                JSONObject jsonObject = new JSONObject(result);
                                customerCount = jsonObject.getInt("ProjectCount");
                                totalPageCount = jsonObject.getInt("PageCount");
                                pageNuber = jsonObject.getInt("PageNumber");
                                pageSize = jsonObject.getInt("PageSize");
                                JSONArray resultsOfProjects = jsonObject.getJSONArray("Result");
                                if(pageNuber < totalPageCount)
                                {
                                    listViewProjects.addFooterView(footerView);
                                    //lianearMain.addView(footerView);
                                }
                                if(resultsOfProjects.length() == 0)
                                {
                                    showLongToast(language.getMessageNoResultFound());
                                }
                                else
                                {
                                    ProjectModel.extractFromJson(resultsOfProjects.toString(), listofProject);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                                showShortToast(language.getMessageErrorAtParsing());
                            }
                        }
                    }
                };
                String url = "";
                try
                {
                    /*url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.Project_Search_Paging
                            + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                            + "&projectsearchparam=" + URLEncoder.encode(jsonToSend, "UTF-8");*/
                    url = DataHelper.URL_PROJECT_HELPER+"projectsearchpaging" + "/token="
                            + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                            + "/projectsearchparam=" + URLEncoder.encode(jsonToSend, "UTF-8");
                    BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                    asyncTask.execute();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                hideProgressDialog();
                showShortToast(language.getMessageNetworkNotAvailable());
            }
        }
        else
        {
            showLongToast(language.getMessageInputSearchParameter());
        }
    }

    private void loadProject(int selectedIndex)
    {
        if(DataHelper.isNetworkAvailable(getActivity()))
        {
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setTitle(language.getMessageWaitWhileLoading());
            dialog.setMessage(language.getMessageWaitWhileLoading());
            dialog.show();
            try
            {
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        if(result.equals("error"))
                        {
                            showShortToast(language.getMessageError());
                            dialog.dismiss();
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
                                dialog.dismiss();
                                FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                transaction1.replace(R.id.content_frame, new ProjectDetailFragment());
                                //transaction.addToBackStack(SettingFragment.Tag);
                                transaction1.addToBackStack("Project Search");
                                transaction1.commit();
                                //getActivity().getSupportFragmentManager().executePendingTransactions();
                            }
                            catch (JSONException ex)
                            {
                                ex.printStackTrace();
                                showShortToast(language.getMessageErrorAtParsing());
                                dialog.dismiss();
                            }
                        }
                    }
                };
                String url = DataHelper.URL_AGENDA_HELPER + "agendaprojectlistshow"//DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.AGENDA_PROJECT_LIST_SHOW
                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/fieldname=" + "Projekt"
                        + "/value=" + listofProject.get(selectedIndex).getProjekt();
                //Projekt
//                String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.PROJECT_LIST_SHOW
//                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
//                        + "&objekt=" + listofProject.get(selectedIndex).getProjekt();
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), false);
                asyncTask.execute();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
                dialog.dismiss();
            }
        }
        else
        {
            showShortToast(language.getMessageNetworkNotAvailable());
        }
    }

    private void searchForNewPage()
    {
        isCallservice=true;
        //loadingMore = true;
        matecoPriceApplication.hideKeyboard(getActivity());
        //listOfCustomerSearchResult.clear();
        adapter.setSelectedIndex(-1);
        adapter.notifyDataSetChanged();
        String matchCode = textProjectSearchMatchCode.getText().toString();
        String description = textProjectSearchDescription.getText().toString();
        String typeOfProject = textProjectSearchTypeOfProject.getText().toString();
        String address = textProjectSearchStreetAddress.getText().toString();
        String from = textProjectSearchFrom.getText().toString();
        String to = textProjectSearchTo.getText().toString();
        String employee = textProjectSearchEmployee.getText().toString();
        String place = textProjectSearchPlace.getText().toString();

        if(description.length() > 0 || typeOfProject.length() > 0 || matchCode.length() > 0 || address.length() > 0 ||
                from.length() > 0 || to.length() > 0 || place.length() > 0 || employee.length() > 0)
        {
            ProjectSearchPagingRequestModel projectSearchPagingRequestModel = new ProjectSearchPagingRequestModel();
            projectSearchPagingRequestModel.setMatchcode(matchCode);
            projectSearchPagingRequestModel.setBeschreibung(description);
            projectSearchPagingRequestModel.setObjektTyp(typeOfProject);
            projectSearchPagingRequestModel.setAdresse(address);
            projectSearchPagingRequestModel.setBeginn(from);
            projectSearchPagingRequestModel.setEnde(to);
            projectSearchPagingRequestModel.setMitrabeiter(employee);
            projectSearchPagingRequestModel.setOrt(place);
            pageNuber = pageNuber + 1;
            projectSearchPagingRequestModel.setPageNumber(pageNuber + "");
            projectSearchPagingRequestModel.setPageSize(pageSize + "");

            String jsonToSend = DataHelper.getGson().toJson(projectSearchPagingRequestModel);
            String url = "";
            try
            {
                /*url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.Project_Search_Paging
                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "&projectsearchparam=" + URLEncoder.encode(jsonToSend, "UTF-8");*/
                url = DataHelper.URL_PROJECT_HELPER+"projectsearchpaging"
                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/projectsearchparam=" + URLEncoder.encode(jsonToSend, "UTF-8");
                LogApp.showLog("url", url);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            if(DataHelper.isNetworkAvailable(getActivity()))
            {
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        hideProgressDialog();
                        if(result.equals("error"))
                        {
                            showLongToast(language.getMessageError());
                        }
                        else if(result.equals(DataHelper.NetworkError)){
                            //showLongToast("Network problem while service calling before");
                            if(isCallservice) {
                                //showLongToast("service call start now");
                                isCallservice=false;
                                showProgressDialog();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        // Actions to do after 10 seconds
                                        searchForNewPage();
                                    }
                                }, DataHelper.NETWORK_CALL_DURATION);
                            }
                        }
                        else
                        {
                            try
                            {
                                JSONObject jsonObject = new JSONObject(result);
                                customerCount = jsonObject.getInt("ProjectCount");
                                totalPageCount = jsonObject.getInt("PageCount");
                                pageNuber = jsonObject.getInt("PageNumber");
                                pageSize = jsonObject.getInt("PageSize");

                                if(pageNuber == totalPageCount)
                                {
                                    listViewProjects.removeFooterView(footerView);
                                    //lianearMain.removeView(footerView);
                                }

                                JSONArray resultsOfProjects = jsonObject.getJSONArray("Result");
                                if(resultsOfProjects.length() == 0)
                                {
                                    showLongToast(language.getMessageNoResultFound());
                                }
                                else
                                {
                                    ProjectModel.extractFromJson(resultsOfProjects.toString(), listofProject);
                                    adapter.notifyDataSetChanged();
                                }
                                //loadingMore = false;
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                    }
                };
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                asyncTask.execute();
            }
            else
            {
                hideProgressDialog();
                showShortToast(language.getMessageNetworkNotAvailable());
            }
        }
        else
        {
            showLongToast(language.getMessageInputSearchParameter());
        }
    }
    public class AutoCompleteSearchAdapter extends BaseAdapter implements Filterable {

        private static final int MAX_RESULTS = 10;
        private Context mContext;
        private List<HintModel> arraylistHint;

        public AutoCompleteSearchAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return arraylistHint.size();
        }

        @Override
        public HintModel getItem(int index) {
            return arraylistHint.get(index);
        }

        @Override
        public long getItemId(int index) {
            return index;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.auto_complete_item, parent, false);
            }
            ((TextView) convertView.findViewById(R.id.txtAutoitem)).setText(getItem(position).getHint());
            //((TextView) convertView.findViewById(R.id.tv_id_product_ac_barcode)).setText(getItem(position).getBarcode());
            return convertView;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        findHintsFromApi(mContext, constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = arraylistHint;
                        filterResults.count = arraylistHint.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        arraylistHint = (List<HintModel>) results.values;
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }};
            return filter;
        }

        /**
         * Returns a search result for the given book title.
         */
        private void findHintsFromApi(final Context context, String search) {
            arraylistHint = new ArrayList<HintModel>();

            try {
                String matchCode = textProjectSearchMatchCode.getText().toString();
                String description = textProjectSearchDescription.getText().toString();
                String typeOfProject = textProjectSearchTypeOfProject.getText().toString();
                String address = textProjectSearchStreetAddress.getText().toString();
                String from = textProjectSearchFrom.getText().toString();
                String to = textProjectSearchTo.getText().toString();
                String employee = textProjectSearchEmployee.getText().toString();
                String place = textProjectSearchPlace.getText().toString();

                if(description.length() > 0 || typeOfProject.length() > 0 || matchCode.length() > 0 || address.length() > 0 ||
                        from.length() > 0 || to.length() > 0 || place.length() > 0 || employee.length() > 0)
                {
                    ProjectSearchPagingRequestModel projectSearchPagingRequestModel = new ProjectSearchPagingRequestModel();

                    projectSearchPagingRequestModel.setMatchcode("");
                    projectSearchPagingRequestModel.setBeschreibung("");
                    projectSearchPagingRequestModel.setObjektTyp("");
                    projectSearchPagingRequestModel.setAdresse("");
                    projectSearchPagingRequestModel.setBeginn("");
                    projectSearchPagingRequestModel.setEnde("");
                    projectSearchPagingRequestModel.setMitrabeiter("");
                    projectSearchPagingRequestModel.setOrt("");

                    if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_MATCHCODE)){
                        projectSearchPagingRequestModel.setMatchcode(matchCode);
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_DESCRIPTION)){
                        projectSearchPagingRequestModel.setBeschreibung(description);
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_TYPE_OF_PROJECT)){
                        projectSearchPagingRequestModel.setObjektTyp(typeOfProject);
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_ADDRESS)){
                        projectSearchPagingRequestModel.setAdresse(address);
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_FROM)){
                        projectSearchPagingRequestModel.setBeginn(from);
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_EMPLOYEE)){
                        projectSearchPagingRequestModel.setMitrabeiter(employee);
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_TO)){
                        projectSearchPagingRequestModel.setEnde(to);
                    }
                    else if(lastFocusedvalue.equalsIgnoreCase(FOCUSED_PLACE)){
                        projectSearchPagingRequestModel.setOrt(place);
                    }
                    projectSearchPagingRequestModel.setPageSize(GlobalClass.pageSizeForHint + "");
                    pageNuber = 1;
                    projectSearchPagingRequestModel.setPageNumber(GlobalClass.pageNuber+"");
                    jsonToSend = DataHelper.getGson().toJson(projectSearchPagingRequestModel);
                }
                String base64Data = DataHelper.getToken();
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
                        DataHelper.URL_PROJECT_HELPER+"projectsearchhint"
                                + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                                + "/projectsearchparam=" + URLEncoder.encode(jsonToSend, "UTF-8"),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.toString());
                                    JSONArray resultsOfCustomers = jsonObject.getJSONArray("Result");
                                    TypeToken<List<HintModel>> token = new TypeToken<List<HintModel>>() {};
                                    arraylistHint = new Gson().fromJson(resultsOfCustomers.toString(),token.getType());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                notifyDataSetChanged();
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                Volley.newRequestQueue(this.mContext).add(req);
            }
            catch (Exception e){
                //Toast.makeText(context,"", Toast.LENGTH_SHORT).show();
            }

        }
    }
    public void showProgressDialog(){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setTitle(language.getMessageWaitWhileLoading());
        progressDialog.setMessage(language.getMessageWaitWhileLoading());
        progressDialog.show();
    }
    public void hideProgressDialog(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}