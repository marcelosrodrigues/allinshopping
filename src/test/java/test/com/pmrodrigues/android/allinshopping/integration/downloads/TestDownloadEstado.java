package test.com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadEstado;
import com.pmrodrigues.android.allinshopping.models.Estado;

public class TestDownloadEstado extends TestDownloadProcesses {

	@Test
	public void testGetAll() throws IntegrationException {
		DownloadEstado download = new DownloadEstado();
		List<Estado> estados = download.getAll();
		Assert.assertFalse(estados.isEmpty());
	}

}
