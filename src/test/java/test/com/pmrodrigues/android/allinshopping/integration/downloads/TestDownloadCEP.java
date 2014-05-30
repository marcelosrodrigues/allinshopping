package test.com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;
import java.util.ResourceBundle;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadCEP;
import com.pmrodrigues.android.allinshopping.models.CEP;

@RunWith(RobolectricTestRunner.class)
public class TestDownloadCEP {
	
	private final ResourceBundle bundle = ResourceBundle.getBundle("json_message");

	
	@Before
	public void setup() {
		
		Robolectric.getFakeHttpLayer().addPendingHttpResponse(200, bundle.getString("cep"));
		
	}
	
	@Test
	public void testDeCarregamentoDeLista() throws IntegrationException {
		final DownloadCEP download = new DownloadCEP();
		final List<CEP> ceps = download.list();

		Assert.assertFalse(ceps.isEmpty());
	}

}
