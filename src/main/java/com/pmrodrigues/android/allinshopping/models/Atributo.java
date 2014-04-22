package com.pmrodrigues.android.allinshopping.models;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Atributo implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String DESCRICAO_FIELD_NAME = "descricao";

	private static final String ID_PRESTASHOP_FIELD_NAME = "atributo_id";

	private static final String PRODUTO_FIELD_NAME = "produto_id";
	
	@DatabaseField(generatedId = true)
	private Long id;
	
	@SerializedName("id")
	@DatabaseField(columnName = Atributo.ID_PRESTASHOP_FIELD_NAME)
	private Long id_atributo;
	
	@SerializedName("descricao")
	@DatabaseField(columnName = Atributo.DESCRICAO_FIELD_NAME)
	private String descricao;
	
	@DatabaseField(columnName = Atributo.PRODUTO_FIELD_NAME, foreign = true, foreignAutoRefresh = true)
	private Produto produto;

	public void setProduto(final Produto produto) {
		this.produto = produto;		
	}
	
}
