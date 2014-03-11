package com.pmrodrigues.android.allinshopping;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.pmrodrigues.android.allinshopping.adapters.ClienteAdapter;
import com.pmrodrigues.android.allinshopping.models.Cliente;
import com.pmrodrigues.android.allinshopping.models.Pedido;
import com.pmrodrigues.android.allinshopping.repository.ClienteRepository;
import com.pmrodrigues.android.allinshopping.utilities.Constante;
public class ListClientesActivity extends AbstractActivity
{

    private AQuery aq;

    public ListClientesActivity()
    {
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_clientes_main);
        aq = new AQuery(this);
        if (getIntent().getExtras() != null)
        {
            Pedido pedido = (Pedido)getIntent().getExtras().get(Constante.PEDIDO);
            setPedido(pedido);
        }
        ClienteRepository clienterepository = new ClienteRepository(this);
        List<Cliente> clientes = clienterepository.list();
        if (clientes != null && !clientes.isEmpty())
        {
            ListView lst = aq.id(R.id.clientes).getListView();
            ClienteAdapter clienteadapter = new ClienteAdapter(this, clienterepository.list(), getPedido());
            lst.setAdapter(clienteadapter);
        } else
        {
            Intent intent = new Intent(this, ClienteActivity.class);
            intent.putExtra(Constante.PEDIDO, getPedido());
            startActivity(intent);
        }
    }
}
