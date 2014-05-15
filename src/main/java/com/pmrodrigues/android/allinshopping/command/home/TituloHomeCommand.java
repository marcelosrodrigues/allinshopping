package com.pmrodrigues.android.allinshopping.command.home;

import com.pmrodrigues.android.allinshopping.HomeActivity;
import com.pmrodrigues.android.allinshopping.command.Command;

public class TituloHomeCommand implements Command {

	private final HomeActivity home;

	public TituloHomeCommand(final HomeActivity home) {
		this.home = home;
	}

	@Override
	public Command execute() {
		home.setTitulo(home.getSecao().getTitulo());
		Command command =  null;
		if (home.getSecao().getSubSecoes().isEmpty()) {				
			command = new ProdutosParaSecaoSemFilhosCommand(home);
		} else {
			command = new ProdutosParaSecaoComFilhosCommand(home);
		}
		return command.execute();
	}

}
