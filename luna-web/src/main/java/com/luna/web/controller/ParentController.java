/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.luna.service.dto.PageHeaderSayVo;

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

	private static final List<PageHeaderSayVo> PAGE_HEADER_SAY = new ArrayList<PageHeaderSayVo>() {

		private static final long serialVersionUID = -634655138563100362L;
		{
			add(new PageHeaderSayVo("生活不止眼前的苟且", "生活之外，不要忘了最初的梦想；不过于执着于物质，多读书，也许会明白自己真正的远方在哪里。——高晓松"));
			add(new PageHeaderSayVo("生如夏花", "我听见回声，来自山谷和心间；生如夏花之绚烂，死如秋叶之静美；还在乎拥有什么。——泰戈尔"));
			add(new PageHeaderSayVo("生如夏花", "惊鸿一般短暂，像夏花一样绚烂；我从远方赶来，赴你一面之约；我为你来看我不顾一切；我将熄灭永不能再回来。——朴树"));
		}
	};

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
		model.addAttribute("PAGE_HEADER_SAY", PAGE_HEADER_SAY.get(RandomUtils.nextInt(PAGE_HEADER_SAY.size())));
	}

	@ModelAttribute
	public void anywhere(Model model) {

	}

	public static void addCookie(HttpServletResponse response, String key, String value, int maxAge) {
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
}
