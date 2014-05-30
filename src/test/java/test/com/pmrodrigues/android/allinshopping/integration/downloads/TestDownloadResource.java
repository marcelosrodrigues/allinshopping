package test.com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import test.com.pmrodrigues.android.allinshopping.responserules.HttpEntityResponseRule;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadProdutos;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadResource;
import com.pmrodrigues.android.allinshopping.models.Produto;

@RunWith(RobolectricTestRunner.class)
public class TestDownloadResource {

	private final ResourceBundle bundle = ResourceBundle
			.getBundle("json_message");

	@Before
	public void setup() {
	
		Robolectric.getFakeHttpLayer().addPendingHttpResponse(200,
				bundle.getString("produto"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(new HttpEntityResponseRule());

	}

	@Test
	public void testGetResourceByProduto() throws IntegrationException {
		final DownloadProdutos downloadProdutos = new DownloadProdutos();
		final List<Produto> produtos = downloadProdutos.list();

		final Produto produto = produtos.get(0);

		final DownloadResource resource = new DownloadResource();
		resource.getResourceByProduto(produto);

	}

}
