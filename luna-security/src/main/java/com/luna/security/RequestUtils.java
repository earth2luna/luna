/**
 * COPYRIGHT@LAULYL
 */
package com.luna.security;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.luna.utils.LangUtils;
import com.luna.utils.UrlUtils;

/**
 * @author laulyl
 * @date 2017年5月5日 上午9:48:53
 * @description
 */
public class RequestUtils {

	public static String getUrlFormQueryBase64(String q, HttpServletRequest request,String def) {
		return LangUtils.defaultValue(UrlUtils.decode(request.getParameter(q)), def);
	}

	public static String geBase64RequestUrl(HttpServletRequest request) {

		try {
			return UrlUtils.encode(getNoProtocolUrlString(request));
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
		return url.toString();
	}

	public static URL getUrl(HttpServletRequest request) throws MalformedURLException {
		return new URL(getUrlString(request));
	}

	public static String getNoProtocolUrlString(HttpServletRequest request) throws MalformedURLException {
		URL url = getUrl(request);
		String port = LangUtils.defaultValue(!(-1 == url.getPort() || 80 == url.getPort()), ":" + url.getPort(), "");
		String query = LangUtils.defaultValue(StringUtils.isNotEmpty(url.getQuery()), "?"+url.getQuery(),"");
		return LangUtils.append("//", url.getHost(), port, url.getPath(),  query);
	}

	public static void main(String[] args) throws IOException {
		try {
			URL url = new URL(
					"http://apoollo.com:80/sso?c=?&t=akFJYlpsTWx6MEp1eGtLL29ScG1Ua2gzMGZjZlBLQ00vQkVzZ1NMbUNHd2pmN0FEZ0t6dUMxVFRzRVdQZGVDN084YzU3aEFLdmVlZFFtVkRGaUw1alRmck1mVkU5QjUxMm5IYWIwV1JYYURiTnIwQzVFNjFvSndoaVQxVDcvV25tMGNqVmg4VWlZam5ZY2FwcTlpSEZDY2FHbnN0dzB3cGw1Z1hTTSswRHZLWkdZekY1UUhOTmVQTDA3VE1ycE9Xa1k3VDdqR0VpTVE1ckJXUFcyZStUbWZrc0E0WVpJZFVEMlhWNWNtVERSSE1OOFJGSEQ3ZDZjUWNpQ3g2aVljWEQ5Z1FWRmxmNm5pMjIwcUhjZEYzMG15NHBwQnB2Nk1MMHJPUVVqKzc0U2FubHRTbFdZT1dSM3NYQlFxR3AydnhqMHlQN3ZBbG9UWVF3QzI4VGJ3RVBBPT0=");
			System.out.println(url.getHost());
			System.out.println(url.getProtocol());
			System.out.println(url.getPort());
			System.out.println(url.getPath());
			System.out.println(url.getQuery());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
