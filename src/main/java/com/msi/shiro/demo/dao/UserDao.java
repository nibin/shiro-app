package com.msi.shiro.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.msi.shiro.demo.model.UserModel;

@Repository
public class UserDao {

	private JdbcTemplate jdbcTemplate;

	public UserDao() {

		super();
	}

	public UserModel getUser(final String userName) {

		UserModel userModel = this.jdbcTemplate.queryForObject(
				"select obj_id, last_name, first_name from users where username = ?", new Object[] { userName },
				new RowMapper<UserModel>() {

					public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {

						UserModel user = new UserModel();
						user.setObjectId(rs.getLong("obj_id"));
						user.setFirstName(rs.getString("first_name"));
						user.setLastName(rs.getString("last_name"));
						user.setUserName(userName);
						return user;
					}
				});

		return userModel;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
