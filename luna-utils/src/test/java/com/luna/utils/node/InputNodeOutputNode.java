/**
 * COPYRIGHT@LAULYL
 */
package com.luna.utils.node;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import com.luna.utils.infce.IInputOutput;
import com.luna.utils.node.INode;

/**
 * @author laulyl
 * @date 2017年2月27日 下午10:43:09
 * @description
 */
public class InputNodeOutputNode implements IInputOutput<CommentVo, INode> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.utils.infce.IInputOutput#get(java.lang.Object)
	 */
	@Override
	public INode get(CommentVo i) {
		CommentNode commentNode = new CommentNode();
		try {
			PropertyUtils.copyProperties(commentNode, i);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return commentNode;
	}

}
