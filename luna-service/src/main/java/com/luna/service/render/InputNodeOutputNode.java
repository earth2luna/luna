/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.render;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import com.luna.dao.po.ResourcesContentMark;
import com.luna.utils.LangUtils;
import com.luna.utils.constant.Constants;
import com.luna.utils.infce.IInputOutput;
import com.luna.utils.node.INode;

/**
 * @author laulyl
 * @date 2017年3月1日 下午3:27:07
 * @description
 */
public class InputNodeOutputNode implements IInputOutput<INode, INode> {

	private static final List<String> NO_ENDING_TAG_NAME = new ArrayList<String>();

	static {
		NO_ENDING_TAG_NAME.add("hr");
		NO_ENDING_TAG_NAME.add("br");
		NO_ENDING_TAG_NAME.add("input");
		NO_ENDING_TAG_NAME.add("image");
	}

	private Map<Long, List<ResourcesContentMark>> resourcesContentMarkMap;

	/**
	 * @param resourcesContentMarkMap
	 */
	public InputNodeOutputNode(Map<Long, List<ResourcesContentMark>> resourcesContentMarkMap) {
		super();
		this.resourcesContentMarkMap = resourcesContentMarkMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.utils.infce.IInputOutput#get(java.lang.Object)
	 */
	public INode get(INode input) {
		if (MapUtils.isNotEmpty(resourcesContentMarkMap)) {
			ResourcesCasecadeNode casecadeNode = (ResourcesCasecadeNode) input;
			List<ResourcesContentMark> contentMarks = resourcesContentMarkMap.get(casecadeNode.getId());
			if (CollectionUtils.isNotEmpty(contentMarks)) {
				Iterator<ResourcesContentMark> iterator = contentMarks.iterator();
				ResourcesContentMark contentMark = iterator.next();
				change(casecadeNode, contentMark);
			}
			change(casecadeNode);
		}
		return input;
	}

	public void change(ResourcesCasecadeNode casecadeNode) {
		// TODO 等完成
		switch (casecadeNode.getResourcesContentHandlerCode()) {
		case 1:
			casecadeNode.setResourcesContent(getFormatHtmlFragment(casecadeNode.getResourcesContent()));
			break;
		}

	}

	public void change(ResourcesCasecadeNode casecadeNode, ResourcesContentMark contentMark) {
		// TODO 等完成
		// casecadeNode.setResourcesContent(contentMark.getContent());
		// casecadeNode.setResourcesContentTitle(contentMark.getContent());

	}

	// private static String getFormatHtml(String content) {
	// String docTypeRegex = "&lt;\\s*!\\s*DOCTYPE\\s*[Hh][Tt][Mm][Ll].*&lt;";
	// String htmlTagRegex =
	// "&lt;\\s*[a-zA-Z]+(\\s+[a-zA-Z]+=\\s*[\"'].*[\"'])+\\s*&lt;";
	// String input = "&lt; div class=\" container \" type=\"dafasdf\"&lt;";
	// Pattern pattern = Pattern.compile(htmlTagRegex);
	// Matcher matcher = pattern.matcher(input);
	// while (matcher.find()) {
	// System.out.println(matcher.group());
	// }
	// return null;
	// }

	public static void main(String[] args) throws IOException {
		String result = new InputNodeOutputNode(null).getFormatHtmlFragment("<input type=\"submit\" value=\"Submit\">");
		System.out.println(result);
	}

	public String getFormatHtmlFragment(String bodyHtml) {
		Document document = Jsoup.parse(bodyHtml);
		return deepNodes(document.childNodes(), 0).getBuilder().toString();
	}

	private DeepNodeStatus deepNodes(List<Node> elements, int index) {
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

	class DeepNodeStatus {
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

	private String getSpace(int index) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < index; i++) {
			builder.append(Constants.DOUBLE_SPACE_STRING);
		}
		return builder.toString();
	}

	private String getGt() {
		return getTag("nt", gt());
	}

	private String getTagNameEndFragment(String tagName) {
		return getTag("nt", LangUtils.append(lt(), "/", trim(tagName), gt()));
	}

	private String getTagNameFragment(String tagName) {
		return getTag("nt", LangUtils.append(lt(), trim(tagName)));
	}

	private String getAttributeNameFragment(String text) {
		return getTag("na", LangUtils.append(trim(text), "="));
	}

	private String getAttributeValueFragment(String text) {
		return getTag("s", LangUtils.append("\"", trim(text), "\""));
	}

	private String getTag(String className, String text) {
		return LangUtils.append(getPreClassSpanTag(className), text, getEndSpanTag());
	}

	private String getPreClassSpanTag(String className) {
		return LangUtils.append("<span class=\"", className, "\">");
	}

	private String lt() {
		return "&lt;";
	}

	private String gt() {
		return "&gt;";
	}

	private String getEndSpanTag() {
		return getEndTag("span");
	}

	private String getEndTag(String tagName) {
		return LangUtils.append("</", tagName, ">");
	}

	private String trim(String input) {
		String ret = "";
		if (null != input)
			ret = input.trim();
		return ret;
	}

}
