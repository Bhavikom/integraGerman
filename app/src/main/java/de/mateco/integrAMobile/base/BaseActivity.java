package de.mateco.integrAMobile.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.R;

public class BaseActivity extends ActionBarActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        DataHelper.setUpUi(this, getWindow().getDecorView().findViewById(android.R.id.content));
    }

    public void                                                                                                                                                                                                                                                                 initializeActivity()
    {
        initializeComponents();
        bindEvents();
    }

    public void initializeComponents()
    {
    }

    public void bindEvents()
    {

    }

    public void showShortToast(String message)
    {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_custom,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void showLongToast(String message)
    {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_custom,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
