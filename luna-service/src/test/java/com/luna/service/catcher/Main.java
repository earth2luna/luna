/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.catcher;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * @author laulyl
 * @date 2017年4月11日 下午10:30:00
 * @description
 */
public class Main implements PageProcessor {

	private Site site = Site.me().setCycleRetryTimes(5).setRetryTimes(5).setSleepTime(500).setTimeOut(3 * 60 * 1000)
			.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0")
			.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
			.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3").setCharset("UTF-8");

	@Override
	public void process(Page page) {
		List<String> answers = page.getHtml().xpath("//article[@class='hentry']/div[@class='asset-content']/*").all();
		for (String answer : answers) {
//			String vote = new Html(answer).xpath("//p/tidyText()").toString();
//			String vote = new Html(answer).xpath("//blockquote/pre/code/tidyText()").toString();
//			String vote = new Html(answer).xpath("//blockquote/pre/code/text()").toString();
//			String vote = new Html(answer).xpath("//blockquote/pre/code/allText()").toString();
//			String vote = new Html(answer).xpath("//blockquote/pre/code/html()").toString();
//			String vote = new Html(answer).xpath("//blockquote/html()").toString();
			String vote = new Html(answer).xpath("//blockquote/p/br").toString();
//			System.out.println(vote);
			System.out.println("*********************************");
			System.out.println(vote);
		}
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
//		Spider.create(new Main()).addUrl("http://www.ruanyifeng.com/blog/2016/08/http.html").thread(5)
//		.run();
//		Spider.create(new Main()).addUrl("http://www.ruanyifeng.com/blog/2017/04/css_in_js.html").thread(5)
//		.run();
//		Spider.create(new Main()).addUrl("http://www.ruanyifeng.com/blog/2014/02/ssl_tls.html").thread(5)
//		.run();
//		Spider.create(new Main()).addUrl("http://www.ruanyifeng.com/blog/2016/08/migrate-from-http-to-https.html").thread(5)
//		.run();
		Spider.create(new Main()).addUrl("http://www.ruanyifeng.com/blog/2011/09/restful.html").thread(5)
				.run();
	}
}
