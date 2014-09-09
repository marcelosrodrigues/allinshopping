package test.com.pmrodrigues.android.allinshopping.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.pmrodrigues.android.allinshopping.AutenticacaoAtualizacaoSistemaActivity;
import com.pmrodrigues.android.allinshopping.ConfigurationActivity;
import com.pmrodrigues.android.allinshopping.MainActivity;
import com.pmrodrigues.android.allinshopping.R;
import com.pmrodrigues.android.allinshopping.alerts.ActionDialog;
import com.pmrodrigues.android.allinshopping.utilities.Constante;
import com.pmrodrigues.android.allinshopping.utilities.ParseUtilities;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowIntent;
import test.com.pmrodrigues.android.allinshopping.responserules.HttpEntityResponseRule;

import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class TesAtualizacaoSistemaActivity {

	private final ResourceBundle integration = ResourceBundle
			.getBundle("integration");

	private final ResourceBundle response = ResourceBundle
			.getBundle("json_message");

	private AutenticacaoAtualizacaoSistemaActivity activity;
	
	private SharedPreferences preferences;
	
	@Before
	public void setup() {
		
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("estado"),response.getString("estado"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cep"),response.getString("cep"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("faixapreco"),response.getString("faixa"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("secao"),response.getString("secao"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("produto"),response.getString("produto"));		
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cliente"),response.getString("cliente"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(new HttpEntityResponseRule());
		
		this.activity = Robolectric.buildActivity(AutenticacaoAtualizacaoSistemaActivity.class).create().get();
		preferences = activity.getApplicationContext().getSharedPreferences(Constante.SHARED_PREFERENCES, Context.MODE_PRIVATE);		
		
		final Editor editor = preferences.edit();
		editor.putString(Constante.DATA_ATUALIZACAO, null);
		editor.commit();
	}


    @Test
    public void atualizarSistema() {

        activity.setEmail("marcelosrodrigues@globo.com");
        activity.setPassword("2pk0#3ty?");
        activity.onClick(activity.findViewById(R.id.logar));

        final AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .callOnClick();

    }
}
