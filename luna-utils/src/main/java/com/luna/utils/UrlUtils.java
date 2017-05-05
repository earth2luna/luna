/**
 * COPYRIGHT@LAULYL
 */
package com.luna.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luna.utils.classes.KV;

/**
 * @author laulyl
 * @date 2017年5月4日 下午3:38:18
 * @description
 */
public class UrlUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(UrlUtils.class);

	public static String encode(String input) {
		if (StringUtils.isEmpty(input))
			return null;
		return Base64.getUrlEncoder().encodeToString(input.getBytes());
	}

	public static String decode(String input) {
		if (StringUtils.isEmpty(input))
			return null;
		return new String(Base64.getDecoder().decode(input));
	}

	public static boolean download(String urlString, File outputPath) {
		boolean ret = false;
		if (StringUtils.isNotEmpty(urlString)) {
			InputStream is = null;
			OutputStream os = null;
			try {
				FilePropertyUtils.touchFile(outputPath);
				URL url = new URL(urlString);
				URLConnection con = url.openConnection();
				con.setConnectTimeout(5 * 1000);
				is = con.getInputStream();
				byte[] bs = new byte[1024];
				int len = 0;
				os = new FileOutputStream(outputPath);
				// 开始读取
				while ((len = is.read(bs)) != -1) {
					os.write(bs, 0, len);
				}
				os.flush();
				ret = true;
			} catch (Exception e) {
				LOGGER.error("download error:", e);
				outputPath.delete();
			} finally {
				IOUtils.close(os);
				IOUtils.close(is);
			}
		}
		return ret;
	}

	public static boolean storeBase64Image(String base64String, File outputPath) {
		boolean ret = false;
		if (StringUtils.isNotEmpty(base64String)) {
			OutputStream os = null;
			try {
				byte[] b = Base64.getDecoder().decode(base64String);
				for (int i = 0; i < b.length; ++i) {
					if (b[i] < 0) {
						b[i] += 256;
					}
				}
				os = new FileOutputStream(outputPath);
				os.write(b);
				os.flush();
				os.close();
				ret = true;
			} catch (Exception e) {
				LOGGER.error("store base 64 image error:", e);
				outputPath.delete();
			} finally {
				IOUtils.close(os);
			}
		}
		return ret;
	}

	public static KV<String, Boolean> storeImage(String input, String directoryAndName) {
		boolean ret = false;
		input = LangUtils.trim(input);
		String suffix=null;
		if (StringUtils.isNotEmpty(input)) {
			String defaultSuffix = "jpeg";

			if (input.startsWith("http")) {
				suffix = LangUtils.defaultValue(FilePropertyUtils.getSuffix(input), defaultSuffix);
				File outputFile = new File(
						LangUtils.append(directoryAndName, FilePropertyUtils.SPLITOR_SUFFIX, suffix));
				ret = download(input, outputFile);
			} else {
				if (-1 != input.indexOf("background")) {
					input = get("data:image/[a-zA-Z]+;base64,.+(?='|&quot;)", input);
				}
				String base64PrefixString = get("^data:image/[a-zA-Z]+;base64,", input);
				if (StringUtils.isNotBlank(base64PrefixString)) {
					suffix = get("\\b(?<=data:image/)[a-zA-Z]+(?=;base64,)", base64PrefixString);
					suffix= LangUtils.defaultValue(suffix, defaultSuffix);
					String base64String = input.replaceAll(base64PrefixString, "");
					File outputFile = new File(
							LangUtils.append(directoryAndName, FilePropertyUtils.SPLITOR_SUFFIX, suffix));
					ret = storeBase64Image(base64String, outputFile);
				}

			}
		}
		return new KV<String, Boolean>(suffix, ret);
	}

	public static void main(String[] args) {
		String base64 = "background-image: url(&quot;data:image/png;base64,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&quot;); background-size: contain; background-color: rgb(255, 255, 255);";
		// String url =
		// "http://img05.tooopen.com/images/20150531/tooopen_sy_127457023651.jpg";
		storeImage(base64, "C:/Users/Administrator/Desktop/404/8");
		// storeImage(url, "C:/Users/Administrator/Desktop/404/44444444444");
	}

	public static String get(String regex, String input) {
		String ret = null;
		if (StringUtils.isNotEmpty(input)) {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(input);
			if (matcher.find()) {
				ret = matcher.group();
			}
		}
		return ret;
	}

}
