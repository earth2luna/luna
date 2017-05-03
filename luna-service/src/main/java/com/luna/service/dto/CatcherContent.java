/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.dto;

import com.luna.dao.po.ResourcesContent;

/**
 * @author laulyl
 * @date 2017年4月9日 下午6:49:04
 * @description
 */
public class CatcherContent extends ResourcesContent {

	private static final long serialVersionUID = -4945752012527013315L;
	
	private Long levelId;
	private Long parentLevelId;
	
	/**
	 * @return the levelId
	 */
	public Long getLevelId() {
		return levelId;
	}
	/**
	 * @param levelId the levelId to set
	 */
	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}
	/**
	 * @return the parentLevelId
	 */
	public Long getParentLevelId() {
		return parentLevelId;
	}
	/**
	 * @param parentLevelId the parentLevelId to set
	 */
	public void setParentLevelId(Long parentLevelId) {
		this.parentLevelId = parentLevelId;
	}

	
}
