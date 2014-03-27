package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.Secao;

public class DownloadSecoes extends AbstractDownload<Secao> {

	private final String LISTA_SESSOES = "http://store.allinshopp.com.br/custom/list_sessoes.php"; // NOPMD

	@Override
	public List<Secao> getAll() throws IntegrationException {
		try {

			final JSONObject json = new GetResource(LISTA_SESSOES).getJSON();
			return toList(json.getJSONObject("sessoes").get("sessao"));

		} catch (JSONException jsonexception) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor "
							+ jsonexception.getMessage(), jsonexception);
		}
	}

}
