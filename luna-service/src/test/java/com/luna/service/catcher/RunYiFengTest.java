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
public class RunYiFengTest extends ParentTest {

	@Test
	public void main() {
		List<String> list = new ArrayList<String>();
		list.add("http://www.ruanyifeng.com/blog/2014/02/ssl_tls.html");
		list.add("http://www.ruanyifeng.com/blog/2016/08/http.html");
		list.add("http://www.ruanyifeng.com/blog/2017/04/css_in_js.html");
		list.add("http://www.ruanyifeng.com/blog/2016/08/migrate-from-http-to-https.html");
		list.add("http://www.ruanyifeng.com/blog/2014/09/illustration-ssl.html");
		list.add("http://www.ruanyifeng.com/blog/2011/02/seven_myths_about_https.html");
		list.add("http://www.ruanyifeng.com/blog/2014/09/ssl-latency.html");
		list.add("http://www.ruanyifeng.com/blog/2014/09/package-management.html");
		list.add("http://www.ruanyifeng.com/blog/2014/09/information-entropy.html");
		list.add("http://www.ruanyifeng.com/blog/2014/07/chinese_fonts.html");
		list.add("http://www.ruanyifeng.com/blog/2014/07/database_implementation.html");
		list.add("http://www.ruanyifeng.com/blog/2014/06/airbnb.html");
		list.add("http://www.ruanyifeng.com/blog/2008/12/a_visual_guide_to_version_control.html");
		list.add("http://www.ruanyifeng.com/blog/2014/06/git_remote.html");
		list.add("http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html");
		list.add("http://www.ruanyifeng.com/blog/2014/05/restful_api.html");
		list.add("http://www.ruanyifeng.com/blog/2011/09/restful.html");
		list.add("http://www.ruanyifeng.com/blog/2017/04/memory-leak.html");
		list.add("http://www.ruanyifeng.com/blog/2017/03/pointfree.html");
		list.add("http://www.ruanyifeng.com/blog/2017/03/ramda.html");
		list.add("http://www.ruanyifeng.com/blog/2017/03/reduce_transduce.html");
		list.add("http://www.ruanyifeng.com/blog/2017/03/gartner-hype-cycle.html");
		list.add("http://www.ruanyifeng.com/blog/2016/04/cors.html");

		//String catcherWebUrl = "http://www.ruanyifeng.com/blog/2016/04/cors.html";
		for (String url : list) {
			catcher(url);
		}
//		catcher("http://www.ruanyifeng.com/blog/2014/09/information-entropy.html");
		
	}

	public void catcher(String catcherWebUrl) {

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
		catcherModel.setCatcherWebsiteCode(1);
		catcherModel.setAttachementPath(
				"D:/workspaces/eclipse_20170116/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/luna-web/static/attachement");
		catcherModel.setResourceCategoryCode(1);

		List<CatcherIteratorRuler> iteratorRulers = new ArrayList<CatcherIteratorRuler>();
		CatcherIteratorRuler iteratorRulerBefore = new CatcherIteratorRuler();
		CatcherIteratorRuler iteratorRuler = new CatcherIteratorRuler();
		iteratorRulers.add(iteratorRulerBefore);
		iteratorRulers.add(iteratorRuler);
		catcherModel.setIteratorRulers(iteratorRulers);

		iteratorRulerBefore.setOneLevelContentTitleCatchRulers(
				evalueCatchRulers(null, null, "//article[@class='hentry']/h1[@id='page-title']/text()", null, null,
						null, HandlerMethodEnum.P.getCode(), null, null));
		iteratorRulerBefore.setIfMark(true);

		// 中间path
		iteratorRuler.setContentXPath("//article[@class='hentry']/div[@class='asset-content']/*");

		// 内容
		List<CatchRuler> contentCatchRulers = new ArrayList<>();

		evalueCatchRulers(contentCatchRulers, null, "//p/allText()", null, null, null, null, null, "上一篇文章我介绍了");
		evalueCatchRulers(contentCatchRulers, null, "//p/text()", null, null, null, null, "==========", null);
		evalueCatchRulers(contentCatchRulers, null, "//p/text()", null, null, null, null,
				"=====================================================", null);

		List<CatcherReplaceModel> replaceModels = new ArrayList<CatcherReplaceModel>();
		replaceModels.add(new CatcherReplaceModel(HtmlMarcherEnum.TAG.getCode(), new String[] { "p", "a", "div" ,"sub"}, ""));
		replaceModels.add(new CatcherReplaceModel(HtmlMarcherEnum.TAG.getCode(), new String[] { "br" }, "\r\n"));
		evalueCatchRulers(contentCatchRulers, "//blockquote/p/br", "//blockquote/html()", replaceModels,
				HandlerMethodEnum.PRE.getCode(), null, null);

		evalueCatchRulers(contentCatchRulers, "//blockquote/ul", "//blockquote/html()",
				HtmlMarcherEnum.TAG.getCode(), new String[] { "p", "a", "div","sub" }, null, HandlerMethodEnum.P.getCode(),
				null, null);
		
		evalueCatchRulers(contentCatchRulers, "//blockquote/p/strong/allText()", "//blockquote/html()",
				HtmlMarcherEnum.TAG.getCode(), new String[] { "p", "a", "div","sub" }, null, HandlerMethodEnum.P.getCode(),
				null, null);
		
		evalueCatchRulers(contentCatchRulers, "//blockquote/div/p/allText()", "//blockquote/div/html()",
				HtmlMarcherEnum.TAG.getCode(), new String[] { "p", "a" ,"sub"}, null, HandlerMethodEnum.PRE.getCode(), null,
				null);
		evalueCatchRulers(contentCatchRulers, "//blockquote/p/allText()", "//blockquote/html()",
				HtmlMarcherEnum.TAG.getCode(), new String[] { "p", "a" ,"sub"}, null, HandlerMethodEnum.PRE.getCode(), null,
				null);

		evalueCatchRulers(contentCatchRulers, "//blockquote/ul/li/a/html()", "//blockquote/ul",
				HtmlMarcherEnum.TAG.getCode(), new String[] { "a" }, null, HandlerMethodEnum.P.getCode(), null, null);
		
		evalueCatchRulers(contentCatchRulers, null, "//blockquote/ul", null, null, null, HandlerMethodEnum.P.getCode(),
				null, null);
		
		evalueCatchRulers(contentCatchRulers, null, "//blockquote/pre/code/allText()", null, null, null,
				HandlerMethodEnum.PRE.getCode(), null, null);

		evalueCatchRulers(contentCatchRulers, "//p/a/strong/text()", "//p/html()", HtmlMarcherEnum.TAG.getCode(),
				new String[] { "a" }, null, HandlerMethodEnum.P.getCode(), null, null);

		evalueCatchRulers(contentCatchRulers, "//ul/li/a/html()", "//ul", null, null, null,
				HandlerMethodEnum.P.getCode(), null, null);

		evalueCatchRulers(contentCatchRulers, "//p/a/text()", "//p/html()", HtmlMarcherEnum.TAG.getCode(),
				new String[] { "a" }, null, HandlerMethodEnum.P.getCode(), null, null);

		evalueCatchRulers(contentCatchRulers, "//p/code/text()", "//p/html()", null, null, null,
				HandlerMethodEnum.P.getCode(), null, null);

		evalueCatchRulers(contentCatchRulers, "//p/strong/text()", "//p/html()", null, null, null,
				HandlerMethodEnum.P.getCode(), null, null);

		evalueCatchRulers(contentCatchRulers, null, "//p/allText()", null, null, null, HandlerMethodEnum.P.getCode(),
				null, null);

		iteratorRuler.setContentCatchRulers(contentCatchRulers);
		// 内容标题
		List<CatchRuler> oneLevelContentTitleCatchRulers = evalueCatchRulers(null, null, "//h2/text()", null, null,
				null, HandlerMethodEnum.P.getCode(), null, null);

		List<CatchRuler> twoLevelContentTitleCatchRulers = evalueCatchRulers(null, null, "//h3/text()", null, null,
				null, HandlerMethodEnum.P.getCode(), null, null);
		iteratorRuler.setOneLevelContentTitleCatchRulers(oneLevelContentTitleCatchRulers);
		iteratorRuler.setTwoLevelContentTitleCatchRulers(twoLevelContentTitleCatchRulers);

		// 路径
		List<CatchRuler> contentPathCatchRulers = new ArrayList<CatchRuler>();

		evalueCatchRulers(contentPathCatchRulers, null, "//p/a/img/@src", null, null, null,
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
			List<CatcherReplaceModel> replaceModels, Integer handlerCode, String breakValue, String indexOfFilter) {
		if (null == catchRulers) {
			catchRulers = new ArrayList<CatchRuler>();
		}
		CatchRuler catchRuler = new CatchRuler(tryXPath, getXPath, replaceModels, handlerCode, breakValue,
				indexOfFilter);
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
