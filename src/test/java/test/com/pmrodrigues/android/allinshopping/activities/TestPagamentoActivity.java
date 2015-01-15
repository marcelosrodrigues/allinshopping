package test.com.pmrodrigues.android.allinshopping.activities;

import android.content.Intent;
import android.widget.RadioGroup;
import com.pmrodrigues.android.allinshopping.CartaoCreditoActivity;
import com.pmrodrigues.android.allinshopping.PagamentoActivity;
import com.pmrodrigues.android.allinshopping.R;
import com.pmrodrigues.android.allinshopping.ShoppingCartActivity;
import com.pmrodrigues.android.allinshopping.async.IntegrationProcess;
import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.models.FormaPagamento;
import com.pmrodrigues.android.allinshopping.repository.FormaPagamentoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;
import test.com.pmrodrigues.android.allinshopping.responserules.HttpEntityResponseRule;

import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Robolectric.shadowOf;

/**
 * Created by Marceloo on 08/10/2014.
 */
@RunWith(RobolectricTestRunner.class)
public class TestPagamentoActivity {

    private final ResourceBundle integration = ResourceBundle
            .getBundle("integration");

    private final ResourceBundle response = ResourceBundle
            .getBundle("json_message");

    private PagamentoActivity activity;

    @Before
    public void setup() throws IntegrationException {

        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("formapagamento"), response.getString("formapagamento"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(new HttpEntityResponseRule());

        IntegrationProcess process = new IntegrationProcess("teste", "teste", Robolectric.buildActivity(ShoppingCartActivity.class).create().get());
        process.importarFormasPagamento();

        activity = Robolectric.buildActivity(PagamentoActivity.class).create().get();
    }

    @Test
    public void montarTela() {

        FormaPagamentoRepository repository = new FormaPagamentoRepository(activity);
        RadioGroup formas = (RadioGroup) activity.findViewById(R.id.formapagamento);
        assertEquals(repository.count(), formas.getChildCount());

    }

    @Test
    public void deveDirecionarParaPagamentoComCartao() {

        FormaPagamentoRepository repository = new FormaPagamentoRepository(activity);
        FormaPagamento formaPagamento = repository.getById(2L);
        activity.setFormaPagamento(formaPagamento);
        activity.onClick(activity.findViewById(R.id.salvar));

        final ShadowActivity actual = shadowOf(activity);
        final Intent next = actual.getNextStartedActivity();
        final ShadowIntent shadowIntent = shadowOf(next);
        assertEquals(CartaoCreditoActivity.class.getName(), shadowIntent.getComponent().getClassName());

    }

}
