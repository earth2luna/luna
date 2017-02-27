/**
 * copyright@lyl
 *lyl-common.org.lyl.common.poi.utils.DateUtils.java.java
 */
package com.luna.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

/**
 * @author lyl 2016-1-26
 * @description
 */
public class DateUtils {

	public static final String DATE_PATTERN_0 = "yyyy年MM月dd日 HH:mm:ss";

	public static final String DATE_PATTERN_1 = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_PATTERN_2 = "yyyy-MM-dd";

	public static String getCurrentDateFormat(String pattern) {
		return getDateFormat(new Date(), pattern);
	}

	public static String getDateFormat(Date date, String pattern) {
		if (null == date)
			return null;
		Validate.notNull(pattern);
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
		dateFormat.setTimeZone(TimeZone.getDefault());
		return dateFormat.format(date);
	}

	public static String getDateFormat(Calendar calendar, String pattern) {
		return getDateFormat(calendar.getTime(), pattern);
	}

	public static String getDateFormat(Object date, String pattern) {
		if (date instanceof Date) {
			return getDateFormat((Date) date, pattern);
		} else if (date instanceof Calendar) {
			return getDateFormat((Calendar) date, pattern);
		}
		throw new RuntimeException("the input object is not a class of Date or Calendar");
	}

	public static Calendar getDiffDate(Calendar calendar, int number, int field, boolean before) {
		Validate.notNull(calendar);
		if (before)
			calendar.add(field, -number);
		else
			calendar.add(field, number);
		return calendar;
	}

	public static Calendar getDiffDate(Date date, int number, int field, boolean before) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getDiffDate(calendar, number, field, before);
	}

	public static Date parse(String date, String pattern) {
		if (StringUtils.isBlank(date))
			return null;
		Validate.notNull(pattern);
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isBeforeOfYear(Date date, int year) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date);
		Calendar calendar = Calendar.getInstance();
		int beforeYear = calendar.get(Calendar.YEAR) - year;
		calendar.set(Calendar.YEAR, beforeYear);
		return calendar1.before(calendar);
	}

}
