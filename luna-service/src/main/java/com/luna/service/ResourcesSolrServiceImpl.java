/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luna.dao.mapper.IResourcesMapper;
import com.luna.service.componet.SolrComponet;
import com.luna.service.componet.SolrQueryPage;
import com.luna.service.componet.SuggetVo;
import com.luna.service.data.utils.Configure;
import com.luna.service.data.utils.ResourcesUtils;
import com.luna.service.dto.ResourceSolrVo;
import com.luna.service.sync.SynchronizedResource;
import com.luna.utils.LangUtils;
import com.luna.utils.classes.Page;

/**
 * @author laulyl
 * @date 2017年4月24日 下午11:00:43
 * @description
 */
@Service
public class ResourcesSolrServiceImpl implements ResourcesSolrService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ResourcesSolrServiceImpl.class);

	@Autowired
	private IResourcesMapper resourcesMapper;

	@Autowired
	private SolrComponet resourceSolrComponet;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.ResourcesSolrService#synchronizedAll()
	 */
	@Override
	public void synchronizedAll() {
		new SynchronizedResource(resourcesMapper, resourceSolrComponet).synchronizedAll(5);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.ResourcesSolrService#delete(java.lang.String)
	 */
	@Override
	public void delete(String deletedIds) {
		if (StringUtils.isEmpty(deletedIds)) {
			resourceSolrComponet.deleteAll();
		} else {
			resourceSolrComponet.deleteById(deletedIds);
		}
		resourceSolrComponet.commit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.ResourcesSolrService#sugget(java.lang.String)
	 */
	@Override
	public List<SuggetVo> sugget(String query) {
		return resourceSolrComponet.querySugget(query);
	}

	private void evalResourceRequestPath(Page<ResourceSolrVo> page) {
		if (!(null == page || page.getList().isEmpty())) {
			Iterator<ResourceSolrVo> iterator = page.getList().iterator();
			while (iterator.hasNext()) {
				ResourceSolrVo solrVo = iterator.next();
				solrVo.setRequestUrl(
						ResourcesUtils.getWebResourcesPath(Configure.getResourceRelativePath(), solrVo.getId()));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.ResourcesSolrService#search(java.lang.String)
	 */
	@Override
	public Page<ResourceSolrVo> query(String query, Integer pageNow) {
		if (StringUtils.isNotEmpty(query)) {
			try {
				SolrQueryPage solrQuery = new SolrQueryPage("suggest:*" + LangUtils.trim(query) + "*", pageNow, null);
				solrQuery.addSort("sourceDate", ORDER.desc);
				solrQuery.addSort("createTime", ORDER.desc);
				Page<ResourceSolrVo> page = resourceSolrComponet.query(solrQuery, ResourceSolrVo.class);
				evalResourceRequestPath(page);
				return page;
			} catch (Exception e) {
				LOGGER.error("query error:", e);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.ResourcesSolrService#query()
	 */
	@Override
	public Page<ResourceSolrVo> query() {
		return query("*", 1);
	}

}
