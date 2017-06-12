/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luna.dao.mapper.IResourcesCategoryMapper;
import com.luna.dao.mapper.IResourcesMapper;
import com.luna.dao.po.Resources;
import com.luna.dao.po.ResourcesCategory;
import com.luna.service.data.utils.CategoryUtils;
import com.luna.service.data.utils.ResourcesUtils;
import com.luna.service.dto.CategoryResourceVo;
import com.luna.service.enumer.resource.StatusEnum;
import com.luna.utils.LangUtils;
import com.luna.utils.classes.Page;
import com.luna.utils.node.INode;
import com.luna.utils.page.PageUtils;

/**
 * @author laulyl
 * @date 2017年6月1日 下午11:26:19
 * @description
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	private IResourcesMapper resourcesMapper;

	@Autowired
	private IResourcesCategoryMapper categoryMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.CategoryService#getCategories()
	 */
	@Override
	public List<INode> getCategories() {
		return CategoryUtils.getCategories(categoryMapper);
	}

	public List<ResourcesCategory> getChildrenCategories() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentIdNotNull", "TRUE");
		List<ResourcesCategory> categories = categoryMapper.selectList(map);
		return categories;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.luna.service.CategoryService#selectCategoryResourceVo(java.lang.Long,
	 * java.lang.Long)
	 */
	@Override
	public CategoryResourceVo selectCategoryResourceVo(Long categoryId, Long pageNow) {
		LOGGER.debug("category id is:" + categoryId);
		Validate.isTrue(LangUtils.booleanValueOfNumber(categoryId), "invalid category id:" + categoryId);
		// 获取类目
		ResourcesCategory category = categoryMapper.selectById(categoryId);
		Validate.notNull(category, "can't find the category by key:" + categoryId);
		Long defaultPageSize = 20L;
		// 获取资源列表
		Page<Resources> page = ResourcesUtils.selectResources(resourcesMapper,
				LangUtils.toString(StatusEnum.ONLINE.getCode()), categoryId,
				LangUtils.intValueOfNumber(defaultPageSize),
				LangUtils.intValueOfNumber(LangUtils.defaultValue(pageNow, 1)));
		// 获取分页迭代内容
		String iteratorPage = PageUtils.evaluate(page.getCount(), pageNow, defaultPageSize,
				LangUtils.append("/category/", categoryId), LangUtils.append("#", category.getName()));
		return new CategoryResourceVo(category, ResourcesUtils.transferResourcesVo(page), iteratorPage);
	}

}
