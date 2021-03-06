
package com.pmrodrigues.android.allinshopping.repository;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.pmrodrigues.android.allinshopping.models.Cliente;

public class ClienteRepository extends AbstractRepository<Cliente,Long>
{

    @Override
	protected Dao<Cliente,Long> getDao() throws SQLException {
		return getDatabase().getClienteDao();
	}

    public ClienteRepository(Context context)
    {
        super(context);
    }

	public boolean exists(final Cliente cliente)
    {   
        try
        {
            Log.i("com.pmrodrigues.android.allinshopping", String.format("Verificando se ja existe um cliente salvo com o email informado %s", cliente.getEmail()));
            Long counted = 0L;
        	counted = this.getDao().queryBuilder().where()
        						  .eq(Cliente.EMAIL_FIELD_NAME, cliente.getEmail())
        						  .countOf();
            	
			return counted > 0L;
        }
        catch (SQLException sqlexception)
        {   
            throw new RuntimeException(sqlexception);
        }
        
    }
   

    @Override
	public List<Cliente> list()
    {
        
        try
        {
            Log.i("com.pmrodrigues.android.allinshopping", "Listando todos os clientes cadastrados");
            return this.getDao().queryBuilder()
                                .orderBy(Cliente.PRIMEIRO_NOME_FIELD_NAME, true)
                                .orderBy(Cliente.ULTIMO_NOME_FIELD_NAME,true)
                                .query();
        }
        catch (SQLException sqlexception)
        {
        	Log.e("com.pmrodrigues.android.allinshopping", "Erro ao listar os clientes " + sqlexception.getMessage(), sqlexception);
            throw new RuntimeException(sqlexception);
        }
    }

    public List<Cliente> listToBeSend()
    {
        
        try
        {
            Log.i("com.pmrodrigues.android.allinshopping", "Listando todos os clientes cadastrados");
            return this.getDao().queryBuilder().where().isNull(Cliente.BACKOFFICE_ID_FIELD_NAME).query();
        }
        catch (SQLException sqlexception)
        {
            Log.e("com.pmrodrigues.android.allinshopping", "Erro ao listar os clientes " + sqlexception.getMessage(), sqlexception);
            throw new RuntimeException(sqlexception);
        }
        
    }
	
}
