package com.pmrodrigues.android.allinshopping.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.pmrodrigues.android.allinshopping.models.Secao;

public class SectionRepository extends AbstractRepository<Secao, Long>
{

    public SectionRepository(Context context)
    {
        super(context);
    }

    public List<Secao> getSections()
        throws SQLException
    {
        List<Secao> secoes = getDatabase().getSecaoDao().queryBuilder().where().isNull("secaoPai_id").query();
        List<Secao> comProduto = new ArrayList<Secao>();
        
        for( Secao secao : secoes ){
        	if( secao.temProduto() ) {
        		comProduto.add(secao);
        	}
        }
        
        
        return comProduto;
    }

	@Override
	protected Dao<Secao, Long> getDao() throws SQLException {
		return getDatabase().getSecaoDao();
	}
}
