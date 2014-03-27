package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.FaixaEntrega;

public class DownloadFaixaEntrega extends AbstractDownload<FaixaEntrega> {

	private static final String LISTAR_FAIXA_ENTREGA = "http://store.allinshopp.com.br/custom/listar_de_para.php"; // NOPMD

	@Override
	public List<FaixaEntrega> getAll() throws IntegrationException {

		try {
			final JSONObject json = new GetResource(LISTAR_FAIXA_ENTREGA)
					.getJSON();

			return toList(json.getJSONObject("configuracao").get("de_para"));

		} catch (JSONException e) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor "
							+ e.getMessage(), e);
		}
	}

}
