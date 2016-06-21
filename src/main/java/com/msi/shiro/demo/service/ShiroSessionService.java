package com.msi.shiro.demo.service;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.msi.shiro.demo.ShiroAppContext;
import com.msi.shiro.demo.dao.SessionDao;
import com.msi.shiro.demo.model.SessionModel;
import com.msi.shiro.demo.params.SessionParamNames;

/**
 * This is not a spring managed service class
 * 
 * @author nibin
 * 
 */
public class ShiroSessionService extends CachingSessionDAO {

	static Logger logger = LoggerFactory.getLogger(ShiroSessionService.class);

	@Override
	protected void doUpdate(Session session) {

		logger.debug("Updating sessionId in database: " + session.getId().toString());
		SessionModel sessionModel = getSessionModel(session);
		try {
			SessionDao sessionDao = ShiroAppContext.getApplicationContext().getBean(SessionDao.class);
			sessionDao.saveSession(sessionModel, false);
		} catch (Exception e) {
			logger.error("Failed to update session for id: {}", session.getId().toString(), e);
		}

	}

	@Override
	protected void doDelete(Session session) {

		logger.debug("Deleting sessionId in database: " + session.getId().toString());
		try {
			SessionDao sessionDao = ShiroAppContext.getApplicationContext().getBean(SessionDao.class);
			sessionDao.deleteSession(session.getId().toString());
		} catch (Exception e) {
			logger.error("Failed to delete session for id: {}", session.getId().toString(), e);
		}

	}

	@Override
	protected Serializable doCreate(Session session) {

		//logger.debug("Creating sessionId in database: " + session.getId().toString());
		String sessionId = this.getSessionIdGenerator().generateId(session).toString();
		assignSessionId(session, sessionId);
		
		session.setAttribute(SessionParamNames.CSESSION__CURRENT_PRIVILEGE_LEVEL, Long.valueOf(0));
		SessionModel sessionModel = getSessionModel(session);
		try {
			SessionDao sessionDao = ShiroAppContext.getApplicationContext().getBean(SessionDao.class);
			sessionDao.saveSession(sessionModel, true);
		} catch (Exception e) {
			logger.error("Failed to save session for id: {}", session.getId().toString(), e);
		}

		return session.getId();
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {

		Session session = null;
		logger.debug("Fetching sessionId from database: " + sessionId.toString());
		try {
			SessionDao sessionDao = ShiroAppContext.getApplicationContext().getBean(SessionDao.class);
			SessionModel sessionModel = sessionDao.getSession(sessionId.toString());
			session = sessionModel.getSession();
		} catch (Exception e) {
			logger.error("Failed to fetch session for id: {}", sessionId.toString(), e);
		}
		return session;
	}

	private SessionModel getSessionModel(Session session) {

		SessionModel sessionModel = null;
		if (session != null) {
			sessionModel = new SessionModel();
			sessionModel.setCurrentPrivilegeLevel((Long) session
					.getAttribute(SessionParamNames.CSESSION__CURRENT_PRIVILEGE_LEVEL));
			sessionModel.setSession(session);
			sessionModel.setSessionId(session.getId().toString());

		}
		return sessionModel;
	}

}
