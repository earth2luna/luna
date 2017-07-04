/**
 * copyright@laulyl
 */
package com.luna.service;

import org.junit.Test;

/**
 * @author laulyl
 * @date 2017年7月2日 下午6:36:22
 * @desction
 */
public class FunctionServiceTest extends ParentTest {

	@Test
	public void test() {
		FunctionService functionService = get(FunctionService.class);
		functionService.exportResourceData(1L, 10L,"C:\\Users\\Administrator\\Desktop\\test\\resource.sql");
	}

}
