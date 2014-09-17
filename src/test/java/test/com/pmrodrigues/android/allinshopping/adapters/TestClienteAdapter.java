package test.com.pmrodrigues.android.allinshopping.adapters;

import android.view.View;
import android.widget.TextView;
import com.pmrodrigues.android.allinshopping.ListClientesActivity;
import com.pmrodrigues.android.allinshopping.MainActivity;
import com.pmrodrigues.android.allinshopping.R;
import com.pmrodrigues.android.allinshopping.adapters.ClienteAdapter;
import com.pmrodrigues.android.allinshopping.async.IntegrationProcess;
import com.pmrodrigues.android.allinshopping.models.Cliente;
import com.pmrodrigues.android.allinshopping.repository.ClienteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import test.com.pmrodrigues.android.allinshopping.responserules.HttpEntityResponseRule;

import java.util.List;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class TestClienteAdapter {

    private final ResourceBundle integration = ResourceBundle
            .getBundle("integration");

    private final ResourceBundle response = ResourceBundle
            .getBundle("json_message");

    @Before
    public void setup() throws Exception {

        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("estado"),response.getString("estado"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cep"),response.getString("cep"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cliente"),response.getString("cliente"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(new HttpEntityResponseRule());

        final IntegrationProcess integration = new IntegrationProcess("teste","teste",Robolectric.buildActivity(MainActivity.class).create().get());
        integration.importarEstado();
        integration.importarCliente();
        integration.importarCEP();

    }

    @Test
    public void montarListaDeClientes() {

        final ListClientesActivity activity = Robolectric.buildActivity(ListClientesActivity.class).create().get();
        final ClienteRepository repository = new ClienteRepository(activity);

        final List<Cliente> clientes = repository.list();

        final ClienteAdapter adapter = new ClienteAdapter(activity,clientes,null);
        final View view = adapter.getView(0,null,null);

        assertNotNull(view.findViewById(R.id.cliente));
        assertEquals(((TextView)view.findViewById(R.id.cliente)).getText(),clientes.get(0).getNomeCompleto());

    }

}
