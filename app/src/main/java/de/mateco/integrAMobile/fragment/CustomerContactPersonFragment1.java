package de.mateco.integrAMobile.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.LogApp;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.CustomerContactPersonListDetailAdapter;
import de.mateco.integrAMobile.adapter.DecisionMakerAdapter;
import de.mateco.integrAMobile.adapter.DocumentLanguageAdapter;
import de.mateco.integrAMobile.adapter.FeatureAdapter;
import de.mateco.integrAMobile.adapter.FunctionAdapter;
import de.mateco.integrAMobile.adapter.SalutationAdapter;
import de.mateco.integrAMobile.asyncTask.AsyncTaskWithAuthorizationHeaderPost;
import de.mateco.integrAMobile.base.LoadedCustomerFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.ContactPersonModel;
import de.mateco.integrAMobile.model.CustomerContactPersonAndFeatureAddModel;
import de.mateco.integrAMobile.model.CustomerContactPersonAndFeatureUpdateModel;
import de.mateco.integrAMobile.model.CustomerModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LoginPersonModel;
import de.mateco.integrAMobile.model.PricingCustomerOrderBasicInfo;
import de.mateco.integrAMobile.model_logonsquare.CustomerContactPersonDecisionMakersListItem;
import de.mateco.integrAMobile.model_logonsquare.CustomerContactPersonDocumentlanguageListItem;
import de.mateco.integrAMobile.model_logonsquare.CustomerContactPersonFeatureListItem;
import de.mateco.integrAMobile.model_logonsquare.CustomerContactPersonFunctionComboListItem;
import de.mateco.integrAMobile.model_logonsquare.CustomerContactPersonSalutationComboListItem;

public class CustomerContactPersonFragment1 extends LoadedCustomerFragment
{
    private Language language;
    private View rootView;
    private MatecoPriceApplication matecoPriceApplication;
    private ListView listCustomerContactPersonFeature, listViewCustomerContactPersonList;
    private Spinner spinnerCustomerContactPersonSalutation, spinnerCustomerContactPersonFunction, spinnerCustomerContactPersonDocumentLanguage,
            spinnerCustomerContactPersonDecisionMaker;
    private ArrayList<CustomerContactPersonSalutationComboListItem> listOfSalutation;
    private SalutationAdapter salutationAdapter;
    private ArrayList<CustomerContactPersonFunctionComboListItem> listOfFunction;
    private FunctionAdapter functionAdapter;
    private ArrayList<CustomerContactPersonDecisionMakersListItem> listOfDecisionMaker;
    private DecisionMakerAdapter decisionMakerAdapter;
    private ArrayList<CustomerContactPersonDocumentlanguageListItem> listOfDocumentLanguage;
    private DocumentLanguageAdapter documentLanguageAdapter;
    private ArrayList<ContactPersonModel> listOfContactPerson;
    private CheckBox checkboxCustomerContactPersonListHideRetired;
    private CustomerContactPersonListDetailAdapter customerContactPersonListDetailAdapter;
    private EditText textCustomerContactPersonEmail, textCustomerContactPersonMobile, textCustomerContactPersonFax,
            textCustomerContactPersonPhone, textCustomerContactPersonAdditionalInfo, textCustomerContactPersonLastName,
            textCustomerContactPersonFirstName, textCustomerContactPersonTitle;
    private boolean isInEditMode = false, isInAddMode = false;
    private ContactPersonModel selectedContactPerson;
    private Menu menu;
    private ArrayList<CustomerContactPersonFeatureListItem> listOfFeatures, listOfSelectedFeatures, listOfRemainingFeature;
    private FeatureAdapter selectedFeatureAdapter, listOfFeatureAdapter;
    private ImageButton buttonAddCustomerContactPersonFeature, buttonRemoveCustomerContactPersonFeature;
    private CustomerContactPersonSalutationComboListItem selectedSalutation;
    private CustomerContactPersonFunctionComboListItem selectedFunction;
    private CustomerContactPersonDecisionMakersListItem selectedDecisionMaker;
    private CustomerContactPersonDocumentlanguageListItem  selectedDocumentLanguage;
    private DataBaseHandler db;
    private LinearLayout linearLayoutCustomerContactPersonList;
    ArrayList<ContactPersonModel> arrayListContactfromDb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_customer_contact_person_detail_layout, container, false);
        super.initializeFragment(rootView);
        return rootView;
    }

    @Override
    public void initializeComponents(View rootView)
    {
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        getActivity().invalidateOptionsMenu();
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelContactPerson());
        setHasOptionsMenu(true);
        listCustomerContactPersonFeature = (ListView)rootView.findViewById(R.id.listCustomerContactPersonFeature);
        listViewCustomerContactPersonList = (ListView)rootView.findViewById(R.id.listViewCustomerContactPersonList);
        spinnerCustomerContactPersonSalutation = (Spinner)rootView.findViewById(R.id.spinnerCustomerContactPersonSalutation);
        spinnerCustomerContactPersonFunction = (Spinner)rootView.findViewById(R.id.spinnerCustomerContactPersonFunction);
        spinnerCustomerContactPersonDocumentLanguage = (Spinner)rootView.findViewById(R.id.spinnerCustomerContactPersonDocumentLanguage);
        spinnerCustomerContactPersonDecisionMaker = (Spinner)rootView.findViewById(R.id.spinnerCustomerContactPersonDecisionMaker);
        linearLayoutCustomerContactPersonList = (LinearLayout)rootView.findViewById(R.id.linearLayoutCustomerContactPersonList);
        textCustomerContactPersonTitle = (EditText)rootView.findViewById(R.id.textCustomerContactPersonTitle);
        textCustomerContactPersonFirstName = (EditText)rootView.findViewById(R.id.textCustomerContactPersonFirstName);
        textCustomerContactPersonLastName = (EditText)rootView.findViewById(R.id.textCustomerContactPersonLastName);
        textCustomerContactPersonAdditionalInfo = (EditText)rootView.findViewById(R.id.textCustomerContactPersonAdditionalInfo);
        textCustomerContactPersonPhone = (EditText)rootView.findViewById(R.id.textCustomerContactPersonPhone);
        textCustomerContactPersonFax = (EditText)rootView.findViewById(R.id.textCustomerContactPersonFax);
        textCustomerContactPersonMobile = (EditText)rootView.findViewById(R.id.textCustomerContactPersonMobile);
        textCustomerContactPersonEmail = (EditText)rootView.findViewById(R.id.textCustomerContactPersonEmail);

        buttonAddCustomerContactPersonFeature = (ImageButton)rootView.findViewById(R.id.buttonAddCustomerContactPersonFeature);
        buttonRemoveCustomerContactPersonFeature = (ImageButton)rootView.findViewById(R.id.buttonRemoveCustomerContactPersonFeature);

        db = new DataBaseHandler(getActivity());
        listOfSalutation = new ArrayList<>();
        listOfSalutation = db.getSalutation();
        listOfFunction = new ArrayList<>();
        listOfFunction = db.getFunction();
        listOfDecisionMaker = new ArrayList<>();
        listOfDecisionMaker = db.getDecisionMakers();
        listOfDocumentLanguage = new ArrayList<>();
        listOfDocumentLanguage = db.getDocumentLanguages();
        salutationAdapter = new SalutationAdapter(getActivity(), listOfSalutation, R.layout.list_item_spinner_salutation, language);
        functionAdapter = new FunctionAdapter(getActivity(), listOfFunction, R.layout.list_item_spinner_function, language);
        documentLanguageAdapter = new DocumentLanguageAdapter(getActivity(), listOfDocumentLanguage, R.layout.list_item_spinner_document_language, language);
        decisionMakerAdapter = new DecisionMakerAdapter(getActivity(), listOfDecisionMaker, R.layout.list_item_spinner_decision_maker, language);
        spinnerCustomerContactPersonSalutation.setAdapter(salutationAdapter);
        spinnerCustomerContactPersonSalutation.setSelection(0);
        spinnerCustomerContactPersonFunction.setAdapter(functionAdapter);
        spinnerCustomerContactPersonFunction.setSelection(0);
        spinnerCustomerContactPersonDocumentLanguage.setAdapter(documentLanguageAdapter);
        spinnerCustomerContactPersonDocumentLanguage.setSelection(0);
        spinnerCustomerContactPersonDecisionMaker.setAdapter(decisionMakerAdapter);
        spinnerCustomerContactPersonDecisionMaker.setSelection(0);

        if(getArguments() != null && getArguments().containsKey("contact_person"))
        {
            listOfContactPerson = new ArrayList<>();

            //listOfContactPerson = getArguments().getParcelableArrayList("contact_person"); /// for perticular resumed offline contact person
            listOfContactPerson = matecoPriceApplication.getLoadedCustomerContactPersons
                    (DataHelper.LoadedCustomerContactPerson, new ContactPersonModel().toString()); /// for loaded customer's constact person
        }else {
            listOfContactPerson = new ArrayList<>();
            listOfContactPerson = matecoPriceApplication.getLoadedCustomerContactPersons(DataHelper.LoadedCustomerContactPerson, new ContactPersonModel().toString());
        }


        customerContactPersonListDetailAdapter = new CustomerContactPersonListDetailAdapter(getActivity(), listOfContactPerson, R.layout.list_item_customer_contact_person_details);
        listViewCustomerContactPersonList.setAdapter(customerContactPersonListDetailAdapter);
        listOfFeatures = new ArrayList<>();
        listOfFeatures = db.getFeatures();
        listOfRemainingFeature = new ArrayList<>();
        listOfSelectedFeatures = new ArrayList<CustomerContactPersonFeatureListItem>();
        selectedFeatureAdapter = new FeatureAdapter(getActivity(), listOfSelectedFeatures, R.layout.list_item_customer_contact_person_feature);
        listOfFeatureAdapter = new FeatureAdapter(getActivity(), listOfRemainingFeature, R.layout.list_item_customer_contact_person_feature);
        listCustomerContactPersonFeature.setAdapter(selectedFeatureAdapter);

        setLanguage();
        makeEditable(false);
        selectedSalutation = new CustomerContactPersonSalutationComboListItem();
        selectedFunction = new CustomerContactPersonFunctionComboListItem();
        selectedDecisionMaker = new CustomerContactPersonDecisionMakersListItem();
        selectedDocumentLanguage = new CustomerContactPersonDocumentlanguageListItem ();
        if(listOfContactPerson.size() > 0)
        {
            listViewCustomerContactPersonList.setSelection(0);
            selectedContactPerson = listOfContactPerson.get(0);
            setCustomerContactPerson(selectedContactPerson);
            customerContactPersonListDetailAdapter.setSelectedIndex(0);
        }
        super.initializeComponents(rootView);
    }

    @Override
    public void bindEvents(View rootView)
    {
        //listCustomerContactPersonFeature.addHeaderView(listHeader, null, false);
        checkboxCustomerContactPersonListHideRetired.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(checkboxCustomerContactPersonListHideRetired.isChecked())
                {
                    ArrayList<ContactPersonModel> customerContactPersonModel = new ArrayList<ContactPersonModel>();
                    customerContactPersonModel = matecoPriceApplication.getLoadedCustomerContactPersons(DataHelper.LoadedCustomerContactPerson, new ContactPersonModel().toString());
                    listOfContactPerson.clear();
                    for(int i = 0; i < customerContactPersonModel.size(); i++)
                    {
                        if(customerContactPersonModel.get(i).getAusgeschieden().equals("Nein"))
                        {
                            listOfContactPerson.add(customerContactPersonModel.get(i));
                        }
                    }
                    if(listOfContactPerson.size() > 0)
                        customerContactPersonListDetailAdapter.setSelectedIndex(0);
                    customerContactPersonListDetailAdapter.notifyDataSetChanged();
                }
                else
                {
                    listOfContactPerson.clear();
                    listOfContactPerson.addAll(matecoPriceApplication.getLoadedCustomerContactPersons(DataHelper.LoadedCustomerContactPerson, new ContactPersonModel().toString()));
                    if(listOfContactPerson.size() > 0)
                        customerContactPersonListDetailAdapter.setSelectedIndex(0);
                    customerContactPersonListDetailAdapter.notifyDataSetChanged();
                }
            }
        });

        listViewCustomerContactPersonList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //view.setSelected(true);
                selectedContactPerson = listOfContactPerson.get(position);
                setCustomerContactPerson(selectedContactPerson);
                customerContactPersonListDetailAdapter.setSelectedIndex(position);

            }
        });
//        listCustomerContactPersonFeature.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });
        listCustomerContactPersonFeature.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //view.setSelected(true);
                selectedFeatureAdapter.setSelectedIndex(position);
            }
        });
        spinnerCustomerContactPersonSalutation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0)
                {
                    selectedSalutation = null;
                }
                else
                {
                    selectedSalutation = listOfSalutation.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerCustomerContactPersonFunction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0)
                {
                    selectedFunction = null;
                }
                else
                {
                    selectedFunction = listOfFunction.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerCustomerContactPersonDecisionMaker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0)
                {
                    selectedDecisionMaker = null;
                }
                else
                {
                    selectedDecisionMaker = listOfDecisionMaker.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerCustomerContactPersonDocumentLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0)
                {
                    selectedDocumentLanguage = null;
                }
                else
                {
                    selectedDocumentLanguage = listOfDocumentLanguage.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        super.bindEvents(rootView);
    }

    private void setCustomerContactPerson(ContactPersonModel contactPerson)
    {
        clearAllValues();
        if(contactPerson != null)
        {
            boolean isSalutationSelected = false;
            for(int i = 0; i < listOfSalutation.size(); i++)
            {
                if(contactPerson.getAnredeID().equals(listOfSalutation.get(i).getAnrede()))
                {
                    spinnerCustomerContactPersonSalutation.setSelection(i + 1);
                    //selectedLegalForm = listOfLegalForm.get(i);
                    selectedSalutation = listOfSalutation.get(i);
                    isSalutationSelected = true;
                    break;
                }
            }
            if(!isSalutationSelected)
            {
                spinnerCustomerContactPersonSalutation.setSelection(0);
            }
            boolean isFunctionSelected = false;
            for(int i = 0; i < listOfFunction.size(); i++)
            {
                if(contactPerson.getFunktionID().equals(listOfFunction.get(i).getFunktion()))
                {
                    spinnerCustomerContactPersonFunction.setSelection(i + 1);
                    selectedFunction = listOfFunction.get(i);
                    isFunctionSelected = true;
                    //selectedLegalForm = listOfLegalForm.get(i);
                    break;
                }
            }
            if(!isFunctionSelected)
            {
                spinnerCustomerContactPersonFunction.setSelection(0);
            }

            boolean isDecisionMakerSelected = false;
            for(int i = 0; i < listOfDecisionMaker.size(); i++)
            {
                if(contactPerson.getEntscheiderID().equals(listOfDecisionMaker.get(i).getEntscheider()))
                {
                    spinnerCustomerContactPersonDecisionMaker.setSelection(i + 1);
                    selectedDecisionMaker = listOfDecisionMaker.get(i);
                    isDecisionMakerSelected = true;
                    //selectedLegalForm = listOfLegalForm.get(i);
                    break;
                }
            }
            if(!isDecisionMakerSelected)
            {
                spinnerCustomerContactPersonDecisionMaker.setSelection(0);
            }

            boolean isDocumentLanguageSelected = false;
            for(int i = 0; i < listOfDocumentLanguage.size(); i++)
            {
                if(contactPerson.getBelegsprache().equals(listOfDocumentLanguage.get(i).getSprache()))
                {
                    spinnerCustomerContactPersonDocumentLanguage.setSelection(i + 1);
                    selectedDocumentLanguage = listOfDocumentLanguage.get(i);
                    isDocumentLanguageSelected = true;
                    //selectedLegalForm = listOfLegalForm.get(i);
                    break;
                }
            }
            if(!isDocumentLanguageSelected)
            {
                spinnerCustomerContactPersonDocumentLanguage.setSelection(0);
            }
            selectedFeatureAdapter.notifyDataSetChanged();
            documentLanguageAdapter.notifyDataSetChanged();
            salutationAdapter.notifyDataSetChanged();
            decisionMakerAdapter.notifyDataSetChanged();
            textCustomerContactPersonTitle.setText(contactPerson.getTitel());
            textCustomerContactPersonFirstName.setText(contactPerson.getVorname());
            textCustomerContactPersonLastName.setText(contactPerson.getNachname());

            textCustomerContactPersonAdditionalInfo.setText(contactPerson.getZusatzinfo());
            textCustomerContactPersonPhone.setText(contactPerson.getTelefon());
            textCustomerContactPersonMobile.setText(contactPerson.getMobil());
            textCustomerContactPersonFax.setText(contactPerson.getTelefax());
            textCustomerContactPersonEmail.setText(contactPerson.getEmail());
            listOfSelectedFeatures.clear();
            listOfFeatures.clear();
            listOfSelectedFeatures.addAll(contactPerson.getFeatureList());
            //listOfFeatures.addAll(contactPerson.getFeatureList());
            listOfFeatures = db.getFeatures();
            selectedFeatureAdapter.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(listCustomerContactPersonFeature);
        }
    }

    private void setLanguage()
    {
        TextView labelCustomerContactPersonDetailsSalutation, labelCustomerContactPersonDetailsTitle, labelCustomerContactPersonDetailsFirstName,
                labelCustomerContactPersonDetailsLastName, labelCustomerContactPersonDetailsFunction, labelCustomerContactPersonDetailsDecisionMakers,
                labelCustomerContactPersonDetailsAdditionalInfo, labelCustomerContactPersonDetailsDocumentLanguage, labelCustomerContactPersonDetailsFeatures,
                labelCustomerContactPersonDetailsPhone, labelCustomerContactPersonDetailsFax, labelCustomerContactPersonDetailsMobile,
                labelCustomerContactPersonDetailsEmail, labelCustomerContactPersonGridHeaderSalutation, labelCustomerContactPersonGridHeaderFirstName,
                labelCustomerContactPersonGridHeaderFunction;

        labelCustomerContactPersonDetailsSalutation = (TextView)rootView.findViewById(R.id.labelCustomerContactPersonDetailsSalutation);
        labelCustomerContactPersonDetailsTitle = (TextView)rootView.findViewById(R.id.labelCustomerContactPersonDetailsTitle);
        labelCustomerContactPersonDetailsFirstName = (TextView)rootView.findViewById(R.id.labelCustomerContactPersonDetailsFirstName);
        labelCustomerContactPersonDetailsLastName = (TextView)rootView.findViewById(R.id.labelCustomerContactPersonDetailsLastName);
        labelCustomerContactPersonDetailsFunction = (TextView)rootView.findViewById(R.id.labelCustomerContactPersonDetailsFunction);
        labelCustomerContactPersonDetailsDecisionMakers = (TextView)rootView.findViewById(R.id.labelCustomerContactPersonDetailsDecisionMakers);
        labelCustomerContactPersonDetailsAdditionalInfo = (TextView)rootView.findViewById(R.id.labelCustomerContactPersonDetailsAdditionalInfo);
        labelCustomerContactPersonDetailsDocumentLanguage = (TextView)rootView.findViewById(R.id.labelCustomerContactPersonDetailsDocumentLanguage);
        labelCustomerContactPersonDetailsFeatures = (TextView)rootView.findViewById(R.id.labelCustomerContactPersonDetailsFeatures);
        labelCustomerContactPersonDetailsPhone = (TextView)rootView.findViewById(R.id.labelCustomerContactPersonDetailsPhone);
        labelCustomerContactPersonDetailsFax = (TextView)rootView.findViewById(R.id.labelCustomerContactPersonDetailsFax);
        labelCustomerContactPersonDetailsMobile = (TextView)rootView.findViewById(R.id.labelCustomerContactPersonDetailsMobile);
        labelCustomerContactPersonDetailsEmail = (TextView)rootView.findViewById(R.id.labelCustomerContactPersonDetailsEmail);

        checkboxCustomerContactPersonListHideRetired = (CheckBox)rootView.findViewById(R.id.checkboxCustomerContactPersonListHideRetired);

        labelCustomerContactPersonGridHeaderSalutation = (TextView)rootView.findViewById(R.id.labelCustomerContactPersonGridHeaderSalutation);
        labelCustomerContactPersonGridHeaderFirstName = (TextView)rootView.findViewById(R.id.labelCustomerContactPersonGridHeaderFirstName);
        labelCustomerContactPersonGridHeaderFunction = (TextView)rootView.findViewById(R.id.labelCustomerContactPersonGridHeaderFunction);

        labelCustomerContactPersonDetailsSalutation.setText(language.getLabelSalutation());
        labelCustomerContactPersonDetailsTitle.setText(language.getLabelTitle());
        labelCustomerContactPersonDetailsFirstName.setText(language.getLabelFirstName());
        labelCustomerContactPersonDetailsLastName.setText(language.getLabelLastName());
        labelCustomerContactPersonDetailsFunction.setText(language.getLabelFunction());
        labelCustomerContactPersonDetailsDecisionMakers.setText(language.getLabelDecisionMakers());
        labelCustomerContactPersonDetailsAdditionalInfo.setText(language.getLabelAdditionalInfo());
        labelCustomerContactPersonDetailsDocumentLanguage.setText(language.getLabelDocumentLanguage());
        labelCustomerContactPersonDetailsFeatures.setText(language.getLabelFeature());
        labelCustomerContactPersonDetailsPhone.setText(language.getLabelPhone());
        labelCustomerContactPersonDetailsFax.setText("Fax");
        labelCustomerContactPersonDetailsMobile.setText(language.getLabelMobile());
        labelCustomerContactPersonDetailsEmail.setText(language.getLabelEmail());
        labelCustomerContactPersonGridHeaderSalutation.setText(language.getLabelSalutation());
        labelCustomerContactPersonGridHeaderFirstName.setText(language.getLabelLastName());
        labelCustomerContactPersonGridHeaderFunction.setText(language.getLabelFunction());

        checkboxCustomerContactPersonListHideRetired.setText(language.getLabelHideRetired());
    }

    private void makeEditable(boolean editable)
    {
        spinnerCustomerContactPersonSalutation.setEnabled(editable);

        checkboxCustomerContactPersonListHideRetired.setEnabled(!editable);

        //checkboxCustomerContactPersonListHideRetired.setFocusable(editable);

        textCustomerContactPersonTitle.setFocusable(editable);
        textCustomerContactPersonTitle.setFocusableInTouchMode(editable);

        textCustomerContactPersonFirstName.setFocusable(editable);
        textCustomerContactPersonFirstName.setFocusableInTouchMode(editable);

        textCustomerContactPersonLastName.setFocusable(editable);
        textCustomerContactPersonLastName.setFocusableInTouchMode(editable);

        spinnerCustomerContactPersonFunction.setEnabled(editable);

        spinnerCustomerContactPersonDecisionMaker.setEnabled(editable);

        textCustomerContactPersonAdditionalInfo.setFocusable(editable);
        textCustomerContactPersonAdditionalInfo.setFocusableInTouchMode(editable);

        spinnerCustomerContactPersonDocumentLanguage.setEnabled(editable);

        textCustomerContactPersonPhone.setFocusable(editable);
        textCustomerContactPersonPhone.setFocusableInTouchMode(editable);

        textCustomerContactPersonFax.setFocusable(editable);
        textCustomerContactPersonFax.setFocusableInTouchMode(editable);

        textCustomerContactPersonMobile.setFocusable(editable);
        textCustomerContactPersonMobile.setFocusableInTouchMode(editable);

        textCustomerContactPersonEmail.setFocusable(editable);
        textCustomerContactPersonEmail.setFocusableInTouchMode(editable);

        if(editable)
        {
            buttonAddCustomerContactPersonFeature.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    showAddFeatureDialog();
                }
            });

            buttonRemoveCustomerContactPersonFeature.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(selectedFeatureAdapter.selectedIndex != -1 && listOfSelectedFeatures.size() > 0)
                    {
                        listOfRemainingFeature.add(listOfSelectedFeatures.get(selectedFeatureAdapter.selectedIndex));
                        listOfSelectedFeatures.remove(selectedFeatureAdapter.selectedIndex);
                        selectedFeatureAdapter.setSelectedIndex(-1);
                        listOfFeatureAdapter.notifyDataSetChanged();
                        selectedFeatureAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        showShortToast(language.getMessageSelectItemToRemove());
                    }
                }
            });
        }
        else
        {
            buttonAddCustomerContactPersonFeature.setOnClickListener(null);
            buttonRemoveCustomerContactPersonFeature.setOnClickListener(null);
        }
        listCustomerContactPersonFeature.setEnabled(editable);
        listViewCustomerContactPersonList.setEnabled(!editable);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);
        this.menu = menu;
        menu.findItem(R.id.actionSearch).setVisible(false);
        menu.findItem(R.id.actionWrong).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);
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
                    getActivity().finish();
                }
                else
                {
                    getFragmentManager().popBackStack();
                }
                return true;
            case R.id.actionEdit:
                if(selectedContactPerson != null)
                {
                    makeEditable(true);
                    menu.findItem(R.id.actionEdit).setVisible(false);
                    menu.findItem(R.id.actionRight).setVisible(true);
                    menu.findItem(R.id.actionWrong).setVisible(true);
                    menu.findItem(R.id.actionAdd).setVisible(false);
                    menu.findItem(R.id.actionBack).setVisible(false);
                    isInEditMode = true;
                }
                else
                {
                    showShortToast(language.getMessageNoCustomerContactPersonFound());
                }
                return true;
            case R.id.actionAdd:
                clearAllValues();
                menu.findItem(R.id.actionAdd).setVisible(false);
                menu.findItem(R.id.actionEdit).setVisible(false);
                menu.findItem(R.id.actionRight).setVisible(true);
                menu.findItem(R.id.actionWrong).setVisible(true);
                addNewContactPersonFormRedesign(true);
                makeEditable(true);
                return true;
            case R.id.actionRight:
                if(isInAddMode)
                {
                    addNewCustomerContactPerson();
                }
                else if(isInEditMode)
                {
                    updateCustomerContactPersonInfo();
                    menu.findItem(R.id.actionAdd).setVisible(true);
                    menu.findItem(R.id.actionEdit).setVisible(true);
                    menu.findItem(R.id.actionRight).setVisible(true);
                    menu.findItem(R.id.actionWrong).setVisible(false);
                    menu.findItem(R.id.actionBack).setVisible(true);
                    //showShortToast("Function will be implemented soon");
                }
                else
                {
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    Bundle args1 = new Bundle();
                    if(getArguments().containsKey("Pricing2AnsprechPartner"))
                    {
                        if(selectedContactPerson != null)
                        {
                            /*PricingFragment2 fragment2 = new PricingFragment2();
                            args1.putParcelable("ContactPerson", selectedContactPerson);
                            args1.putString("kanr", getArguments().getString("kanr"));
                            args1.putInt("branchId", getArguments().getInt("branchId"));
                            args1.putString("branchName", getArguments().getString("branchName"));
                            args1.putString("contactPersonNo", getArguments().getString("contactPersonNo"));
                            args1.putString("contactPersonName", getArguments().getString("contactPerson"));
                            args1.putString("deviceType", getArguments().getString("deviceType"));
                            args1.putString("equipmentIds", getArguments().getString("equipmentIds"));
                            args1.putString("EquipmentJson", getArguments().getString("EquipmentJson"));
                            args1.putInt("rental", getArguments().getInt("rental"));
                            args1.putInt("rentalDays", getArguments().getInt("rentalDays"));

                            PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo,
                                    new Gson().toJson(new PricingCustomerOrderBasicInfo()));

                            model.setContactPersonMobile(selectedContactPerson.getMobil());
                            model.setContactPersonName(selectedContactPerson.getNachname());
                            String jsonString = new Gson().toJson(model);
                            matecoPriceApplication.saveData(DataHelper.PricingCustomerBasicOrderInfo, jsonString);

                            args1.putString("EinsatzStrasse", getArguments().getString("EinsatzStrasse"));
                            args1.putString("EinsatzPLZ", getArguments().getString("EinsatzPLZ"));
                            args1.putString("Einsatzort", getArguments().getString("Einsatzort"));
                            args1.putString("Zusatz", getArguments().getString("Zusatz"));
                            args1.putString("Entfernung", getArguments().getString("Entfernung"));
                            fragment2.setArguments(args1);
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            //getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            getActivity().getSupportFragmentManager().popBackStack();
                            transaction.replace(R.id.content_frame, fragment2);
                            transaction.addToBackStack("Pricing Contact Person");
                            transaction.commit();*/


                            PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo,
                                    new Gson().toJson(new PricingCustomerOrderBasicInfo()));

                            model.setContactPersonMobile(selectedContactPerson.getMobil());
                            model.setContactPersonName(selectedContactPerson.getNachname());
                            String jsonString = new Gson().toJson(model);
                            matecoPriceApplication.saveData(DataHelper.PricingCustomerBasicOrderInfo, jsonString);
                            if (getFragmentManager().getBackStackEntryCount() == 0)
                            {

                                getActivity().finish();

                            }
                            else
                            {
                                getFragmentManager().popBackStack();
                            }


                        }
                        else
                        {
                            showShortToast(language.getMessageNoCustomerSelected());
                        }
                    }
                    else
                    {
                        if(selectedContactPerson != null)
                        {
                            SiteInspectionNewFragment fragment = new SiteInspectionNewFragment();
                            if (getArguments().containsKey("SiteInspectionContactPerson"))
                            {
                                //args1.putParcelable("AnsprechPartnerBack", getArguments().getParcelable("AnsprechPartnerBack"));
                                //args1.putParcelable("ContactPerson", selectedContactPerson);
                            }
                            else
                            {
                                //args1.putParcelable("ContactPersonBack", getArguments().getParcelable("ContactPersonBack"));
                                //args1.putParcelable("AnsprechPartner", selectedContactPerson);
                            }
                            fragment.setArguments(args1);
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            transaction.replace(R.id.content_frame, fragment);
                            transaction.addToBackStack("Site Inspection Contact Person");
                            transaction.commit();
                        }
                        else
                        {
                            showShortToast(language.getMessageNoCustomerSelected());
                        }
                    }
                }
                return true;
            case R.id.actionWrong:
                if(checkIfTextChanged(selectedContactPerson))
                {
                    DialogInterface.OnClickListener positiveCallback = new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            addNewContactPersonFormRedesign(false);
                            menu.findItem(R.id.actionEdit).setVisible(true);
                            menu.findItem(R.id.actionRight).setVisible(false);
                            menu.findItem(R.id.actionWrong).setVisible(false);
                            menu.findItem(R.id.actionAdd).setVisible(true);
                            menu.findItem(R.id.actionBack).setVisible(true);
                            makeEditable(false);
                            isInEditMode = false;
                            setCustomerContactPerson(selectedContactPerson);
                            dialog.cancel();
                        }
                    };

                    DialogInterface.OnClickListener negativeCallback = new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.cancel();
                        }
                    };
                    String title = language.getLabelConfirmation();
                    String message = language.getLabelDelete();
                    if(isInAddMode)
                    {
                        message = language.getMessageCancelAddingContactPerson();
                    }
                    AlertDialog alert = showAlert(title, message, language.getLabelYes(), language.getLabelNo(),
                            positiveCallback, negativeCallback);
                    alert.show();
                }
                else
                {
                    addNewContactPersonFormRedesign(false);
                    menu.findItem(R.id.actionEdit).setVisible(true);
                    menu.findItem(R.id.actionRight).setVisible(false);
                    menu.findItem(R.id.actionWrong).setVisible(false);
                    menu.findItem(R.id.actionAdd).setVisible(true);
                    menu.findItem(R.id.actionBack).setVisible(true);
                    makeEditable(false);
                    isInEditMode = false;
                    setCustomerContactPerson(selectedContactPerson);
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    private void showAddFeatureDialog()
    {
        final Dialog dialog = showCustomDialog(R.layout.dialog_add_feature, "Select Feature");
        listOfRemainingFeature.clear();
        final ListView listViewAlertSelectFeature = (ListView)dialog.findViewById(R.id.listViewAlertSelectFeature);
        listViewAlertSelectFeature.setAdapter(listOfFeatureAdapter);
        listOfRemainingFeature.addAll(removeSelectedFeatures(listOfFeatures, listOfSelectedFeatures));
        listOfFeatureAdapter.notifyDataSetChanged();
        Button buttonDialogAddFeature, buttonDialogAddFeatureCancel;

        buttonDialogAddFeature = (Button)dialog.findViewById(R.id.buttonDialogAddFeature);
        buttonDialogAddFeatureCancel = (Button)dialog.findViewById(R.id.buttonDialogAddFeatureCancel);

        listViewAlertSelectFeature.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //view.setSelected(true);
                listOfFeatureAdapter.setSelectedIndex(position);
            }
        });
        buttonDialogAddFeature.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(listOfFeatureAdapter.selectedIndex != -1)
                {
                    listOfSelectedFeatures.add(listOfRemainingFeature.get(listOfFeatureAdapter.selectedIndex));
                    listOfRemainingFeature.remove(listOfFeatureAdapter.selectedIndex);
                    listOfFeatureAdapter.notifyDataSetChanged();
                    selectedFeatureAdapter.notifyDataSetChanged();
                    setListViewHeightBasedOnChildren(listCustomerContactPersonFeature);
                    listOfFeatureAdapter.setSelectedIndex(-1);
                    dialog.dismiss();
                }
                else
                {
                    showShortToast(language.getMessageSelectItemToRemove());
                }
            }
        });

        buttonDialogAddFeatureCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showShortToast(language.getLabelCancel());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private ArrayList<CustomerContactPersonFeatureListItem> removeSelectedFeatures(ArrayList<CustomerContactPersonFeatureListItem> listOfFeatures, ArrayList<CustomerContactPersonFeatureListItem> selectedFeature)
    {
        for(int i = 0; i < selectedFeature.size(); i++)
        {
            for(int j = 0; j < listOfFeatures.size(); j++)
            {
                if(listOfFeatures.get(j).getMerkmal().equals(selectedFeature.get(i).getMerkmal()))
                {
                    listOfFeatures.remove(j);
                }
            }
        }
        return listOfFeatures;
    }

    private void addNewContactPersonFormRedesign(boolean editable)
    {
        if(editable)
        {
            linearLayoutCustomerContactPersonList.setVisibility(View.INVISIBLE);
        }
        else
        {
            linearLayoutCustomerContactPersonList.setVisibility(View.VISIBLE);
        }
        isInAddMode = editable;
    }

    private void addNewCustomerContactPerson()
    {
        String salutation = "";
        if(selectedSalutation != null)
        {
            salutation = selectedSalutation.getAnrede();
        }
        String title = textCustomerContactPersonTitle.getText().toString();
        String firstName = textCustomerContactPersonFirstName.getText().toString();
        String lastName = textCustomerContactPersonLastName.getText().toString();

        String function = "";
        if(selectedFunction != null)
        {
            function = selectedFunction.getFunktion();
        }
        String decisionMaker = "";
        if(selectedDecisionMaker != null)
        {
            decisionMaker = selectedDecisionMaker.getEntscheider();
        }
        String additionalInfo = textCustomerContactPersonAdditionalInfo.getText().toString();
        String documentLanguage = "";
        if(selectedDocumentLanguage != null)
        {
            documentLanguage = selectedDocumentLanguage.getSprache();
        }
        String telephone = textCustomerContactPersonPhone.getText().toString();
        String mobile = textCustomerContactPersonMobile.getText().toString();
        String fax = textCustomerContactPersonFax.getText().toString();
        String email = textCustomerContactPersonEmail.getText().toString();

        CustomerContactPersonAndFeatureAddModel customerContactPersonAndFeatureAddModel = new CustomerContactPersonAndFeatureAddModel();

        //CustomerContactPersonAddModel customerContactPersonAddModel = new CustomerContactPersonAddModel();
        customerContactPersonAndFeatureAddModel.setAnrede(salutation);
        customerContactPersonAndFeatureAddModel.setTitel(title);
        customerContactPersonAndFeatureAddModel.setNachname(lastName);
        customerContactPersonAndFeatureAddModel.setVorname(firstName);
        customerContactPersonAndFeatureAddModel.setFunktion(function);
        customerContactPersonAndFeatureAddModel.setTelefon(telephone);
        customerContactPersonAndFeatureAddModel.setTelefax(fax);
        customerContactPersonAndFeatureAddModel.setMobil(mobile);
        customerContactPersonAndFeatureAddModel.setEmail(email);
        customerContactPersonAndFeatureAddModel.setUserID(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserId());
        customerContactPersonAndFeatureAddModel.setZusatzinfo(additionalInfo);
        customerContactPersonAndFeatureAddModel.setEntscheider(decisionMaker);
        String loginUserId = matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserId();
        customerContactPersonAndFeatureAddModel.setAenderungMitarbeiter(matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new LoginPersonModel().toString()).get(0).getUserNumber());
        customerContactPersonAndFeatureAddModel.setKontakt(matecoPriceApplication.getLoadedCustomer(DataHelper.LoadedCustomer, new CustomerModel().toString()).getKontakt());

        //customerContactPersonAndFeatureAddModel.setCustomerContactPersonAddModel(customerContactPersonAddModel);

        ArrayList<String> listOfSelectedFeaturesId = new ArrayList<>();
        for(int i = 0; i < listOfSelectedFeatures.size(); i++)
        {
            listOfSelectedFeaturesId.add(listOfSelectedFeatures.get(i).getMerkmal());
        }
        customerContactPersonAndFeatureAddModel.setMerkmal(listOfSelectedFeaturesId);

        String json = new Gson().toJson(customerContactPersonAndFeatureAddModel);
        LogApp.showLog("json", json);
//        if(selectedSalutation == 0)
//        {
//
//        }
        textCustomerContactPersonFirstName.setError(null);
        if(!DataHelper.isValidBlankMail(email))
        {
            textCustomerContactPersonEmail.setError("Invalid");
            textCustomerContactPersonEmail.requestFocus();
        }
        else if(selectedSalutation == null)
        {
            showShortToast(language.getLabelRequired() + " " + language.getLabelSalutation());
        }
        else if(lastName.equals(""))
        {
            textCustomerContactPersonLastName.setError(language.getLabelRequired());
            textCustomerContactPersonLastName.requestFocus();
        }
        else if(selectedFunction == null)
        {
            showShortToast(language.getLabelRequired() + " " + language.getLabelFunction());
        }
        else if(telephone.equals(""))
        {
            textCustomerContactPersonPhone.setError(language.getLabelRequired());
            textCustomerContactPersonPhone.requestFocus();
        }
        else if(!DataHelper.phoneNumberValidationGerman(telephone))
        {
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
            showMessage(language.getLabelAlert(),language.getMessagePhoneValidation(),language.getLabelOk(),listener).show();
            // textCustomerNewPhone.setError(language.getMessageNotValidNumber());
            textCustomerContactPersonPhone.requestFocus();
        }
        else if(DataHelper.isNetworkAvailable(getActivity()))
        {
            AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult()
            {
                @Override
                public void OnAsynResult(String result)
                {
                    if(result.trim().equals("error"))
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
                            listOfContactPerson.clear();
                            showShortToast(language.getMessageContactPersonInserted());
                            ContactPersonModel.extractFromJson(result, listOfContactPerson);
                            String json = new Gson().toJson(listOfContactPerson);
                            if(listOfContactPerson.size() > 0)
                            {
                                customerContactPersonListDetailAdapter.setSelectedIndex(0);
                            }
                            customerContactPersonListDetailAdapter.notifyDataSetChanged();
                            matecoPriceApplication.saveData(DataHelper.LoadedCustomerContactPerson, json);
                            menu.findItem(R.id.actionAdd).setVisible(false);
                            menu.findItem(R.id.actionEdit).setVisible(false);
                            menu.findItem(R.id.actionRight).setVisible(true);
                            menu.findItem(R.id.actionWrong).setVisible(false);
                            menu.findItem(R.id.actionBack).setVisible(true);
                            clearAllValues();
                            addNewContactPersonFormRedesign(false);
                            makeEditable(false);
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                            showShortToast(language.getMessageErrorAtParsing());
                        }
                    }
                }
            };
            //customerContactPersonAndFeatureAddModel.get
            try
            {
                /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.INSERT_CUSTOMER_CONTACT_PERSON + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                url += "&customercontactinsert=" + URLEncoder.encode(json, "UTF-8");
                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                asyncTask.execute();*/
                String url = DataHelper.URL_CUSTOMER_HELPER+"customercontactinsert";
                MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")));
                multipartEntity.addPart("customercontactinsert", new StringBody(json, Charset.forName("UTF-8")));
                AsyncTaskWithAuthorizationHeaderPost asyncTaskPost = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, getActivity(), multipartEntity, true, language);
                asyncTaskPost.execute();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            showShortToast(language.getMessageNetworkNotAvailable());
        }
    }

    private void updateCustomerContactPersonInfo()
    {
        String salutation = "";
        if(selectedSalutation != null)
            salutation = selectedSalutation.getAnrede();
        String title = textCustomerContactPersonTitle.getText().toString();
        String firstName = textCustomerContactPersonFirstName.getText().toString();
        String lastName = textCustomerContactPersonLastName.getText().toString();
        String function = "";
        if(selectedFunction != null)
            function = selectedFunction.getFunktion();
        String decisionMaker = "";
        if(selectedDecisionMaker != null)
            decisionMaker = selectedDecisionMaker.getEntscheider();
        String additionalInfo = textCustomerContactPersonAdditionalInfo.getText().toString();
        String documentLanguage = "";
        if(selectedDocumentLanguage != null)
            documentLanguage = selectedDocumentLanguage.getSprache();
        String telephone = textCustomerContactPersonPhone.getText().toString();
        String mobile = textCustomerContactPersonMobile.getText().toString();
        final String fax = textCustomerContactPersonFax.getText().toString();
        String email = textCustomerContactPersonEmail.getText().toString();

        if(!DataHelper.isValidBlankMail(email))
        {
            textCustomerContactPersonEmail.setError("Invalid");
            textCustomerContactPersonEmail.requestFocus();
        }
        else if(selectedSalutation == null)
        {
            showShortToast(language.getLabelRequired() + " " + language.getLabelSalutation());
        }
        else if(lastName.equals(""))
        {
            textCustomerContactPersonLastName.setError(language.getLabelRequired());
            textCustomerContactPersonLastName.requestFocus();
        }
        else if(selectedFunction == null)
        {
            showShortToast(language.getLabelRequired() + " " + language.getLabelFunction());
        }
        else if(telephone.equals(""))
        {
            textCustomerContactPersonPhone.setError(language.getLabelRequired());
            textCustomerContactPersonPhone.requestFocus();
        }
        else if(!DataHelper.phoneNumberValidationGerman(telephone))
        {
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
            showMessage(language.getLabelAlert(),language.getMessagePhoneValidation(),language.getLabelOk(),listener).show();
            // textCustomerNewPhone.setError(language.getMessageNotValidNumber());
            textCustomerContactPersonPhone.requestFocus();
        }
        else if(DataHelper.isNetworkAvailable(getActivity()))
        {
            AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult onAsyncResult = new AsyncTaskWithAuthorizationHeaderPost.OnAsyncResult()
            {
                @Override
                public void OnAsynResult(String result)
                {
                    if(result.trim().equals("error"))
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
                            listOfContactPerson.clear();
                            ContactPersonModel.extractFromJson(result, listOfContactPerson);
                            String json = new Gson().toJson(listOfContactPerson);
                            matecoPriceApplication.saveData(DataHelper.LoadedCustomerContactPerson, json);
                            showShortToast(language.getMessageCustomerupdated());
                            clearAllValues();
                            if(listOfContactPerson.size() > 0)
                                customerContactPersonListDetailAdapter.setSelectedIndex(0);
                            customerContactPersonListDetailAdapter.notifyDataSetChanged();
                            menu.findItem(R.id.actionAdd).setVisible(true);
                            menu.findItem(R.id.actionEdit).setVisible(true);
                            menu.findItem(R.id.actionRight).setVisible(false);
                            menu.findItem(R.id.actionWrong).setVisible(false);

                            menu.findItem(R.id.actionAdd).setVisible(true);
                            menu.findItem(R.id.actionEdit).setVisible(true);
                            menu.findItem(R.id.actionRight).setVisible(true);
                            menu.findItem(R.id.actionWrong).setVisible(false);
                            menu.findItem(R.id.actionBack).setVisible(true);

                            makeEditable(false);
                            selectedContactPerson = listOfContactPerson.get(0);
                            setCustomerContactPerson(selectedContactPerson);
                            isInAddMode=false;
                            isInEditMode=false;

                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                            showShortToast(language.getMessageErrorAtParsing());
                        }
                    }
                }
            };

            //CustomerContactPersonAndFeatureAddModel customerContactPersonAndFeatureAddModel = new CustomerContactPersonAndFeatureAddModel();

            CustomerContactPersonAndFeatureUpdateModel customerContactPersonAndFeatureUpdateModel = new CustomerContactPersonAndFeatureUpdateModel();
            customerContactPersonAndFeatureUpdateModel.setAnrede(salutation);
            customerContactPersonAndFeatureUpdateModel.setTitel(title);
            customerContactPersonAndFeatureUpdateModel.setVorname(firstName);
            customerContactPersonAndFeatureUpdateModel.setNachname(lastName);
            customerContactPersonAndFeatureUpdateModel.setFunktion(function);
            customerContactPersonAndFeatureUpdateModel.setTelefon(telephone);
            customerContactPersonAndFeatureUpdateModel.setTelefax(fax);
            customerContactPersonAndFeatureUpdateModel.setMobil(mobile);
            customerContactPersonAndFeatureUpdateModel.setEmail(email);
            customerContactPersonAndFeatureUpdateModel.setAnsPartner(selectedContactPerson.getAnspartner());
            customerContactPersonAndFeatureUpdateModel.setKontakt(selectedContactPerson.getKontakt());

            /* added on 7th july becoz service need this paramter otherwise was not giving response */
            if(textCustomerContactPersonAdditionalInfo.getText().toString().equalsIgnoreCase("")){
                customerContactPersonAndFeatureUpdateModel.setZusatzinfo("");
            }else {
                customerContactPersonAndFeatureUpdateModel.setZusatzinfo(textCustomerContactPersonAdditionalInfo.getText().toString());
            }

            ArrayList<String> listOfSelectedFeaturesId = new ArrayList<>();
            for(int i = 0; i < listOfSelectedFeatures.size(); i++)
            {
                listOfSelectedFeaturesId.add(listOfSelectedFeatures.get(i).getMerkmal());
            }
            customerContactPersonAndFeatureUpdateModel.setMerkmal(listOfSelectedFeaturesId);
            //customerContactPersonAndFeatureAddModel.setCustomerContactPersonAddModel(customerContactPersonAddModel);

            String json = new Gson().toJson(customerContactPersonAndFeatureUpdateModel);
            //customerContactPersonAndFeatureAddModel.get
            try
            {
                /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.UPDATE_CUSTOMER_CONTACT_PERSON + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                url += "&customercontactupdate=" + URLEncoder.encode(json, "UTF-8");

                BasicAsyncTaskGetRequest asyncTask = new BasicAsyncTaskGetRequest(url, onAsyncResult, getActivity(), true);
                asyncTask.execute();*/
                String url = DataHelper.URL_CUSTOMER_HELPER+"customercontactupdate";
                MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                multipartEntity.addPart("token", new StringBody(URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")));
                multipartEntity.addPart("customercontactupdate", new StringBody(json, Charset.forName("UTF-8")));
                AsyncTaskWithAuthorizationHeaderPost asyncTaskPost = new AsyncTaskWithAuthorizationHeaderPost(url, onAsyncResult, getActivity(), multipartEntity, true, language);
                asyncTaskPost.execute();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            showShortToast(language.getMessageNetworkNotAvailable());
        }
    }

    private void clearAllValues()
    {
        selectedSalutation = null;
        spinnerCustomerContactPersonSalutation.setSelection(0);

        selectedFunction = null;
        spinnerCustomerContactPersonFunction.setSelection(0);

        selectedDecisionMaker = null;
        spinnerCustomerContactPersonDecisionMaker.setSelection(0);

        selectedDocumentLanguage = null;
        spinnerCustomerContactPersonDocumentLanguage.setSelection(0);

        listOfSelectedFeatures.clear();
        selectedFeatureAdapter.notifyDataSetChanged();
        documentLanguageAdapter.notifyDataSetChanged();
        salutationAdapter.notifyDataSetChanged();
        decisionMakerAdapter.notifyDataSetChanged();
        listOfRemainingFeature.addAll(listOfFeatures);
        super.clearAll();
    }

    private boolean checkIfTextChanged(ContactPersonModel contactPerson)
    {
        if(contactPerson != null)
        {
            String salutation = "";
            if(selectedSalutation != null)
                salutation = selectedSalutation.getAnrede();
            String title = textCustomerContactPersonTitle.getText().toString();
            String firstName = textCustomerContactPersonFirstName.getText().toString();
            String lastName = textCustomerContactPersonLastName.getText().toString();
            String function = "";
            if(selectedFunction != null)
                function = selectedFunction.getFunktion();
            String decisionMaker = "";
            if(selectedDecisionMaker != null)
                decisionMaker = selectedDecisionMaker.getEntscheider();
            String additionalInfo = textCustomerContactPersonAdditionalInfo.getText().toString();
            String documentLanguage = "";
            if(selectedDocumentLanguage != null)
                documentLanguage = selectedDocumentLanguage.getSprache();
            String telephone = textCustomerContactPersonPhone.getText().toString();
            String mobile = textCustomerContactPersonMobile.getText().toString();
            String fax = textCustomerContactPersonFax.getText().toString();
            String email = textCustomerContactPersonEmail.getText().toString();

            String defaultSalutation = contactPerson.getAnredeID();
            String defaultTitle = contactPerson.getTitel();
            String defaultFirstName = contactPerson.getVorname();
            String defaultLastName = contactPerson.getNachname();
            String defaultFunction = contactPerson.getFunktionID();
            String defaultDecisionMaker = contactPerson.getEntscheiderID();
            String defaultAdditionalInfo = contactPerson.getZusatzinfo();
            String defaultDocumentLanguage = contactPerson.getBelegsprache();
            String defaultTelephone = contactPerson.getTelefon();
            String defaultMobile = contactPerson.getMobil();
            String defaultFax = contactPerson.getTelefax();
            String defaultEmail = contactPerson.getEmail();
            ArrayList<CustomerContactPersonFeatureListItem> defaultSelectedFeatures = contactPerson.getFeatureList();
            if((salutation.equals(defaultSalutation)) && (title.equals(defaultTitle)) && (firstName.equals(defaultFirstName)) &&
                    (lastName.equals(defaultLastName)) && (function.equals(defaultFunction)) && (decisionMaker.equals(defaultDecisionMaker)) &&
                    (additionalInfo.equals(defaultAdditionalInfo)) && (documentLanguage.equals(defaultDocumentLanguage)) &&
                    (telephone.equals(defaultTelephone)) && (mobile.equals(defaultMobile)) && (fax.equals(defaultFax)) &&
                    (email.equals(defaultEmail)) && defaultSelectedFeatures.size() == listOfSelectedFeatures.size())
            {
                for(int i = 0; i < defaultSelectedFeatures.size(); i++)
                {
                    boolean isEqual = false;
                    for(int j = 0; j < listOfSelectedFeatures.size(); j++)
                    {
                        if(defaultSelectedFeatures.get(i).getMerkmal().equals(listOfSelectedFeatures.get(j).getMerkmal()))
                        {
                            isEqual = true;
                            break;
                        }
                    }
                    if(!isEqual)
                    {
                        return true;
                    }
                }
                return false;
            }
            else
            {
                return true;
            }
        }
        return true;
    }
}