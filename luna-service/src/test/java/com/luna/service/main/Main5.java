/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.main;

import com.luna.utils.DateUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author laulyl
 * @date 2017年5月21日 下午3:31:54
 * @description
 */
public class Main5 implements PageProcessor {

	private Site site = Site.me().setCycleRetryTimes(5).setRetryTimes(5).setSleepTime(500).setTimeOut(3 * 60 * 1000)
			.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0")
			.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
			.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3").setCharset("UTF-8");

	@Override
	public void process(Page page) {

		System.out.println("开始");
		System.out.println(page.getHtml().xpath(
				"//h1[@class='gh-header-title']/span/allText()"));
		System.out.println(page.getHtml().xpath(
				"//div[@class='timeline-comment']/div[@class='timeline-comment-header']/h3[@class='timeline-comment-header-text']/strong/a/allText()"));
		String dateContent=page.getHtml().xpath(
				"//div[@class='timeline-comment']/div[@class='timeline-comment-header']/h3[@class='timeline-comment-header-text']/a/relative-time/@datetime").toString();
		String date=DateUtils.getDateFormat(DateUtils.parse(dateContent, "yyyy-MM-dd'T'HH:mm:ss'Z'"),DateUtils.DATE_PATTERN_2);
		
		System.out.println(date);
		System.out.println(page.getHtml().xpath("//div[@class='js-discussion']/div[@class='timeline-comment-wrapper'][1]/div[@class='comment']/div[@class='edit-comment-hide']/table/tbody/tr/td[@class='d-block']/*").all());
		System.out.println("结束");
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new Main5()).addUrl("https://github.com/xufei/blog/issues/21").thread(5).run();
	}
}
