package com.luna.utils.page;

import com.luna.utils.infce.IInputOutput;

/**
 * @author laulyl
 * @time 2017年6月9日上午11:34:50
 * @description 计算基本输出
 */
public class InputOutputPage implements IInputOutput<PageInput, PageOutput> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.solomon.common.interfaces.IInputOutput#get(java.lang.Object)
	 */
	@Override
	public PageOutput get(PageInput input) {
		// 校验输入参数
		if (0 >= input.getPageNow()) {
			input.setPageNow(1);
		}
		if (0 >= input.getPageSize()) {
			input.setPageSize(1);
		}
		if (5 > input.getIteratorCount()) {
			input.setIteratorCount(5);
		}
		// 商
		long quotient = input.getTotalCount() / input.getPageSize();
		// 余数
		long remainder = input.getTotalCount() % input.getPageSize();
		// 总页码数
		long pages = 0 == remainder ? quotient : quotient + 1;

		// 校验当前页码
		if (input.getPageNow() > pages) {
			input.setPageNow(pages);
		}

		long half = input.getIteratorCount() / 2;

		long diff = pages - input.getIteratorCount();

		// 开始迭代计算公式
		long startCalc = input.getPageNow() - half;

		startCalc = startCalc > diff ? diff + 1 : startCalc;

		// 开始迭代值
		long iteratorStart = startCalc < 1 ? 1 : startCalc;

		// 结束迭代计算公式
		long endCalc = 1 == iteratorStart ? input.getIteratorCount() : input.getPageNow() + half;

		// 结束迭代值
		long iteratorEnd = endCalc > pages ? pages : endCalc;

		// 获取输入对象
		return new PageOutput(pages, iteratorStart, iteratorEnd, input);
	}

}