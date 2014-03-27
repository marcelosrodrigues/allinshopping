package com.pmrodrigues.android.allinshopping.services;

import java.util.List;

import android.content.Context;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.exceptions.NoUniqueRegistryException;
import com.pmrodrigues.android.allinshopping.models.Cliente;
import com.pmrodrigues.android.allinshopping.models.Estado;
import com.pmrodrigues.android.allinshopping.repository.ClienteRepository;
import com.pmrodrigues.android.allinshopping.repository.EstadoRepository;

public class ClienteService {

	private final ClienteRepository CLIENTE_DAO;
	private final EstadoRepository ESTADO_DAO;

	public ClienteService(Context context) {
		CLIENTE_DAO = new ClienteRepository(context);
		ESTADO_DAO = new EstadoRepository(context);
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
			Estado estado = ESTADO_DAO.findByUF(cliente.getEstado().getUf());
			cliente.setEstado(estado);
			CLIENTE_DAO.insert(cliente);
		} else if (cliente.getId() > 0L) {
			CLIENTE_DAO.update(cliente);
		} else {
			throw new NoUniqueRegistryException(
					"Não foi possível salvar o cliente. Já existe outro cadastrado com o mesmo CPF ou E-mail");
		}

		return cliente;
	}

	public void update(Cliente cliente) {
		Estado estado = ESTADO_DAO.findByUF(cliente.getEstado().getUf());
		cliente.setEstado(estado);
		CLIENTE_DAO.update(cliente);
	}
}
