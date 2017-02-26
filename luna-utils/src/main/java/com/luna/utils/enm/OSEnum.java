/**
 * copyright@lyl
 *solomon-preview Maven Webapp.com.solomon.preview.utils.OSEnum.java.java
 */
package com.luna.utils.enm;

import org.apache.commons.lang.Validate;

/**
 * @author lyl 2016-1-7
 * @description
 */
public enum OSEnum {

	WINDOWS("0", "windows"),

	LINUX_SERIES("1", "linux-series"),

	AIX_SERIES("2", "aix-series"),

	OTHER("-1", "other");

	private String osName;
	private String osCode;

	private OSEnum(String osCode, String osName) {
		this.osName = osName;
		this.osCode = osCode;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getOsCode() {
		return osCode;
	}

	public void setOsCode(String osCode) {
		this.osCode = osCode;
	}

	public static OSEnum getOS() {

		String osName = System.getProperty("os.name");
		Validate.notNull(osName, "os.name not found");

		osName = osName.toLowerCase();
		OSEnum os = null;
		if (osName.indexOf("windows") != -1)
			os = OSEnum.WINDOWS;
		else if (osName.indexOf("linux") != -1
				|| osName.indexOf("sun os") != -1
				|| osName.indexOf("sunos") != -1
				|| osName.indexOf("solaris") != -1
				|| osName.indexOf("mpe/ix") != -1
				|| osName.indexOf("freebsd") != -1
				|| osName.indexOf("irix") != -1
				|| osName.indexOf("digital unix") != -1
				|| osName.indexOf("unix") != -1
				|| osName.indexOf("mac os x") != -1)
			os = OSEnum.LINUX_SERIES;
		else if (osName.indexOf("hp-ux") != -1 || osName.indexOf("aix") != -1)
			os = OSEnum.AIX_SERIES;
		else
			os = OSEnum.OTHER;
		return os;
	}

}
