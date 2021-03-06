/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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
import com.luna.service.dto.HomeVo;
import com.luna.service.dto.ResourceSolrVo;
import com.luna.service.sync.SynchronizedResource;
import com.luna.utils.LangUtils;
import com.luna.utils.classes.Page;
import com.luna.utils.page.PageUtils;

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
				solrVo.setRequestUrl(ResourcesUtils.getWebResourcesPath(Configure.getResourceRelativePath(), solrVo));
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
	public Page<ResourceSolrVo> query(String query, Integer pageNow, Integer pageSize) {
		if (!(StringUtils.isEmpty(query) || query.matches("[\\*\\:]*"))) {
			try {
				// fields
				String primaryField = "id";
				String queryField = "suggest";
				String keywords = resourceSolrComponet.getSeggetText(
						LangUtils.subtring(LangUtils.trim(query), Constants.QUERY_STRING_MAX_LENGTH), true);

				// set sorl query
				SolrQueryPage solrQuery = new SolrQueryPage(queryField + ":" + keywords, pageNow, pageSize);
				resourceSolrComponet.setCiphertext(solrQuery);
				// set highlight
				solrQuery.setHighlight(true);
				solrQuery.addHighlightField(queryField);
				solrQuery.setHighlightFragsize(Constants.SOLR_RESULT_SUMMARY_MAX_LENGTH);
				solrQuery.setHighlightSimplePre("<span class=\"front-color-red\">");
				solrQuery.setHighlightSimplePost("</span>");
				solrQuery.set("hl.preserveMulti", true);
				// set sort
				solrQuery.addSort("score", ORDER.desc);
				solrQuery.addSort("createTime", ORDER.desc);
				solrQuery.addSort("sourceDate", ORDER.desc);
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
							ResourcesUtils.getWebResourcesPath(Configure.getResourceRelativePath(), solrVo));
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
						solrVo.setHighlight(highlight + "...");
					}

					page.addList(solrVo);
				}
				return page;
			} catch (Exception e) {
				LOGGER.error("query error:", e);
			}
		}
		return query(pageNow, pageSize);
	}

	public Page<ResourceSolrVo> query(Integer pageNow, Integer pageSize) {
		try {
			SolrQueryPage solrQuery = new SolrQueryPage("*:*", pageNow, pageSize);
			solrQuery.addSort("createTime", ORDER.desc);
			solrQuery.addSort("sourceDate", ORDER.desc);
			Page<ResourceSolrVo> page = resourceSolrComponet.query(solrQuery, ResourceSolrVo.class);
			evalResource(page);
			return page;
		} catch (Exception e) {
			LOGGER.error("query error:", e);
		}
		return null;
	}

	public Page<ResourceSolrVo> SimpleQuery(String query, Integer pageNow, Integer pageSize) {
		try {

			SolrQueryPage solrQuery = getQuery(query, pageNow, pageSize);
			solrQuery.addSort("createTime", ORDER.desc);
			solrQuery.addSort("sourceDate", ORDER.desc);
			Page<ResourceSolrVo> page = resourceSolrComponet.query(solrQuery, ResourceSolrVo.class);
			if (CollectionUtils.isEmpty(page.getList())) {
				solrQuery.setQuery("*");
				page = resourceSolrComponet.query(solrQuery, ResourceSolrVo.class);
			}
			evalResource(page);
			return page;
		} catch (Exception e) {
			LOGGER.error("query error:", e);
		}
		return null;
	}

	private SolrQueryPage getQuery(String query, Integer pageNow, Integer pageSize) {
		SolrQueryPage solrQuery = null;
		if (!(StringUtils.isEmpty(query) || query.matches("[\\*\\:]*"))) {
			String keywords = resourceSolrComponet
					.getSeggetText(LangUtils.subtring(LangUtils.trim(query), Constants.QUERY_STRING_MAX_LENGTH), true);
			solrQuery = new SolrQueryPage("suggest:" + keywords, pageNow, pageSize);
		} else {
			solrQuery = new SolrQueryPage("*", pageNow, pageSize);
		}
		return solrQuery;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.ResourcesSolrService#getHomeVo()
	 */
	@Override
	public HomeVo getHomeVo(String query, Long pageNow) {
		int defaultPageSize = Constants.HOME_SEARCH_ITEMS_PAGE_SIZE;
		Long defaultPageNow = LangUtils.defaultValue(pageNow, 1L);
		
		String defaultQuery = LangUtils.defaultValue(query, "*:*");
		Page<ResourceSolrVo> page = query(defaultQuery, defaultPageNow.intValue(), defaultPageSize);
		// 获取分页迭代内容
		String route = LangUtils.trim(query);
		String defaultRoute = StringUtils.isNotBlank(route)
				? LangUtils.append(Configure.getThisWebDomain(), "/query/", route)
				: Configure.getThisWebDomain();
		String iteratorPage = PageUtils.evaluate(page.getCount(), defaultPageNow, Long.valueOf(defaultPageSize),
				defaultRoute, LangUtils.append("#", "a"));
		return new HomeVo(page, iteratorPage, query);
	}
}
