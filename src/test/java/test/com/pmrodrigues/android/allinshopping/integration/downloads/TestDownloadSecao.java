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
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadSecoes;
import com.pmrodrigues.android.allinshopping.models.Secao;

@RunWith(RobolectricTestRunner.class)
public class TestDownloadSecao {
	
	private final ResourceBundle bundle = ResourceBundle.getBundle("json_message");
	
	@Before
	public void setup() {
		
		Robolectric.getFakeHttpLayer().addPendingHttpResponse(200, bundle.getString("secao"));
		
	}

	@Test
	public void test() throws IntegrationException {
		DownloadSecoes download = new DownloadSecoes("teste","teste");
		List<Secao> secoes = download.list();
		Assert.assertFalse(secoes.isEmpty());

	}

}
