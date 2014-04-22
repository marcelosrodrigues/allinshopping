package com.pmrodrigues.android.allinshopping.integration.downloads;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class AbstractDownload<E> implements Download<E> {

	private final Class<E> persistentClass;
	
	private final ResourceBundle bundle = ResourceBundle.getBundle("integration");

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
		return bundle.getString(this.persistentClass.getSimpleName().toLowerCase());
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