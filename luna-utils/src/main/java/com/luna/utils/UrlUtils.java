/**
 * COPYRIGHT@LAULYL
 */
package com.luna.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.luna.utils.enm.CharsetEnum;

/**
 * @author laulyl
 * @date 2017年5月4日 下午3:38:18
 * @description
 */
public class UrlUtils {

	public static String encode(String input) {
		if (null == input)
			return null;
		try {
			return URLEncoder.encode(input, CharsetEnum.GBK.getCharsetName());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String decode(String input) {
		if (null == input)
			return null;
		try {
			return URLDecoder.decode(input, CharsetEnum.GBK.getCharsetName());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
