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
import com.luna.utils.LangUtils;

/**
 * @author laulyl
 * @date 2017年5月3日 下午5:34:07
 * @description
 */
public class SecurityHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityHandler.class);

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
		String path = request.getRequestURI();

		LOGGER.info("request uri :" + path);

		boolean ret = true;
		if (needLogin(path)) {
			AuthenticationTicket ticket = parseTicket(request, response);
			if (!(null == ticket || ticket.isExpired())) {
				request.setAttribute(SecurityCusor.PIN_KEY, ticket.get_userName());
				LOGGER.info("need login and sign in .");
			} else {
				LOGGER.info("need login and sign out .");
				redirect2LoginPage(request, response);
				ret = false;
			}
		} else {
			LOGGER.info("do not need login .");
		}
		return ret;
	}

	public AuthenticationTicket parseTicket(HttpServletRequest request, HttpServletResponse response) {
		AuthenticationTicket ticket = null;
		Cookie[] cookies = request.getCookies();
		if (ArrayUtils.isNotEmpty(cookies)) {
			for (int i = 0; i < cookies.length && null == ticket; i++) {
				Cookie cookie = cookies[i];
				if (!Configuration.signInCookiesNamePlaintext.equals(cookie.getName())) {
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
		if (StringUtils.equals(path, Configuration.loginPageUrl) || StringUtils.isEmpty(path)) {
			return false;
		}
		if (CollectionUtils.isEmpty(unLoginPaths) && CollectionUtils.isEmpty(Configuration.unLoginPaths)) {
			return Boolean.TRUE.booleanValue();
		}
		String halfPath = null;
		int lastIndex = path.lastIndexOf(".");
		if (lastIndex > -1) {
			halfPath = path.substring(0, lastIndex);
		}else{
			halfPath=path;
		}

		boolean hasEverNeedLoginPaths = hasEverNeedLoginPaths(halfPath);
		if (hasEverNeedLoginPaths) {
			return true;
		}

		boolean hasUnLoginPaths = hasUnLoginPaths(halfPath, unLoginPaths)
				|| hasUnLoginPaths(halfPath, Configuration.unLoginPaths);
		if (hasUnLoginPaths) {
			return false;
		}

		return Boolean.TRUE.booleanValue();
	}

	protected boolean hasEverNeedLoginPaths(String halfPath) {
		boolean ret = false;
		if (CollectionUtils.isNotEmpty(everNeedLoginPaths)) {
			for (String everNeedLoginPath : everNeedLoginPaths) {
				if (halfPath.endsWith(everNeedLoginPath)) {
					return true;
				}
			}
		}
		return ret;
	}

	protected boolean hasUnLoginPaths(String halfPath, Set<String> unLoginPaths) {
		boolean ret = false;
		if (CollectionUtils.isNotEmpty(unLoginPaths)) {
			for (String unLoginPath : unLoginPaths) {
				if (halfPath.endsWith(unLoginPath)) {
					return true;
				}
			}
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
				response.sendRedirect(LangUtils.append(Configuration.loginPageUrl, "?key=",
						Configuration.signInCookiesNameCiphertext));
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
