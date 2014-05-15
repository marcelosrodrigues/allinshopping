package com.pmrodrigues.android.allinshopping.command.home;

import java.sql.SQLException;

import com.pmrodrigues.android.allinshopping.HomeActivity;
import com.pmrodrigues.android.allinshopping.R;
import com.pmrodrigues.android.allinshopping.adapters.SecaoAdapter;
import com.pmrodrigues.android.allinshopping.command.Command;
import com.pmrodrigues.android.allinshopping.repository.SectionRepository;

public class HomeCommand implements Command {
	
	private final HomeActivity home;
	private final SectionRepository REPOSITORY;

	public HomeCommand(final HomeActivity home) {
		this.home = home;
		REPOSITORY = new SectionRepository(home);
	}

	@Override
	public Command execute() {
		try {

			home.getSecoes().setAdapter(new SecaoAdapter(home, REPOSITORY
					.getSections()));
			home.exibirBoneca(true);
			home.setBoneca(R.drawable.lista_secoes);
			return null;
		} catch (SQLException sqlexception) {
			throw new RuntimeException(sqlexception);
		}
	}

}
