package com.testsys.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.testsys.admin.bean.Admin;
import com.testsys.admin.bean.Exam;
import com.testsys.admin.bean.Page;
import com.testsys.admin.bean.Result;
import com.testsys.admin.bean.Review;
import com.testsys.admin.bean.SignIn;
import com.testsys.admin.service.SuperAdminService;
import com.testsys.constants.CommonConstant;
import com.testsys.web.bean.Choice;
import com.testsys.web.bean.Open;

@Controller
@RequestMapping(value = "/superadmin")
public class SuperAdminController extends BaseController{
	/**
	 * ��������Ա��̨
	 */

    @Autowired
    private SuperAdminService adminService;

    /**
     * ��ҳ��ʾϰ�����Ա�˻��б�
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/ecadminlist.html")
    public ModelAndView ecAdminList(HttpServletRequest request,HttpServletResponse response)
    {
    	try
    	{
	    	int page = 1;
	    	if(request.getParameter("page") != null)
	    		page=Integer.valueOf(request.getParameter("page"));
	    	int listrows = CommonConstant.LISTROWS;
	    	List<Admin> ecadmin= adminService.getAdminList(page, listrows, 2);
	    	request.setAttribute("adminlist" , ecadmin);
	    	request.setAttribute("role", 2);
	    	
	    	int count = adminService.getMatchAdminCount(2);
	    	request.setAttribute("admincount", count);
	        List<Page> pagelist=adminService.getPageList("/superadmin/ecadminlist.html?page=", page, listrows, count);
	        request.setAttribute("pagelist", pagelist);
	    	
	    	return new ModelAndView("admin/stemEvalSystemAdminList");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("admin/stemEvalSystemError");
    	}
    }
    
    @RequestMapping(value = "/choicelist.html")
    public ModelAndView choiceList(HttpServletRequest request)
    {
    	try
    	{
	    	int page = 1;
	    	if(request.getParameter("page") != null)
	    		page=Integer.valueOf(request.getParameter("page"));
	    	int listrows = CommonConstant.LISTROWS;
	    	int adminId = Integer.valueOf(request.getParameter("adminId"));
	    	List<Choice> choicelist = adminService.getChoiceList(page,listrows,adminId);
	    	request.setAttribute("choicelist", choicelist);
	    	
	    	int count = adminService.getMatchChoiceCount(adminId);
	    	request.setAttribute("questioncount", count);
	        List<Page> pagelist=adminService.getPageList("/superadmin/choicelist.html?adminId="+adminId+"&page=", page, listrows, count);
	        request.setAttribute("pagelist", pagelist);
	    	return new ModelAndView("admin/stemEvalSystemSuperChoiceList");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("admin/stemEvalSystemError");
    	}
    }
    
    @RequestMapping(value = "/openlist.html")
    public ModelAndView openList(HttpServletRequest request)
    {
    	try
    	{
	    	int page = 1;
	    	if(request.getParameter("page") != null)
	    		page=Integer.valueOf(request.getParameter("page"));
	    	int listrows = CommonConstant.LISTROWS;
	    	int adminId = Integer.valueOf(request.getParameter("adminId"));
	    	List<Open> openlist=adminService.getOpenList(page,listrows,adminId);
	    	request.setAttribute("openlist", openlist);
	    	
	    	int count = adminService.getMatchOpenCount(adminId);
	    	request.setAttribute("questioncount", count);
	    	
	        List<Page> pagelist=adminService.getPageList("/superadmin/openlist.html?adminId="+adminId+"&page=", page, listrows, count);
	        request.setAttribute("pagelist", pagelist);
	    	return new ModelAndView("admin/stemEvalSystemSuperOpenList");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("admin/stemEvalSystemError");
    	}
    }
    /**
     * �ľ���ʦ�б���ʾ
     * @param request,page
     * 
     * @return
     */
    @RequestMapping(value = "/tcadminlist.html")
    public ModelAndView tcAdminList(HttpServletRequest request)
    {
    	try
    	{
	    	int page = 1;
	    	if(request.getParameter("page") != null)
	    		page=Integer.valueOf(request.getParameter("page"));
	    	int listrows = CommonConstant.LISTROWS;
	    	List<Admin> tcadmin= adminService.getAdminList(page, listrows, 3);
	    	request.setAttribute("adminlist" , tcadmin);
	    	request.setAttribute("role", 3);
	    	int count = adminService.getMatchAdminCount(3);
	    	request.setAttribute("admincount", count);
	        List<Page> pagelist=adminService.getPageList("/superadmin/tcadminlist.html?page=", page, listrows, count);
	        request.setAttribute("pagelist", pagelist);
	    	return new ModelAndView("admin/stemEvalSystemAdminList");
	    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("admin/stemEvalSystemError");
    	}
    }
    /**
     * ���ӹ���Աrole = 2Ϊϰ�����Ա role = 3Ϊ�ľ���ʦ
     * @param request
     * @return
     */
    @RequestMapping(value="/addAdmin.html")
    public ModelAndView addAdmin(HttpServletRequest request,HttpServletResponse response)
    {
    	try
    	{
			request.setCharacterEncoding("UTF-8");
        	String account = request.getParameter("account");
        	String name = request.getParameter("name");
        	String course = request.getParameter("course");
        	String password = request.getParameter("password");
        	int role = Integer.valueOf(request.getParameter("role"));
        	adminService.addAdmin(account, role, name, password, course);
        	if(role == 3)
        		return new ModelAndView(new RedirectView("tcadminlist.html"));
        	else
        		return new ModelAndView(new RedirectView("ecadminlist.html"));
    	}
		catch(Exception e)
		{
			e.printStackTrace();
			return new ModelAndView("admin/stemEvalSystemError");
		}
    }
    /**
     * ɾ�����Ա���������Աid���жϹ���Ա�Ƿ���δʹ�ã������޷�ɾ��
     * @param request
     * @param response
     */
    @RequestMapping(value = "/removeAdmin.html")
    public void removeAdmin(HttpServletRequest request,HttpServletResponse response)
    {
    	PrintWriter out;
    	try
    	{
    		String adminIdList=request.getParameter("adminId");
    		adminService.removeAdmin(adminIdList);
    		out = response.getWriter();
			out.write("success");
    	}
    	catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @RequestMapping(value = "/multicalcu.html")
    public void multiCalcu(HttpServletRequest request,HttpServletResponse response)
    {
    	PrintWriter out;
    	try
    	{
    		String paperIdList=request.getParameter("paperId");
    		adminService.multiCalcu(paperIdList);
    		out = response.getWriter();
			out.write("success");
    	}
    	catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * ��ȡ����ʱ���б?
     * @param request
     * @return
     */
    @RequestMapping(value = "/examlist.html")
    public ModelAndView examList(HttpServletRequest request)
    {
    	try
    	{
	    	int page = 1;
	    	if(request.getParameter("page") != null)
	    		page=Integer.valueOf(request.getParameter("page"));
	    	int listrows = CommonConstant.LISTROWS;
	    	List<Exam> examlist= adminService.getExamList(page, listrows);
	    	request.setAttribute("examlist" , examlist);
	    	
	    	int count = adminService.getMatchExamCount();
	    	request.setAttribute("examcount", count);
	        List<Page> pagelist=adminService.getPageList("/superadmin/examlist.html?page=", page, listrows, count);
	        request.setAttribute("pagelist", pagelist);
	    	return new ModelAndView("admin/stemEvalSystemExamList");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("admin/stemEvalSystemError");
    	}
    }
    /**
     * ��������ʱ�䣬������Ӧ����
     * @param request
     * @return
     */
    @RequestMapping(value="/addExam.html")
    public ModelAndView addExam(HttpServletRequest request,HttpServletResponse response)
    {
    	try
    	{
    		request.setCharacterEncoding("UTF-8");
    		String examName=request.getParameter("exam_name");
    		String examsociety=request.getParameter("exam_society");
    		String examsubject=request.getParameter("exam_subject");
    		String examBegin = request.getParameter("begin_ymd")+" "+request.getParameter("begin_h")+":"+request.getParameter("begin_m")+":00";
    		String examEnd = request.getParameter("end_ymd")+" "+request.getParameter("end_h")+":"+request.getParameter("end_m")+":00";
    		String notice = request.getParameter("notice");
    		double examLine = Double.valueOf(request.getParameter("exam_line"));
    		int choiceNumber = 100;
    		if(request.getParameter("choice_number") != null)
    			choiceNumber = Integer.valueOf(request.getParameter("choice_number"));
    		int choiceTime = 120;
    		if(request.getParameter("choice_time") != null)
    			choiceTime = Integer.valueOf(request.getParameter("choice_time"));
    		//��Ҫ�޸ģ�
    		Admin admin = (Admin)this.getSessionAdmin(request);//request.getSession().getAttribute("admin");
    		
    		adminService.addExam(examName,examsociety,examsubject,examBegin, examEnd, examLine, notice,choiceNumber,choiceTime, admin.getAdminId());
    		return new ModelAndView(new RedirectView("examlist.html"));
    	}
		catch(Exception e)
		{
			e.printStackTrace();
			return new ModelAndView("admin/stemEvalSystemError");
		}
    }
    /**
     * ɾ���ԣ����뿼��id�������������˱���μӣ����޷�ɾ��
     * @param request
     * @param response
     */
    @RequestMapping(value = "/removeExam.html")
    public void removeExam(HttpServletRequest request,HttpServletResponse response)
    {
    	PrintWriter out;
    	try
    	{
    		String examIdList=request.getParameter("examId");
    		adminService.removeExam(examIdList);
    		out = response.getWriter();
			out.write("success");
    	}
    	catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @RequestMapping(value = "/signInList.html")
    public ModelAndView signInList(HttpServletRequest request)
    {
    	try
    	{
    		int examId=Integer.valueOf(request.getParameter("examId"));
    		int page = 1;
        	if(request.getParameter("page") != null)
        		page=Integer.valueOf(request.getParameter("page"));
        	int listrows = CommonConstant.LISTROWS;
        	Exam exam = adminService.getExamInfoById(examId);
        	List<SignIn> signinlist= adminService.getSignInList(examId,page, listrows);
        	request.setAttribute("exam", exam);
        	request.setAttribute("signinlist" , signinlist);
        	
        	int count = adminService.getSignInNumbers(examId);
        	request.setAttribute("signincount", count);
            List<Page> pagelist=adminService.getPageList("/superadmin/signInList.html?examId="+examId+"&page=", page, listrows, count);
            request.setAttribute("pagelist", pagelist);
        	return new ModelAndView("admin/stemEvalSystemSignInList");
    	}
		catch(Exception e)
		{
			e.printStackTrace();
			return new ModelAndView("admin/stemEvalSystemError");
		}
    }
    
    @RequestMapping(value = "/timeoutlist.html")
    public ModelAndView timeOutList(HttpServletRequest request)
    {
    	try
    	{
    		
    		int page = 1;
        	if(request.getParameter("page") != null)
        		page=Integer.valueOf(request.getParameter("page"));
        	int listrows = CommonConstant.LISTROWS;
        	List<SignIn> signinlist= adminService.getTimeOutList(page, listrows);

        	request.setAttribute("signinlist" , signinlist);
        	
        	int count = adminService.getTimeOutNumbers();
        	request.setAttribute("signincount", count);
            List<Page> pagelist=adminService.getPageList("/superadmin/timeoutlist.html?page=", page, listrows, count);
            request.setAttribute("pagelist", pagelist);
        	return new ModelAndView("admin/stemEvalSystemTimeoutList");
    	}
		catch(Exception e)
		{
			e.printStackTrace();
			return new ModelAndView("admin/stemEvalSystemError");
		}
    }
    
    @RequestMapping(value = "/reviewList.html")
    public ModelAndView reviewList(HttpServletRequest request)
    {
    	try
    	{
    		int adminId=Integer.valueOf(request.getParameter("adminId"));
    		int page = 1;
        	if(request.getParameter("page") != null)
        		page=Integer.valueOf(request.getParameter("page"));
        	int listrows = CommonConstant.LISTROWS;
        	Admin admin = adminService.getAdminInfoById(adminId);
        	List<Review> reviewlist= adminService.getReviewList(adminId,page, listrows);
        	request.setAttribute("admin", admin);
        	request.setAttribute("reviewlist" , reviewlist);
        	
        	int count = adminService.getPaperNumbers(adminId);
        	request.setAttribute("reviewcount", count);
            List<Page> pagelist=adminService.getPageList("/superadmin/reviewList.html?adminId="+adminId+"&page=", page, listrows, count);
            request.setAttribute("pagelist", pagelist);
        	
        	return new ModelAndView("admin/stemEvalSystemReviewList");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("admin/stemEvalSystemError");
    	}
    }
    
    @RequestMapping(value = "/examresult.html")
    public void examResult(HttpServletRequest request,HttpServletResponse response) {
    	PrintWriter out;
    	try
    	{
    		response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			/**
			 * 
			 * if(status = 评阅结束)
			 * 返回所有结果
			 * else if(status = 选择题考完)
			 * 返回选择题考试结果
			 * else
			 * 返回错误码
			 */
			int paperId=Integer.valueOf(request.getParameter("paperId"));
			String json = adminService.getExamResultJson(paperId);
    		out = response.getWriter();
    		out.write(json);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	} 
    }
    @RequestMapping(value = "/addexamtimespage.html")
    public ModelAndView addExamTimesPage(HttpServletRequest request)
    {
    	try
    	{
        	return new ModelAndView("admin/stemEvalSystemAddExamTimes");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("admin/stemEvalSystemError");
    	}
    }
    @RequestMapping(value = "/addexamtimes.html")
    public void addExamTimes(HttpServletRequest request,HttpServletResponse response)
    {
    	PrintWriter out;
    	JSONObject json = new JSONObject();
    	try
    	{
    		String idcard = request.getParameter("idcard");
    		int examnum=Integer.valueOf(request.getParameter("num"));
    		
        	response.setContentType("text/html; charset=utf-8");
    		response.setCharacterEncoding("UTF-8");
    		int count = adminService.getMatchUserCountByIdcard(idcard);
    		if(count == 1)
    		{
    			adminService.addExamTimes(idcard,examnum);
        		//String json = adminService.getExamResultJson(paperId);
    			json.put("status", "success");
    		}
    		else
    		{
    			json.put("status", "unmatch");
    		}
    		out = response.getWriter();
        	out.write(json.toString());
        }
        catch(Exception e)
        {
        	json.put("status","error");
        	try {
				out = response.getWriter();
				out.write(json.toString());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	e.printStackTrace();
        } 
        }
    @RequestMapping(value = "/scoredownload.html")
    public ModelAndView ScoreDownload(HttpServletRequest request,HttpServletResponse response){
    		try
        	{
    	    	String society=request.getParameter("examSociety");
    			String subject=request.getParameter("examSubject");
    			String examname=request.getParameter("examName");
    			List<Result> result = adminService.getResultbyinfo(society,subject,examname);
    			request.setAttribute("result", result);
    	    	return new ModelAndView("admin/stemEvalSystemScoreDownload");
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        		return new ModelAndView("admin/stemEvalSystemError");
        	}
    }
 /*   @RequestMapping(value="/findExam.html")
    public ModelAndView findExam(HttpServletRequest request,HttpServletResponse response)
    {
    	try
    	{
    		response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			String society=request.getParameter("examSociety");
			String subject=request.getParameter("examSubject");
			String examname=request.getParameter("examName");
			List<Result> result = adminService.getResultbyinfo(society,subject,examname);
			request.setAttribute("result", result);
	    	return new ModelAndView(new RedirectView("scoredownload.html"));
    	}
		catch(Exception e)
		{
			e.printStackTrace();
			return new ModelAndView("admin/stemEvalSystemError");
		}
    }*/
}

