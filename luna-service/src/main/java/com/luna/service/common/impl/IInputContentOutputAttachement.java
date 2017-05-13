/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.common.impl;

import org.apache.commons.lang.StringUtils;

import com.luna.dao.po.ResourcesContent;
import com.luna.utils.infce.IInputOutput;

/**
 * @author laulyl
 * @date 2017年5月13日 下午6:44:30
 * @description
 */
public class IInputContentOutputAttachement implements IInputOutput<ResourcesContent, String> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.utils.infce.IInputOutput#get(java.lang.Object)
	 */
	@Override
	public String get(ResourcesContent i) {
		String ret = null;
		if (StringUtils.isNotEmpty(i.getPath())) {
			ret = i.getPath();
		}
		return ret;
	}

}
