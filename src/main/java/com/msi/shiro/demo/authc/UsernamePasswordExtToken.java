package com.msi.shiro.demo.authc;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UsernamePasswordExtToken extends UsernamePasswordToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7790206561667663275L;

	private String roleType;
	private String authType;

	public UsernamePasswordExtToken() {

		super();
	}

	public UsernamePasswordExtToken(String username, String password, boolean rememberMe, String host, String roleType,
			String authType) {

		super(username, password, rememberMe, host);
		this.roleType = roleType;
		this.authType = authType;
	}

	public String getRoleType() {

		return roleType;
	}

	public void setRoleType(String roleType) {

		this.roleType = roleType;
	}

	public String getAuthType() {

		return authType;
	}

	public void setAuthType(String authType) {

		this.authType = authType;
	}

}
