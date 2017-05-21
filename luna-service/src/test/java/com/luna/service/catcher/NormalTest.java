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
public class NormalTest extends ParentTest {

	@Test
	public void main() {
		catcher("http://blog.csdn.net/ebay/article/details/46549481");
	}

	public void catcher(String catcherWebUrl) {

		CatcherModel catcherModel = new CatcherModel();
		catcherModel.setCatcherWebUrl(catcherWebUrl);
		catcherModel.setCatcherWebName("CSDN");
		catcherModel.setResourceAuthor("Wang, Josh");
		catcherModel.setResourceDate("2015-06-18");
		catcherModel.setResourceDateFormat("yyyy-MM-dd");
		catcherModel.setResourceTitle("SolrCloud之分布式索引及与Zookeeper的集成");
		catcherModel.setCatcherWebsiteCode(2);
		catcherModel.setResourceCategoryCode(1);
		catcherModel.setAttachementPath(
				"D:/workspaces/eclipse_20170116/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/luna-web/static/attachement");

		List<CatcherIteratorRuler> iteratorRulers = new ArrayList<CatcherIteratorRuler>();
		catcherModel.setIteratorRulers(iteratorRulers);
		CatcherIteratorRuler iteratorRuler = new CatcherIteratorRuler();
		iteratorRulers.add(iteratorRuler);

		// 中间path
		iteratorRuler.setContentXPath("//div[@class='article_content']/*");

		// 一级内容标题
		List<CatchRuler> oneLevelContentTitleCatchRulers = evalueCatchRulers(null, null, "//p/strong/text()", null,
				null, null, HandlerMethodEnum.P.getCode(), null, null);

		iteratorRuler.setOneLevelContentTitleCatchRulers(oneLevelContentTitleCatchRulers);

		// 路径
		List<CatchRuler> contentPathCatchRulers = new ArrayList<CatchRuler>();

		evalueCatchRulers(contentPathCatchRulers, null, "//img/@src", null, null, null,
				HandlerMethodEnum.IMAGE.getCode(), null, null);
		evalueCatchRulers(contentPathCatchRulers, null, "//p/img/@src", null, null, null,
				HandlerMethodEnum.IMAGE.getCode(), null, null);

		iteratorRuler.setContentPathCatchRulers(contentPathCatchRulers);

		// 内容
		List<CatchRuler> contentCatchRulers = new ArrayList<>();
		iteratorRuler.setContentCatchRulers(contentCatchRulers);

		evalueCatchRulers(contentCatchRulers, null, "//p/allText()", null, null, null, HandlerMethodEnum.P.getCode(),
				null, "作者");
		evalueCatchRulers(contentCatchRulers, null, "//p/allText()", null, null, null, HandlerMethodEnum.P.getCode(),
				null, "<br>");
		evalueCatchRulers(contentCatchRulers, null, "//p/allText()", null, null, null, HandlerMethodEnum.P.getCode(),
				"参考资料：", null);
		List<String> equalsFilters = new ArrayList<String>();
		equalsFilters.add("&nbsp;");
		evalueCatchRulers(contentCatchRulers, null, "//p/allText()", null, HandlerMethodEnum.P.getCode(), null, null,
				equalsFilters);

		evalueCatchRulers(contentCatchRulers, null, "//p/allText()", null, null, null, HandlerMethodEnum.P.getCode(),
				null, null);

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

		List<String> indexOfFilters = new ArrayList<String>();
		if (null != indexOfFilter) {
			indexOfFilters.add(indexOfFilter);
		}
		CatchRuler catchRuler = new CatchRuler(tryXPath, getXPath, replaceModels, handlerCode, breakValue,
				indexOfFilters);
		catchRulers.add(catchRuler);
		return catchRulers;
	}

	public static List<CatchRuler> evalueCatchRulers(List<CatchRuler> catchRulers, String tryXPath, String getXPath,
			List<CatcherReplaceModel> replaceModels, Integer handlerCode, String breakValue, String indexOfFilter,
			List<String> equalsFilters) {
		if (null == catchRulers) {
			catchRulers = new ArrayList<CatchRuler>();
		}
		List<String> indexOfFilters = new ArrayList<String>();
		if (null != indexOfFilter) {
			indexOfFilters.add(indexOfFilter);
		}
		CatchRuler catchRuler = new CatchRuler(tryXPath, getXPath, replaceModels, handlerCode, breakValue,
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
