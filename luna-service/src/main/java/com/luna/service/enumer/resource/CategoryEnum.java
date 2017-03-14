/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.enumer.resource;

import com.luna.utils.LangUtils;

/**
 * @author laulyl
 * @date 2017年3月14日 下午11:08:47
 * @description
 */
public enum CategoryEnum {

	TECHNOLOGY(1, "技术"), SENTIMENT(2, "感悟");

	public static CategoryEnum get(Long code) {
		if (null == code)
			return null;
		CategoryEnum[] enums = CategoryEnum.values();
		for (int i = 0; i < enums.length; i++) {
			CategoryEnum ret = enums[i];
			if (LangUtils.equals(ret.getCode(), code)) {
				return ret;
			}
		}
		return null;
	}

	public static String getName(Long code) {
		CategoryEnum ret = get(code);
		return null == ret ? null : ret.getName();
	}

	private long code;
	private String name;

	/**
	 * @param code
	 * @param name
	 */
	private CategoryEnum(long code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * @return the code
	 */
	public long getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(long code) {
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
