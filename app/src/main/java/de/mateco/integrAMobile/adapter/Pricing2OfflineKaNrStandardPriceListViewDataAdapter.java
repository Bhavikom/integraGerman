package de.mateco.integrAMobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.PricingOfflineStandardPriceData;

public class Pricing2OfflineKaNrStandardPriceListViewDataAdapter extends BaseAdapter {

    private List<PricingOfflineStandardPriceData> listKaNrOfflineStandardPriceListView;
    private Context context;
    private int selectionOfTag = -1, selectionOfRow = -1;

    public void setSelection(int selection) {
        selectionOfTag = selection;
    }

    public void setSelectionOfRow(int selection) {
        selectionOfRow = selection;
    }

    public Pricing2OfflineKaNrStandardPriceListViewDataAdapter(Activity context,
            List<PricingOfflineStandardPriceData> listOfflineStandardPriceKaNrListView) {
        this.context = context;
        this.listKaNrOfflineStandardPriceListView = listOfflineStandardPriceKaNrListView;
    }

    @Override
    public int getCount() {
        return listKaNrOfflineStandardPriceListView.size();
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
        convertView = mInflater.inflate(R.layout.fragment_pricing2_offline_kanr_standardprice_listview_data_row, null);

        LinearLayout linearLayoutListStandardPriceRowParentElement =
           (LinearLayout) convertView.findViewById(R.id.linearOfflineStandardPriceStandardPriceRowParentElement);

        TextView txtPricing2OfflineStandardHoehengruppeDataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2OfflineStandardHoehengruppeDataRow);
        TextView txtPricing2OfflineStandardListenpreisDataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2OfflineStandardListenpreisDataRow);
        TextView txtPricing2OfflineStandardSortDataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2OfflineStandardSortDataRow);

        TextView txtPricing2OfflineStandard_1_2_R_DataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2OfflineStandard_1_2_R_DataRow);
        TextView txtPricing2OfflineStandard_1_2_M_DataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2OfflineStandard_1_2_M_DataRow);
        TextView txtPricing2OfflineStandard_3_4_R_DataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2OfflineStandard_3_4_R_DataRow);
        TextView txtPricing2OfflineStandard_3_4_M_DataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2OfflineStandard_3_4_M_DataRow);
        TextView txtPricing2OfflineStandard_5_10_R_DataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2OfflineStandard_5_10_R_DataRow);
        TextView txtPricing2OfflineStandard_5_10_M_DataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2OfflineStandard_5_10_M_DataRow);
        TextView txtPricing2OfflineStandard_11_20_R_DataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2OfflineStandard_11_20_R_DataRow);
        TextView txtPricing2OfflineStandard_11_20_M_DataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2OfflineStandard_11_20_M_DataRow);
        TextView txtPricing2OfflineStandard_Ab_21_R_DataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2OfflineStandard_Ab_21_R_DataRow);
        TextView txtPricing2OfflineStandard_Ab_21_M_DataRow =
                (TextView) convertView.findViewById(R.id.txtPricing2OfflineStandard_Ab_21_M_DataRow);



        txtPricing2OfflineStandardHoehengruppeDataRow.setText
                ("" + listKaNrOfflineStandardPriceListView.get(position).getHoehengruppe());
        txtPricing2OfflineStandardListenpreisDataRow.setText
                ("" + DataHelper.getGermanCurrencyFormat( listKaNrOfflineStandardPriceListView.get(position).getListenpreis()+""));
        //txtPricing2OfflineStandardSortDataRow.setText
        // ("" + listKaNrOfflineStandardPriceListView.get(position).getHoehengruppe());
        txtPricing2OfflineStandardSortDataRow.setVisibility(View.GONE





        );


        txtPricing2OfflineStandard_1_2_R_DataRow.setText(DataHelper.getGermanCurrencyFormat
                (String.valueOf(listKaNrOfflineStandardPriceListView.get(position).getTageR_1_2())));
        txtPricing2OfflineStandard_1_2_M_DataRow.setText("" + DataHelper.getGermanCurrencyFormat
                (String.valueOf(listKaNrOfflineStandardPriceListView.get(position).getTageM_1_2())));
        txtPricing2OfflineStandard_3_4_R_DataRow.setText("" + DataHelper.getGermanCurrencyFormat
                (String.valueOf(listKaNrOfflineStandardPriceListView.get(position).getTageR_3_4())));
        txtPricing2OfflineStandard_3_4_M_DataRow.setText("" + DataHelper.getGermanCurrencyFormat
                (String.valueOf(listKaNrOfflineStandardPriceListView.get(position).getTageM_3_4())));
        txtPricing2OfflineStandard_5_10_R_DataRow.setText("" + DataHelper.getGermanCurrencyFormat
                (String.valueOf(listKaNrOfflineStandardPriceListView.get(position).getTageR_5_10())));
        txtPricing2OfflineStandard_5_10_M_DataRow.setText("" + DataHelper.getGermanCurrencyFormat
                (String.valueOf(listKaNrOfflineStandardPriceListView.get(position).getTageM_5_10())));
        txtPricing2OfflineStandard_11_20_R_DataRow.setText("" + DataHelper.getGermanCurrencyFormat
                (String.valueOf(listKaNrOfflineStandardPriceListView.get(position).getTageR_11_20())));
        txtPricing2OfflineStandard_11_20_M_DataRow.setText("" + DataHelper.getGermanCurrencyFormat
                (String.valueOf(listKaNrOfflineStandardPriceListView.get(position).getTageM_11_20())));
        txtPricing2OfflineStandard_Ab_21_R_DataRow.setText("" + DataHelper.getGermanCurrencyFormat
                (String.valueOf(listKaNrOfflineStandardPriceListView.get(position).getAb21TageR())));
        txtPricing2OfflineStandard_Ab_21_M_DataRow.setText("" + DataHelper.getGermanCurrencyFormat
                (String.valueOf(listKaNrOfflineStandardPriceListView.get(position).getAb21TageM())));


        Log.e("list of array size", listKaNrOfflineStandardPriceListView.size() + "");
        LinearLayout linearTags = (LinearLayout) convertView.findViewById(R.id.linearOfflineStandardPriceTags);
        Log.e("list of key adapter", listKaNrOfflineStandardPriceListView.get(position)
                .getHoehengruppe().length() + "");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
       /* for (int i = 0; i < listKaNrListView.get(position).getKey().size(); i++) {
            TextView textView = new TextView(context);
            textView.setLayoutParams(layoutParams);
            textView.setGravity(Gravity.CENTER);
            textView.setText( DataHelper.getGermanCurrencyFormat
            (listKaNrListView.get(position).getListOfRPrice().get(i)) + "");

            TextView textView1 = new TextView(context);
            textView1.setLayoutParams(layoutParams);
            textView1.setGravity(Gravity.CENTER);
            textView1.setText( DataHelper.getGermanCurrencyFormat
            (listKaNrListView.get(position).getListOfMPrice().get(i)) + "");

            linearTags.addView(textView);
            linearTags.addView(textView1);
            if (i == selectionOfTag) {
                textView.setBackgroundColor(context.getResources().getColor(R.color.red));
                // textView1.setBackgroundColor(context.getResources().getColor(R.color.red));
            }
        }*/
        if (position == selectionOfRow) {
            linearLayoutListStandardPriceRowParentElement.setBackgroundColor
                    (context.getResources().getColor(R.color.gray));
        }

        return convertView;
    }


}
