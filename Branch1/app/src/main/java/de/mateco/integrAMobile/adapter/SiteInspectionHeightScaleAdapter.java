package de.mateco.integrAMobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.Pricing1LevelGroupData;

public class SiteInspectionHeightScaleAdapter extends BaseAdapter {
    private List<Pricing1LevelGroupData> listLevelGroup;
    private Context context;
    private Language language;

    public SiteInspectionHeightScaleAdapter(Activity context,
           ArrayList<Pricing1LevelGroupData> listLevelGroup,Language language) {
        this.context = context;
        this.listLevelGroup = listLevelGroup;
        this.language = language;
    }

    @Override
    public int getCount() {
        return listLevelGroup.size();
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
        TextView txtHeightScale = (TextView)convertView.findViewById(R.id.textSiteInspectionSpinner);
        txtHeightScale.setText(listLevelGroup.get(position).getHeightGroup() + " - " +
                listLevelGroup.get(position).getDesignation());

        return convertView;
    }
}
