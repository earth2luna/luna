/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.data.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luna.dao.mapper.IResourcesMapper;
import com.luna.dao.po.Resources;
import com.luna.dao.vo.ResourcesCasecade;
import com.luna.service.dto.RenderParameter;
import com.luna.service.dto.ResourceLinkVo;
import com.luna.service.node.InputResourceOutputINode;
import com.luna.service.node.ResourcesCasecadeNode;
import com.luna.utils.FilePropertyUtils;
import com.luna.utils.LangUtils;
import com.luna.utils.RegexUtils;
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
		// IResourcesContentMarkMapper contentMarkMapper =
		// renderParameter.getContentMarkMapper();
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
			// Map<Long, List<ResourcesContentMark>> resourcesContentMarkMap =
			// ResourcesUtils
			// .selectResourcesContentMarkMapAsCasecades(contentMarkMapper,
			// current);
			// variable.setInputOutput(new
			// InputNodeOutputNode(resourcesContentMarkMap));
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

	private void initMetaDatas(List<INode> nodes) {
		if (CollectionUtils.isNotEmpty(nodes)) {
			for (INode node : nodes) {
				((ResourcesCasecadeNode) node).initMetaDatas();
			}
		}
	}

	private void format(List<INode> nodes, ResourcesCasecadeNode node, ResourceLinkVo previous, ResourceLinkVo next) {
		Writer out = null;
		try {
			initMetaDatas(nodes);
			String resourcesGeneratePath = Configure.getResourceRelativePath();
			String freemarkerTemplateName = Configure.getFreeMarkerViewName();
			Configuration configuration = renderParameter.getConfiguration();
			Map<String, Object> dataModel = new HashMap<String, Object>();
			dataModel.put("nodes", nodes);
			dataModel.put("node", node);
			dataModel.put("previous", previous);
			dataModel.put("next", next);
			dataModel.put(Constants.APPLICATION_CATEGORY_LIST_KEY, Constants.CATEGORY_LIST);
			dataModel.put(Constants.PAGE_HEADER_SAY_KEY, Constants.getPageHeaderSayVo());
			addHeader(dataModel, node, nodes);
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

	private void addHeader(Map<String, Object> model, ResourcesCasecadeNode node, List<INode> nodes) {
		model.put(Constants.HEADER_TITLE,LangUtils.append( node.getResourcesTitle()," - Apoollo - 阿波罗高质量的触动！"));
		model.put(Constants.HEADER_KEYWORDS,
				LangUtils.append(node.getResourcesTitle(), ",阿波罗,Apoollo,专业文章,计算机技术,编程,旅行,情怀,高质量,青春,教程，知识点"));
		model.put(Constants.HEADER_DESCRIPTION, getSummary(nodes));
		model.put(Constants.HEADER_AUTHOR, "作者：刘玉龙 &lt;673348317@qq.com&gt;");
	}

	private String getSummary(List<INode> nodes) {
		StringBuffer buffer = new StringBuffer();
		int length = Constants.HEADER_DESCRIPTION_LENGHT;
		evalSummary(nodes, buffer, length);
		return LangUtils.subtringDefaultAppender(buffer.toString(), length);
	}

	private void evalSummary(List<INode> nodes, StringBuffer buffer, int length) {
		if (CollectionUtils.isNotEmpty(nodes)) {
			Iterator<INode> iterator = nodes.iterator();
			while (iterator.hasNext() && buffer.length() < length) {
				INode node = iterator.next();
				ResourcesCasecadeNode casecade = (ResourcesCasecadeNode) node;
				evalueSummary(buffer, casecade);
				evalSummary(casecade.getChildrens(), buffer, length);
			}
		}

	}

	private void evalueSummary(StringBuffer buffer, ResourcesCasecadeNode t) {
		String title = LangUtils.replaceAll(t.getResourcesContentTitle(), RegexUtils.HTML_TAG, "");
		title = LangUtils.replaceAll(title, "\"", "");
		String content = LangUtils.replaceAll(t.getResourcesContent(), RegexUtils.HTML_TAG, "");
		content = LangUtils.replaceAll(content, "\"", "");
		if (!LangUtils.isBlank(title)) {
			buffer.append(title);
			buffer.append("-");
		}
		if (!LangUtils.isBlank(content)) {
			buffer.append(content);
			buffer.append(" ");
		}
	}

}
