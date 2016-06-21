package com.msi.shiro.demo.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.SimpleByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.msi.shiro.demo.ShiroAppContext;
import com.msi.shiro.demo.authc.AuthType;
import com.msi.shiro.demo.authc.ExtendedAuthenticationInfo;
import com.msi.shiro.demo.authc.UsernamePasswordExtToken;
import com.msi.shiro.demo.domain.AuthInfoDomain;
import com.msi.shiro.demo.domain.AuthzInfoDomain;
import com.msi.shiro.demo.params.SessionParamNames;
import com.msi.shiro.demo.service.AuthenticationService;
import com.msi.shiro.demo.service.AuthorizationService;

public class DbRealm extends AuthorizingRealm {

	static Logger logger = LoggerFactory.getLogger(DbRealm.class);

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		String username = (String) getAvailablePrincipal(principals);

		AuthorizationService authzService = ShiroAppContext.getApplicationContext().getBean(AuthorizationService.class);
		AuthzInfoDomain authzInfoDomain = authzService.getAuthzInfoDomain(username, session.getId().toString());

		if (session.getAttribute(SessionParamNames.CSESSION__MEMBER_ROLES) == null) {
			session.setAttribute(SessionParamNames.CSESSION__MEMBER_ROLES, authzInfoDomain.getMemberRolesMap());
		}

		AuthorizationInfo info = extractAuthzInfo(authzInfoDomain);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		UsernamePasswordExtToken userPassExtToken = (UsernamePasswordExtToken) token;
		String username = userPassExtToken.getUsername();
		String roleType = userPassExtToken.getRoleType();
		AuthenticationService authService = ShiroAppContext.getApplicationContext().getBean("authenticationService",
				AuthenticationService.class);
		AuthInfoDomain authInfoDomain = authService.getAuthInfoDomain(username, roleType);

		AuthenticationInfo info = extractAuthcInfo(authInfoDomain);
		return info;
	}

	private AuthenticationInfo extractAuthcInfo(AuthInfoDomain authInfoDomain) {

		AuthType authType = AuthType.getAuthType(authInfoDomain.getAuthTypeInfoDomain().getAuthenticationType());
		if (authType == AuthType.SALTED_PASSWORD) {
			ExtendedAuthenticationInfo info = new ExtendedAuthenticationInfo(authInfoDomain.getUsername(),
					authInfoDomain.getAuthTypeInfoDomain().getAuthenticationValue(), getName());
			info.setCredentialsSalt(new SimpleByteSource("salt"));
			return info;
		} else if (authType == AuthType.ONE_TIME_PASSWORD) {
			// TODO: currently the otp passwords are plain text password.
			// salting them to work with shiro credential matcher which
			// requires salt
			Sha256Hash sha256Hash = new Sha256Hash(authInfoDomain.getAuthTypeInfoDomain().getAuthenticationValue(),
					"salt", 1);
			ExtendedAuthenticationInfo info = new ExtendedAuthenticationInfo(authInfoDomain.getUsername(),
					sha256Hash.toHex(), getName());
			info.setCredentialsSalt(new SimpleByteSource("salt"));

			return info;
		} else {
			return null;
		}
	}

	private AuthorizationInfo extractAuthzInfo(AuthzInfoDomain authzInfoDomain) {

		Long currentPrivLevel = authzInfoDomain.getCurrentPrivilegeLevel();
		Set<String> roles = new HashSet<String>();
		for (String roleKey : authzInfoDomain.getMemberRolesMap().keySet()) {
			if (authzInfoDomain.getMemberRolesMap().get(roleKey) <= currentPrivLevel) {
				roles.add(roleKey);
			}
		}

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(roles);

		return info;
	}
}
