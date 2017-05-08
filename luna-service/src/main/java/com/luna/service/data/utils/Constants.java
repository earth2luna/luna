/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.data.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;

import com.luna.service.dto.PageHeaderSayVo;

/**
 * @author laulyl
 * @date 2017年5月1日 上午10:50:46
 * @description
 */
public class Constants {

	// 查询字符串最大字数
	public static final int QUERY_STRING_MAX_LENGTH = 35;

	// SORL查询存储概要最大字数
	public static final int SOLR_RESULT_SUMMARY_MAX_LENGTH = 220;

	// SORL存储内容最大字数
	public static final int SOLR_STORE_CONTENT_MAX_LENGTH = 5000;

	// 首页查询每页条数
	public static final int HOME_SEARCH_ITEMS_PAGE_SIZE = 10;

	public static final String PAGE_HEADER_SAY_KEY = "PAGE_HEADER_SAY";

	private static final List<PageHeaderSayVo> PAGE_HEADER_SAY = new ArrayList<PageHeaderSayVo>() {

		private static final long serialVersionUID = -634655138563100362L;
		{
			add(new PageHeaderSayVo("生活不止眼前的苟且", "生活之外，不要忘了最初的梦想；不过于执着于物质，多读书，也许会明白自己真正的远方在哪里。——高晓松"));
			add(new PageHeaderSayVo("生如夏花", "我听见回声，来自山谷和心间；生如夏花之绚烂，死如秋叶之静美；还在乎拥有什么。——泰戈尔"));
			add(new PageHeaderSayVo("生如夏花", "惊鸿一般短暂，像夏花一样绚烂；我从远方赶来，赴你一面之约；我为你来看我不顾一切；我将熄灭永不能再回来。——朴树"));
			add(new PageHeaderSayVo("青春", "人生有一首诗,当我们拥有它的时候,往往并没有读懂它;而当我们能够读懂它的时候,它却早已远去。这首诗的名字,就叫青春。——董卿"));
			add(new PageHeaderSayVo("青春", "年轻的时候，每件事情你都想明白，因为老觉得，有些事情不明白，就是生活的慌张。后来等老了才发现，那慌张就是青春。你不慌张了，青春就没了。——高晓松"));
			add(new PageHeaderSayVo("青春", "青春是用来奋斗的，不是用来挥霍的。只有这样，当有一天我们回首来时路，和那个站在最绚烂的骄阳下曾经青春的自己告别的时候，我们才可能说：谢谢你。——董卿"));
		}
	};

	public static PageHeaderSayVo getPageHeaderSayVo() {
		return PAGE_HEADER_SAY.get(RandomUtils.nextInt(PAGE_HEADER_SAY.size()));
	}

}
