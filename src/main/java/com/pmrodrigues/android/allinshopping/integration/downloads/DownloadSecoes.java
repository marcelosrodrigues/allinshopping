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

	private final String LISTA_SESSOES = "http://store.allinshopp.com.br/custom/list_sessoes.php";

    public DownloadSecoes()
    {
    }

    @Override
	public List<Secao> getAll()
        throws IntegrationException
    {
        try
        {
        	
        	String json = new GetResource(LISTA_SESSOES).getJSON();
			JSONObject jsonobject = new JSONObject(json);

			Gson gson = new Gson();
			Secao[] secoes = gson.fromJson(jsonobject.getJSONObject("sessoes")
					.getJSONArray("sessao").toString(), Secao[].class);
			return Arrays.asList(secoes);

		} catch (JSONException jsonexception) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor "
							+ jsonexception.getMessage(), jsonexception);
		}
    }

}
