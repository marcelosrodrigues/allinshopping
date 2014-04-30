package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.CEP;

public class DownloadCEP extends AbstractDownload<CEP> {

    @Override
	public List<CEP> getAll()
        throws IntegrationException
    {
        
        try {
			final JSONObject json = new GetResource(this.getURL())
					.getJSON();

			return toList(json.get("list"));
			
		} catch (JSONException e) {
			throw new IntegrationException("Ocorreu um erro para converter a resposta do servidor " + e.getMessage(), e);
		}
        
    }

}
