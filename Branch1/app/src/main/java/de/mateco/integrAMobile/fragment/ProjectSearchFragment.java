package de.mateco.integrAMobile.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.ProjectSearchAdapter;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.LoadedCustomerFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.ProjectModel;
import de.mateco.integrAMobile.model.ProjectSearchPagingRequestModel;

public class ProjectSearchFragment extends LoadedCustomerFragment implements TextView.OnEditorActionListener
{
    LinearLayout lianearMain;
    private Language language;
    private View rootView;
    private MatecoPriceApplication matecoPriceApplication;
    private EditText textProjectSearchMatchCode, textProjectSearchDescription, textProjectSearchTypeOfProject, textProjectSearchStreetAddress, textProjectSearchPlace,
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
            Log.e("rootview", " not null");
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
        textProjectSearchMatchCode = (EditText)rootView.findViewById(R.id.textProjectSearchMatchCode);
        textProjectSearchDescription = (EditText)rootView.findViewById(R.id.textProjectSearchDescription);
        textProjectSearchTypeOfProject = (EditText)rootView.findViewById(R.id.textProjectSearchTypeOfProject);
        textProjectSearchStreetAddress = (EditText)rootView.findViewById(R.id.textProjectSearchStreetAddress);
        textProjectSearchFrom = (EditText)rootView.findViewById(R.id.textProjectSearchFrom);
        textProjectSearchTo = (EditText)rootView.findViewById(R.id.textProjectSearchTo);
        textProjectSearchEmployee = (EditText)rootView.findViewById(R.id.textProjectSearchEmployee);
        textProjectSearchPlace = (EditText)rootView.findViewById(R.id.textProjectSearchPlace);
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
        adapter.setSelectedIndex(-1);
        matecoPriceApplication.hideKeyboard(getActivity());
        listofProject.clear();
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
            projectSearchPagingRequestModel.setPageSize(pageSize + "");
            //listViewProjects.removeFooterView(footerView);
            lianearMain.removeView(footerView);
            pageNuber = 1;
            projectSearchPagingRequestModel.setPageNumber(pageNuber+"");
            String jsonToSend = new Gson().toJson(projectSearchPagingRequestModel);
            Log.e("customer to json", jsonToSend);
            if(DataHelper.isNetworkAvailable(getActivity()))
            {
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        Log.e("result project search", result);
                        if(result.equals("error"))
                        {
                            showShortToast(language.getMessageError());
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
                                    //listViewProjects.addFooterView(footerView);
                                    lianearMain.addView(footerView);
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
                    url = DataHelper.URL_PROJECT_HELPER+"projectsearchpaging"
                            + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                            + "/projectsearchparam=" + URLEncoder.encode(jsonToSend, "UTF-8");


                    Log.e("url", url);
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
                        Log.e("result",result);
                        if(result.equals("error"))
                        {
                            showShortToast(language.getMessageError());
                            dialog.dismiss();
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
                Log.e("url", url);
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
        Log.e("search for new page", "called");
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

            String jsonToSend = new Gson().toJson(projectSearchPagingRequestModel);
            Log.e("customer to json", jsonToSend);
            String url = "";
            try
            {
                /*url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.Project_Search_Paging
                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "&projectsearchparam=" + URLEncoder.encode(jsonToSend, "UTF-8");*/
                url = DataHelper.URL_PROJECT_HELPER+"projectsearchpaging"
                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/projectsearchparam=" + URLEncoder.encode(jsonToSend, "UTF-8");
                Log.e("url", url);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            Log.e("url at customer search", url);
            if(DataHelper.isNetworkAvailable(getActivity()))
            {
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        Log.e("result ", result);
                        if(result.equals("error"))
                        {
                            showLongToast(language.getMessageError());
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
                                    //listViewProjects.removeFooterView(footerView);
                                    lianearMain.removeView(footerView);
                                }

                                JSONArray resultsOfProjects = jsonObject.getJSONArray("Result");
                                Log.e("size add page" + pageNuber, resultsOfProjects.length()+"");
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
                showShortToast(language.getMessageNetworkNotAvailable());
            }
        }
        else
        {
            showLongToast(language.getMessageInputSearchParameter());
        }
    }
}