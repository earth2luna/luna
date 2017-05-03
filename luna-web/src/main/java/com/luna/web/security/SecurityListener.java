/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.security;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author laulyl
 * @date 2017年5月3日 上午8:34:19
 * @description
 */
public class SecurityListener implements ServletContextListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		SecurityCusor.overrideKeyPair();
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
