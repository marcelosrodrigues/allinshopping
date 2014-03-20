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
	public static final String ID_FAIXA_FIELD = "idfaixa";
	public static final String PESO_INICIAL_FIELD = "pesoinicial";
	public static final String PESO_FINAL_FIELD = "pesofinal";
	public static final String PRECO_FIELD = "preco";
	
    
    @DatabaseField(id=true,columnName=FaixaPreco.ID_FIELD)
    private Long id;

	@SerializedName("id_faixa")
    @DatabaseField(columnName=FaixaPreco.ID_FAIXA_FIELD)
    private Long idFaixa;

	@SerializedName("peso_final")
    @DatabaseField(columnName=FaixaPreco.PESO_FINAL_FIELD)
    private Long pesoFinal;

	@SerializedName("peso_inicial")
    @DatabaseField(columnName=FaixaPreco.PESO_INICIAL_FIELD)
    private Long pesoInicial;

	@SerializedName("preco")
    @DatabaseField(columnName=FaixaPreco.PRECO_FIELD)
    private BigDecimal preco;

    public FaixaPreco()
    {
    }

    public FaixaPreco(Long idfaixa, Long pesoInicial, Long pesoFinal, BigDecimal preco)
    {
        this.idFaixa = idfaixa;
        this.pesoInicial = pesoInicial;
        this.pesoFinal = pesoFinal;
        this.preco = preco;
    }

    public Long getId()
    {
        return id;
    }

    public Long getIdFaixa()
    {
        return idFaixa;
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
}
