package test.com.pmrodrigues.android.allinshopping.adapters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import test.com.pmrodrigues.android.allinshopping.responserules.HttpEntityResponseRule;
import android.view.View;
import android.widget.TextView;

import com.pmrodrigues.android.allinshopping.HomeActivity;
import com.pmrodrigues.android.allinshopping.R;
import com.pmrodrigues.android.allinshopping.adapters.ProdutoAdapter;
import com.pmrodrigues.android.allinshopping.async.IntegrationProcess;
import com.pmrodrigues.android.allinshopping.models.Produto;
import com.pmrodrigues.android.allinshopping.repository.ProductRepository;

@RunWith(RobolectricTestRunner.class)
public class TestProdutoAdapter {
	
	private final ResourceBundle integration = ResourceBundle
			.getBundle("integration");

	private final ResourceBundle response = ResourceBundle
			.getBundle("json_message");
	
	@Before
	public void setup() throws Exception {
		
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("estado"),response.getString("estado"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cep"),response.getString("cep"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("faixapreco"),response.getString("faixa"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("secao"),response.getString("secao"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("produto"),response.getString("produto"));		
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cliente"),response.getString("cliente"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(new HttpEntityResponseRule());

		final IntegrationProcess integration = new IntegrationProcess("teste","teste",Robolectric.application.getApplicationContext());
		integration.importarEstado();
		integration.importarCEP();
		integration.importarFaixaPreco();
		integration.importarSecao();
		integration.importarProdutos();
		
	}
	
	@Test
	public void montarListaDeProdutos() {
		
		final HomeActivity activity = Robolectric.buildActivity(HomeActivity.class).create().get();
		
		final ProductRepository service = new ProductRepository(Robolectric.application.getApplicationContext());
		
		final Collection<Produto> produtos = service.list();
		final ProdutoAdapter adapter = new ProdutoAdapter(activity, new ArrayList<Produto>(produtos));
		final Produto produto = (new ArrayList<Produto>(produtos)).get(0);
		final View view  = adapter.getView(0, null, null);
		
		assertNotNull(view.findViewById(R.id.imagem));
		assertEquals(produto.getTitulo() , ((TextView)view.findViewById(R.id.titulo)).getText().toString());
		
	}

}
