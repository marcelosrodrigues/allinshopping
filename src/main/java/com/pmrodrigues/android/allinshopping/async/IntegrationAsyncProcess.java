package com.pmrodrigues.android.allinshopping.async;

import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
	

	public IntegrationAsyncProcess(final Context context) {

		integration = new IntegrationProcess(context);
		service = new ConfigurationService(integration.getContext());
		this.aq = new AQuery(context);
		
	}
	
	@Override
	protected void onPreExecute() {
		Log.d("com.pmrodrigues.android.allinshopping.async","Iniciando a carga do tablet");
		publishProgress("Iniciando a carga do tablet");
	}
	
	@Override
	protected String doInBackground(Void... avoid)
    {
        try {
        	
        	Log.d("com.pmrodrigues.android.allinshopping.async","Enviando os novos clientes para o backoffice");
        	publishProgress("Enviando os novos clientes para o backoffice");
			integration.enviarCliente();
			
			Log.d("com.pmrodrigues.android.allinshopping.async","Enviando os novos pedidos para o backoffice");
			publishProgress("Enviando os novos pedidos para o backoffice");
			integration.enviarPedido();
			
			Log.d("com.pmrodrigues.android.allinshopping.async","Recebendo a lista de Estados do backoffice");
			publishProgress("Recebendo a lista de Estados do backoffice");
			integration.importarEstado();
			
			Log.d("com.pmrodrigues.android.allinshopping.async","Recebendo a lista de departamentos do backoffice");
			publishProgress("Recebendo a lista de departamentos do backoffice");
			integration.importarSecao();
			
			Log.d("com.pmrodrigues.android.allinshopping.async","Recebendo a lista de produtos do backoffice");
			publishProgress("Recebendo a lista de produtos do backoffice");
			integration.importarProdutos();			
			
			Log.d("com.pmrodrigues.android.allinshopping.async","Recebendo a lista de clientes do backoffice");
			publishProgress("Recebendo a lista de clientes do backoffice");
			integration.importarCliente();
			
			Log.d("com.pmrodrigues.android.allinshopping.async","Recebendo a tabela de preços de frete do backoffice");
			publishProgress("Recebendo a tabela de preços de frete do backoffice");
			integration.importarCEP();
			
			service.atualizar();
			
			
			return "Dados importados com sucesso";
			
		} catch (IntegrationException e) {
			Log.e("com.pmrodrigues.android.allinshopping.async","Erro no carga do tablet",e);
			return e.getMessage();
		} catch (JSONException e) {
			Log.e("com.pmrodrigues.android.allinshopping.async","Erro na conversão dos valores recebidos do servidor",e);
			return e.getMessage();
		} catch (NoUniqueRegistryException e) {
			Log.e("com.pmrodrigues.android.allinshopping.async","Erro no salvamento dos dados no tablet",e);
			return e.getMessage();
		} catch (Exception e) {
			Log.e("com.pmrodrigues.android.allinshopping.async","Erro desconhecido",e);
			return e.getMessage();
		}
    }
	
	protected void onProgressUpdate(final String message) {
		this.setMessage(message);
	}

	@Override
	protected void onPostExecute(final String message) {
		super.onPostExecute(message);
		this.setMessage(message);
		
		(new ActionDialog(integration.getContext()))
				.setTitle("Atualização da base de dados")
				.setCancelable(false)
				.setMessage(message)
				.show();
	}
	
	private void setMessage(final String message) {
		this.aq.id(R.id.progressText).text(message);
	}
	
	

}
