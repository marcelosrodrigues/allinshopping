package com.pmrodrigues.android.allinshopping.adapters;

import java.util.List;

import org.apache.commons.validator.GenericValidator;

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
import com.pmrodrigues.android.allinshopping.models.Pedido;
import com.pmrodrigues.android.allinshopping.models.Produto;
import com.pmrodrigues.android.allinshopping.services.CEPService;
import com.pmrodrigues.android.allinshopping.utilities.DrawableUtilities;
import com.pmrodrigues.android.allinshopping.utilities.ParseUtilities;
import com.pmrodrigues.android.allinshopping.utilities.PriceUtilities;

public class ProdutoAdapter extends ArrayAdapter<Produto>
		implements
			OnClickListener
{

    private AQuery aq;
    private final List<Produto> products;
    private final CEPService service;
    
    public ProdutoAdapter(Context context, List<Produto> produtos)
    {
        super(context, android.R.layout.simple_list_item_1, produtos);
        this.products = produtos;
        this.service = new CEPService(context);
    }

    @Override
	public View getView(int i, View view, ViewGroup viewgroup)
    {
        
        Activity activity = (Activity)getContext();
        
        if (view == null)
        {
        	view = activity.getLayoutInflater().inflate(R.layout.item_resumo, null);
        }
        aq = new AQuery(view);
        Produto produto = products.get(i);
//        if (PriceUtilities.getFaixaEntrega() != null)
//        {
//            Long peso = produto.getPeso();
//            FaixaPreco faixapreco = service.getFaixaPreco(PriceUtilities.getFaixaEntrega(), peso);
//            produto.setFaixaPreco(faixapreco);
//        }
        aq.id(R.id.imagem).image(DrawableUtilities.getImage(activity, produto.getImage()));
        aq.id(R.id.imagem).tag(produto.getImage());
        aq.id(R.id.imagem).clicked(new AbrirImagemOnClickEvent());
        aq.id(R.id.adicionar).tag(produto);
        aq.id(R.id.titulo).text(produto.getTitulo());
        aq.id(R.id.preco).text(ParseUtilities.formatMoney(produto.getPrecoVenda()));
        aq.id(R.id.descricao).text(produto.getDescricao());
        
        if( produto.getSecao().isVestuario() ) {
        	
        	showRoupas(View.VISIBLE);
        	showSapatos(View.INVISIBLE);      
        	
        } else if( produto.getSecao().isCalcado() ) {
        	
        	showSapatos(View.VISIBLE);
        	showRoupas(View.INVISIBLE);
        	
        } else if( !produto.getSecao().isVestuario() && !produto.getSecao().isCalcado() ){
        	aq.id(R.id.tamanho).visibility(View.INVISIBLE);
        }
        
        
        aq.id(R.id.adicionar).getButton().setOnClickListener(this);
        RadioGroup radiogroup = (RadioGroup)view.findViewById(R.id.tamanho);
        radiogroup.setOnCheckedChangeListener(new ChangeRadioButtonEventListener());
        return view;
    }

	private void showSapatos(int visibility) {
		aq.id(R.id.T_34).visibility(visibility);
		aq.id(R.id.T_35).visibility(visibility);
		aq.id(R.id.T_36).visibility(visibility);
		aq.id(R.id.T_37).visibility(visibility);
		aq.id(R.id.T_38).visibility(visibility);
		aq.id(R.id.T_39).visibility(visibility);
		aq.id(R.id.T_40).visibility(visibility);
	}

	private void showRoupas(int visibility) {
		aq.id(R.id.P).visibility(visibility);
		aq.id(R.id.M).visibility(visibility);
		aq.id(R.id.G).visibility(visibility);
		aq.id(R.id.GG).visibility(visibility);
	}

    @Override
	public void onClick(View view)
    {
        Produto produto = (Produto)view.getTag();
        Context context = getContext();
        Activity activity = (Activity)getContext();
        Intent intent = new Intent(context, ShoppingCartActivity.class);
        RadioGroup radiogroup = (RadioGroup)activity.findViewById(R.id.tamanho);
        Pedido pedido = PriceUtilities.getPedido();
        int i = radiogroup.getCheckedRadioButtonId();
        RadioButton radiobutton = (RadioButton)radiogroup.findViewById(i);
        
        String s = radiobutton.getHint().toString();
        
		if ((produto.getSecao().isCalcado() || produto.getSecao().isVestuario())
				&& GenericValidator.isBlankOrNull(s)) {
        		
        		( new ErrorAlert(context) )
        			.setTitle("Pedidos")
					.setMessage("Tamanho é obrigatório")
        			.show();
        		
			return;
        }
        
        pedido.add(produto, s);
        getContext().startActivity(intent);
        
        
    }
}
