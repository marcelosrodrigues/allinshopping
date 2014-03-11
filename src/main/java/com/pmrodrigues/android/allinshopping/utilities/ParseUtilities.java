package com.pmrodrigues.android.allinshopping.utilities;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

public final class ParseUtilities
{

    private ParseUtilities()
    {
    }

    public static String formatDate(Date date)
    {
        return formatDate(date, "dd/MM/yyyy");
    }

    public static String formatDate(Date date, String format)
    {   
		return new SimpleDateFormat(format, Constante.PT_BR).format(date);
    }

    public static String formatMoney(Number number)
    {
        DecimalFormat decimalformat = (DecimalFormat) DecimalFormat.getCurrencyInstance(Constante.PT_BR);
        decimalformat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalformat.format(number);
    }

    public static Date toDate(String value, String format)
    {
        Date date;
        try
        {   
            date = new SimpleDateFormat(format, Constante.PT_BR).parse(value);
        }
        catch (ParseException parseexception)
        {   
            Log.e("com.pmrodrigues.android.allinshopping", parseexception.getMessage(), parseexception);
            throw new RuntimeException(parseexception);
        }
        return date;
    }
}
