/**
 * COPYRIGHT@LAULYL
 */
package com.luna.security.brand;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luna.security.Configuration;
import com.luna.security.LoginUtils;
import com.luna.security.RequestUtils;
import com.luna.security.SecurityTicket;

/**
 * @author laulyl
 * @date 2017年5月7日 上午9:21:49
 * @description
 */
public class CookiesOrParameterTicketFilter extends AbstractInitParameterTickeFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(CookiesOrParameterTicketFilter.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		HttpServletResponse rep = (HttpServletResponse) response;

		String passport = req.getParameter(getParameterTicketKey());

		if (LOGGER.isDebugEnabled()) {
			String currentUrl = RequestUtils.getNoProtocolUrlString(req);
			LOGGER.debug("current url is:" + currentUrl);
			LOGGER.debug("parameter key is :" + getParameterTicketKey());
			LOGGER.debug("get parameter ticket is:" + passport);
		}
		SecurityTicket ticket = LoginUtils.getTicketFromString(passport);

		if (null == ticket) {
			ticket = LoginUtils.getTicketFromCookie(req, Configuration.signInCookiesNamePlaintext);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("get cookies ticket is:" + passport);
			}
		}
		if (tryIsLogin(req, rep, ticket)) {
			chain.doFilter(request, response);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}

}
