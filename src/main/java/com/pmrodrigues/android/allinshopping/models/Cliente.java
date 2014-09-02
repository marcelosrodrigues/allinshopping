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

	public static final String DATA_NASCIMENTO_FIELD_NAME = "datanascimento"; 

	public static final String EMAIL_FIELD_NAME = "email";
	
	public static final String NOME_FIELD_NAME = "nome";

	public static final String ENDERECO_FIELD_NAME = "endereco";

	public static final String BACKOFFICE_ID_FIELD_NAME = "backoffice_id";

	public static final String ID_FIELD_NAME = "id";   


	@SerializedName("dataNascimento")
	@DatabaseField(columnName = Cliente.DATA_NASCIMENTO_FIELD_NAME)
    private Date dataNascimento;
    
	@SerializedName("email")
	@DatabaseField(columnName = Cliente.EMAIL_FIELD_NAME)
    private String email;
    
    @DatabaseField(columnName = Cliente.ID_FIELD_NAME ,  generatedId=true)
	private Long internalId; 
    
    @SerializedName("id")
    @DatabaseField(columnName = Cliente.BACKOFFICE_ID_FIELD_NAME)
    private Long backofficeId;
    
	@SerializedName("nome")
	@DatabaseField(columnName = Cliente.NOME_FIELD_NAME)
    private String nome;
	
	@SerializedName("endereco")
	@DatabaseField(columnName = Cliente.ENDERECO_FIELD_NAME,foreign = true,foreignAutoCreate = true,foreignAutoRefresh = true)
	private Endereco endereco;
    
    public Payer toPayer() {
    	
		final Payer payer = new Payer();
    	payer.setName(this.getNomeCompleto());
    	payer.setEmail(this.getEmail());
    	
		final Address address = payer.newAddress();
    	address.setStreet(endereco.getLogradouro());
    	address.setNumber(0);
        address.setComplement("");
        address.setCity(endereco.getCidade());
        address.setNeighbourhood(endereco.getBairro());
        address.setState(Address.State.valueOf(endereco.getEstado().getUf()));
        address.setType(Address.Type.SHIPPING);
        address.setZip(endereco.getCep());
        address.setCountry(Address.Country.BRA);
                
        
        if( !GenericValidator.isBlankOrNull(endereco.getTelefone())  && !"{}".equalsIgnoreCase(endereco.getTelefone()) ) {
			final Phone phone = new Phone();
        	phone.setNumber(endereco.getTelefone());
        	phone.setType(Type.RESIDENTIAL);
        	payer.addPhone(phone);
        }
        
        if( !GenericValidator.isBlankOrNull(endereco.getCelular()) && !"{}".equalsIgnoreCase(endereco.getCelular())) {
			final Phone phone = new Phone();
        	phone.setNumber(endereco.getCelular());
        	phone.setType(Type.CELLPHONE);
        	payer.addPhone(phone);
        }
        
        return payer;
    }

    public Cliente()
    {
		super();
    }

    public Date getDataNascimento()
    {
        return dataNascimento;
    }

    public String getEmail()
    {
        return email;
    }

    public Long getId()
    {
        return internalId;
    }


    public String getNomeCompleto()
    {
        return nome;
    }

    public JSONObject toJSON()
        throws JSONException
    {
		final JSONObject jsonobject = new JSONObject();
        jsonobject.put("nome", getNomeCompleto());
        jsonobject.put("sobrenome", getNomeCompleto());
        jsonobject.put("email", getEmail());
        jsonobject.put("nascimento", ParseUtilities.formatDate(getDataNascimento(), "yyyy-MM-dd"));
        jsonobject.put("endereco", endereco.getLogradouro());
        jsonobject.put("estado", endereco.getEstado());
        jsonobject.put("bairro", endereco.getBairro());
        jsonobject.put("cep", endereco.getCep());
        jsonobject.put("cidade", endereco.getCidade());
        jsonobject.put("telefone", endereco.getTelefone());
        jsonobject.put("celular", endereco.getCelular());
        return jsonobject;
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
       
    }

	public void setId(final Long id) {
		this.internalId = id;
	}

	public Endereco getEndereco() {
		return this.endereco;
	}

	public void setEndereco(final Endereco endereco) {
		this.endereco = endereco;
		
	}

	public void setNomeCompleto(final String nome) {
		this.nome = nome;
		
	}

	public void setDataNascimento(final Date dataNascimento) {
		this.dataNascimento = dataNascimento;		
	}

	public void setEmail(final String email) {
		this.email = email;
	}
}
