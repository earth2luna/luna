/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.dto;

/**
 * @author laulyl
 * @date 2017年3月19日 下午3:01:45
 * @description
 */
public class ResourcesForm {
	private Long rsId;// 资源Id
	private String ttl;// 标题
	private Long categoryId;
	private Long creatorId;
	private String sourceWebsiteName;
	private String sourceWebsiteLink;
	private String sourceAuthor;
	private String sourceDate;
	private Integer websiteCode;

	/**
	 * @return the rsId
	 */
	public Long getRsId() {
		return rsId;
	}

	/**
	 * @param rsId
	 *            the rsId to set
	 */
	public void setRsId(Long rsId) {
		this.rsId = rsId;
	}

	/**
	 * @return the ttl
	 */
	public String getTtl() {
		return ttl;
	}

	/**
	 * @param ttl
	 *            the ttl to set
	 */
	public void setTtl(String ttl) {
		this.ttl = ttl;
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
	 * @return the sourceWebsiteName
	 */
	public String getSourceWebsiteName() {
		return sourceWebsiteName;
	}

	/**
	 * @param sourceWebsiteName
	 *            the sourceWebsiteName to set
	 */
	public void setSourceWebsiteName(String sourceWebsiteName) {
		this.sourceWebsiteName = sourceWebsiteName;
	}

	/**
	 * @return the sourceWebsiteLink
	 */
	public String getSourceWebsiteLink() {
		return sourceWebsiteLink;
	}

	/**
	 * @param sourceWebsiteLink
	 *            the sourceWebsiteLink to set
	 */
	public void setSourceWebsiteLink(String sourceWebsiteLink) {
		this.sourceWebsiteLink = sourceWebsiteLink;
	}

	/**
	 * @return the sourceAuthor
	 */
	public String getSourceAuthor() {
		return sourceAuthor;
	}

	/**
	 * @param sourceAuthor the sourceAuthor to set
	 */
	public void setSourceAuthor(String sourceAuthor) {
		this.sourceAuthor = sourceAuthor;
	}

	/**
	 * @return the sourceDate
	 */
	public String getSourceDate() {
		return sourceDate;
	}

	/**
	 * @param sourceDate the sourceDate to set
	 */
	public void setSourceDate(String sourceDate) {
		this.sourceDate = sourceDate;
	}

	public Integer getWebsiteCode() {
		return websiteCode;
	}

	public void setWebsiteCode(Integer websiteCode) {
		this.websiteCode = websiteCode;
	}


}
