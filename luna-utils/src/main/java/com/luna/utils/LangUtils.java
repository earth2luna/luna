/**
 * copyright@laulyl
 */
package com.luna.utils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.luna.utils.catched.IGetProperty;
import com.luna.utils.classes.KV;
import com.luna.utils.constant.Constants;
import com.luna.utils.infce.IInputOutput;
import com.luna.utils.infce.InputStringOutputLong;

/**
 * @author lyl 2016-3-8
 * @description
 */
public class LangUtils {

	public static boolean isBlank(String input) {
		return StringUtils.isBlank(input) || "null".equals(input);
	}

	public static boolean isNotBlank(String input) {
		return !isBlank(input);
	}

	public static String defaultEmpty(String value) {
		return defaultValue(value, Constants.EMPTY_STRING);
	}

	public static <T> T defaultValue(T value, T defaultValue) {
		if (null == value)
			return defaultValue;
		return value;
	}

	public static <T> T defaultValue(boolean expression, T value, T defaultValue) {
		if (expression)
			return value;
		return defaultValue;
	}

	public static <T> StringBuilder append(CharSequence startFragment, Collection<T> collection,
			IGetProperty<T> property, String splitor) {
		startFragment = (CharSequence) decode(null == startFragment, Constants.EMPTY_STRING, startFragment);
		StringBuilder builder = new StringBuilder(startFragment);
		if (CollectionUtils.isNotEmpty(collection)) {
			Iterator<T> iterator = collection.iterator();
			while (iterator.hasNext()) {
				T t = iterator.next();
				if (null == t)
					continue;
				Serializable fragment = property.get(t);
				if (null == fragment || Constants.EMPTY_STRING.equals(fragment))
					continue;
				builder.append(fragment);
				if (null != splitor) {
					builder.append(splitor);
				}
			}
		}
		if (null != splitor && startFragment.length() != builder.length())
			builder.deleteCharAt(builder.length() - 1).toString();
		return builder;
	}

	public static String appendAndFilterBlank(boolean isFilterBlank, Object... appenders) {
		AssertUtils.isTrue(null != appenders);
		AssertUtils.isTrue(0 != appenders.length);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < appenders.length; i++) {
			if (null == appenders[i])
				continue;
			String appender = appenders[i].toString();
			if (isFilterBlank && isBlank(appender))
				continue;
			builder.append(appender);
		}
		return builder.toString();
	}

	public static String append(Object... appenders) {
		return appendAndFilterBlank(false, appenders);
	}

	public static String pf(String format, Object... args) {
		return String.format(format, args);
	}

	/**
	 * as same as oracle decode method parameter as
	 * (boolean,value,boolean,value,elseValue)
	 * 
	 * <pre>
	 * decode(true, &quot;value&quot;); // return value decode(false,
	 * &quot;value&quot;, true, &quot;value1&quot;); // return value1
	 * decode(false, &quot;value&quot;, false, &quot;value1&quot;); // return
	 * null decode(false); // throw validate format exception decode(false,
	 * &quot;value&quot;, &quot;value1&quot;); // return value1
	 * decode(false,"value","true","value1","value2"); //return value2
	 * 
	 * <pre>
	 */
	public static Object decode(Object... objects) {
		if (ArrayUtils.isEmpty(objects))
			return null;
		Validate.isTrue(1 < objects.length, "input value format exeption");
		for (int i = 0; i < objects.length; i++)
			if (0 == i % 2 && null != (objects[i]) && objects[i] instanceof Boolean
					&& Boolean.valueOf(String.valueOf(objects[i]))) {
				if (objects.length <= (i + 1))
					return objects[i];
				else
					return objects[i + 1];
			}
		if (0 != objects.length % 2) {
			return objects[objects.length - 1];
		}
		return null;
	}

	public static String deleteFirstSplitor(String input, String firstSplitor) {
		AssertUtils.isTrue(StringUtils.isNotBlank(firstSplitor));
		if (StringUtils.isBlank(input))
			return Constants.EMPTY_STRING;
		if (firstSplitor.equals(toString(input.charAt(0)))) {
			return input.substring(1, input.length());
		}
		return input;
	}

	public static String deleteLastSplitor(String input, String lastSplitor) {
		AssertUtils.isTrue(StringUtils.isNotBlank(lastSplitor));
		if (StringUtils.isBlank(input))
			return Constants.EMPTY_STRING;
		if (lastSplitor.equals(toString(input.charAt(input.length() - 1)))) {
			return input.substring(0, input.length() - 1);
		}
		return input;
	}

	public static String deleteFBSplitor(String input, String splitor) {
		return deleteLastSplitor(deleteFirstSplitor(input, splitor), splitor);
	}

	public static String appendFragment(String splitor, String... fragments) {
		if (null == fragments || 0 == fragments.length)
			return Constants.EMPTY_STRING;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < fragments.length; i++) {
			String fgt = fragments[i];
			if (StringUtils.isBlank(fgt))
				continue;
			if (0 == i) {
				builder.append(LangUtils.deleteLastSplitor(fgt, splitor));
			} else {
				builder.append(LangUtils.deleteFBSplitor(fgt, splitor));
			}
			if (i != fragments.length - 1) {
				builder.append(splitor);
			}
		}
		return builder.toString();
	}

	public static <T> Collection<T> merge(Collection<T>[] collections) {
		if (null == collections || 0 == collections.length)
			return null;
		Collection<T> ret = null;
		boolean bool = true;
		for (Collection<T> collection : collections) {
			if (null == collection)
				continue;
			if (bool) {
				ret = collection;
				bool = false;
				continue;
			}
			ret.addAll(collection);
		}
		return ret;
	}

	public static double retainsPointer(double value, int p) {
		return new BigDecimal(String.valueOf(value)).setScale(p, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static double retainsPointer(BigDecimal value, int p) {
		return value.setScale(p, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	private static void number2numberTimes(KV<Double, Integer> kv) {
		double unit = kv.getK() / 1024;
		if (unit >= 1) {
			kv.setK(unit);
			kv.setV(kv.getV() + 1);
			number2numberTimes(kv);
		}
	}

	/**
	 * 通过输入字节返回显示字符串单位
	 * 
	 * @param byteLength
	 *            输入字节数据量
	 * @return 显示单位，保留两位小数
	 */
	public static String byte2SizeUnit(double byteLength) {
		KV<Double, Integer> kv = new KV<Double, Integer>(byteLength, 0);
		number2numberTimes(kv);
		String value = String.valueOf(retainsPointer(kv.getK(), 2));
		switch (kv.getV()) {
		case 0:
			return append(value, "B");
		case 1:
			return append(value, "KB");
		case 2:
			return append(value, "MB");
		case 3:
			return append(value, "GB");
		case 4:
			return append(value, "TB");
		case 5:
			return append(value, "PB");
		case 6:
			return append(value, "EB");
		case 7:
			return append(value, "ZB");
		case 8:
			return append(value, "YB");
		case 9:
			return append(value, "BB");
		case 10:
			return append(value, "NB");
		case 11:
			return append(value, "DB");
		default:
			throw new UnsupportedOperationException();
		}
	}

	public static boolean isArchiveFile(byte[] bytes) {
		// 7z ,rar,zip
		return equalsBytes(bytes, new byte[] { 55, 122, -68, -81 }, new byte[] { 82, 97, 114, 33 },
				new byte[] { 80, 75, 3, 4 });
	}

	public static boolean equalsBytes(byte[] bytes, byte[]... equals) {
		if (null == equals || 0 == equals.length)
			return false;
		for (int i = 0; i < equals.length; i++) {
			if (Arrays.equals(bytes, equals[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean between(long input, long min, long max) {
		return input >= min && input <= max;
	}

	public static int intValueOfNumber(Number number) {
		if (null == number)
			return 0;
		return number.intValue();
	}

	public static long longValueOfNumber(Number number) {
		if (null == number)
			return 0;
		return number.longValue();
	}

	public static double doubleValueOfNumber(Number number) {
		if (null == number)
			return 0;
		return number.doubleValue();
	}

	public static boolean booleanValueOfNumber(Number number) {
		if (null == number)
			return false;
		return number.doubleValue() > 0;
	}

	public static <I, O> void joins2Collection(Collection<O> collection, Collection<I> list,
			IInputOutput<I, O> inputOutput) {
		Validate.notNull(inputOutput);
		if (CollectionUtils.isNotEmpty(list)) {
			for (I i : list) {
				if (null == i)
					continue;
				O o = inputOutput.get(i);
				if (null != o) {
					collection.add(o);
				}
			}
		}
	}

	public static <I, O> Set<O> joins2HashSet(List<I> list, IInputOutput<I, O> inputOutput) {
		Set<O> set = new HashSet<O>();
		joins2Collection(set, list, inputOutput);
		return set;
	}

	public static <I, O> List<O> joins2ArrayList(List<I> list, IInputOutput<I, O> inputOutput) {
		List<O> output = new ArrayList<O>();
		joins2Collection(output, list, inputOutput);
		return output;
	}

	public static <T> void split2Collection(Collection<T> collection, String character, String splitor,
			IInputOutput<String, T> inputOutput) {
		List<String> list = CharacterUtils.split(character, splitor);
		if (CollectionUtils.isNotEmpty(list)) {
			Iterator<String> iterator = list.iterator();
			while (iterator.hasNext()) {
				T o = inputOutput.get(iterator.next());
				if (null != o)
					collection.add(o);
			}
		}
	}

	public static List<Long> split2ArrayListLong(String character, String splitor) {
		List<Long> collection = new ArrayList<Long>();
		split2Collection(collection, character, splitor, new InputStringOutputLong());
		return collection;
	}

	public static List<String> split2ArrayListString(String character, String splitor) {
		return CharacterUtils.split(character, splitor);
	}

	public static Set<String> split2HashSetString(String character, String splitor) {
		Set<String> set = new HashSet<String>();
		List<String> list = CharacterUtils.split(character, splitor);
		if (CollectionUtils.isNotEmpty(list)) {
			for (String s : list) {
				set.add(s);
			}
		}
		return set;
	}

	public static boolean equals(Object object1, Object object2) {
		try {
			return null == object1 || null == object2 ? false
					: object1 instanceof Number || object2 instanceof Number
							? Double.valueOf(object1.toString()).equals(Double.valueOf(object2.toString()))
							: object1 instanceof String || object2 instanceof String
									? object1.toString().equals(object2.toString()) : object1.equals(object2);
		} catch (Exception e) {
			return false;
		}
	}

	public static String toString(Object input, Object defaultValue) {
		Object value = defaultValue(input, defaultValue);
		return null == value ? null : value.toString();
	}

	public static String toString(Object input) {
		return null == input ? null : input.toString();
	}

	public static String trim(Object input) {
		String ret = null;
		if (null != input) {
			ret = input.toString();
			if (null != ret) {
				ret = ret.trim();
			}
		}
		return ret;
	}

	public static String subtringDefaultAppender(String input, int length) {
		return subtringAppender(input, length, "...");
	}

	public static String subtring(String input, int length) {
		return subtringAppender(input, length, "");
	}

	public static String subtringAppender(String input, int length, String appender) {
		return null == input || input.length() <= length ? input : append(input.substring(0, length), appender);
	}

	public static String replaceAll(String input, String regex, String replacement) {
		if (StringUtils.isEmpty(input)) {
			return null;
		}
		return input.replaceAll(regex, replacement);
	}

}
