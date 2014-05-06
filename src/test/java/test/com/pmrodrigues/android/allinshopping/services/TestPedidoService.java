package test.com.pmrodrigues.android.allinshopping.services;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import com.pmrodrigues.android.allinshopping.models.Cliente;
import com.pmrodrigues.android.allinshopping.models.Pedido;
import com.pmrodrigues.android.allinshopping.models.Produto;
import com.pmrodrigues.android.allinshopping.repository.ClienteRepository;
import com.pmrodrigues.android.allinshopping.repository.ProductRepository;
import com.pmrodrigues.android.allinshopping.services.PedidoService;

@RunWith(RobolectricTestRunner.class)
public class TestPedidoService {

	private final PedidoService service = new PedidoService(
			Robolectric.application.getApplicationContext());

	private final ClienteRepository CLIENTE_REPOSITORY = new ClienteRepository(
			Robolectric.application.getApplicationContext());

	private final ProductRepository PRODUTO_REPOSITORY = new ProductRepository(
			Robolectric.application.getApplicationContext());

	@Before
	public void setup() {

		createClient();
		createProduct();
	}



	@Test
	public void createOrder() {

		Pedido pedido = new Pedido(CLIENTE_REPOSITORY.getById(1L));

		for (int i = 1; i <= 3; i++) {
			pedido.add(PRODUTO_REPOSITORY.getById(new Long(i)), "");
		}

		service.save(pedido);
		Assert.assertSame(1L, pedido.getId());

	}

	private void createClient() {

		Cliente cliente = new Cliente();
		CLIENTE_REPOSITORY.insert(cliente);
	}

	private void createProduct() {

		for (long i = 1L; i <= 10L; i++) {
			Produto produto = new Produto(i, String.format("produto %d", i),
					BigDecimal.TEN.multiply(new BigDecimal(i)), i);
			PRODUTO_REPOSITORY.insert(produto);
		}

	}

}
