/**
 * COPYRIGHT@LAULYL
 */
package com.luna.utils;

import java.util.Base64;

import org.apache.commons.lang.StringUtils;

/**
 * @author laulyl
 * @date 2017年5月4日 下午3:38:18
 * @description
 */
public class UrlUtils {

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

	public static void main(String[] args) {
		String input = "oxHVdfcNZNZthihV/riYsX/dskfsv+MvSE/OogCvEgOvPcytpGn73hAz/+tzFtMV6aSHUdr70rrzn9/GFMUQlWW4+yg8vtDar+bsjEO27arBP0jFp7HdK6HtSJKdJvY7no+bhYXL4720C8G3SuhN1gYGDRW2Iz0yOVK7NoHXaiQOac/CxPffHLeBNxCO2Cfl3LdsHMvLD6a/XdFs/fdG872sAGwouEPeteq8fKFdyGXpZ45Urwn/VcDAf8KXiApfTp/nzkGsmHgxBamGkQIZ5d/Vh2VSQr1auSZZ0J1xwRylWEOkL9n3Siu/KFPVWOEuAOaD9rX+Sj//+vbEFjaY1w==";
		String encode = Base64.getUrlEncoder().encodeToString(input.getBytes());
		String decode = new String(Base64.getDecoder().decode(encode));
		System.out.println(input);
		System.out.println(encode);
		System.out.println(decode);
		System.out.println(
				Base64.getEncoder().encodeToString("0 union select 1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7".getBytes()));
	}
}
