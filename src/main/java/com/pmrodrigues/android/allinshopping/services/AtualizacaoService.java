package com.pmrodrigues.android.allinshopping.services;

import java.sql.SQLException;

import org.joda.time.DateTime;

import android.content.Context;

import com.pmrodrigues.android.allinshopping.models.Atualizacao;
import com.pmrodrigues.android.allinshopping.models.Configuracao;
import com.pmrodrigues.android.allinshopping.repository.AtualizacaoRepository;
import com.pmrodrigues.android.allinshopping.repository.ConfiguracaoRepository;

public class AtualizacaoService {

	private AtualizacaoRepository repository;
	
	private ConfiguracaoRepository configRepository;
	
	public AtualizacaoService(Context context) {
		repository = new AtualizacaoRepository(context);
		configRepository = new ConfiguracaoRepository(context);
	}
	
	public boolean precisaAtualizar() {
		try {
			boolean atualiza = repository.foiAtualizado();
			
			if(atualiza) {
				
				Atualizacao ultima = repository.ultimaAtualizacao();
				DateTime agora = DateTime.now();
				DateTime ultimaAtualizacao = new DateTime(ultima.getDataAtualizacao());
					
				return !( agora.dayOfMonth().equals( ultimaAtualizacao.dayOfMonth() ) && agora.monthOfYear().equals(  ultimaAtualizacao.monthOfYear() ) && agora.year().equals( ultimaAtualizacao.year() ) );
				
			}
			
			return !atualiza;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void atualizar(Configuracao configuracao) {
			
			
			Configuracao saved = configRepository.getById(configuracao.getId());
			if(saved != null){
				configRepository.update(configuracao);
			}else{
				configRepository.insert(configuracao);
			}
		
	}

	public Configuracao get() {
		try {
			Configuracao configuracao =  configRepository.get();
			if( configuracao == null ) {
				configuracao = new Configuracao();
			}
			return configuracao;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void atualizar() {
		repository.insert(new Atualizacao());
	}
}
