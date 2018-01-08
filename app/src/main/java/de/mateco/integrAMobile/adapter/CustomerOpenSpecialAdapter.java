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
import de.mateco.integrAMobile.model.CustomerOpenOfferModel;

public class CustomerOpenSpecialAdapter extends BaseAdapter
{
        private List<CustomerOpenOfferModel> listCustomerOpenOffer = new ArrayList<CustomerOpenOfferModel>();
        SimpleDateFormat input_format = new SimpleDateFormat("dd-MM-yyyy H:mm");
        SimpleDateFormat output_format = new SimpleDateFormat("dd.MM.yyyy");

        private Context context;
        private LayoutInflater layoutInflater;
        public int selectedIndex = -1;

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

        public CustomerOpenSpecialAdapter(Context context, ArrayList<CustomerOpenOfferModel> listCustomerOpenOffer)
        {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
            this.listCustomerOpenOffer = listCustomerOpenOffer;
        }

        @Override
        public int getCount() {
            return listCustomerOpenOffer.size();
        }

        @Override
        public CustomerOpenOfferModel getItem(int position) {
            return listCustomerOpenOffer.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_item_offer_specials, null);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.labelItemNL = (TextView) convertView.findViewById(R.id.labelItemNL);
                viewHolder.labelItemAngebot = (TextView) convertView.findViewById(R.id.labelItemAngebot);
                viewHolder.labelItemErstelltAm = (TextView) convertView.findViewById(R.id.labelItemErstellt_am);
                viewHolder.labelItemHohengruppe = (TextView) convertView.findViewById(R.id.labelItemHohengruppe);
                viewHolder.labelItemGeraetetyp = (TextView) convertView.findViewById(R.id.labelItemGeraetetyp);
                viewHolder.labelItemAnzahlGeraete = (TextView) convertView.findViewById(R.id.labelItemAnzahlGeraete);
                viewHolder.labelItemMietdauer = (TextView) convertView.findViewById(R.id.labelItemMietdauer);
                viewHolder.labelItemStaffel = (TextView) convertView.findViewById(R.id.labelItemStaffel);
                viewHolder.labelItemPreis = (TextView) convertView.findViewById(R.id.labelItemPreis);
                viewHolder.labelItemHaftb = (TextView) convertView.findViewById(R.id.labelItemHaftb);
                viewHolder.labelItemStatus = (TextView) convertView.findViewById(R.id.labelItemStatus);

                convertView.setTag(viewHolder);
            }
            initializeViews((CustomerOpenOfferModel)getItem(position),
                    (ViewHolder) convertView.getTag(),position,convertView);
            return convertView;
        }

        private void initializeViews(CustomerOpenOfferModel object,
                                     ViewHolder holder,int position, View convertView)
        {
            holder.labelItemNL.setText(object.getNL());
            holder.labelItemAngebot.setText(object.getAngebot());
            holder.labelItemErstelltAm.setText
                    (DataHelper.getDateFormatFromString(object.getErstellt_am(), output_format, input_format));
            holder.labelItemHohengruppe.setText(object.getHoehengruppe());
            holder.labelItemGeraetetyp.setText(object.getGeraetetyp());
            holder.labelItemAnzahlGeraete.setText(object.getAnzahlGeraete());
            holder.labelItemMietdauer.setText(object.getMietdauer());
            holder.labelItemStaffel.setText(object.getStaffel());
            holder.labelItemPreis.setText(object.getPreis());
            holder.labelItemHaftb.setText(object.getVers());
            holder.labelItemStatus.setText(object.getStatus());

            if(selectedIndex == position) {
                holder.labelItemNL.setTextColor(context.getResources().getColor(R.color.white));
                holder.labelItemAngebot.setTextColor(context.getResources().getColor(R.color.white));
                holder.labelItemErstelltAm.setTextColor(context.getResources().getColor(R.color.white));
                holder.labelItemHohengruppe.setTextColor(context.getResources().getColor(R.color.white));
                holder.labelItemGeraetetyp.setTextColor(context.getResources().getColor(R.color.white));
                holder.labelItemAnzahlGeraete.setTextColor(context.getResources().getColor(R.color.white));
                holder.labelItemMietdauer.setTextColor(context.getResources().getColor(R.color.white));
                holder.labelItemStaffel.setTextColor(context.getResources().getColor(R.color.white));
                holder.labelItemPreis.setTextColor(context.getResources().getColor(R.color.white));
                holder.labelItemHaftb.setTextColor(context.getResources().getColor(R.color.white));
                holder.labelItemStatus.setTextColor(context.getResources().getColor(R.color.white));

                convertView.setBackgroundColor(context.getResources().getColor(R.color.red));
            }
            else
            {
                holder.labelItemNL.setTextColor(context.getResources().getColor(R.color.black));
                holder.labelItemAngebot.setTextColor(context.getResources().getColor(R.color.black));
                holder.labelItemErstelltAm.setTextColor(context.getResources().getColor(R.color.black));
                holder.labelItemHohengruppe.setTextColor(context.getResources().getColor(R.color.black));
                holder.labelItemGeraetetyp.setTextColor(context.getResources().getColor(R.color.black));
                holder.labelItemAnzahlGeraete.setTextColor(context.getResources().getColor(R.color.black));
                holder.labelItemMietdauer.setTextColor(context.getResources().getColor(R.color.black));
                holder.labelItemStaffel.setTextColor(context.getResources().getColor(R.color.black));
                holder.labelItemPreis.setTextColor(context.getResources().getColor(R.color.black));
                holder.labelItemHaftb.setTextColor(context.getResources().getColor(R.color.black));
                holder.labelItemStatus.setTextColor(context.getResources().getColor(R.color.black));

                convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
            }
        }

        protected class ViewHolder {
            private TextView labelItemNL;
            private TextView labelItemAngebot;
            private TextView labelItemErstelltAm;
            private TextView labelItemHohengruppe;
            private TextView labelItemGeraetetyp;
            private TextView labelItemAnzahlGeraete;
            private TextView labelItemMietdauer;
            private TextView labelItemStaffel;
            private TextView labelItemPreis;
            private TextView labelItemHaftb;
            private TextView labelItemStatus;
        }
}
