package com.pmrodrigues.android.allinshopping;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.GenericValidator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.androidquery.AQuery;
import com.pmrodrigues.android.allinshopping.alerts.ActionDialog;
import com.pmrodrigues.android.allinshopping.alerts.ErrorAlert;
import com.pmrodrigues.android.allinshopping.async.SendPedidoIntegrationAsyncProcess;
import com.pmrodrigues.android.allinshopping.models.Pedido;
import com.pmrodrigues.android.allinshopping.services.PedidoService;
import com.pmrodrigues.android.allinshopping.utilities.Constante;

public class LogarActivity extends AbstractActivity
 implements OnClickListener
{

    private AQuery aq;

    public LogarActivity()
    {
    }

    @Override
	public void onClick(View view)
    {
        if (view.getId() == R.id.logar)
        {
        	
        	List<String> messages = new ArrayList<String>();
        	
        	if( GenericValidator.isBlankOrNull(aq.id(R.id.email).getText().toString())){
				messages.add("E-mail é obrigatória");
        	}
        	
        	if( !GenericValidator.isEmail(aq.id(R.id.email).getText().toString()) ) {
				messages.add("E-mail é inválido");
        	}
        	
        	        	
        	if( GenericValidator.isBlankOrNull(aq.id(R.id.password).getText().toString())){
				messages.add("Senha é obrigatória");
        	}
        	if( !messages.isEmpty() ) {
	        	new ErrorAlert(this)
					.setTitle("Pedido")
					.setMessages(messages)
					.show();
        	} else {
        	
	        	Pedido pedido = (Pedido)getIntent().getExtras().get(Constante.PEDIDO);
	        	pedido.setEmail(aq.id(R.id.email).getText().toString());
	        	pedido.setSenha(aq.id(R.id.password).getText().toString());
	        	(new PedidoService(this)).save(pedido);
	        	
	        	
	        	SendPedidoIntegrationAsyncProcess process = new SendPedidoIntegrationAsyncProcess(this);
	        	process.execute();
	        	
	            (new ActionDialog(this))
	            	.setTitle("@ll in Shopping")
						.setMessage(
								"Seu pedido foi concluído com sucesso, obrigado pela preferência")
	            	.show();
	            Intent intent = new Intent(this, HomeActivity.class);
	            startActivity(intent);
            
        	}
        } else
        {
            Intent intent1 = new Intent(this, PagamentoActivity.class);
            startActivity(intent1);
        }
    }

    @Override
	protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);
        aq = new AQuery(this);
        aq.id(R.id.logar).clicked(this);
    }
}
