/**
 * COPYRIGHT@LAULYL
 */
package com.luna.security.brand;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luna.security.Configuration;
import com.luna.security.LoginUtils;
import com.luna.security.SecurityCursor;
import com.luna.utils.LangUtils;

/**
 * @author laulyl
 * @date 2017年5月3日 上午8:34:19
 * @description
 */
public class SecurityListener implements ServletContextListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityListener.class);

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

		Configuration.signInCookiesNamePlaintext = sce.getServletContext()
				.getInitParameter(Configuration.SIGN_IN_COOKIES_NAME);

		Configuration.loginInitKey = sce.getServletContext().getInitParameter(Configuration.LOGIN_INIT_KEY);

		Configuration.rsaKeyPairPath = sce.getServletContext().getInitParameter(Configuration.RSA_KEY_PAIR_PATH);

		Configuration.unLoginPaths = LangUtils
				.split2HashSetString(sce.getServletContext().getInitParameter(Configuration.UN_LOGIN_PATHS), ",");

		Configuration.everLoginPaths = LangUtils
				.split2HashSetString(sce.getServletContext().getInitParameter(Configuration.EVER_LOGIN_PATHS), ",");

		Configuration.needPassportNodeDomains = LangUtils.split2HashSetString(
				sce.getServletContext().getInitParameter(Configuration.NEED_PASSPORT_NODE_DOMAINS), ",");

		Configuration.parameterTicketKey = StringUtils
				.trim(sce.getServletContext().getInitParameter(Configuration.PARAMETER_TICKET_KEY));

		boolean ifOverride = "true"
				.equals(sce.getServletContext().getInitParameter(Configuration.IS_NEED_GENERATE_RSA));
		LOGGER.info("override key pair:" + ifOverride);
		if (ifOverride) {
			SecurityCursor.overrideKeyPair();
		}
		Configuration.parameterTicketValueCipertext = LoginUtils.getParameterTicket();
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
