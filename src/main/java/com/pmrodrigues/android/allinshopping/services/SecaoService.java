package com.pmrodrigues.android.allinshopping.services;

import java.util.concurrent.Callable;

import android.content.Context;

import com.j256.ormlite.misc.TransactionManager;
import com.pmrodrigues.android.allinshopping.models.Secao;
import com.pmrodrigues.android.allinshopping.repository.SectionRepository;

public class SecaoService
{

    private final SectionRepository REPOSITORY;

    public SecaoService(Context context)
    {   
        REPOSITORY = new SectionRepository(context);
    }

    public void save(final Secao secao)
    {
        try {
			TransactionManager.callInTransaction(REPOSITORY.getConnectionSource(), new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					Secao pai = null;
					if( secao.getSecaoPai()!= null ) {
						pai = REPOSITORY.getById(secao.getSecaoPai().getId());
					}
					
					Secao founded = REPOSITORY.getById(secao.getId());
					
					if( founded != null ) {
						
						founded.setNome(secao.getNome());
						founded.setSecaoPai(pai);
						REPOSITORY.update(founded);
						
					} else {
						secao.setSecaoPai(pai);
						REPOSITORY.insert(secao);
					}
					return null;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}
