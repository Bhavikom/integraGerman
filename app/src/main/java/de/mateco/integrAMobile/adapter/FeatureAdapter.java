package de.mateco.integrAMobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.FeatureModel;

public class FeatureAdapter extends BaseAdapter
{
    private int resourceId;
    private ArrayList<FeatureModel> listOfFeature;
    private Context context;

    public int selectedIndex = -1;

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public FeatureAdapter(Context context, ArrayList<FeatureModel> listOfFeature, int resourceId)
    {
        this.context = context;
        this.listOfFeature = listOfFeature;
        this.resourceId = resourceId;
    }

    @Override
    public int getCount() {
        return listOfFeature.size();
    }

    @Override
    public Object getItem(int position) {
        return listOfFeature.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
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
        convertView = mInflater.inflate(resourceId, null);

        TextView labelListItemCustomerContactPersonFeature =
                (TextView)convertView.findViewById(R.id.labelListItemCustomerContactPersonFeature);
        labelListItemCustomerContactPersonFeature.setText(listOfFeature.get(position).getBezeichnung());
        //imgvBackground.setImageResource(R.drawable.english);

        if(selectedIndex == position)
        {
            labelListItemCustomerContactPersonFeature.setTextColor(context.getResources().getColor(R.color.white));
            convertView.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        else
        {
            labelListItemCustomerContactPersonFeature.setTextColor(context.getResources().getColor(R.color.black));
            convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        return convertView;
    }
}
