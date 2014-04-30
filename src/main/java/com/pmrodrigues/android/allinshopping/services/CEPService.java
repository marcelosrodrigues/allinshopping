package com.pmrodrigues.android.allinshopping.services;

import android.content.Context;

import com.pmrodrigues.android.allinshopping.models.CEP;
import com.pmrodrigues.android.allinshopping.models.FaixaPreco;
import com.pmrodrigues.android.allinshopping.repository.CEPRepository;
import com.pmrodrigues.android.allinshopping.repository.FaixaPrecoRepository;

public class CEPService {

	private final CEPRepository CEP_REPOSITORY;
	private final FaixaPrecoRepository PRECO_REPOSITORY;
	

	public CEPService(final Context context) {
		CEP_REPOSITORY = new CEPRepository(context);
		PRECO_REPOSITORY = new FaixaPrecoRepository(context);		
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
	
}
