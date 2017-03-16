/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.data.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import com.luna.dao.mapper.IResourcesContentMarkMapper;
import com.luna.dao.mapper.IResourcesMapper;
import com.luna.dao.po.ResourcesContentMark;
import com.luna.dao.vo.ResourcesCasecade;
import com.luna.service.node.InputNodeOutputNode;
import com.luna.service.node.InputResourceOutputINode;
import com.luna.service.node.ResourcesCasecadeNode;
import com.luna.utils.FilePropertyUtils;
import com.luna.utils.LangUtils;
import com.luna.utils.node.INode;
import com.luna.utils.node.NodeUtils;
import com.luna.utils.node.NodeVariable;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

/**
 * @author laulyl
 * @date 2017年3月16日 下午11:28:51
 * @description
 */
public class Render {

	private static final Logger LOGGER = LoggerFactory.getLogger(Render.class);
	private static InputResourceOutputINode inputOutput = new InputResourceOutputINode();

	private IResourcesMapper resourcesMapper;
	private IResourcesContentMarkMapper contentMarkMapper;
	private FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean;
	private String resourcesGeneratePath;
	private String freemarkerTemplateName;
	private NodeVariable variable;

	public Render(IResourcesMapper resourcesMapper, IResourcesContentMarkMapper contentMarkMapper,
			FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean, String resourcesGeneratePath,
			String freemarkerTemplateName, NodeVariable variable) {
		super();
		this.resourcesMapper = resourcesMapper;
		this.contentMarkMapper = contentMarkMapper;
		this.freeMarkerConfigurationFactoryBean = freeMarkerConfigurationFactoryBean;
		this.resourcesGeneratePath = resourcesGeneratePath;
		this.freemarkerTemplateName = freemarkerTemplateName;
		this.variable = variable;
	}

	public void render() {
		LOGGER.info("[start render htmls]");
		int startIndex = 0;
		List<ResourcesCasecade> casecades = ResourcesUtils.selectResourcesCasecades(resourcesMapper, startIndex);
		Map<Long, List<ResourcesContentMark>> resourcesContentMarkMap = ResourcesUtils
				.selectResourcesContentMarkMapAsCasecades(contentMarkMapper, casecades);
		variable.setInputOutput(new InputNodeOutputNode(resourcesContentMarkMap));
		while (CollectionUtils.isNotEmpty(casecades)) {
			List<INode> nodes = NodeUtils.sort(casecades, inputOutput, variable);
			format(nodes);
			casecades = ResourcesUtils.selectResourcesCasecades(resourcesMapper, ++startIndex);
			resourcesContentMarkMap = ResourcesUtils.selectResourcesContentMarkMapAsCasecades(contentMarkMapper,
					casecades);
			variable.setInputOutput(new InputNodeOutputNode(resourcesContentMarkMap));
		}
		LOGGER.info("[end render htmls]");
	}

	private void format(List<INode> nodes) {
		Writer out = null;
		try {
			ResourcesCasecadeNode node = (ResourcesCasecadeNode) nodes.get(0);
			Map<String, Object> dataModel = new HashMap<String, Object>();
			dataModel.put("node", node);
			dataModel.put("nodes", nodes);
			File origin = new File(resourcesGeneratePath, LangUtils.append(node.getResourcesId(), ".html"));
			FilePropertyUtils.touchFile(origin);
			out = new FileWriter(origin);
			Configuration configuration = freeMarkerConfigurationFactoryBean.getObject();
			Template template = configuration.getTemplate(freemarkerTemplateName);
			template.process(dataModel, out);
			LOGGER.info(origin.toString());
		} catch (TemplateNotFoundException e) {
			LOGGER.error("[can't find the template]", e);
		} catch (MalformedTemplateNameException e) {
			LOGGER.error("[malformed templateName problem]", e);
		} catch (ParseException e) {
			LOGGER.error("[parse template has problem]", e);
		} catch (IOException e) {
			LOGGER.error("[IO problem]", e);
		} catch (TemplateException e) {
			LOGGER.error("[template problem]", e);
		} catch (Exception e) {
			LOGGER.error("[format error]", e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

}
