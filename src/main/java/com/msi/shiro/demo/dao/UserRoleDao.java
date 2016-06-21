package com.msi.shiro.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.msi.shiro.demo.model.UserRoleModel;

@Repository
public class UserRoleDao {

	private JdbcTemplate jdbcTemplate;

	public UserRoleModel getUserRoleModel(final Long userObjectId, final String roleName) {

		UserRoleModel userRoleModel = this.jdbcTemplate.queryForObject(
				"select auth_type, auth_value from users_roles where user_obj_id = ? and role_name = ?",
				new Object[] { userObjectId, roleName }, new RowMapper<UserRoleModel>() {

					public UserRoleModel mapRow(ResultSet rs, int rowNum) throws SQLException {

						UserRoleModel model = new UserRoleModel();
						model.setUserObjectId(userObjectId);
						model.setRoleName(roleName);
						model.setAuthType(rs.getString("auth_type"));
						model.setAuthValue(rs.getString("auth_value"));
						return model;
					}
				});

		return userRoleModel;
	}
	
	public UserRoleModel getUserRoleModel(final String userName, final String roleName) {

		UserRoleModel userRoleModel = this.jdbcTemplate.queryForObject(
				"select users_roles.user_obj_id, users_roles.auth_type, users_roles.auth_value "
				+ "from users_roles, users "
				+ "where users.obj_id = users_roles.user_obj_id "
				+ "and users.username = ? "
				+ "and users_roles.role_name = ?",
				new Object[] { userName, roleName }, new RowMapper<UserRoleModel>() {

					public UserRoleModel mapRow(ResultSet rs, int rowNum) throws SQLException {

						UserRoleModel model = new UserRoleModel();
						model.setUserObjectId(rs.getLong("user_obj_id"));
						model.setRoleName(roleName);
						model.setAuthType(rs.getString("auth_type"));
						model.setAuthValue(rs.getString("auth_value"));
						return model;
					}
				});

		return userRoleModel;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
