/**
 * COPYRIGHT@LAULYL
 */
package com.luna.dao.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author laulyl
 * @date 2017年2月28日 下午3:42:28
 * @description 资源与内容级联
 */
public class ResourcesCasecade implements Serializable {

	private static final long serialVersionUID = -4897511895669242920L;
	private Long resourcesId;// 资源id
	private Date resourcesCreateTime;// 资源创建时间
	private String resourcesSourceSiteName;// 资源来源站点名称
	private String resourcesSourceSiteLink;// 资源站点来源链接
	private Long resourcesCreatetorId;// 资源创建者
	private Long resourcesCategroyId;// 资源类型id
	private String resourcesTitle;// 资源标题
	private String resourcesSourceAuthor;// 资源来源作者
	private Date resourcesSourceDate;// 资源来源日期
	private String resourcesThumbnail;// 资源缩略图
	private Long resourcesPageView;// 资源PV
	private Long resourcesUserView;// 资源UV
	private Long recourcesContentId;// 资源内容id
	private String resourcesContentTitle;// 资源内容标题
	private String resourcesContent;// 资源内容
	private Long resourcesContentParentId;// 资源内容父id
	private Integer resourcesContentSortCode;// 资源内容排序编码
	private Integer resourcesContentHandlerCode;// 资源内容处理编码

	/**
	 * @return the resourcesId
	 */
	public Long getResourcesId() {
		return resourcesId;
	}

	/**
	 * @param resourcesId
	 *            the resourcesId to set
	 */
	public void setResourcesId(Long resourcesId) {
		this.resourcesId = resourcesId;
	}

	/**
	 * @return the resourcesCreateTime
	 */
	public Date getResourcesCreateTime() {
		return resourcesCreateTime;
	}

	/**
	 * @param resourcesCreateTime
	 *            the resourcesCreateTime to set
	 */
	public void setResourcesCreateTime(Date resourcesCreateTime) {
		this.resourcesCreateTime = resourcesCreateTime;
	}

	/**
	 * @return the resourcesSourceSiteName
	 */
	public String getResourcesSourceSiteName() {
		return resourcesSourceSiteName;
	}

	/**
	 * @param resourcesSourceSiteName
	 *            the resourcesSourceSiteName to set
	 */
	public void setResourcesSourceSiteName(String resourcesSourceSiteName) {
		this.resourcesSourceSiteName = resourcesSourceSiteName;
	}

	/**
	 * @return the resourcesSourceSiteLink
	 */
	public String getResourcesSourceSiteLink() {
		return resourcesSourceSiteLink;
	}

	/**
	 * @param resourcesSourceSiteLink
	 *            the resourcesSourceSiteLink to set
	 */
	public void setResourcesSourceSiteLink(String resourcesSourceSiteLink) {
		this.resourcesSourceSiteLink = resourcesSourceSiteLink;
	}

	/**
	 * @return the resourcesCreatetorId
	 */
	public Long getResourcesCreatetorId() {
		return resourcesCreatetorId;
	}

	/**
	 * @param resourcesCreatetorId
	 *            the resourcesCreatetorId to set
	 */
	public void setResourcesCreatetorId(Long resourcesCreatetorId) {
		this.resourcesCreatetorId = resourcesCreatetorId;
	}

	/**
	 * @return the resourcesCategroyId
	 */
	public Long getResourcesCategroyId() {
		return resourcesCategroyId;
	}

	/**
	 * @param resourcesCategroyId
	 *            the resourcesCategroyId to set
	 */
	public void setResourcesCategroyId(Long resourcesCategroyId) {
		this.resourcesCategroyId = resourcesCategroyId;
	}

	/**
	 * @return the resourcesTitle
	 */
	public String getResourcesTitle() {
		return resourcesTitle;
	}

	/**
	 * @param resourcesTitle
	 *            the resourcesTitle to set
	 */
	public void setResourcesTitle(String resourcesTitle) {
		this.resourcesTitle = resourcesTitle;
	}

	/**
	 * @return the resourcesSourceAuthor
	 */
	public String getResourcesSourceAuthor() {
		return resourcesSourceAuthor;
	}

	/**
	 * @param resourcesSourceAuthor
	 *            the resourcesSourceAuthor to set
	 */
	public void setResourcesSourceAuthor(String resourcesSourceAuthor) {
		this.resourcesSourceAuthor = resourcesSourceAuthor;
	}

	/**
	 * @return the resourcesSourceDate
	 */
	public Date getResourcesSourceDate() {
		return resourcesSourceDate;
	}

	/**
	 * @param resourcesSourceDate
	 *            the resourcesSourceDate to set
	 */
	public void setResourcesSourceDate(Date resourcesSourceDate) {
		this.resourcesSourceDate = resourcesSourceDate;
	}

	/**
	 * @return the resourcesThumbnail
	 */
	public String getResourcesThumbnail() {
		return resourcesThumbnail;
	}

	/**
	 * @param resourcesThumbnail
	 *            the resourcesThumbnail to set
	 */
	public void setResourcesThumbnail(String resourcesThumbnail) {
		this.resourcesThumbnail = resourcesThumbnail;
	}

	/**
	 * @return the resourcesPageView
	 */
	public Long getResourcesPageView() {
		return resourcesPageView;
	}

	/**
	 * @param resourcesPageView
	 *            the resourcesPageView to set
	 */
	public void setResourcesPageView(Long resourcesPageView) {
		this.resourcesPageView = resourcesPageView;
	}

	/**
	 * @return the resourcesUserView
	 */
	public Long getResourcesUserView() {
		return resourcesUserView;
	}

	/**
	 * @param resourcesUserView
	 *            the resourcesUserView to set
	 */
	public void setResourcesUserView(Long resourcesUserView) {
		this.resourcesUserView = resourcesUserView;
	}

	/**
	 * @return the recourcesContentId
	 */
	public Long getRecourcesContentId() {
		return recourcesContentId;
	}

	/**
	 * @param recourcesContentId
	 *            the recourcesContentId to set
	 */
	public void setRecourcesContentId(Long recourcesContentId) {
		this.recourcesContentId = recourcesContentId;
	}

	/**
	 * @return the resourcesContentTitle
	 */
	public String getResourcesContentTitle() {
		return resourcesContentTitle;
	}

	/**
	 * @param resourcesContentTitle
	 *            the resourcesContentTitle to set
	 */
	public void setResourcesContentTitle(String resourcesContentTitle) {
		this.resourcesContentTitle = resourcesContentTitle;
	}

	/**
	 * @return the resourcesContent
	 */
	public String getResourcesContent() {
		return resourcesContent;
	}

	/**
	 * @param resourcesContent
	 *            the resourcesContent to set
	 */
	public void setResourcesContent(String resourcesContent) {
		this.resourcesContent = resourcesContent;
	}

	/**
	 * @return the resourcesContentParentId
	 */
	public Long getResourcesContentParentId() {
		return resourcesContentParentId;
	}

	/**
	 * @param resourcesContentParentId
	 *            the resourcesContentParentId to set
	 */
	public void setResourcesContentParentId(Long resourcesContentParentId) {
		this.resourcesContentParentId = resourcesContentParentId;
	}

	/**
	 * @return the resourcesContentSortCode
	 */
	public Integer getResourcesContentSortCode() {
		return resourcesContentSortCode;
	}

	/**
	 * @param resourcesContentSortCode
	 *            the resourcesContentSortCode to set
	 */
	public void setResourcesContentSortCode(Integer resourcesContentSortCode) {
		this.resourcesContentSortCode = resourcesContentSortCode;
	}

	/**
	 * @return the resourcesContentHandlerCode
	 */
	public Integer getResourcesContentHandlerCode() {
		return resourcesContentHandlerCode;
	}

	/**
	 * @param resourcesContentHandlerCode the resourcesContentHandlerCode to set
	 */
	public void setResourcesContentHandlerCode(Integer resourcesContentHandlerCode) {
		this.resourcesContentHandlerCode = resourcesContentHandlerCode;
	}
}
