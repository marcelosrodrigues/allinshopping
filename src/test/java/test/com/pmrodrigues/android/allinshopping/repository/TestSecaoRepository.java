package test.com.pmrodrigues.android.allinshopping.repository;

import android.content.Context;
import com.pmrodrigues.android.allinshopping.MainActivity;
import com.pmrodrigues.android.allinshopping.async.IntegrationProcess;
import com.pmrodrigues.android.allinshopping.models.Secao;
import com.pmrodrigues.android.allinshopping.repository.SectionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import test.com.pmrodrigues.android.allinshopping.responserules.HttpEntityResponseRule;

import java.util.List;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(RobolectricTestRunner.class)
public class TestSecaoRepository {

	private SectionRepository repository;
	
	private final ResourceBundle integration = ResourceBundle
			.getBundle("integration");
	
	private final ResourceBundle response = ResourceBundle.getBundle("json_message");
	
	private final Context context = Robolectric.buildActivity(MainActivity.class).create().get();
	
	@Before
	public void setup() throws Exception {
		
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("secao"),response.getString("secao"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("produto"),response.getString("produto"));		
		Robolectric.getFakeHttpLayer().addHttpResponseRule(new HttpEntityResponseRule());
		
		repository = new SectionRepository(context);
		final IntegrationProcess process = new IntegrationProcess("teste","teste",this.context);
			
		process.importarSecao();
		process.importarProdutos();
		
	}
	
	@Test
	public void listarSecaoComProduto() throws Exception {
		final List<Secao> secoes = repository.getSections();
		assertFalse(secoes.isEmpty());
		assertEquals(8 , secoes.size() );
	}

}
