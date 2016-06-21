package com.msi.shiro.demo.servlet;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.msi.shiro.demo.authc.AuthType;
import com.msi.shiro.demo.authc.UsernamePasswordExtToken;
import com.msi.shiro.demo.params.SessionParamNames;

public class MultiAuthFilter extends FormAuthenticationFilter {

	private String otpLoginUrl;
	private String otpParamName;
	private String defaultRoleType;
	private String defaultErrorMessage;

	static Logger logger = LoggerFactory.getLogger(MultiAuthFilter.class);

	@Override
	protected boolean isLoginSubmission(ServletRequest request, ServletResponse response) {

		boolean b = super.isLoginSubmission(request, response);
		String url = WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
		if (url.equalsIgnoreCase(getLoginUrl()) || url.equalsIgnoreCase(otpLoginUrl))
			return (true && b);
		return false;
	}

	@Override
	protected boolean isLoginRequest(ServletRequest request, ServletResponse response) {

		String url = WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
		if (url.equalsIgnoreCase(getLoginUrl()) || url.equalsIgnoreCase(otpLoginUrl)) {
			return true;
		}

		return false;

	}

	@Override
	public String getLoginUrl() {

		return super.getLoginUrl();
	}

	@Override
	protected AuthenticationToken createToken(String username, String password, ServletRequest request,
			ServletResponse response) {

		String url = WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
		UsernamePasswordExtToken extToken = null;
		String host = getHost(request);
		Session session = SecurityUtils.getSubject().getSession(false);

		if (url.equalsIgnoreCase(getLoginUrl())) {
			boolean rememberMe = isRememberMe(request);
			String authType = AuthType.SALTED_PASSWORD.getValue();
			String roleType = defaultRoleType;
			extToken = new UsernamePasswordExtToken(username, password, rememberMe, host, roleType, authType);

		} else if (url.equalsIgnoreCase(otpLoginUrl)) {
			Subject subject = getSubject(request, response);
			String resolvedUsername = (String) subject.getPrincipal();
			String otpPassword = WebUtils.getCleanParam(request, getOtpParamName());
			String roleType = (String) subject.getSession().getAttribute(
					SessionParamNames.CSESSION__PRIVILEGE_ESCALATION_ROLE);
			extToken = new UsernamePasswordExtToken(resolvedUsername, otpPassword, false, host, roleType,
					AuthType.ONE_TIME_PASSWORD.getValue());
		}
		return extToken;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

		boolean alreadyAuthenticated = super.isAccessAllowed(request, response, mappedValue);
		// /String url =
		// WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
		if (alreadyAuthenticated == false)
			return alreadyAuthenticated;
		else {
			boolean flag = isLoginRequest(request, response) && isLoginSubmission(request, response);
			if (flag == true)
				return false;
			else
				return alreadyAuthenticated;
		}
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {

		Session session = subject.getSession(false);
		if (session != null) {
			session.removeAttribute(SessionParamNames.CSESSION__PRIVILEGE_ESCALATION_ROLE);
			session.removeAttribute(SessionParamNames.CSESSION__PRIVILEGE_ESCALATION_FLAG);
			session.removeAttribute(SessionParamNames.CSESSION__REDIRECT_URL_AFTER_ESCALATION);
		}

		return super.onLoginSuccess(token, subject, request, response);
	}

	@Override
	protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {

		Session session = SecurityUtils.getSubject().getSession(false);

		request.setAttribute(getFailureKeyAttribute(), defaultErrorMessage);

	}

	@Override
	protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {

		String originalPage = request.getParameter("originalPage");
		if (originalPage == null)
			WebUtils.redirectToSavedRequest(request, response, getSuccessUrl());
		else
			WebUtils.redirectToSavedRequest(request, response, originalPage);
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

	public String getOtpParamName() {

		return otpParamName;
	}

	public void setOtpParamName(String otpParamName) {

		this.otpParamName = otpParamName;
	}

	public String getDefaultRoleType() {

		return defaultRoleType;
	}

	public void setDefaultRoleType(String defaultRoleType) {

		this.defaultRoleType = defaultRoleType;
	}

	public String getDefaultErrorMessage() {

		return defaultErrorMessage;
	}

	public void setDefaultErrorMessage(String defaultErrorMessage) {

		this.defaultErrorMessage = defaultErrorMessage;
	}

}
