package test.com.pmrodrigues.android.allinshopping.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.widget.TextView;
import com.pmrodrigues.android.allinshopping.ConfigurationActivity;
import com.pmrodrigues.android.allinshopping.HomeActivity;
import com.pmrodrigues.android.allinshopping.R;
import com.pmrodrigues.android.allinshopping.ShoppingCartActivity;
import com.pmrodrigues.android.allinshopping.async.IntegrationProcess;
import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.models.Pedido;
import com.pmrodrigues.android.allinshopping.models.Produto;
import com.pmrodrigues.android.allinshopping.repository.PedidoRepository;
import com.pmrodrigues.android.allinshopping.utilities.ParseUtilities;
import com.pmrodrigues.android.allinshopping.utilities.PriceUtilities;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowIntent;
import test.com.pmrodrigues.android.allinshopping.responserules.HttpEntityResponseRule;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Robolectric.shadowOf;

/**
 * Created by Marceloo on 06/10/2014.
 */
@RunWith(RobolectricTestRunner.class)
public class TestShoppingCartActivity {

    private final ResourceBundle integration = ResourceBundle
            .getBundle("integration");

    private final ResourceBundle response = ResourceBundle
            .getBundle("json_message");

    @Before
    public void setup() throws IntegrationException {
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("estado"),response.getString("estado"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cep"),response.getString("cep"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("faixapreco"),response.getString("faixa"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("secao"),response.getString("secao"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("produto"),response.getString("produto"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cliente"),response.getString("cliente"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(new HttpEntityResponseRule());

        final IntegrationProcess integration = new IntegrationProcess("teste","teste",Robolectric.buildActivity(HomeActivity.class).create().get());
        integration.importarEstado();
        integration.importarCEP();
        integration.importarFaixaPreco();
        integration.importarSecao();
        integration.importarProdutos();
    }

    @Test
    public void shouldShowTotalPrice() throws Exception {

        Pedido pedido = PriceUtilities.getPedido();
        Produto produto = new Produto();
        produto.setDescricao("TESTE");
        Field preco = Produto.class.getDeclaredField("preco");
        preco.setAccessible(true);
        preco.set(produto,new BigDecimal("100"));
        pedido.adicionar(produto);

        ShoppingCartActivity activity = Robolectric.buildActivity(ShoppingCartActivity.class).create().get();
        TextView total = (TextView) activity.findViewById(R.id.total_pedido);
        assertEquals(ParseUtilities.formatMoney(produto.getPrecoVenda()), total.getText());

    }

    @Test
    public void shouldReturnToProdutoList() {

        ShoppingCartActivity activity = Robolectric.buildActivity(ShoppingCartActivity.class).create().get();
        activity.onClick(activity.findViewById(R.id.continuar_comprando));

        final ShadowActivity actual = shadowOf(activity);
        final Intent next = actual.getNextStartedActivity();
        final ShadowIntent shadowIntent = shadowOf(next);
        assertEquals(HomeActivity.class.getName(), shadowIntent.getComponent().getClassName());

    }

    @Test
    public void shouldSendToClient() {

        ShoppingCartActivity activity = Robolectric.buildActivity(ShoppingCartActivity.class).create().get();
        activity.onClick(activity.findViewById(R.id.finalizar_pedido));

        final AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
        final ShadowAlertDialog shadow = shadowOf(dialog);
        assertEquals("Cadastro de cliente" , shadow.getMessage());
    }
}
