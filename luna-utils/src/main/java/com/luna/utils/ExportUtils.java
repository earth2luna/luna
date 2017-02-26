/**
 * copyright@laulyl
 */
package com.luna.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.luna.utils.enm.CharsetEnum;

/**
 * @author lyl 2016-3-8
 * @description
 */
public class ExportUtils {

	public static String getExportName(String fileName, String userAgent,
			String suffix) {
		Validate.notNull(suffix);
		StringBuilder builder = new StringBuilder("attachment;filename=");
		if (StringUtils.isNotBlank(fileName)
				&& StringUtils.isNotBlank(userAgent)) {
			try {
				if (StringUtils.isNotBlank(userAgent)
						&& -1 != userAgent.toUpperCase().indexOf("MSIE")) {
					builder.append(URLEncoder.encode(fileName,
							CharsetEnum.UTF8.getCharsetName()));
				} else {
					builder.append(new String(fileName
							.getBytes(CharsetEnum.UTF8.getCharsetName()),
							CharsetEnum.ISO88591.getCharsetName()));
				}
			} catch (UnsupportedEncodingException e) {
				builder.append("export");
			}
		} else {
			builder.append("export");
		}
		return builder.append(".").append(suffix).toString();
	}
}
