/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.componet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SuggesterResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.InitializingBean;

import com.luna.security.Configuration;
import com.luna.utils.LangUtils;
import com.luna.utils.classes.Page;

/**
 * @author laulyl
 * @date 2017年4月29日 上午9:26:58
 * @description
 */

public abstract class SolrComponet implements InitializingBean {

	private SolrClient client;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		client = new HttpSolrClient.Builder(getConnectUrl()).build();
	}

	public void setCiphertext(UpdateRequest req) {
		req.setParam(Configuration.parameterTicketKey, Configuration.parameterTicketValueCipertext);
	}

	public void setCiphertext(ModifiableSolrParams query) {
		query.set(Configuration.parameterTicketKey, Configuration.parameterTicketValueCipertext);
	}

	public SolrClient getSolrClient() {
		return client;
	}

	public void commit() {
//		try {
//			UpdateRequest req = new UpdateRequest();
//			setCiphertext(req);
//			req.setAction(UpdateRequest.ACTION.COMMIT, true, true);
//			req.process(client, null);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
	}

	public UpdateResponse deleteByQuery(String query) {
		try {
			UpdateRequest req = new UpdateRequest();
			req.deleteByQuery(query);
			req.setCommitWithin(-1);
			setCiphertext(req);
			return req.process(client, null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public UpdateResponse addBean(Object bean) {
		try {
			UpdateRequest req = new UpdateRequest();
			req.add(client.getBinder().toSolrInputDocument(bean));
			req.setCommitWithin(-1);
			setCiphertext(req);
			return req.process(client, null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void persistenceWhile(Object bean, long currentCount, long limitCount) {
		try {
			addBean(bean);
			if (0 == currentCount % limitCount) {
				commit();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void commitTail(long currentCount, long limitCount) {
		if (0 != currentCount % limitCount) {
			commit();
		}
	}

	public List<SuggetVo> querySugget(String input) {
		List<SuggetVo> list = new ArrayList<SuggetVo>();
		if (StringUtils.isNotEmpty(input)) {
			try {
				SolrQuery query = new SolrQuery();
				setCiphertext(query);
				query.set("suggest.q", input.toLowerCase());
				query.setRequestHandler("/suggest");
				QueryResponse queryResponse = client.query(query);
				SuggesterResponse suggesterResponse = queryResponse.getSuggesterResponse();
				Map<String, List<String>> suggestedTerms = suggesterResponse.getSuggestedTerms();
				if (MapUtils.isNotEmpty(suggestedTerms)) {
					Iterator<Entry<String, List<String>>> iterator = suggestedTerms.entrySet().iterator();
					while (iterator.hasNext()) {
						Entry<String, List<String>> entry = iterator.next();
						List<String> values = entry.getValue();
						transferSuggetVos(list, values);
					}
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return list;
	}

	private void transferSuggetVos(List<SuggetVo> list, List<String> values) {
		if (CollectionUtils.isNotEmpty(values)) {
			for (int i = 0; i < values.size(); i++) {
				list.add(new SuggetVo(i + 1L, values.get(i), values.get(i)));
			}
		}
	}

	public UpdateResponse deleteAll() {
		try {
			return deleteByQuery("*:*");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public UpdateResponse deleteById(String deletedIds) {
		try {
			UpdateRequest req = new UpdateRequest();
			setCiphertext(req);
			req.deleteById(LangUtils.split2ArrayListString(deletedIds, ","));
			req.setCommitWithin(-1);
			return req.process(client, null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public <T> Page<T> query(SolrQueryPage query, Class<T> type) {
		Page<T> page = new Page<T>();
		try {
			setCiphertext(query);
			QueryResponse queryResponse = client.query(query);
			SolrDocumentList solrDocumentList = queryResponse.getResults();
			page.setList(queryResponse.getBeans(type));
			page.setCount(solrDocumentList.getNumFound());
			page.setPageNow(query.getPageNow());
			page.setPageSize(query.getPageSize());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return page;
	}

	public abstract String getConnectUrl();
}
