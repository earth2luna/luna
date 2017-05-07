/**
 * COPYRIGHT@LAULYL
 */
package com.luna.security.brand;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import com.luna.security.Configuration;
import com.luna.security.FilterHandler;

/**
 * @author laulyl
 * @date 2017年5月7日 上午9:30:39
 * @description
 */
public abstract class AbstractInitTickeFilter extends FilterHandler implements Filter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		init(filterConfig.getInitParameter(Configuration.IF_RESTFULL_MODE),
				filterConfig.getInitParameter(Configuration.LOGIN_PAGE_URL),
				filterConfig.getInitParameter(Configuration.UN_LOGIN_PATHS),
				filterConfig.getInitParameter(Configuration.EVER_LOGIN_PATHS));
	}

}
