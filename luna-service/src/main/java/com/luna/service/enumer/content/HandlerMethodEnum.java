/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.enumer.content;

import com.luna.utils.LangUtils;

/**
 * @author laulyl
 * @date 2017年3月14日 下午11:08:47
 * @description
 */
public enum HandlerMethodEnum {

	P(0, "段落"), HTML(1, "完整的THML代码"), HTML_SEGMENT(2, "HTML代码片段"), IMAGE(3, "图片");

	public static HandlerMethodEnum get(Integer code) {
		if (null == code)
			return null;
		HandlerMethodEnum[] enums = HandlerMethodEnum.values();
		for (int i = 0; i < enums.length; i++) {
			HandlerMethodEnum ret = enums[i];
			if (LangUtils.equals(ret.getCode(), code)) {
				return ret;
			}
		}
		return null;
	}

	public static String getName(int code) {
		HandlerMethodEnum ret = get(code);
		return null == ret ? null : ret.getName();
	}

	private int code;
	private String name;

	/**
	 * @param code
	 * @param name
	 */
	private HandlerMethodEnum(int code, String name) {
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
