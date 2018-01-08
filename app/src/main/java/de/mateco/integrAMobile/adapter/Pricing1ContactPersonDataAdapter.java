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
import de.mateco.integrAMobile.model.ContactPersonModel;
import de.mateco.integrAMobile.model.Language;

public class Pricing1ContactPersonDataAdapter extends BaseAdapter {

    private ArrayList<ContactPersonModel> listContactPerson;
    private Context context;
    private boolean isSelectionEnable;
    private LayoutInflater mInflater;
    private Language language;

    public Pricing1ContactPersonDataAdapter(Activity context, ArrayList<ContactPersonModel> listContactPerson,
                                            boolean isSelectionEnable, Language language)
    {
        this.context = context;
        this.listContactPerson = listContactPerson;
        this.isSelectionEnable = isSelectionEnable;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.language = language;
    }

    @Override
    public int getCount() {
        if(isSelectionEnable)
            return listContactPerson.size() + 1;
        else
            return listContactPerson.size();
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

        convertView = mInflater.inflate(R.layout.fragment_pricing1_contact_person_data_row, null);

        TextView txtPricing1ContactPersonData =
                (TextView)convertView.findViewById(R.id.txtPricing1ContactPersonDataRow);
        if(isSelectionEnable)
        {
            if(position == 0)
            {
                txtPricing1ContactPersonData.setText(language.getLabelSelect()+"");
            }
            else
            {
                String name = listContactPerson.get(position - 1).getAnrede() + " " +
                        listContactPerson.get(position - 1).getNachname();

                if(listContactPerson.get(position - 1).getVorname().length() > 0)
                {
                    name += ", " + listContactPerson.get(position - 1).getVorname();
                }
                txtPricing1ContactPersonData.setText(name);
            }
        }
        else
        {
            String name = listContactPerson.get(position).getAnrede() + " " +
                    listContactPerson.get(position).getNachname();

            if(listContactPerson.get(position).getVorname().length() > 0)
            {
                name += ", " + listContactPerson.get(position).getVorname();
            }
            txtPricing1ContactPersonData.setText(name);
        }


        return convertView;
    }


}
