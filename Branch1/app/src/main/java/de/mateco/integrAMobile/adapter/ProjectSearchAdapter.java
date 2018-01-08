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
import de.mateco.integrAMobile.model.ProjectModel;

public class ProjectSearchAdapter extends BaseAdapter
{
    private List<ProjectModel> listOfProjects = new ArrayList<ProjectModel>();
    SimpleDateFormat input_format = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat output_format = new SimpleDateFormat("dd.MM.yyyy");
    private Context context;
    private LayoutInflater layoutInflater;
    public int selectedIndex = -1;

    public void setSelectedIndex(int index) {
        selectedIndex = index;
        notifyDataSetChanged();
    }

    public ProjectSearchAdapter(Context context, ArrayList<ProjectModel> listOfProjects) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.listOfProjects = listOfProjects;
    }

    @Override
    public int getCount() {
        return listOfProjects.size();
    }

    @Override
    public ProjectModel getItem(int position) {
        return listOfProjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_project_search, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.labelListItemProjectSearchMatchCode =
                    (TextView) convertView.findViewById(R.id.labelListItemProjectSearchMatchCode);
            viewHolder.labelListItemProjectSearchDescription =
                    (TextView) convertView.findViewById(R.id.labelListItemProjectSearchDescription);
            viewHolder.labelListItemProjectSearchTypeOfProject =
                    (TextView) convertView.findViewById(R.id.labelListItemProjectSearchTypeOfProject);
            viewHolder.labelListItemProjectSearchStreetAddress =
                    (TextView) convertView.findViewById(R.id.labelListItemProjectSearchStreetAddress);
            viewHolder.labelListItemProjectSearchPlace =
                    (TextView) convertView.findViewById(R.id.labelListItemProjectSearchPlace);
            viewHolder.labelListItemProjectSearchFrom =
                    (TextView) convertView.findViewById(R.id.labelListItemProjectSearchFrom);
            viewHolder.labelListItemProjectSearchTo =
                    (TextView) convertView.findViewById(R.id.labelListItemProjectSearchTo);
            viewHolder.labelListItemProjectSearchEmployee =
                    (TextView) convertView.findViewById(R.id.labelListItemProjectSearchEmployee);

            convertView.setTag(viewHolder);
        }
        initializeViews((ProjectModel)getItem(position), (ViewHolder) convertView.getTag(),
                position, convertView);
        return convertView;
    }

    private void initializeViews(ProjectModel object, ViewHolder holder, int position, View convertView)
    {
        holder.labelListItemProjectSearchMatchCode.setText(object.getMatchCode());
        holder.labelListItemProjectSearchDescription.setText(object.getBeschreibung());
        holder.labelListItemProjectSearchTypeOfProject.setText(object.getProjektart());
        holder.labelListItemProjectSearchStreetAddress.setText(object.getAdresse());
        holder.labelListItemProjectSearchPlace.setText(object.getOrt());
        if(!object.getBeginn().equals(""))
            holder.labelListItemProjectSearchFrom.setText
                    (DataHelper.getDateFormatFromString(object.getBeginn(), output_format, input_format));
        if(!object.getEnde().equals(""))
            holder.labelListItemProjectSearchTo.setText
                    (DataHelper.getDateFormatFromString(object.getEnde(), output_format, input_format));
        holder.labelListItemProjectSearchEmployee.setText(object.getMitarbeiter());

        if(selectedIndex == position)
        {
            holder.labelListItemProjectSearchMatchCode.setTextColor
                    (context.getResources().getColor(R.color.white));
            holder.labelListItemProjectSearchDescription.setTextColor
                    (context.getResources().getColor(R.color.white));
            holder.labelListItemProjectSearchTypeOfProject.setTextColor
                    (context.getResources().getColor(R.color.white));
            holder.labelListItemProjectSearchStreetAddress.setTextColor
                    (context.getResources().getColor(R.color.white));
            holder.labelListItemProjectSearchPlace.setTextColor
                    (context.getResources().getColor(R.color.white));
            holder.labelListItemProjectSearchFrom.setTextColor
                    (context.getResources().getColor(R.color.white));
            holder.labelListItemProjectSearchTo.setTextColor
                    (context.getResources().getColor(R.color.white));
            holder.labelListItemProjectSearchEmployee.setTextColor
                    (context.getResources().getColor(R.color.white));
            convertView.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        else
        {
            holder.labelListItemProjectSearchMatchCode.setTextColor
                    (context.getResources().getColor(R.color.black));
            holder.labelListItemProjectSearchDescription.setTextColor
                    (context.getResources().getColor(R.color.black));
            holder.labelListItemProjectSearchTypeOfProject.setTextColor
                    (context.getResources().getColor(R.color.black));
            holder.labelListItemProjectSearchStreetAddress.setTextColor
                    (context.getResources().getColor(R.color.black));
            holder.labelListItemProjectSearchPlace.setTextColor
                    (context.getResources().getColor(R.color.black));
            holder.labelListItemProjectSearchFrom.setTextColor
                    (context.getResources().getColor(R.color.black));
            holder.labelListItemProjectSearchTo.setTextColor
                    (context.getResources().getColor(R.color.black));
            holder.labelListItemProjectSearchEmployee.setTextColor
                    (context.getResources().getColor(R.color.black));

            convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    protected class ViewHolder {
        private TextView labelListItemProjectSearchMatchCode;
        private TextView labelListItemProjectSearchDescription;
        private TextView labelListItemProjectSearchTypeOfProject;
        private TextView labelListItemProjectSearchStreetAddress;
        private TextView labelListItemProjectSearchPlace;
        private TextView labelListItemProjectSearchFrom;
        private TextView labelListItemProjectSearchTo;
        private TextView labelListItemProjectSearchEmployee;
    }
}
