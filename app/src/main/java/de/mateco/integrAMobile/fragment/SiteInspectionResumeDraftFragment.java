package de.mateco.integrAMobile.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.SiteInspectionResumeAdapter;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.SiteInspection;
import de.mateco.integrAMobile.model.SiteInspectionModel;

public class SiteInspectionResumeDraftFragment extends BaseFragment {

    private View rootView;
    private MatecoPriceApplication application;
    private Language language;
    private SharedPreferences preferences;
    private ArrayList<SiteInspectionModel> listOfSiteInspection = new ArrayList<>();
    private DataBaseHandler db;
    private TextView labelDate;
    private TextView labelKunde;
    private TextView labelMatchCode;
    private TextView labelZipCode;
    private TextView labelPlace;
    private TextView labelRoad;
    private TextView labelLongitude;
    private TextView labelLatitude;

    private ListView listResume;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_site_inspection_resume_tab, container, false);
        application = (MatecoPriceApplication)getActivity().getApplication();
        language = application.getLanguage();
        db = new DataBaseHandler(getActivity());
        getActivity().invalidateOptionsMenu();
        preferences = getActivity().getSharedPreferences("SiteInspection", Context.MODE_PRIVATE);
        setLanguage();
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelBvoResume());
        SiteInspectionModel model = db.getSiteInspection(db.getSiteInspectionId());

        listOfSiteInspection = db.getSiteInspectionList();
        listResume = (ListView)rootView.findViewById(R.id.listResume);
        SiteInspectionResumeAdapter adapter = new SiteInspectionResumeAdapter(getActivity(),listOfSiteInspection);
        listResume.setAdapter(adapter);
        listResume.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle args = new Bundle();
                args.putString("Resume","Resume");
                SiteInspectionNewFragment fragment = new SiteInspectionNewFragment();
                fragment.setArguments(args);
                preferences.edit().putBoolean(DataHelper.isSiteInspectionLoaded,true).commit();
                preferences.edit().putInt(DataHelper.SiteInspectionId,listOfSiteInspection.get(position).getId()).commit();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame,fragment);
                transaction.addToBackStack("Setting");
                transaction.commit();
            }
        });
        return rootView;
    }

    private void setLanguage() {
        labelDate = (TextView)rootView.findViewById(R.id.labelDate);
        labelKunde = (TextView)rootView.findViewById(R.id.labelKunde);
        labelMatchCode = (TextView)rootView.findViewById(R.id.labelMatchCode);
        labelZipCode = (TextView)rootView.findViewById(R.id.labelZipCode);
        labelPlace = (TextView)rootView.findViewById(R.id.labelPlace);
        labelRoad = (TextView)rootView.findViewById(R.id.labelRoad);
        labelLongitude = (TextView)rootView.findViewById(R.id.labelLongitude);
        labelLatitude = (TextView)rootView.findViewById(R.id.labelLatitude);

        labelDate.setText(language.getLabelInsertDate());
        labelKunde.setText(language.getLabelCustomer());
        labelMatchCode.setText(language.getLabelMatchCode());
        labelZipCode.setText(language.getLabelZipCode());
        labelPlace.setText(language.getLabelPlace());
        labelRoad.setText(language.getLabelRoad());
        labelLatitude.setText(language.getLabelLatitude());
        labelLongitude.setText(language.getLabelLongitude());
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
        menu.findItem(R.id.action_settings).setVisible(false);
        //return true;
        //super.onCreateOptionsMenu(menu, inflater);
    }
}
