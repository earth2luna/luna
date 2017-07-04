/**
 * copyright@laulyl
 */
package com.luna.service;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author laulyl
 * @date 2017年7月2日 下午6:29:14
 * @desction
 */
public class ParentTest {

	private ApplicationContext applicationContext;

	@Before
	public void before() {
		applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext-web.xml");
	}

	public <T> T get(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}
}
