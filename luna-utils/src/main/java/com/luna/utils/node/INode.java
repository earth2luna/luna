/**
 * COPYRIGHT@LAULYL
 */
package com.luna.utils.node;

import java.util.List;

/**
 * @author laulyl
 * @date 2017年2月27日 下午4:37:00
 * @description 节点
 */
public interface INode extends Comparable<INode> {

	// 是否是根节点
	public boolean isRoot();

	// ID
	public Long getId();

	// 父节点ID
	public Long getParentId();

	// 是否成已经截取;截取完成后是否还有兄弟节点
	public void setHasSiblingsAnother(boolean hasAnother);

	// 填充子节点
	public void setChildrens(List<INode> childrens);
	
	public void setParentNode(INode node);
}
