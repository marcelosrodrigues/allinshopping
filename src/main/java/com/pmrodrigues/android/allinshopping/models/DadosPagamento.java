package com.pmrodrigues.android.allinshopping.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.pmrodrigues.android.allinshopping.models.constraints.ValidationConstraint;
import com.pmrodrigues.android.allinshopping.utilities.ParseUtilities;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.validator.GenericValidator;

@DatabaseTable
public class DadosPagamento extends ValidationConstraint
    implements Serializable
{

    private static final long serialVersionUID = 1L;
    
    @DatabaseField
    private String agencia;
    @DatabaseField
    private String banco;
    @DatabaseField
    private String conta;
    @DatabaseField
    private String cvv;
    @DatabaseField
    private String dataValidade;
    
    @DatabaseField(foreign=true)
    private FormaPagamento formaPagamento;
    @DatabaseField(generatedId=true)
    private Long id;
    @DatabaseField
    private String nome;
    @DatabaseField
    private String numeroCartao;
    
    @DatabaseField(foreign=true)
    private Pedido pedido;

    @DatabaseField
	private String cpf;
    
    @DatabaseField
    private Integer quantidadeParcelas = 1;

    public DadosPagamento()
    {
    }

    private void validateCartaoCredito()
    {
        if (GenericValidator.isBlankOrNull(numeroCartao))
        {
            add("Número do cartão de crédito é obrigatório");
        }
        if (!GenericValidator.isBlankOrNull(numeroCartao) && !GenericValidator.isCreditCard(numeroCartao))
        {
            add("Número do cartão de crédito inválido");
        }
        if (GenericValidator.isBlankOrNull(dataValidade))
        {
            add("Data de validade é obrigatória");
        }
        if (!GenericValidator.isDate(dataValidade, "MMyy", true))
        {
            add("Data de validade inválida");
        }
        Date date = ParseUtilities.toDate(dataValidade, "MMyy");
        if (date != null)
        {
            Date date1 = new Date();
            if (date.before(date1))
            {
                add("Cartão de crédito vencido");
            }
        }
        if( GenericValidator.isBlankOrNull(nome) ) {
        	add("Nome do portador do cartão é obrigatório");
        } else if( nome.split(" ").length == 0 ){
        	add("Nome do portador deve ser composto por nome e sobrenome");
        }
        
        
    }

    private void validateContaBancaria()
    {
        if (GenericValidator.isBlankOrNull(banco))
        {
            add("Número do banco é obrigatório");
        }
        if (GenericValidator.isBlankOrNull(agencia))
        {
            add("Agência é obrigatório");
        }
        if (GenericValidator.isBlankOrNull(conta))
        {
            add("Némero da conta é obrigatório");
        }
    }

    public Integer getQtdParcelas() {
    	return this.quantidadeParcelas;    		
    }
    
    public void setQtdParcelas(Integer i) {
    	this.quantidadeParcelas = i;
    }
    
    public void setAgencia(String s)
    {
        agencia = s;
    }

    public void setBanco(String s)
    {
        banco = s;
    }

    public void setCVV(String s)
    {
        cvv = s;
    }

    public void setConta(String s)
    {
        conta = s;
    }

    public void setDataValidade(String s)
    {
        dataValidade = s;
    }

    public void setNome(String s)
    {
        nome = s;
    }

    public void setNumero(String s)
    {
        numeroCartao = s;
    }

    public void setPedido(Pedido pedido)
    {
        this.pedido = pedido;
        FormaPagamento formapagamento = pedido.getFormaPagamento();
        this.formaPagamento = formapagamento;
    }
    
    public Pedido getPedido() {
    	return this.pedido;
    }

    protected void validate()
    {
        if (formaPagamento != null && formaPagamento.isCartao())
        {
            validateCartaoCredito();
        } else
        {
            validateContaBancaria();
        }
    }

	public String getValidade() {
		return this.dataValidade;
	}

	public String getNumero() {
		return numeroCartao;
	}

	public String getNome() {
		return this.nome;
	}

	public String getCVV() {
		return this.cvv;
	}

	public String getConta() {
		return this.conta;
	}
	
	public String getAgencia() {
		return this.agencia;
	}

	public String getCPF() {
		return this.cpf;
	}
	
	public void setCPF(String cpf){
		this.cpf = cpf;
	}
	
	@Override
	public boolean isValid() {
		this.validate();
		return super.isValid();
	}
}
