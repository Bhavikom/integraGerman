package de.mateco.integrAMobile.adapter;

/**
 * Created by mmehta on 31.05.2016.
 */
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.LadefahrzeugComboBoxItemModel;

/***** Adapter class extends with ArrayAdapter ******/
public class SpinnerAdapterClass extends ArrayAdapter<String>{

    private Context activity;
    private ArrayList data;
    public Resources res;
    LadefahrzeugComboBoxItemModel tempValues=null;
    LayoutInflater inflater;

    /*************  CustomAdapter Constructor *****************/
    public SpinnerAdapterClass(
            Context context,
            int textViewResourceId,
            ArrayList objects,
            Resources resLocal
    )
    {
        super(context, textViewResourceId, objects);

        /********** Take passed values **********/
        activity = context;
        data     = objects;
        res      = resLocal;

        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = null;

        row = inflater.inflate(R.layout.spinner_rows, parent, false);

        /***** Get each Model object from Arraylist ********/
        tempValues = null;
        tempValues = (LadefahrzeugComboBoxItemModel) data.get(position);

        TextView label=(TextView)row.findViewById(R.id.textItem);
        label.setText(tempValues.getBezeichnung());
        /*if(position==0){
            // Default selected Spinner item
            //label.setText("");
            label.setText(tempValues.getBezeichnung());

        }
        else
        {
            // Set values for spinner each row
            label.setText(tempValues.getBezeichnung());

        }*/
        return row;
    }
}
