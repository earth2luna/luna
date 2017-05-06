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

import com.luna.dao.mapper.IResourcesContentMarkMapper;
import com.luna.dao.mapper.IResourcesMapper;
import com.luna.dao.po.ResourcesContentMark;
import com.luna.dao.vo.ResourcesCasecade;
import com.luna.service.dto.RenderParameter;
import com.luna.service.node.InputNodeOutputNode;
import com.luna.service.node.InputResourceOutputINode;
import com.luna.service.node.ResourcesCasecadeNode;
import com.luna.utils.FilePropertyUtils;
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
	private RenderParameter renderParameter;

	/**
	 * @param renderParameter
	 */
	public Render(RenderParameter renderParameter) {
		super();
		this.renderParameter = renderParameter;
	}

	public void render() {
		LOGGER.info("[start render htmls]");
		// 准备参数
		NodeVariable variable = new NodeVariable();
		int pageNow = 1;
		IResourcesMapper resourcesMapper = renderParameter.getResourcesMapper();
		IResourcesContentMarkMapper contentMarkMapper = renderParameter.getContentMarkMapper();
		String status = renderParameter.getSts();
		Long resourcesId = renderParameter.getId();
		// 获取资源资源内容列表
		List<ResourcesCasecade> casecades = ResourcesUtils.selectResourcesCasecades(resourcesMapper, pageNow, status,
				resourcesId);
		while (CollectionUtils.isNotEmpty(casecades)) {
			// 获取资源内容标记列表
			Map<Long, List<ResourcesContentMark>> resourcesContentMarkMap = ResourcesUtils
					.selectResourcesContentMarkMapAsCasecades(contentMarkMapper, casecades);
			variable.setInputOutput(new InputNodeOutputNode(resourcesContentMarkMap));
			// 划分等级
			List<INode> nodes = NodeUtils.sort(casecades, inputOutput, variable);
			ResourcesCasecadeNode node = (ResourcesCasecadeNode) nodes.get(0);
			// 执行渲染
			format(nodes, node);
			// 数据库上线
			ResourcesUtils.online(resourcesMapper, node.getResourcesId());
			// 再次获取
			casecades = ResourcesUtils.selectResourcesCasecades(resourcesMapper, ++pageNow, status, resourcesId);
		}
		LOGGER.info("[end render htmls]");
	}

	private void format(List<INode> nodes, ResourcesCasecadeNode node) {
		Writer out = null;
		try {
			String resourcesGeneratePath = Configure.getResourceRelativePath();
			String freemarkerTemplateName = Configure.getFreeMarkerViewName();
			Configuration configuration = renderParameter.getConfiguration();
			Map<String, Object> dataModel = new HashMap<String, Object>();
			dataModel.put("node", node);
			dataModel.put("nodes", nodes);
			dataModel.put(Constants.PAGE_HEADER_SAY_KEY, Constants.getPageHeaderSayVo());
			File origin = ResourcesUtils.getResourcesFile(resourcesGeneratePath, node.getResourcesId());
			FilePropertyUtils.touchFile(origin);
			out = new FileWriter(origin);
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
