package de.mateco.integrAMobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.Pricing2KaNrListViewData;

public class Pricing2KaNrListViewDataAdapter extends BaseAdapter {

    private List<Pricing2KaNrListViewData> listKaNrListView;
    private Context context;
    private int selectionOfTag = -1, selectionOfRow = -1;

    public void setSelection(int selection) {
        selectionOfTag = selection;
        Log.e(""," selection of tag in adapter : "+selectionOfTag);
    }

    public void setSelectionOfRow(int selection) {
        selectionOfRow = selection;
        Log.e(""," selection of row in adapter : "+selectionOfRow);
    }

    public Pricing2KaNrListViewDataAdapter(Activity context, List<Pricing2KaNrListViewData> listKaNrListView) {
        this.context = context;
        this.listKaNrListView = listKaNrListView;
    }

    @Override
    public int getCount() {
        return listKaNrListView.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*@Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {

        View v = getView(position, convertView, parent);

        return v;
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = null;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.fragment_pricing2_kanrlistview_data_row, null);
        LinearLayout linearLayoutListStandardPriceRowParentElement =
                (LinearLayout) convertView.findViewById(R.id.linearLayoutListStandardPriceRowParentElement);
        TextView txtPricing2KaNrHeightGroupDataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2KaNrHeightGroupDataRow);
        TextView txtPricing2KaNrListPriceDataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2KaNrListPriceDataRow);
        TextView txtPricing2KaNrSortDataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2KaNrSortDataRow);
        //TextView txtPricing2KaNrTagesDataRow =
        // (TextView) convertView.findViewById(R.id.txtPricing2KaNrTagesDataRow);
        //TextView txtPricing2KaNrRDataRow =
        // (TextView) convertView.findViewById(R.id.txtPricing2KaNrRDataRow);
        //TextView txtPricing2KaNrMDataRow =
        // (TextView) convertView.findViewById(R.id.txtPricing2KaNrMDataRow);

        // TextView txtPricing2KaNrR_Value_DataRow =
        // (TextView)convertView.findViewById(R.id.txtPricing2KaNrRValueDataRow);
        // TextView txtPricing2KaNrM_Value_DataRow =
        // (TextView)convertView.findViewById(R.id.txtPricing2KaNrMValueDataRow);

        txtPricing2KaNrHeightGroupDataRow.setText("" + listKaNrListView.get(position).getHoehengruppe());
        txtPricing2KaNrListPriceDataRow.
                setText("" + DataHelper.getGermanCurrencyFormat( listKaNrListView.get(position).getListenpreis()+""));
        txtPricing2KaNrSortDataRow.setText("" + listKaNrListView.get(position).getSort());
        //txtPricing2KaNrTagesDataRow.setText("" + listKaNrListView.get(position).getKey());

        Log.e("list of array size", listKaNrListView.size() + "");
        LinearLayout linearTags = (LinearLayout) convertView.findViewById(R.id.linearTags);
        Log.e("list of key adapter", listKaNrListView.get(position).getKey().size() + "");
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
        //LinearLayout.LayoutParams layoutParams =
        // new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //layoutParams.setMargins(0,0,5,0);
        for (int i = 0; i < listKaNrListView.get(position).getKey().size(); i++) {
             textView = new TextView(context);
            //textView.setEms(3);
            textView.setLayoutParams(layoutParams);
            textView.setGravity(Gravity.CENTER);
            //textView.setPadding(20,20,20,20);
            textView.setText(DataHelper.getGermanCurrencyFormat
                    (listKaNrListView.get(position).getListOfRPrice().get(i)+"") + "");

            TextView textView1 = new TextView(context);
            //textView.setEms(3);
            textView1.setLayoutParams(layoutParams);
            textView1.setGravity(Gravity.CENTER);
            textView1.setText( DataHelper.getGermanCurrencyFormat
                    (listKaNrListView.get(position).getListOfMPrice().get(i)+"") + "");

            linearTags.addView(textView);
            linearTags.addView(textView1);
            if(i == selectionOfTag && position == selectionOfRow)
            {
                textView.setBackgroundColor(context.getResources().getColor(R.color.red));
                // textView1.setBackgroundColor(context.getResources().getColor(R.color.red));
            }
        }
        if (position == selectionOfRow) {
           // textView.setBackgroundColor(context.getResources().getColor(R.color.red));
            linearLayoutListStandardPriceRowParentElement.setBackgroundColor
                    (context.getResources().getColor(R.color.gray));
        }
//        r += listKaNrListView.get(position).getListOfRPrice().get(i) +",";
//        m += listKaNrListView.get(position).getListOfMPrice().get(i)+",";
//        txtPricing2KaNrRDataRow.setText(r);
//        txtPricing2KaNrMDataRow.setText(m);


//        for (int i = 0; i < listKaNrListView.get(position).getKey().size(); i++) {
//
//
//        }
        return convertView;
    }


}
