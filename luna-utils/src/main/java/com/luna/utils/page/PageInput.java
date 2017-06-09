package com.luna.utils.page;
/**
 * @author laulyl
 * @time 2017年6月9日下午4:43:57
 * @description 分页入参
 */
public class PageInput {

	private long totalCount;// 总页码
	private long pageNow;// 当前页码
	private long pageSize;// 每页的条数
	private long iteratorCount;// 迭代行数

	public PageInput(long totalCount, long pageNow, long pageSize, long iteratorCount) {
		super();
		this.totalCount = totalCount;
		this.pageNow = pageNow;
		this.pageSize = pageSize;
		this.iteratorCount = iteratorCount;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getPageNow() {
		return pageNow;
	}

	public void setPageNow(long pageNow) {
		this.pageNow = pageNow;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getIteratorCount() {
		return iteratorCount;
	}

	public void setIteratorCount(long iteratorCount) {
		this.iteratorCount = iteratorCount;
	}
}