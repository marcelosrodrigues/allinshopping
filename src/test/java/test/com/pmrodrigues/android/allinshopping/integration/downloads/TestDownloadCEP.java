package test.com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import junit.framework.Assert;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadCEP;
import com.pmrodrigues.android.allinshopping.models.CEP;
import com.xtremelabs.robolectric.Robolectric;

public class TestDownloadCEP extends TestDownloadProcesses {

	@Test
	public void testDeCarregamentoDeLista() throws IntegrationException {
		DownloadCEP download = new DownloadCEP();
		List<CEP> ceps = download.getAll();

		Assert.assertFalse(ceps.isEmpty());
	}

	@Test
	public void testCarregarApenasUMRegistro() throws IntegrationException {

		Robolectric.getFakeHttpLayer().interceptHttpRequests(true);
		Robolectric
				.setDefaultHttpResponse(HttpStatus.SC_OK,
						"{ceps: {cep: {id: \"1\",uf: \"SP\",inicial: \"1000\",final: \"19999\"}}}");

		DownloadCEP download = new DownloadCEP();
		List<CEP> ceps = download.getAll();

		Assert.assertFalse(ceps.isEmpty());

	}

}
