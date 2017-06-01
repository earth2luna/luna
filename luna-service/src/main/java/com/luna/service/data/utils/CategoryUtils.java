/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.data.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.luna.dao.mapper.IResourcesCategoryMapper;
import com.luna.dao.po.ResourcesCategory;
import com.luna.service.node.CategoryNode;
import com.luna.service.node.InputCategoryOutputINode;
import com.luna.utils.node.INode;
import com.luna.utils.node.NodeUtils;
import com.luna.utils.node.NodeVariable;

/**
 * @author laulyl
 * @date 2017年6月1日 下午10:47:06
 * @description 
 */
public class CategoryUtils {

	public static List<INode> getCategories(IResourcesCategoryMapper categoryMapper) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ResourcesCategory> categories = categoryMapper.selectList(map);
		List<INode> nodes = NodeUtils.sort(categories, new InputCategoryOutputINode(), new NodeVariable());
		List<INode> ret = new ArrayList<INode>();
		if (CollectionUtils.isNotEmpty(nodes)) {
			for (INode node : nodes) {
				CategoryNode categoryNode = (CategoryNode) node;
				if (CollectionUtils.isNotEmpty(categoryNode.getChildrens())) {
					if (1 == categoryNode.getChildrens().size()) {
						ret.add(categoryNode.getChildrens().get(0));
					} else {
						ret.add(categoryNode);
					}
				}
			}
		}
		return ret;
	}
}
