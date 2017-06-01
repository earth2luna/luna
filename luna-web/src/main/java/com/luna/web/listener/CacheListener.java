/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.luna.service.CategoryService;
import com.luna.service.data.utils.Constants;
import com.luna.web.configure.ApplicationContextHolder;

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
		sce.getServletContext().setAttribute(Constants.CATEGORY_LIST_KEY,
				ApplicationContextHolder.getBean(CategoryService.class).getCategories());
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
