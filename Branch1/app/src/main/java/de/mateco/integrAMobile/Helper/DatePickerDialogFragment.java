package de.mateco.integrAMobile.Helper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePickerDialogFragment extends DialogFragment
{
    private DatePickerDialog.OnDateSetListener callback;
    private DatePickerDialog datePicker;
    //private int year, month, date;

    public DatePickerDialogFragment()
    {

    }

    public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
        callback = ondate;
    }

    private int year, month, day;
    private Date minDate;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
    }

    public void setMinDate(Date date)
    {
        minDate = date;
    }

//    public DatePickerDialogFragment(Fragment mFragment) {
//        this.mFragment = mFragment;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        datePicker = new DatePickerDialog(getActivity(), callback, year, month, day);
        if(minDate != null)
        {
            datePicker.getDatePicker().setMinDate(minDate.getTime());
        }
        SimpleDateFormat outputFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date dateToCompare = null;
        try
        {
            dateToCompare = outputFormatter.parse("01-01-2079 00:00");
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        datePicker.getDatePicker().setMaxDate(dateToCompare.getTime());
        return datePicker;
    }
}
