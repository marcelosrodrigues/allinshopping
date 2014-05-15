package com.pmrodrigues.android.allinshopping.commandfactory;

import android.app.Activity;

import com.pmrodrigues.android.allinshopping.HomeActivity;
import com.pmrodrigues.android.allinshopping.command.Command;
import com.pmrodrigues.android.allinshopping.command.home.HomeCommand;
import com.pmrodrigues.android.allinshopping.command.home.TituloHomeCommand;

class ProdutosCommandFactory extends CommandFactory{

	private final HomeActivity home;

	public ProdutosCommandFactory(final Activity activity) {
		this.home = (HomeActivity) activity;
	}

	@Override
	public Command createCommand() {
		if (home.getSecao() == null) {
			return new HomeCommand(home);
		} else {			
			return new TituloHomeCommand(home);
		}
	}
	
	

}
