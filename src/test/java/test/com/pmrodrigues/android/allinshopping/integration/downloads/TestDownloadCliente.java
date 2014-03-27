package test.com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadCliente;
import com.pmrodrigues.android.allinshopping.models.Cliente;

public class TestDownloadCliente extends TestDownloadProcesses {

	@Test
	public void testGetAll() throws IntegrationException {
		final DownloadCliente downloadCliente = new DownloadCliente();
		final List<Cliente> clientes = downloadCliente.getAll();
		Assert.assertFalse(clientes.isEmpty());
	}

}
