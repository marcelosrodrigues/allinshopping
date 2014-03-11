package com.pmrodrigues.android.allinshopping.utilities;

import java.math.BigDecimal;
import java.util.Locale;

import android.os.Environment;

import com.akatus.connect.api.Akatus;

public interface Constante
{

	String FAIXA_ENTREGA = "cep";
	String PEDIDO = "pedido";
	BigDecimal PERCENTUAL_GATEWAY_PAGAMENTO = new BigDecimal(
			"0.942");
	BigDecimal TAXA_GATEWAY_PAGAMENTO = new BigDecimal("0.39");
	BigDecimal PERCENTUAL_GESTOR = new BigDecimal("0.9547");
	BigDecimal PERCENTUAL_LIDER = new BigDecimal("0.8748");
	BigDecimal PERCENTUAL_PROJETANDOO = new BigDecimal("0.70");
	BigDecimal PERCENTUAL_TI = new BigDecimal("0.95");
	BigDecimal PERCENTUAL_VENDEDORAS = new BigDecimal("0.5828");
	Locale PT_BR = new Locale("pt-BR");
	String SDCARD_ALLINSHOPP_IMAGES = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/allinshopp/images/";
	String DATE_LONG_FORMAT = "dd-MM-yyyy";
	String AUTH_USER = "projetandoo@projetandoo.com.br";
	//PRODUCAO
	String AUTH_PASSWORD = "64D18C2C-9798-4F3A-BD18-FFDCA98707EE";
	Akatus.Environment ENVIROMENT = Akatus.Environment.PRODUCTION;
	
	//DESENVOLVIMENTO
	//public static final String AUTH_PASSWORD = "CC556925-7593-4B66-8568-5C9D592DF992";
	String ERROR = "erro";
	String CLIENTE = "cliente";
	
	

}
