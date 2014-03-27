package com.pmrodrigues.android.allinshopping.services;

import java.io.File;
import java.sql.SQLException;

import android.content.Context;

import com.pmrodrigues.android.allinshopping.models.Produto;
import com.pmrodrigues.android.allinshopping.repository.ProductRepository;
import com.pmrodrigues.android.allinshopping.utilities.Constante;

public class ProdutoService {
	private final ProductRepository PRODUCT_REPOSITORY;

	public ProdutoService(Context context) {
		PRODUCT_REPOSITORY = new ProductRepository(context);
	}

	public void save(final Produto produto) {
		try {
			if (!PRODUCT_REPOSITORY.exists(produto)) {
				PRODUCT_REPOSITORY.insert(produto);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void removeAll() {
		try {
			PRODUCT_REPOSITORY.removeAll();
			File directory = new File(Constante.SDCARD_ALLINSHOPP_IMAGES);
			if (directory.exists()) {
				directory.delete();
			}
			directory.mkdir();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
