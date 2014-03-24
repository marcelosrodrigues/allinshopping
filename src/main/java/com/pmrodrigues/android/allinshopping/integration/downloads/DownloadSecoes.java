package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.Secao;

public class DownloadSecoes
    implements Download
{

	private final String LISTA_SESSOES = "http://store.allinshopp.com.br/custom/list_sessoes.php"; // NOPMD

    @Override
	public List<Secao> getAll()
        throws IntegrationException
    {
        try
        {
        	
			final String json = new GetResource(LISTA_SESSOES).getJSON();
			final JSONObject jsonobject = new JSONObject(json);

			final Gson gson = new Gson();
			final Secao[] secoes = gson.fromJson(
					jsonobject.getJSONObject("sessoes")
					.getJSONArray("sessao").toString(), Secao[].class);
			return Arrays.asList(secoes);

		} catch (JSONException jsonexception) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor "
							+ jsonexception.getMessage(), jsonexception);
		}
    }

}
