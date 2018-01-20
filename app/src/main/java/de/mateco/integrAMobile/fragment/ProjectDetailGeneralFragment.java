package de.mateco.integrAMobile.fragment;

    import android.app.DatePickerDialog;
    import android.app.Dialog;
    import android.app.DialogFragment;
    import android.app.ProgressDialog;
    import android.os.Bundle;
    import android.os.Handler;
    import android.support.annotation.Nullable;
    import android.support.v4.app.FragmentTransaction;
    import android.text.TextUtils;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.Menu;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.CheckBox;
    import android.widget.DatePicker;
    import android.widget.EditText;
    import android.widget.ImageButton;
    import android.widget.Spinner;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.google.gson.Gson;

    import org.apache.http.entity.mime.HttpMultipartMode;
    import org.apache.http.entity.mime.MultipartEntity;
    import org.apache.http.entity.mime.content.StringBody;
    import org.json.JSONObject;

    import java.net.URLEncoder;
    import java.nio.charset.Charset;
    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.Collections;
    import java.util.Comparator;
    import java.util.Date;

    import de.mateco.integrAMobile.Helper.DataHelper;
    import de.mateco.integrAMobile.Helper.DatePickerDialogFragment;
    import de.mateco.integrAMobile.Helper.GlobalClass;
    import de.mateco.integrAMobile.HomeActivity;
    import de.mateco.integrAMobile.R;
    import de.mateco.integrAMobile.adapter.EmployeeAdapter;
    import de.mateco.integrAMobile.adapter.EmployeeAdapter2;
    import de.mateco.integrAMobile.adapter.ProjectAreaAdapter;
    import de.mateco.integrAMobile.adapter.ProjectArtAdapter;
    import de.mateco.integrAMobile.adapter.ProjectBuheneartAdapter;
    import de.mateco.integrAMobile.adapter.ProjectPhaseAdapter;
    import de.mateco.integrAMobile.adapter.ProjectStagesAdapter;
    import de.mateco.integrAMobile.adapter.ProjectTypeAdapter;
    import de.mateco.integrAMobile.asyncTask.AsyncTaskWithAuthorizationHeaderPost;
    import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
    import de.mateco.integrAMobile.base.BaseFragment;
    import de.mateco.integrAMobile.base.MatecoPriceApplication;
    import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
    import de.mateco.integrAMobile.model.BuheneartModel;
    import de.mateco.integrAMobile.model.EmployeeModel;
    import de.mateco.integrAMobile.model.Language;
    import de.mateco.integrAMobile.model.LoginPersonModel;
    import de.mateco.integrAMobile.model.ProjectAreaModel;
    import de.mateco.integrAMobile.model.ProjectArtModel;
    import de.mateco.integrAMobile.model.ProjectDetailGenerallyModel;
    import de.mateco.integrAMobile.model.ProjectDetailGenerallyUpdateModel;
    import de.mateco.integrAMobile.model.ProjectPhaseModel;
    import de.mateco.integrAMobile.model.ProjectStagesModel;
    import de.mateco.integrAMobile.model.ProjectTypeModel;

    public class ProjectDetailGeneralFragment extends BaseFragment implements View.OnClickListener
    {
        boolean isFirstTIme=true,isFirstTime2=true;
        private static String clickedTextbox="";
        private ProgressDialog prd;
        private View rootView;
    private Spinner spinnerProjectDetailGeneralArea, spinnerProjectDetailGeneralStages, spinnerProjectDetailGeneralArt,
            spinnerProjectDetailGeneralType, spinnerProjectDetailGeneralPhase,spinnerProjectDetailGeneralStages1,spinnerProjectDetailGeneralZustAdm;
    private DataBaseHandler db;
    private Language language;
    private MatecoPriceApplication matecoPriceApplication;
    private ArrayList<ProjectTypeModel> listOfProjectType;
    private ArrayList<ProjectPhaseModel> listOfProjectPhase;
    private ArrayList<ProjectArtModel> listOfProjectArt;
    private ArrayList<ProjectStagesModel> listOfProjectStage;
    private ArrayList<ProjectAreaModel> listOfProjectArea;
    private ArrayList<BuheneartModel> listOfProjectStage1;
    private ProjectStagesAdapter adapterProjectStage;
    private ProjectAreaAdapter adapterProjectArea;
    private ProjectArtAdapter adapterProjectArt;
    private ProjectPhaseAdapter adapterProjectPhase;
    private ProjectTypeAdapter adapterProjectType;
    private ProjectBuheneartAdapter adapterProjectSatge1;
    private EmployeeAdapter2 adapterEmployee;
    private TextView textProjectDetailGeneralSize;
    private static TextView textProjectDetailGeneralEndOfConstruction;
    private Menu menu;
    private boolean isInEditMode = false;
    private EditText textProjectDetailGeneralMatchCode;
    private EditText textProjectDetailGeneralDescription;
    private EditText textProjectDetailGeneralRoad;
    private EditText textProjectDetailGeneralZipCode;
    private EditText textProjectDetailGeneralPlace;
    private static EditText textProjectDetailGeneralStartOfConstruction;
    private EditText textProjectDetailGeneralHeight;
    private EditText textProjectDetailGeneralHeightFrom;
    private EditText textProjectDetailGeneralDateOfUpdate;
    private CheckBox checkboxProjectDetailGeneralAbgeschlosse, checkboxProjectDetailGeneralRamp, checkboxProjectDetailGeneralMatureWhite;
    private ProjectAreaModel selectedArea;
    private ProjectStagesModel selectedProjectStage;
    private ProjectArtModel selectedProjectArt;
    private ProjectTypeModel selectedProjectType;
    private ProjectPhaseModel selectedProjectPhase;
    private BuheneartModel selectedBuheneart;
    private ProjectDetailGenerallyModel projectGenerallyDetail;
    private ImageButton imageButtonGenerallyStartDate,imageButtonGenerallyEndDate;
    ArrayList<EmployeeModel> listOfEmployee = new ArrayList<>();
    private EmployeeModel employeeModel = new EmployeeModel();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_project_search_generally, container, false);
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        setupLanguage();

        /*try {
            prd = new ProgressDialog(getActivity());
            prd.setTitle(language.getMessageWaitWhileLoading());
            prd.setMessage(language.getMessageWaitWhileLoading());
            prd.show();
        }
        catch (Exception e){

        }*/

        //rootView = inflater.inflate(R.layout.temp_under_construction, container, false);
//        TextView txtNameFragment = (TextView)rootView.findViewById(R.id.txtNameFragment);
//        String text = getArguments().getString("Name");
//        txtNameFragment.setText(text);

//        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Project Detail General");
        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initializeFragment(view);
            }
        }, 500);
    }

    @Override
    public void initializeComponents(View rootView)
    {
        super.initializeComponents(rootView);
        //matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        //language = matecoPriceApplication.getLanguage();
        db = new DataBaseHandler(getActivity());

        spinnerProjectDetailGeneralArea = (Spinner)rootView.findViewById(R.id.spinnerProjectDetailGeneralArea);
        spinnerProjectDetailGeneralStages = (Spinner)rootView.findViewById(R.id.spinnerProjectDetailGeneralStages);
        spinnerProjectDetailGeneralArt = (Spinner)rootView.findViewById(R.id.spinnerProjectDetailGeneralArt);
        spinnerProjectDetailGeneralType = (Spinner)rootView.findViewById(R.id.spinnerProjectDetailGeneralType);
        spinnerProjectDetailGeneralPhase = (Spinner)rootView.findViewById(R.id.spinnerProjectDetailGeneralPhase);
        spinnerProjectDetailGeneralStages1 = (Spinner)rootView.findViewById(R.id.spinnerProjectDetailGeneralStages1);
        spinnerProjectDetailGeneralZustAdm = (Spinner)rootView.findViewById(R.id.spinnerProjectDetailGeneralZustAdm);

        textProjectDetailGeneralMatchCode = (EditText)rootView.findViewById(R.id.textProjectDetailGeneralMatchCode);
        textProjectDetailGeneralDescription = (EditText)rootView.findViewById(R.id.textProjectDetailGeneralDescription);
        textProjectDetailGeneralRoad = (EditText)rootView.findViewById(R.id.textProjectDetailGeneralRoad);
        textProjectDetailGeneralZipCode = (EditText)rootView.findViewById(R.id.textProjectDetailGeneralZipCode);
        textProjectDetailGeneralPlace = (EditText)rootView.findViewById(R.id.textProjectDetailGeneralPlace);
        textProjectDetailGeneralStartOfConstruction = (EditText)rootView.findViewById(R.id.textProjectDetailGeneralStartOfConstruction);
        textProjectDetailGeneralEndOfConstruction = (TextView)rootView.findViewById(R.id.textProjectDetailGeneralEndOfConstruction);
        textProjectDetailGeneralHeight = (EditText)rootView.findViewById(R.id.textProjectDetailGeneralHeight);
        textProjectDetailGeneralHeightFrom = (EditText)rootView.findViewById(R.id.textProjectDetailGeneralHeightFrom);
        textProjectDetailGeneralDateOfUpdate = (EditText)rootView.findViewById(R.id.textProjectDetailGeneralDateOfUpdate);
        textProjectDetailGeneralSize = (TextView)rootView.findViewById(R.id.textProjectDetailGeneralSize);

        checkboxProjectDetailGeneralAbgeschlosse = (CheckBox)rootView.findViewById(R.id.checkboxProjectDetailGeneralAbgeschlosse);
        checkboxProjectDetailGeneralRamp = (CheckBox)rootView.findViewById(R.id.checkboxProjectDetailGeneralRamp);
        checkboxProjectDetailGeneralMatureWhite = (CheckBox)rootView.findViewById(R.id.checkboxProjectDetailGeneralMatureWhite);

        imageButtonGenerallyEndDate = (ImageButton)rootView.findViewById(R.id.imageButtonGenerallyEndDate);
        imageButtonGenerallyStartDate = (ImageButton)rootView.findViewById(R.id.imageButtonGenerallyStartDate);

        imageButtonGenerallyEndDate.setOnClickListener(this);
        imageButtonGenerallyStartDate.setOnClickListener(this);

        listOfProjectArea = new ArrayList<>();
        listOfProjectArt = new ArrayList<>();
        listOfProjectPhase = new ArrayList<>();
        listOfProjectStage = new ArrayList<>();
        listOfProjectType = new ArrayList<>();
        listOfProjectStage1 = new ArrayList<>();
        listOfEmployee.addAll(db.getEmployees());
        Collections.sort(listOfEmployee, new Comparator<EmployeeModel>() {
            @Override
            public int compare(EmployeeModel s1, EmployeeModel s2) {
                return s1.getNachname().compareToIgnoreCase(s2.getNachname());
            }
        });
        adapterProjectArea = new ProjectAreaAdapter(getActivity(), listOfProjectArea, R.layout.list_item_spinner_country, language);
        adapterProjectArt = new ProjectArtAdapter(getActivity(), listOfProjectArt, R.layout.list_item_spinner_country, language);
        adapterProjectPhase = new ProjectPhaseAdapter(getActivity(), listOfProjectPhase, R.layout.list_item_spinner_country, language);
        adapterProjectStage = new ProjectStagesAdapter(getActivity(), listOfProjectStage, R.layout.list_item_spinner_country, language);
        adapterProjectType = new ProjectTypeAdapter(getActivity(), listOfProjectType, R.layout.list_item_spinner_country, language);
        adapterProjectSatge1 = new ProjectBuheneartAdapter(getActivity(),listOfProjectStage1, R.layout.list_item_spinner_country,language);
        adapterEmployee = new EmployeeAdapter2(getActivity(),listOfEmployee, R.layout.list_item_employee,language);

        spinnerProjectDetailGeneralArea.setAdapter(adapterProjectArea);
        spinnerProjectDetailGeneralStages.setAdapter(adapterProjectStage);
        spinnerProjectDetailGeneralArt.setAdapter(adapterProjectArt);
        spinnerProjectDetailGeneralType.setAdapter(adapterProjectType);
        spinnerProjectDetailGeneralPhase.setAdapter(adapterProjectPhase);
        spinnerProjectDetailGeneralStages1.setAdapter(adapterProjectSatge1);
        spinnerProjectDetailGeneralZustAdm.setAdapter(adapterEmployee);


        listOfProjectArea.addAll(db.getProjectArea());
        listOfProjectArt.addAll(db.getProjectArt());
        listOfProjectPhase.addAll(db.getProjectPhase());
        listOfProjectStage.addAll(db.getProjectStage());
        listOfProjectType.addAll(db.getProjectType());
        listOfProjectStage1.addAll(db.getBuheneart());
        spinnerProjectDetailGeneralStages1.setSelection(0);

//        Log.e("listofproject ara size", listOfProjectArea.size()+"");
//        Log.e("listOfProjectArt ara size", listOfProjectArt.size()+"");
//        Log.e("listOfProjectPhase ara size", listOfProjectPhase.size()+"");
//        Log.e("listOfProjectStage ara size", listOfProjectStage.size()+"");
//        Log.e("listOfProjectType ara size", listOfProjectType.size() + "");

        adapterProjectArea.notifyDataSetChanged();
        adapterProjectArt.notifyDataSetChanged();
        adapterProjectPhase.notifyDataSetChanged();
        adapterProjectStage.notifyDataSetChanged();
        adapterProjectType.notifyDataSetChanged();
        adapterProjectSatge1.notifyDataSetChanged();

       // setupLanguage();
        showLoadedProjectData();
        makeEditable(false);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelProject());
        //prd.dismiss();

    }

    @Override
    public void bindEvents(View rootView) {
        super.bindEvents(rootView);
        spinnerProjectDetailGeneralArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedArea = null;
                } else {
                    selectedArea = listOfProjectArea.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerProjectDetailGeneralStages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0)
                {
                    selectedProjectStage = null;
                }
                else
                {
                    selectedProjectStage = listOfProjectStage.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        spinnerProjectDetailGeneralArt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0)
                {
                    selectedProjectArt = null;
                }
                else
                {
                    selectedProjectArt = listOfProjectArt.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        spinnerProjectDetailGeneralType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0)
                {
                    selectedProjectType = null;
                }
                else
                {
                    selectedProjectType = listOfProjectType.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        spinnerProjectDetailGeneralPhase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0)
                {
                    selectedProjectPhase = null;
                    textProjectDetailGeneralDateOfUpdate.setText("");
                    isFirstTIme=false;
                    isFirstTime2=false;
                }
                else
                {
                    selectedProjectPhase = listOfProjectPhase.get(position - 1);
                    long date = System.currentTimeMillis();

                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    String dateString = format.format(date);

                    try {
                        if(isFirstTIme){
                            isFirstTIme=false;
                        }
                        else {
                            if(isFirstTime2){
                                isFirstTime2=false;
                            }
                            else {
                                textProjectDetailGeneralDateOfUpdate.setText(DataHelper.formatDate(format.parse(dateString)));
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        spinnerProjectDetailGeneralStages1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    selectedBuheneart = null;
                }
                else
                {
                    selectedBuheneart = listOfProjectStage1.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerProjectDetailGeneralZustAdm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    employeeModel = null;
                }
                else {
                    employeeModel = listOfEmployee.get(position);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showLoadedProjectData()
    {
        projectGenerallyDetail = matecoPriceApplication.getProjectDetailGenerallyModel(DataHelper.LoadedProjectDetailGenerallyInfo, new ProjectDetailGenerallyModel().toString());

        textProjectDetailGeneralMatchCode.setText(projectGenerallyDetail.getMatchCode());
        textProjectDetailGeneralDescription.setText(projectGenerallyDetail.getBeschreibung());
        textProjectDetailGeneralRoad.setText(projectGenerallyDetail.getStrasse());
        textProjectDetailGeneralZipCode.setText(projectGenerallyDetail.getPLZ());
        textProjectDetailGeneralPlace.setText(projectGenerallyDetail.getOrt());

        if(!projectGenerallyDetail.getHoehe().equals("0"))
            textProjectDetailGeneralHeight.setText(projectGenerallyDetail.getHoehe());

        textProjectDetailGeneralHeightFrom.setText(projectGenerallyDetail.getHoehe_von_bis());
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try
        {
            if(!TextUtils.isEmpty(projectGenerallyDetail.getBaubeginn())){
                if(!projectGenerallyDetail.getBaubeginn().equals("") && !projectGenerallyDetail.getBaubeginn().equals("01-01-1900"))
                    textProjectDetailGeneralStartOfConstruction.setText(DataHelper.formatDate(format.parse(projectGenerallyDetail.getBaubeginn())));
            }
            if(!TextUtils.isEmpty(projectGenerallyDetail.getBauende())){
                if(!projectGenerallyDetail.getBauende().equals("") && !projectGenerallyDetail.getBauende().equals("01-01-1900"))
                    textProjectDetailGeneralEndOfConstruction.setText(DataHelper.formatDate(format.parse(projectGenerallyDetail.getBauende())));
            }
            if(!TextUtils.isEmpty(projectGenerallyDetail.getDatum_Aktuell())){
                if(!projectGenerallyDetail.getDatum_Aktuell().equals("") && !projectGenerallyDetail.getDatum_Aktuell().equals("01-01-1900"))
                    textProjectDetailGeneralDateOfUpdate.setText(DataHelper.formatDate(format.parse(projectGenerallyDetail.getDatum_Aktuell())));
            }




        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        if(!projectGenerallyDetail.getGroesse().equals("0"))
            textProjectDetailGeneralSize.setText(projectGenerallyDetail.getGroesse());

        if(projectGenerallyDetail.getAbgeschlossen().equals("False"))
        {
            checkboxProjectDetailGeneralAbgeschlosse.setChecked(false);
        }
        else
        {
            checkboxProjectDetailGeneralAbgeschlosse.setChecked(true);
        }

        if(projectGenerallyDetail.getRampe().equals("0"))
        {
            checkboxProjectDetailGeneralRamp.setChecked(false);
        }
        else
        {
            checkboxProjectDetailGeneralRamp.setChecked(true);
        }

        if(projectGenerallyDetail.getWeisse_Reifen().equals("0") || projectGenerallyDetail.getWeisse_Reifen().equals("False"))
        {
            checkboxProjectDetailGeneralMatureWhite.setChecked(false);
        }
        else
        {
            checkboxProjectDetailGeneralMatureWhite.setChecked(true);
        }

        for(int i=0;i<listOfEmployee.size();i++)
        {
            if(projectGenerallyDetail.getZust_ADM().equals(listOfEmployee.get(i).getNachname() + ", " +listOfEmployee.get(i).getVorname())){
                spinnerProjectDetailGeneralZustAdm.setSelection(i);
                break;
            }
            else if(listOfEmployee.get(i).getMitarbeiter().equals(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new ArrayList<LoginPersonModel>().toString()).get(0).getUserNumber())) {
                spinnerProjectDetailGeneralZustAdm.setSelection(0);
                break;

            }



        }
        for(int i = 0; i < listOfProjectArea.size(); i++)
        {
            if(listOfProjectArea.get(i).getProjectAreaDesignation().equals(projectGenerallyDetail.getGebiet()))
            {
                spinnerProjectDetailGeneralArea.setSelection(i + 1);
                selectedArea = listOfProjectArea.get(i);
            }
        }

        for(int i = 0; i < listOfProjectPhase.size(); i++)
        {
            if(listOfProjectPhase.get(i).getProjectPhaseId().equals(projectGenerallyDetail.getPhaseID()))
            {
                spinnerProjectDetailGeneralPhase.setSelection(i + 1);
                selectedProjectPhase = listOfProjectPhase.get(i);
            }
        }

        for(int i = 0; i < listOfProjectArt.size(); i++)
        {
            if(listOfProjectArt.get(i).getProjectArtId().equals(projectGenerallyDetail.getProjektartID()))
            {
                spinnerProjectDetailGeneralArt.setSelection(i + 1);
                selectedProjectArt = listOfProjectArt.get(i);
            }
        }

        for(int i = 0; i < listOfProjectType.size(); i++)
        {
            if(listOfProjectType.get(i).getProjectTypeId().equals(projectGenerallyDetail.getProjekttypID()))
            {
                spinnerProjectDetailGeneralType.setSelection(i + 1);
                selectedProjectType = listOfProjectType.get(i);
            }
        }

        for(int i = 0; i < listOfProjectStage.size(); i++)
        {
            if(listOfProjectStage.get(i).getProjectStageId().equals(projectGenerallyDetail.getArtaussenID()))
            {
                spinnerProjectDetailGeneralStages.setSelection(i + 1);
                selectedProjectStage = listOfProjectStage.get(i);
            }
        }

        for(int i = 0; i < listOfProjectStage1.size(); i++)
        {
            if(listOfProjectStage1.get(i).getBuehnenArt().equals(projectGenerallyDetail.getArtInnenID()))
            {
                spinnerProjectDetailGeneralStages1.setSelection(i + 1);
                selectedBuheneart = listOfProjectStage1.get(i);
            }
        }
    }

    private void setupLanguage()
    {
        TextView labelProjectDetailGeneralMatchCode, labelProjectDetailGeneralDescription, labelProjectDetailGeneralRoad, labelProjectDetailGeneralZipCode,
                labelProjectDetailGeneralPlace, labelProjectDetailGeneralArea, labelProjectDetailGeneralStartOfConstruction, labelProjectDetailGeneralEndOfConstruction,
                labelProjectDetailGeneralSize, labelProjectDetailGeneralHeight, labelProjectDetailGeneralHighFrom, labelProjectDetailGeneralProjectStage,
                labelProjectDetailGeneralProjectArt, labelProjectDetailGeneralProjectType, labelProjectDetailGeneralProjectPhase, labelProjectDetailGeneralProjectDateOfUpdate,
                labelProjectDetailGeneralProjectZustAdm, labelProjectDetailGeneralProjectAbgeschlosse, labelProjectDetailGeneralProjectRamp, labelProjectDetailGeneralProjectMatureWhite,labelProjectDetailGeneralProjectStage1;

        labelProjectDetailGeneralMatchCode = (TextView)rootView.findViewById(R.id.labelProjectDetailGeneralMatchCode);
        labelProjectDetailGeneralDescription = (TextView)rootView.findViewById(R.id.labelProjectDetailGeneralDescription);
        labelProjectDetailGeneralRoad = (TextView)rootView.findViewById(R.id.labelProjectDetailGeneralRoad);
        labelProjectDetailGeneralZipCode = (TextView)rootView.findViewById(R.id.labelProjectDetailGeneralZipCode);
        labelProjectDetailGeneralPlace = (TextView)rootView.findViewById(R.id.labelProjectDetailGeneralPlace);
        labelProjectDetailGeneralArea = (TextView)rootView.findViewById(R.id.labelProjectDetailGeneralArea);
        labelProjectDetailGeneralStartOfConstruction = (TextView)rootView.findViewById(R.id.labelProjectDetailGeneralStartOfConstruction);
        labelProjectDetailGeneralEndOfConstruction = (TextView)rootView.findViewById(R.id.labelProjectDetailGeneralEndOfConstruction);
        labelProjectDetailGeneralSize = (TextView)rootView.findViewById(R.id.labelProjectDetailGeneralSize);
        labelProjectDetailGeneralHeight = (TextView)rootView.findViewById(R.id.labelProjectDetailGeneralHeight);
        labelProjectDetailGeneralHighFrom = (TextView)rootView.findViewById(R.id.labelProjectDetailGeneralHighFrom);
        labelProjectDetailGeneralProjectStage = (TextView)rootView.findViewById(R.id.labelProjectDetailGeneralProjectStage);
        labelProjectDetailGeneralProjectArt = (TextView)rootView.findViewById(R.id.labelProjectDetailGeneralProjectArt);
        labelProjectDetailGeneralProjectType = (TextView)rootView.findViewById(R.id.labelProjectDetailGeneralProjectType);
        labelProjectDetailGeneralProjectPhase = (TextView)rootView.findViewById(R.id.labelProjectDetailGeneralProjectPhase);
        labelProjectDetailGeneralProjectDateOfUpdate = (TextView)rootView.findViewById(R.id.labelProjectDetailGeneralProjectDateOfUpdate);
        labelProjectDetailGeneralProjectZustAdm = (TextView)rootView.findViewById(R.id.labelProjectDetailGeneralProjectZustAdm);
        labelProjectDetailGeneralProjectAbgeschlosse = (TextView)rootView.findViewById(R.id.labelProjectDetailGeneralProjectAbgeschlosse);
        labelProjectDetailGeneralProjectRamp = (TextView)rootView.findViewById(R.id.labelProjectDetailGeneralProjectRamp);
        labelProjectDetailGeneralProjectMatureWhite = (TextView)rootView.findViewById(R.id.labelProjectDetailGeneralProjectMatureWhite);
        labelProjectDetailGeneralProjectStage1 = (TextView)rootView.findViewById(R.id.labelProjectDetailGeneralProjectStage1);

        labelProjectDetailGeneralMatchCode.setText(language.getLabelMatchCode());
        labelProjectDetailGeneralDescription.setText(language.getLabelDescription());
        labelProjectDetailGeneralRoad.setText(language.getLabelRoad());
        labelProjectDetailGeneralZipCode.setText(language.getLabelZipCode());
        labelProjectDetailGeneralPlace.setText(language.getLabelPlace());
        labelProjectDetailGeneralArea.setText(language.getLabelArea());
        labelProjectDetailGeneralStartOfConstruction.setText(language.getLabelStartOfConstruction());
        labelProjectDetailGeneralEndOfConstruction.setText(language.getLabelEndOfConstruction());
        labelProjectDetailGeneralSize.setText(language.getLabelSize());
        labelProjectDetailGeneralHeight.setText(language.getLabelHeight());
        labelProjectDetailGeneralHighFrom.setText(language.getLabelHeightScale1());
        labelProjectDetailGeneralProjectStage.setText(language.getLabelProjectStage());
        labelProjectDetailGeneralProjectArt.setText(language.getLabelProjectArt());
        labelProjectDetailGeneralProjectType.setText(language.getLabelProjectType());
        labelProjectDetailGeneralProjectPhase.setText(language.getLabelProjectPhase());
        labelProjectDetailGeneralProjectDateOfUpdate.setText(language.getLabelDateOfUpdate());
        labelProjectDetailGeneralProjectZustAdm.setText(language.getLabelStatesADM());
        labelProjectDetailGeneralProjectAbgeschlosse.setText(language.getLabelAbgeschlosse());
        labelProjectDetailGeneralProjectRamp.setText(language.getLabelRamp());
        labelProjectDetailGeneralProjectMatureWhite.setText(language.getLabelMatureWhite());
        labelProjectDetailGeneralProjectStage1.setText(language.getLabelProjectStage1());
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
            case R.id.actionEdit:
                makeEditable(true);
                menu.findItem(R.id.actionEdit).setVisible(false);
                menu.findItem(R.id.actionRight).setVisible(true);
                menu.findItem(R.id.actionWrong).setVisible(true);
                menu.findItem(R.id.actionBack).setVisible(false);
                isInEditMode = true;
                return true;
            case R.id.actionWrong:
                makeEditable(false);
                isInEditMode = false;
                menu.findItem(R.id.actionEdit).setVisible(true);
                menu.findItem(R.id.actionRight).setVisible(false);
                menu.findItem(R.id.actionWrong).setVisible(false);
                menu.findItem(R.id.actionBack).setVisible(true);
                return true;
            case R.id.actionRight:
                updateProjectGenerallyInfo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    private void makeEditable(boolean enableEditing)
    {
        checkboxProjectDetailGeneralAbgeschlosse.setEnabled(enableEditing);
        checkboxProjectDetailGeneralRamp.setEnabled(enableEditing);
        checkboxProjectDetailGeneralMatureWhite.setEnabled(enableEditing);

        textProjectDetailGeneralMatchCode.setFocusable(enableEditing);
        textProjectDetailGeneralMatchCode.setFocusableInTouchMode(enableEditing);

        textProjectDetailGeneralDescription.setFocusable(enableEditing);
        textProjectDetailGeneralDescription.setFocusableInTouchMode(enableEditing);

        textProjectDetailGeneralRoad.setFocusable(enableEditing);
        textProjectDetailGeneralRoad.setFocusableInTouchMode(enableEditing);

        textProjectDetailGeneralZipCode.setFocusable(enableEditing);
        textProjectDetailGeneralZipCode.setFocusableInTouchMode(enableEditing);

        textProjectDetailGeneralPlace.setFocusable(enableEditing);
        textProjectDetailGeneralPlace.setFocusableInTouchMode(enableEditing);

        textProjectDetailGeneralStartOfConstruction.setFocusable(false);
        textProjectDetailGeneralStartOfConstruction.setFocusableInTouchMode(false);

        textProjectDetailGeneralEndOfConstruction.setFocusable(false);
        textProjectDetailGeneralEndOfConstruction.setFocusableInTouchMode(false);

        textProjectDetailGeneralSize.setFocusable(enableEditing);
        textProjectDetailGeneralSize.setFocusableInTouchMode(enableEditing);

        textProjectDetailGeneralHeight.setFocusable(enableEditing);
        textProjectDetailGeneralHeight.setFocusableInTouchMode(enableEditing);

        textProjectDetailGeneralHeightFrom.setFocusable(enableEditing);
        textProjectDetailGeneralHeightFrom.setFocusableInTouchMode(enableEditing);

        textProjectDetailGeneralDateOfUpdate.setFocusable(false);
        textProjectDetailGeneralDateOfUpdate.setFocusableInTouchMode(false);


        spinnerProjectDetailGeneralZustAdm.setEnabled(enableEditing);
        spinnerProjectDetailGeneralArea.setEnabled(enableEditing);
        spinnerProjectDetailGeneralStages.setEnabled(enableEditing);
        spinnerProjectDetailGeneralStages1.setEnabled(enableEditing);
        spinnerProjectDetailGeneralArt.setEnabled(enableEditing);
        spinnerProjectDetailGeneralType.setEnabled(enableEditing);
        spinnerProjectDetailGeneralPhase.setEnabled(enableEditing);

        imageButtonGenerallyEndDate.setEnabled(enableEditing);
        imageButtonGenerallyStartDate.setEnabled(enableEditing);
    }

    private void updateProjectGenerallyInfo()
    {
        if(DataHelper.isNetworkAvailable(getActivity()))
        {
            String matchCode = textProjectDetailGeneralMatchCode.getText().toString();
            String description = textProjectDetailGeneralDescription.getText().toString();
            String road = textProjectDetailGeneralRoad.getText().toString();
            String zipCode = textProjectDetailGeneralZipCode.getText().toString();
            String place = textProjectDetailGeneralPlace.getText().toString();

            String area = "";
            if(selectedArea != null)
            {
                area = selectedArea.getProjectAreaId();
            }
            String startOfConstruction = textProjectDetailGeneralStartOfConstruction.getText().toString();
            if(!startOfConstruction.equals(""))
            {
                // my
                startOfConstruction = DataHelper.formatDateToOriginal(textProjectDetailGeneralStartOfConstruction.getText().toString());
            }

            //String startOfConstruction = DataHelper.formatDateToOriginal(textProjectDetailGeneralStartOfConstruction.getText().toString());
            String endOfConstruction = textProjectDetailGeneralEndOfConstruction.getText().toString();
            if(!endOfConstruction.equals(""))
            {
                // my
                endOfConstruction = DataHelper.formatDateToOriginal(textProjectDetailGeneralEndOfConstruction.getText().toString());
            }
            //String endOfConstruction = DataHelper.formatDateToOriginal(textProjectDetailGeneralEndOfConstruction.getText().toString());
            String size = textProjectDetailGeneralSize.getText().toString();
            String height = textProjectDetailGeneralHeight.getText().toString();
            String heightFrom = textProjectDetailGeneralHeightFrom.getText().toString();
            String projectStage = "";
            if(selectedProjectStage != null)
            {
                projectStage = selectedProjectStage.getProjectStageId();
            }
            String justAdm = "";
            if(employeeModel!=null)
                justAdm = employeeModel.getNachname() + ", " +employeeModel.getVorname();
            Log.e("just",justAdm);

            String projectArt = "";
            if(selectedProjectArt != null)
            {
                projectArt = selectedProjectArt.getProjectArtId();
            }

            String projectType = "";
            if(selectedProjectType != null)
            {
                projectType = selectedProjectType.getProjectTypeId();
            }
            String projectPhase = "";
            if(selectedProjectPhase != null)
            {
                projectPhase = selectedProjectPhase.getProjectPhaseId();
            }

            String projectBuheneart = "";
            if(selectedBuheneart != null)
            {
                projectBuheneart = selectedBuheneart.getBuehnenArt();
            }
            String projectDate = textProjectDetailGeneralDateOfUpdate.getText().toString();
            if(!projectDate.equals(""))
            {
                projectDate = DataHelper.formatDateToOriginal(textProjectDetailGeneralDateOfUpdate.getText().toString());
            }

            String abgeschlosse;
            if(checkboxProjectDetailGeneralAbgeschlosse.isChecked())
            {
                abgeschlosse = "True";
            }
            else
            {
                abgeschlosse = "False";
            }

            String ramp;
            if(checkboxProjectDetailGeneralRamp.isChecked())
            {
                ramp = "1";
            }
            else
            {
                ramp = "0";
            }

            String matureWhite;
            if(checkboxProjectDetailGeneralMatureWhite.isChecked())
            {
                matureWhite = "1";
            }
            else
            {
                matureWhite = "0";
            }
            Log.e(" update ###### "," stat and end date on update : "+startOfConstruction+" : "+endOfConstruction);
            if(startOfConstruction.equalsIgnoreCase("")){
                //startOfConstruction="null";
                startOfConstruction="";
            }
            if(endOfConstruction.equalsIgnoreCase("")){
                //endOfConstruction="null";
                endOfConstruction="";
            }
            if(projectDate.equalsIgnoreCase("")){
                //projectDate="null";
                projectDate="";
            }


            ProjectDetailGenerallyUpdateModel model = new ProjectDetailGenerallyUpdateModel();
                if(!TextUtils.isEmpty(projectGenerallyDetail.getProjekt())){
                if(projectGenerallyDetail.getProjekt().equalsIgnoreCase("null") ||
                        projectGenerallyDetail.getProjekt().equalsIgnoreCase("") ||
                        projectGenerallyDetail.getProjekt() == null){
                    model.setProjekt("");
                }
                else {
                    model.setProjekt(projectGenerallyDetail.getProjekt());
                }
            }
            else {
                    model.setProjekt("");
            }

            model.setMatchCode(matchCode);
            model.setBeschreibung(description);
            model.setOrt(place);
            model.setPLZ(zipCode);
            model.setStrasse(road);
            model.setGebiet(area);
            model.setBaubeginn(startOfConstruction);
            model.setBauende(endOfConstruction);
            model.setHoehe(height);
            model.setHoehe_von_bis(heightFrom);
            model.setPhaseID(projectPhase);
            model.setProjektart(projectArt);
            model.setProjekttypID(projectType);
            model.setDatum(projectDate);
            //model.setMitarbeiter(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber());
            if(employeeModel == null || employeeModel.getMitarbeiter().equals(""))
                model.setMitarbeiter(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber());
            else
                model.setMitarbeiter(employeeModel.getMitarbeiter());
            model.setArtaussenID(projectStage);
            model.setArtInnen(projectBuheneart);
            model.setGroesse(size);
            model.setNotiz(projectGenerallyDetail.getNotiz());
            //model.setNotiz("");
            model.setArtaussenID(projectGenerallyDetail.getArtaussenID());
            model.setAenderungMitarbeiter(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber());
            model.setRampe(ramp);
            model.setWeisse_Reifen(matureWhite);
            model.setAbgeschlossen(abgeschlosse);
            model.setZust_ADM(justAdm);

            AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult() {
                @Override
                public void OnAsynResult(String result) {
                    Log.e(" first time : "," eror while calling general fragment  : "+result);
                    if(result.equals("error"))
                    {
                        Log.e(" resulte erro : "," eror while calling general fragment  : "+result);
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
                            isInEditMode = false;
                            menu.findItem(R.id.actionEdit).setVisible(true);
                            menu.findItem(R.id.actionRight).setVisible(false);
                            menu.findItem(R.id.actionWrong).setVisible(false);
                            menu.findItem(R.id.actionBack).setVisible(true);
                        }
                        catch (Exception ex)
                        {
                            Log.e(" parsing erorro: "," eror while calling general fragment  : "+ex.toString());
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
                /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.PROJECT_GENERALLY_UPDATE
                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "&projectgenerally=" + URLEncoder.encode(json, "UTF-8");*/
                String url = DataHelper.URL_PROJECT_HELPER+"projectgenerallyupdate";
                        //+ "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        //+ "&projectgenerally=" + URLEncoder.encode(json, "UTF-8");
                Log.e("url", url);
                MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")));
                multipartEntity.addPart("projectgenerally", new StringBody(json, Charset.forName("UTF-8")));
                AsyncTaskWithAuthorizationHeaderPost asyncTaskPost = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, getActivity(), multipartEntity, true, language);
                asyncTaskPost.execute();

                /*BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                asyncTask.execute();*/
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonGenerallyStartDate:
                clickedTextbox="first";
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getActivity().getFragmentManager(), "DatePicker");
                //openDatePicker("start");
                break;
            case R.id.imageButtonGenerallyEndDate:
                clickedTextbox="second";
                DialogFragment newFragment2 = new SelectDateFragment();
                newFragment2.show(getActivity().getFragmentManager(), "DatePicker");
                //openDatePicker("end");
        }
    }

    DatePickerDialog.OnDateSetListener onFromDate = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
            try
            {
                monthOfYear += 1;
                String date = dayOfMonth + " " + monthOfYear + " " + year;
                String finaldate = DataHelper.formatDate(formatter.parse(date));
                Log.e("startDate", finaldate);
                textProjectDetailGeneralStartOfConstruction.setText(finaldate);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
    };

    DatePickerDialog.OnDateSetListener onEndDate = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
            try
            {
                monthOfYear += 1;
                String date = dayOfMonth + " " + monthOfYear + " " + year;
                String finaldate = DataHelper.formatDate(formatter.parse(date));
                Log.e("startDate", finaldate);
                textProjectDetailGeneralEndOfConstruction.setText(finaldate);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
    };

    private void openDatePicker(String flag)
    {
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        DatePickerDialogFragment newFragment = new DatePickerDialogFragment();
        Bundle args = new Bundle();
        args.putInt("year", c.get(Calendar.YEAR));
        args.putInt("month", c.get(Calendar.MONTH));
        args.putInt("day", c.get(Calendar.DAY_OF_MONTH));
        newFragment.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        if(flag.equals("start"))
            newFragment.setCallBack(onFromDate);
        else
            newFragment.setCallBack(onEndDate);
        Log.e("tag", "startDate");
        //newFragment.setMinDate(today);
        newFragment.show(getActivity().getSupportFragmentManager(), "startDate");
    }
    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
    {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }


        public void populateSetDate(int day, int month, int year) {
            Log.e(" in method"," in populate date set mothod : "+day+" : "+month+" : "+year+" : "+clickedTextbox);
            if(!TextUtils.isEmpty(clickedTextbox)){
                if(clickedTextbox.equalsIgnoreCase("first")){

                    Log.e(" in method 22222"," in populate date set mothod : "+day+" : "+month+" : "+year+" : "+clickedTextbox);
                    if(!TextUtils.isEmpty(textProjectDetailGeneralEndOfConstruction.getText().toString())){
                        String selecteddate=day+"-"+month+"-"+year;
                        String seconddate = textProjectDetailGeneralEndOfConstruction.getText().toString().replace(".","-");
                        Log.e(" in method 3333"," in populate date set mothod : "+selecteddate+" : "+seconddate);
                        if(compareDate1WithDate2(selecteddate,seconddate))
                        {
                            textProjectDetailGeneralStartOfConstruction.setText(new StringBuilder().append(day).append(".")
                                    .append(month).append(".").append(year));
                        }
                        else {
                            // give message here
                            GlobalClass.showToast(getActivity(),"please selecte proprer start date");
                        }
                    }
                    else {
                        textProjectDetailGeneralStartOfConstruction.setText(new StringBuilder().append(day).append(".")
                                .append(month).append(".").append(year));
                    }

                }
                else if(clickedTextbox.equalsIgnoreCase("second")) {
                    if(!TextUtils.isEmpty(textProjectDetailGeneralStartOfConstruction.getText().toString())){
                        String selecteddate=day+"-"+month+"-"+year;
                        String seconddate = textProjectDetailGeneralStartOfConstruction.getText().toString().replace(".","-");
                        Log.e(" in method 44444"," in populate date set mothod : "+selecteddate+" : "+seconddate);
                        if(compareDate2WithDate1(seconddate,selecteddate))
                        {
                            textProjectDetailGeneralEndOfConstruction.setText(new StringBuilder().append(day).append(".")
                                    .append(month).append(".").append(year));
                        }
                        else {
                            GlobalClass.showToast(getActivity(),"please selecte proprer end date");
                            // give message here
                        }
                    }
                    else {
                        textProjectDetailGeneralEndOfConstruction.setText(new StringBuilder().append(day).append(".")
                                .append(month).append(".").append(year));
                    }
                }

            }
        }


        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            Log.e(" on date set"," populatesetdate clicked : "+dayOfMonth+" : "+monthOfYear+" : "+year);
            populateSetDate(dayOfMonth, monthOfYear+1, year);
        }
    }
    public  static Boolean compareDate1WithDate2(String date1,String date2)
    {
        Boolean flag=false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date dateStart = sdf.parse(date1);
            Date dateEnd = sdf.parse(date2);

            if(dateStart.equals(dateEnd))
            {
                flag=true;
            }

            if(dateStart.before(dateEnd)){
                flag=true;
            }

            if(dateStart.after(dateEnd)){
                flag=false;
            }
        }
        catch (Exception e){

        }

        return  flag;

    }
    public  static Boolean compareDate2WithDate1(String date1,String date2)
    {
        Boolean flag=false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date dateStart = sdf.parse(date1);
            Date dateEnd = sdf.parse(date2);

            if(dateEnd.equals(dateStart))
            {
                flag=true;
            }

            if(dateEnd.before(dateStart)){
                flag=false;
            }

            if(dateEnd.after(dateStart)){
                flag=true;
            }
        }
        catch (Exception e){

        }

        return  flag;

    }
}
