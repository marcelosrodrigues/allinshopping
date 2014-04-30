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
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadProdutos;
import com.pmrodrigues.android.allinshopping.models.Produto;

@RunWith(RobolectricTestRunner.class)
public class TestDownloadProduto {

	private final ResourceBundle bundle = ResourceBundle.getBundle("json_message");

	
	@Before
	public void setup() {
		
		Robolectric.getFakeHttpLayer()
				   .addPendingHttpResponse(200, bundle.getString("produto"));
		
	}
	

	@Test
	public void test() throws IntegrationException {
		DownloadProdutos download = new DownloadProdutos();
		List<Produto> produtos = download.getAll();
		Assert.assertFalse(produtos.isEmpty());
	}

}
