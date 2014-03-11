package com.pmrodrigues.android.allinshopping;

import java.util.Date;
import java.util.List;

import org.apache.commons.validator.GenericValidator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.androidquery.AQuery;
import com.pmrodrigues.android.allinshopping.alerts.ErrorAlert;
import com.pmrodrigues.android.allinshopping.events.FormatarDataNascimentoLostFocus;
import com.pmrodrigues.android.allinshopping.exceptions.NoUniqueRegistryException;
import com.pmrodrigues.android.allinshopping.models.Cliente;
import com.pmrodrigues.android.allinshopping.models.Estado;
import com.pmrodrigues.android.allinshopping.models.Pedido;
import com.pmrodrigues.android.allinshopping.repository.EstadoRepository;
import com.pmrodrigues.android.allinshopping.services.ClienteService;
import com.pmrodrigues.android.allinshopping.utilities.Constante;
import com.pmrodrigues.android.allinshopping.utilities.ParseUtilities;


public class ClienteActivity extends AbstractActivity
		implements
			OnClickListener
{
    private AQuery aq;
	private Long id = 0L;

    public ClienteActivity()
    {
    }
    
    private void loadFromCliente(Cliente cliente) {
    	
    	this.setId(cliente.getId());
    	this.setNome(cliente.getNome());
    	this.setDataNascimento(cliente.getDataNascimento());
    	this.setEmail(cliente.getEmail());
    	this.setCidade(cliente.getCidade());
    	this.setBairro(cliente.getBairro());
    	this.setEndereco(cliente.getLogradouro());
    	this.setCep(cliente.getCep());    	
    	setEstado(cliente.getEstado());
    	this.setTelefone(cliente.getTelefone());
    	this.setCelular(cliente.getCelular());
    	
    }

	public void setCelular(String celular) {
		aq.id(R.id.celular).text(celular);
	}

	public void setTelefone(String telefone) {
		aq.id(R.id.telefone).text(telefone);	
	}

	private void setId(Long id) {
		this.id = id;		
	}

	public void setCep(String cep) {
		aq.id(R.id.cep).text(cep);
	}


	public void setEndereco(String endereco) {
		aq.id(R.id.endereco).text(endereco);
	}

	public void setBairro(String bairro) {
		aq.id(R.id.bairro).text(bairro);
	}

	public void setCidade(String cidade) {
		aq.id(R.id.cidade).text(cidade);
	}

	public void setEmail(String email) {
		aq.id(R.id.email).text(email);
	}

	public void setDataNascimento(Date dataNascimento) {
		aq.id(R.id.dataNascimento).text(ParseUtilities.formatDate(dataNascimento, Constante.DATE_LONG_FORMAT));
	}
    
    public void setNome(String nome) {
    	aq.id(R.id.nomeCompleto).text(nome);
    }
    
    public String getNome() {
    	return aq.id(R.id.nomeCompleto).getText().toString();
    }

	@SuppressWarnings("unchecked")
	public void setEstado(Estado estado) {
		Spinner estados = aq.id(R.id.estados).getSpinner();
    	ArrayAdapter<Estado> adapter = (ArrayAdapter<Estado>) estados.getAdapter();
    	int position = adapter.getPosition(estado);
    	estados.setSelection(position);
	}
	
	public Estado getEstado() {
		return (Estado)aq.id(R.id.estados).getSelectedItem();
	}

    private Cliente createCliente()
    {   
        String nome = this.getNome();
        Date dataNascimento = getDataNascimento();
        String email = getEmail();
        Estado estado = this.getEstado();
        String cidade = getCidade();
        String bairro = getBairro();
        String endereco = getEndereco();
        String cep = getCep();
        String telefone = getTelefone();
        String celular = getCelular();
        Cliente cliente =  new Cliente(nome, dataNascimento, email, estado, cidade, bairro, endereco, null, null , cep );
        cliente.setId(this.id);
        cliente.setTelefone(telefone);
        cliente.setCelular(celular);
        return cliente;
        
     }

	public String getCelular() {
		return aq.id(R.id.celular).getText().toString();
	}

	public String getTelefone() {
		return aq.id(R.id.telefone).getText().toString();
	}

	public String getCep() {
		return aq.id(R.id.cep).getText().toString();
	}

	public String getEndereco() {
		return aq.id(R.id.endereco).getText().toString();
	}

	public String getBairro() {
		return aq.id(R.id.bairro).getText().toString();
	}

	public String getCidade() {
		return aq.id(R.id.cidade).getText().toString();
	}

	public String getEmail() {
		return aq.id(R.id.email).getText().toString();
	}

	public Date getDataNascimento() {
		return ParseUtilities.toDate(aq.id(R.id.dataNascimento).getText().toString(), Constante.DATE_LONG_FORMAT);
	}

    private void salvar()
    {
       
            try {
				Pedido pedido = (Pedido)getIntent().getExtras().get(Constante.PEDIDO);
				
				ClienteService clienteservice = new ClienteService(this);
				String dataNascimento = aq.id(R.id.dataNascimento).getText().toString();
				
				if (!GenericValidator.isDate(dataNascimento, Constante.DATE_LONG_FORMAT,true)){
				    (new ErrorAlert(this))
						.setTitle("Não foi possível salvar o cliente.")
				    	.setCancelable(true)
						.setMessage(
								"Não foi possível salvar o cliente. A data de nascimento está errada")
				    	.show();
				} else
				{
					Cliente cliente = createCliente();
				    if (!cliente.isValid())
				    {
					(new ErrorAlert(this))
							.setTitle("Não foi possível salvar o cliente.")
				    						 .setCancelable(true)
				    						 .setMessages(cliente.errors())
				    						 .show();
				    } else {
				    	clienteservice.save(cliente);
				        pedido.setCliente(cliente);
				        Intent intent = new Intent(this, PagamentoActivity.class);
				        intent.putExtra(Constante.PEDIDO, pedido);
				        startActivity(intent);
				    }
				    
				}
			} catch (NoUniqueRegistryException e) {
			(new ErrorAlert(this))
					.setTitle("Não foi possível salvar o cliente.")
									  .setCancelable(true)
									  .setMessage(e.getMessage())
									  .show();
			}
        
    }

    @Override
	public void onClick(View view)
    {
        if( view.getId() == R.id.salvar ) {
        	salvar();
        } else {
        	Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
	protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_cliente_main);
        aq = new AQuery(this);
        Spinner spinner = aq.id(R.id.estados).getSpinner();
		List<Estado> estados = new EstadoRepository(this).getAll();
        estados.add(0, new Estado("","Selecione um Estado"));
        ArrayAdapter<Estado> arrayadapter = new ArrayAdapter<Estado>(this,  android.R.layout.simple_list_item_1, estados);
        spinner.setAdapter(arrayadapter);
        aq.id(R.id.salvar).clicked(this);
        aq.id(R.id.cancelar).clicked(this);
        aq.id(R.id.dataNascimento).getEditText().setOnFocusChangeListener(new FormatarDataNascimentoLostFocus());
        
        Cliente cliente = (Cliente) getIntent().getExtras().get(Constante.CLIENTE);
		
		if( cliente != null ) {
			loadFromCliente(cliente);
		}
    }
}
