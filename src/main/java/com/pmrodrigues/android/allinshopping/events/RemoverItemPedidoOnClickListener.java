package com.pmrodrigues.android.allinshopping.events;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.pmrodrigues.android.allinshopping.R;
import com.pmrodrigues.android.allinshopping.adapters.ShoppingCartAdapter;
import com.pmrodrigues.android.allinshopping.models.ItemPedido;
import com.pmrodrigues.android.allinshopping.models.Pedido;
import com.pmrodrigues.android.allinshopping.utilities.ParseUtilities;
import com.pmrodrigues.android.allinshopping.utilities.PriceUtilities;

public class RemoverItemPedidoOnClickListener
 implements OnClickListener
{

    private AQuery aq;

    public RemoverItemPedidoOnClickListener()
    {
    }

    @Override
	public void onClick(View view)
    {
        Activity activity = (Activity)view.getContext();
        aq = new AQuery(activity);        
        Pedido pedido = PriceUtilities.getPedido();
        ItemPedido itempedido = (ItemPedido)view.getTag();
        pedido.remover(itempedido);
        ListView listview = aq.id(R.id.lista_itens_pedidos).getListView();
        Context context = view.getContext();
        List<ItemPedido> itens = new ArrayList<ItemPedido>(pedido.getItens());
        ShoppingCartAdapter shoppingcartadapter = new ShoppingCartAdapter(context, itens);
        listview.setAdapter(shoppingcartadapter);
        aq.id(R.id.total_pedido).text(ParseUtilities.formatMoney(pedido.getTotal()));
                
    }
}
