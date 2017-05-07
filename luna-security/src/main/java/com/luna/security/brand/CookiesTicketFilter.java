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
import com.luna.security.SecurityTicket;

/**
 * @author laulyl
 * @date 2017年5月3日 下午5:29:21
 * @description
 */
public class CookiesTicketFilter extends AbstractInitTickeFilter {
	
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

		SecurityTicket ticket = LoginUtils.getTicketFromCookie(req, Configuration.signInCookiesNamePlaintext);
		
		if (tryIsLogin(req, rep, ticket)) {
			
			if (LOGGER.isDebugEnabled()) {
				
				LOGGER.debug("get cookies ticket name is:" + ticket.get_userName());
			}
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
