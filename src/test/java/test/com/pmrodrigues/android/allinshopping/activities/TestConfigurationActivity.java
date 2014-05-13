package test.com.pmrodrigues.android.allinshopping.activities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.robolectric.Robolectric.shadowOf;

import java.util.ResourceBundle;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowAlertDialog;

import test.com.pmrodrigues.android.allinshopping.responserules.HttpEntityResponseRule;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.pmrodrigues.android.allinshopping.ConfigurationActivity;
import com.pmrodrigues.android.allinshopping.R;
import com.pmrodrigues.android.allinshopping.services.ConfigurationService;
import com.pmrodrigues.android.allinshopping.utilities.Constante;
import com.pmrodrigues.android.allinshopping.utilities.ParseUtilities;

@RunWith(RobolectricTestRunner.class)
public class TestConfigurationActivity {

	private final ResourceBundle integration = ResourceBundle
			.getBundle("integration");

	private final ResourceBundle response = ResourceBundle
			.getBundle("json_message");

	private ConfigurationActivity activity;
	
	private ConfigurationService service = null;
	
	@Before
	public void setup() {
		
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("estado"),response.getString("estado"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cep"),response.getString("cep"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("faixapreco"),response.getString("faixa"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("secao"),response.getString("secao"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("produto"),response.getString("produto"));		
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cliente"),response.getString("cliente"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(new HttpEntityResponseRule());

		final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Robolectric.application.getApplicationContext());
		final Editor editor = preferences.edit();
		editor.putString(Constante.DATA_ATUALIZACAO, null);
		editor.commit();
		
		
		this.activity = Robolectric.buildActivity(ConfigurationActivity.class).create().get();
		
		service = new ConfigurationService(Robolectric.application.getApplicationContext());
		
	}
	
	@Test
	public void deveSalvarEAtualizarOBancoDeDados() {
		
		activity.setNomeLoja("Marcelo");
		activity.onClick(activity.findViewById(R.id.salvar));
		
		final AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
		dialog.getButton(AlertDialog.BUTTON_POSITIVE)
			  .callOnClick();
				
		assertFalse(service.precisaAtualizar());
		
	}
	
	@Test
	public void deveApenasSalvar() {
		
		final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Robolectric.application.getApplicationContext());
		final Editor editor = preferences.edit();
		editor.putString(Constante.DATA_ATUALIZACAO, ParseUtilities.formatDate(DateTime.now().plusDays(1).toDate(),"yyyy-MM-dd HH:mm:ss"));
		editor.commit();
		activity.setNomeLoja("Marcelo");
		activity.onClick(activity.findViewById(R.id.salvar));
		
		assertEquals("Marcelo",preferences.getString(Constante.NOME_LOJA, null));
		
	}
	
	@Test
	public void deveApenasAtualizar() {
		
		activity.onClick(activity.findViewById(R.id.atualizar));
		
		final AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
		dialog.getButton(AlertDialog.BUTTON_POSITIVE)
			  .callOnClick();
				
		assertFalse(service.precisaAtualizar());
		
	}
	
	@Test
	public void naoPodeSalvarLojaSemNome() {
		
		activity.setNomeLoja("");
		activity.onClick(activity.findViewById(R.id.salvar));
		
		final AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
		final ShadowAlertDialog shadow = shadowOf(dialog);
		assertEquals("Erro na configuração do sistema",shadow.getTitle().toString());
		
	}

}
