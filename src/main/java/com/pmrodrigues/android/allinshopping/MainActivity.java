package com.pmrodrigues.android.allinshopping;

import org.apache.commons.validator.GenericValidator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.androidquery.AQuery;
import com.pmrodrigues.android.allinshopping.alerts.ErrorAlert;
import com.pmrodrigues.android.allinshopping.models.FaixaPreco;
import com.pmrodrigues.android.allinshopping.services.CEPService;
import com.pmrodrigues.android.allinshopping.services.ConfigurationService;
import com.pmrodrigues.android.allinshopping.utilities.Constante;
import com.pmrodrigues.android.allinshopping.utilities.PriceUtilities;

public class MainActivity extends AbstractActivity implements OnClickListener {

	private AQuery aq;
	private CEPService cepservice;
	private ConfigurationService service;
	
	private static final Long UNDEFINED_ZIPCODE = 0L;
	
	public void setCEP(final String cep) {
		aq.id(R.id.zipcode).text(cep);
	}
	
	public Long getCEP() {
		final String zipcode = aq.id(R.id.zipcode).getText().toString();
		Long cep = UNDEFINED_ZIPCODE;
		if( !GenericValidator.isBlankOrNull(zipcode) && GenericValidator.isLong(zipcode)){
			cep = Long.parseLong(zipcode.substring(0,5));
		} 
		return cep;
	}

	@Override
	public void onClick(final View view) {

		final Long cep = this.getCEP();
		if( cep > UNDEFINED_ZIPCODE ){
			final Intent intent = new Intent(this, HomeActivity.class);
			final FaixaPreco frete = cepservice.getFaixaPrecoByCEPDestino(cep);
			PriceUtilities.setFrete(frete);
			intent.putExtra(Constante.FRETE, frete);
			startActivity(intent);
		} else {
			new ErrorAlert(this)
					.setTitle("CEP é obrigatório")
					.setCancelable(true)
					.show();
		}

	}
	

	@Override
	public void onCreate(final Bundle bundle) {
		super.onCreate(bundle);		
		setContentView(R.layout.activity_home);
		cepservice = new CEPService(this);
		service = new ConfigurationService(this);
		aq = new AQuery(this);
		
		aq.id(R.id.progressText).clicked(this);
		aq.id(R.id.shopping).clicked(this);
		aq.id(R.id.progressText).text(service.getNomeLoja());
		aq.clickable(true);
		
		if (service.precisaAtualizar()) {
			final Intent intent = new Intent(this, ConfigurationActivity.class);
			startActivity(intent);
		} 
	}

}
