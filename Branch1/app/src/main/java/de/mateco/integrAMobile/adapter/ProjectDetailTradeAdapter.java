package de.mateco.integrAMobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.ProjectDetailTradeModel;

public class ProjectDetailTradeAdapter extends BaseAdapter
{
    private ArrayList<ProjectDetailTradeModel> listOfProjectTrade = new ArrayList<ProjectDetailTradeModel>();

    private Context context;
    private LayoutInflater layoutInflater;
    public int selectedIndex = -1;

    public void setSelectedIndex(int ind)
    {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public ProjectDetailTradeAdapter(Context context, ArrayList<ProjectDetailTradeModel> listOfProjectTrade)
    {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.listOfProjectTrade = listOfProjectTrade;
    }

    @Override
    public int getCount()
    {
        return listOfProjectTrade.size();
    }

    @Override
    public ProjectDetailTradeModel getItem(int position)
    {
        return listOfProjectTrade.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.list_item_project_trade, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.labelListItemProjectTradeMatchCode =
                    (TextView)convertView.findViewById(R.id.labelListItemProjectTradeMatchCode);
            viewHolder.labelListItemProjectTradeGewerk =
                    (TextView) convertView.findViewById(R.id.labelListItemProjectTradeGewerk);

            convertView.setTag(viewHolder);
        }
        initializeViews((ProjectDetailTradeModel)getItem(position),
                (ViewHolder) convertView.getTag(), position, convertView);
        return convertView;
    }

    private void initializeViews(ProjectDetailTradeModel object, ViewHolder holder,
                                 int position, View convertView)
    {
        //TODO implement
        holder.labelListItemProjectTradeMatchCode.setText(object.getMatchCode());
        holder.labelListItemProjectTradeGewerk.setText(object.getGewerk());
        if(selectedIndex == position)
        {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.labelListItemProjectTradeMatchCode.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelListItemProjectTradeGewerk.setTextColor(context.getResources().getColor(R.color.white));
        }
        else
        {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.labelListItemProjectTradeMatchCode.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelListItemProjectTradeGewerk.setTextColor(context.getResources().getColor(R.color.black));
        }
    }

    protected class ViewHolder {
        private TextView labelListItemProjectTradeMatchCode;
        private TextView labelListItemProjectTradeGewerk;
    }
}
