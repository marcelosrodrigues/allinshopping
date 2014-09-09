package com.pmrodrigues.android.allinshopping;

import android.app.AlertDialog;
import android.content.Intent;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import com.pmrodrigues.android.allinshopping.alerts.ActionDialog;
import com.pmrodrigues.android.allinshopping.async.IntegrationAsyncProcess;

public class AutenticacaoAtualizacaoSistemaActivity
		extends
			AbstractLogarActivity {

	@Override
	protected void doAction() {

        final IntegrationAsyncProcess integrationasyncprocess = new IntegrationAsyncProcess(this);

		integrationasyncprocess.setUserName(super.getEmail())
							   .setPassword(super.getPassword())
                               .execute();

	}

    @Override
	protected void doBack() {
		Intent pagamento = new Intent(this, ConfigurationActivity.class);
		startActivity(pagamento);	
	}

    @Override
    protected int getLayout() {
        return R.layout.activity_atualizar_sistema;
    }

}
