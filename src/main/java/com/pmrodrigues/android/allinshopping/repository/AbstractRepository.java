package com.pmrodrigues.android.allinshopping.repository;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.pmrodrigues.android.allinshopping.database.DbHelper;
import com.pmrodrigues.android.allinshopping.database.DbHelperFactory;
import com.pmrodrigues.android.allinshopping.exceptions.DatabaseOperationException;

public abstract class AbstractRepository<E,T>
{

    private final DbHelper helper;

    public AbstractRepository(Context context)
    {
        DbHelper dbhelper = DbHelperFactory.getDbHelper(context);
        helper = dbhelper;
    }

    public ConnectionSource getConnectionSource()
    {
        return helper.getConnectionSource();
    }

    protected DbHelper getDatabase()
    {
        return helper;
    }
    
    public void insert(E e) {    	    	
    	try {
			this.getDao().create(e);
		} catch (SQLException sqlexp) {
			throw new DatabaseOperationException("Falha ao inserir o valor " + e + " no banco de dados", sqlexp);
		}
    }
    
    public void update(E e){
    	try {
			this.getDao().update(e);
		} catch (SQLException sqlexp) {
			throw new DatabaseOperationException("Falha ao atualizar o valor " + e + " no banco de dados", sqlexp);
		}
    }
    
    public void delete(E e){
    	try {
			this.getDao().delete(e);
		} catch (SQLException sqlexp) {
			throw new DatabaseOperationException("Falha ao excluir o valor " + e + " no banco de dados", sqlexp);
		}
    }
    
    public E getById(T id) {
    	try {
    		
    		if( id != null ) {
    			return this.getDao().queryForId(id);
    		} else {
    			return null;
    		}
    		
		} catch (SQLException sqlexp) {
			throw new DatabaseOperationException("Falha ao pesquisar o id " + id + " no banco de dados", sqlexp);
		}
    }
    
    public List<E> list() {
    	try {
			return this.getDao().queryForAll();
		} catch (SQLException sqlexp) {
			throw new DatabaseOperationException("Falha ao listar os valores do banco de dados", sqlexp);
		}
    }
    
    protected abstract Dao<E,T> getDao() throws SQLException;
}
