package com.pmrodrigues.android.allinshopping.repository;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.pmrodrigues.android.allinshopping.models.CEP;
import com.pmrodrigues.android.allinshopping.models.FaixaEntrega;
import com.pmrodrigues.android.allinshopping.models.FaixaPreco;

public class CEPRepository extends AbstractRepository<CEP, Long>
{

    public CEPRepository(Context context)
    {
        super(context);
    }

    public FaixaPreco findFaixaPrecoByCEPAndPeso(FaixaEntrega faixaentrega, Long peso)
        throws NumberFormatException, SQLException
    {
        return getDatabase().getFaixaPrecoDao()
        					.queryBuilder()
        					.where()
        					.le(FaixaPreco.PESO_INICIAL_FIELD, peso)
        					.and()
        					.ge(FaixaPreco.PESO_FINAL_FIELD, peso)
        					.and()
        					.eq(FaixaPreco.ID_FAIXA_FIELD, faixaentrega.getIdFaixa()).queryForFirst();
    }

    public FaixaEntrega getFaixaEntrega(Long cep)
    {
    	try {
			FaixaEntrega faixaentrega = null;
			List<CEP> ceps = getDatabase().getCEPDao()
											   .queryBuilder()
											   .where()
											   .le("inicio", cep)
											   .and()
											   .ge("fim", cep)
											   .query();
			
			if( !ceps.isEmpty() ) {
			
				CEP founded = ceps.get(0);
				faixaentrega = getDatabase().getFaixaEntregaDao()
							 				.queryBuilder()
							 				.where()
							 				.eq(FaixaEntrega.UF_ORIGEM_FIELD, "RJ")
							 				.and()
							 				.eq(FaixaEntrega.UF_DESTINO_FIELD, founded.getUf())
							 				.queryForFirst();
			}
			
			return faixaentrega;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
        
        
    }

    public void insert(FaixaEntrega faixaentrega)
    {
        try
        {
            
            Log.d("com.pmrodrigues.android.allinshopping", String.format("Inserindo o faixa de entrega %s na tabela de faixas de entrega", faixaentrega));
            FaixaEntrega founded = getDatabase().getFaixaEntregaDao()
						            			 .queryBuilder()
						            			 .where()
						            			 .eq(FaixaEntrega.UF_ORIGEM_FIELD, faixaentrega.getUfOrigem())
						            			 .and()
						            			 .eq(FaixaEntrega.UF_DESTINO_FIELD, faixaentrega.getUfDestino()).queryForFirst();
            if (founded != null)
            {
                getDatabase().getFaixaEntregaDao().delete(founded);
            }
            getDatabase().getFaixaEntregaDao().create(faixaentrega);
            Log.d("com.pmrodrigues.android.allinshopping", String.format("Faixa de entrega %s salva com sucesso", faixaentrega));
            return;
        }
        catch (SQLException sqlexception)
        {   
            Log.e("com.pmrodrigues.android.allinshopping", String.format("Ocorreu um erro no salvamento do faixa de entrega %s",faixaentrega), sqlexception);
            throw new RuntimeException(sqlexception);
        }
    }

    public void insert(FaixaPreco faixapreco)
    {
        try
        {
            
            Log.d("com.pmrodrigues.android.allinshopping", String.format("Inserindo o faixa de entrega %s na tabela de faixas de entrega", faixapreco));
            FaixaPreco founded = getDatabase().getFaixaPrecoDao()
            			 .queryBuilder()
            			 .where()
            			 .eq(FaixaPreco.ID_FAIXA_FIELD, faixapreco.getIdFaixa())
            			 .and()
            			 .ge(FaixaPreco.PESO_INICIAL_FIELD, faixapreco.getPesoInicial())
            			 .and()
            			 .le(FaixaPreco.PESO_FINAL_FIELD, faixapreco.getPesoFinal())
            			 .queryForFirst();
            
            if (founded != null)
            {
            	 getDatabase().getFaixaPrecoDao().delete(founded);
            }
            getDatabase().getFaixaPrecoDao().create(faixapreco);
            Log.d("com.pmrodrigues.android.allinshopping", String.format("Faixa de entrega %s salva com sucesso", faixapreco));
            return;
        }
        catch (SQLException sqlexception)
        {
            Log.e("com.pmrodrigues.android.allinshopping", String.format("Ocorreu um erro no salvamento do faixa de entrega %s",faixapreco), sqlexception);
            throw new RuntimeException(sqlexception);
        }
    }

	@Override
	protected Dao<CEP, Long> getDao() throws SQLException {
		return getDatabase().getCEPDao();
	}

	public boolean exists(CEP cep) {
		try {
			return this.getDao().queryBuilder()
								.where()
								.eq("uf", cep.getUf()).countOf() > 0;
		} catch (SQLException e) {
			Log.e("com.pmrodrigues.android.allinshopping", e.getMessage(), e);
            throw new RuntimeException(e);
		}
	}
}
