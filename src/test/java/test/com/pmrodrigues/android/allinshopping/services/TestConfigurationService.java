package test.com.pmrodrigues.android.allinshopping.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.pmrodrigues.android.allinshopping.services.ConfigurationService;
import com.pmrodrigues.android.allinshopping.utilities.Constante;
import com.pmrodrigues.android.allinshopping.utilities.ParseUtilities;


@RunWith(RobolectricTestRunner.class)
public class TestConfigurationService {

	private ConfigurationService service = null;
	
	@Before
	public void setup() {
		
		service = new ConfigurationService(Robolectric.application.getApplicationContext());
		
	}
	
	@Test
	public void temQueAtualizar() {		
		assertTrue(service.precisaAtualizar());
	}
	
	@Test
	public void jaFoiAtualizado() {
		
		final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Robolectric.application.getApplicationContext());
		final Editor editor = preferences.edit();
		editor.putString(Constante.DATA_ATUALIZACAO, ParseUtilities.formatDate(DateTime.now().plusDays(1).toDate(),"yyyy-MM-dd HH:mm:ss"));
		editor.commit();
		assertFalse(service.precisaAtualizar());
	}
	
	@Test
	public void salvarNomeDaLoja() {
		service.setNomeLoja("TESTE");
		final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Robolectric.application.getApplicationContext());
		assertEquals("TESTE",preferences.getString(Constante.NOME_LOJA, null));
	}
	
	@Test
	public void recuperarNomeDaLoja() {
		service.setNomeLoja("TESTE");
		assertEquals("TESTE",service.getNomeLoja());
	}
	
	@Test
	public void marcarComoAtualizado() {
		assertTrue(service.precisaAtualizar());
		service.atualizar();
		assertFalse(service.precisaAtualizar());
	}

}
