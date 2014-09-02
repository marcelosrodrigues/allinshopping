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
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadEstado;
import com.pmrodrigues.android.allinshopping.models.Estado;

@RunWith(RobolectricTestRunner.class)
public class TestDownloadEstado  {
	
	private final ResourceBundle bundle = ResourceBundle.getBundle("json_message");

	
	@Before
	public void setup() {
		
		Robolectric.getFakeHttpLayer().addPendingHttpResponse(200, bundle.getString("estado"));
		
	}
	

	@Test
	public void testGetAll() throws IntegrationException {
		DownloadEstado download = new DownloadEstado("teste","teste");
		List<Estado> estados = download.list();
		Assert.assertFalse(estados.isEmpty());
	}

}
