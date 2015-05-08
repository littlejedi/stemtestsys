package com.testsys.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.testsys.admin.bean.Admin;
import com.testsys.admin.service.SuperAdminService;

@Controller
@RequestMapping(value = "/adminlogin")
public class LoginController extends BaseController{

    @Autowired
    private SuperAdminService adminService;

    @RequestMapping(value = "/login.html")
    public String loginPage() {
        return "admin/stemEvalSystemLogin";
    }
    
    @RequestMapping(value = "/")
    public String defaults() {
        return "admin/stemEvalSystemLogin";
    }
    
    @RequestMapping(value = "/index.html")
    public String index() {
        return "admin/stemEvalSystemLogin";
    }
    
    @RequestMapping(value = "/logout.html")
    public ModelAndView logout(HttpServletRequest request) {
    	request.getSession().invalidate();
    	return new ModelAndView(new RedirectView("login.html"));
    }
    /**
     * 登陆验证
     * @param request
     * @param loginCommand
     * @return
     */
    @RequestMapping(value = "/loginCheck.html")
    public ModelAndView loginCheck(HttpServletRequest request, LoginCommand loginCommand) {
    	//System.out.println("loginCheck"+loginCommand.getAccount()+","+loginCommand.getPassword());
        boolean isValidAdmin =  adminService.hasMatchAdmin(loginCommand.getAccount(),loginCommand.getPassword());
        if (!isValidAdmin) {
            return new ModelAndView("admin/stemEvalSystemLogin", "error", "用户名或密码错误");
        } else {
            Admin admin = adminService.findUserByUserName(loginCommand.getAccount());
            //request.getSession().setAttribute("admin", admin);
            this.setSessionAdmin(request,admin);
            return this.roleRedirect(admin.getRole());
        }
    }
    /**
	 * ��ݽ�ɫ��ɲ�ͬ��view
	 * 
	 * @param role
	 * @return
	 */
	protected ModelAndView roleRedirect(int role) {
		ModelAndView mav = null;
		switch (role) {
		case 1:
			mav = new ModelAndView("redirect:/superadmin/ecadminlist.html");
			break;
		case 2:
			mav = new ModelAndView("redirect:/ecadmin/questionlist.html");
			break;
		case 3:
			mav = new ModelAndView("redirect:/tcadmin/reviewlist.html");
			break;
		}
		return mav;
	}
	
}
