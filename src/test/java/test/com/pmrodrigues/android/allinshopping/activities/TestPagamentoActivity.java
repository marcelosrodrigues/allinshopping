package test.com.pmrodrigues.android.allinshopping.activities;

import android.content.Intent;
import android.widget.RadioGroup;
import com.pmrodrigues.android.allinshopping.CartaoCreditoActivity;
import com.pmrodrigues.android.allinshopping.PagamentoActivity;
import com.pmrodrigues.android.allinshopping.R;
import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.models.FormaPagamento;
import com.pmrodrigues.android.allinshopping.repository.FormaPagamentoRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Robolectric.shadowOf;

/**
 * Created by Marceloo on 08/10/2014.
 */
@RunWith(RobolectricTestRunner.class)
public class TestPagamentoActivity {

    private PagamentoActivity activity;

    @Before
    public void setup() throws IntegrationException {
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
