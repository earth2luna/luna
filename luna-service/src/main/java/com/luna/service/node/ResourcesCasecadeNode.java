/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.luna.dao.vo.ResourcesCasecade;
import com.luna.service.dto.RenderMetaData;
import com.luna.utils.node.INode;

/**
 * @author laulyl
 * @date 2017年2月28日 下午10:44:52
 * @description
 */
public class ResourcesCasecadeNode extends ResourcesCasecade implements INode {

	private static final long serialVersionUID = 3488481771060606870L;

	private boolean hasSiblingsAnother;
	private List<INode> childrens;
	private INode parentNode;
	private List<RenderMetaData> metaDatas;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(INode o) {
		ResourcesCasecadeNode that = (ResourcesCasecadeNode) o;
		return this.getResourcesContentSortCode().compareTo(that.getResourcesContentSortCode());
	}

	public void initMetaDatas() {
		if (CollectionUtils.isNotEmpty(childrens)) {
			this.metaDatas = new ArrayList<RenderMetaData>();
			Iterator<INode> iterator = childrens.iterator();
			while (iterator.hasNext()) {
				ResourcesCasecadeNode that = (ResourcesCasecadeNode) iterator.next();
				if (StringUtils.isNotBlank(that.getResourcesContentTitle())) {
					metaDatas.add(new RenderMetaData(that.getId(), that.getResourcesContentTitle()));
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.utils.node.INode#isRoot()
	 */
	public boolean isRoot() {
		return null == this.getParentId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.utils.node.INode#getId()
	 */
	public Long getId() {
		return this.getRecourcesContentId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.utils.node.INode#getParentId()
	 */
	public Long getParentId() {
		return this.getResourcesContentParentId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.utils.node.INode#setHasSiblingsAnother(boolean)
	 */
	public void setHasSiblingsAnother(boolean hasAnother) {
		this.hasSiblingsAnother = hasAnother;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.utils.node.INode#setChildrens(java.util.List)
	 */
	public void setChildrens(List<INode> childrens) {
		this.childrens = childrens;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.utils.node.INode#setParentNode(com.luna.utils.node.INode)
	 */
	public void setParentNode(INode node) {
		this.parentNode = node;
	}

	/**
	 * @return the hasSiblingsAnother
	 */
	public boolean isHasSiblingsAnother() {
		return hasSiblingsAnother;
	}

	/**
	 * @return the childrens
	 */
	public List<INode> getChildrens() {
		return childrens;
	}

	/**
	 * @return the parentNode
	 */
	public INode getParentNode() {
		return parentNode;
	}

	public List<RenderMetaData> getMetaDatas() {
		return metaDatas;
	}

	public void setMetaDatas(List<RenderMetaData> metaDatas) {
		this.metaDatas = metaDatas;
	}

}
