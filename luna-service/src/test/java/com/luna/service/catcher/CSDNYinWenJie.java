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
 * @date 2017年6月13日 下午10:46:53
 * @description
 */
public class CSDNYinWenJie extends ParentTest {

	@Test
	public void main() {

//		catcher("http://blog.csdn.net/yinwenjie/article/details/46605451");
//		catcher("http://blog.csdn.net/yinwenjie/article/details/46620711");
//		catcher("http://blog.csdn.net/yinwenjie/article/details/46742661");
//		catcher("http://blog.csdn.net/yinwenjie/article/details/46845997");
//		catcher("http://blog.csdn.net/yinwenjie/article/details/47010569");
//		catcher("http://blog.csdn.net/yinwenjie/article/details/47130609");
//		catcher("http://blog.csdn.net/yinwenjie/article/details/47211551");
//		catcher("http://blog.csdn.net/yinwenjie/article/details/47211641");
		catcher("http://blog.csdn.net/yinwenjie/article/details/48101869");

	}

	public void catcher(String catcherWebUrl) {

		String catcherWebName = "csdn";
		CatcherModel catcherModel = new CatcherModel();
		catcherModel.setResourceCategoryCode(10);
		catcherModel.setCatcherWebsiteCode(5);
		catcherModel.setCatcherWebUrl(catcherWebUrl);
		catcherModel.setCatcherWebName(catcherWebName);
		catcherModel.setResourceTitleCatchRulers(
				evalueCatchRulers("//div[@id='article_details']/div[@class='article_title']/h1/span/a/text()"));
		catcherModel.setResourceAuthor("yinwenjie");
		catcherModel.setResourceDateCatchRulers(evalueCatchRulers(
				"//div[@id='article_details']/div[@class='article_manage']/div[@class='article_r']/span[@class='link_postdate']/text()"));
		catcherModel.setResourceDateFormat("yyyy-MM-dd HH:mm");
		catcherModel.setAttachementPath(
				"D:/workspaces/eclipse_20170116/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/luna-web/static/attachement");

		List<CatcherIteratorRuler> iteratorRulers = new ArrayList<CatcherIteratorRuler>();
		catcherModel.setIteratorRulers(iteratorRulers);
		CatcherIteratorRuler iteratorRuler = new CatcherIteratorRuler();
		iteratorRulers.add(iteratorRuler);

		// 中间path
		iteratorRuler.setContentXPath("//div[@id='article_content']/div[@class='markdown_views']/*");

		// 内容
		List<CatchRuler> contentCatchRulers = new ArrayList<>();

		List<String> indexOfFilters = new ArrayList<String>();
		List<String> equalsFilters = new ArrayList<String>();
		equalsFilters.add("&nbsp;");
		equalsFilters.add("<span>&nbsp;</span>");

		List<String> breakValues = new ArrayList<String>();

		List<CatcherReplaceModel> replaceModelsA = new ArrayList<CatcherReplaceModel>();
		CatcherReplaceModel catcherReplaceModel = new CatcherReplaceModel(HtmlMarcherEnum.TAG.getCode(),
				new String[] { "a", "span" }, "");
		catcherReplaceModel.setIndexOfcondition("http://blog.csdn.net/yinwenjie");
		replaceModelsA.add(catcherReplaceModel);
		// replaceModelsA.add(new
		// CatcherReplaceModel(HtmlMarcherEnum.TAG_ATTRIBUTE.getCode(),
		// new String[] { "strong", "p", "span", "ul", "li" }, ""));
		//
		CatcherReplaceModel catcherReplaceModel1 = new CatcherReplaceModel(HtmlMarcherEnum.TAG.getCode(),
				new String[] { "a", "span" }, "");
		catcherReplaceModel1.setIndexOfcondition("http://lib.csdn.net");
		replaceModelsA.add(catcherReplaceModel1);

		evalueCatchRulers(contentCatchRulers, "//ul/li/allText()", "//ul", replaceModelsA,
				HandlerMethodEnum.ORIGIN.getCode(), null, null);

		evalueCatchRulers(contentCatchRulers, "//blockquote/allText()", "//blockquote", replaceModelsA,
				HandlerMethodEnum.ORIGIN.getCode(), null, null);

		evalueCatchRulers(contentCatchRulers, "//pre/code/allText()", "//pre/code/allText()", replaceModelsA,
				HandlerMethodEnum.PRE.getCode(), null, null);

		// evalueCatchRulers(contentCatchRulers, null,
		// "//div[@class='quote_div']/allText()", null,
		// HandlerMethodEnum.LANGUGE_JAVA.getCode(), null, null);

		evalueCatchRulers(contentCatchRulers, "//p/a/allText()", "//p/html()", replaceModelsA,
				HandlerMethodEnum.P.getCode(), breakValues, indexOfFilters, equalsFilters);
		evalueCatchRulers(contentCatchRulers, "//p/strong/allText()", "//p/html()", replaceModelsA,
				HandlerMethodEnum.P.getCode(), breakValues, indexOfFilters, equalsFilters);
		evalueCatchRulers(contentCatchRulers, "//p/span/strong/allText()", "//p/html()", replaceModelsA,
				HandlerMethodEnum.P.getCode(), breakValues, indexOfFilters, equalsFilters);
		evalueCatchRulers(contentCatchRulers, null, "//p/allText()", replaceModelsA, HandlerMethodEnum.P.getCode(),
				breakValues, indexOfFilters, equalsFilters);

		iteratorRuler.setContentCatchRulers(contentCatchRulers);
		// 内容标题
		List<CatchRuler> oneLevelContentTitleCatchRulers = evalueCatchRulers(null, "//h1/span/text()", "//h1/allText()",
				null, HandlerMethodEnum.P.getCode(), null, null, equalsFilters);

		evalueCatchRulers(oneLevelContentTitleCatchRulers, "//h1/strong/text()", "//h1/allText()", null,
				HandlerMethodEnum.P.getCode(), null, null, equalsFilters);

		evalueCatchRulers(oneLevelContentTitleCatchRulers, null, "//h1/allText()", null, HandlerMethodEnum.P.getCode(),
				null, null, equalsFilters);

		List<CatchRuler> twoLevelContentTitleCatchRulers = evalueCatchRulers(null, "//h3/a/text()", "//h3/allText()",
				null, HandlerMethodEnum.P.getCode(), null, null, equalsFilters);

		evalueCatchRulers(twoLevelContentTitleCatchRulers, "//h2/span/text()", "//h2/allText()", null,
				HandlerMethodEnum.P.getCode(), null, null, equalsFilters);
		evalueCatchRulers(twoLevelContentTitleCatchRulers, "//h2/strong/text()", "//h2/allText()", null,
				HandlerMethodEnum.P.getCode(), null, null, equalsFilters);
		evalueCatchRulers(twoLevelContentTitleCatchRulers, null, "//h2/allText()", null, HandlerMethodEnum.P.getCode(),
				null, null, equalsFilters);

		evalueCatchRulers(twoLevelContentTitleCatchRulers, "//h3/span/text()", "//h3/allText()", null,
				HandlerMethodEnum.P.getCode(), null, null, equalsFilters);

		evalueCatchRulers(twoLevelContentTitleCatchRulers, "//h3/strong/text()", "//h3/allText()", null,
				HandlerMethodEnum.P.getCode(), null, null, equalsFilters);

		evalueCatchRulers(twoLevelContentTitleCatchRulers, "//h4/span/text()", "//h4/allText()", null,
				HandlerMethodEnum.P.getCode(), null, null, equalsFilters);
		evalueCatchRulers(twoLevelContentTitleCatchRulers, "//h4/strong/text()", "//h4/allText()", null,
				HandlerMethodEnum.P.getCode(), null, null, equalsFilters);
		evalueCatchRulers(twoLevelContentTitleCatchRulers, null, "//h3/allText()", null, HandlerMethodEnum.P.getCode(),
				null, null, equalsFilters);
		evalueCatchRulers(twoLevelContentTitleCatchRulers, null, "//h4/allText()", null, HandlerMethodEnum.P.getCode(),
				null, null, equalsFilters);

		iteratorRuler.setOneLevelContentTitleCatchRulers(oneLevelContentTitleCatchRulers);
		iteratorRuler.setTwoLevelContentTitleCatchRulers(twoLevelContentTitleCatchRulers);

		// 路径
		List<CatchRuler> contentPathCatchRulers = new ArrayList<CatchRuler>();

		evalueCatchRulers(contentPathCatchRulers, null, "//p/a/img/@src", null, null, null,
				HandlerMethodEnum.IMAGE.getCode(), null, null);
		evalueCatchRulers(contentPathCatchRulers, null, "//p/span/a/img/@src", null, null, null,
				HandlerMethodEnum.IMAGE.getCode(), null, null);

		evalueCatchRulers(contentPathCatchRulers, null, "//p/span/img/@src", null, null, null,
				HandlerMethodEnum.IMAGE.getCode(), null, null);

		evalueCatchRulers(contentPathCatchRulers, null, "//p/strong/img/@src", null, null, null,
				HandlerMethodEnum.IMAGE.getCode(), null, null);

		evalueCatchRulers(contentPathCatchRulers, null, "//p/img/@src", null, null, null,
				HandlerMethodEnum.IMAGE.getCode(), null, null);

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
			Integer replaceCode, String[] replaceTagNames, String replacement, Integer handlerCode,
			List<String> breakValues, String indexOfFilter) {
		if (null == catchRulers) {
			catchRulers = new ArrayList<CatchRuler>();
		}
		List<CatcherReplaceModel> replaceModels = new ArrayList<CatcherReplaceModel>();
		replaceModels.add(new CatcherReplaceModel(replaceCode, replaceTagNames, replacement));

		List<String> indexOfFilters = new ArrayList<String>();
		if (null != indexOfFilter) {
			indexOfFilters.add(indexOfFilter);
		}
		CatchRuler catchRuler = new CatchRuler(tryXPath, getXPath, replaceModels, handlerCode, breakValues,
				indexOfFilters);
		catchRulers.add(catchRuler);
		return catchRulers;
	}

	public static List<CatchRuler> evalueCatchRulers(List<CatchRuler> catchRulers, String tryXPath, String getXPath,
			List<CatcherReplaceModel> replaceModels, Integer handlerCode, List<String> breakValues,
			List<String> indexOfFilters) {
		if (null == catchRulers) {
			catchRulers = new ArrayList<CatchRuler>();
		}
		CatchRuler catchRuler = new CatchRuler(tryXPath, getXPath, replaceModels, handlerCode, breakValues,
				indexOfFilters);
		catchRulers.add(catchRuler);
		return catchRulers;
	}

	public static List<CatchRuler> evalueCatchRulers(List<CatchRuler> catchRulers, String tryXPath, String getXPath,
			List<CatcherReplaceModel> replaceModels, Integer handlerCode, List<String> breakValues,
			List<String> indexOfFilters, List<String> equalsFilters) {
		if (null == catchRulers) {
			catchRulers = new ArrayList<CatchRuler>();
		}
		CatchRuler catchRuler = new CatchRuler(tryXPath, getXPath, replaceModels, handlerCode, breakValues,
				indexOfFilters);
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
