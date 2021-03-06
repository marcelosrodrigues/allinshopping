package com.pmrodrigues.android.allinshopping.command.home;

import java.util.ArrayList;
import java.util.List;

import com.pmrodrigues.android.allinshopping.HomeActivity;
import com.pmrodrigues.android.allinshopping.R;
import com.pmrodrigues.android.allinshopping.adapters.SecaoAdapter;
import com.pmrodrigues.android.allinshopping.command.Command;
import com.pmrodrigues.android.allinshopping.models.Secao;

public class ProdutosParaSecaoComFilhosCommand implements Command {
	
	private final Secao TODOS = new Secao("Todos");
	
	private final HomeActivity home;

	public ProdutosParaSecaoComFilhosCommand(final HomeActivity home) {
		this.home = home;
	}

	@Override
	public Command execute() {

		final List<Secao> secoes = new ArrayList<Secao>(
				home.getSecao().getSubSecoes());
		TODOS.setSecaoPai(home.getSecao().getSecaoPai());
		TODOS.addProdutos(home.getSecao().getProdutos());
		secoes.add(TODOS);

		home.getSecoes().setAdapter(new SecaoAdapter(home, secoes));
		home.setBoneca(R.drawable.lista_produtos);
		
		return null;
	}

}
