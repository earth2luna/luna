/**
 * COPYRIGHT@LAULYL
 */
package com.luna.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
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
public class FilterHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(FilterHandler.class);

	public boolean tryIsLogin(HttpServletRequest request, HttpServletResponse response) {

		String path = LoginUtils.getInterceptorPath(request);

		LOGGER.info("interceptor path :" + path);

		boolean ret = true;
		if (needLogin(path)) {
			SecurityTicket ticket = LoginUtils.getTicketFromCookie(request, Configuration.signInCookiesNamePlaintext);
			if (null != ticket) {
				request.setAttribute(SecurityCursor.PIN_KEY, ticket.get_userName());
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

	public boolean needLogin(String path) {
		if (StringUtils.equals(path, Configuration.loginPageUrl) || StringUtils.isEmpty(path)) {
			return false;
		}
		if (CollectionUtils.isEmpty(Configuration.unLoginPaths)) {
			return Boolean.TRUE.booleanValue();
		}
		// String halfPath = null;
		// int lastIndex = path.lastIndexOf(".");
		// if (lastIndex > -1) {
		// halfPath = path.substring(0, lastIndex);
		// }else{
		// halfPath=path;
		// }

		boolean hasEverNeedLoginPaths = hasEverNeedLoginPaths(path, Configuration.everLoginPaths);
		if (hasEverNeedLoginPaths) {
			return true;
		}

		boolean hasUnLoginPaths = hasUnLoginPaths(path, Configuration.unLoginPaths);
		if (hasUnLoginPaths) {
			return false;
		}

		return Boolean.TRUE.booleanValue();
	}

	protected boolean hasEverNeedLoginPaths(String halfPath, Set<String> everNeedLoginPaths) {
		boolean ret = false;
		if (CollectionUtils.isNotEmpty(everNeedLoginPaths)) {
			// for (String everNeedLoginPath : everNeedLoginPaths) {
			// if (halfPath.endsWith(everNeedLoginPath)) {
			// return true;
			// }
			// }
			ret = everNeedLoginPaths.contains(halfPath);
		}
		return ret;
	}

	protected boolean hasUnLoginPaths(String halfPath, Set<String> unLoginPaths) {
		boolean ret = false;
		if (CollectionUtils.isNotEmpty(unLoginPaths)) {
			// for (String unLoginPath : unLoginPaths) {
			// if (halfPath.endsWith(unLoginPath)) {
			// return true;
			// }
			// }
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
			writer.write(SecurityCursor.NO_LOGIN_OBJECT);
		} catch (Exception e) {
			LOGGER.error("ajax response error:", e);
		} finally {
			IOUtils.close(writer);
		}
	}

}
