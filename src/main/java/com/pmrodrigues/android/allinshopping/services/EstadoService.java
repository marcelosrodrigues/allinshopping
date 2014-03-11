package com.pmrodrigues.android.allinshopping.services;

import android.content.Context;

import com.pmrodrigues.android.allinshopping.models.Estado;
import com.pmrodrigues.android.allinshopping.repository.EstadoRepository;

public class EstadoService {

	private EstadoRepository repository;

	public EstadoService(Context context) {
		repository = new EstadoRepository(context);
	}
	
	public void save(Estado estado) {
		Estado saved = repository.getById(estado.getUf());
		if(saved == null){
			repository.insert(estado);
		}else{
			repository.update(estado);
		}
	}

}
