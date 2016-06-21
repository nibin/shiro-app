package com.msi.shiro.demo.authc;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.subject.PrincipalCollection;

public class ExtendedAuthenticationInfo extends SimpleAuthenticationInfo {

	private Map<String, Object> userContextMap;

	/**
	 * 
	 */
	private static final long serialVersionUID = -5666867693067204943L;

	public ExtendedAuthenticationInfo() {

		userContextMap = new HashMap<String, Object>();
	}

	/**
	 * Constructor that takes in a single 'primary' principal of the account and
	 * its corresponding credentials, associated with the specified realm.
	 * <p/>
	 * This is a convenience constructor and will construct a
	 * {@link PrincipalCollection PrincipalCollection} based on the
	 * {@code principal} and {@code realmName} argument.
	 * 
	 * @param principal
	 *            the 'primary' principal associated with the specified realm.
	 * @param credentials
	 *            the credentials that verify the given principal.
	 * @param realmName
	 *            the realm from where the principal and credentials were
	 *            acquired.
	 */
	public ExtendedAuthenticationInfo(Object principal, Object credentials, String realmName) {

		super(principal, credentials, realmName);
		userContextMap = new HashMap<String, Object>();
	}

	public Map<String, Object> getUserContextMap() {

		return userContextMap;
	}

	public void setUserContextMap(Map<String, Object> userContextMap) {

		this.userContextMap = userContextMap;
	}

	public void addUserContextId(String key, Object id) {

		userContextMap.put(key, id);
	}

}
