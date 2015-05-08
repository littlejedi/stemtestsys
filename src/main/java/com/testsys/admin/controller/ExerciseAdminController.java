package com.testsys.admin.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.testsys.admin.bean.Page;
import com.testsys.admin.service.EcAdminService;
import com.testsys.constants.CommonConstant;
import com.testsys.web.bean.Choice;
import com.testsys.web.bean.Open;
import com.testsys.web.bean.Option;

@Controller
@RequestMapping(value = "/ecadmin")
public class ExerciseAdminController extends BaseController{

    @Autowired
    private EcAdminService ecService;

    @RequestMapping(value = "/questionlist.html")
    public ModelAndView questionList(HttpServletRequest request,HttpServletResponse response)
    {
    	try
    	{
    		request.setCharacterEncoding("UTF-8");
    		response.setContentType("text/html; charset=utf-8");
    		response.setCharacterEncoding("UTF-8");
	    	int page = 1;
	    	if(request.getParameter("page") != null)
	    		page=Integer.valueOf(request.getParameter("page"));
	    	//设置查询条件
	    	String condition = "";
	    	String url = "";
	    	//中文参数获取乱码，因此采用这种方式
	    	String qs = request.getQueryString();
	    	if(qs != null)
	    	{
	    		String[] arr1 = qs.split("&");
		    	Map<String,String> para = new HashMap<String,String>();
		    	for (int i = 0; i< arr1.length;i++)
		    	{
		    		String[] arr2 = arr1[i].split("=");
		    		para.put(arr2[0], arr2[1]);
		    	}
		    	String tt = (String) para.get("searchtext");
		    	if(tt != null)
		    	{
		    		String searchtext = java.net.URLDecoder.decode(tt, "utf-8");
		    		condition = " title like '%"+searchtext+"%' ";
		    		url="searchtext="+searchtext+"&";
		    		request.setAttribute("searchtext", searchtext);
		    	}
	    	}
	    	
	    	if(request.getParameter("course") != null)
	    	{
	    		condition += (condition.equals("") ? " ":" and ")+" course = '"+request.getParameter("course")+"' ";
	    		url+="course="+request.getParameter("course")+"&";
	    		request.setAttribute("course", request.getParameter("course"));
	    	}
	    	int listrows = CommonConstant.LISTROWS;
	    	int adminId = this.getSessionAdmin(request).getAdminId();
	    	List<Choice> choicelist=ecService.getChoiceList(page,listrows,adminId,condition);
	    	request.setAttribute("choicelist", choicelist);
	    	
	    	int count = ecService.getMatchChoiceCount(adminId,condition);
	    	request.setAttribute("questioncount", count);
	        List<Page> pagelist=ecService.getPageList("/ecadmin/questionlist.html?"+url+"page=", page, listrows, count);
	        request.setAttribute("pagelist", pagelist);
	    	return new ModelAndView("admin/stemEvalSystemQuestionList");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("admin/stemEvalSystemError");
    	}
    }
    
    @RequestMapping(value = "/openlist.html")
    public ModelAndView openList(HttpServletRequest request,HttpServletResponse response)
    {
    	try
    	{
    		request.setCharacterEncoding("UTF-8");
    		response.setContentType("text/html; charset=utf-8");
    		response.setCharacterEncoding("UTF-8");
	    	int page = 1;
	    	if(request.getParameter("page") != null)
	    		page=Integer.valueOf(request.getParameter("page"));
	    	//设置查询条件
	    	String condition = "";
	    	String url = "";
	    	//中文参数获取乱码，因此采用这种方式
	    	String qs = request.getQueryString();
	    	if(qs != null)
	    	{
	    		String[] arr1 = qs.split("&");
		    	Map<String,String> para = new HashMap<String,String>();
		    	for (int i = 0; i< arr1.length;i++)
		    	{
		    		String[] arr2 = arr1[i].split("=");
		    		para.put(arr2[0], arr2[1]);
		    	}
		    	String tt = para.get("searchtext");
		    	if(tt != null)
		    	{
		    		String searchtext = java.net.URLDecoder.decode(tt, "utf-8");
		    		condition = " title like '%"+searchtext+"%' ";
		    		url="searchtext="+searchtext+"&";
		    		request.setAttribute("searchtext", searchtext);
		    	}
	    	}
	    	if(request.getParameter("course") != null)
	    	{
	    		condition += (condition.equals("") ? " ":" and ")+" course = '"+request.getParameter("course")+"' ";
	    		url+="course="+request.getParameter("course")+"&";
	    		request.setAttribute("course", request.getParameter("course"));
	    	}
	    	int listrows = CommonConstant.LISTROWS;
	    	int adminId = this.getSessionAdmin(request).getAdminId();
	    	List<Open> openlist=ecService.getOpenList(page,listrows,adminId,condition);
	    	request.setAttribute("openlist", openlist);
	    	
	    	int count = ecService.getMatchOpenCount(adminId,condition);
	    	request.setAttribute("questioncount", count);
	    	
	        List<Page> pagelist=ecService.getPageList("/ecadmin/openlist.html?"+url+"page=", page, listrows, count);
	        request.setAttribute("pagelist", pagelist);
	    	return new ModelAndView("admin/stemEvalSystemOpenList");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("admin/stemEvalSystemError");
    	}
    }
    
    @RequestMapping(value = "/readexcel.html")
    public ModelAndView ReadExcel( HttpServletRequest request) 
    {
    	try
    	{
	         String filepath = (String) request.getAttribute("excelfile");
	         String imagepath = (String) request.getAttribute("imagefile");
	         File uploadedFile = new File(filepath);
	         int adminId = this.getSessionAdmin(request).getAdminId();
			 ecService.readUploadedExcel(uploadedFile,imagepath, adminId);
	         return new ModelAndView(new RedirectView("questionlist.html"));
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("admin/stemEvalSystemError");
    	}
     }
    
    @RequestMapping(value = "/viewchoiceinfo.html")
    public void viewChoiceInfo(HttpServletRequest request,HttpServletResponse response) 
    {
    	PrintWriter out;
    	try
    	{
    		response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
    		int choiceId=Integer.valueOf(request.getParameter("choiceId"));
    		String json=ecService.getChoiceInfo(choiceId);  
    		out = response.getWriter();
			out.write(json);
    	}
    	catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @RequestMapping(value = "/questiondetail.html")
    public ModelAndView questionDetail(HttpServletRequest request,HttpServletResponse response) 
    {
    	try
    	{
    		response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
    		int choiceId=Integer.valueOf(request.getParameter("choiceId"));
    		Choice choice =ecService.getChoiceById(choiceId);
    		List<Option> optionlist = ecService.getOptionListById(choiceId);
    		request.setAttribute("choice", choice);
    		request.setAttribute("optionlist", optionlist);
    		return new ModelAndView("admin/stemEvalSystemQuestionDetail");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("admin/stemEvalSystemError");
    	}
    }
    
    @RequestMapping(value = "/opendetail.html")
    public ModelAndView openDetail(HttpServletRequest request,HttpServletResponse response) 
    {
    	try
    	{
    		response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
    		int openId=Integer.valueOf(request.getParameter("openId"));
    		Open open = ecService.getOpenInfoById(openId);
    		request.setAttribute("open", open);
    		return new ModelAndView("admin/stemEvalSystemOpenDetail");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ModelAndView("admin/stemEvalSystemError");
    	}
    }
    @RequestMapping(value = "/removechoice.html")
    public void removechoice( HttpServletRequest request,HttpServletResponse response) 
    {
    	PrintWriter out;
    	try
    	{
    		String choiceIdList=request.getParameter("choiceId");
    		ecService.removeChoice(choiceIdList);
    		out = response.getWriter();
			out.write("success");
    	}
    	catch(Exception e)
    	{
    		try 
    		{
    			e.printStackTrace();
				out = response.getWriter();
				out.write("failed");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	} 

     }
    @RequestMapping(value = "/removeopen.html")
    public void removeopen( HttpServletRequest request,HttpServletResponse response) 
    {
    	PrintWriter out;
    	try
    	{
    		String openIdList=request.getParameter("openId");
    		ecService.removeOpen(openIdList);
    		out = response.getWriter();
			out.write("success");
    	}
    	catch(Exception e)
    	{
    		try {
				out = response.getWriter();
				out.write("failed");
				e.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	} 
     }
    @RequestMapping(value = "/optionmodified.html")
    public void optionModified( HttpServletRequest request,HttpServletResponse response) 
    {
    	PrintWriter out;
    	try
    	{
    		String optionId=request.getParameter("optionId");
    		String content = request.getParameter("content");
    		String point = request.getParameter("point");
    		ecService.updateOption(optionId,content,point);
    		out = response.getWriter();
    		JSONObject json = new JSONObject();
    		json.put("status", "success");
			out.write(json.toString());
    	}
    	catch(Exception e)
    	{
    		try {
				out = response.getWriter();
				JSONObject json = new JSONObject();
	    		json.put("status", "failed");
				out.write(json.toString());
				e.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	} 
     }
    
    @RequestMapping(value = "/imageupload.html")
    public void imageUpload( HttpServletRequest request,HttpServletResponse response,@RequestParam("choiceimg") MultipartFile file) 
    {
    	PrintWriter out;
    	try
    	{
    		request.setCharacterEncoding("UTF-8");
    		out = response.getWriter();
    		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
            ServletContext servletContext = webApplicationContext.getServletContext(); 
    		String imagePath=CommonConstant.UPLOAD_FOLDER+"image";//servletContext.getRealPath("image");
    		String type = request.getParameter("type");
    		if (!file.isEmpty()) 
    		{
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    			Date date=new Date();
    			int suffixpos = file.getOriginalFilename().lastIndexOf(".");
    			String fileName = "modified/"+sdf.format(date)+"-"+request.getParameter("id") + file.getOriginalFilename().substring(suffixpos);//MD5Util. file.getOriginalFilename();
    			File modidir=new File(imagePath + "/modified/");
    	    	if(!modidir.exists())
    	    		modidir.mkdirs();
    			
    			FileCopyUtils.copy(file.getBytes(), new File(imagePath +"/" +  fileName));
    			if(type.equals("choice"))
    			{
    				String choiceId = request.getParameter("id");
    				ecService.updateChoiceImg(choiceId, fileName);
    			}
    			else if(type.equals("open"))
    			{
    				String openId = request.getParameter("id");
    				ecService.updateOpenImg(openId, fileName);
    			}
    			else
    			{
    				String optionId = request.getParameter("id");
    				ecService.updateOptionImg(optionId, fileName);
    			}
    			out.write("{\"src\":\""+fileName+"\"}");
    		}
    		else
    			out.write("{\"status\":\"failed\"}");
    		
    	}
    	catch(Exception e)
    	{
    		try 
    		{
				out = response.getWriter();
				out.write("{\"status\":\"exception\"}");
				e.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	} 
     }
}
