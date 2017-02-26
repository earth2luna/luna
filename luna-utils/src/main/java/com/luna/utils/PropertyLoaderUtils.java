/**
 * copyright@laulyl
 */
package com.luna.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author lyl 2016-3-6
 * @description
 */
public class PropertyLoaderUtils {

	public static Properties loadProperties(InputStream inStream)
			throws IOException {
		Properties properties = new Properties();
		fillProperties(properties, inStream);
		return properties;
	}

	public static void fillProperties(Properties properties,
			InputStream inStream) throws IOException {
		try {
			properties.load(inStream);
		} catch (IOException e) {
			inStream.close();
		}
	}

	public static Properties loadClassPathProperties(
			String classPathProperties, ClassLoader classLoader)
			throws IOException {
		InputStream stream = classLoader
				.getResourceAsStream(classPathProperties);
		return loadProperties(stream);
	}

}
