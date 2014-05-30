package com.pmrodrigues.android.allinshopping;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

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
	
    @Override
	public boolean onCreateOptionsMenu(final Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
	public boolean onOptionsItemSelected(final MenuItem menuitem)
    {
    	Intent toStart = null; //NOPMD
        switch(menuitem.getItemId()){
        	case R.id.action_settings:
        		toStart = new Intent(this, ConfigurationActivity.class);
        		break;
        	case R.id.shopping_cart:
        		toStart = new Intent(this, ShoppingCartActivity.class);
        		break;
        	default:
        		toStart = new Intent(this,HomeActivity.class);
        		break;
        }
        startActivity(toStart);
        return super.onOptionsItemSelected(menuitem);
    }
}
