package com.pmrodrigues.android.allinshopping.services;

import java.sql.SQLException;

import android.content.Context;
import android.util.Log;

import com.pmrodrigues.android.allinshopping.models.CEP;
import com.pmrodrigues.android.allinshopping.models.Estado;
import com.pmrodrigues.android.allinshopping.models.FaixaPreco;
import com.pmrodrigues.android.allinshopping.repository.CEPRepository;
import com.pmrodrigues.android.allinshopping.repository.EstadoRepository;
import com.pmrodrigues.android.allinshopping.repository.FaixaPrecoRepository;

public class CEPService {

	private final CEPRepository CEP_REPOSITORY;
	private final FaixaPrecoRepository PRECO_REPOSITORY;
	private final EstadoRepository ESTADO_REPOSITORY;
	private final Estado estado;
	
	public CEPService(final Context context) {
		CEP_REPOSITORY = new CEPRepository(context);
		PRECO_REPOSITORY = new FaixaPrecoRepository(context);
		ESTADO_REPOSITORY = new EstadoRepository(context);
		this.estado = ESTADO_REPOSITORY.findByUF("RJ");
	}

	public void save(final CEP cep) {

		if (CEP_REPOSITORY.exists(cep)) {
			CEP_REPOSITORY.update(cep);		
			
		} else {
			CEP_REPOSITORY.insert(cep);			
		}
	
	}
	
	public void save(final FaixaPreco preco) {
		
		if( PRECO_REPOSITORY.isExist(preco) ) {
			PRECO_REPOSITORY.update(preco);
		} else {
			PRECO_REPOSITORY.insert(preco);
		}
		
		
	}

	public FaixaPreco getFaixaPrecoByCEPDestino( final Long cep) {
		try {
			Log.d("com.pmrodrigues.android.allinshopping", String.format("pesquisando o frete para o destino %s",cep));
			
			
			final CEP destino = CEP_REPOSITORY.findCepByZipCode(cep);
			return PRECO_REPOSITORY.findFaixaPrecoByCEPAndPeso(estado, destino);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
