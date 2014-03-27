package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.FaixaPreco;

public class DownloadFaixaPrecoEntrega extends AbstractDownload<FaixaPreco> {

	private static final String LISTAR_FAIXA_ENTREGA = "http://store.allinshopp.com.br/custom/listar_preco_frete.php"; // NOPMD

	@Override
	public List<FaixaPreco> getAll() throws IntegrationException {

		try {
			final JSONObject json = new GetResource(LISTAR_FAIXA_ENTREGA)
					.getJSON();

			return toList(json.getJSONObject("tabela_preco").get("faixa_preco"));

		} catch (JSONException e) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor "
							+ e.getMessage(), e);
		}

	}

}
