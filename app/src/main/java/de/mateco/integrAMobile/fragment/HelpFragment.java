package de.mateco.integrAMobile.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import de.mateco.integrAMobile.Helper.Constants;
import de.mateco.integrAMobile.Helper.GlobalMethods;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;

public class HelpFragment extends BaseFragment implements View.OnClickListener
{
    private Button buttonSendDataToAdmin, buttonSupportEmail;
    private View rootView;
    private TextView labelSupportPhoneNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        rootView = inflater.inflate(R.layout.fragment_setting_help, container, false);
        super.initializeFragment(rootView);
        return rootView;
    }

    @Override
    public void initializeComponents(View rootView) {
        super.initializeComponents(rootView);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);

        buttonSendDataToAdmin = (Button)rootView.findViewById(R.id.buttonSendDataToAdmin);
        buttonSupportEmail = (Button)rootView.findViewById(R.id.buttonSupportEmail);
        ImageView imageViewCallSupport = (ImageView)rootView.findViewById(R.id.imageViewCallSupport);
        labelSupportPhoneNo = (TextView)rootView.findViewById(R.id.labelSupportPhoneNo);
        labelSupportPhoneNo.setText(Constants.PHONE_NUMBER_SUPPORT);
        setLanguage();
        imageViewCallSupport.setOnClickListener(this);
    }

    @Override
    public void bindEvents(View rootView) {
        super.bindEvents(rootView);
        buttonSendDataToAdmin.setOnClickListener(this);
        buttonSupportEmail.setOnClickListener(this);
    }

    private void setLanguage()
    {
        TextView labelSendDataToAdmin, labelSendDataForAnalysis, labelForSupportCallHeading, labelForSupportCallInfo, labelForSupportEmailHeading, labelForSupportEmailInfo,
                    labelAppVersion, labelValueAppVersion;
        labelSendDataToAdmin = (TextView)rootView.findViewById(R.id.labelSendDataToAdmin);
        labelSendDataForAnalysis = (TextView)rootView.findViewById(R.id.labelSendDataForAnalysis);
        labelForSupportCallHeading = (TextView)rootView.findViewById(R.id.labelForSupportCallHeading);
        labelForSupportCallInfo = (TextView)rootView.findViewById(R.id.labelForSupportCallInfo);
        labelForSupportEmailHeading = (TextView)rootView.findViewById(R.id.labelForSupportEmailHeading);
        labelForSupportEmailInfo = (TextView)rootView.findViewById(R.id.labelForSupportEmailInfo);

        labelSendDataToAdmin.setText(language.getLabelSendDataToAdmin());
        labelSendDataForAnalysis.setText(language.getLabelSendDataForAnalysis());
        labelForSupportCallHeading.setText(language.getLabelForSupportCallHeading());
        labelForSupportCallInfo.setText(language.getLabelForSupportCallInfo());
        labelForSupportEmailInfo.setText(language.getLabelForSupportCallInfo());
        labelForSupportEmailHeading.setText(language.getLabelForSupportCallHeading());
        buttonSendDataToAdmin.setText(language.getLabelSendDataToAdmin());
        buttonSupportEmail.setText(language.getButtonSupportEmail());
        //buttonContactAdmin.setText();
        labelAppVersion = (TextView)rootView.findViewById(R.id.labelAppVersion);
        labelValueAppVersion = (TextView)rootView.findViewById(R.id.labelValueAppVersion);

        labelAppVersion.setText(language.getLabelAppVersion());

        labelValueAppVersion.setText(GlobalMethods.getAppVersion(context));

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);
        menu.clear();
        //return true;
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.actionSettings:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new SettingFragment(),"Setting");
                //transaction.addToBackStack(SettingFragment.Tag);
                transaction.addToBackStack("Setting");
                transaction.commit();
                return true;
            case R.id.actionBack:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view)
    {
         switch (view.getId())
         {
             case R.id.buttonSendDataToAdmin:
                 GlobalMethods.sendBackupToUs(context);
                 break;
             case R.id.imageViewCallSupport:
                 if ( Build.VERSION.SDK_INT >= 23)
                 {
                     if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) ==
                             PackageManager.PERMISSION_GRANTED)
                     {
                         GlobalMethods.callToNumber(context, labelSupportPhoneNo);
                     }
                     else{
                         ActivityCompat.requestPermissions(getActivity(), new String[] {
                                         Manifest.permission.CALL_PHONE},
                                 80);
                     }
                 }
                 else {
                     GlobalMethods.callToNumber(context, labelSupportPhoneNo);
                 }

                 break;
             case R.id.buttonSupportEmail:
                 GlobalMethods.sendSupportEmail(context);
                 break;
         }
    }
}
