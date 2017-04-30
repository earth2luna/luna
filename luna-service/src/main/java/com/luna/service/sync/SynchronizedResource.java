/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.sync;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luna.dao.mapper.IResourcesMapper;
import com.luna.dao.po.Resources;
import com.luna.service.componet.ResourceSolr;
import com.luna.service.componet.SolrComponet;
import com.luna.service.data.utils.ResourcesUtils;
import com.luna.service.enumer.resource.CategoryEnum;
import com.luna.service.enumer.resource.CreatorEnum;
import com.luna.service.enumer.resource.StatusEnum;
import com.luna.utils.LangUtils;
import com.luna.utils.classes.Page;

/**
 * @author laulyl
 * @date 2017年4月29日 上午9:02:03
 * @description
 */
public class SynchronizedResource extends AbstractListWhileDo<Resources> {
	private static final Logger LOGGER = LoggerFactory.getLogger(SynchronizedResource.class);
	private static final Long COMMIT_LIMIT_COUNT = 50L;
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
	public List<Resources> getList(long count) {
		Page<Resources> page = ResourcesUtils.selectResources(resourcesMapper,
				String.valueOf(StatusEnum.ONLINE.getCode()), LangUtils.intValueOfNumber(count));
		return page.getList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.luna.service.sync.AbstractListWhileDo#doElement(java.lang.Object,
	 * long)
	 */
	@Override
	public void doElement(Resources t, long count) {
		List<String> titlePinyin = new ArrayList<String>();
		// try {
		// titlePinyin.add(PinyinHelper.getShortPinyin(t.getTitle()));
		// titlePinyin.add(PinyinHelper.convertToPinyinString(t.getTitle(), "",
		// PinyinFormat.WITHOUT_TONE));
		// } catch (PinyinException e) {
		// LOGGER.error("pin yin error:", e);
		// }
		solrComponet.persistenceWhile(new ResourceSolr(t.getId().toString(), t.getTitle(), t.getCreateTime(),
				t.getCreatorId(), CreatorEnum.getName(t.getCreatorId()), t.getCategoryId(),
				CategoryEnum.getName(t.getCategoryId()), t.getSourceAuthor(), t.getSourceDate(), t.getThumbnail(),
				titlePinyin), count, COMMIT_LIMIT_COUNT);

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
