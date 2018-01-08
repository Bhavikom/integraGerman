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
import de.mateco.integrAMobile.adapter.LostSaleAdapter;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.model.CustomerLostSaleDataModel;
import de.mateco.integrAMobile.model.Language;

public class CustomerOffersLostSaleFragment extends BaseFragment
{
    private Language language;
    private View rootView;
    private MatecoPriceApplication matecoPriceApplication;
    private ListView listViewCustomerLostSale;
    private LostSaleAdapter lostSaleAdapter;
    private ArrayList<CustomerLostSaleDataModel> listOfLostSale;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_customer_lost_sale, container, false);
        super.initializeFragment(rootView);
        return rootView;
    }

    @Override
    public void initializeComponents(View rootView)
    {
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        listViewCustomerLostSale = (ListView)rootView.findViewById(R.id.listViewCustomerLostSale);
        listOfLostSale = new ArrayList<>();

        listOfLostSale = matecoPriceApplication.getLoadedCustomerLostSale(DataHelper.LoadedCustomerLostSale, new ArrayList<CustomerLostSaleDataModel>().toString());
        lostSaleAdapter = new LostSaleAdapter(getActivity(), listOfLostSale);
        listViewCustomerLostSale.setAdapter(lostSaleAdapter);
        getActivity().invalidateOptionsMenu();
//        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelLostSale());
//        Log.e("here at", language.getLabelLostSale());
        setHasOptionsMenu(true);
        setUpLanguage();
        super.initializeComponents(rootView);
    }

    private void setUpLanguage()
    {
        TextView labelAbsagegrund, labelErstellt_am, labelGeraetetyp, labelHohengruppe,
                labelMietdauer, labelMietpreis, labelNL, labelVersicherung;
//, labelKontakt, labelGeraetegruppe, labelMandant, labelRegion
        labelAbsagegrund = (TextView)rootView.findViewById(R.id.labelAbsagegrund);
        labelErstellt_am = (TextView)rootView.findViewById(R.id.labelErstellt_am);
        //labelGeraetegruppe = (TextView)rootView.findViewById(R.id.labelGeraetegruppe);
        labelGeraetetyp = (TextView)rootView.findViewById(R.id.labelGeraetetyp);
        labelHohengruppe = (TextView)rootView.findViewById(R.id.labelHohengruppe);
        //labelKontakt = (TextView)rootView.findViewById(R.id.labelKontakt);
        //labelMandant = (TextView)rootView.findViewById(R.id.labelMandant);
        labelMietdauer = (TextView)rootView.findViewById(R.id.labelMietdauer);
        labelMietpreis = (TextView)rootView.findViewById(R.id.labelMietpreis);
        labelNL = (TextView)rootView.findViewById(R.id.labelNL);
        //labelRegion = (TextView)rootView.findViewById(R.id.labelRegion);
        labelVersicherung = (TextView)rootView.findViewById(R.id.labelVersicherung);

        labelAbsagegrund.setText(language.getLabelReasonForRejection());
        labelErstellt_am.setText(language.getLabelCreatedOn());
        //labelGeraetegruppe.setText(language.getLabelDeviceGroup());
        labelGeraetetyp.setText(language.getLabelDeviceType());
        labelHohengruppe.setText(language.getLabelLevelGroup());

        //labelKontakt.setText(language.getLabelContact());
        //labelMandant.setText(language.getLabelMandant());
        labelMietdauer.setText(language.getLabelRental());
        labelMietpreis.setText(language.getLabelRentalPrice());
        labelNL.setText(language.getLabelNL());
        //labelRegion.setText(language.getLabelRegion());
        labelVersicherung.setText(language.getLabelInsurance());
    }

    @Override
    public void bindEvents(View rootView)
    {
        listViewCustomerLostSale.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //view.setSelected(true);
                lostSaleAdapter.setSelectedIndex(position);


            }
        });
        super.bindEvents(rootView);
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
        switch (item.getItemId())
        {
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
