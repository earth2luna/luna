/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author laulyl
 * @date 2017年3月12日 下午6:07:10
 * @description
 */
@Controller
@RequestMapping("/manager")
public class ManagerController {

	@RequestMapping("/init")
	public String init(Model model) {
		return "/manager";
	}
}
