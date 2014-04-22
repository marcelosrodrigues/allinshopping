package com.pmrodrigues.android.allinshopping.repository;

import java.sql.SQLException;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.pmrodrigues.android.allinshopping.models.CEP;

public class CEPRepository extends AbstractRepository<CEP, Long>
{

	public CEPRepository(final Context context)
    {
        super(context);
    }

	@Override
	protected Dao<CEP, Long> getDao() throws SQLException {
		return getDatabase().getCEPDao();
	}

	public CEP findCepByCepCode(final Long cepcode) {
		try {
			CEP cep = getDatabase().getCEPDao().queryBuilder().where()
					.le("inicio", cepcode).and().ge("fim", cepcode)
					.queryForFirst();

			return cep;

		} catch (SQLException e) {
			Log.e("com.pmrodrigues.android.allinshopping", e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	public boolean exists(CEP cep) {
		try {
			return this.getDao().queryBuilder()
								.where()
								.eq("uf", cep.getEstado().getUf()).countOf() > 0;
		} catch (SQLException e) {
			Log.e("com.pmrodrigues.android.allinshopping", e.getMessage(), e);
            throw new RuntimeException(e);
		}
	}
}
