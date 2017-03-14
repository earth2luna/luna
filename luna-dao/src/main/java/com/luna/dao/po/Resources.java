/**
 * copyright@www.laulyl.com
 */
package com.luna.dao.po;

import java.util.Date;
import java.io.Serializable;

/**
 * @author laulyl
 * @time 2017-02-25 18:14:21
 * @description create by laulyl project
 */
public class Resources implements Serializable {

	private Long id;// 主键
	private Date createTime;// 创建时间
	private String sourceSiteName;// 来源网站名称
	private String sourceSiteLink;// 来源网站链接
	private Long creatorId;// 创建人id
	private Long categoryId;// 资源分类id
	private String title;// 资源标题
	private String sourceAuthor;// 来源作者
	private Date sourceDate;// 来源日期
	private String thumbnail;// 缩略图
	private Long pageView;// pv
	private Long userView;// uv
	private Integer status;//状态
	private static final long serialVersionUID = 1488017661183L;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setSourceSiteName(String sourceSiteName) {
		this.sourceSiteName = sourceSiteName;
	}

	public String getSourceSiteName() {
		return this.sourceSiteName;
	}

	public void setSourceSiteLink(String sourceSiteLink) {
		this.sourceSiteLink = sourceSiteLink;
	}

	public String getSourceSiteLink() {
		return this.sourceSiteLink;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Long getCreatorId() {
		return this.creatorId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getCategoryId() {
		return this.categoryId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setSourceAuthor(String sourceAuthor) {
		this.sourceAuthor = sourceAuthor;
	}

	public String getSourceAuthor() {
		return this.sourceAuthor;
	}

	public void setSourceDate(Date sourceDate) {
		this.sourceDate = sourceDate;
	}

	public Date getSourceDate() {
		return this.sourceDate;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getThumbnail() {
		return this.thumbnail;
	}

	public void setPageView(Long pageView) {
		this.pageView = pageView;
	}

	public Long getPageView() {
		return this.pageView;
	}

	public void setUserView(Long userView) {
		this.userView = userView;
	}

	public Long getUserView() {
		return this.userView;
	}

	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
}
