package com.msi.shiro.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msi.shiro.demo.dao.UrlConfigDao;
import com.msi.shiro.demo.model.UrlConfigModel;

@Service
public class ConfigService {

	@Autowired
	private UrlConfigDao urlConfigDao;

	static Logger logger = LoggerFactory.getLogger(ConfigService.class);

	public List<UrlConfigModel> getUrlConfigurations() {

		logger.info("Received request to fetch url configurations");
		return urlConfigDao.getUrlConfigModelsInAscOrder();

	}

}
