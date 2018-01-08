package de.mateco.integrAMobile.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.CustomerModel;
import de.mateco.integrAMobile.model.Language;

public class LoadedCustomerFragment extends BaseFragment
{
    private MatecoPriceApplication matecoPriceApplication;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void initializeFragment(View rootView)
    {
        Log.e("initializeing ", "loaded customer fragment");
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        //initializeComponents(rootView);
        super.initializeFragment(rootView);

    }

    @Override
    public void initializeComponents(View rootView)
    {
        Log.e("initializeing ", "loaded customer components");
        if(matecoPriceApplication.isCustomerLoaded(DataHelper.isCustomerLoaded, false))
        {
            ViewStub stub = (ViewStub) rootView.findViewById(R.id.stub);
            View inflated = stub.inflate();
            loadLanguage(rootView);
        }
        //super.initializeComponents(rootView);
    }

    private void loadLanguage(View rootView)
    {
        TextView labelLoadedCustomerNo, labelNameLoadedCustomerBonusIndex, labelLoadedCustomerBonusIndex,
                labelNameLoadedCustomerTotalOP, labelLoadedCustomerTotalOP, labelNameLoadedCustomerSalesRunning,
                labelLoadedCustomerSalesRunning,labelNameLoadedCustomerSalesPreviousYear,
                labelLoadedCustomerSalesPreviousYear,labelNameLoadedCustomerSales12Months,
                labelLoadedCustomerSales12Months,labelNameLoadedCustomerKASales12Months,
                labelLoadedCustomerKASales12Months,labelNameLoadedCustomerKASalesPreviousYear,
                labelLoadedCustomerKASalesPreviousYear,labelNameLoadedCustomerKASalesRunning,
                labelLoadedCustomerKASalesRunning,labelNameLoadedCustomerBonusDate,
                labelLoadedCustomerBonusDate,labelNameLoadedCustomerKanr,
                labelLoadedCustomerKanr,labelLoadedCustomerName1;
        //labelNameLoadedCustomerNo, labelNameLoadedCustomerName1, labelNameLoadedCustomerKontakt,
        // labelLoadedCustomerKontakt,
        //labelNameLoadedCustomerSalesCurrentYear, labelLoadedCustomerSalesCurrentYear
        Language language = matecoPriceApplication.getLanguage();
        CustomerModel customer = matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, "");
        //labelNameLoadedCustomerNo = (TextView)rootView.findViewById(R.id.labelNameLoadedCustomerNo);
        labelLoadedCustomerNo = (TextView)rootView.findViewById(R.id.labelLoadedCustomerNo);
        labelNameLoadedCustomerBonusIndex =
                (TextView)rootView.findViewById(R.id.labelNameLoadedCustomerBonusIndex);
        labelLoadedCustomerBonusIndex = (TextView)rootView.findViewById(R.id.labelLoadedCustomerBonusIndex);
        labelNameLoadedCustomerTotalOP = (TextView)rootView.findViewById(R.id.labelNameLoadedCustomerTotalOP);
        labelLoadedCustomerTotalOP = (TextView)rootView.findViewById(R.id.labelLoadedCustomerTotalOP);
        labelNameLoadedCustomerSalesRunning =
                (TextView)rootView.findViewById(R.id.labelNameLoadedCustomerSalesRunning);
        labelLoadedCustomerSalesRunning = (TextView)rootView.findViewById(R.id.labelLoadedCustomerSalesRunning);
        labelNameLoadedCustomerSalesPreviousYear =
                (TextView)rootView.findViewById(R.id.labelNameLoadedCustomerSalesPreviousYear);
        labelLoadedCustomerSalesPreviousYear =
                (TextView)rootView.findViewById(R.id.labelLoadedCustomerSalesPreviousYear);
        //labelNameLoadedCustomerSalesCurrentYear =
        // (TextView)rootView.findViewById(R.id.labelNameLoadedCustomerSalesCurrentYear);
        //labelLoadedCustomerSalesCurrentYear =
        // (TextView)rootView.findViewById(R.id.labelLoadedCustomerSalesCurrentYear);
        labelNameLoadedCustomerSales12Months =
                (TextView)rootView.findViewById(R.id.labelNameLoadedCustomerSales12Months);
        labelLoadedCustomerSales12Months =
                (TextView)rootView.findViewById(R.id.labelLoadedCustomerSales12Months);
        labelNameLoadedCustomerKASales12Months =
                (TextView)rootView.findViewById(R.id.labelNameLoadedCustomerKASales12Months);
        labelLoadedCustomerKASales12Months =
                (TextView)rootView.findViewById(R.id.labelLoadedCustomerKASales12Months);
        labelNameLoadedCustomerKASalesPreviousYear =
                (TextView)rootView.findViewById(R.id.labelNameLoadedCustomerKASalesPreviousYear);
        labelLoadedCustomerKASalesPreviousYear =
                (TextView)rootView.findViewById(R.id.labelLoadedCustomerKASalesPreviousYear);
        labelNameLoadedCustomerKASalesRunning =
                (TextView)rootView.findViewById(R.id.labelNameLoadedCustomerKASalesRunning);
        labelLoadedCustomerKASalesRunning =
                (TextView)rootView.findViewById(R.id.labelLoadedCustomerKASalesRunning);
        labelNameLoadedCustomerBonusDate =
                (TextView)rootView.findViewById(R.id.labelNameLoadedCustomerBonusDate);
        labelLoadedCustomerBonusDate =
                (TextView)rootView.findViewById(R.id.labelLoadedCustomerBonusDate);
        //labelNameLoadedCustomerKontakt = (TextView)rootView.findViewById(R.id.labelNameLoadedCustomerKontakt);
        //labelLoadedCustomerKontakt = (TextView)rootView.findViewById(R.id.labelLoadedCustomerKontakt);
        labelNameLoadedCustomerKanr = (TextView)rootView.findViewById(R.id.labelNameLoadedCustomerKanr);
        labelLoadedCustomerKanr = (TextView)rootView.findViewById(R.id.labelLoadedCustomerKanr);
        //labelNameLoadedCustomerName1 = (TextView)rootView.findViewById(R.id.labelNameLoadedCustomerName1);
        labelLoadedCustomerName1 = (TextView)rootView.findViewById(R.id.labelLoadedCustomerName1);

//        labelNameLoadedCustomerKontakt.setText(language.getLabelContact());
//        labelLoadedCustomerKontakt.setText(customer.getKontakt());
//
//        labelNameLoadedCustomerNo.setText(language.getLabelCustomerNo());
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy");
        if(customer != null){
            labelLoadedCustomerNo.setText(customer.getKundenNr());
            labelLoadedCustomerName1.setText(customer.getName1());
            labelLoadedCustomerKanr.setText(customer.getKaNr());
            labelLoadedCustomerBonusIndex.setText(DataHelper.getGermanCurrencyFormat(customer.getBoni_Index()));
            if(customer.getBoni_Datum()!=null && !customer.getBoni_Datum().equals(""))
            {
                labelLoadedCustomerBonusDate.setText
                        (DataHelper.getDateFormatFromString(customer.getBoni_Datum(), outputFormat, inputFormat));
            }
            if(customer.getGesamt_OP()!=null && !customer.getGesamt_OP().equals(""))
                labelLoadedCustomerTotalOP.setText(DataHelper.round(Math.round(Float.parseFloat
                        (customer.getGesamt_OP()))+""));
            if(customer.getUmsatz_LFD()!=null && !customer.getUmsatz_LFD().equals(""))
                labelLoadedCustomerSalesRunning.setText
                        (DataHelper.round(Math.round(Float.parseFloat(customer.getUmsatz_LFD()))+""));
            if(customer.getUmsatz_Vorjahr()!=null && !customer.getUmsatz_Vorjahr().equalsIgnoreCase(""))
                labelLoadedCustomerSalesPreviousYear.setText
                        (DataHelper.round(Math.round(Float.parseFloat(customer.getUmsatz_Vorjahr()))+""));
            if(customer.getUmsatz_12_Monate()!=null && !customer.getUmsatz_12_Monate().equals(""))
                labelLoadedCustomerSales12Months.setText
                        (DataHelper.round(Math.round(Float.parseFloat(customer.getUmsatz_12_Monate()))+""));
            if(customer.getKA_Umsatz_LFD()!=null && !customer.getKA_Umsatz_LFD().equals(""))
                labelLoadedCustomerKASalesRunning.setText
                        (DataHelper.round(Math.round(Float.parseFloat(customer.getKA_Umsatz_LFD()))+""));

            labelNameLoadedCustomerKASalesPreviousYear.setText("KA "+language.getLabelSales() + " " +
                    (Calendar.getInstance().get(Calendar.YEAR) - 1));
            if(customer.getKA_Umsatz_Vorjahr()!=null && !customer.getKA_Umsatz_Vorjahr().equals(""))
                labelLoadedCustomerKASalesPreviousYear.setText
                        (DataHelper.round(Math.round(Float.parseFloat(customer.getKA_Umsatz_Vorjahr())) + ""));

            labelNameLoadedCustomerKASales12Months.setText(language.getLabelKASalesLast12Month());
            if(customer.getKA_Umsatz_12_Monate()!=null && !customer.getKA_Umsatz_12_Monate().equals(""))
                labelLoadedCustomerKASales12Months.setText
                        (DataHelper.round(Math.round(Float.parseFloat(customer.getKA_Umsatz_12_Monate())) + ""));
        }
        else
        {
            labelLoadedCustomerNo.setText("");
        }


        //labelNameLoadedCustomerName1.setText(language.getLabelName() + " 1");



        labelNameLoadedCustomerKanr.setText(language.getLabelKanr());


        labelNameLoadedCustomerBonusIndex.setText(language.getLabelBonusIndex());


        labelNameLoadedCustomerBonusDate.setText(language.getLabelSalesBoniDate());


        labelNameLoadedCustomerTotalOP.setText(language.getLabelTotalOutput());


        labelNameLoadedCustomerSalesRunning.setText(language.getLabelSales() + " " +
                (Calendar.getInstance().get(Calendar.YEAR)));


        labelNameLoadedCustomerSalesPreviousYear.setText(language.getLabelSales() + " " +
                (Calendar.getInstance().get(Calendar.YEAR) - 1));


        labelNameLoadedCustomerSales12Months.setText(language.getLabelSales12Month());


        labelNameLoadedCustomerKASalesRunning.setText("KA "+language.getLabelSales() + " " +
                (Calendar.getInstance().get(Calendar.YEAR)));


    }
}
