package com.pmrodrigues.android.allinshopping.repository;

import java.sql.SQLException;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.pmrodrigues.android.allinshopping.models.Endereco;

public class EnderecoRepository extends AbstractRepository<Endereco, Long> {

	public EnderecoRepository(Context context) {
		super(context);
	}

	@Override
	protected Dao<Endereco, Long> getDao() throws SQLException {
		return super.getDatabase().getEnderecoDao();
	}

}
