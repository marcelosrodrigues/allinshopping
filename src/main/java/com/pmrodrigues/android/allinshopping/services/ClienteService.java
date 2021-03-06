package com.pmrodrigues.android.allinshopping.services;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.misc.TransactionManager;
import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.exceptions.NoUniqueRegistryException;
import com.pmrodrigues.android.allinshopping.models.Cliente;
import com.pmrodrigues.android.allinshopping.repository.ClienteRepository;
import com.pmrodrigues.android.allinshopping.repository.EnderecoRepository;

public class ClienteService {

	private final ClienteRepository CLIENTE_DAO;
	private final EnderecoRepository ENDERECO_DAO;

	public ClienteService(Context context) {
		CLIENTE_DAO = new ClienteRepository(context);
		ENDERECO_DAO = new EnderecoRepository(context);
	}

	public boolean exists(Cliente cliente) {
		return CLIENTE_DAO.exists(cliente);
	}

	public List<Cliente> listToBeSend() throws IntegrationException {
		return CLIENTE_DAO.listToBeSend();
	}

	public Cliente save(final Cliente cliente) throws NoUniqueRegistryException {
		if (!CLIENTE_DAO.exists(cliente)
				&& (cliente.getId() == null || cliente.getId() == 0L)) {
			CLIENTE_DAO.insert(cliente);
		} else if (cliente.getId() > 0L) {
			this.update(cliente);
		} else {
			throw new NoUniqueRegistryException(
					"Não foi possível salvar o cliente. Já existe outro cadastrado com o mesmo CPF ou E-mail");
		}

		return cliente;
	}

	public void update(final Cliente cliente) {

            ENDERECO_DAO.update(cliente.getEndereco());
            CLIENTE_DAO.update(cliente);

	}
}
