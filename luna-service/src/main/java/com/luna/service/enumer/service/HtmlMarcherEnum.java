/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.enumer.service;

import com.luna.utils.AssertUtils;
import com.luna.utils.LangUtils;

/**
 * @author laulyl
 * @date 2017年4月6日 下午1:14:29
 * @description
 */
public enum HtmlMarcherEnum {

	TAG_ATTRIBUTE(1, "标签属性") {
		@Override
		public String getRegex(String... tagNames) {
			return tagAttributeRegex(tagNames);
		}
	},
	TAG_NAME(2, "标签名称") {
		@Override
		public String getRegex(String... tagNames) {
			return tagNameRegex(tagNames);
		}
	},
	TAG(3, "标签名称+属性") {
		@Override
		public String getRegex(String... tagNames) {
			return tagRegex(tagNames);
		}
	},
	TAG_INCLUDE_HTML(4, "标签属性+名称+包含在标签里面的任何内容") {
		@Override
		public String getRegex(String... tagNames) {
			return tagIncludeHtmlRegex(tagNames);
		}
	};

	public static HtmlMarcherEnum get(Integer code) {
		if (null == code)
			return null;
		HtmlMarcherEnum[] enums = HtmlMarcherEnum.values();
		for (int i = 0; i < enums.length; i++) {
			HtmlMarcherEnum ret = enums[i];
			if (LangUtils.equals(ret.getCode(), code)) {
				return ret;
			}
		}
		return null;
	}

	public static String getName(int code) {
		HtmlMarcherEnum ret = get(code);
		return null == ret ? null : ret.getName();
	}

	public abstract String getRegex(String... tagNames);

	// 标签属性
	private static String tagAttributeRegex(String... tagNames) {
		String regx = getTagNameRegex(tagNames);
		return LangUtils.append("(?<=<", regx, ")[^>]*");
	}

	// 标签名称
	private static String tagNameRegex(String... tagNames) {
		String regx = getTagNameRegex(tagNames);
		return LangUtils.append("(?<=</?)", regx, "(?=[^>]*>)");
	}

	// 标签名称+属性
	private static String tagRegex(String... tagNames) {
		String regx = getTagNameRegex(tagNames);
		return LangUtils.append("</?", regx, "[^>]*>");
	}

	// 标签属性+名称+包含在标签里面的任何内容
	private static String tagIncludeHtmlRegex(String... tagNames) {
		String regex = getTagNameRegex(tagNames);
		return LangUtils.append("<" + regex + "[^>]*>.*", "</" + regex + ">");
	}

	private static String getTagNameRegex(String... tagNames) {
		AssertUtils.isTrue(!(null == tagNames || 0 == tagNames.length), "invalid tagNames parameter");
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < tagNames.length; i++) {
			builder.append(tagNames[i]);
			if (i < tagNames.length - 1) {
				builder.append("|");
			}
		}
		return tagNames.length > 1 ? LangUtils.append("(", builder, ")") : builder.toString();
	}

	private int code;
	private String name;

	private HtmlMarcherEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
