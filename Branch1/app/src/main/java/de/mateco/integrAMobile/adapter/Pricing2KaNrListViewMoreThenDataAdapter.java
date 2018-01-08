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
import de.mateco.integrAMobile.model.Pricing2KaNrListViewMoreThen1800Data;

public class Pricing2KaNrListViewMoreThenDataAdapter extends BaseAdapter {

    private List<Pricing2KaNrListViewMoreThen1800Data> listKaNrListViewMoreThen;
    private Context context;
    private int selectionOfTag = -1, selectionOfRow = -1;

    public void setSelection(int selection) {
        selectionOfTag = selection;
    }

    public void setSelectionOfRow(int selection) {
        selectionOfRow = selection;
    }

    public Pricing2KaNrListViewMoreThenDataAdapter(Activity context,
           List<Pricing2KaNrListViewMoreThen1800Data> listKaNrListViewMoreThen) {
        this.context = context;
        this.listKaNrListViewMoreThen = listKaNrListViewMoreThen;
    }

    @Override
    public int getCount() {
        return listKaNrListViewMoreThen.size();
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
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.fragment_pricing2_kanrlistview_more_then_data_row, null);
        LinearLayout linearLayoutListMoreThenRowParentElement =
                (LinearLayout) convertView.findViewById(R.id.linearLayoutListMoreThenRowParentElement);
        TextView txtPricing2KaNrMoreThenHoehengruppeDataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2KaNrMoreThenHoehengruppeDataRow);
        TextView txtPricing2KaNrMoreThenListenpreisDataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2KaNrMoreThenListenpreisDataRow);
        TextView txtPricing2KaNrMoreThenSortDataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2KaNrMoreThenSortDataRow);

        txtPricing2KaNrMoreThenHoehengruppeDataRow.setText
                ("" + listKaNrListViewMoreThen.get(position).getHoehengruppe());
        //txtPricing2KaNrMoreThenListenpreisDataRow.setText
        // ("" + listKaNrListViewMoreThen.get(position).getListenpreis());

        txtPricing2KaNrMoreThenListenpreisDataRow.setText
                (DataHelper.getGermanCurrencyFormat(listKaNrListViewMoreThen.get(position).getListenpreis()+"") + "");

        txtPricing2KaNrMoreThenSortDataRow.setText
                ("" + listKaNrListViewMoreThen.get(position).getSort());
        Log.e("list of key adapter", listKaNrListViewMoreThen.get(position).getKey().size() + "");

        LinearLayout linearTags = (LinearLayout) convertView.findViewById(R.id.linearMoreThenTags);

        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        for (int i = 0; i < listKaNrListViewMoreThen.get(position).getKey().size(); i++) {

            TextView textView = new TextView(context);
            textView.setLayoutParams(layoutParams);
            textView.setGravity(Gravity.CENTER);
            textView.setText( DataHelper.getGermanCurrencyFormat
                    (listKaNrListViewMoreThen.get(position).getListPrice().get(i)+"") + "");

            linearTags.addView(textView);
            if(i == selectionOfTag && position == selectionOfRow)
            {
                textView.setBackgroundColor(context.getResources().getColor(R.color.red));
            }
        }
        if (position == selectionOfRow) {
            linearLayoutListMoreThenRowParentElement.setBackgroundColor
                    (context.getResources().getColor(R.color.gray));
        }
        return convertView;

    }


}
