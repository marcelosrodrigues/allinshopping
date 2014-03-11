package com.pmrodrigues.android.allinshopping.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.pmrodrigues.android.allinshopping.models.Atualizacao;
import com.pmrodrigues.android.allinshopping.models.CEP;
import com.pmrodrigues.android.allinshopping.models.Cliente;
import com.pmrodrigues.android.allinshopping.models.Configuracao;
import com.pmrodrigues.android.allinshopping.models.DadosPagamento;
import com.pmrodrigues.android.allinshopping.models.Estado;
import com.pmrodrigues.android.allinshopping.models.FaixaEntrega;
import com.pmrodrigues.android.allinshopping.models.FaixaPreco;
import com.pmrodrigues.android.allinshopping.models.FormaPagamento;
import com.pmrodrigues.android.allinshopping.models.ItemPedido;
import com.pmrodrigues.android.allinshopping.models.Pedido;
import com.pmrodrigues.android.allinshopping.models.Produto;
import com.pmrodrigues.android.allinshopping.models.Promocao;
import com.pmrodrigues.android.allinshopping.models.Secao;

public class DbHelper extends OrmLiteSqliteOpenHelper
{

    private static final String DATABASE_NAME = "allinshopping.db";
    private static final Integer DATABASE_VERSION = 1;
    private Dao<CEP, Long> cepDAO;
    private Dao<Cliente, Long> clienteDao;
    private Dao<Estado, String> estadoDao;
    private Dao<FaixaEntrega, Long> faixaDao;
    private Dao<FaixaPreco, Long> faixaPrecoDao;
    private Dao<FormaPagamento, Long> formaPagamentoDao;
    private Dao<ItemPedido, Long> itemDao;
    private Dao<DadosPagamento, Long> pagtoDao;
    private Dao<Pedido, Long> pedidoDao;
    private Dao<Produto, Long> produtoDao;
    private Dao<Promocao, Long> promocaoDao;
    private Dao<Secao, Long> secaoDao;
    private Dao<Atualizacao, Long> atualizacaoDao;
    private Dao<Configuracao, Long> configuracaoDao;

    public DbHelper(Context context)
    {   
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void createDatabase(ConnectionSource connectionsource)
    {
        try
        {
            Log.i("com.pmrodrigues.android.allinshopping", "gerando banco de dados do projeto");
            TableUtils.createTable(connectionsource, Estado.class);
            TableUtils.createTable(connectionsource, Cliente.class);
            TableUtils.createTable(connectionsource, FormaPagamento.class);
            TableUtils.createTable(connectionsource, Pedido.class);
            TableUtils.createTable(connectionsource, Secao.class);
            TableUtils.createTable(connectionsource, Produto.class);
            TableUtils.createTable(connectionsource, Promocao.class);
            TableUtils.createTable(connectionsource, ItemPedido.class);
            TableUtils.createTable(connectionsource, DadosPagamento.class);
            TableUtils.createTable(connectionsource, CEP.class);
            TableUtils.createTable(connectionsource, FaixaEntrega.class);
            TableUtils.createTable(connectionsource, FaixaPreco.class);
            TableUtils.createTable(connectionsource, Atualizacao.class);
            TableUtils.createTable(connectionsource, Configuracao.class);
            return;
        }
        catch (SQLException sqlexception)
        {   
            Log.e("com.pmrodrigues.android.allinshopping", sqlexception.getMessage());
            throw new RuntimeException(sqlexception);
        }
    }

    public void close()
    {
        super.close();
        estadoDao = null;
        clienteDao = null;
        formaPagamentoDao = null;
        pedidoDao = null;
        secaoDao = null;
        produtoDao = null;
        promocaoDao = null;
        itemDao = null;
        pagtoDao = null;
        cepDAO = null;
        faixaDao = null;
        faixaPrecoDao = null;
        atualizacaoDao = null;
        
    }

    public Dao<CEP, Long> getCEPDao()
        throws SQLException
    {
        if (cepDAO == null)
        {
            cepDAO = getDao(CEP.class);
        }
        return cepDAO;
    }

    public Dao<Cliente,Long> getClienteDao()
        throws SQLException
    {
        if (clienteDao == null)
        {
            clienteDao = getDao(Cliente.class);
        }
        return clienteDao;
    }

    public Dao<DadosPagamento,Long> getDadosPagamento()
        throws SQLException
    {
        if (pagtoDao == null)
        {
            pagtoDao = getDao(DadosPagamento.class);
        }
        return pagtoDao;
    }

    public Dao<Estado,String> getEstadoDao()
        throws SQLException
    {
        if (estadoDao == null)
        {
            estadoDao = getDao(Estado.class);
        }
        return estadoDao;
    }

    public Dao<FaixaEntrega,Long> getFaixaEntregaDao()
        throws SQLException
    {
        if (faixaDao == null)
        {
            faixaDao = getDao(FaixaEntrega.class);
        }
        return faixaDao;
    }

    public Dao<FaixaPreco,Long> getFaixaPrecoDao()
        throws SQLException
    {
        if (faixaPrecoDao == null)
        {   
            faixaPrecoDao = getDao(FaixaPreco.class);
        }
        return faixaPrecoDao;
    }

    public Dao<FormaPagamento,Long> getFormaPagamentoDao()
        throws SQLException
    {
        if (formaPagamentoDao == null)
        {
            formaPagamentoDao = getDao(FormaPagamento.class);
        }
        return formaPagamentoDao;
    }

    public Dao<ItemPedido,Long> getItemDao()
        throws SQLException
    {
        if (itemDao == null)
        {
            itemDao = getDao(ItemPedido.class);
        }
        return itemDao;
    }

    public Dao<Pedido,Long> getPedidoDao()
        throws SQLException
    {
        if (pedidoDao == null)
        {
            pedidoDao = getDao(Pedido.class);
        }
        return pedidoDao;
    }

    public Dao<Produto,Long> getProdutoDao()
        throws SQLException
    {
        if (produtoDao == null)
        {
            produtoDao = getDao(Produto.class);
        }
        return produtoDao;
    }

    public Dao<Promocao,Long> getPromocaoDao()
        throws SQLException
    {
        if (promocaoDao == null)
        {
            promocaoDao = getDao(Promocao.class);
        }
        return promocaoDao;
    }

    public Dao<Secao, Long> getSecaoDao()
        throws SQLException
    {
        if (secaoDao == null)
        {
            secaoDao = getDao(Secao.class);
        }
        return secaoDao;
    }
    
    public Dao<Atualizacao,Long> getAtualizacaoDao() throws SQLException {
    	if( atualizacaoDao == null ) {
    		atualizacaoDao = getDao(Atualizacao.class);
    	}
    	return atualizacaoDao;
    }
    
    public Dao<Configuracao,Long> getConfiguracaoDao() throws SQLException {
    	if( configuracaoDao == null ) {
    		configuracaoDao = getDao(Configuracao.class);
    	}
    	return configuracaoDao;
    }

    public void onCreate(SQLiteDatabase sqlitedatabase, ConnectionSource connectionsource)
    {
        createDatabase(connectionsource);
    }

    public void onUpgrade(SQLiteDatabase sqlitedatabase, ConnectionSource connectionsource, int i, int j)
    {
    }

}
