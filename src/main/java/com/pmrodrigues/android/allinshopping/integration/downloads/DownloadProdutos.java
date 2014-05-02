package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.models.Atributo;
import com.pmrodrigues.android.allinshopping.models.Imagem;
import com.pmrodrigues.android.allinshopping.models.Produto;

public class DownloadProdutos extends AbstractDownload<Produto> {
	
	@Override
	public List<Produto> getAll() throws IntegrationException {
		
			final List<Produto> produtos =  super.getAll();
			for(Produto produto : produtos){
				for( Imagem imagem : produto.getImagens() ) {
					imagem.setProduto(produto);
				}
				for( Atributo atributo : produto.getAtributos() ){
					atributo.setProduto(produto);
				}
			}
			
			return produtos;

	}

}
