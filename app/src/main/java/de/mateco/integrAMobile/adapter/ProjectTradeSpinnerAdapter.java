package de.mateco.integrAMobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.ProjectTradeModel;

public class ProjectTradeSpinnerAdapter extends BaseAdapter
{
    private ArrayList<ProjectTradeModel> listOfTrades = new ArrayList<ProjectTradeModel>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ProjectTradeSpinnerAdapter(Context context, ArrayList<ProjectTradeModel> listOfTrades)
    {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.listOfTrades = listOfTrades;
    }

    @Override
    public int getCount() {
        return listOfTrades.size();
    }

    @Override
    public ProjectTradeModel getItem(int position) {
        return listOfTrades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_spinner_trade, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.listItemTradeName = (TextView) convertView.findViewById(R.id.listItemTradeName);

            convertView.setTag(viewHolder);
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(ProjectTradeModel object, ViewHolder holder) {
        //TODO implement
        holder.listItemTradeName.setText(object.getProjectTradeDesignation());
    }

    protected class ViewHolder {
        private TextView listItemTradeName;
    }
}
