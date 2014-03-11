package com.pmrodrigues.android.allinshopping.repository;

import java.sql.SQLException;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.pmrodrigues.android.allinshopping.models.Atualizacao;

public class AtualizacaoRepository extends AbstractRepository<Atualizacao,Long> {
	
	public AtualizacaoRepository(Context context) {
		super(context);		
	}
	
	public boolean foiAtualizado() throws SQLException {
		
		return super.getDatabase().getAtualizacaoDao().countOf() > 0L;
		
	}
	
	public Atualizacao ultimaAtualizacao() throws SQLException {
		
		return super.getDatabase().getAtualizacaoDao()
						   			  .queryBuilder()
						   			  .orderBy(Atualizacao.DATA_ATUALIZACAO_FIELD, false)
						   			  .queryForFirst();
		
		
	}

	@Override
	protected Dao<Atualizacao, Long> getDao() throws SQLException {
		return getDatabase().getAtualizacaoDao();
	}

}
