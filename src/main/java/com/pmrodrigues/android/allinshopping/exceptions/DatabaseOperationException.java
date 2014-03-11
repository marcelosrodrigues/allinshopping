package com.pmrodrigues.android.allinshopping.exceptions;

public class DatabaseOperationException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;

	public DatabaseOperationException(String message ,Throwable throwable) {
		super(message ,throwable);
	}

	

}
