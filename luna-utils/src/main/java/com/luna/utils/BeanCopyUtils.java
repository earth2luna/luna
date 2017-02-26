/**
 * copyright@lyl
 *lyl-base-common Maven Webapp.com.lylframework.base.common.bean.BeanCopy.java.java
 */
package com.luna.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

/**
 * @author lyl 2016-2-6
 * @description 实现匹配属性值的赋值
 */
public class BeanCopyUtils {

	public static void copyProperties(Object orig, String origProperty,
			Object dest, String destProperty) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			IntrospectionException {
		AssertUtils.isTrue(null != dest, "dest object is null");
		AssertUtils.isTrue(StringUtils.isNotBlank(destProperty),
				"dest property is blank");
		AssertUtils.isTrue(null != orig, "orig object is null");
		AssertUtils.isTrue(StringUtils.isNotBlank(origProperty),
				"orig property is blank");

		PropertyDescriptor origPropertyDescriptor = new PropertyDescriptor(
				origProperty, orig.getClass());
		Method originMethod = origPropertyDescriptor.getReadMethod();
		AssertUtils.isTrue(null != originMethod, String.format(
				"can't find the orig get method [%s]", origProperty));
		Object value = originMethod.invoke(orig);
		if (null != value) {
			PropertyDescriptor destPropertyDescriptor = new PropertyDescriptor(
					destProperty, dest.getClass());
			AssertUtils
					.isTrue(destPropertyDescriptor.getPropertyType() == origPropertyDescriptor
							.getPropertyType(),
							String.format(
									"the orig property [%s] and dest property [%s] is not the same class type",
									origProperty, destProperty));
			Method destMethod = destPropertyDescriptor.getWriteMethod();
			AssertUtils.isTrue(null != destMethod, String.format(
					"can't find the dest set method [%s]", destProperty));
			destMethod.invoke(dest, value);
		}
	}

	public static void copyProperties(Object orig, Object dest,
			Map<String, String> describe) {
		AssertUtils.isTrue(null != describe && !describe.isEmpty(),
				"describe info is empty");
		Iterator<Map.Entry<String, String>> iterator = describe.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			try {
				copyProperties(orig, entry.getKey(), dest, entry.getValue());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static Map<String, Object> getMap(Object bean) {
		if (null == bean)
			return new HashMap<String, Object>();
		try {
			return PropertyUtils.describe(bean);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T transferObject(T dest, Object origin) {
		Validate.notNull(dest);
		if (null == origin) {
			return null;
		}
		try {
			PropertyUtils.copyProperties(dest, origin);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return dest;
	}

	public static <E, T> List<E> transferObjects(Class<E> dest, List<T> origin) {
		Validate.notNull(dest);
		List<E> list = new ArrayList<E>();
		if (CollectionUtils.isNotEmpty(origin)) {
			for (T t : origin) {
				try {
					E object = transferObject(dest.newInstance(), t);
					list.add(object);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		return list;
	}

}
