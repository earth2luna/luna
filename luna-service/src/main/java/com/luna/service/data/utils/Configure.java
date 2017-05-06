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

	// 数据静态化的相对路径 /static..
	@Value("${luna.resources.generate.path}")
	private String resourceRelativePath;

	// 本站域名
	@Value("${luna.web.domain}")
	private String thisWebDomain;

	// 静态化文件的附件路径 /static..
	@Value("${luna.attachement.path}")
	private String attachementPath;

	// free marker vew 页面渲染模板名称
	@Value("${luna.template.freemarker.view.name}")
	private String freeMarkerViewName;

	public static String getResourceRelativePath() {
		return configure.resourceRelativePath;
	}

	public static String getThisWebDomain() {
		return configure.thisWebDomain;
	}

	public static String getAttachementPath() {
		return configure.attachementPath;
	}

	public static String getFreeMarkerViewName() {
		return configure.freeMarkerViewName;
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
		configure.freeMarkerViewName = freeMarkerViewName;

	}

}
