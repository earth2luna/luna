package com.luna.utils.page;

import com.luna.utils.LangUtils;
import com.luna.utils.infce.IInputOutput;

/**
 * @author laulyl
 * @time 2017年6月9日下午2:41:10
 * @description 路由输出
 */
public abstract class AbstractRoutePageOutput implements IInputOutput<PageOutput, String> {

	protected String route;
	protected String appender;

	private String html;

	public AbstractRoutePageOutput(String route, String appender) {
		super();
		this.route = route;
		this.appender = appender;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.solomon.common.interfaces.IInputOutput#get(java.lang.Object)
	 */
	@Override
	public String get(PageOutput i) {

		// 校验输入参数
		// Validate.isTrue(0 != i.getPageNow());
		// Validate.isTrue(0 < i.getPageSize());
		// Validate.isTrue(5 >= i.getIteratorCount());

		String ret = null;
		if (i.getTotalCount() > i.getPageSize()) {
			// 上一页 、首页及其分隔符、迭代器、尾页及其分隔符、下一页、GO
			String core = LangUtils.append(getPreviousPage(i), getFirstSplitor(i), getIteratorPage(i),
					getLastPageSplitor(i), getNextPage(i), getGo(i));
			// 包装外层DIV
			ret = warpper(core);
		}
		return ret;
	}

	protected String warpper(String input) {
		return LangUtils.append(
				"<div class=\"mb15 cl script-page-parentor-cls\"><div class=\"fr\"><div class=\"page_v1 page\">", input,
				"</div></div></div>");
	}

	// 上一页
	protected String getPreviousPage(PageOutput i) {
		String ret = null;
		if (1 < i.getPageNow()) {
			ret = "<a href=\"" + getRoute(i.getPageNow() - 1) + "\"  title=\"上一页\">&lt;</a>";
		} else {
			ret = "<span title=\"上一页\" class=\"font-c\">&lt;</span>";
		}
		return ret;
	}

	// 首页及其分隔符
	protected String getFirstSplitor(PageOutput i) {
		String ret = null;
		if (i.getIteratorStart() > 1 && i.getPages() > i.getIteratorCount()) {
			ret = "<a href=\"" + getRoute(1L) + "\"  title=\"首页\">1</a><span>...</span>";
		}
		return ret;
	}

	// 迭代
	protected String getIteratorPage(PageOutput input) {
		StringBuffer ret = new StringBuffer();
		for (long i = input.getIteratorStart(); i <= input.getIteratorEnd(); i++) {
			if (i == input.getPageNow()) {
				ret.append("<a class=\"current\" href=\"javascript:void(0);\" title=\"当前第" + i + "页\">" + i + "</a>");
			} else {
				ret.append("<a href=\"" + getRoute(i) + "\"  title=\"第" + i + "页\">" + i + "</a>");
			}
		}
		return ret.toString();
	}

	// 尾页及其分隔符
	protected String getLastPageSplitor(PageOutput i) {
		String ret = null;
		if (i.getIteratorEnd() < i.getPages() && i.getPages() > i.getIteratorCount()) {
			ret = "<span>...</span><a href=\"" + getRoute(i.getPages()) + "\"  title=\"尾页\">" + i.getPages() + "</a>";
		}
		return ret;
	}

	// 下一页
	protected String getNextPage(PageOutput i) {
		String ret = null;
		if (i.getPages() > i.getPageNow()) {
			ret = "<a href=\"" + getRoute(i.getPageNow() + 1) + "\"  title=\"下一页\">&gt;</a>";
		} else {
			ret = "<span title=\"下一页\" class=\"font-c\">&gt;</span>";
		}
		return ret;
	}

	protected String getGo(PageOutput i) {
		StringBuffer ret = new StringBuffer();
		ret.append("<span class=\"txt\">到第</span>");
		ret.append("<span class=\"numBox_v1\"><input class=\"numInput_v1\" value=\"" + i.getPageNow()
				+ "\" type=\"text\"><i class=\"plus_v1\" onclick=\"PageObject.inputValuePlus(this," + i.getPages()
				+ ");\"></i><i class=\"reduce_v1\" onclick=\"PageObject.inputValueMinus(this)\"></i></span>");
		ret.append("<span class=\"txt\">页&nbsp;</span>");
		ret.append("<input value=\"go\" class=\"btn-vice script-go\" type=\"button\" onclick=\"PageObject.go(this,'"
				+ route + "," + appender + "'," + i.getPages() + ")\">");
		return ret.toString();
	}

	public abstract String getRoute(Long pageNow);

	public String getHtml() {
		return html;
	}

}