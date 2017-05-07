/**
 * COPYRIGHT@LAULYL
 */
package com.luna.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
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

	protected boolean ifRESTfulModel = false;
	protected String loginPageUrl;
	protected Set<String> unLoginPaths;
	protected Set<String> everLoginPaths;

	@SuppressWarnings("unchecked")
	public void init(String ifRESTfulModelInput, String loginPageUrlInput, String unLoginPathsInput,
			String everLoginPathsInput) {
		ifRESTfulModel = StringUtils.equals("true", StringUtils.trim(ifRESTfulModelInput));
		loginPageUrl = LangUtils.defaultValue(StringUtils.trim(loginPageUrlInput), Configuration.loginPageUrl);
		unLoginPaths = LangUtils.split2HashSetString(StringUtils.trim(unLoginPathsInput), ",");
		everLoginPaths = LangUtils.split2HashSetString(StringUtils.trim(everLoginPathsInput), ",");
		LangUtils.merge(new Collection[] { unLoginPaths, Configuration.unLoginPaths });
		LangUtils.merge(new Collection[] { everLoginPaths, Configuration.everLoginPaths });
		Validate.isTrue(ifRESTfulModel || StringUtils.isNotEmpty(loginPageUrl));
	}

	public boolean tryIsLogin(HttpServletRequest request, HttpServletResponse response, SecurityTicket ticket) {

		String path = LoginUtils.getInterceptorPath(request);

		LOGGER.info("interceptor path :" + path);

		boolean ret = true;
		if (needLogin(path)) {
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
		if (StringUtils.equals(path, loginPageUrl) || StringUtils.isEmpty(path)) {
			return false;
		}
		if (CollectionUtils.isEmpty(unLoginPaths)) {
			return Boolean.TRUE.booleanValue();
		}

		boolean hasEverNeedLoginPaths = hasEverNeedLoginPaths(path, everLoginPaths);
		if (hasEverNeedLoginPaths) {
			return true;
		}

		boolean hasUnLoginPaths = hasUnLoginPaths(path, unLoginPaths);
		if (hasUnLoginPaths) {
			return false;
		}

		return Boolean.TRUE.booleanValue();
	}

	protected boolean hasEverNeedLoginPaths(String halfPath, Set<String> everNeedLoginPaths) {
		boolean ret = false;
		if (CollectionUtils.isNotEmpty(everNeedLoginPaths)) {
			ret = everNeedLoginPaths.contains(halfPath);
		}
		return ret;
	}

	protected boolean hasUnLoginPaths(String halfPath, Set<String> unLoginPaths) {
		boolean ret = false;
		if (CollectionUtils.isNotEmpty(unLoginPaths)) {
			ret = unLoginPaths.contains(halfPath);
		}
		return ret;
	}

	protected void redirect2LoginPage(HttpServletRequest request, HttpServletResponse response) {
		if (isAjaxRequest(request) || ifRESTfulModel) {
			ajaxResponse(response);
		} else {
			try {
				String ret = RequestUtils.geBase64RequestUrl(request);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(ret);
				}
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0L);
				response.sendRedirect(LangUtils.append(loginPageUrl, "?ret=", ret));
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
			writer.write("{\"code\":\"0\",\"message\":\"NotLogin\"}");
		} catch (Exception e) {
			LOGGER.error("ajax response error:", e);
		} finally {
			IOUtils.close(writer);
		}
	}

}
