package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.GetResource;

public abstract class AbstractDownload<E> implements Download<E> {

	private final Class<E> persistentClass;

	private final ResourceBundle bundle = ResourceBundle
			.getBundle("integration");
	
	private final GetResource GET;

	private final String url;

	private final String username;

	private final String password;
	
	@SuppressWarnings("unchecked")
	public AbstractDownload(String username, String password) {
		this.username = username;
		this.password = password;
		final ParameterizedType type = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		this.persistentClass = (Class<E>) type.getActualTypeArguments()[0];
		this.url = bundle.getString(this.persistentClass.getSimpleName()
				.toLowerCase()); 
		GET = new GetResource(url,username,password);
	}

	protected List<E> toList(final Object json) {
		final Gson gson = new GsonBuilder().create();
		return this.toList(gson, json);
	}

	protected String getURL() {
		return this.url;
	}

	@Override
	public List<E> list() throws IntegrationException {

		try {
			final JSONObject json = GET.getJSON();

			return toList(json.get("list"));

		} catch (JSONException e) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor "
							+ e.getMessage(), e);
		}

	}

	@SuppressWarnings("unchecked")
	protected List<E> toList(final Gson gson, final Object json) {
		List<E> elements = null; 
		if (json instanceof JSONArray) {
			elements = Arrays.asList((E[]) gson.fromJson(json.toString(), Array
					.newInstance(persistentClass, 0).getClass()));
		} else {
			elements = new ArrayList<E>();
			elements.add(gson.fromJson(json.toString(), persistentClass));
		}
		return elements;
	}

	public String getUserName() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
}
