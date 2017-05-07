/**
 * COPYRIGHT@LAULYL
 */
package com.luna.security;

import java.util.Set;

/**
 * @author laulyl
 * @date 2017年5月3日 下午12:19:23
 * @description
 */
public class Configuration {

	public static final String URL_HELLO = "/hello";

	public static final String URL_SSO = "/sso";

	public static final String NEED_PASSPORT_NODE_DOMAINS = "NEED_PASSPORT_NODE_DOMAINS";

	public static final String SIGN_IN_COOKIES_NAME = "SIGN_IN_COOKIES_NAME";

	public static final String IF_RESTFULL_MODE = "IF_RESTFULL_MODE";

	public static final String LOGIN_PAGE_URL = "LOGIN_PAGE_URL";

	public static final String LOGIN_SUCCESS_URL = "LOGIN_SUCCESS_URL";

	public static final String LOGIN_INIT_KEY = "LOGIN_INIT_KEY";

	public static final String UN_LOGIN_PATHS = "UN_LOGIN_PATHS";

	public static final String EVER_LOGIN_PATHS = "EVER_LOGIN_PATHS";

	public static final String RSA_KEY_PAIR_PATH = "RSA_KEY_PAIR_PATH";

	public static final String IS_NEED_GENERATE_RSA = "IS_NEED_GENERATE_RSA";

	public static final String PARAMETER_TICKET_KEY = "PARAMETER_TICKET_KEY";

	public static String parameterTicketKey;
	
	public static String parameterTicketValueCipertext;

	public static String signInCookiesNamePlaintext;

	public static String loginPageUrl;

	public static String loginSuccessUrl;

	public static String loginInitKey;

	public static String rsaKeyPairPath;

	public static Set<String> unLoginPaths;

	public static Set<String> everLoginPaths;

	public static Set<String> needPassportNodeDomains;

}
