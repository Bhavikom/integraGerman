package de.mateco.integrAMobile.fragment;

import android.os.Bundle;

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

public class PricingFragment4 extends BaseFragment {
    private MatecoPriceApplication matecoPriceApplication;
    private Language language;
    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_pricing_4, container, false);
//        TextView txtNameFragment = (TextView)rootView.findViewById(R.id.txtNameFragment);
//        String text = getArguments().getString("Name");
//        txtNameFragment.setText(text);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        matecoPriceApplication = (MatecoPriceApplication) getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        setUpLanguage();
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle("Customer Data");
        return rootView;
    }

    private void setUpLanguage() {
        TextView txtCost, txtCost1, txtHaftb, txtHandllingFee, txtTotal, txtTransport1, txtEquipmentRent, txtToll, txtCache, txtGesAmenities, txtSatntag, txtServicePackages, txtBranch, txtHGRP, txtDeviceType, txtManufacturer, txtType, txtMD, txtRentalPrice, txtSB, txtHF;

        txtCost = (TextView) rootView.findViewById(R.id.txtCost);
        txtCost1 = (TextView) rootView.findViewById(R.id.txtCost1);
        txtHaftb = (TextView) rootView.findViewById(R.id.txtHaftb);
        txtHandllingFee = (TextView) rootView.findViewById(R.id.txtHandllingFee);
        txtTotal = (TextView) rootView.findViewById(R.id.txtTotal);
        txtTransport1 = (TextView) rootView.findViewById(R.id.txtTransport1);
        txtEquipmentRent = (TextView) rootView.findViewById(R.id.txtEquipmentRent);
        txtToll = (TextView) rootView.findViewById(R.id.txtToll);
        txtCache = (TextView) rootView.findViewById(R.id.txtCache);
        txtGesAmenities = (TextView) rootView.findViewById(R.id.txtGesAmenities);
        txtSatntag = (TextView) rootView.findViewById(R.id.txtSatntag);
        txtServicePackages = (TextView) rootView.findViewById(R.id.txtServicePackages);
        txtBranch = (TextView) rootView.findViewById(R.id.txtBranch);
        txtHGRP = (TextView) rootView.findViewById(R.id.txtHGRP);
        txtDeviceType = (TextView) rootView.findViewById(R.id.txtDeviceType);
        txtManufacturer = (TextView) rootView.findViewById(R.id.txtManufacturer);
        txtType = (TextView) rootView.findViewById(R.id.txtType);
        txtMD = (TextView) rootView.findViewById(R.id.txtMD);
        txtRentalPrice = (TextView) rootView.findViewById(R.id.txtRentalPrice);
        txtSB = (TextView) rootView.findViewById(R.id.txtSB);
        txtHF = (TextView) rootView.findViewById(R.id.txtHF);

        txtCost.setText(language.getLabelCost());
        txtCost1.setText(language.getLabelCost());
        txtHaftb.setText(language.getLabelHaftb());
        txtHandllingFee.setText(language.getLabelHandlingFee());
        txtTotal.setText(language.getLabelTotal());
        txtTransport1.setText(language.getLabelTransport());
        txtEquipmentRent.setText(language.getLabelEquipmentRent());
        txtToll.setText(language.getLabelToll());
        txtCache.setText(language.getLabelCache());
        txtGesAmenities.setText(language.getLabelGesAmenities());
        txtSatntag.setText(language.getLabelSatntag());
        txtServicePackages.setText(language.getLabelServicePackages());
        txtBranch.setText(language.getLabelBranch());
        txtHGRP.setText(language.getLabelHGRP());
        txtDeviceType.setText(language.getLabelDeviceType());
        txtManufacturer.setText(language.getLabelManufacturer());
        txtType.setText(language.getLabelType());
        txtMD.setText(language.getLabelMD());
        txtRentalPrice.setText(language.getLabelRentalPrice());
        txtSB.setText(language.getLabelSB());
        txtHF.setText(language.getLabelHF());


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_menu, menu);
        //return true;
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
                if (getFragmentManager().getBackStackEntryCount() == 0) {
                    getActivity().finish();
                } else {
                    getFragmentManager().popBackStack();
                }
                return true;
            case R.id.actionForward:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }
}
