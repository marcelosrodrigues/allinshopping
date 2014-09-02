package com.pmrodrigues.android.allinshopping;

import android.app.Application;

public class ApplicationContext extends Application {

	private static ApplicationContext context = new ApplicationContext();
	
	public static ApplicationContext getInstance() {
		return context;
	}
}
