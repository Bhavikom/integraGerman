package de.mateco.integrAMobile.fragment;

import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.CustomerOpenSpecialAdapter;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.model.CustomerOpenOfferModel;
import de.mateco.integrAMobile.model.Language;

public class CustomerOffersOpenOffersFragment extends BaseFragment
{
    private Language language;
    private View rootView;
    private MatecoPriceApplication matecoPriceApplication;
    private ListView listViewCustomerOpenSpecials;
    private ArrayList<CustomerOpenOfferModel> listOfOpenSpecials;
    private CustomerOpenSpecialAdapter customerOpenSpecialAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_customer_open_specials, container, false);
        super.initializeFragment(rootView);

        return rootView;
    }

    @Override
    public void initializeComponents(View rootView)
    {
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        listViewCustomerOpenSpecials = (ListView)rootView.findViewById(R.id.listViewCustomerOpenSpecials);
        listOfOpenSpecials = new ArrayList<>();

        listOfOpenSpecials = matecoPriceApplication.getLoadedCustomerOpenSpecials(DataHelper.LoadedCustomerOpenSpecials, new ArrayList<>().toString());
        customerOpenSpecialAdapter = new CustomerOpenSpecialAdapter(getActivity(), listOfOpenSpecials);
        listViewCustomerOpenSpecials.setAdapter(customerOpenSpecialAdapter);

        getActivity().invalidateOptionsMenu();
//        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelOpenOffer());
//        Log.e("here at", language.getLabelOpenOffer());
        setHasOptionsMenu(true);
        setUpLanguage();
        super.initializeComponents(rootView);
    }

    @Override
    public void bindEvents(View rootView)
    {
        listViewCustomerOpenSpecials.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //view.setSelected(true);
                customerOpenSpecialAdapter.setSelectedIndex(position);
            }
        });
        super.bindEvents(rootView);
    }

    private void setUpLanguage()
    {
        TextView labelNL, labelAngebot, labelErstellt_am, labelHohengruppe, labelGeraetetyp, labelAnzahlGeraete, labelMietdauer,
                labelStaffel, labelPreis, labelHaftb, labelStatus;

        labelNL = (TextView)rootView.findViewById(R.id.labelNL);
        labelAngebot = (TextView)rootView.findViewById(R.id.labelAngebot);
        labelErstellt_am = (TextView)rootView.findViewById(R.id.labelErstellt_am);
        labelHohengruppe = (TextView)rootView.findViewById(R.id.labelHohengruppe);
        labelGeraetetyp = (TextView)rootView.findViewById(R.id.labelGeraetetyp);
        labelAnzahlGeraete = (TextView)rootView.findViewById(R.id.labelAnzahlGeraete);
        labelMietdauer = (TextView)rootView.findViewById(R.id.labelMietdauer);
        labelStaffel = (TextView)rootView.findViewById(R.id.labelStaffel);
        labelPreis = (TextView)rootView.findViewById(R.id.labelPreis);
        labelHaftb = (TextView)rootView.findViewById(R.id.labelHaftb);
        labelStatus = (TextView)rootView.findViewById(R.id.labelStatus);

        labelStatus.setText(language.getLabelStatus());
        labelNL.setText(language.getLabelNL());
        labelAngebot.setText(language.getLabelOffer());
        labelErstellt_am.setText(language.getLabelCreatedOn());
        labelHohengruppe.setText(language.getLabelLevelGroup());
        labelGeraetetyp.setText(language.getLabelDeviceType());
        labelAnzahlGeraete.setText(language.getLabelDevice());
        labelMietdauer.setText(language.getLabelRental());
        labelStaffel.setText(language.getLabelRelay());
        labelPreis.setText(language.getLabelPrice());
        labelHaftb.setText(language.getLabelHaftb());
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
                    //getActivity().finish();
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
