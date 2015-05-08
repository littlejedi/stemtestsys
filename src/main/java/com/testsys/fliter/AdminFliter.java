package com.testsys.fliter;

import javax.servlet.http.HttpServletRequest;
import com.testsys.admin.bean.Admin;
import com.testsys.constants.CommonConstant;

/**
 * �?��Controller的基�? </pre>
 * 
 * @see
 */
public class AdminFliter {
	protected static final String ERROR_MSG_KEY = "errorMsg";
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
	protected void setSessionAdmin(HttpServletRequest request, Admin admin) 
	{
		request.getSession().setAttribute(CommonConstant.ADMIN_CONTEXT, admin);
	}
	/**
	 * ��ݽ�ɫ��ɲ�ͬ��view
	 * 
	 * @param role
	 * @return
	 */
	protected String roleRedirect(int role) {
		String returnurl = "adminlogin/login.html";
		switch (role) {
		case 1:
			returnurl = "superadmin/ecadminlist.html";
			break;
		case 2:
			returnurl = "ecadmin/questionlist.html";
			break;
		case 3:
			returnurl = "tcadmin/reviewlist.html";
			break;
		default:
			
		}
		return returnurl;
	}
}
