package de.mateco.integrAMobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.mateco.integrAMobile.R;

/**
 * language spinenr adapter
 */
public class SpinnerLanguageAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<String> languageName;
    private ArrayList<String> languageImageId;

    public SpinnerLanguageAdapter(Context context, ArrayList<String> languageName,ArrayList<String> languageImageId)
    {
        this.context = context;
        this.languageName = languageName;
        this.languageImageId = languageImageId;
    }

    @Override
    public int getCount() {
        return languageName.size();
    }

    @Override
    public Object getItem(int i) {
        return languageName.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View v = getView(position, convertView, parent);
        return v;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup)
    {
        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.list_item_spinner_language, null);
		
        TextView txtvSpinnerLanguageName = (TextView)convertView.findViewById(R.id.txtvSpinnerLanguageName);
        txtvSpinnerLanguageName.setText(languageName.get(i));

        txtvSpinnerLanguageName.setCompoundDrawablesWithIntrinsicBounds(context.getResources().
                getIdentifier(languageImageId.get(i), "drawable", context.getPackageName()), 0, 0, 0);
        //imgvBackground.setImageResource(R.drawable.english);

        return convertView;
    }
}
