/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.dto;

/**
 * @author laulyl
 * @date 2017年5月14日 上午10:12:40
 * @description
 */
public class ResourceLinkVo {

	private String title;
	private String link;
	
	/**
	 * @param title
	 * @param link
	 */
	public ResourceLinkVo(String title, String link) {
		super();
		this.title = title;
		this.link = link;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
}
