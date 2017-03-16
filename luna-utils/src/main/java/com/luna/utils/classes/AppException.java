/**
 * COPYRIGHT@LAULYL
 */
package com.luna.utils.classes;

/**
 * @author laulyl
 * @date 2017年3月16日 下午11:00:06
 * @description
 */
public class AppException extends RuntimeException {

	private static final long serialVersionUID = 1144824946461317904L;

	private Integer code;
	private String message;

	public AppException(Integer code, String message) {
		super(message);
		this.code = code;
		this.message = message;

	}

	public InvokeVo getInvokeVo() {
		return new InvokeVo(message, null, code);
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

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
