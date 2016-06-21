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

import com.msi.shiro.demo.model.UrlConfigModel;

@Repository
public class UrlConfigDao {

	private JdbcTemplate jdbcTemplate;

	public UrlConfigDao() {

		super();
	}

	public List<UrlConfigModel> getUrlConfigModelsInAscOrder() {

		List<UrlConfigModel> urlConfigs = new ArrayList<UrlConfigModel>();

		urlConfigs = this.jdbcTemplate.query("SELECT * FROM url_config order by priority_order asc",
				new RowMapper<UrlConfigModel>() {

					public UrlConfigModel mapRow(ResultSet rs, int rowNum) throws SQLException {

						UrlConfigModel model = new UrlConfigModel();
						model.setUrlPath(rs.getString("url_path"));
						model.setFilterList(rs.getString("filter_list"));
						model.setPriorityOrder(rs.getInt("priority_order"));

						return model;
					}

				});
		return urlConfigs;

	}

	@Autowired
	public void setDataSource(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
