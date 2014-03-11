package com.pmrodrigues.android.allinshopping.repository;

import java.sql.SQLException;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.pmrodrigues.android.allinshopping.models.Configuracao;

public class ConfiguracaoRepository extends AbstractRepository<Configuracao,Long> {

	public ConfiguracaoRepository(Context context) {
		super(context);
	}
	
	public Configuracao get() throws SQLException {
		return getDatabase().getConfiguracaoDao().queryBuilder().queryForFirst();
	}

	@Override
	protected Dao<Configuracao, Long> getDao() throws SQLException {
		return getDatabase().getConfiguracaoDao();
	}

}
