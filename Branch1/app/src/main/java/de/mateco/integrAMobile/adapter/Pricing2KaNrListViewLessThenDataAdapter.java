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
import de.mateco.integrAMobile.model.Pricing2KaNrListViewLessThen1800Data;

public class Pricing2KaNrListViewLessThenDataAdapter extends BaseAdapter {

    private List<Pricing2KaNrListViewLessThen1800Data> listKaNrListViewLessThen;
    private Context context;
    private int selectionOfTag = -1, selectionOfRow = -1;

    public void setSelection(int selection)
    {
        selectionOfTag = selection;
    }

    public void setSelectionOfRow(int selection)
    {
        selectionOfRow = selection;
    }


    public Pricing2KaNrListViewLessThenDataAdapter(Activity context,
           List<Pricing2KaNrListViewLessThen1800Data> listKaNrListViewLessThen) {
        this.context = context;
        this.listKaNrListViewLessThen = listKaNrListViewLessThen;
    }

    @Override
    public int getCount() {
        return listKaNrListViewLessThen.size();
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
        convertView = mInflater.inflate(R.layout.fragment_pricing2_kanrlistview_less_then_data_row, null);
        LinearLayout linearLayoutListLessThenRowParentElement =
                (LinearLayout)convertView.findViewById(R.id.linearLayoutListLessThenRowParentElement);


        TextView txtPricing2KaNrLessThenHoehengruppeDataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2KaNrLessThenHoehengruppeDataRow);
        TextView txtPricing2KaNrLessThenArbeitshoeheDataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2KaNrLessThenArbeitshoeheDataRow);
        TextView txtPricing2KaNrLessThenGeratetypDataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2KaNrLessThenGeratetypDataRow);
        TextView txtPricing2KaNrLessThenListenpreisDataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2KaNrLessThenListenpreisDataRow);

        txtPricing2KaNrLessThenHoehengruppeDataRow.setText
                ("" + listKaNrListViewLessThen.get(position).getHoehengruppe());
        txtPricing2KaNrLessThenArbeitshoeheDataRow.setText
                ("" + listKaNrListViewLessThen.get(position).getArbeitshoehe());
        txtPricing2KaNrLessThenGeratetypDataRow.setText
                ("" + listKaNrListViewLessThen.get(position).getGerätetyp());
        txtPricing2KaNrLessThenListenpreisDataRow.setText
                ("" +  DataHelper.getGermanCurrencyFormat(listKaNrListViewLessThen.get(position).getListenpreis()+""));
        Log.e("list of key adapter", listKaNrListViewLessThen.get(position).getKey().size() + "");

        LinearLayout linearTags = (LinearLayout)convertView.findViewById(R.id.linearLessThenTags);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        for (int i = 0; i < listKaNrListViewLessThen.get(position).getKey().size(); i++)
        {
            TextView textView = new TextView(context);
            textView.setLayoutParams(layoutParams);
            textView.setGravity(Gravity.CENTER);
            textView.setText
                    ( DataHelper.getGermanCurrencyFormat(listKaNrListViewLessThen.get(position).
                            getListPrice().get(i)+"") +"");

            linearTags.addView(textView);
            if(i == selectionOfTag && position == selectionOfRow)
            {
                textView.setBackgroundColor(context.getResources().getColor(R.color.red));
            }
        }
        if(position == selectionOfRow)
        {
            linearLayoutListLessThenRowParentElement.setBackgroundColor
                    (context.getResources().getColor(R.color.gray));
        }
            return convertView;
        }


}
