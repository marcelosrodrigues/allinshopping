package test.com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadProdutos;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadResource;
import com.pmrodrigues.android.allinshopping.models.Produto;


public class TestDownloadResource extends TestDownloadProcesses {

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
