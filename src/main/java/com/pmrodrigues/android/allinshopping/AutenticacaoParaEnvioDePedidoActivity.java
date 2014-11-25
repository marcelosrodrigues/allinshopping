package com.pmrodrigues.android.allinshopping;

import android.content.Intent;

import com.pmrodrigues.android.allinshopping.alerts.ActionDialog;
import com.pmrodrigues.android.allinshopping.async.SendPedidoIntegrationAsyncProcess;
import com.pmrodrigues.android.allinshopping.models.Pedido;
import com.pmrodrigues.android.allinshopping.services.PedidoService;
import com.pmrodrigues.android.allinshopping.utilities.Constante;
import com.pmrodrigues.android.allinshopping.utilities.PriceUtilities;


public class AutenticacaoParaEnvioDePedidoActivity extends AbstractLogarActivity {

	@Override
	protected void doAction() {

		Pedido pedido = PriceUtilities.getPedido();
		(new PedidoService(this)).save(pedido);

		SendPedidoIntegrationAsyncProcess process = new SendPedidoIntegrationAsyncProcess(this);
		process.execute();

		
	}

	@Override
	protected void doBack() {
		Intent pagamento = new Intent(this, PagamentoActivity.class);
		startActivity(pagamento);
        this.finish();
	}

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }


}
