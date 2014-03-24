package com.pmrodrigues.android.allinshopping.integration.downloads;

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

	private final static String LISTAR_ESTADOS = "http://store.allinshopp.com.br/custom/list_estado.php";

    @Override
	public List<Estado> getAll()
        throws IntegrationException
    {
        
        try {
			final String json = new GetResource(DownloadEstado.LISTAR_ESTADOS)
					.getJSON();
			final JSONObject jsonobject = new JSONObject(json);
			
			final Gson gson = new Gson();
			final Estado[] estados = gson.fromJson(
					jsonobject
					.getJSONObject("estados").get("estado").toString(),
					Estado[].class);
			return Arrays.asList(estados);
			
		} catch (JSONException e) {
			throw new IntegrationException("Ocorreu um erro para converter a resposta do servidor " + e.getMessage(), e);
		}
        
    }

}
