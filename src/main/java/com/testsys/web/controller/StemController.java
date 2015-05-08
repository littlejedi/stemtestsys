package com.testsys.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.testsys.admin.bean.Exam;
import com.testsys.admin.bean.User;
import com.testsys.admin.controller.BaseController;
import com.testsys.admin.service.ExamService;
import com.testsys.constants.CommonConstant;
import com.testsys.constants.CommonConstant.paperStatus;
import com.testsys.web.bean.ChoiceAllo;
import com.testsys.web.bean.ChoiceResult;
import com.testsys.web.bean.Myexam;
import com.testsys.web.bean.Open;
import com.testsys.web.bean.Paper;
import com.testsys.web.bean.PaperState;
import com.testsys.web.bean.Profile;
import com.testsys.web.service.StemService;

@Controller
@RequestMapping(value = "/stem")
public class StemController extends BaseController{

    @Autowired
    private StemService stemService;

    @Autowired
    private ExamService examService;
    /**
     * 获取考试列表，显示考试时间，状态（正常，过期，已报名），以及可选操作（无，报名）
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/examlist.html")
    public ModelAndView examList(HttpServletRequest request,HttpServletResponse response) 
    {
    	try
    	{
	    	int page = 1;
	    	if(request.getParameter("page") != null)
	    		page=Integer.valueOf(request.getParameter("page"));
	    	int listrows = CommonConstant.LISTROWS;
	    	User user = this.getSessionUser(request);
	    	int userId = user.getUserId();
	    	List<com.testsys.web.bean.Exam> examlist = stemService.getExamList(userId, page, listrows);
	    	request.setAttribute("examlist", examlist);
	    	return new ModelAndView("stem/stemEvalSystemExamList");//ExamList");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("stem/stemEvalSystemError");
    	}
    }
    @RequestMapping(value = "/newexam.html")
    public ModelAndView newStem(HttpServletRequest request,HttpServletResponse response) 
    {
    	try
    	{
	    	User user = this.getSessionUser(request);
	    	int userId = user.getUserId();
	    	Profile profile = stemService.getProfileInfo(userId);
	    	request.setAttribute("profile", profile);
	    	String examname=String.valueOf(request.getParameter("examname"));
	    	request.setAttribute("examname", examname);
	    	return new ModelAndView("stem/stemEvalSystemNewStem");//ExamList");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("stem/stemEvalSystemError");
    	}
    }
    @RequestMapping(value = "/waitpage.html")
    public ModelAndView waitPage(HttpServletRequest request,HttpServletResponse response) 
    {
    	try
    	{
	    	User user = this.getSessionUser(request);
	    	int userId = user.getUserId();
	    	
    		int paperId=Integer.valueOf(request.getParameter("paperId"));
    		Paper paper = stemService.getPaperInfo(paperId);
    		if(paper.getStatus() != paperStatus.HASPAY.ordinal() || paper.getUserId() != userId)
    			throw new Exception("此页面无法访问");
    		
    		Exam exam = examService.getExamInfoById(paper.getExamId());
    		//ChoiceAllo ca = stemService.getChoiceAlloById(paper.getChoiceAlloId());
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		
    		Date createtime = sdf.parse(exam.getExamBegin());
    		long createseconds = createtime.getTime()/1000;
    		Date now = new Date();
    		long nowseconds = now.getTime()/1000;
    		long remainingtime = createseconds - nowseconds + 10;
    		if(remainingtime < 0)
    		{
    			stemService.beginExam(userId,paperId);
    			return new ModelAndView(new RedirectView("answer.html?paperId="+paperId));
    		}
    		request.setAttribute("paperId", paperId);
    		request.setAttribute("remainingtime", remainingtime);
	    	
	    	return new ModelAndView("stem/stemEvalSystemExamWait");//ExamList");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("stem/stemEvalSystemError");
    	}
    }
    /**
     * 报名函数，点击报名触发，通过json进行传参，返回一个状态参数
     * status success,noinfo,expired,duplicate.分别代表成功，未填写个人信息，考试已过期，重复报名
     * @param request
     * @param response
     */
    @RequestMapping(value = "/newsignin.html")
    public void newSignIn(HttpServletRequest request,HttpServletResponse response) {
    	PrintWriter out;
    	try
    	{
    		response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
    		User user = this.getSessionUser(request);
        	int userId = user.getUserId();
        	String examName=String.valueOf(request.getParameter("examname"));
    		String json=stemService.newSignIn(userId,examName);
    		out = response.getWriter();
			out.write(json);
    	}
    	catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * 报名函数，点击报名触发，通过json进行传参，返回一个状态参数
     * status success,noinfo,expired,duplicate.分别代表成功，未填写个人信息，考试已过期，重复报名
     * @param request
     * @param response
     */
    @RequestMapping(value = "/signin.html")
    public void signIn(HttpServletRequest request,HttpServletResponse response) {
    	PrintWriter out;
    	try
    	{
    		response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
    		int examId=Integer.valueOf(request.getParameter("examId"));
    		User user = this.getSessionUser(request);
        	int userId = user.getUserId();
    		String json=stemService.signIn(userId,examId);
    		out = response.getWriter();
			out.write(json);
    	}
    	catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * 显示我已报名的考试列表，考试相关信息加上我的考试进程，不同进程对应不同操作
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/myexam.html")
    public ModelAndView myExam(HttpServletRequest request,HttpServletResponse response) {
    	try
    	{
	    	int page = 1;
	    	if(request.getParameter("page") != null)
	    		page=Integer.valueOf(request.getParameter("page"));
	    	int listrows = CommonConstant.LISTROWS;
	    	User user = this.getSessionUser(request);
	    	int userId = user.getUserId();
	    	List<Myexam> examlist = stemService.getMyExamList(userId, page, listrows);
	    	request.setAttribute("myexamlist", examlist);
	    	return new ModelAndView("stem/stemEvalSystemMyExam");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("stem/stemEvalSystemError");
    	}
    }
    @RequestMapping(value = "/pay.html")
    public void pay(HttpServletRequest request,HttpServletResponse response) 
    {
    	PrintWriter out;
    	try
    	{
    		response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
    		int paperId=Integer.valueOf(request.getParameter("paperId"));
    		User user = this.getSessionUser(request);
        	int userId = user.getUserId();
    		String json=stemService.pay(userId,paperId);
    		out = response.getWriter();
			out.write(json);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    }
    /**
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/beginexam.html")
    public void beginExam(HttpServletRequest request,HttpServletResponse response) 
    {
    	PrintWriter out;
    	try
    	{
    		response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
    		int paperId=Integer.valueOf(request.getParameter("paperId"));
    		User user = this.getSessionUser(request);
        	int userId = user.getUserId();
    		String json=stemService.beginExam(userId,paperId);
    		out = response.getWriter();
			out.write(json);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
   
    /**
     * 答题页面，显示当前的答题
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/answer.html")
    public ModelAndView answer(HttpServletRequest request,HttpServletResponse response) 
    {
    	try
    	{
    		User user = this.getSessionUser(request);
        	int userId = user.getUserId();
    		int paperId=Integer.valueOf(request.getParameter("paperId"));
    		Paper paper = stemService.getPaperInfo(paperId);
    		if(paper.getStatus() != 2 || paper.getUserId() != userId)
    			throw new Exception("此页面无法访问");
    		Exam exam = examService.getExamInfoById(paper.getExamId());
    		ChoiceAllo ca = stemService.getChoiceAlloById(paper.getChoiceAlloId());
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		Date createtime = sdf.parse(ca.getCreateTime());
    		long createseconds = createtime.getTime()/1000;
    		Date now = new Date();
    		long nowseconds = now.getTime()/1000;
    		long examlimit = exam.getChoiceTime()*60;
    		long remainingtime = examlimit - (nowseconds - createseconds);
    		if(remainingtime <= 0)
    		{
    			if(PaperState.stateTurnCheck(paperStatus.values()[paper.getStatus()], paperStatus.CHOICEEXPIRED))
					stemService.updatePaperStatus(paperStatus.CHOICEEXPIRED.ordinal(),paperId);
    			return new ModelAndView(new RedirectView("myexam.html"));
    		}
    		request.setAttribute("remainingtime", remainingtime);
    		request.setAttribute("paperId", paperId);
    		return new ModelAndView("stem/stemEvalSystemAnswer");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("stem/stemEvalSystemError");
    	} 
    }
    
    /**
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/allchoice.html")
    public void allChoice(HttpServletRequest request,HttpServletResponse response) {
    	PrintWriter out;
    	try
    	{
    		response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
    		int paperId=Integer.valueOf(request.getParameter("paperId"));
    		//User user = this.getSessionUser(request);
        	String json = stemService.generateAllAnswerJson(paperId);
    		out = response.getWriter();
			out.write(json);
    	}
    	catch(NullPointerException e)
    	{
    		e.printStackTrace();
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/submitchoice.html")
    public void submitChoice(HttpServletRequest request,HttpServletResponse response) {
    	PrintWriter out;
    	try
    	{
    		response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
    		int paperId=Integer.valueOf(request.getParameter("paperId"));
    		int type = Integer.valueOf(request.getParameter("type"));
    		String resultIdList=request.getParameter("resultIdList");
    		User user = this.getSessionUser(request);
        	int userId = user.getUserId();
    		String json = stemService.submitChoice(paperId, userId,resultIdList,type);
    		out = response.getWriter();
			out.write(json);
    	}
    	catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @RequestMapping(value = "/choiceresult.html")
    public ModelAndView choiceResult(HttpServletRequest request,HttpServletResponse response) 
    {
    	try
    	{
    		User user = this.getSessionUser(request);
        	int userId = user.getUserId();
	    	int paperId=Integer.valueOf(request.getParameter("paperId"));
	    	Paper paper = stemService.getPaperInfo(paperId);
	    	if(paper.getUserId() != userId)
	    		throw new Exception("您无权访问此页面");
	    	if(paper.getStatus() == paperStatus.SIGNIN.ordinal() ||paper.getStatus() == paperStatus.HASPAY.ordinal() ||paper.getStatus() == paperStatus.CHOICEING.ordinal() ||paper.getStatus() == paperStatus.CHOICEEXPIRED.ordinal())
	    		throw new Exception("此页面暂时不可访问");
	    	ChoiceResult cr = stemService.getChoiceResult(paper.getResultId());
	    	Exam exam= examService.getExamInfoById(paper.getExamId());
	    	
	    	Profile profile = stemService.getProfileInfo(userId);
	    	request.setAttribute("profile", profile);
	    	request.setAttribute("fullmark",exam.getChoiceNumber()*3);
	    	request.setAttribute("examline", exam.getExamLine());
	    	request.setAttribute("exam", exam);
	    	request.setAttribute("result", cr);
	    	request.setAttribute("paperId", paperId);
	    	return new ModelAndView("stem/stemEvalSystemChoiceResult");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("stem/stemEvalSystemError");
    	}
    }  
    /**
     * 维护个人信息页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/profile.html")
    public ModelAndView profile(HttpServletRequest request,HttpServletResponse response) 
    {
    	try
    	{
    		User user = this.getSessionUser(request);
        	int userId = user.getUserId();
    		Profile profile = stemService.getProfileInfo(userId);
    		request.setAttribute("profile", profile);
    		return new ModelAndView("stem/stemEvalSystemProfile");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("stem/stemEvalSystemError");
    	}
    }    
    
    @RequestMapping(value = "/updateprofile.html")
    public void updateProfile(HttpServletRequest request,HttpServletResponse response) {
    	PrintWriter out;
    	try
    	{
    		response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
    		String idcard=request.getParameter("idcard");
    		String phone = request.getParameter("telephone");
    		String realname = request.getParameter("realname");
    		User user = this.getSessionUser(request);
        	int userId = user.getUserId();
    		String json=stemService.updateProfileInfo(userId,idcard,phone,realname);
    		out = response.getWriter();
			out.write(json);
    	}
    	catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @RequestMapping(value = "/registeruser.html")
    public ModelAndView registerUser(HttpServletRequest request,HttpServletResponse response) 
    {
    	try
    	{
    		String url = (String) request.getAttribute("url");
    		User user = this.getSessionUser(request);
    		int usercount = stemService.matchUserCount(user.getUserId());
    		if(usercount == 0)
    			stemService.registerUser(user.getUserId(),user.getUserName(),user.getIdcard(),user.getRealname(),user.getTelephone());
    		else if(usercount == 1)
    			stemService.updateUser(user.getUserId(),user.getUserName(),user.getIdcard(),user.getRealname(),user.getTelephone());
    			
    		String  uri = request.getRequestURI();
        	uri = uri.substring(0, uri.lastIndexOf("stem"));
    		response.sendRedirect(uri + "stem/"+url);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("stem/stemEvalSystemError");
    	}
    	return new ModelAndView("stem/stemEvalSystemProfile");
    } 
    
    @RequestMapping(value = "/choosecourse.html")
    public ModelAndView chooseCourse(HttpServletRequest request,HttpServletResponse response)
    {
    	try
    	{
    		//是否登录else
    		User user = this.getSessionUser(request);
        	int userId = user.getUserId();
    		int paperId=Integer.valueOf(request.getParameter("paperId"));
    		Paper paper = stemService.getPaperInfo(paperId);
    		if(paper.getUserId() != userId || !PaperState.stateTurnCheck(paperStatus.values()[paper.getStatus()], paperStatus.OPENBEGIN))
    			throw new Exception("此页面无法访问");
    		
    		Exam exam = examService.getExamInfoById(paper.getExamId());	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now=new Date();
			String nowstr=sdf.format(now);
			int end = nowstr.compareTo(exam.getExamEnd());
			if(end > 0)
			{
				if(PaperState.stateTurnCheck(paperStatus.values()[paper.getStatus()], paperStatus.OPENEXPIRED))
					stemService.updatePaperStatus(paperStatus.OPENEXPIRED.ordinal(),paperId);
    			return new ModelAndView(new RedirectView("myexam.html"));
			}
    		request.setAttribute("paperId", paperId);
    		return new ModelAndView("stem/stemEvalSystemChooseCourse");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("stem/stemEvalSystemError");
    	} 
    }
    @RequestMapping(value = "/beginopen.html")
    public void beginOpen(HttpServletRequest request,HttpServletResponse response) 
    {
    	PrintWriter out;
    	try
    	{
    		response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
    		int paperId=Integer.valueOf(request.getParameter("paperId"));
    		String course = request.getParameter("course");
    		User user = this.getSessionUser(request);
        	int userId = user.getUserId();
    		String json=stemService.beginOpen(paperId,userId,course);
    		out = response.getWriter();
			out.write(json);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
  
    
    @RequestMapping(value = "/open.html")
    public ModelAndView open(HttpServletRequest request,HttpServletResponse response) 
    {
    	try
    	{
    		//是否登录else
    		User user = this.getSessionUser(request);
        	int userId = user.getUserId();
    		int paperId=Integer.valueOf(request.getParameter("paperId"));
    		Paper paper = stemService.getPaperInfo(paperId);
    		if(paper.getStatus() != paperStatus.OPENBEGIN.ordinal() || paper.getUserId() != userId)
    			throw new Exception("此页面无法访问");
    		
    		
    		Exam exam = examService.getExamInfoById(paper.getExamId());	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now=new Date();
			String nowstr=sdf.format(now);
			//int begin = nowstr.compareTo(exam.getExamBegin());
			int end = nowstr.compareTo(exam.getExamEnd());
			if(end > 0)
			{
				if(PaperState.stateTurnCheck(paperStatus.values()[paper.getStatus()], paperStatus.OPENEXPIRED))
					stemService.updatePaperStatus(paperStatus.OPENEXPIRED.ordinal(),paperId);
    			return new ModelAndView(new RedirectView("myexam.html"));
			}
			List<Open> openlist = stemService.getOpenList(paper.getOpenAlloId());
			request.setAttribute("openlist", openlist);
    		request.setAttribute("paperId", paperId);
    		return new ModelAndView("stem/stemEvalSystemOpen");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("stem/stemEvalSystemError");
    	} 
    }
    @RequestMapping(value = "/opensubmitprocess.html")
    public void openSubmitProcess(HttpServletRequest request,HttpServletResponse response) 
    {
    	PrintWriter out;
    	try
    	{
    		response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
    		int paperId=Integer.valueOf(request.getParameter("paperId"));
    		int chooseOpenid= Integer.valueOf(request.getParameter("openId"));
    		User user = this.getSessionUser(request);
        	int userId = user.getUserId();
    		String json=stemService.chooseOpen(paperId,userId,chooseOpenid);
    		out = response.getWriter();
			out.write(json);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    //开放题提交页面选择文件上传
    @RequestMapping(value = "/opensubmit.html")
    public ModelAndView openSubmit(HttpServletRequest request,HttpServletResponse response) 
    {
    	try
    	{
    		//是否登录else
    		User user = this.getSessionUser(request);
        	int userId = user.getUserId();
    		int paperId=Integer.valueOf(request.getParameter("paperId"));
    		Paper paper = stemService.getPaperInfo(paperId);
    		if(paper.getStatus() != paperStatus.OPENCHOOSE.ordinal() || paper.getUserId() != userId)
    			throw new Exception("此页面无法访问");
    		
    		
    		Exam exam = examService.getExamInfoById(paper.getExamId());	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now=new Date();
			String nowstr=sdf.format(now);
			int end = nowstr.compareTo(exam.getExamEnd());
			if(end > 0)
			{
				if(PaperState.stateTurnCheck(paperStatus.values()[paper.getStatus()], paperStatus.OPENEXPIRED))
					stemService.updatePaperStatus(paperStatus.OPENEXPIRED.ordinal(),paperId);
    			return new ModelAndView(new RedirectView("myexam.html"));
			}
			Open open = stemService.getOpenInfo(paper.getOpenAlloId());
			request.setAttribute("open", open);
    		request.setAttribute("paperId", paperId);
    		return new ModelAndView("stem/stemEvalSystemOpenSubmit");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("stem/stemEvalSystemError");
    	} 
    }
    @RequestMapping(value = "/openupload.html")
    public ModelAndView openUpload( HttpServletRequest request,HttpServletResponse response,@RequestParam("answer") MultipartFile file) 
    {
    	try
    	{
    		request.setCharacterEncoding("UTF-8");
    		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
            ServletContext servletContext = webApplicationContext.getServletContext(); 
    		String answerPath=servletContext.getRealPath("answer");
    		int paperId=Integer.valueOf(request.getParameter("paperId"));
    		User user = this.getSessionUser(request);
        	int userId = user.getUserId();
    		if (!file.isEmpty()) {
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
    			Date date=new Date();
    			int suffixpos = file.getOriginalFilename().lastIndexOf(".");
    			String fileName = sdf2.format(date) + "/" + sdf.format(date)+"-"+request.getParameter("paperId") + file.getOriginalFilename().substring(suffixpos);//MD5Util. file.getOriginalFilename();
    			File dir=new File(answerPath +"/" + sdf2.format(date) + "/");
    	    	if(!dir.exists())
    	    		dir.mkdirs();
    			FileCopyUtils.copy(file.getBytes(), new File(answerPath +"/" +  fileName));
    			String status = stemService.openUpload(paperId, userId, fileName);
    			if(status.equals("success"))
    				return new ModelAndView(new RedirectView("myexam.html"));//跳转到我的考试页面
    			else
    				throw new Exception(status);
    		}
    		else
    			throw new Exception("文件不能为空！");
    		
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("stem/stemEvalSystemError");
    	} 

     }
    // 根据考试名称找到考试改写的等待页面，得到paper之后还可以使用paperId的方法
 /*   @RequestMapping(value = "/waitpage.html")
    public ModelAndView waitPage(HttpServletRequest request,HttpServletResponse response) 
    {
    	try
    	{
	    	User user = this.getSessionUser(request);
	    	int userId = user.getUserId();
	    	
    		String examName=String.valueOf(request.getParameter("examname"));
    		Exam exam=examService.getExamInfoByName(examName);
    		int examId=exam.getExamId();
    		Paper paper = stemService.getPaperInfobyexamname(examName);
    		if(paper.getStatus() != paperStatus.HASPAY.ordinal() || paper.getUserId() != userId)
    			throw new Exception("此页面无法访问");
    		//ChoiceAllo ca = stemService.getChoiceAlloById(paper.getChoiceAlloId());
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		
    		Date createtime = sdf.parse(exam.getExamBegin());
    		long createseconds = createtime.getTime()/1000;
    		Date now = new Date();
    		long nowseconds = now.getTime()/1000;
    		long remainingtime = createseconds - nowseconds + 10;
    		if(remainingtime < 0)
    		{
    			stemService.beginExam(userId,paper.getPaperId());
    			return new ModelAndView(new RedirectView("answer.html?paperId="+paper.getPaperId()));
    		}
    		request.setAttribute("paperId", paper.getPaperId());
    		request.setAttribute("remainingtime", remainingtime);
	    	
	    	return new ModelAndView("stem/stemEvalSystemExamWait");//ExamList");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("stem/stemEvalSystemError");
    	}
    }
    
   /* @RequestMapping(value = "/getchoiceresultlist.html")
    public void getChoiceResultList(HttpServletRequest request,HttpServletResponse response) {
    	PrintWriter out;
    	try
    	{
    		response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
    		String idcard=request.getParameter("idcard");
    		//User user = this.getSessionUser(request);
        	String json = stemService.getChoiceResultList(idcard);
    		out = response.getWriter();
			out.write(json);
    	}
    	catch(Exception e) {
			// TODO Auto-generated catch block
    		JSONObject json=new JSONObject();
    		json.put("status", "exception");
    		try {
				out = response.getWriter();
				out.write(json.toString());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
    }*/
    
    
}
