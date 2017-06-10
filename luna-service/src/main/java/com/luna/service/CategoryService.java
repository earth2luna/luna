/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service;

import java.util.List;

import com.luna.dao.po.ResourcesCategory;
import com.luna.service.dto.CategoryResourceVo;
import com.luna.utils.node.INode;

/**
 * @author laulyl
 * @date 2017年6月1日 下午11:25:38
 * @description
 */
public interface CategoryService {

	List<INode> getCategories();

	List<ResourcesCategory> getChildrenCategories();

	CategoryResourceVo selectCategoryResourceVo(Long categoryId, Long pageNow);
}
