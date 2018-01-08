package de.mateco.integrAMobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.Pricing1LevelGroupData;

public class Pricing1LevelGroupDataAdapter extends BaseAdapter {

    private List<Pricing1LevelGroupData> listLevelGroup;
    private Context context;
    public int selectedIndex=-1;

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public Pricing1LevelGroupDataAdapter(Activity context, List<Pricing1LevelGroupData> listLevelGroup) {
        this.context = context;
        this.listLevelGroup = listLevelGroup;
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

    /*@Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {

        View v = getView(position, convertView, parent);

        return v;
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.fragment_pricing1_levelgroup_row, null);

        TextView txtPricing1LevelGroupData =
                (TextView)convertView.findViewById(R.id.txtPricing1LevelGroupData_row);

        txtPricing1LevelGroupData.setText
                (listLevelGroup.get(position).getHeightGroup()+" - "+listLevelGroup.get(position).getDesignation());
        if(selectedIndex == position)
        {
            txtPricing1LevelGroupData.setTextColor(context.getResources().getColor(R.color.white));
            convertView.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        else
        {
            txtPricing1LevelGroupData.setTextColor(context.getResources().getColor(R.color.black));
            convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        return convertView;
    }


}
