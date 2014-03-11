package com.pmrodrigues.android.allinshopping.integration.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Resource
{
    protected String toString(InputStream stream)
        throws IOException
    {
        BufferedReader bufferedreader = null;
        try {
			bufferedreader = new BufferedReader(new InputStreamReader(stream));
			StringBuffer json = new StringBuffer();
			String s = null;
			while((s = bufferedreader.readLine())!=null) {
				json.append(s);
			}
			return json.toString();
		} finally {
			if( bufferedreader != null ) {
				bufferedreader.close();
			}
		}
    }
}
