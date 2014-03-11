package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.Cliente;
import com.pmrodrigues.android.allinshopping.models.Estado;
import com.pmrodrigues.android.allinshopping.utilities.ParseUtilities;

public class DownloadCliente
    implements Download
{

    

    @Override
	public List<Cliente> getAll()
        throws IntegrationException
    {
        
    	
    	try {
			String json = new GetResource(
					"http://store.allinshopp.com.br/custom/listar_clientes.php")
					.getJSON();
			JSONObject jsonobject = new JSONObject(json);
			List<Cliente> clientes = new ArrayList<Cliente>();
			JSONArray jsonarray = jsonobject.getJSONObject("clientes").optJSONArray("cliente");
			if( jsonarray != null ) {
				for(int i = 0 , j = jsonarray.length() ; i < j ; i++ ) {
				
					 	String nome = jsonarray.getJSONObject(i).getString("nome");
				        Date dataNascimento = ParseUtilities.toDate(jsonarray.getJSONObject(i).getString("nascimento"), "yyyy-MM-dd");
				        String email = jsonarray.getJSONObject(i).getString("email");
				        String uf = jsonarray.getJSONObject(i).getString("estado");
				        Estado estado = new Estado(uf, null);
				        String cidade = jsonarray.getJSONObject(i).getString("cidade");
				        String bairro = jsonarray.getJSONObject(i).getString("bairro");
				        String logradouro = jsonarray.getJSONObject(i).getString("endereco");				        
				        String cep = jsonarray.getJSONObject(i).getString("cep");
				        Cliente cliente = new Cliente( nome, dataNascimento, email, estado, cidade, bairro, logradouro, "", "", cep);
				        Long idAddress = Long.valueOf(jsonarray.getJSONObject(i).getLong("id_address"));
				        cliente.setIdAddress(idAddress);
				        Long idPrestashop = Long.valueOf(jsonarray.getJSONObject(i).getLong("id_prestashop"));
				        cliente.setIdPrestashop(idPrestashop);
				        if( !"{}".equalsIgnoreCase(jsonarray.getJSONObject(i).getString("telefone")) ){
				        	cliente.setTelefone(jsonarray.getJSONObject(i).getString("telefone"));
				        }
				        if( !"{}".equalsIgnoreCase(jsonarray.getJSONObject(i).getString("celular")) ){
				        	cliente.setCelular(jsonarray.getJSONObject(i).getString("celular"));
				        }
				        clientes.add(cliente);
					
				}
			}
			
			return clientes;
		} catch (JSONException e) {
			throw new IntegrationException("Ocorreu um erro para converter a resposta do servidor " + e.getMessage(), e);
		}
    	
    }

    @Override
	public List<Cliente> getBy(Serializable serializable)
    {
        return null;
    }
}
