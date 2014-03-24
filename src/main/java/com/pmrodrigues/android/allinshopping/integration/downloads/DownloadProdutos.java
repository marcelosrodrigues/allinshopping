package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.Produto;

public class DownloadProdutos
    implements Download
{
	// TODO Lembrar de alterar a URL para a URL definitiva
	private final String LIST_PRODUTOS = "http://store.allinshopp.com.br/custom/list_produtos_novo.php"; // NOPMD

    @Override
	public List<Produto> getAll()
        throws IntegrationException
    {
    	
    	 try {
			final String json = new GetResource(LIST_PRODUTOS).getJSON();
			final JSONObject jsonobject = new JSONObject(json);

			final Gson gson = new Gson();
			final Produto[] produtos = gson.fromJson(
					jsonobject.getJSONObject("produtos").get("produto")
							.toString(), Produto[].class);
			return Arrays.asList(produtos);

 		} catch (JSONException e) {
 			throw new IntegrationException("Ocorreu um erro para converter a resposta do servidor " + e.getMessage(), e);
 		}
    	
    }

}
