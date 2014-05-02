package com.pmrodrigues.android.allinshopping.repository;

import java.sql.SQLException;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.pmrodrigues.android.allinshopping.models.CEP;
import com.pmrodrigues.android.allinshopping.models.FaixaPreco;

public class FaixaPrecoRepository extends AbstractRepository<FaixaPreco, Long> {

	public FaixaPrecoRepository(final Context context) {
		super(context);
	}

	public FaixaPreco findFaixaPrecoByCEPAndPeso(final CEP origem , final CEP destino,
			final Long peso) throws NumberFormatException, SQLException {
		
		return getDao().queryBuilder()
				.where()
				.eq(FaixaPreco.ORIGEM_FIELD_NAME , origem.getEstado().getId())
				.and()
				.le(FaixaPreco.PESO_INICIAL_FIELD, peso)
				.and()
				.ge(FaixaPreco.PESO_FINAL_FIELD, peso)
				.and()
				.eq(FaixaPreco.DESTINO_FIELD_NAME, destino.getEstado().getId())
				.queryForFirst();
	}

	public boolean isExist(final FaixaPreco faixapreco) {
		try {
			final FaixaPreco founded = findByFaixaPreco(faixapreco);
			return (founded != null);

		} catch (SQLException e) {
			Log.e("com.pmrodrigues.android.allinshopping", String.format(
					"Ocorreu um erro no salvamento do faixa de entrega %s",
					faixapreco), e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(final FaixaPreco faixapreco) {

		try {
			FaixaPreco founded = this.findByFaixaPreco(faixapreco);
			if (founded != null) {
				super.delete(founded);
			}
		} catch (SQLException e) {
			Log.e("com.pmrodrigues.android.allinshopping", String.format(
					"Ocorreu um erro no salvamento do faixa de entrega %s",
					faixapreco), e);
			throw new RuntimeException(e);
		}

	}

	public FaixaPreco findByFaixaPreco(final FaixaPreco faixapreco)
			throws SQLException {
		FaixaPreco founded = this.getById(faixapreco.getId());
		return founded;
	}

	@Override
	protected Dao<FaixaPreco, Long> getDao() throws SQLException {
		return this.getDatabase().getFaixaPrecoDao();
	}

	

}
