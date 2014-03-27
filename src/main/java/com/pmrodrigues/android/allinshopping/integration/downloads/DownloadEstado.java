package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.Estado;

public class DownloadEstado extends AbstractDownload<Estado>
{

	private final static String LISTAR_ESTADOS = "http://store.allinshopp.com.br/custom/list_estado.php";

    @Override
	public List<Estado> getAll()
        throws IntegrationException
    {
        
        try {
			final JSONObject json = new GetResource(
					DownloadEstado.LISTAR_ESTADOS)
					.getJSON();
			
			return toList(json.getJSONObject("estados").get("estado"));
			
		} catch (JSONException e) {
			throw new IntegrationException("Ocorreu um erro para converter a resposta do servidor " + e.getMessage(), e);
		}
        
    }

}
