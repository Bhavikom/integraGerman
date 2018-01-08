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
import de.mateco.integrAMobile.model.CustomerModel;

public class CustomerSearchResultAdapter extends BaseAdapter {

    private List<CustomerModel> objects = new ArrayList<CustomerModel>();

    private Context context;
    private LayoutInflater layoutInflater;
    public int selectedIndex = -1;

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public CustomerSearchResultAdapter(Context context, ArrayList<CustomerModel> customerModels) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = customerModels;
    }

//    @Override
//    public int getCount() {
//        return objects.size();
//    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public CustomerModel getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_customer_search_result, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.listItemCustomerSearchResultCustomerNo =
                    (TextView) convertView.findViewById(R.id.listItemCustomerSearchResultCustomerNo);
            viewHolder.listItemCustomerSearchResultKaNr =
                    (TextView) convertView.findViewById(R.id.listItemCustomerSearchResultKaNr);
            viewHolder.listItemCustomerSearchResultMatchCode =
                    (TextView) convertView.findViewById(R.id.listItemCustomerSearchResultMatchCode);
            viewHolder.listItemCustomerSearchResultName1 =
                    (TextView) convertView.findViewById(R.id.listItemCustomerSearchResultName1);
            viewHolder.listItemCustomerSearchResultRoad =
                    (TextView) convertView.findViewById(R.id.listItemCustomerSearchResultRoad);
            viewHolder.listItemCustomerSearchResultZipcode =
                    (TextView) convertView.findViewById(R.id.listItemCustomerSearchResultZipcode);
            viewHolder.listItemCustomerSearchResultPlace =
                    (TextView) convertView.findViewById(R.id.listItemCustomerSearchResultPlace);
            viewHolder.listItemCustomerSearchResultTelephone =
                    (TextView) convertView.findViewById(R.id.listItemCustomerSearchResultTelephone);

            convertView.setTag(viewHolder);
        }
        initializeViews((CustomerModel) getItem(position), (ViewHolder) convertView.getTag(), position, convertView);
        return convertView;
    }

    private void initializeViews(CustomerModel object, ViewHolder holder, int position, View convertView) {
        holder.listItemCustomerSearchResultCustomerNo.setText(object.getKundenNr());
        holder.listItemCustomerSearchResultKaNr.setText(object.getKaNr());
        holder.listItemCustomerSearchResultMatchCode.setText(object.getMatchCode());
        holder.listItemCustomerSearchResultName1.setText(object.getName1());
        holder.listItemCustomerSearchResultRoad.setText(object.getStrasse());
        holder.listItemCustomerSearchResultZipcode.setText(object.getPLZ());
        holder.listItemCustomerSearchResultPlace.setText(object.getOrt());
        holder.listItemCustomerSearchResultTelephone.setText(object.getTelefon());

        if(selectedIndex == position)
        {
            holder.listItemCustomerSearchResultCustomerNo.setTextColor
                    (context.getResources().getColor(R.color.white));
            holder.listItemCustomerSearchResultKaNr.setTextColor
                    (context.getResources().getColor(R.color.white));
            holder.listItemCustomerSearchResultMatchCode.setTextColor
                    (context.getResources().getColor(R.color.white));
            holder.listItemCustomerSearchResultName1.setTextColor
                    (context.getResources().getColor(R.color.white));
            holder.listItemCustomerSearchResultRoad.setTextColor
                    (context.getResources().getColor(R.color.white));
            holder.listItemCustomerSearchResultZipcode.setTextColor
                    (context.getResources().getColor(R.color.white));
            holder.listItemCustomerSearchResultPlace.setTextColor
                    (context.getResources().getColor(R.color.white));
            holder.listItemCustomerSearchResultTelephone.setTextColor
                    (context.getResources().getColor(R.color.white));
            convertView.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        else
        {

            holder.listItemCustomerSearchResultCustomerNo.setTextColor
                    (context.getResources().getColor(R.color.black));
            holder.listItemCustomerSearchResultKaNr.setTextColor
                    (context.getResources().getColor(R.color.black));
            holder.listItemCustomerSearchResultMatchCode.setTextColor
                    (context.getResources().getColor(R.color.black));
            holder.listItemCustomerSearchResultName1.setTextColor
                    (context.getResources().getColor(R.color.black));
            holder.listItemCustomerSearchResultRoad.setTextColor
                    (context.getResources().getColor(R.color.black));
            holder.listItemCustomerSearchResultZipcode.setTextColor
                    (context.getResources().getColor(R.color.black));
            holder.listItemCustomerSearchResultPlace.setTextColor
                    (context.getResources().getColor(R.color.black));
            holder.listItemCustomerSearchResultTelephone.setTextColor
                    (context.getResources().getColor(R.color.black));

            convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    protected class ViewHolder {
        private TextView listItemCustomerSearchResultCustomerNo;
        private TextView listItemCustomerSearchResultKaNr;
        private TextView listItemCustomerSearchResultMatchCode;
        private TextView listItemCustomerSearchResultName1;
        private TextView listItemCustomerSearchResultRoad;
        private TextView listItemCustomerSearchResultZipcode;
        private TextView listItemCustomerSearchResultPlace;
        private TextView listItemCustomerSearchResultTelephone;
    }
}
