/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.componet;

import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

/**
 * @author laulyl
 * @date 2017年4月29日 上午9:34:17
 * @description
 */
public class ResourceSolr {

	@Field
	private String id;// 主键
	@Field
	private String title;// 资源标题
	@Field
	private String imageUrl;// image Url
	@Field
	private String summary;// 简介
	@Field
	private Date createTime;// 创建时间
	@Field
	private Long creatorId;// 创建人id
	@Field
	private String creatorName;// 创建人名称
	@Field
	private Long categoryId;// 资源分类id
	@Field
	private String categoryName;// 类型名称
	@Field
	private String sourceAuthor;// 来源作者
	@Field
	private Date sourceDate;// 来源日期
	@Field
	private String thumbnail;// 缩略图

	@Field
	private List<String> titlePinyin;// 标题拼音

	public ResourceSolr() {
	}

	
	public ResourceSolr(String id, String title, String imageUrl, String summary, Date createTime, Long creatorId,
			String creatorName, Long categoryId, String categoryName, String sourceAuthor, Date sourceDate,
			String thumbnail, List<String> titlePinyin) {
		super();
		this.id = id;
		this.title = title;
		this.imageUrl = imageUrl;
		this.summary = summary;
		this.createTime = createTime;
		this.creatorId = creatorId;
		this.creatorName = creatorName;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.sourceAuthor = sourceAuthor;
		this.sourceDate = sourceDate;
		this.thumbnail = thumbnail;
		this.titlePinyin = titlePinyin;
	}



	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the creatorId
	 */
	public Long getCreatorId() {
		return creatorId;
	}

	/**
	 * @param creatorId
	 *            the creatorId to set
	 */
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * @return the creatorName
	 */
	public String getCreatorName() {
		return creatorName;
	}

	/**
	 * @param creatorName
	 *            the creatorName to set
	 */
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	/**
	 * @return the categoryId
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId
	 *            the categoryId to set
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName
	 *            the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the sourceAuthor
	 */
	public String getSourceAuthor() {
		return sourceAuthor;
	}

	/**
	 * @param sourceAuthor
	 *            the sourceAuthor to set
	 */
	public void setSourceAuthor(String sourceAuthor) {
		this.sourceAuthor = sourceAuthor;
	}

	/**
	 * @return the sourceDate
	 */
	public Date getSourceDate() {
		return sourceDate;
	}

	/**
	 * @param sourceDate
	 *            the sourceDate to set
	 */
	public void setSourceDate(Date sourceDate) {
		this.sourceDate = sourceDate;
	}

	/**
	 * @return the thumbnail
	 */
	public String getThumbnail() {
		return thumbnail;
	}

	/**
	 * @param thumbnail
	 *            the thumbnail to set
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * @return the titlePinyin
	 */
	public List<String> getTitlePinyin() {
		return titlePinyin;
	}

	/**
	 * @param titlePinyin
	 *            the titlePinyin to set
	 */
	public void setTitlePinyin(List<String> titlePinyin) {
		this.titlePinyin = titlePinyin;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary
	 *            the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
