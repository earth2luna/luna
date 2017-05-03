/**
 * COPYRIGHT@LAULYL
 */
package com.luna.security;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.luna.utils.LangUtils;
import com.luna.utils.VerificationUtils;

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

		Configuration.loginPageUrl = sce.getServletContext().getInitParameter(Configuration.LOGIN_PAGE_URL);

		Configuration.loginSuccessUrl = sce.getServletContext().getInitParameter(Configuration.LOGIN_SUCCESS_URL);

		Configuration.signInCookiesNameCiphertext = sce.getServletContext()
				.getInitParameter(Configuration.SIGN_IN_COOKIES_NAME);

		Configuration.signInCookiesNamePlaintext = VerificationUtils
				.getMD5Encode(Configuration.signInCookiesNameCiphertext);

		Configuration.loginInitKey = sce.getServletContext().getInitParameter(Configuration.LOGIN_INIT_KEY);

		Configuration.rsaKeyPairPath = sce.getServletContext().getInitParameter(Configuration.RSA_KEY_PAIR_PATH);

		Configuration.unLoginPaths = LangUtils
				.split2HashSetString(sce.getServletContext().getInitParameter(Configuration.UN_LOGIN_PATHS), ",");
		
		if ("true".equals(sce.getServletContext().getInitParameter(Configuration.IS_NEED_GENERATE_RSA)))
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
