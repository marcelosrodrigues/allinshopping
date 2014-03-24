package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.FaixaPreco;

public class DownloadFaixaPrecoEntrega implements Download {

	private static final String LISTAR_FAIXA_ENTREGA = "http://store.allinshopp.com.br/custom/listar_preco_frete.php"; // NOPMD

	@Override
	public List<FaixaPreco> getAll() throws IntegrationException {

		try {
			final String json = new GetResource(LISTAR_FAIXA_ENTREGA).getJSON();
			final JSONObject jsonobject = new JSONObject(json);

			final Gson gson = new Gson();
			final FaixaPreco[] faixas = gson.fromJson(
					jsonobject.getJSONObject("tabela_preco")
							.getJSONArray("faixa_preco").toString(),
					FaixaPreco[].class);
			return Arrays.asList(faixas);

		} catch (JSONException e) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor "
							+ e.getMessage(), e);
		}

	}

}
