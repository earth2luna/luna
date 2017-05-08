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
public class CatcherForm {

	private String catcherWebUrl;
	private String catcherWebName;
	private List<CatchRuler> resourceTitleCatchRulers;
	private List<CatchRuler> resourceAuthorCatchRulers;
	private List<CatchRuler> resourceDateCatchRulers;
	private String resourceDateFormat;
	private Integer resourceCategoryCode;
	private Long resourceCreatorId;
	private List<String> contentXPaths;
	private List<CatchRuler> contentTitleCatchRulers;
	private List<CatchRuler> contentCatchRulers;
	private List<CatchRuler> contentPathCatchRulers;// 路径

	public CatcherForm() {
	}

	public CatcherForm(String catcherWebUrl, String catcherWebName, List<CatchRuler> resourceTitleCatchRulers,
			List<CatchRuler> resourceAuthorCatchRulers, List<CatchRuler> resourceDateCatchRulers,
			String resourceDateFormat, Integer resourceCategoryCode, Long resourceCreatorId,
			List<CatchRuler> contentTitleCatchRulers, List<CatchRuler> contentCatchRulers,
			List<CatchRuler> contentPathCatchRulers) {
		super();
		this.catcherWebUrl = catcherWebUrl;
		this.catcherWebName = catcherWebName;
		this.resourceTitleCatchRulers = resourceTitleCatchRulers;
		this.resourceAuthorCatchRulers = resourceAuthorCatchRulers;
		this.resourceDateCatchRulers = resourceDateCatchRulers;
		this.resourceDateFormat = resourceDateFormat;
		this.resourceCategoryCode = resourceCategoryCode;
		this.resourceCreatorId = resourceCreatorId;
		this.contentTitleCatchRulers = contentTitleCatchRulers;
		this.contentCatchRulers = contentCatchRulers;
		this.contentPathCatchRulers = contentPathCatchRulers;
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
	 * @return the contentTitleCatchRulers
	 */
	public List<CatchRuler> getContentTitleCatchRulers() {
		return contentTitleCatchRulers;
	}

	/**
	 * @param contentTitleCatchRulers
	 *            the contentTitleCatchRulers to set
	 */
	public void setContentTitleCatchRulers(List<CatchRuler> contentTitleCatchRulers) {
		this.contentTitleCatchRulers = contentTitleCatchRulers;
	}

	/**
	 * @return the contentCatchRulers
	 */
	public List<CatchRuler> getContentCatchRulers() {
		return contentCatchRulers;
	}

	/**
	 * @param contentCatchRulers
	 *            the contentCatchRulers to set
	 */
	public void setContentCatchRulers(List<CatchRuler> contentCatchRulers) {
		this.contentCatchRulers = contentCatchRulers;
	}

	/**
	 * @return the contentPathCatchRulers
	 */
	public List<CatchRuler> getContentPathCatchRulers() {
		return contentPathCatchRulers;
	}

	/**
	 * @param contentPathCatchRulers
	 *            the contentPathCatchRulers to set
	 */
	public void setContentPathCatchRulers(List<CatchRuler> contentPathCatchRulers) {
		this.contentPathCatchRulers = contentPathCatchRulers;
	}

	/**
	 * @return the contentXPaths
	 */
	public List<String> getContentXPaths() {
		return contentXPaths;
	}

	/**
	 * @param contentXPaths the contentXPaths to set
	 */
	public void setContentXPaths(List<String> contentXPaths) {
		this.contentXPaths = contentXPaths;
	}

}
