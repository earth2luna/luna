/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.render;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import com.luna.dao.po.ResourcesContentMark;
import com.luna.utils.infce.IInputOutput;
import com.luna.utils.node.INode;

/**
 * @author laulyl
 * @date 2017年3月1日 下午3:27:07
 * @description
 */
public class InputNodeOutputNode implements IInputOutput<INode, INode> {

	private Map<Long, List<ResourcesContentMark>> resourcesContentMarkMap;

	/**
	 * @param resourcesContentMarkMap
	 */
	public InputNodeOutputNode(Map<Long, List<ResourcesContentMark>> resourcesContentMarkMap) {
		super();
		this.resourcesContentMarkMap = resourcesContentMarkMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.utils.infce.IInputOutput#get(java.lang.Object)
	 */
	public INode get(INode input) {
		if (MapUtils.isNotEmpty(resourcesContentMarkMap)) {
			ResourcesCasecadeNode casecadeNode = (ResourcesCasecadeNode) input;
			List<ResourcesContentMark> contentMarks = resourcesContentMarkMap.get(casecadeNode.getId());
			if (CollectionUtils.isNotEmpty(contentMarks)) {
				Iterator<ResourcesContentMark> iterator = contentMarks.iterator();
				ResourcesContentMark contentMark = iterator.next();
				change(casecadeNode, contentMark);
			}
		}
		return input;
	}

	public void change(ResourcesCasecadeNode casecadeNode, ResourcesContentMark contentMark) {
		//TODO 等完成
		casecadeNode.setResourcesContent(contentMark.getContent());
		casecadeNode.setResourcesContentTitle(contentMark.getContent());
	}

}
