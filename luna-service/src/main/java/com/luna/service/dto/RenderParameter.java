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
	private String sts;
	private Long id;

	public RenderParameter(IResourcesMapper resourcesMapper, IResourcesContentMarkMapper contentMarkMapper,
			Configuration configuration, String sts, Long id) {
		super();
		this.resourcesMapper = resourcesMapper;
		this.contentMarkMapper = contentMarkMapper;
		this.configuration = configuration;
		this.sts = sts;
		this.id = id;
	}

	public IResourcesMapper getResourcesMapper() {
		return resourcesMapper;
	}

	public void setResourcesMapper(IResourcesMapper resourcesMapper) {
		this.resourcesMapper = resourcesMapper;
	}

	public IResourcesContentMarkMapper getContentMarkMapper() {
		return contentMarkMapper;
	}

	public void setContentMarkMapper(IResourcesContentMarkMapper contentMarkMapper) {
		this.contentMarkMapper = contentMarkMapper;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
