package de.mateco.integrAMobile.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.LogApp;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.CustomerSearchPagingRequestModel;
import de.mateco.integrAMobile.model.HintModel;

/**
 * Created by kadir on 23.08.2015.
 */
public class AutoCompleteSearchAdapter extends BaseAdapter implements Filterable {

    private static final int MAX_RESULTS = 10;
    private Context mContext;
    private List<HintModel> arraylistHint;

    public AutoCompleteSearchAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return arraylistHint.size();
    }

    @Override
    public HintModel getItem(int index) {
        return arraylistHint.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.auto_complete_item, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.txtAutoitem)).setText(getItem(position).getHint());
        //((TextView) convertView.findViewById(R.id.tv_id_product_ac_barcode)).setText(getItem(position).getBarcode());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    findHintsFromApi(mContext, constraint.toString());

                    // Assign the data to the FilterResults
                    filterResults.values = arraylistHint;
                    filterResults.count = arraylistHint.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    arraylistHint = (List<HintModel>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }

    /**
     * Returns a search result for the given book title.
     */
    private void findHintsFromApi(final Context context, String search) {
        // GoogleBooksProtocol is a wrapper for the Google Books API
        //GoogleBooksProtocol protocol = new GoogleBooksProtocol(context, MAX_RESULTS);
        arraylistHint = new ArrayList<HintModel>();
        String base64Data = DataHelper.getToken();

        try {
             int pageNuber = 1;
             int customerCount = 0;
             int totalPageCount = 1;
             int pageSize = 10;

            String customerNo ="";
            String kaNr = "";
            String matchCode = search;
            String name1 = "";
            String road = "";
            String zipCode = "";
            String place = "";
            String telePhone = "";


                CustomerSearchPagingRequestModel customerSearch = new CustomerSearchPagingRequestModel();
                customerSearch.setKundenNr(customerNo.trim());
                customerSearch.setKaNr(kaNr.trim());
                customerSearch.setMatchCode(matchCode.trim());
                customerSearch.setName1(name1.trim());
                customerSearch.setStrasse(road.trim());
                customerSearch.setPLZ(zipCode.trim());
                customerSearch.setOrt(place.trim());
                customerSearch.setTelefon(telePhone.trim());
                customerSearch.setPageSize(pageSize + "");
                customerSearch.setPageNumber(pageNuber + "");
                customerSearch.setMitarbeiter("");

                pageNuber = 1;
                //Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                String jsonToSend = DataHelper.getGson().toJson(customerSearch);

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, DataHelper.URL_CUSTOMER_HELPER+"customersearchhint/token=" + URLEncoder.encode(base64Data.trim(), "UTF-8")
                    + "/customersearch=" + URLEncoder.encode(jsonToSend, "UTF-8"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Log.d(MainActivity.TAG, response.toString());

                            try {
                                // TODO: Json respond check status?
                                // Parsing json array response
                                // loop through each json object
                                //JSONArray jsonProducts = response.getJSONArray("Results");
                                JSONObject jsonObject = new JSONObject(response.toString());
                                JSONArray resultsOfCustomers = jsonObject.getJSONArray("Result");
                                TypeToken<List<HintModel>> token = new TypeToken<List<HintModel>>() {};
                                //String jsonStringArray = "[\"JSON\",\"To\",\"Java\"]";
                                arraylistHint = new Gson().fromJson(resultsOfCustomers.toString(),token.getType());
                                LogApp.showLog(" test "," hint list size : "+ arraylistHint.size());
                                //mDepartmentArrayAdapter = new DepartmentArrayAdapter(getActivity(), R.layout.list_item_spinner_activity_topic, hintList);
                                //textCustomerSearchMatchCode.setAdapter(mDepartmentArrayAdapter);
                                //mDepartmentArrayAdapter = new DepartmentArrayAdapter(getActivity(), R.layout.list_item_spinner_activity_topic, hintList);
                                //textCustomerSearchMatchCode.setAdapter(mDepartmentArrayAdapter);

                            /*for (int i = 0; i < jsonProducts.length(); i++) {
                                JSONObject p = (JSONObject) jsonProducts.get(i);
                                String name = p.getString("name");
                                String barcode = p.getString("barcode");


                                //arraylistHint.add(new HintModel(name, barcode));
                            }*/
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            Volley.newRequestQueue(this.mContext).add(req);
        }
        catch (Exception e){
            Toast.makeText(context,"", Toast.LENGTH_SHORT).show();
        }

    }
}
