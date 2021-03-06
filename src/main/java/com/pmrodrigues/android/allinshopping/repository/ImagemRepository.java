package com.pmrodrigues.android.allinshopping.repository;

import java.sql.SQLException;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.pmrodrigues.android.allinshopping.exceptions.DatabaseOperationException;
import com.pmrodrigues.android.allinshopping.models.Imagem;
import com.pmrodrigues.android.allinshopping.models.Produto;

public class ImagemRepository extends AbstractRepository<Imagem, Long> {

	public ImagemRepository(Context context) {
		super(context);
	}

	@Override
	protected Dao<Imagem, Long> getDao() throws SQLException {
		return super.getDatabase().getImagemDao();
	}

	public void delete(final Produto produto) {
		try {
			this.getDao().executeRaw("delete from imagem where produto_id = ?", Long.toString(produto.getId()));
		} catch (SQLException e) {
			throw new DatabaseOperationException("Falha ao excluir as imagens do produto " + produto + " no banco de dados", e);
		}
		
	}
}
