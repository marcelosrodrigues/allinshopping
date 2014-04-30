package com.pmrodrigues.android.allinshopping;

import org.apache.commons.validator.GenericValidator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.androidquery.AQuery;
import com.pmrodrigues.android.allinshopping.alerts.ErrorAlert;
import com.pmrodrigues.android.allinshopping.models.Configuracao;
import com.pmrodrigues.android.allinshopping.services.AtualizacaoService;
import com.pmrodrigues.android.allinshopping.services.CEPService;

public class MainActivity extends AbstractActivity
 implements OnClickListener
{

    private AQuery aq;
	private CEPService cepservice;
    private AtualizacaoService service;

    @Override
	public void onClick(View view)
    {
        
        if (!GenericValidator.isBlankOrNull(aq.id(R.id.zipcode).getText().toString()))
        {
        	Intent intent = new Intent(this, HomeActivity.class);

			if (!GenericValidator.isLong(aq.id(R.id.zipcode).getText()
					.toString())) {
				Long cep = Long.parseLong(aq.id(R.id.zipcode).getText()
						.toString().substring(0, 5));
//				FaixaEntrega faixaentrega = cepservice.findByCEPCode(cep);
//				PriceUtilities.setFaixaEntrega(faixaentrega);
//				intent.putExtra(Constante.FAIXA_ENTREGA, faixaentrega);
			}

            startActivity(intent);
        } else {
			new ErrorAlert(this).setTitle("CEP é obrigatório")
        						.setCancelable(true)
        						.show();
        }
        
    }

    @Override
	protected void onCreate(Bundle bundle)
    {	
        super.onCreate(bundle);
		cepservice = new CEPService(this);
        service = new AtualizacaoService(this);
        if( service.precisaAtualizar() ) {
        	Intent intent = new Intent(this,ConfigurationActivity.class);
        	startActivity(intent);
        } else {
	        setContentView(R.layout.activity_home);
	        aq = new AQuery(this);
	        
	        Configuracao configuracao = service.get();
	        
	        aq.id(R.id.progressText).clicked(this);
	        aq.id(R.id.shopping).clicked(this);
	        aq.id(R.id.progressText).text(configuracao.getNomeLoja());
	        aq.clickable(true);
	        
        }
    }
}
