package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.io.Serializable;
import java.util.List;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;

public interface Download
{

	List getAll()
        throws IntegrationException;

	List getBy(Serializable serializable);
}
