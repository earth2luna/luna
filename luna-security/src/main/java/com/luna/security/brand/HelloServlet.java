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

import com.luna.security.LoginUtils;
import com.luna.utils.IOUtils;
import com.luna.utils.enm.CharsetEnum;

/**
 * @author laulyl
 * @date 2017年5月4日 下午8:05:46
 * @description
 */
public class HelloServlet extends HttpServlet {

	private static final long serialVersionUID = -1697096738521308992L;

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
			outputStream = resp.getOutputStream();
			outputStream.write(LoginUtils.getJSONPassport(req.getParameter("u"), req.getParameter("p"))
					.getBytes(CharsetEnum.UTF8.getCharsetName()));
			outputStream.flush();
		} finally {
			IOUtils.close(outputStream);
		}
	}
}
