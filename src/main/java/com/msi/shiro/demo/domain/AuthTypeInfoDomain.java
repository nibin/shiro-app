package com.msi.shiro.demo.domain;

public class AuthTypeInfoDomain {

	private String authenticationType;
	private String authenticationValue;
	private boolean salted;
	private String salt;

	public AuthTypeInfoDomain() {

		super();

	}

	public String getAuthenticationType() {

		return authenticationType;
	}

	public void setAuthenticationType(String authenticationType) {

		this.authenticationType = authenticationType;
	}

	public String getAuthenticationValue() {

		return authenticationValue;
	}

	public void setAuthenticationValue(String authenticationValue) {

		this.authenticationValue = authenticationValue;
	}

	public boolean isSalted() {

		return salted;
	}

	public void setSalted(boolean salted) {

		this.salted = salted;
	}

	public String getSalt() {

		return salt;
	}

	public void setSalt(String salt) {

		this.salt = salt;
	}

}
