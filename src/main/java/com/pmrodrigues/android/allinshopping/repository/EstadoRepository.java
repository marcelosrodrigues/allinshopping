package com.pmrodrigues.android.allinshopping.repository;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.pmrodrigues.android.allinshopping.models.Estado;

public class EstadoRepository extends AbstractRepository<Estado,String>
{

    public EstadoRepository(Context context)
    {
        super(context);
    }

    public Estado findByUF(String s)
    {
        Estado estado;
        try
        {
            estado = (Estado)super.getDatabase().getEstadoDao().queryForId(s);
        }
        catch (SQLException sqlexception)
        {
            throw new RuntimeException(sqlexception);
        }
        return estado;
    }

    public List<Estado> getAll()
    {
        try
        {
            return super.getDatabase().getEstadoDao().queryForAll();
        }
        catch (SQLException sqlexception)
        {
            throw new RuntimeException(sqlexception);
        }
        
    }

	@Override
	protected Dao<Estado, String> getDao() throws SQLException {
		return getDatabase().getEstadoDao();
	}
}
