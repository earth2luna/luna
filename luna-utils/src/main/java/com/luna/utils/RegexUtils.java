/**
 * copyright@lyl
 */
package com.luna.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.Validate;

/**
 * @author lyl
 * @date 2016-4-1
 * @time 上午10:50:32
 * @desc 正则工具类
 */
public class RegexUtils {

	// 邮箱
	public static final String REGX_EMAIL = "^[\\w\\d-\\.\\+\\!]+@[\\w\\d-\\.\\+\\!]+$";

	// 电话
	public static final String REGX_MOBILE = "^(13|14|15|17|18)\\d{9}$";

	// 最少一个最多50个中文、英文或者数字
	public static final String REGX_CN_DIG_ENG_1_50 = "^[0-9a-zA-Z\u4e00-\u9fa5]{1,50}$";

	// 数字逗号分割
	public static final String REGX_DIG_COMMA = "^[0-9]+(,[0-9]+)*$";
	// 数字
	public static final String REGX_DIG = "^[0-9]+$";
	
	public static final String HTML_TAG="</?[^>]*>";

	// 验证是否是小数
	// input:验证参数值,p:总长度,s:小数长度,isPositive:是否有正数限制,allowNull:是否允许为null
	public static boolean isNumeric(String input, int p, int s, boolean isPositive, boolean allowNull) {
		int digital = 0;
		// 0==p 代表不限制整数部分长度
		if (0 != p) {
			Validate.isTrue(p > 0 && p > s && s >= 0, "无效的小数点校验规则");
			digital = p - s;
		}
		String ret = null;
		// joiner isPositive part
		if (!isPositive) {
			ret = LangUtils.append("-?");
		}
		// joiner digital part
		if (1 < digital)
			ret = LangUtils.append(ret, "([1-9][0-9]{1,", digital - 1, "}|0)");
		else if (1 == digital)
			ret = LangUtils.append(ret, "[0-9]");
		else
			ret = LangUtils.append(ret, "([1-9][0-9]*|0)");
		// joiner numeric part
		if (0 < s) {
			ret = LangUtils.append(ret, "(\\.[0-9]{1,", s, "})?");
		}
		// validate result
		return matches(input, LangUtils.append("^", ret, "$"), allowNull);
	}

	// 验证是否是整数
	public static boolean isDigital(String input, int maxLength, boolean isPositive, boolean allowNull) {
		return isNumeric(input, maxLength, 0, isPositive, allowNull);
	}

	/**
	 * 匹配方法
	 * 
	 * @param input
	 *            输入值
	 * @param regx
	 *            正则表达式
	 * @param allowNull
	 *            是否允许为空
	 * @return true or false
	 */
	public static boolean matches(String input, String regx, boolean allowNull) {
		Validate.notNull(regx);
		return (allowNull && null == input) || null != input && input.matches(regx);
	}

	// 验证身份证
	// 身份证规则 18位=六位数字地址码+八位数字出生日期码+三位数字顺序码+一位数字校验码
	public static boolean isIdentifier(String identifier, boolean strict, boolean allowNull) {
		if (null == identifier) {
			return allowNull;
		}
		// 18位
		if (18 != identifier.length())
			return false;
		// 六位数字地址码
		if (!matches(identifier.substring(0, 6),
				"^(11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65|71|81|82|886|852|853)(\\d{4}|\\d{3})$",
				false))
			return false;
		// 八位数字出生日期码
		String mdate8 = identifier.substring(6, 14);
		if (!matches(mdate8, "^\\d{8}$", false))
			return false;

		// 如果是严格校验
		if (strict) {
			Date date8 = null;
			try {
				date8 = new SimpleDateFormat("yyyyMMdd").parse(mdate8);
			} catch (ParseException e) {
				return false;
			}
			// 身份证拒绝120岁以上的老人与未满18岁的少年
			if (null == date8 || DateUtils.isBeforeOfYear(date8, 120) || !DateUtils.isBeforeOfYear(date8, 18))
				return false;
		}

		// 三位数字顺序码
		if (!matches(identifier.substring(14, 17), "^\\d{3}$", false))
			return false;

		// 一位数字校验码
		int[] factors = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
		String prefix17 = identifier.substring(0, 17);
		long total = 0;
		for (int i = 0; i < prefix17.length(); i++) {
			total += Integer.valueOf(String.valueOf(prefix17.charAt(i))) * factors[i];
		}
		String[] factorMod = new String[] { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
		Long mod = total % 11;
		if (mod > factorMod.length - 1)
			return false;

		return factorMod[mod.intValue()].equals(identifier.substring(17));

	}

	public static boolean isIdentifier(String identifier, boolean allowNull) {
		return isIdentifier(identifier, false, allowNull);
	}

}
