package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.Estado;

public class DownloadEstado
    implements Download
{

	private final String LISTAR_ESTADOS = "http://store.allinshopp.com.br/custom/list_estado.php";

    @Override
	public List<Estado> getAll()
        throws IntegrationException
    {
        
        try {
			String json = new GetResource(LISTAR_ESTADOS).getJSON();
			JSONObject jsonobject = new JSONObject(json);
			List<Estado> estados = new ArrayList<Estado>();
			JSONArray jsonarray = jsonobject.getJSONObject("estados").getJSONArray("estado");
			for(int i = 0 , j = jsonarray.length() ; i < j ; i++ ) {
			
				estados.add(new Estado(jsonarray.getJSONObject(i).getString("uf"), jsonarray.getJSONObject(i).getString("nome")));				
				
			}
			
			return estados;
		} catch (JSONException e) {
			throw new IntegrationException("Ocorreu um erro para converter a resposta do servidor " + e.getMessage(), e);
		}
        
    }

    @Override
	public List<Estado> getBy(Serializable serializable)
    {
        return null;
    }
}
