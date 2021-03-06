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
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (null == classLoader) {
			classLoader = PropertyLoaderUtils.class.getClassLoader();
		}
		return classLoader;
	}

	public static InputStream getInputStream(String name) {
		return getClassLoader().getResourceAsStream(name);
	}

	public static String getLocation(String name) {
		File file = getFileLocation(name);
		return getAbsolutePath(file);
	}

	public static String getAbsolutePath(File file) {
		return null == file ? null : file.getAbsolutePath();
	}

	public static File getFileLocation(String name) {
		return getFileLocation(getClassLoader(), name);
	}

	public static String getLocation(Class<?> clazz) {
		String path = new File(clazz.getName().replace(".", "/")).getParent();
		String input = FilePropertyUtils.replace2WebUrl(path);
		return FilePropertyUtils.replace2WebUrl(getAbsolutePath(getFileLocation(clazz.getClassLoader(), input)));
	}

	public static File getFileLocation(ClassLoader classLoader, String name) {
		if (null == classLoader) {
			classLoader = getClassLoader();
		}
		URL url = classLoader.getResource(name);
		try {
			return null == url ? null : new File(URLDecoder.decode(url.getFile(), CharsetEnum.UTF8.getCharsetName()));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
