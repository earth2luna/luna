/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luna.security.SignIn;
import com.luna.service.ResourcesSolrService;
import com.luna.service.data.utils.Constants;

/**
 * @author laulyl
 * @date 2017年5月1日 下午10:07:09
 * @description
 */
@Controller
public class IndexController extends ParentController {

	@Autowired
	private ResourcesSolrService resourcesSolrService;

	@ModelAttribute
	public void indexAnywhere(Model model) {
		addPageHeaderSay(model);
	}

	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("QUERY_STRING_MAX_LENGTH", Constants.QUERY_STRING_MAX_LENGTH);
		model.addAttribute("page", resourcesSolrService.query(null, 1));
		return "front/page_home";
	}

	@RequestMapping("/about")
	public String about(Model model) {
		return "front/page_about";
	}

	@RequestMapping("/login")
	public String login(Model model, HttpServletRequest request) {
		model.addAttribute("w", request.getParameter("w"));
		model.addAttribute("key", request.getParameter("key"));
		return "front/page_login";
	}

	@RequestMapping("/signin")
	public void signIn(Model model, String key, String userName, String password, HttpServletResponse response) {
		SignIn.simpleSignIn(response, key, userName, password);
	}

}
