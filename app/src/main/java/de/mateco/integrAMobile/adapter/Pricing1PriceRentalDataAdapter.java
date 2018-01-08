package de.mateco.integrAMobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.Pricing1PriceRentalData;

public class Pricing1PriceRentalDataAdapter extends BaseAdapter {

    private ArrayList<Pricing1PriceRentalData> listPriceRental;
    private Context context;


    public Pricing1PriceRentalDataAdapter(Activity context, ArrayList<Pricing1PriceRentalData> listPriceRental) {
        this.context = context;
        this.listPriceRental = listPriceRental;
    }

    @Override
    public int getCount() {
        return listPriceRental.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {

        View v = getView(position, convertView, parent);

        return v;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.fragment_pricing1_price_rental_data_row, null);

        TextView txtPricing1BranchData = (TextView)convertView.findViewById(R.id.txtPricing1PriceRentalDataRow);

        txtPricing1BranchData.setText(listPriceRental.get(position).getDesignation());

        return convertView;
    }


}
