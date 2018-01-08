package de.mateco.integrAMobile.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.MailAddressModel;

public class MailListAdapter extends BaseAdapter
{

    private List<MailAddressModel> listOfMail = new ArrayList<MailAddressModel>();

    private Context context;
    private LayoutInflater layoutInflater;
    public SparseBooleanArray mCheckStates;

    public MailListAdapter(Context context, List<MailAddressModel> listOfMail)
    {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.listOfMail = listOfMail;
        mCheckStates = new SparseBooleanArray(listOfMail.size());
    }

    @Override
    public int getCount()
    {
        return listOfMail.size();
    }

    @Override
    public MailAddressModel getItem(int position)
    {
        return listOfMail.get(position);
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
            convertView = layoutInflater.inflate(R.layout.list_item_site_inspection_mail_selection, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.checkBoxListItemSiteInspectionMailSelectionSelectMailId =
               (CheckBox) convertView.findViewById(R.id.checkBoxListItemSiteInspectionMailSelectionSelectMailId);
            viewHolder.labelListItemSiteInspectionMailSelectionEmailAddress =
                (TextView) convertView.findViewById(R.id.labelListItemSiteInspectionMailSelectionEmailAddress);
            viewHolder.labelListItemSiteInspectionMailSelectionEmailDesignation =
                (TextView) convertView.findViewById(R.id.labelListItemSiteInspectionMailSelectionEmailDesignation);

            convertView.setTag(viewHolder);
        }
        initializeViews((MailAddressModel) getItem(position),
                (ViewHolder) convertView.getTag(), position, convertView);
        return convertView;
    }

    private void initializeViews(MailAddressModel object, final ViewHolder holder, int position,
                                 final View convertView)
    {
        holder.labelListItemSiteInspectionMailSelectionEmailAddress.setText(object.getEmail());
        holder.labelListItemSiteInspectionMailSelectionEmailDesignation.setText(object.getFunktion());
        holder.checkBoxListItemSiteInspectionMailSelectionSelectMailId.setTag(position);
        holder.checkBoxListItemSiteInspectionMailSelectionSelectMailId.setChecked
                (mCheckStates.get(position, false));
        holder.checkBoxListItemSiteInspectionMailSelectionSelectMailId.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                mCheckStates.put((Integer) buttonView.getTag(), isChecked);
                if (isChecked)
                {
                    holder.labelListItemSiteInspectionMailSelectionEmailAddress.setTextColor
                            (context.getResources().getColor(R.color.white));
                    holder.labelListItemSiteInspectionMailSelectionEmailDesignation.setTextColor
                            (context.getResources().getColor(R.color.white));
                    convertView.setBackgroundColor(context.getResources().getColor(R.color.red));
                }
                else
                {
                    holder.labelListItemSiteInspectionMailSelectionEmailAddress.setTextColor
                            (context.getResources().getColor(R.color.black));
                    holder.labelListItemSiteInspectionMailSelectionEmailDesignation.setTextColor
                            (context.getResources().getColor(R.color.black));
                    convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
                }
            }
        });
    }

    protected class ViewHolder
    {
        private CheckBox checkBoxListItemSiteInspectionMailSelectionSelectMailId;
        private TextView labelListItemSiteInspectionMailSelectionEmailAddress;
        private TextView labelListItemSiteInspectionMailSelectionEmailDesignation;
    }

    public boolean isChecked(int position)
    {
        return mCheckStates.get(position, false);
    }

    public void setChecked(int position, boolean isChecked)
    {
        mCheckStates.put(position, isChecked);
        notifyDataSetChanged();
    }

    public void toggle(int position)
    {
        setChecked(position, !isChecked(position));
    }

}
