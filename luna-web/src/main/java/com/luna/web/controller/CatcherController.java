/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luna.service.CatcherService;
import com.luna.service.CategoryService;
import com.luna.service.catcher.CatchRuler;
import com.luna.service.catcher.CatcherModel;
import com.luna.service.enumer.content.HandlerMethodEnum;
import com.luna.service.enumer.service.HtmlMarcherEnum;
import com.luna.utils.classes.InvokeVo;

/**
 * @author laulyl
 * @date 2017年4月29日 上午10:04:46
 * @description
 */
@Controller
@RequestMapping("/catcher")
public class CatcherController extends ParentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CatcherController.class);

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CatcherService catcherService;

	@RequestMapping("/modify")
	@ResponseBody
	public InvokeVo modify(CatcherModel catcherModel, CatchRuler catchRuler, HttpServletRequest request) {
		InvokeVo invokeVo = new InvokeVo("执行成功", null, 1);
		try {
			catcherService.modify(catcherModel, catchRuler);
		} catch (Exception e) {
			invokeVo = new InvokeVo("执行失败", null, 0);
			LOGGER.error("modify error:", e);
		}
		return invokeVo;
	}

	@RequestMapping("/del")
	@ResponseBody
	public InvokeVo del(Long key, HttpServletRequest request) {
		InvokeVo invokeVo = new InvokeVo("执行成功", null, 1);
		try {
			catcherService.delete(key);
		} catch (Exception e) {
			invokeVo = new InvokeVo("执行失败", null, 0);
			LOGGER.error("del error:", e);
		}
		return invokeVo;
	}

	@RequestMapping("/catching")
	@ResponseBody
	public InvokeVo catching(Long key, HttpServletRequest request) {
		InvokeVo invokeVo = new InvokeVo("执行完毕", null, 1);
		try {
			catcherService.catching(key);
		} catch (Exception e) {
			invokeVo = new InvokeVo("执行失败", null, 0);
			LOGGER.error("del error:", e);
		}
		return invokeVo;
	}

	@RequestMapping("/items")
	public String items(Model model, Integer page) {
		model.addAttribute("page", catcherService.selectCatcherRules(page));
		return "catcher/items";
	}

	@RequestMapping("/list")
	public String list(Model model, Integer pageNow) {
		addPageHeaderSay(model);
		return "catcher/list";
	}

	@RequestMapping("/view")
	public String view(Model model, Long key) {
		addPageHeaderSay(model);
		model.addAttribute("handlers", HandlerMethodEnum.values());
		model.addAttribute("htmlMarchers", HtmlMarcherEnum.values());
		model.addAttribute("childrenCategories", categoryService.getChildrenCategories());
		CatcherModel catcherModel = catcherService.select(key);
		if (null != catcherModel) {
			model.addAttribute("rule", catcherModel);
			model.addAttribute("hRule", catcherModel.getIteratorRuler().getContentCatchRulers().get(0));
		}
		return "catcher/view";
	}
}
