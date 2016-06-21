package com.msi.shiro.demo.model;

public class RoleModel {

	private String roleName;
	private Long privilegeLevel;

	public RoleModel() {

		super();
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

}
