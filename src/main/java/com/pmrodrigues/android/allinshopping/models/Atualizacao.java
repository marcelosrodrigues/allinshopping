package com.pmrodrigues.android.allinshopping.models;

import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Atualizacao implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String DATA_ATUALIZACAO_FIELD = "dataatualizacao"; // NOPMD

	private static final String ID_FIELD = "id";

	@DatabaseField(generatedId=true,columnName=Atualizacao.ID_FIELD)
	private Long id; // NOPMD
	
	@DatabaseField(canBeNull=false,columnName=Atualizacao.DATA_ATUALIZACAO_FIELD)
	private Date dataAtualizacao = new Date();
	
	public Long getId() {
		return id;
	}

	public void setId(final Long id) { // NOPMD
		this.id = id;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(final Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	
	
}
