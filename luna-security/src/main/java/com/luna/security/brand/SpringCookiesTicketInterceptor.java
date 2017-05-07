/**
 * COPYRIGHT@LAULYL
 */
package com.luna.security.brand;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.luna.security.Configuration;
import com.luna.security.FilterHandler;
import com.luna.security.LoginUtils;
import com.luna.security.SecurityTicket;

/**
 * @author laulyl
 * @date 2017年5月2日 下午2:24:51
 * @description
 */
public class SpringCookiesTicketInterceptor extends FilterHandler implements HandlerInterceptor, InitializingBean {

	private static final PathMatcher MATCHER = new AntPathMatcher();

	private String ifRESTfulModelInput;
	private String loginPageUrlInput;
	private String unLoginPathsInput;
	private String everLoginPathsInput;

	public String getIfRESTfulModelInput() {
		return ifRESTfulModelInput;
	}

	public void setIfRESTfulModelInput(String ifRESTfulModelInput) {
		this.ifRESTfulModelInput = ifRESTfulModelInput;
	}

	public String getLoginPageUrlInput() {
		return loginPageUrlInput;
	}

	public void setLoginPageUrlInput(String loginPageUrlInput) {
		this.loginPageUrlInput = loginPageUrlInput;
	}

	public String getUnLoginPathsInput() {
		return unLoginPathsInput;
	}

	public void setUnLoginPathsInput(String unLoginPathsInput) {
		this.unLoginPathsInput = unLoginPathsInput;
	}

	public String getEverLoginPathsInput() {
		return everLoginPathsInput;
	}

	public void setEverLoginPathsInput(String everLoginPathsInput) {
		this.everLoginPathsInput = everLoginPathsInput;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.
	 * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		SecurityTicket ticket = LoginUtils.getTicketFromCookie(request, Configuration.signInCookiesNamePlaintext);
		return tryIsLogin(request, response, ticket);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.security.SecurityFilter#hasUnLoginPaths(java.lang.String)
	 */
	@Override
	protected boolean hasUnLoginPaths(String halfPath, Set<String> unLoginPaths) {
		if (CollectionUtils.isNotEmpty(unLoginPaths)) {
			for (String perPath : unLoginPaths) {
				if (MATCHER.match(perPath, halfPath)) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.
	 * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.
	 * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		init(ifRESTfulModelInput, loginPageUrlInput, unLoginPathsInput, everLoginPathsInput);

	}

}
