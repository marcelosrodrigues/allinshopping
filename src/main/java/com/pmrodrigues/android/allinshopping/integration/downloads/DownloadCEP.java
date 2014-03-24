package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.CEP;

public class DownloadCEP
    implements Download
{

	private final static String LISTAR_CEP = "http://store.allinshopp.com.br/custom/listar_cep.php";

    @Override
	public List<CEP> getAll()
        throws IntegrationException
    {
        
        try {
			final String json = new GetResource(DownloadCEP.LISTAR_CEP)
					.getJSON();
			final JSONObject jsonobject = new JSONObject(json);
			final Gson gson = new Gson();
			final CEP[] ceps = gson.fromJson(
					jsonobject.getJSONObject("ceps").get("cep")
					.toString(),
					CEP[].class);
			return Arrays.asList(ceps);

		} catch (JSONException e) {
			throw new IntegrationException("Ocorreu um erro para converter a resposta do servidor " + e.getMessage(), e);
		}
        
    }

}
