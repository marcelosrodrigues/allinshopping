package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.Estado;

public class DownloadEstado
    implements Download
{

	private final String LISTAR_ESTADOS = "http://store.allinshopp.com.br/custom/list_estado.php";

    @Override
	public List<Estado> getAll()
        throws IntegrationException
    {
        
        try {
			String json = new GetResource(LISTAR_ESTADOS).getJSON();
			JSONObject jsonobject = new JSONObject(json);
			
			Gson gson = new Gson();
			Estado[] estados = gson.fromJson(jsonobject
					.getJSONObject("estados").get("estado").toString(),
					Estado[].class);
			return Arrays.asList(estados);
			
		} catch (JSONException e) {
			throw new IntegrationException("Ocorreu um erro para converter a resposta do servidor " + e.getMessage(), e);
		}
        
    }

    @Override
	public List<Estado> getBy(Serializable serializable)
    {
        return null;
    }
}
