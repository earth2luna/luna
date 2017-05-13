/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.catcher;

import java.util.List;

/**
 * @author laulyl
 * @date 2017年4月6日 下午12:03:18
 * @description
 */
public class CatchRuler {

	private String tryXPath;// 测试节点是否有效的xpath表达式
	private String getXPath;// 真正获取内容的xpath表达式
	private List<CatcherReplaceModel> replaceModels;
	private Integer handlerCode;// 处理编码
	private String breakValue;// 规则取到的值与此值相同则停止抓取
	private String indexOfFilter;// index of 过滤条件

//	public CatchRuler(String tryXPath, String getXPath, Integer replaceCode, String[] replaceTagNames,
//			String replacement, Integer handlerCode, String breakValue, String indexOfFilter) {
//		super();
//		this.tryXPath = tryXPath;
//		this.getXPath = getXPath;
//		this.handlerCode = handlerCode;
//		this.breakValue = breakValue;
//		this.indexOfFilter = indexOfFilter;
//		replaceModels = new ArrayList<CatcherReplaceModel>();
//		replaceModels.add(new CatcherReplaceModel(replaceCode, replaceTagNames, replacement));
//	}
	
	

	public CatchRuler(String tryXPath, String getXPath, List<CatcherReplaceModel> replaceModels, Integer handlerCode,
			String breakValue, String indexOfFilter) {
		super();
		this.tryXPath = tryXPath;
		this.getXPath = getXPath;
		this.replaceModels = replaceModels;
		this.handlerCode = handlerCode;
		this.breakValue = breakValue;
		this.indexOfFilter = indexOfFilter;
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

	public List<CatcherReplaceModel> getReplaceModels() {
		return replaceModels;
	}

	public void setReplaceModels(List<CatcherReplaceModel> replaceModels) {
		this.replaceModels = replaceModels;
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

	public String getBreakValue() {
		return breakValue;
	}

	public void setBreakValue(String breakValue) {
		this.breakValue = breakValue;
	}

	public String getIndexOfFilter() {
		return indexOfFilter;
	}

	public void setIndexOfFilter(String indexOfFilter) {
		this.indexOfFilter = indexOfFilter;
	}

}