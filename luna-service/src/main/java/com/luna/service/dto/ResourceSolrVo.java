/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.dto;

import com.luna.service.componet.ResourceSolr;

/**
 * @author laulyl
 * @date 2017年5月1日 上午9:42:47
 * @description
 */
public class ResourceSolrVo extends ResourceSolr {
	

	private String requestUrl;

	/**
	 * @return the requestUrl
	 */
	public String getRequestUrl() {
		return requestUrl;
	}

	/**
	 * @param requestUrl
	 *            the requestUrl to set
	 */
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

}
