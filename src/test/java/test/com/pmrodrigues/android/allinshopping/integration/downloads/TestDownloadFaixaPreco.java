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
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadFaixaPreco;
import com.pmrodrigues.android.allinshopping.models.FaixaPreco;

@RunWith(RobolectricTestRunner.class)
public class TestDownloadFaixaPreco {

	private final ResourceBundle bundle = ResourceBundle
			.getBundle("json_message");

	@Before
	public void setup() {

		Robolectric.getFakeHttpLayer().addPendingHttpResponse(200,
				bundle.getString("faixa"));

	}

	@Test
	public void testDeCarregamentoDeLista() throws IntegrationException {
		final DownloadFaixaPreco download = new DownloadFaixaPreco();
		final List<FaixaPreco> faixas = download.getAll();

		Assert.assertFalse(faixas.isEmpty());
	}

}
