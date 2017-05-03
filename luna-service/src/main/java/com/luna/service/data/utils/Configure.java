package com.luna.service.data.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.luna.security.Configuration;

/**
 * @author laulyl
 * @time 2017年4月13日上午10:30:55
 * @description
 */
@Component
public class Configure implements InitializingBean {

	private static Configure configure = new Configure();

	@Value("${resources.generate.path}")
	private String resourceRelativePath;

	@Value("${sign.in.pass.key}")
	private String signInPassKey;

	@Value("${sign.in.cookies.name}")
	private String signInCookiesName;

	@Value("${login.page.url}")
	private String loginPageUrl;

	@Value("${login.success.url}")
	private String loginSuccessUrl;

	public static String getResourceRelativePath() {
		return configure.resourceRelativePath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		configure.resourceRelativePath = resourceRelativePath;

		Configuration.loginInitKey = signInPassKey;
		Configuration.signInCookiesName = signInCookiesName;
		Configuration.loginPageUrl = loginPageUrl;
		Configuration.loginSuccessUrl = loginSuccessUrl;
	}

}
