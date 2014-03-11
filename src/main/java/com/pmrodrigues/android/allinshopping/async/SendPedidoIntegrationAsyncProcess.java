package com.pmrodrigues.android.allinshopping.async;

import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.exceptions.NoUniqueRegistryException;
import com.pmrodrigues.android.allinshopping.services.AtualizacaoService;

public class SendPedidoIntegrationAsyncProcess extends
		AsyncTask<Void, String, String> {

	private final IntegrationProcess integration;

	public SendPedidoIntegrationAsyncProcess(Context context1) {

		integration = new IntegrationProcess(context1);		

	}

	@Override
	protected String doInBackground(Void... avoid) {
		try {
			integration.enviarCliente();
			integration.enviarPedido();
			

			AtualizacaoService service = new AtualizacaoService(
					this.integration.getContext());
			service.atualizar();

			return "Pedido enviado com sucesso";

		} catch (IntegrationException e) {
			return e.getMessage();
		} catch (JSONException e) {
			return e.getMessage();
		} catch (NoUniqueRegistryException e) {
			return e.getMessage();
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
