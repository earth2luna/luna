package com.luna.service.data.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
	
	@Value("${luna.web.domain}")
	private String thisWebDomain;
	
	@Value("${luna.attachement.path}")
	private String attachementPath;

	public static String getResourceRelativePath() {
		return configure.resourceRelativePath;
	}
	
	public static String getThisWebDomain() {
		return configure.thisWebDomain;
	}
	
	public static String getAttachementPath() {
		return configure.attachementPath;
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
		configure.thisWebDomain = thisWebDomain;
		configure.attachementPath = attachementPath;

	}

}
