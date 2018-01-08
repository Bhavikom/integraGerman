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
import de.mateco.integrAMobile.model.DailyAgendaModel;

public class DailyAgendaListAdapter extends BaseAdapter
{
    private int resourceId;
    private ArrayList<DailyAgendaModel> listOfDailyAgenda;
    private Context context;
    public int selectedIndex=-1;

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public DailyAgendaListAdapter(Context context, ArrayList<DailyAgendaModel> listOfDailyAgenda, int resourceId)
    {
        this.context = context;
        this.listOfDailyAgenda = listOfDailyAgenda;
        this.resourceId = resourceId;
    }

    @Override
    public int getCount() {
        return listOfDailyAgenda.size();
    }

    @Override
    public Object getItem(int position) {
        return listOfDailyAgenda.get(position);
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

        TextView labelListItemCustomerActivityDate =
                (TextView)convertView.findViewById(R.id.labelListItemCustomerActivityDate);

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try
        {
            labelListItemCustomerActivityDate.setText
                    (DataHelper.formatDate(format.parse(listOfDailyAgenda.get(position).getStartzeit())));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        TextView labelListItemCustomerActivityType =
                (TextView)convertView.findViewById(R.id.labelListItemCustomerActivityType);
        labelListItemCustomerActivityType.setText(listOfDailyAgenda.get(position).getAktivitaetstytp());

        CheckBox checkBoxCustomerActivityRealized =
                (CheckBox)convertView.findViewById(R.id.checkBoxCustomerActivityRealized);
        if(listOfDailyAgenda.get(position).getRealisiert().equals("True"))
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
