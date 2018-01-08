package de.mateco.integrAMobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.CustomerActivityModel;

public class CustomerActivityListAdapter extends BaseAdapter
{
    private int resourceId;
    private ArrayList<CustomerActivityModel> listOfCustomerActivity;
    private Context context;
    public int selectedIndex=-1;

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public CustomerActivityListAdapter(Context context, ArrayList<CustomerActivityModel>
            listOfCustomerActivity, int resourceId)
    {
        this.context = context;
        this.listOfCustomerActivity = listOfCustomerActivity;
        this.resourceId = resourceId;
    }

    @Override
    public int getCount() {
        return listOfCustomerActivity.size();
    }

    @Override
    public Object getItem(int position) {
        return listOfCustomerActivity.get(position);
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

        TextView labelListItemCustomerActivityDate = (TextView)convertView.findViewById
                (R.id.labelListItemCustomerActivityDate);

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try
        {
            labelListItemCustomerActivityDate.setText(DataHelper.formatDate
                    (format.parse(listOfCustomerActivity.get(position).getStartdatum())));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        TextView labelListItemCustomerActivityType = (TextView)convertView.findViewById(R.id.labelListItemCustomerActivityType);
        labelListItemCustomerActivityType.setText(listOfCustomerActivity.get(position).getAktivitatstyp());

        CheckBox checkBoxCustomerActivityRealized = (CheckBox)convertView.findViewById(R.id.checkBoxCustomerActivityRealized);
        if(listOfCustomerActivity.get(position).getRealisiert().equals("True"))
        {
            checkBoxCustomerActivityRealized.setChecked(true);
        }
        if(selectedIndex == position)
        {
            labelListItemCustomerActivityDate.setTextColor(context.getResources().getColor(R.color.white));
            labelListItemCustomerActivityType.setTextColor(context.getResources().getColor(R.color.white));
            convertView.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        else
        {
            labelListItemCustomerActivityDate.setTextColor(context.getResources().getColor(R.color.black));
            labelListItemCustomerActivityType.setTextColor(context.getResources().getColor(R.color.black));
            convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        return convertView;
    }
}
