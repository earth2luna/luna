/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luna.dao.mapper.IResourcesMapper;
import com.luna.service.data.utils.ResourcesUtils;
import com.luna.service.enumer.resource.CategoryEnum;
import com.luna.service.enumer.resource.CreatorEnum;
import com.luna.service.enumer.resource.StatusEnum;

/**
 * @author laulyl
 * @date 2017年3月14日 下午10:43:44
 * @description
 */
@Controller
@RequestMapping("/resources")
public class ResourcesController extends ParentController {

	@Autowired
	private IResourcesMapper resourcesMapper;

	@RequestMapping("/query")
	public String query(Model model) {
		model.addAttribute("status_list", StatusEnum.values());
		return "/resources_list";
	}

	@RequestMapping("/queryItems")
	public String queryItems(Model model, String sts, Integer pageNow) {
		setDefaultStaticModel(model, CategoryEnum.class, CreatorEnum.class, StatusEnum.class);
		model.addAttribute("page", ResourcesUtils.selectResources(resourcesMapper, sts, pageNow));
		return "/resources_query_items";
	}
}
