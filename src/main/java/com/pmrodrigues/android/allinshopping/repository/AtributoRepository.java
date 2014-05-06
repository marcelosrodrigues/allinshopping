package com.pmrodrigues.android.allinshopping.repository;

import java.sql.SQLException;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.pmrodrigues.android.allinshopping.exceptions.DatabaseOperationException;
import com.pmrodrigues.android.allinshopping.models.Atributo;
import com.pmrodrigues.android.allinshopping.models.Produto;

public class AtributoRepository extends AbstractRepository<Atributo, Long> {

	public AtributoRepository(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Dao<Atributo, Long> getDao() throws SQLException {
		return super.getDatabase().getAtributoDao();
	}
	
	public void delete(final Produto produto) {
		try {
			this.getDao().executeRaw("delete from atributo where produto_id = ?", Long.toString(produto.getId()));
		} catch (SQLException e) {
			throw new DatabaseOperationException("Falha ao excluir as imagens do produto " + produto + " no banco de dados", e);
		}
		
	}

}
