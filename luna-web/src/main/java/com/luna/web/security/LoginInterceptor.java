/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.luna.service.data.utils.Configure;
import com.luna.utils.IOUtils;

/**
 * @author laulyl
 * @date 2017年5月2日 下午2:24:51
 * @description
 */
public class LoginInterceptor implements HandlerInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);
	private static final PathMatcher MATCHER = new AntPathMatcher();

	protected Set<String> everNeedLoginPaths;
	protected Set<String> unLoginPaths;

	/**
	 * @return the everNeedLoginPaths
	 */
	public Set<String> getEverNeedLoginPaths() {
		return everNeedLoginPaths;
	}

	/**
	 * @param everNeedLoginPaths
	 *            the everNeedLoginPaths to set
	 */
	public void setEverNeedLoginPaths(Set<String> everNeedLoginPaths) {
		this.everNeedLoginPaths = everNeedLoginPaths;
	}

	/**
	 * @return the unLoginPaths
	 */
	public Set<String> getUnLoginPaths() {
		return unLoginPaths;
	}

	/**
	 * @param unLoginPaths
	 *            the unLoginPaths to set
	 */
	public void setUnLoginPaths(Set<String> unLoginPaths) {
		this.unLoginPaths = unLoginPaths;
	}

	public boolean tryIsLogin(HttpServletRequest request, HttpServletResponse response) {
		AuthenticationTicket ticket = parseTicket(request, response);
		String path = request.getServletPath();
		boolean ret = true;
		if (needLogin(path) && (null == ticket || ticket.isExpired())) {
			redirect2LoginPage(request, response);
			ret = false;
		}
		if (null != ticket) {
			request.setAttribute(SecurityConstants.PIN_KEY, ticket.get_userName());
		}

		return ret;
	}

	public AuthenticationTicket parseTicket(HttpServletRequest request, HttpServletResponse response) {
		AuthenticationTicket ticket = null;
		Cookie[] cookies = request.getCookies();
		if (ArrayUtils.isNotEmpty(cookies)) {
			for (int i = 0; i < cookies.length && null == ticket; i++) {
				Cookie cookie = cookies[i];
				if (!Configure.getSignInCookiesName().equals(cookie.getName())) {
					continue;
				}
				try {
					ticket = SecurityConstants.getAuthenticationTicket(cookie.getValue());
				} catch (Exception e) {
					LOGGER.error("login error:", e);
					break;
				}
			}

		}
		return ticket;
	}

	public boolean needLogin(String path) {
		if (StringUtils.equals(path, Configure.getLoginPageUrl())) {
			return false;
		}
		if ((StringUtils.isEmpty(path)) || CollectionUtils.isEmpty(unLoginPaths)) {
			return Boolean.TRUE.booleanValue();
		}
		String halfPath = "";
		int lastIndex = path.lastIndexOf(".");
		if (lastIndex > -1) {
			halfPath = path.substring(0, lastIndex);
		}
		if (CollectionUtils.isNotEmpty(everNeedLoginPaths)) {
			for (String perPath : this.everNeedLoginPaths) {
				if (halfPath.equals(perPath)) {
					return Boolean.TRUE.booleanValue();
				}
			}
		}

		for (String perPath : this.unLoginPaths) {
			if (MATCHER.match(perPath, path))
				return false;
		}
		return Boolean.TRUE.booleanValue();
	}

	public void redirect2LoginPage(HttpServletRequest request, HttpServletResponse response) {
		if (isAjaxRequest(request)) {
			ajaxResponse(response);
		} else {
			try {
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0L);
				response.sendRedirect(Configure.getLoginPageUrl());
			} catch (IOException e) {
				LOGGER.error("send redirect response error:", e);
			}
		}
	}

	protected boolean isAjaxRequest(HttpServletRequest request) {
		return StringUtils.equals("XMLHttpRequest", request.getHeader("X-Requested-With"));
	}

	protected void ajaxResponse(HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(SecurityConstants.NO_LOGIN_OBJECT);
		} catch (Exception e) {
			LOGGER.error("ajax response error:", e);
		} finally {
			IOUtils.close(writer);
		}
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
		return tryIsLogin(request, response);
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

}
