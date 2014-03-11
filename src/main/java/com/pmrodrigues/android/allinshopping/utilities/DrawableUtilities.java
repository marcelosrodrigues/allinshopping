package com.pmrodrigues.android.allinshopping.utilities;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;

public final class DrawableUtilities
{

    public static Drawable getImage(Activity activity, String image)
    {
        Log.i("com.pmrodrigues.android.allinshopping", "Gerando a imagem em formato drawable");        
        return Drawable.createFromPath(image);

    }
}
