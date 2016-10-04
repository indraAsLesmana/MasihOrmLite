package com.tutor93.tugasfrensky.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by frensky on 1/8/16.
 */
public class TextViewRegular extends TextView {

    public TextViewRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(context.getAssets(),
                "Quicksand-Regular.otf");
        setTypeface(type);
    }

    public TextViewRegular(Context context) {
        // TODO Auto-generated constructor stub
        super(context, null);
    }

}