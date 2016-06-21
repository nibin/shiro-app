package com.msi.shiro.demo;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ShiroAppContext implements ApplicationContextAware {

	public static ApplicationContext applicationContext;

	public ShiroAppContext() {

		super();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		this.applicationContext = applicationContext;

	}

	public static ApplicationContext getApplicationContext() {

		return applicationContext;
	}

}
