package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.Cliente;

public class DownloadCliente extends AbstractDownload<Cliente> {
	
	@Override
	public List<Cliente> getAll() throws IntegrationException {

		try {
			final JSONObject json = new GetResource(this.getURL()).getJSON();
			final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
					.create();
			
			return toList(gson, json.get("list"));
			
		} catch (JSONException e) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor "
							+ e.getMessage(), e);
		}

	}	
}
