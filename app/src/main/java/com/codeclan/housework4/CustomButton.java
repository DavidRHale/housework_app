package com.codeclan.housework4;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by David Hale on 22/03/2017.
 */

public class CustomButton extends Button {

    public CustomButton(Context context) {
        super(context);

        applyCustomFont(context, "Muli-Light.ttf");
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context, "Muli-Light.ttf");
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        applyCustomFont(context, "Muli-Light.ttf");
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        applyCustomFont(context, "Muli-Light.ttf");
    }

    private void applyCustomFont(Context context, String fontName) {
        Typeface customFont = FontCache.getTypeFace(context, fontName);

        setTypeface(customFont);
    }

}
