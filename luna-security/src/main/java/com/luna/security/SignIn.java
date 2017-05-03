/**
 * COPYRIGHT@LAULYL
 */
package com.luna.security;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.luna.utils.LangUtils;
import com.luna.utils.VerificationUtils;

/**
 * @author laulyl
 * @date 2017年5月3日 下午12:58:44
 * @description
 */
public class SignIn {

	public static void simpleSignIn(HttpServletResponse response, String key, String userName, String password) {
		String returnUrl = null;
		if (StringUtils.isEmpty(key)) {
			returnUrl = "/";
		} else if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			returnUrl = LangUtils.append(Configuration.loginPageUrl, "?w=a15byq897xxc&key=" + key);
		} else {
			String signIn = VerificationUtils.getMD5Encode(LangUtils.append(userName, ":", password));
			if (Configuration.loginInitKey.equals(signIn)) {
				try {
					addCookie(response, VerificationUtils.getMD5Encode(key),
							SecurityCusor.getBase64String(new AuthenticationTicket(userName, null, null)),
							SecurityCusor.LOGIN_STAY_TIME_SECONDS);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				returnUrl = Configuration.loginSuccessUrl;
			} else {
				returnUrl = LangUtils.append(Configuration.loginPageUrl, "?w=a15byq897xxc&key=" + key);
			}
		}

		try {
			response.sendRedirect(returnUrl);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void addCookie(HttpServletResponse response, String key, String value, int maxAge) {
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

}
