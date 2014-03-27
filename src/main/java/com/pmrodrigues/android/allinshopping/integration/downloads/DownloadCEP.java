package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.CEP;

public class DownloadCEP extends AbstractDownload<CEP> {

	private final static String LISTAR_CEP = "http://store.allinshopp.com.br/custom/listar_cep.php";

    @Override
	public List<CEP> getAll()
        throws IntegrationException
    {
        
        try {
			final JSONObject json = new GetResource(DownloadCEP.LISTAR_CEP)
					.getJSON();

			return toList(json.getJSONObject("ceps").get("cep"));

		} catch (JSONException e) {
			throw new IntegrationException("Ocorreu um erro para converter a resposta do servidor " + e.getMessage(), e);
		}
        
    }

}
