/**
 * COPYRIGHT@LAULYL
 */
package com.luna.security;

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
		Configuration.loginPageUrl = sce.getServletContext().getInitParameter(Configuration.LOGIN_PAGE_URL);
		Configuration.loginSuccessUrl = sce.getServletContext().getInitParameter(Configuration.LOGIN_SUCCESS_URL);
		Configuration.signInCookiesName = sce.getServletContext().getInitParameter(Configuration.SIGN_IN_COOKIES_NAME);
		Configuration.loginInitKey = sce.getServletContext().getInitParameter(Configuration.LOGIN_INIT_KEY);
		Configuration.rsaKeyPairPath = sce.getServletContext().getInitParameter(Configuration.RSA_KEY_PAIR_PATH);
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
