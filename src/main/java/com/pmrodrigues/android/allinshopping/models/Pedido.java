package com.pmrodrigues.android.allinshopping.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
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

    @Expose
    @DatabaseField(foreign=true,foreignAutoRefresh=true)
    private Cliente cliente;

    @Expose
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
	    	trans.setExpiration( ParseUtilities.formatDate(dadosPagamento.getValidade(),"MM/yyyy")) ;
	    	trans.setNumber(dadosPagamento.getNumero());
	    	trans.setPaymentMethod(formaPagamento.getPaymentMethod());
	    	trans.setSecurityNumber(dadosPagamento.getCVV());
    	} 
    	//TODO avaliar outros meios de pagamento
    	trans.setHolder(new Holder());
    	trans.getHolder().setDocument(dadosPagamento.getCPF());
    	trans.getHolder().setName(dadosPagamento.getPortador());
    	
    	if( !GenericValidator.isBlankOrNull(cliente.getEndereco().getTelefone()) && !"{}".equalsIgnoreCase(cliente.getEndereco().getTelefone()) ) {
    		trans.getHolder().setPhone(cliente.getEndereco().getTelefone());
        }
        
        if( !GenericValidator.isBlankOrNull(cliente.getEndereco().getCelular())  && !"{}".equalsIgnoreCase(cliente.getEndereco().getCelular())) {
        	trans.getHolder().setPhone(cliente.getEndereco().getCelular());
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

    public void adicionar(Produto produto, Atributo atributo)
 {
     ItemPedido item = new ItemPedido(produto,atributo,this);
     if( !this.itens.contains(item) ){
         this.itens.add(item);
     } else {
         for( ItemPedido i : this.itens ){
             if( i.equals(item) ){
                 i.aumentar();
                 break;
             }
         }
     }
    	
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

    public void setCliente(Cliente cliente)
    {
        this.cliente = cliente;
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

	public Long getId() {
		return this.id;
	}

    public void adicionar(Produto produto) {

        this.adicionar(produto,null);

    }
}
