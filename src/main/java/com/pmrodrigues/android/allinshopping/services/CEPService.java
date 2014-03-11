package com.pmrodrigues.android.allinshopping.services;

import android.content.Context;
import com.pmrodrigues.android.allinshopping.models.FaixaEntrega;
import com.pmrodrigues.android.allinshopping.models.FaixaPreco;
import com.pmrodrigues.android.allinshopping.repository.CEPRepository;
import java.sql.SQLException;

public class CEPService
{

    private final CEPRepository repository;

    public CEPService(Context context)
    {
        CEPRepository ceprepository = new CEPRepository(context);
        repository = ceprepository;
    }

    public FaixaPreco getFaixaPreco(FaixaEntrega faixaentrega, Long peso)
    {
        FaixaPreco faixapreco;
        try
        {
            faixapreco = repository.findFaixaPrecoByCEPAndPeso(faixaentrega, peso);
        }
        catch (NumberFormatException numberformatexception)
        {
            throw new RuntimeException(numberformatexception);
        }
        catch (SQLException sqlexception)
        {
            throw new RuntimeException(sqlexception);
        }
        return faixapreco;
    }
}
