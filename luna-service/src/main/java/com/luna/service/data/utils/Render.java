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
import com.luna.dao.po.Resources;
import com.luna.dao.po.ResourcesContentMark;
import com.luna.dao.vo.ResourcesCasecade;
import com.luna.service.dto.RenderParameter;
import com.luna.service.dto.ResourceLinkVo;
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

		List<ResourcesCasecade> previous = null;
		// 获取资源资源内容列表
		List<ResourcesCasecade> current = ResourcesUtils.selectResourcesCasecades(resourcesMapper, pageNow, status,
				resourcesId);
		while (CollectionUtils.isNotEmpty(current)) {

			List<ResourcesCasecade> next = ResourcesUtils.selectResourcesCasecades(resourcesMapper, ++pageNow, null,
					resourcesId);
			// 获取资源内容标记列表
			Map<Long, List<ResourcesContentMark>> resourcesContentMarkMap = ResourcesUtils
					.selectResourcesContentMarkMapAsCasecades(contentMarkMapper, current);
			variable.setInputOutput(new InputNodeOutputNode(resourcesContentMarkMap));
			// 划分等级
			List<INode> nodes = NodeUtils.sort(current, inputOutput, variable);
			ResourcesCasecadeNode node = (ResourcesCasecadeNode) nodes.get(0);
			// 执行渲染
			format(nodes, node, getResourceLinkVoFromCasecad(previous), getResourceLinkVoFromCasecad(next));
			// 数据库上线
			ResourcesUtils.online(resourcesMapper, node.getResourcesId());
			// 数据交换
			previous = current;
			current = next;
		}
		LOGGER.info("[end render htmls]");
	}

	public void renderSingle() {
		// 准备参数
		NodeVariable variable = new NodeVariable();
		IResourcesMapper resourcesMapper = renderParameter.getResourcesMapper();
		long rsId = renderParameter.getId();
		List<ResourcesCasecade> current = ResourcesUtils.selectResourcesCasecades(resourcesMapper, 1, null, rsId);
		if (CollectionUtils.isNotEmpty(current)) {
			List<INode> nodes = NodeUtils.sort(current, inputOutput, variable);
			ResourcesCasecadeNode node = (ResourcesCasecadeNode) nodes.get(0);
			List<Resources> previous = ResourcesUtils.selectPreviousResourcesCasecades(resourcesMapper, rsId);
			List<Resources> next = ResourcesUtils.selectNextResourcesCasecades(resourcesMapper, rsId);
			format(nodes, node, getResourceLinkVoFromResoure(previous), getResourceLinkVoFromResoure(next));
			// 数据库上线
			ResourcesUtils.online(resourcesMapper, node.getResourcesId());
		}
	}

	private ResourceLinkVo getResourceLinkVoFromResoure(List<Resources> resources) {
		ResourceLinkVo ret = null;
		if (CollectionUtils.isNotEmpty(resources)) {
			Resources resoure = resources.get(0);
			ret = new ResourceLinkVo(resoure.getTitle(),
					ResourcesUtils.getWebResourcesPath(Configure.getResourceRelativePath(), resoure));
		}
		return ret;
	}

	private ResourceLinkVo getResourceLinkVoFromCasecad(List<ResourcesCasecade> casecades) {
		ResourceLinkVo ret = null;
		if (CollectionUtils.isNotEmpty(casecades)) {
			ResourcesCasecadeNode casecadeNode = (ResourcesCasecadeNode) inputOutput.get(casecades.get(0));
			ret = new ResourceLinkVo(casecadeNode.getResourcesTitle(),
					ResourcesUtils.getWebResourcesPath(Configure.getResourceRelativePath(), casecadeNode));
		}
		return ret;
	}

	private void format(List<INode> nodes, ResourcesCasecadeNode node, ResourceLinkVo previous, ResourceLinkVo next) {
		Writer out = null;
		try {
			String resourcesGeneratePath = Configure.getResourceRelativePath();
			String freemarkerTemplateName = Configure.getFreeMarkerViewName();
			Configuration configuration = renderParameter.getConfiguration();
			Map<String, Object> dataModel = new HashMap<String, Object>();
			dataModel.put("nodes", nodes);
			dataModel.put("node", node);
			dataModel.put("previous", previous);
			dataModel.put("next", next);
			dataModel.put(Constants.PAGE_HEADER_SAY_KEY, Constants.getPageHeaderSayVo());
			File origin = ResourcesUtils.getResourcesFile(resourcesGeneratePath, node);
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
