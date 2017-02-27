/**
 * copyright@www.jd.com
 */
package com.luna.utils.node;

import com.luna.utils.infce.IInputOutput;

/**
 * @author lyl
 * @date 2016-7-16
 * @time 下午5:48:34
 * @desc 配置变量
 */
public class NodeVariable {

	private int casecade; // 深度
	private int cascadeNumber; // 深度下展示数量
	private IInputOutput<INode, INode> inputOutput;// 转换内容
	private boolean sortAllCaseCade;// 是否全部排序后再截取深度数量

	/**
	 * @return the casecade
	 */
	public int getCasecade() {
		return casecade;
	}

	/**
	 * @param casecade
	 *            the casecade to set
	 */
	public void setCasecade(int casecade) {
		this.casecade = casecade;
	}

	/**
	 * @return the cascadeNumber
	 */
	public int getCascadeNumber() {
		return cascadeNumber;
	}

	/**
	 * @param cascadeNumber
	 *            the cascadeNumber to set
	 */
	public void setCascadeNumber(int cascadeNumber) {
		this.cascadeNumber = cascadeNumber;
	}

	/**
	 * @return the sortAllCaseCade
	 */
	public boolean isSortAllCaseCade() {
		return sortAllCaseCade;
	}

	/**
	 * @param sortAllCaseCade
	 *            the sortAllCaseCade to set
	 */
	public void setSortAllCaseCade(boolean sortAllCaseCade) {
		this.sortAllCaseCade = sortAllCaseCade;
	}

	/**
	 * @return the inputOutput
	 */
	public IInputOutput<INode, INode> getInputOutput() {
		return inputOutput;
	}

	/**
	 * @param inputOutput
	 *            the inputOutput to set
	 */
	public void setInputOutput(IInputOutput<INode, INode> inputOutput) {
		this.inputOutput = inputOutput;
	}

}
