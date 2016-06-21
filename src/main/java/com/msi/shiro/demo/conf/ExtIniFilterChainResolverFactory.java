package com.msi.shiro.demo.conf;

import java.util.List;
import java.util.Map;

import org.apache.shiro.config.Ini;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.msi.shiro.demo.ShiroAppContext;
import com.msi.shiro.demo.model.UrlConfigModel;
import com.msi.shiro.demo.service.ConfigService;

public class ExtIniFilterChainResolverFactory extends IniFilterChainResolverFactory {

	static Logger logger = LoggerFactory.getLogger(ExtIniFilterChainResolverFactory.class);

	public ExtIniFilterChainResolverFactory() {

		super();
	}

	public ExtIniFilterChainResolverFactory(Ini ini) {

		super(ini);
	}

	public ExtIniFilterChainResolverFactory(Ini ini, Map<String, ?> defaultBeans) {

		super(ini, defaultBeans);

	}

	@Override
	protected void createChains(Map<String, String> urls, FilterChainManager manager) {

		logger.info("Creating url chains mapped with the filters");
		super.createChains(urls, manager);

		ConfigService configService = ShiroAppContext.getApplicationContext().getBean(ConfigService.class);
		List<UrlConfigModel> urlConfigs = configService.getUrlConfigurations();

		for (UrlConfigModel urlConfigModel : urlConfigs) {
			manager.createChain(urlConfigModel.getUrlPath(), urlConfigModel.getFilterList());
		}

	}

}
