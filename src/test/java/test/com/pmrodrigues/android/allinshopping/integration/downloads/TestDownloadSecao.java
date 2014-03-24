package test.com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadSecoes;
import com.pmrodrigues.android.allinshopping.models.Secao;

public class TestDownloadSecao extends TestDownloadProcesses {

	@Test
	public void test() throws IntegrationException {
		DownloadSecoes download = new DownloadSecoes();
		List<Secao> secoes = download.getAll();
		Assert.assertFalse(secoes.isEmpty());

	}

}
