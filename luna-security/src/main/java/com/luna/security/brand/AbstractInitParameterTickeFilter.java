/**
 * COPYRIGHT@LAULYL
 */
package com.luna.security.brand;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.luna.security.Configuration;
import com.luna.utils.LangUtils;

/**
 * @author laulyl
 * @date 2017年5月7日 上午9:30:39
 * @description
 */
public abstract class AbstractInitParameterTickeFilter extends AbstractInitTickeFilter {

	private String parameterTicketKey;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		String filterInit = StringUtils.trim(filterConfig.getInitParameter(Configuration.PARAMETER_TICKET_KEY));
		parameterTicketKey = LangUtils.defaultValue(filterInit, Configuration.parameterTicketKey);
		Validate.notNull(parameterTicketKey);
	}

	public String getParameterTicketKey() {
		return parameterTicketKey;
	}

	public void setParameterTicketKey(String parameterTicketKey) {
		this.parameterTicketKey = parameterTicketKey;
	}

}
