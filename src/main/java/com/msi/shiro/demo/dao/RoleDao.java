package com.msi.shiro.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.msi.shiro.demo.model.RoleModel;

@Repository
public class RoleDao {

	private JdbcTemplate jdbcTemplate;

	public RoleDao() {

		super();
	}

	public List<RoleModel> getMemberRolesByUserName(String userName) {

		List<RoleModel> roles = new ArrayList<RoleModel>();

		roles = this.jdbcTemplate.query("select roles.role_name, roles.privilege_level "
				+ "from roles, users, users_roles " + "where users.obj_id = users_roles.user_obj_id "
				+ "and roles.role_name = users_roles.role_name " + "and users.username = ?", new Object[] { userName },
				new RowMapper<RoleModel>() {

					public RoleModel mapRow(ResultSet rs, int rowNum) throws SQLException {

						RoleModel rm = new RoleModel();
						rm.setRoleName(rs.getString(1));
						rm.setPrivilegeLevel(rs.getLong(2));

						return rm;
					}

				});
		return roles;

	}

	public Long getPrivilegeLevelByRoleName(String roleName) {

		Long privLevel = this.jdbcTemplate.queryForObject("select privilege_level from roles where role_name = ?",
				Long.class, roleName);

		return privLevel;

	}

	@Autowired
	public void setDataSource(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
