package com.pmrodrigues.android.allinshopping.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.androidquery.AQuery;
import com.pmrodrigues.android.allinshopping.R;
import com.pmrodrigues.android.allinshopping.ShoppingCartActivity;
import com.pmrodrigues.android.allinshopping.alerts.ErrorAlert;
import com.pmrodrigues.android.allinshopping.events.AbrirImagemOnClickEvent;
import com.pmrodrigues.android.allinshopping.events.ChangeRadioButtonEventListener;
import com.pmrodrigues.android.allinshopping.models.Atributo;
import com.pmrodrigues.android.allinshopping.models.Pedido;
import com.pmrodrigues.android.allinshopping.models.Produto;
import com.pmrodrigues.android.allinshopping.utilities.DrawableUtilities;
import com.pmrodrigues.android.allinshopping.utilities.ParseUtilities;
import com.pmrodrigues.android.allinshopping.utilities.PriceUtilities;

import java.util.List;

public class ProdutoAdapter extends ArrayAdapter<Produto>
        implements
        OnClickListener {

    private AQuery aq;
    private final List<Produto> products;

    public ProdutoAdapter(final Context context, final List<Produto> produtos) {
        super(context, android.R.layout.simple_list_item_1, produtos);
        this.products = produtos;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewgroup) {

        Activity activity = (Activity) getContext();

        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.item_resumo,
                    null);
        }
        if (aq == null) {
            aq = new AQuery(view);
        }
        Produto produto = products.get(i);

        aq.id(R.id.imagem).image(DrawableUtilities.getImage(produto.getDefaultImage()));
        aq.id(R.id.imagem).tag(produto.getDefaultImage());
        aq.id(R.id.imagem).clicked(new AbrirImagemOnClickEvent());
        aq.id(R.id.adicionar).tag(produto);
        aq.id(R.id.titulo).text(produto.getTitulo());
        aq.id(R.id.preco).text(
                ParseUtilities.formatMoney(produto.getPrecoVenda()));
        aq.id(R.id.descricao).text(produto.getDescricao());


        if (produto.temAtributos()) {

            RadioGroup radiogroup = (RadioGroup) view.findViewById(R.id.tamanho);
            radiogroup
                    .setOnCheckedChangeListener(new ChangeRadioButtonEventListener());

            for (Atributo atributo : produto.getAtributos()) {
                RadioButton radiobutton = new RadioButton(activity);
                radiobutton.setTag(atributo);
                radiobutton.setHint(atributo.getDescricao());
                radiogroup.addView(radiobutton);
            }

            radiogroup.setVisibility(View.VISIBLE);
        }


        aq.id(R.id.adicionar).getButton().setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        final Produto produto = (Produto) view.getTag();
        final Context context = getContext();
        final Activity activity = (Activity) getContext();
        final Intent intent = new Intent(context, ShoppingCartActivity.class);
        final Pedido pedido = PriceUtilities.getPedido();

        final Atributo atributo = getAtributo((View) view.getParent());

        if (produto.temAtributos() && atributo == null) {

            (new ErrorAlert(context)).setTitle("Pedidos")
                    .setMessage("Tamanho é obrigatório").show();

            return;
        }

        pedido.adicionar(produto, atributo);
        getContext().startActivity(intent);

    }

    private Atributo getAtributo(View view) {

        final RadioGroup radiogroup = (RadioGroup) view.findViewById(R.id.tamanho);

        if (radiogroup != null) {
            final int i = radiogroup.getCheckedRadioButtonId();
            final RadioButton radiobutton = (RadioButton) radiogroup.findViewById(i);
            if (radiobutton != null) {
                return (Atributo) radiobutton.getTag();
            }
        }
        return null;

    }
}