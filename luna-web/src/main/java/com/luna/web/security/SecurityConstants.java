/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.security;

import java.security.Key;

import com.alibaba.fastjson.JSON;
import com.luna.utils.ClassLoaderUtils;
import com.luna.utils.FilePropertyUtils;

/**
 * @author laulyl
 * @date 2017年5月2日 下午3:46:10
 * @description
 */
public class SecurityConstants {

	public static final int LOGIN_STAY_TIME_SECONDS = 1800;

	public static final long LOGIN_STAY_TIME_MILLIS = LOGIN_STAY_TIME_SECONDS * 1000;

	public static final String KEY_ALGORITHM = "RSA";

	public static final String SECURITY_PRIVATE_RAS = "Security.private.rsa";

	public static final String SECURITY_PUBLIC_RAS = "Security.public.rsa";

	public static final String PIN_KEY = SecurityConstants.class.getName() + ".PIN.KEY";

	public static final String NO_LOGIN_OBJECT = "{\"code\":\"0\",\"message\":\"NotLogin\"}";

	public static final String SECURITY_PRIVATE_RAS_PATH = FilePropertyUtils
			.appendPath(ClassLoaderUtils.getLocation(LoginInterceptor.class), SecurityConstants.SECURITY_PRIVATE_RAS);

	public static final String SECURITY_PUBLIC_RAS_PATH = FilePropertyUtils
			.appendPath(ClassLoaderUtils.getLocation(LoginInterceptor.class), SecurityConstants.SECURITY_PUBLIC_RAS);

	private static Key publicKey;

	private static Key privateKey;

	public static Key getPublicKey() {
		if (null == publicKey) {
			synchronized (SECURITY_PUBLIC_RAS) {
				if (null == publicKey) {
					publicKey = RSACoder.getKeyBase64(SecurityConstants.SECURITY_PUBLIC_RAS_PATH, true);
				}
			}
		}
		return publicKey;
	}

	public static Key getPrivateKey() {
		if (null == privateKey) {
			synchronized (SECURITY_PRIVATE_RAS) {
				if (null == privateKey) {
					privateKey = RSACoder.getKeyBase64(SecurityConstants.SECURITY_PRIVATE_RAS_PATH, false);
				}
			}
		}
		return privateKey;
	}

	public static AuthenticationTicket getAuthenticationTicket(String input) throws Exception {
		String text = RSACoder.decryptByPrivateKey(input, getPrivateKey());
		return JSON.parseObject(text, AuthenticationTicket.class);
	}

	public static String getBase64String(AuthenticationTicket input) throws Exception {
		return RSACoder.encryptBase64String(JSON.toJSONString(input), getPublicKey());
	}

}
