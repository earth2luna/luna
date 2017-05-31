/**
 * copyright@laulyl
 */
package com.luna.mapper;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.luna.dao.mapper.IResourcesCategoryMapper;
import com.luna.dao.po.ResourcesCategory;

/**
 * @author laulyl
 * @date 2016-11-23 上午11:13:43
 * @desc
 */
public class CategoryMapperTest {

	private static ApplicationContext applicationContext;
	private static IResourcesCategoryMapper mapper;

	@Before
	public void before() {
		applicationContext = new ClassPathXmlApplicationContext("applicationContext-data.xml");
		mapper = applicationContext.getBean(IResourcesCategoryMapper.class);
	}

	@Test
	public void test_insert() {

		ResourcesCategory category=new ResourcesCategory();
		category.setHandleCode(1);
		category.setName("计算机技术");
		long count = mapper.insert(category);
		System.out.println(count);
	}

	@Test
	public void test_selectById() {
		ResourcesCategory category = mapper.selectById(1L);
		System.out.println(category.getName());
	}

}
