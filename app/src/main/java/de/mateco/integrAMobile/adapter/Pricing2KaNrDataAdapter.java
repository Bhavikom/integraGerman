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
import de.mateco.integrAMobile.model.Pricing2KaNrData;

public class Pricing2KaNrDataAdapter extends BaseAdapter {

    private ArrayList<Pricing2KaNrData> listKaNr;
    private Context context;


    public Pricing2KaNrDataAdapter(Activity context, ArrayList<Pricing2KaNrData> listKaNr) {
        this.context = context;
        this.listKaNr = listKaNr;
    }

    @Override
    public int getCount() {
        return listKaNr.size();
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
        convertView = mInflater.inflate(R.layout.fragment_pricing2_kanr_data_row, null);

        TextView txtPricing2KaNrIdDataRow = (TextView)convertView.findViewById(R.id.txtPricing2KaNrIdDataRow);
        TextView txtPricing2KaNrNameDataRow = (TextView)convertView.findViewById(R.id.txtPricing2KaNrNameDataRow);

        txtPricing2KaNrIdDataRow.setText(""+listKaNr.get(position).getKaNr());
        txtPricing2KaNrNameDataRow.setText(listKaNr.get(position).getName());

        return convertView;
    }


}
