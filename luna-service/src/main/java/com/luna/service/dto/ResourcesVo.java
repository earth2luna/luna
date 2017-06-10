/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.dto;

import com.luna.dao.po.Resources;

/**
 * @author laulyl
 * @date 2017年6月10日 上午11:17:08
 * @description
 */
public class ResourcesVo extends Resources {

	private static final long serialVersionUID = -3924920433574503775L;
	private String link;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
