package com.pmrodrigues.android.allinshopping.models;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.validator.GenericValidator;
import org.json.JSONException;
import org.json.JSONObject;

import com.akatus.connect.api.v1.entity.Address;
import com.akatus.connect.api.v1.entity.Payer;
import com.akatus.connect.api.v1.entity.Phone;
import com.akatus.connect.api.v1.entity.Phone.Type;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.pmrodrigues.android.allinshopping.models.constraints.ValidationConstraint;
import com.pmrodrigues.android.allinshopping.utilities.ParseUtilities;

@DatabaseTable
public class Cliente extends ValidationConstraint
    implements Serializable
{

    private static final long serialVersionUID = 1L;

	private static final String BAIRRO_FIELD_NAME = "bairro";

	private static final String CEP_FIELD_NAME = "cep";

	private static final String CIDADE_FIELD_NAME = "cidade";

	private static final String COMPLEMENTO_FIELD_NAME = "complemento"; // NOPMD

	private static final String DATA_NASCIMENTO_FIELD_NAME = "datanascimento"; // NOPMD

	private static final String EMAIL_FIELD_NAME = "email";

	private static final String ESTADO_FIELD_NAME = "estado";

	private static final String LOGRADOURO_FIELD_NAME = "logradouro"; // NOPMD

	private static final String NOME_FIELD_NAME = "nome";

	private static final String NUMERO_FIELD_NAME = "numero";

	private static final String TELEFONE_FIELD_NAME = "telefone"; // NOPMD

	private static final String CELULAR_FIELD_NAME = "celular"; // NOPMD
    
	@SerializedName("bairro")
	@DatabaseField(columnName = Cliente.BAIRRO_FIELD_NAME)
    private String bairro;
    
	@SerializedName("cep")
	@DatabaseField(columnName = Cliente.CEP_FIELD_NAME)
    private String cep;
    
	@SerializedName("cidade")
	@DatabaseField(columnName = Cliente.CIDADE_FIELD_NAME)
    private String cidade;
    
	@SerializedName("complemento")
	@DatabaseField(columnName = Cliente.COMPLEMENTO_FIELD_NAME)
    private String complemento;

	@SerializedName("nascimento")
	@DatabaseField(columnName = Cliente.DATA_NASCIMENTO_FIELD_NAME)
    private Date dataNascimento;
    
	@SerializedName("email")
	@DatabaseField(columnName = Cliente.EMAIL_FIELD_NAME)
    private String email;
    
	@SerializedName("estado")
	@DatabaseField(columnName = Cliente.ESTADO_FIELD_NAME, foreign = true, foreignAutoRefresh = true)
    private Estado estado;
    
    @DatabaseField(generatedId=true)
	private Long id; // NOPMD
    
	@SerializedName("id_address")
    @DatabaseField
	private Long id_address; // NOPMD
    
	@SerializedName("id_prestashop")
    @DatabaseField
	private Long id_prestashop; // NOPMD
    
	@SerializedName("logradouro")
	@DatabaseField(columnName = Cliente.LOGRADOURO_FIELD_NAME)
    private String logradouro;
    
	@SerializedName("nome")
	@DatabaseField(columnName = Cliente.NOME_FIELD_NAME)
    private String nome;
    
	@SerializedName("numero")
	@DatabaseField(columnName = Cliente.NUMERO_FIELD_NAME)
    private String numero;
    
	@SerializedName("telefone")
	@DatabaseField(columnName = Cliente.TELEFONE_FIELD_NAME)
    private String telefone;
    
	@SerializedName("celular")
	@DatabaseField(columnName = Cliente.CELULAR_FIELD_NAME)
    private String celular;
    
    public Payer toPayer() {
    	
		final Payer payer = new Payer();
    	payer.setName(this.getNome());
    	payer.setEmail(this.getEmail());
    	
		final Address address = payer.newAddress();
    	address.setStreet(this.getLogradouro());
    	if( !GenericValidator.isBlankOrNull(this.getNumero()) && GenericValidator.isInt(this.getNumero()) ){
    		address.setNumber(Integer.parseInt(this.getNumero()));
    	}else{
    		address.setNumber(0);
    	}
        address.setComplement(this.getComplemento());
        address.setCity(this.getCidade());
        address.setNeighbourhood(this.getBairro());
        address.setState(Address.State.valueOf(this.getEstado().getUf()));
        address.setType(Address.Type.SHIPPING);
        address.setZip(this.getCep());
        address.setCountry(Address.Country.BRA);
                
        
        if( !GenericValidator.isBlankOrNull(this.telefone)  && !"{}".equalsIgnoreCase(this.telefone) ) {
			final Phone phone = new Phone();
        	phone.setNumber(this.telefone);
        	phone.setType(Type.RESIDENTIAL);
        	payer.addPhone(phone);
        }
        
        if( !GenericValidator.isBlankOrNull(this.celular) && !"{}".equalsIgnoreCase(this.celular)) {
			final Phone phone = new Phone();
        	phone.setNumber(this.celular);
        	phone.setType(Type.CELLPHONE);
        	payer.addPhone(phone);
        }
        
        return payer;
    }

    public Cliente()
    {
		super();
    }

	public Cliente(String nome, Date dataNascimento, String email,
			Estado estado, String cidade, String bairro,
            String logradouro, String numero, String complemento, String cep)
    {
        this();
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
        validate();
	}

    public String getBairro()
    {
        return bairro;
    }

    public String getCep()
    {
        return cep;
    }

    public String getCidade()
    {
        return cidade;
    }

    public String getComplemento()
    {
        return complemento;
    }

    public Date getDataNascimento()
    {
        return dataNascimento;
    }

    public String getEmail()
    {
        return email;
    }

    public Estado getEstado()
    {
        return estado;
    }

    public Long getId()
    {
        return id;
    }

    public Long getIdAddress()
    {
        return id_address;
    }

    public Long getIdCustomer()
    {
        return id_prestashop;
    }

    public String getLogradouro()
    {
        return logradouro;
    }

    public String getNome()
    {
        return nome;
    }

    public String getNumero()
    {
        return numero;
    }

	public void setEstado(final Estado estado)
    {
		this.estado = estado;
    }

	public void setIdAddress(final Long id_address)
    {
        this.id_address = id_address;
    }

	public void setIdPrestashop(final Long id_prestashop)
    {
        this.id_prestashop = id_prestashop;
    }

    public JSONObject toJSON()
        throws JSONException
    {
		final JSONObject jsonobject = new JSONObject();
        jsonobject.put("nome", getNome());
        jsonobject.put("sobrenome", getNome());
        jsonobject.put("email", getEmail());
        jsonobject.put("nascimento", ParseUtilities.formatDate(getDataNascimento(), "yyyy-MM-dd"));
        jsonobject.put("endereco", getLogradouro() + " " + getNumero() + getComplemento());
        jsonobject.put("estado", getEstado().getUf());
        jsonobject.put("bairro", getBairro());
        jsonobject.put("cep", getCep());
        jsonobject.put("cidade", getCidade());
        jsonobject.put("telefone", getTelefone());
        jsonobject.put("celular", getCelular());
        return jsonobject;
    }

    public String getCelular() {
		return this.celular;
	}

	public String getTelefone() {
		return this.telefone;
	}

	@Override
	protected void validate()
    {
        if (GenericValidator.isBlankOrNull(nome))
        {
			super.add("Nome completo é obrigatório");
        }
        if (dataNascimento == null)
        {
			add("Data de nascimento é obrigatório");
        }
        if (GenericValidator.isBlankOrNull(logradouro))
        {
			add("Endereço é obrigatório");
        }
        if (GenericValidator.isBlankOrNull(bairro))
        {
			add("Bairro é obrigatório");
        }
        if (GenericValidator.isBlankOrNull(cep))
        {
			add("CEP é obrigatório");
        }
        if (estado == null || GenericValidator.isBlankOrNull(estado.getUf()))
        {
			add("Estado é obrigatório");
        }
        if (GenericValidator.isBlankOrNull(cidade))
        {
			add("Cidade é obrigatório");
        }
    }

	public void setTelefone(final String telefone) {
		this.telefone = telefone;		
	}

	public void setCelular(final String celular) {
		this.celular = celular;		
	}

	public void setId(final Long id) {
		this.id = id;
	}
}
