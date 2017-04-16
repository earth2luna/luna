/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.catcher;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.luna.dao.mapper.IResourcesContentMapper;
import com.luna.dao.mapper.IResourcesMapper;
import com.luna.service.enumer.content.HandlerMethodEnum;
import com.luna.service.enumer.service.HtmlMarcherEnum;

import us.codecraft.webmagic.Spider;

/**
 * @author laulyl
 * @date 2017年4月4日 下午2:26:18
 * @description
 */
public class CatcherTest1 extends ParentTest {

	@Test
	public void main() {
//		String catcherWebUrl = "http://www.ruanyifeng.com/blog/2016/08/http.html";
		String catcherWebUrl = "http://www.ruanyifeng.com/blog/2017/04/css_in_js.html";

		String catcherWebName = "阮一峰个人网站";
		CatcherModel catcherModel = new CatcherModel();
		catcherModel.setCatcherWebUrl(catcherWebUrl);
		catcherModel.setCatcherWebName(catcherWebName);
		catcherModel.setResourceTitleCatchRulers(
				evalueCatchRulers("//article[@class='hentry']/h1[@id='page-title']/text()"));
		catcherModel.setResourceAuthorCatchRulers(
				evalueCatchRulers("//article[@class='hentry']/div[@class='asset-meta']/p[@class='author']/a/text()"));
		catcherModel.setResourceDateCatchRulers(evalueCatchRulers(
				"//article[@class='hentry']/div[@class='asset-meta']/p//abbr[@class='published']/text()"));
		catcherModel.setResourceDateFormat("yyyy年MM月dd日");

		List<CatcherIteratorRuler> iteratorRulers = new ArrayList<CatcherIteratorRuler>();
		CatcherIteratorRuler iteratorRulerBefore = new CatcherIteratorRuler();
		CatcherIteratorRuler iteratorRuler = new CatcherIteratorRuler();
		iteratorRulers.add(iteratorRulerBefore);
		iteratorRulers.add(iteratorRuler);
		catcherModel.setIteratorRulers(iteratorRulers);

		iteratorRulerBefore.setOneLevelContentTitleCatchRulers(
				evalueCatchRulers(null, null, "//article[@class='hentry']/h1[@id='page-title']/text()", null, null,
						null, HandlerMethodEnum.P.getCode()));
		iteratorRulerBefore.setIfMark(true);

		// 中间path
		iteratorRuler.setContentXPath("//article[@class='hentry']/div[@class='asset-content']/*");

		// 内容
		List<CatchRuler> contentCatchRulers = evalueCatchRulers(null, "//p/a/strong/text()", "//p/html()",
				HtmlMarcherEnum.TAG.getCode(), new String[] { "a" }, null, HandlerMethodEnum.P.getCode());

		evalueCatchRulers(contentCatchRulers, "//p/code/text()", "//p/html()", null, null, null,
				HandlerMethodEnum.P.getCode());
		evalueCatchRulers(contentCatchRulers, "//p/strong/text()", "//p/html()", null, null, null,
				HandlerMethodEnum.P.getCode());
		evalueCatchRulers(contentCatchRulers, null, "//blockquote/ul", null, null, null, HandlerMethodEnum.P.getCode());
		evalueCatchRulers(contentCatchRulers, null, "//blockquote/pre/code/allText()", null, null, null,
				HandlerMethodEnum.PRE.getCode());
		evalueCatchRulers(contentCatchRulers, null, "//p/html()", null, null, null, HandlerMethodEnum.P.getCode());
		iteratorRuler.setContentCatchRulers(contentCatchRulers);
		// 内容标题
		List<CatchRuler> oneLevelContentTitleCatchRulers = evalueCatchRulers(null, null, "//h2/text()", null, null,
				null, HandlerMethodEnum.P.getCode());

		List<CatchRuler> twoLevelContentTitleCatchRulers = evalueCatchRulers(null, null, "//h3/text()", null, null,
				null, HandlerMethodEnum.P.getCode());
		iteratorRuler.setOneLevelContentTitleCatchRulers(oneLevelContentTitleCatchRulers);
		iteratorRuler.setTwoLevelContentTitleCatchRulers(twoLevelContentTitleCatchRulers);

		// 路径
		List<CatchRuler> contentPathCatchRulers = evalueCatchRulers(null, null, "//p/img/@src", null, null, null,
				HandlerMethodEnum.IMAGE.getCode());
		iteratorRuler.setContentPathCatchRulers(contentPathCatchRulers);

		Spider.create(
				new CatcherProcessor(get(IResourcesMapper.class), get(IResourcesContentMapper.class), catcherModel))
				// 开始抓
				.addUrl(catcherModel.getCatcherWebUrl())
				// 开启5个线程抓取
				.thread(1)
				// 启动爬虫
				.run();
	}

	public static List<CatchRuler> evalueCatchRulers(List<CatchRuler> catchRulers, String tryXPath, String getXPath,
			Integer replaceCode, String[] replaceTagNames, String replacement, Integer handlerCode) {
		if (null == catchRulers) {
			catchRulers = new ArrayList<CatchRuler>();
		}
		CatchRuler catchRuler = new CatchRuler(tryXPath, getXPath, replaceCode, replaceTagNames, replacement,
				handlerCode);
		catchRulers.add(catchRuler);
		return catchRulers;
	}

	public static List<CatchRuler> evalueCatchRulers(String getXPath) {
		return evalueCatchRulers(null, null, getXPath, null, null, null, null);
	}

	public static List<String> evalueXPath(String xpath) {
		return evalueXPath(null, xpath);
	}

	public static List<String> evalueXPath(List<String> xpaths, String xpath) {
		if (null == xpaths) {
			xpaths = new ArrayList<String>();
		}
		xpaths.add(xpath);
		return xpaths;
	}
}
