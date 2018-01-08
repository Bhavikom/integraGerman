package de.mateco.integrAMobile.Helper;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * used for filtering text (number) for input type number
 */
public class InputFilterDecimal implements InputFilter
{
    private float min, max;

    public InputFilterDecimal(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public InputFilterDecimal(String min, String max) {
        this.min = Float.parseFloat(min);
        this.max = Float.parseFloat(max);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String input = dest.toString() + source.toString();
        //Log.i("MXM", input);
        try {
            float inputf = Float.parseFloat(input);
            if (isInRange(min, max, inputf))
                return null;
        } catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(float minimum, float maximum, float input) {
        if (input>=minimum && input<=maximum) {
            return true;
        }
        return false;
    }

}
