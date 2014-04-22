package com.pmrodrigues.android.allinshopping.models;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class FaixaEntrega
    implements Serializable
{

    private static final long serialVersionUID = 1L;
    
    public static final String ID_FIELD = "id";
	public static final String ID_FAIXA_FIELD = FaixaPreco.CEP_FIELD_NAME;
	public static final String UF_ORIGEM_FIELD = "uforigem";
	public static final String UF_DESTINO_FIELD = "ufdestino";
    
    
    @DatabaseField(id=true,columnName=FaixaEntrega.ID_FIELD)
    private Long id;
    
	@SerializedName("id_faixa_preco")
    @DatabaseField(columnName=FaixaEntrega.ID_FAIXA_FIELD)
    private Long idFaixa;

	@SerializedName("uf_destino")
    @DatabaseField(columnName=FaixaEntrega.UF_DESTINO_FIELD)
    private String ufDestino;

	@SerializedName("uf_origem")
    @DatabaseField(columnName=FaixaEntrega.UF_ORIGEM_FIELD)
    private String ufOrigem;

    public FaixaEntrega()
    {
    }

    public FaixaEntrega(String ufOrigem, String ufDestino, Long idFaixa)
    {
        this();
        this.ufOrigem = ufOrigem;
        this.ufDestino = ufDestino;
        this.idFaixa = idFaixa;
    }

    public Long getId()
    {
        return id;
    }

    public Long getIdFaixa()
    {
        return idFaixa;
    }

    public String getUfDestino()
    {
        return ufDestino;
    }

    public String getUfOrigem()
    {
        return ufOrigem;
    }
}
