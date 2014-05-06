package test.com.pmrodrigues.android.allinshopping.repository;

import static org.junit.Assert.assertNotSame;

import java.util.List;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadProdutos;
import com.pmrodrigues.android.allinshopping.models.Produto;
import com.pmrodrigues.android.allinshopping.repository.ImagemRepository;
import com.pmrodrigues.android.allinshopping.services.ProdutoService;


@RunWith(RobolectricTestRunner.class)
public class TestImageRepository {

	private ImagemRepository repository;	
	private final ResourceBundle bundle = ResourceBundle.getBundle("json_message");
	private List<Produto> produtos;
	
	@Before
	public void setup() throws IntegrationException {
		
		Robolectric.getFakeHttpLayer()
				   .addPendingHttpResponse(200, bundle.getString("produto"));
		
		this.repository = new ImagemRepository(Robolectric.application.getApplicationContext());
		final DownloadProdutos download = new DownloadProdutos();
		this.produtos = download.getAll();
		
		ProdutoService service = new ProdutoService(Robolectric.application.getApplicationContext());
		for(final Produto produto : this.produtos ){
			service.save(produto);
		}
		
	}
	
	@Test
	public void removerTodasAsImagensPorProduto() {
		
		Produto produto = this.produtos.get(0);
		repository.delete(produto);
		
		assertNotSame(Long.valueOf(produto.getImagens().size()) , repository.count());
	}

}
