package com.luna.utils.page;
/**
 * @author laulyl
 * @time 2017年6月9日上午11:18:18
 * @description 分页出参
 */
public class PageOutput extends PageInput {

	public PageOutput(long pages, long iteratorStart, long iteratorEnd, PageInput input) {
		super(input.getTotalCount(), input.getPageNow(), input.getPageSize(), input.getIteratorCount());
		this.pages = pages;
		this.iteratorStart = iteratorStart;
		this.iteratorEnd = iteratorEnd;
	}

	private long pages;// 总页码
	private long iteratorStart;// 迭代开始
	private long iteratorEnd;// 迭代结束

	public long getPages() {
		return pages;
	}

	public void setPages(long pages) {
		this.pages = pages;
	}

	public long getIteratorStart() {
		return iteratorStart;
	}

	public void setIteratorStart(long iteratorStart) {
		this.iteratorStart = iteratorStart;
	}

	public long getIteratorEnd() {
		return iteratorEnd;
	}

	public void setIteratorEnd(long iteratorEnd) {
		this.iteratorEnd = iteratorEnd;
	}

}