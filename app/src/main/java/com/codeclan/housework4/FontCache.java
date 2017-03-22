package com.codeclan.housework4;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Created by David Hale on 22/03/2017.
 */

public class FontCache {

    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeFace(Context context, String fontName) {

        Typeface typeface = fontCache.get(fontName);

        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), fontName);

            fontCache.put(fontName, typeface);
        }

        return typeface;
    }

}
