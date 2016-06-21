package com.msi.shiro.demo.authc;

public enum AuthType {

	NONE("none"), 
	PLAIN_PASSWORD("plain_password"), 
	SALTED_PASSWORD("salted_password"), 
	ONE_TIME_PASSWORD("one_time_password"), 
	X509_CERTIFICATE("X509Certificate"),
	SSO("SSO");

	String value;

	AuthType(String value) {

		this.value = value;
	}

	public String getValue() {

		return value;
	}

	public static AuthType getAuthType(String value) {

		for (AuthType type : AuthType.values()) {
			if (type.getValue().equalsIgnoreCase(value)) {
				return type;
			}
		}

		return AuthType.NONE;
	}

}
