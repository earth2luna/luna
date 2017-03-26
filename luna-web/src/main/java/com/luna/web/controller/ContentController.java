/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luna.service.ContentService;
import com.luna.service.dto.ContentForm;
import com.luna.service.enumer.content.HandlerMethodEnum;
import com.luna.utils.LangUtils;
import com.luna.utils.classes.InvokeVo;

/**
 * @author laulyl
 * @date 2017年3月12日 下午6:07:10
 * @description
 */
@Controller
@RequestMapping("/content")
public class ContentController extends ParentController {

	@Autowired
	private ContentService contentService;

	@RequestMapping("/query")
	public String query(Model model, Long rsId) {
		model.addAttribute("rsId", rsId);
		return "/content_list";
	}

	@RequestMapping("/queryItems")
	public String queryItems(Model model, Long rsId, Integer pageNow) {
		setDefaultStaticModel(model, HandlerMethodEnum.class, LangUtils.class);
		model.addAttribute("page", contentService.selectResourcesContents(rsId, pageNow));
		return "/content_query_items";
	}

	@RequestMapping("/queryForm")
	public String queryForm(Model model, Long rsId, Long cId) {
		model.addAttribute("handlers", HandlerMethodEnum.values());
		model.addAttribute("parents", contentService.selectParentContents(rsId));
		model.addAttribute("content", contentService.selectById(cId));
		return "/content_query_form";
	}

	@RequestMapping("/mdf")
	@ResponseBody
	public InvokeVo modify(ContentForm contentForm) {
		return contentService.modify(contentForm);
	}
	
	@RequestMapping("/deleteItem")
	@ResponseBody
	public InvokeVo deleteItem(Long cId) {
		return contentService.delete(cId);
	}
}
