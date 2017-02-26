/**
 * copyright@laulyl
 */
package com.luna.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * @author lyl 2016-3-12
 * @description
 */
public class DomUtils {

	public static void execute(Document document, String outputStream)
			throws FileNotFoundException, IOException {

		AssertUtils.isTrue(StringUtils.isNotBlank(outputStream));
		execute(document, new FileOutputStream(outputStream));
	}

	public static void execute(Document document, OutputStream outputStream)
			throws IOException {
		AssertUtils.isTrue(null != document);
		AssertUtils.isTrue(null != outputStream);
		XMLOutputter outputter = null;
		try {
			outputter = new XMLOutputter(Format.getPrettyFormat());
			outputter.output(document, outputStream);
		} finally {
			IOUtils.close(outputStream);
		}
	}

	public static Element getDefaultNameSpaceElement(Document document,
			String name) {
		AssertUtils.isTrue(null != document);
		AssertUtils.isTrue(StringUtils.isNotBlank(name));
		Element element = new Element(name);
		element.setNamespace(document.getRootElement().getNamespace());
		return element;
	}

	public static Element createDefaultNameSpaceElement(Element parentElement,
			String name, boolean force) {
		AssertUtils.isTrue(null != parentElement);
		AssertUtils.isTrue(StringUtils.isNotBlank(name));
		AssertUtils.isTrue(null != parentElement.getNamespace());
		Element element = null;
		if (!force) {
			element = parentElement
					.getChild(name, parentElement.getNamespace());
		}
		if (null == element) {
			element = new Element(name);
			element.setNamespace(parentElement.getNamespace());
			parentElement.addContent(element);
		}
		return element;
	}

	public static Element createDefaultNameSpaceElement(Element parentElement,
			String name) {
		return createDefaultNameSpaceElement(parentElement, name, false);
	}

	public static Document getDocument(String in) throws FileNotFoundException,
			JDOMException, IOException {
		return getDocument(new FileInputStream(in));
	}

	public static void evalueText(Element parentElement, Map<String, String> map) {
		if (MapUtils.isNotEmpty(map)) {
			Iterator<Entry<String, String>> iterator = map.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				evalueText(parentElement, entry.getKey(), entry.getValue());
			}
		}
	}

	public static void evalueText(Element parentElement, String tagName,
			String text) {
		if (StringUtils.isNotBlank(text)) {
			Element element = parentElement.getChild(tagName,
					parentElement.getNamespace());
			if (null == element) {
				element = DomUtils.createDefaultNameSpaceElement(parentElement,
						tagName);
				// parentElement.addContent(element);
			}
			element.setText(text);
		}
	}

	public static Document getDocument(InputStream in) throws JDOMException,
			IOException {
		AssertUtils.isTrue(null != in);
		SAXBuilder builder = new SAXBuilder();
		try {
			return builder.build(in);
		} finally {
			IOUtils.close(in);
		}
	}
}
