package com.pmrodrigues.android.allinshopping.utilities;

import android.os.Environment;

import java.math.BigDecimal;
import java.util.Locale;

public interface Constante {

    String FRETE = "cep";
    String PEDIDO = "pedido";

    BigDecimal PERCENTUAL_GATEWAY_PAGAMENTO = new BigDecimal("0.942");
    BigDecimal TAXA_GATEWAY_PAGAMENTO = new BigDecimal("0.39");
    BigDecimal PERCENTUAL_GESTOR = new BigDecimal("0.9547");
    BigDecimal PERCENTUAL_LIDER = new BigDecimal("0.8748");
    BigDecimal PERCENTUAL_PROJETANDOO = new BigDecimal("0.70");
    BigDecimal PERCENTUAL_TI = new BigDecimal("0.95");
    BigDecimal PERCENTUAL_VENDEDORAS = new BigDecimal("0.5828");

    Locale PT_BR = new Locale("pt-BR");

    String SDCARD_ALLINSHOPP_IMAGES = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/allinshopp/images/";
    String DATE_LONG_FORMAT = "dd-MM-yyyy";
    String AUTH_USER = "projetandoo@projetandoo.com.br";
    // PRODUCAO
    String AUTH_PASSWORD = "64D18C2C-9798-4F3A-BD18-FFDCA98707EE";
    //Akatus.Environment ENVIROMENT = Akatus.Environment.PRODUCTION;

    String ERROR = "erro";
    String CLIENTE = "cliente";

    String CREDENTIALS = "KIGDS4I9RGFKHW48673LEOLYHMH5S0EN";
    String DATA_ATUALIZACAO = "dataAtualizacao";
    String NOME_LOJA = "nomeLoja";
    String PASSWORD = "password";
    String USERNAME = "username";
    String SHARED_PREFERENCES = "ellasa";

}
