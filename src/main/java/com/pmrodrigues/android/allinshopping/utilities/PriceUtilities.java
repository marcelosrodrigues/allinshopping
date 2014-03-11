package com.pmrodrigues.android.allinshopping.utilities;

import com.pmrodrigues.android.allinshopping.models.FaixaEntrega;
import com.pmrodrigues.android.allinshopping.models.Pedido;

public class PriceUtilities {

	private static FaixaEntrega faixaentrega;
	private static Pedido pedido;
	
	private PriceUtilities() {}

	public static void setFaixaEntrega(FaixaEntrega faixaentrega) {
		PriceUtilities.faixaentrega = faixaentrega;		
	}
	
	public static FaixaEntrega getFaixaEntrega() {
		return PriceUtilities.faixaentrega;
	}

	public static Pedido getPedido() {
		
		if( PriceUtilities.pedido == null ) {
			PriceUtilities.pedido = new Pedido();
		}
		return PriceUtilities.pedido;
	}
	
}
