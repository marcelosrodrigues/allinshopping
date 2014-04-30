package com.pmrodrigues.android.allinshopping.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.validator.GenericValidator;

import android.util.Log;

import com.akatus.connect.api.Akatus;
import com.akatus.connect.api.v1.cart.CartOperation;
import com.akatus.connect.api.v1.cart.CartResponse;
import com.akatus.connect.api.v1.entity.Holder;
import com.akatus.connect.api.v1.entity.Transaction;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.utilities.Constante;
import com.pmrodrigues.android.allinshopping.utilities.ParseUtilities;

@DatabaseTable
public class Pedido
    implements Serializable
{

    private static final long serialVersionUID = 1L;

    @DatabaseField(foreign=true,foreignAutoRefresh=true)
    private Cliente cliente;
    
    @DatabaseField(foreign=true,foreignAutoRefresh=true)
    private DadosPagamento dadosPagamento;
    
    @DatabaseField
    private Date dataEnvio;
    
    @DatabaseField
    private Date dataPedido;
    
    @DatabaseField
    private Date dataPagamento;
    
    @DatabaseField(foreign=true,foreignAutoRefresh=true)
    private FormaPagamento formaPagamento;
    
    @DatabaseField(generatedId=true)
    private Long id;
    
    @ForeignCollectionField(eager=true)
    private Collection<ItemPedido> itens = new ArrayList<ItemPedido>();

    @DatabaseField
	private String idTransacao;
    
    @DatabaseField
    private String email;
    
    @DatabaseField
    private String password;
    
    public boolean pagar() throws IntegrationException {
    	CartOperation cart = new Akatus(Constante.ENVIROMENT,Constante.AUTH_USER, Constante.AUTH_PASSWORD).cart();
    	cart.setPayer(cliente.toPayer());
    	
    	for(ItemPedido item : this.itens ){
    		cart.addProduct(item.getProduto().getId().toString(), 
    						item.getProduto().getTitulo(), 
    						item.getTotal().divide(new BigDecimal(item.getQuantidade()),2,BigDecimal.ROUND_UP).doubleValue(), 
    						item.getProduto().getPeso().doubleValue(), 
    						item.getQuantidade().intValue());
    	}
    	
    	Transaction trans = cart.getTransaction();
    	trans.setReference(this.id.toString());
    	trans.setInstallments(dadosPagamento.getQtdParcelas());
    	if( formaPagamento.isCartao() ) {
	    	trans.setExpiration( ParseUtilities.formatDate(ParseUtilities.toDate(dadosPagamento.getValidade(),"MMyy"),"MM/yyyy")) ;
	    	trans.setNumber(dadosPagamento.getNumero());
	    	trans.setPaymentMethod(formaPagamento.getPaymentMethod());
	    	trans.setSecurityNumber(dadosPagamento.getCVV());
    	} 
    	//TODO avaliar outros meios de pagamento
    	trans.setHolder(new Holder());
    	trans.getHolder().setDocument(dadosPagamento.getCPF());
    	trans.getHolder().setName(dadosPagamento.getNome());
    	
    	if( !GenericValidator.isBlankOrNull(cliente.getTelefone()) && !"{}".equalsIgnoreCase(cliente.getTelefone()) ) {
    		trans.getHolder().setPhone(cliente.getTelefone());
        }
        
        if( !GenericValidator.isBlankOrNull(cliente.getCelular())  && !"{}".equalsIgnoreCase(cliente.getCelular())) {
        	trans.getHolder().setPhone(cliente.getCelular());
        }
    	
    	
    	
    	CartResponse response = (CartResponse) cart.execute();
    	if( !Constante.ERROR.equalsIgnoreCase(response.getStatus())) {
    		this.dataPagamento = new Date();
    		this.idTransacao = response.getTransaction();
    		return true;
    	} else {
    		Log.d("com.pmrodrigues.android.allinshopping", response.getDescription());
    		return false;    		
    	}
    	
    }

    public Pedido()
    {
        Date date = new Date();
        dataPedido = date;
    }

    public Pedido(Cliente cliente)
    {
        this();
        this.cliente = cliente;
        dataPedido = new Date();
    }

    public void add(Produto produto, String s)
 {
    	for( ItemPedido item : itens ) {
    		if (item.getProduto().equals(produto) && item.getTamanho().equals(s)) {
    			item.aumentar();
    			return;
    		}	
    	}
    	
    	ItemPedido item = new ItemPedido(produto, s);
    	item.setPedido(this);
    	this.itens.add(item);
    	
    }

    public void enviado()
    {
        Date date = new Date();
        dataEnvio = date;
    }

    public Cliente getCliente()
    {
        return cliente;
    }

    public DadosPagamento getDadosPagamento()
    {
        return dadosPagamento;
    }

    public FormaPagamento getFormaPagamento()
    {
        return formaPagamento;
    }

    public Collection<ItemPedido> getItens()
    {
        return itens;
    }

    public BigDecimal getTotal()
    {
		BigDecimal total = BigDecimal.ZERO;
        for(ItemPedido item : itens) {
        	total = total.add(item.getTotal());
        }

/*		if (PriceUtilities.getFaixaEntrega() == null) {
			total = total.add(new BigDecimal(2));
		}*/

        return total;
    }

    public BigDecimal getTotalLiquido()
    {
		BigDecimal total = BigDecimal.ZERO;
        for(ItemPedido item : itens) {
        	total = total.add(item.getTotalLiquido());
        }
        return total;
    }

    public void remover(ItemPedido itempedido)
    {
        itens.remove(itempedido);
    }

    public void setCliente(Cliente cliente1)
    {
        cliente = cliente1;
    }

    public void setDadosPagamento(DadosPagamento dadospagamento)
    {
        dadosPagamento = dadospagamento;
        
    }

    public void setFormaPagamento(FormaPagamento formapagamento)
    {
        formaPagamento = formapagamento;
    }

    public void setItens(List<ItemPedido> itens)
    {
        this.itens = itens;
    }

	public String getIdTransacao() {
		return this.idTransacao;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSenha(String senha) {
		this.password = senha;
	}

	public String getEmail() {
		return this.email ;
	}

	public String getSenha() {
		return this.password;
	}

	public Long getId() {
		return this.id;
	}
}
