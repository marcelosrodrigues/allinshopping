package com.pmrodrigues.android.allinshopping.async;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadResource;
import com.pmrodrigues.android.allinshopping.models.Produto;
import com.pmrodrigues.android.allinshopping.services.ProdutoService;

public class IntegrationProdutoRunnable implements Runnable {

	private final DownloadResource download = new DownloadResource(); // NOPMD
	private final Produto produto;
	private final ProdutoService service;

	public IntegrationProdutoRunnable(final Produto produto,
			final ProdutoService produtoservice) {
		this.produto = produto;
		this.service = produtoservice;
	}

	@Override
	public void run() {
		try {
			download.getResourceByProduto(produto);
			service.save(produto);
		} catch (IntegrationException e) {
			throw new RuntimeException(e);
		}

	}

}
