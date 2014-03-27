package com.pmrodrigues.android.allinshopping.models;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Configuracao implements Serializable{

	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId=true)
	private Long id; // NOPMD
	
	@DatabaseField(canBeNull=true)
	private String nomeLoja; // NOPMD

	public Long getId() {
		return id;
	}

	public void setId(final Long id) { // NOPMD
		this.id = id;
	}

	public String getNomeLoja() {
		return nomeLoja;
	}

	public void setNomeLoja(final String nomeLoja) {
		this.nomeLoja = nomeLoja;
	}
	
	
}
