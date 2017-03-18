/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.dto;

import com.luna.dao.mapper.IResourcesContentMarkMapper;
import com.luna.dao.mapper.IResourcesMapper;

import freemarker.template.Configuration;

/**
 * @author laulyl
 * @date 2017年3月18日 下午1:17:40
 * @description
 */
public class RenderParameter {
	private IResourcesMapper resourcesMapper;
	private IResourcesContentMarkMapper contentMarkMapper;
	private Configuration configuration;
	private String resourcesGeneratePath;
	private String freemarkerTemplateName;
	private String sts;
	private Long id;

	public RenderParameter(IResourcesMapper resourcesMapper, IResourcesContentMarkMapper contentMarkMapper,
			Configuration configuration, String resourcesGeneratePath, String freemarkerTemplateName, String sts,
			Long id) {
		super();
		this.resourcesMapper = resourcesMapper;
		this.contentMarkMapper = contentMarkMapper;
		this.configuration = configuration;
		this.resourcesGeneratePath = resourcesGeneratePath;
		this.freemarkerTemplateName = freemarkerTemplateName;
		this.sts = sts;
		this.id = id;
	}

	/**
	 * @return the resourcesMapper
	 */
	public IResourcesMapper getResourcesMapper() {
		return resourcesMapper;
	}

	/**
	 * @param resourcesMapper
	 *            the resourcesMapper to set
	 */
	public void setResourcesMapper(IResourcesMapper resourcesMapper) {
		this.resourcesMapper = resourcesMapper;
	}

	/**
	 * @return the contentMarkMapper
	 */
	public IResourcesContentMarkMapper getContentMarkMapper() {
		return contentMarkMapper;
	}

	/**
	 * @param contentMarkMapper
	 *            the contentMarkMapper to set
	 */
	public void setContentMarkMapper(IResourcesContentMarkMapper contentMarkMapper) {
		this.contentMarkMapper = contentMarkMapper;
	}

	/**
	 * @return the configuration
	 */
	public Configuration getConfiguration() {
		return configuration;
	}

	/**
	 * @param configuration
	 *            the configuration to set
	 */
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	/**
	 * @return the resourcesGeneratePath
	 */
	public String getResourcesGeneratePath() {
		return resourcesGeneratePath;
	}

	/**
	 * @param resourcesGeneratePath
	 *            the resourcesGeneratePath to set
	 */
	public void setResourcesGeneratePath(String resourcesGeneratePath) {
		this.resourcesGeneratePath = resourcesGeneratePath;
	}

	/**
	 * @return the freemarkerTemplateName
	 */
	public String getFreemarkerTemplateName() {
		return freemarkerTemplateName;
	}

	/**
	 * @param freemarkerTemplateName
	 *            the freemarkerTemplateName to set
	 */
	public void setFreemarkerTemplateName(String freemarkerTemplateName) {
		this.freemarkerTemplateName = freemarkerTemplateName;
	}

	/**
	 * @return the sts
	 */
	public String getSts() {
		return sts;
	}

	/**
	 * @param sts
	 *            the sts to set
	 */
	public void setSts(String sts) {
		this.sts = sts;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
