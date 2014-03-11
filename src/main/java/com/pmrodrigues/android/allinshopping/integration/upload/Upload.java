package com.pmrodrigues.android.allinshopping.integration.upload;

import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;

public interface Upload
{
	JSONObject sendTo(JSONObject jsonobject)
        throws IntegrationException;
}
