package de.mateco.integrAMobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.CustomerLostSaleDataModel;

public class LostSaleAdapter extends BaseAdapter
{
    private List<CustomerLostSaleDataModel> listOfLostSale = new ArrayList<CustomerLostSaleDataModel>();
    SimpleDateFormat input_format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    SimpleDateFormat output_format = new SimpleDateFormat("dd.MM.yyyy");

    private Context context;
    private LayoutInflater layoutInflater;

    public int selectedIndex = -1;

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public LostSaleAdapter(Context context, ArrayList<CustomerLostSaleDataModel> listOfLostSale) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.listOfLostSale = listOfLostSale;
    }

    @Override
    public int getCount() {
        return listOfLostSale.size();
    }

    @Override
    public CustomerLostSaleDataModel getItem(int position) {
        return listOfLostSale.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_offer_lost_sale, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.labelItemAbsagegrund = (TextView) convertView.findViewById(R.id.labelItemAbsagegrund);
            viewHolder.labelItemErstelltAm = (TextView) convertView.findViewById(R.id.labelItemErstellt_am);
            //viewHolder.labelItemGeraetegruppe = (TextView) convertView.findViewById(R.id.labelItemGeraetegruppe);
            viewHolder.labelItemGeraetetyp = (TextView) convertView.findViewById(R.id.labelItemGeraetetyp);
            viewHolder.labelItemHohengruppe = (TextView) convertView.findViewById(R.id.labelItemHohengruppe);
            //viewHolder.labelItemKontakt = (TextView) convertView.findViewById(R.id.labelItemKontakt);
            //viewHolder.labelItemMandant = (TextView) convertView.findViewById(R.id.labelItemMandant);
            viewHolder.labelItemMietdauer = (TextView) convertView.findViewById(R.id.labelItemMietdauer);
            viewHolder.labelItemMietpreis = (TextView) convertView.findViewById(R.id.labelItemMietpreis);
            viewHolder.labelItemNL = (TextView) convertView.findViewById(R.id.labelItemNL);
            //viewHolder.labelItemRegion = (TextView) convertView.findViewById(R.id.labelItemRegion);
            viewHolder.labelItemVersicherung = (TextView) convertView.findViewById(R.id.labelItemVersicherung);

            convertView.setTag(viewHolder);
        }
        initializeViews((CustomerLostSaleDataModel)getItem(position),
                (ViewHolder) convertView.getTag(),position,convertView);
        return convertView;
    }

    private void initializeViews(CustomerLostSaleDataModel object,
                                 ViewHolder holder,int position,View convertView) {
        //TODO implement
        holder.labelItemAbsagegrund.setText(object.getAbsagegrund());
        holder.labelItemErstelltAm.setText(DataHelper.getDateFormatFromString(object.getErstellt_am(),
                output_format, input_format));
       // holder.labelItemErstelltAm.setText(object.getErstellt_am());
        //holder.labelItemGeraetegruppe.setText(object.getGeraetegruppe());
        holder.labelItemGeraetetyp.setText(object.getGeraetetyp());
        holder.labelItemHohengruppe.setText(object.getHohengruppe());
        //holder.labelItemKontakt.setText(object.getKontakt());
        //holder.labelItemMandant.setText(object.getMandant());
        holder.labelItemMietdauer.setText(object.getMietdauer());
        holder.labelItemMietpreis.setText(object.getMietpreis());
        holder.labelItemNL.setText(object.getNL());
        //holder.labelItemRegion.setText(object.getRegion());
        holder.labelItemVersicherung.setText(object.getVersicherung());

        if(selectedIndex == position)
        {
            holder.labelItemAbsagegrund.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemErstelltAm.setTextColor(context.getResources().getColor(R.color.white));
            //holder.labelItemGeraetegruppe.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemGeraetetyp.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemHohengruppe.setTextColor(context.getResources().getColor(R.color.white));
            //holder.labelItemKontakt.setTextColor(context.getResources().getColor(R.color.white));
            //holder.labelItemMandant.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemMietdauer.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemMietpreis.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemNL.setTextColor(context.getResources().getColor(R.color.white));
            //holder.labelItemRegion.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemVersicherung.setTextColor(context.getResources().getColor(R.color.white));

            convertView.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        else
        {
            holder.labelItemAbsagegrund.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemErstelltAm.setTextColor(context.getResources().getColor(R.color.black));
            //holder.labelItemGeraetegruppe.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemGeraetetyp.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemHohengruppe.setTextColor(context.getResources().getColor(R.color.black));
            //holder.labelItemKontakt.setTextColor(context.getResources().getColor(R.color.black));
            //holder.labelItemMandant.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemMietdauer.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemMietpreis.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemNL.setTextColor(context.getResources().getColor(R.color.black));
            //holder.labelItemRegion.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemVersicherung.setTextColor(context.getResources().getColor(R.color.black));

            convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    protected class ViewHolder {
        private TextView labelItemAbsagegrund;
        private TextView labelItemErstelltAm;
        //private TextView labelItemGeraetegruppe;
        private TextView labelItemGeraetetyp;
        private TextView labelItemHohengruppe;
        //private TextView labelItemKontakt;
        //private TextView labelItemMandant;
        private TextView labelItemMietdauer;
        private TextView labelItemMietpreis;
        private TextView labelItemNL;
        //private TextView labelItemRegion;
        private TextView labelItemVersicherung;
    }
}
