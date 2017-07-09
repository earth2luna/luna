/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.catcher;

import java.util.List;

/**
 * @author laulyl
 * @date 2017年4月9日 下午7:37:58
 * @description
 */
public class CatcherIteratorRuler {

	private String contentXPath;// 内容路径选择器
	private List<CatchRuler> oneLevelContentTitleCatchRulers;// 一级内容标题
	private List<CatchRuler> twoLevelContentTitleCatchRulers;// 二级内容标题
	private List<CatchRuler> contentCatchRulers;// 内容
	private List<CatchRuler> contentPathCatchRulers;// 路径

	/**
	 * @return the contentXPath
	 */
	public String getContentXPath() {
		return contentXPath;
	}

	/**
	 * @param contentXPath
	 *            the contentXPath to set
	 */
	public void setContentXPath(String contentXPath) {
		this.contentXPath = contentXPath;
	}

	/**
	 * @return the oneLevelContentTitleCatchRulers
	 */
	public List<CatchRuler> getOneLevelContentTitleCatchRulers() {
		return oneLevelContentTitleCatchRulers;
	}

	/**
	 * @param oneLevelContentTitleCatchRulers
	 *            the oneLevelContentTitleCatchRulers to set
	 */
	public void setOneLevelContentTitleCatchRulers(List<CatchRuler> oneLevelContentTitleCatchRulers) {
		this.oneLevelContentTitleCatchRulers = oneLevelContentTitleCatchRulers;
	}

	/**
	 * @return the twoLevelContentTitleCatchRulers
	 */
	public List<CatchRuler> getTwoLevelContentTitleCatchRulers() {
		return twoLevelContentTitleCatchRulers;
	}

	/**
	 * @param twoLevelContentTitleCatchRulers
	 *            the twoLevelContentTitleCatchRulers to set
	 */
	public void setTwoLevelContentTitleCatchRulers(List<CatchRuler> twoLevelContentTitleCatchRulers) {
		this.twoLevelContentTitleCatchRulers = twoLevelContentTitleCatchRulers;
	}

	/**
	 * @return the contentCatchRulers
	 */
	public List<CatchRuler> getContentCatchRulers() {
		return contentCatchRulers;
	}

	/**
	 * @param contentCatchRulers
	 *            the contentCatchRulers to set
	 */
	public void setContentCatchRulers(List<CatchRuler> contentCatchRulers) {
		this.contentCatchRulers = contentCatchRulers;
	}

	/**
	 * @return the contentPathCatchRulers
	 */
	public List<CatchRuler> getContentPathCatchRulers() {
		return contentPathCatchRulers;
	}

	/**
	 * @param contentPathCatchRulers
	 *            the contentPathCatchRulers to set
	 */
	public void setContentPathCatchRulers(List<CatchRuler> contentPathCatchRulers) {
		this.contentPathCatchRulers = contentPathCatchRulers;
	}


}
