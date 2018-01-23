package de.mateco.integrAMobile.Helper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.Language;

public class GooglePlacesAutocompleteAdapter extends BaseAdapter implements Filterable
{
    private ArrayList resultList;
    private Context context;
    private Language language;

    public GooglePlacesAutocompleteAdapter(Context context, Language language)
    {
        this.context = context;
        resultList = new ArrayList();
        this.language = language;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Object getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.list_item_spinner_country, null);
        TextView labelListItemCountryName = (TextView)convertView.findViewById(R.id.labelListItemCountryName);
        if(resultList != null && resultList.size() > 0)
        {
            labelListItemCountryName.setText(resultList.get(position).toString());
        }


        return convertView;
    }

    @Override
    public Filter getFilter()
    {
        Filter filter = new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence constraint)
            {
                FilterResults filterResults = new FilterResults();
                if(constraint != null)
                {
                    resultList = autocomplete(constraint.toString());

                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results)
            {
                if(results != null && results.count > 0)
                {
                    notifyDataSetChanged();
                }
                else
                {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    public ArrayList autocomplete(String input)
    {
        ArrayList resultList = null;
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try
        {
            StringBuilder sb = new StringBuilder(DataHelper.PLACES_API_BASE + DataHelper.TYPE_AUTOCOMPLETE + DataHelper.OUT_JSON);
            sb.append("?key=" + DataHelper.API_KEY);
            sb.append("&language=" + language.getLangCode());
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));
            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1)
            {
                jsonResults.append(buff, 0, read);
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            return resultList;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return resultList;
        }
        finally
        {
            if (conn != null)
            {
                conn.disconnect();
            }
        }

        try
        {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
            // Extract the Place descriptions from the results
            resultList = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++)
            {
                LogApp.showLog("json array", predsJsonArray.getJSONObject(i).getString("description"));
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return resultList;
    }
}
