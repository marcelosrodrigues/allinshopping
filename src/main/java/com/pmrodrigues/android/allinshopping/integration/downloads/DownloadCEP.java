package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.CEP;

public class DownloadCEP
    implements Download
{

	private final String LISTAR_CEP = "http://store.allinshopp.com.br/custom/listar_cep.php";

    @Override
	public List<CEP> getAll()
        throws IntegrationException
    {
        
        try {
			String json = new GetResource(LISTAR_CEP).getJSON();
			JSONObject jsonobject = new JSONObject(json);
			List<CEP> ceps = new ArrayList<CEP>();
			JSONArray jsonarray = jsonobject.getJSONObject("ceps").getJSONArray("cep");
			for(int i = 0 , j = jsonarray.length() ; i < j ; i++ ) {
			
				Long id = Long.valueOf(jsonarray.getJSONObject(i).getLong("id"));
			    String uf = jsonarray.getJSONObject(i).getString("uf");
			    Long inicial = Long.valueOf(jsonarray.getJSONObject(i).getLong("inicial"));
			    Long fim = Long.valueOf(jsonarray.getJSONObject(i).getLong("final"));
			    CEP cep = new CEP(id, uf, inicial, fim);
			    ceps.add(cep);
				
			}
			
			return ceps;
		} catch (JSONException e) {
			throw new IntegrationException("Ocorreu um erro para converter a resposta do servidor " + e.getMessage(), e);
		}
        
    }

    @Override
	public List<CEP> getBy(Serializable serializable)
    {
        return null;
    }
}
