package com.pmrodrigues.android.allinshopping.repository;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.pmrodrigues.android.allinshopping.models.Produto;
import com.pmrodrigues.android.allinshopping.models.Secao;

public class ProductRepository extends AbstractRepository<Produto,Long>
{

    public ProductRepository(Context context)
    {
        super(context);
    }

    public List<Produto> getBySales()
        throws SQLException
    {
        return getDatabase().getProdutoDao().queryForAll();
    }

    public List<Produto>  getBySection(Secao secao)
        throws SQLException
    {
        Log.i("com.pmrodrigues.android.allinshopping", "Listando os produtos cadastrados na secao " + secao);        
        return getDatabase().getSecaoDao().queryForId(secao.getId()).getProdutos();
    }

    public void remove(Produto produto)
        throws SQLException
    {
    	produto.apagarImagem();
        getDatabase().getProdutoDao().delete(produto);
    }

    public void removeAll()
        throws SQLException
    {   
        List<Produto> produtos = getDatabase().getProdutoDao().queryForAll();
        
        for( Produto produto : produtos ) {
        	produto.apagarImagem();
        	getDatabase().getProdutoDao().delete(produto);
        
        }
    }

	@Override
	protected Dao<Produto, Long> getDao() throws SQLException {
		return  getDatabase().getProdutoDao();
	}


}
