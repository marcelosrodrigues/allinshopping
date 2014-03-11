package com.pmrodrigues.android.allinshopping.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.akatus.connect.api.v1.entity.Transaction.PaymentMethod;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.pmrodrigues.android.allinshopping.models.FormaPagamento;

public class DummyDbHelper extends DbHelper
{

    public DummyDbHelper(Context context)
    {
        super(context);
    }

    void createFormaPagamento()
        throws SQLException
    {
        Dao<FormaPagamento,Long> dao = super.getFormaPagamentoDao();
        dao.createIfNotExists(new FormaPagamento(2L, "Cartão de Crédito Visa", PaymentMethod.CARTAO_VISA));
        dao.createIfNotExists(new FormaPagamento(3L, "Cartão de Crédito MasterCard", PaymentMethod.CARTAO_MASTER));
        dao.createIfNotExists(new FormaPagamento(4L, "Cartão de Crédito Diners", PaymentMethod.CARTAO_DINERS));
        dao.createIfNotExists(new FormaPagamento(5L, "Cartão de Crédito Elo", PaymentMethod.CARTAO_ELO));
        dao.createIfNotExists(new FormaPagamento(6L, "Cartão de Crédito America Express", PaymentMethod.CARTAO_AMEX));
        //dao.createIfNotExists(new FormaPagamento(8L, "Débito em conta Bradesco", PaymentMethod.TEF_BRADESCO));
        //dao.createIfNotExists(new FormaPagamento(9L, "Débito em conta Itaú", PaymentMethod.TEF_ITAU));
        
    }

    public void onCreate(SQLiteDatabase sqlitedatabase, ConnectionSource connectionsource)
    {
        super.onCreate(sqlitedatabase, connectionsource);
        try
        {
            Log.i("com.pmrodrigues.android.allinshopping", "gerando banco de dados do projeto");
            createFormaPagamento();
            return;
        }
        catch (SQLException sqlexception)
        {
            Log.e("com.pmrodrigues.android.allinshopping", sqlexception.getMessage());
            throw new RuntimeException(sqlexception);
        }
    }
}
