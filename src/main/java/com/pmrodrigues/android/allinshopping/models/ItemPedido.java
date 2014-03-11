package com.pmrodrigues.android.allinshopping.models;

import java.io.Serializable;
import java.math.BigDecimal;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.pmrodrigues.android.allinshopping.utilities.Constante;

@DatabaseTable
public class ItemPedido
    implements Serializable
{

    private static final long serialVersionUID = 1L;
    
    @DatabaseField
    private Long cor;
    @DatabaseField(generatedId=true)    
    private Long id = 0L;
    @DatabaseField(foreign=true,foreignAutoRefresh=true)
    private Pedido pedido;
    @DatabaseField(foreign=true,foreignAutoRefresh=true)
    private Produto produto;
    @DatabaseField
    private Long quantidade;
    @DatabaseField
    private String tamanho;

    @DatabaseField
	private BigDecimal frete;

    public ItemPedido()
    {
        
    }

    public ItemPedido(Produto produto, String tamanho)
    {
        this();
        this.produto = produto;
        this.quantidade = 1L;
        this.tamanho = tamanho;
        this.frete = produto.getFrete();
    }

    public void aumentar()
    {   
        quantidade++;
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if (obj instanceof ItemPedido)
        {
            Produto produto1 = ((ItemPedido)obj).getProduto();
            Produto produto2 = produto;
            flag = produto1.equals(produto2);
        } else
        {
            flag = false;
        }
        return flag;
    }

    public Long getId()
    {
        return id;
    }

    public Produto getProduto()
    {
        return produto;
    }

    public Long getQuantidade()
    {
        return quantidade;
    }

    public String getTamanho()
    {
        return tamanho;
    }

    public BigDecimal getTotal()
    {
        return produto.getPreco()
        			  .multiply(new BigDecimal(quantidade))
        			  .add(getMargemProjetandoo())
					  .add(getMargemTI())
					  .add(getMargemGestor())
					  .add(getMargemVendedoras())
					  .add(getMargemLider())
					  .add(getFrete())
        			  .divide(Constante.PERCENTUAL_GATEWAY_PAGAMENTO,2,BigDecimal.ROUND_UP)
        			  .add(Constante.TAXA_GATEWAY_PAGAMENTO);
    }
    
    public BigDecimal getMargemGestor()
    {
        return produto.getMargemGestor().multiply(new BigDecimal(quantidade));
    }
    
    public BigDecimal getMargemLider()
    {
        return produto.getMargemLider().multiply(new BigDecimal(quantidade));
    }

    public BigDecimal getMargemTI()
    {
    	return produto.getMargemTI().multiply(new BigDecimal(quantidade));
    }

    public BigDecimal getMargemVendedoras()
    {
    	return produto.getMargemVendedoras().multiply(new BigDecimal(quantidade));
    }

    public BigDecimal getMargemProjetandoo()
    {
    	return produto.getMargemProjetandoo().multiply(new BigDecimal(quantidade));
    }
    
    public BigDecimal getFrete() {
    	return this.frete.multiply(new BigDecimal(quantidade));
    }

    public BigDecimal getTotalLiquido()
    {
    	return produto.getPreco()
  			  .multiply(new BigDecimal(quantidade));
    }
    
    
    public int hashCode()
    {
        return produto.hashCode();
    }

    public void setPedido(Pedido pedido1)
    {
        pedido = pedido1;
    }

    public void setQuantidade(Long long1)
    {
        quantidade = long1;
    }

	public BigDecimal getAkatus() {
		
		return this.getTotal().subtract(produto.getPreco()
								  			  .multiply(new BigDecimal(quantidade))
								  			  .add(this.getMargemGestor())
								  			  .add(this.getMargemProjetandoo())
								  			  .add(this.getMargemTI())
								  			  .add(this.getMargemVendedoras())
								  			  .add(this.getFrete()));
	}
}
