/**
 * copyright@laulyl
 */
package com.luna.utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

/**
 * @author laulyl
 * @date 2017年7月10日 下午8:05:31
 * @desction
 */
public class UrlUtils {

	public static String getHome(String requestUrl) {
		try {
			URL url = new URL(requestUrl);
			return LangUtils.append(url.getProtocol(), "://", url.getHost());
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public static String makesureFullUrl(String requestUrl, String tryUrl) {
		String ret = tryUrl;
		try {
			if (StringUtils.isNotBlank(tryUrl)) {
				if (tryUrl.startsWith("http") || tryUrl.startsWith("https")) {
					return ret;
				}
				URL url = new URL(requestUrl);
				if (tryUrl.startsWith("//")) {
					return LangUtils.append(url.getProtocol(), ":", tryUrl);
				}
				if (!tryUrl.startsWith("/")) {
					return FilePropertyUtils.appendPath(requestUrl, tryUrl);
				}
				String home = LangUtils.append(url.getProtocol(), "://", url.getHost());
				return FilePropertyUtils.appendPath(home, tryUrl);
			}

		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		return ret;
	}

	public static void main(String[] args) {
		System.out.println(makesureFullUrl("http://harttle.com/2016/04/24/client-height-width.html",
				"//harttle.com/2016/04/24/client-height-width.html"));
		System.out.println(makesureFullUrl("http://harttle.com/2016/04/24/client-height-width.html",
				"/2016/04/24/client-height-width.html"));
		
		System.out.println(makesureFullUrl("http://harttle.com/2016/04/24/",
				"client-height-width.html"));
	}
}
