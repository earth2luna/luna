/**
 * copyright@lyl
 *lyl-base-common Maven Webapp.com.lylframework.base.common.enumeration.CharsetEnum.java.java
 */
package com.luna.utils.enm;

/**
 * @author lyl 2015-10-30
 * @description
 */
public enum CharsetEnum {

	UTF8("UTF-8"), GBK("GBK"), ISO88591("ISO-8859-1");
	private String charsetName;

	private CharsetEnum(String charsetName) {
		this.charsetName = charsetName;
	}

	public String getCharsetName() {
		return charsetName;
	}

	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}

}
