package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.Atributo;
import com.pmrodrigues.android.allinshopping.models.Imagem;
import com.pmrodrigues.android.allinshopping.models.Produto;

public class DownloadProdutos extends AbstractDownload<Produto> {
	
	@Override
	public List<Produto> getAll() throws IntegrationException {

		try {
			final JSONObject json = new GetResource(this.getURL()).getJSON();

			List<Produto> produtos =  toList(json.get("list"));
			
			for(Produto produto : produtos){
				for( Imagem imagem : produto.getImagens() ) {
					imagem.setProduto(produto);
				}
				for( Atributo atributo : produto.getAtributos() ){
					atributo.setProduto(produto);
				}
			}
			
			return produtos;

		} catch (JSONException e) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor "
							+ e.getMessage(), e);
		}

	}

}
