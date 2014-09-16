package com.pmrodrigues.android.allinshopping.utilities;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import org.apache.commons.lang3.BitField;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

public final class DrawableUtilities
{

	private DrawableUtilities() {
	}

	public static Drawable getImage(final String image)
    {
        Log.i("com.pmrodrigues.android.allinshopping", "Gerando a imagem em formato drawable");
        return Drawable.createFromPath(image);

    }
}
