package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.FaixaEntrega;

public class DownloadFaixaEntrega
    implements Download
{

	private static final String LISTAR_FAIXA_ENTREGA = "http://store.allinshopp.com.br/custom/listar_de_para.php";

    public DownloadFaixaEntrega()
    {
    }

    @Override
	public List<FaixaEntrega> getAll()
        throws IntegrationException
    {
        
	
    try {
			String json = new GetResource(LISTAR_FAIXA_ENTREGA).getJSON();
			JSONObject jsonobject = new JSONObject(json);
			
			Gson gson = new Gson();
			FaixaEntrega[] faixas = gson.fromJson(
					jsonobject.getJSONObject("configuracao").get("de_para")
							.toString(), FaixaEntrega[].class);
			return Arrays.asList(faixas);
			
		} catch (JSONException e) {
			throw new IntegrationException("Ocorreu um erro para converter a resposta do servidor " + e.getMessage(), e);
		}
    }
    	
    @Override
	public List<FaixaEntrega> getBy(Serializable serializable)
    {
        return null;
    }
}
