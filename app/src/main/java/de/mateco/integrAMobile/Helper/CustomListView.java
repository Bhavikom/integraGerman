package de.mateco.integrAMobile.Helper;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

/**
 * Created by S Soft on 04-Sep-17.
 */

public class CustomListView extends ListView {

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        try {
            super.dispatchDraw(canvas);
        } catch (IndexOutOfBoundsException e) {
            LogApp.showLog("luna", "Ignore list view error ->" + e.toString());
        }
    }
}
