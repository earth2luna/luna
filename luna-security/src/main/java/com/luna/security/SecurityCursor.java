/**
 * COPYRIGHT@LAULYL
 */
package com.luna.security;

import java.security.Key;
import java.security.KeyPair;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.luna.utils.ClassLoaderUtils;
import com.luna.utils.FilePropertyUtils;
import com.luna.utils.IOUtils;
import com.luna.utils.LangUtils;

/**
 * @author laulyl
 * @date 2017年5月2日 下午3:46:10
 * @description
 */
public class SecurityCursor {

	// 30分钟=1800秒
	public static final int LOGIN_STAY_TIME_SECONDS = 1800;

	// 30分钟=1800秒*1000
	public static final long LOGIN_STAY_TIME_MILLIS = LOGIN_STAY_TIME_SECONDS * 1000;

	// 3年=30分钟*2*24*365*3
	public static final long LOGIN_STAY_TIME_YEAR = LOGIN_STAY_TIME_MILLIS * 2 * 24 * 365 * 3;

	public static final String KEY_ALGORITHM = "RSA";

	public static final String SECURITY_PRIVATE_RAS = "Security.private.rsa";

	public static final String SECURITY_PUBLIC_RAS = "Security.public.rsa";

	public static final String PIN_KEY = SecurityCursor.class.getName() + ".PIN.KEY";

	public static final String CLASS_PATH = FilePropertyUtils.filterPathAsWeb(ClassLoaderUtils.getLocation("."));

	private static Key publicKey;

	private static Key privateKey;

	public static String getKeyPath(String appender) {
		String classPath = LangUtils.defaultValue(Configuration.rsaKeyPairPath, CLASS_PATH);
		return FilePropertyUtils.appendPath(classPath, "security", appender);
	}

	public static Key getPublicKey() {
		if (null == publicKey) {
			synchronized (SECURITY_PUBLIC_RAS) {
				if (null == publicKey) {
					String keyPath = getKeyPath(SecurityCursor.SECURITY_PUBLIC_RAS);
					publicKey = RSACoder.getKeyBase64(keyPath, true);
				}
			}
		}
		return publicKey;
	}

	public static Key getPrivateKey() {
		if (null == privateKey) {
			synchronized (SECURITY_PRIVATE_RAS) {
				if (null == privateKey) {
					String keyPath = getKeyPath(SecurityCursor.SECURITY_PRIVATE_RAS);
					privateKey = RSACoder.getKeyBase64(keyPath, false);
				}
			}
		}
		return privateKey;
	}

	public static SecurityTicket getAuthenticationTicket(String input) throws Exception {
		if (StringUtils.isEmpty(input))
			return null;
		String text = RSACoder.decryptByPrivateKey(input, getPrivateKey());
		return JSON.parseObject(text, SecurityTicket.class);
	}

	public static String getBase64String(SecurityTicket input) throws Exception {
		return RSACoder.encryptBase64String(JSON.toJSONString(input), getPublicKey());
	}

	public static void overrideKeyPair() {
		try {
			KeyPair keyPair = RSACoder.generateKeyPair();
			IOUtils.write(getKeyPath(SecurityCursor.SECURITY_PUBLIC_RAS),
					Base64.encodeBase64String(keyPair.getPublic().getEncoded()));

			IOUtils.write(getKeyPath(SecurityCursor.SECURITY_PRIVATE_RAS),
					Base64.encodeBase64String(keyPair.getPrivate().getEncoded()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
