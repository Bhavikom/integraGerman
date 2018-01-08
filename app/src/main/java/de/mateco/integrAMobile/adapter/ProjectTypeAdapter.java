package de.mateco.integrAMobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.ProjectTypeModel;

public class ProjectTypeAdapter extends BaseAdapter
{
    private int resourceId;
    private ArrayList<ProjectTypeModel> listOfProjectType;
    private Context context;
    private Language language;

    public ProjectTypeAdapter(Context context, ArrayList<ProjectTypeModel> listOfProjectType,
                              int resourceId, Language language)
    {
        this.context = context;
        this.listOfProjectType = listOfProjectType;
        this.resourceId = resourceId;
        this.language = language;
    }

    @Override
    public int getCount() {
        return listOfProjectType.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return listOfProjectType.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        //return super.getDropDownView(position, convertView, parent);
        View v = getView(position, convertView, parent);
        return v;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
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
            labelListItemCountryName.setText(listOfProjectType.get(position - 1).getProjectTypeDesignation());
        }
        //imgvBackground.setImageResource(R.drawable.english);

        return convertView;
    }
}
