package test.com.pmrodrigues.android.allinshopping.services;

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
import com.pmrodrigues.android.allinshopping.repository.ProductRepository;
import com.pmrodrigues.android.allinshopping.services.ProdutoService;

@RunWith(RobolectricTestRunner.class)
public class TestProdutoService {

	private ProdutoService service;
	
	private final ResourceBundle bundle = ResourceBundle.getBundle("json_message");
	
	private final DownloadProdutos download = new DownloadProdutos();

	private List<Produto> produtos;
	
	private ProductRepository repository;
	
	private ImagemRepository imageRepository;
	
	@Before
	public void setup() throws IntegrationException {		
		service = new ProdutoService(Robolectric.application.getApplicationContext());
		repository = new ProductRepository(Robolectric.application.getApplicationContext());
		imageRepository = new ImagemRepository(Robolectric.application.getApplicationContext());
		Robolectric.getFakeHttpLayer().addPendingHttpResponse(200, bundle.getString("produto"));
		this.produtos = download.getAll();
	}
	
	@Test
	public void save() {
		final Produto produto = produtos.get(0);		
		service.save(produto);		
		assertNotSame(0L , repository.count());
		assertNotSame(produto.getImagens().size() , imageRepository.count());
	}
	

}
