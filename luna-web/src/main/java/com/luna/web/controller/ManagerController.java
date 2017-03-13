/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luna.web.form.ContentForm;

/**
 * @author laulyl
 * @date 2017年3月12日 下午6:07:10
 * @description
 */
@Controller
@RequestMapping("/content")
public class ManagerController {

	@RequestMapping("/query")
	public String query(Model model) {
		return "/content_query";
	}

	@RequestMapping("/mdf")
	@ResponseBody
	public String mdf(ContentForm contentForm) {

		return "/save_or_update_content";
	}
}
