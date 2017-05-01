/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luna.dao.mapper.IResourcesMapper;
import com.luna.service.componet.SolrComponet;
import com.luna.service.componet.SolrQueryPage;
import com.luna.service.componet.SuggetVo;
import com.luna.service.data.utils.Configure;
import com.luna.service.data.utils.Constants;
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

	private void evalResource(Page<ResourceSolrVo> page) {
		if (!(null == page || page.getList().isEmpty())) {
			Iterator<ResourceSolrVo> iterator = page.getList().iterator();
			while (iterator.hasNext()) {
				ResourceSolrVo solrVo = iterator.next();
				solrVo.setRequestUrl(
						ResourcesUtils.getWebResourcesPath(Configure.getResourceRelativePath(), solrVo.getId()));
				solrVo.setHighlight(solrVo.getSummary());
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
		if (!(StringUtils.isEmpty(query) || query.matches("\\**"))) {
			try {
				// fields
				String primaryField = "id";
				String queryField = "suggest";

				// set sorl query
				SolrQueryPage solrQuery = new SolrQueryPage(queryField + ":*"
						+ LangUtils.subtring(LangUtils.trim(query), Constants.QUERY_STRING_MAX_LENGTH) + "*", pageNow,
						null);
				// set highlight
				solrQuery.setHighlight(true);
				solrQuery.addHighlightField(queryField);
				solrQuery.setHighlightFragsize(Constants.SOLR_RESULT_SUMMARY_MAX_LENGTH);
				solrQuery.setHighlightSimplePre("<span class=\"front-color-red\">");
				solrQuery.setHighlightSimplePost("</span>");
				solrQuery.set("hl.preserveMulti", true);
				// set sort
				solrQuery.addSort("sourceDate", ORDER.desc);
				solrQuery.addSort("createTime", ORDER.desc);
				// get response
				QueryResponse queryResponse = resourceSolrComponet.getSolrClient().query(solrQuery);
				SolrDocumentList solrDocumentList = queryResponse.getResults();
				Page<ResourceSolrVo> page = new Page<ResourceSolrVo>();
				page.setCount(solrDocumentList.getNumFound());
				page.setPageNow(solrQuery.getPageNow());
				page.setPageSize(solrQuery.getPageSize());
				// get highlight and results
				Map<String, Map<String, List<String>>> highlightMap = queryResponse.getHighlighting();

				for (SolrDocument doc : solrDocumentList) {
					ResourceSolrVo solrVo = resourceSolrComponet.getSolrClient().getBinder()
							.getBean(ResourceSolrVo.class, doc);
					// set request url
					solrVo.setRequestUrl(
							ResourcesUtils.getWebResourcesPath(Configure.getResourceRelativePath(), solrVo.getId()));
					// set highlight
					List<String> summarys = highlightMap.get(doc.getFieldValue(primaryField).toString())
							.get(queryField);
					String highlight = StringUtils.join(summarys, " ");
					if (StringUtils.isEmpty(highlight)
							|| highlight.length() < Constants.SOLR_RESULT_SUMMARY_MAX_LENGTH) {
						solrVo.setHighlight(LangUtils.subtringDefaultAppender(
								LangUtils.append(highlight, " - ", solrVo.getSummary()),
								Constants.SOLR_RESULT_SUMMARY_MAX_LENGTH));
					} else {
						solrVo.setHighlight(highlight+"...");
					}

					page.addList(solrVo);
				}
				return page;
			} catch (Exception e) {
				LOGGER.error("query error:", e);
			}
		}
		return query(pageNow);
	}

	public Page<ResourceSolrVo> query(Integer pageNow) {
		try {
			SolrQueryPage solrQuery = new SolrQueryPage("*:*", pageNow, null);
			solrQuery.addSort("sourceDate", ORDER.desc);
			solrQuery.addSort("createTime", ORDER.desc);
			Page<ResourceSolrVo> page = resourceSolrComponet.query(solrQuery, ResourceSolrVo.class);
			evalResource(page);
			return page;
		} catch (Exception e) {
			LOGGER.error("query error:", e);
		}
		return null;
	}

}
