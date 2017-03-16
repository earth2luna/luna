/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.pimpl;

import com.luna.dao.vo.ResourcesCasecade;
import com.luna.utils.infce.IInputOutput;

/**
 * @author laulyl
 * @date 2017年3月1日 下午2:37:33
 * @description
 */
public class InputResourcesCasecadeOutputId implements IInputOutput<ResourcesCasecade, Long> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.utils.infce.IInputOutput#get(java.lang.Object)
	 */
	public Long get(ResourcesCasecade input) {
		return input.getRecourcesContentId();
	}

}
