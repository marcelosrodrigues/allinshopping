package com.pmrodrigues.android.allinshopping.repository;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.pmrodrigues.android.allinshopping.models.FormaPagamento;

public class FormaPagamentoRepository extends AbstractRepository<FormaPagamento,Long>
{

    public FormaPagamentoRepository(Context context)
    {
        super(context);
       
    }

    public List<FormaPagamento> list()
    {
        try
        {
            Log.i("com.pmrodrigues.android.allinshopping", "Listando todas as formas de pagamento aceitas");
            return getDatabase().getFormaPagamentoDao().queryBuilder().orderBy("id", true).query();
        }
        catch (SQLException sqlexception)
        {
            Log.e("com.pmrodrigues.android.allinshopping", "Erro ao listar as formas de pagamento " + sqlexception.getMessage(), sqlexception);
            throw new RuntimeException(sqlexception);
        }
        
    }

	@Override
	protected Dao<FormaPagamento, Long> getDao() throws SQLException {
		return getDatabase().getFormaPagamentoDao();
	}
}
