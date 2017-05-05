/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.node;

import org.apache.commons.lang3.StringUtils;

import com.luna.dao.vo.ResourcesCasecade;
import com.luna.service.data.utils.Configure;
import com.luna.utils.FilePropertyUtils;
import com.luna.utils.infce.IInputOutput;
import com.luna.utils.node.INode;

/**
 * @author laulyl
 * @date 2017年2月28日 下午10:59:46
 * @description
 */
public class InputResourceOutputINode implements IInputOutput<ResourcesCasecade, INode> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.utils.infce.IInputOutput#get(java.lang.Object)
	 */
	public INode get(ResourcesCasecade input) {
		ResourcesCasecadeNode output = new ResourcesCasecadeNode();
		output.setResourcesId(input.getResourcesId());
		output.setResourcesCreateTime(input.getResourcesCreateTime());
		output.setResourcesSourceSiteName(input.getResourcesSourceSiteName());
		output.setResourcesSourceSiteLink(input.getResourcesSourceSiteLink());
		output.setResourcesCreatetorId(input.getResourcesCreatetorId());
		output.setResourcesCategroyId(input.getResourcesCategroyId());
		output.setResourcesTitle(input.getResourcesTitle());
		output.setResourcesSourceAuthor(input.getResourcesSourceAuthor());
		output.setResourcesSourceDate(input.getResourcesSourceDate());
		output.setResourcesThumbnail(input.getResourcesThumbnail());
		output.setResourcesPageView(input.getResourcesPageView());
		output.setResourcesUserView(input.getResourcesUserView());
		output.setRecourcesContentId(input.getRecourcesContentId());
		output.setResourcesContentTitle(input.getResourcesContentTitle());
		output.setResourcesContent(input.getResourcesContent());
		output.setResourcesContentParentId(input.getResourcesContentParentId());
		output.setResourcesContentSortCode(input.getResourcesContentSortCode());
		output.setResourcesContentHandlerCode(input.getResourcesContentHandlerCode());
		if (StringUtils.isNotEmpty(input.getResourcesContentPath())) {
			output.setResourcesContentPath(FilePropertyUtils.appendPath(Configure.getThisWebDomain(),
					Configure.getAttachementPath(), input.getResourcesContentPath()));
		}
		output.setWebsiteCode(input.getWebsiteCode());
		return output;
	}

}
