package com.pmrodrigues.android.allinshopping.services;

import org.joda.time.DateTime;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.pmrodrigues.android.allinshopping.ApplicationContext;
import com.pmrodrigues.android.allinshopping.utilities.Constante;
import com.pmrodrigues.android.allinshopping.utilities.ParseUtilities;

public class ConfigurationService {

	private final SharedPreferences preferences;
	
	public ConfigurationService(final Context context) {
		preferences = context.getApplicationContext()
							 .getSharedPreferences(Constante.SHARED_PREFERENCES, Context.MODE_PRIVATE);
	}
	
	public ConfigurationService() {
		preferences = ApplicationContext.getInstance().getSharedPreferences(Constante.SHARED_PREFERENCES, Context.MODE_PRIVATE);
	}

	private void add(final String key , final String value ){
		
		final Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
		
	}
	
	private String get(final String key ){
		return preferences.getString(key, null);
	}
	
	public boolean precisaAtualizar() {
	
			final DateTime ultimaAtualizacao = new DateTime(ParseUtilities.toDate(preferences.getString(Constante.DATA_ATUALIZACAO, 
																					   ParseUtilities.formatDate(DateTime.now()
																							   							 .minusDays(1)
																							   							 .toDate(),"yyyy-MM-dd HH:mm:ss")),
																					   "yyyy-MM-dd HH:mm:ss"));			

			final DateTime agora = DateTime.now()
										   .withMinuteOfHour(0)
										   .withHourOfDay(0)
										   .withSecondOfMinute(0);
			return !( agora.isBefore(ultimaAtualizacao.getMillis()) );
				
			
	}
	
	public void atualizar() {
		this.add(Constante.DATA_ATUALIZACAO, ParseUtilities.formatDate(DateTime.now().toDate() , "yyyy-MM-dd HH:mm:ss"));
	}
	
	public void setNomeLoja(final String nomeLoja) {
		this.add(Constante.NOME_LOJA, nomeLoja);
	}
	
	public String getNomeLoja() {
		return this.get(Constante.NOME_LOJA);
	}

	public String getPassword() {
		return this.get(Constante.PASSWORD);
	}

	public String getUserName() {
		return this.get(Constante.USERNAME);
	}

}
