/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.data.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import com.luna.service.dto.PageHeaderSayVo;
import com.luna.utils.LangUtils;
import com.luna.utils.node.INode;

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
	public static final int HOME_SEARCH_ITEMS_PAGE_SIZE = 20;

	// 页面相关文章查询每页条数
	public static final int RELATIVE_SEARCH_ITEMS_PAGE_SIZE = 5;

	// header info
	public static final String HEADER_TITLE = "title";
	public static final String HEADER_KEYWORDS = "keywords";
	public static final String HEADER_DESCRIPTION = "description";
	public static final String HEADER_AUTHOR = "author";
	public static final int HEADER_DESCRIPTION_LENGHT = 100;

	// 寄语
	public static final String PAGE_HEADER_SAY_KEY = "PAGE_HEADER_SAY";

	private static final List<PageHeaderSayVo> PAGE_HEADER_SAY = new ArrayList<PageHeaderSayVo>() {

		private static final long serialVersionUID = -634655138563100362L;
		{
			add(new PageHeaderSayVo("生活不止眼前的苟且", "生活之外，不要忘了最初的梦想；不过于执着于物质，多读书，也许会明白自己真正的远方在哪里。"));
			add(new PageHeaderSayVo("生如夏花", "我听见回声，来自山谷和心间；生如夏花之绚烂，死如秋叶之静美；还在乎拥有什么。"));
			add(new PageHeaderSayVo("生如夏花", "惊鸿一般短暂，像夏花一样绚烂；我从远方赶来，赴你一面之约；我为你来看我不顾一切；我将熄灭永不能再回来。"));
			add(new PageHeaderSayVo("青春", "人生有一首诗,当我们拥有它的时候,往往并没有读懂它;而当我们能够读懂它的时候,它却早已远去。这首诗的名字,就叫青春。"));
			add(new PageHeaderSayVo("青春", "年轻的时候，每件事情你都想明白，因为老觉得，有些事情不明白，就是生活的慌张。后来等老了才发现，那慌张就是青春。你不慌张了，青春就没了。"));
			add(new PageHeaderSayVo("青春", "青春是用来奋斗的，不是用来挥霍的。只有这样，当有一天我们回首来时路，和那个站在最绚烂的骄阳下曾经青春的自己告别的时候，我们才可能说：谢谢你。"));
			add(new PageHeaderSayVo("青春", "如果你浪费了自己的年龄，那是挺可悲的。因为你的青春只能持续一点儿时间——很短的一点儿时间。 "));
			add(new PageHeaderSayVo("青春", "青春是没有经验和任性的。 "));
			add(new PageHeaderSayVo("青春", "生活赋予我们一种巨大的和无限高贵的礼品，这就是青春：充满着力量，充满着期待志愿，充满着求知和斗争的志向，充满着希望信心和青春。 "));
			add(new PageHeaderSayVo("青春", "你不能同时又有青春又有关于青春的知识。因为青春忙于生活，而顾不得去了解；而知识为着要生活，而忙于自我寻求。"));
			add(new PageHeaderSayVo("青春", "一个人只要他有纯洁的心灵，无愁无恨，他的青春时期，定可因此而延长。 "));
			add(new PageHeaderSayVo("青春", "青春是一种持续的陶醉，是理智的狂热。 "));
			add(new PageHeaderSayVo("青春", "谁虚度年华，青春就要裉色，生命就会抛弃他们。 "));
			add(new PageHeaderSayVo("青春", "超乎一切之上的一件事，就是保持青春朝气。 "));
		}
	};

	public static PageHeaderSayVo getPageHeaderSayVo() {
		return PAGE_HEADER_SAY.get(RandomUtils.nextInt(PAGE_HEADER_SAY.size()));
	}

	public static final String APPLICATION_CATEGORY_LIST_KEY = "category_list";

	public static List<INode> CATEGORY_LIST;

	public static String getTitle(String title) {
		String defaultTitle = "Apoollo - 阿波罗高质量的触动！";
		String ret = null;
		if (StringUtils.isNotBlank(title)) {
			ret = LangUtils.append(title, " - ", defaultTitle);
		} else {
			ret = defaultTitle;
		}
		return ret;
	}

	public static String getKeywords(String keywords) {
		String defaultKeywords = "阿波罗,Apoollo,专业文章,计算机技术,编程,旅行,情怀,高质量,青春,教程，知识点";
		String ret = null;
		if (StringUtils.isNotBlank(keywords)) {
			ret = LangUtils.append(keywords, ",", defaultKeywords);
		} else {
			ret = defaultKeywords;
		}
		return ret;
	}

	public static String getDescription(String description) {
		String defaultDescription = "Apoollo ( www.apoollo.com ) 阿波罗只提供高质量的文章，涉及计算机编程、旅行、情怀、青春等各个主题领域，旨在帮助到您，对您有所触动！";
		return LangUtils.defaultValue(description, defaultDescription);
	}

	public static String getAuthor() {
		return "作者：刘玉龙 &lt;673348317@qq.com&gt;";
	}

}
