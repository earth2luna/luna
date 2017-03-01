/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.render;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.luna.dao.mapper.IResourcesContentMarkMapper;
import com.luna.dao.mapper.IResourcesMapper;
import com.luna.dao.po.ResourcesContentMark;
import com.luna.dao.vo.ResourcesCasecade;
import com.luna.service.data.utils.ResourcesUtils;
import com.luna.utils.node.INode;
import com.luna.utils.node.NodeUtils;
import com.luna.utils.node.NodeVariable;

/**
 * @author laulyl
 * @date 2017年2月26日 下午1:31:10
 * @description
 */
@Component
public class TimeRender {

	@Autowired
	private IResourcesMapper resourcesMapper;
	@Autowired
	private IResourcesContentMarkMapper contentMarkMapper;

	private static InputResourceOutputINode inputOutput = new InputResourceOutputINode();

	private static NodeVariable variable = new NodeVariable();

	@Scheduled(cron = "0 01 16 * * ?")
	public void render() {
		int startIndex = 0;
		List<ResourcesCasecade> casecades = ResourcesUtils.selectResourcesCasecades(resourcesMapper, startIndex);
		Map<Long, List<ResourcesContentMark>> resourcesContentMarkMap = ResourcesUtils
				.selectResourcesContentMarkMapAsCasecades(contentMarkMapper, casecades);
		variable.setInputOutput(new InputNodeOutputNode(resourcesContentMarkMap));
		while (CollectionUtils.isNotEmpty(casecades)) {
			List<INode> nodes = NodeUtils.sort(casecades, inputOutput, variable);

			System.out.println("----------------------------------------------------------------");
			pf(nodes, 0);

			casecades = ResourcesUtils.selectResourcesCasecades(resourcesMapper, ++startIndex);
			resourcesContentMarkMap = ResourcesUtils.selectResourcesContentMarkMapAsCasecades(contentMarkMapper,
					casecades);
			variable.setInputOutput(new InputNodeOutputNode(resourcesContentMarkMap));
		}

	}

	public static void pf(List<INode> list, int index) {
		index++;
		if (CollectionUtils.isNotEmpty(list)) {
			Iterator<INode> iterator = list.iterator();
			while (iterator.hasNext()) {
				ResourcesCasecadeNode node = (com.luna.service.render.ResourcesCasecadeNode) iterator.next();
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
