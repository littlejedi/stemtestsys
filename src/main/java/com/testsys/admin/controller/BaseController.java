package com.testsys.admin.controller;

import javax.servlet.http.HttpServletRequest;
import com.testsys.admin.bean.Admin;
import com.testsys.admin.bean.User;
import com.testsys.constants.CommonConstant;
import org.springframework.util.Assert;

/**
 * �?��Controller的基�? </pre>
 * 
 * @see
 */
public class BaseController {
	protected static final String ERROR_MSG_KEY = "errorMsg";

	/**
	 * 获取保存在Session中的用户对象
	 * 
	 * @param request
	 * @return
	 */
	protected User getSessionUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(
				CommonConstant.USER_CONTEXT);
	}

	/**
	 * 保存用户对象到Session�?
	 * 
	 * @param request
	 * @param user
	 */
	protected void setSessionUser(HttpServletRequest request, User user) {
		request.getSession().setAttribute(CommonConstant.USER_CONTEXT, user);
	}

	/**
	 * 获取保存在Session中的用户对象
	 * 
	 * @param request
	 * @return
	 */
	protected Admin getSessionAdmin(HttpServletRequest request) {
		return (Admin) request.getSession().getAttribute(
				CommonConstant.ADMIN_CONTEXT);
	}

	/**
	 * 保存用户对象到Session�?
	 * 
	 * @param request
	 * @param user
	 */
	protected void setSessionAdmin(HttpServletRequest request, Admin admin) {
		request.getSession().setAttribute(CommonConstant.ADMIN_CONTEXT, admin);
	}
	/**
	 * 获取基于应用程序的url绝对路径
	 * 
	 * @param request
	 * @param url
	 *            �?/"打头的URL地址
	 * @return 基于应用程序的url绝对路径
	 */
	public final String getAppbaseUrl(HttpServletRequest request, String url) {
		Assert.hasLength(url, "url不能为空");
		Assert.isTrue(url.startsWith("/"), "必须�?打头");
		return request.getContextPath() + url;
	}

}
