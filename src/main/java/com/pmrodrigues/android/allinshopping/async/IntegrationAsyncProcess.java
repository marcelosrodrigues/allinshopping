package com.pmrodrigues.android.allinshopping.async;

import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;

import com.androidquery.AQuery;
import com.pmrodrigues.android.allinshopping.R;
import com.pmrodrigues.android.allinshopping.alerts.ActionDialog;
import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.exceptions.NoUniqueRegistryException;
import com.pmrodrigues.android.allinshopping.services.ConfigurationService;

public class IntegrationAsyncProcess extends AsyncTask<Void, String, String> {

	private final IntegrationProcess integration;
	private final ConfigurationService service;
	private final AQuery aq;
	

	public IntegrationAsyncProcess(Context context) {

		integration = new IntegrationProcess(context);
		service = new ConfigurationService(integration.getContext());
		this.aq = new AQuery(context);
		
	}
	
	@Override
	protected void onPreExecute() {
		publishProgress("Iniciando a carga do tablet");
	}
	
	@Override
	protected String doInBackground(Void... avoid)
    {
        try {

			integration.enviarCliente();
			integration.enviarPedido();
			integration.importarEstado();
			integration.importarSecao();
			integration.importarProdutos();
			integration.importarCliente();
			integration.importarCEP();
			
			service.atualizar();
			
			
			return "Dados importados com sucesso";
			
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

	@Override
	protected void onPostExecute(String s) {
		super.onPostExecute(s);
		this.aq.id(R.id.progressText).text(s);
		
		(new ActionDialog(integration.getContext()))
				.setTitle("Atualização da base de dados")
				.setCancelable(false)
				.setMessage(s).show();
	}

}
