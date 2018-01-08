package de.mateco.integrAMobile.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.ContactPersonModel;
import de.mateco.integrAMobile.model.Language;

public class CustomerContactPersonSpinnerAdapter extends BaseAdapter
{
    private int resourceId;
    private ArrayList<ContactPersonModel> listOfContactPerson;
    private Context context;
    private Language language;

    public CustomerContactPersonSpinnerAdapter(Context context, ArrayList<ContactPersonModel> listOfContactPerson,
                                               int resourceId, Language language)
    {
        this.context = context;
        this.listOfContactPerson = listOfContactPerson;
        this.resourceId = resourceId;
        this.language = language;
    }

    @Override
    public int getCount() {
        return listOfContactPerson.size() + 1;
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
            convertView = mInflater.inflate(resourceId, null);

            TextView labelListItemCustomerContactPersonName =
                    (TextView)convertView.findViewById(R.id.labelListItemCustomerContactPersonName);

            String name = listOfContactPerson.get(position - 1).getAnrede() + " " +
                    listOfContactPerson.get(position - 1).getNachname();

            if(listOfContactPerson.get(position - 1).getVorname().length() > 0)
            {
                name += ", " + listOfContactPerson.get(position - 1).getVorname();
            }
            Log.e(" in adpater class "," name value before settext : "+name);
            labelListItemCustomerContactPersonName.setText(name);
        }
        //imgvBackground.setImageResource(R.drawable.english);
        return convertView;
    }
}