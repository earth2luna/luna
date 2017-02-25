/**
 * copyright@www.laulyl.com
 */
package com.luna.po;

import java.io.Serializable;
/**
 * @author laulyl
 * @time 2017-02-25 18:14:21
 * @description create by laulyl project
 */
public class ResourcesContentMark implements Serializable {

	private Long id;// 主键
	private Long resourcesContentId;// 资源内容id
	private String content;// 标记内容
	private Byte handleCode;// 处理编码
	private static final long serialVersionUID = 1488017661199L;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setResourcesContentId(Long resourcesContentId) {
		this.resourcesContentId = resourcesContentId;
	}

	public Long getResourcesContentId() {
		return this.resourcesContentId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setHandleCode(Byte handleCode) {
		this.handleCode = handleCode;
	}

	public Byte getHandleCode() {
		return this.handleCode;
	}
}
