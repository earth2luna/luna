/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.catcher;

/**
 * @author laulyl
 * @date 2017年5月7日 下午10:19:30
 * @description
 */
public class CatcherSubModel {

	private String value;
	
	private Integer handlerCode;
	
	private boolean ifBreak;
	
	
	public CatcherSubModel(String value, Integer handlerCode, boolean ifBreak) {
		super();
		this.value = value;
		this.handlerCode = handlerCode;
		this.ifBreak = ifBreak;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getHandlerCode() {
		return handlerCode;
	}

	public void setHandlerCode(Integer handlerCode) {
		this.handlerCode = handlerCode;
	}

	public boolean isIfBreak() {
		return ifBreak;
	}

	public void setIfBreak(boolean ifBreak) {
		this.ifBreak = ifBreak;
	}
	
	
}
