/**
 * copyright@lyl
 *laulyl-web Maven Webapp.com.laulyl.web.configuration.Aplication.java.java
 */
package com.luna.web.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * @author lyl 2016-2-4
 * @description
 */
@Configuration
@ComponentScan
public class Aplication {

	@Bean(name = "handlerExceptionResolver")
	public SimpleMappingExceptionResolver getSimpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
		exceptionResolver.setDefaultErrorView("404");
		exceptionResolver.setDefaultStatusCode(500);
		exceptionResolver.setWarnLogCategory("com.luna");
		return exceptionResolver;
	}
}
