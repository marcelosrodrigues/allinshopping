package com.pmrodrigues.android.allinshopping.integration.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.exceptions.InternalServerException;
import com.pmrodrigues.android.allinshopping.exceptions.PageNotFoundException;

public class PostResource extends Resource
{

    private final HttpClient CLIENT;
    private final HttpPost POST;
    private final String URL;

	public PostResource(final String URL)
    {
		super();
    	this.CLIENT = new DefaultHttpClient();
    	this.URL = URL;
    	this.POST = new HttpPost(URL);
    }

	public String sendJSON(final JSONObject jsonobject)
        throws IntegrationException
    {
		HttpResponse httpresponse = null; // NOPMD
        try
        {
			final String json = jsonobject.toString();
			final StringEntity stringentity = new StringEntity(json);
			final BasicHeader basicheader = new BasicHeader("Content-Type",
					"application/json");
            stringentity.setContentType(basicheader);
            POST.setEntity(stringentity);
            httpresponse = CLIENT.execute(POST);
            
			if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
				final HttpEntity httpentity = httpresponse.getEntity();
				if (httpentity == null)
                {   
					throw new IntegrationException(String.format(
							"Ocorreu um erro no processamento no endereço %s",
							URL));

                } else
                {
					return toString(httpentity.getContent());
                }
			} else if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND)
            {
                throw new PageNotFoundException(String.format("endereço %s não encontrado ou errado", URL));
                
			} else if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR)
            {
                throw new InternalServerException(String.format("Ocorreu um erro no processamento no endereço %s", URL));
            } else {
				throw new IntegrationException(String.format(
						"Ocorreu um erro não documentado no endereço %s", URL));
            }
        }
        catch (UnsupportedEncodingException e)
        {
			throw new IntegrationException(
					"Ocorreu um erro no envio para o backoffice " // NOPMD
							+ e.getMessage(), e); // NOPMD
        }
        catch (ClientProtocolException e)
        {
			throw new IntegrationException(
					"Ocorreu um erro no envio para o backoffice "
							+ e.getMessage(), e);// NOPMD
        }
        catch (IllegalStateException e)
        {
			throw new IntegrationException(
					"Ocorreu um erro no envio para o backoffice "
							+ e.getMessage(), e);// NOPMD
        }
        catch (IOException e)
        {
			throw new IntegrationException(
					"Ocorreu um erro no envio para o backoffice "
							+ e.getMessage(), e);// NOPMD
        }
        
    }
}
