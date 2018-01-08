package de.mateco.integrAMobile.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.asyncTask.AsyncTaskWithAuthorizationHeaderPost;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LoginPersonModel;
import de.mateco.integrAMobile.model.ProjectAreaModel;
import de.mateco.integrAMobile.model.ProjectDetailGenerallyModel;
import de.mateco.integrAMobile.model.ProjectDetailGenerallyUpdateModel;

public class ProjectDetailNotesFragment extends BaseFragment
{
    private View rootView;
    private MatecoPriceApplication matecoPriceApplication;
    private Language language;
    private DataBaseHandler db;
    private EditText textProjectDetailNote;
    private Menu menu;
    private ProjectDetailGenerallyModel projectGenerallyDetail;
    private boolean isInEditMode = false,isInAddMode = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_project_search_notes, container, false);
        //View rootView = inflater.inflate(R.layout.temp_under_construction, container, false);
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
        textProjectDetailNote = (EditText)rootView.findViewById(R.id.textProjectDetailNote);
        setupLanguage();

        ProjectDetailGenerallyModel object = matecoPriceApplication.getProjectDetailGenerallyModel(DataHelper.LoadedProjectDetailGenerallyInfo, new ProjectDetailGenerallyModel().toString());
        textProjectDetailNote.setText(object.getNotiz());
        showLoadedProjectData();
        makeEditable(false);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelProject());
    }

    @Override
    public void bindEvents(View rootView)
    {
        super.bindEvents(rootView);

    }

    private void showLoadedProjectData()
    {
        projectGenerallyDetail = matecoPriceApplication.getProjectDetailGenerallyModel(DataHelper.LoadedProjectDetailGenerallyInfo, new ProjectDetailGenerallyModel().toString());
        textProjectDetailNote.setText(projectGenerallyDetail.getNotiz());
    }

    private void makeEditable(boolean isEditable)
    {
        textProjectDetailNote.setFocusable(isEditable);
        textProjectDetailNote.setFocusableInTouchMode(isEditable);
    }

    private void setupLanguage()
    {
        TextView labelProjectDetailNote;
        labelProjectDetailNote = (TextView)rootView.findViewById(R.id.labelProjectDetailNote);
        labelProjectDetailNote.setText(language.getLabelNote());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);
        this.menu = menu;
        menu.findItem(R.id.actionSearch).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);
        menu.findItem(R.id.actionAdd).setVisible(false);
        menu.findItem(R.id.actionWrong).setVisible(false);
        menu.findItem(R.id.actionRight).setVisible(false);
        if(textProjectDetailNote.getText().toString().equals(""))
        {
            menu.findItem(R.id.actionAdd).setVisible(true);
            menu.findItem(R.id.actionEdit).setVisible(false);
        }

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
            case R.id.actionEdit:
                makeEditable(true);
                isInEditMode = true;
                menu.findItem(R.id.actionEdit).setVisible(false);
                menu.findItem(R.id.actionBack).setVisible(false);
                menu.findItem(R.id.actionWrong).setVisible(true);
                menu.findItem(R.id.actionRight).setVisible(true);
                return true;
            case R.id.actionAdd:
                makeEditable(true);
                isInAddMode = true;
                menu.findItem(R.id.actionAdd).setVisible(false);
                menu.findItem(R.id.actionEdit).setVisible(false);
                menu.findItem(R.id.actionBack).setVisible(false);
                menu.findItem(R.id.actionWrong).setVisible(true);
                menu.findItem(R.id.actionRight).setVisible(true);
                return true;
            case R.id.actionRight:
                if(isInEditMode)
                    updateNotice("edit");
                else if(isInAddMode)
                    updateNotice("add");
                return true;
            case R.id.actionWrong:
                if(isInAddMode)
                    menu.findItem(R.id.actionAdd).setVisible(true);
                else
                    menu.findItem(R.id.actionEdit).setVisible(true);
                menu.findItem(R.id.actionWrong).setVisible(false);
                menu.findItem(R.id.actionRight).setVisible(false);

                menu.findItem(R.id.actionBack).setVisible(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    private void updateNotice(final String string)
    {
        ArrayList<ProjectAreaModel> listOfProjectArea = new ArrayList<>();
        projectGenerallyDetail = matecoPriceApplication.getProjectDetailGenerallyModel(DataHelper.LoadedProjectDetailGenerallyInfo, new ProjectDetailGenerallyModel().toString());
        if(DataHelper.isNetworkAvailable(getActivity()))
        {
            ProjectDetailGenerallyUpdateModel model = new ProjectDetailGenerallyUpdateModel();
            model.setProjekt(projectGenerallyDetail.getProjekt());
            model.setMatchCode(projectGenerallyDetail.getMatchCode());
            model.setGebiet(projectGenerallyDetail.getGebiet());
            model.setBeschreibung(projectGenerallyDetail.getBeschreibung());
            model.setOrt(projectGenerallyDetail.getOrt());
            model.setPLZ(projectGenerallyDetail.getPLZ());
            model.setStrasse(projectGenerallyDetail.getStrasse());
            listOfProjectArea.addAll(db.getProjectArea());
            for(int i = 0; i < listOfProjectArea.size(); i++)
            {
                if(listOfProjectArea.get(i).getProjectAreaDesignation().equals(projectGenerallyDetail.getGebiet()))
                {
                    model.setGebiet(listOfProjectArea.get(i).getProjectAreaId());
                    break;
                }
            }
            if(!projectGenerallyDetail.getBaubeginn().equals("01-01-1900") && !projectGenerallyDetail.getBaubeginn().equals(""))
                model.setBaubeginn(projectGenerallyDetail.getBaubeginn());
            else
                model.setBaubeginn("");
            if(!projectGenerallyDetail.getBauende().equals("01-01-1900") && !projectGenerallyDetail.getBauende().equals(""))
                model.setBauende(projectGenerallyDetail.getBauende());
            else
                model.setBauende("");
            model.setHoehe(projectGenerallyDetail.getHoehe());
            model.setHoehe_von_bis(projectGenerallyDetail.getHoehe_von_bis());
            model.setPhaseID(projectGenerallyDetail.getPhaseID());
            model.setProjektart(projectGenerallyDetail.getProjektartID());
            model.setProjekttypID(projectGenerallyDetail.getProjekttypID());
            if(!projectGenerallyDetail.getDatum_Aktuell().equals("01-01-1900") && !projectGenerallyDetail.getDatum_Aktuell().equals(""))
                model.setDatum(projectGenerallyDetail.getDatum_Aktuell());
            else
                model.setDatum("");
            model.setMitarbeiter(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber());
            model.setArtaussenID(projectGenerallyDetail.getArtaussenID());
            model.setArtInnen(projectGenerallyDetail.getArtInnenID());
            model.setGroesse(projectGenerallyDetail.getGroesse());
            model.setNotiz(textProjectDetailNote.getText().toString());
            model.setArtaussenID(projectGenerallyDetail.getArtaussenID());
            model.setAenderungMitarbeiter(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber());
            model.setRampe(projectGenerallyDetail.getRampe());
            model.setWeisse_Reifen(projectGenerallyDetail.getWeisse_Reifen());
            model.setAbgeschlossen(projectGenerallyDetail.getAbgeschlossen());
            model.setZust_ADM(projectGenerallyDetail.getZust_ADM());
            AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult() {
                @Override
                public void OnAsynResult(String result) {
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
                            matecoPriceApplication.saveData(DataHelper.LoadedProjectDetailGenerallyInfo, jsonObject.getJSONObject("ProjectGenerallyList").toString());
                            matecoPriceApplication.saveData(DataHelper.LoadedProjectDetailActivityInfo, jsonObject.getJSONArray("ProjectActivityList").toString());
                            matecoPriceApplication.saveData(DataHelper.LoadedProjectDetailTradesInfo, jsonObject.getJSONArray("ProjectTradesList").toString());
                            showLoadedProjectData();
                            makeEditable(false);
                            if(string.equals("edit"))
                                isInEditMode = false;
                            else
                                isInAddMode = false;
                            menu.findItem(R.id.actionEdit).setVisible(true);
                            menu.findItem(R.id.actionRight).setVisible(false);
                            menu.findItem(R.id.actionWrong).setVisible(false);
                            menu.findItem(R.id.actionBack).setVisible(true);

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
                String json = new Gson().toJson(model);
                Log.e("json", json);
                String url = DataHelper.URL_PROJECT_HELPER+"projectgenerallyupdate";
//                String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.PROJECT_GENERALLY_UPDATE
//                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
//                        + "&projectgenerally=" + URLEncoder.encode(json, "UTF-8");
//                Log.e("url", url);
//                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
//                asyncTask.execute();

                Log.e("url", url);
                MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")));
                multipartEntity.addPart("projectgenerally", new StringBody(json, Charset.forName("UTF-8")));
                AsyncTaskWithAuthorizationHeaderPost asyncTaskPost = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, getActivity(), multipartEntity, true, language);
                asyncTaskPost.execute();
            }
            catch (Exception ex)
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
