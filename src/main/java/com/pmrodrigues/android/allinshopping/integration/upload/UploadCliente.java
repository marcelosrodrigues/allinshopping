package com.pmrodrigues.android.allinshopping.integration.upload;

import org.json.JSONException;
import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.PostResource;

public class UploadCliente implements Upload {

	private static final String SEND_CLIENTE = "http://store.allinshopp.com.br/custom/incluir_cliente.php";

	@Override
	public JSONObject sendTo(JSONObject jsonobject) throws IntegrationException {
		try {
			return new JSONObject(
					new PostResource(UploadCliente.SEND_CLIENTE,null,null)
							.sendJSON(jsonobject));
		} catch (JSONException jsonexception) {
			throw new IntegrationException(String.format(
					"Erro na recuperação do retorno json do servidor %s",
					jsonexception.getMessage()), jsonexception);
		}

	}
}
