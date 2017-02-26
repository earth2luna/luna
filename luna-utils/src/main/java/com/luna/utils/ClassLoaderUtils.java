/**
 * copyright@laulyl
 */
package com.luna.utils;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import com.luna.utils.enm.CharsetEnum;

/**
 * @author lyl 2016-3-6
 * @description
 */
public class ClassLoaderUtils {

	public static ClassLoader getClassLoader() {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		if (null == classLoader) {
			classLoader = PropertyLoaderUtils.class.getClassLoader();
		}
		return classLoader;
	}

	public static InputStream getInputStream(String name) {
		return getClassLoader().getResourceAsStream(name);
	}

	public static String getLocation(String name) {
		URL url = getClassLoader().getResource(name);
		try {
			return null == url ? null : new File(URLDecoder.decode(
					url.getFile(), CharsetEnum.UTF8.getCharsetName()))
					.getAbsolutePath();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
