/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.luna.service.data.utils.Constants;
import com.luna.utils.LangUtils;

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

	protected void addPageHeaderSay(Model model) {
		model.addAttribute(Constants.PAGE_HEADER_SAY_KEY, Constants.getPageHeaderSayVo());
	}

	protected void addHeader(Model model, String title, String keywords, String description) {
		model.addAttribute(Constants.HEADER_TITLE, LangUtils.defaultValue(title, "Apoollo - 阿波罗高质量的触动！"));
		model.addAttribute(Constants.HEADER_KEYWORDS,
				LangUtils.defaultValue(keywords, "阿波罗,Apoollo,专业文章,计算机技术,编程,旅行,情怀,高质量,青春,教程，知识点"));
		model.addAttribute(Constants.HEADER_DESCRIPTION, LangUtils.defaultValue(description,
				"Apoollo ( www.apoollo.com ) 阿波罗只提供高质量的文章，涉及计算机编程、旅行、情怀、青春等各个主题领域，旨在帮助到您，对您有所触动！"));
		model.addAttribute(Constants.HEADER_AUTHOR, "作者：刘玉龙 &lt;673348317@qq.com&gt;");
	}

	protected void addHeader(Model model, String title) {
		addHeader(model, title, null, null);
	}

	@ModelAttribute
	public void anywhere(Model model) {

	}

}
