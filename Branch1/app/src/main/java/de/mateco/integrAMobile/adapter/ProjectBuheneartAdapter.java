package de.mateco.integrAMobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.BuheneartModel;
import de.mateco.integrAMobile.model.Language;



public class ProjectBuheneartAdapter extends BaseAdapter {

    private int resourceId;
    private ArrayList<BuheneartModel> listOfBuheneart;
    private Context context;
    private Language language;

    public ProjectBuheneartAdapter(Context context, ArrayList<BuheneartModel> listOfBuheneart,
                                   int resourceId, Language language)
    {
        this.context = context;
        this.listOfBuheneart = listOfBuheneart;
        this.resourceId = resourceId;
        this.language = language;
    }
    @Override
    public int getCount() {
        return listOfBuheneart.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return listOfBuheneart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(position == 0)
        {
            convertView = mInflater.inflate(R.layout.list_item_spinner_blank, null);
            TextView labelSpinnerDefaultBlank = (TextView)convertView.findViewById(R.id.labelSpinnerDefaultBlank);
            labelSpinnerDefaultBlank.setText(language.getLabelSelect()+"");
        }
        else
        {
            convertView = mInflater.inflate(resourceId, null);

            TextView labelListItemCountryName = (TextView)convertView.findViewById(R.id.labelListItemCountryName);
            labelListItemCountryName.setText(listOfBuheneart.get(position - 1).getBezeichnung());
        }
        return convertView;
    }
}
