/**
 * copyright@www.laulyl.com
 */
package com.luna.dao.po;

import java.io.Serializable;

/**
 * @author laulyl
 * @time 2017-02-25 18:14:21
 * @description create by laulyl project
 */
public class ResourcesContent implements Serializable {

	private Long id;// 主键
	private Long resourcesId;// 外键,资源id
	private String title;// 标题
	private String content;// 内容
	private Long pId;// 父id
	private Integer sortCode;// 排序编码
	private Integer handlerCode;// 处理编码
	private String path;//路径
	private static final long serialVersionUID = 1488017661199L;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setResourcesId(Long resourcesId) {
		this.resourcesId = resourcesId;
	}

	public Long getResourcesId() {
		return this.resourcesId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setPId(Long pId) {
		this.pId = pId;
	}

	public Long getPId() {
		return this.pId;
	}

	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}

	public Integer getSortCode() {
		return this.sortCode;
	}

	public Integer getHandlerCode() {
		return handlerCode;
	}

	public void setHandlerCode(Integer handlerCode) {
		this.handlerCode = handlerCode;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
