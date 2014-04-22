package com.pmrodrigues.android.allinshopping.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class CEP implements Serializable { // NOPMD

	private static final long serialVersionUID = 1L;

	public static final String FIM_FIELD_NAME = "fim";

	public static final String ID_FIELD_NAME = "id";

	public static final String INICIO_FIELD_NAME = "inicio";

	public static final String UF_FIELD_NAME = "uf";

	@SerializedName("fim")
	@DatabaseField(columnName = CEP.FIM_FIELD_NAME)
	private Long fim;

	@SerializedName("id")
	@DatabaseField(id = true, columnName = CEP.ID_FIELD_NAME)
	private Long id; // NOPMD

	@SerializedName("inicial")
	@DatabaseField(columnName = CEP.INICIO_FIELD_NAME)
	private Long inicio;

	@SerializedName("estado")
	@DatabaseField(columnName = CEP.UF_FIELD_NAME, foreign = true, foreignAutoRefresh = true)
	private Estado estado; // NOPMD
	
	@SerializedName("faixas")
    @ForeignCollectionField(eager=true)
    private final Collection<FaixaPreco> faixas = new ArrayList<FaixaPreco>();

	public Long getFim() {
		return fim;
	}

	public Long getId() {
		return id;
	}

	public Long getInicio() {
		return inicio;
	}

	public Estado getEstado() {
		return estado;
	}

	public Collection<FaixaPreco>  getFaixas() {
		return faixas;
	}
}
