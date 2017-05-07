/**
 * COPYRIGHT@LAULYL
 */
package com.luna.security;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luna.utils.CoderUtils;
import com.luna.utils.LangUtils;

/**
 * @author laulyl
 * @date 2017年5月5日 上午9:48:53
 * @description
 */
public class RequestUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestUtils.class);

	public static String getUrlFormQueryBase64(String q, HttpServletRequest request, String def) {
		String base64Url = request.getParameter(q);
		String normalUrl = LangUtils.defaultValue(CoderUtils.decode(base64Url), def);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("base64 url is :" + base64Url);
			LOGGER.debug("normal url is :" + normalUrl);
		}
		return normalUrl;
	}

	public static String geBase64RequestUrl(HttpServletRequest request) {

		try {
			return CoderUtils.encode(getNoProtocolUrlString(request));
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getUrlString(HttpServletRequest request) {
		String queryString = request.getQueryString();
		StringBuffer url = request.getRequestURL();
		if (StringUtils.isNotEmpty(queryString)) {
			url.append("?").append(queryString);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("request url is :" + url);
		}
		return url.toString();
	}

	public static URL getUrl(HttpServletRequest request) throws MalformedURLException {
		return new URL(getUrlString(request));
	}

	public static String getNoProtocolUrlString(HttpServletRequest request) throws MalformedURLException {
		URL url = getUrl(request);
		String port = LangUtils.defaultValue(!(-1 == url.getPort() || 80 == url.getPort()), ":" + url.getPort(), "");
		String query = LangUtils.defaultValue(StringUtils.isNotEmpty(url.getQuery()), "?" + url.getQuery(), "");
		String noProtocolUrlString = LangUtils.append("//", url.getHost(), port, url.getPath(), query);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("no protocol url is :" + noProtocolUrlString);
		}
		return noProtocolUrlString;
	}

}
