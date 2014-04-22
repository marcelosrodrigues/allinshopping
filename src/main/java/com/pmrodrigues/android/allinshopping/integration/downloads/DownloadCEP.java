package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.CEP;
import com.pmrodrigues.android.allinshopping.models.FaixaPreco;

public class DownloadCEP extends AbstractDownload<CEP> {

    @Override
	public List<CEP> getAll()
        throws IntegrationException
    {
        
        try {
			final JSONObject json = new GetResource(this.getURL())
					.getJSON();

			List<CEP> ceps =  toList(json.get("list"));
			
			for(CEP cep : ceps){
				for(FaixaPreco preco : cep.getFaixas() ){
					preco.setCEP(cep);
				}
			}
			
			return ceps;

		} catch (JSONException e) {
			throw new IntegrationException("Ocorreu um erro para converter a resposta do servidor " + e.getMessage(), e);
		}
        
    }

}
