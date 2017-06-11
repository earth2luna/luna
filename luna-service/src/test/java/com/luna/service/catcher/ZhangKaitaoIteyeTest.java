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
public class ZhangKaitaoIteyeTest extends ParentTest {

	@Test
	public void main() {

//		catcher("http://jinnianshilongnian.iteye.com/blog/2232271");
//		catcher("http://jinnianshilongnian.iteye.com/blog/2232357");
//		catcher("http://jinnianshilongnian.iteye.com/blog/2235572");
//		catcher("http://jinnianshilongnian.iteye.com/blog/2245925");
//		catcher("http://jinnianshilongnian.iteye.com/blog/2247685");
//		catcher("http://jinnianshilongnian.iteye.com/blog/2258111");
//		catcher("http://jinnianshilongnian.iteye.com/blog/2259546");
		catcher("http://jinnianshilongnian.iteye.com/blog/2280928");

	}

	public void catcher(String catcherWebUrl) {

		String catcherWebName = "张开涛博客";
		CatcherModel catcherModel = new CatcherModel();
		catcherModel.setResourceCategoryCode(10);
		catcherModel.setCatcherWebsiteCode(4);
		catcherModel.setCatcherWebUrl(catcherWebUrl);
		catcherModel.setCatcherWebName(catcherWebName);
		catcherModel.setResourceTitleCatchRulers(
				evalueCatchRulers("//div[@id='main']/div[@class='blog_main']/div[@class='blog_title']/h3/a/text()"));
		catcherModel.setResourceAuthor("张开涛");
		catcherModel.setResourceDate("20170611");
		catcherModel.setResourceDateFormat("yyyyMMdd");
		catcherModel.setAttachementPath(
				"D:/workspaces/eclipse_20170116/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/luna-web/static/attachement");

		List<CatcherIteratorRuler> iteratorRulers = new ArrayList<CatcherIteratorRuler>();
		catcherModel.setIteratorRulers(iteratorRulers);
		CatcherIteratorRuler iteratorRuler = new CatcherIteratorRuler();
		iteratorRulers.add(iteratorRuler);

		// 中间path
		iteratorRuler.setContentXPath(
				"//div[@id='main']/div[@class='blog_main']/div/div[@class='iteye-blog-content-contain']/*");

		// 内容
		List<CatchRuler> contentCatchRulers = new ArrayList<>();

		List<String> indexOfFilters = new ArrayList<String>();
		List<String> equalsFilters = new ArrayList<String>();
		equalsFilters.add("&nbsp;");
		equalsFilters.add("<span>&nbsp;</span>");

		List<String> breakValues = new ArrayList<String>();
		breakValues.add("相关文章 ");
		
		List<CatcherReplaceModel> replaceModelsA = new ArrayList<CatcherReplaceModel>();
		CatcherReplaceModel catcherReplaceModel = new CatcherReplaceModel(HtmlMarcherEnum.TAG.getCode(),
				new String[] { "a" ,"span"}, "");
		catcherReplaceModel.setIndexOfcondition("jinnianshilongnian.iteye.com");
		replaceModelsA.add(catcherReplaceModel);
		replaceModelsA
				.add(new CatcherReplaceModel(HtmlMarcherEnum.TAG_ATTRIBUTE.getCode(), new String[] { "strong","p" }, ""));

//		evalueCatchRulers(contentCatchRulers, "//table/allText()", "//table", replaceModelsA,
//				HandlerMethodEnum.ORIGIN.getCode(), null, null);
//
//		evalueCatchRulers(contentCatchRulers, "//ol/li/allText()", "//ol", replaceModelsA,
//				HandlerMethodEnum.ORIGIN.getCode(), null, null);
//
		evalueCatchRulers(contentCatchRulers, "//ul/li/allText()", "//ul", replaceModelsA,
				HandlerMethodEnum.ORIGIN.getCode(), null, null);

		evalueCatchRulers(contentCatchRulers, null, "//pre[@name='code']/allText()", replaceModelsA,
				HandlerMethodEnum.LANGUGE_JAVA.getCode(), null, null);
//
//		evalueCatchRulers(contentCatchRulers, null, "//blockquote/pre/code[@class='language-javascript']/allText()",
//				null, HandlerMethodEnum.LANGUGE_JS.getCode(), null, null);
//
//		evalueCatchRulers(contentCatchRulers, null, "//blockquote/pre/code[@class='language-bash']/allText()", null,
//				HandlerMethodEnum.LANGUGE_BASH.getCode(), null, null);
//
//		evalueCatchRulers(contentCatchRulers, null, "//blockquote/pre/code[@class='language-http']/allText()", null,
//				HandlerMethodEnum.PRE.getCode(), null, null);
//
//		evalueCatchRulers(contentCatchRulers, null, "//blockquote/pre/code[@class='language-markup']/allText()", null,
//				HandlerMethodEnum.PRE.getCode(), null, null);
//
//		List<CatcherReplaceModel> replaceModels = new ArrayList<CatcherReplaceModel>();
//		replaceModels.add(new CatcherReplaceModel(HtmlMarcherEnum.TAG.getCode(), new String[] { "p" }, ""));
//		replaceModels.add(new CatcherReplaceModel(HtmlMarcherEnum.TAG.getCode(), new String[] { "br" }, "\r\n"));
//		evalueCatchRulers(contentCatchRulers, "//blockquote/p/allText()", "//blockquote/html()", replaceModels,
//				HandlerMethodEnum.PRE.getCode(), null, null);
//
//		evalueCatchRulers(contentCatchRulers, "//blockquote/div/p/allText()", "//blockquote/html()", replaceModelsA,
//				HandlerMethodEnum.CALLOUT_INFO.getCode(), null, null);

		evalueCatchRulers(contentCatchRulers, "//p/a/allText()", "//p/html()", replaceModelsA, HandlerMethodEnum.P.getCode(),
				breakValues, indexOfFilters, equalsFilters);
		evalueCatchRulers(contentCatchRulers, "//p/strong/allText()", "//p/html()", replaceModelsA, HandlerMethodEnum.P.getCode(),
				breakValues, indexOfFilters, equalsFilters);
		evalueCatchRulers(contentCatchRulers, null, "//p/allText()", replaceModelsA, HandlerMethodEnum.P.getCode(),
				breakValues, indexOfFilters, equalsFilters);

		iteratorRuler.setContentCatchRulers(contentCatchRulers);
		// 内容标题
		List<CatchRuler> oneLevelContentTitleCatchRulers = evalueCatchRulers(null, null, "//h1/text()", null, null,
				null, HandlerMethodEnum.P.getCode(), null, null);
		evalueCatchRulers(oneLevelContentTitleCatchRulers, null, "//h1/span/text()", null, null, null,
				HandlerMethodEnum.P.getCode(), null, null);
		evalueCatchRulers(oneLevelContentTitleCatchRulers, null, "//h1/strong/text()", null, null, null,
				HandlerMethodEnum.P.getCode(), null, null);
		evalueCatchRulers(oneLevelContentTitleCatchRulers, null, "//h2/text()", null, null, null,
				HandlerMethodEnum.P.getCode(), null, null);
		evalueCatchRulers(oneLevelContentTitleCatchRulers, null, "//h2/span/text()", null, null, null,
				HandlerMethodEnum.P.getCode(), null, null);
		evalueCatchRulers(oneLevelContentTitleCatchRulers, "//h2/strong/text()", "//h2/allText()", null, null, null,
				HandlerMethodEnum.P.getCode(), null, null);

		List<CatchRuler> twoLevelContentTitleCatchRulers = evalueCatchRulers(null, "//h3/a/text()", "//h3/allText()", null, null,
				null, HandlerMethodEnum.P.getCode(), null, null);
		evalueCatchRulers(twoLevelContentTitleCatchRulers, null, "//h3/span/text()", null, null,
				null, HandlerMethodEnum.P.getCode(), null, null);
		
		evalueCatchRulers(twoLevelContentTitleCatchRulers, null, "//h3/strong/text()", null, null,
				null, HandlerMethodEnum.P.getCode(), null, null);
		
		evalueCatchRulers(twoLevelContentTitleCatchRulers, null, "//h4/span/text()", null, null,
				null, HandlerMethodEnum.P.getCode(), null, null);
		evalueCatchRulers(twoLevelContentTitleCatchRulers, null, "//h4/strong/text()", null, null,
				null, HandlerMethodEnum.P.getCode(), null, null);
		evalueCatchRulers(twoLevelContentTitleCatchRulers, null, "//h3/text()", null, null,
				null, HandlerMethodEnum.P.getCode(), null, null);
		evalueCatchRulers(twoLevelContentTitleCatchRulers, null, "//h4/text()", null, null,
				null, HandlerMethodEnum.P.getCode(), null, null);
		
		iteratorRuler.setOneLevelContentTitleCatchRulers(oneLevelContentTitleCatchRulers);
		iteratorRuler.setTwoLevelContentTitleCatchRulers(twoLevelContentTitleCatchRulers);

		// 路径
		List<CatchRuler> contentPathCatchRulers = new ArrayList<CatchRuler>();

		evalueCatchRulers(contentPathCatchRulers, null, "//p/a/img/@src", null, null, null,
				HandlerMethodEnum.IMAGE.getCode(), null, null);
		evalueCatchRulers(contentPathCatchRulers, null, "//p/span/a/img/@src", null, null, null,
				HandlerMethodEnum.IMAGE.getCode(), null, null);

		evalueCatchRulers(contentPathCatchRulers, null, "//p/img/@src", null, null, null,
				HandlerMethodEnum.IMAGE.getCode(), null, null);
		
		evalueCatchRulers(contentPathCatchRulers, null, "//p/span/img/@src", null, null, null,
				HandlerMethodEnum.IMAGE.getCode(), null, null);
		
		evalueCatchRulers(contentPathCatchRulers, null, "//p/strong/img/@src", null, null, null,
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
