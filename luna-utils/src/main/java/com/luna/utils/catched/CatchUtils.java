/**
 * copyright@www.jd.com
 */
package com.luna.utils.catched;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author lyl
 * @date 2016-5-9
 * @time 上午11:56:45
 * @desc 抓取子集合工具类
 */
public class CatchUtils {

	private static final Log LOG = LogFactory.getLog(CatchUtils.class);

	public static <T> String join(List<T> list, IGetProperty<T> getProperty,
			String splitor) {
		Set<Serializable> keys = joins(list, getProperty);
		if (CollectionUtils.isEmpty(keys))
			return null;
		return StringUtils.join(keys.iterator(), splitor);
	}

	public static <T> Set<Serializable> joins(List<T> list,
			IGetProperty<T> getProperty) {
		Validate.notNull(getProperty);
		Set<Serializable> keys = new HashSet<Serializable>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (T t : list) {
				if (null == t)
					continue;
				Serializable property = getProperty.get(t);
				if (null != property) {
					if (property instanceof String
							&& StringUtils.isNotBlank(property.toString())) {
						keys.add(property);
					} else {
						keys.add(property);
					}
				}
			}
		}
		return keys;
	}

	public static OpenOff getOpenOff(int totalCount, int onceCatchNumber,
			int times) {
		Validate.isTrue(0 < totalCount);
		Validate.isTrue(0 < onceCatchNumber);
		Validate.isTrue(0 < times);
		// calculation max times
		int catchRemain = totalCount % onceCatchNumber;
		int catchTimesFull = totalCount / onceCatchNumber;
		int maxTimes = 0 == catchRemain ? catchTimesFull : catchTimesFull + 1;
		// 分析：当抓取次数超过最大抓取次数为非正常超标
		if (times > maxTimes) {
			return null;
		}
		// 总数量等于或小于一次抓取数据数量，则直接抓取总数量
		if (onceCatchNumber >= totalCount) {
			return new OpenOff(0, totalCount, 1);
		}
		int off = onceCatchNumber * times;
		int open = off - onceCatchNumber;
		// 如果抓取下标超过了最最大下标
		if (off > totalCount) {
			off = totalCount;
		}
		return new OpenOff(open, off, maxTimes);
	}

	/**
	 * 
	 * @param list
	 *            入参集合
	 * @param onceCatchNumber
	 *            一次抓取数量
	 * @param times
	 *            第几次抓取
	 * @return 抓取子集数量
	 */
	public static <T> List<T> onceCatch(List<T> list, int onceCatchNumber,
			int times) {
		if (CollectionUtils.isNotEmpty(list)) {
			OpenOff openOff = getOpenOff(list.size(), onceCatchNumber, times);
			if (null != openOff) {
				return list.subList(openOff.getOpen(), openOff.getOff());
			}
		}
		return null;
	}

	/**
	 * 同步抓取，单线程执行完所有抓取批次
	 * 
	 * @param list
	 *            入参集合
	 * @param onceCatchNumber
	 *            一次抓取数量
	 * @param hanlder
	 *            处理器
	 */
	public static <T> void synchronousCatch(List<T> list, int onceCatchNumber,
			ICatchHanlder<T> hanlder) {
		Validate.notNull(hanlder);
		if (CollectionUtils.isNotEmpty(list)) {
			int i = 0;
			List<T> onceCatch = null;
			while (null != (onceCatch = onceCatch(list, onceCatchNumber, ++i))) {
				hanlder.handle(onceCatch);
			}
		}
	}

	/**
	 * 多线程异步抓取，线程数量跟抓取次数变动
	 * 
	 * @param list
	 *            入参集合
	 * @param onceCatchNumber
	 *            一次抓取数量
	 * @param hanlder
	 *            处理器
	 */
	public static <T> void asynchronousCatch(ExecutorService service,
			List<T> list, int onceCatchNumber, ICatchHanlder<T> hanlder) {
		Validate.isTrue(0 < onceCatchNumber);
		Validate.notNull(hanlder);
		if (CollectionUtils.isNotEmpty(list)) {
			List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>();
			int i = 0;
			while (true) {
				List<T> onceCatch = onceCatch(list, onceCatchNumber, ++i);
				if (null == onceCatch) {
					break;
				}
				futures.add(service.submit(new CatchUtils.ListCatchRunable<T>(
						hanlder, onceCatch)));
			}
			assertLoopFuture(futures);
		}

	}

	/**
	 * 多线程异步抓取,并指定最大线程数量
	 * 
	 * @param list
	 *            入参集合
	 * @param onceCatchNumber
	 *            一次抓取数量
	 * @param nThreads
	 *            指定最大线程数量
	 * @param hanlder
	 *            处理器
	 */
	public static <T> void asynchronousSecureCatch(ExecutorService service,
			List<T> list, int onceCatchNumber, int nThreads,
			ICatchHanlder<T> hanlder) {
		if (CollectionUtils.isNotEmpty(list)) {
			int i = 0;
			OpenOff dataOpenOff = null;
			OpenOff threadOpenOff = null;
			List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>();
			ListCatchRunable<T> catchRunable = null;
			// 直到抓不到子数据为止
			while (null != (dataOpenOff = getOpenOff(list.size(),
					onceCatchNumber, ++i))) {
				if (null == threadOpenOff) {
					threadOpenOff = getOpenOff(dataOpenOff.getTimes(),
							nThreads, 1);
				}
				List<T> onceCatch = list.subList(dataOpenOff.getOpen(),
						dataOpenOff.getOff());
				if (null == catchRunable) {
					catchRunable = new ListCatchRunable<T>(hanlder, onceCatch);
				} else {
					catchRunable.addList(onceCatch);
				}
				if (hasNextThreadCatched(threadOpenOff)) {
					continue;
				}

				futures.add(service.submit(catchRunable));
				// 复位
				catchRunable = null;
				threadOpenOff = getOpenOff(dataOpenOff.getTimes(), nThreads, 1);
			}
			assertLoopFuture(futures);
		}
	}

	// 当前线程还有下一个子集合任务
	private static boolean hasNextThreadCatched(OpenOff threadOpenOff) {
		while (threadOpenOff.getTimes() > 0) {
			threadOpenOff.setTimes(threadOpenOff.getTimes() - 1);
			// 如果当前线程还有下一个子集合任务就继续添加子集合
			if (threadOpenOff.getTimes() > 0) {
				return true;
			}
		}
		return false;
	}

	public static class OpenOff {
		private int open;
		private int off;
		private int times;

		/**
		 * @param open
		 * @param off
		 */
		public OpenOff(int open, int off, int times) {
			super();
			this.open = open;
			this.off = off;
			this.times = times;
		}

		/**
		 * @param open
		 *            the open to set
		 */
		public void setOpen(int open) {
			this.open = open;
		}

		/**
		 * @return the open
		 */
		public int getOpen() {
			return open;
		}

		/**
		 * @return the off
		 */
		public int getOff() {
			return off;
		}

		/**
		 * @return the times
		 */
		public int getTimes() {
			return times;
		}

		/**
		 * @param times
		 *            the times to set
		 */
		public void setTimes(int times) {
			this.times = times;
		}

	}

	private static class ListCatchRunable<T> implements Callable<Boolean> {
		private static final Log LOG = LogFactory
				.getLog(ListCatchRunable.class);
		private ICatchHanlder<T> hanlder;
		private List<List<T>> list = new ArrayList<List<T>>();

		/**
		 * @param hanlder
		 * @param list
		 */
		public ListCatchRunable(ICatchHanlder<T> hanlder, List<T> list) {
			super();
			this.hanlder = hanlder;
			this.list.add(list);
		}

		public void addList(List<T> list) {
			this.list.add(list);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.concurrent.Callable#call()
		 */
		@Override
		public Boolean call() throws Exception {
			try {
				if (CollectionUtils.isNotEmpty(list)) {
					Iterator<List<T>> iterator = list.iterator();
					while (iterator.hasNext()) {
						hanlder.handle(iterator.next());
					}
				}
			} catch (Exception e) {
				LOG.error("handlercall exception ", e);
				return false;
			}
			return true;
		}
	}

	private static void assertLoopFuture(List<Future<Boolean>> futures) {
		Iterator<Future<Boolean>> iterator = futures.iterator();
		while (iterator.hasNext()) {
			try {
				Validate.isTrue(iterator.next().get());
			} catch (Exception e) {
				LOG.error("asynchronous catch get future error ", e);
				throw new RuntimeException(e);
			}
		}
	}

}
