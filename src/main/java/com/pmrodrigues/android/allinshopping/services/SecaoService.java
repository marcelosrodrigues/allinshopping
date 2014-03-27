package com.pmrodrigues.android.allinshopping.services;

import android.content.Context;

import com.pmrodrigues.android.allinshopping.models.Secao;
import com.pmrodrigues.android.allinshopping.repository.SectionRepository;

public class SecaoService {

	private final SectionRepository REPOSITORY;

	public SecaoService(final Context context) {
		REPOSITORY = new SectionRepository(context);
	}

	public void save(final Secao secao) {

		final Secao founded = REPOSITORY.getById(secao.getId());

		if (founded == null) {
			REPOSITORY.insert(secao);
		} else {
			REPOSITORY.update(founded);
		}

	}
}
