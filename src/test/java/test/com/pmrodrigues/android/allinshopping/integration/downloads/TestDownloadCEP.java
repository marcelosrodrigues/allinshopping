package test.com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadCEP;
import com.pmrodrigues.android.allinshopping.models.CEP;

public class TestDownloadCEP extends TestDownloadProcesses {

	@Test
	public void testDeCarregamentoDeLista() throws IntegrationException {
		final DownloadCEP download = new DownloadCEP();
		final List<CEP> ceps = download.getAll();

		Assert.assertFalse(ceps.isEmpty());
	}

}
