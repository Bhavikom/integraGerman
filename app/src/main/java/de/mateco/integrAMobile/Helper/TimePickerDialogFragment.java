package de.mateco.integrAMobile.Helper;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class TimePickerDialogFragment extends DialogFragment
{
    private int mHour = 0;
    private int mMinute = 0;
    private TimePickerDialog.OnTimeSetListener callback;

    public void setCallBack(TimePickerDialog.OnTimeSetListener callback) {
        this.callback = callback;
    }

    public TimePickerDialogFragment() {
    }

    @Override
    public void setArguments(Bundle args)
    {
        mHour = args.getInt("hour");
        mMinute = args.getInt("min");
        super.setArguments(args);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        TimePickerDialog timePicker = new TimePickerDialog(getActivity(), callback, mHour, mMinute, true);


        timePicker.updateTime(mHour, mMinute);
        return timePicker;
    }
}
