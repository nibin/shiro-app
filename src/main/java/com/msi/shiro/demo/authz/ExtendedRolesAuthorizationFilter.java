package com.msi.shiro.demo.authz;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.msi.shiro.demo.ShiroAppContext;
import com.msi.shiro.demo.authc.AuthType;
import com.msi.shiro.demo.domain.AuthTypeInfoDomain;
import com.msi.shiro.demo.domain.AuthzInfoDomain;
import com.msi.shiro.demo.domain.AuthzTypeInfoDomain;
import com.msi.shiro.demo.params.SessionParamNames;
import com.msi.shiro.demo.service.AuthorizationService;

public class ExtendedRolesAuthorizationFilter extends AuthorizationFilter {

	private String otpLoginUrl;
	private String userPasswordLoginUrl;

	static Logger logger = LoggerFactory.getLogger(ExtendedRolesAuthorizationFilter.class);

	@SuppressWarnings({ "unchecked" })
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws IOException {

		Subject subject = getSubject(request, response);
		String[] rolesArray = (String[]) mappedValue;

		if (rolesArray == null || rolesArray.length == 0) {
			// no roles specified, so nothing to check - allow access.
			return true;
		}

		Set<String> roles = CollectionUtils.asSet(rolesArray);

		boolean isAllowed = subject.hasAllRoles(roles);

		if (isAllowed == false) {
			/* may be principal is not authorized */
			Map<String, Long> memberRolesMap = new HashMap<String, Long>();
			memberRolesMap.putAll((Map<String, Long>) subject.getSession().getAttribute(
					SessionParamNames.CSESSION__MEMBER_ROLES));
			boolean isAllowedAfterPrivilegeEscalation = memberRolesMap.keySet().containsAll(roles);
			roles.retainAll(memberRolesMap.keySet());

			// ideally it should only be one
			String roleForEscalation = (String) roles.toArray()[0];

			subject.getSession().setAttribute(SessionParamNames.CSESSION__PRIVILEGE_ESCALATION_FLAG,
					isAllowedAfterPrivilegeEscalation);
			subject.getSession().setAttribute(SessionParamNames.CSESSION__PRIVILEGE_ESCALATION_ROLE, roleForEscalation);

		}

		return isAllowed;
	}

	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {

		Subject subject = getSubject(request, response);
		// If the subject isn't identified, redirect to login URL
		if (subject.getPrincipal() == null) {
			saveRequestAndRedirectToLogin(request, response);
		} else {

			Boolean privEscFlag = (Boolean) subject.getSession().getAttribute(
					SessionParamNames.CSESSION__PRIVILEGE_ESCALATION_FLAG);
			String roleForPrivEsc = (String) subject.getSession().getAttribute(
					SessionParamNames.CSESSION__PRIVILEGE_ESCALATION_ROLE);

			if (privEscFlag != null && privEscFlag.booleanValue() == true && roleForPrivEsc != null) {

				String username = (String) subject.getPrincipal();

				AuthorizationService authzService = ShiroAppContext.getApplicationContext().getBean(
						AuthorizationService.class);
				AuthzTypeInfoDomain authzTypeInfoDomain = authzService.getAuthzTypeInfoDomain(username, roleForPrivEsc);
				String redirectUrl = getRedirectUrl(authzTypeInfoDomain);

				if (redirectUrl == null) {
					processUnauthorizedUrl(request, response);
				} else {
					String url = WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
					subject.getSession().setAttribute(SessionParamNames.CSESSION__REDIRECT_URL_AFTER_ESCALATION, url);
					WebUtils.issueRedirect(request, response, redirectUrl);

				}

			} else {

				processUnauthorizedUrl(request, response);
			}

		}
		return false;
	}

	private void processUnauthorizedUrl(ServletRequest request, ServletResponse response) throws IOException {

		String unauthorizedUrl = getUnauthorizedUrl();
		if (StringUtils.hasText(unauthorizedUrl)) {
			WebUtils.issueRedirect(request, response, unauthorizedUrl);
		} else {
			WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

	}

	private String getRedirectUrl(AuthzTypeInfoDomain authzTypeInfo) {

		if (authzTypeInfo != null) {
			AuthType authType = AuthType.getAuthType(authzTypeInfo.getAuthType());
			if (authType == AuthType.SALTED_PASSWORD) {
				return userPasswordLoginUrl;
			} else if (authType == AuthType.ONE_TIME_PASSWORD) {
				return otpLoginUrl;
			}
		}
		return null;
	}

	@Override
	public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception) throws Exception {

		DefaultSecurityManager securityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();

	}

	public String getOtpLoginUrl() {

		return otpLoginUrl;
	}

	public void setOtpLoginUrl(String otpLoginUrl) {

		this.otpLoginUrl = otpLoginUrl;
	}

	public String getUserPasswordLoginUrl() {

		return userPasswordLoginUrl;
	}

	public void setUserPasswordLoginUrl(String userPasswordLoginUrl) {

		this.userPasswordLoginUrl = userPasswordLoginUrl;
	}

}
