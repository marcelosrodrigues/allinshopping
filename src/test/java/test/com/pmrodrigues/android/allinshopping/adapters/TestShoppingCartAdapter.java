package test.com.pmrodrigues.android.allinshopping.adapters;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.pmrodrigues.android.allinshopping.HomeActivity;
import com.pmrodrigues.android.allinshopping.R;
import com.pmrodrigues.android.allinshopping.ShoppingCartActivity;
import com.pmrodrigues.android.allinshopping.adapters.ShoppingCartAdapter;
import com.pmrodrigues.android.allinshopping.async.IntegrationProcess;
import com.pmrodrigues.android.allinshopping.models.Atributo;
import com.pmrodrigues.android.allinshopping.models.ItemPedido;
import com.pmrodrigues.android.allinshopping.models.Produto;
import com.pmrodrigues.android.allinshopping.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import test.com.pmrodrigues.android.allinshopping.responserules.HttpEntityResponseRule;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Marceloo on 19/09/2014.
 */
@RunWith(RobolectricTestRunner.class)
public class TestShoppingCartAdapter {

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

        final IntegrationProcess integration = new IntegrationProcess("teste","teste",Robolectric.buildActivity(ShoppingCartActivity.class).create().get());
        integration.importarEstado();
        integration.importarCEP();
        integration.importarFaixaPreco();
        integration.importarSecao();
        integration.importarProdutos();

    }

    @Test
    public void montarCarrinhoCompras() {

        final ShoppingCartActivity activity = Robolectric.buildActivity(ShoppingCartActivity.class).create().get();

        final ProductRepository service = new ProductRepository(activity);
        final List<ItemPedido> itens = new ArrayList<ItemPedido>();
        Produto produto = service.getById(256L);
        ItemPedido item = new ItemPedido(produto,null,null);
        itens.add(item);

        final ShoppingCartAdapter adapter = new ShoppingCartAdapter(activity,itens);
        final View view = adapter.getView(0,null,null);

        TextView nome = (TextView) view.findViewById(R.id.nome);

        assertEquals(produto.getTitulo(),nome.getText());
        assertEquals(item.getQuantidade(),new Long(((EditText)view.findViewById(R.id.quantidade)).getText().toString()));

    }

    @Test
    public void montarCarrinhoDeCompraComProdutoComAtributo() {

        final HomeActivity activity = Robolectric.buildActivity(HomeActivity.class).create().get();

        final ProductRepository service = new ProductRepository(activity);

        final List<ItemPedido> itens = new ArrayList<ItemPedido>();
        Produto produto = service.getById(256L);
        Atributo atributo = new ArrayList<Atributo>(produto.getAtributos()).get(0);
        ItemPedido item = new ItemPedido(produto,atributo,null);
        itens.add(item);

        final ShoppingCartAdapter adapter = new ShoppingCartAdapter(activity,itens);
        final View view = adapter.getView(0,null,null);

        TextView nome = (TextView) view.findViewById(R.id.nome);

        assertEquals(produto.getTitulo(),nome.getText());
        assertEquals(item.getQuantidade(),new Long(((EditText)view.findViewById(R.id.quantidade)).getText().toString()));
        assertEquals(item.getAtributo().getDescricao(),((TextView)view.findViewById(R.id.atributo)).getText());

    }

}
