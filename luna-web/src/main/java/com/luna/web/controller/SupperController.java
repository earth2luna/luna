/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luna.service.FunctionService;
import com.luna.service.ResourcesSolrService;
import com.luna.utils.LangUtils;
import com.luna.utils.classes.InvokeVo;

/**
 * @author laulyl
 * @date 2017年4月29日 上午10:04:46
 * @description
 */
@Controller
@RequestMapping("/super")
public class SupperController extends ParentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SupperController.class);

	@Autowired
	private ResourcesSolrService resourcesSolrService;

	@Autowired
	private FunctionService functionService;

	@RequestMapping("/doIt")
	@ResponseBody
	public InvokeVo doIt(String op, HttpServletRequest request) {
		InvokeVo invokeVo = new InvokeVo("执行成功", null, 1);
		try {
			if (LangUtils.equals("1", op)) {
				resourcesSolrService.synchronizedAll();
			} else if (LangUtils.equals("2", op)) {
				resourcesSolrService.delete(request.getParameter("DELETEDIDS"));
			} else if (LangUtils.equals("3", op)) {
				functionService.exportResourceData(LangUtils.toLongDfNull(request.getParameter("ltId")),
						LangUtils.toLongDfNull(request.getParameter("gtId")), null);
			} else if (LangUtils.equals("4", op)) {
				functionService.exportResourceContentData(LangUtils.toLongDfNull(request.getParameter("ltId")),
						LangUtils.toLongDfNull(request.getParameter("gtId")), null);
			} else if (LangUtils.equals("5", op)) {
				functionService.exportResourceCasecadeData(LangUtils.toLongDfNull(request.getParameter("ltId")),
						LangUtils.toLongDfNull(request.getParameter("gtId")), null);
			}
		} catch (Exception e) {
			invokeVo = new InvokeVo("执行失败", null, 0);
			LOGGER.error("do it error:", e);
		}
		return invokeVo;
	}

	@RequestMapping("/view")
	public String view() {
		return "super/view";
	}
}
