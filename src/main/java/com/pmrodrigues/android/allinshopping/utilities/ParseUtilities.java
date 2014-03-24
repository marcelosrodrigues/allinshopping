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

	public static String formatDate(final Date toFormat)
    {
        return formatDate(toFormat, "dd/MM/yyyy");
    }

	public static String formatDate(final Date toFormat, final String format)
    {   
		return new SimpleDateFormat(format, Constante.PT_BR).format(toFormat);
    }

	public static String formatMoney(final Number money)
    {
		final DecimalFormat decimalformat = (DecimalFormat) DecimalFormat
				.getCurrencyInstance(Constante.PT_BR);
        decimalformat.setRoundingMode(RoundingMode.HALF_UP);
		return decimalformat.format(money);
    }

	public static Date toDate(final String toParse, final String format)
    {
        try
        {   
			return new SimpleDateFormat(format, Constante.PT_BR).parse(toParse);
        }
        catch (ParseException parseexception)
        {   
            Log.e("com.pmrodrigues.android.allinshopping", parseexception.getMessage(), parseexception);
            throw new RuntimeException(parseexception);
        }

    }
}
