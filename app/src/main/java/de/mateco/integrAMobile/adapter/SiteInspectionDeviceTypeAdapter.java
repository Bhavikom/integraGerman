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
import de.mateco.integrAMobile.model_logonsquare.BVODeviceTypeListItem;

public class SiteInspectionDeviceTypeAdapter extends BaseAdapter {
    private List<BVODeviceTypeListItem> listDeviceType;
    private Context context;
    private Language language;
    public int selectedIndex=-1;

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public SiteInspectionDeviceTypeAdapter(Activity context,
           List<BVODeviceTypeListItem> listDeviceType,Language language) {
        this.context = context;
        this.listDeviceType = listDeviceType;
        this.language = language;
    }

    @Override
    public int getCount() {
        return listDeviceType.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return listDeviceType.get(position - 1);
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
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(position == 0)
        {
            convertView = mInflater.inflate(R.layout.list_item_spinner_blank, null);
            TextView labelSpinnerDefaultBlank = (TextView)convertView.findViewById(R.id.labelSpinnerDefaultBlank);
            labelSpinnerDefaultBlank.setText(language.getLabelSelect()+"");
        }
        else
        {
            convertView = mInflater.inflate(R.layout.site_inspection_spinner_item_row, null);
            TextView txtHeightScale = (TextView)convertView.findViewById(R.id.textSiteInspectionSpinner);
            txtHeightScale.setText(listDeviceType.get(position - 1).getBezeichnung());
        }
        if(selectedIndex == position)
        {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        else
        {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        return convertView;
    }
}
