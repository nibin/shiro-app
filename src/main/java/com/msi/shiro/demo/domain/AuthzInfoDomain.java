package com.msi.shiro.demo.domain;

import java.util.HashMap;
import java.util.Map;

public class AuthzInfoDomain {

	private Long userObjId;
	private String userName;
	private Map<String, Long> memberRolesMap = new HashMap<String, Long>();
	private Long currentPrivilegeLevel;

	public AuthzInfoDomain() {

		super();
	}

	public Long getUserObjId() {

		return userObjId;
	}

	public void setUserObjId(Long userObjId) {

		this.userObjId = userObjId;
	}

	public String getUserName() {

		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public Long getCurrentPrivilegeLevel() {

		return currentPrivilegeLevel;
	}

	public void setCurrentPrivilegeLevel(Long currentPrivilegeLevel) {

		this.currentPrivilegeLevel = currentPrivilegeLevel;
	}

	public Map<String, Long> getMemberRolesMap() {

		return memberRolesMap;
	}

	public void setMemberRolesMap(Map<String, Long> memberRolesMap) {

		this.memberRolesMap = memberRolesMap;
	}

}
