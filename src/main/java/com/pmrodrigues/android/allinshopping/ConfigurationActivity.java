package com.pmrodrigues.android.allinshopping;

import org.apache.commons.validator.GenericValidator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.androidquery.AQuery;
import com.pmrodrigues.android.allinshopping.alerts.ErrorAlert;
import com.pmrodrigues.android.allinshopping.alerts.UpdateApplicationDialog;
import com.pmrodrigues.android.allinshopping.models.Configuracao;
import com.pmrodrigues.android.allinshopping.services.AtualizacaoService;

public class ConfigurationActivity extends AbstractActivity
		implements
			OnClickListener
{

    private AQuery aq;

    public ConfigurationActivity()
    {
    }

    @Override
	public void onClick(View view)
    {
        if (view.getId() == R.id.atualizar)
        {
            (new UpdateApplicationDialog(this))
            		.setCancelable(true)
            		.setTitle("Deseja atualizar a base de dados?")
            		.setMessage("Enviaremos suas vendas em aberto e atualizaremos o seu banco de dados")
            		.show();
        } else if (view.getId() == R.id.salvar ){
        	
        	if( !GenericValidator.isBlankOrNull(aq.id(R.id.nomeLoja).getText().toString()) ) {
		    	
        		AtualizacaoService service = new AtualizacaoService(this);
		    	Configuracao config = service.get();
		    	config.setNomeLoja(aq.id(R.id.nomeLoja).getText().toString());
		    	service.atualizar(config);
		    	
		    	if( service.precisaAtualizar() ) {
		    		(new UpdateApplicationDialog(this))
	            		.setCancelable(true)
	            		.setTitle("Deseja atualizar a base de dados?")
	            		.setMessage("Enviaremos suas vendas em aberto e atualizaremos o seu banco de dados")
	            		.show();
		    	}
		    	
        	} else {
        		ErrorAlert alert = new ErrorAlert(this);
				alert.setTitle("Erro na configuração do sistema")
        			 .setCancelable(true)
						.setMessage("Nome da Loja é obrigatório")
        			 .show();
        	}
        	
        } else
        {
            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
	protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_config);
        aq = new AQuery(this);
        aq.id(R.id.atualizar).clicked(this);
        aq.id(R.id.salvar).clicked(this);
        aq.id(R.id.cancelar).clicked(this);
        
        AtualizacaoService service = new AtualizacaoService(this);
    	Configuracao config = service.get();
    	
    	if( !GenericValidator.isBlankOrNull(config.getNomeLoja()) ){
    		aq.id(R.id.nomeLoja).text(config.getNomeLoja());
    	}
        
    }
}
