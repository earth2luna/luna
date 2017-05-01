/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.componet;

import org.apache.solr.client.solrj.SolrQuery;

import com.luna.service.data.utils.Constants;
import com.luna.utils.LangUtils;

/**
 * @author laulyl
 * @date 2017年4月30日 上午10:43:58
 * @description
 */
public class SolrQueryPage extends SolrQuery {

	private static final long serialVersionUID = -44399164121181024L;

	private Integer pageNow;
	private Integer pageSize;

	public SolrQueryPage(String query, Integer pageNow, Integer pageSize) {
		super();
		this.setQuery(query);
		set(pageNow, pageSize);
	}

	private void set(Integer pageNow, Integer pageSize) {
		int defaultPageNow = LangUtils.defaultValue(pageNow, 1);
		int defaultPageSize = LangUtils.defaultValue(pageSize, Constants.HOME_SEARCH_ITEMS_PAGE_SIZE);
		this.pageNow = defaultPageNow;
		this.pageSize = defaultPageSize;
		this.setStart((defaultPageNow - 1) * defaultPageSize);
		this.setRows(defaultPageSize);
	}

	public Integer getPageNow() {
		return pageNow;
	}

	public Integer getPageSize() {
		return pageSize;
	}

}
