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
import de.mateco.integrAMobile.adapter.CustomerCompletedOrderAdapter;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.model.CustomerCompletedOrderModel;
import de.mateco.integrAMobile.model.Language;

public class CustomerOffersCompletedOrdersFragment extends BaseFragment
{
    private Language language;
    private View rootView;
    private MatecoPriceApplication matecoPriceApplication;
    private ArrayList<CustomerCompletedOrderModel> listOfCompletedOrder;
    private CustomerCompletedOrderAdapter customerCompletedOrderAdapter;
    private ListView listViewCustomerCompletedOrderOrder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_customer_completed_order, container, false);
        super.initializeFragment(rootView);

        return rootView;
    }

    @Override
    public void initializeComponents(View rootView)
    {
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();

        listViewCustomerCompletedOrderOrder = (ListView)rootView.findViewById(R.id.listViewCustomerCompletedOrderOrder);
        listOfCompletedOrder = new ArrayList<>();

        listOfCompletedOrder = matecoPriceApplication.getLoadedCustomerCompletedOrders(DataHelper.LoadedCustomerCompletedOrders, new ArrayList<CustomerCompletedOrderModel>().toString());

        //Log.e("size", listOfCompletedOrder.size() + "");

        customerCompletedOrderAdapter = new CustomerCompletedOrderAdapter(getActivity(), listOfCompletedOrder);
        listViewCustomerCompletedOrderOrder.setAdapter(customerCompletedOrderAdapter);

        getActivity().invalidateOptionsMenu();
//        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelCompletedOffers());
        setHasOptionsMenu(true);
        setUpLanguage();
        super.initializeComponents(rootView);
    }

    @Override
    public void bindEvents(View rootView)
    {
        listViewCustomerCompletedOrderOrder.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //view.setSelected(true);
                customerCompletedOrderAdapter.setSelectedIndex(position);
            }
        });
        super.bindEvents(rootView);
    }

    private void setUpLanguage()
    {
//        TextView labelEinheit, labelEinsatzPLZ, labelEinsatzort, labelGel_Gerat, labelGeraetegruppe, labelHohengruppe,
//                labelKaNr, labelKaP, labelKundenNr, labelLP, labelMandant, labelMietbeginn, labelMietdauer, labelMietende, labelNL,
//                labelRegion, labelSelbstbehalt, labelTP, labelVers_LP, labelVers_TP, labelWunschgerat;
        TextView labelAuftragNr, labelNL, labelKaNr, labelHohengruppe, labelWunschgerat, labelGel_Gerat, labelMietbeginn, labelMietdauer,
                labelEinheit, labelLP, labelKaP, labelTP, labelSelbstbehalt, labelVers_LP, labelVers_TP,
                labelEinsatzPLZ, labelEinsatzort;
        labelAuftragNr = (TextView)rootView.findViewById(R.id.labelAuftragNr);
        //labelItemDatumAb = (TextView)rootView.findViewById(R.id.labelItemDatumAb);
        labelEinheit = (TextView)rootView.findViewById(R.id.labelEinheit);
        labelEinsatzPLZ = (TextView)rootView.findViewById(R.id.labelEinsatzPLZ);
        labelEinsatzort = (TextView)rootView.findViewById(R.id.labelEinsatzort);
        labelGel_Gerat = (TextView)rootView.findViewById(R.id.labelGel_Gerat);
        //labelGeraetegruppe = (TextView)rootView.findViewById(R.id.labelGeraetegruppe);
        labelHohengruppe = (TextView)rootView.findViewById(R.id.labelHohengruppe);
        labelKaNr = (TextView)rootView.findViewById(R.id.labelKaNr);
        labelKaP = (TextView)rootView.findViewById(R.id.labelKaP);
        //labelKundenNr = (TextView)rootView.findViewById(R.id.labelKundenNr);
        labelLP = (TextView)rootView.findViewById(R.id.labelLP);
        //labelMandant = (TextView)rootView.findViewById(R.id.labelMandant);
        labelMietbeginn = (TextView)rootView.findViewById(R.id.labelMietbeginn);
        labelMietdauer = (TextView)rootView.findViewById(R.id.labelMietdauer);
//        labelMietende = (TextView)rootView.findViewById(R.id.labelMietende);
        labelNL = (TextView)rootView.findViewById(R.id.labelNL);
//        labelRegion = (TextView)rootView.findViewById(R.id.labelRegion);
        labelSelbstbehalt = (TextView)rootView.findViewById(R.id.labelSelbstbehalt);
        labelTP = (TextView)rootView.findViewById(R.id.labelTP);
        labelVers_LP = (TextView)rootView.findViewById(R.id.labelVers_LP);
        labelVers_TP = (TextView)rootView.findViewById(R.id.labelVers_TP);
        labelWunschgerat = (TextView)rootView.findViewById(R.id.labelWunschgerat);

        labelAuftragNr.setText(language.getLabelContractNo());
        //labelItemDatumAb.setText(language.getLabelDate());
        labelEinheit.setText(language.getLabelUnit());
        labelEinsatzPLZ.setText(language.getLabelUseZipCode());
        labelEinsatzort.setText(language.getLabelOfUse());
        labelGel_Gerat.setText(language.getLabelGel_Gerat());
        //labelGeraetegruppe.setText(language.getLabelDeviceGroup());
        labelHohengruppe.setText(language.getLabelLevelGroup());
        labelKaNr.setText(language.getLabelKanr());
        labelKaP.setText(language.getLabelKaP());
        //labelKundenNr.setText(language.getLabelCustomerNo());
        labelLP.setText(language.getLabelLP());
        //labelMandant.setText(language.getLabelMandant());
        labelMietbeginn.setText(language.getLabelArrival());
        labelMietdauer.setText(language.getLabelRental());
        //labelMietende.setText(language.getLabelMietende());
        labelNL.setText(language.getLabelNL());
        //labelRegion.setText(language.getLabelRegion());
        labelSelbstbehalt.setText(language.getLabelSelbstbehalt());
        labelTP.setText(language.getLabelTP());
        labelVers_LP.setText(language.getLabelVers_LP());
        labelVers_TP.setText(language.getLabelVers_TP());
        labelWunschgerat.setText(language.getLabelWunschgerat());
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
