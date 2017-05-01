/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luna.service.ResourcesSolrService;
import com.luna.service.data.utils.Constants;

/**
 * @author laulyl
 * @date 2017年5月1日 下午10:07:09
 * @description
 */
@Controller
public class IndexController {

	@Autowired
	private ResourcesSolrService resourcesSolrService;

	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("QUERY_STRING_MAX_LENGTH", Constants.QUERY_STRING_MAX_LENGTH);
		model.addAttribute("page", resourcesSolrService.query(null, 1));
		return "front/page_home";
	}
}
