/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.catcher;

import com.luna.service.enumer.service.HtmlMarcherEnum;

/**
 * @author laulyl
 * @date 2017年5月7日 下午10:45:41
 * @description
 */
public class Main1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String input = "<div class=\"asset-meta\"><p class=\"vcard author\">作者： <a class=\"fn url\" href=\"http://www.ruanyifeng.com\">阮一峰</a></p><p>日期： <a href=\"http://www.ruanyifeng.com/blog/2017/04/\"><abbr class=\"published\" title=\"2017-04-05T00:15:51+08:00\">2017年4月 5日</abbr></a></p></div>";
		String tag = HtmlMarcherEnum.TAG.getRegex("p","div","a");
		System.out.println(tag);
		System.out.println(input.replaceAll(tag, ""));
		System.out.println("==============================");
		String TAG_NAME = HtmlMarcherEnum.TAG_NAME.getRegex("p","div","a");
		System.out.println(TAG_NAME);
		System.out.println(input.replaceAll(TAG_NAME, ""));
		System.out.println("==============================");
		String TAG_ATTRIBUTE = HtmlMarcherEnum.TAG_ATTRIBUTE.getRegex("p","div","a");
		System.out.println(TAG_ATTRIBUTE);
		System.out.println(input.replaceAll(TAG_ATTRIBUTE, ""));
		System.out.println("==============================");

	}

}
