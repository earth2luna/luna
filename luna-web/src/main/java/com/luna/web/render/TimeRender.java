/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.render;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import com.luna.dao.mapper.IResourcesContentMarkMapper;
import com.luna.dao.mapper.IResourcesMapper;
import com.luna.dao.po.ResourcesContentMark;
import com.luna.dao.vo.ResourcesCasecade;
import com.luna.service.data.utils.ResourcesUtils;
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
 * @date 2017年2月26日 下午1:31:10
 * @description
 */
@Component
public class TimeRender {

	private static final Logger LOGGER = LoggerFactory.getLogger(TimeRender.class);

	@Autowired
	private IResourcesMapper resourcesMapper;
	@Autowired
	private IResourcesContentMarkMapper contentMarkMapper;
	@Autowired
	private FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean;
	@Value("${resources.generate.path}")
	private String resourcesGeneratePath;
	@Value("${freemarker.template.name}")
	private String freemarkerTemplateName;

	private static InputResourceOutputINode inputOutput = new InputResourceOutputINode();

	private static NodeVariable variable = new NodeVariable();

	@Scheduled(cron = "0 20 * * * ?")
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

	public static void pf(List<INode> list, int index) {
		index++;
		if (CollectionUtils.isNotEmpty(list)) {
			Iterator<INode> iterator = list.iterator();
			while (iterator.hasNext()) {
				ResourcesCasecadeNode node = (com.luna.service.node.ResourcesCasecadeNode) iterator.next();
				pf(node, index);
				pf(node.getChildrens(), index);
			}
		}
	}

	public static void pf(ResourcesCasecadeNode node, int index) {
		System.out.println(getPrefix(index) + node.getResourcesContentTitle());
	}

	public static String getPrefix(int index) {
		String ret = "";
		for (int i = 1; i <= index; i++) {
			ret += "-";
		}
		return ret;
	}
}
