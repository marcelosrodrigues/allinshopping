package com.pmrodrigues.android.allinshopping.integration.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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

    public PostResource(String URL)
    {
    	this.CLIENT = new DefaultHttpClient();
    	this.URL = URL;
    	this.POST = new HttpPost(URL);
    }

    public String sendJSON(JSONObject jsonobject)
        throws IntegrationException
    {
        HttpResponse httpresponse = null ;
        try
        {
            String s = jsonobject.toString();
            StringEntity stringentity = new StringEntity(s);
            BasicHeader basicheader = new BasicHeader("Content-Type", "application/json");
            stringentity.setContentType(basicheader);
            POST.setEntity(stringentity);
            httpresponse = CLIENT.execute(POST);
            
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
            } else if (httpresponse.getStatusLine().getStatusCode() == 404)
            {
                throw new PageNotFoundException(String.format("endereço %s não encontrado ou errado", URL));
                
            } else if (httpresponse.getStatusLine().getStatusCode() == 500)
            {
                throw new InternalServerException(String.format("Ocorreu um erro no processamento no endereço %s", URL));
            } else {
	            Object aobj3[] = new Object[1];
	            String s13 = URL;
	            aobj3[0] = s13;
	            String s14 = String.format("Ocorreu um erro não documentado no endereço %s", aobj3);
	            throw new IntegrationException(s14);
            }
        }
        catch (UnsupportedEncodingException e)
        {
            throw new IntegrationException("Ocorreu um erro no envio para o backoffice "+ e.getMessage(),e);
        }
        catch (ClientProtocolException e)
        {
        	throw new IntegrationException("Ocorreu um erro no envio para o backoffice "+ e.getMessage(),e);
        }
        catch (IllegalStateException e)
        {
        	throw new IntegrationException("Ocorreu um erro no envio para o backoffice "+ e.getMessage(),e);
        }
        catch (IOException e)
        {
        	throw new IntegrationException("Ocorreu um erro no envio para o backoffice "+ e.getMessage(),e);
        }
        
    }
}
