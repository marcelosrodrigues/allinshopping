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

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.models.Imagem;
import com.pmrodrigues.android.allinshopping.models.Produto;
import com.pmrodrigues.android.allinshopping.utilities.Constante;

public class DownloadResource {

	private CredentialsProvider getCredential() {

		final BasicCredentialsProvider provider = new BasicCredentialsProvider();
		final AuthScope authscope = new AuthScope(AuthScope.ANY_HOST,
				AuthScope.ANY_PORT);
		final UsernamePasswordCredentials credential = new UsernamePasswordCredentials(
				Constante.CREDENTIALS, "");
		provider.setCredentials(authscope, credential);
		return provider;

	}

	public void getResourceByProduto(final Produto produto)
			throws IntegrationException {
		
		for( Imagem imagem : produto.getImagens() ) {
			String imagepath = getResourceByImage(imagem);
			imagem.setFileName(imagepath);
		}
		
		
		
	}

	private String getResourceByImage(final Imagem imagem)
			throws IntegrationException {
		FileOutputStream output = null; // NOPMD
		BufferedInputStream buffer = null; // NOPMD
		String absolutImagePath = null; // NOPMD
		
		
		try {

			final DefaultHttpClient client = new DefaultHttpClient();
			client.setCredentialsProvider(this.getCredential());
			final HttpGet GET = new HttpGet(imagem.getURL());

			final HttpEntity httpentity = client.execute(GET).getEntity();

			if (httpentity != null) {

				buffer = new BufferedInputStream(httpentity.getContent());
				final File directory = getImageDirectort();
				final File image = createImageFile(imagem, directory);

				output = new FileOutputStream(image);
				final ByteArrayBuffer bab = new ByteArrayBuffer(1024);

				int i = 0; // NOPMD
				while ((i = buffer.read()) != -1) { // NOPMD
					bab.append(i);
				}

				final byte b[] = bab.toByteArray(); // NOPMD
				output.write(b);
				output.flush();

				absolutImagePath = image.getAbsoluteFile().getAbsolutePath();

			}
			
			return absolutImagePath;
			
		} catch (ClientProtocolException e) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor " // NOPMD
							+ e.getMessage(), e);
		} catch (IllegalStateException e) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor " // NOPMD
							+ e.getMessage(), e);
		} catch (FileNotFoundException e) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor " // NOPMD
							+ e.getMessage(), e);
		} catch (IOException e) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor " // NOPMD
							+ e.getMessage(), e);
		} finally {
			try {
				if (output != null) {
					output.close();
				}

				if (buffer != null) {
					buffer.close();
				}
			} catch (IOException e) { // NOPMD
				System.out.println(e);
			}
		}
	}

	private File createImageFile(final Imagem imagem, final File directory)
			throws IOException {
		final File image = new File(directory.getAbsolutePath(),
				String.format("%s-%s.png", imagem.getProduto().getId(),imagem.getId()));
		image.createNewFile();
		return image;
	}

	private File getImageDirectort() {
		final File directory = new File(Constante.SDCARD_ALLINSHOPP_IMAGES);

		if (!directory.exists()) {
			directory.mkdirs();
		}
		return directory;
	}

}
