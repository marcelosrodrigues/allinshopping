package com.pmrodrigues.android.allinshopping.services;

import java.io.File;
import java.sql.SQLException;

import android.content.Context;

import com.pmrodrigues.android.allinshopping.models.Atributo;
import com.pmrodrigues.android.allinshopping.models.Imagem;
import com.pmrodrigues.android.allinshopping.models.Produto;
import com.pmrodrigues.android.allinshopping.repository.AtributoRepository;
import com.pmrodrigues.android.allinshopping.repository.ImagemRepository;
import com.pmrodrigues.android.allinshopping.repository.ProductRepository;
import com.pmrodrigues.android.allinshopping.utilities.Constante;

public class ProdutoService {
	private final ProductRepository PRODUCT_REPOSITORY;
	private final ImagemRepository IMAGEM_REPOSITORY;
	private final AtributoRepository ATRIBUTO_REPOSITORY;

	public ProdutoService(Context context) {
		PRODUCT_REPOSITORY = new ProductRepository(context);
		IMAGEM_REPOSITORY = new ImagemRepository(context);
		ATRIBUTO_REPOSITORY = new AtributoRepository(context);
	}

	public void save(final Produto produto) {
		try {
			if (PRODUCT_REPOSITORY.exists(produto)) {
				IMAGEM_REPOSITORY.delete(produto);
				ATRIBUTO_REPOSITORY.delete(produto);
				PRODUCT_REPOSITORY.delete(produto);
			}
			PRODUCT_REPOSITORY.insert(produto);

			for (final Imagem imagem : produto.getImagens()) {
				IMAGEM_REPOSITORY.insert(imagem);
			}

			for (final Atributo atributo : produto.getAtributos()) {
				ATRIBUTO_REPOSITORY.insert(atributo);
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
