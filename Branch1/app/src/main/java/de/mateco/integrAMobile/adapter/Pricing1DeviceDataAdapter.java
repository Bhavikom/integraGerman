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
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.Pricing1DeviceData;

public class Pricing1DeviceDataAdapter extends BaseAdapter {

    private ArrayList<Pricing1DeviceData> listDevice;
    private Context context;
    private LayoutInflater mInflater;
    private boolean isSelectionEnable;
    private Language language;

    public Pricing1DeviceDataAdapter(Activity context, ArrayList<Pricing1DeviceData> listDevice,
                                     boolean isSelectionEnable, Language language) {
        this.context = context;
        this.listDevice = listDevice;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.isSelectionEnable = isSelectionEnable;
        this.language = language;
    }

    @Override
    public int getCount() {
        if(isSelectionEnable)
            return listDevice.size() + 1;
        else
            return listDevice.size();
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

        convertView = mInflater.inflate(R.layout.fragment_pricing1_device_data_row, null);

        TextView txtPricing1BranchData = (TextView)convertView.findViewById(R.id.txtPricing1DeviceDataRow);

        if(isSelectionEnable)
        {
            if(position == 0)
            {
                txtPricing1BranchData.setText(language.getLabelSelect()+"");
            }
            else
            {
                txtPricing1BranchData.setText(listDevice.get(position - 1).getDesignation());
            }
        }
        else
        {
            txtPricing1BranchData.setText(listDevice.get(position).getDesignation());
        }
        return convertView;
    }
}
