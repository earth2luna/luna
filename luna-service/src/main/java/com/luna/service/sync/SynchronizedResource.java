/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.sync;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luna.dao.mapper.IResourcesMapper;
import com.luna.dao.vo.ResourcesCasecade;
import com.luna.service.componet.ResourceSolr;
import com.luna.service.componet.SolrComponet;
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
public class SynchronizedResource extends AbstractListWhileDo<INode> {
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
	public List<INode> getList(long count) {
		// Page<Resources> page =
		// ResourcesUtils.selectResources(resourcesMapper,
		// String.valueOf(StatusEnum.ONLINE.getCode()),
		// LangUtils.intValueOfNumber(count));
		// return page.getList();

		List<ResourcesCasecade> casecades = ResourcesUtils.selectResourcesCasecades(resourcesMapper,
				LangUtils.intValueOfNumber(count), String.valueOf(StatusEnum.ONLINE.getCode()), null);
		// 划分等级
		List<INode> nodes = NodeUtils.sort(casecades, inputOutput, variable);
		return nodes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.luna.service.sync.AbstractListWhileDo#doElement(java.lang.Object,
	 * long)
	 */
	@Override
	public void doElement(INode node, long count) {
		List<String> titlePinyin = new ArrayList<String>();
		// try {
		// titlePinyin.add(PinyinHelper.getShortPinyin(t.getTitle()));
		// titlePinyin.add(PinyinHelper.convertToPinyinString(t.getTitle(), "",
		// PinyinFormat.WITHOUT_TONE));
		// } catch (PinyinException e) {
		// LOGGER.error("pin yin error:", e);
		// }
		ResourcesCasecadeNode t = (ResourcesCasecadeNode) node;
		String summary = getSummary(t);
		String imageUrl = getImageUrl(t);
		solrComponet.persistenceWhile(new ResourceSolr(t.getResourcesId().toString(), t.getResourcesTitle(), imageUrl,
				summary, t.getResourcesCreateTime(), t.getResourcesCreatetorId(),
				CreatorEnum.getName(t.getResourcesCreatetorId()), t.getResourcesCategroyId(),
				CategoryEnum.getName(t.getResourcesCategroyId()), t.getResourcesSourceAuthor(),
				t.getResourcesSourceDate(), t.getResourcesThumbnail(), titlePinyin), count, COMMIT_LIMIT_COUNT);

		LOGGER.info("current complete count is:" + count);
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

	private String getSummary(ResourcesCasecadeNode t) {
		int length = 220;
		StringBuffer buffer = new StringBuffer();
		List<INode> nodes = t.getChildrens();
		if (CollectionUtils.isNotEmpty(nodes)) {
			Iterator<INode> iterator = nodes.iterator();
			while (iterator.hasNext() && buffer.length() < length) {
				INode node = iterator.next();
				ResourcesCasecadeNode casecade = (ResourcesCasecadeNode) node;
				String content = LangUtils.replaceAll(casecade.getResourcesContent(), RegexUtils.HTML_TAG, "");
				if (!LangUtils.isBlank(content)) {
					buffer.append(content);
				}
			}
		}
		return LangUtils.subtringDefaultAppender(buffer.toString(), length);
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
