package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;

import com.pmrodrigues.android.allinshopping.models.Produto;
import com.pmrodrigues.android.allinshopping.utilities.Constante;

public class DownloadResource {

	private CredentialsProvider getCredential() {

		BasicCredentialsProvider provider = new BasicCredentialsProvider();
		AuthScope authscope = new AuthScope(AuthScope.ANY_HOST,
				AuthScope.ANY_PORT);
		UsernamePasswordCredentials credential = new UsernamePasswordCredentials(
				Constante.CREDENTIALS, "");
		provider.setCredentials(authscope, credential);
		return provider;

	}

	public String getResourceByProduto(Produto produto) {
		FileOutputStream output = null;
		BufferedInputStream buffer = null;
		try {

			DefaultHttpClient client = new DefaultHttpClient();
			client.setCredentialsProvider(this.getCredential());

			final HttpGet GET = new HttpGet(produto.getImage());
			HttpEntity httpentity = client.execute(GET).getEntity();

			if (httpentity != null) {

				buffer = new BufferedInputStream(httpentity.getContent());
				File directory = new File(Constante.SDCARD_ALLINSHOPP_IMAGES);

				if (!directory.exists()) {
					directory.mkdirs();
				}

				File image = new File(directory.getAbsolutePath(),
						String.format("%s.jpg", produto.getId()));
				image.createNewFile();
				output = new FileOutputStream(image);
				ByteArrayBuffer bab = new ByteArrayBuffer(1024);
				int i = 0;
				while ((i = buffer.read()) != -1) {
					bab.append(i);
				}
				byte b[] = bab.toByteArray();
				output.write(b);
				output.flush();

				return image.getAbsoluteFile().getAbsolutePath();

			}

			return null;
		} catch (ClientProtocolException e) {
			throw new RuntimeException(
					"Ocorreu um erro para converter a resposta do servidor "
							+ e.getMessage(), e);
		} catch (IllegalStateException e) {
			throw new RuntimeException(
					"Ocorreu um erro para converter a resposta do servidor "
							+ e.getMessage(), e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(
					"Ocorreu um erro para converter a resposta do servidor "
							+ e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(
					"Ocorreu um erro para converter a resposta do servidor "
							+ e.getMessage(), e);
		} finally {
			try {
				if (output != null) {
					output.close();
				}

				if (buffer != null) {
					buffer.close();
				}
			} catch (IOException e) {
			}
		}
	}

}
