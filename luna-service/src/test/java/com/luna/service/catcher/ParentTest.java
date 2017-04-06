/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.catcher;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author laulyl
 * @date 2017年4月6日 下午5:22:01
 * @description
 */
public class ParentTest {

	private static ApplicationContext applicationContext;

	@Before
	public void before() {
		applicationContext = new ClassPathXmlApplicationContext("applicationContext-service.xml");
	}

	public <T> T get(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}
}
