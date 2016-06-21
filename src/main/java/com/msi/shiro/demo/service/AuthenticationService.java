package com.msi.shiro.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msi.shiro.demo.dao.UserDao;
import com.msi.shiro.demo.dao.UserRoleDao;
import com.msi.shiro.demo.domain.AuthInfoDomain;
import com.msi.shiro.demo.domain.AuthTypeInfoDomain;
import com.msi.shiro.demo.model.UserModel;
import com.msi.shiro.demo.model.UserRoleModel;

@Service
public class AuthenticationService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserRoleDao userRoleDao;

	public AuthInfoDomain getAuthInfoDomain(String username, String roleType) {

		AuthInfoDomain authInfo = null;
		UserModel user = userDao.getUser(username);
		UserRoleModel userRoleModel = userRoleDao.getUserRoleModel(username, roleType);
		if (user != null && userRoleModel != null) {
			authInfo = new AuthInfoDomain();
			authInfo.setUsername(username);
			authInfo.setRoleType(roleType);
			
			AuthTypeInfoDomain authTypeInfo = new AuthTypeInfoDomain();
			authTypeInfo.setAuthenticationType(userRoleModel.getAuthType());
			authTypeInfo.setAuthenticationValue(userRoleModel.getAuthValue());

			authInfo.setAuthTypeInfoDomain(authTypeInfo);
		}

		return authInfo;
	}

}
