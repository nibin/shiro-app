package com.msi.shiro.demo.model;

import org.apache.shiro.session.Session;

public class SessionModel {

	private String sessionId;
	private String userName;
	private Long currentPrivilegeLevel;
	private Session session;

	public SessionModel() {

		super();
	}

	public String getSessionId() {

		return sessionId;
	}

	public void setSessionId(String sessionId) {

		this.sessionId = sessionId;
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

	public Session getSession() {

		return session;
	}

	public void setSession(Session session) {

		this.session = session;
	}

}
