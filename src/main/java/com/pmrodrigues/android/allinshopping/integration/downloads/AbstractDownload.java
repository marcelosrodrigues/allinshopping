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

	@SuppressWarnings("unchecked")
	public AbstractDownload() {
		final ParameterizedType type = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		this.persistentClass = (Class<E>) type.getActualTypeArguments()[0];
	}

	protected List<E> toList(final Object json) {
		final Gson gson = new GsonBuilder().create();
		return this.toList(gson, json);
	}

	protected String getURL() {
		return bundle.getString(this.persistentClass.getSimpleName()
				.toLowerCase());
	}

	@Override
	public List<E> getAll() throws IntegrationException {

		try {
			final JSONObject json = new GetResource(this.getURL()).getJSON();

			return toList(json.get("list"));

		} catch (JSONException e) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor "
							+ e.getMessage(), e);
		}

	}

	@SuppressWarnings("unchecked")
	protected List<E> toList(final Gson gson, final Object json) {
		List<E> elements = null; // NOPMD
		if (json instanceof JSONArray) {
			elements = Arrays.asList((E[]) gson.fromJson(json.toString(), Array
					.newInstance(persistentClass, 0).getClass()));
		} else {
			elements = new ArrayList<E>();
			elements.add(gson.fromJson(json.toString(), persistentClass));
		}
		return elements;
	}
}