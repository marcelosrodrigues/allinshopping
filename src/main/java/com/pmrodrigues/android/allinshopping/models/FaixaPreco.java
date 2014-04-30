package com.pmrodrigues.android.allinshopping.models;

import java.io.Serializable;
import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable
public class FaixaPreco
    implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	public static final String ID_FIELD = "id";
	public static final String PESO_INICIAL_FIELD = "pesoinicial";
	public static final String PESO_FINAL_FIELD = "pesofinal";
	public static final String PRECO_FIELD = "preco";
	public static final String DESTINO_FIELD_NAME = "destino";
	public static final String ORIGEM_FIELD_NAME = "origem";
	
    
    @DatabaseField(id=true,columnName=FaixaPreco.ID_FIELD)
    private Long id;

	@SerializedName("inicial")
    @DatabaseField(columnName=FaixaPreco.PESO_FINAL_FIELD)
    private Long pesoFinal;

	@SerializedName("termino")
    @DatabaseField(columnName=FaixaPreco.PESO_INICIAL_FIELD)
    private Long pesoInicial;

	@SerializedName("preco")
    @DatabaseField(columnName=FaixaPreco.PRECO_FIELD)
    private BigDecimal preco;
	
	@SerializedName("destino")
	@DatabaseField(columnName = FaixaPreco.DESTINO_FIELD_NAME, foreign = true, foreignAutoRefresh = true)
	private Estado destino; // NOPMD
	
	@SerializedName("origem")
	@DatabaseField(columnName = FaixaPreco.ORIGEM_FIELD_NAME, foreign = true, foreignAutoRefresh = true)
	private Estado origem; // NOPMD

    public FaixaPreco()
    {
    }

    public FaixaPreco(Long pesoInicial, Long pesoFinal, BigDecimal preco)
    {
    
        this.pesoInicial = pesoInicial;
        this.pesoFinal = pesoFinal;
        this.preco = preco;
    }

    public Long getId()
    {
        return id;
    }

    public Long getPesoFinal()
    {
        return pesoFinal;
    }

    public Long getPesoInicial()
    {
        return pesoInicial;
    }

    public BigDecimal getPreco()
    {
        return preco;
    }

	public Estado getDestino() {
		return destino;
	}
	
	public Estado getOrigem() {
		return origem;
	}
	
}
