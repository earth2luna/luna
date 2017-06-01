/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.node;

import com.luna.dao.po.ResourcesCategory;
import com.luna.utils.infce.IInputOutput;
import com.luna.utils.node.INode;

/**
 * @author laulyl
 * @date 2017年6月1日 下午10:59:11
 * @description
 */
public class InputCategoryOutputINode implements IInputOutput<ResourcesCategory, INode> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.utils.infce.IInputOutput#get(java.lang.Object)
	 */
	@Override
	public INode get(ResourcesCategory i) {
		CategoryNode categoryNode = new CategoryNode();
		categoryNode.setHandleCode(i.getHandleCode());
		categoryNode.setId(i.getId());
		categoryNode.setName(i.getName());
		categoryNode.setParentId(i.getParentId());
		return categoryNode;
	}

}
