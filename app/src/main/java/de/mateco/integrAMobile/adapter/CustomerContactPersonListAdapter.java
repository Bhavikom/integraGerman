package de.mateco.integrAMobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.ContactPersonModel;

public class CustomerContactPersonListAdapter extends BaseAdapter
{
    private int resourceId;
    private ArrayList<ContactPersonModel> listOfContactPerson;
    private Context context;

    public int selectedIndex = -1;

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public CustomerContactPersonListAdapter(Context context, ArrayList<ContactPersonModel>
            listOfContactPerson, int resourceId)
    {
        this.context = context;
        this.listOfContactPerson = listOfContactPerson;
        this.resourceId = resourceId;
    }

    @Override
    public int getCount() {
        return listOfContactPerson.size();
    }

    @Override
    public Object getItem(int position) {
        return listOfContactPerson.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(resourceId, null);

        TextView labelListItemCustomerContactPersonName = (TextView)
                convertView.findViewById(R.id.labelListItemCustomerContactPersonName);

        String name = listOfContactPerson.get(position).getAnrede()
                + " " +  listOfContactPerson.get(position).getNachname();

        if(listOfContactPerson.get(position).getVorname().length() > 0)
        {
            name += ", " + listOfContactPerson.get(position).getVorname();
        }

        labelListItemCustomerContactPersonName.setText(name);
        //imgvBackground.setImageResource(R.drawable.english);
        if(selectedIndex == position)
        {
            labelListItemCustomerContactPersonName.setTextColor(context.getResources().getColor(R.color.white));
            convertView.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        else
        {
            labelListItemCustomerContactPersonName.setTextColor(context.getResources().getColor(R.color.black));
            convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        return convertView;
    }
}
