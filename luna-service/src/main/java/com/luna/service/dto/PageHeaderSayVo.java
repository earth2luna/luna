/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.dto;

/**
 * @author laulyl
 * @date 2017年5月1日 下午11:33:37
 * @description
 */
public class PageHeaderSayVo {

	private String title;
	private String description;

	public PageHeaderSayVo(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
