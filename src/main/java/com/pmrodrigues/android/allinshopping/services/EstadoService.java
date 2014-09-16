package com.pmrodrigues.android.allinshopping.services;

import android.content.Context;

import com.pmrodrigues.android.allinshopping.models.Estado;
import com.pmrodrigues.android.allinshopping.repository.EstadoRepository;

public class EstadoService {

	private final EstadoRepository repository;

	public EstadoService(final Context context) {
		repository = new EstadoRepository(context);
	}

	public void save(final Estado estado) {

        final Estado saved = repository.findByUF(estado.getUf());
        if (saved == null) {
            repository.insert(estado);
        } else {
            repository.update(estado);
        }

	}

}
