package test.com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadFaixaPrecoEntrega;
import com.pmrodrigues.android.allinshopping.models.FaixaPreco;

public class TestDownloadFaixaPrecoEntrega {

	@Test
	public void testGetAll() throws IntegrationException {
		DownloadFaixaPrecoEntrega download = new DownloadFaixaPrecoEntrega();
		List<FaixaPreco> faixas = download.getAll();
		Assert.assertFalse(faixas.isEmpty());
	}

}
