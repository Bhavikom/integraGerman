package de.mateco.integrAMobile.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.CustomerModel;
import de.mateco.integrAMobile.model.Language;

public class CustomerSearchOfflineResultAdapter extends BaseAdapter
{

    private List<CustomerModel> listOfCustomer = new ArrayList<CustomerModel>();

    private Context context;
    private LayoutInflater layoutInflater;
    public int selectedIndex = -1;
    private Language language;
    private OnCustomerRefreshClick onCustomerRefreshClick;

    public interface OnCustomerRefreshClick
    {
        void onCustomerRefreshClicked(int position);
    }

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public CustomerSearchOfflineResultAdapter(Context context, ArrayList<CustomerModel> listOfCustomer,
            Language language, OnCustomerRefreshClick onCustomerRefreshClick) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.listOfCustomer = listOfCustomer;
        this.language = language;
        this.onCustomerRefreshClick = onCustomerRefreshClick;
    }

//    @Override
//    public int getCount() {
//        return objects.size();
//    }

    @Override
    public int getCount()
    {
        return listOfCustomer.size();
    }

    @Override
    public CustomerModel getItem(int position) {
        return listOfCustomer.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_customer_search_offline_result, null);
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
            viewHolder.linearLayoutDelete =
                    (LinearLayout)convertView.findViewById(R.id.linearLayoutDelete);
            viewHolder.linearLayoutRefresh =
                    (LinearLayout)convertView.findViewById(R.id.linearLayoutRefresh);

            convertView.setTag(viewHolder);
        }
        initializeViews((CustomerModel) getItem(position), (ViewHolder) convertView.getTag(),
                position, convertView);
        return convertView;
    }

    private void initializeViews(final CustomerModel object, ViewHolder holder, final int position,
                                 View convertView) {
        holder.listItemCustomerSearchResultCustomerNo.setText(object.getKundenNr());
        holder.listItemCustomerSearchResultKaNr.setText(object.getKaNr());
        holder.listItemCustomerSearchResultMatchCode.setText(object.getMatchCode());
        holder.listItemCustomerSearchResultName1.setText(object.getName1());
        holder.listItemCustomerSearchResultRoad.setText(object.getStrasse());
        holder.listItemCustomerSearchResultZipcode.setText(object.getPLZ());
        holder.listItemCustomerSearchResultPlace.setText(object.getOrt());
        holder.listItemCustomerSearchResultTelephone.setText(object.getTelefon());
        holder.linearLayoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRemoveAlert(object, position);
            }
        });

        holder.linearLayoutRefresh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onCustomerRefreshClick.onCustomerRefreshClicked(position);
            }
        });

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
        private LinearLayout linearLayoutDelete;
        private LinearLayout linearLayoutRefresh;
    }

    private void showRemoveAlert(final CustomerModel object, final int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(language.getLabelAlert());
        builder.setMessage(language.getMessageDelete());
        builder.setCancelable(false);
        String positiveButtonText = language.getLabelYes();
        String negativeButtonText = language.getLabelNo();

        builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                DataBaseHandler db = new DataBaseHandler(context);
                db.deleteCustomerFromKontakt(object.getKontakt());
                listOfCustomer.remove(position);
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(negativeButtonText, negativeCallback);
        AlertDialog alert = builder.create();
        alert.show();
    }

    DialogInterface.OnClickListener negativeCallback = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dialog.dismiss();
        }
    };
}
