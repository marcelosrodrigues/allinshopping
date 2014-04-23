package com.pmrodrigues.android.allinshopping.models;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable
public class Imagem implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final String URL_FIELD_NAME = "url";

	private static final String FILENAME_FIELD_NAME = "filename";

	private static final String PRODUTO_FIELD_NAME = "produto_id";

	@SerializedName("id")
	@DatabaseField(id = true)
	private Long id;
	
	@SerializedName("url")
	@DatabaseField(columnName = Imagem.URL_FIELD_NAME)
	private String url;
	
	@DatabaseField(columnName = Imagem.FILENAME_FIELD_NAME)
	private final String fileName = null;
	
	@DatabaseField(columnName = Imagem.PRODUTO_FIELD_NAME, foreign = true, foreignAutoRefresh = true)
	private Produto produto;

	public void setProduto(final Produto produto) {
		this.produto = produto;
	}
}