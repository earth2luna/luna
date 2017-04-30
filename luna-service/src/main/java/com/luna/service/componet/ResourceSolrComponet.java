/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.componet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author laulyl
 * @date 2017年4月29日 下午8:44:43
 * @description
 */
@Component
public class ResourceSolrComponet extends SolrComponet {

	@Value("${solr.core.resource.url}")
	private String connectUrl;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.componet.SolrComponet#getConnectUrl()
	 */
	@Override
	public String getConnectUrl() {
		return connectUrl;
	}
}
