/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luna.service.ResourcesSolrService;
import com.luna.service.data.utils.Constants;
import com.luna.service.dto.PageHeaderSayVo;

/**
 * @author laulyl
 * @date 2017年5月1日 下午10:07:09
 * @description
 */
@Controller
public class IndexController {

	private static final List<PageHeaderSayVo> PAGE_HEADER_SAY = new ArrayList<PageHeaderSayVo>() {

		private static final long serialVersionUID = -634655138563100362L;
		{
			add(new PageHeaderSayVo("生活不止眼前的苟且", "生活之外，不要忘了最初的梦想；不过于执着于物质，多读书，也许会明白自己真正的远方在哪里。——高晓松"));
			add(new PageHeaderSayVo("生如夏花", "我听见回声，来自山谷和心间；生如夏花之绚烂，死如秋叶之静美；还在乎拥有什么。——泰戈尔"));
			add(new PageHeaderSayVo("生如夏花", "惊鸿一般短暂，像夏花一样绚烂；我从远方赶来，赴你一面之约；我为你来看我不顾一切；我将熄灭永不能再回来。——朴树"));
		}
	};

	@Autowired
	private ResourcesSolrService resourcesSolrService;

	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("PAGE_HEADER_SAY", PAGE_HEADER_SAY.get(RandomUtils.nextInt(PAGE_HEADER_SAY.size())));
		model.addAttribute("QUERY_STRING_MAX_LENGTH", Constants.QUERY_STRING_MAX_LENGTH);
		model.addAttribute("page", resourcesSolrService.query(null, 1));
		return "front/page_home";
	}
}
