package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.Produto;

public class DownloadProdutos extends AbstractDownload<Produto> {
	// TODO Lembrar de alterar a URL para a URL definitiva
	private final String LIST_PRODUTOS = "http://store.allinshopp.com.br/custom/list_produtos_novo.php"; // NOPMD

	@Override
	public List<Produto> getAll() throws IntegrationException {

		try {
			final JSONObject json = new GetResource(LIST_PRODUTOS).getJSON();

			return toList(json.getJSONObject("produtos").get("produto"));

		} catch (JSONException e) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor "
							+ e.getMessage(), e);
		}

	}

}
