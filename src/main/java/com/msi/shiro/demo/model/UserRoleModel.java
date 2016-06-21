package com.msi.shiro.demo.model;

public class UserRoleModel {

	private Long userObjectId;
	private String roleName;
	private String authType;
	private String authValue;

	public UserRoleModel() {

		super();
	}

	public Long getUserObjectId() {

		return userObjectId;
	}

	public void setUserObjectId(Long userObjectId) {

		this.userObjectId = userObjectId;
	}

	public String getRoleName() {

		return roleName;
	}

	public void setRoleName(String roleName) {

		this.roleName = roleName;
	}

	public String getAuthType() {

		return authType;
	}

	public void setAuthType(String authType) {

		this.authType = authType;
	}

	public String getAuthValue() {

		return authValue;
	}

	public void setAuthValue(String authValue) {

		this.authValue = authValue;
	}

}
