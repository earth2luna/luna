package com.luna.utils.page;

import com.luna.utils.LangUtils;

/**
 * @author laulyl
 * @time 2017年6月9日下午4:14:56
 * @description 路径参数路由输出
 */
public class PathRoutePageOuput extends AbstractRoutePageOutput {

	
	public PathRoutePageOuput(String route, String appender) {
		super(route, appender);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.solomon.resource.web.page.AbstractRoutePageOutput#getRoute(java.lang.
	 * Long)
	 */
	@Override
	public String getRoute(Long pageNow) {
		return LangUtils.append(route, "/", pageNow, "/", appender);
	}

}