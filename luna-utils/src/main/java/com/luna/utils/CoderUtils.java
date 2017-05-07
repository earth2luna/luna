/**
 * COPYRIGHT@LAULYL
 */
package com.luna.utils;

import java.util.Base64;

import org.apache.commons.lang.StringUtils;

/**
 * @author laulyl
 * @date 2017年5月4日 下午3:38:18
 * @description
 */
public class CoderUtils {

	

	public static String encode(String input) {
		if (StringUtils.isEmpty(input))
			return null;
		return Base64.getUrlEncoder().encodeToString(input.getBytes());
	}

	public static String decode(String input) {
		if (StringUtils.isEmpty(input))
			return null;
		return new String(Base64.getDecoder().decode(input));
	}


}
