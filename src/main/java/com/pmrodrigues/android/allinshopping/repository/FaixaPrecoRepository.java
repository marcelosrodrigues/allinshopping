package com.pmrodrigues.android.allinshopping.repository;

import java.sql.SQLException;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.pmrodrigues.android.allinshopping.models.FaixaEntrega;
import com.pmrodrigues.android.allinshopping.models.FaixaPreco;

public class FaixaPrecoRepository extends AbstractRepository<FaixaPreco, Long> {

	public FaixaPrecoRepository(final Context context) {
		super(context);
	}

	public FaixaPreco findFaixaPrecoByCEPAndPeso(FaixaEntrega faixaentrega,
			Long peso) throws NumberFormatException, SQLException {

		return getDao().queryBuilder().where()
				.le(FaixaPreco.PESO_INICIAL_FIELD, peso).and()
				.ge(FaixaPreco.PESO_FINAL_FIELD, peso).and()
				.eq(FaixaPreco.CEP_FIELD_NAME, faixaentrega.getIdFaixa())
				.queryForFirst();
	}

	public boolean isExist(final FaixaPreco faixapreco) {
		try {
			FaixaPreco founded = findByFaixaPreco(faixapreco);
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
		FaixaPreco founded = getDao().queryBuilder().where()
				.eq(FaixaPreco.UF_FIELD_NAME, faixapreco.getDestino().getUf()).and()
				.ge(FaixaPreco.PESO_INICIAL_FIELD, faixapreco.getPesoInicial())
				.and()
				.le(FaixaPreco.PESO_FINAL_FIELD, faixapreco.getPesoFinal())
				.queryForFirst();
		return founded;
	}

	@Override
	protected Dao<FaixaPreco, Long> getDao() throws SQLException {
		return this.getDatabase().getFaixaPrecoDao();
	}

}
