package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;

public interface Download
{

	List getAll()
        throws IntegrationException;

}
