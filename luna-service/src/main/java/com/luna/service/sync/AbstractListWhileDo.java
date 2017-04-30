package com.luna.service.sync;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author laulyl
 * @datetime 2017年4月20日 下午6:24:26
 * @desction
 */
public abstract class AbstractListWhileDo<T> implements ISynchronizedData<T> {

	public abstract List<T> getList(long count);

	public abstract void doElement(T t, long count);

	public abstract void after(long count);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.solomon.common.service.declare.ISynchronizedData#synchronizedAll()
	 */
	@Override
	public void synchronizedAll(int threadNunber) {

		ExecutorService service = Executors.newCachedThreadPool();
		AtomicLong counter = new AtomicLong(0);
		for (int i = 0; i < threadNunber; i++) {
			service.submit(new synchronizedCall<T>(counter, this));
		}
		service.shutdown();
	}

}

class synchronizedCall<T> implements Runnable {

	private AtomicLong counter;
	private AbstractListWhileDo<T> listWhileDo;

	public synchronizedCall(AtomicLong counter, AbstractListWhileDo<T> listWhileDo) {
		super();
		this.counter = counter;
		this.listWhileDo = listWhileDo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		long count = counter.incrementAndGet();
		List<T> list = listWhileDo.getList(count);
		while (CollectionUtils.isNotEmpty(list)) {
			Iterator<T> iterator = list.iterator();
			while (iterator.hasNext()) {
				listWhileDo.doElement(iterator.next(), count);
			}
			count = counter.incrementAndGet();
			list = listWhileDo.getList(count);
		}
		listWhileDo.after(count);
	}

}