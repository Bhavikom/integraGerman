package de.mateco.integrAMobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.NavigationChild;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.base.MatecoPriceApplication;

public class ExpandableListDrawerAdapter extends BaseExpandableListAdapter
{
    private ArrayList<NavigationChild> parentItems;
    private ArrayList<String> tempChild;
    private ArrayList<ArrayList<String>> Childtem = new ArrayList<ArrayList<String>>();
    private LayoutInflater minflater;
    private Activity activity;
    private final Context context;
    private static final int[] EMPTY_STATE_SET = {};
    private static final int[] GROUP_EXPANDED_STATE_SET =
            {android.R.attr.state_expanded};
    private static final int[][] GROUP_STATE_SETS = {
            EMPTY_STATE_SET, // 0
            GROUP_EXPANDED_STATE_SET // 1
    };
    private MatecoPriceApplication matecoPriceApplication;

    public ExpandableListDrawerAdapter(Context context, ArrayList<NavigationChild> parentItems,
           ArrayList<ArrayList<String>> childItem, MatecoPriceApplication matecoPriceApplication)
    {
        this.context = context;
        this.parentItems = parentItems;
        this.Childtem = childItem;
        this.matecoPriceApplication = matecoPriceApplication;
    }

    public void setInflater(LayoutInflater mInflater, Activity act)
    {
        this.minflater = mInflater;
        activity = act;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent)
    {
        tempChild = Childtem.get(groupPosition);

        if (convertView == null)
        {
            convertView = minflater.inflate(R.layout.list_item_nd_elv_child, null);
        }
        TextView text = (TextView) convertView.findViewById(R.id.txtv_li_nd_elv_Child);
        text.setText(tempChild.get(childPosition));
        //Log.e("user at adapter", groupPosition + " " + childPosition);
        if(!matecoPriceApplication.isCustomerLoaded(DataHelper.isCustomerLoaded, false) && groupPosition != 1)
        {
            //Log.e("user", groupPosition + " " + childPosition);
            convertView.setBackgroundColor(context.getResources().getColor(R.color.gray));
        }
        else if(!matecoPriceApplication.isCustomerLoaded(DataHelper.isCustomerLoaded, false) &&
                (groupPosition == 1 && childPosition != 0) && (groupPosition == 1 && childPosition != 1))
        {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.gray));
        }
        else
        {
            convertView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }
//		convertView.setOnClickListener(new View.OnClickListener()
//        {
//			@Override
//			public void onClick(View v)
//            {
//				Toast.makeText(activity, tempChild.get(childPosition), Toast.LENGTH_SHORT).show();
//			}
//		});
        convertView.setTag(tempChild.get(childPosition));
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return (Childtem.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return null;
    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = minflater.inflate(R.layout.list_item_nd_elv_parent, null);
        }
        TextView text = (TextView) convertView.findViewById(R.id.txtv_li_nd_elv_Parent);
        text.setText(parentItems.get(groupPosition).getName());
        text.setCompoundDrawablesWithIntrinsicBounds(parentItems.get(groupPosition).getRespurceId(), 0, 0, 0);
        View ind = convertView.findViewById( R.id.explist_indicator);
        if( ind != null ) {
            ImageView indicator = (ImageView)ind;
            if( getChildrenCount( groupPosition ) == 0 ) {
                indicator.setVisibility( View.INVISIBLE );
            } else {
                indicator.setVisibility( View.VISIBLE );
                int stateSetIndex = ( isExpanded ? 1 : 0) ;
                Drawable drawable = indicator.getDrawable();
                drawable.setState(GROUP_STATE_SETS[stateSetIndex]);
            }
        }
        //Log.e("user at adapter", groupPosition + " ");
        if(!matecoPriceApplication.isCustomerLoaded(DataHelper.isCustomerLoaded, false) && groupPosition != 1)
        {
            //Log.e("user", groupPosition + " ");
            convertView.setBackgroundColor(context.getResources().getColor(R.color.gray));
        }
        else
        {
            convertView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
