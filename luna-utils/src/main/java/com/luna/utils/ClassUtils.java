/**
 * copyright@laulyl
 */
package com.luna.utils;

import java.io.ObjectStreamClass;

import org.apache.commons.lang.StringUtils;

import com.luna.utils.constant.Constants;
import com.luna.utils.constant.RegxConstants;

/**
 * @author lyl 2016-3-7
 * @description
 */
public class ClassUtils {
	

	public static String getParentPackage(String packageName) {
		AssertUtils.isTrue(StringUtils.isNotBlank(packageName));
		AssertUtils.isTrue(-1 != packageName.indexOf(Constants.POINT_SIGIN));
		return packageName.substring(0,
				packageName.indexOf(Constants.POINT_SIGIN));
	}

	public static String getClassNameSuffix(String className) {
		AssertUtils.isTrue(StringUtils.isNotBlank(className));
		return className
				.substring(className.lastIndexOf(Constants.POINT_SIGIN) + 1);
	}

	public static boolean isImportClass(String localPackage, String classPackage) {
		return !StringUtils.equals(localPackage, classPackage)
				&& ClassUtils.isImportClassName(classPackage);
	}

	public static boolean isImportClassName(String className) {
		if (StringUtils.isNotBlank(className)) {
			if (!className.startsWith("java.lang"))
				return true;
		}
		return false;
	}

	public static long getSerialVersionUID(Class<?> clazz) {
		if (null == clazz)
			return System.currentTimeMillis();
		return ObjectStreamClass.lookup(clazz).getSerialVersionUID();
	}

	public static String packageWebUrl(String packageName) {
		AssertUtils.isTrue(StringUtils.isNotBlank(packageName));
		AssertUtils.isTrue(packageName
				.matches(RegxConstants.JAVA_PACKAGE_PATTERN));
		return packageName.replace(Constants.POINT_SIGIN,
				FilePropertyUtils.WEB_URL_SPLITOR);
	}

}
