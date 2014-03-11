package com.pmrodrigues.android.allinshopping.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.androidquery.AQuery;
import com.pmrodrigues.android.allinshopping.R;
import com.pmrodrigues.android.allinshopping.events.AtualizarQuantidadeItemPedidoEvent;
import com.pmrodrigues.android.allinshopping.events.RemoverItemPedidoOnClickListener;
import com.pmrodrigues.android.allinshopping.models.ItemPedido;
import com.pmrodrigues.android.allinshopping.utilities.ParseUtilities;

public class ShoppingCartAdapter extends ArrayAdapter<ItemPedido>
{

    private AQuery aq;
    private List<ItemPedido>  itens;

    public ShoppingCartAdapter(Context context, List<ItemPedido> itens)
    {
        super(context, android.R.layout.simple_list_item_1, itens);
        this.itens = itens;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        
        Activity activity = (Activity)getContext();
        if (view == null)
        {
        	view = activity.getLayoutInflater().inflate(R.layout.item_pedido, null);
        }
        aq = new AQuery(view);
        ItemPedido itempedido = (ItemPedido)itens.get(i);
        aq.id(R.id.remover).tag(itempedido);
        aq.id(R.id.remover).clicked(new RemoverItemPedidoOnClickListener());
        aq.id(R.id.id).text(itempedido.getId().toString());
        aq.id(R.id.nome).text(itempedido.getProduto().getTitulo());
        aq.id(R.id.quantidade).text(itempedido.getQuantidade().toString());
        aq.id(R.id.quantidade).tag(itempedido);
        aq.id(R.id.quantidade).getEditText().setOnFocusChangeListener(new AtualizarQuantidadeItemPedidoEvent());
        aq.id(R.id.preco).text(ParseUtilities.formatMoney(itempedido.getTotal()));
        return view;
    }
}
