/**
 * copyright@laulyl
 */
package com.luna.mapper;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.luna.dao.mapper.IResourcesMapper;
import com.luna.dao.po.Resources;

/**
 * @author laulyl
 * @date 2016-11-23 上午11:13:43
 * @desc
 */
public class ResourcesMapperTest {

	private static ApplicationContext applicationContext;
	private static IResourcesMapper mapper;

	@Before
	public void before() {
		applicationContext = new ClassPathXmlApplicationContext("applicationContext-data.xml");
		mapper = applicationContext.getBean(IResourcesMapper.class);
	}

	@Test
	public void test_insert() {

		Resources o = new Resources();
		o.setCategoryId(10L);
		o.setCreateTime(new Date());
		o.setCreatorId(1L);
		o.setPageView(100000L);
		o.setSourceAuthor("laulyl");
		o.setSourceDate(new Date());
		o.setSourceSiteLink("wwww.luna.com");
		o.setSourceSiteName("luna");
		o.setThumbnail("www.baidu.com/thumbnail.png");
		o.setTitle("一则月亮的故事");
		o.setUserView(10000L);
		long count = mapper.insert(o);
		System.out.println(count);
	}

	@Test
	public void test_selectById() {
		Resources resources = mapper.selectById(1L);
		System.out.println(resources.getTitle());
	}

}
