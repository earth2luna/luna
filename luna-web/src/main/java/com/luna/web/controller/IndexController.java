/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luna.service.CategoryService;
import com.luna.service.ResourcesSolrService;
import com.luna.service.data.utils.Constants;
import com.luna.service.dto.CategoryResourceVo;

/**
 * @author laulyl
 * @date 2017年5月1日 下午10:07:09
 * @description
 */
@Controller
public class IndexController extends ParentController {

	@Autowired
	private ResourcesSolrService resourcesSolrService;

	@Autowired
	private CategoryService categoryService;

	@ModelAttribute
	public void indexAnywhere(Model model) {
		addPageHeaderSay(model);
	}

	@RequestMapping(value = { "/category/{categroyId}/{pageNow}/", "/category/{categroyId}/{pageNow}" })
	public String categoryList(Model model, @PathVariable Long categroyId, @PathVariable Long pageNow) {
		CategoryResourceVo categoryVo = categoryService.selectCategoryResourceVo(categroyId, pageNow);
		model.addAttribute("categoryVo", categoryVo);
		addHeader(model, categoryVo.getCategory().getName());
		return "categroy/list";
	}

	@RequestMapping(value = { "/query/{query}/{pageNow}/", "/query/{query}/{pageNow}" })
	public String home(Model model, @PathVariable String query, @PathVariable Long pageNow) {
		addHeader(model, null);
		model.addAttribute("QUERY_STRING_MAX_LENGTH", Constants.QUERY_STRING_MAX_LENGTH);
		model.addAttribute("homeVo", resourcesSolrService.getHomeVo(query, pageNow));
		return "front/page_home";
	}

	@RequestMapping(value = { "/{pageNow}/", "/{pageNow}" })
	public String home(Model model, @PathVariable Long pageNow) {
		return home(model, null, pageNow);
	}

	@RequestMapping("/")
	public String home(Model model) {
		return home(model, null, null);
	}

	@RequestMapping("/about")
	public String about(Model model) {
		addHeader(model, "关于我们-Apoollo");
		return "front/page_about";
	}

	@RequestMapping("/login")
	public String login(Model model, HttpServletRequest request) {
		addHeader(model, "登陆-Apoollo");
		model.addAttribute("w", request.getParameter("w"));
		model.addAttribute("key", request.getParameter("key"));
		return "front/page_login";
	}

}
