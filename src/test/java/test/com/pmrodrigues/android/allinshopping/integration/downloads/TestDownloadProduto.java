package test.com.pmrodrigues.android.allinshopping.integration.downloads;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadProdutos;
import com.pmrodrigues.android.allinshopping.models.Produto;


public class TestDownloadProduto {


	@Test
	public void test() throws IntegrationException {
		DownloadProdutos download = new DownloadProdutos();
		List<Produto> produtos = download.getAll();
		Assert.assertFalse(produtos.isEmpty());
	}

}
