package test.com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadProdutos;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadResource;
import com.pmrodrigues.android.allinshopping.models.Produto;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class TestDownloadResource {

	@Before
	public void setup() {
		Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
	}

	@Test
	public void testGetResourceByProduto() throws IntegrationException {
		DownloadProdutos downloadProdutos = new DownloadProdutos();
		List<Produto> produtos = downloadProdutos.getAll();

		Produto produto = produtos.get(0);

		DownloadResource resource = new DownloadResource();
		String imageURL = resource.getResourceByProduto(produto);
		Assert.assertNotSame(produto.getImage(), imageURL);
	}

}
