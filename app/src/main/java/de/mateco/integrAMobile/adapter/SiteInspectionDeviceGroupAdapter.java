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
import de.mateco.integrAMobile.model_logonsquare.PriceDeviceGroupListItem;

public class SiteInspectionDeviceGroupAdapter extends BaseAdapter {
    private ArrayList<PriceDeviceGroupListItem> listDevice;
    private Context context;
    private Language language;

    public SiteInspectionDeviceGroupAdapter(Activity context,
           ArrayList<PriceDeviceGroupListItem > listDevice,Language language) {
        this.context = context;
        this.listDevice = listDevice;
        this.language = language;
    }

    @Override
    public int getCount() {
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
        //return super.getDropDownView(position, convertView, parent);
        View v = getView(position, convertView, parent);
        return v;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.site_inspection_spinner_item_row, null);
        TextView txtDeviceGroup = (TextView)convertView.findViewById(R.id.textSiteInspectionSpinner);
        txtDeviceGroup.setText(listDevice.get(position).getBezeichnung());

        return convertView;
    }
}
