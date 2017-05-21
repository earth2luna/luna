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

import us.codecraft.webmagic.Spider;

/**
 * @author laulyl
 * @date 2017年5月15日 下午11:42:46
 * @description
 */
public class GitHubTest extends ParentTest {

	@Test
	public void main() {
		List<String> list = new ArrayList<String>();
		list.add("https://github.com/xufei/blog/issues/21");
		list.add("https://github.com/xufei/blog/issues/47");
		list.add("https://github.com/xufei/blog/issues/44");
		list.add("https://github.com/xufei/blog/issues/42");
		list.add("https://github.com/xufei/blog/issues/39");
		list.add("https://github.com/xufei/blog/issues/38");
		list.add("https://github.com/xufei/blog/issues/37");
		list.add("https://github.com/xufei/blog/issues/36");
		list.add("https://github.com/xufei/blog/issues/35");
		list.add("https://github.com/xufei/blog/issues/33");

		catcher("https://github.com/xufei/blog/issues/33");

	}

	public void catcher(String catcherWebUrl) {

		CatcherModel catcherModel = new CatcherModel();
		catcherModel.setCatcherWebUrl(catcherWebUrl);
		catcherModel.setCatcherWebName("github");
		catcherModel.setCatcherWebsiteCode(3);
		catcherModel.setResourceCategoryCode(1);

		catcherModel.setResourceTitleCatchRulers(evalueCatchRulers("//h1[@class='gh-header-title']/span/allText()"));
		catcherModel.setResourceAuthorCatchRulers(evalueCatchRulers(
				"//div[@class='timeline-comment']/div[@class='timeline-comment-header']/h3[@class='timeline-comment-header-text']/strong/a/allText()"));
		catcherModel.setResourceDateCatchRulers(evalueCatchRulers(
				"//div[@class='timeline-comment']/div[@class='timeline-comment-header']/h3[@class='timeline-comment-header-text']/a/relative-time/@datetime"));
		catcherModel.setResourceDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		catcherModel.setAttachementPath(
				"D:/workspaces/eclipse_20170116/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/luna-web/static/attachement");

		List<CatcherIteratorRuler> iteratorRulers = new ArrayList<CatcherIteratorRuler>();
		catcherModel.setIteratorRulers(iteratorRulers);

		CatcherIteratorRuler iteratorRuler = new CatcherIteratorRuler();

		iteratorRulers.add(iteratorRuler);

		// 中间path
		iteratorRuler.setContentXPath(
				"//div[@class='js-discussion']/div[@class='timeline-comment-wrapper'][1]/div[@class='comment']/div[@class='edit-comment-hide']/table/tbody/tr/td[@class='d-block']/*");

		// 一级内容标题
		List<CatchRuler> oneLevelContentTitleCatchRulers = evalueCatchRulers(null, null, "//h1/allText()", null, null,
				null, HandlerMethodEnum.P.getCode(), null, null);
		evalueCatchRulers(oneLevelContentTitleCatchRulers, null, "//h2/allText()", null, null, null,
				HandlerMethodEnum.P.getCode(), null, null);

		iteratorRuler.setOneLevelContentTitleCatchRulers(oneLevelContentTitleCatchRulers);

		// 二级内容标题
		List<CatchRuler> twoLevelContentTitleCatchRulers = evalueCatchRulers(null, null, "//h3/text()", null, null,
				null, HandlerMethodEnum.P.getCode(), null, null);
		iteratorRuler.setTwoLevelContentTitleCatchRulers(twoLevelContentTitleCatchRulers);

		// 路径
		List<CatchRuler> contentPathCatchRulers = new ArrayList<CatchRuler>();

		evalueCatchRulers(contentPathCatchRulers, null, "//img/@src", null, null, null,
				HandlerMethodEnum.IMAGE.getCode(), null, null);
		evalueCatchRulers(contentPathCatchRulers, null, "//p/img/@src", null, null, null,
				HandlerMethodEnum.IMAGE.getCode(), null, null);
		evalueCatchRulers(contentPathCatchRulers, null, "//p/a/img/@src", null, null, null,
				HandlerMethodEnum.IMAGE.getCode(), null, null);

		iteratorRuler.setContentPathCatchRulers(contentPathCatchRulers);

		// 内容
		List<CatchRuler> contentCatchRulers = new ArrayList<>();
		iteratorRuler.setContentCatchRulers(contentCatchRulers);

		evalueCatchRulers(contentCatchRulers, "//ul/li/html()", "//ul", null, null, null,
				HandlerMethodEnum.ORIGIN.getCode(), null, null);

		evalueCatchRulers(contentCatchRulers, null, "//div[@class='highlight-source-js']/pre/allText()", null, null,
				null, HandlerMethodEnum.LANGUGE_JS.getCode(), null, null);

		evalueCatchRulers(contentCatchRulers, null, "//div[@class='highlight-text-html-basic']/pre/allText()", null,
				null, null, HandlerMethodEnum.LANGUGE_HTML.getCode(), null, null);

		evalueCatchRulers(contentCatchRulers, null, "//div[@class='highlight-source-json']/pre/allText()", null, null,
				null, HandlerMethodEnum.LANGUGE_JSON.getCode(), null, null);

		evalueCatchRulers(contentCatchRulers, null, "//pre/code/allText()", null, null, null,
				HandlerMethodEnum.PRE.getCode(), null, null);

		evalueCatchRulers(contentCatchRulers, "//blockquote/p/allText()", "//blockquote/p", null, null, null,
				HandlerMethodEnum.CALLOUT_INFO.getCode(), null, null);

		evalueCatchRulers(contentCatchRulers, null, "//p/html()", null, null, null, HandlerMethodEnum.P.getCode(), null,
				"这是对知乎上一个问题的回答");

		evalueCatchRulers(contentCatchRulers, null, "//p/html()", null, null, null, HandlerMethodEnum.P.getCode(), null,
				"codepen.io/xufei");

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
			Integer replaceCode, String[] replaceTagNames, String replacement, Integer handlerCode, String breakValue,
			String indexOfFilter) {
		if (null == catchRulers) {
			catchRulers = new ArrayList<CatchRuler>();
		}
		List<CatcherReplaceModel> replaceModels = new ArrayList<CatcherReplaceModel>();
		replaceModels.add(new CatcherReplaceModel(replaceCode, replaceTagNames, replacement));
		CatchRuler catchRuler = new CatchRuler(tryXPath, getXPath, replaceModels, handlerCode, breakValue,
				indexOfFilter);
		catchRulers.add(catchRuler);
		return catchRulers;
	}

	public static List<CatchRuler> evalueCatchRulers(List<CatchRuler> catchRulers, String tryXPath, String getXPath,
			List<CatcherReplaceModel> replaceModels, Integer handlerCode, String breakValue, String indexOfFilter,
			List<String> equalsFilters) {
		if (null == catchRulers) {
			catchRulers = new ArrayList<CatchRuler>();
		}
		CatchRuler catchRuler = new CatchRuler(tryXPath, getXPath, replaceModels, handlerCode, breakValue,
				indexOfFilter);
		catchRuler.setEqualsFilters(equalsFilters);
		catchRulers.add(catchRuler);
		return catchRulers;
	}

	public void addCatcherReplaceModels(List<CatcherReplaceModel> replaceModels, Integer replaceCode,
			String[] replaceTagNames, String replacement) {
		if (null == replaceModels) {
			replaceModels = new ArrayList<CatcherReplaceModel>();
		}
		replaceModels.add(new CatcherReplaceModel(replaceCode, replaceTagNames, replacement));
	}

	public static List<CatchRuler> evalueCatchRulers(String getXPath) {
		return evalueCatchRulers(null, null, getXPath, null, null, null, null, null, null);
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
