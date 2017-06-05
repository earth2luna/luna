/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.luna.service.data.utils.ApplicationGetUtils;
import com.luna.service.data.utils.Constants;

/**
 * @author laulyl
 * @date 2017年6月1日 下午11:37:02
 * @description
 */
public class CacheListener implements ServletContextListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Constants.CATEGORY_LIST=ApplicationGetUtils.getCategories();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
