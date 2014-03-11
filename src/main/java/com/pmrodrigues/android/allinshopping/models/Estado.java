package com.pmrodrigues.android.allinshopping.models;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Estado
    implements Serializable
{

    private static final long serialVersionUID = 1L;
    @DatabaseField
    private String nome;
    @DatabaseField(id=true)
    private String uf;

    public Estado()
    {
    }

    public Estado(String uf, String nome)
    {
        this();
        this.uf = uf;
        this.nome = nome;
    }

    public String getNome()
    {
        return nome;
    }

    public String getUf()
    {
        return uf;
    }

    public void setNome(String s)
    {
        nome = s;
    }

    public void setUf(String s)
    {
        uf = s;
    }

    public String toString()
    {
        return nome;
    }
    
    @Override
    public boolean equals(Object o) {
    	if( o instanceof Estado ){
    		Estado estado = (Estado) o;
    		return this.uf.equalsIgnoreCase(estado.uf);
    	} else {
    		return false;
    	}
    }
    
    @Override
    public int hashCode() {
    	return ( this.uf.hashCode() * 32 ) >> 4;
    }
}
