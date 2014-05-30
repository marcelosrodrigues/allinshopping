package com.pmrodrigues.android.allinshopping.integration.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.exceptions.InternalServerException;
import com.pmrodrigues.android.allinshopping.exceptions.PageNotFoundException;

public class PostResource extends Resource {
	private final HttpPost POST;

	public PostResource(final String URL, final String username,
			final String password) {
		super(URL, username, password);
		this.POST = new HttpPost(URL);
	}

	public String sendJSON(final JSONObject jsonobject)
			throws IntegrationException {
		 
		try {
			final String json = jsonobject.toString();
			final StringEntity stringentity = new StringEntity(json);
			final BasicHeader basicheader = new BasicHeader("Content-Type",
					"application/json");
			stringentity.setContentType(basicheader);
			POST.setEntity(stringentity);
			final HttpResponse httpresponse = this.getHttpClient().execute(POST);

			if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				final HttpEntity httpentity = httpresponse.getEntity();
				if (httpentity == null) {
					throw new IntegrationException(String.format(
							"Ocorreu um erro no processamento no endereço %s",
							super.getURL()));

				} else {
					return EntityUtils.toString(httpentity);
				}
			} else if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
				throw new PageNotFoundException(String.format(
						"endereço %s não encontrado ou errado", super.getURL()));

			} else if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
				throw new InternalServerException(String.format(
						"Ocorreu um erro no processamento no endereço %s",
						super.getURL()));
			} else if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
				throw new PageNotFoundException("Acesso negado");
			} else {
				throw new IntegrationException(String.format(
						"Ocorreu um erro não documentado no endereço %s",
						super.getURL()));
			}
		} catch (UnsupportedEncodingException e) {
			throw new IntegrationException(
					"Ocorreu um erro no envio para o backoffice " // NOPMD
							+ e.getMessage(), e); // NOPMD
		} catch (ClientProtocolException e) {
			throw new IntegrationException(
					"Ocorreu um erro no envio para o backoffice "
							+ e.getMessage(), e);// NOPMD
		} catch (IllegalStateException e) {
			throw new IntegrationException(
					"Ocorreu um erro no envio para o backoffice "
							+ e.getMessage(), e);// NOPMD
		} catch (IOException e) {
			throw new IntegrationException(
					"Ocorreu um erro no envio para o backoffice "
							+ e.getMessage(), e);// NOPMD
		}

	}
}
