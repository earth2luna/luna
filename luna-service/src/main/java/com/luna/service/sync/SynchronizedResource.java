/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.sync;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luna.dao.mapper.IResourcesMapper;
import com.luna.dao.vo.ResourcesCasecade;
import com.luna.security.Configuration;
import com.luna.service.componet.ResourceSolr;
import com.luna.service.componet.SolrComponet;
import com.luna.service.data.utils.Constants;
import com.luna.service.data.utils.ResourcesUtils;
import com.luna.service.enumer.resource.CategoryEnum;
import com.luna.service.enumer.resource.CreatorEnum;
import com.luna.service.enumer.resource.StatusEnum;
import com.luna.service.node.InputResourceOutputINode;
import com.luna.service.node.ResourcesCasecadeNode;
import com.luna.utils.LangUtils;
import com.luna.utils.RegexUtils;
import com.luna.utils.node.INode;
import com.luna.utils.node.NodeUtils;
import com.luna.utils.node.NodeVariable;

/**
 * @author laulyl
 * @date 2017年4月29日 上午9:02:03
 * @description
 */
public class SynchronizedResource extends AbstractListWhileDo<ResourceSolr> {
	private static final Logger LOGGER = LoggerFactory.getLogger(SynchronizedResource.class);
	private static final Long COMMIT_LIMIT_COUNT = 50L;
	private static InputResourceOutputINode inputOutput = new InputResourceOutputINode();
	private static NodeVariable variable = new NodeVariable();
	private IResourcesMapper resourcesMapper;
	private SolrComponet solrComponet;

	public SynchronizedResource(IResourcesMapper resourcesMapper, SolrComponet solrComponet) {
		super();
		this.resourcesMapper = resourcesMapper;
		this.solrComponet = solrComponet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.sync.AbstractListWhileDo#getList(long)
	 */
	@Override
	public List<ResourceSolr> getList(long count) {

		List<ResourcesCasecade> casecades = ResourcesUtils.selectResourcesCasecades(resourcesMapper,
				LangUtils.intValueOfNumber(count), String.valueOf(StatusEnum.ONLINE.getCode()), null);
		// 划分等级
		List<INode> nodes = NodeUtils.sort(casecades, inputOutput, variable);

		if (CollectionUtils.isEmpty(nodes)) {
			return null;
		}

		Iterator<INode> iterator = nodes.iterator();
		ResourcesCasecadeNode t = null;
		String imageUrl = null;
		StringBuffer summary = new StringBuffer();
		int solrSummaryMaxLength = Constants.SOLR_RESULT_SUMMARY_MAX_LENGTH;
		StringBuffer content = new StringBuffer();
		int solrContentMaxLength = Constants.SOLR_STORE_CONTENT_MAX_LENGTH;
		// has next
		// &&
		// ( empty image url ||
		// summary < solrSummaryMaxLength ||
		// content < solrContentMaxLength)
		while (iterator.hasNext()
				&& (StringUtils.isEmpty(imageUrl) || (null == summary || solrSummaryMaxLength > summary.length())
						|| (null == content || solrContentMaxLength > content.length()))) {

			t = (ResourcesCasecadeNode) iterator.next();

			if (StringUtils.isEmpty(imageUrl)) {
				imageUrl = getImageUrl(t);
			}

			if (null == summary || solrSummaryMaxLength > summary.length()) {
				evalueSummary(summary, t, solrSummaryMaxLength);
			}

			if (null == content || solrContentMaxLength > content.length()) {
				evalueSummary(content, t, solrContentMaxLength);
			}
		}

		List<String> titlePinyin = new ArrayList<String>();
		// try {
		// titlePinyin.add(PinyinHelper.getShortPinyin(t.getTitle()));
		// titlePinyin.add(PinyinHelper.convertToPinyinString(t.getTitle(), "",
		// PinyinFormat.WITHOUT_TONE));
		// } catch (PinyinException e) {
		// LOGGER.error("pin yin error:", e);
		// }
		ResourceSolr resourceSolr = new ResourceSolr(t.getResourcesId().toString(), t.getResourcesTitle(), imageUrl,
				LangUtils.subtringDefaultAppender(summary.toString(), solrSummaryMaxLength), t.getResourcesCreateTime(),
				t.getResourcesCreatetorId(), CreatorEnum.getName(t.getResourcesCreatetorId()),
				t.getResourcesCategroyId(), CategoryEnum.getName(t.getResourcesCategroyId()),
				t.getResourcesSourceAuthor(), t.getResourcesSourceDate(), t.getResourcesThumbnail(), titlePinyin);
		// set content
		resourceSolr.setContent(LangUtils.subtringDefaultAppender(content.toString(), solrContentMaxLength));

		List<ResourceSolr> list = new ArrayList<ResourceSolr>() {

			private static final long serialVersionUID = 3013883866990547963L;

			{
				add(resourceSolr);
			}
		};
		return list;
	}

	private String getImageUrl(ResourcesCasecadeNode t) {
		List<INode> nodes = t.getChildrens();
		if (CollectionUtils.isNotEmpty(nodes)) {
			Iterator<INode> iterator = nodes.iterator();
			while (iterator.hasNext()) {
				INode node = iterator.next();
				ResourcesCasecadeNode casecade = (ResourcesCasecadeNode) node;
				if (!LangUtils.isBlank(casecade.getResourcesContentPath())) {
					return casecade.getResourcesContentPath();
				}
			}
		}
		return null;
	}

	private void evalueSummary(StringBuffer buffer, ResourcesCasecadeNode t, int length) {
		evalueSummary(buffer, t);
		List<INode> nodes = t.getChildrens();
		if (CollectionUtils.isNotEmpty(nodes)) {
			Iterator<INode> iterator = nodes.iterator();
			while (iterator.hasNext() && buffer.length() < length) {
				INode node = iterator.next();
				ResourcesCasecadeNode casecade = (ResourcesCasecadeNode) node;
				evalueSummary(buffer, casecade);
			}
		}
	}

	private void evalueSummary(StringBuffer buffer, ResourcesCasecadeNode t) {
		String title = LangUtils.replaceAll(t.getResourcesTitle(), RegexUtils.HTML_TAG, "");
		String content = LangUtils.replaceAll(t.getResourcesContent(), RegexUtils.HTML_TAG, "");
		if (!LangUtils.isBlank(title)) {
			buffer.append(title);
			buffer.append(" - ");
		}
		if (!LangUtils.isBlank(content)) {
			buffer.append(content);
			buffer.append(" ");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.luna.service.sync.AbstractListWhileDo#doElement(java.lang.Object,
	 * long)
	 */
	@Override
	public void doElement(ResourceSolr node, long count) {
		node.setTicket(Configuration.parameterTicketValueCipertext);
		solrComponet.persistenceWhile(node, count, COMMIT_LIMIT_COUNT);

		LOGGER.info("current complete count is:" + count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.sync.AbstractListWhileDo#after(long)
	 */
	@Override
	public void after(long count) {
		solrComponet.commitTail(count, COMMIT_LIMIT_COUNT);
	}

}
