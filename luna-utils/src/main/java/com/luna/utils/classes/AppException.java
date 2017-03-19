/**
 * COPYRIGHT@LAULYL
 */
package com.luna.utils.classes;

import com.luna.utils.LangUtils;

/**
 * @author laulyl
 * @date 2017年3月16日 下午11:00:06
 * @description
 */
public class AppException extends RuntimeException {

	private static final long serialVersionUID = 1144824946461317904L;

	private Integer code;

	public AppException() {
	}

	public AppException(String message) {
		super(message);
	}

	public AppException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public InvokeVo getInvokeVo() {
		return new InvokeVo(getMessage(), null, LangUtils.defaultValue(code, 0));
	}

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

}
