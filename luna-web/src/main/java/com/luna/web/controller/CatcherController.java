/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@RequestMapping("/add")
	@ResponseBody
	public InvokeVo add(CatcherModel catcherModel, CatchRuler catchRuler, HttpServletRequest request) {
		InvokeVo invokeVo = new InvokeVo("执行成功", null, 1);
		try {
			merge(catcherModel, catchRuler);
		} catch (Exception e) {
			invokeVo = new InvokeVo("执行失败", null, 0);
			LOGGER.error("do it error:", e);
		}
		return invokeVo;
	}

	private void merge(CatcherModel catcherModel, CatchRuler catchRuler) {
		merge(catcherModel.getResourceTitleCatchRulers(), catchRuler);
		merge(catcherModel.getResourceAuthorCatchRulers(), catchRuler);
		merge(catcherModel.getResourceDateCatchRulers(), catchRuler);
		merge(catcherModel.getIteratorRuler().getContentCatchRulers(), catchRuler);
		merge(catcherModel.getIteratorRuler().getContentPathCatchRulers(), catchRuler);
		merge(catcherModel.getIteratorRuler().getOneLevelContentTitleCatchRulers(), catchRuler);
		merge(catcherModel.getIteratorRuler().getTwoLevelContentTitleCatchRulers(), catchRuler);
	}

	private void merge(List<CatchRuler> catchRulers, CatchRuler catchRuler) {
		if (CollectionUtils.isNotEmpty(catchRulers)) {
			catchRulers.forEach(element -> merge(element, catchRuler));
		}
	}

	private void merge(CatchRuler catchRuler1, CatchRuler catchRuler2) {
		catchRuler1.setBreakValues(catchRuler2.getBreakValues());
		catchRuler1.setEqualsFilters(catchRuler2.getEqualsFilters());
		catchRuler1.setIndexOfFilters(catchRuler2.getIndexOfFilters());
		catchRuler1.setReplaceModels(catchRuler2.getReplaceModels());
	}

	@RequestMapping("/view")
	public String view(Model model) {
		addPageHeaderSay(model);
		model.addAttribute("handlers", HandlerMethodEnum.values());
		model.addAttribute("htmlMarchers", HtmlMarcherEnum.values());
		model.addAttribute("childrenCategories", categoryService.getChildrenCategories());
		return "catcher/view";
	}
}
