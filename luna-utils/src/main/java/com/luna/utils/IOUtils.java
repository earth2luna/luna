/**
 * copyright@laulyl
 */
package com.luna.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import com.luna.utils.infce.IHandler;

/**
 * @author lyl 2016-3-8
 * @description
 */
public class IOUtils {

	public static void write(String file, IHandler handlder) throws IOException {
		Writer writer = null;
		try {
			writer = openWriter(new File(file));
			write(writer, handlder);
		} finally {
			close(writer);
		}
	}

	public static Writer openWriter(File file) throws IOException {
		checkOpenFile(file);
		return new FileWriter(file);
	}

	public static void checkOpenFile(File file) {
		if (file.exists()) {
			AssertUtils.isTrue(file.isFile());
			AssertUtils.isTrue(file.canRead());
		} else {
			File parent = file.getParentFile();
			if (parent.exists()) {
				AssertUtils.isTrue(parent.isDirectory());
			} else {
				AssertUtils.isTrue(parent.mkdirs());
			}
		}
	}

	public static void write(Writer writer, IHandler handlder)
			throws IOException {
		try {
			handlder.handle(writer);
			writer.flush();
		} catch (Exception e) {
			throw new IOException(e);
		} finally {
			close(writer);
		}
	}

	public static void close(Closeable closeable) {
		if (null != closeable) {
			try {
				closeable.close();
			} catch (IOException e) {
			}
		}
	}

	public static InputStream openInputStream(File file)
			throws FileNotFoundException {
		AssertUtils.isTrue(file.isFile());
		AssertUtils.isTrue(file.canRead());
		return new FileInputStream(file);
	}

}
