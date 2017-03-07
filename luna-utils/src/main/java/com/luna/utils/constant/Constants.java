/**
 * copyright@laulyl
 */
package com.luna.utils.constant;

import com.luna.utils.LangUtils;

/**
 * @author lyl 2016-3-8
 * @description
 */
public interface Constants {

	// request.getHeader("User-Agent")
	String USER_AGENT = "User-Agent";

	// response.setHeader("Content-Disposition",XXXX.xls);
	String CONTENT_DISPOSITION = "Content-Disposition";

	// response.setContentType("application/msexcel");
	String EXCEL_CONTENT_TYPE = "application/msexcel";

	String POINT_SIGIN = ".";

	String COMMA_STRING = ",";

	String EMPTY_STRING = "";

	String SPACE_STRING = " ";
	
	String DOUBLE_SPACE_STRING =LangUtils.append(SPACE_STRING,SPACE_STRING);
	
	String TAB_STRING = "	";
	
	String NEW_LINE = "\r\n";
}
