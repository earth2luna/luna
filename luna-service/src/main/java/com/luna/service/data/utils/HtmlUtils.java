/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.data.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import com.luna.utils.LangUtils;
import com.luna.utils.constant.Constants;

/**
 * @author laulyl
 * @date 2017年3月7日 下午10:49:53
 * @description
 */
public class HtmlUtils {

	private static final List<String> NO_ENDING_TAG_NAME = new ArrayList<String>();

	static {
		NO_ENDING_TAG_NAME.add("hr");
		NO_ENDING_TAG_NAME.add("br");
		NO_ENDING_TAG_NAME.add("input");
		NO_ENDING_TAG_NAME.add("image");
	}

	public static String getFormatHtmlFragment(String bodyHtml) {
		Document document = Jsoup.parseBodyFragment(bodyHtml);
		return deepNodes(document.body().childNodes(), 0).getBuilder().toString();
	}

	public static String getFormatHtml(String bodyHtml) {
		Document document = Jsoup.parse(bodyHtml);
		return deepNodes(document.childNodes(), 0).getBuilder().toString();
	}

	private static DeepNodeStatus deepNodes(List<Node> elements, int index) {
		StringBuilder outer = new StringBuilder();
		DeepNodeStatus outerDeepNodeStatus = new DeepNodeStatus(outer);
		if (CollectionUtils.isEmpty(elements)) {
			return outerDeepNodeStatus;
		}
		Iterator<Node> elementIterator = elements.iterator();
		String text = null;
		String tagName = null;
		boolean isText = false;
		while (elementIterator.hasNext()) {
			Node node = elementIterator.next();
			if (node instanceof Element) {
				Element element = (Element) node;
				text = element.text();
				tagName = element.tagName();
				outer.append(getTagNameFragment(tagName));
				Attributes attributes = node.attributes();
				Iterator<Attribute> attributeIterator = attributes.iterator();
				while (attributeIterator.hasNext()) {
					Attribute attribute = attributeIterator.next();
					outer.append(Constants.SPACE_STRING);
					outer.append(getAttributeNameFragment(attribute.getKey()));
					outer.append(getAttributeValueFragment(attribute.getValue()));
				}
				outer.append(getGt());
				DeepNodeStatus innerDeepNodeStatus = deepNodes(node.childNodes(), (index + 1));
				StringBuilder inner = innerDeepNodeStatus.getBuilder();
				if (0 != inner.length()) {
					if (!innerDeepNodeStatus.isText()) {
						// 如果是Element
						outer.append(Constants.NEW_LINE);
						outer.append(getSpace(index + 1));
						outer.append(inner);
						outer.append(getSpace(index));
					} else {
						// 如果是TextNode
						outer.append(inner);
					}
				}
				if (!NO_ENDING_TAG_NAME.contains(tagName)) {
					// 用ending的标签才打上ending
					outer.append(getTagNameEndFragment(tagName));
				}
				if (!isText) {
					// 如果兄弟节点有文本，则视为都不换行
					outer.append(Constants.NEW_LINE);
				}
				if (elementIterator.hasNext() && !isText) {
					// 如果还有下一个兄弟节点则tab
					outer.append(getSpace(index));
				}
			} else if (node instanceof TextNode) {
				TextNode textNode = (TextNode) node;
				text = textNode.text();
				outer.append(LangUtils.defaultEmpty(text));
				isText = true;
			}

		}
		outerDeepNodeStatus.setText(isText);
		return outerDeepNodeStatus;
	}

	private static String getSpace(int index) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < index; i++) {
			builder.append(Constants.DOUBLE_SPACE_STRING);
		}
		return builder.toString();
	}

	private static String getGt() {
		return getTag("nt", gt());
	}

	private static String getTagNameEndFragment(String tagName) {
		return getTag("nt", LangUtils.append(lt(), "/", trim(tagName), gt()));
	}

	private static String getTagNameFragment(String tagName) {
		return getTag("nt", LangUtils.append(lt(), trim(tagName)));
	}

	private static String getAttributeNameFragment(String text) {
		return getTag("na", LangUtils.append(trim(text), "="));
	}

	private static String getAttributeValueFragment(String text) {
		return getTag("s", LangUtils.append("\"", trim(text), "\""));
	}

	private static String getTag(String className, String text) {
		return LangUtils.append(getPreClassSpanTag(className), text, getEndSpanTag());
	}

	private static String getPreClassSpanTag(String className) {
		return LangUtils.append("<span class=\"", className, "\">");
	}

	private static String lt() {
		return "&lt;";
	}

	private static String gt() {
		return "&gt;";
	}

	private static String getEndSpanTag() {
		return getEndTag("span");
	}

	private static String getEndTag(String tagName) {
		return LangUtils.append("</", tagName, ">");
	}

	private static String trim(String input) {
		String ret = "";
		if (null != input)
			ret = input.trim();
		return ret;
	}
	
	static class DeepNodeStatus {
		private StringBuilder builder;
		private boolean isText;

		/**
		 * @param builder
		 * @param nodeType
		 */
		public DeepNodeStatus(StringBuilder builder) {
			super();
			this.builder = builder;
		}

		/**
		 * @return the builder
		 */
		public StringBuilder getBuilder() {
			return builder;
		}

		/**
		 * @param builder
		 *            the builder to set
		 */
		public void setBuilder(StringBuilder builder) {
			this.builder = builder;
		}

		/**
		 * @return the isText
		 */
		public boolean isText() {
			return isText;
		}

		/**
		 * @param isText
		 *            the isText to set
		 */
		public void setText(boolean isText) {
			this.isText = isText;
		}

	}
}
