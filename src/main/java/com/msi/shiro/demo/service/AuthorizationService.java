package com.msi.shiro.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msi.shiro.demo.dao.RoleDao;
import com.msi.shiro.demo.dao.SessionDao;
import com.msi.shiro.demo.dao.UserRoleDao;
import com.msi.shiro.demo.domain.AuthzInfoDomain;
import com.msi.shiro.demo.domain.AuthzTypeInfoDomain;
import com.msi.shiro.demo.model.RoleModel;
import com.msi.shiro.demo.model.UserRoleModel;

@Service
public class AuthorizationService {

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UserRoleDao userRoleDao;

	@Autowired
	private SessionDao sessionDao;

	public AuthzInfoDomain getAuthzInfoDomain(String username, String sessionId) {

		AuthzInfoDomain authzInfoDomain = new AuthzInfoDomain();
		List<RoleModel> roleModels = roleDao.getMemberRolesByUserName(username);
		Long currentPrivLevel = sessionDao.getCurrentPrivilegeLevelByUserId(sessionId);
		if (roleModels.size() > 0) {
			authzInfoDomain.setUserName(username);
			authzInfoDomain.setCurrentPrivilegeLevel(currentPrivLevel);
			for (RoleModel rm : roleModels) {
				authzInfoDomain.getMemberRolesMap().put(rm.getRoleName(), rm.getPrivilegeLevel());
			}
		}

		return authzInfoDomain;

	}

	public AuthzTypeInfoDomain getAuthzTypeInfoDomain(String username, String roleType) {

		AuthzTypeInfoDomain authzTypeInfoDomain = new AuthzTypeInfoDomain();
		UserRoleModel userRoleModel = userRoleDao.getUserRoleModel(username, roleType);
		Long privilegeLevel = roleDao.getPrivilegeLevelByRoleName(roleType);
		if(userRoleModel != null && privilegeLevel != null) {
			
			authzTypeInfoDomain.setAuthType(userRoleModel.getAuthType());
			authzTypeInfoDomain.setRoleName(roleType);
			authzTypeInfoDomain.setUserName(username);
			authzTypeInfoDomain.setPrivilegeLevel(privilegeLevel);
		}

		return authzTypeInfoDomain;

	}

}
