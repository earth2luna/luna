/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.render;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.luna.web.render.TimeRender;

/**
 * @author laulyl
 * @date 2017年2月28日 下午11:30:51
 * @description
 */
public class TimeRenderTest {

	private static ApplicationContext applicationContext;
	private static TimeRender render;

	@Before
	public void before() {
		applicationContext = new ClassPathXmlApplicationContext("applicationContext-web.xml");
		render = applicationContext.getBean(TimeRender.class);
	}

	@Test
	public void test_shcheduleRender() {
		try {
			if (null != render) {
				System.out.println("开睡！");
				Thread.sleep(1000 * 60);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_rightNowRender() {
		render.render();
	}
}
