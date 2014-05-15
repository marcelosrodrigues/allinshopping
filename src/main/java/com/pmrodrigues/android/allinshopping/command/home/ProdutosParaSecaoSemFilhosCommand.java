package com.pmrodrigues.android.allinshopping.command.home;

import com.pmrodrigues.android.allinshopping.HomeActivity;
import com.pmrodrigues.android.allinshopping.adapters.ProdutoAdapter;
import com.pmrodrigues.android.allinshopping.command.Command;

public class ProdutosParaSecaoSemFilhosCommand implements Command {

	private final HomeActivity home;

	public ProdutosParaSecaoSemFilhosCommand(final HomeActivity home) {
		this.home = home;
	}

	@Override
	public Command execute() {
		
		final ProdutoAdapter produtoadapter = new ProdutoAdapter(home,
				home.getSecao().getProdutos());
		home.getProdutos().setAdapter(produtoadapter);
		home.exibirBoneca(false);
		home.exibirListaProdutos(true);
		
		return null;
	}

}
