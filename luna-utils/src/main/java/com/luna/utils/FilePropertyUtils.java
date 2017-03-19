/**
 * copyright@lyl
 */
package com.luna.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.luna.utils.constant.Constants;
import com.luna.utils.enm.OSEnum;

/**
 * @author lyl 2016-1-8
 * @description
 */
public class FilePropertyUtils {

	public static final String SPLITOR_SUFFIX = ".";

	public static final char WEB_URL_CHAR_SPLITOR = '/';

	public static final String WEB_URL_SPLITOR = WEB_URL_CHAR_SPLITOR + "";

	public static final String WINDOWS_URL_SPLITOR = "\\";

	public static String filterPathAsLocal(String path) {
		Validate.notNull(path, "the path is null");
		String ret = null;
		if (WEB_URL_CHAR_SPLITOR == File.separatorChar)
			// now is linux system
			ret = WINDOWS_URL_SPLITOR;
		else
			// now is windows system
			ret = WEB_URL_SPLITOR;
		return path.replace(ret, File.separator);
	}

	public static String filterPathAsWeb(String path) {
		Validate.notNull(path, "the path is null");
		return path.replace(WINDOWS_URL_SPLITOR, WEB_URL_SPLITOR);
	}

	/**
	 * get path file name ,not include suffix
	 * 
	 * <pre>
	 * for example: 
	 * in parameter is /export/data/fileName.doc 
	 * return fileName
	 * </pre>
	 */
	public static String getFileName(File origin) {
		Validate.notNull(origin, "can't find the origin file");
		// Validate.isTrue(origin.isFile(), "the origin file is not a file");

		String fileName = origin.getName();
		int suffixSpitorIndex = fileName.lastIndexOf(SPLITOR_SUFFIX);
		String ret = null;
		if (-1 != suffixSpitorIndex) {
			ret = fileName.substring(0, suffixSpitorIndex);
		} else
			ret = fileName;
		return ret;
	}

	/**
	 * get path file name and assign suffix
	 * 
	 * <pre>
	 * for example: 
	 * 	in parameter is /export/data/fileName.doc and jpg 
	 * 	return fileName.jpg
	 * </pre>
	 */
	public static String getConcatFullFileName(File origin, String destSuffix) {
		return new StringBuilder(getFileName(origin)).append(SPLITOR_SUFFIX).append(destSuffix).toString();
	}

	/**
	 * get same path as in parameter file and change file suffix
	 * 
	 * <pre>
	 * for example: in
	 * 	parameter is /export/data/fileName.doc and jpg 
	 * 	return /export/data/fileName.jpg
	 * </pre>
	 */
	public static File getSamePathOfFile(File origin, String destSuffix) {
		Validate.notNull(origin, "can't find the origin file");
		Validate.isTrue(origin.isFile(), "the origin file is not a file");

		String fileAbsolutePath = origin.getAbsolutePath();
		int suffixSpitorIndex = fileAbsolutePath.lastIndexOf(SPLITOR_SUFFIX);
		String ret = null;
		if (-1 != suffixSpitorIndex) {
			if (suffixSpitorIndex != fileAbsolutePath.length() - 1)
				ret = fileAbsolutePath.substring(0, suffixSpitorIndex);
			else
				Validate.isTrue(false, String.format("the path [%s] is invalid", fileAbsolutePath));
		} else {
			if (fileAbsolutePath.endsWith(WEB_URL_SPLITOR) || fileAbsolutePath.endsWith(WINDOWS_URL_SPLITOR))
				ret = new StringBuilder().append(System.currentTimeMillis()).toString();
			else
				ret = new StringBuilder().append(File.separator).append(System.currentTimeMillis()).toString();

		}
		return new File(new StringBuilder(ret).append(SPLITOR_SUFFIX).append(destSuffix).toString());
	}

	public static void touchDirectory(File origin) {
		Validate.notNull(origin, "can't find the origin file");
		if (!origin.exists()) {
			Validate.isTrue(origin.mkdirs());
		}
	}

	public static void touchDirectory(String origin) {
		Validate.notNull(StringUtils.isNotBlank(origin), "can't find the origin file");
		touchDirectory(new File(origin));
	}

	public static void touchFile(File origin) throws IOException {
		Validate.notNull(origin, "can't find the origin file");
		if (!origin.exists()) {
			touchDirectory(origin.getParentFile());
			Validate.isTrue(origin.createNewFile(), "touch file failed");
		}
	}

	public static void touchFile(String origin) throws IOException {
		Validate.notNull(StringUtils.isNotBlank(origin), "can't find the origin file");
		touchFile(new File(origin));
	}

	public static void copyDirectory(File origin, File dest) throws IOException {
		Validate.notNull(origin, "can't find the origin file");
		Validate.isTrue(origin.canRead(),
				String.format("the origin file [%s] is can't read", origin.getAbsolutePath()));
		Validate.notNull(dest, "can't find the dest file");

		if (dest.exists()) {
			if (origin.isDirectory()) {
				Validate.isTrue(dest.isDirectory(),
						String.format("can't copy [%s] to [%s]", origin.getAbsolutePath(), dest.getAbsolutePath()));
				FileUtils.copyDirectory(origin, dest);
			} else {
				Validate.isTrue(origin.isFile(),
						String.format("the origin [%s] is neither directory nor file", origin.getAbsolutePath()));
				if (dest.isDirectory())
					FileUtils.copyFile(origin, new File(dest, origin.getName()));
				else
					FileUtils.copyFile(origin, dest);
			}
		} else {
			if (origin.isDirectory()) {
				FileUtils.copyDirectory(origin, dest);
			} else {
				Validate.isTrue(origin.isFile(),
						String.format("the origin [%s] is neither directory nor file", origin.getAbsolutePath()));
				FileUtils.copyFile(origin, dest);
			}
		}
	}

	public static void copyDirectory(String origin, String dest) throws IOException {
		Validate.isTrue(StringUtils.isNotBlank(origin), "can't find the origin file");
		Validate.isTrue(StringUtils.isNotBlank(dest), "can't find the dest file");
		copyDirectory(new File(origin), new File(dest));
	}

	public static String getRootPath(String file) {
		Validate.isTrue(StringUtils.isNotBlank(file), "can't find the file");
		OSEnum os = OSEnum.getOS();
		switch (os) {
		case WINDOWS:
			String absolutePath = new File(file).getAbsolutePath();
			int index = absolutePath.indexOf(":");
			Validate.isTrue(-1 != index);
			return absolutePath.substring(0, index + 1);
		default:
			return "/";
		}
	}

	public static String replace2WebUrl(String path) {
		if (StringUtils.isBlank(path))
			return Constants.EMPTY_STRING;
		return path.replace(WINDOWS_URL_SPLITOR, WEB_URL_SPLITOR);
	}

	public static String deleteUrlSplitor(String path) {
		return LangUtils.deleteLastSplitor(replace2WebUrl(path), WEB_URL_SPLITOR);
	}

	// 拼接路径，会转换成web URL，会删除最后一个 /
	public static String appendPath(String... paths) {
		return LangUtils.appendFragment(WEB_URL_SPLITOR, paths);
	}

	// 获取Web App 路径
	public static File getWebAppFile() {
		File file = ClassLoaderUtils.getFileLocation("/");
		validateFilePath(file, "classes");
		File parentFile = file.getParentFile();
		validateFilePath(parentFile, "WEB-INF");
		return parentFile.getParentFile();
	}

	public static void validateFilePath(File file, String endWidth) {
		AssertUtils.notNullOfApp(file, "invalid file");
		AssertUtils.isTrueOfApp(file.getAbsolutePath().endsWith(endWidth), "[validate file failed]");
	}
}
