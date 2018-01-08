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

import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.Pricing1EquipmentData;

public class Pricing1EquipementDataAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Pricing1EquipmentData> TeamPlayerList = new ArrayList<Pricing1EquipmentData>();
    public SparseBooleanArray mCheckStates;
    int selected = 0;
    private int finalSelectedPosition = 0;
    public int selectedIndex = -1;

    public void setSelectedIndex(int ind)
    {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public Pricing1EquipementDataAdapter(Context context, int resourceId,
                                         ArrayList<Pricing1EquipmentData> TeamPlayerList) {
        //super(context, resourceId, levels);
        this.context = context;
        this.TeamPlayerList = TeamPlayerList;
        mCheckStates = new SparseBooleanArray(TeamPlayerList.size());
    }

    private class ViewHolder
    {
        LinearLayout linearEquipment;
        TextView txtname;
        CheckBox chkSelect;
    }

    public void setPosition(int position) {
        finalSelectedPosition = position;
    }

    public int getPosition() {
        return finalSelectedPosition;
    }

    @Override
    public int getCount() {

        return TeamPlayerList.size();
    }

    @Override
    public Object getItem(int position) {
        return TeamPlayerList.get(position);
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
        final ViewHolder holder;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.fragment_pricing1_equipment_row, null);
            holder = new ViewHolder();
            holder.linearEquipment = (LinearLayout) convertView.findViewById(R.id.linearEquipment);
            holder.txtname = (TextView) convertView.findViewById(R.id.txtPricing1EquipmentData_row);
            holder.chkSelect = (CheckBox) convertView.findViewById(R.id.chkPricing1EquipmentData_row);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtname.setText(TeamPlayerList.get(position).getDesignationId());

        holder.chkSelect.setTag(position);

        holder.chkSelect.setChecked(mCheckStates.get(position, false));

        holder.chkSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                mCheckStates.put((Integer) buttonView.getTag(), isChecked);
                getItemId(position);

                View row = (View) holder.txtname.getParent();
                if (isChecked)
                {
                    holder.txtname.setTextColor(context.getResources().getColor(R.color.white));
                    row.setBackgroundColor(context.getResources().getColor(R.color.red));

                }
                else
                {
                    holder.txtname.setTextColor(context.getResources().getColor(R.color.black));
                    row.setBackgroundColor(context.getResources().getColor(R.color.white));

                }
            }
        });


        if(selectedIndex == position)
        {
            if(holder.chkSelect.isChecked())
            {
                holder.chkSelect.setChecked(false);

                holder.txtname.setTextColor(context.getResources().getColor(R.color.black));

               // holder.linearEquipment.setBackgroundColor(context.getResources().getColor(R.color.white));
            }
            else
            {
                holder.chkSelect.setChecked(true);

                holder.txtname.setTextColor(context.getResources().getColor(R.color.white));
               // holder.linearEquipment.setBackgroundColor(context.getResources().getColor(R.color.red));
            }


        }
        else
        {
            //convertView.setBackgroundColor(context.getResources().getColor(R.color.white));

        }

        // holder.motif.setTag(position);

        return convertView;
    }

    public boolean isChecked(int position) {
        return mCheckStates.get(position, false);

    }

    public void setChecked(int position, boolean isChecked) {
        mCheckStates.put(position, isChecked);
        notifyDataSetChanged();
    }

    public void toggle(int position) {
        setChecked(position, !isChecked(position));
    }

    public int captainId(int position) {
        return TeamPlayerList.get(position).getEquipment();
    }


}