package com.pmrodrigues.android.allinshopping.database;

import android.content.Context;

public final class DbHelperFactory
{

    public DbHelperFactory(){}

	public static DbHelper getDbHelper(Context context)
    {
        DummyDbHelper dummydbhelper = new DummyDbHelper(context);
        return dummydbhelper;
    }
}
