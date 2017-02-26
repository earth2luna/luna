/**
 * copyright@laulyl
 */
package com.luna.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.luna.utils.constant.Constants;
import com.luna.utils.constant.RegxConstants;

/**
 * @author lyl 2016-3-7
 * @description
 */
public class CharacterUtils {

	public static boolean has(String orgin, String sub) {
		if (null == orgin || null == sub)
			return false;
		return -1 != orgin.indexOf(sub);
	}

	public static void assertJavaWord(String javaWord, String message) {
		AssertUtils
				.isTrue(!(StringUtils.isBlank(javaWord)
						|| 0 == javaWord.length() || !String.valueOf(
						javaWord.charAt(0)).matches(
						RegxConstants.JAVA_NAME_PATTERN)), message);
	}

	public static boolean isUpperCaseFirst(String pascal) {
		if (StringUtils.isBlank(pascal) || 0 == pascal.length())
			return false;
		return Character.isUpperCase(pascal.charAt(0));
	}

	public static String toUpperCaseFirst(String pascal) {
		assertJavaWord(pascal,
				String.format("the pascal character [%s] is not allow", pascal));
		if (!isUpperCaseFirst(pascal)) {
			if (1 == pascal.length())
				return String.valueOf(pascal.charAt(0)).toUpperCase();
			return String.valueOf(pascal.charAt(0)).toUpperCase()
					+ (new StringBuilder(pascal).deleteCharAt(0));
		}
		return pascal;
	}

	/**
	 * <pre>
	 * make javaWord is one word
	 * </pre>
	 */
	public static String toPascal(String javaWord) {
		assertJavaWord(javaWord, null);
		if (!isUpperCaseFirst(javaWord)) {
			return String.valueOf(javaWord.charAt(0)).toUpperCase()
					+ javaWord.substring(1).toLowerCase();
		}
		return javaWord;
	}

	public static boolean isLowerCaseFirst(String hump) {
		if (StringUtils.isBlank(hump) || 0 == hump.length())
			return false;
		return Character.isLowerCase(hump.charAt(0));
	}

	public static String toLowerCaseFirst(String hump) {
		assertJavaWord(hump,
				String.format("the hump character [%s] is not allow", hump));
		if (!isLowerCaseFirst(hump)) {
			return String.valueOf(hump.charAt(0)).toLowerCase()
					+ (new StringBuilder(hump).deleteCharAt(0));
		}
		return hump;
	}

	public static String ifBlankDefaultValue(String input, String defaultValue) {
		if (StringUtils.isBlank(input))
			return defaultValue;
		return input;
	}

	public static String ifNulDefaultValue(Object input, String defaultValue) {
		if (null == input)
			return defaultValue;
		return input.toString();
	}

	public static String ifNulDefaultValue(String input, String defaultValue) {
		if (null == input)
			return defaultValue;
		return input;
	}

	public static String toUpperCase(String input) {
		if (null == input)
			return null;
		return input.toUpperCase();
	}

	public static String getPascal(String input, String[] cleaner,
			String[] joiner) {
		AssertUtils.isTrue(StringUtils.isNotBlank(input));
		AssertUtils.isTrue(input.matches(RegxConstants.DATABASE_NAME_PATTERN),
				LangUtils.pf("input [%s] not match regx of [%s]", input,
						RegxConstants.DATABASE_NAME_PATTERN));
		List<String> fragments = joinCharater(cleanCharater(input, cleaner),
				joiner);
		StringBuilder builder = new StringBuilder();
		for (String fragment : fragments) {
			if (StringUtils.isBlank(fragment))
				continue;
			builder.append(CharacterUtils.toUpperCaseFirst(fragment));
		}
		AssertUtils.isTrue(0 != builder.length());
		return builder.toString();
	}

	public static String cleanCharater(String input, String[] cleaner) {
		if (null == cleaner || 0 == cleaner.length)
			return input;
		String ret = input;
		for (String cleanCharater : cleaner) {
			if (StringUtils.isBlank(cleanCharater))
				continue;
			ret = ret.replace(cleanCharater, Constants.EMPTY_STRING);
			ret = ret.replace(cleanCharater.toLowerCase(),
					Constants.EMPTY_STRING);
			ret = ret.replace(cleanCharater.toUpperCase(),
					Constants.EMPTY_STRING);
		}
		return ret;
	}

	public static List<String> joinCharater(String input, String[] joiner) {
		List<String> fragments = new ArrayList<String>();
		if (null == input)
			return fragments;
		if (null == joiner || 0 == joiner.length) {
			fragments.add(input);
			return fragments;
		}
		for (int i = 0; i < joiner.length; i++) {
			// 如果首先
			if (fragments.isEmpty()) {
				String joinCharater = joiner[i];
				if (StringUtils.isBlank(joinCharater))
					continue;
				List<String> splitors = splitIgnoreCase(input, joinCharater);
				// 如果没有拆分出来
				if (null == splitors || splitors.contains(input))
					continue;
				fragments.addAll(splitors);
				continue;
			}
			//
			if (i < joiner.length - 2) {
				joinCharaterWorker(fragments, joiner[i + 1]);
			}
		}
		if (fragments.isEmpty()) {
			fragments.add(input);
		}
		return fragments;
	}

	private static void joinCharaterWorker(List<String> fragments, String joiner) {
		List<String> fragmentsNext = new ArrayList<String>();
		for (String fragment : fragments) {
			List<String> splitors = splitIgnoreCase(fragment, joiner);
			if (null == splitors || 0 == splitors.size())
				continue;
			fragmentsNext.addAll(splitors);
		}
		if (!(fragmentsNext.isEmpty() || fragmentsNext.size() == fragments
				.size())) {
			fragments.clear();
			fragments.addAll(fragmentsNext);
		}

	}

	public static String getHump(String input, String[] cleaner, String[] joiner) {
		return CharacterUtils
				.toLowerCaseFirst(getPascal(input, cleaner, joiner));
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static List<String> splitIgnoreCase(String input, String splitor) {
		return split(input, splitor, true);
	}

	public static List<String> split(String input, String splitor) {
		return split(input, splitor, false);
	}

	private static List<String> split(String input, String splitor,
			boolean isIgnoreCase) {
		List<String> list = new ArrayList<String>();
		if (null == input || null == splitor
				|| Constants.EMPTY_STRING.equals(input)
				|| Constants.EMPTY_STRING.equals(splitor))
			return list;
		splitWorker(list, input, splitor, isIgnoreCase);
		return list;
	}

	private static void splitWorker(List<String> list, String input,
			String splitor, boolean isIgnoreCase) {
		int index = isIgnoreCase ? input.toUpperCase().indexOf(
				splitor.toUpperCase()) : input.indexOf(splitor);
		if (-1 != index) {
			String ret = input.substring(0, index);
			if (StringUtils.isNotBlank(ret))
				list.add(ret);
			splitWorker(list,
					input.substring(index + splitor.length(), input.length()),
					splitor, isIgnoreCase);
		} else {
			list.add(input);
		}
	}
}
