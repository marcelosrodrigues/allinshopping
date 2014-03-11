package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.GenericValidator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.Secao;

public class DownloadSecoes
    implements Download
{

	private final String LISTA_SESSOES = "http://store.allinshopp.com.br/custom/list_sessoes.php";

    public DownloadSecoes()
    {
    }

    @Override
	public List<Secao> getAll()
        throws IntegrationException
    {
        try
        {
        	
        	String json = new GetResource(LISTA_SESSOES).getJSON();
 			JSONObject jsonobject = new JSONObject(json);
 			List<Secao> secoes = new ArrayList<Secao>();
 			JSONArray jsonarray = jsonobject.getJSONObject("sessoes").getJSONArray("sessao");
 			for(int i = 0 , j = jsonarray.length() ; i < j ; i++ ) {
 			
 				 Long id = jsonarray.getJSONObject(i).getLong("id_secao");
                 Long isPai = null;
                 if (GenericValidator.isLong(jsonarray.getJSONObject(i).get("id_secao_pai").toString()))
                 {
                     isPai = jsonarray.getJSONObject(i).getLong("id_secao_pai");
                 } 
                 String nome = jsonarray.getJSONObject(i).getString("secao");
                 Log.d("com.pmrodrigues.android.allinshopping", "Baixando a sessao " + nome);
                 if (isPai == null)
                 {
                     Secao secao = new Secao(id, nome);
                     secoes.add(secao);
                 } else
                 {
                     Secao pai = new Secao(isPai, null);
                     Secao secao = new Secao(id, nome, pai);
                     secoes.add(secao);
                 }
 		        
 				
 			}
 			return secoes;
 		}catch (JSONException jsonexception){
            throw new IntegrationException("Ocorreu um erro para converter a resposta do servidor " + jsonexception.getMessage(), jsonexception);
        }
    }

    @Override
	public List<Secao> getBy(Serializable serializable)
    {
        return null;
    }
}
