/**
 * COPYRIGHT@LAULYL
 */
package com.luna.utils.classes;

import java.util.List;

/**
 * @author laulyl
 * @date 2017年3月15日 下午11:16:54
 * @description
 */
public class Page<T> {

	public Page() {

	}

	/**
	 * @param list
	 * @param count
	 * @param pageSize
	 * @param pageNow
	 */
	public Page(List<T> list, long count, int pageSize, int pageNow) {
		super();
		this.list = list;
		this.count = count;
		this.pageSize = pageSize;
		this.pageNow = pageNow;
	}

	private List<T> list;

	private long count;
	private int pageSize;
	private int pageNow;

	/**
	 * @return the list
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<T> list) {
		this.list = list;
	}

	/**
	 * @return the count
	 */
	public long getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(long count) {
		this.count = count;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the pageNow
	 */
	public int getPageNow() {
		return pageNow;
	}

	/**
	 * @param pageNow
	 *            the pageNow to set
	 */
	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

}
