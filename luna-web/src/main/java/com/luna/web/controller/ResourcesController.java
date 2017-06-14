/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luna.service.CategoryService;
import com.luna.service.ResourcesService;
import com.luna.service.dto.ResourcesForm;
import com.luna.service.enumer.resource.CreatorEnum;
import com.luna.service.enumer.resource.StatusEnum;
import com.luna.utils.LangUtils;
import com.luna.utils.classes.InvokeVo;

/**
 * @author laulyl
 * @date 2017年3月14日 下午10:43:44
 * @description
 */
@Controller
@RequestMapping("/resources")
public class ResourcesController extends ParentController {

	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private CategoryService categoryService;

	@RequestMapping("/query")
	public String query(Model model) {
		addPageHeaderSay(model);
		model.addAttribute("status_list", StatusEnum.values());
		return "behind/resources_list";
	}

	@RequestMapping("/queryItems")
	public String queryItems(Model model, String sts, Integer pageNow) {
		setDefaultStaticModel(model, CreatorEnum.class, StatusEnum.class,LangUtils.class);
		model.addAttribute("page", resourcesService.selectResources(sts, pageNow));
		return "behind/resources_query_items";
	}

	@RequestMapping("/queryForm")
	public String queryForm(Model model, Long rsId) {
		model.addAttribute("childrenCategories", categoryService.getChildrenCategories());
		model.addAttribute("creator_list", CreatorEnum.values());
		model.addAttribute("resources", resourcesService.selectById(rsId));
		return "behind/resources_query_form";
	}

	@RequestMapping("/operation")
	@ResponseBody
	public InvokeVo operation(Long key, Integer op) {
		return resourcesService.operation(key, op);
	}

	@RequestMapping("/mdf")
	@ResponseBody
	public InvokeVo modify(Model model, ResourcesForm resourcesForm) {
		return resourcesService.modify(resourcesForm);
	}
}
