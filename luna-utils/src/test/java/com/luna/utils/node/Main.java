/**
 * COPYRIGHT@LAULYL
 */
package com.luna.utils.node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.luna.utils.DateUtils;
import com.luna.utils.node.INode;
import com.luna.utils.node.NodeUtils;
import com.luna.utils.node.NodeVariable;

/**
 * @author laulyl
 * @date 2017年2月27日 下午5:52:58
 * @description
 */
public class Main {

	public static void main(String[] args) {
		List<CommentVo> list = new ArrayList<>();
		list.add(new CommentVo(1L, null, "今天的月亮好圆啊", "laulyl", DateUtils.parse("2017-02-01", DateUtils.DATE_PATTERN_2),
				0));
		list.add(new CommentVo(2L, 1L, "是啊，今天的月亮好圆啊", "zhaomin",
				DateUtils.parse("2017-02-02", DateUtils.DATE_PATTERN_2), 0));
		list.add(new CommentVo(3L, 2L, "是啊，是啊，今天的月亮好圆啊", "xiaozhao",
				DateUtils.parse("2017-02-01", DateUtils.DATE_PATTERN_2), 0));

		list.add(new CommentVo(4L, 1L, "圆个毛线！", "zhouzhiruo", DateUtils.parse("2017-02-01", DateUtils.DATE_PATTERN_2),
				0));

		List<INode> nodes = NodeUtils.sort(list, new InputNodeOutputNode(), new NodeVariable());

		Iterator<INode> iterator = nodes.iterator();
		while (iterator.hasNext()) {
			CommentNode node = (CommentNode) iterator.next();
			pf(node, 0);
			pf(node.getChildrens(), 0);
		}
	}

	public static void pf(List<CommentNode> list, int index) {
		index++;
		if (CollectionUtils.isNotEmpty(list)) {
			Iterator<CommentNode> iterator = list.iterator();
			while (iterator.hasNext()) {
				CommentNode node = iterator.next();
				pf(node, index);
				pf(node.getChildrens(), index);
			}
		}
	}

	public static void pf(CommentNode node, int index) {
		System.out.println(
				getPrefix(index) + " " + DateUtils.getDateFormat(node.getCreateTime(), DateUtils.DATE_PATTERN_2) + " "
						+ node.getOwner() + " 说:" + node.getContent());
	}

	public static String getPrefix(int index) {
		String ret = "";
		for (int i = 0; i <= index; i++) {
			ret += "-";
		}
		return ret;
	}

}
