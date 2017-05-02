/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luna.service.ResourcesSolrService;
import com.luna.service.data.utils.Configure;
import com.luna.service.data.utils.Constants;
import com.luna.utils.LangUtils;
import com.luna.utils.VerificationUtils;
import com.luna.web.security.AuthenticationTicket;
import com.luna.web.security.SecurityConstants;

/**
 * @author laulyl
 * @date 2017年5月1日 下午10:07:09
 * @description
 */
@Controller
public class IndexController extends ParentController {

	@Autowired
	private ResourcesSolrService resourcesSolrService;

	@ModelAttribute
	public void indexAnywhere(Model model) {
		addPageHeaderSay(model);
	}

	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("QUERY_STRING_MAX_LENGTH", Constants.QUERY_STRING_MAX_LENGTH);
		model.addAttribute("page", resourcesSolrService.query(null, 1));
		return "front/page_home";
	}

	@RequestMapping("/about")
	public String about(Model model) {
		return "front/page_about";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		return "front/page_login";
	}

	@RequestMapping("/signin")
	public void signIn(Model model, String userName, String password, HttpServletResponse response) {
		String returnUrl = Configure.getLoginPageUrl();
		if (StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(password)) {
			String signIn = VerificationUtils.getMD5Encode(LangUtils.append(userName, ":", password));
			if (Configure.getSignInPassKey().equals(signIn)) {
				try {
					addCookie(response, Configure.getSignInCookiesName(),
							SecurityConstants.getBase64String(new AuthenticationTicket(userName, null, null)),
							SecurityConstants.LOGIN_STAY_TIME_SECONDS);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				returnUrl = Configure.getLoginSuccessUrl();
			}
		}
		try {
			response.sendRedirect(returnUrl);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
