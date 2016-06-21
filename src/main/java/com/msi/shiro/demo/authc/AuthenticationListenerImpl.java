package com.msi.shiro.demo.authc;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.msi.shiro.demo.ShiroAppContext;
import com.msi.shiro.demo.dao.RoleDao;
import com.msi.shiro.demo.params.AccountParamNames;
import com.msi.shiro.demo.params.SessionParamNames;

public class AuthenticationListenerImpl implements AuthenticationListener {

	static Logger logger = LoggerFactory.getLogger(AuthenticationListenerImpl.class);

	@Override
	public void onSuccess(AuthenticationToken token, AuthenticationInfo info) {

		logger.info("Got a success");
		UsernamePasswordExtToken extToken = (UsernamePasswordExtToken) token;
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		session.setAttribute(AccountParamNames.CURRENT_ROLE_TYPE, extToken.getRoleType());

		RoleDao roleDao = ShiroAppContext.getApplicationContext().getBean(RoleDao.class);
		Long privLevel = roleDao.getPrivilegeLevelByRoleName(extToken.getRoleType());
		session.setAttribute(SessionParamNames.CSESSION__CURRENT_PRIVILEGE_LEVEL, privLevel);
	}

	@Override
	public void onFailure(AuthenticationToken token, AuthenticationException ae) {

		// TODO Auto-generated method stub

	}

	@Override
	public void onLogout(PrincipalCollection principals) {

		// TODO Auto-generated method stub

	}

}
