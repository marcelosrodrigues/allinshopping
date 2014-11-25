package com.pmrodrigues.android.allinshopping.integration.upload;

import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;

public interface Upload<E>
{
	void send(E e) throws IntegrationException;
}
