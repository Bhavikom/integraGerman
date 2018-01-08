package de.mateco.integrAMobile.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.model.Language;

public class CustomerDashBoardFragment extends BaseFragment
{
    private View rootView;
    private Language language;
    private MatecoPriceApplication matecoPriceApplication;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        rootView = inflater.inflate(R.layout.temp_under_construction, container, false);
        super.initializeFragment(rootView);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelDashBoard());
        setUpLanguage();
        return rootView;
    }

    @Override
    public void initializeComponents(View rootView)
    {

        super.initializeComponents(rootView);
    }

    @Override
    public void bindEvents(View rootView)
    {

        super.bindEvents(rootView);
    }

    private void setUpLanguage()
    {
        TextView labelLostSaleReasons, labelCustomerTypeTrend, labelCustomerDistributionType;

//        labelCustomerDistributionType = (TextView)rootView.findViewById(R.id.labelCustomerDistributionType);
//        labelCustomerTypeTrend = (TextView)rootView.findViewById(R.id.labelCustomerTypeTrend);
//        labelLostSaleReasons = (TextView)rootView.findViewById(R.id.labelLostSaleReasons);
//
//        labelCustomerDistributionType.setText(language.getLabelCustomerDistributionType());
//        labelCustomerTypeTrend.setText(language.getLabelCustomerTypeTrend());
//        labelLostSaleReasons.setText(language.getLabelLostSaleReason());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);
        menu.clear();
        //return true;
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.actionSettings:
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new SettingFragment(),"Setting");
                //transaction.addToBackStack(SettingFragment.Tag);
                transaction.addToBackStack("Setting");
                transaction.commit();
                return true;
            case R.id.actionBack:
                if (getFragmentManager().getBackStackEntryCount() == 0)
                {
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.replace(R.id.content_frame, new HomeFragment());
                    getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    transaction.commit();
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
}
