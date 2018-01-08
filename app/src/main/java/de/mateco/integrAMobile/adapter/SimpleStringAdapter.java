package de.mateco.integrAMobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.mateco.integrAMobile.R;

public class SimpleStringAdapter extends BaseAdapter
{
    private ArrayList<String> listOfString;
    private Context context;
    private LayoutInflater mInflater;

    public SimpleStringAdapter(Context context, ArrayList<String> listOfString)
    {
        this.context = context;
        this.listOfString = listOfString;
        this.mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return listOfString.size();
    }

    @Override
    public String getItem(int position) {
        return listOfString.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.list_item_simple_string, null);

        TextView labelSimpleString = (TextView)convertView.findViewById(R.id.labelSimpleString);
        labelSimpleString.setText(listOfString.get(position).toString());
        return convertView;
    }
}
