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
public class RunYiFengTestNew extends ParentTest {

	@Test
	public void main() {
		// List<String> list = new ArrayList<String>();
		// list.add("http://www.ruanyifeng.com/blog/2014/02/ssl_tls.html");
		// list.add("http://www.ruanyifeng.com/blog/2016/08/http.html");
		// list.add("http://www.ruanyifeng.com/blog/2017/04/css_in_js.html");
		// list.add("http://www.ruanyifeng.com/blog/2016/08/migrate-from-http-to-https.html");
		// list.add("http://www.ruanyifeng.com/blog/2014/09/illustration-ssl.html");
		// list.add("http://www.ruanyifeng.com/blog/2011/02/seven_myths_about_https.html");
		// list.add("http://www.ruanyifeng.com/blog/2014/09/ssl-latency.html");
		// list.add("http://www.ruanyifeng.com/blog/2014/09/package-management.html");
		// list.add("http://www.ruanyifeng.com/blog/2014/09/information-entropy.html");
		// list.add("http://www.ruanyifeng.com/blog/2014/07/chinese_fonts.html");
		// list.add("http://www.ruanyifeng.com/blog/2014/07/database_implementation.html");
		// list.add("http://www.ruanyifeng.com/blog/2014/06/airbnb.html");
		// list.add("http://www.ruanyifeng.com/blog/2008/12/a_visual_guide_to_version_control.html");
		// list.add("http://www.ruanyifeng.com/blog/2014/06/git_remote.html");
		// list.add("http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html");
		// list.add("http://www.ruanyifeng.com/blog/2014/05/restful_api.html");
		// list.add("http://www.ruanyifeng.com/blog/2011/09/restful.html");
		// list.add("http://www.ruanyifeng.com/blog/2017/04/memory-leak.html");
		// list.add("http://www.ruanyifeng.com/blog/2017/03/pointfree.html");
		// list.add("http://www.ruanyifeng.com/blog/2017/03/ramda.html");
		// list.add("http://www.ruanyifeng.com/blog/2017/03/reduce_transduce.html");
		// list.add("http://www.ruanyifeng.com/blog/2017/03/gartner-hype-cycle.html");
		// list.add("http://www.ruanyifeng.com/blog/2016/04/cors.html");
		// list.add("http://www.ruanyifeng.com/blog/2017/05/fish_shell.html");
		// list.add("http://www.ruanyifeng.com/blog/2017/05/css-variables.html");
		// list.add("http://www.ruanyifeng.com/blog/2017/02/filename-should-be-lowercase.html");
		// list.add("http://www.ruanyifeng.com/blog/2016/09/csp.html");
		// list.add("http://www.ruanyifeng.com/blog/2016/09/how_amazon_take_soa.html");
		// list.add("http://www.ruanyifeng.com/blog/2016/09/conservative_vs_liberal_programmer.html");
		// list.add("http://www.ruanyifeng.com/blog/2016/09/software-architecture.html");
		// list.add("http://www.ruanyifeng.com/blog/2016/07/yaml.html");
		// list.add("http://www.ruanyifeng.com/blog/2016/07/google-monolithic-source-repository.html");
		// list.add("http://www.ruanyifeng.com/blog/2016/06/css_modules.html");
		// list.add("http://www.ruanyifeng.com/blog/2016/03/systemd-tutorial-part-two.html");
		// list.add("http://www.ruanyifeng.com/blog/2016/03/systemd-tutorial-commands.html");
		// list.add("http://www.ruanyifeng.com/blog/2016/02/linux-daemon.html");
		// list.add("http://www.ruanyifeng.com/blog/2016/01/commit_message_change_log.html");
		// list.add("http://www.ruanyifeng.com/blog/2015/12/git-workflow.html");
		// list.add("http://www.ruanyifeng.com/blog/2015/12/git-cheat-sheet.html");
		// list.add("http://www.ruanyifeng.com/blog/2015/09/git-bitmap.html");
		// list.add("http://www.ruanyifeng.com/blog/2015/08/git-use-process.html");
		// list.add("http://www.ruanyifeng.com/blog/2012/07/git.html");
		// list.add("http://www.ruanyifeng.com/blog/2015/09/web-page-performance-in-depth.html");
		// list.add("http://www.ruanyifeng.com/blog/2015/07/flex-examples.html");
		// list.add("http://www.ruanyifeng.com/blog/2015/07/flex-grammar.html");
		// list.add("http://www.ruanyifeng.com/blog/2015/04/fortune.html");
		// list.add("http://www.ruanyifeng.com/blog/2015/03/build-website-with-make.html");
		// list.add("http://www.ruanyifeng.com/blog/2015/02/make.html");
		// list.add("http://www.ruanyifeng.com/blog/2015/02/mvcmvp_mvvm.html");
		// list.add("http://www.ruanyifeng.com/blog/2014/11/basic-charts.html");
		// list.add("http://www.ruanyifeng.com/blog/2014/03/server_setup.html");
		// list.add("http://www.ruanyifeng.com/blog/2014/02/css_transition_and_animation.html");
		// list.add("http://www.ruanyifeng.com/blog/2013/12/getting_started_with_postgresql.html");
		// list.add("http://www.ruanyifeng.com/blog/2013/07/how_to_make_search_engines_find_ajax_content.html");
		// list.add("http://www.ruanyifeng.com/blog/2013/06/rsa_algorithm_part_one.html");
		// list.add("http://www.ruanyifeng.com/blog/2013/07/rsa_algorithm_part_two.html");
		// list.add("http://www.ruanyifeng.com/blog/2013/07/gpg.html");
		// list.add("http://www.ruanyifeng.com/blog/2013/06/emmet_and_haml.html");
		// list.add("http://www.ruanyifeng.com/blog/2016/01/website-obesity-crisis.html");
		// list.add("http://www.ruanyifeng.com/blog/2013/01/secure_boot.html");
		// list.add("http://www.ruanyifeng.com/blog/2004/06/gpl.html");
		// list.add("http://www.ruanyifeng.com/blog/2005/03/post_112.html");
		// list.add("http://www.ruanyifeng.com/blog/2006/03/programming_language_evaluations.html");
		// list.add("http://www.ruanyifeng.com/blog/2006/05/laziness_impatience_and_hubris.html");
		// list.add("http://www.ruanyifeng.com/blog/2006/08/minimalism_of_web_design.html");
		// list.add("http://www.ruanyifeng.com/blog/2007/04/principles_of_providing_a_tool.html");
		// list.add("http://www.ruanyifeng.com/blog/2007/06/camelcase.html");
		// list.add("http://www.ruanyifeng.com/blog/2007/11/mvc.html");
		// list.add("http://www.ruanyifeng.com/blog/2007/12/google_s_software_principles.html");
		// list.add("http://www.ruanyifeng.com/blog/2009/10/5_ways_to_search_for_files_using_the_terminal.html");
		// list.add("http://www.ruanyifeng.com/blog/2016/09/redux_tutorial_part_one_basic_usages.html");
		// list.add("http://www.ruanyifeng.com/blog/2016/09/redux_tutorial_part_two_async_operations.html");
		// list.add("http://www.ruanyifeng.com/blog/2016/09/redux_tutorial_part_three_react-redux.html");
		// list.add("http://www.ruanyifeng.com/blog/2015/03/react.html");
		// list.add("http://www.ruanyifeng.com/blog/2012/12/obama_fundraising_website.html");
		// list.add("http://www.ruanyifeng.com/blog/2007/03/rfc2119.html");
		// list.add("http://www.ruanyifeng.com/blog/2007/07/three_principles_of_web_design.html");
		// list.add("http://www.ruanyifeng.com/blog/2007/12/google_chart_api.html");
		// list.add("http://www.ruanyifeng.com/blog/2008/01/javascript_book_recommendation.html");
		// list.add("http://www.ruanyifeng.com/blog/2008/02/notes_on_the_cathedral_and_the_bazaar.html");
		// list.add("http://www.ruanyifeng.com/blog/2008/02/codes_for_language_names.html");
		
		// list.add("http://www.ruanyifeng.com/blog/2017/05/websocket.html");
		// list.add("http://www.ruanyifeng.com/blog/2017/05/technology-is-future.html");
		// list.add("http://www.ruanyifeng.com/blog/2008/02/rdf.html");
		// list.add("http://www.ruanyifeng.com/blog/2017/05/server-sent_events.html");

		catcher("http://www.ruanyifeng.com/blog/2017/05/server-sent_events.html");

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
		catcherModel.setIteratorRulers(iteratorRulers);
		CatcherIteratorRuler iteratorRuler = new CatcherIteratorRuler();
		iteratorRulers.add(iteratorRuler);

		// 中间path
		iteratorRuler.setContentXPath("//article[@class='hentry']/div[@class='asset-content']/*");

		// 内容
		List<CatchRuler> contentCatchRulers = new ArrayList<>();

		List<String> indexOfFilters = new ArrayList<String>();
		indexOfFilters.add("阮一峰 / 2005-03-31");
		indexOfFilters.add("加入了文件的命名规则。");
		indexOfFilters.add("上一篇文章我介绍了");
		indexOfFilters.add("前两篇文章，我介绍了");
		indexOfFilters.add("阮一峰 / 2005-03-31");
		indexOfFilters.add("今天，就接着来看，现实中怎么使用这个算法，对信息加密和解密");
		indexOfFilters.add("Redux 的基本用法就介绍到这里");
		indexOfFilters.add("本文选自我正在写的新书《未来世界的幸存者》");
		indexOfFilters.add("=================");

		List<String> breakValues = new ArrayList<String>();

		breakValues.add("下面是推广时间。");
		breakValues.add("=====================================================");
		breakValues.add("===================================");
		breakValues.add("==========");

		List<CatcherReplaceModel> replaceModelsA = new ArrayList<CatcherReplaceModel>();
		CatcherReplaceModel catcherReplaceModel = new CatcherReplaceModel(HtmlMarcherEnum.TAG.getCode(),
				new String[] { "a" }, "");
		catcherReplaceModel.setIndexOfcondition("www.ruanyifeng.com");
		replaceModelsA.add(catcherReplaceModel);

		evalueCatchRulers(contentCatchRulers, "//table/allText()", "//table", replaceModelsA,
				HandlerMethodEnum.ORIGIN.getCode(), null, null);
		
		evalueCatchRulers(contentCatchRulers, "//ol/li/allText()", "//ol", replaceModelsA,
				HandlerMethodEnum.ORIGIN.getCode(), null, null);
		
		evalueCatchRulers(contentCatchRulers, "//ul/li/allText()", "//ul", replaceModelsA,
				HandlerMethodEnum.ORIGIN.getCode(), null, null);
		
		evalueCatchRulers(contentCatchRulers, "//blockquote/ul/li/allText()", "//blockquote/html()", replaceModelsA,
				HandlerMethodEnum.ORIGIN.getCode(), null, null);

		evalueCatchRulers(contentCatchRulers, null, "//blockquote/pre/code[@class='language-javascript']/allText()",
				null, HandlerMethodEnum.LANGUGE_JS.getCode(), null, null);
		
		evalueCatchRulers(contentCatchRulers, null, "//blockquote/pre/code[@class='language-bash']/allText()",
				null, HandlerMethodEnum.LANGUGE_BASH.getCode(), null, null);
		
		evalueCatchRulers(contentCatchRulers, null, "//blockquote/pre/code[@class='language-http']/allText()",
				null, HandlerMethodEnum.PRE.getCode(), null, null);
		
		evalueCatchRulers(contentCatchRulers, null, "//blockquote/pre/code[@class='language-markup']/allText()",
				null, HandlerMethodEnum.PRE.getCode(), null, null);

		evalueCatchRulers(contentCatchRulers, "//blockquote/p/allText()", "//blockquote/html()", replaceModelsA,
				HandlerMethodEnum.CALLOUT_INFO.getCode(), null, null);
		
		evalueCatchRulers(contentCatchRulers, "//blockquote/div/p/allText()", "//blockquote/html()", replaceModelsA,
				HandlerMethodEnum.CALLOUT_INFO.getCode(), null, null);

		evalueCatchRulers(contentCatchRulers, null, "//p/html()", replaceModelsA, HandlerMethodEnum.P.getCode(),
				breakValues, indexOfFilters);

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
