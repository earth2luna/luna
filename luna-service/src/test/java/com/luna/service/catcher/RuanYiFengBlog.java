package com.luna.service.catcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.luna.dao.mapper.IResourcesContentMapper;
import com.luna.dao.mapper.IResourcesMapper;
import com.luna.dao.po.Resources;
import com.luna.dao.po.ResourcesContent;
import com.luna.service.data.utils.ContentUtils;
import com.luna.service.enumer.resource.CategoryEnum;
import com.luna.service.enumer.resource.CreatorEnum;
import com.luna.service.enumer.resource.StatusEnum;
import com.luna.service.enumer.service.HtmlMarcherEnum;
import com.luna.utils.DateUtils;
import com.luna.utils.LangUtils;
import com.luna.utils.classes.KV;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class RuanYiFengBlog implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(5000);

	private IResourcesMapper resourcesMapper;
	private IResourcesContentMapper contentMapper;
	private CatcherForm catcherForm;

	public RuanYiFengBlog(IResourcesMapper resourcesMapper, IResourcesContentMapper contentMapper,
			CatcherForm catcherForm) {
		super();
		this.resourcesMapper = resourcesMapper;
		this.contentMapper = contentMapper;
		this.catcherForm = catcherForm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see us.codecraft.webmagic.processor.PageProcessor#process(us.codecraft.
	 * webmagic.Page)
	 */
	@Override
	public void process(Page page) {
		// String xpath = "//article[@class='hentry']";

		// String articleTitle = LangUtils.trim(page.getHtml().xpath(xpath +
		// "/h1[@id='page-title']/text()").toString());
		// String articleAuthor = LangUtils
		// .trim(page.getHtml().xpath(xpath +
		// "/div[@class='asset-meta']/p[@class='author']/a/text()").toString());
		// String articlePublishTime = LangUtils.trim(page.getHtml()
		// .xpath(xpath +
		// "/div[@class='asset-meta']/p//abbr[@class='published']/text()").toString());

		// page.putField("articleTitle", articleTitle);
		// page.putField("articleAuthor", articleAuthor);
		// page.putField("articlePublishTime", articlePublishTime);

		Resources resources = new Resources();
		resources.setCategoryId(CategoryEnum.TECHNOLOGY.getCode());
		resources.setCreateTime(new Date());
		resources.setCreatorId(CreatorEnum.LAULYL.getCode());
		resources.setStatus(LangUtils.intValueOfNumber(StatusEnum.INIT.getCode()));
		resources.setSourceSiteLink(catcherForm.getCatcherWebUrl());
		resources.setSourceSiteName(catcherForm.getCatcherWebName());

		KV<String, Integer> resourceTitleKv = handler(catcherForm.getResourceTitleCatchRulers(), page.getHtml());
		if (null != resourceTitleKv) {
			resources.setTitle(resourceTitleKv.getK());
		}
		KV<String, Integer> resourceDateKv = handler(catcherForm.getResourceDateCatchRulers(), page.getHtml());
		if (null != resourceDateKv) {
			resources.setSourceDate(DateUtils.parse(resourceDateKv.getK(), catcherForm.getResourceDateFormat()));
		}
		KV<String, Integer> resourceAuthorKv = handler(catcherForm.getResourceDateCatchRulers(), page.getHtml());
		if (null != resourceAuthorKv) {
			resources.setSourceAuthor(resourceAuthorKv.getK());
		}

		List<ResourcesContent> rcs = new ArrayList<>();
		List<String> xpaths = catcherForm.getContentXPaths();
		Iterator<String> iterator = xpaths.iterator();
		while (iterator.hasNext()) {
			// List<String> transit = page.getHtml().xpath(xpath +
			// "/div[@class='asset-content']/*").all();
			List<String> transit = page.getHtml().xpath(iterator.next()).all();
			for (int i = 0; i < transit.size(); i++) {
				String content = transit.get(i);
				Html tempHtml = Html.create(content);

				// String h2Text = tempHtml.xpath("//h2/text()").toString();
				// String h3Text = tempHtml.xpath("//h3/text()").toString();
				//
				// String pText = tempHtml.xpath("//p/text()").toString();
				// String pImage = tempHtml.xpath("//p/img").toString();
				// String pCodeText =
				// tempHtml.xpath("//p/code/text()").toString();
				// String pAText = tempHtml.xpath("//p/a/text()").toString();
				//
				// String pAStrongText =
				// tempHtml.xpath("//p/a/strong/text()").toString();
				// String pStrongText =
				// tempHtml.xpath("//p/strong/text()").toString();
				//
				// String bpCodeText =
				// tempHtml.xpath("//blockquote/pre/code/text()").toString();
				// String bpUlLiText =
				// tempHtml.xpath("//blockquote/ul").toString();
				//
				// String pAllText = tempHtml.xpath("//p/allText()").toString();
				// String bpCodeAllText =
				// tempHtml.xpath("//blockquote/pre/code/allText()").toString();
				// System.out.println(bpCodeAllText);
				//
				// System.out.println(pAllText);
				// 相等内容忽略
				// 包含内容忽略
				// 标签属性删除
				// 标签名称替换
				// 标签名称属性删除(保留文本)
				// 标签删除(同样删除标签内HTML)
				// 原内容
				// if (-1 != content.indexOf("<h3>六、参考链接</h3>")) {
				// System.out.println("====================================");
				// } else if (null != bpUlLiText) {
				// System.out.println(bpUlLiText);
				// } else if (null != bpCodeText) {
				// System.out.println(bpCodeText);
				// } else if (null != pAText || null != pAStrongText || null !=
				// pStrongText || null != pCodeText) {
				// System.out.println(tempHtml.xpath("//p").replace(tagRegex("p",
				// "a", "strong"), "").toString());
				// } else if (null != pImage) {
				// System.out.println(tempHtml.xpath("//p/img/@src"));
				// } else if (null != h3Text) {
				// System.out.println(h3Text);
				// } else if (null != h2Text) {
				// System.out.println(h2Text);
				//
				// } else {
				// System.out.println(pText);
				// }

				ResourcesContent rc = new ResourcesContent();
				KV<String, Integer> contentTitleKv = handler(catcherForm.getContentTitleCatchRulers(), tempHtml);
				if (null != contentTitleKv) {
					rc.setTitle(contentTitleKv.getK());
					rc.setHandlerCode(contentTitleKv.getV());
					rcs.add(rc);
					continue;
				}

				KV<String, Integer> contentPathKv = handler(catcherForm.getContentPathCatchRulers(), tempHtml);
				if (null != contentPathKv) {
					rc.setPath(contentPathKv.getK());
					rc.setHandlerCode(contentPathKv.getV());
					rcs.add(rc);
					continue;
				}

				KV<String, Integer> contentKv = handler(catcherForm.getContentCatchRulers(), tempHtml);
				if (null != contentKv) {
					rc.setContent(contentKv.getK());
					rc.setHandlerCode(contentKv.getV());
					rcs.add(rc);
					continue;
				}

			}
		}
		resourcesMapper.insert(resources);
//		ContentUtils.insert(contentMapper, rcs, resources.getId());
	}

	private static KV<String, Integer> handler(List<CatchRuler> rulers, Html html) {
		KV<String, Integer> kv = null;
		Iterator<CatchRuler> iterator = rulers.iterator();
		while (iterator.hasNext()) {
			CatchRuler ruler = iterator.next();

			if (LangUtils.isBlank(ruler.getGetXPath())) {
				continue;
			}
			String value = null;

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
				value = LangUtils.trim(html.xpath(ruler.getGetXPath()));
			} else {
				String replacement = LangUtils.defaultValue(ruler.getReplacement(), "");
				value = LangUtils.trim(html.xpath(ruler.getGetXPath())
						.replace(marcherEnum.getRegex(ruler.getReplaceTagNames()), replacement));
			}

			if (LangUtils.isBlank(value)) {
				continue;
			}

			kv = new KV<String, Integer>(value, ruler.getHandlerCode());

			break;
		}
		return kv;
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