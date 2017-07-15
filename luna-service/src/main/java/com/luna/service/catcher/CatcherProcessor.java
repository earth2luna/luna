package com.luna.service.catcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luna.dao.mapper.IResourcesContentMapper;
import com.luna.dao.mapper.IResourcesMapper;
import com.luna.dao.po.Resources;
import com.luna.service.data.utils.ContentUtils;
import com.luna.service.dto.CatcherContent;
import com.luna.service.enumer.content.HandlerMethodEnum;
import com.luna.service.enumer.resource.CreatorEnum;
import com.luna.service.enumer.resource.StatusEnum;
import com.luna.service.enumer.service.HtmlMarcherEnum;
import com.luna.utils.DateUtils;
import com.luna.utils.DowloadUtils;
import com.luna.utils.FilePropertyUtils;
import com.luna.utils.LangUtils;
import com.luna.utils.UrlUtils;
import com.luna.utils.classes.KV;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

public class CatcherProcessor implements PageProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(CatcherProcessor.class);

	private Site site = Site.me().setRetryTimes(3).setSleepTime(5000).setTimeOut(10000);

	private IResourcesMapper resourcesMapper;
	private IResourcesContentMapper contentMapper;
	private CatcherModel catcherModel;

	public CatcherProcessor(IResourcesMapper resourcesMapper, IResourcesContentMapper contentMapper,
			CatcherModel catcherModel) {
		super();
		this.resourcesMapper = resourcesMapper;
		this.contentMapper = contentMapper;
		this.catcherModel = catcherModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see us.codecraft.webmagic.processor.PageProcessor#process(us.codecraft.
	 * webmagic.Page)
	 */
	@Override
	public void process(Page page) {
		Resources resources = new Resources();
		resources.setCategoryId(LangUtils.longValueOfNumber(catcherModel.getResourceCategoryCode()));
		resources.setCreateTime(new Date());
		resources.setCreatorId(CreatorEnum.LAULYL.getCode());
		resources.setStatus(LangUtils.intValueOfNumber(StatusEnum.INIT.getCode()));
		resources.setSourceSiteLink(catcherModel.getCatcherWebUrl());
		resources.setSourceSiteName(catcherModel.getCatcherWebName());
		resources.setWebsiteCode(catcherModel.getCatcherWebsiteCode());

		if (StringUtils.isEmpty(catcherModel.getResourceTitle())) {
			CatcherSubModel resourceTitleKv = handler(catcherModel.getResourceTitleCatchRulers(), page.getHtml());
			if (null != resourceTitleKv) {
				resources.setTitle(resourceTitleKv.getValue());
			}
		} else {
			resources.setTitle(catcherModel.getResourceTitle());
		}

		if (StringUtils.isEmpty(catcherModel.getResourceDate())) {
			CatcherSubModel resourceDateKv = handler(catcherModel.getResourceDateCatchRulers(), page.getHtml());
			if (null != resourceDateKv) {
				resources.setSourceDate(
						DateUtils.parse(resourceDateKv.getValue(), catcherModel.getResourceDateFormat()));
			}
		} else {
			resources.setSourceDate(
					DateUtils.parse(catcherModel.getResourceDate(), catcherModel.getResourceDateFormat()));
		}

		if (StringUtils.isEmpty(catcherModel.getResourceAuthor())) {
			CatcherSubModel resourceAuthorKv = handler(catcherModel.getResourceAuthorCatchRulers(), page.getHtml());
			if (null != resourceAuthorKv) {
				resources.setSourceAuthor(resourceAuthorKv.getValue());
			}
		} else {
			resources.setSourceAuthor(catcherModel.getResourceAuthor());
		}

		List<CatcherContent> rcs = new ArrayList<CatcherContent>();
		// CatcherIteratorRuler xpaths = catcherModel.getIteratorRuler()
		// Iterator<CatcherIteratorRuler> iterator = xpaths.iterator();

		// Long currentLevel = null;// 当前级别
		Long oneLevelId = null;// 一级别id
		Long twoLevelId = null;// 二级别id
		Long levelId = 0L;// 当前levelId
		// boolean ifSkip = false;// 是否终止
		// while (iterator.hasNext()) {
		CatcherIteratorRuler iteratorRuler = catcherModel.getIteratorRuler();
		// if (iteratorRuler.isIfMark()) {
		// // 获取一级内容标题
		// CatcherSubModel oneLevelContentTitleKv =
		// handler(iteratorRuler.getOneLevelContentTitleCatchRulers(),
		// page.getHtml());
		// if (null != oneLevelContentTitleKv) {
		// CatcherContent rc = new CatcherContent();
		// rc.setTitle(oneLevelContentTitleKv.getValue());
		// rc.setHandlerCode(oneLevelContentTitleKv.getHandlerCode());
		// rcs.add(rc);
		// oneLevelId = ++levelId;
		// rc.setLevelId(oneLevelId);
		// // currentLevel = 1L;
		// }
		// } else {
		List<String> transit = page.getHtml().xpath(iteratorRuler.getContentXPath()).all();
		CatcherContent rc = null;
		for (int i = 0; i < transit.size(); i++) {
			String content = transit.get(i);
			Html tempHtml = Html.create(content);
			// 获取一级内容标题
			CatcherSubModel oneLevelContentTitleKv = handler(iteratorRuler.getOneLevelContentTitleCatchRulers(),
					tempHtml);
			if (null != oneLevelContentTitleKv) {
				if (oneLevelContentTitleKv.isIfBreak()) {
					break;
				}
				if (oneLevelContentTitleKv.isIfFilter()) {
					continue;
				}
				// 如果当前是一或者二级级标题，并且找到了一级标题
				if (null != rc) {
					rcs.add(rc);
				}
				// 重新开启一个一级标题
				rc = new CatcherContent();
				rc.setTitle(oneLevelContentTitleKv.getValue());
				rc.setHandlerCode(oneLevelContentTitleKv.getHandlerCode());
				oneLevelId = ++levelId;
				rc.setLevelId(oneLevelId);
				LOGGER.info("一级标题:" + oneLevelContentTitleKv.getValue());
				// currentLevel = 1L;
				continue;
			}
			// 如果第一个不是一级标题，则把文章看作一级标题
			if (null == oneLevelId) {
				rc = new CatcherContent();
				rc.setTitle(resources.getTitle());
				rc.setHandlerCode(HandlerMethodEnum.P.getCode());
				oneLevelId = ++levelId;
				rc.setLevelId(oneLevelId);
			}

			// 获取二级内容标题
			CatcherSubModel twoLevelContentTitleKv = handler(iteratorRuler.getTwoLevelContentTitleCatchRulers(),
					tempHtml);
			if (null != twoLevelContentTitleKv) {

				if (twoLevelContentTitleKv.isIfBreak()) {
					break;
				}

				if (twoLevelContentTitleKv.isIfFilter()) {
					continue;
				}
				// 如果当前是一或者二级级标题，并且第二次找到了二级标题，则先放入当前标题，则先放入当前标题
				if (null != rc) {
					rcs.add(rc);
				}
				// 重新开启一个二级标题
				rc = new CatcherContent();
				rc.setTitle(twoLevelContentTitleKv.getValue());
				rc.setHandlerCode(twoLevelContentTitleKv.getHandlerCode());
				rc.setParentLevelId(oneLevelId);

				twoLevelId = ++levelId;
				rc.setLevelId(twoLevelId);
				LOGGER.info("二级标题:" + twoLevelContentTitleKv.getValue());
				// currentLevel = 2L;
				continue;
			}

			// 获取当前内容属于哪个标题下

			Long currentLevelId = oneLevelId;

			// 获取路径
			CatcherSubModel contentPathKv = handler(iteratorRuler.getContentPathCatchRulers(), tempHtml);
			if (null != contentPathKv) {
				if (contentPathKv.isIfBreak()) {
					break;
				}

				if (contentPathKv.isIfFilter()) {
					continue;
				}

				boolean pathFlat = false;
				for (String path : contentPathKv.getValues()) {
					// 网站编码+类目编码+当前日期+系统毫秒时间
					String endPath = FilePropertyUtils.appendPath(catcherModel.getCatcherWebsiteCode().toString(),
							catcherModel.getResourceCategoryCode().toString(),
							DateUtils.getCurrentDateFormat(DateUtils.DATE_PATTERN_3),
							LangUtils.toString(System.currentTimeMillis()));
					// 附件路径+网站编码+类目编码+系统毫秒时间
					String outputPath = FilePropertyUtils.appendPath(catcherModel.getAttachementPath(), endPath);
					String fullPath = UrlUtils.makesureFullUrl(catcherModel.getCatcherWebUrl(), path);
					LOGGER.info("当前下载路径:" + fullPath);
					LOGGER.info("下载至:" + outputPath);
					KV<String, Boolean> storeValue = DowloadUtils.storeImage(fullPath, outputPath);
					if (storeValue.getV()) {
						if (null == rc) {
							rc = new CatcherContent();
							rc.setParentLevelId(currentLevelId);
						} else {
							if (StringUtils.isNotEmpty(rc.getPath())) {
								rcs.add(rc);
								rc = new CatcherContent();
								rc.setParentLevelId(currentLevelId);
							}
						}
						rc.setPath(endPath + FilePropertyUtils.SPLITOR_SUFFIX + storeValue.getK());
						rc.setHandlerCode(contentPathKv.getHandlerCode());
						rcs.add(rc);
						rc = null;
						pathFlat = true;
					}
				}
				if (pathFlat) {
					continue;
				}

			}

			// 获取内容
			CatcherSubModel contentKv = handler(iteratorRuler.getContentCatchRulers(), tempHtml);
			if (null != contentKv) {
				if (contentKv.isIfBreak()) {
					break;
				}

				if (contentKv.isIfFilter()) {
					continue;
				}
				if (null == rc) {
					rc = new CatcherContent();
					rc.setParentLevelId(currentLevelId);
				} else {
					if (StringUtils.isNotEmpty(rc.getContent())) {
						rcs.add(rc);
						rc = new CatcherContent();
						rc.setParentLevelId(currentLevelId);
					}
				}
				rc.setContent(contentKv.getValue());
				rc.setHandlerCode(contentKv.getHandlerCode());
				rcs.add(rc);
				rc = null;

				LOGGER.info("内容:" + contentKv.getValue());
				continue;
			}

		}

		// if (null != rc) {
		//
		// }
		// }
		// }
		resourcesMapper.insert(resources);
		ContentUtils.insertCatchers(contentMapper, rcs, resources.getId());
	}

	private static void completeRootXPath(CatchRuler ruler) {
		if (null != ruler) {
			String beforeInsert = "/html/body";
			// 如果是根，并且没有加根标记，就加上根标记
			if (StringUtils.isNotBlank(ruler.getGetXPath()) && ruler.getGetXPath().startsWith("/")
					&& !ruler.getGetXPath().startsWith(beforeInsert)) {
				ruler.setGetXPath(beforeInsert + ruler.getGetXPath());
			}
			if (StringUtils.isNotBlank(ruler.getTryXPath()) && ruler.getTryXPath().startsWith("/")
					&& !ruler.getTryXPath().startsWith(beforeInsert)) {
				ruler.setTryXPath(beforeInsert + ruler.getTryXPath());
			}
		}
	}

	private static CatcherSubModel handler(List<CatchRuler> rulers, Html relative) {
		if (CollectionUtils.isEmpty(rulers))
			return null;
		Iterator<CatchRuler> iterator = rulers.iterator();
		while (iterator.hasNext()) {
			CatchRuler ruler = iterator.next();
			Html html = relative;
			if (LangUtils.isBlank(ruler.getGetXPath())) {
				continue;
			}

			// 增加根xPath补全
			completeRootXPath(ruler);

			String tryValue = null;
			Selectable originSelectable = null;

			// 探测拦截
			if (LangUtils.isNotBlank(ruler.getTryXPath())) {
				tryValue = LangUtils.trim(html.xpath(ruler.getTryXPath()));

				if (LangUtils.isBlank(tryValue)) {
					continue;
				}
			}

			// 真正获取

			originSelectable = html.xpath(ruler.getGetXPath());

			if (null == originSelectable || LangUtils.isBlank(originSelectable.toString())) {
				continue;
			}

			String originValue = LangUtils.trim(StringUtils.join(originSelectable.all(), ""));

			// 替换内容
			if (CollectionUtils.isNotEmpty(ruler.getReplaceModels())) {
				for (CatcherReplaceModel catchReplaceModel : ruler.getReplaceModels()) {
					String[] tagNames = StringUtils.split(catchReplaceModel.getReplaceTagNames(), ",");
					HtmlMarcherEnum marcherEnum = HtmlMarcherEnum.get(catchReplaceModel.getReplaceCode());
					if (ArrayUtils.isEmpty(tagNames) || null == marcherEnum) {
						continue;
					}

					if (StringUtils.isEmpty(catchReplaceModel.getIndexOfcondition())
							|| -1 != originValue.indexOf(catchReplaceModel.getIndexOfcondition())) {
						String replacement = LangUtils.defaultValue(catchReplaceModel.getReplacement(), "");

						String regex = marcherEnum.getRegex(tagNames);
						originSelectable = originSelectable.replace(regex, replacement);
					}

				}
			}
			// 替换完成后再重新生成
			originValue = StringUtils.join(originSelectable.all(), "");
			// trim
			String trimValue = LangUtils.trim(originValue);

			// 过滤空串
			if (LangUtils.isBlank(trimValue)) {
				continue;
			}

			// 集合值
			List<String> values = originSelectable.all();

			// 处理编码
			Integer code = getHandlerCode(ruler, html);
			HandlerMethodEnum methodEnum = HandlerMethodEnum.get(code);
			// pre 不去除空格、不进行文本过滤、不进行抓取结束

			if (HandlerMethodEnum.LANGUGE_BASH == methodEnum || HandlerMethodEnum.LANGUGE_HTML == methodEnum
					|| HandlerMethodEnum.LANGUGE_JAVA == methodEnum || HandlerMethodEnum.LANGUGE_JS == methodEnum
					|| HandlerMethodEnum.LANGUGE_JSON == methodEnum || HandlerMethodEnum.PRE == methodEnum) {

				CatcherSubModel model = new CatcherSubModel(originValue, code, false, false);
				model.setValues(values);
				return model;
			}

			// index of 过滤文本
			if (CollectionUtils.isNotEmpty(ruler.getIndexOfFilters())) {
				for (String indexFilter : ruler.getIndexOfFilters()) {
					if (StringUtils.isNotBlank(indexFilter) && -1 != trimValue.indexOf(indexFilter)) {
						return new CatcherSubModel(null, null, false, true);
					}
				}
			}

			// equals 过滤文本
			if (CollectionUtils.isNotEmpty(ruler.getEqualsFilters())) {
				for (String eqFilter : ruler.getEqualsFilters()) {
					if (StringUtils.isNotBlank(eqFilter) && StringUtils.equals(trimValue, eqFilter)) {
						return new CatcherSubModel(null, null, false, true);
					}
				}
			}

			// 结束抓取
			if (CollectionUtils.isNotEmpty(ruler.getBreakValues())) {
				for (String breakValue : ruler.getBreakValues()) {
					if (StringUtils.isNotBlank(breakValue) && StringUtils.equals(trimValue, breakValue)) {
						return new CatcherSubModel(null, null, true, false);
					}
				}
			}

			CatcherSubModel model = new CatcherSubModel(trimValue, code, false, false);

			model.setValues(values);
			return model;
		}

		return null;
	}

	private static Integer getHandlerCode(CatchRuler ruler, Html html) {
		Integer handlerCode = null;
		if (null == ruler.getHandlerCode()) {
			// String codeAssertContent =
			// LangUtils.trim(html.xpath(ruler.getHandlerCodeXPath()));
			// if (StringUtils.isBlank(codeAssertContent)
			// || -1 ==
			// codeAssertContent.indexOf(ruler.getIndexOfAssertHandlerContent()))
			// {
			// throw new Error("can't find code handler");
			// }
			// handlerCode = ruler.getIndexOfAssertHandlerCode();
		} else {
			handlerCode = ruler.getHandlerCode();
		}
		return handlerCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see us.codecraft.webmagic.processor.PageProcessor#getSite()
	 */
	@Override
	public Site getSite() {
		return site;
	}

}