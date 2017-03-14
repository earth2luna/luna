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
public enum CreatorEnum {

	LAULYL(1, "laulyl");

	public static CreatorEnum get(Long code) {
		if (null == code)
			return null;
		CreatorEnum[] enums = CreatorEnum.values();
		for (int i = 0; i < enums.length; i++) {
			CreatorEnum ret = enums[i];
			if (LangUtils.equals(ret.getCode(), code)) {
				return ret;
			}
		}
		return null;
	}

	public static String getName(Long code) {
		CreatorEnum ret = get(code);
		return null == ret ? null : ret.getName();
	}

	private long code;
	private String name;

	/**
	 * @param code
	 * @param name
	 */
	private CreatorEnum(long code, String name) {
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
