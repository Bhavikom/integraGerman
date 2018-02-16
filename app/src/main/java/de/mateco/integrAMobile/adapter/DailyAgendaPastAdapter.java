package de.mateco.integrAMobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model_logonsquare.PastItem;

public class DailyAgendaPastAdapter extends BaseAdapter
{
    private int resourceId;
    private List<PastItem> listOfDailyAgenda;
    private Context context;
    public int selectedIndex=-1;
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public DailyAgendaPastAdapter(Context context, List<PastItem> listOfDailyAgenda, int resourceId)
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
        labelListItemDailyAgendaCustomerName.setText(listOfDailyAgenda.get(position).getName1());

        TextView labelListItemCustomerActivityType =
                (TextView)convertView.findViewById(R.id.labelListItemCustomerActivityType);
        labelListItemCustomerActivityType.setText(listOfDailyAgenda.get(position).getAktivitaetstytp());


        TextView labelListItemCustomerActivityDate =
                (TextView)convertView.findViewById(R.id.labelListItemCustomerActivityDate);
        try
        {
            labelListItemCustomerActivityDate.setText
                    (DataHelper.formatDate(format.parse(listOfDailyAgenda.get(position).getDatum())));
        }
        catch (ParseException ex)
        {
            ex.printStackTrace();
        }

        //CheckBox checkBoxCustomerActivityRealized =
        // (CheckBox)convertView.findViewById(R.id.checkBoxCustomerActivityRealized);
//        if(listOfDailyAgenda.get(position).getRealisiert().equals("True"))
//        {
//            checkBoxCustomerActivityRealized.setChecked(true);
//        }
        if(selectedIndex == position)
        {
            labelListItemDailyAgendaCustomerName.setTextColor(context.getResources().getColor(R.color.white));
            labelListItemCustomerActivityType.setTextColor(context.getResources().getColor(R.color.white));
            labelListItemCustomerActivityDate.setTextColor(context.getResources().getColor(R.color.white));
            convertView.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        else
        {
            labelListItemDailyAgendaCustomerName.setTextColor(context.getResources().getColor(R.color.black));
            labelListItemCustomerActivityType.setTextColor(context.getResources().getColor(R.color.black));
            labelListItemCustomerActivityDate.setTextColor(context.getResources().getColor(R.color.black));
            convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        return convertView;
    }
}
