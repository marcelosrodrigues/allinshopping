package com.pmrodrigues.android.allinshopping.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Secao
    implements Serializable
{

    private static final long serialVersionUID = 1L;
    
	@SerializedName("id_secao")
    @DatabaseField(id=true)
    private Long id;
    
	@SerializedName("secao")
    @DatabaseField
    private String nome;
    
    @ForeignCollectionField
    private final Collection<Produto> produtos = new ArrayList<Produto>();

	@SerializedName("pai")
    @DatabaseField(foreign=true,foreignAutoRefresh=true)
    private Secao secaoPai;
    
    @ForeignCollectionField(eager=true)
    private final Collection<Secao> subsecoes = new ArrayList<Secao>();

    public Secao()
    {
    	
    }

    public Secao(Long id, String nome)
    {
        this();
        this.id = id;
        this.nome = nome;
    }
    
    public boolean isVestuario() {
    	Secao secao = this;
    	boolean  isvestuario = "Vestuário".equalsIgnoreCase(secao.getNome());    	
    	if( !isvestuario ) {
    		secao = secao.getSecaoPai();
    		while( secao != null ) {
    			isvestuario = secao.isVestuario();
	            if(isvestuario){
	            	break;
	            }else{
	            	secao = secao.getSecaoPai();
	            }
    		}
    	}
                
        return isvestuario;
    }
    
    public boolean isCalcado() {
    	Secao secao = this;
    	boolean  isvestuario = "Calçados".equalsIgnoreCase(secao.getNome());    	
    	if( !isvestuario ) {
    		secao = secao.getSecaoPai();
    		while( secao != null ) {
    			isvestuario = secao.isCalcado();
	            if(isvestuario){
	            	break;
	            }else{
	            	secao = secao.getSecaoPai();
	            }
    		}
    	}
                                
        return isvestuario;
    }
    

    public Secao(Long long1, String s, Secao secao)
    {
        this(long1, s);
        secaoPai = secao;
    }

    public Secao(String s)
    {
        this(0L, s);
    }

    public void add(Secao secao)
    {
        subsecoes.add(secao);
    }

    public void addProdutos(Collection<Produto> produtos)
    {
    	for(Produto produto : produtos) {
    		produto.setSecao(this);
    		this.produtos.add(produto);
    	}
        
    }

    public void addSecoes(Collection<Secao> secoes)
    {
        subsecoes.addAll(secoes);
    }

    public Long getId()
    {
        return id;
    }

    public String getNome()
    {
        return nome;
    }

    public List<Produto> getProdutos()
    {
    	List<Produto> produtos = new ArrayList<Produto>();
        for(Secao secao : subsecoes ) {
        	produtos.addAll(secao.getProdutos());
        }
        produtos.addAll(this.produtos);
        return produtos;
    }

    public Secao getSecaoPai()
    {
        return secaoPai;
    }

    public Collection<Secao> getSubSecoes()
    {
    	Collection<Secao> subsecoes = new ArrayList<Secao>();
    	for( Secao secao : this.subsecoes ) {
    		if( secao.temProduto() ) {
    			subsecoes.add(secao);
    		}
    	}
        return subsecoes;
    }

    public String getTitulo()
    {
        String titulo = "";
        if( this.getSecaoPai() != null )
        {	
        	titulo += this.getSecaoPai().getTitulo();
        }
        titulo += "/" + this.nome;
        return titulo;
    }

    public void setNome(String s)
    {
        nome = s;
    }

    public void setSecaoPai(Secao secao)
    {
        secaoPai = secao;
    }

	public boolean temProduto() {
		return !this.getProdutos().isEmpty();
	}
}
