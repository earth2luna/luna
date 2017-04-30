/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luna.service.ResourcesSolrService;
import com.luna.service.componet.SuggetVo;

/**
 * @author laulyl
 * @date 2017年4月22日 上午8:59:53
 * @description
 */
@RequestMapping("/front")
@Controller
public class FrontController extends ParentController {

	@Autowired
	private ResourcesSolrService resourcesSolrService;

	@RequestMapping("/home")
	public String home() {
		return "front/page_home";
	}

	@RequestMapping("/sugget")
	@ResponseBody
	public List<SuggetVo> sugget(String query) {
		return resourcesSolrService.sugget(query);
	}

	@RequestMapping("/items")
	public String searchItems(Model model, String query, Integer pageNow) {
		model.addAttribute("page", resourcesSolrService.query(query, pageNow));
		return "front/search_items";
	}
}
