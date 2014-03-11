package com.pmrodrigues.android.allinshopping;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.pmrodrigues.android.allinshopping.adapters.ProdutoAdapter;
import com.pmrodrigues.android.allinshopping.adapters.SecaoAdapter;
import com.pmrodrigues.android.allinshopping.models.Secao;
import com.pmrodrigues.android.allinshopping.repository.SectionRepository;

public class HomeActivity extends AbstractActivity implements OnClickListener
{

    private AQuery aq;
    private Secao secao;
	private ListView secoes;
	private ListView produtos;
	private SectionRepository sectionrepository;
	
    public HomeActivity()
    {
    
    }

    @Override
	protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        this.sectionrepository = new SectionRepository(this);
        aq  = new AQuery(this);
        this.secoes = aq.id(R.id.secoes).getListView();
        this.produtos = aq.id(R.id.promocoes).getListView();
      
    }
    
    @Override
    protected void onStart() {
    	  super.onStart();
    	  carregarProdutosPrincipais();
    }

	public void carregarProdutosPrincipais() {
		try
          {
	
    		  //aq.id(R.id.promocao).text(R.string.promocao);
              SecaoAdapter secaoadapter = new SecaoAdapter(this, sectionrepository.getSections());
              secoes.setAdapter(secaoadapter);
              aq.id(R.id.promocoes).invisible();
              aq.id(R.id.boneca).visible();
              aq.id(R.id.boneca).image(R.drawable.lista_secoes);
                          
          }
          catch (SQLException sqlexception)
          {
              throw new RuntimeException(sqlexception);
          }
	}

    @Override
	public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
	public boolean onOptionsItemSelected(MenuItem menuitem)
    {
    	Intent intent = null;
        switch(menuitem.getItemId()){
        	case R.id.action_settings:
        		intent = new Intent(this, ConfigurationActivity.class);
        		break;
        	case R.id.shopping_cart:
        		intent = new Intent(this, ShoppingCartActivity.class);
        		break;
        	default:
        		intent = new Intent(this,HomeActivity.class);
        		break;
        }
        startActivity(intent);
        boolean returned = super.onOptionsItemSelected(menuitem);
        return returned;
    }

    @Override
    public void onBackPressed() {
    	
    	if( this.secao == null ) {    	
    		super.onBackPressed();
    	} else {
    		this.secao = secao.getSecaoPai();
    		mountAdapters();
    	}
    }


    @Override
	public void onClick(View view)
    {
        
        
        this.secao = (Secao)view.getTag();
        this.mountAdapters();
       
        
    }

	public void mountAdapters() {
	
		
		if( this.secao != null ) {
		
			aq.id(R.id.promocao).text(secao.getTitulo());
			
			if( !this.secao.getSubSecoes().isEmpty() ) {		
				
				List<Secao> secoes = new ArrayList<Secao>(this.secao.getSubSecoes());
				Secao todos = new Secao("Todos");
				todos.setSecaoPai(this.secao.getSecaoPai());
				todos.addProdutos(this.secao.getProdutos());
				secoes.add(todos);
				
				SecaoAdapter secaoadapter = new SecaoAdapter(this,secoes);
				this.secoes.setAdapter(secaoadapter);
				aq.id(R.id.boneca).image(R.drawable.lista_produtos);
				
			} else {
				ProdutoAdapter produtoadapter = new ProdutoAdapter(this, this.secao.getProdutos());
				produtos.setAdapter(produtoadapter);
				aq.id(R.id.promocoes).getListView().setAdapter(produtoadapter);
				aq.id(R.id.boneca).invisible();
				aq.id(R.id.promocoes).visible();
			}
        
		} else {
			carregarProdutosPrincipais(); 
		}
	}
    
}
