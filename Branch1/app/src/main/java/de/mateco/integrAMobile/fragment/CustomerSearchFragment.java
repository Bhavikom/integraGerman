package de.mateco.integrAMobile.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.PreferencesClass;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.CustomerSearchResultAdapter;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.ContactPersonModel;
import de.mateco.integrAMobile.model.CustomerActivityModel;
import de.mateco.integrAMobile.model.CustomerCompletedOrderModel;
import de.mateco.integrAMobile.model.CustomerDatabaseModel;
import de.mateco.integrAMobile.model.CustomerFullDetailModel;
import de.mateco.integrAMobile.model.CustomerLostSaleDataModel;
import de.mateco.integrAMobile.model.CustomerModel;
import de.mateco.integrAMobile.model.CustomerOfferModel;
import de.mateco.integrAMobile.model.CustomerOpenOfferModel;
import de.mateco.integrAMobile.model.CustomerOpenOrderModel;
import de.mateco.integrAMobile.model.CustomerProjectModel;
import de.mateco.integrAMobile.model.CustomerSearchPagingRequestModel;
import de.mateco.integrAMobile.model.EmployeeModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LoginPersonModel;

public class CustomerSearchFragment extends BaseFragment implements TextView.OnEditorActionListener
{
    String loginUserNumber="";
    PreferencesClass preferences2;
    private View rootView;
    private Language language;
    private MatecoPriceApplication matecoPriceApplication;
    private EditText textCustomerSearchCustomerNo, textCustomerSearchKanr,textCustomerSearchMatchCode,
            textCustomerSearchName1,textCustomerSearchRoad, textCustomerSearchZipCode,textCustomerSearchPlace, textCustomerSearchTel;
    private ListView listCustomerSearchResult;
    private ArrayList<CustomerModel> listOfCustomerSearchResult;
    private ArrayList<CustomerModel> listOfCustomerSearchResultTemp;
    private CustomerSearchResultAdapter adapter;
    private Menu menu;
    private DataBaseHandler db;
    private int pageNuber = 1;
    private int customerCount = 0;
    private int totalPageCount = 1;
    private int pageSize = 10;
    private View footerView;
    //private boolean loadingMore = false;
    private Button footerButton;
    CheckBox checkBoxCustomer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        /*if(rootView == null)
        {
            Log.e("rootview", "null");
            rootView = inflater.inflate(R.layout.fragment_customer_search, container, false);
            super.initializeFragment(rootView);
        }
        else
        {
            if(rootView.getParent() != null)
                ((ViewGroup)rootView.getParent()).removeView(rootView);
            Log.e("rootview", " not null");
        }*/

        rootView = inflater.inflate(R.layout.fragment_customer_search, container, false);
        super.initializeFragment(rootView);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelCustomerSearch());
        return rootView;
    }

    @Override
    public void initializeComponents(View rootView)
    {
        Log.e("initializeComponents", "initializeComponents");
        preferences2 = new PreferencesClass(getActivity());
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();

        language = matecoPriceApplication.getLanguage();
        db = new DataBaseHandler(getActivity());
        getActivity().invalidateOptionsMenu();
//        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelSearch());
            setHasOptionsMenu(true);

        listOfCustomerSearchResultTemp = new ArrayList<>();

        checkBoxCustomer=(CheckBox)rootView.findViewById(R.id.checkBoxCustomer);
        checkBoxCustomer.setChecked(false);
        checkBoxCustomer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    loginUserNumber = matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber();
                    if(listOfCustomerSearchResult.size() > 0){
                        listOfCustomerSearchResultTemp.clear();
                        listOfCustomerSearchResultTemp.addAll(listOfCustomerSearchResult);
                        listOfCustomerSearchResult.clear();
                        if(listOfCustomerSearchResultTemp.size() > 0){
                            for (int i=0;i<listOfCustomerSearchResultTemp.size();i++){
                                if(!TextUtils.isEmpty(listOfCustomerSearchResultTemp.get(i).getMitarbeiter())){
                                    if(listOfCustomerSearchResultTemp.get(i).getMitarbeiter().equalsIgnoreCase(loginUserNumber)){
                                        listOfCustomerSearchResult.add(listOfCustomerSearchResultTemp.get(i));
                                    }
                                }

                            }
                        }
                        adapter.notifyDataSetChanged();

                    }

                }else {
                    loginUserNumber = "";
                    if(listOfCustomerSearchResultTemp.size() > 0){
                        listOfCustomerSearchResult.clear();
                        listOfCustomerSearchResult.addAll(listOfCustomerSearchResultTemp);
                        listOfCustomerSearchResultTemp.clear();
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        listCustomerSearchResult = (ListView)rootView.findViewById(R.id.listCustomerSearchResult);
        textCustomerSearchCustomerNo = (EditText)rootView.findViewById(R.id.textCustomerSearchCustomerNo);
        textCustomerSearchKanr = (EditText)rootView.findViewById(R.id.textCustomerSearchKanr);
        textCustomerSearchMatchCode = (EditText)rootView.findViewById(R.id.textCustomerSearchMatchCode);
        textCustomerSearchName1 = (EditText)rootView.findViewById(R.id.textCustomerSearchName1);
        textCustomerSearchRoad = (EditText)rootView.findViewById(R.id.textCustomerSearchRoad);
        textCustomerSearchZipCode = (EditText)rootView.findViewById(R.id.textCustomerSearchZipCode);
        textCustomerSearchPlace = (EditText)rootView.findViewById(R.id.textCustomerSearchPlace);
        textCustomerSearchTel = (EditText)rootView.findViewById(R.id.textCustomerSearchTel);
        listOfCustomerSearchResult = new ArrayList<>();

        adapter = new CustomerSearchResultAdapter(getActivity(), listOfCustomerSearchResult);
        footerView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_list_button, null, false);
        footerButton = (Button)footerView.findViewById(R.id.buttonLoadMore);
        pageSize = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).getInt("CustomerPageSize", 10);
        setUpLanguage();
        super.initializeComponents(rootView);
    }

    @Override
    public void bindEvents(View rootView)
    {
        textCustomerSearchCustomerNo.setOnEditorActionListener(this);
        textCustomerSearchKanr.setOnEditorActionListener(this);
        textCustomerSearchMatchCode.setOnEditorActionListener(this);
        textCustomerSearchName1.setOnEditorActionListener(this);
        textCustomerSearchRoad.setOnEditorActionListener(this);
        textCustomerSearchZipCode.setOnEditorActionListener(this);
        textCustomerSearchPlace.setOnEditorActionListener(this);
        textCustomerSearchTel.setOnEditorActionListener(this);
        //listCustomerSearchResult.setOnScrollListener(this);
        listCustomerSearchResult.setAdapter(adapter);
        listCustomerSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                //listCustomerSearchResult.setSelection(position);
                adapter.setSelectedIndex(position);
                if (getArguments() == null) {
                    menu.findItem(R.id.actionForward).setVisible(true);
                } else {
                    menu.findItem(R.id.actionRight).setVisible(true);
                    if (!(adapter.selectedIndex == -1)) {
                        getCustomerDetails(adapter.selectedIndex);
                    } else {
                        showShortToast(language.getMessageSelectCustomerFirst());
                    }
                }
                //menu.findItem(R.id.actionAdd).setVisible(true);
            }
        });

        footerButton.setText(language.getMessageLoadMoreData());
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchForNewPage();
            }
        });

        super.bindEvents(rootView);
    }

    private void setUpLanguage()
    {
        TextView labelCustomerSearchCustomerNo, labelCustomerSearchKanr, labelCustomerSearchMatchCode,labelCustomerSearchName1,
                labelCustomerSearchRoad, labelCustomerSearchZipCode, labelCustomerSearchPlace, labelCustomerSearchTel;

        labelCustomerSearchCustomerNo = (TextView)rootView.findViewById(R.id.labelCustomerSearchCustomerNo);
        labelCustomerSearchKanr = (TextView)rootView.findViewById(R.id.labelCustomerSearchKanr);
        labelCustomerSearchMatchCode = (TextView)rootView.findViewById(R.id.labelCustomerSearchMatchCode);
        labelCustomerSearchName1 = (TextView)rootView.findViewById(R.id.labelCustomerSearchName1);
        labelCustomerSearchRoad = (TextView)rootView.findViewById(R.id.labelCustomerSearchRoad);
        labelCustomerSearchZipCode = (TextView)rootView.findViewById(R.id.labelCustomerSearchZipCode);
        labelCustomerSearchPlace = (TextView)rootView.findViewById(R.id.labelCustomerSearchPlace);
        labelCustomerSearchTel = (TextView)rootView.findViewById(R.id.labelCustomerSearchTel);

        // commented on 26th june 2017
        /*checkBoxCustomer.setText(language.getLabelCustomervon()+" "
        +matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserName());*/

        /* replace above code  with below code on 26th june 2017 */
        ArrayList<EmployeeModel> listOfEmployee = new ArrayList<>();
        String loginPersonId = matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new ArrayList<LoginPersonModel>().toString()).get(0).getUserNumber();
        listOfEmployee = db.getEmployees();
        for(int i = 0; i < listOfEmployee.size(); i++) {
            if (listOfEmployee.get(i).getMitarbeiter().equals(loginPersonId)) {
                checkBoxCustomer.setText(language.getLabelCustomervon()+" "
                        +listOfEmployee.get(i).getNachname()+", "+listOfEmployee.get(i).getVorname());
                break;
            }
        }

        labelCustomerSearchCustomerNo.setText(language.getLabelCustomerNo());
        labelCustomerSearchKanr.setText(language.getLabelKanr());
        labelCustomerSearchMatchCode.setText(language.getLabelMatchCode());
        labelCustomerSearchName1.setText(language.getLabelName() + " 1");
        labelCustomerSearchRoad.setText(language.getLabelRoad());
        labelCustomerSearchZipCode.setText(language.getLabelZipCode());
        labelCustomerSearchPlace.setText(language.getLabelPlace());
        labelCustomerSearchTel.setText(language.getLabelTelephone());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);
        this.menu = menu;
        menu.findItem(R.id.actionWrong).setVisible(false);
        menu.findItem(R.id.actionRight).setVisible(false);
        menu.findItem(R.id.actionEdit).setVisible(false);
        menu.findItem(R.id.actionAdd).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);
        if(getArguments() == null)
        {
            menu.findItem(R.id.actionBack).setVisible(false);
        }
        else
        {
            menu.findItem(R.id.actionBack).setVisible(true);
        }

        //return true;
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.actionSettings:
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new SettingFragment(),"Setting");
                //transaction.addToBackStack(SettingFragment.Tag);
                transaction.addToBackStack("Setting");
                transaction.commit();
                return true;
            case R.id.actionBack:
                if (getFragmentManager().getBackStackEntryCount() == 0)
                {
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.replace(R.id.content_frame, new HomeFragment());
                    getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    transaction.commit();
                }
                else
                {
                    getFragmentManager().popBackStack();
                }
                return true;
            case R.id.actionForward:

                if(!(adapter.selectedIndex == -1))
                {
                    preferences2.clearPreferences();
                    getCustomerDetails(adapter.selectedIndex);
                }
                else
                {
                    showShortToast(language.getMessageSelectCustomerFirst());
                }
                return true;
            case R.id.actionAdd:
                    //loadCustomer();
                    menu.findItem(R.id.actionForward).setVisible(true);
                return true;
            case R.id.actionSearch:
                    searchForResults();
                return true;
            case R.id.actionRight:
                if(getArguments().containsKey("ProjectCustomer"))
                {
                   getActivity().getSupportFragmentManager().popBackStack();
                }
                else if(getArguments().containsKey("TradeObject"))
                {
                    Fragment fragment = new ProjectDetailFragment();
                    Bundle args = new Bundle();
                    args.putParcelable("TradeObject",getArguments().getParcelable("TradeObject"));
                    fragment.setArguments(args);
                    transaction.replace(R.id.content_frame, fragment);
                    getActivity().getSupportFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
                    transaction.addToBackStack("Customer Data");
                    transaction.commit();
                }
                else
                {
                    Fragment fragment = new SiteInspectionNewFragment();
                    transaction.replace(R.id.content_frame, fragment);
                    getActivity().getSupportFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
                    transaction.addToBackStack("Customer Data");
                    transaction.commit();
                }

            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    private void searchForResults()
    {
        matecoPriceApplication.hideKeyboard(getActivity());
        listOfCustomerSearchResult.clear();
        listOfCustomerSearchResultTemp.clear();
        adapter.setSelectedIndex(-1);
        adapter.notifyDataSetChanged();
        String customerNo = textCustomerSearchCustomerNo.getText().toString().trim();
        String kaNr = textCustomerSearchKanr.getText().toString().trim();
        String matchCode = textCustomerSearchMatchCode.getText().toString().trim();
        String name1 = textCustomerSearchName1.getText().toString().trim();
        String road = textCustomerSearchRoad.getText().toString().trim();
        String zipCode = textCustomerSearchZipCode.getText().toString().trim();
        String place = textCustomerSearchPlace.getText().toString().trim();
        String telePhone = textCustomerSearchTel.getText().toString().trim();

        if(customerNo.length() > 0 || kaNr.length() > 0 || matchCode.length() > 0 || name1.length() > 0 ||
                road.length() > 0 || zipCode.length() > 0 || place.length() > 0 || telePhone.length() > 0)
        {
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
            customerSearch.setPageNumber(pageNuber+"");
            customerSearch.setMitarbeiter(loginUserNumber);
            listCustomerSearchResult.removeFooterView(footerView);
            pageNuber = 1;
            String jsonToSend = new Gson().toJson(customerSearch);
            Log.e("customer to json", jsonToSend);
            String base64Data = DataHelper.getToken();
            String url = "";
            try
            {
               /* url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.CUSTOMER_SEARCH_PAGING
                        + "?token=" + URLEncoder.encode(base64Data.trim(), "UTF-8")
                        + "&customersearch=" + URLEncoder.encode(jsonToSend, "UTF-8");*/
                url = DataHelper.URL_CUSTOMER_HELPER+"customersearchpaging/token=" + URLEncoder.encode(base64Data.trim(), "UTF-8")
                        + "/customersearch=" + URLEncoder.encode(jsonToSend, "UTF-8");

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            Log.e("url at customer search", url);
            if(DataHelper.isNetworkAvailable(getActivity()))
            {
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        Log.e("result ", result);
                        if(result.equals("error"))
                        {
                            showLongToast(language.getMessageError());
                        }
                        else
                        {
                            try
                            {
                                JSONObject jsonObject = new JSONObject(result);
                                customerCount = jsonObject.getInt("CustomerCount");
                                totalPageCount = jsonObject.getInt("PageCount");
                                pageNuber = jsonObject.getInt("PageNumber");
                                pageSize = jsonObject.getInt("PageSize");
                                JSONArray resultsOfCustomers = jsonObject.getJSONArray("Result");
                                if(pageNuber < totalPageCount)
                                {
                                    listCustomerSearchResult.addFooterView(footerView);
                                }
                                if(resultsOfCustomers.length() == 0)
                                {
                                            showLongToast(language.getMessageNoResultFound());
                                }
                                else
                                {
                                    CustomerModel.extractFromJson(
                                            resultsOfCustomers.toString(), listOfCustomerSearchResult);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                    }
                };
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                asyncTask.execute();
            }
            else
            {
                listOfCustomerSearchResult.clear();
                showLongToast(language.getMessageNetworkNotAvailable());
                listOfCustomerSearchResult.addAll(db.getCustomer(customerSearch));
                Log.e("clear", listOfCustomerSearchResult.size()+"");
                adapter.notifyDataSetChanged();
//                if(listOfCustomerSearchResult.size() > 20)
//                {
//                    showShortToast(language.getMessageSearchQueryBroad());
//                }
            }
        }
        else
        {
            showLongToast(language.getMessageInputSearchParameter());
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        if (actionId == EditorInfo.IME_ACTION_SEARCH)
        {
            searchForResults();
            return true;
        }
        return false;
    }

    private void getCustomerDetails(int selectedIndex)
    {
        if(DataHelper.isNetworkAvailable(getActivity()))
        {
            try
            {
                DataBaseHandler db = new DataBaseHandler(getActivity());
                db.deleteKANR();
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        Log.e("result",result);
                        if(result.equals("error"))
                        {
                            showShortToast(language.getMessageError());
                        }
                        else
                        {
                            try
                            {
                                CustomerFullDetailModel customerFullDetail = new Gson().fromJson(result, CustomerFullDetailModel.class);
                                String json = new Gson().toJson(customerFullDetail.getCustomerSearchList());
                                matecoPriceApplication.clearPricingData();
                                matecoPriceApplication.saveLoadedCustomer(DataHelper.LoadedCustomer, json);
                                Log.e("customer detail", customerFullDetail.getCustomerSearchList().toString());
                                String listOfContactPerson = new Gson().toJson(customerFullDetail.getCustomerContactPersonList());
                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerContactPerson, listOfContactPerson);

                                String listOfActivity = new Gson().toJson(customerFullDetail.getCustomerActivityListt().getListOfActivities());
                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerActivity, listOfActivity);

                                String listOfProject = new Gson().toJson(customerFullDetail.getCustomerActivityListt().getListOfProject());
                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerProject, listOfProject);

                                String listOfOffer = new Gson().toJson(customerFullDetail.getCustomerActivityListt().getListOfOffers());
                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerOffer, listOfOffer);

                                String listOfLostSale = new Gson().toJson(customerFullDetail.getCustomerLostSaleList());
                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerLostSale, listOfLostSale);

                                String listOfOpenSpecials = new Gson().toJson(customerFullDetail.getCustomerOpenSpecialsList());
                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerOpenSpecials, listOfOpenSpecials);

                                String listOfOpenOrder = new Gson().toJson(customerFullDetail.getCustomerOpenOrderList());
                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerOpenOrders, listOfOpenOrder);

                                String listOfCompletedOrder = new Gson().toJson(customerFullDetail.getCustomerCompletedOrdersList());
                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerCompletedOrders, listOfCompletedOrder);
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                if(getArguments()==null)
                                {
                                    ((HomeActivity)getActivity()).updateAdapter();
                                    Fragment fragment = new CustomerDataFragment();
                                    //Bundle bundle = new Bundle();
                                    //bundle.putParcelable("");
                                    //bundle.putParcelable("customerObject", listOfCustomerSearchResult.get(adapter.selectedIndex));
                                    //fragment.setArguments(bundle);
                                    transaction.replace(R.id.content_frame, fragment);
                                    //getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                    //transaction.addToBackStack(SettingFragment.Tag);
                                    transaction.addToBackStack("Customer Search");
                                    transaction.commit();
                                }
                                else
                                {
                                   /* Fragment fragment = new CustomerDataFragment1();
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("customerObject",listOfCustomerSearchResult.get(adapter.selectedIndex));
                                    fragment.setArguments(bundle);
                                    transaction.replace(R.id.content_frame, fragment);
                                    //getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                    transaction.addToBackStack("Customer Search");
                                    transaction.commit();*/
                                }
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                                showShortToast(language.getMessageErrorAtParsing());
                            }
                        }
                    }
                };

                String url = DataHelper.URL_AGENDA_HELPER + "agendacustomershow" //+ DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.AGENDA_CUSTOMER_SHOW
                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/fieldname=" + "Kontakt"
                        + "/value=" + listOfCustomerSearchResult.get(selectedIndex).getKontakt();
                Log.e("url", url);

                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                asyncTask.execute();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        else
        {
            showShortToast(language.getMessageNetworkNotAvailable());
            if(db.isCustomerAvailable(listOfCustomerSearchResult.get(selectedIndex).getKontakt()))
            {
                Gson gson = new Gson();
                CustomerDatabaseModel customerDatabaseModel = db.getCustomer(listOfCustomerSearchResult.get(selectedIndex).getKontakt());
                matecoPriceApplication.saveLoadedCustomer(DataHelper.LoadedCustomer, gson.toJson(customerDatabaseModel.getCustomerModel()));

                ArrayList<ContactPersonModel> listOfContactPersonList = new ArrayList<>();
                listOfContactPersonList.addAll(customerDatabaseModel.getListOfContactPerson());
                matecoPriceApplication.saveData(DataHelper.LoadedCustomerContactPerson, gson.toJson(listOfContactPersonList));

                ArrayList<CustomerActivityModel> listOfActivity = new ArrayList<>();
                listOfActivity.addAll(customerDatabaseModel.getListOfActivity());
                matecoPriceApplication.saveData(DataHelper.LoadedCustomerActivity, gson.toJson(listOfActivity));

                ArrayList<CustomerProjectModel> listOfProject = new ArrayList<>();
                listOfProject.addAll(customerDatabaseModel.getListOfProject());
                matecoPriceApplication.saveData(DataHelper.LoadedCustomerProject, gson.toJson(listOfProject));

                ArrayList<CustomerOfferModel> listOfOffer = new ArrayList<>();
                listOfOffer.addAll(customerDatabaseModel.getListOfOffer());
                matecoPriceApplication.saveData(DataHelper.LoadedCustomerOffer, gson.toJson(listOfOffer));

                ArrayList<CustomerOpenOrderModel> listOfOpenOrder = new ArrayList<>();
                listOfOpenOrder.addAll(customerDatabaseModel.getListOfOpenOrder());
                matecoPriceApplication.saveData(DataHelper.LoadedCustomerOpenOrders, gson.toJson(listOfOpenOrder));

                ArrayList<CustomerCompletedOrderModel> listOfCompletedOrder = new ArrayList<>();
                listOfCompletedOrder.addAll(customerDatabaseModel.getListOfCompletedOrder());
                matecoPriceApplication.saveData(DataHelper.LoadedCustomerCompletedOrders, gson.toJson(listOfCompletedOrder));

                ArrayList<CustomerOpenOfferModel> listOfOpenSpecials = new ArrayList<>();
                listOfOpenSpecials.addAll(customerDatabaseModel.getListOfOpenOffer());
                matecoPriceApplication.saveData(DataHelper.LoadedCustomerOffer, gson.toJson(listOfOpenSpecials));

                ArrayList<CustomerLostSaleDataModel> listOfLostSale = new ArrayList<>();
                listOfLostSale.addAll(customerDatabaseModel.getListOfLostSale());
                matecoPriceApplication.saveData(DataHelper.LoadedCustomerOffer, gson.toJson(listOfLostSale));

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                if(getArguments() == null)
                {
                    Fragment fragment = new CustomerDataFragment();
                    //Bundle bundle = new Bundle();
                    //bundle.putParcelable("");
                    //bundle.putParcelable("customerObject", listOfCustomerSearchResult.get(adapter.selectedIndex));
                    //fragment.setArguments(bundle);
                    transaction.replace(R.id.content_frame, fragment);
                    //getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    //transaction.addToBackStack(SettingFragment.Tag);
                    transaction.addToBackStack("Customer Search");
                    transaction.commit();
                }
                else
                {
                    Fragment fragment = new CustomerDataFragment1();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("customerObject",listOfCustomerSearchResult.get(adapter.selectedIndex));
                    fragment.setArguments(bundle);
                    transaction.replace(R.id.content_frame, fragment);
                    getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    transaction.addToBackStack("Customer Search");
                    transaction.commit();
                }
            }
            else
            {
                showShortToast(language.getMessageCustomerNotFound());
            }
        }
    }

//    private void loadCustomer(int selectedIndex)
//    {
//        Gson gson = new Gson();
//        String json = gson.toJson(listOfCustomerSearchResult.get(selectedIndex));
//        Log.e("json of selected", json);
//        if(DataHelper.isNetworkAvailable(getActivity()))
//        {
//            try
//            {
//                DataBaseHandler db = new DataBaseHandler(getActivity());
//                db.deleteKANR();
//                //matecoPriceApplication.clearData();
//                matecoPriceApplication.saveLoadedCustomer(DataHelper.LoadedCustomer, json);
//                final ProgressDialog prd;
//                prd = new ProgressDialog(getActivity());
//                prd.setTitle(language.getMessageWaitWhileLoading());
//                prd.setMessage(language.getMessageWaitWhileLoading());
//                prd.show();
//                final BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResultContactPersons = new BasicAsyncTaskGetRequest.OnAsyncResult()
//                {
//                    @Override
//                    public void OnAsynResult(String result)
//                    {
//                        Log.e("result of ", result);
//                        if(result.equals("error"))
//                        {
//                            prd.dismiss();
//                            showShortToast(language.getMessageError());
//                        }
//                        else
//                        {
//                            try
//                            {
//                                ArrayList<ContactPersonModel> listOfContactPersonList = new ArrayList<>();
//                                ContactPersonModel.extractFromJson(result, listOfContactPersonList);
//                                String json = new Gson().toJson(listOfContactPersonList);
//                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerContactPerson, json);
//
//                                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResultActivity = new BasicAsyncTaskGetRequest.OnAsyncResult()
//                                {
//                                    @Override
//                                    public void OnAsynResult(String result)
//                                    {
//                                        Log.e("result", result);
//                                        if(result.equals("error"))
//                                        {
//                                            prd.dismiss();
//                                            showShortToast(language.getMessageError());
//                                        }
//                                        else
//                                        {
//                                            try
//                                            {
//                                                CustomerActivityGetModel customerActivityGetModel = new Gson().fromJson(result, CustomerActivityGetModel.class);
//                                                //CustomerActivityGetModel.extractFromJson(result, listOfCustomerActivityDetails);
//                                                //customerActivityGetModel =
//                                                //Log.e("customerActivityGetMod", customerActivityGetModel.getListOfActivities().toString());
//                                                String listOfActivity = new Gson().toJson(customerActivityGetModel.getListOfActivities());
//                                                Log.e("listOfActivity", listOfActivity);
//                                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerActivity, listOfActivity);
//                                                String listOfProjects = new Gson().toJson(customerActivityGetModel.getListOfProject());
//                                                Log.e("listOfProjects", listOfProjects);
//                                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerProject, listOfProjects);
//                                                String listOfOffer = new Gson().toJson(customerActivityGetModel.getListOfOffers());
//                                                Log.e("listOfOffer", listOfOffer);
//                                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerOffer, listOfOffer);
//                                                //showShortToast("Customer Loaded");
//                                                showShortToast(language.getMessageCustomerLoaded());
//                                                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResultLostSales = new BasicAsyncTaskGetRequest.OnAsyncResult()
//                                                {
//                                                    @Override
//                                                    public void OnAsynResult(String result)
//                                                    {
//                                                        Log.e("result", result);
//                                                        if(result.equals("error"))
//                                                        {
//                                                            prd.dismiss();
//                                                            showShortToast(language.getMessageError());
//                                                        }
//                                                        else
//                                                        {
//                                                            try
//                                                            {
//                                                                ArrayList<CustomerLostSaleDataModel> listOfLostSale = new ArrayList<>();
//                                                                CustomerLostSaleDataModel.extractFromJson(result, listOfLostSale);
//                                                                String json = new Gson().toJson(listOfLostSale);
//                                                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerLostSale, json);
//
//                                                                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResultOpenSales = new BasicAsyncTaskGetRequest.OnAsyncResult()
//                                                                {
//                                                                    @Override
//                                                                    public void OnAsynResult(String result)
//                                                                    {
//                                                                        if(result.equals("error"))
//                                                                        {
//                                                                            prd.dismiss();
//                                                                            showShortToast(language.getMessageError());
//                                                                        }
//                                                                        else
//                                                                        {
//                                                                            try
//                                                                            {
//                                                                                ArrayList<CustomerOpenOfferModel> listOfOpenOffer = new ArrayList<>();
//                                                                                CustomerOpenOfferModel.extractFromJson(result, listOfOpenOffer);
//                                                                                String json = new Gson().toJson(listOfOpenOffer);
//                                                                                matecoPriceApplication.saveData(DataHelper.LoadedCustomerOpenSpecials, json);
//
//                                                                                if(matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKundenNr().equals(""))
//                                                                                {
//                                                                                    //showShortToast(language.getMessageCustomerNumberNotFound());
//
//                                                                                    ArrayList<CustomerOpenOrderModel> listOfOpenOrders = new ArrayList<>();
//                                                                                    String jsonOfOpenOrders = new Gson().toJson(listOfOpenOrders);
//                                                                                    matecoPriceApplication.saveData(DataHelper.LoadedCustomerOpenOrders, jsonOfOpenOrders);
//
//                                                                                    ArrayList<CustomerCompletedOrderModel> listOfCompletedOrders = new ArrayList<>();
//                                                                                    String jsonOfCompletedOrders = new Gson().toJson(listOfCompletedOrders);
//                                                                                    matecoPriceApplication.saveData(DataHelper.LoadedCustomerCompletedOrders, jsonOfCompletedOrders);
//
//                                                                                    prd.dismiss();
//                                                                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                                                                                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                                                                                    if(getArguments()==null)
//                                                                                    {
//                                                                                        ((HomeActivity)getActivity()).updateAdapter();
//                                                                                        Fragment fragment = new CustomerDataFragment();
//                                                                                        //Bundle bundle = new Bundle();
//                                                                                        //bundle.putParcelable("");
//                                                                                        //bundle.putParcelable("customerObject", listOfCustomerSearchResult.get(adapter.selectedIndex));
//                                                                                        //fragment.setArguments(bundle);
//                                                                                        transaction.replace(R.id.content_frame, fragment);
//                                                                                        //getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                                                                                        //transaction.addToBackStack(SettingFragment.Tag);
//                                                                                        transaction.addToBackStack("Customer Search");
//                                                                                        transaction.commit();
//                                                                                    }
//                                                                                    else
//                                                                                    {
//                                                                                        Fragment fragment = new CustomerDataFragment1();
//                                                                                        Bundle bundle = new Bundle();
//                                                                                        bundle.putParcelable("customerObject",listOfCustomerSearchResult.get(adapter.selectedIndex));
//                                                                                        fragment.setArguments(bundle);
//                                                                                        transaction.replace(R.id.content_frame, fragment);
//                                                                                        //getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                                                                                        transaction.addToBackStack("Customer Search");
//                                                                                        transaction.commit();
//                                                                                    }
//                                                                                }
//                                                                                else
//                                                                                {
//                                                                                    BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResultOpenOrder = new BasicAsyncTaskGetRequest.OnAsyncResult()
//                                                                                    {
//                                                                                        @Override
//                                                                                        public void OnAsynResult(String result)
//                                                                                        {
//                                                                                            Log.e("result of offer", result);
//                                                                                            if(result.equals("error"))
//                                                                                            {
//                                                                                                prd.dismiss();
//                                                                                                showShortToast(language.getMessageError());
//                                                                                            }
//                                                                                            else
//                                                                                            {
//                                                                                                try
//                                                                                                {
//                                                                                                    ArrayList<CustomerOpenOrderModel> listOfOpenOrders = new ArrayList<>();
//                                                                                                    CustomerOpenOrderModel.extractFromJson(result, listOfOpenOrders);
//                                                                                                    String json = new Gson().toJson(listOfOpenOrders);
//                                                                                                    matecoPriceApplication.saveData(DataHelper.LoadedCustomerOpenOrders, json);
//                                                                                                    BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResultCompleteOrder = new BasicAsyncTaskGetRequest.OnAsyncResult()
//                                                                                                    {
//                                                                                                        @Override
//                                                                                                        public void OnAsynResult(String result)
//                                                                                                        {
//                                                                                                            if(result.equals("error"))
//                                                                                                            {
//                                                                                                                prd.dismiss();
//                                                                                                                showShortToast(language.getMessageError());
//                                                                                                            }
//                                                                                                            else
//                                                                                                            {
//                                                                                                                try
//                                                                                                                {
//                                                                                                                    ArrayList<CustomerCompletedOrderModel> listOfOpenOrders = new ArrayList<>();
//                                                                                                                    CustomerCompletedOrderModel.extractFromJson(result, listOfOpenOrders);
//                                                                                                                    String json = new Gson().toJson(listOfOpenOrders);
//                                                                                                                    matecoPriceApplication.saveData(DataHelper.LoadedCustomerCompletedOrders, json);
//
//                                                                                                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                                                                                                                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                                                                                                                    prd.dismiss();
//                                                                                                                    if(getArguments()==null)
//                                                                                                                    {
//                                                                                                                        ((HomeActivity)getActivity()).updateAdapter();
//                                                                                                                        Fragment fragment = new CustomerDataFragment();
//                                                                                                                        //Bundle bundle = new Bundle();
//                                                                                                                        //bundle.putParcelable("");
//                                                                                                                        //bundle.putParcelable("customerObject", listOfCustomerSearchResult.get(adapter.selectedIndex));
//                                                                                                                        //fragment.setArguments(bundle);
//                                                                                                                        transaction.replace(R.id.content_frame, fragment);
//                                                                                                                        //getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                                                                                                                        //transaction.addToBackStack(SettingFragment.Tag);
//                                                                                                                        transaction.addToBackStack("Customer Search");
//                                                                                                                        transaction.commit();
//                                                                                                                    }
//                                                                                                                    else
//                                                                                                                    {
//                                                                                                                        Fragment fragment = new CustomerDataFragment1();
//                                                                                                                        Bundle bundle = new Bundle();
//                                                                                                                        bundle.putParcelable("customerObject",listOfCustomerSearchResult.get(adapter.selectedIndex));
//                                                                                                                        fragment.setArguments(bundle);
//                                                                                                                        transaction.replace(R.id.content_frame, fragment);
//                                                                                                                        //getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                                                                                                                        transaction.addToBackStack("Customer Search");
//                                                                                                                        transaction.commit();
//                                                                                                                    }
//                                                                                                                }
//                                                                                                                catch (Exception ex)
//                                                                                                                {
//                                                                                                                    ex.printStackTrace();
//                                                                                                                    showShortToast(language.getMessageErrorAtParsing());
//                                                                                                                    prd.dismiss();
//                                                                                                                }
//                                                                                                            }
//                                                                                                        }
//                                                                                                    };
//
//                                                                                                    try
//                                                                                                    {
//                                                                                                        String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.GET_CUSTOMER_COMPLETED_ORDER
//                                                                                                                + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
//                                                                                                                + "&Kundennr=" + matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKundenNr();
//                                                                                                        Log.e("url", url);
//                                                                                                        BasicAsyncTaskGetRequest asyncTaskLoadCustomerCompleteOrder = new BasicAsyncTaskGetRequest(url, onAsyncResultCompleteOrder, getActivity(), false);
//                                                                                                        asyncTaskLoadCustomerCompleteOrder.execute();
//                                                                                                    }
//                                                                                                    catch (IOException e)
//                                                                                                    {
//                                                                                                        e.printStackTrace();
//                                                                                                    }
//                                                                                                }
//                                                                                                catch (Exception ex)
//                                                                                                {
//                                                                                                    ex.printStackTrace();
//                                                                                                    showShortToast(language.getMessageErrorAtParsing());
//                                                                                                    prd.dismiss();
//                                                                                                }
//                                                                                            }
//                                                                                        }
//                                                                                    };
//
//                                                                                    try
//                                                                                    {
//                                                                                        String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.GET_CUSTOMER_OPEN_ORDER
//                                                                                                + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
//                                                                                                + "&Kundennr=" + matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKundenNr();
//                                                                                        Log.e("url", url);
//                                                                                        BasicAsyncTaskGetRequest asyncTaskLoadCustomerOpenOrder = new BasicAsyncTaskGetRequest(url, onAsyncResultOpenOrder, getActivity(), false);
//                                                                                        asyncTaskLoadCustomerOpenOrder.execute();
//                                                                                    }
//                                                                                    catch (IOException e)
//                                                                                    {
//                                                                                        e.printStackTrace();
//                                                                                    }
//                                                                                }
//                                                                            }
//                                                                            catch (Exception ex)
//                                                                            {
//                                                                                ex.printStackTrace();
//                                                                                showShortToast(language.getMessageErrorAtParsing());
//                                                                                prd.dismiss();
//                                                                            }
//                                                                        }
//                                                                    }
//                                                                };
//
//                                                                try
//                                                                {
//                                                                    String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.GET_CUSTOMER_OPEN_SPECIALS
//                                                                            + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
//                                                                            + "&Kontakt=" + matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();
//                                                                    Log.e("url", url);
//                                                                    BasicAsyncTaskGetRequest asyncTaskLoadOpenSales = new BasicAsyncTaskGetRequest(url, onAsyncResultOpenSales, getActivity(), false);
//                                                                    asyncTaskLoadOpenSales.execute();
//                                                                }
//                                                                catch (IOException e)
//                                                                {
//                                                                    e.printStackTrace();
//                                                                }
//                                                            }
//                                                            catch (Exception ex)
//                                                            {
//                                                                ex.printStackTrace();
//                                                                showShortToast(language.getMessageErrorAtParsing());
//                                                                prd.dismiss();
//                                                            }
//                                                        }
//                                                    }
//                                                };
//
//                                                try
//                                                {
//                                                    String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.GET_CUSTOMER_LOST_SALE
//                                                            + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
//                                                            + "&Kontakt=" + matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();
//                                                    Log.e("url", url);
//                                                    BasicAsyncTaskGetRequest asyncTaskLoadCustomerLostSales = new BasicAsyncTaskGetRequest(url, onAsyncResultLostSales, getActivity(), false);
//                                                    asyncTaskLoadCustomerLostSales.execute();
//                                                }
//                                                catch (IOException e)
//                                                {
//                                                    e.printStackTrace();
//                                                }
//                                            }
//                                            catch (Exception ex)
//                                            {
//                                                ex.printStackTrace();
//                                                prd.dismiss();
//                                                showShortToast(language.getMessageErrorAtParsing());
//                                            }
//                                            //ArrayList<CustomerActivityGetModel> listOfCustomerActivityDetails = new ArrayList<>();
//                                        }
//                                    }
//                                };
//                                try
//                                {
//                                    String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.CUSTOMER_ACTIVITY_LIST
//                                            + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
//                                            + "&Kontakt=" + matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();
//                                    Log.e("url", url);
//                                    BasicAsyncTaskGetRequest asyncTaskLoadContactPersons = new BasicAsyncTaskGetRequest(url, onAsyncResultActivity, getActivity(), false);
//                                    asyncTaskLoadContactPersons.execute();
//                                }
//                                catch (IOException e)
//                                {
//                                    e.printStackTrace();
//                                }
//                            }
//                            catch (Exception ex)
//                            {
//                                ex.printStackTrace();
//                                prd.dismiss();
//                                showShortToast(language.getMessageErrorAtParsing());
//                            }
//                        }
//                    }
//                };
//                try
//                {
//                    String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.CUSTOMER_CONTACT_PERSON_LIST
//                            + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
//                            + "&Kontakt=" + matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();
//                    Log.e("url", url);
//                    BasicAsyncTaskGetRequest asyncTaskLoadContactPersons = new BasicAsyncTaskGetRequest(url, onAsyncResultContactPersons, getActivity(), false);
//                    asyncTaskLoadContactPersons.execute();
//                }
//                catch (IOException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//            catch (Exception ex)
//            {
//                ex.printStackTrace();
//                showShortToast(language.getMessageErrorAtParsing());
//            }
//        }
//        else
//        {
//            showShortToast(language.getMessageNetworkNotAvailable());
//            if(db.isCustomerAvailable(listOfCustomerSearchResult.get(selectedIndex).getKontakt()))
//            {
//                CustomerDatabaseModel customerDatabaseModel = db.getCustomer(listOfCustomerSearchResult.get(selectedIndex).getKundenNr());
//                matecoPriceApplication.saveLoadedCustomer(DataHelper.LoadedCustomer, gson.toJson(customerDatabaseModel.getCustomerModel()));
//
//                ArrayList<ContactPersonModel> listOfContactPersonList = new ArrayList<>();
//                listOfContactPersonList.addAll(customerDatabaseModel.getListOfContactPerson());
//                matecoPriceApplication.saveData(DataHelper.LoadedCustomerContactPerson, gson.toJson(listOfContactPersonList));
//
//                ArrayList<CustomerActivityModel> listOfActivity = new ArrayList<>();
//                listOfActivity.addAll(customerDatabaseModel.getListOfActivity());
//                matecoPriceApplication.saveData(DataHelper.LoadedCustomerActivity, gson.toJson(listOfActivity));
//
//                ArrayList<CustomerProjectModel> listOfProject = new ArrayList<>();
//                listOfProject.addAll(customerDatabaseModel.getListOfProject());
//                matecoPriceApplication.saveData(DataHelper.LoadedCustomerProject, gson.toJson(listOfProject));
//
//                ArrayList<CustomerOfferModel> listOfOffer = new ArrayList<>();
//                listOfOffer.addAll(customerDatabaseModel.getListOfOffer());
//                matecoPriceApplication.saveData(DataHelper.LoadedCustomerOffer, gson.toJson(listOfOffer));
//
//                ArrayList<CustomerOpenOrderModel> listOfOpenOrder = new ArrayList<>();
//                listOfOpenOrder.addAll(customerDatabaseModel.getListOfOpenOrder());
//                matecoPriceApplication.saveData(DataHelper.LoadedCustomerOpenOrders, gson.toJson(listOfOpenOrder));
//
//                ArrayList<CustomerCompletedOrderModel> listOfCompletedOrder = new ArrayList<>();
//                listOfCompletedOrder.addAll(customerDatabaseModel.getListOfCompletedOrder());
//                matecoPriceApplication.saveData(DataHelper.LoadedCustomerCompletedOrders, gson.toJson(listOfCompletedOrder));
//
//                ArrayList<CustomerOpenOfferModel> listOfOpenSpecials = new ArrayList<>();
//                listOfOpenSpecials.addAll(customerDatabaseModel.getListOfOpenOffer());
//                matecoPriceApplication.saveData(DataHelper.LoadedCustomerOffer, gson.toJson(listOfOpenSpecials));
//
//                ArrayList<CustomerLostSaleDataModel> listOfLostSale = new ArrayList<>();
//                listOfLostSale.addAll(customerDatabaseModel.getListOfLostSale());
//                matecoPriceApplication.saveData(DataHelper.LoadedCustomerOffer, gson.toJson(listOfLostSale));
//
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                if(getArguments() == null)
//                {
//                    Fragment fragment = new CustomerDataFragment();
//                    //Bundle bundle = new Bundle();
//                    //bundle.putParcelable("");
//                    //bundle.putParcelable("customerObject", listOfCustomerSearchResult.get(adapter.selectedIndex));
//                    //fragment.setArguments(bundle);
//                    transaction.replace(R.id.content_frame, fragment);
//                    //getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    //transaction.addToBackStack(SettingFragment.Tag);
//                    transaction.addToBackStack("Customer Search");
//                    transaction.commit();
//                }
//                else
//                {
//                    Fragment fragment = new CustomerDataFragment1();
//                    Bundle bundle = new Bundle();
//                    bundle.putParcelable("customerObject",listOfCustomerSearchResult.get(adapter.selectedIndex));
//                    fragment.setArguments(bundle);
//                    transaction.replace(R.id.content_frame, fragment);
//                    getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    transaction.addToBackStack("Customer Search");
//                    transaction.commit();
//                }
//            }
//            else
//            {
//                showShortToast(language.getMessageCustomerNotFound());
//            }
//        }
//    }

    private void searchForNewPage()
    {
        Log.e("search for new page", "called");
        //loadingMore = true;
        matecoPriceApplication.hideKeyboard(getActivity());
        //listOfCustomerSearchResult.clear();
        adapter.setSelectedIndex(-1);
        adapter.notifyDataSetChanged();
        String customerNo = textCustomerSearchCustomerNo.getText().toString();
        String kaNr = textCustomerSearchKanr.getText().toString();
        String matchCode = textCustomerSearchMatchCode.getText().toString();
        String name1 = textCustomerSearchName1.getText().toString();
        String road = textCustomerSearchRoad.getText().toString();
        String zipCode = textCustomerSearchZipCode.getText().toString();
        String place = textCustomerSearchPlace.getText().toString();
        String telePhone = textCustomerSearchTel.getText().toString();

        if(customerNo.length() > 0 || kaNr.length() > 0 || matchCode.length() > 0 || name1.length() > 0 ||
                road.length() > 0 || zipCode.length() > 0 || place.length() > 0 || telePhone.length() > 0)
        {
            CustomerSearchPagingRequestModel customerSearch = new CustomerSearchPagingRequestModel();
            customerSearch.setKundenNr(customerNo);
            customerSearch.setKaNr(kaNr);
            customerSearch.setMatchCode(matchCode);
            customerSearch.setName1(name1);
            customerSearch.setStrasse(road);
            customerSearch.setPLZ(zipCode);
            customerSearch.setOrt(place);
            customerSearch.setTelefon(telePhone);
            customerSearch.setMitarbeiter(loginUserNumber);
            pageNuber = pageNuber + 1;
            customerSearch.setPageNumber(pageNuber + "");
            customerSearch.setPageSize(pageSize + "");

            String jsonToSend = new Gson().toJson(customerSearch);
            Log.e("customer to json", jsonToSend);
            String base64Data = DataHelper.getToken();
            String url = "";
            try
            {
                /*url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.CUSTOMER_SEARCH_PAGING
                        + "?token=" + URLEncoder.encode(base64Data.trim(), "UTF-8")
                        + "&customersearch=" + URLEncoder.encode(jsonToSend, "UTF-8");*/
                url = DataHelper.URL_CUSTOMER_HELPER+"customersearchpaging/token=" + URLEncoder.encode(base64Data.trim(), "UTF-8")
                        + "/customersearch=" + URLEncoder.encode(jsonToSend, "UTF-8");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            Log.e("url at customer search", url);
            if(DataHelper.isNetworkAvailable(getActivity()))
            {
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        Log.e("result ", result);
                        if(result.equals("error"))
                        {
                            showLongToast(language.getMessageError());
                        }
                        else
                        {
                            try
                            {
                                JSONObject jsonObject = new JSONObject(result);
                                customerCount = jsonObject.getInt("CustomerCount");
                                totalPageCount = jsonObject.getInt("PageCount");
                                pageNuber = jsonObject.getInt("PageNumber");
                                pageSize = jsonObject.getInt("PageSize");

                                if(pageNuber == totalPageCount)
                                {
                                    listCustomerSearchResult.removeFooterView(footerView);
                                }

                                JSONArray resultsOfCustomers = jsonObject.getJSONArray("Result");
                                Log.e("size add page" + pageNuber, resultsOfCustomers.length()+"");
                                if(result.length() == 0)
                                {
                                    showLongToast(language.getMessageNoResultFound());
                                }
                                else
                                {
                                    CustomerModel.extractFromJson(resultsOfCustomers.toString(), listOfCustomerSearchResult);
                                    CustomerModel.extractFromJson(resultsOfCustomers.toString(), listOfCustomerSearchResultTemp);
                                    adapter.notifyDataSetChanged();
                                }
                                //loadingMore = false;
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                    }
                };
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                asyncTask.execute();
            }
            else
            {
                listOfCustomerSearchResult.clear();
                showLongToast(language.getMessageNetworkNotAvailable());
                listOfCustomerSearchResult.addAll(db.getCustomer(customerSearch));
                Log.e("clear", listOfCustomerSearchResult.size()+"");
                adapter.notifyDataSetChanged();
//                if(listOfCustomerSearchResult.size() > 20)
//                {
//                    showShortToast(language.getMessageSearchQueryBroad());
//                }
            }
        }
        else
        {
            showLongToast(language.getMessageInputSearchParameter());
        }
    }
}