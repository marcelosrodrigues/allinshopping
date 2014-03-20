package test.com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadFaixaEntrega;
import com.pmrodrigues.android.allinshopping.models.FaixaEntrega;

public class TestDownloadFaixaEntrega {

	@Test
	public void testGetAll() throws IntegrationException {
		DownloadFaixaEntrega download = new DownloadFaixaEntrega();
		List<FaixaEntrega> faixas = download.getAll();
		Assert.assertFalse(faixas.isEmpty());
	}

}
