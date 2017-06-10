package com.luna.utils.page;

import com.luna.utils.LangUtils;

/**
 * @author laulyl
 * @time 2017年6月9日上午11:32:28
 * @description 分页工具
 */
public class PageUtils {

	// 输出基本计算参数
	public static PageOutput evaluate(PageInput pageInput) {
		return new InputOutputPage().get(pageInput);
	}

	// 路由输出，直接输入html
	// 由v3.js支持
	public static String evaluate(PageInput pageInput, String route) {
		return new PathRoutePageOuput(route).get(evaluate(pageInput));
	}

	// 默认路由输出
	// 由v3.js支持
	public static String evaluate(Long totalCount, Long pageNow, Long pageSize, String route) {
		return evaluate(new PageInput(LangUtils.longValueOfNumber(totalCount), LangUtils.defaultValue(pageNow, 1L),
				LangUtils.defaultValue(pageSize, 10L), 5), route);
	}

}