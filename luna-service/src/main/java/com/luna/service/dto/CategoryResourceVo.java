/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.dto;

import com.luna.dao.po.ResourcesCategory;
import com.luna.utils.classes.Page;

/**
 * @author laulyl
 * @date 2017年6月10日 上午8:15:01
 * @description
 */
public class CategoryResourceVo {
	private ResourcesCategory category;
	private Page<ResourcesVo> resourcePage;
	private String iteratorPage;

	/**
	 * @param category
	 * @param resourcePage
	 * @param iteratorPage
	 */
	public CategoryResourceVo(ResourcesCategory category, Page<ResourcesVo> resourcePage, String iteratorPage) {
		super();
		this.category = category;
		this.resourcePage = resourcePage;
		this.iteratorPage = iteratorPage;
	}

	public ResourcesCategory getCategory() {
		return category;
	}

	public void setCategory(ResourcesCategory category) {
		this.category = category;
	}

	public Page<ResourcesVo> getResourcePage() {
		return resourcePage;
	}

	public void setResourcePage(Page<ResourcesVo> resourcePage) {
		this.resourcePage = resourcePage;
	}

	public String getIteratorPage() {
		return iteratorPage;
	}

	public void setIteratorPage(String iteratorPage) {
		this.iteratorPage = iteratorPage;
	}

}
