package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.io.*;

import android.graphics.BitmapFactory;
import android.util.Log;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;

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

	public void getResourceByProduto(final Produto produto) //NOPMD
			throws IntegrationException {
		
		for( final Imagem imagem : produto.getImagens() ) {
			final String imagepath = getResourceByImage(imagem);
			imagem.setFileName(imagepath);
		}
		
		
		
	}

	private String getResourceByImage(final Imagem imagem)
			throws IntegrationException {
		
		String absolutImagePath = null;
        FileOutputStream output = null; // NOPMD
        BufferedInputStream buffer = null; // NOPMD

		try {

			final DefaultHttpClient client = new DefaultHttpClient();
			client.setCredentialsProvider(this.getCredential());
			final HttpGet GET = new HttpGet(imagem.getURL());

			final HttpEntity httpentity = client.execute(GET).getEntity();

			if (httpentity != null) {

                buffer = new BufferedInputStream(httpentity.getContent());
				final File image = new File(Constante.SDCARD_ALLINSHOPP_IMAGES , String.format("%s-%s.bmp", imagem.getProduto().getId(),imagem.getId()));
                final File directory = new File(Constante.SDCARD_ALLINSHOPP_IMAGES);

                if( !directory.exists() ){
                    directory.mkdirs();
                }

                if(image.exists()){
                    image.delete();
                }
                image.createNewFile();

                output = new FileOutputStream(image);

                int i = 0; // NOPMD
                byte[] readed = new byte[1024];
                while ((i = buffer.read(readed)) != -1 ) { // NOPMD
                    output.write(readed,0,i);
                    output.flush();
                }

				absolutImagePath = image.getAbsoluteFile().getAbsolutePath();

			}
			
			return absolutImagePath;
			
		} catch (ClientProtocolException e) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor " 
							+ e.getMessage(), e);
		} catch (IllegalStateException e) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor " 
							+ e.getMessage(), e);
		} catch (FileNotFoundException e) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor " 
							+ e.getMessage(), e);
		} catch (IOException e) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor " 
							+ e.getMessage(), e);
		} finally {

                try {

                    if( buffer != null ) {
                        buffer.close();
                    }
                    if(output != null ){
                        output.close();
                    }
                } catch (IOException e) {
                    Log.w("com.pmrodrigues.android.allinshopping.integration.downloads",e.getMessage());
                }
        }
	}
}
