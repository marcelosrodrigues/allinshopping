package test.com.pmrodrigues.android.allinshopping.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import com.pmrodrigues.android.allinshopping.ClienteActivity;
import com.pmrodrigues.android.allinshopping.MainActivity;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import com.pmrodrigues.android.allinshopping.exceptions.NoUniqueRegistryException;
import com.pmrodrigues.android.allinshopping.models.Cliente;
import com.pmrodrigues.android.allinshopping.models.Endereco;
import com.pmrodrigues.android.allinshopping.models.Estado;
import com.pmrodrigues.android.allinshopping.repository.ClienteRepository;
import com.pmrodrigues.android.allinshopping.services.ClienteService;

@RunWith(RobolectricTestRunner.class)
public class TestClienteService {
	
	private ClienteService service;
	
	private ClienteRepository repository;

    private MainActivity activity;
	
	@Before
	public void setup() {
        activity = Robolectric.buildActivity(MainActivity.class).create().get();
		service = new ClienteService(activity);
		repository = new ClienteRepository(activity);
	}

	private Cliente create() {
		Endereco endereco = new Endereco();
		endereco.setBairro("teste");
		endereco.setCelular("12345678");
		endereco.setCep("22745310");
		endereco.setCidade("Rio de Janeiro");
		Estado estado = new Estado();
		estado.setUf("RJ");
		estado.setNome("Rio de Janeiro");
		estado.setId(330);
		endereco.setEstado(estado);
		endereco.setLogradouro("teste");
		endereco.setTelefone("12345678");
		
		Cliente cliente = new Cliente();
		cliente.setEndereco(endereco);
		cliente.setPrimeiroNome("Marcelo");
        cliente.setUltimoNome("Marcelo");
		cliente.setDataNascimento(new DateTime(1977, 8, 17, 0 , 0).toDate());
		cliente.setEmail("marcelosrodrigues@globo.com");
		
		return cliente;
	}
	
	@Test
	public void testSave() throws NoUniqueRegistryException {

		Cliente cliente = this.create();
		Endereco endereco = cliente.getEndereco();
		
		service.save(cliente);
		
		assertNotNull(cliente.getId());
		assertNotSame(0L , cliente.getId());
		assertNotSame(0L, endereco.getId());
		assertNotNull(endereco.getId());		
	}
	
	@Test
	public void testUpdate() throws NoUniqueRegistryException {
		
		Cliente cliente = this.create();
				
		service.save(cliente);
		
		Cliente toupdate = repository.getById(cliente.getId());
		toupdate.setPrimeiroNome("Marcelo");
        toupdate.setUltimoNome("Rodrigues");
		toupdate.getEndereco().setLogradouro("teste teste");
		service.save(toupdate);
		
		Cliente updated = repository.getById(cliente.getId());
		assertFalse(cliente.getNomeCompleto().equalsIgnoreCase(updated.getNomeCompleto()));
		assertFalse(cliente.getEndereco().getLogradouro().equalsIgnoreCase(updated.getEndereco().getLogradouro()));
		
		
	}

}
