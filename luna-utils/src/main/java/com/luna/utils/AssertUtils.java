/**
 * copyright@lyl
 *lyl-base-common Maven Webapp.com.lylframework.base.common.utils.AssertUtils.java.java
 */
package com.luna.utils;

import org.apache.commons.lang.StringUtils;

/**
 * @author lyl 2015-10-30
 * @description
 */
public class AssertUtils {

	public static void isTrue(boolean expression,
			Class<? extends RuntimeException> t, String message) {
		if (!expression)
			try {
				RuntimeException exception = t.newInstance();
				if (StringUtils.isNotBlank(message))
					exception.initCause(new Throwable(message));
				throw exception;
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
	}

	public static void isTrue(boolean expression, String message) {
		isTrue(expression, IllegalArgumentException.class, message);
	}

	public static void isTrue(boolean expression) {
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}

}
