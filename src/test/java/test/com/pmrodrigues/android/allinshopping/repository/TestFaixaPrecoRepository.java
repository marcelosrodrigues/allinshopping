package test.com.pmrodrigues.android.allinshopping.repository;

import static org.junit.Assert.assertNotNull;

import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;

import com.pmrodrigues.android.allinshopping.async.IntegrationProcess;
import com.pmrodrigues.android.allinshopping.models.CEP;
import com.pmrodrigues.android.allinshopping.models.FaixaPreco;
import com.pmrodrigues.android.allinshopping.repository.CEPRepository;
import com.pmrodrigues.android.allinshopping.repository.FaixaPrecoRepository;


@RunWith(RobolectricTestRunner.class)
public class TestFaixaPrecoRepository {

	private FaixaPrecoRepository repository;
	
	private CEPRepository ceprepository;
	
	private final ResourceBundle integration = ResourceBundle
			.getBundle("integration");
	
	private final ResourceBundle response = ResourceBundle.getBundle("json_message");
	
	@Before
	public void setup() throws Exception {
		
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("estado"),response.getString("estado"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cep"),response.getString("cep"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("faixapreco"),response.getString("faixa"));
		
		final Context context = Robolectric.application.getApplicationContext();
		repository = new FaixaPrecoRepository(context);
		ceprepository = new CEPRepository(context);
		final IntegrationProcess process = new IntegrationProcess(context);
		process.importarEstado();
		process.importarCEP();
		process.importarFaixaPreco();
		
	}
	
	@Test
	public void pesquisarFaixaPrecoPorCEP() throws Exception {
		
		final CEP origem = ceprepository.findCepByZipCode(69900L);
		final CEP destino = ceprepository.findCepByZipCode(69999L);
		final FaixaPreco faixa = repository.findFaixaPrecoByCEPAndPeso(origem , destino, 500L);
		assertNotNull(faixa);
		
	}

}