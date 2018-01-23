package de.mateco.integrAMobile.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.SiteInspectionModel;


public class SiteInspectionResumeAdapter extends BaseAdapter {
    private Context context;
    private  ArrayList<SiteInspectionModel> listOfSiteInspectionModel = new ArrayList<>();

    public SiteInspectionResumeAdapter(Context context,
                                       ArrayList<SiteInspectionModel> listOfSiteInspectionModel) {
        this.context = context;
        this.listOfSiteInspectionModel = listOfSiteInspectionModel;
    }

    @Override
    public int getCount() {
        return listOfSiteInspectionModel.size();
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
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.site_inspection_resume_list_row, null);
        TextView txtDate = (TextView)convertView.findViewById(R.id.labelDate);
        TextView txtRoad = (TextView)convertView.findViewById(R.id.labelRoad);
        TextView txtPlace = (TextView)convertView.findViewById(R.id.labelPlace);
        TextView txtZipCode = (TextView)convertView.findViewById(R.id.labelZipCode);
        TextView txtKunde = (TextView)convertView.findViewById(R.id.labelKunde);
        TextView txtMatchCode = (TextView)convertView.findViewById(R.id.labelMatchCode);
        TextView txtLongitude = (TextView)convertView.findViewById(R.id.labelLatitude);
        TextView txtLatitude = (TextView)convertView.findViewById(R.id.labelLongitude);
        String date = listOfSiteInspectionModel.get(position).getSiteInspectionNewModel().getEinsatzdatum();
        if(date != null){
            if(date.length() >  10 ){
                if(date != null && !date.equals(""))
                {
                    date = date.substring(0,10);
                    date = DataHelper.formatDateToGerman(date);
                }
            }
            if(date.equalsIgnoreCase("null") || date.equalsIgnoreCase("") || date == null)
            {
                txtDate.setText("");
            }
            else {
                txtDate.setText(date);
            }
        }


        txtRoad.setText(listOfSiteInspectionModel.get(position).getSiteInspectionNewModel().getEinsatzstrasse());
        txtPlace.setText(listOfSiteInspectionModel.get(position).getSiteInspectionNewModel().getEinsatzort());
        txtZipCode.setText(listOfSiteInspectionModel.get(position).getSiteInspectionNewModel().getEinsatzPLZ());
        txtKunde.setText(listOfSiteInspectionModel.get(position).getSiteInspectionNewModel().getKundeName());
        txtMatchCode.setText(listOfSiteInspectionModel.get(position).getSiteInspectionNewModel().getMatchCode());
        txtLongitude.setText(listOfSiteInspectionModel.get(position).getSiteInspectionNewModel().getGeoLaenge());
        txtLatitude.setText(listOfSiteInspectionModel.get(position).getSiteInspectionNewModel().getGeoBreite());

        if(listOfSiteInspectionModel.get(position).getFlag()==3)
        {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.dark_grey));
        }

        return convertView;
    }
}
