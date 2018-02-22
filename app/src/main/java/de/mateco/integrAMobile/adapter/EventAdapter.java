package de.mateco.integrAMobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model_logonsquare.WeeklyAgendaListItem;


public class EventAdapter extends BaseAdapter {

    List<WeeklyAgendaListItem> listOfEvents;
    public int selectedIndex = -1;
    private Context context;


    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public EventAdapter(Context context, List<WeeklyAgendaListItem> listOfEvents)
    {
        this.context = context;
        this.listOfEvents = listOfEvents;
    }

    @Override
    public int getCount() {
        return listOfEvents.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.list_item_event, null);

        TextView labelEventName = (TextView)convertView.findViewById(R.id.labelEventName);
        if(listOfEvents.get(position).getName1().equals(""))
            labelEventName.setText(listOfEvents.get(position).getAktivitaetstytp());
        else
            labelEventName.setText(listOfEvents.get(position).getName1());
        TextView labelEventTime = (TextView)convertView.findViewById(R.id.labelEventTime);
        if(listOfEvents != null && listOfEvents.size() > 0){
            if(!listOfEvents.get(position).getStartzeit().equalsIgnoreCase("") &&
                    !listOfEvents.get(position).getEndzeit().equalsIgnoreCase("")){
                labelEventTime.setText
                        (listOfEvents.get(position).getStartzeit().substring(0,5) + " - " +
                                listOfEvents.get(position).getEndzeit().substring(0,5));
            }

        }

        //imgvBackground.setImageResource(R.drawable.english);
        if (listOfEvents.get(position).getRealisiert().equals("False") &&
                listOfEvents.get(position).getFest().equals("False"))
        {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.lightRed));
            for(int j=0;j<position;j++)
            {
                if(listOfEvents.get(j).getDatum().equals(listOfEvents.get(position).getDatum()) &&
                        listOfEvents.get(j).getStartzeit().equals(listOfEvents.get(position).getStartzeit()) &&
                        listOfEvents.get(j).getEndzeit().equals(listOfEvents.get(position).getEndzeit())) {
                    convertView.setBackgroundColor(context.getResources().getColor(R.color.yellow));
                }
            }
        }
        else if (listOfEvents.get(position).getRealisiert().equals("False") &&
                listOfEvents.get(position).getFest().equals("True"))
        {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.red));
            for(int j=0;j<position;j++)
            {
                if(listOfEvents.get(j).getDatum().equals(listOfEvents.get(position).getDatum()) &&
                        listOfEvents.get(j).getStartzeit().equals(listOfEvents.get(position).getStartzeit()) &&
                        listOfEvents.get(j).getEndzeit().equals(listOfEvents.get(position).getEndzeit())) {
                    convertView.setBackgroundColor(context.getResources().getColor(R.color.yellow));
                }
            }
        }
        else if (listOfEvents.get(position).getRealisiert().equals("True"))
        {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.cyan));
        }
        else
        {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.ripple_material_dark));

        }
        if(selectedIndex == position)
        {
            labelEventName.setTextColor(context.getResources().getColor(R.color.white));
            labelEventTime.setTextColor(context.getResources().getColor(R.color.white));
            convertView.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        return convertView;
    }
}
