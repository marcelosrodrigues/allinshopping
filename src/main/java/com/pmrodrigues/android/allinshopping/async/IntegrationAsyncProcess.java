package com.pmrodrigues.android.allinshopping.async;

import android.app.ProgressDialog;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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
	
	private final ConfigurationService service;
	private String email;
	private String password;
	private final Context context;
    private ProgressDialog progress;


    public IntegrationAsyncProcess(final Context context) {

		this.context = context;
		this.service = new ConfigurationService(context);

	}
	
	@Override
	protected void onPreExecute() {
		Log.d("com.pmrodrigues.android.allinshopping.async","Iniciando a carga do tablet");
        this.progress = ProgressDialog.show(context,"Atualização do Catalógo Digital Ella S/A","Aguarde, estamos atualização do seu Catalógo Digital Ella S/A",true);
        publishProgress("Iniciando a carga do tablet");
	}

    @Override
	protected String doInBackground(Void... avoid)
    {
        try {

        	final IntegrationProcess integration = new IntegrationProcess(email,password,this.context);
        	
        	Log.d("com.pmrodrigues.android.allinshopping.async","Enviando os novos clientes para o backoffice");

			integration.enviarCliente();
			
			Log.d("com.pmrodrigues.android.allinshopping.async","Enviando os novos pedidos para o backoffice");

			integration.enviarPedido();
			
			Log.d("com.pmrodrigues.android.allinshopping.async","Recebendo a lista de Estados do backoffice");

            integration.importarEstado();
			
			Log.d("com.pmrodrigues.android.allinshopping.async","Recebendo a lista de departamentos do backoffice");

            integration.importarSecao();
			
			Log.d("com.pmrodrigues.android.allinshopping.async","Recebendo a lista de produtos do backoffice");

            integration.importarProdutos();
			
			Log.d("com.pmrodrigues.android.allinshopping.async","Recebendo a lista de clientes do backoffice");

            integration.importarCliente();
			
			Log.d("com.pmrodrigues.android.allinshopping.async","Recebendo a tabela de preços de frete do backoffice");

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

	@Override
	protected void onPostExecute(final String message) {
		super.onPostExecute(message);

        if (progress.isShowing()) {
            progress.dismiss();
        }

		new ActionDialog(this.context)
				.setTitle("Atualização da base de dados")
				.setCancelable(false)
				.setMessage(message)
				.show();
	}

	public IntegrationAsyncProcess setUserName(final String email) {
		this.email = email;
		return this;
	}

	public IntegrationAsyncProcess setPassword(final String password) {
		this.password = password;
		return this;		
	}

    public boolean isRunning() {
        return this.getStatus() == Status.RUNNING;
    }

}
