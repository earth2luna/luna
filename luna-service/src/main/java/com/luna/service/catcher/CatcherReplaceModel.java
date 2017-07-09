/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.catcher;

/**
 * @author laulyl
 * @date 2017年5月8日 下午2:25:37
 * @description
 */
public class CatcherReplaceModel {

	private Integer replaceCode;// 替换规则编码
	private String replaceTagNames;// 要替换的标签名称,多个以逗号分割
	private String replacement;// 替换内容
	private String indexOfcondition;// 替换条件

	public CatcherReplaceModel() {
	}
	
	public CatcherReplaceModel(Integer replaceCode, String replaceTagNames, String replacement) {
		super();
		this.replaceCode = replaceCode;
		this.replaceTagNames = replaceTagNames;
		this.replacement = replacement;
	}

	public Integer getReplaceCode() {
		return replaceCode;
	}

	public void setReplaceCode(Integer replaceCode) {
		this.replaceCode = replaceCode;
	}

	public String getReplaceTagNames() {
		return replaceTagNames;
	}

	public void setReplaceTagNames(String replaceTagNames) {
		this.replaceTagNames = replaceTagNames;
	}

	public String getReplacement() {
		return replacement;
	}

	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}

	public String getIndexOfcondition() {
		return indexOfcondition;
	}

	public void setIndexOfcondition(String indexOfcondition) {
		this.indexOfcondition = indexOfcondition;
	}

	

}
