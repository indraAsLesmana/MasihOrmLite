package com.tutor93.tugasfrensky.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by frensky on 7/1/15.
 */
public class TextUtil {
    public static final String FONT_TYPE = "Quicksand-Regular.otf";
    private static TextUtil textHelper;
    private Context context;

    public static TextUtil getInstance(Context context) {
        if (textHelper == null) {
            textHelper = new TextUtil();
        }
        textHelper.context = context;
        return textHelper;
    }

    public void setFont(ViewGroup group) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), FONT_TYPE);
        int count = group.getChildCount();
        View v;
        for (int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if (v instanceof TextView || v instanceof Button)
                ((TextView) v).setTypeface(font);
            else if (v instanceof ViewGroup)
                setFont((ViewGroup) v);
        }
    }

    public String limitString(String value, int length) {
        StringBuilder buf = new StringBuilder(value);
        if (buf.length() > length) {
            buf.setLength(length);
            buf.append(" ...");
        }
        return buf.toString();
    }
}
