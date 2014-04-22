package com.pmrodrigues.android.allinshopping.async;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.pmrodrigues.android.allinshopping.enumations.IntegrationType;
import com.pmrodrigues.android.allinshopping.enumations.ResourceType;
import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.exceptions.NoUniqueRegistryException;
import com.pmrodrigues.android.allinshopping.integration.Integration;
import com.pmrodrigues.android.allinshopping.integration.IntegrationFactory;
import com.pmrodrigues.android.allinshopping.integration.upload.Upload;
import com.pmrodrigues.android.allinshopping.models.CEP;
import com.pmrodrigues.android.allinshopping.models.Cliente;
import com.pmrodrigues.android.allinshopping.models.Estado;
import com.pmrodrigues.android.allinshopping.models.Produto;
import com.pmrodrigues.android.allinshopping.models.Secao;
import com.pmrodrigues.android.allinshopping.services.CEPService;
import com.pmrodrigues.android.allinshopping.services.ClienteService;
import com.pmrodrigues.android.allinshopping.services.EstadoService;
import com.pmrodrigues.android.allinshopping.services.PedidoService;
import com.pmrodrigues.android.allinshopping.services.ProdutoService;
import com.pmrodrigues.android.allinshopping.services.SecaoService;

public class IntegrationProcess {

	private final Context context;
	private final Integration integration;

	public Context getContext() {
		return context;
	}

	public IntegrationProcess(final Context context) {
		this.integration = IntegrationFactory.getInstance().getIntegration(
				IntegrationType.PRESTASHOP);
		this.context = context;
	}

	@SuppressWarnings("unchecked")
	// NOPMD
	public void importarEstado() throws IntegrationException { // NOPMD
		final EstadoService service = new EstadoService(context); // NOPMD
		final List<Estado> estados = integration.getDownload(
				ResourceType.ESTADOS).getAll();
		for (final Estado estado : estados) {
			service.save(estado);
		}
	}

	public void enviarCliente() throws IntegrationException, JSONException {
		ClienteService clienteservice = new ClienteService(context);
		Upload upload = integration.getUpload(ResourceType.CLIENTE);
		List<Cliente> clientes = clienteservice.listToBeSend();
		for (Cliente cliente : clientes) {
			JSONObject jsonobject = upload.sendTo(cliente.toJSON());
			cliente.setIdPrestashop(jsonobject.getJSONObject("cliente")
					.getLong("id"));
			cliente.setIdAddress(jsonobject.getJSONObject("cliente").getLong(
					"address"));
			clienteservice.update(cliente);
		}

	}

	public void enviarPedido() throws Exception {
		(new PedidoService(context)).sendToBackoffice();
	}

	@SuppressWarnings("unchecked")
	public void importarCEP() throws IntegrationException { // NOPMD

		final List<CEP> ceps = integration.getDownload(ResourceType.CEP)
				.getAll();
		final CEPService service = new CEPService(context); // NOPMD
		for (CEP cep : ceps) { // NOPMD
			service.save(cep);
		}
	}

	@SuppressWarnings("unchecked")
	public void importarCliente() throws IntegrationException,
			NoUniqueRegistryException { // NOPMD
		final ClienteService clienteservice = new ClienteService(context); // NOPMD
		final List<Cliente> clientes = integration.getDownload(
				ResourceType.CLIENTE).getAll();

		for (final Cliente cliente : clientes) {
			clienteservice.save(cliente);
		}
	}

	@SuppressWarnings("unchecked")
	public void importarProdutos() throws IntegrationException { // NOPMD
		final ProdutoService produtoservice = new ProdutoService(context);
		produtoservice.removeAll();

		final List<Produto> produtos = integration.getDownload(
				ResourceType.PRODUTOS).getAll();

		final BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>();
		final ThreadPoolExecutor t = new ThreadPoolExecutor(15, 15, 1,
				TimeUnit.SECONDS, blockingQueue);
		
		for (final Produto produto : produtos) {
			t.execute(new IntegrationProdutoRunnable(produto, produtoservice));
		}

		do {
			if (t.getActiveCount() == 0
					&& t.getCompletedTaskCount() == produtos.size()) {
				t.shutdown();
				break;
			}
		} while (true);

	}

	@SuppressWarnings("unchecked")
	public void importarSecao() throws IntegrationException { // NOPMD

		final SecaoService secaoservice = new SecaoService(context); // NOPMD
		final List<Secao> secoes = integration.getDownload(ResourceType.SECOES)
				.getAll();
		for (final Secao secao : secoes) {
			secaoservice.save(secao);
		}
	}

}