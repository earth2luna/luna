/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.data.utils;

import java.util.List;

import com.luna.service.CategoryService;
import com.luna.utils.node.INode;

/**
 * @author laulyl
 * @date 2017年6月5日 下午9:24:56
 * @description
 */
public class ApplicationGetUtils {
	
	public static List<INode> getCategories(){
		return ApplicationContextHolder.getBean(CategoryService.class).getCategories();
	}

}
