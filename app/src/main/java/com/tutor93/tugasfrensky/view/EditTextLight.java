package com.tutor93.tugasfrensky.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by frensky on 1/8/16.
 */
public class EditTextLight extends EditText {

    public EditTextLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(context.getAssets(),
                "Quicksand-Light.otf");
        setTypeface(type);

       /* forbid emoticon and special character
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    int type = Character.getType(source.charAt(i));
                    //System.out.println("Type : " + type);
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                        return "";
                    }
                }
                return null;
            }
        };

        setFilters(new InputFilter[]{filter});
        */
    }

    public EditTextLight(Context context) {
        // TODO Auto-generated constructor stub
        super(context, null);

        /* forbid emoticon and special character
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    int type = Character.getType(source.charAt(i));
                    //System.out.println("Type : " + type);
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                        return "";
                    }
                }
                return null;
            }
        };

        setFilters(new InputFilter[]{filter});
        */
    }
}