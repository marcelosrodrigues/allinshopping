package test.com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadCEP;
import com.pmrodrigues.android.allinshopping.models.CEP;

public class TestDownloadCEP {

	@Test
	public void testGetAll() throws IntegrationException {
		DownloadCEP download = new DownloadCEP();
		List<CEP> ceps = download.getAll();

		Assert.assertFalse(ceps.isEmpty());
	}

}
