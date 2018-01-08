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
import de.mateco.integrAMobile.model.CustomerCompletedOrderModel;

public class CustomerCompletedOrderAdapter extends BaseAdapter
{
    private List<CustomerCompletedOrderModel> listOfCompletedOrder = new ArrayList<CustomerCompletedOrderModel>();

    SimpleDateFormat input_format = new SimpleDateFormat("dd-MM-yyyy H:mm");
    SimpleDateFormat output_format = new SimpleDateFormat("dd.MM.yyyy");
    private Context context;
    private LayoutInflater layoutInflater;

    public int selectedIndex = -1;

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }


    public CustomerCompletedOrderAdapter(Context context, ArrayList<CustomerCompletedOrderModel> listOfCompletedOrder) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.listOfCompletedOrder = listOfCompletedOrder;
    }

    @Override
    public int getCount() {
        return listOfCompletedOrder.size();
    }

    @Override
    public CustomerCompletedOrderModel getItem(int position) {
        return listOfCompletedOrder.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_offer_completed_order, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.labelItemAuftragNr = (TextView) convertView.findViewById(R.id.labelItemAuftragNr);
            //viewHolder.labelItemDatumAb = (TextView)convertView.findViewById(R.id.labelItemDatumAb);
            viewHolder.labelItemEinheit = (TextView) convertView.findViewById(R.id.labelItemEinheit);
            viewHolder.labelItemEinsatzPLZ = (TextView) convertView.findViewById(R.id.labelItemEinsatzPLZ);
            viewHolder.labelItemEinsatzort = (TextView) convertView.findViewById(R.id.labelItemEinsatzort);
            viewHolder.labelItemGelGerat = (TextView) convertView.findViewById(R.id.labelItemGel_Gerat);
            //viewHolder.labelItemGeraetegruppe = (TextView) convertView.findViewById(R.id.labelItemGeraetegruppe);
            viewHolder.labelItemHohengruppe = (TextView) convertView.findViewById(R.id.labelItemHohengruppe);
            viewHolder.labelItemKaNr = (TextView) convertView.findViewById(R.id.labelItemKaNr);
            viewHolder.labelItemKaP = (TextView) convertView.findViewById(R.id.labelItemKaP);
            //viewHolder.labelItemKundenNr = (TextView) convertView.findViewById(R.id.labelItemKundenNr);
            viewHolder.labelItemLP = (TextView) convertView.findViewById(R.id.labelItemLP);
            //viewHolder.labelItemMandant = (TextView) convertView.findViewById(R.id.labelItemMandant);
            viewHolder.labelItemMietbeginn = (TextView) convertView.findViewById(R.id.labelItemMietbeginn);
            viewHolder.labelItemMietdauer = (TextView) convertView.findViewById(R.id.labelItemMietdauer);
            //viewHolder.labelItemMietende = (TextView) convertView.findViewById(R.id.labelItemMietende);
            viewHolder.labelItemNL = (TextView) convertView.findViewById(R.id.labelItemNL);
            //viewHolder.labelItemRegion = (TextView) convertView.findViewById(R.id.labelItemRegion);
            viewHolder.labelItemSelbstbehalt = (TextView) convertView.findViewById(R.id.labelItemSelbstbehalt);
            viewHolder.labelItemTP = (TextView) convertView.findViewById(R.id.labelItemTP);
            viewHolder.labelItemVersLP = (TextView) convertView.findViewById(R.id.labelItemVers_LP);
            viewHolder.labelItemVersTP = (TextView) convertView.findViewById(R.id.labelItemVers_TP);
            viewHolder.labelItemWunschgerat = (TextView) convertView.findViewById(R.id.labelItemWunschgerat);

            convertView.setTag(viewHolder);
        }
        initializeViews((CustomerCompletedOrderModel)getItem(position), (ViewHolder) convertView.getTag(),
                position,convertView);
        return convertView;
    }

    private void initializeViews(CustomerCompletedOrderModel object, ViewHolder holder,int position, View convertView)
    {
        //TODO implement
        holder.labelItemAuftragNr.setText(object.getAuftragNr());
        //holder.labelItemDatumAb.setText(DataHelper.getDateFormatFromString(object.getDatumAb(), output_format, input_format));
        holder.labelItemEinheit.setText(object.getEinheit());
        holder.labelItemEinsatzPLZ.setText(object.getEinsatzPLZ());
        holder.labelItemEinsatzort.setText(object.getEinsatzort());
        holder.labelItemGelGerat.setText(object.getGel_Gerat());
        //holder.labelItemGeraetegruppe.setText(object.getGeraetegruppe());
        holder.labelItemHohengruppe.setText(object.getHohengruppe());
        holder.labelItemKaNr.setText(object.getKaNr());
        holder.labelItemKaP.setText(DataHelper.getGermanFromEnglish(object.getKaP()));
        //holder.labelItemKundenNr.setText(object.getKundenNr());
        holder.labelItemLP.setText(DataHelper.getGermanFromEnglish(object.getLP()));
        //holder.labelItemMandant.setText(object.getMandant());
        holder.labelItemMietbeginn.setText(DataHelper.getDateFormatFromString(object.getMietbeginn(),
                output_format, input_format));
        holder.labelItemMietdauer.setText(object.getMietdauer());
        //holder.labelItemMietende.setText(DataHelper.getDateFormatFromString(object.getMietende(),
        // output_format, input_format));
        holder.labelItemNL.setText(object.getNL());
        //holder.labelItemRegion.setText(object.getRegion());
        holder.labelItemSelbstbehalt.setText(object.getSelbstbehalt());
        holder.labelItemTP.setText(DataHelper.getGermanFromEnglish(object.getTP()));
        holder.labelItemVersLP.setText(DataHelper.getGermanFromEnglish(object.getVers_LP()));
        holder.labelItemVersTP.setText(DataHelper.getGermanFromEnglish(object.getVers_TP()));
        holder.labelItemWunschgerat.setText(object.getWunschgerat());


        if(selectedIndex == position)
        {
            holder.labelItemAuftragNr.setTextColor(context.getResources().getColor(R.color.white));
            //holder.labelItemDatumAb.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemEinheit.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemEinsatzPLZ.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemEinsatzort.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemGelGerat.setTextColor(context.getResources().getColor(R.color.white));
            //holder.labelItemGeraetegruppe.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemHohengruppe.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemKaNr.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemKaP.setTextColor(context.getResources().getColor(R.color.white));
            //holder.labelItemKundenNr.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemLP.setTextColor(context.getResources().getColor(R.color.white));
            //holder.labelItemMandant.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemMietbeginn.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemMietdauer.setTextColor(context.getResources().getColor(R.color.white));
            //holder.labelItemMietende.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemNL.setTextColor(context.getResources().getColor(R.color.white));
            //holder.labelItemRegion.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemSelbstbehalt.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemTP.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemVersLP.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemVersTP.setTextColor(context.getResources().getColor(R.color.white));
            holder.labelItemWunschgerat.setTextColor(context.getResources().getColor(R.color.white));


            convertView.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        else
        {
            holder.labelItemAuftragNr.setTextColor(context.getResources().getColor(R.color.black));
            //holder.labelItemDatumAb.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemEinheit.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemEinsatzPLZ.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemEinsatzort.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemGelGerat.setTextColor(context.getResources().getColor(R.color.black));
           // holder.labelItemGeraetegruppe.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemHohengruppe.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemKaNr.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemKaP.setTextColor(context.getResources().getColor(R.color.black));
            //holder.labelItemKundenNr.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemLP.setTextColor(context.getResources().getColor(R.color.black));
            //holder.labelItemMandant.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemMietbeginn.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemMietdauer.setTextColor(context.getResources().getColor(R.color.black));
            //holder.labelItemMietende.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemNL.setTextColor(context.getResources().getColor(R.color.black));
            //holder.labelItemRegion.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemSelbstbehalt.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemTP.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemVersLP.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemVersTP.setTextColor(context.getResources().getColor(R.color.black));
            holder.labelItemWunschgerat.setTextColor(context.getResources().getColor(R.color.black));

            convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    protected class ViewHolder {
        private TextView labelItemAuftragNr;
        //private TextView labelItemDatumAb;
        private TextView labelItemEinheit;
        private TextView labelItemEinsatzPLZ;
        private TextView labelItemEinsatzort;
        private TextView labelItemGelGerat;
        //private TextView labelItemGeraetegruppe;
        private TextView labelItemHohengruppe;
        private TextView labelItemKaNr;
        private TextView labelItemKaP;
        //private TextView labelItemKundenNr;
        private TextView labelItemLP;
       // private TextView labelItemMandant;
        private TextView labelItemMietbeginn;
        private TextView labelItemMietdauer;
        //private TextView labelItemMietende;
        private TextView labelItemNL;
        //private TextView labelItemRegion;
        private TextView labelItemSelbstbehalt;
        private TextView labelItemTP;
        private TextView labelItemVersLP;
        private TextView labelItemVersTP;
        private TextView labelItemWunschgerat;
    }
}
