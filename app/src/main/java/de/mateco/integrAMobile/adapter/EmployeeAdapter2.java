package de.mateco.integrAMobile.adapter;

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
import de.mateco.integrAMobile.model_logonsquare.CustomerActivityEmployeeListItem;

public class EmployeeAdapter2 extends BaseAdapter
{
    private int resourceId;
    private List<CustomerActivityEmployeeListItem> listOfEmployee;
    private Context context;
    private Language language;

    public int selectedIndex = -1;

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public EmployeeAdapter2(Context context, List<CustomerActivityEmployeeListItem> listOfEmployee,
                            int resourceId,Language language)
    {
        this.context = context;
        this.listOfEmployee = listOfEmployee;
        this.resourceId = resourceId;
        this.language = language;
    }


    @Override
    public int getCount() {
        return listOfEmployee.size();
    }

    @Override
    public Object getItem(int position) {
        return listOfEmployee.get(position);
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
        if(position == 0)
        {
            convertView = mInflater.inflate(R.layout.list_item_spinner_blank, null);
            TextView labelSpinnerDefaultBlank = (TextView)convertView.findViewById(R.id.labelSpinnerDefaultBlank);
            labelSpinnerDefaultBlank.setText(language.getLabelSelect()+"");
        }
        else
        {
            convertView = mInflater.inflate(resourceId, null);

            TextView labelListItemEmployeeName =
                    (TextView)convertView.findViewById(R.id.labelListItemEmployeeName);
            labelListItemEmployeeName.setText
                    (listOfEmployee.get(position).getNachname() + ", " +listOfEmployee.get(position).getVorname());
            //imgvBackground.setImageResource(R.drawable.english);
            if(selectedIndex == position)
            {
                labelListItemEmployeeName.setTextColor(context.getResources().getColor(R.color.white));
                convertView.setBackgroundColor(context.getResources().getColor(R.color.red));
            }
            else
            {
                labelListItemEmployeeName.setTextColor(context.getResources().getColor(R.color.black));
                convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
            }
        }



        return convertView;
    }
}
