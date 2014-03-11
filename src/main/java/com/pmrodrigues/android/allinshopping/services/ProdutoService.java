package com.pmrodrigues.android.allinshopping.services;

import java.sql.SQLException;
import java.util.concurrent.Callable;

import android.content.Context;

import com.j256.ormlite.misc.TransactionManager;
import com.pmrodrigues.android.allinshopping.models.Produto;
import com.pmrodrigues.android.allinshopping.models.Secao;
import com.pmrodrigues.android.allinshopping.repository.ProductRepository;
import com.pmrodrigues.android.allinshopping.repository.SectionRepository;

public class ProdutoService
{

    private final ProductRepository PRODUCT_REPOSITORY;
    private final SectionRepository SECTION_REPOSITORY;

    public ProdutoService(Context context)
    {
        SECTION_REPOSITORY = new SectionRepository(context);
        PRODUCT_REPOSITORY = new ProductRepository(context);
    }

    public void save(final Produto produto)
    {
        final Secao secao = SECTION_REPOSITORY.getById(produto.getIdCategoriaPrestashop());
		produto.setSecao(secao);
		PRODUCT_REPOSITORY.insert(produto);
		
    }

	public void removeAll() {
		try {
			TransactionManager.callInTransaction(PRODUCT_REPOSITORY.getConnectionSource(), new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					PRODUCT_REPOSITORY.removeAll();
					return null;
				}
				
			});
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
   

}
