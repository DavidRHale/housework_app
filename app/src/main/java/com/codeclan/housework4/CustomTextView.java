package com.codeclan.housework4;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by David Hale on 22/03/2017.
 */

public class CustomTextView extends TextView {

    public CustomTextView(Context context) {
        super(context);
        applyCustomFont(context, "Muli-Light.ttf");
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context, "Muli-Light.ttf");
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context, "Muli-Light.ttf");
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyCustomFont(context, "Muli-Light.ttf");
    }

    private void applyCustomFont(Context context, String fontName) {
        Typeface customFont = FontCache.getTypeFace(context, fontName);

        setTypeface(customFont);
    }
}
