/**
 * COPYRIGHT@LAULYL
 */
package com.luna.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luna.utils.classes.KV;

/**
 * @author laulyl
 * @date 2017年5月7日 上午7:30:11
 * @description
 */
public class DowloadUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(DowloadUtils.class);

	public static boolean downloadUrl(String urlString, File outputPath) {
		boolean ret = false;
		if (StringUtils.isNotEmpty(urlString)) {
			InputStream is = null;
			OutputStream os = null;
			try {
				FilePropertyUtils.touchFile(outputPath);
				URL url = new URL(urlString);
				URLConnection con = url.openConnection();
				con.setConnectTimeout(10 * 1000);
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

	public static boolean storeSvgImage(byte[] svg, File outputPath) {
		boolean ret = false;
		if (null != svg) {
			OutputStream os = null;
			try {
				os = new FileOutputStream(outputPath);
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(svg);
				PNGTranscoder t = new PNGTranscoder();
				TranscoderInput input = new TranscoderInput(byteArrayInputStream);
				TranscoderOutput output = new TranscoderOutput(os);
				t.transcode(input, output);
				os.flush();
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
		String suffix = null;
		if (StringUtils.isNotEmpty(input)) {
			String defaultSuffix = "jpeg";

			if (input.startsWith("http")) {
				suffix = LangUtils.defaultValue(FilePropertyUtils.getSuffix(input), defaultSuffix);
				File outputFile = new File(
						LangUtils.append(directoryAndName, FilePropertyUtils.SPLITOR_SUFFIX, suffix));
				ret = downloadUrl(input, outputFile);
			} else {
				if (-1 != input.indexOf("background")) {
					// 如果有background 包裹，就提取整个base64
					input = RegexUtils.getMatches("data:image/[a-zA-Z+]+;base64,.+(?='|&quot;)", input);
				}
				// 提取base64开头
				String base64PrefixString = RegexUtils.getMatches("^data:image/[a-zA-Z+]+;base64,", input);
				if (StringUtils.isNotBlank(base64PrefixString)) {
					// 获取图片后缀
					suffix = RegexUtils.getMatches("\\b(?<=data:image/)[a-zA-Z+]+(?=;base64,)", base64PrefixString);
					Validate.notNull(suffix);
					// 得到真正的base64
					String base64String = input.replace(base64PrefixString, "");
					if (-1 != suffix.indexOf("svg")) {
						// svg base64
						suffix = defaultSuffix;
						ret = storeSvgImage(Base64.getDecoder().decode(base64String),
								new File(LangUtils.append(directoryAndName, FilePropertyUtils.SPLITOR_SUFFIX, suffix)));
					} else {
						// 二进制 base64
						File outputFile = new File(
								LangUtils.append(directoryAndName, FilePropertyUtils.SPLITOR_SUFFIX, suffix));
						ret = storeBase64Image(base64String, outputFile);
					}

				}

			}
		}
		return new KV<String, Boolean>(suffix, ret);
	}

	public static void main(String[] args) {
		String base64 = "data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTcxIiBoZWlnaHQ9IjE4MCIgdmlld0JveD0iMCAwIDE3MSAxODAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjwhLS0KU291cmNlIFVSTDogaG9sZGVyLmpzLzEwMCV4MTgwCkNyZWF0ZWQgd2l0aCBIb2xkZXIuanMgMi42LjAuCkxlYXJuIG1vcmUgYXQgaHR0cDovL2hvbGRlcmpzLmNvbQooYykgMjAxMi0yMDE1IEl2YW4gTWFsb3BpbnNreSAtIGh0dHA6Ly9pbXNreS5jbwotLT48ZGVmcz48c3R5bGUgdHlwZT0idGV4dC9jc3MiPjwhW0NEQVRBWyNob2xkZXJfMTViZGI0ODM4ODUgdGV4dCB7IGZpbGw6I0FBQUFBQTtmb250LXdlaWdodDpib2xkO2ZvbnQtZmFtaWx5OkFyaWFsLCBIZWx2ZXRpY2EsIE9wZW4gU2Fucywgc2Fucy1zZXJpZiwgbW9ub3NwYWNlO2ZvbnQtc2l6ZToxMHB0IH0gXV0+PC9zdHlsZT48L2RlZnM+PGcgaWQ9ImhvbGRlcl8xNWJkYjQ4Mzg4NSI+PHJlY3Qgd2lkdGg9IjE3MSIgaGVpZ2h0PSIxODAiIGZpbGw9IiNFRUVFRUUiLz48Zz48dGV4dCB4PSI1OS41NjI1IiB5PSI5NC41NTYyNSI+MTcxeDE4MDwvdGV4dD48L2c+PC9nPjwvc3ZnPg==";
		// String url =
		// "http://img05.tooopen.com/images/20150531/tooopen_sy_127457023651.jpg";
		storeImage(base64, "C:/Users/Administrator/Desktop/404/8999999");
		// storeImage(url, "C:/Users/Administrator/Desktop/404/44444444444");
	}

}
