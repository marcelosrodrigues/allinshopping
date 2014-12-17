package com.pmrodrigues.android.allinshopping.async;

import android.content.Context;
import com.pmrodrigues.android.allinshopping.enumations.IntegrationType;
import com.pmrodrigues.android.allinshopping.enumations.ResourceType;
import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.exceptions.NoUniqueRegistryException;
import com.pmrodrigues.android.allinshopping.integration.Integration;
import com.pmrodrigues.android.allinshopping.integration.IntegrationFactory;
import com.pmrodrigues.android.allinshopping.models.*;
import com.pmrodrigues.android.allinshopping.repository.FormaPagamentoRepository;
import com.pmrodrigues.android.allinshopping.services.*;

import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class IntegrationProcess {

	private Context context;
	private final Integration integration;
	private final ResourceBundle bundle = ResourceBundle
			.getBundle("configuration");
	private String username;
	private String password;

	public Context getContext() {
		return context;
	}

	public IntegrationProcess() {
		this.integration = IntegrationFactory.getInstance().getIntegration(
				IntegrationType.PRESTASHOP);

	}

	public IntegrationProcess(final String username, final String password,Context context) {
		this();
		this.username = username;
		this.password = password;
		this.integration.setUserName(username)
						.setPassword(password);
        this.context = context;
	}

	@SuppressWarnings("unchecked")
	
	public void importarEstado() throws IntegrationException { 
		final EstadoService service = new EstadoService(context); 
		final List<Estado> estados = integration.getDownload(
				ResourceType.ESTADOS).list();
		for (final Estado estado : estados) {
			service.save(estado);
		}
	}

	public void enviarPedido() throws Exception {
        (new PedidoService(context)).send();
    }

	@SuppressWarnings("unchecked")
	public void importarCEP() throws IntegrationException { 

		final List<CEP> ceps = integration.getDownload(ResourceType.CEP)
				.list();
		final CEPService service = new CEPService(context); 
		for (CEP cep : ceps) { 
			service.save(cep);
		}
	}

    public void importarFormasPagamento() throws IntegrationException {
        final List<FormaPagamento> formas = integration.getDownload(ResourceType.FORMA_PAGAMENTO).list();
        final FormaPagamentoRepository repository = new FormaPagamentoRepository(context);
        for(final FormaPagamento formaPagamento : formas ){
            final FormaPagamento existed = repository.getById(formaPagamento.getId());
            if (existed != null) {
                repository.delete(existed);
            }
            repository.insert(formaPagamento);
        }
    }

	@SuppressWarnings("unchecked")
	public void importarFaixaPreco() throws IntegrationException { 

		final List<FaixaPreco> faixas = integration.getDownload(
				ResourceType.FAIXA_PRECO).list();
		final CEPService service = new CEPService(context); 
		for (final FaixaPreco faixa : faixas) { 
			service.save(faixa);
		}

	}

	@SuppressWarnings("unchecked")
	public void importarCliente() throws IntegrationException,
			NoUniqueRegistryException { 
		final ClienteService clienteservice = new ClienteService(context); 
		final List<Cliente> clientes = integration.getDownload(
				ResourceType.CLIENTE).list();

		for (final Cliente cliente : clientes) {
			clienteservice.save(cliente);
		}
	}

	@SuppressWarnings("unchecked")
	public void importarProdutos() throws IntegrationException { 
		final ProdutoService produtoservice = new ProdutoService(context);
		produtoservice.removeAll();

		final List<Produto> produtos = integration.getDownload(
				ResourceType.PRODUTOS).list();

		final BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>();
		final ThreadPoolExecutor t = new ThreadPoolExecutor(
											Integer.valueOf(bundle.getString("initial-pool-size")), 
											Integer.valueOf(bundle.getString("max-pool-size")), 
											1,
											TimeUnit.SECONDS, 
											blockingQueue);

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
	public void importarSecao() throws IntegrationException { 

		final SecaoService secaoservice = new SecaoService(context); 
		final List<Secao> secoes = integration.getDownload(ResourceType.SECOES)
				.list();
		for (final Secao secao : secoes) {
			secaoservice.save(secao);
		}
	}

}
