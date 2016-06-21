package com.msi.shiro.demo.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.shiro.session.Session;
import org.apache.shiro.util.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.msi.shiro.demo.model.SessionModel;

@Repository
public class SessionDao {

	private JdbcTemplate jdbcTemplate;

	static Logger logger = LoggerFactory.getLogger(SessionDao.class);

	public SessionDao() {

		super();
	}

	public SessionModel getSession(final String sessionId) {

		SessionModel sessionModel = this.jdbcTemplate.queryForObject(
				"select username, current_privilege_level, serialized_session " + "from sessions where session_id = ?",
				new Object[] { sessionId }, new RowMapper<SessionModel>() {

					public SessionModel mapRow(ResultSet rs, int rowNum) throws SQLException {

						SessionModel model = new SessionModel();
						model.setUserName(rs.getString("username"));
						model.setSessionId(sessionId);
						model.setCurrentPrivilegeLevel(rs.getLong("current_privilege_level"));

						try {
							ByteArrayInputStream in = new ByteArrayInputStream(rs.getBytes("serialized_session"));
							ObjectInputStream oin = new ObjectInputStream(in);
							Session session = (Session) oin.readObject();
							model.setSession(session);
						} catch (IOException e) {
							logger.error("Failed to read serialized_session for id: {}", sessionId, e);
							SQLException sqle = new SQLException();
							sqle.initCause(e.getCause());
							throw sqle;
						} catch (ClassNotFoundException e) {
							logger.error("Failed to read serialized_session for id: {}", sessionId, e);
							SQLException sqle = new SQLException();
							sqle.initCause(e.getCause());
							throw sqle;
						}
						return model;
					}
				});

		return sessionModel;

	}

	public Long getCurrentPrivilegeLevelByUserId(final String sessionId) {

		Long currentPrivLevel = this.jdbcTemplate.queryForObject(
				"select current_privilege_level from sessions where session_id = ?", Long.class, sessionId);

		return currentPrivLevel;
	}

	public void saveSession(SessionModel sessionModel, boolean insertOk) throws SQLException {

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection conn = null;

		final String insertQuery = "INSERT INTO sessions (session_id, username, serialized_session, current_privilege_level) VALUES"
				+ " (?, ?, ?, ?)";

		final String updateQuery = "UPDATE sessions SET username=?, serialized_session=?, current_privilege_level=? WHERE session_id=?";

		if (insertOk) {
			try {
				conn = jdbcTemplate.getDataSource().getConnection();
				statement = conn.prepareStatement(insertQuery);
				statement.setString(1, sessionModel.getSession().getId().toString());
				statement.setString(2, sessionModel.getUserName());
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				ObjectOutputStream bout = new ObjectOutputStream(out);
				bout.writeObject(sessionModel.getSession());
				statement.setBytes(3, out.toByteArray());
				statement.setLong(4, sessionModel.getCurrentPrivilegeLevel());
				statement.executeUpdate();
				logger.debug("Inserted record in database for id: " + sessionModel.getSession().getId().toString());

			} catch (IOException e) {
				logger.error("Failed to save serialized_session for id: {}", sessionModel.getSession().getId()
						.toString(), e);
				SQLException sqle = new SQLException();
				sqle.initCause(e.getCause());
				throw sqle;
			} finally {
				JdbcUtils.closeStatement(statement);
				JdbcUtils.closeConnection(conn);
				JdbcUtils.closeResultSet(resultSet);

			}
		} else {

			try {
				conn = jdbcTemplate.getDataSource().getConnection();
				statement = conn.prepareStatement(updateQuery);
				statement.setString(1, sessionModel.getUserName());
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				ObjectOutputStream bout = new ObjectOutputStream(out);
				bout.writeObject(sessionModel.getSession());
				statement.setBytes(2, out.toByteArray());
				statement.setLong(3, sessionModel.getCurrentPrivilegeLevel());
				statement.setString(4, sessionModel.getSession().getId().toString());
				statement.executeUpdate();
				logger.debug("Updated record in database for id: " + sessionModel.getSession().getId().toString());

			} catch (IOException e) {
				logger.error("Failed to update serialized_session for id: {}", sessionModel.getSession().getId()
						.toString(), e);
				SQLException sqle = new SQLException();
				sqle.initCause(e.getCause());
				throw sqle;
			} finally {
				JdbcUtils.closeStatement(statement);
				JdbcUtils.closeConnection(conn);
				JdbcUtils.closeResultSet(resultSet);

			}

		}

	}

	public void deleteSession(String sessionId) {

		this.jdbcTemplate.update("delete from sessions where session_id = ?", sessionId);
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
