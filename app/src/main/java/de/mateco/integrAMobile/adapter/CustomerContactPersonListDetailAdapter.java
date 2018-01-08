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

public class CustomerContactPersonListDetailAdapter extends BaseAdapter
{
    private int resourceId;
    private ArrayList<ContactPersonModel> listOfContactPerson;
    private Context context;

    public int selectedIndex = -1;

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public CustomerContactPersonListDetailAdapter
            (Context context, ArrayList<ContactPersonModel> listOfContactPerson, int resourceId)
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

        TextView labelListItemCustomerContactPersonSalutation =
                (TextView)convertView.findViewById(R.id.labelListItemCustomerContactPersonSalutation);

        labelListItemCustomerContactPersonSalutation.setText(listOfContactPerson.get(position).getAnrede());

        TextView labelListItemCustomerContactPersonFirstName =
                (TextView)convertView.findViewById(R.id.labelListItemCustomerContactPersonFirstName);
        labelListItemCustomerContactPersonFirstName.setText(listOfContactPerson.get(position).getNachname());

        TextView labelListItemCustomerContactPersonFunction =
                (TextView)convertView.findViewById(R.id.labelListItemCustomerContactPersonFunction);
        labelListItemCustomerContactPersonFunction.setText(listOfContactPerson.get(position).getFunktion());
        if(selectedIndex == position)
        {
            labelListItemCustomerContactPersonSalutation.
                    setTextColor(context.getResources().getColor(R.color.white));
            labelListItemCustomerContactPersonFirstName.
                    setTextColor(context.getResources().getColor(R.color.white));
            labelListItemCustomerContactPersonFunction.
                    setTextColor(context.getResources().getColor(R.color.white));
            convertView.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        else
        {
            labelListItemCustomerContactPersonFunction.
                    setTextColor(context.getResources().getColor(R.color.black));
            labelListItemCustomerContactPersonFirstName.
                    setTextColor(context.getResources().getColor(R.color.black));
            labelListItemCustomerContactPersonFunction.
                    setTextColor(context.getResources().getColor(R.color.black));
            convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        return convertView;
    }
}
