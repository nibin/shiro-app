package com.msi.shiro.demo.domain;

public class AuthzTypeInfoDomain {

	private String userName;
	private String roleName;
	private Long privilegeLevel;
	private String authType;

	public AuthzTypeInfoDomain() {

		super();
	}

	public String getUserName() {

		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public String getRoleName() {

		return roleName;
	}

	public void setRoleName(String roleName) {

		this.roleName = roleName;
	}

	public Long getPrivilegeLevel() {

		return privilegeLevel;
	}

	public void setPrivilegeLevel(Long privilegeLevel) {

		this.privilegeLevel = privilegeLevel;
	}

	public String getAuthType() {

		return authType;
	}

	public void setAuthType(String authType) {

		this.authType = authType;
	}

}
