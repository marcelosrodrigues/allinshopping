package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
			List<FaixaEntrega> faixa = new ArrayList<FaixaEntrega>();
			JSONArray jsonarray = jsonobject.getJSONObject("configuracao").getJSONArray("de_para");
			for(int i = 0 , j = jsonarray.length() ; i < j ; i++ ) {
			
				String origem = jsonarray.getJSONObject(i).getString("uf_origem");
		        String destino = jsonarray.getJSONObject(i).getString("uf_destino");
		        Long idfaixa = jsonarray.getJSONObject(i).getLong("id_faixa_preco");
		        FaixaEntrega faixaentrega = new FaixaEntrega(origem, destino, idfaixa);
			    faixa.add(faixaentrega);
				
			}
			
			return faixa;
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
