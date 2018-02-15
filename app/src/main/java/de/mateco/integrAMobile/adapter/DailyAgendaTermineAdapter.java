package de.mateco.integrAMobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.DailyAgendaModel;
import de.mateco.integrAMobile.model_logonsquare.TermineItem;

public class DailyAgendaTermineAdapter extends BaseAdapter
{
    private int resourceId;
    private List<TermineItem> listOfDailyAgenda;
    private Context context;
    public int selectedIndex=-1;

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public DailyAgendaTermineAdapter(Context context, List<TermineItem> listOfDailyAgenda, int resourceId)
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

        TextView labelListItemDailyAgendaCustomerName =
                (TextView)convertView.findViewById(R.id.labelListItemDailyAgendaCustomerName);

        //SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        labelListItemDailyAgendaCustomerName.setText
                (listOfDailyAgenda.get(position).getName1());

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
            labelListItemDailyAgendaCustomerName.setTextColor(context.getResources().getColor(R.color.white));
            labelListItemCustomerActivityType.setTextColor(context.getResources().getColor(R.color.white));
            convertView.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        else
        {
            labelListItemDailyAgendaCustomerName.setTextColor(context.getResources().getColor(R.color.black));
            labelListItemCustomerActivityType.setTextColor(context.getResources().getColor(R.color.black));
            convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        return convertView;
    }
}
