package test.com.pmrodrigues.android.allinshopping.repository;

import static org.junit.Assert.assertNotNull;

import java.util.ResourceBundle;

import com.pmrodrigues.android.allinshopping.MainActivity;
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
		
		final Context context = Robolectric.buildActivity(MainActivity.class).create().get();
		repository = new FaixaPrecoRepository(context);
		ceprepository = new CEPRepository(context);
		final IntegrationProcess process = new IntegrationProcess("teste","teste",context);
		process.importarEstado();
		process.importarCEP();
		process.importarFaixaPreco();
		
	}
	
	@Test
	public void pesquisarFaixaPrecoPorCEP() throws Exception {
		
		final CEP origem = ceprepository.findCepByZipCode(22745L);
		final CEP destino = ceprepository.findCepByZipCode(20775L);
		final FaixaPreco faixa = repository.findFaixaPrecoByCEPAndPeso(origem.getEstado() , destino);
		assertNotNull(faixa);
		
	}

}
