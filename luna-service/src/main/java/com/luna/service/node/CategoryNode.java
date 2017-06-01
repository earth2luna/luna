/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.node;

import java.util.List;

import com.luna.dao.po.ResourcesCategory;
import com.luna.utils.node.INode;

/**
 * @author laulyl
 * @date 2017年6月1日 下午11:00:20
 * @description
 */
public class CategoryNode extends ResourcesCategory implements INode {

	private static final long serialVersionUID = 8926823384097223856L;

	private boolean hasSiblingsAnother;
	private List<INode> childrens;
	private INode parentNode;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(INode o) {
		return getId().intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.utils.node.INode#isRoot()
	 */
	@Override
	public boolean isRoot() {
		return null == getParentId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.utils.node.INode#setHasSiblingsAnother(boolean)
	 */
	@Override
	public void setHasSiblingsAnother(boolean hasAnother) {
		this.hasSiblingsAnother = hasAnother;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.utils.node.INode#setChildrens(java.util.List)
	 */
	@Override
	public void setChildrens(List<INode> childrens) {
		this.childrens = childrens;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.utils.node.INode#setParentNode(com.luna.utils.node.INode)
	 */
	@Override
	public void setParentNode(INode node) {
		this.parentNode = node;
	}

	public boolean isHasSiblingsAnother() {
		return hasSiblingsAnother;
	}

	public List<INode> getChildrens() {
		return childrens;
	}

	public INode getParentNode() {
		return parentNode;
	}
	
	

}
