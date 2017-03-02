/**
 * COPYRIGHT@LAULYL
 */
package com.luna.utils.node;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.luna.utils.node.INode;

/**
 * @author laulyl
 * @date 2017年2月27日 下午10:07:27
 * @description
 */
public class CommentNode extends CommentVo implements INode {

	private boolean hasSiblingsAnother;
	private INode parentNode;
	private List<INode> childrens;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(INode o) {
		CommentNode another = (CommentNode) o;
		return this.getCreateTime().compareTo(another.getCreateTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.INode#isRoot()
	 */
	@Override
	public boolean isRoot() {
		return null == this.getpId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.INode#getParentId()
	 */
	@Override
	public Long getParentId() {
		return this.getpId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.INode#setHasSiblingsAnother(boolean)
	 */
	@Override
	public void setHasSiblingsAnother(boolean hasAnother) {
		this.hasSiblingsAnother = hasAnother;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.INode#setChildrens(java.util.List)
	 */
	@Override
	public void setChildrens(List<INode> childrens) {
		this.childrens = childrens;
	}

	/**
	 * @return the hasSiblingsAnother
	 */
	public boolean isHasSiblingsAnother() {
		return hasSiblingsAnother;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.INode#setParentNode(com.luna.INode)
	 */
	@Override
	public void setParentNode(INode node) {
		this.parentNode = node;

	}

	/**
	 * @return the parentNode
	 */
	public CommentNode getParentNode() {
		return (CommentNode) parentNode;
	}

	/**
	 * @return the childrens
	 */
	public List<CommentNode> getChildrens() {
		List<CommentNode> commentNodes = new ArrayList<CommentNode>();
		if (CollectionUtils.isEmpty(childrens))
			return commentNodes;
		for (int i = 0; i < childrens.size(); i++) {
			commentNodes.add((CommentNode) childrens.get(i));
		}
		return commentNodes;
	}

}
