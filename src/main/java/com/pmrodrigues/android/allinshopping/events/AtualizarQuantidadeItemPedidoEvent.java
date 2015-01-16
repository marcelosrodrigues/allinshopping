package com.pmrodrigues.android.allinshopping.events;

import android.app.Activity;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import com.androidquery.AQuery;
import com.pmrodrigues.android.allinshopping.R;
import com.pmrodrigues.android.allinshopping.models.ItemPedido;
import com.pmrodrigues.android.allinshopping.models.Pedido;
import com.pmrodrigues.android.allinshopping.utilities.Constante;
import com.pmrodrigues.android.allinshopping.utilities.ParseUtilities;
import com.pmrodrigues.android.allinshopping.utilities.PriceUtilities;
import org.apache.commons.lang3.math.NumberUtils;

public class AtualizarQuantidadeItemPedidoEvent
        implements
        OnFocusChangeListener {

    private AQuery aq;

    @Override
    public void onFocusChange(View view, boolean flag) {
        if (!flag) {
            Activity activity = (Activity) view.getContext();
            if (aq == null) {
                aq = new AQuery(activity);
            }
            Pedido pedido = PriceUtilities.getPedido();
            ItemPedido itempedido = (ItemPedido) view.getTag();
            String quantidade = ((EditText) view).getText().toString();
            if (!"".equalsIgnoreCase(quantidade)) {
                Long qtd = NumberUtils.toLong(quantidade);
                if (qtd.longValue() > 0L) {
                    itempedido.setQuantidade(qtd);
                } else {
                    pedido.remover(itempedido);
                }
            }
            aq.id(R.id.total_pedido).text(ParseUtilities.formatMoney(pedido.getTotal()));
            activity.getIntent().putExtra(Constante.PEDIDO, pedido);
        }
    }
}
