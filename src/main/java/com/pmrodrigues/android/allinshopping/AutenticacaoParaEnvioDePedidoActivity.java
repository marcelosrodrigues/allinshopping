package com.pmrodrigues.android.allinshopping;

import android.content.Intent;

import com.pmrodrigues.android.allinshopping.alerts.ActionDialog;
import com.pmrodrigues.android.allinshopping.async.SendPedidoIntegrationAsyncProcess;
import com.pmrodrigues.android.allinshopping.models.Pedido;
import com.pmrodrigues.android.allinshopping.services.PedidoService;
import com.pmrodrigues.android.allinshopping.utilities.Constante;


public class AutenticacaoParaEnvioDePedidoActivity extends AbstractLogarActivity {

	@Override
	protected void doAction() {

		Pedido pedido = (Pedido) getIntent().getExtras().get(
				Constante.PEDIDO);
		(new PedidoService(this)).save(pedido);

		SendPedidoIntegrationAsyncProcess process = new SendPedidoIntegrationAsyncProcess(this);
		process.execute();

		(new ActionDialog(this))
				.setTitle("Ella S/A")
				.setMessage(
						"Seu pedido foi concluído com sucesso, obrigado pela preferência")
				.show();
		Intent home = new Intent(this, HomeActivity.class);
		startActivity(home);
		
	}

	@Override
	protected void doBack() {
		Intent pagamento = new Intent(this, PagamentoActivity.class);
		startActivity(pagamento);		
	}

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }


}
