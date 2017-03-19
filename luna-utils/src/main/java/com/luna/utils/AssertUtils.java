/**
 * copyright@lyl
 *lyl-base-common Maven Webapp.com.lylframework.base.common.utils.AssertUtils.java.java
 */
package com.luna.utils;

import java.lang.reflect.InvocationTargetException;

import com.luna.utils.classes.AppException;

/**
 * @author lyl 2015-10-30
 * @description
 */
public class AssertUtils {

	public static void isTrue(boolean expression, Class<? extends RuntimeException> t, Integer code, String message) {
		if (!expression)
			try {
				RuntimeException exception = null;
				if (t.isAssignableFrom(AppException.class))
					exception = t.getConstructor(Integer.class, String.class).newInstance(code, message);
				else
					exception = t.getConstructor(String.class).newInstance(message);
				throw exception;
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e);
			} catch (SecurityException e) {
				throw new RuntimeException(e);
			}
	}

	public static void isTrue(boolean expression, String message) {
		isTrue(expression, IllegalArgumentException.class, 0, message);
	}

	public static void isTrue(boolean expression) {
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}

	public static void isTrueOfApp(boolean expression, Integer code, String message) {
		isTrue(expression, AppException.class, code, message);
	}

	public static void isTrueOfApp(boolean expression, String message) {
		isTrueOfApp(expression, 0, message);
	}

	public static void notNullOfApp(Object object, Integer code, String message) {
		isTrueOfApp(null != object, code, message);
	}

	public static void notNullOfApp(Object object, String message) {
		notNullOfApp(object, 0, message);
	}

}
