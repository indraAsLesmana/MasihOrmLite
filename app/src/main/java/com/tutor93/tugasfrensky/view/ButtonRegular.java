package com.tutor93.tugasfrensky.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by frensky on 1/8/16.
 */
public class ButtonRegular extends Button {

    public ButtonRegular(Context context) {
        super(context, null);
        // TODO Auto-generated constructor stub

    }

    public ButtonRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(context.getAssets(),
                "Quicksand-Regular.otf");
        setTypeface(type);
    }

}
