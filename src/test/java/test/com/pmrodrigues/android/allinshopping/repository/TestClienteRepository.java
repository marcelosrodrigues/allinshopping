package test.com.pmrodrigues.android.allinshopping.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.pmrodrigues.android.allinshopping.repository.ClienteRepository;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class TestClienteRepository {

	private ClienteRepository repository;

	@Before
	public void setup() {
		repository = new ClienteRepository(
				Robolectric.application.getApplicationContext());
	}

	@Test
	public void test() {
		repository.list();
	}

}
