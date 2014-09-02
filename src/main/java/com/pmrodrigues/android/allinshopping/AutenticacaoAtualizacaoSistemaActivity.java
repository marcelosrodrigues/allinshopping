package com.pmrodrigues.android.allinshopping;

import android.content.Intent;

import com.pmrodrigues.android.allinshopping.async.IntegrationAsyncProcess;

public class AutenticacaoAtualizacaoSistemaActivity
		extends
			AbstractLogarActivity {

	@Override
	protected void doAction() {
		
		IntegrationAsyncProcess integrationasyncprocess = new IntegrationAsyncProcess(this);
		integrationasyncprocess.setUserName(super.getEmail())
							   .setPassword(super.getPassword())
							   .execute();

	}

	@Override
	protected void doBack() {
		Intent pagamento = new Intent(this, ConfigurationActivity.class);
		startActivity(pagamento);	
	}

}
