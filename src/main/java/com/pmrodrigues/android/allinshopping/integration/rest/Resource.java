package com.pmrodrigues.android.allinshopping.integration.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Resource
{
	protected String toString(final InputStream stream)
        throws IOException
    {
		BufferedReader bufferedreader = null; // NOPMD
        try {
			bufferedreader = new BufferedReader(new InputStreamReader(stream));
			final StringBuffer buffer = new StringBuffer();
			String partial = null; // NOPMD
			while ((partial = bufferedreader.readLine()) != null) { // NOPMD
				buffer.append(partial);
			}

			return buffer.substring(buffer.indexOf("{")).toString();

		} finally {
			if( bufferedreader != null ) {
				bufferedreader.close();
			}
		}
    }
}
