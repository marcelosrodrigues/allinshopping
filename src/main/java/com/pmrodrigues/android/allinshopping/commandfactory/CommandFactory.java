package com.pmrodrigues.android.allinshopping.commandfactory;

import android.app.Activity;

import com.pmrodrigues.android.allinshopping.HomeActivity;
import com.pmrodrigues.android.allinshopping.command.Command;

public abstract class CommandFactory {
	
	public static CommandFactory getFactory(final Activity activity) {
		if( activity instanceof HomeActivity ){
			return new ProdutosCommandFactory(activity);
		} else {
			return null;
		}
	}
	
	public abstract Command createCommand();
}
