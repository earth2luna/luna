/**
 * COPYRIGHT@LAULYL
 */
package com.luna.security.brand;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luna.security.Configuration;
import com.luna.security.LoginUtils;
import com.luna.security.SecurityCursor;
import com.luna.utils.IOUtils;
import com.luna.utils.LangUtils;
import com.luna.utils.classes.InvokeVo;
import com.luna.utils.enm.CharsetEnum;

/**
 * @author laulyl
 * @date 2017年5月4日 下午4:37:06
 * @description
 */
public class SSOServlet extends HttpServlet {

	private static final long serialVersionUID = 6745372349060671962L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletOutputStream outputStream = null;
		try {
			InvokeVo invokeVo = LoginUtils.getPassort(req.getParameter("t"));
			if (1 == invokeVo.getCode()) {
				LoginUtils.addCookie(resp, Configuration.signInCookiesNamePlaintext,
						LangUtils.toString(invokeVo.getData()), SecurityCursor.LOGIN_STAY_TIME_SECONDS);
			}
			outputStream = resp.getOutputStream();
			outputStream.write(LoginUtils.getJSONPPassport(invokeVo).getBytes(CharsetEnum.UTF8.getCharsetName()));
			outputStream.flush();
		} finally {
			IOUtils.close(outputStream);
		}
	}

}
