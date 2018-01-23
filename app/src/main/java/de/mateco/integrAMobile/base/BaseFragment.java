package de.mateco.integrAMobile.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.model.Language;

public class BaseFragment extends Fragment
{
    View rootView;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    public Context context;
    public MatecoPriceApplication application;
    public Language language;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    public void initializeFragment(View rootView)
    {
        this.rootView = rootView;
        context = getActivity();
        application = (MatecoPriceApplication)getActivity().getApplicationContext();
        language = application.getLanguage();
        initializeComponents(rootView);
        bindEvents(rootView);
    }
    public void initializeComponents(View rootView)
    {
    }
    public void bindEvents(View rootView)
    {
    }
    @Override
    public void onResume() {
        super.onResume();
        DataHelper.setUpUi(getActivity(), getActivity().getWindow().getDecorView().findViewById(android.R.id.content));
    }

    public void clearAll() {
        DataHelper.clearAll(getActivity(), getActivity().getWindow().getDecorView().findViewById(android.R.id.content));
    }

    public  void showShortToast(String message)
    {
        if(getActivity() != null)
        {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            if(inflater != null)
            {
                View layout = inflater.inflate(R.layout.toast_custom,
                        (ViewGroup) rootView.findViewById(R.id.toast_layout_root));
                TextView text = (TextView) layout.findViewById(R.id.text);
                text.setText(message+"");
                Toast toast = new Toast(getActivity());
                toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
            }
        }
    }

    public void showLongToast(String message)
    {
        if(getActivity() != null)
        {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            if(inflater != null)
            {
                View layout = inflater.inflate(R.layout.toast_custom,
                        (ViewGroup) rootView.findViewById(R.id.toast_layout_root));
                TextView text = (TextView) layout.findViewById(R.id.text);
                text.setText(message+"");
                Toast toast = new Toast(getActivity());
                toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        }
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams
                        (desiredWidth, LinearLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public Dialog showCustomDialog(int resourceId, String title)
    {
        Dialog dialog = new Dialog(getActivity());
        dialog.setTitle(title);
        dialog.setContentView(resourceId);
        //dialog.show();

        return dialog;
    }
    public Dialog showCustomDialog2(int resourceId, String title)
    {
        Dialog dialog = new Dialog(getActivity());
        dialog.setTitle(title);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(resourceId);
        //dialog.show();

        return dialog;
    }


    public AlertDialog showAlert(String title, String message, String positiveButtonText,
           String negativeButtonText,DialogInterface.OnClickListener positiveCallback,
           DialogInterface.OnClickListener negativeCallback)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(positiveButtonText, positiveCallback);
        builder.setNegativeButton(negativeButtonText, negativeCallback);
        AlertDialog alert = builder.create();
        return alert;
    }

    public AlertDialog showMessage(String title, String message, String positiveButtonText,
           DialogInterface.OnClickListener positiveCallback)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(positiveButtonText, positiveCallback);
        AlertDialog alert = builder.create();
        return alert;
    }

}
