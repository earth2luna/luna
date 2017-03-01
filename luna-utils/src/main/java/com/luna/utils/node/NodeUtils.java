/**
 * copyright@laulyl
 */
package com.luna.utils.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import com.luna.utils.infce.IInputOutput;

/**
 * @author laulyl 2016-7-15
 * @description 节点工具
 */
public class NodeUtils {

	/**
	 * <pre>
	 *  排序所有节点，构成级联树形结构 将根节点倒序，子节点正序
	 * </pre>
	 * 
	 * @param list
	 *            无结构节点列表
	 * @param variable
	 *            节点配置变量
	 * @return 有序结构的的节点列表
	 */
	public static <T> List<INode> sort(List<T> list, IInputOutput<T, INode> inputOutput, NodeVariable variable) {
		// 拿到所有的根节点
		List<INode> roots = new ArrayList<INode>();
		// 所有子节点
		Map<Long, List<INode>> subNodes = new HashMap<Long, List<INode>>();
		if (CollectionUtils.isNotEmpty(list)) {
			Iterator<T> iterator = list.iterator();
			while (iterator.hasNext()) {
				INode node = inputOutput.get(iterator.next());
				inputOutput(node, variable);
				Long pId = node.getParentId();
				if (node.isRoot() || null == pId) {
					roots.add(node);
				} else {
					List<INode> childrens = subNodes.get(pId);
					if (null == childrens) {
						childrens = new ArrayList<INode>();
						subNodes.put(pId, childrens);
					}
					childrens.add(node);
				}
			}
			// 根节点排序
			sort(roots);
			// 排序所有子节点
			sort(roots, subNodes, 1, variable);
		}
		return roots;
	}

	// 排序所有子节点
	private static void sort(List<INode> roots, Map<Long, List<INode>> subNodes, int ret, NodeVariable variable) {
		if (MapUtils.isNotEmpty(subNodes) && CollectionUtils.isNotEmpty(roots)) {
			Iterator<INode> iterator = roots.iterator();
			while (iterator.hasNext()) {
				INode parentNode = iterator.next();
				List<INode> nodes = subNodes.get(parentNode.getId());
				if (CollectionUtils.isNotEmpty(nodes)) {
					subNodes.remove(parentNode.getId());
					setDefaultValue(parentNode, nodes, variable);
					if (0 != variable.getCasecade() && variable.getCasecade() == ret) {
						List<INode> casecades = new ArrayList<INode>();
						casecades.addAll(nodes);
						setCasecadeValue(parentNode, nodes, subNodes, casecades, variable);
						parentNode.setChildrens(casecades);
						sort(casecades);
						if (variable.isSortAllCaseCade())
							deleteIfMoreAssignNumber(parentNode, casecades, variable.getCascadeNumber());
					} else {
						parentNode.setChildrens(nodes);
						sort(nodes);
						sort(nodes, subNodes, ret + 1, variable);
					}
				}
			}
		}
	}

	private static void setCasecadeValue(INode parent, List<INode> roots, Map<Long, List<INode>> subNodes,
			List<INode> casecades, NodeVariable variable) {
		int cascadeNumber = variable.getCascadeNumber();
		// 正常情况下只要满足当前casecades.size 小于 cascadeNumber即可
		// 但是为了判断在当前 相等的情况下还需要继续探测是否有更多的子节点所以:casecades.size() <= cascadeNumber
		if (0 == cascadeNumber || variable.isSortAllCaseCade() || casecades.size() <= cascadeNumber) {
			if (MapUtils.isNotEmpty(subNodes) && CollectionUtils.isNotEmpty(roots)) {
				Iterator<INode> iterator = roots.iterator();
				while (iterator.hasNext()) {
					INode node = iterator.next();
					List<INode> nodes = subNodes.get(node.getId());
					if (CollectionUtils.isNotEmpty(nodes)) {
						casecades.addAll(nodes);
						subNodes.remove(node.getId());
						setDefaultValue(node, nodes, variable);
						// 同理以上注释内容
						if (0 == cascadeNumber || variable.isSortAllCaseCade() || casecades.size() <= cascadeNumber) {
							setCasecadeValue(parent, nodes, subNodes, casecades, variable);
						} else {
							deleteIfMoreAssignNumber(parent, casecades, cascadeNumber);
							break;
						}
					}
				}
			}
		} else {
			deleteIfMoreAssignNumber(parent, casecades, cascadeNumber);

		}
	}

	private static void deleteIfMoreAssignNumber(INode parent, List<INode> values, int assignNumber) {
		if (values.size() > assignNumber) {
			int diff = values.size() - assignNumber;
			for (int i = 0; i < diff; i++) {
				values.remove(values.size() - 1);
			}
			parent.setHasSiblingsAnother(true);
		}
	}

	// 设置父类默认值
	private static void setDefaultValue(INode parentNode, List<INode> nodes, NodeVariable variable) {
		for (INode node : nodes) {
			node.setParentNode(parentNode);
		}
	}

	private static INode inputOutput(INode node, NodeVariable variable) {
		IInputOutput<INode, INode> inputOutput = variable.getInputOutput();
		if (null == inputOutput) {
			return node;
		}
		return inputOutput.get(node);
	}

	// 当前节点倒序
	private static void sort(List<? extends INode> nodes) {
		if (CollectionUtils.isNotEmpty(nodes)) {
			Collections.sort(nodes);
		}
	}

}
