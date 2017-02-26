/**
 * copyright@lyl
 *solomon-preview Maven Webapp.com.solomon.preview.utils.ProcessInvokeUtils.java.java
 */
package com.luna.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.luna.utils.enm.OSEnum;

/**
 * @author lyl 2016-1-8
 * @description
 */
public class ProcessUtils {

	public static List<String> performCommand(String command) throws IOException {
		Process proc = null;
		InputStream in = null;
		OutputStream out = null;
		InputStream err = null;
		BufferedReader inr = null;
		List<String> lines = new ArrayList<String>(20);
		try {
			proc = Runtime.getRuntime().exec(command);
			in = proc.getInputStream();
			out = proc.getOutputStream();
			err = proc.getErrorStream();
			inr = new BufferedReader(new InputStreamReader(in));
			String line = inr.readLine();
			while (line != null) {
				// line = line.toLowerCase().trim();
				lines.add(line);
				line = inr.readLine();
			}
			proc.waitFor();
			if (proc.exitValue() != 0) {
				throw new IOException(
						"Command line returned OS error code '" + proc.exitValue() + "' for command " + command);
			}

		} catch (InterruptedException ex) {
			throw new IOException(
					"Command line threw an InterruptedException '" + ex.getMessage() + "' for command " + command);
		} finally {
			IOUtils.close(in);
			IOUtils.close(out);
			IOUtils.close(err);
			IOUtils.close(inr);
			if (proc != null) {
				proc.destroy();
			}
		}
		return lines;
	}

	public static List<String> switchDirCommand(String to) {
		File file = new File(to);
		Validate.isTrue(file.exists());
		String ret = file.getAbsolutePath();
		if (file.isFile())
			ret = file.getParent();
		List<String> commands = new ArrayList<String>();
		OSEnum os = OSEnum.getOS();
		switch (os) {
		case WINDOWS:
			commands.add(FilePropertyUtils.getRootPath(to));
			commands.add(new StringBuilder("cd ").append(ret).toString());
			break;
		default:
			commands.add(new StringBuilder("cd ").append(ret).toString());
			break;
		}
		return commands;
	}

}
