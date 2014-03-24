package com.pmrodrigues.android.allinshopping;

import android.app.Activity;

import com.pmrodrigues.android.allinshopping.models.Pedido;

public abstract class AbstractActivity extends Activity
{

	private Pedido pedido;

    protected Pedido getPedido()
    {
		return this.pedido;
    }

	protected void setPedido(final Pedido pedido)
    {
		this.pedido = pedido;
    }
}
