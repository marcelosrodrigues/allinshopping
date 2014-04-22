package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.Secao;

public class DownloadSecoes extends AbstractDownload<Secao> {

	@Override
	public List<Secao> getAll() throws IntegrationException {
		try {

			final JSONObject json = new GetResource(this.getURL()).getJSON();
			return toList(json.get("list"));

		} catch (JSONException jsonexception) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor "
							+ jsonexception.getMessage(), jsonexception);
		}
	}

}
