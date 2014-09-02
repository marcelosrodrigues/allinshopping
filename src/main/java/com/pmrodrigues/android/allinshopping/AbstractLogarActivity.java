package com.pmrodrigues.android.allinshopping;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.GenericValidator;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.androidquery.AQuery;
import com.pmrodrigues.android.allinshopping.alerts.ErrorAlert;

public abstract class AbstractLogarActivity extends AbstractActivity
		implements
			OnClickListener {

	private AQuery aq;

	public AbstractLogarActivity() {
		super();
	}

	private boolean isValid() {
		
		List<String> messages = new ArrayList<String>();

		if (GenericValidator.isBlankOrNull(aq.id(R.id.email).getText()
				.toString())) {
			messages.add("E-mail é obrigatória");
		}

		if (!GenericValidator.isEmail(aq.id(R.id.email).getText()
				.toString())) {
			messages.add("E-mail é inválido");
		}

		if (GenericValidator.isBlankOrNull(aq.id(R.id.password).getText()
				.toString())) {
			messages.add("Senha é obrigatória");
		}
		if (!messages.isEmpty()) {
			new ErrorAlert(this).setTitle("Pedido").setMessages(messages)
					.show();
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public void onClick(View view) {
		
		if (view.getId() == R.id.logar) {

				if( isValid() ) {
					this.doAction();
				}

		} else {
			this.doBack();
		}
	
	}

	protected abstract void doAction();
	
	protected abstract void doBack();

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_login);
		aq = new AQuery(this);
		aq.id(R.id.logar).clicked(this);
	}

	public String getEmail() {
		return aq.id(R.id.email).getText().toString();
	}

	public String getPassword() {
		return aq.id(R.id.password).getText().toString();
	}

}