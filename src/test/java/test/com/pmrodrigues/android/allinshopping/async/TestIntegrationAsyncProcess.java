package test.com.pmrodrigues.android.allinshopping.async;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.os.AsyncTask;

import com.pmrodrigues.android.allinshopping.async.IntegrationAsyncProcess;

@RunWith(RobolectricTestRunner.class)
public class TestIntegrationAsyncProcess {


	@Before
	public void setup() {

		Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
		Robolectric.getBackgroundScheduler().pause();
		Robolectric.getUiThreadScheduler().pause();
	}

	@Test
	public void testandoImportandoDadosDoServidor() {
		final IntegrationAsyncProcess process = new IntegrationAsyncProcess(
				Robolectric.application.getApplicationContext());

		process.execute();
		Robolectric.runBackgroundTasks();
		Assert.assertEquals(process.getStatus(), AsyncTask.Status.FINISHED);

		
	}

}
