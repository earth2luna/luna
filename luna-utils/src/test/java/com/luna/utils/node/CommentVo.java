/**
 * copyright@laulyl
 */
package com.luna.utils.node;

import java.util.Date;

/**
 * @author laulyl 2016-7-15
 * @description
 */
public class CommentVo {
	private Long id;
	private Long pId;
	private String content;
	private String owner;
	private Date createTime;
	private Integer status;
	private String parentOwner;

	public CommentVo() {

	}

	public CommentVo(Long id, Long pId, String content, String owner, Date createTime, Integer status) {
		super();
		this.id = id;
		this.pId = pId;
		this.content = content;
		this.owner = owner;
		this.createTime = createTime;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getParentOwner() {
		return parentOwner;
	}

	public void setParentOwner(String parentOwner) {
		this.parentOwner = parentOwner;
	}

}
