package com.pmrodrigues.android.allinshopping.integration.upload;

import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.PostResource;

public class UploadPedido implements Upload {

	private static final String SEND_PEDIDO = "http://store.allinshopp.com.br/custom/incluir_pedido.php";

	@Override
	public JSONObject sendTo(JSONObject jsonobject) throws IntegrationException {
		new PostResource(UploadPedido.SEND_PEDIDO, null, null)
				.sendJSON(jsonobject);
		return null;
	}
}
