package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;
import com.pmrodrigues.android.allinshopping.models.Produto;
import com.pmrodrigues.android.allinshopping.utilities.Constante;

public class DownloadProdutos
    implements Download
{
	private final String LIST_PRODUTOS = "http://store.allinshopp.com.br/custom/list_produtos.php";
    private final String CREDENTIALS = "KIGDS4I9RGFKHW48673LEOLYHMH5S0EN";

    public DownloadProdutos()
    {
    }

    private String getImage(Produto produto, String URL)
    {
    	FileOutputStream output = null;
    	BufferedInputStream buffer = null;
        try {
			BasicCredentialsProvider basiccredentialsprovider = new BasicCredentialsProvider();
			AuthScope authscope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT);
			UsernamePasswordCredentials usernamepasswordcredentials = new UsernamePasswordCredentials(CREDENTIALS, "");        
			basiccredentialsprovider.setCredentials(authscope, usernamepasswordcredentials);
			DefaultHttpClient defaulthttpclient = new DefaultHttpClient();
			defaulthttpclient.setCredentialsProvider(basiccredentialsprovider);
			
			
			HttpGet httpget = new HttpGet(URL);
			HttpEntity httpentity = defaulthttpclient.execute(httpget).getEntity();
			
			if (httpentity != null)  {
				buffer = new BufferedInputStream(httpentity.getContent()); 
				File directory = new File(Constante.SDCARD_ALLINSHOPP_IMAGES);
				
				if( !directory.exists() ) {
					directory.mkdirs();
				}
				
				String imageName = String.format("%s.jpg",produto.getId());
				File image = new File(directory.getAbsolutePath(),imageName);
				image.createNewFile();
				output = new FileOutputStream(image);
				ByteArrayBuffer bab = new ByteArrayBuffer(1024);
				int i = 0;
				while(( i = buffer.read() )!= -1){
					bab.append(i);
				}
				byte abyte0[] = bab.toByteArray();
				output.write(abyte0);
				output.flush();
				
				return image.getAbsoluteFile().getAbsolutePath();
				
			}
			
			return null;
		} catch (ClientProtocolException e) {
			throw new RuntimeException("Ocorreu um erro para converter a resposta do servidor " + e.getMessage(), e);
		} catch (IllegalStateException e) {
			throw new RuntimeException("Ocorreu um erro para converter a resposta do servidor " + e.getMessage(), e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Ocorreu um erro para converter a resposta do servidor " + e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException("Ocorreu um erro para converter a resposta do servidor " + e.getMessage(), e);
		} finally {
			try {
				if( output != null ){
					output.close();
				}
				
				if( buffer != null ) {
					buffer.close();
				}
			} catch (IOException e) {}
		}
    }

    @Override
	public List<Produto> getAll()
        throws IntegrationException
    {
    	
    	 try {
 			String json = new GetResource(LIST_PRODUTOS).getJSON();
 			JSONObject jsonobject = new JSONObject(json);
 			List<Produto> produtos = new ArrayList<Produto>();
 			JSONArray jsonarray = jsonobject.getJSONObject("produtos").getJSONArray("produto");
 			for(int i = 0 , j = jsonarray.length() ; i < j ; i++ ) {
 			
 			    Long id = jsonarray.getJSONObject(i).getLong("id_product");
 		        String nome = jsonarray.getJSONObject(i).getString("name");
 		        BigDecimal preco = new BigDecimal(jsonarray.getJSONObject(i).getDouble("price")); 		        
 		        Long idShop = jsonarray.getJSONObject(i).getLong("id_shop"); 		        
 		        Long idCategoria = jsonarray.getJSONObject(i).getLong("id_category");
 		        Produto produto = new Produto(id, nome, preco, idShop, idCategoria);
 		        produto.setDescricao(jsonarray.getJSONObject(i).getString("description"));
 		        produto.setDescricaoBreve(jsonarray.getJSONObject(i).getString("description_short"));
 		        String image = jsonarray.getJSONObject(i).getString("image");
 		        produto.setImage(getImage(produto, image));
 		        if( !produtos.contains(produto) ) {
 		        	produtos.add(produto);
 		        }
 		        
 				
 			}
 			
 			return produtos;
 		} catch (JSONException e) {
 			throw new IntegrationException("Ocorreu um erro para converter a resposta do servidor " + e.getMessage(), e);
 		}
    	
    }

    @Override
	public List<Produto> getBy(Serializable serializable)
    {
        return null;
    }
}
