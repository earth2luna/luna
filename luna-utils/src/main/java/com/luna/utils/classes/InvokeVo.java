/**
 * COPYRIGHT@LAULYL
 */
package com.luna.utils.classes;

/**
 * @author laulyl
 * @date 2017年3月16日 下午10:12:35
 * @description
 */
public class InvokeVo {
	
	private String message;
	private Object data;
	private int code;

	public InvokeVo() {
	}

	public InvokeVo(String message, Object data, int code) {
		super();
		this.message = message;
		this.data = data;
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

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		this.data = data;
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

}
