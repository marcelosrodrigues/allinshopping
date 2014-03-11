package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.FaixaPreco;

public class DownloadFaixaPrecoEntrega
    implements Download
{

	private static final String LISTAR_FAIXA_ENTREGA = "http://store.allinshopp.com.br/custom/listar_preco_frete.php";

    public DownloadFaixaPrecoEntrega()
    {
    }

    @Override
	public List<FaixaPreco> getAll()
        throws IntegrationException
    {
    	
    	
        try {
    			String json = new GetResource(LISTAR_FAIXA_ENTREGA).getJSON();
    			JSONObject jsonobject = new JSONObject(json);
    			List<FaixaPreco> faixa = new ArrayList<FaixaPreco>();
    			JSONArray jsonarray = jsonobject.getJSONObject("tabela_preco").getJSONArray("faixa_preco");
    			for(int i = 0 , j = jsonarray.length() ; i < j ; i++ ) {
    			
    				Long id = Long.valueOf(jsonarray.getJSONObject(i).getLong("id_faixa"));
    		        Long peso_inicial = Long.valueOf(jsonarray.getJSONObject(i).getLong("peso_inicial"));
    		        Long peso_final = Long.valueOf(jsonarray.getJSONObject(i).getLong("peso_final"));
    		        BigDecimal preco = new BigDecimal(jsonarray.getJSONObject(i).getDouble("preco"));
    		        FaixaPreco faixapreco = new FaixaPreco(id, peso_inicial, peso_final, preco);
    			    faixa.add(faixapreco);
    				
    			}
    			
    			return faixa;
    		} catch (JSONException e) {
    			throw new IntegrationException("Ocorreu um erro para converter a resposta do servidor " + e.getMessage(), e);
    		}
      
    }

    @Override
	public List<FaixaPreco> getBy(Serializable serializable)
    {
        return null;
    }
}
