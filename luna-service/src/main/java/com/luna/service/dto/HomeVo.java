/**
 * copyright@laulyl
 */
package com.luna.service.dto;

import com.luna.utils.classes.Page;

/**
 * @author laulyl
 * @date 2017年7月15日 下午7:36:32
 * @desction
 */
public class HomeVo {

	private Page<ResourceSolrVo> page;
	private String iteratorPage;
	private String query;

	public HomeVo(Page<ResourceSolrVo> page, String iteratorPage, String query) {
		super();
		this.page = page;
		this.iteratorPage = iteratorPage;
		this.query = query;
	}

	/**
	 * @return the page
	 */
	public Page<ResourceSolrVo> getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(Page<ResourceSolrVo> page) {
		this.page = page;
	}

	/**
	 * @return the iteratorPage
	 */
	public String getIteratorPage() {
		return iteratorPage;
	}

	/**
	 * @param iteratorPage
	 *            the iteratorPage to set
	 */
	public void setIteratorPage(String iteratorPage) {
		this.iteratorPage = iteratorPage;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query
	 *            the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

}
