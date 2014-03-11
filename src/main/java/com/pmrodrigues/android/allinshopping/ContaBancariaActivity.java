package com.pmrodrigues.android.allinshopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.androidquery.AQuery;
import com.pmrodrigues.android.allinshopping.alerts.ErrorAlert;
import com.pmrodrigues.android.allinshopping.models.DadosPagamento;
import com.pmrodrigues.android.allinshopping.models.Pedido;
import com.pmrodrigues.android.allinshopping.services.PedidoService;
import com.pmrodrigues.android.allinshopping.utilities.Constante;

public class ContaBancariaActivity extends AbstractActivity
		implements
			OnClickListener
{

    private AQuery aq;

    public ContaBancariaActivity()
    {
    }

    @Override
	public void onClick(View view)
    {
        Pedido pedido = (Pedido)getIntent().getExtras().get(Constante.PEDIDO);
        Intent intent = null;
        if (view.getId() == R.id.salvar)
        {
            String banco = aq.id(R.id.banco).getText().toString();
            String conta =aq.id(R.id.conta).getText().toString();
            String agencia =aq.id(R.id.agencia).getText().toString();
            DadosPagamento dadospagamento = new DadosPagamento();
            dadospagamento.setPedido(pedido);
            dadospagamento.setBanco(banco);
            dadospagamento.setAgencia(agencia);
            dadospagamento.setConta(conta);
            if (dadospagamento.isValid())
            {
                (new PedidoService(this)).save(dadospagamento);
                intent = new Intent(this, LogarActivity.class);
                intent.putExtra(Constante.PEDIDO, pedido);
            } else
            {
				(new ErrorAlert(this)).setTitle("Dados de Conta bancária")
                					 .setMessages(dadospagamento.errors())
                					 .setCancelable(true)
                					 .show();
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
        setContentView(R.layout.activity_conta_bancaria);
        aq = new AQuery(this);
        aq.id(R.id.salvar).clicked(this);
        aq.id(R.id.cancelar).clicked(this);
    }
}
