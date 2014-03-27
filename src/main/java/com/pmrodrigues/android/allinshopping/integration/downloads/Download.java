package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;

public interface Download<E>
{

	List<E> getAll()
        throws IntegrationException;

}
