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
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.pmrodrigues.android.allinshopping.models.constraints.ValidationConstraint;
import com.pmrodrigues.android.allinshopping.utilities.ParseUtilities;

@DatabaseTable
public class Cliente extends ValidationConstraint
    implements Serializable
{

    private static final long serialVersionUID = 1L;
    
    @DatabaseField
    private String bairro;
    
    @DatabaseField
    private String cep;
    
    @DatabaseField
    private String cidade;
    
    @DatabaseField
    private String complemento;
        
    @DatabaseField
    private Date dataNascimento;
    
    @DatabaseField
    private String email;
    
    @DatabaseField(foreign=true,foreignAutoRefresh=true)
    private Estado estado;
    
    @DatabaseField(generatedId=true)
    private Long id;
    
    @DatabaseField
    private Long id_address;
    
    @DatabaseField
    private Long id_prestashop;
    
    @DatabaseField
    private String logradouro;
    
    @DatabaseField
    private String nome;
    
    @DatabaseField
    private String numero;
    
    @DatabaseField
    private String telefone;
    
    @DatabaseField
    private String celular;
    
    public Payer toPayer() {
    	
    	Payer payer = new Payer();
    	payer.setName(this.getNome());
    	payer.setEmail(this.getEmail());
    	
    	Address address = payer.newAddress();
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
        	Phone phone = new Phone();
        	phone.setNumber(this.telefone);
        	phone.setType(Type.RESIDENTIAL);
        	payer.addPhone(phone);
        }
        
        if( !GenericValidator.isBlankOrNull(this.celular) && !"{}".equalsIgnoreCase(this.celular)) {
        	Phone phone = new Phone();
        	phone.setNumber(this.celular);
        	phone.setType(Type.CELLPHONE);
        	payer.addPhone(phone);
        }
        
        return payer;
    }

    public Cliente()
    {
    }

    public Cliente(String nome, Date dataNascimento, String email, Estado estado, String cidade, String bairro, 
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

    public void setEstado(Estado estado1)
    {
        estado = estado1;
    }

    public void setIdAddress(Long id_address)
    {
        this.id_address = id_address;
    }

    public void setIdPrestashop(Long id_prestashop)
    {
        this.id_prestashop = id_prestashop;
    }

    public JSONObject toJSON()
        throws JSONException
    {
        JSONObject jsonobject = new JSONObject();
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

	public void setTelefone(String telefone) {
		this.telefone = telefone;		
	}

	public void setCelular(String celular) {
		this.celular = celular;		
	}

	public void setId(Long id) {
		this.id = id;
	}
}
