package com.luna.utils.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author laulyl
 * @since 2011-09-25 17：30
 * @update 2011-10-24 03：18
 * @lastUpdate 2013-10-08 14：14
 * @description: 这是一个关于关于分页的类，应用于WEB开发中数据分页。封装了分页所用到的所有的变量。在页码产生变化中每个页码的状态。
 *               此类全部围绕pageNow成员变量展开
 *               每次的访问中首先要传入pageNow的值。如果没有传入此值，则默认为初始化状态。然后根据（first--首页 、
 *               previous--上一页 、值、 next--下一页 、
 *               end--尾页）调用pageAction方法，则会相应改变所有的值的状态。
 *               两个主要用到的方法全部采用链式编程，返回本对象。方便开发。
 * @Example (分两部操作) 第一步，初始化（预先拟定biz.getCounts()，返回总行数）页面中的点每次访问都要传入action值 new
 *          Split ().setCounts(biz.getCounts().intValue()).split(split.
 *          getAction()); 第二步，调用 biz.分页方法（split.getpageNow,split.getPageSize）;
 */
public class SplitPage<T> {
	// 被分页数据库表中所有的行数有多少行，依赖外部注入。在初始化类时注入参数。
	private int counts;
	// 当前页码。默认页码为1.用户可以根据自己需求进行调整。正常情况下默认为1
	private int pageNow = 1;
	// 总共页码数量。根据counts pageSize 计算的出结果。其值有这两个参数决定
	private int pages;
	// 每页现实的数据行数。默认每页显示行。此数据为方便测试。程序员可以根据自己需求进行调整。也可以由外部注入
	private int pageSize = 10;
	// 分页后开始条数
	private int beginRowCount;
	// 分页后结束条数
	private int endRowCount;
	// 如果应用的Struts，此参数为对应相应的 split中的action，作为临时传参使用，用户也可在Struts的Action中自己定义
	private String action;
	// 迭代变量。默认从为从1开始循环。
	private int itrator = 1;
	// 迭代次数。由definedValue决定
	private int itratorCount = definedValue;
	// 自定义迭代次数。并且关联pageNow的值为此参数时进行相应的改变。建议5-10为合理范围
	private static int definedValue = 5;

	private static final String DIGITAL_PATTERN = "^\\d{1,}$";

	// 存储分页后数据
	private List<T> objects = new ArrayList<T>();

	public SplitPage() {

	}

	/**
	 * @param counts
	 *            数据库实际记录数量
	 */
	public SplitPage(int counts, String pageSize) {
		if (null != pageSize) {
			if (pageSize.matches(DIGITAL_PATTERN)) {
				this.pageSize = Integer.valueOf(pageSize);
			}
		}
		initialPages(counts);
	}

	/**
	 * 初始化总页数
	 */
	private void initialPages(int countParamter) {
		// 设置总行数
		this.counts = countParamter;
		// 通过总行数计算总页数
		pages = counts / pageSize;
		if (counts % pageSize != 0)
			pages++;
		if (0 >= pages)
			pages = 1;
		initailIteratorCount();
		calcRowPosition();
	}

	private void initailIteratorCount() {
		itratorCount = definedValue;
		// 设置每页页码迭代数
		// 设置迭代页码数大于实际页数
		if (pages < definedValue) {
			this.itratorCount = pages;
		}
	}

	/**
	 * @param action
	 *            分页命令
	 */
	public SplitPage<T> split(String action, String pageNow) {
		if (!isBlank(pageNow)) {
			pageNow = pageNow.trim();
			if (pageNow.matches(DIGITAL_PATTERN)) {
				setPageNow(Integer.valueOf(pageNow));
			}
		}
		return pageAction(action);
	}

	/**
	 * @param action
	 *            共有五个个参数 （first--首页 、 previous--上一页 、 next--下一页
	 *            、end---尾页）如果参数错误则有 转换异常错误
	 * @return this
	 */
	private final SplitPage<T> pageAction(String action) {
		if (null == action)
			return this;
		if (action.endsWith("first")) {// 首页
			this.pageNow = 1;
		} else if (action.endsWith("end")) {// 尾页
			pageNow = this.pages;
		} else if (action.equals("previous")) {// 上一页
			pageNow--;
		} else if (action.equals("next")) {// 下一页
			pageNow++;
		} else {
			// 带值的页码
			protectPageNowFront(action);
		}
		// 以下将pageNow划分为三个范围来规定iterator and iteratorCount’s values
		// 1，比自定义范围小的
		// 2,比自定范围大或者等于自定义范围 但是 小于（总页数减定义页数减一的值）。这样做的目的是为了前后对称
		// 3,else
		if (pageNow < definedValue) {
			this.itrator = 1;
			initailIteratorCount();
		} else if (pageNow >= definedValue && pageNow <= pages - (definedValue - 1)) {
			this.itrator = pageNow - definedValue / 2;
			this.itratorCount = pageNow + definedValue / 2;
		} else {
			this.itrator = pages - (definedValue - 1);
			this.itratorCount = pages;
		}
		protectPageNowEnd();
		calcRowPosition();
		return this;
	}

	private void protectPageNowEnd() {
		if (pageNow <= 0)
			pageNow = 1;
		else if (pageNow >= pages)
			pageNow = pages;
	}

	private void protectPageNowFront(String action) {
		if (null == action || !action.matches(DIGITAL_PATTERN))
			pageNow = 1;
		else
			pageNow = Integer.valueOf(action);
		protectPageNowEnd();
	}

	/**
	 * 通过总数据集合，得到分页后的数据集合
	 * 
	 * @param objs数据集合
	 * @return 分页后的数据集合
	 */
	public SplitPage<T> subList(List<T> objs) {
		if (null == objs || objs.size() == 0)
			return this;
		int totalRows = pageNow * pageSize;
		if (totalRows > objs.size())
			totalRows = objs.size();
		this.objects = objs.subList((pageNow - 1) * pageSize, totalRows);
		return this;
	}

	private boolean isBlank(String inParam) {
		return (null == inParam || "".equals(inParam) || inParam.matches("^\\s{1,}$"));
	}

	private void calcRowPosition() {
		if (0 == counts)
			return;
		int tempEndRowCount = pageSize * pageNow;
		if (tempEndRowCount > counts) {
			tempEndRowCount = counts;
		}
		endRowCount = tempEndRowCount;
		if (endRowCount < pageSize) {
			beginRowCount = 1;
		} else {
			beginRowCount = endRowCount - pageSize + 1;
		}
	}

	/**
	 * @return 为Struts提供临时获取参数使用
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @return 为Struts提供临时存储参数使用
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @param counts
	 *            初始化setCounts
	 */
	public SplitPage<T> setCounts(int counts) {
		initialPages(counts);
		return this;
	}

	/**
	 * @return 每页现实的行数
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @return 得到总页数 如果整除，则正好是商。否则为商+1
	 */
	public int getPages() {
		return pages;
	}

	public List<T> getObjects() {
		return objects;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public static void setDefinedValue(int definedValue) {
		SplitPage.definedValue = definedValue;
	}

	/**
	 * @return 迭代基数
	 */
	public int getItrator() {
		return itrator;
	}

	/**
	 * @return 迭代总数
	 */
	public int getItratorCount() {
		return itratorCount;
	}

	public int getCounts() {
		return counts;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public void setObjects(List<T> objects) {
		this.objects = objects;
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public int getBeginRowCount() {
		return beginRowCount;
	}

	public int getEndRowCount() {
		return endRowCount;
	}

}
