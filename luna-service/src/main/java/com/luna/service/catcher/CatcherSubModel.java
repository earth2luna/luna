/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.catcher;

import java.util.List;

/**
 * @author laulyl
 * @date 2017年5月7日 下午10:19:30
 * @description
 */
public class CatcherSubModel {

	private List<String> values;

	private String value;// 一个value 已经不能完成多个图片的问题

	private Integer handlerCode;

	private boolean ifBreak;

	private boolean ifFilter;

	public CatcherSubModel(String value, Integer handlerCode, boolean ifBreak, boolean ifFilter) {
		super();
		this.value = value;
		this.handlerCode = handlerCode;
		this.ifBreak = ifBreak;
		this.ifFilter = ifFilter;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
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

	public boolean isIfFilter() {
		return ifFilter;
	}

	public void setIfFilter(boolean ifFilter) {
		this.ifFilter = ifFilter;
	}

}
