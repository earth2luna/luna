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
public class ResourcesAttachment implements Serializable {

	private Long id;// 主键
	private Long resourcesId;// 资源id
	private Long attachmentId;// 附件id
	private Integer joinType;// 关联类型
	private static final long serialVersionUID = 1488017661183L;

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

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public Long getAttachmentId() {
		return this.attachmentId;
	}

	public void setJoinType(Integer joinType) {
		this.joinType = joinType;
	}

	public Integer getJoinType() {
		return this.joinType;
	}
}
