/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.catcher;

import java.util.List;

/**
 * @author laulyl
 * @date 2017年4月4日 下午10:38:35
 * @description
 */
public class CatcherModel {

	private String catcherWebUrl;
	private String catcherWebName;
	private Integer catcherWebsiteCode;
	private String attachementPath;
	private Long categoryId;
	
	private String resourceTitle;// 文章标题
	private String resourceAuthor;// 作者
	private String resourceDate;// 发布日期
	
	private List<CatchRuler> resourceTitleCatchRulers;// 文章标题
	private List<CatchRuler> resourceAuthorCatchRulers;// 作者
	private List<CatchRuler> resourceDateCatchRulers;// 发布日期
	private String resourceDateFormat;
	private Integer resourceCategoryCode;
	private Long resourceCreatorId;
	private List<CatcherIteratorRuler> iteratorRulers;

	public CatcherModel() {
	}

	public CatcherModel(String catcherWebUrl, String catcherWebName, List<CatchRuler> resourceTitleCatchRulers,
			List<CatchRuler> resourceAuthorCatchRulers, List<CatchRuler> resourceDateCatchRulers,
			String resourceDateFormat, Integer resourceCategoryCode, Long resourceCreatorId,
			List<CatcherIteratorRuler> iteratorRulers) {
		super();
		this.catcherWebUrl = catcherWebUrl;
		this.catcherWebName = catcherWebName;
		this.resourceTitleCatchRulers = resourceTitleCatchRulers;
		this.resourceAuthorCatchRulers = resourceAuthorCatchRulers;
		this.resourceDateCatchRulers = resourceDateCatchRulers;
		this.resourceDateFormat = resourceDateFormat;
		this.resourceCategoryCode = resourceCategoryCode;
		this.resourceCreatorId = resourceCreatorId;
		this.iteratorRulers = iteratorRulers;
	}

	/**
	 * @return the catcherWebUrl
	 */
	public String getCatcherWebUrl() {
		return catcherWebUrl;
	}

	/**
	 * @param catcherWebUrl
	 *            the catcherWebUrl to set
	 */
	public void setCatcherWebUrl(String catcherWebUrl) {
		this.catcherWebUrl = catcherWebUrl;
	}

	/**
	 * @return the catcherWebName
	 */
	public String getCatcherWebName() {
		return catcherWebName;
	}

	/**
	 * @param catcherWebName
	 *            the catcherWebName to set
	 */
	public void setCatcherWebName(String catcherWebName) {
		this.catcherWebName = catcherWebName;
	}

	/**
	 * @return the resourceTitleCatchRulers
	 */
	public List<CatchRuler> getResourceTitleCatchRulers() {
		return resourceTitleCatchRulers;
	}

	/**
	 * @param resourceTitleCatchRulers
	 *            the resourceTitleCatchRulers to set
	 */
	public void setResourceTitleCatchRulers(List<CatchRuler> resourceTitleCatchRulers) {
		this.resourceTitleCatchRulers = resourceTitleCatchRulers;
	}

	/**
	 * @return the resourceAuthorCatchRulers
	 */
	public List<CatchRuler> getResourceAuthorCatchRulers() {
		return resourceAuthorCatchRulers;
	}

	/**
	 * @param resourceAuthorCatchRulers
	 *            the resourceAuthorCatchRulers to set
	 */
	public void setResourceAuthorCatchRulers(List<CatchRuler> resourceAuthorCatchRulers) {
		this.resourceAuthorCatchRulers = resourceAuthorCatchRulers;
	}

	/**
	 * @return the resourceDateCatchRulers
	 */
	public List<CatchRuler> getResourceDateCatchRulers() {
		return resourceDateCatchRulers;
	}

	/**
	 * @param resourceDateCatchRulers
	 *            the resourceDateCatchRulers to set
	 */
	public void setResourceDateCatchRulers(List<CatchRuler> resourceDateCatchRulers) {
		this.resourceDateCatchRulers = resourceDateCatchRulers;
	}

	/**
	 * @return the resourceDateFormat
	 */
	public String getResourceDateFormat() {
		return resourceDateFormat;
	}

	/**
	 * @param resourceDateFormat
	 *            the resourceDateFormat to set
	 */
	public void setResourceDateFormat(String resourceDateFormat) {
		this.resourceDateFormat = resourceDateFormat;
	}

	/**
	 * @return the resourceCategoryCode
	 */
	public Integer getResourceCategoryCode() {
		return resourceCategoryCode;
	}

	/**
	 * @param resourceCategoryCode
	 *            the resourceCategoryCode to set
	 */
	public void setResourceCategoryCode(Integer resourceCategoryCode) {
		this.resourceCategoryCode = resourceCategoryCode;
	}

	/**
	 * @return the resourceCreatorId
	 */
	public Long getResourceCreatorId() {
		return resourceCreatorId;
	}

	/**
	 * @param resourceCreatorId
	 *            the resourceCreatorId to set
	 */
	public void setResourceCreatorId(Long resourceCreatorId) {
		this.resourceCreatorId = resourceCreatorId;
	}

	/**
	 * @return the iteratorRulers
	 */
	public List<CatcherIteratorRuler> getIteratorRulers() {
		return iteratorRulers;
	}

	/**
	 * @param iteratorRulers
	 *            the iteratorRulers to set
	 */
	public void setIteratorRulers(List<CatcherIteratorRuler> iteratorRulers) {
		this.iteratorRulers = iteratorRulers;
	}

	/**
	 * @return the catcherWebsiteCode
	 */
	public Integer getCatcherWebsiteCode() {
		return catcherWebsiteCode;
	}

	/**
	 * @param catcherWebsiteCode
	 *            the catcherWebsiteCode to set
	 */
	public void setCatcherWebsiteCode(Integer catcherWebsiteCode) {
		this.catcherWebsiteCode = catcherWebsiteCode;
	}

	/**
	 * @return the attachementPath
	 */
	public String getAttachementPath() {
		return attachementPath;
	}

	/**
	 * @param attachementPath
	 *            the attachementPath to set
	 */
	public void setAttachementPath(String attachementPath) {
		this.attachementPath = attachementPath;
	}

	public String getResourceTitle() {
		return resourceTitle;
	}

	public void setResourceTitle(String resourceTitle) {
		this.resourceTitle = resourceTitle;
	}

	public String getResourceAuthor() {
		return resourceAuthor;
	}

	public void setResourceAuthor(String resourceAuthor) {
		this.resourceAuthor = resourceAuthor;
	}

	public String getResourceDate() {
		return resourceDate;
	}

	public void setResourceDate(String resourceDate) {
		this.resourceDate = resourceDate;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}


}
