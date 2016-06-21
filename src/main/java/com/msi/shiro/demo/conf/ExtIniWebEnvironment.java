package com.msi.shiro.demo.conf;

import org.apache.shiro.config.Ini;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.env.IniWebEnvironment;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtIniWebEnvironment extends IniWebEnvironment {

	static Logger logger = LoggerFactory.getLogger(ExtIniWebEnvironment.class);

	@Override
	protected void configure() {

		logger.info("Configuring shiro web environment");

		this.objects.clear();

		WebSecurityManager securityManager = createWebSecurityManager();
		setWebSecurityManager(securityManager);

		FilterChainResolver resolver = createFilterChainResolver();
		if (resolver != null) {
			setFilterChainResolver(resolver);
		}
	}

	@Override
	protected FilterChainResolver createFilterChainResolver() {

		FilterChainResolver resolver = null;

		Ini ini = getIni();

		if (!CollectionUtils.isEmpty(ini)) {
			// explicitly creating resolver as the url's will be read from DB
			ExtIniFilterChainResolverFactory factory = new ExtIniFilterChainResolverFactory(ini, this.objects);
			resolver = factory.getInstance();

		}

		return resolver;
	}
}
