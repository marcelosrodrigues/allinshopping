package com.pmrodrigues.android.allinshopping;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.androidquery.AQuery;
import com.pmrodrigues.android.allinshopping.alerts.AbstractDialog;
import com.pmrodrigues.android.allinshopping.alerts.ErrorAlert;
import com.pmrodrigues.android.allinshopping.models.DadosPagamento;
import com.pmrodrigues.android.allinshopping.models.Pedido;
import com.pmrodrigues.android.allinshopping.services.PedidoService;
import com.pmrodrigues.android.allinshopping.utilities.Constante;

public class CartaoCreditoActivity extends AbstractActivity
		implements
			OnClickListener
{

    private AQuery aq;

    public CartaoCreditoActivity()
    {
    }

    @Override
	public void onClick(View view)
    {
        Pedido pedido = (Pedido)getIntent().getExtras().get(Constante.PEDIDO);
        Intent intent = null;
        if (view.getId() == R.id.salvar){
        	
            DadosPagamento dadospagamento = new DadosPagamento();
            dadospagamento.setPedido(pedido);
            dadospagamento.setNome(aq.id(R.id.nome).getText().toString());
            dadospagamento.setNumero(aq.id(R.id.cartao).getText().toString());
            dadospagamento.setDataValidade(aq.id(R.id.validade).getText().toString());
            dadospagamento.setCVV(aq.id(R.id.cvv).getText().toString());
            dadospagamento.setCPF(aq.id(R.id.cpf).getText().toString());
            if( aq.id(R.id.parcelas).getSelectedItemPosition() > 0 ) {
            	dadospagamento.setQtdParcelas(aq.id(R.id.parcelas).getSelectedItemPosition());
            }
            
            if (dadospagamento.isValid())
            {
                (new PedidoService(this)).save(dadospagamento);
                intent = new Intent(this, AutenticacaoParaEnvioDePedidoActivity.class);
                intent.putExtra(Constante.PEDIDO, pedido);
            } else
            {
				AbstractDialog abstractdialog = new ErrorAlert(this)
						.setTitle("Dados de Cartão de crédito");
                List<String> list = dadospagamento.errors();
                abstractdialog.setMessages(list).setCancelable(true).show();
            }
        } else
        {
            intent = new Intent(this, PagamentoActivity.class);
            intent.putExtra(Constante.PEDIDO, pedido);
        }
        startActivity(intent);
    }

    @Override
	protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_cartao_credito);
        
        aq = new AQuery(this);
        aq.id(R.id.salvar).clicked(this);
        aq.id(R.id.cancelar).clicked(this);
        
        Spinner spinner = aq.id(R.id.parcelas).getSpinner();
        List<String> parcelas = new ArrayList<String>();
        
        parcelas.add("Seleciona a quantidade de parcelas");
		parcelas.add("À vista");
        parcelas.add("2 vezes");
        parcelas.add("3 vezes");
        parcelas.add("4 vezes");
        parcelas.add("5 vezes");
        parcelas.add("6 vezes");
        parcelas.add("7 vezes");
        parcelas.add("8 vezes");
        parcelas.add("9 vezes");
        parcelas.add("10 vezes");
        parcelas.add("11 vezes");
        parcelas.add("12 vezes");
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, parcelas);
        spinner.setAdapter(adapter);
    }
}
