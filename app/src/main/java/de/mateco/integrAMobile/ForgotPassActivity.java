package de.mateco.integrAMobile;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.GlobalClass;
import de.mateco.integrAMobile.asyncTask.BasicAsyncTaskGetRequest;
import de.mateco.integrAMobile.base.BaseActivity;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.model.Language;


public class ForgotPassActivity extends BaseActivity implements View.OnClickListener
{
    private MatecoPriceApplication application;
    private Language language;
    private EditText textForgotPasswordUserName;
    private Button buttonForgotPassword;
    private TextView labelLoginTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        GlobalClass.retainOrientation(ForgotPassActivity.this);
        super.initializeActivity();
    }

    @Override
    public void initializeComponents()
    {
        super.initializeComponents();
        application = (MatecoPriceApplication)getApplication();
        language = application.getLanguage();

        Window window= this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if(DataHelper.APP_NAME.equalsIgnoreCase("integrAMobile/MatecoSalesAppService.svc/json/")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(this.getResources().getColor(R.color.primary_dark));
            }

        }
        else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                window.setStatusBarColor(this.getResources().getColor(R.color.primary_dark2));
            }

        }

        textForgotPasswordUserName = (EditText)findViewById(R.id.textForgotPasswordUserName);
        buttonForgotPassword = (Button)findViewById(R.id.btnForgotPassword);
        buttonForgotPassword.setText(language.getLabelForgotPassword());
        buttonForgotPassword.setOnClickListener(this);
        labelLoginTitle = (TextView)findViewById(R.id.labelLoginTitle);
        textForgotPasswordUserName.setHint(language.getLabelEmailAddress());
        labelLoginTitle.setText(language.getLabelForgotPassword());
    }

    @Override
    public void bindEvents()
    {
        super.bindEvents();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnForgotPassword:
                String str = textForgotPasswordUserName.getText().toString();

                if(textForgotPasswordUserName.getText().toString().equals(""))
                {
                    textForgotPasswordUserName.setError(language.getLabelRequired());
                }
                else if(!DataHelper.isValidBlankMail(textForgotPasswordUserName.getText().toString()))
                {
                    textForgotPasswordUserName.setError(language.getMessageNotValidEmail());
                    textForgotPasswordUserName.requestFocus();
                }
                else if(!str.substring(str.indexOf("@")+1).equals("mateco.de"))
                {
                    textForgotPasswordUserName.setError(language.getMessageEmailAddress());
                    textForgotPasswordUserName.requestFocus();
                }
                else if (DataHelper.isNetworkAvailable(ForgotPassActivity.this))
                {
                    try
                    {
                        BasicAsyncTaskGetRequest.OnAsyncResult onAsyncResult = new BasicAsyncTaskGetRequest.OnAsyncResult()
                        {
                            @Override
                            public void OnAsynResult(String result)
                            {
                                showLongToast(language.getMessagePasswordChangedSuccessfully());
                                finish();
                            }
                        };

                        String url = DataHelper.URL_USER_HELPER +"forgetpassword/token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");
                        //String url = DataHelper.ACCESS_PROTOCOL + DataHelper.ACCESS_HOST + DataHelper.APP_NAME + DataHelper.ForgotPassword + "?token=" + URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8");

                        url = url + "/email=" + textForgotPasswordUserName.getText().toString();
                        BasicAsyncTaskGetRequest asyncTaskGetRequest = new BasicAsyncTaskGetRequest(url,onAsyncResult,ForgotPassActivity.this,true);
                        asyncTaskGetRequest.execute();
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
