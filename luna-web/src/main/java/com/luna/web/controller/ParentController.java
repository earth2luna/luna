/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;

/**
 * @author laulyl
 * @date 2017年3月15日 上午12:16:00
 * @description
 */
public class ParentController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ParentController.class);

	protected static void setDefaultStaticModel(Model model, Class<?>... classes) {
		BeansWrapper wrapper = new BeansWrapperBuilder(Configuration.VERSION_2_3_23).build();
		for (Class<?> clz : classes) {
			try {
				model.addAttribute(clz.getSimpleName(), wrapper.getStaticModels().get(clz.getCanonicalName()));
			} catch (TemplateModelException e) {
				LOGGER.error("[static model error]", e);
			}
		}
	}

	@ModelAttribute
	public void anywhere(Model model) {

	}
}
