/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luna.service.ResourcesContentService;
import com.luna.service.dto.ContentForm;
import com.luna.service.enumer.content.HandlerMethodEnum;
import com.luna.utils.LangUtils;

/**
 * @author laulyl
 * @date 2017年3月12日 下午6:07:10
 * @description
 */
@Controller
@RequestMapping("/content")
public class ResourcesContentController extends ParentController {

	@Autowired
	private ResourcesContentService contentService;

	@RequestMapping("/query")
	public String query(Model model, Long rsId) {
		model.addAttribute("rsId", rsId);
		return "/content_query";
	}

	@RequestMapping("/queryItems")
	public String queryItems(Model model, Long rsId, Integer pageNow) {
		setDefaultStaticModel(model, HandlerMethodEnum.class, LangUtils.class);
		model.addAttribute("page", contentService.selectResourcesContents(rsId, pageNow));
		return "/content_query_items";
	}

	@RequestMapping("/mdf")
	@ResponseBody
	public String mdf(ContentForm contentForm) {

		return "/save_or_update_content";
	}
}
