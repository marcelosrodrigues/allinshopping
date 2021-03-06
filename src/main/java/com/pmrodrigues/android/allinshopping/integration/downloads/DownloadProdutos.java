package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.models.Atributo;
import com.pmrodrigues.android.allinshopping.models.Imagem;
import com.pmrodrigues.android.allinshopping.models.Produto;

public class DownloadProdutos extends AbstractDownload<Produto> {
	
	public DownloadProdutos(String username, String password) {
		super(username,password);
	}

	@Override
	public List<Produto> list() throws IntegrationException { //NOPMD
		
			final List<Produto> produtos =  super.list();
			for(final Produto produto : produtos){
				for( final Imagem imagem : produto.getImagens() ) {
					imagem.setProduto(produto);
				}
				for( final Atributo atributo : produto.getAtributos() ){
					atributo.setProduto(produto);
				}
			}
			
			return produtos;

	}

}
