package com.luna.service.sync;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author laulyl
 * @datetime 2017年4月20日 下午6:24:26
 * @desction
 */
public abstract class AbstractListWhileDoNormal<T> implements ISynchronizedData<T> {

	public abstract List<T> getList(long count);

	public abstract void doElement(T t, long count);

	public abstract void before();

	public abstract void after(long count);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.solomon.common.service.declare.ISynchronizedData#synchronizedAll()
	 */
	@Override
	public void synchronizedAll(int pages) {
		before();
		long count = 1;
		List<T> list = null;
		while (count <= pages) {
			list = this.getList(count);
			if (CollectionUtils.isNotEmpty(list)) {
				Iterator<T> iterator = list.iterator();
				while (iterator.hasNext()) {
					doElement(iterator.next(), count);
				}
			}
			++count;
		}
		after(count);
	}

}
