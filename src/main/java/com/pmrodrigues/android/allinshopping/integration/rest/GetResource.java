package com.pmrodrigues.android.allinshopping.integration.rest;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.exceptions.InternalServerException;
import com.pmrodrigues.android.allinshopping.exceptions.PageNotFoundException;

public class GetResource extends Resource
{

    private final HttpClient CLIENT;
    private final HttpGet GET;
    private final String URL;

	public GetResource(final String URL)
    {
		super();
        CLIENT = new DefaultHttpClient();
        this.URL = URL;
        GET = new HttpGet(URL);
    }

	public JSONObject getJSON()
        throws IntegrationException
    {
        HttpResponse httpresponse;
        try
        {
            httpresponse = CLIENT.execute(GET);
			if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
				final HttpEntity httpentity = httpresponse.getEntity();
				if (httpentity == null)
                {   
					throw new IntegrationException(String.format(
							"Ocorreu um erro no processamento no endereço %s",
							URL));
				} else {
					return new JSONObject(toString(httpentity.getContent()));

                }
			} else if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                throw new PageNotFoundException(String.format("endereço %s não encontrado ou errado", URL));
			} else if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR)
            {
                throw new InternalServerException(String.format("Ocorreu um erro no processamento no endereço %s", URL));
            } else {
            	throw new IntegrationException(String.format("Ocorreu um erro não documentado no endereço %s", URL));
            }
        }
        catch (ClientProtocolException e)
        {
            throw new IntegrationException(e.getMessage(), e);
        }
        catch (IllegalStateException e)
        {
        	throw new IntegrationException(e.getMessage(), e);
        }
        catch (IOException e)
        {
        	throw new IntegrationException(e.getMessage(), e);
		} catch (JSONException e) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor "
							+ e.getMessage(), e);
		}
        
        
    }
}
