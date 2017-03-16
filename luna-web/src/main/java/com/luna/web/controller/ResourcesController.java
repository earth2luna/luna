/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luna.service.ResourcesService;
import com.luna.service.enumer.resource.CategoryEnum;
import com.luna.service.enumer.resource.CreatorEnum;
import com.luna.service.enumer.resource.StatusEnum;
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

	@RequestMapping("/query")
	public String query(Model model) {
		model.addAttribute("status_list", StatusEnum.values());
		return "/resources_list";
	}

	@RequestMapping("/queryItems")
	public String queryItems(Model model, String sts, Integer pageNow) {
		setDefaultStaticModel(model, CategoryEnum.class, CreatorEnum.class, StatusEnum.class);
		model.addAttribute("page", resourcesService.selectResources(sts, pageNow));
		return "/resources_query_items";
	}

	@RequestMapping("/operation")
	@ResponseBody
	public InvokeVo operation(Long key, Integer op) {
		return resourcesService.operation(key, op);
	}
}
