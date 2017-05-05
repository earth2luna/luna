/**
 * COPYRIGHT@LAULYL
 */
package com.luna.security;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.luna.utils.LangUtils;
import com.luna.utils.UrlUtils;
import com.luna.utils.VerificationUtils;
import com.luna.utils.classes.InvokeVo;
import com.luna.utils.infce.IInputOutput;

/**
 * @author laulyl
 * @date 2017年5月3日 下午12:58:44
 * @description
 */
public class LoginUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginUtils.class);

	public static SecurityTicket getTicket(String passport) {
		try {
			String ticketInput = UrlUtils.decode(passport);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(ticketInput);
			}
			SecurityTicket ticket = SecurityCursor.getAuthenticationTicket(ticketInput);
			if (!(null == ticket || ticket.isExpired() || !Configuration.loginInitKey.equals(ticket.get_userData()))) {
				return ticket;
			}
		} catch (Exception e) {
			LOGGER.error("get ticket error:", e);
		}
		return null;
	}

	public static SecurityTicket getTicketFromCookie(HttpServletRequest request, String name) {
		Cookie cookie = findCookie(request, name);
		if (null != cookie) {
			return getTicket(cookie.getValue());
		}
		return null;
	}

	public static boolean verificationTicket(String passport) {
		return null != getTicket(passport);
	}

	public static String getUserString(String userName, String password) {
		String ret = null;
		if (!(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password))) {
			String s = VerificationUtils.getMD5Encode(LangUtils.append(userName, ":", password));
			if (Configuration.loginInitKey.equals(s)) {
				ret = s;
			}
		}
		return ret;
	}

	public static boolean verificationUser(String userName, String password) {
		return null != getUserString(userName, password);
	}

	public static InvokeVo getPassort(HttpServletRequest req, String userName, String password) {
		InvokeVo invokeVo = null;
		try {
			if (verificationUser(userName, password)) {
				String ticket = SecurityCursor
						.getBase64String(new SecurityTicket(userName, Configuration.loginInitKey, null));
				Set<String> domains = new HashSet<String>();

				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(ticket);
				}
				LangUtils.joins2Collection(domains, Configuration.needPassportNodeDomains,
						new IInputOutput<String, String>() {

							@Override
							public String get(String i) {
								return LangUtils.append("//", i, Configuration.URL_SSO,
										"?c=?&t=" + UrlUtils.encode(ticket));
							}

						});
				String ret = RequestUtils.getUrlFormQueryBase64("ret", req, "/");
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(ret);
				}
				invokeVo = new InvokeVo("verification success!", new Passport(ret, domains), 1);
			} else {
				invokeVo = new InvokeVo("verification failed!", null, 0);
			}
		} catch (Exception e) {
			invokeVo = new InvokeVo("verification exception!", null, -1);
		}

		return invokeVo;
	}

	public static String getJSONPassport(HttpServletRequest req, String userName, String password) {
		return JSON.toJSONString(getPassort(req, userName, password));
	}

	public static String getJSONPPassport(String ticket, HttpServletResponse resp) {
		InvokeVo invokeVo = null;
		if (verificationTicket(ticket)) {
			invokeVo = new InvokeVo("verification success!", null, 1);
			LoginUtils.addCookie(resp, Configuration.signInCookiesNamePlaintext, ticket,
					SecurityCursor.LOGIN_STAY_TIME_SECONDS);
		} else {
			invokeVo = new InvokeVo("verification failed!", null, 0);
		}
		return LangUtils.append("c(", JSON.toJSONString(invokeVo), ")");
	}

	public static String getInterceptorPath(HttpServletRequest request) {
		return getInterceptorPath(request.getContextPath(), request.getRequestURI());
	}

	public static String getInterceptorPath(String contextPath, String uri) {
		String ret = null;
		if (StringUtils.isEmpty(contextPath) || "/".equals(contextPath) || !uri.startsWith(contextPath)) {
			ret = uri;
		} else {
			ret = uri.substring(contextPath.length(), uri.length());
		}
		return ret;
	}

	// public static void simpleSignIn(HttpServletResponse response, String key,
	// String userName, String password) {
	// String returnUrl = null;
	// if (StringUtils.isEmpty(key)) {
	// returnUrl = "/";
	// } else if (StringUtils.isEmpty(userName) ||
	// StringUtils.isEmpty(password)) {
	// returnUrl = LangUtils.append(Configuration.loginPageUrl,
	// "?w=a15byq897xxc&key=" + key);
	// } else {
	// String signIn = VerificationUtils.getMD5Encode(LangUtils.append(userName,
	// ":", password));
	// if (Configuration.loginInitKey.equals(signIn)) {
	// try {
	// addCookie(response, VerificationUtils.getMD5Encode(key),
	// SecurityCursor.getBase64String(new SecurityTicket(userName, null, null)),
	// SecurityCursor.LOGIN_STAY_TIME_SECONDS);
	// } catch (Exception e) {
	// throw new RuntimeException(e);
	// }
	// returnUrl = Configuration.loginSuccessUrl;
	// } else {
	// returnUrl = LangUtils.append(Configuration.loginPageUrl,
	// "?w=a15byq897xxc&key=" + key);
	// }
	// }
	//
	// try {
	// response.sendRedirect(returnUrl);
	// } catch (IOException e) {
	// throw new RuntimeException(e);
	// }
	// }

	public static void addCookie(HttpServletResponse response, String key, String value, int maxAge) {
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	public static Cookie findCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (ArrayUtils.isNotEmpty(cookies)) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					return cookie;
				}
			}
		}
		return null;
	}

}
