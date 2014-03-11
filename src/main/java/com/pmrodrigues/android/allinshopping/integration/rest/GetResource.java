package com.pmrodrigues.android.allinshopping.integration.rest;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.exceptions.InternalServerException;
import com.pmrodrigues.android.allinshopping.exceptions.PageNotFoundException;

public class GetResource extends Resource
{

    private final HttpClient CLIENT;
    private final HttpGet GET;
    private final String URL;

    public GetResource(String URL)
    {
        CLIENT = new DefaultHttpClient();
        this.URL = URL;
        GET = new HttpGet(URL);
    }

    public String getJSON()
        throws IntegrationException
    {
        HttpResponse httpresponse;
        try
        {
            httpresponse = CLIENT.execute(GET);
            if (httpresponse.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity httpentity = httpresponse.getEntity();
                if (httpentity != null)
                {   
                    return toString(httpentity.getContent());
                } else
                {
                    throw new IntegrationException(String.format("Ocorreu um erro no processamento no endereço %s", URL));
                }
            } else if (httpresponse.getStatusLine().getStatusCode() == 404){
                throw new PageNotFoundException(String.format("endereço %s não encontrado ou errado", URL));
            } else if (httpresponse.getStatusLine().getStatusCode() == 500)
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
        }
        
        
    }
}
