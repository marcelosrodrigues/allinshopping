package test.com.pmrodrigues.android.allinshopping.services;

import static org.junit.Assert.assertNotNull;

import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;

import com.pmrodrigues.android.allinshopping.async.IntegrationProcess;
import com.pmrodrigues.android.allinshopping.models.FaixaPreco;
import com.pmrodrigues.android.allinshopping.repository.EstadoRepository;
import com.pmrodrigues.android.allinshopping.services.CEPService;

@RunWith(RobolectricTestRunner.class)
public class TestCEPService {

	private final ResourceBundle integration = ResourceBundle
			.getBundle("integration");
	
	private final ResourceBundle response = ResourceBundle.getBundle("json_message");
	
	private CEPService service;
	
	private EstadoRepository repository;
	
	@Before
	public void setup() throws Exception {
		
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("estado"),response.getString("estado"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cep"),response.getString("cep"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("faixapreco"),response.getString("faixa"));
		
		final Context context = Robolectric.application.getApplicationContext();
		service = new CEPService(context);
		repository = new EstadoRepository(context);
		final IntegrationProcess process = new IntegrationProcess(context);
		process.importarEstado();
		process.importarCEP();
		process.importarFaixaPreco();
		
	}
	
	@Test
	public void pesquisarFaixaPreco() {
				
		final FaixaPreco faixaPreco = service.getFaixaPrecoByCEPDestino(22745L);
		assertNotNull(faixaPreco);
		
	}

}