package com.msi.shiro.demo.params;

public class SessionParamNames {

	public static final String CSESSION__CURRENT_PRIVILEGE_LEVEL = SessionParamNames.class.getName() + ".csession.current_privilege_level";
	public static final String CSESSION__MEMBER_ROLES = SessionParamNames.class.getName() + ".csession.member_roles";
	public static final String CSESSION__AUTHORIZED_ROLES = SessionParamNames.class.getName()
			+ ".csession.authorized_roles";

	
	public static final String CSESSION__PRIVILEGE_ESCALATION_FLAG = SessionParamNames.class.getName()
			+ ".csession.privilege_escalation_flag";
	public static final String CSESSION__PRIVILEGE_ESCALATION_ROLE = SessionParamNames.class.getName()
			+ ".csession.privilege_escalation_role";
	

	public static final String CSESSION__REDIRECT_URL_AFTER_ESCALATION = SessionParamNames.class.getName()
			+ ".csession.redirect_url_after_escalation";

}
