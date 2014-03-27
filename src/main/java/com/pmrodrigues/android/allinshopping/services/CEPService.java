package com.pmrodrigues.android.allinshopping.services;

import java.sql.SQLException;

import android.content.Context;

import com.pmrodrigues.android.allinshopping.models.CEP;
import com.pmrodrigues.android.allinshopping.models.FaixaEntrega;
import com.pmrodrigues.android.allinshopping.models.FaixaPreco;
import com.pmrodrigues.android.allinshopping.repository.CEPRepository;
import com.pmrodrigues.android.allinshopping.repository.FaixaEntregaRepository;
import com.pmrodrigues.android.allinshopping.repository.FaixaPrecoRepository;

public class CEPService {

	private final CEPRepository CEP_REPOSITORY;
	private final FaixaPrecoRepository PRECO_REPOSITORY;
	private final FaixaEntregaRepository FAIXA_ENTREGA_REPOSITORY;

	public CEPService(final Context context) {
		CEP_REPOSITORY = new CEPRepository(context);
		PRECO_REPOSITORY = new FaixaPrecoRepository(context);
		FAIXA_ENTREGA_REPOSITORY = new FaixaEntregaRepository(context);
	}

	public void save(final CEP cep) {

		if (CEP_REPOSITORY.exists(cep)) {
			CEP_REPOSITORY.update(cep);
		} else {
			CEP_REPOSITORY.insert(cep);
		}

	}

	public void save(final FaixaPreco faixapreco) {

		if (PRECO_REPOSITORY.isExist(faixapreco)) {
			PRECO_REPOSITORY.delete(faixapreco);
		}
		PRECO_REPOSITORY.insert(faixapreco);
	}

	public void save(final FaixaEntrega faixaEntrega) {

		if (FAIXA_ENTREGA_REPOSITORY.isExists(faixaEntrega)) {
			FAIXA_ENTREGA_REPOSITORY.delete(faixaEntrega);
		}
		FAIXA_ENTREGA_REPOSITORY.insert(faixaEntrega);
	}

	public FaixaEntrega findByCEPCode(final Long CEP) {

		return this.FAIXA_ENTREGA_REPOSITORY
				.findFaixaEntregaByCEP(CEP_REPOSITORY.findCepByCepCode(CEP));

	}

	public FaixaPreco getFaixaPreco(final FaixaEntrega faixaentrega,
			final Long peso) {
		try {
			final FaixaPreco faixapreco = PRECO_REPOSITORY
					.findFaixaPrecoByCEPAndPeso(faixaentrega, peso);
			return faixapreco;
		} catch (NumberFormatException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
}
