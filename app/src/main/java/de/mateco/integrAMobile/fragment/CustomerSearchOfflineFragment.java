package de.mateco.integrAMobile.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.CustomerSearchOfflineResultAdapter;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.ContactPersonModel;
import de.mateco.integrAMobile.model.CustomerActivityGetModel;
import de.mateco.integrAMobile.model.CustomerCompletedOrderModel;
import de.mateco.integrAMobile.model.CustomerDatabaseModel;
import de.mateco.integrAMobile.model.CustomerFullDetailModel;
import de.mateco.integrAMobile.model.CustomerLostSaleDataModel;
import de.mateco.integrAMobile.model.CustomerModel;
import de.mateco.integrAMobile.model.CustomerOpenOfferModel;
import de.mateco.integrAMobile.model.CustomerOpenOrderModel;
import de.mateco.integrAMobile.model.CustomerSearchPagingRequestModel;
import de.mateco.integrAMobile.model.Language;

public class CustomerSearchOfflineFragment extends BaseFragment implements TextView.OnEditorActionListener
{
    private View rootView;
    private Language language;
    private MatecoPriceApplication matecoPriceApplication;
    private EditText textCustomerSearchCustomerNo, textCustomerSearchKanr,textCustomerSearchMatchCode,textCustomerSearchName1,
             textCustomerSearchRoad, textCustomerSearchZipCode,textCustomerSearchPlace, textCustomerSearchTel;
    private ListView listCustomerSearchResult;
    private ArrayList<CustomerModel> listOfCustomerSearchResult;
    private CustomerSearchOfflineResultAdapter adapter;
    private Menu menu;
    private DataBaseHandler db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if(rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_customer_offline_search, container, false);
            super.initializeFragment(rootView);
        }
        else
        {
            if(rootView.getParent() != null)
                ((ViewGroup)rootView.getParent()).removeView(rootView);
        }

        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelSearch());
        return rootView;
    }

    @Override
    public void initializeComponents(View rootView)
    {
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        db = new DataBaseHandler(getActivity());
        getActivity().invalidateOptionsMenu();
//        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelSearch());
            setHasOptionsMenu(true);

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

        CustomerSearchOfflineResultAdapter.OnCustomerRefreshClick onCustomerRefreshClick = new CustomerSearchOfflineResultAdapter.OnCustomerRefreshClick()
        {
            @Override
            public void onCustomerRefreshClicked(int position)
            {
                getCustomerDetails(position);
            }
        };

        listOfCustomerSearchResult.addAll(db.getAllCustomer());
        adapter = new CustomerSearchOfflineResultAdapter(getActivity(), listOfCustomerSearchResult, language, onCustomerRefreshClick);

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

        listCustomerSearchResult.setAdapter(adapter);
        listCustomerSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //view.setSelected(true);
                //listCustomerSearchResult.setSelection(position);
                adapter.setSelectedIndex(position);
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

        labelCustomerSearchCustomerNo.setText(language.getLabelCustomerNo());
        labelCustomerSearchKanr.setText(language.getLabelKanr());
        labelCustomerSearchMatchCode.setText(language.getLabelMatchCode());
        labelCustomerSearchName1.setText(language.getLabelName() + " 1");
        labelCustomerSearchRoad.setText(language.getLabelRoad());
        labelCustomerSearchZipCode.setText(language.getLabelZipCode());
        labelCustomerSearchPlace.setText(language.getLabelPlace());
        labelCustomerSearchTel.setText(language.getLabelTelephone());

//        textCustomerSearchCustomerNo.setHint(language.getLabelCustomerNo());
//        textCustomerSearchKanr.setHint(language.getLabelKanr());
//        textCustomerSearchMatchCode.setHint(language.getLabelMatchCode());
//        textCustomerSearchName1.setHint(language.getLabelName() + " 1");
//        textCustomerSearchRoad.setHint(language.getLabelRoad());
//        textCustomerSearchZipCode.setHint(language.getLabelZipCode());
//        textCustomerSearchPlace.setHint(language.getLabelPlace());
//        textCustomerSearchTel.setHint(language.getLabelTelephone());
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
        menu.findItem(R.id.actionSettings).setVisible(false);
        //return true;
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch (item.getItemId())
        {
            case R.id.actionSettings:
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new SettingFragment(),"Setting");
                //transaction.addToBackStack(SettingFragment.Tag);
                transaction.addToBackStack("Setting");
                transaction.commit();
                return true;
            case R.id.actionBack:
                    getFragmentManager().popBackStack();
                return true;
            case R.id.actionForward:

                return true;
            case R.id.actionAdd:

                return true;
            case R.id.actionSearch:
                    searchForResults();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    private void searchForResults()
    {
        matecoPriceApplication.hideKeyboard(getActivity());
        listOfCustomerSearchResult.clear();
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

            //Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String jsonToSend = DataHelper.getGson().toJson(customerSearch);
            String base64Data = DataHelper.getToken();
            String url = "";
            try
            {
                /*url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.CUSTOMER_SEARCH
                        + "?token=" + URLEncoder.encode(base64Data.trim(), "UTF-8") + "&customersearch=" + URLEncoder.encode(jsonToSend, "UTF-8");*/
                url = DataHelper.URL_CUSTOMER_HELPER+"customersearchpaging/token=" + URLEncoder.encode(base64Data.trim(), "UTF-8")
                        + "/customersearch=" + URLEncoder.encode(jsonToSend, "UTF-8");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            listOfCustomerSearchResult.clear();
            listOfCustomerSearchResult.addAll(db.getCustomer(customerSearch));
            adapter.notifyDataSetChanged();
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
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        if(result.equals("error"))
                        {
                            showShortToast(language.getMessageError());
                        }
                        else if(result.equals(DataHelper.NetworkError)){
                            showShortToast(language.getMessageNetworkNotAvailable());
                        }
                        else
                        {
                            try
                            {
                                CustomerDatabaseModel customerDatabaseModel = new CustomerDatabaseModel();
                                CustomerFullDetailModel customerFullDetail = new Gson().fromJson(result, CustomerFullDetailModel.class);
                                //String json = new Gson().toJson(customerFullDetail.getCustomerSearchList());
                                customerDatabaseModel.setCustomerModel(customerFullDetail.getCustomerSearchList());
                                customerDatabaseModel.setListOfContactPerson(customerFullDetail.getCustomerContactPersonList());
                                customerDatabaseModel.setListOfActivity(customerFullDetail.getCustomerActivityListt().getListOfActivities());
                                //matecoPriceApplication.saveData(DataHelper.LoadedCustomerProject, listOfProjects);
                                customerDatabaseModel.setListOfProject(customerFullDetail.getCustomerActivityListt().getListOfProject());
                                //matecoPriceApplication.saveData(DataHelper.LoadedCustomerOffer, listOfOffer);
                                customerDatabaseModel.setListOfOffer(customerFullDetail.getCustomerActivityListt().getListOfOffers());
                                customerDatabaseModel.setListOfLostSale(customerFullDetail.getCustomerLostSaleList());
                                customerDatabaseModel.setListOfOpenOffer(customerFullDetail.getCustomerOpenSpecialsList());
                                customerDatabaseModel.setListOfOpenOrder(customerFullDetail.getCustomerOpenOrderList());
                                customerDatabaseModel.setListOfCompletedOrder(customerFullDetail.getCustomerCompletedOrdersList());
                                db.updateCustomer(customerDatabaseModel);
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                                showShortToast(language.getMessageErrorAtParsing());
                            }
                        }
                    }
                };

                /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.AGENDA_CUSTOMER_SHOW
                        + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "&fieldname=" + "Kontakt"
                        + "&value=" + listOfCustomerSearchResult.get(selectedIndex).getKontakt();*/
                String url = DataHelper.URL_AGENDA_HELPER + "agendacustomershow" //+ DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.AGENDA_CUSTOMER_SHOW
                        + "/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                        + "/fieldname=" + "Kontakt"
                        + "/value=" + listOfCustomerSearchResult.get(selectedIndex).getKontakt();
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
        }
    }

    private void updateCustomer(int selectedIndex)
    {
        Gson gson = new Gson();
        String json = gson.toJson(listOfCustomerSearchResult.get(selectedIndex));
        if(DataHelper.isNetworkAvailable(getActivity()))
        {
            //matecoPriceApplication.saveLoadedCustomer(DataHelper.LoadedCustomer, json);
            final CustomerDatabaseModel customerDatabaseModel = new CustomerDatabaseModel();
            customerDatabaseModel.setCustomerModel(listOfCustomerSearchResult.get(selectedIndex));
            final ProgressDialog prd;
            prd = new ProgressDialog(getActivity());
            prd.setTitle(language.getMessageWaitWhileLoading());
            prd.setMessage(language.getMessageWaitWhileLoading());
            prd.show();
            final BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResultContactPersons = new BasicAsyncTaskGetRequest.OnAsyncResult()
            {
                @Override
                public void OnAsynResult(String result)
                {
                    if(result.equals("error"))
                    {
                        prd.dismiss();
                        showShortToast(language.getMessageError());
                    }
                    else
                    {
                        try
                        {
                            ArrayList<ContactPersonModel> listOfContactPersonList = new ArrayList<>();
                            ContactPersonModel.extractFromJson(result, listOfContactPersonList);
                            String json = new Gson().toJson(listOfContactPersonList);
                            //matecoPriceApplication.saveData(DataHelper.LoadedCustomerContactPerson, json);
                            customerDatabaseModel.setListOfContactPerson(listOfContactPersonList);
                            BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResultActivity = new BasicAsyncTaskGetRequest.OnAsyncResult()
                            {
                                @Override
                                public void OnAsynResult(String result)
                                {
                                    if(result.equals("error"))
                                    {
                                        prd.dismiss();
                                        showShortToast(language.getMessageError());
                                    }
                                    else
                                    {
                                        //ArrayList<CustomerActivityGetModel> listOfCustomerActivityDetails = new ArrayList<>();
                                        CustomerActivityGetModel customerActivityGetModel = new Gson().fromJson(result, CustomerActivityGetModel.class);
                                        //CustomerActivityGetModel.extractFromJson(result, listOfCustomerActivityDetails);
                                        //customerActivityGetModel =
                                        String listOfActivity = new Gson().toJson(customerActivityGetModel.getListOfActivities());
                                        //matecoPriceApplication.saveData(DataHelper.LoadedCustomerActivity, listOfActivity);
                                        customerDatabaseModel.setListOfActivity(customerActivityGetModel.getListOfActivities());
                                        String listOfProjects = new Gson().toJson(customerActivityGetModel.getListOfProject());
                                        //matecoPriceApplication.saveData(DataHelper.LoadedCustomerProject, listOfProjects);
                                        customerDatabaseModel.setListOfProject(customerActivityGetModel.getListOfProject());
                                        String listOfOffer = new Gson().toJson(customerActivityGetModel.getListOfOffers());
                                        //matecoPriceApplication.saveData(DataHelper.LoadedCustomerOffer, listOfOffer);
                                        customerDatabaseModel.setListOfOffer(customerActivityGetModel.getListOfOffers());
                                        //showShortToast("Customer Loaded");
                                        showShortToast(language.getMessageCustomerLoaded());
                                        BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResultLostSales = new BasicAsyncTaskGetRequest.OnAsyncResult()
                                        {
                                            @Override
                                            public void OnAsynResult(String result)
                                            {
                                                if(result.equals("error"))
                                                {
                                                    prd.dismiss();
                                                    showShortToast(language.getMessageError());
                                                }
                                                else
                                                {
                                                    ArrayList<CustomerLostSaleDataModel> listOfLostSale = new ArrayList<>();
                                                    CustomerLostSaleDataModel.extractFromJson(result, listOfLostSale);
                                                    String json = new Gson().toJson(listOfLostSale);
                                                    //matecoPriceApplication.saveData(DataHelper.LoadedCustomerLostSale, json);
                                                    customerDatabaseModel.setListOfLostSale(listOfLostSale);
                                                    BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResultOpenSales = new BasicAsyncTaskGetRequest.OnAsyncResult()
                                                    {
                                                        @Override
                                                        public void OnAsynResult(String result)
                                                        {
                                                            if(result.equals("error"))
                                                            {
                                                                prd.dismiss();
                                                                showShortToast(language.getMessageError());
                                                            }
                                                            else
                                                            {
                                                                ArrayList<CustomerOpenOfferModel> listOfOpenOffer = new ArrayList<>();
                                                                CustomerOpenOfferModel.extractFromJson(result, listOfOpenOffer);
                                                                String json = new Gson().toJson(listOfOpenOffer);
                                                                //matecoPriceApplication.saveData(DataHelper.LoadedCustomerOpenSpecials, json);
                                                                customerDatabaseModel.setListOfOpenOffer(listOfOpenOffer);
                                                                if(matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKundenNr().equals(""))
                                                                {
                                                                    //showShortToast(language.getMessageCustomerNumberNotFound());

                                                                    ArrayList<CustomerOpenOrderModel> listOfOpenOrders = new ArrayList<>();
                                                                    String jsonOfOpenOrders = new Gson().toJson(listOfOpenOrders);
                                                                    //matecoPriceApplication.saveData(DataHelper.LoadedCustomerOpenOrders, jsonOfOpenOrders);
                                                                    customerDatabaseModel.setListOfOpenOrder(listOfOpenOrders);
                                                                    ArrayList<CustomerCompletedOrderModel> listOfCompletedOrders = new ArrayList<>();
                                                                    String jsonOfCompletedOrders = new Gson().toJson(listOfCompletedOrders);
                                                                    //matecoPriceApplication.saveData(DataHelper.LoadedCustomerCompletedOrders, jsonOfCompletedOrders);
                                                                    customerDatabaseModel.setListOfCompletedOrder(listOfCompletedOrders);
                                                                    prd.dismiss();
                                                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                                                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                                                    db.updateCustomer(customerDatabaseModel);
                                                                }
                                                                else
                                                                {
                                                                    BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResultOpenOrder = new BasicAsyncTaskGetRequest.OnAsyncResult()
                                                                    {
                                                                        @Override
                                                                        public void OnAsynResult(String result)
                                                                        {
                                                                            if(result.equals("error"))
                                                                            {
                                                                                prd.dismiss();
                                                                                showShortToast(language.getMessageError());
                                                                            }
                                                                            else
                                                                            {
                                                                                ArrayList<CustomerOpenOrderModel> listOfOpenOrders = new ArrayList<>();
                                                                                CustomerOpenOrderModel.extractFromJson(result, listOfOpenOrders);
                                                                                String json = new Gson().toJson(listOfOpenOrders);
                                                                                //matecoPriceApplication.saveData(DataHelper.LoadedCustomerOpenOrders, json);
                                                                                customerDatabaseModel.setListOfOpenOrder(listOfOpenOrders);
                                                                                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResultCompleteOrder = new BasicAsyncTaskGetRequest.OnAsyncResult()
                                                                                {
                                                                                    @Override
                                                                                    public void OnAsynResult(String result)
                                                                                    {
                                                                                        if(result.equals("error"))
                                                                                        {
                                                                                            prd.dismiss();
                                                                                            showShortToast(language.getMessageError());
                                                                                        }
                                                                                        else
                                                                                        {
                                                                                            ArrayList<CustomerCompletedOrderModel> listOfCompletedOrder = new ArrayList<>();
                                                                                            CustomerCompletedOrderModel.extractFromJson(result, listOfCompletedOrder);
                                                                                            String json = new Gson().toJson(listOfCompletedOrder);
                                                                                            //matecoPriceApplication.saveData(DataHelper.LoadedCustomerCompletedOrders, json);
                                                                                            customerDatabaseModel.setListOfCompletedOrder(listOfCompletedOrder);
                                                                                            db.updateCustomer(customerDatabaseModel);
                                                                                            prd.dismiss();
                                                                                        }
                                                                                    }
                                                                                };

                                                                                try
                                                                                {
                                                                                    /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.GET_CUSTOMER_COMPLETED_ORDER + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                                                                                    url += "&Kundennr=" + matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKundenNr();*/
                                                                                    String url = DataHelper.URL_CUSTOMER_HELPER+"customercompletedorders/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                                                                                    url += "/Kundennr=" + matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKundenNr();
                                                                                    BasicAsyncTaskGetRequest asyncTaskLoadCustomerCompleteOrder = new BasicAsyncTaskGetRequest(url, onAsyncResultCompleteOrder, getActivity(), false);
                                                                                    asyncTaskLoadCustomerCompleteOrder.execute();
                                                                                }
                                                                                catch (IOException e)
                                                                                {
                                                                                    e.printStackTrace();
                                                                                }
                                                                            }
                                                                        }
                                                                    };

                                                                    try
                                                                    {
                                                                        /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.GET_CUSTOMER_OPEN_ORDER + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                                                                        url += "&Kundennr=" + matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKundenNr();*/

                                                                        String url = DataHelper.URL_CUSTOMER_HELPER+"customeropenorder/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                                                                        url += "/Kundennr=" + matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKundenNr();
                                                                        BasicAsyncTaskGetRequest asyncTaskLoadCustomerOpenOrder = new BasicAsyncTaskGetRequest(url, onAsyncResultOpenOrder, getActivity(), false);
                                                                        asyncTaskLoadCustomerOpenOrder.execute();
                                                                    }
                                                                    catch (IOException e)
                                                                    {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    };

                                                    try
                                                    {
                                                        /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.GET_CUSTOMER_OPEN_SPECIALS + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                                                        url += "&Kontakt=" + matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();*/

                                                        String url = DataHelper.URL_CUSTOMER_HELPER+"customeropenspecials/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                                                        url += "/Kundennr=" + matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKundenNr();
                                                        BasicAsyncTaskGetRequest asyncTaskLoadOpenSales = new BasicAsyncTaskGetRequest(url, onAsyncResultOpenSales, getActivity(), false);
                                                        asyncTaskLoadOpenSales.execute();
                                                    }
                                                    catch (IOException e)
                                                    {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        };

                                        try
                                        {
                                            /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.GET_CUSTOMER_LOST_SALE + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                                            url += "&Kontakt=" + matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();*/

                                            String url = DataHelper.URL_CUSTOMER_HELPER+"customerlostsale/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                                            url += "/Kundennr=" + matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKundenNr();
                                            BasicAsyncTaskGetRequest asyncTaskLoadCustomerLostSales = new BasicAsyncTaskGetRequest(url, onAsyncResultLostSales, getActivity(), false);
                                            asyncTaskLoadCustomerLostSales.execute();
                                        }
                                        catch (IOException e)
                                        {
                                            e.printStackTrace();
                                        }

                                    }
                                }
                            };
                            try
                            {
                                /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.CUSTOMER_ACTIVITY_LIST + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                                url += "&Kontakt=" + matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();*/

                                String url = DataHelper.URL_CUSTOMER_HELPER+"customeractivitylistshow/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                                url += "/Kontakt=" + matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();
                                BasicAsyncTaskGetRequest asyncTaskLoadContactPersons = new BasicAsyncTaskGetRequest(url, onAsyncResultActivity, getActivity(), false);
                                asyncTaskLoadContactPersons.execute();
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
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
            try
            {
                /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.CUSTOMER_CONTACT_PERSON_LIST + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                url += "&Kontakt=" + matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();*/

                String url = DataHelper.URL_CUSTOMER_HELPER+"customercontactpersonlist/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                url += "/Kontakt=" + matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt();
                BasicAsyncTaskGetRequest asyncTaskLoadContactPersons = new BasicAsyncTaskGetRequest(url, onAsyncResultContactPersons, getActivity(), false);
                asyncTaskLoadContactPersons.execute();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            showShortToast(language.getMessageNetworkNotAvailable());
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
//                if(getArguments()==null)
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
        }
        //Log.e("object swtored", matecoPriceApplication.getLoadedCustomer(getActivity(), DataHelper.LoadedCustomer, ""));
    }
}
