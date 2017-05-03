/**
 * copyright@laulyl
 */
package com.luna.utils;

import java.io.ByteArrayOutputStream;
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

	public static void write(String file, String input) {
		try {
			write(file, new IHandler() {

				@Override
				public void handle(Object object) throws Exception {
					((Writer) (object)).write(input);
				}
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

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

	public static void checkOpenFile(File file) throws IOException {
		if (file.exists()) {
			AssertUtils.isTrue(file.isFile());
			AssertUtils.isTrue(file.canRead());
			AssertUtils.isTrue(file.canWrite());
		} else {
			FilePropertyUtils.touchFile(file);
		}
	}

	public static void write(Writer writer, IHandler handlder) throws IOException {
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

	public static InputStream openInputStream(File file) throws FileNotFoundException {
		AssertUtils.isTrue(file.isFile());
		AssertUtils.isTrue(file.canRead());
		return new FileInputStream(file);
	}

	public static byte[] readBytes(String path) {
		try {
			return readBytes(openInputStream(new File(path)));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] readBytes(InputStream in) {
		int buffSize = 1024;
		ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);
		byte[] buffer = new byte[buffSize];
		int size = 0;
		try {
			while ((size = in.read(buffer)) != -1) {
				out.write(buffer, 0, size);
			}
		} catch (IOException e) {
			close(in);
		}
		byte[] content = out.toByteArray();
		return content;
	}

}
