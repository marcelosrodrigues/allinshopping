package com.pmrodrigues.android.allinshopping.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.androidquery.AQuery;
import com.pmrodrigues.android.allinshopping.ClienteActivity;
import com.pmrodrigues.android.allinshopping.R;
import com.pmrodrigues.android.allinshopping.models.Cliente;
import com.pmrodrigues.android.allinshopping.models.Pedido;
import com.pmrodrigues.android.allinshopping.utilities.Constante;

public class ClienteAdapter extends ArrayAdapter<Cliente>
		implements
			OnClickListener
{

    private AQuery aq;
    private final List<Cliente> clientes;
    private final Pedido pedido;

    public ClienteAdapter(Context context, List<Cliente> clientes, Pedido pedido)
    {
        super(context, android.R.layout.simple_list_item_1, clientes);
        this.clientes = clientes;
        this.pedido = pedido;
    }

    @Override
	public View getView(int i, View view, ViewGroup viewgroup)
    {
        Activity activity = (Activity)getContext();
        if (view == null)
        {
            view = activity.getLayoutInflater().inflate(R.layout.cliente, null);
        }
        
        aq = new AQuery(view);
        Cliente cliente = clientes.get(i);
        aq.id(R.id.cliente).text(cliente.getNome());
        aq.id(R.id.cliente).tag(cliente);
        aq.id(R.id.cliente).clicked(this);
        return view;
    }

    @Override
	public void onClick(View view)
    {
        Cliente cliente = (Cliente)view.getTag();
        Context context = getContext();
        Intent intent = new Intent(context, ClienteActivity.class);
        pedido.setCliente(cliente);
        intent.putExtra(Constante.PEDIDO, pedido);
        intent.putExtra(Constante.CLIENTE, cliente);
        getContext().startActivity(intent);
    }
}
