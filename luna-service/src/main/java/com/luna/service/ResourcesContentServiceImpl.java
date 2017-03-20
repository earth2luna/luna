/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luna.dao.mapper.IResourcesContentMapper;
import com.luna.dao.po.ResourcesContent;
import com.luna.dao.vo.ResourcesCasecade;
import com.luna.service.data.utils.ResourcesContentUtils;
import com.luna.utils.AssertUtils;
import com.luna.utils.LangUtils;
import com.luna.utils.classes.Page;

/**
 * @author laulyl
 * @date 2017年3月20日 下午9:39:00
 * @description
 */
@Service
public class ResourcesContentServiceImpl implements ResourcesContentService {

	@Autowired
	private IResourcesContentMapper resourcesContentMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.luna.service.ResourcesContentService#selectResourcesContents(java.
	 * lang.Long, java.lang.Integer)
	 */
	@Override
	public Page<ResourcesContent> selectResourcesContents(Long rsId, Integer pageNow) {
		AssertUtils.isTrueOfApp(LangUtils.booleanValueOfNumber(rsId), "无效的资源key值");
		return ResourcesContentUtils.selectResources(resourcesContentMapper, rsId, pageNow);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.luna.service.ResourcesContentService#selectResourcesCasecade(java.
	 * lang.Long, java.lang.Integer)
	 */
	@Override
	public Page<ResourcesCasecade> selectResourcesCasecade(Long rsId, Integer pageNow) {
		AssertUtils.isTrueOfApp(LangUtils.booleanValueOfNumber(rsId), "无效的资源key值");
		return ResourcesContentUtils.selectResourcesCasecade(resourcesContentMapper, rsId, pageNow);
	}

}
