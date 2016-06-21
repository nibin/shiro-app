package com.msi.shiro.demo.domain;

public class AuthInfoDomain {

	private String username;

	private String roleType;

	private AuthTypeInfoDomain authTypeInfoDomain;

	public AuthInfoDomain() {

		super();
	}

	public String getUsername() {

		return username;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public String getRoleType() {

		return roleType;
	}

	public void setRoleType(String roleType) {

		this.roleType = roleType;
	}

	public AuthTypeInfoDomain getAuthTypeInfoDomain() {

		return authTypeInfoDomain;
	}

	public void setAuthTypeInfoDomain(AuthTypeInfoDomain authTypeInfoDomain) {

		this.authTypeInfoDomain = authTypeInfoDomain;
	}

}
