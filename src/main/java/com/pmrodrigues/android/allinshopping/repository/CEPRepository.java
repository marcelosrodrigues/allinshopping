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

	public CEP findCepByZipCode(final Long cepcode) {
		try {
			
			final CEP cep = getDatabase().getCEPDao()
								   .queryBuilder()
								   .where()
								   .le(CEP.INICIO_FIELD_NAME, cepcode)
								   .and()
								   .ge(CEP.FIM_FIELD_NAME, cepcode)
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
								.eq(CEP.ID_FIELD_NAME, cep.getId()).countOf() > 0;
		} catch (SQLException e) {
			Log.e("com.pmrodrigues.android.allinshopping", e.getMessage(), e);
            throw new RuntimeException(e);
		}
	}
}
