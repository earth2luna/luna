package com.luna.service.catcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luna.dao.mapper.IResourcesContentMapper;
import com.luna.dao.mapper.IResourcesMapper;
import com.luna.dao.po.Resources;
import com.luna.service.data.utils.ContentUtils;
import com.luna.service.dto.CatcherContent;
import com.luna.service.enumer.content.HandlerMethodEnum;
import com.luna.service.enumer.resource.CategoryEnum;
import com.luna.service.enumer.resource.CreatorEnum;
import com.luna.service.enumer.resource.StatusEnum;
import com.luna.service.enumer.service.HtmlMarcherEnum;
import com.luna.utils.DateUtils;
import com.luna.utils.DowloadUtils;
import com.luna.utils.FilePropertyUtils;
import com.luna.utils.LangUtils;
import com.luna.utils.classes.KV;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

public class CatcherProcessor implements PageProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(CatcherProcessor.class);

	private Site site = Site.me().setRetryTimes(3).setSleepTime(5000);

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
		resources.setCategoryId(CategoryEnum.TECHNOLOGY.getCode());
		resources.setCreateTime(new Date());
		resources.setCreatorId(CreatorEnum.LAULYL.getCode());
		resources.setStatus(LangUtils.intValueOfNumber(StatusEnum.INIT.getCode()));
		resources.setSourceSiteLink(catcherModel.getCatcherWebUrl());
		resources.setSourceSiteName(catcherModel.getCatcherWebName());
		resources.setWebsiteCode(catcherModel.getCatcherWebsiteCode());

		CatcherSubModel resourceTitleKv = handler(catcherModel.getResourceTitleCatchRulers(), page.getHtml());
		if (null != resourceTitleKv) {
			resources.setTitle(resourceTitleKv.getValue());
		}
		CatcherSubModel resourceDateKv = handler(catcherModel.getResourceDateCatchRulers(), page.getHtml());
		if (null != resourceDateKv) {
			resources.setSourceDate(DateUtils.parse(resourceDateKv.getValue(), catcherModel.getResourceDateFormat()));
		}
		CatcherSubModel resourceAuthorKv = handler(catcherModel.getResourceAuthorCatchRulers(), page.getHtml());
		if (null != resourceAuthorKv) {
			resources.setSourceAuthor(resourceAuthorKv.getValue());
		}

		List<CatcherContent> rcs = new ArrayList<CatcherContent>();
		List<CatcherIteratorRuler> xpaths = catcherModel.getIteratorRulers();
		Iterator<CatcherIteratorRuler> iterator = xpaths.iterator();

		Long currentLevel = null;// 当前级别
		Long oneLevelId = null;// 一级别id
		Long twoLevelId = null;// 二级别id
		Long levelId = 0L;// 当前levelId
		boolean ifSkip = false;// 是否终止
		while (iterator.hasNext()) {
			CatcherIteratorRuler iteratorRuler = iterator.next();
			if (iteratorRuler.isIfMark()) {
				// 获取一级内容标题
				CatcherSubModel oneLevelContentTitleKv = handler(iteratorRuler.getOneLevelContentTitleCatchRulers(),
						page.getHtml());
				if (null != oneLevelContentTitleKv) {
					CatcherContent rc = new CatcherContent();
					rc.setTitle(oneLevelContentTitleKv.getValue());
					rc.setHandlerCode(oneLevelContentTitleKv.getHandlerCode());
					rcs.add(rc);
					oneLevelId = ++levelId;
					rc.setLevelId(oneLevelId);
					currentLevel = 1L;
				}
			} else {
				List<String> transit = page.getHtml().xpath(iteratorRuler.getContentXPath()).all();
				for (int i = 0; i < transit.size(); i++) {
					String content = transit.get(i);
					Html tempHtml = Html.create(content);
					CatcherContent rc = new CatcherContent();
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
						rc.setTitle(oneLevelContentTitleKv.getValue());
						rc.setHandlerCode(oneLevelContentTitleKv.getHandlerCode());
						rcs.add(rc);
						oneLevelId = ++levelId;
						rc.setLevelId(oneLevelId);
						currentLevel = 1L;
						continue;
					}

					if (null == oneLevelId) {
						ifSkip = true;
						LOGGER.error("[one level key is null]");
						break;
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
						rc.setTitle(twoLevelContentTitleKv.getValue());
						rc.setHandlerCode(twoLevelContentTitleKv.getHandlerCode());
						rc.setParentLevelId(oneLevelId);
						rcs.add(rc);
						twoLevelId = ++levelId;
						rc.setLevelId(twoLevelId);
						currentLevel = 2L;
						continue;
					}
					// 获取当前内容属于哪个标题下
					Long currentLevelId = getCurrentParentId(currentLevel, oneLevelId, twoLevelId);
					if (null == currentLevelId) {
						ifSkip = true;
						LOGGER.error(LangUtils.append("[current level key is null] :", levelId, " currentLevel:",
								currentLevel, " oneLevelId:", oneLevelId, " twoLevelId:", twoLevelId));
						break;
					}

					// 获取路径
					CatcherSubModel contentPathKv = handler(iteratorRuler.getContentPathCatchRulers(), tempHtml);
					if (null != contentPathKv) {
						if (contentPathKv.isIfBreak()) {
							break;
						}

						if (contentPathKv.isIfFilter()) {
							continue;
						}
						// web site code + category code + system time
						String endPath = FilePropertyUtils.appendPath(catcherModel.getCatcherWebsiteCode().toString(),
								catcherModel.getResourceCategoryCode().toString(),
								LangUtils.toString(System.currentTimeMillis()));
						// attachment path + web site code + category
						// code+system time
						String outputPath = FilePropertyUtils.appendPath(catcherModel.getAttachementPath(), endPath);
						KV<String, Boolean> storeValue = DowloadUtils.storeImage(contentPathKv.getValue(), outputPath);
						if (storeValue.getV()) {
							rc.setPath(endPath + FilePropertyUtils.SPLITOR_SUFFIX + storeValue.getK());
							rc.setHandlerCode(contentPathKv.getHandlerCode());
							rc.setParentLevelId(currentLevelId);
							rcs.add(rc);
						}
						continue;
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
						rc.setContent(contentKv.getValue());
						rc.setHandlerCode(contentKv.getHandlerCode());
						rc.setParentLevelId(currentLevelId);
						rcs.add(rc);
						continue;
					}

				}
			}
		}
		if (!ifSkip) {
			resourcesMapper.insert(resources);
			ContentUtils.insertCatchers(contentMapper, rcs, resources.getId());
		}
		page.setSkip(ifSkip);
	}

	private Long getCurrentParentId(Long currentLevel, Long oneLevelId, Long twoLevelId) {
		// Long ret = null;
		// if (LangUtils.equals(1, currentLevel)) {
		// ret = oneLevelId;
		// } else if (LangUtils.equals(2, currentLevel)) {
		// ret = twoLevelId;
		// }
		// return ret;
		return oneLevelId;
	}

	private static CatcherSubModel handler(List<CatchRuler> rulers, Html relative) {
		if (CollectionUtils.isEmpty(rulers))
			return null;
		CatcherSubModel model = null;
		Iterator<CatchRuler> iterator = rulers.iterator();
		while (iterator.hasNext()) {
			CatchRuler ruler = iterator.next();
			Html html = relative;
			if (LangUtils.isBlank(ruler.getGetXPath())) {
				continue;
			}
			String value = null;
			Selectable originValue = null;

			// 探测拦截
			if (LangUtils.isNotBlank(ruler.getTryXPath())) {
				value = LangUtils.trim(html.xpath(ruler.getTryXPath()));

				if (LangUtils.isBlank(value)) {
					continue;
				}
			}

			// 真正获取
			HtmlMarcherEnum marcherEnum = HtmlMarcherEnum.get(ruler.getReplaceCode());
			if (null == marcherEnum) {
				originValue = html.xpath(ruler.getGetXPath());
				value = LangUtils.trim(originValue);
			} else {
				String replacement = LangUtils.defaultValue(ruler.getReplacement(), "");

				originValue = html.xpath(ruler.getGetXPath()).replace(marcherEnum.getRegex(ruler.getReplaceTagNames()),
						replacement);

				value = LangUtils.trim(originValue);
			}

			// 过滤空串
			if (LangUtils.isBlank(value)) {
				continue;
			}

			// 过滤文本
			if (StringUtils.isNotEmpty(ruler.getIndexOfFilter())) {
				if (-1 == value.indexOf(ruler.getIndexOfFilter())) {
					continue;
				}
			}

			// 结束抓取
			if (StringUtils.isNotEmpty(ruler.getBreakValue())) {
				if (!StringUtils.equals(value, ruler.getBreakValue())) {
					continue;
				}
			}

			// pre 不去除空格

			String outputValue = HandlerMethodEnum.PRE == HandlerMethodEnum.get(ruler.getHandlerCode())
					? LangUtils.toString(originValue) : value;

			model = new CatcherSubModel(outputValue, ruler.getHandlerCode(),
					StringUtils.equals(value, ruler.getBreakValue()),
					StringUtils.isNotEmpty(ruler.getIndexOfFilter()) && -1 != value.indexOf(ruler.getIndexOfFilter()));
			break;
		}
		return model;
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