/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.catcher;

/**
 * @author laulyl
 * @date 2017年4月6日 下午12:03:18
 * @description
 */
public class CatchRuler {

	private String tryXPath;// 测试节点是否有效的xpath表达式
	private String getXPath;// 真正获取内容的xpath表达式
	private Integer replaceCode;// 替换规则编码
	private String[] replaceTagNames;// 要替换的标签名称
	private String replacement;// 替换内容
	private Integer handlerCode;// 处理编码

	public CatchRuler(String tryXPath, String getXPath, Integer replaceCode, String[] replaceTagNames,
			String replacement, Integer handlerCode) {
		super();
		this.tryXPath = tryXPath;
		this.getXPath = getXPath;
		this.replaceCode = replaceCode;
		this.replaceTagNames = replaceTagNames;
		this.replacement = replacement;
		this.handlerCode = handlerCode;
	}

	/**
	 * @return the tryXPath
	 */
	public String getTryXPath() {
		return tryXPath;
	}

	/**
	 * @param tryXPath
	 *            the tryXPath to set
	 */
	public void setTryXPath(String tryXPath) {
		this.tryXPath = tryXPath;
	}

	/**
	 * @return the getXPath
	 */
	public String getGetXPath() {
		return getXPath;
	}

	/**
	 * @param getXPath
	 *            the getXPath to set
	 */
	public void setGetXPath(String getXPath) {
		this.getXPath = getXPath;
	}

	/**
	 * @return the replaceCode
	 */
	public Integer getReplaceCode() {
		return replaceCode;
	}

	/**
	 * @param replaceCode
	 *            the replaceCode to set
	 */
	public void setReplaceCode(Integer replaceCode) {
		this.replaceCode = replaceCode;
	}

	/**
	 * @return the replaceTagNames
	 */
	public String[] getReplaceTagNames() {
		return replaceTagNames;
	}

	/**
	 * @param replaceTagNames
	 *            the replaceTagNames to set
	 */
	public void setReplaceTagNames(String[] replaceTagNames) {
		this.replaceTagNames = replaceTagNames;
	}

	/**
	 * @return the replacement
	 */
	public String getReplacement() {
		return replacement;
	}

	/**
	 * @param replacement
	 *            the replacement to set
	 */
	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}

	/**
	 * @return the handlerCode
	 */
	public Integer getHandlerCode() {
		return handlerCode;
	}

	/**
	 * @param handlerCode
	 *            the handlerCode to set
	 */
	public void setHandlerCode(Integer handlerCode) {
		this.handlerCode = handlerCode;
	}

}
