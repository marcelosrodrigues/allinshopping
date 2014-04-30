package test.com.pmrodrigues.android.allinshopping.async;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;

import com.pmrodrigues.android.allinshopping.async.IntegrationProcess;
import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.exceptions.NoUniqueRegistryException;

@RunWith(RobolectricTestRunner.class)
public class TestIntegrationProcesses {

	private IntegrationProcess process;
	private Context context;

	@Before
	public void setup() {
		Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
		this.context = Robolectric.application.getApplicationContext();
		this.process = new IntegrationProcess(context);
	}

	@Test
	public void testImportarEstado() throws IntegrationException {
		process.importarEstado();
	}

	@Test
	public void testImportarCEP() throws IntegrationException {
		process.importarCEP();
	}
	
	@Test
	public void testImportarFaixaPreco() throws IntegrationException {
		process.importarFaixaPreco();
	}
	
	@Test
	public void testImportarCliente() throws IntegrationException,
			NoUniqueRegistryException {
		process.importarCliente();
	}

	@Test
	public void testImportarProdutos() throws IntegrationException {
		process.importarProdutos();
	}

	@Test
	public void testImportarSecao() throws IntegrationException {
		process.importarSecao();
	}
}
