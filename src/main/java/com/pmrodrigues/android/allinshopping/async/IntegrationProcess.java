package com.pmrodrigues.android.allinshopping.async;

import java.util.List;

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
import com.pmrodrigues.android.allinshopping.models.FaixaEntrega;
import com.pmrodrigues.android.allinshopping.models.FaixaPreco;
import com.pmrodrigues.android.allinshopping.models.Produto;
import com.pmrodrigues.android.allinshopping.models.Secao;
import com.pmrodrigues.android.allinshopping.repository.CEPRepository;
import com.pmrodrigues.android.allinshopping.services.ClienteService;
import com.pmrodrigues.android.allinshopping.services.EstadoService;
import com.pmrodrigues.android.allinshopping.services.PedidoService;
import com.pmrodrigues.android.allinshopping.services.ProdutoService;
import com.pmrodrigues.android.allinshopping.services.SecaoService;

public class IntegrationProcess {
	
	public Context getContext() {
		return context;
	}

	private Context context;
	private Integration integration;

	public IntegrationProcess(Context context) {
		integration = IntegrationFactory.getInstance().getIntegration(
				IntegrationType.PRESTASHOP);
		this.context = context;		
	}
	
	public void importarEstado() throws IntegrationException {
		EstadoService service = new EstadoService(context);
		List<Estado> estados = integration.getDownload(ResourceType.ESTADOS).getAll();
		for(Estado estado : estados) {
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

	public void importarCEP() throws IntegrationException {
		CEPRepository ceprepository = new CEPRepository(context);
		List<CEP> ceps = integration.getDownload(ResourceType.CEP).getAll();
		for (CEP cep : ceps) {
			if( !ceprepository.exists(cep) ) {
				ceprepository.insert(cep);
			} else {
				ceprepository.update(cep);
			} 
		}
	}

	public void importarCliente() throws IntegrationException,
			NoUniqueRegistryException {
		ClienteService clienteservice = new ClienteService(context);
		List<Cliente> clientes = integration.getDownload(ResourceType.CLIENTE)
				.getAll();
		for (Cliente cliente : clientes) {
			if (!clienteservice.exists(cliente)) {
				clienteservice.save(cliente);
			} else {
				clienteservice.update(cliente);
			}
		}
	}

	public void importarFaixaEntrega() throws IntegrationException {

		CEPRepository ceprepository = new CEPRepository(context);
		List<FaixaEntrega> faixas = integration.getDownload(
				ResourceType.FAIXA_ENTREGA).getAll();
		for (FaixaEntrega faixa : faixas) {
			ceprepository.insert(faixa);
		}

	}

	public void importarFaixaPreco() throws IntegrationException {
		CEPRepository ceprepository = new CEPRepository(context);
		List<FaixaPreco> faixas = integration.getDownload(
				ResourceType.FAIXA_PRECO).getAll();
		for (FaixaPreco faixa : faixas) {
			ceprepository.insert(faixa);
		}
	}

	public void importarProdutos() throws IntegrationException {
		ProdutoService produtoservice = new ProdutoService(context);
		produtoservice.removeAll();
		List<Produto> produtos = integration.getDownload(ResourceType.PRODUTOS).getAll();
		for(Produto produto : produtos ) {
			produtoservice.save(produto);
		}
	}

	public void importarSecao() throws IntegrationException {
		
		SecaoService secaoservice = new SecaoService(context);
		
		List<Secao> secoes = integration.getDownload(ResourceType.SECOES).getAll();
		for(Secao secao : secoes ){
			secaoservice.save(secao);
		}
	}

}