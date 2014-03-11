package com.pmrodrigues.android.allinshopping.async;

import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;

import com.androidquery.AQuery;
import com.pmrodrigues.android.allinshopping.R;
import com.pmrodrigues.android.allinshopping.alerts.ActionDialog;
import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.exceptions.NoUniqueRegistryException;
import com.pmrodrigues.android.allinshopping.services.AtualizacaoService;

public class IntegrationAsyncProcess extends AsyncTask<Void, String, String> {

	private final IntegrationProcess integration;
	private AQuery aq;
	

	public IntegrationAsyncProcess(Context context1) {

		integration = new IntegrationProcess(context1);
		this.aq = new AQuery(context1);
		
	}
	
	
	protected String doInBackground(Void... avoid)
    {
        try {
			integration.importarEstado();
			integration.enviarCliente();
			integration.enviarPedido();
			integration.importarSecao();
			integration.importarProdutos();
			integration.importarCliente();
			integration.importarCEP();
			integration.importarFaixaEntrega();
			integration.importarFaixaPreco();
			
			AtualizacaoService service = new AtualizacaoService(this.integration.getContext());
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

	protected void onPostExecute(String s) {
		super.onPostExecute(s);
		this.aq.id(R.id.progressText).text(s);
		
		(new ActionDialog(integration.getContext()))
				.setTitle("Atualização da base de dados")
				.setCancelable(false)
				.setMessage(s).show();
	}

}
