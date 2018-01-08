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
import de.mateco.integrAMobile.model.Pricing1BranchData;

public class Pricing1BranchDataAdapter extends BaseAdapter {

    private ArrayList<Pricing1BranchData> listBranch;
    private Context context;
    private Language language;

    public Pricing1BranchDataAdapter(Activity context,ArrayList<Pricing1BranchData> listBranch,
                                     Language language) {
        this.context = context;
        this.listBranch = listBranch;
        this.language = language;
    }

    @Override
    public int getCount() {
        return listBranch.size()+1;
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
        if(position == 0)
        {
            convertView = mInflater.inflate(R.layout.list_item_spinner_blank, null);
            TextView labelSpinnerDefaultBlank = (TextView)convertView.findViewById(R.id.labelSpinnerDefaultBlank);
            labelSpinnerDefaultBlank.setText(language.getLabelSelect()+"");
        }
        else
        {
            convertView = mInflater.inflate(R.layout.fragment_pricing1_branch_data_row, null);

            TextView txtPricing1BranchData = (TextView)convertView.findViewById(R.id.txtPricing1BranchDataRow);

            txtPricing1BranchData.setText(listBranch.get(position-1).getDesignation());
        }


        return convertView;
    }


}
