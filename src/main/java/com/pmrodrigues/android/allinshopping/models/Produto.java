package com.pmrodrigues.android.allinshopping.models;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.pmrodrigues.android.allinshopping.utilities.Constante;

@DatabaseTable
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String ID_CATEGORIA_PRESTASHOP_FIELD_NAME = "idcategoria_prestashop"; // NOPMD
	private static final String ID_LOJA_PRESTASHOP_FIELD_NAME = "id_loja"; // NOPMD
	private static final String IMAGE_URL_FIELD_NAME = "imageurl"; // NOPMD
	private static final String NOME_FIELD_NAME = "nome";
	private static final String PRECO_FIELD_NAME = "preco";
	private static final String SECAO_FIELD_NAME = "secao";
	private static final String DESCRICAO_FIELD_NAME = "descricao"; // NOPMD
	private static final String DESCRICAO_BREVE_FIELD_NAME = "descricaobreve"; // NOPMD
	private static final String PESO_FIELD_NAME = "peso";
	private FaixaPreco faixaPreco;

	@SerializedName("id_product")
	@DatabaseField(id = true)
	private Long id; // NOPMD

	@SerializedName("id_category")
	@DatabaseField(columnName = Produto.ID_CATEGORIA_PRESTASHOP_FIELD_NAME)
	private Long idCategoriaPrestashop; // NOPMD

	@SerializedName("id_shop")
	@DatabaseField(columnName = Produto.ID_LOJA_PRESTASHOP_FIELD_NAME)
	private Long idLoja;

	@SerializedName("image")
	@DatabaseField(columnName = Produto.IMAGE_URL_FIELD_NAME)
	private String imageUrl;

	@SerializedName("name")
	@DatabaseField(columnName = Produto.NOME_FIELD_NAME)
	private String nome;

	@SerializedName("price")
	@DatabaseField(columnName = Produto.PRECO_FIELD_NAME)
	private BigDecimal preco;

	@SerializedName("peso")
	@DatabaseField(columnName = Produto.PESO_FIELD_NAME)
	private BigDecimal peso; // NOPMD

	@SerializedName("secao")
	@DatabaseField(columnName = Produto.SECAO_FIELD_NAME, foreign = true, foreignAutoRefresh = true)
	private Secao secao;

	@SerializedName("description")
	@DatabaseField(columnName = Produto.DESCRICAO_FIELD_NAME)
	private String descricao;

	@SerializedName("description_short")
	@DatabaseField(columnName = Produto.DESCRICAO_BREVE_FIELD_NAME)
	private String descricaoCurto;

	public Produto() {
		super();
	}

	public Produto(final Long id, final String nome, final BigDecimal preco,
			final Long idLoja, final Long idCategoriaPrestashop) { // NOPMD
		this();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.idLoja = idLoja;
		this.idCategoriaPrestashop = idCategoriaPrestashop;
	}

	public Produto(final Long id, final String nome, final String imageUrl,
			final BigDecimal preco, final Secao secao) { // NOPMD
		this();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.secao = secao;
		if (imageUrl.endsWith("jpg") || imageUrl.endsWith("gif")
				|| imageUrl.endsWith("png")) {
			this.imageUrl = imageUrl;
		}
	}

	public Produto(String imageUrl, String nome, BigDecimal preco) {
		this();
		this.imageUrl = imageUrl;
		this.nome = nome;
		this.preco = preco;
	}

	@Override
	public boolean equals(Object obj) {
		boolean flag;
		if (obj instanceof Produto) {
			Long long1 = ((Produto) obj).id;
			Long long2 = id;
			flag = long1.equals(long2);
		} else {
			flag = false;
		}
		return flag;
	}

	public Long getId() {
		return id;
	}

	public Long getIdCategoriaPrestashop() {
		return idCategoriaPrestashop;
	}

	public Long getIdLoja() {
		return idLoja;
	}

	public String getImage() {
		return imageUrl;
	}

	public BigDecimal getMargemGestor() {
		return preco
				.divide(Constante.PERCENTUAL_GESTOR, 2, BigDecimal.ROUND_UP)
				.subtract(preco);
	}

	public BigDecimal getMargemLider() {
		return preco.divide(Constante.PERCENTUAL_LIDER, 2, BigDecimal.ROUND_UP)
				.subtract(preco);
	}

	public BigDecimal getMargemTI() {
		return preco.divide(Constante.PERCENTUAL_TI, 2, BigDecimal.ROUND_UP)
				.subtract(preco);
	}

	public BigDecimal getMargemVendedoras() {
		return preco.divide(Constante.PERCENTUAL_VENDEDORAS, 2,
				BigDecimal.ROUND_UP).subtract(preco);
	}

	public BigDecimal getMargemProjetandoo() {
		return preco.divide(Constante.PERCENTUAL_PROJETANDOO, 2,
				BigDecimal.ROUND_UP).subtract(preco);
	}

	public Long getPeso() {
		return Long.valueOf(0L);
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public BigDecimal getPrecoVenda() {
		return preco
				.add(this.getMargemProjetandoo())
				.add(getMargemTI())
				.add(getMargemGestor())
				.add(getMargemVendedoras())
				.add(getMargemLider())
				.add(getFrete())
				.divide(Constante.PERCENTUAL_GATEWAY_PAGAMENTO, 2,
						BigDecimal.ROUND_UP)
				.add(Constante.TAXA_GATEWAY_PAGAMENTO);
	}

	public String getTitulo() {
		return nome;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	public void setFaixaPreco(FaixaPreco faixapreco) {
		faixaPreco = faixapreco;
	}

	public void setId(Long long1) {
		id = long1;
	}

	public void setIdCategoriaPrestashop(Long long1) {
		idCategoriaPrestashop = long1;
	}

	public void setIdLoja(Long long1) {
		idLoja = long1;
	}

	public void setImage(String s) {
		imageUrl = s;
	}

	public void setSecao(Secao secao1) {
		secao = secao1;
	}

	public BigDecimal getFrete() {

		BigDecimal frete = BigDecimal.ZERO;

		if (faixaPreco != null) {
			frete = faixaPreco.getPreco();
		}

		return frete;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setDescricaoBreve(String descricaoCurto) {
		this.descricaoCurto = descricaoCurto;
	}

	public String getDescricaoBreve() {
		return descricaoCurto;
	}

	public String getDescricao() {
		return descricao;
	}

	public Secao getSecao() {
		return secao;
	}

	public boolean apagarImagem() {
		File image = new File(this.imageUrl);
		return image.delete();
	}
}
