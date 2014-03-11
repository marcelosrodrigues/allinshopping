package com.pmrodrigues.android.allinshopping.models;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class CEP
    implements Serializable
{

    private static final long serialVersionUID = 1L;
    
    public static final String FIM_FIELD = "fim";
    
    public static final String ID_FIELD = "id";
    
    public static final String INICIO_FIELD = "inicio";
    
    public static final String UF_FIELD = "uf";
    
    
    @DatabaseField(columnName=CEP.FIM_FIELD)
    private Long fim;
    
    @DatabaseField(id=true,columnName=CEP.ID_FIELD)
    private Long id;
    
    @DatabaseField(columnName=CEP.INICIO_FIELD)
    private Long inicio;
    
    @DatabaseField(columnName=CEP.UF_FIELD)
    private String uf;

    public CEP()
    {
    }

    public CEP(Long id, String uf, Long inicio, Long fim)
    {
        this();
        this.id = id;
        this.uf = uf;
        this.inicio = inicio;
        this.fim = fim;
    }

    public Long getFim()
    {
        return fim;
    }

    public Long getId()
    {
        return id;
    }

    public Long getInicio()
    {
        return inicio;
    }

    public String getUf()
    {
        return uf;
    }
}
