package de.mateco.integrAMobile.fragment;

import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.model.ChangePasswordModel;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.LoginPersonModel;

public class SettingAccountFragment extends BaseFragment implements View.OnClickListener
{
    private View rootView;
    private Button buttonChangePassword;
    private MatecoPriceApplication matecoPriceApplication;
    private Language language;
    private EditText textEmailAddress, textPassword, textConfirmPassword, textCurrentPassword;//, textUserName, textLocation, textCountry;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        rootView = inflater.inflate(R.layout.fragment_setting_account, container, false);
        //rootView = inflater.inflate(R.layout.temp_under_construction, container, false);
        super.initializeFragment(rootView);

        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        //((HomeActivity)getActivity()).getSupportActionBar().setTitle("Setting Account");
        return rootView;
    }

    @Override
    public void initializeComponents(View rootView)
    {
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();

        textEmailAddress = (EditText)rootView.findViewById(R.id.textEmailAddress);
        textPassword = (EditText)rootView.findViewById(R.id.textPassword);
        textConfirmPassword = (EditText)rootView.findViewById(R.id.textConfirmPassword);
        textCurrentPassword = (EditText)rootView.findViewById(R.id.textCurrentPassword);

        buttonChangePassword = (Button)rootView.findViewById(R.id.buttonChangePassword);

        buttonChangePassword.setOnClickListener(this);

//        textUserName = (EditText)rootView.findViewById(R.id.textUserName);
//        textLocation = (EditText)rootView.findViewById(R.id.textLocation);
//        textCountry = (EditText)rootView.findViewById(R.id.textCountry);

        setupLanguage();

        loadDefaultValues();
        super.initializeComponents(rootView);
    }

    @Override
    public void bindEvents(View rootView)
    {
        super.bindEvents(rootView);
    }

    private void setupLanguage()
    {
        TextView labelEmailAddress, labelPassword, labelConfirmPassword, labelChangePassword, labelCurrentPassword;
        //labelCountry, labelLocation, labelUserName, labelUserName,
        buttonChangePassword.setText(language.getLabelChangePassword());
        labelChangePassword = (TextView)rootView.findViewById(R.id.labelChangePassword);
        labelCurrentPassword = (TextView)rootView.findViewById(R.id.labelCurrentPassword);
        labelChangePassword.setText(language.getLabelChangePassword());
        labelEmailAddress = (TextView)rootView.findViewById(R.id.labelEmailAddress);
        labelPassword = (TextView)rootView.findViewById(R.id.labelPassword);
        labelConfirmPassword = (TextView)rootView.findViewById(R.id.labelConfirmPassword);
        //labelUserName = (TextView)rootView.findViewById(R.id.labelUserName);
//        labelCountry = (TextView)rootView.findViewById(R.id.labelCountry);
//        labelLocation = (TextView)rootView.findViewById(R.id.labelLocation);


        //labelUserName.setText(language.getLabelUsername());
//        labelCountry.setText(language.getLabelCountry());
//        labelLocation.setText(language.getLabelLocation());
        labelEmailAddress.setText(language.getLabelEmailAddress());
        if(language.getLangCode().equals("de")) {
            labelPassword.setText("Neues " + language.getLabelPassword());
            textPassword.setHint("Neues " +  language.getLabelPassword());
        }
        else {
            labelPassword.setText("New " + language.getLabelPassword());
            textPassword.setHint("New " +  language.getLabelPassword());

        }

        labelConfirmPassword.setText(language.getLabelConfirmPassword());
        labelCurrentPassword.setText(language.getLabelCurrentPassword());
        textConfirmPassword.setHint(language.getLabelConfirmPassword());
        textEmailAddress.setHint(language.getLabelEmailAddress());


        textCurrentPassword.setHint(language.getLabelCurrentPassword());
//        textUserName.setHint(language.getLabelUsername());
//        textCountry.setHint(language.getLabelCountry());
//        textLocation.setHint(language.getLabelLocation());
    }

    private void loadDefaultValues()
    {
        LoginPersonModel loginPerson = new LoginPersonModel();
        loginPerson = matecoPriceApplication.getLoginUser(DataHelper.LoginPerson, new ArrayList<LoginPersonModel>().toString()).get(0);
        textEmailAddress.setText(loginPerson.getUserEmail());
//        textUserName.setText(loginPerson.getUserName());
//        textLocation.setText(loginPerson.getLocation());
//        textCountry.setText(loginPerson.getCountry());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);
        //menu.clear();
        menu.findItem(R.id.actionSearch).setVisible(false);
        menu.findItem(R.id.actionWrong).setVisible(false);
        menu.findItem(R.id.actionRight).setVisible(false);
        menu.findItem(R.id.actionAdd).setVisible(false);
        menu.findItem(R.id.actionEdit).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);
        //menu.findItem(R.id.actionForwardSpace).setVisible(false);
        menu.findItem(R.id.actionSettings).setVisible(false);
        //return true;
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.actionSettings:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
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
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonChangePassword:
                changePassword();
                break;
        }
    }

    private void changePassword()
    {
        String emailAddress = textEmailAddress.getText().toString().trim();
        String password = textPassword.getText().toString();
        String confirmPassword = textConfirmPassword.getText().toString();
        String currentPassword = textCurrentPassword.getText().toString();
        if(currentPassword.length() == 0)
        {
            textCurrentPassword.setError(language.getLabelRequired());
            textCurrentPassword.requestFocus();
        }
        else if(password.length() == 0)
        {
            textPassword.setError(language.getLabelRequired());
            textPassword.requestFocus();
        }
        else if(password.length() < 5)
        {
            textPassword.setError(language.getMessagePasswordLengthMustBeGreaterThen());
            textPassword.requestFocus();
        }
        else if(!password.equals(confirmPassword))
        {
            textPassword.setError(language.getMessageBothPasswordMustMatch());
            textPassword.requestFocus();
        }
        else
        {
            if(DataHelper.isNetworkAvailable(getActivity()))
            {
                BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                {
                    @Override
                    public void OnAsynResult(String result)
                    {
                        Log.e("result change passowrd", result);
                        if(result.trim().replace("\"", "").equals("Password Changed Successfully...."))
                        {
                            showShortToast(language.getMessagePasswordChangedSuccessfully());
                            textPassword.setText("");
                            textConfirmPassword.setText("");
                            textCurrentPassword.setText("");
                        }
                        else if(result.trim().replace("\"", "").equals("Incorrent Email or Current Password...!"))
                        {
                            showShortToast(language.getMessagePasswordChangedSuccessfully());
                        }
                        else
                        {
                            showShortToast(language.getMessageError());
                        }
                    }
                };

                try
                {
                    ChangePasswordModel model =new ChangePasswordModel();
                    model.setCurrentpassword(currentPassword);
                    model.setEmail(emailAddress);
                    model.setNewpassword(password);
                    String jsonString = new Gson().toJson(model);
                    Log.e("change password", jsonString);
                    /*String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.CHANGE_CURRENT_PASSWORD
                            + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                            + "&changepasswordparam=" + URLEncoder.encode(jsonString, "UTF-8");*/
                    String url = DataHelper.URL_USER_HELPER +"changecurrentpassword/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8")
                            + "/changepasswordparam=" + URLEncoder.encode(jsonString, "UTF-8");
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
            }
        }
    }
}
