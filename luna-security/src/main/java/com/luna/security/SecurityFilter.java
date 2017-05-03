/**
 * COPYRIGHT@LAULYL
 */
package com.luna.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luna.utils.IOUtils;

/**
 * @author laulyl
 * @date 2017年5月3日 下午5:34:07
 * @description
 */
public class SecurityFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);

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
		String path = request.getRequestURI();
		boolean ret = true;
		if (needLogin(path) && (null == ticket || ticket.isExpired())) {
			redirect2LoginPage(request, response);
			ret = false;
		}
		if (null != ticket) {
			request.setAttribute(SecurityCusor.PIN_KEY, ticket.get_userName());
		}

		return ret;
	}

	public AuthenticationTicket parseTicket(HttpServletRequest request, HttpServletResponse response) {
		AuthenticationTicket ticket = null;
		Cookie[] cookies = request.getCookies();
		if (ArrayUtils.isNotEmpty(cookies)) {
			for (int i = 0; i < cookies.length && null == ticket; i++) {
				Cookie cookie = cookies[i];
				if (!Configuration.signInCookiesName.equals(cookie.getName())) {
					continue;
				}
				try {
					ticket = SecurityCusor.getAuthenticationTicket(cookie.getValue());
				} catch (Exception e) {
					LOGGER.error("login error:", e);
					break;
				}
			}

		}
		return ticket;
	}

	public boolean needLogin(String path) {
		if (StringUtils.equals(path, Configuration.loginPageUrl)) {
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

		boolean hasEverNeedLoginPaths = hasEverNeedLoginPaths(halfPath);
		if (hasEverNeedLoginPaths) {
			return true;
		}

		boolean hasUnLoginPaths = hasUnLoginPaths(halfPath);
		if (hasUnLoginPaths) {
			return false;
		}

		return Boolean.TRUE.booleanValue();
	}

	protected boolean hasEverNeedLoginPaths(String halfPath) {
		boolean ret = false;
		if (CollectionUtils.isNotEmpty(everNeedLoginPaths)) {
			ret = everNeedLoginPaths.contains(halfPath);
		}
		return ret;
	}

	protected boolean hasUnLoginPaths(String halfPath) {
		boolean ret = false;
		if (CollectionUtils.isNotEmpty(unLoginPaths)) {
			ret = unLoginPaths.contains(halfPath);
		}
		return ret;
	}

	protected void redirect2LoginPage(HttpServletRequest request, HttpServletResponse response) {
		if (isAjaxRequest(request)) {
			ajaxResponse(response);
		} else {
			try {
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0L);
				response.sendRedirect(Configuration.loginPageUrl);
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
			writer.write(SecurityCusor.NO_LOGIN_OBJECT);
		} catch (Exception e) {
			LOGGER.error("ajax response error:", e);
		} finally {
			IOUtils.close(writer);
		}
	}
}
