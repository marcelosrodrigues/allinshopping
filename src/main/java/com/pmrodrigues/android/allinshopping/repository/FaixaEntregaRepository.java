package com.pmrodrigues.android.allinshopping.repository;

import java.sql.SQLException;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.pmrodrigues.android.allinshopping.models.CEP;
import com.pmrodrigues.android.allinshopping.models.FaixaEntrega;

public class FaixaEntregaRepository
		extends
			AbstractRepository<FaixaEntrega, Long> {

	public FaixaEntregaRepository(final Context context) {
		super(context);
	}

	@Override
	protected Dao<FaixaEntrega, Long> getDao() throws SQLException {
		return this.getDatabase().getFaixaEntregaDao();
	}

	public FaixaEntrega findFaixaEntregaByCEP(final CEP cep) {
		try {
			FaixaEntrega faixaentrega = null;

			if (cep != null) {

				faixaentrega = getDatabase().getFaixaEntregaDao()
						.queryBuilder().where()
						.eq(FaixaEntrega.UF_ORIGEM_FIELD, "RJ").and()
						.eq(FaixaEntrega.UF_DESTINO_FIELD, cep.getEstado().getUf())
						.queryForFirst();
			}

			return faixaentrega;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public boolean isExists(final FaixaEntrega faixaentrega) {
		try {
			FaixaEntrega founded = findFaixaEntrega(faixaentrega);

			return (founded != null);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void delete(FaixaEntrega faixaentrega) {
		try {
			FaixaEntrega founded = this.findFaixaEntrega(faixaentrega);
			super.delete(founded);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public FaixaEntrega findFaixaEntrega(final FaixaEntrega faixaentrega)
			throws SQLException {

		return getDatabase()
				.getFaixaEntregaDao()
				.queryBuilder()
				.where()
				.eq(FaixaEntrega.UF_ORIGEM_FIELD,
						faixaentrega.getUfOrigem())
				.and()
				.eq(FaixaEntrega.UF_DESTINO_FIELD,
						faixaentrega.getUfDestino()).queryForFirst();

	}

}
