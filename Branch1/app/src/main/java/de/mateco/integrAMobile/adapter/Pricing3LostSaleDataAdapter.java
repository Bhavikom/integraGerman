package de.mateco.integrAMobile.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.Pricing3LostSaleData;

public class Pricing3LostSaleDataAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Pricing3LostSaleData> listLostSale = new ArrayList<Pricing3LostSaleData>();
    public SparseBooleanArray mCheckStatesLostSale;
    int selected = 0;
    private int finalSelectedPosition = 0;


    public int selectedIndex=-1;

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }
    public Pricing3LostSaleDataAdapter(Context context, int resourceId,
                                       ArrayList<Pricing3LostSaleData> TeamPlayerList) {
        //super(context, resourceId, levels);
        this.context = context;
        this.listLostSale = TeamPlayerList;
        mCheckStatesLostSale = new SparseBooleanArray(TeamPlayerList.size());
    }

    private class ViewHolder {

        LinearLayout linearLostsaleRow;

        CheckBox chkSelect;

        TextView txtBranchLSP3;
        TextView txtHGRPLSP3;
        TextView txtMDLSP3;
        TextView txtRentalPriceLSP3;
        TextView txtSBLSP3;
        TextView txtHFLSP3;
        TextView txtSPP3;
        TextView txtHBP3;
        TextView txtBestP3;


    }

    public void setPosition(int position) {
        finalSelectedPosition = position;
    }

    public int getPosition() {
        return finalSelectedPosition;
    }

    @Override
    public int getCount() {

        return listLostSale.size();
    }

    @Override
    public Object getItem(int position) {
        return listLostSale.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.fragment_pricing_3_lost_sale_row, null);

            holder = new ViewHolder();

            holder.linearLostsaleRow = (LinearLayout) convertView.findViewById(R.id.linearLostsaleRow);

            holder.chkSelect = (CheckBox) convertView.findViewById(R.id.chkPricing3LostSaleData_row);

            holder.txtBranchLSP3 = (TextView) convertView.findViewById(R.id.txtBranchLSP3);
            holder.txtHGRPLSP3 = (TextView) convertView.findViewById(R.id.txtHGRPLSP3);

            holder.txtMDLSP3 = (TextView) convertView.findViewById(R.id.txtMDLSP3);
            holder.txtRentalPriceLSP3 = (TextView) convertView.findViewById(R.id.txtRentalPriceLSP3);
            holder.txtSBLSP3 = (TextView) convertView.findViewById(R.id.txtSBLSP3);
            holder.txtHFLSP3 = (TextView) convertView.findViewById(R.id.txtHFLSP3);
            holder.txtSPP3 = (TextView) convertView.findViewById(R.id.txtSPP3);
            holder.txtHBP3 = (TextView) convertView.findViewById(R.id.txtHBP3);
            holder.txtBestP3 = (TextView) convertView.findViewById(R.id.txtBestP3);


            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtBranchLSP3.setText(listLostSale.get(position).getBranch());
        holder.txtHGRPLSP3.setText(listLostSale.get(position).gethGRP());

        holder.txtMDLSP3.setText(listLostSale.get(position).getMd() + "");
        holder.txtRentalPriceLSP3.setText(DataHelper.getGermanCurrencyFormat
                (listLostSale.get(position).getRentalPrice()+"") + "");
        holder.txtSBLSP3.setText(DataHelper.getGermanCurrencyFormat
                (listLostSale.get(position).getSb()+"") + "");
        holder.txtHFLSP3.setText(listLostSale.get(position).getHfStatus()+"");
        holder.txtSPP3.setText(listLostSale.get(position).getSpStatus()+"");
        holder.txtHBP3.setText(DataHelper.getGermanCurrencyFormat(listLostSale.get(position).getHb()+"") + "");
        holder.txtBestP3.setText(listLostSale.get(position).getBest() + "");





        holder.chkSelect.setOnCheckedChangeListener(myCheckChangList);
        holder.chkSelect.setTag(position);
        //holder.chkSelect.setChecked(mCheckStatesLostSale.get(position, false));
        holder.chkSelect.setChecked(listLostSale.get(position).isSelected());

        convertView.setTag(holder);
        convertView.setTag(R.id.title, holder.txtHFLSP3);
        convertView.setTag(R.id.checkbox, holder.chkSelect);

        if(listLostSale.get(position).isSelected()){
            holder.linearLostsaleRow.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        else {
            holder.linearLostsaleRow.setBackgroundColor(context.getResources().getColor(R.color.white));
        }


        /*holder.chkSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCheckStatesLostSale.put((Integer) buttonView.getTag(), isChecked);
                getItemId(position);

            }
        });*/

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listLostSale.get(position).isSelected()==true) {
                    listLostSale.get(position).setSelected(false);
                }else{
                    listLostSale.get(position).setSelected(true);
                }
                notifyDataSetChanged();
            }
        });

        // holder.motif.setTag(position);
        /*if(selectedIndex == position)
        {
            if(holder.chkSelect.isChecked()) {
                holder.chkSelect.setChecked(false);

                holder.txtBranchLSP3.setTextColor(context.getResources().getColor(R.color.black));
                holder.txtHGRPLSP3.setTextColor(context.getResources().getColor(R.color.black));
                holder.txtMDLSP3.setTextColor(context.getResources().getColor(R.color.black));
                holder.txtRentalPriceLSP3.setTextColor(context.getResources().getColor(R.color.black));
                holder.txtSBLSP3.setTextColor(context.getResources().getColor(R.color.black));
                holder.txtHFLSP3.setTextColor(context.getResources().getColor(R.color.black));
                holder.txtSPP3.setTextColor(context.getResources().getColor(R.color.black));
                holder.txtHBP3.setTextColor(context.getResources().getColor(R.color.black));
                holder.txtBestP3.setTextColor(context.getResources().getColor(R.color.black));

                holder.linearLostsaleRow.setBackgroundColor(context.getResources().getColor(R.color.white));
            }
            else
            {
                holder.chkSelect.setChecked(true);

                holder.txtBranchLSP3.setTextColor(context.getResources().getColor(R.color.white));
                holder.txtHGRPLSP3.setTextColor(context.getResources().getColor(R.color.white));
                holder.txtMDLSP3.setTextColor(context.getResources().getColor(R.color.white));
                holder.txtRentalPriceLSP3.setTextColor(context.getResources().getColor(R.color.white));
                holder.txtSBLSP3.setTextColor(context.getResources().getColor(R.color.white));
                holder.txtHFLSP3.setTextColor(context.getResources().getColor(R.color.white));
                holder.txtSPP3.setTextColor(context.getResources().getColor(R.color.white));
                holder.txtHBP3.setTextColor(context.getResources().getColor(R.color.white));
                holder.txtBestP3.setTextColor(context.getResources().getColor(R.color.white));

                holder.linearLostsaleRow.setBackgroundColor(context.getResources().getColor(R.color.red));
            }


        }
        else
        {
            //convertView.setBackgroundColor(context.getResources().getColor(R.color.white));

        }*/
        return convertView;
    }
    public Pricing3LostSaleData getTableData(int position)
    {
        return ((Pricing3LostSaleData) getItem(position));
    }
    CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener()
    {
        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
        {
            getTableData((Integer) buttonView.getTag()).setSelected(isChecked);
            notifyDataSetChanged();
        }
    };

    public boolean isChecked(int position) {
        return mCheckStatesLostSale.get(position, false);

    }

    public void setChecked(int position, boolean isChecked) {
        mCheckStatesLostSale.put(position, isChecked);
        notifyDataSetChanged();
    }

    public void toggle(int position) {
        setChecked(position, !isChecked(position));
    }




}
