/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.node;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import com.luna.dao.po.ResourcesContentMark;
import com.luna.service.data.utils.HtmlUtils;
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
		ResourcesCasecadeNode casecadeNode = (ResourcesCasecadeNode) input;
		if (MapUtils.isNotEmpty(resourcesContentMarkMap)) {
			List<ResourcesContentMark> contentMarks = resourcesContentMarkMap.get(casecadeNode.getId());
			if (CollectionUtils.isNotEmpty(contentMarks)) {
				Iterator<ResourcesContentMark> iterator = contentMarks.iterator();
				ResourcesContentMark contentMark = iterator.next();
				change(casecadeNode, contentMark);
			}
		}
		change(casecadeNode);
		return input;
	}

	public void change(ResourcesCasecadeNode casecadeNode) {
		switch (casecadeNode.getResourcesContentHandlerCode()) {
		case 1:
			casecadeNode.setResourcesContent(HtmlUtils.getFormatHtml(casecadeNode.getResourcesContent()));
			break;
		case 2:
			casecadeNode.setResourcesContent(HtmlUtils.getFormatHtmlFragment(casecadeNode.getResourcesContent()));
			break;

		}
	}

	public void change(ResourcesCasecadeNode casecadeNode, ResourcesContentMark contentMark) {
		// TODO 等完成
		// casecadeNode.setResourcesContent(contentMark.getContent());
		// casecadeNode.setResourcesContentTitle(contentMark.getContent());

	}

	// private static String getFormatHtml(String content) {
	// String docTypeRegex = "&lt;\\s*!\\s*DOCTYPE\\s*[Hh][Tt][Mm][Ll].*&lt;";
	// String htmlTagRegex =
	// "&lt;\\s*[a-zA-Z]+(\\s+[a-zA-Z]+=\\s*[\"'].*[\"'])+\\s*&lt;";
	// String input = "&lt; div class=\" container \" type=\"dafasdf\"&lt;";
	// Pattern pattern = Pattern.compile(htmlTagRegex);
	// Matcher matcher = pattern.matcher(input);
	// while (matcher.find()) {
	// System.out.println(matcher.group());
	// }
	// return null;
	// }

}
